/**
 * Created by wuchang at 1/8/18
 * Question 80
由于需要在nums的基础上修改，因此我们使用i记录当前遍历的值，而cursor为当前需要填写数字的位置。
 遍历数组，如果发现重复，则记录次数，当重复次数多于2次，那么cursor不动，i后移，直到发现一个新的数字，将这个数字填入到cursor的位置，然后cursor和i都往后移动。
 ```
 [1,1,1,2,2,3]
 cursor=0,i = 0  [1,1,1,2,2,3]
 cursor =1 i =1数字1重复了2次， [1,1,1,2,2,3]
 cursor=2 i =2 ，发现数字1重复了3次 [1,1,1,2,2,3]
 cursor=2,i= 3 , 没有重复，数组变成 [1,1,2,2,2,3]
 cursor=3 i =4，数字2重复了2次 [1,1,2,2,2,3]
 cursor=4 i=5，数字2重复了3次，cursor保持不变 [1,1,2,2,2,3]
 cursor=4 i = 6 , 数字3是新的，数组变成 [1,1,2,2,3,3]

 ```
 */

public class RemoveDuplicatesFromSortedArrayII {
    public int removeDuplicates(int[] nums) {

        if(nums.length == 0)
            return 0;
        int res = 0;//返回值
        int curDulp = 0;//当前值已经重复的次数
        int preVal = nums[0]-1;//上一个位置的值，为了让 nums[0]判断为不重复，设置为一个与nums[0]不同的值
        for(int i=0,cursor=0;i<nums.length;i++) {

            if (nums[i] != preVal) { //不等于前一个值
                preVal = nums[i];//preVal复制为当前值
                nums[cursor++] = nums[i]; //没有发生重复，i和cursor都需要往后移动
                curDulp = 1; //由于这是一个新的值，因此当前重复值设置为1
                res++;
            } else { //如果有重复，判断重复超过是否2次
                curDulp++;//重复次数+1
                if (curDulp <= 2) {//重复没有超过两次
                    res++;
                    nums[cursor++] = nums[i];
                }
                //如果重复次数超过2次，则cursor不移动
            }
        }
        return res;
    }


    /**
     * 别人的代码，由于数字允许重复2次，因此只要当前数字不等于nums[i-2]，那么就可以判断它的重复次数不超过2次
     * @param nums
     * @return
     */
    public int removeDuplicates1(int[] nums) {
        int i = 0;
        for (int n : nums)
            if (i < 2 || n > nums[i-2])//i始终代表当前需要进行填写的位置，如果n不等于nums[i-2]，说明没有重复超过2次
                nums[i++] = n;//将数字n放入到位置i
        return i;
    }


    public static void main(String[] args) {

        int[] nums = {1,1,1,1};
        new RemoveDuplicatesFromSortedArrayII().removeDuplicates(nums);
    }
}
