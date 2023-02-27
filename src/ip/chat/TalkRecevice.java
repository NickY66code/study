package ip.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @ClassName TalkRecevice
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/11/3 15:12
 * @Version 1.0
 **/
public class TalkRecevice implements Runnable {
    DatagramSocket socket=null;

    private int port;
    private String msgFrom;

    public TalkRecevice(int port,String msgFrom) {
        this.port = port;
        this.msgFrom=msgFrom;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true){

            try {
                byte[] container = new byte[1024];
                DatagramPacket receive = new DatagramPacket(container,0,container.length);
                //准备接收包裹
                socket.receive(receive);//阻塞式接收包裹
                //断开连接
                byte[] data=receive.getData();
                String s = new String(data,0,data.length);
                s.trim();
                System.out.println(msgFrom+": "+s);
                if (s.trim().equals("bye")){
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}
