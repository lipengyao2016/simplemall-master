package com.simplemall.micro.serv.prd.test.ThreadPool;

import java.util.Date;
import java.util.concurrent.*;

public class ThreadExecutorTest {

    public static  class Task implements Runnable {

        private Date initDate;
        private String name;

        public Task(String name){
            initDate=new Date();
            this.name=name;
        }


        @Override
        public void run() {
           // System.out.printf("task:%s: Task %s: Created on: %s\n",Thread.currentThread().getName(),name,initDate);
            System.out.printf("task:%s: Task %s: Started on: %s\n",Thread.currentThread().getName(),name,new Date());

         /*   try {
//                Long duration=(long)(Math.random()*10);
               // System.out.printf("task:%s: Task %s: Doing a task during %dseconds\n",Thread.currentThread().getName(),name,duration);
//                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            System.out.printf("task:%s: Task %s: Finished on: %s\n",Thread.currentThread().getName(),name,new Date());
        }
    }

    public static  class Server {
//        private ThreadPoolExecutor executor;
        private ScheduledExecutorService  executor;

        public Server(){
            //executor=(ThreadPoolExecutor) Executors.newCachedThreadPool();
//            executor=(ThreadPoolExecutor) Executors.newFixedThreadPool(5);
            executor=(ScheduledExecutorService) Executors.newScheduledThreadPool(1);
        }

        public void executeTask(Task task) {
            System.out.printf("Server: A new task has arrived\n");
            ScheduledFuture<?> result=executor.scheduleAtFixedRate(task,1, 1, TimeUnit.SECONDS);
//            executor.execute(task);
         /*   System.out.printf("Server: Pool Size: %d,Active Count: %d,Completed Tasks: %d,task count:%d\n"
                    ,executor.getPoolSize(),executor.getActiveCount(),executor.getCompletedTaskCount()
            ,executor.getTaskCount());*/

    /*        for (int i=0; i<10; i++){
                System.out.printf("Main: Delay: %d\n",result.
                        getDelay(TimeUnit.MILLISECONDS));
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/


        }

        public void endServer() {
            executor.shutdown();
        }
    }


        public static  void main(String[] args) {
            Server server=new Server();
            for (int i=0; i<1; i++){
                Task task=new Task("Task "+i);
                server.executeTask(task);
            }
            //server.endServer();

            try {
                TimeUnit.SECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }





}
