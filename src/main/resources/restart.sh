#!/bin/bash
APP_NAME=server
pid=`ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}'`
  
if [ -z "${pid}" ]; then
   echo "$APP_NAME is not running"
else
    echo "kill thread...$pid"
    kill -9 $pid
fi

nohup java -jar /srv/git/webexecuter-server/target/server-0.0.1-SNAPSHOT.jar
