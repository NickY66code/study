package annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/24 10:58
 */
public class Test04 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {
        Class c1 = Class.forName("annotation.User");

        //获得类的名字
        System.out.println(c1.getName());
        System.out.println(c1.getSimpleName());

        //获得指定属性的值 只能找到public属性
        for (Field field : c1.getFields()) {
            System.out.println(field);
        }
        System.out.println("=============================");
        //获取全部属性
        for (Field declaredField : c1.getDeclaredFields()) {
            System.out.println(declaredField);
        }
        System.out.println("=============================");
        //获取指定属性
        System.out.println(c1.getDeclaredField("name"));
        System.out.println("=============================");
        //获得类的方法
        for (Method method : c1.getMethods()) { //获得本类及其父类的全部public方法
            System.out.println("getMethods:"+method);
        }
        for (Method declaredMethod : c1.getDeclaredMethods()) { //获取本类的所有方法
            System.out.println("getDeclaredMethods"+declaredMethod);
        }
        System.out.println("=============================");
        //获得指定方法
        System.out.println(c1.getMethod("getName", null));
        System.out.println(c1.getMethod("setName", String.class));
        System.out.println("=============================");
        //获得指定构造器
        for (Constructor constructor : c1.getConstructors()) {
            System.out.println("getConstructors:"+constructor);
        }
        for (Constructor declaredConstructor : c1.getDeclaredConstructors()) {
            System.out.println("getDeclaredConstructors:"+declaredConstructor);
        }

    }
}
