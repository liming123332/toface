import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABA {
    static class MyDate{

        //原子引用类 可以解决因为CAS引发的ABA问题的产生
         AtomicStampedReference<Integer> atoInteger=
                new AtomicStampedReference<>(100,1);//初始值，版本号

    }

    public static void main(String[] args) {

        MyDate myDate=new MyDate();
        //初始版本号
        int stamp = myDate.atoInteger.getStamp();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName()+"\t"+"初始版本号为"+stamp);
                myDate.atoInteger.compareAndSet(100,101,stamp,myDate.atoInteger.getStamp()+1);
                myDate.atoInteger.compareAndSet(101,100,myDate.atoInteger.getStamp(),myDate.atoInteger.getStamp()+1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"thread1").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(4);
                System.out.println(Thread.currentThread().getName()+"\t"+"初始版本号为"+stamp);
                System.out.println(myDate.atoInteger.compareAndSet(100,101,stamp,myDate.atoInteger.getStamp()+1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"thread2").start();

    }
}
