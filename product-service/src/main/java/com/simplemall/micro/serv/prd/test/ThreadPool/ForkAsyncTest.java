package com.simplemall.micro.serv.prd.test.ThreadPool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class ForkAsyncTest {
    public static  class FolderProcessor extends RecursiveTask<List<String>> {
        private static final long serialVersionUID = 1L;
        private String path;
        private String extension;

        public FolderProcessor (String path, String extension) {
            this.path=path;
            this.extension=extension;
        }



        @Override
        protected List<String> compute() {
            List<String> list=new ArrayList<>();
            List<FolderProcessor> tasks=new ArrayList<>();
            File file=new File(path);
            File content[] = file.listFiles();
            if (content != null) {
                for (int i = 0; i < content.length; i++) {
                    if (content[i].isDirectory()) {
                        FolderProcessor task = new FolderProcessor(content[i].
                                getAbsolutePath(), extension);
                        task.fork();
                        tasks.add(task);
                    } else {
                        if (checkFile(content[i].getName())) {
                            list.add(content[i].getAbsolutePath());
                        }
                    }
                }
            }

            if (tasks.size()>50) {
                System.out.printf("%s: %d tasks ran.\n",file.
                        getAbsolutePath(),tasks.size());
            }
            addResultsFromTasks(list,tasks);
            return  list;
        }

        private void addResultsFromTasks(List<String> list,
                                         List<FolderProcessor> tasks) {
            for (FolderProcessor item: tasks) {
                list.addAll(item.join());
            }
        }

        private boolean checkFile(String name) {
            return name.endsWith(extension);
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool=new ForkJoinPool();

        String extension = args[0];
        String dir = args[1];

        long lCur = System.currentTimeMillis();
        FolderProcessor system=new FolderProcessor(/*"C:\\Windows"*/ dir, extension);
     /*   FolderProcessor apps=new FolderProcessor("C:\\Program Files",extension);
        FolderProcessor documents=new FolderProcessor("C:\\Documents And Settings",extension);
        pool.execute(apps);
        pool.execute(documents);*/

        pool.execute(system);

        do {
            System.out.printf("******************************************\n");
            System.out.printf("Main: Parallelism: %d\n",pool.getParallelism());
            System.out.printf("Main: Active Threads: %d\n",pool.getActiveThreadCount());
            System.out.printf("Main: Task Count: %d\n",pool.getQueuedTaskCount());
            System.out.printf("Main: Steal Count: %d\n",pool.getStealCount());
            System.out.printf("******************************************\n");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
          } while((!system.isDone())/*||(!apps.isDone())||(!documents.isDone())*/);
        pool.shutdown();
        List<String> results;
        results=system.join();
        System.out.printf("all finished time: %d s.\n", (System.currentTimeMillis()-lCur) / 1000);
        System.out.printf("System: %d files found,.\n",results.size());
        for(int i = 0 ;i < results.size();i++)
        {
            System.out.printf("System file path:%s\n",results.get(i));
        }
      /*  results=apps.join();
        System.out.printf("Apps: %d files found.\n",results.size());
        results=documents.join();
        System.out.printf("Documents: %d files found.\n",results.size());*/


    }


}
