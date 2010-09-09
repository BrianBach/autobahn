@echo off
setlocal EnableDelayedExpansion
set CP=.
for /F %%a in ('dir lib\*.jar /b /ON') do (
   set CP=!CP!;lib\%%a
)

java -Dcxf.config.file=etc/cxf/cxf.xml -Dorg.apache.cxf.Logger=org.apache.cxf.common.logging.Log4jLogger -classpath !CP! net.geant.autobahn.framework.Framework start