#!/bin/sh

WILDFLY=wildfly-8.2.0.Final
PROJECT=~/Documents/projects/online
DOWNLOAD=~/Downloads/$WILDFLY.zip
INSTALL=~/bin
TARGET=wildfly8

cd $PROJECT
mvn -q clean package

cd $INSTALL
killall java
test -d $TARGET && rm -rf $TARGET
cp $DOWNLOAD ./
unzip $WILDFLY.zip >/dev/null
rm $WILDFLY.zip
mv $WILDFLY $TARGET

./$TARGET/bin/add-user.sh vasko 123456 --silent
./$TARGET/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 &
sleep 5s

cd $PROJECT/setup/wildfly
sh setup.sh dev

cd ~/bin
./$TARGET/bin/jboss-cli.sh --connect --command="deploy $PROJECT/target/online.war --force"

sleep 3s
killall java
