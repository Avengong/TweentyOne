# 差分数组

场景： 频繁的原始数组的某个区间，进行增减。

```
int[] diff = new int[nums.length];
// 构造差分数组
diff[0] = nums[0];
for (int i = 1; i ‹ nums.length; i++) {
    diff[i] = nums[i] - nums[i - 1];
}


反推原始数组：
 
int[] res = new int[diff.length];
// 根据差分数组构造结果数组
res[0] = diff[0];
for (int i = 1; i ‹ diff.length; i++) {
    res[i] = res[i - 1] + diff[i];
}
需求： 
i ,j 之间的元素加3 ： i=0,j=2
nums:[1,2,3,5]
differ: [1,1,1,2]
=>>> res:[4,5,6,5]

推导过程：
differ-i+3,j-3-> new differ[4,1,1,-1] 
res[0]=diff[0]=4
res[1]=res[0]+differ[1]=4+1=5
res[2]=res[1]+differ[2]=5+1=6
res[3]=res[2]+differ[3]=6+-1=5

所以： [4,5,6,5]


对differ[i] =differ[i]+3;
differ[j+1] =differ[j+1]-3;

懂了： 
为什么说在differ[i]+3，相当于在nums[i]之后的值都加了3。因为differ加了3，
根据反推公式去求nums[i]时候，就会得到nums[i]+3。
此时因为diff[i+1]的值是不变的，必然导致nums[i+1]的值也必须要+3，从而nums[i...]后面的值都加了3.
但是我们是有区间的，所以，在j的时候要减3，达到平衡。最终在[i..j]区间都加了3。

如果不想包含i和j，只要改变下下标就行。需要注意的是j+1如果等于数组长度，则表示整个数组从i开始都加了3，那么也就没必要去减3了。

```

# 拼车

初始数组为0，nums[4]。想要在每个位置做一个人。大于0则不能加了。

# 航班座位数

```
这里有 n 个航班，它们分别从 1 到 n 进行编号。

有一份航班预订表 bookings ，表中第 i 条预订记录 bookings[i] = [firsti, lasti, seatsi] 意味着在从 firsti 到 lasti （包含 firsti 和 lasti ）的 每个航班 上预订了 seatsi 个座位。

请你返回一个长度为 n 的数组 answer，里面的元素是每个航班预定的座位总数。

```







