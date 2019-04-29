import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {


    public static void main(String[] args) {
        CountDownLatch countDownLatch=new CountDownLatch(7);
        for (int i=1;i<=7;i++) {
            final int tempInt=i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 离开教室");
                countDownLatch.countDown();
            },String.valueOf(tempInt)).start();
        }
        try {
            countDownLatch.await();
            System.out.println("班长锁门离开教室");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
