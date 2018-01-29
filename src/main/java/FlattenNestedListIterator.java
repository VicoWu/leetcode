import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/19/18
 * Question 341
 */

public class FlattenNestedListIterator {

    public interface NestedInteger {

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }


    public static class NestedIntegerImpl implements NestedInteger{

        private boolean isInteger;
        private List<NestedInteger> list;
        private Integer intR;

        public NestedIntegerImpl(boolean isInteger,List<NestedInteger> list,Integer intR){
            this.isInteger = isInteger;
            this.list = list;
            this.intR = intR;
        }

        @Override
        public boolean isInteger() {
            return isInteger;
        }

        @Override
        public Integer getInteger() {
            return intR;
        }

        @Override
        public List<NestedInteger> getList() {
            return list;
        }
    }

    public static class NestedIterator implements Iterator<Integer> {

        int index = 0;
        List<Integer> res ;

        public NestedIterator(List<NestedInteger> nestedList) {
            res = new LinkedList<>();
            traverse(nestedList,res);
        }

        @Override
        public Integer next() {
            return res.get(index++);
        }

        @Override
        public boolean hasNext() {
            return index < res.size();
        }

        public void traverse(List<NestedInteger> nestedList,List<Integer> res){
            for(int i=0;i<nestedList.size();i++){
                NestedInteger ni = nestedList.get(i);
                if(ni.isInteger())
                    res.add(ni.getInteger());
                else
                    traverse(ni.getList(),res);
            }
        }
    }

    public static void main(String[] args) {
        NestedInteger ni1 = new NestedIntegerImpl(true,null,1);

        List<NestedInteger> list = new ArrayList<>();
        list.add( new NestedIntegerImpl(true,null,2));
        list.add(new NestedIntegerImpl(true,null,3));

        NestedInteger ni2 = new NestedIntegerImpl(false,list,null);


        List<NestedInteger> list1 = new ArrayList<>();
        list1.add( new NestedIntegerImpl(true,null,4));
        list1.add(new NestedIntegerImpl(true,null,5));

        List<NestedInteger> list2 = new ArrayList<>();

        list2.add(new NestedIntegerImpl(false,list1,null));
        list2.add(new NestedIntegerImpl(true,null,6));

        NestedInteger ni3 = new NestedIntegerImpl(false,list2,null);

        List<NestedInteger> list3 = new ArrayList<>();
        list3.add(ni1);list3.add(ni2);list3.add(ni3);


        NestedIterator ite = new NestedIterator(list3);
        while(ite.hasNext()){
            System.out.println(ite.next());
        }

    }

}
