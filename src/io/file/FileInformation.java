package io.file;

import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @ClassName FileInformation
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/11/4 17:16
 * @Version 1.0
 **/
public class FileInformation {
    public static void main(String[] args) {

    }

    //获取文件信息
    @Test
    public void info(){
        //创建文件
        File file = new File("e:\\news1.txt");

        //获取相应的方法,得到对应的信息
        System.out.println("文件名=>"+file.getName());


    }
}
