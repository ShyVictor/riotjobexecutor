package br.devshy.jobexecutor.service;

import br.devshy.jobexecutor.domain.RiotGiftJob;
import org.springframework.stereotype.Service;

import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class RiotGiftJobExecutor {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private PriorityQueue<RiotGiftJob> queue = new PriorityQueue<>();

    public void addJob(RiotGiftJob job) {
        lock.lock();
        try {
            queue.add(job);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void run() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    condition.await();
                }
                RiotGiftJob nextJob = queue.peek();
                if (nextJob.canExecute()) {
                    nextJob.execute();
                    queue.poll();
                } else {
                    condition.await(nextJob.getTimestamp() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
