package thread;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/14 14:24
 */
public class ThreadDeamon {
    public static void main(String[] args) {
        God god=new God();
        You you=new You();

        Thread thread=new Thread(god);
        //默认false是用户线程，正常的线程都是用户线程
        thread.setDaemon(true);
        thread.start();

        new Thread(you).start();
    }



}

/**
 * 上帝
 */
class God implements Runnable{

    @Override
    public void run() {
        while (true){
            System.out.println("上帝在保佑着你");
        }
    }
}

/**
 * 你
 */
class You implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 36500; i++) {
            System.out.println("开心活着的第"+i+"天");
        }
        System.out.println("=========good bye world===========");
    }
}