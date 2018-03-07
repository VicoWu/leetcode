package leetcode;

/**
 * Created by wuchang at 3/7/18
 */

public class LucyNumber {


    public int howMany(int n){

        if(n<2)
            return 0;
        if(n>=2 && n<5)
            return 1;
        if(n>=5 & n<8)
            return 2;
        else
            return 3;
    }

    public int countBySingleNumber(int number){
        int res = 1;
        for(int i=10;;i*=10){
            int remain = number%i;
            number = number/i;
            if(number != 0){
                res = res * 3;
            }
            else{
                res = res + howMany(remain) * res;
                return res;
            }
        }
//        int res = howMany(number%10);number = number/10;
//        for(int i=100;number>0;i*=10){
//            int remain = number%i;
//            number = number/i;
//            res = res * (howMany(remain) + 1);
//        }
//        return res;
    }

    public int countLucy(int from , int to){

        return countBySingleNumber(to) - countBySingleNumber(from);
    }

    public static void main(String[] args) {

        new LucyNumber().countBySingleNumber(20);
    }
}
