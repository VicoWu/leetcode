package leetcode;

/**
 * Created by wuchang at 2/6/18
 */

public class IntegerToRoman {

    public String intToRoman(int num) {
        if(num <= 0) return "";
        String ret = "";
         Integer[] number = {1000, 900, 500, 400, 100,90, 50, 40, 10, 9, 5, 4, 1};
         String[] flags = {"M","CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for(int i = 0; i < 13 && num > 0; i++){
            if(num < number[i]) continue;
            // cout<< i << " " << number[i] << " - " <<flags[i] << endl;
            while(num >= number[i]){
                num-= number[i];
                ret += flags[i];
            }

        }
         return ret;
    }

    public static void main(String[] args) {
        new IntegerToRoman().intToRoman(49);
        new IntegerToRoman().intToRoman(2);
        new IntegerToRoman().intToRoman(3);
        new IntegerToRoman().intToRoman(4);
        new IntegerToRoman().intToRoman(5);
    }
}

