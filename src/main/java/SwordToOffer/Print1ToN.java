package SwordToOffer;

/**
 * 剑指offer 17题
 * @author chang.wu
 * @date 11/6/20
 */
public class Print1ToN
{
    public void print1ToMaxOfNDigit(int n){
        char[] cur = new char[n+1];
        for(int i = 0; i<=n; i++)
            cur[i] = '0';
        while(increment(n, cur)){
            print(n, cur);
        }
    }

    public boolean increment(int n , char[] cur){

        int takeOver = 1;

        for(int i = n; i > 0; i--){
            int sum = cur[i] - '0' + takeOver ;
            if(sum >= 10)
            {
                takeOver = 1;
                cur[i] = '0';
                if(i == 1) // 已经到了第一位数字, 不用再打印了
                {
                    return false;
                }
            }
            else{ //没有发生进位
                cur[i] = (char)(cur[i] + 1);
                break;
            }

        }
        return true;

    }

    public void print(int n , char[] c){
       int firstNonZero = -1;
       while(c[++firstNonZero] == '0')
           continue;
       for(int i = firstNonZero; i<=n; i++)
           System.out.print(c[i]);
       System.out.println();
    }

    public static void main(String[] args)
    {
        new Print1ToN().print1ToMaxOfNDigit(3);
    }

}
