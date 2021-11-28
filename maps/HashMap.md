### 存储节点相关类

Entry 、 Node、LinkedHashMapEntry、TreeNode到底什么关系？ 数据是如何存储的呢？

## 类的结构分析

### 常量

//hash数组的初始长度：16。 必须是2的n次方，1<n<=30 static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16 //
hash数组的最大长度： size=1 * 2^30 static final int MAXIMUM_CAPACITY = 1 << 30; static final float
DEFAULT_LOAD_FACTOR = 0.75f; static final int TREEIFY_THRESHOLD = 8;

static final int UNTREEIFY_THRESHOLD = 6; static final int MIN_TREEIFY_CAPACITY = 64;

### 成员变量

### 内部类

Entry<K, V> 是Map接口的内部接口。

Node类实现 Map.Entry<K, V> 接口。封装了int类型的hash值、key、value、指向下一个Node的next指针。 因此，Node是一个单链表结构。

static final class TreeNode<K,V> extends LinkedHashMap.LinkedHashMapEntry<K,V>

class EntrySet extends AbstractSet<Map.Entry<K,V>>，EntrySet用来干什么？

final class Values extends AbstractCollection<V>，作用？

final class KeySet extends AbstractSet<K>，作用？

树节点继承了LinkedHashMap的LinkedHashMapEntry static final class TreeNode<K,V> extends
LinkedHashMap.LinkedHashMapEntry<K,V>

LinkedHashMap的LinkedHashMapEntry 又继承了HashMap的Node

```
 static class LinkedHashMapEntry<K,V> extends HashMap.Node<K,V> {
        LinkedHashMapEntry<K,V> before, after;
        LinkedHashMapEntry(int hash, K key, V value, Node<K,V> next) {
            super(hash, key, value, next);
        }
    }
```

### 成员方法

## 构造函数

```
 public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
        //数组的初始length不能超过限制                                       initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
        this.loadFactor = loadFactor;
        // 初始化或者扩容时候的阈值，肯定会是2的幂。
        //注意： 由于外部可以传initialCapacity值，因此，这个值可能是3，那么tableSizeFor方法的作用就是
        //保证threshold值始终为2的幂。
        this.threshold = tableSizeFor(initialCapacity);
    }
```

构造并没有初始化数组之类的。 threshold 跟 capacity * load factor 什么关系？ 必须肯定的是 threshold =capacity *
load。但是在构造方法中的threshold初始化的时候的值理论上就是capacity。为什么这样啊？ 难道之前的公式不对吗？
别急，等你看到resize方法的时候就知道了。因为它需要这个构造后的threshold来赋值给newCap，然后
threshold会重新在计算。也就是说构造中的threshold的赋值并没有起到threshold的作用。 理论上
threshold=capacity（注意，这仅仅只在构造方法中哦），但是，实际开发中有可能capacity不是2的幂。需要通过下面的方法转换。

```
    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
```

这个算法怎么回事？ 找到>=cap的最小2的幂。 举个例子：

- cap=0，threshold=1。
- cap=1，threshold=1。
- cap=3，threshold=4。
- cap=4，threshold=4。因为4是2的幂，所以不变。
- cap=5，threshold=8。 这样就清楚了。

## put

```
 public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }
```

### hash 函数

```
 static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
```

### putVal 函数

重点看下putVal方法：

```
 final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        // 如果hash数组为null，就初始化。（第一次put，肯定是null）
        if ((tab = table) == null || (n = tab.length) == 0)
        // 1，resize()方法 需要关注
            n = (tab = resize()).length;
        if ((p = tab[i = (n - 1) & hash]) == null)
            // 2，如果数组中下标i的位置为null，则表示没有冲突，newNode节点 直接存进去
            tab[i] = newNode(hash, key, value, null); 
        else {
            // 3，下标i的位置有值了
            Node<K,V> e; K k;
            // 3.1，先比较当前节点与要插入的hash和key值是否相等，相等肯定是同一个节点。
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof TreeNode)
            // 3.2，冲突，且不是当前节点。如果是树节点，则插入到红黑树里面。
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
            // 3.2，既不是第一个节点，也不是二叉树节点。则去链表中查找
                for (int binCount = 0; ; ++binCount) {
                        //3.3 在整个链表都没有找到，插入到链表尾部
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        //3.3.1 继续检查是否触发树化逻辑
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    //3.4 如果链表中某个节点匹配，则break循环，找到了!
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //4，如果已经存在，那么开始更新
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                // 更新
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                // 4.1 专门留给LinkedHashMap的后续操作，啥操作？？
                afterNodeAccess(e);
                return oldValue;
            }
        }
        //5, 只有插入新元素才会走这里逻辑
        ++modCount; //
        // 所以插入的元素个数大于扩容阈值，则进行扩容
        if (++size > threshold)
            resize();
            //
        afterNodeInsertion(evict);
        return null;
    }
```

### resize()

resize 函数: 都是2的次数进行初始化或者扩容的？ 是的。

- 初始化hash数组size，大小是16或者外部指定。 是threshhold 成员变量
- 翻倍增加size(扩容)，

```
 final Node<K,V>[] resize() {
        // 拿到旧的table数组 以及旧的size
        Node<K,V>[] oldTab = table;
        //oldCap一开始肯定是0
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold; // 旧的扩容阈值，
        int newCap, newThr = 0;
        // oldCap>0 肯定是发生了扩容
        if (oldCap > 0) {
            //如果旧的size已经很大了，超过2^30，那么指定为Integer.MAX_VALUE，也就是2^32。
            if (oldCap >= MAXIMUM_CAPACITY) { 
                threshold = Integer.MAX_VALUE;
                // 直接返回旧的hash数组 ？？？ 不扩容了吗？？
                return oldTab;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                     //如果新的数组长度是旧的2倍且小于2^30方 同时 旧的数组长度> 16。
                     //那么就直接扩容为2倍大小。
                newThr = oldThr << 1; // double threshold
        }
        else if (oldThr > 0) // initial capacity was placed in threshold
            //如果外部通过构造指定了初始化容量值，会走这里的初始化逻辑。new HashMap(3)
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            //走这里表示，我们是通过空参构造方法构造的如： new HashMap()
            newCap = DEFAULT_INITIAL_CAPACITY; //16
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY); //16*0.75=12 
            // newThr=12,也就是说如果数组占用个数>=12，那么就表示触发扩容阈值，需要扩容了。
            //换句话说，达到原始容量的 0.75倍（加载因子倍），即表示快装不下了，再装效率就会下降，必须要扩容。
        }
        ////如果外部通过构造指定了初始化容量值，会走这里的初始化逻辑。手动计算新的threashHold
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        //至此，我们需要明确两个概念cap和thr：
        cap：数组容量。 thr：整个元素个数的阈值。
        以上的逻辑都是在根据旧的容量和扩容阈值，来计算得到新的容量和扩容阈值。
        //更新扩容阈值，下次超过了它就需要再次扩容
        threshold = newThr;
        @SuppressWarnings({"rawtypes","unchecked"})
        // 直接根据newCap new出新数组newTab
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;// 赋值新数组
        //遍历旧的数组 ，准备拷贝到新数组里面
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    //把旧数组对应的位置置空。
                    oldTab[j] = null;
                    //该节点只有自己一个人
                    if (e.next == null)
                        //那么根据该元素原来的hash值，重新计算下标，存入元素。 
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        //如果是二叉树类型，那么进行切割分组
                        //内部工作： 也是根据原来数组长度，进行分组：高、低。
                        //然后在重新存储对应的心的下标位置。注意，这里有可能会因为二叉树的个数分组后会出现小于6的情况，
                        //触发二叉树退化为链表。
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order 维护顺序
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                        // 链表有多个元素
                            next = e.next;
                            //e.hash & oldCap 用来判断>=oldCap。
                            //==0表示 新插入的位置还是之前的位置。处于低位，也就是j。为什么叫低位？j<oldCap。
                            //else表示 新插入的位置是高位，也就是j+oldCap位置。
                            //本质是吧原来的链表进行分组，在插入到新的数组里。
                            if ((e.hash & oldCap) == 0) {//??? 什么意思？ 表示是低位组。
                                //hash不都是一样的吗？？ 不一定哦。。。铥。
                                //之前的理解： key转成hash，hash对数组长度取余得到数组下标，因此下标一致就存在一起了。
                                //但是，下标一致并不代表hash就是一样的。如果长度变化了，那么hash取余的值也会变化。
                                //因此，蹲在一起的节点并不一定是hash冲突了。但，hash冲突了那肯定是存到一起了。
                               
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else { //高位组
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
```

## 红黑树的插入 putTreeVal

这个不要太细节啦，有兴趣的朋友可自行研究。

```
        /**
         * Tree version of putVal.
         */
        final TreeNode<K,V> putTreeVal(HashMap<K,V> map, Node<K,V>[] tab,
                                       int h, K k, V v) {
            Class<?> kc = null;
            boolean searched = false;
            // 找到红黑树树的根节点。两种情况：1，该节点不是根节点。2，该节点是根节点。这里应该是put方法调用的。
            TreeNode<K,V> root = (parent != null) ? root() : this;
            for (TreeNode<K,V> p = root;;) {
                int dir, ph; K pk;
                // 以下三个判断都是根据当前节点的hash值和要插入的hash值比较： 
                if ((ph = p.hash) > h) //大于
                    dir = -1;
                else if (ph < h)//小于
                    dir = 1;
                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
                    // hash值相等，且key相等，就是同一个节点，直接返回节点p，那不更新value了吗？？ 
                    //答案：更新啊！这里负责找到节点，更新操作在put方法的后部分呢！
                    return p;
                else if ((kc == null &&
                          (kc = comparableClassFor(k)) == null) ||
                          //hash值相等，key不等，发生了hash冲突。根据key做一次比较，来确定是插入到左节点还是右节点
                          //k.compareTo(x) ，比较当前节点pk和要插入的节点k的key值
                         (dir = compareComparables(kc, k, pk)) == 0) {
                         //如果dir==0，表示根据key按照上面的规则比较还是没比较出来。需要去左右子树中查找该节点是否存在
                    if (!searched) {
                        TreeNode<K,V> q, ch;
                        searched = true;
                        if (((ch = p.left) != null &&
                             (q = ch.find(h, k, kc)) != null) ||
                            ((ch = p.right) != null &&
                             (q = ch.find(h, k, kc)) != null))
                             //按照key的规则，遍历左右子树，找到了节点，返回。
                            return q;
                    }
                    //到这里，证明发生了hash冲突，且常规key比较也失效了。
                    //在当前节点以及当前节点的左右子树中都没有找到，要插入的节点是新的，那我们必须要比较出key值的大小了。
                    //因为如果等于0，那就破坏了红黑树的规则了。原则就是一定要找到插入节点和当期节点key的不一样的地方
                    dir = tieBreakOrder(k, pk); //这个方法一定会返回-1或者1.确保顺序
                }

                TreeNode<K,V> xp = p;
                //根据dir的值，来决定往左还是右子树中插入
                if ((p = (dir <= 0) ? p.left : p.right) == null) {
                    Node<K,V> xpn = xp.next;
                    TreeNode<K,V> x = map.newTreeNode(h, k, v, xpn);
                    if (dir <= 0)
                        xp.left = x;
                    else
                        xp.right = x;
                    xp.next = x;
                    x.parent = x.prev = xp;// x已经插入到xp节点的左节点或者右节点。
                    if (xpn != null)
                        ((TreeNode<K,V>)xpn).prev = x; //这里还维护了一个双向链表？？？
                    moveRootToFront(tab, balanceInsertion(root, x));
                    return null;
                }
            }
        }
```

## 树化 treeifyBin

```
final void treeifyBin(Node<K,V>[] tab, int hash) {
        int n, index; Node<K,V> e;
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
        //如果数组为空，或者数组的长度小于64. 没达到最小树化容量。那么只进行扩容。
            resize();
        else if ((e = tab[index = (n - 1) & hash]) != null) {
        // 拿的插入到链表头,，开始遍历链表，转为树节点
            TreeNode<K,V> hd = null, tl = null;
            //hd 是什么？ tl是什么？ 
            do {
                TreeNode<K,V> p = replacementTreeNode(e, null);
                //p虽然是TreeNode，但是还是以链表结构相连。hd就是链表的头结点。
                //第一次tl肯定是null。所以hd、tl都指向第一个节点。
                if (tl == null)
                    hd = p;// 树头
                else {
                //
                    p.prev = tl;
                    tl.next = p;
                }
                //tl不断的指向最后一个节点
                tl = p;
            } while ((e = e.next) != null);// 遍历原链表节点。
            // 以上总结： 把以Node为节点的单向链表 转变为以TreeNode为节点的双向链表。双向链表用来干啥？？？？
            //把双向链表存储到数组的位置
            if ((tab[index] = hd) != null)
                //从头结点开始真正构建红黑树
                hd.treeify(tab); 
        }
    }
```

### TreeNode的 treeify()

功能：从该节点开始，构造红黑树。且返回树的根节点。 红黑树的特点就是，左小右大。因此，核心思想就是根据hash值的大小来决定是往左子树还是右子树插入。

```
 final void treeify(Node<K,V>[] tab) {
            TreeNode<K,V> root = null;
            // 从头结点开始遍历
            for (TreeNode<K,V> x = this, next; x != null; x = next) {
                next = (TreeNode<K,V>)x.next;
                x.left = x.right = null;
                if (root == null) {
                //构造红黑树的根节点
                    x.parent = null;
                    x.red = false;
                    root = x;
                }
                else {
                // 根节点不为空，准备要插入的节点 x
                    K k = x.key;
                    int h = x.hash;
                    Class<?> kc = null;
                    //从根节点root开始遍历 ，
                    for (TreeNode<K,V> p = root;;) {
                        int dir, ph;
                        K pk = p.key;
                        // 如果节点p的hash大于要插入的节点x的 hash值，dir=-1.
                        if ((ph = p.hash) > h)
                            dir = -1;
                        else if (ph < h) //如果小，dir=1.
                            dir = 1;
                        else if ((kc == null &&
                                  (kc = comparableClassFor(k)) == null) ||
                                  //通过compareTo方法 比较一次，如果dir==0，那么后面在比较一次
                                 (dir = compareComparables(kc, k, pk)) == 0)
                                 //dir==0，第二次key比较！！
                            dir = tieBreakOrder(k, pk);

                        //走出if-else逻辑，得到dir的值了
                        TreeNode<K,V> xp = p;
                        //一直遍历:如果dir<=0，则往左子树继续遍历。如果> 0则往右子树继续遍历.
                        //直到找到最后一个节点。
                        if ((p = (dir <= 0) ? p.left : p.right) == null) {
                            //p就是最后一个节点了，要插入的节点x的父节点指向xp。
                            x.parent = xp;
                            //插到xp的左边节点
                            if (dir <= 0)
                                xp.left = x;
                            else
                                xp.right = x;//插到xp的右边节点
                            //保证红黑树平衡
                            root = balanceInsertion(root, x);
                            break;
                        }
                    }
                }
            }
            //确保root是树的根节点。
            moveRootToFront(tab, root);
        }
```

至此，红黑树已经构建完成。 整个put方法涉及到的知识点也讲完了。 接着看get方法。

## get

```
 public V get(Object key) {
        Node<K,V> e;
        // 根据key生成hash，那key跟hash是什么关系？ 
        //答案： 不同的key可以she鞥成相同的hash，这就是发生了碰撞。 hash相同并不代表key就相同。
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }
```

返回节点，或者返回null。

```
  final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        // 数组不为null+数组长度>0+hash对数组长度取余得到下标对应的第一个节点不为null
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {
            
            if (first.hash == hash && // always check first node
                ((k = first.key) == key || (key != null && key.equals(k))))
                //如果hash相同，且key也相同，那么就是第一个节点。
                return first;
            if ((e = first.next) != null) {
            //不是第一个节点。首先判断是否是红黑树节点，如果是那么接下来就去红黑树中查找
                if (first instanceof TreeNode)
                    //first就是红黑树的根节点。遍历二叉树。
                    // 内部遍历过程： 根据hash的大小，进行递归查找。
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                    
                //链表结构，开始遍历查找，找到就返回。
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }
```

## remove

```
 final Node<K,V> removeNode(int hash, Object key, Object value,
                               boolean matchValue, boolean movable) {
        Node<K,V>[] tab; Node<K,V> p; int n, index;
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (p = tab[index = (n - 1) & hash]) != null) {
            //得到hash取余对应数组的头结点
            Node<K,V> node = null, e; K k; V v;
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                // 就是头结点
                node = p;
            else if ((e = p.next) != null) {
            //不是头结点，则判断next节点是不是红黑树节点
                if (p instanceof TreeNode)
                    node = ((TreeNode<K,V>)p).getTreeNode(hash, key); // 从红黑树拿到节点
                else {
                    do {
                        if (e.hash == hash &&
                            ((k = e.key) == key ||
                             (key != null && key.equals(k)))) {
                           //链表，找到节点
                            node = e;
                           
                            break;
                        }
                        p = e;
                    } while ((e = e.next) != null); //链表的遍历
                }
            }
            if (node != null && (!matchValue || (v = node.value) == value ||
                                 (value != null && value.equals(v)))) {
                                 //开始移除逻辑
                if (node instanceof TreeNode)
                    ((TreeNode<K,V>)node).removeTreeNode(this, tab, movable);
                else if (node == p)
                    tab[index] = node.next; // 如果是数组下标对应的头结点
                else
                    p.next = node.next; // 正常的节点，删除找到的node节点。
                ++modCount;
                --size; //容量更新
                afterNodeRemoval(node);
                return node;
            }
        }
        return null;
    }
```

# 遍历

Set、Map、List 顶层接口的区别？ Map：抽象出了<k,v> 键值对的存储结构。接口 Set： 接口,继承了Collect接口 没有重复元素存储结构 无序，最多允许一个null元素
List:
接口，继承collection接口 可以允许重复元素存在的存储结构。 允许多个null 有序

## EntrySet

## HashMap的遍历有三种方式：

```
 HashMap<String, String> map = new HashMap<>();
 
 map.entrySet().iterator() //1，从EntrySet维度
 map.keySet().iterator()// 2，从key的维度
 map.values().iterator()// 3，从values维度 
```

三者都是都通过iterator迭代器实现遍历。

### map.entrySet()方法 返回什么？

```
// 返回缓存的所有键值对数据。  EntrySet
  public Set<Map.Entry<K,V>> entrySet() {
        Set<Map.Entry<K,V>> es;
        return (es = entrySet) == null ? (entrySet = new EntrySet()) : es;
    }
```

答案： 返回不重复的Set集合，里面包含Entry类型。 Entry类型是 Map中的一个内部类。

### map.keySet() KeySet

```
 public Set<K> keySet() {
        Set<K> ks = keySet;
        if (ks == null) {
            ks = new KeySet();
            keySet = ks;
        }
        return ks;
    }
```

返回所有的key的集合。

### map.values()

```
 public Collection<V> values() {
        Collection<V> vs = values;
        if (vs == null) {
            vs = new Values();
            values = vs;
        }
        return vs;
    }
```

返回所有的value集合，存在重复

## 迭代器

```
 // 抽象类 
 abstract class HashIterator {
        Node<K,V> next;        // next entry to return
        Node<K,V> current;     // current entry
        int expectedModCount;  // for fast-fail
        int index;             // current slot

        HashIterator() {
            expectedModCount = modCount;
            Node<K,V>[] t = table;
            current = next = null;
            index = 0;
            
            if (t != null && size > 0) { // advance to first entry
                do {} while (index < t.length && (next = t[index++]) == null);
            }
            // 1，next是table数组中第一个有值的Node节点。
        }

        public final boolean hasNext() {
            return next != null;
        }
        // 迭代的核心方法
        final Node<K,V> nextNode() {
            Node<K,V>[] t;
            Node<K,V> e = next;
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if (e == null)
                throw new NoSuchElementException();
                //如果下一个不为null则返回，直到链表或红黑树的末尾
            if ((next = (current = e).next) == null && (t = table) != null) {
                //已经遍历完上一个节点，接着往数组下一个位置遍历。知道数组最后一个位置
                do {} while (index < t.length && (next = t[index++]) == null);
            }
            //下一个不为null则返回.
            return e;
        }

        public final void remove() {
            Node<K,V> p = current;
            if (p == null)
                throw new IllegalStateException();
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            current = null;
            K key = p.key;
            removeNode(hash(key), key, null, false, false);
            expectedModCount = modCount;
        }
    }

    final class KeyIterator extends HashIterator
        implements Iterator<K> {
        public final K next() { return nextNode().key; }
    }

    final class ValueIterator extends HashIterator
        implements Iterator<V> {
        public final V next() { return nextNode().value; }
    }

    final class EntryIterator extends HashIterator
        implements Iterator<Map.Entry<K,V>> {
        public final Map.Entry<K,V> next() { return nextNode(); }
    }

```









