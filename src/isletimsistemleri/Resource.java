package isletimsistemleri;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Resource {

    private int available;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public Resource(int available) {
        this.available = available;
    }

    public void lock() throws InterruptedException {
        lock.lock();
        try {
            while (available <= 0) {
                condition.await();
            }
            available--;
        } finally {
            lock.unlock();
        }
    }

    public void unlock() {
        lock.lock();
        try {
            available++;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public boolean isAvailable() {
        return available > 0;
    }
}