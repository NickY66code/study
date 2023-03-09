

# 1.进程和线程

- **进程：**一个程序，QQ.exe,Music.exe,jar
  - 一个进程往往可以包含多个线程，至少包含一个
  - Java默认有两个线程。MAIN GC
- **线程：**开了一个Typora进程，当前在写字（一个线程），默认自动保存（一个线程）

Thread，Runnable，Callable

**Java可以开启线程吗？ **

不可以

```java
public synchronized void start() {
        /**
         * This method is not invoked for the main method thread or "system"
         * group threads created/set up by the VM. Any new functionality added
         * to this method in the future may have to also be added to the VM.
         *
         * A zero status value corresponds to state "NEW".
         */
        if (threadStatus != 0)
            throw new IllegalThreadStateException();

        /* Notify the group that this thread is about to be started
         * so that it can be added to the group's list of threads
         * and the group's unstarted count can be decremented. */
        group.add(this);

        boolean started = false;
        try {
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
                /* do nothing. If start0 threw a Throwable then
                  it will be passed up the call stack */
            }
        }
    }
	//本地方法 调用底层c++ Java无法操作硬件
    private native void start0();
```

## 1.1 并发和并行

- 并发（多个线程操作一个资源）
  - CPU 一核，模拟出来多条线程，天下武功唯快不破，快速交替
- 并行（多个人一起行走）
  - CPU 多核，多个线程一起执行

并发编程的本质：**充分利用CPU的资源**

## 1.2 线程的几种状态

```java
public enum State {
        /**
         * Thread state for a thread which has not yet started.
         */
    	// 创建
        NEW,

        /**
         * Thread state for a runnable thread.  A thread in the runnable
         * state is executing in the Java virtual machine but it may
         * be waiting for other resources from the operating system
         * such as processor.
         */
    	// 运行
        RUNNABLE,

        /**
         * Thread state for a thread blocked waiting for a monitor lock.
         * A thread in the blocked state is waiting for a monitor lock
         * to enter a synchronized block/method or
         * reenter a synchronized block/method after calling
         * {@link Object#wait() Object.wait}.
         */
    	// 阻塞
        BLOCKED,

        /**
         * Thread state for a waiting thread.
         * A thread is in the waiting state due to calling one of the
         * following methods:
         * <ul>
         *   <li>{@link Object#wait() Object.wait} with no timeout</li>
         *   <li>{@link #join() Thread.join} with no timeout</li>
         *   <li>{@link LockSupport#park() LockSupport.park}</li>
         * </ul>
         *
         * <p>A thread in the waiting state is waiting for another thread to
         * perform a particular action.
         *
         * For example, a thread that has called <tt>Object.wait()</tt>
         * on an object is waiting for another thread to call
         * <tt>Object.notify()</tt> or <tt>Object.notifyAll()</tt> on
         * that object. A thread that has called <tt>Thread.join()</tt>
         * is waiting for a specified thread to terminate.
         */
    	// 等待
        WAITING,

        /**
         * Thread state for a waiting thread with a specified waiting time.
         * A thread is in the timed waiting state due to calling one of
         * the following methods with a specified positive waiting time:
         * <ul>
         *   <li>{@link #sleep Thread.sleep}</li>
         *   <li>{@link Object#wait(long) Object.wait} with timeout</li>
         *   <li>{@link #join(long) Thread.join} with timeout</li>
         *   <li>{@link LockSupport#parkNanos LockSupport.parkNanos}</li>
         *   <li>{@link LockSupport#parkUntil LockSupport.parkUntil}</li>
         * </ul>
         */
    	// 超时等待
        TIMED_WAITING,

        /**
         * Thread state for a terminated thread.
         * The thread has completed execution.
         */
    	// 终止
        TERMINATED;
    }
```

## 1.3 wait和sleep的区别

- 类不一样
  - wait：Object类
  - sleep：Thread类
- 关于锁的释放
  - wait：会释放锁
  - sleep：抱着锁睡觉了，不会释放
- 使用的范围不一样
  - wait：必须在同步代码块中
  - sleep：可以在任何地方睡
- 是否需要捕获异常
  - wait：不需要捕获异常
  - sleep：需要捕获异常

# 2. Lock（锁）

- 传统Synchronized
- Lock

![image-20230227141943821](JUC.assets/image-20230227141943821.png)

![image-20230227142020215](JUC.assets/image-20230227142020215.png)

公平锁：十分公平，先来后到

**非公平锁：十分不公平，可以插队（默认）**

**区别**：

1. Synchronized是关键字 Lock是Java类
2. Synchronized无法判断锁的状态 Lock可以判断锁的状态  显式表现
3. Synchronized会自动释放锁 Lock必须需要手动释放。如果不释放锁会造成死锁
4. Synchronized 线程1获取锁（阻塞）线程2（等待）   Lock锁就不一定会等待下去tryLock()
5. Synchronized 可重入锁、不可以中断、非公平锁 Lock 可重入锁、可以判断锁、公平/非公平
6. Synchronized 适合锁少量的代码同步问题 Lock 适合锁大量的同步代码

# 3. 生产者和消费者问题

![image-20230227152015758](JUC.assets/image-20230227152015758.png)

### synchroized版

```java
package juc.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author noah
 * @version 1.0
 * @Description 测试Syn
 * Create by 2023/2/27 11:39
 */
public class test01 {
    public static void main(String[] args) {
        PC pc = new PC();


        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    pc.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    pc.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"B").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    pc.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"C").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    pc.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"D").start();
    }
}

class PC{
    private int num=0;

    public synchronized void incr() throws InterruptedException {
        while (num!=0){
            this.wait();
        }
        System.out.println(Thread.currentThread().getName()+num++);
        //唤醒其他线程
        this.notifyAll();
    }
    public synchronized void decr() throws InterruptedException {
        while (num==0){
            this.wait();
        }
        System.out.println(Thread.currentThread().getName()+num--);
        //唤醒其他线程
        this.notifyAll();
    }
}
```

### JUC版

public interface Condition

`Condition`因素出`Object`监视器方法（ [`wait`](../../../../java/lang/Object.html#wait--) ， [`notify`](../../../../java/lang/Object.html#notify--)和[`notifyAll`](../../../../java/lang/Object.html#notifyAll--)  ）成不同的对象，以得到具有多个等待集的每个对象，通过将它们与使用任意的组合的效果[`Lock`个](../../../../java/util/concurrent/locks/Lock.html)实现。  `Lock`替换`synchronized`方法和语句的使用，  `Condition`取代了对象监视器方法的使用。 

```java
package juc.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author noah
 * @version 1.0
 * @Description 测试Syn
 * Create by 2023/2/27 11:39
 */
public class test01 {
    public static void main(String[] args) {
        //PCSyn pc = new PCSyn();
        PCJuc pc = new PCJuc();


        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    pc.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    pc.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"B").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    pc.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"C").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    pc.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"D").start();
    }
}

public class PCJuc {
    private int num = 0;
    private final Lock lock=new ReentrantLock();
    /**
     * 相当于房间
     */
    private final Condition condition=lock.newCondition();

    public void incr() throws InterruptedException {
        lock.lock();
        try {
            while (num != 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + num++);
            //唤醒其他线程
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void decr() throws InterruptedException {
        lock.lock();
        try {
            while (num == 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + num--);
            //唤醒其他线程
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
```

Condition优点：可以让线程有序进行

```java
package juc.test;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/27 16:45
 */
public class test02 {
    public static void main(String[] args) {
        PCJuc2 pc = new PCJuc2();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                pc.a();
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                pc.b();
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                pc.c();
            }
        }).start();
    }
}
package juc.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PCJuc2 {
    private int num=1;
    private Lock lock =new ReentrantLock();
    private Condition condition1=lock.newCondition();
    private Condition condition2=lock.newCondition();
    private Condition condition3=lock.newCondition();

    public void a(){
        lock.lock();
        try {
            while (num!=1){
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName()+":=>AAAAA");
            num=2;
            condition2.signal();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void b(){
        lock.lock();
        try {
            while (num!=2){
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName()+":=>BBBBB");
            num=3;
            condition3.signal();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void c(){
        lock.lock();
        try {
            while (num!=3){
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName()+":=>CCCCC");
            num=1;
            condition1.signal();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
```

# 4. 8锁现象

如何判断锁的是谁？什么是锁，锁到底锁的是谁？

```java
package juc.test.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description 8锁问题
 * 1：标准情况下，两个线程先打印 发短信还是打电话？ 1.发短信 2.打电话
 * 2：send()延迟4s情况下，两个线程先打印 发短信还是打电话？ 1.发短信 2.打电话
 * Create by 2023/2/27 17:10
 */
public class test01 {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();

        new Thread(()->{
            try {
                phone.send();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"A").start();

        //休息1s
        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            phone.call();
        },"B").start();
    }
}

class Phone{

    /**
     * synchronized 锁的对象是方法的调用者
     * 两个方法用的是同一个锁
     * @throws InterruptedException
     */
    public synchronized void send() throws InterruptedException {
        TimeUnit.SECONDS.sleep(4);
        System.out.println(Thread.currentThread().getName()+"发短信");
    }

    public synchronized void call(){
        System.out.println(Thread.currentThread().getName()+"打电话");
    }
}
```

```java
 package juc.test.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description 8锁问题 （2）
 * 3：增加了普通方法hello()后，两个线程先打印 发短信还是hello？ 1.hello 2.发短信
 * 4：两个对象，两个同步方法，两个线程先打印 发短信还是打电话？ 1.打电话 2.发短信
 * Create by 2023/2/27 17:10
 */
public class test02 {
    public static void main(String[] args) throws InterruptedException {
        //两个不同的对象，因此有两个不同的锁
        Phone2 phone = new Phone2();
        Phone2 phone2 = new Phone2();

        new Thread(()->{
            try {
                phone.send();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"A").start();

        //休息1s
        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            phone2.call();
        },"B").start();
    }
}

class Phone2{

    /**
     * synchronized 锁的对象是方法的调用者
     * 两个方法用的是同一个锁
     * @throws InterruptedException
     */
    public synchronized void send() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.currentThread().getName()+"发短信");
    }

    public synchronized void call(){
        System.out.println(Thread.currentThread().getName()+"打电话");
    }

    /**
     * 无锁，并不是同步方法，不受锁的影响
     */
    public void hello(){
        System.out.println(Thread.currentThread().getName()+"hello");
    }
}
```

```java
package juc.test.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description 8锁问题 （3）
 * 5：增加两个静态同步方法后，只有一个对象，两个线程先打印 发短信还是打电话？ 1.发短信 2.打电话
 * 6：增加两个静态同步方法后，有两个对象，两个线程先打印 发短信还是打电话？ 1.发短信 2.打电话
 * Create by 2023/2/27 17:10
 */
public class test03 {
    public static void main(String[] args) throws InterruptedException {
        //两个对象 只有一个Class模板
        Phone3 phone = new Phone3();
        Phone3 phone2 = new Phone3();

        new Thread(()->{
            try {
                phone.send();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"A").start();

        //休息1s
        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            phone2.call();
        },"B").start();
    }
}

class Phone3{

    /**
     * synchronized 锁的对象是方法的调用者
     * 两个方法用的是同一个锁
     * static 静态方法 当类一加载就有了！锁的是Class
     * @throws InterruptedException
     */
    public static synchronized void send() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.currentThread().getName()+"发短信");
    }

    public static synchronized void call(){
        System.out.println(Thread.currentThread().getName()+"打电话");
    }

}
```

```java
package juc.test.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description 8锁问题 （4）
 * 7：增加一个静态同步方法，一个普通同步后，只有一个对象，两个线程先打印 发短信还是打电话？ 1.打电话 2.发短信
 * 8：增加一个静态同步方法，一个普通同步后，有两个对象，两个线程先打印 发短信还是打电话？ 1.发短信 2.打电话
 * Create by 2023/2/27 17:10
 */
public class test04 {
    public static void main(String[] args) throws InterruptedException {
        //两个对象 只有一个Class模板
        Phone4 phone = new Phone4();
        Phone4 phone2 = new Phone4();

        new Thread(()->{
            try {
                phone.send();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"A").start();

        //休息1s
        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            phone2.call();
        },"B").start();
    }
}

class Phone4{

    /**
     * 静态同步方法 锁的是Class模板
     */
    public static synchronized void send() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.currentThread().getName()+"发短信");
    }

    /**
     * 普通同步方法 锁的是对象方法
     */
    public synchronized void call(){
        System.out.println(Thread.currentThread().getName()+"打电话");
    }

}
```

**小结**

new this 具体的一个手机

static Class 唯一的一个 模板

# 5. 集合类不安全

## 5.1 List不安全

```java
package juc.test.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author noah
 * @version 1.0
 * @Description 测试线程不安全
 * Create by 2023/2/28 10:03
 */
public class ListTest {
    /**
     * java.util.ConcurrentModificationException 并发修改异常
     * @param args
     */
    public static void main(String[] args) {
        /**
         * 解决方案：
         * 1.List<String> list = new Vector<>();
         * 2.List<String> list = Collections.synchronizedList(new ArrayList<>());
         * 3.List<String> list = new CopyOnWriteArrayList(); JUC 写入时复制，COW 计算机领域的一种优化策略
         * 在多线程调用List的时候，读取时是固定的，写入的时候避免覆盖，造成数据问题
         * CopyOnWriteArrayList 相较于 Vector lion给lock锁效率大于synchronized
         */
        //List<String> list = new ArrayList<>();
        //List<String> list = new Vector<>();
        //List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList();

        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
```

## 5.2 Set不安全

```java
package juc.test.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author noah
 * @version 1.0
 * @Description Set集合不安全
 * Create by 2023/2/28 10:32
 */
public class SetTest {
    public static void main(String[] args) {
        /**
         * 解决方案：
         * 1.Set<String> set = Collections.synchronizedSet(new HashSet<>());
         * 2.Set<String> set = new CopyOnWriteArraySet();
         */
        //Set<String> set = new HashSet<>();
        //Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet();

        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }
}
```

HashSet的是什么？

```java
public HashSet() {
    map = new HashMap<>();
}

public boolean add(E e) {
    return map.put(e, PRESENT)==null; //key是唯一的
}

private static final Object PRESENT = new Object();
```

## 5.3 Map不安全

```java
package juc.test.unsafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author noah
 * @version 1.0
 * @Description Map集合不安全
 * Create by 2023/2/28 10:45
 */
public class MapTest {
    public static void main(String[] args) {
        /**
         * 解决方案：
         * 1.Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
         * 2.Map<String, String> map = new ConcurrentHashMap<>();
         * 主要利用了CAS 加 volatile 或者 synchronized 的方式来保证线程安全。
         * 通过对头结点加锁来保证线程安全的。
         */
        //Map<String, String> map = new HashMap<>();
        //Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        Map<String, String> map = new ConcurrentHashMap<>();

        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,5));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}
```

# 6. Callable

![image-20230228145345171](JUC.assets/image-20230228145345171.png)

1. 可以有返回值
2. 可以抛出异常
3. 方法不同，run()/call()

```java
package juc.test.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author noah
 * @version 1.0
 * @Description 测试callable<>
 * Create by 2023/2/28 15:05
 */
public class callableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread myThread = new MyThread();
        FutureTask<String> futureTask = new FutureTask<>(myThread);
        new Thread(futureTask).start();
        new Thread(futureTask).start();//结果会被缓存，效率高
        if (!futureTask.isDone()){
            System.out.println(futureTask.get()); //可能会产生阻塞
        }
    }
}

class MyThread implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("calling");
        return "hello world";
    }
}
```

# 7. 常用辅助类

## 7.1 CountDownLatch

```java
package juc.test.auxiliary;

import java.util.concurrent.CountDownLatch;

/**
 * @author noah
 * @version 1.0
 * @Description CountDownLatch 减法计数器
 * Create by 2023/2/28 15:21
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+" go out");
                countDownLatch.countDown(); //数量-1
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();//待所有计数清零才执行后面代码
        System.out.println("close door");
    }
}

```



## 7.2 CyclicBarrier

```java
package juc.test.auxiliary;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author noah
 * @version 1.0
 * @Description CyclicBarrier 加法计数器
 * Create by 2023/2/28 15:31
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        /**
         * 集齐龙珠召唤神龙
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("召唤神龙成功！");
        });
        for (int i = 1; i <= 7; i++) {
            new Thread(()->{
                System.out.println("收集到"+Thread.currentThread().getName());
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            },"龙珠"+i).start();
        }
    }
}

```



## 7.3 Semaphore(信号量)

```java
package juc.test.auxiliary;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description Semaphore
 * Create by 2023/2/28 16:05
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        //permits 线程数量 停车位 限流
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire(); //得到
                    System.out.println(Thread.currentThread().getName()+"得到车位");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName()+"离开车位");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    semaphore.release();//释放
                }
            },String.valueOf(i)).start();
        }
    }
}
```

# 8. ReadWriteLock

```java
package juc.test.rw;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author noah
 * @version 1.0
 * @Description ReadWriteLock 独占锁(写锁) 共享锁(读锁)
 * Create by 2023/3/1 9:57
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {
        //MyCache myCache = new MyCache();
        MyCacheLock myCache = new MyCacheLock();

        for (int i = 1; i < 6; i++) {
            final int temp =i;
            new Thread(()->{
                myCache.put(temp+"",temp);
            },String.valueOf(i)).start();
        }

        for (int i = 1; i < 6; i++) {
            final int temp =i;
            new Thread(()->{
                myCache.get(temp+"");
            },String.valueOf(i)).start();
        }
    }
}

class MyCacheLock{
    private volatile Map<String,Object> map= new HashMap<>();
    private ReadWriteLock lock=new ReentrantReadWriteLock();
    //private Lock lock=new ReentrantLock();

    public void put(String key,Object value){
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"put begin");
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"put end");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void get(String key){
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"get begin");
            map.get(key);
            System.out.println(Thread.currentThread().getName()+"get end");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.readLock().unlock();
        }
    }
}

/**
 * 自定义缓存
 */
class MyCache{
    private volatile Map<String,Object> map= new HashMap<>();

    public void put(String key,Object value){
        System.out.println(Thread.currentThread().getName()+"put begin");
        map.put(key,value);
        System.out.println(Thread.currentThread().getName()+"put end");
    }

    public void get(String key){
        System.out.println(Thread.currentThread().getName()+"get begin");
        map.get(key);
        System.out.println(Thread.currentThread().getName()+"get end");
    }
}
```



# 9. Queue

## 9.1 ArrayBlockingQueue

![image-20230301112115394](JUC.assets/image-20230301112115394.png)

使用阻塞队列：多线程并发、线程池

**四组api**

| 方式     | 抛出异常  | 不会抛出异常，有返回值 | 阻塞等待 | 超时等待  |
| -------- | --------- | ---------------------- | -------- | --------- |
| 添加     | add()     | offer()                | put()    | offer(,,) |
| 移除     | remove()  | poll()                 | take()   | poll(,)   |
| 判断队首 | element() | prrk()                 | -        | -         |

```java
 /**
  * 抛出异常
  */
public static void test1(){
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(3);

        System.out.println(arrayBlockingQueue.add("a"));
        System.out.println(arrayBlockingQueue.add("b"));
        System.out.println(arrayBlockingQueue.add("c"));

        //Exception in thread "main" java.lang.IllegalStateException: Queue full
        //System.out.println(arrayBlockingQueue.add("d"));

        //FIFO
        System.out.println(arrayBlockingQueue.remove());
        System.out.println(arrayBlockingQueue.remove());
        System.out.println(arrayBlockingQueue.remove());

        //Exception in thread "main" java.util.NoSuchElementException
        //System.out.println(arrayBlockingQueue.remove());

    }

   /**
     * 不抛出异常
     */
    public static void test2(){
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(3);

        System.out.println(arrayBlockingQueue.offer("a"));
        System.out.println(arrayBlockingQueue.offer("b"));
        System.out.println(arrayBlockingQueue.offer("c"));

        System.out.println(arrayBlockingQueue.offer("d")); //false 不抛出异常

        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());//null 不抛出异常

    }

/**
     * 等待 一直阻塞
     */
public static void test3() throws InterruptedException {
    ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(3);

    //一直阻塞
    arrayBlockingQueue.put("a");
    arrayBlockingQueue.put("b");
    arrayBlockingQueue.put("c");
    arrayBlockingQueue.put("d"); //队列没有位置，一直阻塞

    System.out.println(arrayBlockingQueue.take());
    System.out.println(arrayBlockingQueue.take());
    System.out.println(arrayBlockingQueue.take());
}

/**
     * 等待 阻塞超时
     */
public static void test4() throws InterruptedException {
    ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(3);

    System.out.println(arrayBlockingQueue.offer("a"));
    System.out.println(arrayBlockingQueue.offer("b"));
    System.out.println(arrayBlockingQueue.offer("c"));
    System.out.println(arrayBlockingQueue.offer("d",2, TimeUnit.SECONDS));

    System.out.println(arrayBlockingQueue.poll());
    System.out.println(arrayBlockingQueue.poll());
    System.out.println(arrayBlockingQueue.poll());
    System.out.println(arrayBlockingQueue.poll(2,TimeUnit.SECONDS));
}
```

## 9.2 SynchronousQueue

```java
package juc.test.bq;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description SynchronousQueue不存储元素，即放即用
 * Create by 2023/3/1 14:49
 */
public class SynchronousQueueTest {
    public static void main(String[] args) {
        //同步队列
        BlockingQueue<String> synchronousQueue = new SynchronousQueue();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+" put 1");
                synchronousQueue.put("1");
                System.out.println(Thread.currentThread().getName()+" put 2");
                synchronousQueue.put("2");
                System.out.println(Thread.currentThread().getName()+" put 3");
                synchronousQueue.put("3");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"T1").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+" take "+synchronousQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+" take "+synchronousQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+" take "+synchronousQueue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"T2").start();
    }
}
```

# 10. 线程池

线程池：3种方法、7大参数、4种拒绝策略

程序运行的本质：占用系统资源！ 因此需要优化资源的使用=》池化技术

线程池、连接池、内存池、对象池.......(创建、销毁十分浪费资源)

池化技术：事先准备好资源，有人要用，就来我这里拿，用完之后还给我。

> 线程池的好处

1. 降低资源的小号
2. 提高响应的速度
3. 方便管理

**线程复用、控制最大并发数、管理线程**

## 10.1 三大方法

![image-20230301151505559](JUC.assets/image-20230301151505559.png)

```java
package juc.test.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author noah
 * @version 1.0
 * @Description PoolTest 3大方法
 * Create by 2023/3/1 15:53
 */
public class PoolTest {
    public static void main(String[] args) {
        //Executors 工具类、3大方法
        //单个线程
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //创建一个固定的线程池大小
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //可伸缩的，遇强则强，遇弱则弱
        ExecutorService threadPool =Executors.newCachedThreadPool();

        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+" running");
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //用完线程池，程序关闭，需要关闭线程池
            threadPool.shutdown();
        }

    }
}
```

## 10.2 七大参数

>源码分析

```java
/**
newSingleThreadExecutor
*/
public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
    }

/**
newFixedThreadPool
*/
 public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }

/**
newCachedThreadPool
*/
public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    }

//本质：ThreadPoolExecutor
public ThreadPoolExecutor(int corePoolSize, //核心线程池大小
                              int maximumPoolSize, //最大核心线程池大小
                              long keepAliveTime, //超时了没人调用就会释放
                              TimeUnit unit, //超时单位
                              BlockingQueue<Runnable> workQueue, //阻塞队列
                              ThreadFactory threadFactory, //线程工厂。创建线程的，一般不动
                              RejectedExecutionHandler handler //拒绝策略 
                         ) {
        if (corePoolSize < 0 ||
            maximumPoolSize <= 0 ||
            maximumPoolSize < corePoolSize ||
            keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.acc = System.getSecurityManager() == null ?
                null :
                AccessController.getContext();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }
```

![image-20230301161903759](JUC.assets/image-20230301161903759.png)

>手动创建线程池

```java
package juc.test.pool;

import java.util.concurrent.*;

/**
 * @author noah
 * @version 1.0
 * @Description PoolTest
 * Create by 2023/3/1 15:53
 */
public class PoolTest {
    public static void main(String[] args) {
        //Executors 工具类、3大方法
        //单个线程
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //创建一个固定的线程池大小
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //可伸缩的，遇强则强，遇弱则弱
        //ExecutorService threadPool =Executors.newCachedThreadPool();
        //自己创建线程池
        ExecutorService threadPool =new ThreadPoolExecutor(
                2, //核心线程数
                5, //最大线程数
                3, //超时3s，释放空闲线程
                TimeUnit.SECONDS, //超时单位
                new LinkedBlockingDeque<>(3), //候客区
                Executors.defaultThreadFactory(), //默认线程工厂
                //四个拒绝策略
                //new ThreadPoolExecutor.AbortPolicy() //默认拒绝策略：银行满了(业务厅+候客区) 不处理后面的人的请求，抛出异常
                //new ThreadPoolExecutor.CallerRunsPolicy() //哪里来的去哪里 返回到main running主线程执行
                //new ThreadPoolExecutor.DiscardPolicy() //队列满了，不会抛出异常,丢掉任务
                new ThreadPoolExecutor.DiscardOldestPolicy() //队列满了，尝试去和最早的竞争,竞争失败，则丢掉任务，也不会抛出异常

        );
        //最大承载值：max+queue
        //超出异常 java.util.concurrent.RejectedExecutionException
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+" running");
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //用完线程池，程序关闭，需要关闭线程池
            threadPool.shutdown();
        }

    }
}

```



## 10.3 四种拒绝策略

![image-20230301162739633](JUC.assets/image-20230301162739633.png)

```java
//四个拒绝策略
//new ThreadPoolExecutor.AbortPolicy() //默认拒绝策略：银行满了(业务厅+候客区) 不处理后面的人的请求，抛出异常
//new ThreadPoolExecutor.CallerRunsPolicy() //哪里来的去哪里 返回到main running主线程执行
//new ThreadPoolExecutor.DiscardPolicy() //队列满了，不会抛出异常,丢掉任务
new ThreadPoolExecutor.DiscardOldestPolicy() //队列满了，尝试去和最早的竞争,竞争失败，则丢掉任务，也不会抛出异常
```

## 10.4 小结

```java
//池的最大大小 如何定义？
//1.CPU密集型 (几核就是几，保证cpu效率最高) 
Runtime.getRuntime().availableProcessors()
//2.IO密集型 (判断程序中十分耗IO的线程)>2n
//如存在15个大型任务 io十分占用资源 30
```

# 11. 四大函数式接口

## 11.1 Function

![image-20230302105444908](JUC.assets/image-20230302105444908.png)

```java
package juc.test.function;

import java.util.function.Function;

/**
 * @author noah
 * @version 1.0
 * @Description FunctionTest
 * Create by 2023/3/2 10:14
 */
public class FunctionTest {
    /**
     * @FunctionalInterface
     * public interface Function<T, R> {
     * R apply(T t);
     */
    public static void main(String[] args) {
        Function<String,String> function= s-> s;
        System.out.println(function.apply("asd"));
    }
}
```

## 11.2 Predicate

![image-20230302105424060](JUC.assets/image-20230302105424060.png)

```java
package juc.test.function;

import java.util.function.Predicate;

/**
 * @author noah
 * @version 1.0
 * @Description Predicate:断定形接口，有一个输入参数，返回值只能是boolean
 * Create by 2023/3/2 10:21
 */
public class PredicateTest {
    public static void main(String[] args) {
    /**
     *  @FunctionalInterface
     *         public interface Predicate<T> {
     *
     *             boolean test(T t);
     */
    //Predicate<String> predicate =s->s.isEmpty();
    Predicate<String> predicate = String::isEmpty;
    System.out.println(predicate.test("hi"));
    }
}
```

## 11.3 Consumer

![image-20230302105252392](JUC.assets/image-20230302105252392.png)

```java
package juc.test.function;

import java.util.function.Consumer;

/**
 * @author noah
 * @version 1.0
 * @Description Consumer 消费者接口
 * Create by 2023/3/2 10:36
 */
public class ConsumerTest {
    public static void main(String[] args) {
        /**
         * @FunctionalInterface
         * public interface Consumer<T> {
         *void accept (T t);
         */
        //Consumer<String> consumer=(str)-> System.out.println(str);
        Consumer<String> consumer=System.out::println;
        consumer.accept("asd");
    }
}
```

## 11.4 Supplier

![image-20230302105938877](JUC.assets/image-20230302105938877.png)

```java
package juc.test.function;

import java.util.function.Supplier;

/**
 * @author noah
 * @version 1.0
 * @Description Supplier 供给型接口
 * Create by 2023/3/2 10:55
 */
public class SupplierTest {
    public static void main(String[] args) {
        /**
         * @FunctionalInterface
         * public interface Supplier<T> {
         *T get ();
         */
        Supplier<String> supplier=()->"hello world";
        System.out.println(supplier.get());
    }
}
```



# 12. Stream流式计算

>什么是流式计算

大数据：存储+计算

存储：集合、数据库

计算：都应该交给流操作

```java
package juc.test.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author noah
 * @version 1.0
 * @Description 测试流
 * Create by 2023/3/2 11:22
 */
public class Test {
    /**
     * 题目：
     * 现在有5个用户，要筛选一下条件
     * 1.ID必须是偶数
     * 2.年龄必须大于23岁
     * 3.用户名必须转换为大小写
     * 4.用户名字母倒着排序
     * 5.只输出一个用户
     */
    public static void main(String[] args) {
        User u1=new User("a",25,1);
        User u2=new User("b",29,2);
        User u3=new User("c",55,3);
        User u4=new User("d",33,4);
        User u5=new User("e",9,5);

        List<User> list= Arrays.asList(u1,u2,u3,u4,u5);

        list.stream()
                .filter((u)-> u.getId()%2==0 && u.getAge()>23)
                .map(u->u.getName().toUpperCase())
                .sorted(Comparator.reverseOrder())
                .limit(1)
                //.sorted(String::compareTo) 正序
                .forEach(System.out::println);
    }
}
```

# 13. ForkJoin

> 什么是ForkJoin？

ForkJoin在JDK1.7，并行执行任务！提高效率，大数据量

大数据：Map Reduce（把大任务拆分成小任务）

![image-20230302143028436](JUC.assets/image-20230302143028436.png)

>ForkJoin特点 ： 工作窃取

这里面维护的都是双端队列

![image-20230302143408230](JUC.assets/image-20230302143408230.png)

>ForkJoin操作

![image-20230302144935992](JUC.assets/image-20230302144935992.png)

```java
package juc.test.forkJoin;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.locks.Lock;

/**
 * @author noah
 * @version 1.0
 * @Description ForkJoin
 * Create by 2023/3/2 14:35
 */
public class ForkJoinTest extends RecursiveTask<Long> {
    private long start;
    private long end;

    //临界值
    private long temp=10000L;

    public ForkJoinTest(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if ((end-start)>temp){
            long middle=(start+end)/2; //中间值
            ForkJoinTest forkJoinTest1 = new ForkJoinTest(start,middle);
            forkJoinTest1.fork(); //拆分任务，把任务压入线程队列
            ForkJoinTest forkJoinTest2 = new ForkJoinTest(middle+1,end);
            forkJoinTest2.fork();
            return forkJoinTest1.join()+forkJoinTest2.join();
        }else {
            //正常代码
            long sum=0L;
            for (long i = start; i <= end; i++) {
                sum+=i;
            }
            return sum;
        }
    }
}
```

```java
package juc.test.forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * @author noah
 * @version 1.0
 * @Description 测试不同计算
 * Create by 2023/3/2 15:01
 */
public class Test {
    public static void main(String[] args) {
        //test1(); //time:6301 294
        //test2(); //time:3583 167
        test3(); //time:143
    }

    public static void test1(){
        long sum=0L;
        long start =System.currentTimeMillis();
        //正常代码
        for (long i = 1L; i <= 10_0000_0000L; i++) {
            sum+=i;
        }
        long end =System.currentTimeMillis();
        System.out.println("sum: "+sum+" 时间: " +(end-start));
    }

    public static void test2(){
        long start =System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTest task = new ForkJoinTest(0L,10_0000_0000L);
        //forkJoinPool.execute(task); //执行任务 无返回值
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);
        Long sum=0L;
        try {
             sum=submit.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        long end =System.currentTimeMillis();
        System.out.println("sum: "+sum+"时间: " +(end-start));
    }

    public static void test3(){
        long start =System.currentTimeMillis();
        //Stream并行流
        long sum = LongStream.rangeClosed(0L, 10_0000_0000L).parallel().reduce(0, Long::sum);
        long end =System.currentTimeMillis();
        System.out.println("sum: "+sum+"时间: " +(end-start));
    }
}
```

# 14. 异步回调

```java
package juc.test.future;

import java.util.Comparator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description 异步调用
 * Create by 2023/3/3 9:59
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        //发起一个请求
//        CompletableFuture<Void> completableFuture= CompletableFuture.runAsync(()->{
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println(Thread.currentThread().getName()+"=>runAsync Void");
//        });
//        System.out.println("end");
//        completableFuture.get();

        CompletableFuture<Integer> completableFuture= CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"=>supplyAsync Integer");
            //int i=10/0;
            return 200;
        });

        System.out.println(completableFuture.whenComplete((t, u) -> {
            System.out.println("t: " + t); //正常的返回结果
            System.out.println("u: " + u); //错误返回信息
        }).exceptionally((e) -> {
            e.getMessage();
            return 500; //可以获取错误的返回结果
        }).get());
    }
}
```

# 15. JMM

>请你谈谈队volatile的理解

Volatile是Java提供的**轻量级**的同步机制

1. 保证可见性（JMM）
2. 不保证原子性
3. 禁止指令重排

>什么是JMM

JMM：Java内存模型，不存在。概念、约定

关于JMM的同步约定

1. 线程解锁前，必须把共享变量**立即**刷回主存
2. 线程加锁前，必须读取主存中的最新值到工作内存中
3. 加锁和解锁是同一把锁

线程 **工作内存、主内存**

**八种操作**

![image-20230303104502003](JUC.assets/image-20230303104502003.png)

![image-20230303104847502](JUC.assets/image-20230303104847502.png)

为了支持 JMM，Java 定义了8种原子操作，用来控制主存与工作内存之间的交互：

- read 读取：作用于主内存，将共享变量从主内存传送到线程的工作内存中。

- load 载入：作用于工作内存，把 read 读取的值放到工作内存中的副本变量中。

- store 存储：作用于工作内存，把工作内存中的变量传送到主内存中。

- write 写入：作用于主内存，把从工作内存中 store 传送过来的值写到主内存的变量中

- use 使用：作用于工作内存，把工作内存的值传递给执行引擎，当虚拟机遇到一个需要使用这个变量的指令时，就会执行这个动作。

- assign 赋值：作用于工作内存，把执行引擎获取到的值赋值给工作内存中的变量，当虚拟机栈遇到给变量赋值的指令时，就执行此操作。

- lock锁定： 作用于主内存，把变量标记为线程独占状态。

- unlock解锁： 作用于主内存，它将释放独占状态。

**除此之外，Java内存模型还规定了在执行上述8种基本操作时必须满足如下规则**：

- 不允许read和load、store和write操作之一单独出现，即不允许一个变量从主内存读取了但工作内存不接受，或者工作内存发起回写了但主内存不接受的情况出现。
- 不允许一个线程丢弃它最近的assign操作，即变量在工作内存中改变了之后必须把该变化同步回主内存。
  不允许一个线程无原因地（没有发生过任何assign操作）把数据从线程的工作内存同步回主内存中。
- 一个新的变量只能在主内存中“诞生”，不允许在工作内存中直接使用一个未被初始化（load或 assign）的变量，换句话说就是对一个变量实施use、store操作之前，必须先执行assign和load操作。
- 一个变量在同一个时刻只允许一条线程对其进行lock操作，但lock操作可以被同一条线程重复执行多次，多次执行lock后，只有执行相同次数的unlock操作，变量才会被解锁。
- 如果对一个变量执行lock操作，那将会清空工作内存中此变量的值，在执行引擎使用这个变量前，需要重新执行load或assign操作以初始化变量的值。
- 如果一个变量事先没有被lock操作锁定，那就不允许对它执行unlock操作，也不允许去unlock一个被其他线程锁定的变量。
- 对一个变量执行unlock操作之前，必须先把此变量同步回主内存中（执行store、write操作）。
  

>程序不知道内存中的值给修改过了 , Thread_1仍在一直执行

```java
package juc.test.ccvolatile;

import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/3/3 10:54
 */
public class VolatileTest {
    private static int num=0;
    public static void main(String[] args) throws InterruptedException { //main
        new Thread(()->{ //Thread_1
            while (num==0){

            }
        }).start();

        TimeUnit.SECONDS.sleep(1);

        num=1;

        System.out.println(num);
    }
}
```

# 16. Volatile

>保证可见性

```java
package juc.test.ccvolatile;

import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/3/3 10:54
 */
public class VolatileTest {
    //private static int num=0;
    private volatile static int num=0;//不加volatile程序就会进入死循环
    public static void main(String[] args) throws InterruptedException { //main
        new Thread(()->{ //Thread_1
            while (num==0){ //对于主线程的更改是不知道的

            }
            System.out.println(Thread.currentThread().getName()+" end");
        }).start();

        TimeUnit.SECONDS.sleep(1);

        num=1;

        System.out.println(num);
    }
}
```

>不保证原子性

原子性：不可分割

线程A在执行任务的时候，不能被打扰，不能分割

要么同时成功、要么同时失败

```java
package juc.test.ccvolatile;

/**
 * @author noah
 * @version 1.0
 * @Description VolatileTest2：不保证原子性
 * Create by 2023/3/3 14:01
 */
public class VolatileTest2 {
    //不保证原子性
    private volatile static int num=0;

    //synchronized
    public  static void add(){
        num++;
    }

    public static void main(String[] args) {
        //理论上 num结果应该为20000
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount()>2){ //main gc
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName()+" :"+num); //main :19917
    }
}
```

**如果不加lock和synchronized，怎么解决原子性?**

![image-20230303142107155](JUC.assets/image-20230303142107155.png)

使用原子类，解决原子性问题

```java
package juc.test.ccvolatile;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author noah
 * @version 1.0
 * @Description VolatileTest2：不保证原子性
 * Create by 2023/3/3 14:01
 */
public class VolatileTest2 {
    //不保证原子性
   // private volatile static int num=0;
    private volatile static AtomicInteger num=new AtomicInteger();

    //synchronized
    public  static void add(){
        num.getAndIncrement();//+1方法 CAS
    }

    public static void main(String[] args) {
        //理论上 num结果应该为20000
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount()>2){ //main gc
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName()+" :"+num); //main :19917
    }
}
```

>原子类为什么会解决这个问题

这些类的底层都是直接和操作系统挂钩。在内存中修改值.Unsafe类，是很特殊的存在

>指令重排

什么是指令重排: 你写的程序，计算机并不是按照你写的顺序执行的

源代码-> 编译器优化重排->指令并行可能会重排->内存系统也会重排->  执行

处理器在进行指令重排的时候，**考虑：数据之间的依赖性！**

```java
int x =1; //1
int x=2;  //2
x = x+5;  //3
y=x*x;    //4

我所期望的:1234   但是执行的时候可能是2134 2134 
    不可能4123!
```

可能造成影响的结果 ：a b x y 这四个值默认都是0

![image-20230303143753071](JUC.assets/image-20230303143753071.png)

正常结果 x=0；y=0；但是可能指令重排

![image-20230303143937179](JUC.assets/image-20230303143937179.png)

指令重排导致的异常结果：x=2；y=1；

**volatile可以避免指令重排**

内存屏障。CPU指令。作用：

1. 保证特定的操作的执行顺序
2. 可以保证某些变量的内存可见性（利用这些特性 volatile）实现了可见性

![image-20230303145530088](JUC.assets/image-20230303145530088.png)





# 17. 彻底玩转单例模式

> 饿汉式 

```java
package juc.test.single;

/**
 * @author noah
 * @version 1.0
 * @Description 饿汉式单例
 * Create by 2023/3/8 10:52
 */
public class Hungry {

    //可能会浪费空间
    private byte[] data1=new byte[1024*1024];
    private byte[] data2=new byte[1024*1024];
    private byte[] data3=new byte[1024*1024];
    private byte[] data4=new byte[1024*1024];

    private Hungry(){

    }

    private final static Hungry HUNGRY=new Hungry();

    public static Hungry getInstance(){
        return HUNGRY;
    }

}
```



懒汉式->DCL懒汉式

```java
package juc.test.single;

import java.lang.reflect.Constructor;

/**
 * @author noah
 * @version 1.0
 * @Description 懒汉
 * Create by 2023/3/8 10:56
 */
public class Lazy {

    private static boolean flag=false; //加密判断符

    private Lazy(){

        synchronized (Lazy.class){
            if (flag==false){
                flag=true;
            }else {
                throw new RuntimeException("不要试图使用反射破坏异常"); //解决都是反射创建的问题
            }
//            if (lazy!=null){
//                throw new RuntimeException("不要试图使用反射破坏异常"); //用的是同一个类锁
//            }
        }
        //System.out.println(Thread.currentThread().getName());
    }

    private volatile static Lazy lazy;

    //双重检测锁模式 懒汉式单例 DCL
    public static Lazy getInstance(){
        if (lazy==null){
            synchronized (Lazy.class){
                if (lazy == null) {
                    lazy = new Lazy(); //不是原子性操作
                    /**
                     * 1.分配内存空间
                     * 2.执行构造方法，初始化对象
                     * 3.把这个对象指向这个空间
                     *
                     * 123
                     * 132 A
                     *     B //此时lazy还没完成构造
                     */
                }
            }
        }
        return lazy;
    }

    //单线程下没问题
    //多线程并发
    public static void main(String[] args) throws Exception {
//        for (int i = 0; i < 10; i++) {
//            new Thread(()->{
//                Lazy.getInstance();
//            }).start();
//        }
       // Lazy lazy1 = Lazy.getInstance();

        Constructor<Lazy> declaredConstructor = Lazy.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        Lazy lazy1 = declaredConstructor.newInstance();
        Lazy lazy2 = declaredConstructor.newInstance();
        System.out.println(lazy1.hashCode());
        System.out.println(lazy2.hashCode());
    }
}
```



> 静态内部类

```java
package juc.test.single;

/**
 * @author noah
 * @version 1.0
 * @Description 静态内部类
 * Create by 2023/3/8 11:09
 */
public class Holder {
    private Holder(){

    }

    public static Holder getInstance(){
        return InnerClass.HOLDER;
    }

    public static class InnerClass{
        private static final Holder HOLDER = new Holder();
    }
}
```

**单例不安全，可以利用反射破解**

> 枚举单例

```java
package juc.test.single;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import java.lang.reflect.Constructor;

/**
 * @author noah
 * @version 1.0
 * @Description 枚举单例
 * Create by 2023/3/8 11:33
 */
public enum EnumSingle {
    /**
     * 枚举单例
     */
    INSTANCE;

    public EnumSingle getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) throws Exception {
        EnumSingle instance1 = EnumSingle.INSTANCE;
        Constructor<EnumSingle> instance2 = EnumSingle.class.getDeclaredConstructor(String.class,int.class);
        instance2.setAccessible(true);
        instance2.newInstance();

        System.out.println(instance1);
        System.out.println(instance2);//IllegalArgumentException: Cannot reflectively create enum objects
    }
}
```

![image-20230308115107300](JUC.assets/image-20230308115107300.png)

是有参构造，非无参

![image-20230308115200263](JUC.assets/image-20230308115200263.png)

# 18. 深入理解CAS

> 什么是CAS

```java
package juc.test.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author noah
 * @version 1.0
 * @Description cas:比较并交换
 * Create by 2023/3/8 15:27
 */
public class CasTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(2023);
        //public final boolean compareAndSet(int expect, int update)
        // CAS 是CPU的并发原语
        atomicInteger.compareAndSet(2023,2024);
        System.out.println(atomicInteger.get());
    }
}
```

> Unsafe类

![image-20230308154040376](JUC.assets/image-20230308154040376.png)

![image-20230308160158823](JUC.assets/image-20230308160158823.png)

CAS:比较当前工作内存中的值和主内存中的值，如果这个值是期望的，那么则执行操作，如果不是则一直循环！

缺点：

1. 循环会耗时
2. 一次性只能保证一个共享变量的原子性
3. 存在ABA问题

>CAS:ABA问题（狸猫换太子）

![image-20230308161159837](JUC.assets/image-20230308161159837.png)

# 19. 各种锁的理解

## 19.1 公平锁、非公平锁

公平锁：非常公平，不能插队，必须先来后到！

非公平锁：非常不公平，可以插队（默认 ）

```java
/** 默认 */
public ReentrantLock() {
    sync = new NonfairSync();
}
/** 公平锁 */
public ReentrantLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
    }
```



## 19.2 可重入锁

> synchroized

```java
package juc.test.lock;

/**
 * @author noah
 * @version 1.0
 * @Description 可重入锁 synchronized
 * Create by 2023/3/9 9:53
 */
public class LockTest01 {
    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(()->{
            phone.sms();
        },"A").start();

        new Thread(()->{
            phone.sms();
        },"B").start();
    }
}


/**
 * synchronized 结果：ASMS ACALL BSMS BCALL
 */

class Phone{
    public synchronized void sms(){
        System.out.println(Thread.currentThread().getName()+"SMS");
        call();//这里也有锁
    }

    public synchronized void call(){
        System.out.println(Thread.currentThread().getName()+"CALL");

    }
}
```

> lock

```java
package juc.test.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author noah
 * @version 1.0
 * @Description 可重入锁 Lock
 * Create by 2023/3/9 10:02
 */
public class LockTest02 {
    public static void main(String[] args) {
        Phone02 phone = new Phone02();

        new Thread(()->{
            phone.sms();
        },"A").start();

        new Thread(()->{
            phone.sms();
        },"B").start();
    }
}


/**
 * synchronized 结果：ASMS ACALL BSMS BCALL
 */

class Phone02{

    Lock lock=new ReentrantLock();

    public void sms(){
        lock.lock();
        //lock.lock(); //lock锁必须配对。否则会死在里面
        try {
            System.out.println(Thread.currentThread().getName()+"SMS");
            call();//这里也有锁
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void call(){
        lock.lock();

        try {
            System.out.println(Thread.currentThread().getName()+"CALL");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }
}
```



## 19.3 自旋锁

![image-20230309102106016](JUC.assets/image-20230309102106016.png)

> 自定义自旋锁

```java
package juc.test.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author noah
 * @version 1.0
 * @Description 自旋锁
 * Create by 2023/3/9 10:21
 */
public class SpinLockTest {
    /**
     * int 0
     * Thread null
     */
    AtomicReference<Thread> atomicReference=new AtomicReference<>();

    /**
     * 加锁
     */
    public void lock(){
        Thread thread=Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"==> myLock");

        //自旋锁
        while (!atomicReference.compareAndSet(null,thread)){

        }
    }

    /**
     * 解锁
     */
    public void unlock(){
        Thread thread=Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"==> myUnlock");
        atomicReference.compareAndSet(thread,null);
    }

    public static void main(String[] args) throws InterruptedException {
        //自旋锁
        SpinLockTest spinLockTest = new SpinLockTest();

        new Thread(()->{
            spinLockTest.lock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                spinLockTest.unlock();
            }
        },"T1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            spinLockTest.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                spinLockTest.unlock();
            }
        },"T2").start();

    }
}
```



## 19.4 死锁

![image-20230309105737847](JUC.assets/image-20230309105737847.png)

> 死锁问题

```java
package juc.test.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description 死锁
 * Create by 2023/3/9 11:04
 */
public class DeadLockTest01 {
    public static void main(String[] args) {

        String lockA="a";
        String lockB="b";

        new Thread(new MyThread(lockA,lockB),"T1").start();
        new Thread(new MyThread(lockB,lockA),"T2").start();
    }
}

class MyThread implements Runnable{
    private String lock01;
    private String lock02;

    public MyThread(String lock01, String lock02) {
        this.lock01 = lock01;
        this.lock02 = lock02;
    }

    @Override
    public void run() {
        synchronized (lock01){
            System.out.println(Thread.currentThread().getName()+" lock" +lock01+"=>get "+lock02);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock02){
                System.out.println(Thread.currentThread().getName()+" lock" +lock02+"=>get "+lock01);
            }
        }
    }
}
```

> 解决

1.使用`jps -l`定位进程号

![image-20230309111336579](JUC.assets/image-20230309111336579.png)

2.使用`jstack `进程号 找到死锁

![image-20230309111647935](JUC.assets/image-20230309111647935.png)

![image-20230309111714350](JUC.assets/image-20230309111714350.png)
