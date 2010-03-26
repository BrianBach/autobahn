#!/bin/bash

#This script takes as user input remote and local public IPs,
((i=1))
declare local_router_id
declare network
declare area

abspath=$(cd ${0%/*} && echo $PWD/${0##*/})

# to get the path only - not the script name - add
path_only=`dirname "$abspath"`

function get_tunnel_number {
	(( i=1 ))
	local max=0
	touch tempfile
	ip tunnel ls | while read line; do
		if [ $i -eq 1 ]; then
			(( i=i+1 ))
			continue
		fi	
		a=`echo $line|awk '{print $1}'`
		a=${a/ /}
		lchr=`echo $a | awk '{ print $(NF-1) }' FS=` 
		cur_number=${lchr}
		if [[ ${cur_number} -gt ${max} ]]; then
			max=${cur_number}
			#echo "now lchr:$lchr max: $max!"
			echo $max > tempfile
		fi
	done
	max=`cat tempfile`
	#echo "max = $max"
	 max=$(( $max+1 ))
#	echo "max tunnel=$max"
	rm -f tempfile
	return $max
}

readonly -f get_tunnel_number
declare -t get_tunnel_number

#The installer.conf should have ospfd.conf defined
source "$path_only/installer.conf"
ospfd_conf_backup="$ospfd_conf.backup"
#echo "OSPFD_CONF=$ospfd_conf BACKUP=$ospfd_conf_backup"
#Backup old ospfd.conf, create new!
[ -f $ospfd_conf ] && cp $ospfd_conf $ospfd_conf_backup

conf_written="false"
$path_only/tunnels_not_installed.sh > tunnels_to_install

cat tunnels_to_install|while read line; do
# skip comments
        [[ ${line:0:1} == "#" ]] && continue
	
        # skip empty lines
        [[ -z "$line" ]] && continue
	echo $line > curline
	remote=`awk '{print $2}' curline` 
	localn=`awk '{print $3}' curline` 
	local_subnet=`awk '{print $4}' curline` 
	get_tunnel_number
	n=$?
	#echo "n=$n"
	tunnel_name="gre$n"
	local_router_id=`awk '{print $5}' curline`
	network=`awk '{print $6}' curline`
        area=`awk '{print $7}' curline`
	echo "Creating tunnel($tunnel_name) for Remote: $remote Local:$localn Local_subnet:$local_subnet ..."
#echo "local_router_id: $local_router_id"	
	if [ $conf_written == "false" ]; then
	conf_text=`printf "!\n!Zebra configuration saved from vty\nrouter ospf\n\tospf router-id %s\n\tnetwork %s area %s\n\tcapability opaque\n!\npassword XXXX\nlog file /var/log/quagga/ospfd.log\n!\n" "$local_router_id" "$network" "$area"`

#echo "$conf_text"
echo "$conf_text" > ${ospfd_conf}/ospfd.conf
conf_written="true"
#kill `cat /var/run/quagga/ospfd.pid`
#kill `cat /var/run/quagga/zebra.pid`
#zebra -d
#ospfd -d -a
#newpath_john="`cat $path_only/startDaemons.tmp`"
#newpath_john_2="${newpath_john}quagga"
#daemons_start_zebra="${newpath_john}zebra -d"
#daemons_start_ospfd="${newpath_john}ospfd -d -a"
#echolog "Johnies daemons: $newpath_john"
#$daemons_start_zebra restart > /dev/null 2>&1
#$daemons_start_ospfd restart > /dev/null 2>&1
#$newpath_john restart > /dev/null 2>&1
#$newpath_john_2 restart > /dev/null 2>&1
	fi

#newpath_john="`cat $path_only/startDaemons.tmp`"
#newpath_john_2="${newpath_john}quagga"
#echo $newpath_john_2
#daemons_start_zebra="${newpath_john}zebra -d"
#daemons_start_ospfd="${newpath_john}ospfd -d -a"
#echolog "Johnies daemons: $newpath_john"
#$daemons_start_zebra restart > /dev/null 2>&1
#$daemons_start_ospfd restart > /dev/null 2>&1
#$newpath_john restart > /dev/null 2>&1
#$newpath_john_2 restart > /dev/null 2>&1
ip tunnel add $tunnel_name mode gre remote $remote local $localn ttl 255
	ip link set $tunnel_name up multicast on
	ip addr add $local_subnet brd + dev $tunnel_name

	((i=$i+1))

done

$path_only/startDaemons.sh
rm -f curline tunnels_to_install
echo "Installation complete."
