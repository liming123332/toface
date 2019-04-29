public class Singleton3 {
    private Singleton3(){

    }
    private static Singleton3 singleton3=null;

    //通过内部类创建懒汉式单例模式 只有当内部类调用该属性的时候才会创建该单例对象
    private static class Inner{
        private static final Singleton3 INSTANCE = new Singleton3();
    }
    private Singleton3 getSingleton3(){
        return Inner.INSTANCE;
    }
}
