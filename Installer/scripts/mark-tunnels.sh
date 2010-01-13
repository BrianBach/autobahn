#!/bin/bash

#This script marks the link name of a configuration file as i if the corresponding tunnel has been installed

EXPECTED_ARGS=1
E_BADARGS=65

if [ $# -ne $EXPECTED_ARGS ]; then
	echo "Usage: `basename $0` routing_file"
	exit $E_BADARGS
fi

if [ ! -f $1 ]; then
	echo "$1 does not exist. Please use an existing routing configuration file!"
	exit 1
fi

echo "#Link_name   remote           local          local_subnet local_router_id network      area"
echo 

(( i=0 ))
cat $1 | while read line; do
(( i=i+1 ))
if [ $i -gt 1 ]; then
	cur_remote=`echo $line|awk '{print $2}'`
	cur_remote=${cur_remote/ /}
	cur_local=`echo $line|awk '{print $3}'`
	cur_local_subnet=`echo $line|awk '{print $4}'`
	cur_local_router_id=`echo $line|awk '{print $5}'`
	cur_network=`echo $line|awk '{print $6}'`
	cur_area=`echo $line|awk '{print $7}'`

	if [[ $cur_remote != "" ]]; then
	#	echo "Cur_remote = $cur_remote Cur_local = $cur_local"
		#echo "Line = $line"	
		exists=`ip tunnel ls | grep  "remote $cur_remote" | grep "local $cur_local"`
		if [[ -n $exists ]]; then
			printf "I\t$cur_remote\t$cur_local\t$cur_local_subnet\t$cur_local_router_id\t$cur_network\t$cur_area\n"	
		else
			printf "U\t$cur_remote\t$cur_local\t$cur_local_subnet\t$cur_local_router_id\t$cur_network\t$cur_area\n"	
		fi
	fi
fi
done
