package com.simplemall.micro.serv.prd.test.collection;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.ConcurrentLinkedDeque;

public class NonBlockQueueTest {

    public static  class AddTask implements Runnable {
        private ConcurrentLinkedDeque<String> list;

        public AddTask(ConcurrentLinkedDeque<String> list) {
            this.list=list;
        }


        @Override
        public void run() {
            String name=Thread.currentThread().getName();
            System.out.printf("AddTask: %s\n",name);
            for (int i=0; i<10000; i++){
                list.add(name+"_"+i);
            }
        }


    }

    public static  class PollTask implements Runnable {
        private ConcurrentLinkedDeque<String> list;

        public PollTask(ConcurrentLinkedDeque<String> list) {
            this.list=list;
        }


        @Override
        public void run() {
            String name=Thread.currentThread().getName();
            System.out.printf("PollTask: %s\n",name);
            for (int i=0; i<5000; i++){
                if(StringUtils.isEmpty((String) list.pollFirst()))
                {
                    System.out.printf("PollTask: pollFirst is null, threadName:%s\n",name);
                }
                if(StringUtils.isEmpty((String)list.pollLast()))
                {
                    System.out.printf("PollTask: pollLast is null, threadName:%s\n",name);
                }
            }
        }
    }

    public static void main(String[] args) {
        ConcurrentLinkedDeque<String> list=new ConcurrentLinkedDeque<>();
        Thread threads[]=new Thread[100];
        for (int i=0; i<threads.length ; i++){
            if(i % 2 ==0)
            {
                AddTask task=new AddTask(list);
                threads[i]=new Thread(task);
                threads[i].start();
            }
            else
            {
                PollTask task=new PollTask(list);
                threads[i]=new Thread(task);
                threads[i].start();
            }
        }
   /*     System.out.printf("Main: %d AddTask threads have been launched\n",threads.length);
        for (int i=0; i<threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Main: push end Size of the List: %d\n",list.size());

        for (int i=0; i<threads.length; i++){
            PollTask task=new PollTask(list);
            threads[i]=new Thread(task);
            threads[i].start();
        }
        System.out.printf("Main: %d PollTask threads have been launched\n",threads.length);
        for (int i=0; i<threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        System.out.printf("Main: poll end Size of the List: %d\n",list.size());

    }

}
