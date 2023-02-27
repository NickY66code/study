package ip.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

/**
 * @ClassName UdpSender
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/11/2 17:30
 * @Version 1.0
 **/
public class UdpSenderDemo01 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(8888);

        //准备数据:控制台读取
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true){
            String data = reader.readLine();
            //转成数据
            byte[] datas = data.getBytes();

            DatagramPacket packet = new DatagramPacket(datas,0,datas.length,new InetSocketAddress("localhost",6666));

            //发送数据
            socket.send(packet);

            if (data.equals("bye")){
                break;
            }
        }

        //关闭资源
        reader.close();
        socket.close();
    }
}
