package juc.test.single;

import java.lang.reflect.Constructor;

/**
 * @author noah
 * @version 1.0
 * @Description 懒汉
 * Create by 2023/3/8 10:56
 */
public class Lazy {

    private static boolean flag=false; //加密判断符

    private Lazy(){

        synchronized (Lazy.class){
            if (flag==false){
                flag=true;
            }else {
                throw new RuntimeException("不要试图使用反射破坏异常"); //解决都是反射创建的问题
            }
//            if (lazy!=null){
//                throw new RuntimeException("不要试图使用反射破坏异常"); //用的是同一个类锁
//            }
        }
        //System.out.println(Thread.currentThread().getName());
    }

    private volatile static Lazy lazy;

    //双重检测锁模式 懒汉式单例 DCL
    public static Lazy getInstance(){
        if (lazy==null){
            synchronized (Lazy.class){
                if (lazy == null) {
                    lazy = new Lazy(); //不是原子性操作
                    /**
                     * 1.分配内存空间
                     * 2.执行构造方法，初始化对象
                     * 3.把这个对象指向这个空间
                     *
                     * 123
                     * 132 A
                     *     B //此时lazy还没完成构造
                     */
                }
            }
        }
        return lazy;
    }

    //单线程下没问题
    //多线程并发
    public static void main(String[] args) throws Exception {
//        for (int i = 0; i < 10; i++) {
//            new Thread(()->{
//                Lazy.getInstance();
//            }).start();
//        }
       // Lazy lazy1 = Lazy.getInstance();

        Constructor<Lazy> declaredConstructor = Lazy.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        Lazy lazy1 = declaredConstructor.newInstance();
        Lazy lazy2 = declaredConstructor.newInstance();
        System.out.println(lazy1.hashCode());
        System.out.println(lazy2.hashCode());
    }
}
