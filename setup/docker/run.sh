#!/bin/sh

VERSION=$1
if [ -z "$VERSION" ]
then
    VERSION = 1_0_1
fi

docker run -d -p 443:8443 -p 2200:22 --name=online online:$VERSION
