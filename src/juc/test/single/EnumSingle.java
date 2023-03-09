package juc.test.single;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import java.lang.reflect.Constructor;

/**
 * @author noah
 * @version 1.0
 * @Description 枚举单例
 * Create by 2023/3/8 11:33
 */
public enum EnumSingle {
    /**
     * 枚举单例
     */
    INSTANCE;

    public EnumSingle getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) throws Exception {
        EnumSingle instance1 = EnumSingle.INSTANCE;
        Constructor<EnumSingle> instance2 = EnumSingle.class.getDeclaredConstructor(String.class,int.class);
        instance2.setAccessible(true);
        instance2.newInstance();

        System.out.println(instance1);
        System.out.println(instance2);//IllegalArgumentException: Cannot reflectively create enum objects
    }
}
