#!/bin/sh
java -Dorg.apache.cxf.Logger=org.apache.cxf.common.logging.Log4jLogger -classpath .:lib/* net.geant2.jra3.framework.Framework start
