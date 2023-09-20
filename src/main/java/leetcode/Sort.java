package leetcode;

/**
 * Question 912
 */
public class Sort {

    public int[] sortArray(int[] nums) {
        shellSort(nums);
        return nums;
    }

    public void shellSort(int[] nums) {
        int gap = 1;
        while ((gap << 1) <= nums.length / 2) {
            gap = gap << 1;
        }

        while (gap >= 1) {
            for (int i = 0; i < gap; i++) { //使用插入排序
                int j = i + gap;// 由于gap * 2 < nums.length, 因此 i + gap 一定不会越界

                for (; j < nums.length; j += gap) { // 对于当前的元素nums[j] , 执行插入操作
                    int k = j;
                    int tmp = nums[j];
                    while (k - gap >= 0 && nums[k - gap] > tmp) {
                        nums[k] = nums[k - gap];// 大数后移
                        k -= gap;
                    }
                    nums[k] = tmp;
                }
            }
            gap = gap >> 1;
        }
    }

    public static void main(String[] args) {
        int[] input = {5,2,1};
        new Sort().shellSort(input);
    }
}
