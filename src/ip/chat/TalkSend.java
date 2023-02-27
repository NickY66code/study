package ip.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * @ClassName TalkSend
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/11/3 15:01
 * @Version 1.0
 **/
public class TalkSend implements Runnable {

    DatagramSocket socket=null;
    BufferedReader reader=null;

    private int formPort;
    private String toIp;
    private int toPort;

    public TalkSend(int formPort, String toIp, int toPort)  {
        this.formPort = formPort;
        this.toIp = toIp;
        this.toPort = toPort;

        try {
            socket = new DatagramSocket(formPort);
            //准备数据:控制台读取
            reader = new BufferedReader(new InputStreamReader(System.in));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                String data = reader.readLine();
                //转成数据
                byte[] datas = data.getBytes();
                DatagramPacket packet = new DatagramPacket(datas,0,datas.length,new InetSocketAddress(this.toIp,this.toPort));
                //发送数据
                socket.send(packet);
                if (data.equals("bye")){
                    break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //关闭资源

        socket.close();
    }
}
