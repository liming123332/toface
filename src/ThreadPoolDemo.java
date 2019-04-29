import java.util.concurrent.*;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        ExecutorService threadpool=
                new ThreadPoolExecutor(
                        2,
                        5,
                        1L,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(3),
                        Executors.defaultThreadFactory(),
                        new  ThreadPoolExecutor.DiscardPolicy());


        for (int i = 0; i <=10 ; i++) {
            threadpool.execute(()->{
                System.out.println(Thread.currentThread().getName()+"\t 办理业务");
            });
        }
    }

}
