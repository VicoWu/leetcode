package leetcode;

/**
 * Created by wuchang at 1/12/18
 * Question 165
 */

public class CompareVersionNumbers {
    public int compareVersion(String version1, String version2) {

        String[] s1 = version1.split("\\.");
        String[] s2 = version2.split("\\.");
        for(int i=0;i<s1.length || i<s2.length;i++){
            Integer v1 =  i<s1.length?Integer.valueOf(s1[i]):0;
            Integer v2 =  i<s2.length?Integer.valueOf(s2[i]):0;
            if(v1!=v2)
                return v1>v2?1:-1;
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(new CompareVersionNumbers().compareVersion("1.21","1.31"));
        System.out.println(new CompareVersionNumbers().compareVersion("1.2","1.13"));
    }



}
