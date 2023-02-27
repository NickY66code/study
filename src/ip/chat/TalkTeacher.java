package ip.chat;

/**
 * @ClassName TalkTeacher
 * @Description TODO
 * @Author haiqiang.yang
 * @Date 2022/11/3 15:24
 * @Version 1.0
 **/
public class TalkTeacher {
    public static void main(String[] args) {
        new Thread(new TalkSend(5555,"localhost",8888)).start();
        new Thread(new TalkRecevice(9999,"小航")).start();
    }
}
