#!/bin/sh
CP=.
for i in `ls lib/*.jar`; do CP=$CP:$i; done
java -Dcxf.config.file=etc/cxf/cxf.xml -Dorg.apache.cxf.Logger=org.apache.cxf.common.logging.Log4jLogger -classpath $CP net.geant.autobahn.framework.Framework stop