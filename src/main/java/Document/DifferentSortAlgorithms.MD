[《这篇》](https://baike.baidu.com/item/%E6%8E%92%E5%BA%8F%E7%AE%97%E6%B3%95%E7%A8%B3%E5%AE%9A%E6%80%A7/9763250?fr=aladdin)帖子有讲到各种排序算法的稳定性。
1. 冒泡排序
冒泡排序是稳定的排序算法，因为如果遇到两个相邻元素相等，是跳过的，不会交换

```
public class BubbleSort
{
    public void sort(int[] a)
    {
        int temp = 0;
        for (int i = a.length - 1; i > 0; --i)
        {
            for (int j = 0; j < i; ++j)
            {
                if (a[j + 1] < a[j]) //如果前一个元素小于后一个元素，则交换。这里用的是小于，而不是小于等于，这决定了冒泡排序是稳定的排序算法
                {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }
}

```
2. 简单选择排序
设置一个最小元素的坐标，从无序数组中选择出最小的元素放到数组的开头。时间复杂度是O(n).简单选择排序存在元素交换，因此是不稳定的排序算法。
比如，5 8 5 2 9， 我们知道第一遍选择第1个元素5会和2交换，那么原序列中2个5的相对前后顺序就被破坏了，所以选择排序不是一个稳定的排序算法。
```
const int N=10;
int main()
{
    int a[N],i,j,temp,b;
    for(i=0;i<N-1;i++)
    {
        temp=i;
        for(j=i+1;j<N;j++)
        {
            if(a[temp]>a[j])
                temp=j;
        }
        if(i!=temp) //这一步的交换，导致简单选择排序是不稳定的排序算法
        {
            b=a[temp];
            a[temp]=a[i];
            a[i]=b;}
    }
    for(i=0;i<N;i++)
        cout<<setw(3)<<a[i];
    cout<<endl;
}
```


3. 插入排序
插入排序的基本思想是，每次从无序数组中取出一个元素，放到已经排序完成的有序树种。根据插入到有序数组的方法，分为直接插入排序和折半插入排序。插入排序当找到插入位置后，是把插入位置后面的元素顺序后移，因此是稳定的排序算法。
3.1  直接插入排序
直接插入排序是一种简单的插入排序法，其基本思想是：把待排序的记录按其关键码值的大小逐个插入到一个已经排好序的有序序列中，直到所有的记录插入完为止，得到一个新的有序序列

```
public void zjinsert(Redtype r[], int n) {
        int I, j;
        Redtype temp;
        for (i = 1; i < n; i++) {
            temp = r[i];
            j = i - 1;
            while (j > -1 && temp.key < r[j].key) {
                r[j + 1] = r[j];
                j--;
            }
            r[j + 1] = temp;
        }
    }
```

3.2  折半插入排序
```
void InsertSort(Elemtype A[],int n){
    int i,j,low,high,mid;
    for(i=2;i<=n;i++){
        A[0]=A[i];
        low=1;
        high=i-1;//设置折半查找的范围，从1到i-1,A[0]用来暂存元素
        while(low<=high){
            mid=(low+high)/2;
            if(A[mid].key>A[0].key) high=mid-1;//查找左半子表
            else low=mid+1;//查找右半子表
        }

        for(j=i-1;j>=high+1;--j)
                A[j+1]=A[j];//统一向后移动元素，空出插入位置
        A[high+1]=A[0];//插入操作
    }
}
```


4. 归并排序(一般使用的是二路归并排序)
归并排序是稳定的排序算法
```
void Merge(int sourceArr[],int tempArr[], int startIndex, int midIndex, int endIndex)
{
    int i = startIndex, j=midIndex+1, k = startIndex;
    while(i!=midIndex+1 && j!=endIndex+1)
    {
        if(sourceArr[i] > sourceArr[j])
            tempArr[k++] = sourceArr[j++];
        else
            tempArr[k++] = sourceArr[i++];
    }
    while(i != midIndex+1)
        tempArr[k++] = sourceArr[i++];
    while(j != endIndex+1)
        tempArr[k++] = sourceArr[j++];
    for(i=startIndex; i<=endIndex; i++)
        sourceArr[i] = tempArr[i];
}
 
//内部使用递归
void MergeSort(int sourceArr[], int tempArr[], int startIndex, int endIndex)
{
    int midIndex;
    if(startIndex < endIndex)
    {
        midIndex = (startIndex + endIndex) / 2;
        MergeSort(sourceArr, tempArr, startIndex, midIndex);
        MergeSort(sourceArr, tempArr, midIndex+1, endIndex);
        Merge(sourceArr, tempArr, startIndex, midIndex, endIndex);
    }
}
```

5. 堆排序

```
//对一个大顶堆进行调整
void HeapAdjust(int array[],int i,int nLength)
{
    int nChild;
    int nTemp;
    for(;2*i+1<nLength;i=nChild)
    {
        //子结点的位置=2*（父结点位置）+1
        nChild=2*i+1;
        //得到子结点中较大的结点
        if(nChild<nLength-1&&array[nChild+1]>array[nChild])++nChild;
        //如果较大的子结点大于父结点那么把较大的子结点往上移动，替换它的父结点
        if(array[i]<array[nChild])
        {
            nTemp=array[i];
            array[i]=array[nChild];
            array[nChild]=nTemp; 
        }
        else break; //否则退出循环
    }
}
//堆排序算法
void HeapSort(int array[],int length)
{
    int i;
    //调整序列的前半部分元素，调整完之后第一个元素是序列的最大的元素
    //length为元素个树，具有n个元素的完全二叉树的非叶子节点个数肯定是n/2，因此，这个length多个元素的树种，length/2-1是最后一个非叶节点，此处"/"为整除
    for(i=length/2-1;i>=0;--i)
       HeapAdjust(array,i,length);
    //从最后一个元素开始对序列进行调整，不断的缩小调整的范围直到第一个元素
    //经过了堆的构建，A[0]存放了堆顶元素，对于大顶堆，就是最大的元素，因此，我们将最大的元素放到最后，然后对前面的堆进行重新调整
    for(i=length-1;i>0;--i)
    {
        //把第一个元素和当前的最后一个元素交换
        //保证当前的最后一个位置的元素都是在现在的这个序列之中最大的
        array[i]=array[0]^array[i];
        array[0]=array[0]^array[i];
        array[i]=array[0]^array[i];
        //不断缩小调整heap的范围，每一次调整完毕保证第一个元素是当前序列的最大值
        HeapAdjust(array,0,i); //对[0,i]之间的元素进行调整
    }
}
```


6. 希尔排序(缩小增量排序)
查看ShellSort.java
先取一个小于n的整数d1作为第一个增量，把文件的全部记录分组。所有距离为d1的倍数的记录放在同一个组中。先在各组内进行直接插入排序；然后，取第二个增量d2<d1重复上述的分组和排序，直至所取的增量  dt=dt<dt-1<..<d2<d1)，即所有记录放在同一组中进行直接插入排序为止。

希尔排序是基于插入排序的以下两点性质而提出改进方法的：
插入排序在对几乎已经排好序的数据操作时，效率高，即可以达到线性排序的效率。
但插入排序一般来说是低效的，因为插入排序每次只能将数据移动一位。
由于多次插入排序，我们知道一次插入排序是稳定的，不会改变相同元素的相对顺序，但在不同的插入排序过程中，相同的元素可能在各自的插入排序中移动，最后其稳定性就会被打乱，所以shell排序是不稳定的。
```

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

```

7. 快速排序

通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
由于快速排序存在元素交换，因此是不稳定的排序算法

```
void sort(int *a, int left, int right)
{
    if(left >= right)/*如果左边索引大于或者等于右边的索引就代表已经整理完成一个组了*/
    {
        return ;
    }
    int i = left;
    int j = right;
    int key = a[left];
     
    while(i < j)                               /*控制在当组内寻找一遍*/
    {
        while(i < j && key <= a[j])
        /*而寻找结束的条件就是，1，找到一个小于或者大于key的数（大于或小于取决于你想升
        序还是降序）2，没有符合条件1的，并且i与j的大小没有反转*/ 
        {
            j--;/*向前寻找*/
        }
         
        a[i] = a[j];
        /*找到一个这样的数后就把它赋给前面的被拿走的i的值（如果第一次循环且key是
        a[left]，那么就是给key）*/
         
        while(i < j && key >= a[i])
        /*这是i在当组内向前寻找，同上，不过注意与key的大小关系停止循环和上面相反，
        因为排序思想是把数往两边扔，所以左右两边的数大小与key的关系相反*/
        {
            i++;
        }
         
        a[j] = a[i];
    }
     
    a[i] = key;/*当在当组内找完一遍以后就把中间数key回归*/
    sort(a, left, i - 1);/*最后用同样的方式对分出来的左边的小组进行同上的做法*/
    sort(a, i + 1, right);/*用同样的方式对分出来的右边的小组进行同上的做法*/
                       /*当然最后可能会出现很多分左右，直到每一组的i = j 为止*/
```