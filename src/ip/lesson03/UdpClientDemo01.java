package ip.lesson03;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @ClassName UdpClient
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/11/2 17:00
 * @Version 1.0
 **/
public class UdpClientDemo01 {
    public static void main(String[] args) throws Exception{
        //1.建立一个socket
        DatagramSocket socket = new DatagramSocket();
        //2.建个包
        String msg="hello,nick!";

        //发送给谁
        InetAddress localhost = InetAddress.getByName("localhost");
        int port=9090;

        //数据，数据长度，发送给谁
        DatagramPacket packet = new DatagramPacket(msg.getBytes(),0,msg.getBytes().length,localhost,port);

        //3.发个包
        socket.send(packet);

        //4.关闭流
        socket.close();
    }
}
