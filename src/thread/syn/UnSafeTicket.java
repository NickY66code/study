package thread.syn;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/14 15:01
 */
public class UnSafeTicket {
    public static void main(String[] args) {
        BuyTicket station=new BuyTicket();
        new Thread(station,"james").start();
        new Thread(station,"noah").start();
        new Thread(station,"黄牛").start();
        new Thread(station,"rose").start();
    }


}

class BuyTicket implements Runnable{
    //票数
    private int ticket=10;
    private boolean flag=true;

    @Override
    public void run() {
        //买票
        while (flag){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            buy();
        }
    }
    /**
     * synchronized 同步方法
     */
    public synchronized void buy(){
        //判断是否有票
        if (ticket<=0){
            flag=false;
            return;
        }
        //模拟延迟 sleep不释放锁
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        //买票
        System.out.println(Thread.currentThread().getName()+"拿到第"+ticket--+"张票");
    }
}