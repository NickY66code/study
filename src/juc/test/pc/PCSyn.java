package juc.test.pc;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/27 16:19
 */
class PCSyn {
    private int num = 0;

    public synchronized void incr() throws InterruptedException {
        while (num != 0) {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + num++);
        //唤醒其他线程
        this.notifyAll();
    }

    public synchronized void decr() throws InterruptedException {
        while (num == 0) {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + num--);
        //唤醒其他线程
        this.notifyAll();
    }
}
