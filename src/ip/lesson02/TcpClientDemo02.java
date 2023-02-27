package ip.lesson02;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @ClassName TcpClientDemo02
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/11/1 10:15
 * @Version 1.0
 **/
public class TcpClientDemo02 {
    public static void main(String[] args) throws Exception {
        //1.创建一个socket连接
        Socket socket=new Socket(InetAddress.getByName("127.0.0.1"),9000);
        //2.创建一个输出流
        OutputStream os = socket.getOutputStream();
        //3.文件流
        FileInputStream fis = new FileInputStream(new File("1.jpg"));

        //4.写出文件
        byte[] buffer=new byte[1024];
        int len;
        while ((len=fis.read(buffer))!=-1){
            os.write(buffer,0,len);
        }

        //通知服务器，我已经结束
        socket.shutdownOutput();

        //确定服务器接收完毕，才能够断开连接
        InputStream is = socket.getInputStream();
        //String byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer2 = new byte[1024];
        int len2;
        while ((len2=is.read(buffer2))!=-1){
            baos.write(buffer2,0,len2);
        }
        System.out.println(baos.toString());

        //5.关闭资源
        baos.close();
        is.close();
        fis.close();
        os.close();
        socket.close();

    }
}
