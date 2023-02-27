package thread.syn;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author noah
 * @version 1.0
 * @Description 测试锁
 * Create by 2023/2/17 14:04
 */
public class ThreadLock {

    public static void main(String[] args) {
        Lock2 lock2 = new Lock2();
        new Thread(lock2).start();
        new Thread(lock2).start();
        new Thread(lock2).start();

    }
}

class Lock2 implements Runnable{

    int ticketNums =10;

    //定义锁
    private final ReentrantLock lock=new ReentrantLock();

    @Override
    public void run() {
        while (true){
            try {
                lock.lock();
                if (ticketNums>0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(ticketNums--);
                }else {
                    break;
                }
            } finally {
                lock.unlock();//解锁
            }
        }

    }
}