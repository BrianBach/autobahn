#!/bin/sh
CP=.
for i in `ls lib/*.jar`; do CP=$CP:$i; done
java -Dorg.apache.cxf.Logger=org.apache.cxf.common.logging.Log4jLogger -classpath $CP net.geant.autobahn.tool.mock.MockToolServer $1 $2