import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

//验证volatile的可见性（保证可见性，不保证原子性，禁止指令重排(多线程下可能出现指令重排的问题 使用volatile可以解决)）
//JMM java内存模型 (可见性，原子性，有序性)
//加入number=0;number变量在没有用volatile修饰之前 没有可见性
class MyDate{
    volatile int number=0;
    public void addTo60(){
        this.number=60;
    }

    //测试volatile是否保证原子性
    public void addNumber(){
        number++;
    }
    //解决原子性 我们有两个方法 第一个方法在需要保证线程安全的方法前加sync
    //直接是使用Atomic提供的Integer保证原子性
    AtomicInteger atomicInteger=new AtomicInteger();
    public void addANumber(){
        atomicInteger.getAndIncrement();
        //atomicInteger.getAndAdd(1);
    }


}


public class VolatileDemo {
    public static void main(String[] args) {
        //seeVolatileOk();
        MyDate myDate=new MyDate();
        for (int i=1;i<=20;i++){
            new Thread(()->{
                for (int j=1;j<=1000;j++){
                    myDate.addNumber();
                    myDate.addANumber();
                }
            }).start();
        }
        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println("普通的int类型 在多线程的并发量很大的时候，会发生数据丢失的问题"+myDate.number);
        System.out.println("使用原子性Integer 可以保证原子性"+myDate.atomicInteger);
    }

    //volatile可见性保证，某个线程在工作空间修改后 写回主内存的时候可以即时通知其他线程值已经修改完成
    private static void seeVolatileOk() {
        MyDate myDate=new MyDate();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try{
                TimeUnit.SECONDS.sleep(3);
            }catch (Exception e){
                e.printStackTrace();
            }
            myDate.addTo60();
            System.out.println(Thread.currentThread().getName()+"\t update number "+myDate.number);
        },"aaa").start();

        while (myDate.number==0){

        }
        System.out.println(Thread.currentThread().getName()+"\t执行完成");
    }
}
