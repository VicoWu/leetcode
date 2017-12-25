import java.util.Arrays;

/**
 * Created by wuchang at 12/20/17
 * Question 564
 *
 * 这一题要求找到与某个数字相隔最近的回文字符串，并且，题目要求，如果输入串本身就是回文串，则必须跳过本身去寻找，即不可以返回自己。

 基本思路：我们必须找到回文串，一个是大于输入数字的最小的回文串，一个是小于输入数字的最大的回文串，然后在二者中返回一个离输入数字最近的回文串。

 - 求大于输入串的最小回文
 输入串为n=1201，要找到一个大于输入数字的最小的回文串，我们从Long.valueOf(1201)+1 = 1202开始找。显然，我们找到的是1221，即，把前半部分保留
 ，后半部分写成前半部分的回文串。这个1221必定是最小的，不会有大于等于1202并且比1221更小的了。但是，是不是按照把前半部分保留，后半部分写成前半部分的
 回文串得到的回文串一定是我们要求的大于输入串的最小回文串呢？不一定。比如，1278，我们从Long.valueOf(1278)+1 = 1279开始找，我们得到的回文串是1221，
 但是1221小于1278，因此不合法。这时候，我们需要从中间元素（位置是length/2 -1 ，比如，这里输入串长度4，中间元素是位置1），加1，然后求得回文串，即1331，由于我们只是加1，因此我们知道没有更小的符合要求的了，答案就是1331.

 - 求小于输入串的最大回文
 输入串为n=1201，要找到一个小于输入数字的最大的回文串，我们从Long.valueOf(1201)-1 = 1200开始找。第一步与上文相同，即，把前半部分保留，
 后半部分写成后半部分的回文串。这个1221明显不符合要求，因为它大于1200，于是我们只好把中间位置的元素减去1，然后同样按照把前半部分保留，后半部分写成前
 半部分的回文串的方式，得到1111，很显然，这个1111就是我们要的。可以想见，没有比1111更大并且小于1200的回文串了。

 这样，对于1201这个输入串，我们求得离他最近的小于和大于它的回文串分别是1111,1221,然后122比1111离1201更近，因此我们返回1111.



 进位和借位问题：

 求大于输入串的最小回文的时候，我们可能需要从中间位置开始+1。如果数字为9 ，则还需要向前进位，求小于输入串的最大回文的时候，我们可能需要从中间位置开始-1，
 如果数字为0，则需要借位。

 比如12034，我们把0 -1 ， 如果发现相减以后字符<0，说明发生了借位，需要变成11934，然后后半部分根据前半部分进行回文，变成11911

 如果遇到一直借位的，比如10000，我们从中间位置(index=2)开始往前借位，借位完成变成09900,我们发现第一位数字是0，说明我们一直借位到了最高位，
 这时候借位完毕的数字肯定全部都是9，并且位数=input.length-1。比如，输入11，我们求小于输入串的最大回文，即不大于10的最大回文。经过借位变成00，
 此时这个回文是9。又比如，，我们求小于1001的最大回文，即不大于1000的最大回文，经过从中间开始往前借位变成0100，此时这个合法的回文就是999.

 */

public class FindTheClosestPalindrome {

    public String nearestPalindromic(String n) {
         if(n==null||n.equals("")) return n;
         Long lower = this.findLower(((Long)(Long.valueOf(n)-1)).toString());
         Long higher = this.findHigher(((Long)(Long.valueOf(n)+1)).toString());
         return (Math.abs(lower-Long.valueOf(n)) >  Math.abs(higher - Long.valueOf(n)))?higher.toString():lower.toString();
    }

    private Long findHigher(String inputStr){
        char[] input = inputStr.toCharArray();
        char[] paradim = new char[input.length];
        int middle = (input.length+1 ) / 2 -1 ;
        for(int i=0;i<=middle;i++){
            paradim[i] = paradim[input.length-1-i] = input[i];
        }
        int compared = compare(paradim,input);
        if(compared == 0)
            return Long.valueOf(inputStr);
        else if(compared > 0){
            return Long.valueOf(new String(paradim));
        }
        char[] p = null;
        for(int i=middle;i>=0;i--){
            if(++paradim[i] > '9') //如果相加以后 > '9'，说明发生了进位，因此需要继续往前
                paradim[i] = '0';
            else
                break; //进位完成
        }
        if(paradim[0] == '0'){

            paradim = new char[input.length+1]; //如果经过不断进位，最前面的数组是0，说明进位已经溢出，因此，长度+1，第一位为0
            Arrays.fill(paradim,'0');//这个数字必定是1开头1结尾，中间是0
            paradim[0]='1';paradim[input.length]='1';
        }
        else{
            for(int i = middle+1;i < input.length;i++){ //前半部分设置完毕，后半部分直接按照前半部分进行回文
                paradim[i] = paradim[input.length-1-i];
            }
        }
        return Long.valueOf(new String(paradim));
    }


    private Long findLower(String inputStr){
        char[] input = inputStr.toCharArray();
        char[] paradim = new char[input.length];
        int middle = (input.length+1 ) / 2 -1 ;
        for(int i=0;i<=middle;i++){
            paradim[i] = paradim[input.length-1-i] = input[i];
        }
        int compared = compare(paradim,input);
        if(compared == 0)
            return Long.valueOf(inputStr);
        else if(compared < 0){
            return Long.valueOf(new String(paradim));
        }
        char[] p = null;
        for(int i=middle;i>=0;i--){
            if(--paradim[i] < '0') //说明发生了借位，因此需要继续往上一位相减
                paradim[i] = '9';
            else
                break;
        }
        if(paradim[0] == '0'){ //如果第一位是0，说明这个数字必定是9999，并且长度为input.length-1

            paradim = new char[input.length-1];
            Arrays.fill(paradim,'9');
        }
        else{
            for(int i = middle+1;i < input.length;i++){//前半部分设置完毕，后半部分直接按照前半部分进行回文
                paradim[i] = paradim[input.length-1-i];
            }
        }
        return Long.valueOf(new String(paradim));
    }


    public int compare(char[] a,char[] b){
        int minLength = Math.min(a.length,b.length);
        for(int i=0;i<minLength;i++){
            if(a[i] > b[i] )
                return 1;
            else if(a[i] < b[i])
                return -1;
        }
        return 0;
    }

    private Long addOrSub(boolean isAdd,char[] input,int middle){
        Long base = Long.valueOf(new String(input));
        char[] tail = new char[input.length -1 -middle];
        tail[0]='1';
        for(int i=1;i<tail.length;i++) tail[i]='0';
        Long added = isAdd? (base + Long.valueOf(new String(tail))):(base - Long.valueOf(new String(tail))) ;
        return added;
    }

    private char[] getPalindromic(String a,int middle){
        char[] input = a.toCharArray();
        char[] lowerPalindrome = new char[input.length];char[] upperPalindrome = null;
        for(int i=0;i<=middle;i++){
            lowerPalindrome[i] = lowerPalindrome[input.length-1-i] = input[i];
        }
        return  lowerPalindrome;
    }


    public Long toLong(char[] input){
        return Long.valueOf(new String(input));
    }



    public String nearestPalindromic1(String n) {
        Long number = Long.parseLong(n);
        Long big = findHigherPalindrome(number + 1);
        Long small = findLowerPalindrome(number - 1);
        return Math.abs(number - small) > Math.abs(big - number) ? String.valueOf(big) : String.valueOf(small);
    }
    public Long findHigherPalindrome(Long limit) {
        String n = Long.toString(limit);
        char[] s = n.toCharArray(); // limit
        int m = s.length;
        char[] t = Arrays.copyOf(s, m); // target
        for (int i = 0; i < m / 2; i++) {
            t[m - 1 - i] = t[i];
        }
        for (int i = 0; i < m; i++) {
            if (s[i] < t[i]) {
                return Long.parseLong(String.valueOf(t));
            } else if (s[i] > t[i]) {
                for (int j = (m - 1) / 2; j >= 0; j--) {
                    if (++t[j] > '9') {
                        t[j] = '0';
                    } else {
                        break;
                    }
                }
                // make it palindrome again
                for (int k = 0; k < m / 2; k++) {
                    t[m - 1 - k] = t[k];
                }
                return Long.parseLong(String.valueOf(t));
            }
        }
        return Long.parseLong(String.valueOf(t));
    }
    public Long findLowerPalindrome(Long limit) {
        String n = Long.toString(limit);
        char[] s = n.toCharArray();
        int m = s.length;
        char[] t = Arrays.copyOf(s, m);
        for (int i = 0; i < m / 2; i++) {
            t[m - 1 - i] = t[i];
        }
        for (int i = 0; i < m; i++) {
            if (s[i] > t[i]) {
                return Long.parseLong(String.valueOf(t));
            } else if (s[i] < t[i]) {
                for (int j = (m - 1) / 2; j >= 0; j--) {
                    if (--t[j] < '0') {
                        t[j] = '9';
                    } else {
                        break;
                    }
                }
                if (t[0] == '0') {
                    char[] a = new char[m - 1];
                    Arrays.fill(a, '9');
                    return Long.parseLong(String.valueOf(a));
                }
                // make it palindrome again
                for (int k = 0; k < m / 2; k++) {
                    t[m - 1 - k] = t[k];
                }
                return Long.parseLong(String.valueOf(t));
            }
        }
        return Long.parseLong(String.valueOf(t));
    }



    public static void main(String[] args) {
        Long.valueOf("2147557412");
        System.out.println(new FindTheClosestPalindrome().nearestPalindromic1("9999"));

    }
}