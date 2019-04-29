import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableDemo {
    public static void main(String[] args) {
        Callable<Integer> callable=new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName()+"\t 线程启动");
                return 1;
            }
        };
        FutureTask<Integer> futureTask= new FutureTask(callable);
        new Thread(futureTask,"1").start();
        Integer integer = null;
        try {
            integer = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(integer);
    }
}
