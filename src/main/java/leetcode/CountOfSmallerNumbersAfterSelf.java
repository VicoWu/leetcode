package leetcode;

import java.util.*;


/**
 * Question 315
 * # 归并分治
 这一题如果是求真个数组里面的逆序数量，则与[Reverse Pairs](https://leetcode.com/problems/reverse-pairs/discuss/)是几乎相同的问题，只是比较的标准不一样。

 但是这一题需要得到每一个数字的右侧逆序数。为了避免在进行merge的过程中我们忘记了元素的位置，我们对元素进行了封装：
 ```
 class NumberIndex {
 int number;
 int index;

 NumberIndex(int number, int index) {
 this.number = number;
 this.index = index;
 }

 NumberIndex(NumberIndex another) {
 this.number = another.number;
 this.index = another.index;
 }
 }
 ```

 我们在进行merge 的过程中，全局使用一个结果集`int[] smaller`记录中间结果，当merge完全退出，smaller中就保存了每一个元素的右侧小于自己的值。
 递归逻辑是：
 对于两个子问题A和B，先对A和B进行求解，然后，再对A和B进行merge：由于A和B都是有序的，因此对于A中的每一个元素a，我们很容易就知道B中有多少个元素小于a：
 ```
 int m = left.length, n = right.length, i = 0, j = 0;
 while (i < m || j < n) {
 if (j == n || i < m && left[i].number <= right[j].number) {
 nums[i + j] = left[i];
 smaller[left[i].index] += j;
 i++;
 } else {
 nums[i + j] = right[j];
 j++;
 }
 }
 ```

 这样，当不断归并，最终运算结束，我们的smaller中就存放了全局的结果。


 # BIT树
 我自己根据BIT算法的实现`countSmallerByBIT()`：
 先复制一份nums并且进行排序，放到sortedCopy，由于sortedCopy是经过排序的，所以，对于nums中的任何一个数字，我们可以在logn的时间内知道这个数字是排第几的。然后，我们从后往前遍历nums:
 对于nums中的每一个数字a，通过在sortedCopy中二分查找得到a在sortedCopy中的位置，通过当前BIT树查找树中小于等于a的元素有几个（由于我们往BIT中插入数据的时候数值是1，因此，元素的值的和刚好等于元素的数量），
 就得到小于等于a且在nums中位于a右侧的元素个数，然后，我们把a所在的位置插入到BIT中。
 这样，我们在处理nums中的某个元素nums[i]的时候，BIT树中已经存放了nums中在i右侧的每一个元素在排序数组中的位置。
 因此，对于nums[i]这个元素，如果它在sortedArray中排序位置是k，那么，我们只需要查找BIT树里面在k左侧有多少个元素就行了。




 */
public class CountOfSmallerNumbersAfterSelf {

    class NumberIndex { //对元素进行封装
        int number;
        int index;

        NumberIndex(int number, int index) {
            this.number = number;
            this.index = index;
        }

        NumberIndex(NumberIndex another) {
            this.number = another.number;
            this.index = another.index;
        }
    }

    /**
     * 通过MergeSort的方式计算
     * @param nums
     * @return
     */
    public List<Integer> countSmaller(int[] nums) {
        NumberIndex[] cnums = new NumberIndex[nums.length];//将nums中每个元素和它的位置封装为NumberIndex对象
        for (int i = 0; i < nums.length; i++) {
            cnums[i] = new NumberIndex(nums[i], i);
        }
        int[] smaller = new int[nums.length];//smaller中存放最终结果，在递归进行过程中，smaller存放中间结果
        cnums = sort(cnums, smaller);
        List<Integer> res = new ArrayList<>();
        for (int i : smaller) {
            res.add(i);
        }
        return res;
    }

    private NumberIndex[] sort(NumberIndex[] nums, int[] smaller) {
        int half = nums.length / 2;
        if (half > 0) {
            NumberIndex[] rightPart = new NumberIndex[nums.length - half];
            NumberIndex[] leftPart = new NumberIndex[half];
            for (int i = 0; i < leftPart.length; i++) {
                leftPart[i] = new NumberIndex(nums[i]);
            }
            for (int i = 0; i < rightPart.length; i++) {
                rightPart[i] = new NumberIndex(nums[half + i]);
            }
            NumberIndex[] left = sort(leftPart, smaller); //递归计算左侧结果
            NumberIndex[] right= sort( rightPart, smaller); //递归计算右侧结果
            int m = left.length, n = right.length, i = 0, j = 0;
            while (i < m || j < n) {
                if (j == n || i < m && left[i].number <= right[j].number) {
                    nums[i + j] = left[i]; //不断进行排序，保证归并以后nums是有序的，因为每一层归并，都依赖元素有序。这样，我们避免归并以后还要单独再排序才返回
                    smaller[left[i].index] += j; //更新对应元素的右侧逆序值
                    i++;
                } else {
                    nums[i + j] = right[j];////不断进行排序，保证归并以后nums是有序的，因为每一层归并，都依赖元素有序。这样，我们避免归并以后还要单独再排序才返回
                    j++;
                }
            }
        }
        return nums;
    }



    int sum(int[] c,int x){ //求x前缀和
        int ans = 0;
        while(x>0){ //迭代到虚拟结点
            ans+=c[x];
            x -= lowbit(x); //向左上方移动，一直移动到虚拟节点
        }
        return ans;
    }


    void add(int[] c,int x, int d){ //修改操作
        while(x<c.length){ //判断是否超出边界
            c[x]+=d;
            x+=lowbit(x); //向右下方走，一直移动到范围外
        }
        return ;
    }

    int lowbit ( int x){
        return x&-x;
    }


    /*
    对于已经从小到达排序的数组arr，返回val前面的元素的个数，即小于等于val的元素个数
     */
    private int index(int[] arr, long val) {
        int l = 0, r = arr.length - 1, m = 0;

        while (l <= r) {
            m = l + ((r - l) >> 1);

            if (arr[m] >= val) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return l + 1;
    }

    public List<Integer> countSmallerByBIT(int[] nums){
        LinkedList<Integer> result = new LinkedList<Integer>();
        int[] sortedCopy = Arrays.copyOf(nums,nums.length);
        Arrays.sort(sortedCopy);
        int[] bit = new int[nums.length+1];
        for(int i=nums.length-1;i>=0;i--){
            int index = index(sortedCopy,nums[i]);
            int lessThan = sum(bit,index);
            result.addFirst(lessThan);
            add(bit,index,1);
        }
        return result;
    }



    public static void main(String[] args) {

        Properties properties = new Properties();
//        try {
//            InputStream inputStream = new FileInputStream(new File("/Users/wuchang/leetcodedata"));
//            properties.load(inputStream);
//            String a = properties.getProperty("key");
//            String output = properties.getProperty("output");
//            String answer = properties.getProperty("answer");
//
//            String[] arrayStr = a.split(",");
//            String[] arrayOutput = output.split(",");
//            String[] arrayAnswer = answer.split(",");
//            for(int i=0;i<arrayAnswer.length;i++){
//                if(!arrayAnswer[i].equals(arrayOutput[i]))
//                    System.out.println("index "+i+" not equal.output is "+arrayOutput[i]+", answer is "+arrayAnswer[i]);
//            }
//            int[] input = new int[arrayStr.length];
//            for(int i=0;i<arrayStr.length;i++)
//                input[i] = Integer.valueOf(arrayStr[i]);
//
//            for(int i=0;i<input.length;i++)
//                if(input[i] == 737)
//                    System.out.println("found 737");
            int[] input = {5,2,6,1};

           List result =  new CountOfSmallerNumbersAfterSelf().countSmallerByBIT(input);

           System.out.println("");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



    }


}
