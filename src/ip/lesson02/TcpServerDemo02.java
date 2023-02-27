package ip.lesson02;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName TcpServerDemo02
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/11/1 10:27
 * @Version 1.0
 **/
public class TcpServerDemo02 {
    public static void main(String[] args) throws Exception{
        //1.创建服务
        ServerSocket serverSocket = new ServerSocket(9000);
        //2.监听客户端的连接
        Socket socket = serverSocket.accept();//阻塞式连接，会一直等待客户端连接
        //3.获取输入流
        InputStream is=socket.getInputStream();
        //4.文件输出
        FileOutputStream fos=new FileOutputStream(new File("receive.jpg"));
        byte[] buffer = new byte[1024];
        int len;
        while((len=is.read(buffer))!=-1){
            fos.write(buffer,0,len);
        }

        //通知客户端我接收完毕
        OutputStream os=socket.getOutputStream();
        os.write("我已接收完毕,可以断开了".getBytes());

        //关闭资源
        os.close();
        fos.close();
        is.close();
        socket.close();
        serverSocket.close();
    }

}
