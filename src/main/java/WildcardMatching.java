/**
 *
 * Question 44
 * Created by wuchang at 12/16/17
 *
 */

public class WildcardMatching {

    Boolean result[][] = null;


    public boolean isMatch(String s, String p) {

        result = new Boolean[s.length()+1][p.length()+1];
        return recurse(0,0,s.toCharArray(),p.toCharArray());
    }

    private boolean recurse(int i,int j ,char[] s,char[] p){

        if(result[i][j] != null)
            return result[i][j];
        if(i==s.length)
        {
            //这一行代码可以注释掉，因为在下面的for循环由于j=p.length所以不执行，直接也是返回true
//            if(j==p.length){
//                result[i][j] = true; //空的输入串和空的正则肯定是匹配的
//                return true;
//            }

            for(;j<p.length;j++){ //如果输入串已经结束了，但是正则还有，那就只能全部是*
                if(p[j] != '*') {
                    result[i][j] = false;
                    return false;
                }
            }
            result[i][j] = true;
            return result[i][j];
        }
        else if(j==p.length) //正则用完了，但是输入串还有字符，肯定格式匹配失败
        {
            result[i][j] = false;
            return result[i][j];
        }

        if(p[j] == '*'){ //如果当前遇到了正则的*，则分两种情况，* 匹配了0个，*至少匹配了1个
            result[i][j] = recurse(i+1,j,s,p) //至少匹配了一个字符
                    || recurse(i,j+1,s,p); //匹配了0个字符
            return result[i][j];
        }

        result[i][j]  = ( s[i] == p[j] || p[j] == '?' ) //输入串和正则的头部是否匹配
                ?  recurse(i+1,j+1,s,p) //头部匹配，需要看看后面是否匹配，因此递归调用
                : false ; //头部不匹配，肯定就不匹配了，后面不用比较了
        return result[i][j];
//        boolean matchHead = s[i] == p[j] || p[j] == '?';
//        if(!matchHead){
//            result[i][j] = false;
//            return result[i][j];
//        }
//
//        result[i][j] =  recurse(i+1,j+1,s,p);
//        return result[i][j];
    }

    public static void main(String[] args) {
       System.out.println(new WildcardMatching().isMatch("d","*d")) ;
    }
}
