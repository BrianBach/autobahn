#!/usr/bin/env bash
if [ $# -ne 2 ]; then
	echo "Usage: $0 remoteip localip"
	exit 1
fi
cat routing.conf |while read line; do
exists=`echo $line|grep $1|grep $2`
if [[ -z $exists ]]; then
	echo $line  
fi

done
