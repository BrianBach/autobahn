#!/usr/bin/env bash

let "using_debian=0"
abspath=$(cd ${0%/*} && echo $PWD/${0##*/})

# to get the path only - not the script name - add
path_only=`dirname "$abspath"`

#include logging mechanism
source $path_only/log.sh

#This file performs a self-check to find if AutoBAHN dependencies are 
#available and in place.

#Self-check performs a series of sanity checks to ensure
#everything needed is in place
declare -rx SCRIPT=${0##*/}
declare -rx java=`which java`
declare -rx psql=`which psql`
declare -x quagga
declare -x INSTALL_COMMAND
declare -x CONFIGURE_FILE_QUAGGA="configure_file_quagga_c"
declare -x CONFIGURE_FILE_AUTOBAHN="configure_file_autobahn_c"
declare -x DIALOG=${DIALOG=dialog}
declare -r quagga_zebra=`which zebra`
declare -r debian_quagga="/etc/init.d/quagga"
declare -r ip_gre_loaded=`lsmod | grep ip_gre`
declare -x DBNAME="autobahn"
declare -x DBPASS
declare -x DBUSER
declare -x DBHOST
declare -x DBPORT
declare -x DBTYPE
declare -x TOPOLOGYFILE
declare -x GRAPHICAL
declare -x AUTOBAHN_FOLDER


echo

#Performs dependency checks
function perform_checks {
	check_ui
	if [ $? -eq 0 ] && [ "$graphics" == yes  ]; then
		LOGGER_GRAPHICAL="yes"
	else
		LOGGER_GRAPHICAL="no"
	fi
	pushlocalinfo
	newlogparagraph "function perform_checks"
#LOCAL_INFO is for the logging mechanism
	LOCAL_INFO="function perform_checks"	
#Check for debian-based distribution
	apt-get install &>/dev/null
	if [ $? -ne 127 ]; then
		pushlog "Debian package management detected."
		INSTALL_COMMAND="apt-get install"
		((debian=1))
	fi	

	#Check for redhat-based distribution
	yum install &>/dev/null
	if [ $? -ne 127 ]; then
		pushlog "Redhat package management detected.\n"
		INSTALL_COMMAND="yum install"
		((redhat=1))
	fi	


	# More sophisticated checking is possible (what if ip_gre doesn't exist?)
	#Module checking
	log "IP_GRE:$ip_gre_loaded"
	if test -z "$ip_gre_loaded"; then
		pushlog "Must load ip_gre module, please enter root pass or run script with root permissions(you must be a sudoer or else run the installer as root):"
		sudo modprobe ip_gre
	fi

	if test ! -x "$java"; then
	   pushlog "$SCRIPT:$LINENO: Sun Java 1.5+ is not available. Self-check failed. Please install Sun Java 1.5 or higher and retry! \n" >&2
	   popecholog
	   finalize
	   exit 192
	else
		ver=`"$java" -version &> javaver.txt && cat javaver.txt | grep version | awk -F "\"" '{print $2}'`
	#echo "Java Version detected: ${ver:0:3}"	
		l_d=${ver:2:1}
		if [ $l_d -ge 5 ]; then
	   	 pushlog "Java installation test passed! Java version: ${ver:0:3}"
		fi
  	rm -f javaver.txt  
	fi

	if test ! -x "$psql"; then
   		pushlog "$SCRIPT:$LINENO: PostgresSQL not found! Please install PostgreSQL 8.x or higher and retry!\n" >&2
	popecholog
	finalize
   	exit 192
	fi


	if test ! -x "$quagga_zebra" -a ! -x "$debian_quagga"; then
   		pushlog "$SCRIPT:$LINENO: Quagga routing package is not available. Please install Quagga 0.99.6 and retry!\n" >&2
		popecholog
		finalize
	        exit 192
	elif test -x "$quagga_zebra"; then
	   	quagga=`dirname "$quagga_zebra"`
	   	pushlog "Quagga installation detected."
	elif test -x "$debian_quagga"; then
		 quagga=$debian_quagga
   		let "using_debian=1"
  		 pushlog "Debian quagga installation detected."
	fi
	pushlog	""
	pushlog "*PRELIMINARY PREREQUISITES OK*"
	pushlog ""	
	popecholog
	poplocalinfo
}

#Adds a new/or changes existing attribute to the installer configuration
#add_attribute new_attribute file
function add_attribute {
	pushlocalinfo
	newlogparagraph "add_attribute"
	initkey=`echo $1 | awk -F "=" '{print $1}'`
	initvalue=`echo $1 | awk -F "=" '{print $2}'`
	echo "$initkey" | sed "s/\//\\\\\//g" >key
	echo "$initvalue" | sed "s/\//\\\\\//g" >value
	key=`cat key`
	value=`cat value`
	if [ ! -e $2 ]; then 
		touch $2
		echo "$initkey=$initvalue" >> $2
	else
		cat "$2" | sed "s/$key=.*/$key=$value/;//h;\$G;s/\n..*//;s/\n/&$key=$value/"  &>tempfile
		mv tempfile $2
		rm -f tempfile
	fi
	log "$initkey=$initvalue was written in $2"
	poplocalinfo
}
## Asks the user configuration info for Quagga in a non graphical way
#configure_file_quagga_c filename path attribute_name
function configure_file_quagga_c {
	     pushlocalinfo
	     newpath=$2
             newpath_john=$3
             while [[ ! -d "$newpath" && ! -f "$newpath" ]]; do
                     echo -n "Enter the system folder (end with a /) where $1 is located (e.g. /etc/quagga/): "
		     read newpath
		     add_attribute "$3=$newpath" "$path_only/installer.conf"
		     source $path_only/installer.conf
		     echolog "$1 is at $newpath"

             # Kostas-Giannis addition to copy conf files to quagga etc folder
             newpath_path_only=$(cd ${newpath%/*} && echo $PWD)
             cp ./ospfd.conf $newpath_path_only
             cp ./debian.conf $newpath_path_only
             cp ./daemons $newpath_path_only
             cp ./zebra.conf $newpath_path_only
             echolog "Copied ospfd.conf, debian.conf, daemons, zebra.conf to $newpath_path_only"
	    done 
	    
             while [[ ! -d "$newpath_john" && ! -f "$newpath_john" ]]; do
                     echo -n "Please enter the path to start Quagga daemons (end path with a /): " 
		     read newpath_john 
                     echo $newpath_john > startDaemons.tmp
                     echolog $newpath_john
		     #export $newpath_john
		     add_attribute "daemons=$newpath_john" "$path_only/installer.conf"
		     #source $path_only/installer.conf
		     #echolog "$1 is at $newpath"
             done
	    poplocalinfo	
}

## Asks the user configuration info for autobahn in a non graphical way
#configure_file_autobahn_c filename path attribute_name
function configure_file_autobahn_c {
	     pushlocalinfo
	     newpath=$2
             while [[ ! -d "$newpath" && ! -f "$newpath" ]]; do
                     echo -n "Please enter $1 path: " 
		     read newpath
		     add_attribute "$3=$newpath" "$path_only/installer.conf"
		     source $path_only/installer.conf
		     echolog "$1 is at $newpath"
	    done 
	    poplocalinfo	
}

#file_editor presents an ncurses form which shows the basic attributes of a file and permits editing them
#Usage: file_editor file property1 value1 property2 value2 .. propertyn valuen
function file_editor {
	pushlocalinfo
	newlogparagraph "function file_editor"
	conf_file="$1"
	shift
	c=0
	y1=2
	x1=4
	
	y2=2
	x2=22
	flen=20 
	ilen=100
	num_of_prop=0
	until [ $# -eq 0 ]; do
		cur_property="$1";shift		
		cur_propval="$1";shift
		echo "$cur_property " >> $path_only/cur_properties
		echo -n "$cur_property $y1 $x1 $cur_propval $y2 $x2 $flen $ilen " >> $path_only/temp_editor
		((y1+=2))
		((y2+=2))
		((num_of_prop+=1))
      done
      properties=`cat $path_only/temp_editor`

      exec 3>&1
     cmd=$($DIALOG --backtitle "Editing $conf_file" --title "Edit most important attributes"  --form "Use [up] [down] arrows to select input field" 31 80 31  $properties 2>&1 1>&3) 
     exec 3>&-

#If user pressed Cancel    
     if [ -z "$cmd" ]; then
	log "User pressed Cancel while editing $conf_file"
	rm -f cur_properties temp_editor
	return 1
     fi
     log "CMD = $cmd num_of_properties = $num_of_prop"

    echo "$cmd" >> $path_only/cur_properties 
    i=0
    reqval=0
    log "to cur_prop `cat $path_only/cur_properties`"
    cat $path_only/cur_properties | while read line; do 
    	((i++))	
	[[ $i -gt $num_of_prop ]] && break
	((reqval=$i+$num_of_prop))
	valnum=$reqval"p"
	val=$(sed -n "$valnum" $path_only/cur_properties)
	add_attribute "$line=$val" "$conf_file"
    done
    rm -f cur_properties temp_editor
    poplocalinfo
}      
     


#Configuring a Quagga file in an ncurses env
function configure_file_quagga {
	     pushlocalinfo
	     newlogparagraph "function configure_file_quagga"
	     newpath=$2
             newpath_john=$3
	     while [[ ! -d "$newpath" && ! -f "$newpath" ]]; do
		    FILE=`$DIALOG --stdout --title "Enter system folder (end path with a /) containing $1 (e.g. /etc/quagga/) " --fselect $HOME/$1 24 78`
		     case $? in
		        0   )   
		     	      echo $FILE >newpath
			      newpath=$FILE
			      ;;
			255 ) break;;
		     esac
		     newpath=`cat newpath` 
		     add_attribute "$3=$newpath" "$path_only/installer.conf"
		     source $path_only/installer.conf
 		     log "$1 is at $newpath"

             # Kostas-Giannis addition to copy conf files to quagga etc folder
             newpath_path_only=$(cd ${newpath%/*} && echo $PWD)
             cp ./ospfd.conf $newpath_path_only
             cp ./debian.conf $newpath_path_only
             cp ./daemons $newpath_path_only
             cp ./zebra.conf $newpath_path_only
             echolog "Copied ospfd.conf, debian.conf, daemons, zebra.conf to $newpath_path_only"
	    done 
	    
            # while [[ ! -d "$newpath_john" && ! -f "$newpath_john" ]]; do
                     FILE=`$DIALOG --stdout --title "Enter system folder (end path with a /) containing the scripts to run Quagga daemons (e.g. /etc/init.d/) " --fselect $HOME/$1 24 78`
		     #read newpath_john
                    
		     case $? in
		        0   )   
		     	      echo $FILE >newpath_john
			      newpath_john=$FILE
                              echolog $newpath_john
			      ;;
			255 ) break;;
		     esac
		     newpath_john=`cat newpath` 
		     echolog $newpath_john
		     #export $newpath_john
		     add_attribute "daemons=$newpath_john" "$path_only/installer.conf"
		     #source $path_only/installer.conf
		     #echolog "$1 is at $newpath"
             #done
	    poplocalinfo	
}

#Configuring an Autobahn file in an ncurses env
function configure_file_autobahn {
	     pushlocalinfo
	     newlogparagraph "function configure_file_autobahn"
	     newpath=$2
             while [[ ! -d "$newpath" && ! -f "$newpath" ]]; do
		    FILE=`$DIALOG --stdout --title "Enter $1 folder (end with a /): " --fselect $HOME/$1 24 78`
		     case $? in
		        0   )   
		     	      echo $FILE >newpath
			      newpath=$FILE
			      ;;
			255 ) break;;
		     esac
		     newpath=`cat newpath` 
		     add_attribute "$3=$newpath" "$path_only/installer.conf"
		     source $path_only/installer.conf
 		     log "$1 is at $newpath"
	    done 
	    poplocalinfo	
}

#Parses the db and stores important information  
#into appropriate variables
function read_db_info_from_dm {
	pushlocalinfo
	newlogparagraph "function read_db_info_from_dm"
	propfile=`cat $1`
		for line in $propfile; do
			#curprop has the current property that is read
			#and curval its value
			curprop=`echo $line | awk -F "=" '{print $1}'`
			curval=`echo $line |awk -F "=" '{print $2}'`
			case $curprop in 
			  db.host ) DBHOST=$curval
			  ;;
			  db.port ) DBPORT=$curval
			  ;;
			  db.name ) DBNAME=$curval; DBNAME=$curval
		          ;;
			  db.user ) DBUSER=$curval; DBUSER=$curval
			  ;;
			  db.pass ) DBPASS=$curval; DBPASS=$curval
		   	  ;;
			  db.type ) DBTYPE=$curval
		          ;;
			  monitoring.use ) monitoring_use=$curval
		   	  ;;
			esac
		done
	poplocalinfo 
}

#Changes a property in a file
#change_property property value file
function change_property {
	pushlocalinfo
	key=$1
	value=$2
	file=$3
	add_attribute "$1=$2" "$file"
	poplocalinfo
}


declare -x ASK_FOR_PARAMETER
#Asks for parameter info from the command line
#ask_for_parameter parameter default_value
function ask_for_parameter_c {
	pushlocalinfo
	echo -n "Please enter the desired value of parameter $1 (default $2): "
 	read -e t1
	if [ -n "$t1" ]; then
		echo $t1 > retval
	else
		echo $2 > retval
	fi
	poplocalinfo
}

#Asks for parameter info using a ncurses dialog 
#ask_for_parameter parameter default_value
function ask_for_parameter {
	pushlocalinfo
	$DIALOG --title "Enter value of $1" --inputbox "Please enter the desired value of parameter $1 (default $2):" 9 40  2>ret 	
	t1=`cat ret`
	if [ -n "$t1" ]; then
		echo $t1 > retval
	else
		echo $2 > retval
	fi
	rm -f ret	
	poplocalinfo
}

#Asks and changes many properties at once in a file
#change_properties file property1 property2 ...
function change_properties {
	pushlocalinfo
	newlogparagraph "function change_properties"
	(( i=1 ))
	(( firsttime=0 ))
	myfile=""
	par=""
	val=""
	for p in $*;
	do
		if [ $i -eq 1 ]; then
			myfile=$1
			(( i=i+1 ))
		else
			if [ $firsttime -eq 0 ]; then
				par="$p";
				(( firsttime=1 ))
			else
				val="$p"
				(( firsttime=0 ))
#echo "$par=$val"
				$ASK_FOR_PARAMETER $par $val
				retval=`cat retval`
				change_property $par $retval $myfile
			fi
#echo "Parameter: [$p]"
		fi
	done;
	log "File to be written: *$myfile*"
	poplocalinfo
}

#if dm.properties exists the default values are the existing
#else other values are proposed
#get_dm_defaults dm_properties_file
function get_dm_defaults {
	pushlocalinfo
	newlogparagraph "function get_dm_defaults"	
	declare db_host db_port db_name db_user db_pass db_type
	db_host=localhost
	db_port=5432
	db_name=autobahn_2
	db_user=jra3
	db_pass="pass"
	db_type=ethernet
	tool_address=none
	tool_time_setup=120
	tool_time_teardown=60	
	lookuphost="http://ls-host:8080/perfsonar-java-xml-ls/services/LookupService"
	monitoring_use=""
	log "The dm file is $1"
	if [ -e "$1" ]; then
		propfile=`cat $1`
		for line in $propfile; do
			#curprop has the current property that is read
			#and curval its value
			curprop=`echo $line | awk -F "=" '{print $1}'`
			curval=`echo $line |awk -F "=" '{print $2}'`
#echo "Curprop=$curprop Curval=$curval";sleep 5
			case $curprop in 
			  db.host ) db_host=$curval
			  ;;
			  db.port ) db_port=$curval
			  ;;
			  db.name ) db_name=$curval; DBNAME=$curval
		      ;;
			  db.user ) db_user=$curval; DBUSER=$curval
			  ;;
			  db.pass ) db_pass=$curval; DBPASS=$curval
		   	  ;;
			  db.type ) db_type=$curval
		      ;;
			  tool.address ) tool_address=$curval
		      ;;
			  tool.time.setup ) tool_time_setup=$curval
		      ;;
			  tool.time.teardown ) tool_time_teardown=$curval
		      ;;
			  lookuphost ) lookuphost=$curval
		      ;;
			  monitoring.use ) monitoring_use=$curval
		   	  ;;
			esac
		done
	fi	
	log "db.host ${db_host} db.port ${db_port} db.name ${db_name} db.user ${db_user} db.pass ${db_pass} db.type ${db_type} tool.address ${tool_address} tool.time.setup ${tool_time_setup} tool.time.teardown ${tool_time_teardown} lookuphost ${lookuphost}"| tr -d '\r' 
	echo -n "db.host ${db_host} db.port ${db_port} db.name ${db_name} db.user ${db_user} db.pass ${db_pass} db.type ${db_type} tool.address ${tool_address} tool.time.setup ${tool_time_setup} tool.time.teardown ${tool_time_teardown} lookuphost ${lookuphost}"| tr -d '\r' > $path_only/dm_defaults
	poplocalinfo
}

function get_idm_defaults {
	pushlocalinfo
	newlogparagraph "function get_idm_defaults"
	domain="http://your-host:8080/autobahn/interdomain"
	domainName="http://some_domain:8080/autobahn/interdomain"
	latitude="0.000000"
	longitude="0.000000"
	ospf_use=true
	ospf_opaqueType=135
	ospf_opaqueId=1001
	db_host=localhost
	db_port=5432
	db_name=jra3_1
	db_user=jra3
	db_pass="pass"
	gui_address="http://gui-host:8080/autobahn-gui/service/gui"
	lookuphost="http://ls-host:8080/perfsonar-java-xml-ls/services/LookupService"
	if [ -f $1 ]; then
		propfile=`cat $1`
		for line in $propfile; do
			curprop=`echo $line | awk -F "=" '{print $1}'`
			curval=`echo $line |awk -F "=" '{print $2}'`
			if [ -z $curval ]; then
				continue
			fi
			case $curprop in 
			  domain ) domain=$curval
			  ;;
			  domainName ) domainName=$curval
			  ;;
			  latitude ) latitude=$curval
			  ;;
			  longitude ) longitude=$curval
			  ;;
			  ospf.use ) ospf_use=$curval
		      ;;
			  ospf.opaqueType ) ospf_opaqueType=$curval
		      ;;
			  ospf.opaqueId ) ospf_opaqueId=$curval
		      ;;
			  db.host ) db_host=$curval
			  ;;
			  db.port ) db_port=$curval
			  ;;
			  db.name ) db_name=$curval
		      ;;
			  db.user ) db_user=$curval
			  ;;
			  db.pass ) db_pass=$curval
		   	  ;;
			  gui.address ) gui_address=$curval
		      ;;
			  lookuphost ) lookuphost=$curval
		      ;;
			esac
		done
	fi	
	log "domain $domain domainName $domainName latitude $latitude longitude $longitude ospf.use $ospf_use ospf.opaqueType $ospf.opaqueType ospf.opaqueId $ospf.opaqueId db.host $db_host db.port $db_port db.name $db_name db.user $db_user db.pass $db_pass gui.address $gui_address lookuphost $lookuphost"
	echo -n "domain $domain domainName $domainName latitude $latitude longitude $longitude ospf.use $ospf_use ospf.opaqueType $ospf.opaqueType ospf.opaqueId $ospf.opaqueId db.host $db_host db.port $db_port db.name $db_name db.user $db_user db.pass $db_pass gui.address $gui_address lookuphost $lookuphost" | tr -d '\r' > $path_only/idm_defaults
	poplocalinfo
}

function get_calendar_defaults {
	pushlocalinfo
	newlogparagraph "function get_calendar_defaults"
	db_type=ethernet
	tool_time_setup=120
	tool_time_teardown=60
	if [ -f $1 ]; then
		propfile=`cat $1`
		for line in $propfile; do
			curprop=`echo $line | awk -F "=" '{print $1}'`
			curval=`echo $line |awk -F "=" '{print $2}'`
			case $curprop in 
			  db.type ) db_type=$curval
		          ;;
			  tool.time.setup ) tool_time_setup=$curval
		          ;;
			  tool.time.teardown ) tool_time_teardown=$curval
		          ;;
			esac
		done
	fi	
	log "db.type $db_type tool.time.setup $tool_time_setup tool.time.teardown $tool_time_teardown" 
	echo -n "db.type $db_type tool.time.setup $tool_time_setup tool.time.teardown $tool_time_teardown" | tr -d '\r' > $path_only/calendar_defaults
	poplocalinfo
}

function get_framework_defaults {
	pushlocalinfo
	newlogparagraph "function get_framework_defaults"
	framework_commandLine=localhost
	framework_port=5000 
	if [ -f $1 ]; then
		propfile=`cat $1`
		for line in $propfile; do
			curprop=`echo $line | awk -F "=" '{print $1}'`
			curval=`echo $line |awk -F "=" '{print $2}'`
			case $curprop in 
		          framework.commandLine ) framework_commandLine=$curval
			  ;;
		          framework.port ) framework_port=$curval
			  ;;
			esac
		done
	fi	
	log "framework.commandLine $framework_commandLine framework.port $framework_port" 
	echo -n "framework.commandLine $framework_commandLine framework.port $framework_port" | tr -d '\r' > $path_only/framework_defaults
	poplocalinfo
}


function get_ta_defaults {
	pushlocalinfo
	newlogparagraph "funciton get_ta_defaults"
	id_nodes=10.10.0.0/24
	id_ports=10.10.32.0/24
	id_links=10.10.64.0/24
	lookuphost="http://ls-host:8080/perfsonar-java-xml-ls/services/LookupService"
	if [ -f $1 ]; then
		propfile=`cat $1`
		for line in $propfile; do
			curprop=`echo $line | awk -F "=" '{print $1}'`
			curval=`echo $line |awk -F "=" '{print $2}'`
			case $curprop in 
			  id.nodes ) id_nodes=$curval
		          ;;
			  id.ports ) id_ports=$curval
		          ;;
			  id.links ) id_links=$curval
		          ;;
			  lookuphost ) lookuphost=$curval
		          ;;
			esac
		done
	fi	
	log "id.nodes $id_nodes id.ports $id_ports id.links $id_links lookuphost $lookuphost" 
	echo -n "id.nodes $id_nodes id.ports $id_ports id.links $id_links lookuphost $lookuphost" > $path_only/ta_defaults
	poplocalinfo
}

###The following functions create the relevant configuration files
###create_x_properties path_to_property_file

function create_dm_properties {
         get_dm_defaults "$1"
	 all_properties=`cat $path_only/dm_defaults`
	 change_properties "$1" $all_properties
}

function create_idm_properties {
	 get_idm_defaults "$1"
	 all_properties=`cat $path_only/idm_defaults`
	 change_properties "$1" $all_properties
}

function create_calendar_properties {
	 get_calendar_defaults "$1"
	 all_properties=`cat $path_only/calendar_defaults`
	 change_properties "$1" $all_properties
}

function create_ta_properties {
	 get_ta_defaults "$1"
	 all_properties=`cat $path_only/ta_defaults`
	 change_properties "$1" $all_properties
}


declare -x CREATE_CONF
#Checks if a specific configuration file exists and if not
#calls the CREATE_CONF to create it. Works for both ncurses
#and simple command line ui
#check_conf_file file path
function check_conf_file {
	pushlocalinfo
	newlogparagraph "function check_conf_file"
	echo
	if [ ! -f "$1"  ]; then
	 #Create dm.properties - doesn't exist
		if [ $GRAPHICAL="no" ]; then
			echolog "$1 doesn't exist! Will try to create it!"
		else
			$DIALOG --title "$1 does not exist" --msgbox "$1 doesn't exist. Will try to create it!" 8 67
			log "$1 doesn't exist! Will try to create it!"
		fi
		$CREATE_CONF "$1"
	else
##Now the default action is to proceed if there is a record in installer.conf
		if [[ "$GRAPHICAL" == "no" ]]; then
			echo -n "$1 exists. Do you want to recreate it[y/n] ?"
			read answer
		else
			$DIALOG --title "Recreation of $1" --keep-window --yesno "$1 exists. Do you want to recreate it?" 8 69
			if [ $? -eq 0 ]; then
				answer="y"
			else
				answer="n"
			fi
		fi
		case "$answer" in
			y|Y)
				$CREATE_CONF "$1"
			;;
			n|N)  if [[ "$GRAPHICAL"="no" ]]; then
				echolog "$1 will be kept as is."
			      else
				$DIALOG --title "No action will be taken" --msgbox "The file $1 will be kept as is" 8 67
				log "$1 will be kept as is."
			      fi
			;;
			*)   echolog "Assumed NO. $1 will be kept as is."
			;;
		esac
#		fi
	fi
}

function get_default_autobahn_folder {
	pushlocalinfo
	newlogparagraph "function get_default_autobahn_folder"
	if [ ! -f $path_only/installer.conf ]; then
		echo "ospfd_conf=$path_only/ospfd.conf" > $path_only/installer.conf
		echo "autobahn_folder=$path_only" >> $path_only/installer.conf
	fi

	source $path_only/installer.conf
	if [ ! -d "$autobahn_folder" ]; then
		log "didn't find auto fold and will recreate"
		autobahn_folder=$(cd ${path_only} && cd .. && echo $PWD/${0##*/})
	        autobahn_folder=`dirname "$autobahn_folder"`
		AUTOBAHN_FOLDER=$autobahn_folder
#$CONFIGURE_FILE_AUTOBAHN "autobahn folder" $autobahn_folder "autobahn_folder"
	else
#echo "FOUND autofolder=$autobahn_folder"
		autobahn_folder=$(cd ${autobahn_folder%/*} && echo $PWD/${0##*/})
	        autobahn_folder=`dirname "$autobahn_folder"`
		AUTOBAHN_FOLDER=$autobahn_folder
		log "AUTOBAHN_FOLDER=$AUTOBAHN_FOLDER"
	fi
#echo "autobahn folder is $autobahn_folder and installer conf is `cat $path_only/installer.conf`";sleep 5
	poplocalinfo
}

#Takes care of configuration files 
function check_configuration_files {
	pushlocalinfo
	newlogparagraph "function check_configuration_files"
	if [ ! -e $path_only/installer.conf ]; then
		echo "ospfd_conf=$path_only/ospfd.conf" > $path_only/installer.conf
		echo "autobahn_folder=$path_only" >> $path_only/installer.conf
	fi

	source $path_only/installer.conf
	ospfd_path=$(cd ${ospfd_conf%/*} && echo $PWD/${0##*/})
	ospfd_path=`dirname "$ospfd_path"`
	ospfd_conf="$ospfd_path/ospfd.conf"

	$CONFIGURE_FILE_QUAGGA "ospfd.conf" "$ospfd_conf" "ospfd_conf"

	##Check ip permissions
	ip tunnel add gre
	if [ $? -ne 0 ]; then
		echolog "*ERROR*: You need to adjust permissions of the \"ip\" command.Either run installer as root or set the suid bit of ip command e.g. chmod +s `which ip` "
		exit 1
	fi

	echo 
	if [ ! -d "$autobahn_folder" ]; then
		autobahn_folder=$(cd ${path_only} && cd .. && echo $PWD/${0##*/})
	        autobahn_folder=`dirname "$autobahn_folder"`
		$CONFIGURE_FILE_AUTOBAHN "autobahn folder" $autobahn_folder "autobahn_folder"
	else
		autobahn_folder=$(cd ${autobahn_folder%/*} && echo $PWD/${0##*/})
	        autobahn_folder=`dirname "$autobahn_folder"`
	fi

	CREATE_CONF=create_dm_properties
	check_conf_file "$autobahn_folder/etc/dm.properties"		
	#parse dm.properties get dbname and password and run
	CREATE_CONF=create_idm_properties
	check_conf_file "$autobahn_folder/etc/idm.properties"		
	CREATE_CONF=create_calendar_properties
	check_conf_file "$autobahn_folder/etc/calendar.properties"	
	CREATE_CONF=create_ta_properties
	check_conf_file "$autobahn_folder/etc/ta.properties"		
	poplocalinfo
}

#Checks if dialog command is available
function check_ui {
	dialog &>/dev/null
	if [ $? -ne 0 ]; then
		return 1
	fi	
	return 0
}

#Initializes the ncurses interface
function main_loop_with_gui {
	pushlocalinfo
	newlogparagraph "function main_loop_with_gui"		
	GRAPHICAL="yes"
	LOGGER_GRAPHICAL="yes"
	CONFIGURE_FILE_QUAGGA="configure_file_quagga"
	CONFIGURE_FILE_AUTOBAHN="configure_file_autobahn"
	ASK_FOR_PARAMETER="ask_for_parameter"
		$DIALOG --title "AutoBAHN Installer" --keep-window --yesno "Do you want the property wizard to begin(Yes) or the Property Editor(No). If unsure choose Yes." 10 80
	if [ $? -eq 0 ]; then
		check_configuration_files 
	else
		get_default_autobahn_folder
		autobahn_folder=$AUTOBAHN_FOLDER
		config_editor 
		clear
	fi
	poplocalinfo
}

#Initializes the simple command-line interface
function simple_ui {
	pushlocalinfo
	newlogparagraph "function simple_ui"
	GRAPHICAL="no"
	LOGGER_GRAPHICAL="no"
	CONFIGURE_FILE_QUAGGA="configure_file_quagga_c"
	CONFIGURE_FILE_AUTOBAHN="configure_file_autobahn_c"
	ASK_FOR_PARAMETER="ask_for_parameter_c"
	check_configuration_files 
	poplocalinfo
}

graphics=yes
enterui=yes
#Final actions
function finalize {
	if [ "$graphics" == yes ]; then
		clear
	fi
	rm -f retval key value   $path_only/idm_defaults  $path_only/calendar_defaults  $path_only/ta_defaults temp_editor temp_prop ans $path_only/cur_properties $path_only/dm_defaults $path_only/templocalinfostack $path_only/framework_defaults
}

#init_db
function init_db {
	  pushlocalinfo
	  newlogparagraph "init_db"
	  export dbname="$1"
echolog "Exported dbname=$dbname"
          export PGPASSWORD="$2"
          export dbuser="$3"
echolog "Exported dbuser $dbuser"
          sudo -u postgres createuser --superuser $dbuser
          cmd="sudo -u postgres psql template1  -c \"create database $dbname\" -t > /dev/null 2>&1"
     echolog "Create dbuser performed."
          eval $cmd
          if [ $? -ne 0 ]; then
	        echolog "Database $dbname already existed."
	  else
                echolog "Database $dbname was created."
		echolog "Proceeding to create the db structure..."
		sudo -u postgres psql $dbname < $path_only/create_db.sql
		if [ $? -ne 0 ]; then
			echolog "An error occured while creating the  database structure! Please make sure that the user running this program has the permissions to modify $dbname and try again or run manually create_db.sql!"
		fi
	  fi
	  poplocalinfo
}

#init_ospfd initializes ospfd
#in debian-based systems it assumes that in /etc/quagga/daemons
#ospfd is enabled (which is by default)
function init_ospfd {
	pushlocalinfo
	newlogparagraph "init_ospfd"
	if test -x "$debian_quagga"; then
		log "Will try to run debian quagga ospfd..."
		$newpath_john restart &> $path_only/quaggares
		#echolog "testjohn" $newpath_john
		log "Quagga returned: `cat $path_only/quaggares`"
	else if test -x "$quagga_zebra"; then
		log "Will try to run ospfd ..."
		$quagga/ospfd &> $path_only/quaggares
		echolog "Quagga returned: `cat $path_only/quaggares`"
	     else 
		echolog "ospfd not found"
	     fi
	fi
	rm -f $path_only/quaggares 
	poplocalinfo
}

function config_editor {
	pushlocalinfo
	newlogparagraph "function config_editor"
        get_default_autobahn_folder
        autobahn_folder=$AUTOBAHN_FOLDER
	should_exit=0
	while [ $should_exit -ne 1 ]; do
		$DIALOG --clear --backtitle "AutoBAHN configuration files editor" --menu "AutoBAHN configuration files" 15 80 8 "dm.properties" "Sets properties about the Domain Manager" "idm.properties" "Sets properties about the Interdomain Manager" "ta.properties" "Sets properties about the Topology Abstraction" "calendar.properties" "Sets properties about the Calendar" "framework.properties" "Sets properties about the Framework" "Back" "Return to previous menu" 2>ans
		case $? in 
		    255 ) return 1
		    should_exit=1
		    ;;
		esac
        	choice=`cat ans`
		case $choice in 
			"dm.properties" )
	        	   get_dm_defaults "$autobahn_folder/etc/dm.properties"
			   log "Will call file_editor $autobahn_folder/etc/dm.properties `cat $path_only/dm_defaults`"
			   dm_def=`cat $path_only/dm_defaults`
			   file_editor "$autobahn_folder/etc/dm.properties" `cat $path_only/dm_defaults`

			;;
			"idm.properties" )
		   	   get_idm_defaults "$autobahn_folder/etc/idm.properties"
#  echo "IDM_DEFAULTS: `cat $path_only/idm_defaults`";sleep 3
			   log "Will call file_editor $autobahn_folder/etc/idm.properties `cat $path_only/idm_defaults`"
		   	   file_editor "$autobahn_folder/etc/idm.properties" `cat $path_only/idm_defaults`
		;;
			"ta.properties" )
			   get_ta_defaults "$autobahn_folder/etc/ta.properties"
			   log "Will call file_editor $autobahn_folder/etc/ta.properties `cat $path_only/ta_defaults`"
			   file_editor "$autobahn_folder/etc/ta.properties" `cat $path_only/ta_defaults`
	;;
			"calendar.properties" )
			   get_calendar_defaults "$autobahn_folder/etc/calendar.properties"
			   log "Will call file_editor $autobahn_folder/etc/calendar.properties `cat $path_only/calendar_defaults`"
		   	   file_editor "$autobahn_folder/etc/calendar.properties" `cat $path_only/calendar_defaults`

		;;
			"framework.properties" )
			    get_framework_defaults "$autobahn_folder/etc/framework.properties"
			   log "Will call file_editor $autobahn_folder/etc/framework.properties `cat $path_only/framework_defaults`"
		   	   file_editor "$autobahn_folder/etc/framework.properties" `cat $path_only/framework_defaults`

;; 
			"Back" )
			   should_exit=1
		 	;;
	esac
    done
    poplocalinfo
}


function print_help {
	echo "AutoBAHN command line Help"
	echo
	echo "Usage: $0 [OPTION]"
	echo
	printf "\t\t-c\tBegin command line interface\n
		-p\tPerform only preliminary tests\n
		-f\tAttribute editor\n
	       	-h\tShow help\n\n"
}

###############################################################
################### M A I N  P R O G R A M ####################
###############################################################

#Manage command line arguments
while getopts "cChpf" flag; do
	case $flag in
		c )graphics=no
		;;
		C )graphics=no
		;;
		p )enterui=no;graphics=no
		   perform_checks
		;;
		f )enterui=no; graphics=no
		   get_default_autobahn_folder
		   autobahn_folder=$AUTOBAHN_FOLDER
		   config_editor 
		   clear
		;;
		h ) enterui=no;graphics=no
		    print_help
		;;
	esac
done

if [ "$enterui" == yes ]; then
	newlog
	perform_checks
	echo
	check_ui
	if [ $? -eq 0 ] && [ "$graphics" == yes  ]; then
		main_loop_with_gui
	else
		simple_ui
	fi
	read_db_info_from_dm $autobahn_folder/etc/dm.properties
	init_db $DBNAME $DBPASS $DBUSER
        echo $DBNAME > tempdbname.tmp
        echo $DBUSER > tempdbuser.tmp
	init_ospfd
fi

finalize
																					       
