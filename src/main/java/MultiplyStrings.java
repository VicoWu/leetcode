import java.util.Arrays;

/**
 * Created by wuchang at 1/4/18
 * Question 43
 * 这一题比较简单：
 123 * 456
 我们先用3和456相乘，得到1368
 然后用2和456相乘,得到912，然后`912*10+1368 = 10488`
 然后用1和456相乘得到456 ， 然后`456 * 10*10 + 10488 = 56088`
 */

public class MultiplyStrings {
    /**
     * 这是我的解法
     * @param num1
     * @param num2
     * @return
     */
    public String multiply(String num1, String num2) {

        String result="";
        int ahead = 0;
        for(int i=num1.length()-1;i>=0;i--){
            char c = num1.charAt(i);
            int lower = 0;
            StringBuffer sb = new StringBuffer();
            for(int j=num2.length()-1;j>=0;j--){
                int r = multiply(c,num2.charAt(j)) + lower;
                sb.append(r%10);
                lower = r/10;
            }
            if(lower>0)
                sb.append(lower);//看看最后有没有向高位的进位
            char[] padding = new char[ahead++];//需要补0 的个数
            Arrays.fill(padding,'0'); //需要补0 的个数，比如,123*456，当5*123的结果获取以后，后面需要补一个0，再加上6*123的结果，同理，4*123的结果应该补两个0
            result = add(result,sb.reverse().toString().concat(new String(padding)));
        }
        return result;
    }


    public String add(String a,String b){
        int i = a.length()-1;int j = b.length()-1;
        int lower  = 0;
        StringBuffer sb = new StringBuffer();
        for(;i>=0 || j>=0;i--,j--){


            int sum = lower + (i>=0?a.charAt(i):'0') - '0' + (j>=0?b.charAt(j):'0')- '0';
            sb.append(sum%10);
            lower = sum/10;
        }

        if(lower>0) sb.append(lower);
        String result = sb.reverse().toString();
        int m = 0;
        for(m=0;m<result.length() && result.charAt(m) == '0';m++);
        if(m==result.length()) return "0";
        return result.substring(m).toString();
    }

    /**
     * 两个字符相乘
     * @param a
     * @param b
     * @return
     */
    public int multiply(char a , char b){
        return ((int)(a-'0')) * ((int)(b-'0'));
    }


    /**
     * 这是别人的算法
     * @param num1
     * @param num2
     * @return
     */
    public String multiply2(String num1, String num2) {
        int m = num1.length(), n = num2.length();
        int[] pos = new int[m + n];

        for(int i = m - 1; i >= 0; i--) {
            for(int j = n - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p1 = i + j, p2 = i + j + 1;
                int sum = mul + pos[p2]; //当前的结果加上上一位的进位

                pos[p1] += sum / 10; //进位
                pos[p2] = (sum) % 10; //进位到高位
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int p : pos) if(!(sb.length() == 0 && p == 0)) sb.append(p);
        return sb.length() == 0 ? "0" : sb.toString();
    }

    public static void main(String[] args) {
        new MultiplyStrings().add("0","00");
        new MultiplyStrings().multiply("32014","0");
    }
}
