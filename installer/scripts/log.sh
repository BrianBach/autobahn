# This file contains the logging functions
# To use it from another script use: source log.sh

DIALOG=${DIALOG=dialog}
abspath=$(cd ${0%/*} && echo $PWD/${0##*/})

# to get the path only - not the script name - add
path_only=`dirname "$abspath"`

# Usage: set INSTALLER_LOG to the file you want as log
# set LOGGER_GRAPHICAL to yes for graphical interface
# set LOCALINFO contain the info about local debugging information
export INSTALLER_LOG=$path_only/installer.log
export LOGGER_GRAPHICAL="no"
export LOCAL_INFO=""

#TEMP_LOG and ECHO_LOG is used for pushlog poplog popecholog
TEMP_LOG=$path_only/templog.log
ECHO_LOG=$path_only/echolog.log

# Makes log entries seem separate for better inspection
# Usage newlog [script]
function newlog {
	scriptname=""
	if [[ ! -z $1 ]]; then
		scriptname=$1
	fi
	echo "" >> $INSTALLER_LOG
	echo "" >> $INSTALLER_LOG
	echo "***********************************************************" >> $INSTALLER_LOG
	echo "***********************************************************" >> $INSTALLER_LOG
	echo "-----------------------------------------------------------" >> $INSTALLER_LOG
	echo "New log entry: `date`" >> $INSTALLER_LOG
	if [[ ! -z $scriptname ]]; then
		echo "SCRIPT: $scriptname"
	fi
	echo "-----------------------------------------------------------" >> $INSTALLER_LOG
	echo "" >> $INSTALLER_LOG
}

# Makes 
# Usage newlogparagraph [localinfo]
function newlogparagraph {
	if [[ ! -z $1 ]]; then
		LOCAL_INFO=$1
	fi
	echo "" >> $INSTALLER_LOG
	echo "-----------------------------------------------------------" >> $INSTALLER_LOG
	echo "$LOCAL_INFO: `date`" >> $INSTALLER_LOG
	echo "-----------------------------------------------------------" >> $INSTALLER_LOG
	echo "" >> $INSTALLER_LOG
}


# Echoes a message and logs it
# Usage: echolog message 
function echolog {
	if [ "$LOGGER_GRAPHICAL" == "yes" ]; then
		echo "$1" > $path_only/tempmsg
		$DIALOG --clear --title "System messages" --textbox $path_only/tempmsg 25 78	
		rm -f $path_only/tempmsg
	else
		echo "$1"
	fi	
	echo "$LOCAL_INFO: *`date`*: $1" >> $INSTALLER_LOG
}

# Usage: log message 
function log {
	echo "$LOCAL_INFO: *`date`*: $1" >> $INSTALLER_LOG
}

# Pushes temp logs so that you can display them all
# Usage pushlog message
function pushlog {
	echo "$1" >> $ECHO_LOG
	if [[ ! -z "$1" ]]; then
		echo "$LOCAL_INFO: *`date`*: $1" >> $TEMP_LOG	
	fi
}

#poplog writes logs to the official log and clears ECHO_LOG and TEMP_LOG
#Usage poplog
function poplog {
	cat $TEMP_LOG >> $INSTALLER_LOG
	rm -f $TEMP_LOG $ECHO_LOG
}

#popecholog does what poplog does and echoes the messages accordingly
#Usage popecholog
function popecholog {
	if [[ "$LOGGER_GRAPHICAL" == "yes" ]]; then
		$DIALOG --clear --title "System messages" --textbox $ECHO_LOG 25 78	
	else
		echo "`cat $ECHO_LOG`"
	fi	
	cat $TEMP_LOG >> $INSTALLER_LOG
	rm -f $TEMP_LOG $ECHO_LOG
}

# pushlocalinfo, poplocalinfo are used to change the LOCAL_INFO
# so that it is correct in every log entry. pushlocalinfo should be
# called before making a new logparagraph and poplocalinfo when exiting
# the function

# Usage pushlocalinfo
function pushlocalinfo {
	echo "$LOCAL_INFO" >> $path_only/templocalinfostack
}

# Usage poplocalinfo
function poplocalinfo {
	if [[ -f $path_only/templocalinfostack ]]; then
		LOCAL_INFO=`tail -n 1 $path_only/templocalinfostack`
		sed "`cat $path_only/templocalinfostack|wc -l`d" $path_only/templocalinfostack > $path_only/mytemp
		mv $path_only/mytemp $path_only/templocalinfostack
		checkstack=`cat $path_only/templocalinfostack`
		if [[ -z $checkstack ]]; then
			rm -f $path_only/templocalinfostack
		fi
		return 0
	fi
	return 1
}


