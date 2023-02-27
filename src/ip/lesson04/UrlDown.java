package ip.lesson04;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @ClassName UrlDown
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/11/3 16:55
 * @Version 1.0
 **/
public class UrlDown {
    public static void main(String[] args) throws Exception {
        //下载地址
        URL url = new URL("http://localhost:8080/hello/hello.txt");

        //连接到这个资源
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        InputStream inputStream=urlConnection.getInputStream();

        FileOutputStream fileOutputStream=new FileOutputStream("hello.txt");

        byte[] buffer=new byte[1024];
        int len;
        while ((len=inputStream.read(buffer))!=-1){
            fileOutputStream.write(buffer,0,len);
        }

        fileOutputStream.close();
        inputStream.close();
        urlConnection.disconnect();
    }
}
