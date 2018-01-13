import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/10/18
 * Question 229
 *
[这里](https://gregable.com/2013/10/majority-vote-algorithm-find-majority.html)有Boyer-Moore Algorithm算法的详细解释。
 注意，Moore Algorithm算法需要两次遍历输入，第一次是选出候选，第二次是确认候选的确满足出现次数的要求，即Moore Algorithm算法保证如果输入中有满足要求的数据的时候肯定输出这个满足要求的数据，但是，如果不确定输入中是否真的存在这样的数据，那么Moore Algorithm算法也会有输出，是错误的输出，因此需要在找到candidate以后第二次遍历input来进行确认。
 在[Majority Element](https://leetcode.com/problems/majority-element/description/)中由于已经确认了候选肯定存在，因此我们不需要第二轮遍历，否则仍然需要第二轮遍历确认我们找到的元素是否的确满足要求。

 # 本题目的具体解法

 我们使用两个候选变量candidates1和candidates2来记录当前的候选元素，使用c1和c2来分别代表他们出现的次数。
 他们初始化的时候
 ```
 int candidate1 = nums[0];int c1 = 1;
 int candidate2 = nums[0];int c2 = 0;//初始化candidate2为nums[0]并且c2=0,这是没关系的，因为我们在遍历过程中只要遇到c2=0的，就会用当前的数字替换掉candidate2的
 ```
 遍历过程中，如果发现当前的nums[i]和candidate1或者candidate2中任何一个相同，则将对应的candidate的c值加1，而如果与他们都不同，则会发生以下情况：
 如果candidate1或者candidate2中有一个的c值是0，即被完全抵消为0，则用当前元素替换掉这个已经被完全抵消的元素。而如果两个candidate元素都还没有被完全抵消为0，则将两个元素的c值全部抵消1个，即都被抵消1次。

 注意，如果我们发现candiates1或者candidate2中有任何一个元素出现的次数被抵消为0，就用当前元素替换掉当前的元素，此时，另外一个元素的c值不应该被抵消，比如，输入1,1,1,3,3,2,2,2，当处理完[1,1,1]的时候 ，candidates1=1,c1=3,candidates2=1,c1=0,此时来了一个2，我们除了将candidate2替换为2，是否还应该将c1--呢？不行，因为由于c1=0，我们将candidates2设置为2，此时不是一种替换，而相当于一种新增，即相当于3到来以前只有一个candidates，现在增加了一个candidates而已，已经存在的candidates不受影响。




 ```
 candidate1(c1)      candidate2(c2)

 3               3(1)                   3(0)       初始化状态

 3               3(2)                   3(0)      出现3，并且candidate1==3，因此c1++

 3               3(3)                   3(0)       出现3，并且candidate1==3，因此c1++

 2               3(3)                   2(1)      出现2，并且c2=0，因此candidate2=2,c2=1，candidate1和c1不变

 2               3(3)                   2(2)     出现2，并且candidate2==2，因此c2++，candidate1和c1不变

 1               3(2)                   2(1)    出现1，candidate2和candidate1都不等于1，并且c1>0,c2>0,因此c2和c1都自减

 1               3(1)                   2(0)    出现1，candidate2和candidate1都不等于1，并且c1>0,c2>0,因此c2和c1都自减

 1               3(0)                   1(0)    出现1，candidate2和candidate1都不等于1，但是c2==0,因此将candidate2替换为1，c2=1，结束

 ```



 */

public class MajorityElementII {
    public List<Integer> majorityElement(int[] nums) {

        List<Integer> res = new LinkedList<>();
        if(nums==null || nums.length == 0)
            return res;
        int candidate1 = nums[0];int c1 = 1;
        int candidate2 = nums[0];int c2 = 0;//初始化candidate2为nums[0]并且c2=0,这是没关系的，因为我们在遍历过程中只要遇到c2=0的，就会用当前的数字替换掉candidate2的



        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == candidate1) {
                c1++;
            } else if (nums[i] == candidate2) {
                c2++;
            } else {
                //如果我们发现candiates1或者candidate2中有任何一个元素出现的次数被抵消为0，就用当前元素替换掉当前的元素，注意，另外一个元素的c值此时不应该被抵消
                //比如，输入1,1,1,3,3,2,2,2，当处理完[1,1,1]的时候 ，candidates1=1,c1=3,candidates2=1,c1=0,此时来了一个2，我们除了将candidate2替换为2，是否还应该将c1--呢？不行，因为由于c1=0，我们将candidates2设置为2，
                //此时不是一种替换，而相当于一种新增，即相当于3到来以前只有一个candidates，现在增加了一个candidates而已，已经存在的candidates不受影响。
                if (c1 == 0) {
                    candidate1 = nums[i];
                    c1++;
                }
                else if (c2 == 0) { //发现c2=0，将candidate2设置为nums[i]。同样，此时c1不应该进行减一操作
                    candidate2 = nums[i];
                    c2++;
                } else{ //如果我们没有发现任何一个candidates抵消为0，那么所有的candidate的count值减1，即都被抵消一次
                    c1--;
                    c2--;
                }
            }
        }
        //选出了两个candidates，但是还需要验证这两个候选出现的次数是不是真正的满足要求
        c1 = 0; c2 = 0;
        for (Integer num : nums) {
            if (num == candidate1)
                c1++;
            if (num == candidate2)
                c2++;
        }
        if (c1 > nums.length / 3) res.add(candidate1);
        if (candidate2!=candidate1&& c2 > nums.length / 3) res.add(candidate2);
        return res;
    }


    public static void main(String[] args) {

        int[] nums = {1,1,1,3,3,2,2,2};
        new MajorityElementII().majorityElement(nums);
    }
}
