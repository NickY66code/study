package io.inputStream;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @ClassName FileInputStream_
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/11/7 13:49
 * @Version 1.0
 **/
public class FileInputStream_ {
    public static void main(String[] args) {

    }

    @Test
    public void readFile01() {
        String filePath="e:\\hello.txt";
        int read=0;
        try {
            //创建fileInputStream对象，用于读取文件
            FileInputStream fileInputStream = new FileInputStream(filePath);
            //从该输入读取一个字节的数据。如果没有输入可用，此方法阻止
            //如果返回-1，表示读取完毕
            while((read=fileInputStream.read())!=-1){

            };
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
