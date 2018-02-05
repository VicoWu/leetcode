package leetcode;

/**
 * Created by wuchang at 12/21/17
 */

public class KMP {

    public static void main(String[] args) {


    }


    public int[] getKMPTableByME(String patternStr){

        char[] pattern = patternStr.toCharArray();
        int[] table = new int[pattern.length];
        int left = 0;
        for(int right=1;right<table.length;){
            if(pattern[left] == pattern[right]){
                table[right] = left+1; //匹配成功，由于left指向了匹配的位置，比如，left=1 ,则说明pattern[0,1]匹配成功，因此匹配成功的字符是pattern[0,1,...,left]，总共left+1个成功的匹配
                right++;left++;
            }else if(left == 0){//由于匹配失败，不断向前递归，此时left == 0了，说明pattern[right]这个字符没有找到任何的公共前缀，自然值设置为0
                table[right++] = 0;
            }
            else{
                /**
                 *
                 匹配失败，不断递归向前查找。为什么是用table[left-1]而不是table[left]呢？
                 举个例子pattern = abcdabc#abcdabd  table已经计算了6个数值[0,0,0,0,1,2,3,0,1,2,3,4,5,6],即table[0,13] ，开始计算table[16]，即最后一个字符d的table值
                 显然，此时left=6,right=14, pattern[0,5]==pattern[10,15]，到了pattern[6]和pattern[16]匹配的时候我们发现pattern[6]!=pattern[16],既然abcdabc无法和abcdabd匹配，那由于abcdabc的最后一个字符c对应的最长前缀是abc,
                 那我们就看看abc能不能匹配abd。显然，这个c的位置是table[left-1]
                 显然， 即 table[left-1] = table[6-1] = table[5] = 2 ，即开始匹配pattern[2]和pattern[16]
                 */
                left = table[left-1];
            }
        }
        return table;
    }

    public int myKMP(String targetStr ,String patternStr){
        char[] target = targetStr.toCharArray();
        char[] pattern = patternStr.toCharArray();

        int[] table = this.getKMPTableByME(patternStr);
        int targetIndex=0;//index of target
        int patternIndex=0; // index of pattern

        while(targetIndex<target.length && patternIndex<pattern.length){
            if(target[targetIndex] == pattern[patternIndex]){ //当前字符匹配，两个index同时向前移动一位
                targetIndex++;patternIndex++;
            }
            else if(patternIndex==0) //当前patternIndex已经为0，可能是递归缩减失败了，此时我们开始用pattern的第一个字符开始匹配target的下一个字符
                targetIndex++;
            else{
                patternIndex = table[patternIndex-1]; //递归缩减，尝试使用前缀进行匹配，看看是否成功
            }
        }
        if(patternIndex==pattern.length) return targetIndex-patternIndex;
        return -1;
    }




}