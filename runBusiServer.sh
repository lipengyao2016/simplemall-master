#!/bin/sh 
CMD=$1
echo $CMD
JAVAPRG="/home/java/jdk1.8/bin/java"
echo $JAVAPRG

case $CMD in 
-start)
        cd /usr/local/games/mallBusi
		nohup $JAVAPRG -Xmx256m -Xms128m -XX:+UseAdaptiveSizePolicy -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -jar account-sevice-0.0.1-SNAPSHOT.jar  >> accountServer.log 2>&1 &
		echo "run adminServer ok..."
	    nohup $JAVAPRG -Xmx256m -Xms128m -XX:+UseAdaptiveSizePolicy -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -jar product-service-0.0.1-SNAPSHOT.jar  >> productServer.log 2>&1 &
		echo "run eurekaServer ok..."
		nohup $JAVAPRG -Xmx256m -Xms128m -XX:+UseAdaptiveSizePolicy -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -jar front-app-0.0.1-SNAPSHOT.jar  >> front-appServer.log 2>&1 &
		echo "run confServer ok..."
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