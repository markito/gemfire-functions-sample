#!/bin/bash
## Startup script
## @author: markito
##################

. ./setEnv.sh

IP=localhost

# server and locator directories
DIR=( "locator1" "server1" "server2" )

for i in "${DIR[@]}"
do
	if [ ! -d $i ]; then
		echo "Creating folder $i"
		mkdir $i
	fi
done

# locator startup
gfsh start locator --name=locator1 --dir=locator1 --bind-address=${IP}

# servers startup
gfsh start server --name=server1 --classpath=/Users/markito/Projects/Pivotal/workspaces/articles/FunctionSamples/build/libs/tmp/lib/groovy-all-2.3.0-beta-2.jar --dir=server1 --server-port=40001 --locators=${IP}[10334] --cache-xml-file=cache.xml
gfsh start server --name=server2 --classpath=/Users/markito/Projects/Pivotal/workspaces/articles/FunctionSamples/build/libs/tmp/lib/groovy-all-2.3.0-beta-2.jar --dir=server2 --server-port=40002 --locators=${IP}[10334] --cache-xml-file=cache.xml

#gfsh start server --name=server2 --dir=server2 --server-port=40002 --locators=${IP}[10334] --cache-xml-file=cache.xml --server-bind-address=${IP}
