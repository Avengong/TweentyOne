# 回文字符串判断

正反读都是一样的 如aba， abba。所以可以是奇数也可以是偶数。

解题技巧： 双指针，从字符串中间开始向两边扩散。

```
直接遍历字符串，寻找所有的回文串 
for(0<n< len(s)){
    以i为中心
    以i和i+1为中心

    更新最长回文串
}

void findLoopStr(string str,int start,int end){
    int p1=start,p2=end;
    string res;
    while(p1>=0&&p2<str.length){
    res+=str[p1];
        if(str[p1]==str[p2]){
          p1--;p2++;
        }
    }
    
    //
    return str.substring(p1,p2);
    
}

```














