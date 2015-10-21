#!/bin/sh

export JAVA_HOME=/opt/jdk
export PATH=$PATH:/opt/jdk/bin

dpkg-reconfigure ntp
service postfix start
service ssh start
service cron start
service ntp start

# H2 Database TCP Server
#java -cp /opt/wildfly/modules/system/layers/base/com/h2database/h2/main/h2-1.3.173.jar -tcp -web &

# Wildfly Application Server with the movies application
/opt/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0
