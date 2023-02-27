package ip.lesson01;

import java.net.InetSocketAddress;

/**
 * @ClassName TestInetSocketAddress
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/10/31 16:09
 * @Version 1.0
 **/
public class TestInetSocketAddress {
    public static void main(String[] args) {
       InetSocketAddress inetSocketAddress= new InetSocketAddress("127.0.0.1",8080);
        System.out.println(inetSocketAddress);
    }
}
