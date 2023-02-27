package juc.test.pc;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/27 16:45
 */
public class test02 {
    public static void main(String[] args) {
        PCJuc2 pc = new PCJuc2();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                pc.a();
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                pc.b();
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                pc.c();
            }
        }).start();
    }
}
