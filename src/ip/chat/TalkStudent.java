package ip.chat;

/**
 * @ClassName TalkStudent
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/11/3 15:20
 * @Version 1.0
 **/
public class TalkStudent {
    public static void main(String[] args) {
        //开启两个线程
        new Thread(new TalkSend(7777,"localhost",9999)).start();
        new Thread(new TalkRecevice(8888,"小明")).start();
    }

}
