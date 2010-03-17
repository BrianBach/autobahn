#!/usr/bin/env bash
abspath=$(cd ${0%/*} && echo $PWD/${0##*/})

# to get the path only - not the script name - add
path_only=`dirname "$abspath"`


((i=0))

ip tunnel ls | while read line; do
((i=i+1))
#echo "Line $i: $line"
tunnel_name=`echo $line | awk '{print $1}'`
tunnel_name=${tunnel_name/ /}
#echo "Now tunnel: $tunnel_name"
if [ $i -ne 1 ]; then
	echo "Deleting $tunnel_name ..." 
	ip tunnel del $tunnel_name
fi
done
echo
echo "Tunnel deletion completed!"
cp routing.conf routing.conf.backup
$path_only/init_routing.sh > routing.conf
echo "Routing.conf cleared!"
#echo "Checking for tunnels: "
#ip tunnel ls
