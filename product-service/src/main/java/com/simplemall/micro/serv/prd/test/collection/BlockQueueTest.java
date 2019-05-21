package com.simplemall.micro.serv.prd.test.collection;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class BlockQueueTest {

    public static  class Client implements Runnable{
        private LinkedBlockingDeque requestList;

        public Client (LinkedBlockingDeque requestList) {
            this.requestList=requestList;
        }

        @Override
        public void run() {
            System.out.printf("Client: Start.\n");
            for (int i=0; i<3; i++) {
                for (int j=0; j<5; j++) {
                    StringBuilder request=new StringBuilder();
                    request.append(i);
                    request.append(":");
                    request.append(j);
                    try {
                        requestList.put(request.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("Client: %s at %s.\n",request,new Date());
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.printf("Client: End.\n");
        }
    }

    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque list=new LinkedBlockingDeque(3);
        Client client=new Client(list);
        Thread thread=new Thread(client);
        thread.start();

        TimeUnit.SECONDS.sleep(5);

        for (int i=0; i < 3 ;i++)
        {
            for (int j=0; j<5; j++) {
                String request= (String) list.take();
                System.out.printf("Main: Request: %s at %s. Size:%d\n",request,new Date(),list.size());
            }
            TimeUnit.MILLISECONDS.sleep(300);
        }

        System.out.printf("Main: End of the program.\n");


    }



}
