package leetcode;

import java.util.Arrays;

/**
 * Created by wuchang at 1/3/18
 * Question 132
 *
 # 我的解法
 minCut数组保存了当前已经计算完成了最小切割数的结果，比如，`minCut[k]`代表s的前k个字符的最小切割数量，例如， `minCut[1]`，
 代表s 的第一个字符切割数量，显然，一个字符本身就是回文的，因此不需要切割，故而`minCut[1]=0`。如果我们知道了前k个minCut，
 即，`minCut[0.....i]`，现在加入了第i+1个字符，需要计算`minCut[i+1]`，显然，至少，`minCut[i+1]`可以等于`minCut[i]+1`，因为至少这个新加入的字符可以单独作为一个切割，但是，也许，还有更小的切割方案。于是，我们让j从0遍历到i-1，如果`s[j,i]`是一个回文串，那么，这种切割方式形成的切割数量就是`minCut[j]+1`：

 ```
 比如：aabab 显然，现在minCut[1]=0,minCut[2]=0,minCut[3]=1,minCut[4]=2,然后，加入了一个字符b，显然，
 如果字符b自成一个切割，那么至少这个切割数可以是minCut[4]+1 = 3，但是，有没有更小的切割方案呢？显然，如果有更小的切割方案，b肯定应该与左侧的某一部分串形成一个回文串，于是，我们让j=0，看看aabab是不是回文，我们让j=1,看看abab是不是回文，让j=2,看看bab是不是回文，这时候我们看到j=2的时候bab是回文，因此，这种切割形成的切割数量为minCut[2]+1 =1。这样，当j在[0,i]的范围内遍历完，我们就得到了minCut[i]。
 ```

 那么，我们判断一个串是不是回文串的时候，能不能不每次都重新计算呢？可以。我们使用isPar的二维数组保存已经计算得到的某个子串是否是回文的结果，
 即`isPar[j][i]`代表`s[j..i]`(左右边界都包含)是否是回文。显然，当我们计算到i=k的时候，`i<k`的所有子字符串的`isPar`都计算完成了，因此，我们只需要比较两端的字符就可以得到是否是回文串：
 ```if(input[j] == input[i] && (i==j+1 || isPar[j+1][i-1]))```





 # 优化解法

 这个解法的相关解释在[这里](https://leetcode.com/problems/palindrome-partitioning-ii/discuss/42198)

 这是别人的解法，与我上面的解法不同的地方，就是我上面的解法是从左到右依次添加字符，我们只能使用isPar这个数组来保存之前的任意子串是不是回文串的
 中间结果。可是，如果我们不是从左到右，而是以i为中心，向两侧辐射，那么只要中间[i..j]是回文，并且S[i-1] == S[j+1]，那么[i-1,j+1]就是回文串。

 初始化状态下，每一个i，它的切割数量最多是i-1，比如,我们1个字符本身回文，切割数是0，2个字符切割数是1，3个是2，以此类推。

 对于abcdefghij，我们以d为例，以d为中心形成的回文串包括了两种回文串，奇数回文串和偶数回文串，奇数回文串有d , cde, bcdef, abcdefg  ,
 偶数回文串有   de  cdef bcdefg abcdefgh，拿奇数回文串S="bcdcf"为例，对于中心S[2]='d'，i=2，当j=0,由于S[i+j]=S[i-j]，即单字符'd'是回文，这时候就可以更新一下cut[3] = min(cut[3],1+cut[2])，
 即字符串'bc'和d形成的切割的数量和当前旧的切割数量哪个更小，去较小值，然后j=1，就需要看看新的切割[b,cdc]形成的切割是不是小于旧的切割数量，也取较小值。

 */

public class PalindromePartitioningII {
    /**
     * 这是我的解法
     * @param s
     * @return
     */
    public int minCut(String s) {

        char[] input = s.toCharArray();
        boolean[][] isPar = new boolean[s.length()][s.length()]; //存放s的任意子串是不是回文串
        for(boolean[] row:isPar)
            Arrays.fill(row,false);
        int[] minCut = new int[s.length()+1];//minCut[k]代表s的前k个字符的最小切割数量，例如， minCut[1]，代表s 的第一个字符切割数量
        minCut[0] = -1;
        for(int i=0;i<s.length();i++){//我们逐个计算s[0,i]所需要的minCut
            int min = minCut[i]+1;//至少可以因为加入了一个字符增加一个cut
            //我们现在已经计算出了s[0,i]内任意一个子串是否是回文串，并且知道对于任意k<i的minCut[0,k]
            isPar[i][i] = true;
            for(int j=0;j<i;j++){ //对于小于i的任意一个位置j，我们计算按照s[0,j]和s[j+1,i]进行切割的成本
            //利用前面已经计算完毕的isPar,判断s[j...i]是不是回文串，如果
                if(input[j] == input[i] && (i==j+1 || isPar[j+1][i-1])){
                    min = Math.min(min , minCut[j]+1);
                    isPar[j][i] = true;
                }
            }
            minCut[i+1] = min;
        }

        return minCut[s.length()];
    }


    /**
     * 别人的优化解法
     * @param str
     * @return
     */
    public  int minCut3(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[] cut = new int[n+1];  // number of cuts for the first k characters
        for (int i = 0; i <= n; i++) cut[i] = i-1;// cut[i]代表前i个字符所需要的cut数量，即s[0,i-1]所需要的cut数量
        for (int i = 0; i < n; i++) {
            for (int j = 0; i-j >= 0 && i+j < n && s[i-j]==s[i+j] ; j++) // j代表半径，这个回文串含有奇数个字符，以i作为中心
                cut[i+j+1] = Math.min(cut[i+j+1],1+cut[i-j]); //由中心向外扩展计算回文串，一旦发现不是回文，这个循环就退出了，比如cabcbdg,加入中心是c，因此，c是回文，bcb是回文，abcbd不是回文，这时候循环退出

            for (int j = 1; i-j+1 >= 0 && i+j < n && s[i-j+1] == s[i+j]; j++) // j代表半径，这个回文串含有偶数个字符,以i和i+1作为中心
                cut[i+j+1] = Math.min(cut[i+j+1],1+cut[i-j+1]);
        }
        return cut[n];
    }





    public static void main(String[] args) {
        new PalindromePartitioningII().minCut("efe");
    }

}
