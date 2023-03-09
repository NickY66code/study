package juc.test.single;

/**
 * @author noah
 * @version 1.0
 * @Description 饿汉式单例
 * Create by 2023/3/8 10:52
 */
public class Hungry {

    //可能会浪费空间
    private byte[] data1=new byte[1024*1024];
    private byte[] data2=new byte[1024*1024];
    private byte[] data3=new byte[1024*1024];
    private byte[] data4=new byte[1024*1024];

    private Hungry(){

    }

    private final static Hungry HUNGRY=new Hungry();

    public static Hungry getInstance(){
        return HUNGRY;
    }

}
