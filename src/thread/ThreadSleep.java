package thread;

/**
 * @author noah
 * @version 1.0
 * @Description 线程休眠：模拟网络延迟:放大问题的发生性  每个对象都有一个锁，sleep不会释放锁
 * Create by 2023/2/14 10:05
 */

public class ThreadSleep implements Runnable{

    /**
     * 票数
     */
    private int ticket=10;

    @Override
    public void run() {

        while (true){
            if (ticket<=0){
                break;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName()+"已拿到第"+ticket--+"张票");
        }
    }

    /**
     * 模拟倒计时
     */
    public static void countDown(){
        int num=10;
        while (true){
            if (num<=0){
                break;
            }
            try {
                System.out.println("倒计时："+num--);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
//        ThreadSleep threadSleep =new ThreadSleep();
//
//        new Thread(threadSleep,"noah").start();
//        new Thread(threadSleep,"curry").start();
//        new Thread(threadSleep,"xxx").start();

        countDown();

    }
}
