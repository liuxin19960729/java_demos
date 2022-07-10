//package com.liuxin.threadPool.custom;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.concurrent.*;
//
//public class ExecutorServiceCus {
//    public static void main(String[] args) {
//        ExecutorServiceCus executorServiceCus = new ExecutorServiceCus();
//    }
//}
//
//
//class ExecutorServiceCusCus extends AbstractExecutorService {
//
//
//    private final BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>(1);
//    private volatile CusThread thread = null;
//    private final Object threadMessage = new Object();
//
//
//    public ExecutorServiceCusCus() {
//        thread = new CusThread();
//    }
//
//    @Override
//    public void shutdown() {
//
//    }
//
//    @Override
//    public List<Runnable> shutdownNow() {
//        return null;
//    }
//
//    @Override
//    public boolean isShutdown() {
//        return false;
//    }
//
//    @Override
//    public boolean isTerminated() {
//        return false;
//    }
//
//    @Override
//    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
//        return false;
//    }
//
//    @Override
//    public void execute(Runnable command) {
//        Objects.requireNonNull(command);
//        boolean add = queue.add(command);
//        if (!add) {
//            System.out.println("reject");
//            return;
//        }
//        threadMessage.notifyAll();
//    }
//
//
//    private class CusThread extends Thread {
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    if (queue.isEmpty()) threadMessage.wait();
//                    Runnable runnable = queue.poll();
//                    if (Objects.isNull(runnable)) continue;
//                    runnable.run();
//
//                } catch (InterruptedException e) {
//                    // shutdown
//                    break;
//                } catch (Exception e) {
//                    continue;
//                } finally {
//                    System.out.println("thread finally");
//                }
//            }
//        }
//    }
//}
