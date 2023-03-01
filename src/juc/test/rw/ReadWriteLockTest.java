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
