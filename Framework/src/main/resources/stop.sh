#!/bin/sh
echo "Terminating Autobahn"
kill -15 `ps h -C java -o "%p:%a" | grep net.geant.autobahn.framework.Framework | cut -d: -f1`
echo "Autobahn terminated"