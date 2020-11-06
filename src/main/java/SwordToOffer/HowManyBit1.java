package SwordToOffer;

/**
 * @author chang.wu
 * @date 11/6/20
 *
 * 假如最长长度是4个bit
 * 1 左移：1, 2, 4, -8, 1,2 ,4
 * 1 右移：1,0,0,0
 * -1 左移：-2, -4,-8, -1,-2,-4, -8...
 * -1 右移：-1, -1 , -1, -1
 *
 */
public class HowManyBit1
{

    public static void main(String[] args)
    {
        int i = 1;
        int n = -1;
        System.out.println(-1/2);
        while(i < 60){
            int a = n >> i;
            System.out.println("左移动 " + i +  "位， " + a );
            i++;
        }
    }


}
