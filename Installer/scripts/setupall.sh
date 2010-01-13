#!/usr/bin/env bash

abspath=$(cd ${0%/*} && echo $PWD/${0##*/})

# to get the path only - not the script name - add
path_only=`dirname "$abspath"`

$path_only/self-check.sh $@ 
$path_only/setup.sh $@

