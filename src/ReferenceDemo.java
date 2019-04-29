import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * 强软弱虚四种引用
 */
public class ReferenceDemo {

    public static void main(String[] args) {
        System.out.println("强引用");
        strongReference();
        System.out.println("===================");
        System.out.println("软引用,内存充足的时候不回收，如果内存不充足就回收");
        softReference();
        System.out.println("===================");
        System.out.println("弱引用,不管内存充足不充足，一定回收");
        weakReference();
        System.out.println("===================");
        System.out.println("弱hashamp,不管内存充足不充足，一定回收");
        weakMap();
    }

    private static void strongReference() {
        Object o1=new Object();
        Object o2=o1;
        o1=null;
        System.gc();
        System.out.println(o1);
        System.out.println(o2);
    }

    private static void softReference(){
        Object o1=new Object();
        SoftReference<Object> o2=new SoftReference<>(o1);
        o1=null;
        System.gc();
        System.out.println(o1);
        System.out.println(o2.get());
    }

    private  static void weakReference(){
        Object o1=new Object();
        WeakReference<Object> o2=new WeakReference<>(o1);
        o1=null;
        System.gc();
        System.out.println(o1);
        System.out.println(o2.get());

    }

    /**
     * 弱hashmap
     */
    private static void weakMap(){
        WeakHashMap weakHashMap=new WeakHashMap();
        Integer key=new Integer(1);
        weakHashMap.put(key,"Integer");
        key=null;
        System.out.println(weakHashMap);
        System.gc();
        System.out.println(weakHashMap);
    }


}
