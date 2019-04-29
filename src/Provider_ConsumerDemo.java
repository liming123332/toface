import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 资源类
 */
class MyDate2{
    private int number=0;
    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();
    public void addNumber(){
        lock.lock();
        try{
            while (number!=0){
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            //通知唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
    public void deNumber(){
        lock.lock();
        try {
            while (number==0){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            //通知唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }

    }
}
//要求两个线程，操作同一个变量，一个加一，一个减一，共5轮
public class Provider_ConsumerDemo {

    public static void main(String[] args) {
        //线程操纵资源类
        MyDate2 myDate2=new MyDate2();
        new Thread(()->{
            for(int i=1;i<=5;i++){
                myDate2.addNumber();
            }
        },"AAA").start();

        new Thread(()->{
            for(int i=1;i<=5;i++){
                myDate2.deNumber();
            }
        },"BBB").start();
    }
}
