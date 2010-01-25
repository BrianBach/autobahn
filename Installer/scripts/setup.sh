#!/bin/bash

## PREREQUISITES
# $DIALOG command (in debian based: apt-get install $DIALOG)
# database and tables set up
#---------------------------------------------------------

#select graphical interface
DIALOG=${DIALOG=dialog}

#select interface for input
ENTER_IP=${ENTER_IP=enter_ip}
ENTER_IP_NO_CHECK=${ENTER_IP_NO_CHECK=enter_ip_john}

should_exit=0
current_ip=0.0.0.0
abspath=$(cd ${0%/*} && echo $PWD/${0##*/})

# to get the path only - not the script name - add
export path_only=`dirname "$abspath"`

source $path_only/log.sh

echolog "path_only:$path_only"
#Manages installation of tunnels
function install_tunnels {
	pushlocalinfo
	newlogparagraph "function install_tunnels"
	$DIALOG --clear --infobox "Installing tunnels ..." 10 30
	$path_only/do-install.sh routing.conf &>tunans
	$DIALOG --no-shadow --title "Installing returned:" --textbox tunans 25 78
	log "Installing tunnels returned: `cat tunans`"
	poplocalinfo
}

#Manages menu item "View Tunnels"
function view_tunnels {
	pushlocalinfo
	newlogparagraph "function view_tunnels"
	ip tunnel ls > ans 2>/dev/null
		if [ $? -ne 0 ]; then
			$DIALOG --title "Error" --msgbox "Error while trying to find installed tunnels. Maybe there aren't any" 5 40
			log "Error while trying to find installed tunnels. Maybe there aren't any"
			continue
		else
			$DIALOG --no-shadow --title "List of installed tunnels:" --msgbox "`cat ans`" 25 78
			log "List of installed tunnels `cat ans`"
		fi
	poplocalinfo
}

#Manages menu item "Help"
function show_help {
	printf "Welcome to Command Line AutoBAHN Installer. Below all the basic menu items are explained:\n\n\n1.Add New Tunnel: Asks the user a series of IP addresses to set up new tunnels.\nInserts a new address in routing.conf\n\n2.Install tunnels: Installs the tunnels that have been added to routing.conf.\n\n3.View tunnels: Shows currently installed tunnels\n\n5.Tunnel Editor: A tool to edit existing tunnels\n\n6.Delete All Tunnels: Deletes all existing tunnels after asking for confirmation\n\n4.Setup database: Asks for an sql dump of the database and tries to restore it.\n If the dump does not create the database and its structures, they must already exist.\n\n5.Help: Show the current $DIALOG\n\n6.Exit: Exits the installer\n" > helpans
	$DIALOG --backtitle "AutoBAHN Help" --cr-wrap --no-shadow --title "AutoBAHN Help" --textbox helpans 30 90
	rm -f helpans
}

# Test an IP address for validity:
# Usage:
#      valid_ip IP_ADDRESS
#      if [[ $? -eq 0 ]]; then echo good; else echo bad; fi
#   OR
#      if valid_ip IP_ADDRESS; then echo good; else echo bad; fi
#
function valid_ip()
{
    local  ip=$1
    local  stat=1
    if [[ $ip =~ ^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$ ]]; then
         OIFS=$IFS
         IFS='.'
         ip=($ip)
         IFS=$OIFS
         [[ ${ip[0]} -le 255 && ${ip[1]} -le 255 \
               && ${ip[2]} -le 255 && ${ip[3]} -le 255 ]]
         stat=$?
    fi
# echo "Tested ip $ip and will return $stat"; sleep 3
    return $stat
}


function valid_ip_john()
{
    #local  ip=$1
    local  stat=0
    #if [[ $ip =~ ^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$ ]]; then
     #    OIFS=$IFS
      #   IFS='.'
       #  ip=($ip)
        # IFS=$OIFS
         #[[ ${ip[0]} -le 255 && ${ip[1]} -le 255 \
          #     && ${ip[2]} -le 255 && ${ip[3]} -le 255 ]]
         #stat=$?
   # fi
# echo "Tested ip $ip and will return $stat"; sleep 3
    return $stat
}
readonly -f valid_ip
declare -t valid_ip

function enter_ip_john {
	pushlocalinfo
	newlogparagraph "function enter_ip"
	val_ip=0 #true
	while [ $val_ip -ne 1 ]; do
		$DIALOG --title "$1" --inputbox "$2" 9 40 2>ipans
		if [ $? -eq 255 ]; then #escape was pressed
			return 1
		fi
		IP=`cat ipans`
		#valid_ip $IP
		#if [[ $? -eq 0 ]]; then
			val_ip=1
			current_ip=$IP
		#else
		 # 	$DIALOG --clear --title "Invalid IP address" --msgbox "$IP is an invalid IPv4 address. Please enter a valid one! (X.X.X.X / 0<=X<=255)" 8 67
	#		val_ip=0
	#	fi
	done
	rm -f ipans
	poplocalinfo
	return 0
}
#Usage: enter_ip "Window Title" "Window Content"
#enter_ip displays an input box suitable to enter an IP address 
function enter_ip {
	pushlocalinfo
	newlogparagraph "function enter_ip"
	val_ip=0 #true
	while [ $val_ip -ne 1 ]; do
		$DIALOG --title "$1" --inputbox "$2" 9 40 2>ipans
		if [ $? -eq 255 ]; then #escape was pressed
			return 1
		fi
		IP=`cat ipans`
		valid_ip $IP
		if [[ $? -eq 0 ]]; then
			val_ip=1
			current_ip=$IP
		else
		  	$DIALOG --clear --title "Invalid IP address" --msgbox "$IP is an invalid IPv4 address. Please enter a valid one! (X.X.X.X / 0<=X<=255)" 8 67
			val_ip=0
		fi
	done
	rm -f ipans
	poplocalinfo
	return 0
}

readonly -f enter_ip
declare -t enter_ip

function setup_database {
	pushlocalinfo
	newlogparagraph "function setup_database"
	val_sql=0 #true
	while [ $val_sql -ne 1 ]; do
		FILE=`$DIALOG --stdout --title "Please choose a PSQL dump" --fselect $HOME/ 24 78`
		case $? in
			0)filename=${FILE##*/}
			  extension=${filename##*.}
			if [ $extension = "sql" ]; then
				val_sql=1
                                dbname=`cat $path_only/tempdbname.tmp`
				echolog "dbname:$dbname path_only:$path_only"
                                rm -f tempdbname.tmp
                                dbuser=`cat $path_only/tempdbuser.tmp`
                                rm -f tempdbuser.tmp
				psql $dbname -U $dbuser < $FILE
				log "psql $path_only/$dbname -U $dbuser < $FILE returned $?"
			fi
				  ;;
			255) val_sql=1;;
		esac	
	done
	rm -f ipans
	poplocalinfo
}

#Manages menu item "Add Tunnel"
function add_tunnel {
	pushlocalinfo
	newlogparagraph "function add_tunnel"
	$ENTER_IP "Remote instance:" "Remote AutoBAHN instance"
	if [ $? -eq 1 ]; then #escape was pressed
		return 
	fi
	remote_instance=$current_ip
	$ENTER_IP "Local instance:" "Local AutoBAHN instance"
	if [ $? -eq 1 ]; then #escape was pressed
		return
	fi
	local_instance=$current_ip
	$ENTER_IP_NO_CHECK "Local subnet:" "Local subnet address"
	if [ $? -eq 1 ]; then #escape was pressed
		return 
	fi
	local_subnet=$current_ip
	$ENTER_IP "Local Router:" "Local Router ID"
	if [ $? -eq 1 ]; then #escape was pressed
		return 
	fi
	local_router=$current_ip
	$ENTER_IP_NO_CHECK "Network:" "Network"
	if [ $? -eq 1 ]; then #escape was pressed
		return 
	fi
	network=$current_ip
	$ENTER_IP "Area:" "Area"
	if [ $? -eq 1 ]; then #escape was pressed
		return	
	fi
	area=$current_ip

	log "Link $remote_instance\t$local_instance\t$local_subnet\t$local_router\t$network\t$area\n" 
	printf "Link $remote_instance\t$local_instance\t$local_subnet\t$local_router\t$network\t$area\n" >> $path_only/routing.conf
	poplocalinfo
}

#Automated tunnel adding 
#Usage ADD_TUNNEL remote_instance local_instance local_subnet local_router network area

function ADD_TUNNEL {
	pushlocalinfo
	newlogparagraph "function ADD_TUNNEL"
	if [ $# -ne 6 ]; then
		echo "Usage ADD_TUNNEL remote_instance local_instance local_subnet local_router network area"
	fi
	exists=`cat $path_only/routing.conf | grep $1 | grep $2`
	if [[ -z $exists ]]; then
		log "Adding Link $1\t$2\t$3\t$4\t$5\t$6\n" 
		printf "Link $1\t$2\t$3\t$4\t$5\t$6\n" >> $path_only/routing.conf
		return 0
	else
		log "Will return 1, because Link $1\t$2\t$3\t$4\t$5\t$6 already exists"
		return 1
	fi
	poplocalinfo
}




#Writes all current tunnels in the tunnels file
function get_tunnels {
	pushlocalinfo
	newlogparagraph "function get_tunnels"
	rm -f tunnels
	(( i=0 ))
	(( tunnels=0 ))
	local cur_tunnel
	log "I will return tunnels using ip tunnel ls, if unsuccessful either ip doesn't exist or the user doesn't have the appropriate permisisons"
	ip tunnel ls | while read line; do
		if [ $i -lt 1 ]; then
			(( i=1 ))
			continue
		fi
	  	tunnel_name=`echo $line | awk '{print $1}'`
		tunnel_remote=`echo $line | awk '{print $4}'`
		tunnel_local=`echo $line | awk '{print $6}'`
		log "\"$tunnel_name\" \"remote: $tunnel_remote local: $tunnel_local\" " 
		echo -n "\"$tunnel_name\" \"remote: $tunnel_remote local: $tunnel_local\" " >> tunnels
		(( tunnels=$tunnels+1 ))
	done
	poplocalinfo
}

readonly -f get_tunnels
declare -t get_tunnels

#Implements tunnel_editor
function tunnel_editor {
	pushlocalinfo
	newlogparagraph "function tunnel_editor"
	local cur_tunnel
	exit_editor=0
	while [ $exit_editor -eq 0 ]; do
		get_tunnels
		args=`cat tunnels`
		echo $args | xargs $DIALOG --clear --backtitle "AutoBAHN Installer -- Tunnel Editor"   --menu "           Name        Information" 15 80 8   2>ans
		retval=$?
		cur_tunnel=`cat ans`
		if [ $retval -ne 0 ]; then
			exit_editor=1
		else
			$DIALOG --clear --backtitle "AutoBAHN Installer -- Tunnel Editor" --menu "`ip tunnel ls|grep $cur_tunnel`" 10 80 2 "Edit Tunnel" "Edit Currently Selected Tunnel" "Delete Tunnel" "Deletes currently selected tunnel" 2>ans
			ret=$?
			ANS=`cat ans`
			declare remoteip
			declare localip
			declare localsubnet
			declare localrouterid
			declare network
			declare area
			if [ $ret -eq 0 ]; then
				case $ANS in
					"Edit Tunnel")
						cur_tunnel_info=`ip tunnel ls|grep $cur_tunnel`
						remoteip=`echo $cur_tunnel_info|awk '{print $4}'`	
						localip=`echo $cur_tunnel_info|awk '{print $6}'`	
						exists=`$path_only/tunnels_installed.sh|grep $localip|grep $remoteip`
						localsubnet=""
						localrouterid=""
						network=""
						area=""
						if [[ -n $exists ]]; then
						  line=$exists
						  localsubnet=`echo $line|awk '{print $4}'`
       						  localrouterid=`echo $line|awk '{print $5}'`
        					  network=`echo $line|awk '{print $6}'`
   					          area=`echo $line|awk '{print $7}'`
						else
							$DIALOG --clear --title "The tunnel doesn't exist in routing.conf." --msgbox "The tunnel doesn't exist in routing.conf. Please fill in the missing information if you want to proceed." 8 67	
						fi
						val_ip=0 #true
						while [ $val_ip -ne 1 ]; do
						$DIALOG --backtitle "Edit Tunnel" --title "Edit tunnel" --form "Use [up] [down] to select input field" 21 70 18 "Remote IP:" 2 4 "$remoteip" 2 22 20 0 "Local IP:" 4 4 "$localip" 4 22 20 0 "Local Subnet:" 6 4 "$localsubnet" 6 22 20 0 "Local Router ID:" 8 4 "$localrouterid" 8 22 20 0 "Network:" 10 4 "$network" 10 22 20 0 "Area:" 12 4 "$area" 12 22 20 0 2>ans
							if [ $? -eq 255 ]; then #escape was pressed
								return 1
							fi
							(( i=0 ));
							cat ans|while read line; do
								(( i=$i+1 ))
								[ $i -eq 1 ] && echo $line > remoteip 
								[ $i -eq 2 ] && echo $line > localip	
								[ $i -eq 3 ] && echo $line > localsubnet	
								[ $i -eq 4 ] && echo $line > localrouterid	
								[ $i -eq 5 ] && echo $line > network
								[ $i -eq 6 ] && echo $line > area
							done
							remoteip=`cat remoteip`
							localip=`cat localip`
							localsubnet=`cat localsubnet`
							localrouterid=`cat localrouterid`
							network=`cat network`
							area=`cat area`
							valid_ip $remoteip
							if [[ $? -eq 0 ]]; then
							       val_ip=1 
								current_ip=$remoteip
							else
		 					 	$DIALOG --clear --title "Invalid IP address in Remote Address" --msgbox "$remoteip is an invalid IPv4 address. Please enter a valid one! (X.X.X.X / 0<=X<=255)" 8 67
								val_ip=0 
							fi
							valid_ip $localip
							if [[ $? -eq 0 ]]; then
								current_ip=$localip
							else
		 					 	$DIALOG --clear --title "Invalid IP address in Remote Address" --msgbox "$localip is an invalid IPv4 address. Please enter a valid one! (X.X.X.X / 0<=X<=255)" 8 67
								val_ip=0 
							fi
							valid_ip_john $localsubnet
							if [[ $? -eq 0 ]]; then
							        
								current_ip=$localsubnet
							else
		 					 	$DIALOG --clear --title "Invalid IP address in Remote Address" --msgbox "$localsubnet is an invalid IPv4 address. Please enter a valid one! (X.X.X.X / 0<=X<=255)" 8 67
								val_ip=0 
							fi
							valid_ip $localrouterid
							if [[ $? -eq 0 ]]; then
								current_ip=$localrouterid
							else
		 					 	$DIALOG --clear --title "Invalid IP address in Remote Address" --msgbox "$localrouterid is an invalid IPv4 address. Please enter a valid one! (X.X.X.X / 0<=X<=255)" 8 67
								val_ip=0 
							fi
							valid_ip_john $network
							if [[ $? -eq 0 ]]; then
								current_ip=$network
							else
		 					 	$DIALOG --clear --title "Invalid IP address in Remote Address" --msgbox "$network is an invalid IPv4 address. Please enter a valid one! (X.X.X.X / 0<=X<=255)" 8 67
								val_ip=0 
							fi
							valid_ip $area
							if [[ $? -eq 0 ]]; then
								current_ip=$area
							else
		 					 	$DIALOG --clear --title "Invalid IP address in Remote Address" --msgbox "$area is an invalid IPv4 address. Please enter a valid one! (X.X.X.X / 0<=X<=255)" 8 67
								val_ip=0 
							fi

						done
						log "I will write $remoteip\t$localip\t$localsubnet\t$localrouterid\t$network\t$area $cur_tunnel"
						change_tunnel_arg=`printf "$remoteip\t$localip\t$localsubnet\t$localrouterid\t$network\t$area $cur_tunnel"`
						$path_only/change-tunnel.sh $change_tunnel_arg
						rm -f ipans

					;;
					"Delete Tunnel")
						ip tunnel del $cur_tunnel
						log "I deleted $cur_tunnel"
					;;
				esac
			fi
		fi
	done
	rm -f tunnels
	poplocalinfo
}

#Deletes all existing tunnels after asking for confirmation
function delete_all_tunnels {
	pushlocalinfo
	newlogparagraph "function delete_all_tunnels"
	$DIALOG --title "Deletion confirmation" --keep-window --yesno "Are you sure you want to delete existing tunnels ?" 10 30
	if [ $? -eq 0 ]; then
		$path_only/del-tunnels.sh
	
	
		$DIALOG --clear --title "All tunnels deleted" --msgbox "All tunnels were deleted successfully" 8 67	
		log "All tunnels were deleted"
	fi	
	poplocalinfo
}

function property_editor {
	$path_only/self-check.sh -f
}

readonly -f install_tunnels
declare -t  install_tunnels
readonly -f view_tunnels
declare -t view_tunnels
readonly -f setup_database
declare -t setup_database
readonly -f add_tunnel
declare -t add_tunnel
readonly -f show_help
declare -t show_help
readonly -f tunnel_editor
declare -t tunnel_editor
readonly -f delete_all_tunnels
declare -t delete_all_tunnels
readonly -f property_editor
declare -t property_editor

#Shows the main menu
function show_menu {
  $DIALOG --clear --backtitle "AutoBAHN Command Line Installer" --menu "AutoBAHN Installer Options" 20 80 9 "Add Tunnel" "Adds new tunnel to routing.conf" "Install Tunnels" "Installs tunnels detailed in routing.conf" "View Tunnels" "Shows existing tunnels"  "Tunnel Editor" "Edit Existing Tunnels" "Delete All Tunnels" "Deletes all existing tunnels" "Setup database" "Sets up a database from a PostgresSQL dump" "Property Editor" "Go back to change properties" "Help"  "Shows Help for the installer" "Exit" "Exits the Installer"  2>ans
  return $?
}
#Useful functions
readonly -f show_menu
declare -t show_menu


#Binds menu choices with bash functions
function decide {
	case $1 in
		"Add") 
			add_tunnel
		;;
		"Install") 
			install_tunnels
		;;
		"View") 
			view_tunnels	
		;;
		"Setup") 
			setup_database
		;;
		"Tunnel")
			tunnel_editor
		;;
		"Delete")
			delete_all_tunnels
		;;
		"Property")
			property_editor
		;;
		"Help")	show_help
		;;
		"Exit")	should_exit=1 
		;;
	esac
	return 0
}

readonly -f decide
declare -t decide

#Manages setting up the database in the simple command line interface
function setup_database_c {
	pushlocalinfo
	newlogparagraph "function setup_database_c"
	val_sql=0 #true
	while [ $val_sql -ne 1 ]; do
		printf "Please enter the path of a PSQL dump:"
		read FILE
		case $? in
			0)filename=${FILE##*/}
			  extension=${filename##*.}
			if [ $extension = "sql" ]; then
				dbname=`cat $path_only/tempdbname.tmp`
				echolog "dbname:$dbname path_only:$path_only"
                                rm -f tempdbname.tmp
                                dbuser=`cat $path_only/tempdbuser.tmp`
                                rm -f tempdbuser.tmp

				val_sql=1
                psql $dbname -U $dbuser < $FILE
				log "I ran psql $dbname -U $dbuser < $FILE, if unsuccessful either psql doesn't exist or the user doesn't have the appropriate permissions"
			fi
				  ;;
		esac	
	done
	rm -f ipans
}
readonly -f setup_database_c
declare -t setup_database_c

#Checks if the dialog command exists
function check_ui {
	res=`dialog`
	if [ $? -ne 0 ]; then
		return 1
	fi	
	return 0
}

function main_loop_with_gui {
	while [ $should_exit -ne 1 ]; do
		show_menu
		should_exit=$?
		ANS=`cat ans`
		decide $ANS
	done
}

function enter_ip_c_john {
	pushlocalinfo
	newlogparagraph "function enter_ip_c"
	val_ip=0 #true
	echo
	while [ $val_ip -ne 1 ]; do
		echo "---------------------------------------------------"
		echo $1
		echo "---------------------------------------------------"
		echo
		printf "$2: "
		read IP
		#valid_ip $IP
		#if [[ $? -eq 0 ]]; then
			val_ip=1
			current_ip=$IP
		#else
		#	echo
		 # 	echo "$IP is an invalid IPv4 address. Please enter a valid one! (X.X.X.X / 0<=X<=255)"
		#	echo
		#	val_ip=0
	#	fi
	done
	rm -f ipans
	poplocalinfo
	return 0
}

function enter_ip_c {
	pushlocalinfo
	newlogparagraph "function enter_ip_c"
	val_ip=0 #true
	echo
	while [ $val_ip -ne 1 ]; do
		echo "---------------------------------------------------"
		echo $1
		echo "---------------------------------------------------"
		echo
		printf "$2: "
		read IP
		valid_ip $IP
		if [[ $? -eq 0 ]]; then
			val_ip=1
			current_ip=$IP
		else
			echo
		  	echo "$IP is an invalid IPv4 address. Please enter a valid one! (X.X.X.X / 0<=X<=255)"
			echo
			val_ip=0
		fi
	done
	rm -f ipans
	poplocalinfo
	return 0
}

readonly -f enter_ip_c
declare -t enter_ip_c

# Function usage print_step "Step Number" "Step Description"
function print_step {
	clear
	echo "---------------------------------------------------------"
	echo "AutoBAHN Command Line Installer - Command Line Interface "
	echo "---------------------------------------------------------"
	echo
	echo "***************************************************"
	echo "STEP $1 - $2"
	echo "***************************************************"
	echo
}

#Manages installing tunels in the simple command line ui
function install_tunnels_c {
	pushlocalinfo
	newlogparagraph "function install_tunnels_c"
	echolog
	echolog "Installing tunnels ..." 
	$path_only/do-install.sh $path_only/routing.conf &>tunans
	echolog
	echolog "Installing returned:" 
	echolog "----------------------------------------------------"
	echolog "`cat tunans`"
	echolog "----------------------------------------------------"
	echolog
	rm -f tunans
	poplocalinfo
}

#Implements the simple command-line wizard-like ui
function simple_ui {
#	echo "In simple_ui ENTER_IP = $ENTER_IP"
	clear
	printf "Do you want to select sql file to setup database[y/n]:"
	read answer
	while true; do
		case "$answer" in
	          y|Y) #echo "do it";
		       setup_database_c
		       break;;
		  n|N) #echo "drop it";
		       break;;
		    *) echo "Please enter y or n"
                       read answer;;
		 esac
        done
	print_step "1" "Adding tunnels"
	ENTER_IP=enter_ip_c
        ENTER_IP_NO_CHECK=enter_ip_c_john
	while true; do
		printf "Do you want to enter a new tunnel[y/n]?"
		read answer
		case "$answer" in
	          y|Y) add_tunnel
		       ;;
		  n|N) echo "Tunnel addition terminated.";
		       break;;
		    *) echo "Please enter y or n"
                       read answer;;
		 esac
        done

	print_step "2" "Installing Tunnels"
	install_tunnels_c
#	sleep 3
#	clear	
}


graphics=yes
enterui=yes

#Function that runs in the end to clean up
function finalize {
	rm -f ans tunans remoteip localip localsubnet localrouterid network area $path_only/templocalinfostack
	if [ "$graphics" == yes ]; then
		clear
	fi
#	echo "ENTERUI when finalizing: $enterui"
}

#Prints help. It executes when the setup.sh is called with the -h parameter
function print_help {
	echo
	echo "AutoBAHN Installer command line Help"
	echo
	echo "Usage: $0 [OPTION]"
	echo
	printf "\t\t-c\tBegin command line interface\n
	        -n\tRun first-time installation scripts (self-check.sh)\n
		-e\tRun all installation scripts (setupall.sh)\n
		-b\tSetup database\n
		-a\tAdd a tunnel from command line\n
		-A\tAdd a tunnel automatically enter parameters \"remote_instance local_instance local_subnet local_router network area\"\n
		-x\tAdd and install tunnel from command line (Equivalent to -ai)\n
		-p\tDependency check\n
		-f\tProperty files editor\n
		-i\tOnly install tunnels from routing.conf\n
		-l\tShow installed tunnels\n
		-L\tShow all tunnels [I for Installed]\n
		-t\tShow tunnel editor\n
		-d [tunnel_name] Delete tunnel \"tunnel_name\"\n
		-D\tDelete all existing tunnels (use with caution)\n
		-h\tShow help\n\n"
}

############################################################################
##############################  MAIN  PROGRAM ##############################
############################################################################


readonly -f print_help
declare -t print_help
readonly -f finalize
declare -t finalize
readonly -f check_ui
declare -t check_ui
readonly -f main_loop_with_gui
declare -t main_loop_with_gui
readonly -f simple_ui
declare -t simple_ui

#Manages command-line options
while getopts "ncbxCialLhfDpted:A:" flag; do
	case $flag in
		n )enterui=no;
		if [ "$graphics" == "no" ]; then
		   $path_only/self-check.sh -c
		else
		   graphics=no
		   $path_only/self-check.sh
		fi
		;;
		c ) graphics=no
#  echo "GRAPHICS = NO!"
		;;
		C ) graphics=no
		;;
		i ) graphics=no;install_tunnels_c;enterui=no
		;;
		b ) graphics=no;enterui=no;setup_database_c
		;;
		a ) graphics=no;ENTER_IP=enter_ip_c;add_tunnel;enterui=no
		;;
		A ) graphics=no; enterui=no; 
#echo "argument $OPTARG"
			arg1=`echo $OPTARG|awk '{print $1}'`
			arg2=`echo $OPTARG|awk '{print $2}'`
			arg3=`echo $OPTARG|awk '{print $3}'`
			arg4=`echo $OPTARG|awk '{print $4}'`
			arg5=`echo $OPTARG|awk '{print $5}'`
			arg6=`echo $OPTARG|awk '{print $6}'`
			ADD_TUNNEL $arg1 $arg2 $arg3 $arg4 $arg5 $arg6
		;;
		x ) graphics=no;ENTER_IP=enter_ip_c;add_tunnel;install_tunnels_c;enterui=no
		;;
		l ) enterui=no;graphics=no; ip tunnel ls
		;;
		L ) enterui=no;graphics=no; echo;echo "I - already installed tunnel";
					    echo "U - to-be-installed tunnel";echo
					    $path_only/mark-tunnels.sh $path_only/routing.conf
		;;
		d)  enterui=no;graphics=no;ip tunnel del $OPTARG
		    echo "Tunnel $OPTARG was deleted!"
		;;
		D ) enterui=no;graphics=no;
		while true; do
			printf "Are you sure you want to delete all tunnels[y/n]?"
			read answer
			case "$answer" in
		          y|Y) $path_only/del-tunnels.sh
			       break;;
			  n|N) #echo "drop it";
			       break;;
			    *) echo "Please enter y or n";;
			 esac
        	done
	
		;;
		e ) enterui=no;graphics=no
			$path_only/setupall.sh
		;;
		f ) enterui=no;graphics=no
			$path_only/self-check.sh -f
		;;
		p ) enterui=no;graphics=no
			$path_only/self-check.sh -p
		;;
		t ) enterui=no;graphics=no
			tunnel_editor
			clear
			finalize
		;;
		h ) enterui=no;graphics=no; print_help
		;;
	esac
done

#This is where the decision is made about
#ncurses or simple command-line interface
if [ "$enterui" == yes ]; then
	newlog "setup.sh"
	check_ui
	if [ $? -eq 0 ] && [ "$graphics" == yes  ]; then
		
		main_loop_with_gui

	else
		simple_ui
	fi
fi

finalize