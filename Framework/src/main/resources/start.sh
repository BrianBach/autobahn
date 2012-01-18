#!/bin/bash
PID_FILE=lock/pid
OLD_PATH=`pwd`

#this loads the path where the script file is located
FILE_PATH=`dirname $0`

#this is the framework properties file
FILE=etc/framework.properties
if [ -f etc/autobahn.properties ]
  then
    echo "Using autobahn.properties"
    FILE=etc/autobahn.properties
fi

CONFIG=etc/cxf/cxf.xml

cd $FILE_PATH
mkdir -p lock

CP=.
for i in `ls lib/*.jar`; do CP=$CP:$i; done
rm -f ./nohup.out;

#the command to start the autobahn
AUTOBAHN="java -Dcxf.config.file=$CONFIG -Dorg.apache.cxf.Logger=org.apache.cxf.common.logging.Log4jLogger -classpath $CP net.geant.autobahn.framework.Framework"

#we read the property file eliminating the comments
#then we find the line containing the framework.commandLine and we only keep its value
line=`sed 's/[#!].*$//g' $FILE |grep "framework.commandLine"`
framework_commandLine=`echo $line |sed 's/framework.commandLine//g'| tr -d '' | tr -d '=' | tr -d ':'`

if [ -e $PID_FILE ] ; then
  pid=`cat $PID_FILE`  
  name=`ps -p $pid -o comm= 2>/dev/null`
  
  if [ $name ] && [ $name == "java" ] ; then    
    echo "seems that Autobahn is already running"
    exit
  else
    rm $PID_FILE
  fi
fi


#if the framework_commandLine property has been set to interactive
#we do not start the autobahn as daemon
if [ "$framework_commandLine" == "interactive" ]
then
    echo "Starting autobahn"    
    $AUTOBAHN
    cd $OLD_PATH
else
    echo "Starting autobahn daemon"
    touch ./nohup.out
    nohup $AUTOBAHN &
    pid=$!
    echo $pid >$PID_FILE
    cd $OLD_PATH
    tail -f $FILE_PATH/nohup.out
fi