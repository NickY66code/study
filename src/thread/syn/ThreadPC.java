package thread.syn;

import javafx.scene.CacheHint;

/**
 * @author noah
 * @version 1.0
 * @Description 生产者消费者模型-->利用缓冲区解决：管程法
 * 生产者 消费者 产品 缓冲区
 * Create by 2023/2/17 14:53
 */
public class ThreadPC {

    public static void main(String[] args) {
        SynContainer container=new SynContainer();

        new Product(container).start();
        new Consumer(container).start();
    }
}

/**
 * 生产者
 */
class Product extends Thread{
    SynContainer container;

    public Product(SynContainer container){
        this.container=container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("生产了"+i+"只鸡");
            container.push(new Chicken(i));
        }
    }
}

/**
 * 消费者
 */
class Consumer extends Thread{
    SynContainer container;

    public Consumer(SynContainer container){
        this.container=container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("消费了"+container.pop().id+"只鸡");
        }
    }
}

/**
 * 产品
 */
class Chicken {
    int id;//产品编号

    public Chicken(int id) {
        this.id = id;
    }
}

/**
 * 缓冲区
 */
class SynContainer{
    /**
     * 容器的大小
     */
    Chicken[] chickens=new Chicken[10];

    /**
     * 容器计数器
     */
    int count=0;

    /**
     * 生产者放入产品
     */
    public synchronized void push(Chicken chicken){
        //如果容器满了就需要等待消费
        if (count==chickens.length){
            //通知消费者消费，生产等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //如果没有满则需要丢入产品
        chickens[count]=chicken;
        count++;

        //可以通知消费者消费了
        this.notifyAll();
    }

    /**
     * 消费者消费产品
     */
    public synchronized Chicken pop(){
        //判断能否消费
        if (count==0){
            //等待生产者生产，消费者等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //如果可以消费
        count--;
        Chicken chicken = chickens[count];
        this.notifyAll();

        //吃完了通知生产者生产
        return chicken;

    }
}