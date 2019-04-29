import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁 读可以共享 写要独占 读写分离
 * 手写一个缓存 写操作保证原子性
 */

class MyCache{
    private volatile Map<String,Object> map=new HashMap();

    ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();

    public void put(String key,Object value){
        try{
            readWriteLock.writeLock();
            map.put(key,value);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key){
        try {
            readWriteLock.readLock();
            Object o = map.get(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.readLock().unlock();
        }
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {

    }
}
