
//懒汉式单例模式
public class Singleton {

    //私有化构造方法
    private Singleton(){

    }
    //在单例的对象前加入volatile防止指令重排的问题发生
    private static volatile Singleton singleton=null;

    public  Singleton getSingleton(){
        //双重检测锁机制可以解决除指令重排的多线程创建单例模式的问题
        if (singleton==null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

}
