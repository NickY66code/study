package io.file;

import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @ClassName Directory_
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/11/4 17:30
 * @Version 1.0
 **/
public class Directory_ {
    public static void main(String[] args) {

    }

    @Test
    public void m1(){
        String filePath="e:\\news1.txt";
        File file = new File(filePath);
        if (file.exists()){
            if (file.delete()) {
                System.out.println(filePath+"删除成功");
            }else {
                System.out.println(filePath+"删除失败");
            }
        }else {
            System.out.println("文件不存在！");
        }
    }

    @Test
    public void m2(){
        String directryPath="e:\\demo\\a\\b\\c";
        File file = new File(directryPath);
        if (file.exists()){
            System.out.println("该目录已存在");
        }else {
            //mkdirs()创建多级目录
            if (file.mkdirs()) {
                System.out.println("创建"+directryPath+"目录成功");
            }else {
                System.out.println("创建"+directryPath+"目录失败");
            }
        }
    }
}
