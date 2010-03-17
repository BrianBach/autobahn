#!/usr/bin/env bash

#This script takes as user input remote and local public IPs,
#TODO: Sanitation check of file given
((i=1))
declare local_router_id
declare network
declare area

abspath=$(cd ${0%/*} && echo $PWD/${0##*/})

# to get the path only - not the script name - add
path_only=`dirname "$abspath"`


ospfd_conf="./ospfd.conf"
ospfd_conf_backup="./ospfd.backup"
#Backup old ospfd.conf, create new!
[ -f $ospfd_conf ] && cp $ospfd_conf $ospfd_conf_backup

conf_written="false"
line="$@"	
        # skip empty lines
        [[ -z "$line" ]] && continue
	echo $line > curline
	remote=`awk '{print $1}' curline` 
	localn=`awk '{print $2}' curline` 
	local_subnet=`awk '{print $3}' curline` 
	n=$?
	#echo "n=$n"
	tunnel_name=`awk '{print $7}' curline`
	local_router_id=`awk '{print $4}' curline`
	network=`awk '{print $5}' curline`
        area=`awk '{print $6}' curline`

	echo "Creating tunnel($tunnel_name) for Remote: $remote Local:$localn Local_subnet:$local_subnet ..."
	if [ $conf_written == "false" ]; then
	conf_text=`printf "!\n!Zebra configuration saved from vty\nrouter ospf\n\tospf router-id %s\n\tnetwork %s area %s\n\tcapability opaque\n!\npassword XXXX\nlog file /var/log/quagga/ospfd.log\n!\n" "$local_router_id" "$network" "$area"`
#echo "TUNNEL NAME:$tunnel_name"
existing_tunnel=`ip tunnel ls|grep $tunnel_name`
tunnel_name=`echo $tunnel_name|awk -F ":" '{print $1}'`
ex_tunnel_remote=`echo $existing_tunnel|awk '{print $4}'`
ex_tunnel_local=`echo $existing_tunnel|awk '{print $6}'`
#echo "EX_TUNNEL REMOTE: $ex_tunnel_remote #EX_TUNNEL_LOCAL $ex_tunnel_local"
$path_only/remove_from_conf.sh $ex_tunnel_remote $ex_tunnel_local &>$path_only/route.bak 

printf "Link\t$remote\t$localn\t$local_subnet\t$local_router_id\t$network\t$area\n">>$path_only/route.bak 
mv route.bak routing.conf

echo "$conf_text" > $ospfd_conf
conf_written="true"
	fi
ip tunnel del $tunnel_name
ip tunnel add $tunnel_name mode gre remote $remote local $localn ttl 255
	ip link set $tunnel_name up multicast on
	ip addr add $local_subnet brd + dev $tunnel_name

	(( i=$i+1 ))


echo "Installation complete."


