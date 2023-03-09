package juc.test.single;

/**
 * @author noah
 * @version 1.0
 * @Description 静态内部类
 * Create by 2023/3/8 11:09
 */
public class Holder {
    private Holder(){

    }

    public static Holder getInstance(){
        return InnerClass.HOLDER;
    }

    public static class InnerClass{
        private static final Holder HOLDER = new Holder();
    }
}
