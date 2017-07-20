package blog.brucefeng.info.ratelimiter;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RateLimiterRun {
    public static void main(String[] args) {
//        RateLimiter rateLimiter = RateLimiter.create(3);
        RateLimiter rateLimiter = RateLimiter.create(3,1, TimeUnit.MILLISECONDS);

        List<Thread> threadList = Lists.newArrayList();




        for(int i = 0;i<10;i++){
            threadList.add(new Thread(new FlowRunnable(rateLimiter,i)));
//            executorService.submit(new FlowRunnable(rateLimiter,i));
        }

        for(Thread t:threadList) t.start();

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        System.out.println("again with rate:"+rateLimiter.getRate());

//        for(Thread t:threadList) t.start();

//        executorService.shutdown();
    }

    public static class FlowRunnable implements Runnable{
        private RateLimiter rateLimiter;
        private int index;

        public FlowRunnable(RateLimiter rateLimiter, int index) {
            this.rateLimiter = rateLimiter;
            this.index = index;
        }

        public void run() {
//            rateLimiter.acquire();
//            System.out.println(System.currentTimeMillis()+":"+index+": run with limiter rate:"+rateLimiter.getRate());

            if(rateLimiter.tryAcquire()){
                System.out.println("--:"+index+": run with limiter rate:"+rateLimiter.getRate());
            }else{
                System.out.println(index +": cannnot run with limiter rate:"+rateLimiter.getRate());
            }
        }
    }
}
