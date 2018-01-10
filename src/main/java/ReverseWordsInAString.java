/**
 * Created by wuchang at 1/9/18
 * Question 151
 # 基本思路
 对于`this is a boy`，我们想要的结果是`boy a is this`,如果我们直接一个单词一个单词的进行调换，由于单词长度各不相同，因此比较困难，但是我们想到，对一个单词进行翻转比较简单，因此，分两步 :
 1. 先把`this is a boy` 整个字符串进行翻转，`yob a si siht`，
 2. 然后，对这个翻转以后的串内部的单词进行翻转，就变成了`boy a is this`。

 但是这里需要注意的是：
 1. 如果单词前后有空格，需要全部去掉空格
 2. 如果单词中间有多个空格，这多个空格只能有一个

 总之，经过转换以后的结果必须是标准化的结果，前后没有空格，单词之间被一个空格分隔。
 因此，我们在完成了第一步以后，这样操作：
 因此，我们使用一个变量insL标记当前插入的位置，使用i和j来代表当前正在处理的单词的位置，显然,` i>=insL`。
 比如
 `_this is a boy_`
 经过第一步：
 `_yob a is siht_`

 然后，我们准备对yob进行翻转，此时,insL=0,i=1,j=3
 我们首先将yob`平移`到insL指向的位置以覆盖多于的空格：
 `yob a is siht_`
 我们使用start 和 end记录平移后的位置，显然，此时,start=0,end=2
 然后，对[start,end]之间的这个 yob进行翻转，就变成了boy：
 `boy a is siht_`
 这样，我们完成了boy的翻转，并且前面的空格被处理掉了。
 然后，我们需要让insL指向下一个单词的起始位置，让i和j再包含住下一个单词，对下一个单词进行翻转
 当全部单词翻转完毕， `new String(arr,0,end+1);`就是最后的结果，因此end一直指向的是最后一个单词的最后位置，因此，`[0,end+1]`（左闭右开）就是最终的结果。

 */

public class ReverseWordsInAString {

    public String reverseWords(String s) {


        if(s==null || "".equals(s))
            return s;
        char[] arr = s.toCharArray();
        //两步操作之第一步，将整个字符串翻转
        for(int i=0;i<=(s.length()-1)/2;i++){
            swap(arr,i,s.length()-1-i);
        }

        //两步操作之第二步，对每个单词进行翻转
        int end=-1;
        for(int i=0,j=0,insL =0;i<arr.length&&j<arr.length;){
            while(i<arr.length && arr[i]==' '){ //不断循环，让i和j指向单词的第一个字符
                i++;j=i;
            }
            if(i>=arr.length)
                break;
            while(j+1<arr.length && arr[j+1]!=' ') //j不断循环，指向单词的最后一个字符，这样[i,j]就是当前单词的范围
                j++;
            int start = insL;//当前插入的位置
            for(int k=i;k<=j;k++) //将单词平移，使得单词对齐当前插入的位置
                arr[insL++] = arr[k];
            end = start  + (j-i);//[start,end]是单词平移以后的位置范围
            for(int k=start;k<=(start+end)/2;k++) {//对这个单词进行翻转
                swap(arr,k,start+end - k);
            }
            if(insL>=arr.length)
                break;
            arr[insL++]=' '; //insL自增以后，经过了一个空格，指向了下一个单词应该插入的位置
            i=++j;//i或者j指向了下一个单词，或者空格

        }
        return new String(arr,0,end+1);
    }

    private void swap(char[] arr,int i,int j){
        char t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
    public static void main(String[] args) {

       System.out.println(new ReverseWordsInAString().reverseWords("  d dd   ddd  ddd    dd  d  ")) ;
        System.out.println(new ReverseWordsInAString().reverseWords("d dd   ddd  ddd    dd  d  ")) ;
        System.out.println(new ReverseWordsInAString().reverseWords("     d dd ddd  ddd    dd  d")) ;
        System.out.println(new ReverseWordsInAString().reverseWords("   d dd   ddd  ddd    dd  d")) ;
    }
}
