package hello;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SetWtableThreadPool {

    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(50000);
    private ThreadPoolExecutor te =
            new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()*2,
                    Runtime.getRuntime().availableProcessors() * 2, 0L, TimeUnit.MICROSECONDS,
                    queue, new ThreadFactory() {
                        private final AtomicInteger mCount = new AtomicInteger(1);

                        @Override
                        public Thread newThread(Runnable r) {
                            return new Thread(r, "setWtable-" + mCount.getAndIncrement());
                        }
                    });

    private static SetWtableThreadPool instance = new SetWtableThreadPool();

    private SetWtableThreadPool() {
        te.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static SetWtableThreadPool getInstance() {
        return instance;
    }

    /**
     * 加入线程池
     * 
     * @param run
     */
    public void addTask(Runnable run) {
        // executor.execute(run);
        te.execute(run);
    }

    /**
     * 返回理想情况下（没有内存和资源约束）此队列可接受并且不会被阻塞的附加元素数量。
     * 
     * @return
     */
    public int getRemainingCapacity() {
        return queue.remainingCapacity();
    }

    public String getRunStatus() {
        StringBuffer str = new StringBuffer();
        str.append("setWtableThreadPool queue size:").append(queue.size()).append(",queue remaining:")
                .append(queue.remainingCapacity()).append(",active count:")
                .append(te.getActiveCount());
        return str.toString();
    }
    
    /**
     * 返回队列中的元素个数。
     * 
     * @return
     */
    public int getSize() {
        return queue.size();
    }

}
