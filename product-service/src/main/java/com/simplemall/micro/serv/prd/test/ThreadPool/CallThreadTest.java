package com.simplemall.micro.serv.prd.test.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CallThreadTest {

      public static class  Task implements Callable<Integer> {
          private Integer mNum = 0;

          public  Task(int num)
          {
              mNum = num;
          }

        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算 "+mNum);
            Thread.sleep(30);
            int sum = 0;
            for(int i=0;i<mNum;i++)
                sum += i;
            System.out.println("子线程执行完毕 " +mNum + ",sum:" + sum);
            return sum;
        }
    }

        public static void main(String[] args) {
//            ExecutorService executor = Executors.newCachedThreadPool();
            ScheduledThreadPoolExecutor  executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
            int taskLength = 2;
            List<Task> tasks = new ArrayList<>();
            for(int i = 1 ;i < taskLength;i++)
            {
                Task testTask = new Task(i * 10);
                tasks.add(testTask) ;
                executor.schedule(testTask,i+1 , TimeUnit.SECONDS);


            }
            executor.shutdown();
            try {
                executor.awaitTermination(1, TimeUnit.HOURS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



//            Future<Integer> result = executor.submit(task);

       /*     List<Future<Integer>>resultList=null;

            try {
                resultList=executor.invokeAll(tasks);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

           /* System.out.println("Main: Printing the results");
            for (int i=0; i<resultList.size(); i++){
                Future<Integer> future=resultList.get(i);
                try {
                    Integer result=future.get();
                    System.out.println("result : "+ result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }*/





          /*  try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.out.println("主线程在执行任务");
            try {
                //System.out.println("task运行结果"+result.get());

                Integer nRet = executor.invokeAny(tasks);

                System.out.println("task运行结果"+nRet);


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }*/

            System.out.println("所有任务执行完毕");
        }

}
