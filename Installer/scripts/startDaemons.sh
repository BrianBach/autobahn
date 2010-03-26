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

zebra -d 127.0.0.1 2>>$log
ospfd -d -a 127.0.0.1 2>>$log

#restarting quagga

${startDaemon}quagga start 1>/dev/null 2>>$log
