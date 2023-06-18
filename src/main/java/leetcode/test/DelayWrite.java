package leetcode.test;

public class DelayWrite implements Runnable {
    private volatile String str;
    void setStr(String str) {this.str = str;}

    public void run() {
        while (str == null);
            //System.out.println("null");
        System.out.println(str);
    }

    public static void main(String[] args) throws InterruptedException {
        DelayWrite delay = new DelayWrite();
        new Thread(delay).start();
        Thread.sleep(1000);
        System.out.println("Slepted");
        delay.setStr("Hello world!!");
    }
}