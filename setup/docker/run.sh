#!/bin/sh

docker run -d -p 80:8080 -p 2210:22 --name=online -v /mnt/media/backup/pgsql:/mnt/backup online:1_0_0
