# 前缀和技巧 返回一个索引区间之内的元素和

使用场景 ： 在原地对数组频繁的获取某个区间的和。

直接加不香吗？ 为什么要做前缀和？

说回到前缀和技巧，我们说要将 sumRange 函数的时间复杂度降为 O(1)，说白了就是不要在 sumRange 里面用 for 循环，咋整？ 直接看代码实现：

```
nums:[1,2,2,3]
preSum: [0,0+1,1+2,3+2,5+3]

如： 下标为1，那么preSum[1]=1 表示下标前面的数字之和，不包括该下标本身。
为什么长度要加一，为了给数组的第一个下标添加前缀和0。

 // 前缀和数组
    private int[] preSum;

    /* 输入一个数组，构造前缀和 */
    public NumArray(int[] nums) {
        preSum = new int[nums.length + 1];
        // 计算 nums 的累加和
        for (int i = 1; i ‹ preSum.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }
    }
    
    /* 查询闭区间 [left, right] 的累加和 */
    public int sumRange(int left, int right) {
        return preSum[right + 1] - preSum[left];
    }
```

比方说，你们班上有若干同学，每个同学有一个期末考试的成绩（满分 100 分），那么请你实现一个 API，输入任意一个分数段，返回有多少同学的成绩在这个分数段内。

```
int[] scores; // 存储着所有同学的分数
// 试卷满分 100 分
int[] count = new int[100 + 1]
// 记录每个分数有几个同学
for (int score : scores)
    count[score]++
// 构造前缀和
for (int i = 1; i ‹ count.length; i++)
    count[i] = count[i] + count[i-1];

// 利用 count 这个前缀和数组进行分数段查询
```















