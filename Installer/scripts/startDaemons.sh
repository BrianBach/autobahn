#!/bin/bash

abspath=$(cd ${0%/*} && echo $PWD/${0##*/})
path_only=`dirname "$abspath"`

log="$path_only/startDaemons.log"

touch $log

startDaemon=`cat "$path_only/startDaemons.tmp" ` 2>>$log

${startDaemon}zebra stop 1>/dev/null 2>>$log
${startDaemon}ospfd stop 1>/dev/null 2>>$log
${startDaemon}quagga stop 1>/dev/null 2>>$log

#starting the daemons

${startDaemon}zebra start 1>/dev/null 2>>$log
${startDaemon}ospfd start 1>/dev/null 2>>$log

zebra -d 127.0.0.1 2>>$log
ospfd -d -a 127.0.0.1 2>>$log

#restarting quagga

${startDaemon}quagga start 1>/dev/null 2>>$log

#checking daemons

if ! netstat -ltn | grep 2601 > /dev/null ; then
	echo "zebra not running at port 2601";
fi
	
if ! netstat -ltn | grep 2604 > /dev/null ; then
	echo "ospfd not running at port 2604";
fi
	
if ! netstat -ltn | grep 2607 > /dev/null ; then
	echo "ospfapi not running at port 2607";
fi
