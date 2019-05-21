#!/bin/sh 
CMD=$1
echo $CMD
JAVAPRG="/home/java/jdk1.8/bin/java"
echo $JAVAPRG

case $CMD in 
-start)
        cd /usr/local/games/mallServer
		nohup $JAVAPRG -Xmx256m -Xms128m -XX:+UseAdaptiveSizePolicy -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -jar admin-server-0.0.1-SNAPSHOT.jar  >> adminServer.log 2>&1 &
		echo "run adminServer ok..."
	    nohup $JAVAPRG -Xmx256m -Xms128m -XX:+UseAdaptiveSizePolicy -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -jar eureka-server-0.0.1-SNAPSHOT.jar  >> eurekaServer.log 2>&1 &
		echo "run eurekaServer ok..."
		nohup $JAVAPRG -Xmx256m -Xms128m -XX:+UseAdaptiveSizePolicy -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -jar conf-server-0.0.1-SNAPSHOT.jar  >> confServer.log 2>&1 &
		echo "run confServer ok..."
		nohup $JAVAPRG -Xmx256m -Xms128m -XX:+UseAdaptiveSizePolicy -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -jar zuul-server-0.0.1-SNAPSHOT.jar  >> zuulServer.log 2>&1 &
		echo "run zuulServer ok..."
		nohup $JAVAPRG -Xmx256m -Xms128m -XX:+UseAdaptiveSizePolicy -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -jar hystrix-dashboard-0.0.1-SNAPSHOT.jar  >> zuulServer.log 2>&1 &
		echo "run hystrix-dashboard ok..."
		nohup $JAVAPRG -Xmx256m -Xms128m -XX:+UseAdaptiveSizePolicy -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -jar turbine-server-0.0.1-SNAPSHOT.jar  >> zuulServer.log 2>&1 &
		echo "run turbine-server ok..."
        ;;
-stop)
        eurekaPid=$(ps -ef|grep eurekaserver | grep -v grep| awk '{print $2}')
		echo $eurekaPid
        sudo kill $eurekaPid
		echo "stop eurekaServer ok..."
        ;;

-restart)
        eurekaPid=$(ps -ef|grep eurekaserver | grep -v grep| awk '{print $2}')
        echo $eurekaPid
        sudo kill $eurekaPid
		echo "kill eurekaServer ok..."
        cd /usr/local/eurekaServer
	    nohup $JAVAPRG -jar eurekaserver-0.0.1-SNAPSHOT.jar  >> eurekaServer.log 2>&1 &
		echo "restart eurekaServer ok..."
        ;;
*)
        echo "Usage: runEureka.sh -start | -stop | -restart .Or use systemctl start | stop | restart  eureka.service "
        ;;
esac