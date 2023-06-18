package leetcode.test;

import java.util.Random;

public class VolatileThread extends Thread
    {
        public static ThreadLocal<String> th = new ThreadLocal<String>();

        private final VolatileData data;
public VolatileThread(VolatileData data)
        {
            this.data = data;
        }
        @Override
        public void run()
        {
            th.set(Thread.currentThread().getName());
            th.get();
            Random r =  new Random();
            int oldValue = data.getCounter();
            System.out.println("[Thread " + Thread.currentThread().getId() + "]: Old value = " + oldValue);

            data.increaseCounter();
            int newValue = data.getCounter();
            System.out.println("[Thread " + Thread.currentThread().getId() + "]: New value = " + newValue);
        }
    }
