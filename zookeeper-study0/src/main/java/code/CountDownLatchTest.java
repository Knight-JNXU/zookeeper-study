/**
 
* Copyright (c) 2014 Baozun All Rights Reserved.
 
*
 
* This software is the confidential and proprietary information of Baozun.
 
* You shall not disclose such Confidential Information and shall use it only in
 
* accordance with the terms of the license agreement you entered into
 
* with Baozun.
 
*
 
* BAOZUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 
* SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 
* IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 
* PURPOSE, OR NON-INFRINGEMENT. BAOZUN SHALL NOT BE LIABLE FOR ANY DAMAGES
 
* SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 
* THIS SOFTWARE OR ITS DERIVATIVES.
 
*
 
*/
package code;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 探究 CountDownLatch 的作用
 * @author liuwen
 * @version 1.0
 * @date 2018年5月16日
 * CountDownLatch是一个同步工具类，它允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行。
 * 典型的用法是将一个程序分为n个互相独立的可解决任务，并创建值为n的CountDownLatch。当每一个任务完成时，
 * 都会在这个锁存器上调用countDown，等待问题被解决的任务调用这个锁存器的await，将他们自己拦住，直至锁存器计数结束。
 */
public class CountDownLatchTest{
    
    private int threadNum = 5;//执行任务的子线程数量
    private int workNum = 20;//任务数量
    private final int sleepTime = 10000;
    private ExecutorService service;
    private ArrayBlockingQueue<String> blockingQueue;
    private CountDownLatch latch;

    @Before
    public void setUp() {
        service = Executors.newFixedThreadPool(threadNum, new ThreadFactoryBuilder().setNameFormat("WorkThread-%d").build());
        blockingQueue = new ArrayBlockingQueue<>(workNum);
        for (int i = 0; i < workNum; i++) {
            blockingQueue.add("任务-" + i);
        }
        latch = new CountDownLatch(workNum);//计数器的值为任务的数量
    }

    @Test
    public void test() throws InterruptedException {
        System.out.println("主线程开始运行");
        for (int i = 0; i < workNum; i++) {
            service.execute(new WorkRunnable());
        }
        latch.await();//等待子线程的所有任务完成
        System.out.println("主线程去做其它事");
    }

    //用blockQueue中的元素模拟任务
    public String getWork() {
        return blockingQueue.poll();
    }

    class WorkRunnable implements Runnable {

        public void run() {
            String work = getWork();
            performWork(work);
            latch.countDown();//完成一个任务就调用一次
        }
    }

    private void performWork(String work) {
        System.out.println("处理任务：" + work);
        try {
            //模拟耗时的任务
            Thread.currentThread().sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    
}
