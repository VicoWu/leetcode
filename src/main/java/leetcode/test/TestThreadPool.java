package leetcode.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPool {
    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        ScheduledThreadPoolExecutor scheduledThreadPool = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(2);
//        scheduledThreadPool.schedule(new ThreadPoolTask(time), 1000, TimeUnit.MILLISECONDS);
//        scheduledThreadPool.schedule(new ThreadPoolTask(time), 1000, TimeUnit.MILLISECONDS);
        scheduledThreadPool.scheduleAtFixedRate(new ThreadPoolTask("a",time), 0,1000, TimeUnit.MILLISECONDS);
        scheduledThreadPool.scheduleAtFixedRate(new ThreadPoolTask("b", time), 0,1000, TimeUnit.MILLISECONDS);
    }

    public static class ThreadPoolTask implements Runnable {

        private long time;
        private String name;

        public ThreadPoolTask(String name, long time) {
            this.time = time;
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "执行" + this.name + " " + "用时" + (System.currentTimeMillis() - time));
            try {
                Thread.sleep(3000);//让线程休息一会儿
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


