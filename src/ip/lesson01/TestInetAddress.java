package ip.lesson01;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName TestInetAddress
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/10/31 14:46
 * @Version 1.0
 **/
public class TestInetAddress {
    public static void main(String[] args) throws UnknownHostException {
        //查询本机地址
        InetAddress  inetAddress=InetAddress.getByName("127.0.0.1");
        System.out.println(inetAddress);
        InetAddress  inetAddress3=InetAddress.getByName("localhost");
        System.out.println(inetAddress3);
        InetAddress  inetAddress4=InetAddress.getLocalHost();
        System.out.println(inetAddress4);

        //查询网站ip地址
        InetAddress  inetAddress2=InetAddress.getByName("www.baidu.com");
        System.out.println(inetAddress2);

        //查用方法
        //System.out.println(inetAddress2.getAddress());
        System.out.println(inetAddress2.getCanonicalHostName());//规范名字
        System.out.println(inetAddress2.getHostAddress());//ip
        System.out.println(inetAddress2.getHostName());//域名或者自己主机的名字
    }
}
