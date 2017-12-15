import java.util.*;

/**
 * Question 381
 * 源代码中是我仿照[这个](https://discuss.leetcode.com/topic/53216/java-solution-using-a-hashmap-and-an-arraylist-along-with-a-follow-up-131-ms/5)进行的实现；

 ## 分析
 1. 由于存在重复元素，因此简单的hashmap不能满足要求，至少是`HashMap<Integer,List<Integer>>` ，至于这个List的具体实现是什么，稍后讨论;
 2. 在这里，我们基本上假设HashMap的查找、删除一个元素的 时间复杂度是常量O(1);
 3. 由于需要在O(1)的时间内随机、等概率获取某个值，因此设想用一个array保存元素,在元素位置上进行随机就行。并且，添加元素也得是O(1)，因此可以使用ArrayList实现。
 4. 我们在删除一个元素的时候，可以轻松把这个元素从map中删除，但是如何从array中在O(1)时间定位到元素并删除？方法是我们的map的key是元素本身，value是元素在array中的位置
 5. 如果我们删除的是array的最后一个元素还好，可是如果删除的是中间某个元素b，array中在它后面的元素的序号全部减1，整个map都需要修改，显然没必要。我们的方式是，如果删除的不
 是最后一个元素，就把array的最后一个元素c放到这个被删除元素a的位置即替换a，从array中删掉c，然后，修改map中这个最后一个元素c的位置信息就行。这样，hashmap中所有元素的位置依然是[0,array.size-1]的连续值。

 为什么使用`HashMap<Integer,LinkedHashSet<Integer>>()`，看我在方法`testHashSetInterator`和`testLinkedHashSetInterator`中测试了HashSet和LinkedHashSetI的iterator()
 方法的性能，发现只有LinkedHashSet的iterator是线性的，而HashSet跟元素的个数和bucket的个数线性相关。
 这是由于LinkedHashSet是在Set的基础上进行了有序化[实现](https://docs.oracle.com/javase/7/docs/api/java/util/LinkedHashSet.html)，即，它使用了链表来连接已有的元素，因此，
 如果整个set的capacity很大，但是元素数量少，它的iterator()查找第一个元素只需要一步，而不需要像HashSet一样步步遍历；

 ```
 I didn't read the implementation, so I'm not entirely certain, but I think it's like this: In my test I added many elements, so the hash table grew large.
 Then I deleted all but one, and I guess the hash table didn't reduce its capacity. So it ended up with a large table, but only one element somewhere in it.
 And then finding that one element in the large table simply takes long. The LinkedHashSet on the other hand additionally has a list of its elements,
 so it simply iterates that list instead of going searching in the large table.
 ```












 */
public class InsertDeleteGetRandomDuplicatesAllowed {
    ArrayList<Integer> nums;
    HashMap<Integer,LinkedHashSet<Integer>> locs;
    java.util.Random rand = new java.util.Random();

    public InsertDeleteGetRandomDuplicatesAllowed(){
        nums = new ArrayList<>();
        locs  =new HashMap<Integer,LinkedHashSet<Integer>>();

    }

    public boolean insert(int val) {

        nums.add(val);
        boolean contains = true;
        LinkedHashSet<Integer> set = locs.get(val);
        if(set == null) {
            set = new LinkedHashSet<Integer>();
            contains = false;
        }
        set.add(nums.size()-1);//将其在数组中的坐标保存下来
        locs.put(val,set);
        return !contains;
    }


    public boolean remove(int val){
        boolean contains = locs.containsKey(val);
        if(!contains) return false;
        int loc = locs.get(val).iterator().next(); //获取它在数组中的位置

        locs.get(val).remove(loc); //先把这个位置记录从map中删除
        if(locs.get(val).isEmpty())
            locs.remove(val);
        int last = nums.get(nums.size()-1);
        if(loc != nums.size()-1){
            nums.set(loc,last);
            locs.get(last).remove(nums.size()-1); //之前n个元素，现在n-1个，因此，之前最大序号n-1没有了，需要把这个最大序号n-1删除
            locs.get(last).add(loc); //把val所在的序号填补到最大元素的序号列中去
        }
        nums.remove(nums.size()-1);//从nums中把最大元素删除(上面已经把最大元素放到被删除位置去了，因此最大元素并没有丢失)
        return true;
    }


    public int getRandom(){
        return nums.get(rand.nextInt(nums.size()));
    }


    public static void testHashSetInterator() {
        Set<Integer> set = new HashSet<>();
        for (int i=0; i<=100000; i++)//在这里，添加100001个元素
            set.add(i);
        for (int i=0; i<100000; i++)
            set.remove(i);

        long t0 = System.currentTimeMillis();
        for (int i=0; i<10000; i++)
            set.iterator().next();
        System.out.println("HashSet:"+(System.currentTimeMillis() - t0));
    }


    public static void testLinkedHashSetInterator() {
        Set<Integer> set = new LinkedHashSet<>();
        for (int i=0; i<=100000; i++)//在这里，添加100001个元素
            set.add(i);
        for (int i=0; i<100000; i++)//删除100000个元素，这样，只有一个元素，但是由于之前添加过100001个元素，因此set的capacity非常大，尽管只有一个元素
            set.remove(i);

        long t0 = System.currentTimeMillis();
        for (int i=0; i<10000; i++)
            set.iterator().next();
        System.out.println("LinkedHashSet:"+(System.currentTimeMillis() - t0));
    }


    public static void main(String[] args) {
        InsertDeleteGetRandomDuplicatesAllowed randomInstance = new InsertDeleteGetRandomDuplicatesAllowed();
        randomInstance.insert(97);
        randomInstance.insert(98);
        randomInstance.insert(97);
        randomInstance.remove(97);
        randomInstance.remove(98);

//        new InsertDeleteGetRandomDuplicatesAllowed().testHashSetInterator();
//        new InsertDeleteGetRandomDuplicatesAllowed().testLinkedHashSetInterator();
//        new InsertDeleteGetRandomDuplicatesAllowed().testHashSetInterator();
//        new InsertDeleteGetRandomDuplicatesAllowed().testLinkedHashSetInterator();
//
//        new InsertDeleteGetRandomDuplicatesAllowed().testHashSetInterator();
//        new InsertDeleteGetRandomDuplicatesAllowed().testLinkedHashSetInterator();
//
//        new InsertDeleteGetRandomDuplicatesAllowed().testHashSetInterator();
//        new InsertDeleteGetRandomDuplicatesAllowed().testLinkedHashSetInterator();

    }
}
