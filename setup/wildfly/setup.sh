#!/bin/bash

MAC=/Users/vasko/bin/wildfly8/
LINUX=/home/vasko/bin/wildfly/
WINDOWS=/e/Java/wildfly8/

if [ -d "$JBOSS_HOME" ]
then
    echo Found >/dev/null
elif [ -d "$MAC" ]
then
    export JBOSS_HOME="$MAC"
elif [ -d "$LINUX" ]
then
    export JBOSS_HOME="$LINUX"
elif [ -d "$WINDOWS" ]
then
    export JBOSS_HOME="$WINDOWS"
else
    echo "Cannot find Wildfly Home"
    exit 3
fi

CLI="${JBOSS_HOME}/bin/jboss-cli.sh"

if [ ! -x "$CLI" ]
then
    echo "Wildfly command line $JBOSS_HOME not found"
    exit 1
fi

if [ -z "$1.cli" ]
then
    echo "Provide environment name (dev, prod...)"
    exit 2
fi

echo ... driver
$CLI --file=driver-install.cli

echo ... deleting resources
$CLI --file=common.cli

echo ... mail
$CLI --file=mail-session.cli

echo ... data source
$CLI --file="$1-ds.cli"

echo ... authentication
$CLI --file=authentication-realm.cli
