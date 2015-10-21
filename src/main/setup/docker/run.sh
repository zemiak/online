#!/bin/sh

VERSION=$1
if [ -z "$VERSION" ]
then
    VERSION=2_0_0
fi

mkdir -p /home/vasko/online-database
docker run -d -p 8080:8080 -p 2200:22 --name=online \
    -v /home/vasko/online-database:/mnt/backup \
    online:$VERSION
