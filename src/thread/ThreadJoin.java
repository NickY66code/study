package thread;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/14 10:49
 */
public class ThreadJoin implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("我是vip我来插队了！"+ i);
        }
    }

    public static void main(String[] args) {
        ThreadJoin threadJoin = new ThreadJoin();
        Thread thread = new Thread(threadJoin);
        thread.start();

        for (int i = 0; i < 100; i++) {
            if(i==10){
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("main "+i);
        }
    }
}
