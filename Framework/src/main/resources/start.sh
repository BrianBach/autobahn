#!/bin/sh
CP=.
for i in `ls lib/*.jar`; do CP=$CP:$i; done
rm -f ./nohup.out;touch nohup.out
nohup java -Dcxf.config.file=etc/cxf/cxf.xml -Dorg.apache.cxf.Logger=org.apache.cxf.common.logging.Log4jLogger -classpath $CP net.geant.autobahn.framework.Framework start &
tail -f nohup.out &
