package leetcode;

/**
 * Created by wuchang at 2/6/18
 *
 */

public class RomanToInteger {

    public int romanToInt(String s) {
        char[] tagVal = new char[256];
        tagVal['I'] = 1;
        tagVal['V'] = 5;
        tagVal['X'] = 10;
        tagVal['C'] = 100;
        tagVal['M'] = 1000;
        tagVal['L'] = 50;
        tagVal['D'] = 500;
        int val = 0;
        for(int i = 0; i < s.length(); i++){
            if(i+1 >= s.length() || tagVal[s.charAt(i+1)] <= tagVal[s.charAt(i)])
                val += tagVal[s.charAt(i)];
            else
                val -= tagVal[s.charAt(i)];
        }
        return val;
    }


    public static void main(String[] args) {
        new RomanToInteger().romanToInt("VI");
    }
}
