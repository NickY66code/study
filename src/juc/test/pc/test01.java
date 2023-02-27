package juc.test.pc;

/**
 * @author noah
 * @version 1.0
 * @Description 测试Syn
 * Create by 2023/2/27 11:39
 */
public class test01 {
    public static void main(String[] args) {
        //PCSyn pc = new PCSyn();
        PCJuc pc = new PCJuc();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    pc.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    pc.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"B").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    pc.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"C").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    pc.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"D").start();
    }
}

