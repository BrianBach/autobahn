#!/usr/bin/env bash

abspath=$(cd ${0%/*} && echo $PWD/${0##*/})

# to get the path only - not the script name - add
path_only=`dirname "$abspath"`
#checks if dialog command is available
function check_ui {
        dialog &>/dev/null
        if [ $? -ne 0 ]; then
                return 1
        fi
        return 0
}

while getopts "c" flag; do
        case $flag in
		c )	
		$path_only/self-check.sh $@ 
		$path_only/setup.sh $@
		exit 1
		;;
	esac
done

check_ui
        if [ $? -eq 0 ]; then
#                ../../size.sh
		dialog --print-maxsize --stdout | awk ' $2<35{print "Your display is not enough,please run setupall.sh -c"} $3<89{print "Your display is not enough,please run setupall.sh -c"} $2<35{exit 1} $3<89{exit 1} '
		#echo "Installation begins with graphical environment";
		#awk ' {exit 1} '
		awkstatus=$?
		#echo $awkstatus;
		if [ $awkstatus -eq 0 ]; then
			echo "Installation begins with graphical environment";
			#exit 0
			$path_only/self-check.sh $@ 
			$path_only/setup.sh $@
		else
			echo "Exiting installation"
			exit 1
		fi
        else
                echo "Installation begins";
		$path_only/self-check.sh $@ 
		$path_only/setup.sh $@
        fi


#$path_only/self-check.sh $@ 
#$path_only/setup.sh $@

