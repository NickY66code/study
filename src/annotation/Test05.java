package annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/24 14:48
 */
public class Test05 {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Class c1 = Class.forName("annotation.User");

        //创建对象
        //User user =(User) c1.newInstance();

        //System.out.println(user);

        //通过构造器创建对象
        //Constructor declaredConstructor = c1.getDeclaredConstructor(String.class, int.class, int.class);
        //User user2 = (User)declaredConstructor.newInstance("noah", 1, 18);
        //System.out.println(user2);

        //通过反射调用普通方法
//        User user3 =(User) c1.newInstance();
//        Method setName = c1.getDeclaredMethod("setName", String.class);
//        setName.invoke(user3,"noah");
//        System.out.println(user3.getName());

        //通过反射操作属性
        User user4 =(User) c1.newInstance();
        Field name = c1.getDeclaredField("name");
            //不能直接访问private属性,关闭安全检测
            name.setAccessible(true);
            name.set(user4,"rose");
        System.out.println(user4.getName());
    }
}
