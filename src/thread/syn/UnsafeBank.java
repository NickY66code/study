package thread.syn;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/14 15:29
 */
public class UnsafeBank {

    public static void main(String[] args) {
        //账户
        Account account=new Account(300,"结婚基金");

        Bank noah = new Bank(account, 200, "noah");
        Bank girl = new Bank(account, 200, "girl");

        noah.start();
        girl.start();
    }
}

/**
 * 账户
 */
class Account{
    /**
     * 余额
     */
    int money;

    /**
     * 卡名
     */
    String name;

    public Account(int money, String name) {
        this.money = money;
        this.name = name;
    }
}

/**
 * 银行
 */
class Bank extends Thread{

    /**
     * 账户
     */
    Account account;

    /**
     * 取了多少钱
     */
    int drawingMoney;

    /**
     * 现在手里有多少钱
     */
    int nowMoney;

    public Bank(Account account, int drawingMoney, String name) {
        super(name);
        this.account = account;
        this.drawingMoney = drawingMoney;
        this.nowMoney = nowMoney;
    }

    /**
    取钱 synchronized主要锁的是this
     */
    @Override
    public void run(){

        //锁的量 必须锁变化的量 默认是锁this 也就是Bank
        //同步监视器
        synchronized (account){
            if (account.money-drawingMoney<0){
                System.out.println(this.getName()+"余额不足了");
                return;
            }

            //放大问题的发生性
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //取钱
            account.money=account.money-drawingMoney;
            System.out.println(account.name +"余额为"+account.money);

            nowMoney=account.money+drawingMoney;
            System.out.println(this.getName()+"手里余额为："+nowMoney);
        }
    }

}