package leetcode.test;

public class ZookeeperBugs {
    public static void main(String[] args) {
        long time_20220407=1649322015000L;
        long time_20220406=1649174400000L;
        long time_20210206=1612540800000L;
        long time_20200128=1580202015000L;
        long id = 1;
        System.out.println(Long.MAX_VALUE);
        System.out.println("Current time "+ System.currentTimeMillis());

        System.out.println(String.format("20220407 format 0x%08X", time_20220407) + ", " + String.format("20220406 format 0x%08X", time_20220406));
        System.out.println("i " + 24 + " : " + ((time_20220407 << 24) >> 8)+ "," + ((time_20220406 << 24)  >> 8 )+ ", " + ((time_20210206 << 24) >>8 )+ ", " + ((time_20200128 << 24) >> 8)  );


        for(int i = 0;i<25;i++){
            System.out.println("i " + i + " : " + (time_20220407 << i) + ","+ (time_20220406 << i) + ", " + (time_20210206 << i) + ", " + (time_20200128 << i));
        }

        for(int i = 0;i<25;i++){
            System.out.println("i " + i + " : " + ((time_20220407 << i) >> 8)+ "," + ((time_20220406 << i)  >> 8 )+ ", " + ((time_20210206 << i) >>8 )+ ", " + ((time_20200128 << i) >> 8) );
        }

        System.out.printf("%010x\n",1649322015000L<<24);
        System.out.printf("%010x\n",1649322015000L);
        System.out.printf("%010x\n",1649174400000L);
        System.out.printf("%010x\n",1649174400000L<<24);



//        for(int i = 0;i<63;i++){
//            System.out.println("i " + i + " : id  " + (id << i) );
//        }

    }

    public static long initializeNextSession(long id) {
        long nextSid = 0;
        nextSid = (System.currentTimeMillis() << 24) >> 8; // 右移动8位，让高8位为0，作为sid的位置
        nextSid =  nextSid | (id <<56); // 这个默认nextSid的高八位是0，这样才会在经过或操作以后让id占据高8位
        return nextSid;
    }
}
