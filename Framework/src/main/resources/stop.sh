#!/bin/sh
PID_FILE=lock/pid

if [ -e $PID_FILE ] ; then
  pid=`cat $PID_FILE`
else
  echo "seems that Autobahn is not running"
  exit
fi

name=`ps -p $pid -o comm= 2>/dev/null`
if [ -z "$name" ] || [ $name != "java" ] ; then
  rm $PID_FILE
  echo "seems that Autobahn is not running"
  exit
fi

echo "Terminating Autobahn"
#kill -15 `ps h -C java -o "%p:%a" | grep net.geant.autobahn.framework.Framework | cut -d: -f1`
kill -15 $pid
rm $PID_FILE
echo "Autobahn terminated"
