## Executor 框架的两级调度模型
```
java Thread 线程 ----local os thread 线程一一对应
OS-->调度本地线程
java 线程终止，OS回收线程资源

Executor
  创建固定线程数(java thread ---os thread 一一对应) 


Executor 调度任务到县城池里面的线程执行
那个线程在CPU 执行是OS调度的  

```
### Executor结构
```
任务 Runnable or Callable

任执行     public interface Executor ,  interface ExecutorService extends Executor 

public class ThreadPoolExecutor extends AbstractExecutorService 
public class ScheduledThreadPoolExecutor
        extends ThreadPoolExecutor
        implements ScheduledExecutorService


异步结果:
  interface Future
实现类
 public class FutureTask<V> implements RunnableFuture<V> 

1.提交和任务执行分开
public interface Executor
2.执行任务提交
public class ThreadPoolExecutor

//延迟执行 定期执行
public class ScheduledThreadPoolExecutor


FutureTask 代表异步计算

 Executors.callable(runnable,result)
 将一个 Runable 封装成 Calleable 并且返回result

FutureTask futureTask = new FutureTask<>();
        futureTask.cancel() 取消任务的执行

```
###Executor框架成员
```

class Executors;
interface Future;
interface Runnale
interface Callable
ThreadPoolExecutor
   
    SingelThreadExecutors
        保证顺序执行各个任务,没有多个线程是活动的应用场景
    FixedThreadPool
        限制线程数
        适用场景 负载比较重的服务器
    CacheThreadPool
            大小无界限线程池,
            适用于 短期异步任务 负载较轻的服务器
        
ScheduledThreadPoolExecutor
                Executors.newSingleThreadScheduledExecutor();
                     一个线程
                     适用单个后台执行周期性的任务
                Executors.newScheduledThreadPool()
                        适用多个后台执行周期性的任务


```
## ThreadPoolExecutor 详解
### newFixedThreadPool
```

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }
0L, TimeUnit.MILLISECONDS 
keepAliveTime 只会回收>corePoolSize 大小的Idle(空闲)


note:blocking queue new LinkedBlockingQueue<Runnable>() capacity Interger.MAX_VALUE
不会拒绝 rejectHandler(在没有执行shutdown...)

```


###  newSingleThreadExecutor
```
    public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
    }


```
### newCachedThreadPool
```
    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    }


corePoolSize=0;
maximumPool=Integer.MAX_VALUE
keepAliveTime 60L, TimeUnit.SECONDS 1分钟

blocingQueue
new SynchronousQueue<Runnable>() 没有容量的队列

main--executorService.sumbit 提交过多不断创建线程
     note:在极端的情况下可能会创建线程过多导致CPU和内存资源耗尽

线程池空闲线程 获取元素
queue.poll(timeout,timeunit.) 或阻塞到等待60秒 超过时间 空闲线程终止 
 
提交元素到队列
sumbit-->queue.offer(runable,time)-->
     boolean offer(E e, long timeout, TimeUnit unit)
超过时间没有空闲线程 会创建线程
     
```
### ScheduledExecutorService
```
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new ScheduledThreadPoolExecutor(corePoolSize);
    }

    public ScheduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
              new DelayedWorkQueue());
    }


无界限队列
DelayedWorkQueue
设置 maximumPoolSize  Integer.MAX_VALUE 没有设么用处

    sheduele.scheduleWithFixedDelay();
        sheduele.scheduleAtFixedRate()


```

### ScheduledThreadPoolExecutor的实现
```
runnabel 被包装成  ScheduledFutureTask

      this.time = ns;执行具体时间
      this.period = 0;时间执行间隔
      this.sequenceNumber = sequencer.getAndIncrement();添加序列号

DelayedWorkQueue 对PriorityQueue的封装 
   time 作为优先级  time相同相同 比较 sequenceNumber

1.从DelequeQueue 中获取以到期的ScheduledFutureTask(delayqueue.take()) 
   delayqueue.take() 会阻塞到有一个可以运行队(时间到期)列元素

2.执行获取的任务

3.修改下一次执行的时间

4.把修改的futureTask 再次放入DelayQueue 里面

take:

   public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();//1 获取锁
        try {
            for (;;) {
                E first = q.peek();看一下有么有条件满足的的task(时间满足)
                if (first == null)
                    available.await();//2.1queue 当前任务为空先等待一下
                else {
                    long delay = first.getDelay(NANOSECONDS);
                    if (delay <= 0)//时间满足 
                        return q.poll();
                    first = null; // don't retain ref while waiting
                    if (leader != null)
                        available.await();
                    else {
                        Thread thisThread = Thread.currentThread();
                        leader = thisThread;
                        try {
                            available.awaitNanos(delay);
                        } finally {
                            if (leader == thisThread)
                                leader = null;
                        }
                    }
                }
            }
        } finally {
            if (leader == null && q.peek() != null)
                available.signal();//唤醒等待的线程
            lock.unlock();
        }
    }





offer():
public boolean offer(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();//1
        try {
            q.offer(e);
            if (q.peek() == e) {
                leader = null;
                available.signal();//唤醒等待的线程
            }
            return true;
        } finally {
            lock.unlock();
        }
    }
```
## FutureTask
```
interface RunnableFuture<V> extends Runnable, Future<V>

class FutureTask implement RunnableFuture
 
executor 执行 会调用 future.run()


future 状态
    private volatile int state;
    private static final int NEW          = 0; 未启动状态
    private static final int COMPLETING   = 1;
    private static final int NORMAL       = 2;
    private static final int EXCEPTIONAL  = 3;run 过程exception
    private static final int CANCELLED    = 4;task.cancel()
    private static final int INTERRUPTING = 5;
    private static final int INTERRUPTED  = 6;

       

```










