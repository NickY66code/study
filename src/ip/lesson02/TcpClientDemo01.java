package ip.lesson02;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @ClassName TcpClientDemo01
 * @Description 客户端
 * @Author haiqiang.yang
 * @Date 2022/10/31 16:36
 * @Version 1.0
 **/
public class TcpClientDemo01 {
    public static void main(String[] args) {
        InetAddress inetAddress=null;
        Socket socket=null;
        OutputStream os=null;
        try {
            //1.要知道客户端地址
            inetAddress = InetAddress.getByName("127.0.0.1");
            //2.端口号
            int port =9999;
            //3.创建一个socket连接
            socket = new Socket(inetAddress,port);
            //3.发送消息IO流
            os = socket.getOutputStream();

            os.write("你好，欢迎".getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
           if (os!=null){
               try {
                   os.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           if (socket!=null){
               try {
                   socket.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }
    }
}
