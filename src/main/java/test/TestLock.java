package test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wuchang at 1/15/18
 */

public class TestLock {

    public synchronized  static void coreBlock() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+" enter core block.");
        Thread.sleep(500000);
    }
    public static void main(String[] args) {

        final Lock l1 = new ReentrantLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

//
                try{
                    Thread.currentThread().setName("A");
                    TestLock.coreBlock();
                System.out.println("a trying to lock.");
//                l1.lock();
                 System.out.println("a locked.");
            }catch (Exception e){
                System.out.println(123);
            }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.currentThread().setName("B");
                    TestLock.coreBlock();
//                    System.out.println("b trying to lock.");
//                    l1.lockInterruptibly();
                     System.out.println("b locked.");
                }catch (Exception e){
                    System.out.println(123);
                }
            }
        });


        t1.start();
        t2.start();

        t2.interrupt();
        t1.interrupt();
    }
}
