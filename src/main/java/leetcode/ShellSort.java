package leetcode;

/**
 * Created by wuchang at 1/1/18
 * 这是希尔排序算法的java实现
 */

public class ShellSort {

    public static void main(String [] args)
    {
        int[]a={49,38,65,97,76,13,27,49,78,34,12,64,1};
        System.out.println("排序之前：");
        for(int i=0;i<a.length;i++)
        {
            System.out.print(a[i]+" ");
        }
        //希尔排序
        shellSort(a);

        System.out.println();
        System.out.println("排序之后：");
        for(int i=0;i<a.length;i++)
        {
            System.out.print(a[i]+" ");
        }
    }


    private static void shellSort(int[] a){
        int d=a.length;
        while(true)
        {
            d=d/2; //d逐渐减半，最终减小到1
            for(int x=0;x<d;x++) //x从0到d，比如，当前a的长度是13，当前d=3, 因此，x从0到2
            {
                for(int i=x+d;i<a.length;i=i+d)
                {
                    int temp=a[i]; //按照正常的普通插入排序，将temp在前面已经排好序的数组上进行插入
                    int j;
                    for(j=i-d;j>=0&&a[j]>temp;j=j-d)
                    {
                        a[j+d]=a[j]; //只要元素大于temp，则元素按照增量长度进行后移
                    }
                    a[j+d]=temp; //a[i]=temp排序完成，爱是对a[i+d]进行排序
                }
            }
            if(d==1)
            {
                break;
            }
        }
    }


}
