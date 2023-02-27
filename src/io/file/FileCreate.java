package io.file;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName FileCreate
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/11/4 16:24
 * @Version 1.0
 **/
public class FileCreate {

    public static void main(String[] args) {

    }

    //方式1 根据路径创建文件
    @Test
    public void create01(){
        String filePath="e:\\news1.txt";
        File file = new File(filePath);
        try {
            file.createNewFile();
            System.out.println("创建成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //方式2 根据父目录文件+子路径构建
    @Test
    public void create02(){
        File parentFile = new File("e:\\");
        String fileName="news2.txt";
        File file = new File(parentFile, fileName);
        try {
            file.createNewFile();
            System.out.println("创建成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //方式3 根据父目录+子路径创建
    @Test
    public void create03(){
        String parentName="e:\\";
        String fileName="news3.txt";
        File file = new File(parentName, fileName);

        try {
            file.createNewFile();
            System.out.println("创建成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
