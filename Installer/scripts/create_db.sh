#!/bin/sh
########################################################
# - create a test database if it does not exist.
# if necessary some variables may be passed
# with arguments; db password, path to psql,
# name of the database, db owner... anything else
#-----------------------------------------------------
 
# if we get the db password, we put it in our temp
# env., otherwise psql may pause and prompt for it.

#USE: create_db.sh database_name database_password

if [ $# -ne 2 ]; then
	echo "Usage: $0 database_name database_password"
	exit 1
fi

#if [ "$1" ]; then
   export dbname="$1"
#fi
#if [ "$2" ] ; then
   export PGPASSWORD="$2"
#   fi
# echo "DBNAME = $dbname pass=$PGPASSWORD"
    
# may check the args to see if dbname
# was provided
# dbname=test
 cmd="psql template1  -c \"create database $dbname\" -t > /dev/null 2>&1"
# echo "Return value: $?"
 eval $cmd
 if [ $? -ne 0 ]; then
 	echo "Database $dbname already existed."
  	exit 0
 else
 	echo "Database $dbname was created."
	exit 1
fi

