#!/bin/bash

cd ../
mvn clean install -DskipTests=true
mvn clean package -DskipTests=true 

echo 'finish package and start copy war file to tomcat'
cp -v thirdpartydemo-web/target/thirdpartydemo-web-*.war $HOME/server/apache-tomcat-7.0.63-thirdpartyweb/webapps/thirdpartydemo-web.war
cp -v thirdpartydemo-job/target/thirdpartydemo-job-*.war $HOME/server/apache-tomcat-7.0.63-thirdpartyjob/webapps/thirdpartydemo-job.war

echo 'deploy finish'
