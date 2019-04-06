package pri.lr.Utils;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CommondMessageQueue<T> {
    private Object[]   messages;
    // 添加的下标，删除的下标和数组当前数量
    private int addIndex;
    private int removeIndex;
    private int count;
    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public volatile AtomicBoolean refuse = new AtomicBoolean(false);

    int DEFAULT_SIZE = 10;
    int MAX_SIZE = Integer.MAX_VALUE;

    public CommondMessageQueue() {
        messages = new Object[DEFAULT_SIZE];
    }

    public CommondMessageQueue(int size) {
        messages = new Object[size > MAX_SIZE ? MAX_SIZE : size];
    }


    public void add(T t) throws InterruptedException {
        if(refuse.get()){
            return;
        }
        lock.lock();
        try {
            while (count == messages.length)
                notFull.await();
            messages[addIndex] = t;
            if (++addIndex == messages.length)
                addIndex = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
    public T remove() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            Object x = messages[removeIndex];
            if (++removeIndex == messages.length)
                removeIndex = 0;
            --count;
            notFull.signal();
            return (T) x;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty(){
        return count == 0;
    }

    public void refuseAll(){
        refuse.set(true);
    }
}

