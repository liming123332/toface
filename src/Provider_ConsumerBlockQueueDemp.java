import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource{
    private volatile boolean FLAG=true;

    private BlockingQueue<String> blockingQueue;
    private AtomicInteger atomicInteger=new AtomicInteger();
    public MyResource(BlockingQueue blockingQueue){
        this.blockingQueue=blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }
    public void myProvider() throws Exception {
        String data=null;
        boolean result;
        while (FLAG){
            data=atomicInteger.incrementAndGet() + "";
            result= blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if(result){
                System.out.println(Thread.currentThread().getName()+"\t 插入数据"+data+"到队列成功");
            }else{
                System.out.println(Thread.currentThread().getName()+"\t 插入数据"+data+"到队列失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println(Thread.currentThread().getName()+"\t 5秒过后大老板叫停 生产者停止生产");
    }

    public void myConsumer()throws Exception{
        while(FLAG){
            String data = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if(null==data||"".equals(data)){
                System.out.println(Thread.currentThread().getName()+"\t 超过2秒没有拿到消息 消费者停止消费");
                FLAG=false;
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t 消费者消费一个数据"+data);
        }
    }

    public void stop(){
        this.FLAG=false;
    }
}


public class Provider_ConsumerBlockQueueDemp {

    public static void main(String[] args) {
        MyResource myResource=new MyResource(new ArrayBlockingQueue(1));
        new Thread(()->{
            try {
                myResource.myProvider();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"AAA").start();

        new Thread(()->{
            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"BBB").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myResource.stop();
    }
}
