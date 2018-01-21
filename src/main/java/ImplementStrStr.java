/**
 * Created by wuchang at 12/21/17
 * Question 28
 # 暴力方法
 [这里](https://leetcode.com/problems/implement-strstr/discuss/12807/)讲了暴力解法。
 # KMP方法
 这里的KMP算法是我自己的实现，最核心的是创建KMP表。网上的一些讲解方法非常复杂，KMP表的长度还需要是pattern的长度+1，我创建的KMP表的长度直接等于pattern的长度；
 ```
 pattern     table
 abab        0012
 aabaaac     0101220
 aaaaa       01234
 ```
 这个表里面的每一个数字，代表的是相同的前缀和后缀(前缀不包含自己，后缀包含自己)的长度，应该是非常直观的。
 我们以aabaaac构建表的过程为例：
 ```
 在构建table的过程中，我们有一个左侧指针left和一个右侧指针right，left代表了目前进行匹配的前缀的位置，right代表了目前正在进行匹配的后缀的最后的字符：
 A="aabaaac"
 right=0，显然，table[0]=0，因为是第一个元素，没有前缀不包含自己，一般初始化table的时候直接table[0]=0，然后right从1开始；
 right=1, 由于A[left] == A[right]，因此， table[1]=1，left=1
 right=2，由于b没有最长前缀，我们只好将left修改为table[left-1]即table[0]看看，还是不匹配，由于已经到0了，说明就是匹配失败了，因此table[2]=0，然后，right修改为3，此时left为0
 right=3，由于A[0] == A[3] ，因此，table[3] =1 ，left = 1
 right=4，由于A[1] == A[4] , 因此，table[right]设置为left+1，即table[4] = 1+1=2,
 right=5，由于A[2] !=  A[5] ，因此，我们将left修改为table[left-1] ，即left=table[left-1] = table[2-1] = table[1] = 1,我们看看A[1]和A[5] ,我们发现A[1] == A[5] ,因此table[5] = left+1 = 2，然后left++ ，right++
 right=6,  此时left=2,由于A[2] != A[6] ，因此同样进行递归缩减，缩减过程与right=5一致，只不过最终没有匹配成功，left一直所见到0，因此table[6] = 0

 ```

 其实，我们使用pattern以及KMP表去判断是否是子串的过程和我们对pattern构建KMP table的过程是一样的，pattern构建KMP表的过程，其实就是用当前的前缀作为pattern去匹配后缀的过程。
 ```
 target = abcdabd#abcdabc
 pattern=abcdabc KMP表为[0,0,0,0,1,2,3]
 由于pattern[6]和target[6]匹配失败:
 abcdabd#abcdabc
 abcdabc
 此时对pattern进行缩减，由于abcdabc的最后一个字符c有一个前缀abc，因此我们看看abc能不能匹配(即patternIndex = table[patternIndex-1];即更新pattern的比较位置) abcdabd的后面三个字符:
 abcdabd#abcdabc
 abcdabc
 还是失败，不断递归，最终patternIndex 递减到0，我们只好将targetIndex++，即：
 abcdabd#abcdabc
 abcdabc

 ```




 */

public class ImplementStrStr {
    public int strStr(String target, String pattern) {
        if(pattern.equals(""))
            return 0;
        int[] table = getKMPNextArray(pattern);
        int i=0;int index=0;
        char[] S = target.toCharArray();
        char[] P = pattern.toCharArray();
        for(;i<S.length && index < P.length;){
            if(S[i] == P[index]){
                i++;index++;
                if(index == P.length)
                    return i - pattern.length();
            }
            else if(index == 0){
                i++;
            }
            else{
                index = table[index-1];
            }
        }
        return -1;
    }

    private int[] getKMPNextArray(String needle){
        char[] chars = needle.toCharArray();
        int[] kmpTable = new int[chars.length];
        if(kmpTable.length==0)
            return kmpTable;
        kmpTable[0]=0;int index = 0;
        for(int i=1;i<chars.length;i++){
            if(chars[i]==chars[index]){
                kmpTable[i] = kmpTable[i-1]+1;
                index++;
            }
            else{
                index = kmpTable[i-1];
                while( chars[index] != chars[i]){
                    if(index == kmpTable[index])
                    {
                        index = 0;
                        break;
                    }
                    index = kmpTable[index];
                }
                if(chars[index] == chars[i])
                    kmpTable[i] = kmpTable[index];
                else
                    kmpTable[i] = 0;
            }
        }

        return kmpTable;
    }

    public String strStr1(String haystack, String needle) {
        //KMP algorithms
        if(needle.equals("")) return haystack;
        if(haystack.equals("")) return null;
        char[] arr = needle.toCharArray();
        int[] next = makeNext(arr);

        for(int i = 0, j = 0, end = haystack.length(); i < end;){
            if(j == -1 || haystack.charAt(i) == arr[j]){
                j++;
                i++;
                if(j == arr.length) return haystack.substring(i - arr.length);
            }
            if(i < end && haystack.charAt(i) != arr[j]) j = next[j];
        }
        return null;
    }



    private int[] makeNext(char[] arr){
        int len = arr.length;
        int[] next = new int[len];

        next[0] = -1;
        for(int i = 0, j = -1; i + 1 < len;){
            if(j == -1 || arr[i] == arr[j]){
                next[i+1] = j+1;
                if(arr[i+1] == arr[j+1]) next[i+1] = next[j+1];
                i++;
                j++;
            }
            if(arr[i] != arr[j]) j = next[j];
        }

        return next;
    }

    public static void main(String[] args) {
//       System.out.println( new ImplementStrStr().strStr("BBC ABCDAB ABCDABCDABDE","ABCDABD"));
      //  System.out.println( new ImplementStrStr().strStr("aabaaabaaac","aabaaac"));
        System.out.println( new ImplementStrStr().strStr("abcdabhe","abcdabc"));
    }
}
