import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 手写一个自旋锁 不使用sync lock 用Unsafe CAS
 * 实现一个自旋锁  比较并交换 原子引用放Thread
 */

public class MyLock {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void getLock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.currentThread().getName() + "\t 获得一个锁");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public void unLock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.currentThread().getName() + "\t 释放一个锁");
        atomicReference.compareAndSet(thread, null);
    }


    /**
     * 手写一个自旋锁
     */


    public static void main(String[] args) {
        MyLock myLock = new MyLock();
        new Thread(() -> {
            myLock.getLock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myLock.unLock();
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            myLock.getLock();
            myLock.unLock();
        }, "t2").start();

    }
}