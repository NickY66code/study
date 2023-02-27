package thread;

/**
 * @author noah
 * @version 1.0
 * @Description 线程停止
 * Create by 2023/2/14 9:58
 */
public class ThreadStop implements Runnable{

    private boolean flag=true;

    @Override
    public void run() {
        int i = 0;
        while (flag){
            System.out.println("Thread...running"+i++);
        }
    }

    private void stop(){
        this.flag=false;
        System.out.println("Thread stop!");
    }

    public static void main(String[] args) {
        ThreadStop threadStop=new ThreadStop();
        new Thread(threadStop).start();

        for (int i = 0; i < 1000; i++) {
            System.out.println("main"+i);
            if (i==900){
                threadStop.stop();
            }
        }
    }

}

