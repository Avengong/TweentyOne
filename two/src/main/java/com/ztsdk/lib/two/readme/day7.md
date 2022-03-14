# 滑动窗口 技巧

双指针技巧的一种 。 使用场景： 很多时候是在处理字符串相关的问题。

# 滑动窗口原理

```
int left, right;
while(right< s.size()){
    window.add(s[right]);
    right++;
    while(window.needShrink){
        window.remove(s[left]);
        left++;
    }
    
}
时间复杂度为： O（n）
```

# 滑动窗口算法框架：

````
void slidingWindow(string s, string t) {
    Hashmap<char,int> char,need;
    for(char c: t){
        need.put(c,need.getDefualt(c,0)+1);
    }
    
    int left,right=0;
    while(right< s.size){
        char c=s[right];
        right++;
        //进行窗口window的更新。。。。
    
        while(window need shrink){
            cahr c= s[left];
            left++;
            //进行窗口window的更新。。。。
        
        }
    }
}
````

# 最小覆盖子串

# 无重复字符的最长子串












