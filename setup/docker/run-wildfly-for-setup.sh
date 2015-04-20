#!/bin/sh

export JAVA_HOME=/opt/jdk
export PATH=$PATH:/opt/jdk/bin

# Database
service postfix start
service postgresql start

# Wildfly Application Server with the online application
/opt/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 &
