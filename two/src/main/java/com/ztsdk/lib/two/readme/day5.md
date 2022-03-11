# 二分查找技巧

使用场景： 寻找一个数 寻找左侧边界 寻找右侧边界

细节问题： mid到底是加1还是减1，while循环里面是带等号还是不带等号？？

解题框架：

```
int binarySearch(int[] nums,target){
    int left-0,right=...; ///// 1
   
    while(left < right){ //边界 2
       int mid=left+(right-left)/2; /////注意溢出？？ left、right太大？？
        if(nums[mid]==target){
            return ...; /// 3
        }else if(nums[mid]>target){
            right ...; /// 4
        }else if(nums[mid]< target){
            left ...; /// 5
        }
    }
    return ...; /////
}
```

注意点： mid的溢出问题： 边界问题： 1 right=nums.length 还是 right=nums.length-1？ 前者是左闭右开[left,right),
后者是左闭右闭 [left,right]
2 while(left<=right) 还是 while(left< right) ？ 后者当[2，2] 时，就会漏掉mid=2这个位置，前者则不会漏掉

以上是两种方式，可以组合使用。

3 left=mid-1 还是 mid？ 因为mid位置的元素跟target已经不相等了，所以没必要在包含在搜索区间了。 因此，是mid-1 或者mid+1

4 right=mid+1 还是 mid？

# 寻找一个数

理解了临界问题，就满足了。

```
 public int search(int[] nums, int target) {
    int left=0, right=nums.
 
 
 
 
 
 }



```

# 寻找左侧边界的二分查找

how？

# 寻找右侧边界的二分查找















