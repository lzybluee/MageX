#!/bin/sh

cd "`dirname "$0"`"

java -Xms256m -Xmx512m -XX:MaxPermSize=256m -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -jar ./lib/mage-client-${project.version}.jar &