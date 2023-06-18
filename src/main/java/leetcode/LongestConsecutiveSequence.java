package leetcode;

import java.util.*;

/**
 * Question 128
 *
 * 问题：如果可以在O(n)的时间复杂度内完成找到连续的数，是不是意味着可以在O(n)的时间复杂度内完成排序呢？不是的。
 * 因为连续是比有序更加严格的条件，连续必须递增且增量为1，有序则必须递增但是不要求增量为1；
 因此，如果我们对所有的数据存入hash，那么，随便给出一个数字，我们就可以在O(1)的时间内确认这个数字或者任何其它数字(比如与他相邻的数字)是否存在。

 ## 解法1
 https://discuss.leetcode.com/topic/15383/simple-o-n-with-explanation-just-walk-each-streak
 先把所有数字存入hash，然后，遍历数组中的每个元素，如果发现这个元素的前一个元素不存在，那么这个元素就必定是一个序列的第一个元素(这个序列)的长度可能为1，
 此时逐渐递增查找获取以这个数字为开头值的序列长度。最终获取一个全局最大值

 ## 解法2
 https://discuss.leetcode.com/topic/6148/my-really-simple-java-o-n-solution-accepted

 用一个map，map的key是数组中的数字，value是这个数字所在的序列的长度
 比如，现在序列[3,4,5,6,7,8,10,11,12]，因此有(3,6),(8,6),(10,3),(12,3)，因为3和8所在的序列长度都是8，10和12所在的序列长度都是3。然后，新来了一个数字9，
 如何确定这个数字所在的序列的长度呢？这需要看看这个数字9-1=8和9+1=10的序列情况，由于8的序列长度已经确定了，因此，9所在的序列的左边界肯定8所在的序列的左边界，
 9所在的序列的右边界肯定就是10所在的序列的右边界，从而，9所在序列的长度就是6+3+1=10了。同时，最左侧的数字3和最右侧的数字12的序列长度值也需要更新为10.尽管数字8和10没有更新，
 但是不应惜那个结果了，因为他们处在序列的中间；

 ## 我的解法
 我使用一个数组保存每个数字的取余数的值，可以想见，如果两个数字相邻，那么余数肯定相邻；但是，余数相邻，两个数字不一定相邻。那么，这个余数的底数取多少呢？就取数组的长度值length，这样，
 一般情况下，如果存在连续序列，这个序列通过取余数映射到余数数组的对应位置，由于连续序列的长度肯定不大于length，因此，这个连续序列的每个值映射到这个余数数组的某个位置并且连续数组中不会有两个数字映射到余数数组的同一位置。

 我在代码中数组长度设置为`length*2-1`，这是考虑到存在负数的情况。比如，输入数据的数组长度为3，因此，余数数组remainSets长度为5，remainSets[0]对应余数为-2的数据，
 remainSets[0]对应余数为-1的，remainSets[2]对应余数为0的数据，remainSets[3]对应余数为1的数据，remainSets[4]对应余数为2的数据。
 显然，对于余数为i的，有可能存在多个值，比如，length=10，余数为3，那么，3，13，23都是的。因此，某一个余数对应的多个不同的值，我存放到set里面。
 余数数组建立完毕以后，如果计算最长连续序列呢？分两种情况：
 1.模数相同，余数连续则连续，比如，length=10，那么12，13，14是连续的，他们余数连续，模数都是1
 2.余数连续，模数相隔为1，比如，length=10，那么18，19，20，21是连续的，他们的模数相隔为1

 因此，我们在遍历余数数组的时候，通过一边遍历，处理完情况1，然后，针对情况2，我们单独提取了数组尾部和数组首部的值，看看是否满足这种特殊情况。


 */
public class LongestConsecutiveSequence {
    public int longestConsecutive(int[] nums) {

        int length = nums.length;
        if(length==0) return 0;
        Set<Integer>[] remainSets = new HashSet[length*2-1];


        for(int i=0;i<length;i++)
        {
            int key = length+nums[i]%length-1;
            Set<Integer> remainResult = remainSets[key];
            if(remainResult == null)
                remainResult = new HashSet<Integer>();
            remainResult.add(nums[i]/length);
            remainSets[key] = remainResult;
        }

        Map<Integer,int[]> segment = new HashMap<Integer,int[]>(); //result的key为数字除以length的值，比如10，12，12的值为1，而22，23，24的值为2，value是一个数组，分别表示(当前区间的余数左侧值，当前区间的余数右侧值,当前区间的长度)
        Integer globalMaxResult = 0;

        for(int i=0;i<remainSets.length;i++){
             Set<Integer> remainSet = remainSets[i]; //余数为i的数字集合
            if(remainSet==null)
                continue;
             for(Integer item:remainSet){ //对于//余数为i的数字集合中的所有值
                 int[] remainValue = segment.get(item); //比如length=10，对于余数2对应的所有数字，比如22，32，52，102，当前处理到202(202/10=20)，发现还没有20对应的中间结果，说明是第一次遇到200-209之间的值
                 if(remainValue == null) //上一个余数值不存在
                 {
                     int[] newValue = {i,i,1};
                     segment.put(item,newValue);
                     //if(globalMaxResult<1) globalMaxResult = 1;
                 }
                 else{
                     if(i == remainValue[1]+1){//余数值连续，比如28,29

                         remainValue[1] = i; //更新余数的右侧值为当前值
                         remainValue[2]++;
                         if(globalMaxResult < remainValue[2])
                             globalMaxResult = remainValue[2];
                     }
                     else{//余数不连续

                         remainValue[0] = remainValue[1] = i;
                         remainValue[2] = 1;
                     }
                 }

                 remainValue = segment.get(item);
                 globalMaxResult = globalMaxResult < remainValue[2]?remainValue[2]:globalMaxResult;
             }
        }

        //有三处可能需要拼接
        if(remainSets.length==0)
            return globalMaxResult;
        Set<Integer> zeroRemains = remainSets[length-1]; //中间位置是整除为0的数字集合
        for(int i=0;i<=1;i++){
            int sub = i==0?-1:1;
            if(remainSets[(length-1)*(sub+1)] == null) continue;
            for(Integer item: remainSets[(length-1)*(sub+1)]){ //对于所有余数为length-1的集合里面的每一个整数值，比如，89，99，199，余数都是9，但是大小不同，分别是8，9，19
                if(zeroRemains!=null && zeroRemains.contains(item+sub)){ //length-1和0是连续的，举一个例子，假如length=10，则89，99，100是连续的
                    int concatedLength = segment.get(item)[2] + segment.get(item+sub)[2];//段拼接
                    globalMaxResult = globalMaxResult<concatedLength?concatedLength:globalMaxResult;
                }
            }
        }
        return globalMaxResult;
    }


    public int longestConsecutive3(int[] nums) {
        Map<Integer,Integer> lower = new HashMap<Integer,Integer>();
        Map<Integer,Integer> upper = new HashMap<Integer,Integer>();
        Set<Integer> existing = new HashSet();
        int longest = 0;
        for(int i = 0;i<nums.length;i++){
            int leftLength = 1; int rightLength = 1;
            int leftNum = 0; int rightNum = 0;
            if(existing.contains(nums[i])){
                continue;
            }
            existing.add(nums[i]);
            if(upper.containsKey(nums[i]-1)){
                leftLength = upper.get(nums[i]-1) + 1;
                leftNum = nums[i] - upper.get(nums[i]-1);
                upper.put(nums[i], leftLength);
                lower.put(leftNum, leftLength);
                upper.remove(nums[i]-1);
            }else{
                upper.put(nums[i], 1);
            }
            if(lower.containsKey(nums[i]+1)){
                rightLength = lower.get(nums[i]+1) + 1;
                rightNum = nums[i] + lower.get(nums[i]+1);
                lower.put(nums[i], rightLength);
                upper.put(rightNum, rightLength);
                lower.remove(nums[i]+1);
            }else{
                lower.put(nums[i],1);
            }
            if(leftLength != 1 && rightLength != 1){
                lower.remove(nums[i]);
                upper.remove(nums[i]);
                lower.put(leftNum, leftLength + rightLength - 1);
                upper.put(rightNum, leftLength + rightLength - 1);
            }
            longest = Math.max(longest, leftLength + rightLength - 1);
        }
        return longest;
    }


    public static void main(String[] args) {


        int[] input = {0,3,7,2,5,8,4};
        System.out.println(new LongestConsecutiveSequence().longestConsecutive3(input));

        System.out.println(-100 / 10);
        System.out.println(-20 % 10);
    }
}

