@ECHO OFF
IF NOT EXIST "C:\Program Files (x86)\Java\jre7\bin\" GOTO NOJAVADIR
set JAVA_HOME="C:\Program Files (x86)\Java\jre7\"
set CLASSPATH=%JAVA_HOME%/bin;%CLASSPATH%
set PATH=%JAVA_HOME%/bin;%PATH%
:NOJAVADIR
java -Xms256M -Xmx512M -XX:MaxPermSize=384m -Djava.security.policy=./config/security.policy -Djava.util.logging.config.file=./config/logging.config -Dlog4j.configuration=file:./config/log4j.properties -jar ./lib/mage-server-${project.version}.jar