package ip.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @ClassName UdpReceiveDemo01
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/11/2 17:31
 * @Version 1.0
 **/
public class UdpReceiveDemo01 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(6666);

        while (true){
            byte[] container = new byte[1024];
            DatagramPacket receive = new DatagramPacket(container,0,container.length);
            //准备接收包裹
            socket.receive(receive);//阻塞式接收包裹

            //断开连接
            byte[] data=receive.getData();
            System.out.println(data.length);
            String s = new String(data,0,data.length);
            s.trim();
            System.out.println(s);
            if (s.trim().equals("bye")){
                break;
            }
        }

        socket.close();

    }
}
