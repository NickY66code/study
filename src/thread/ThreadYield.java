package thread;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/14 10:44
 */
public class ThreadYield implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"Thread start");
        Thread.yield();//礼让:不一定成功，看cpu心情
        System.out.println(Thread.currentThread().getName()+"Thread stop");
    }

    public static void main(String[] args) {
        ThreadYield threadYield=new ThreadYield();

        new Thread(threadYield,"a").start();
        new Thread(threadYield,"b").start();
    }
}
