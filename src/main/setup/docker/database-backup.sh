#!/bin/bash

BACKUPDIR="/mnt/backup"
TEMPFILE="/tmp/errdb"
ERR=0
echo "">$TEMPFILE

thedate=`date +%Y%m%d%H`
themonth=`date +%Y%m`

# erase backups older than 6 months
cd $BACKUPDIR
find . -mtime +60 -exec rm {} \;

/opt/jdk/bin/java -cp /opt/wildfly/modules/system/layers/base/com/h2database/h2/main/h2-1.3.173.jar \
  org.h2.tools.Script -url 'jdbc:h2:/var/lib/online/database;FILE_LOCK=no' -user sa \
  -script "$BACKUPDIR/$thedate-online.plain" 2>>$TEMPFILE
ERR=$?

if [ "$ERR" == "0" ] ; then
    bzip2 "$BACKUPDIR/$thedate-online.plain"
    #echo "backup for $thedate done" | mail -s "online backup OK" zemiak@gmail.com >/dev/null 2>/dev/null
else
    echo "">>$TEMPFILE
    echo "">>$TEMPFILE
    echo "">>$TEMPFILE
    echo "">>$TEMPFILE

    echo "online database backup for $thedate FAILED" >>$TEMPFILE
    cat $TEMPFILE | mail -s "online backup FAILED" zemiak@gmail.com >/dev/null 2>/dev/null
    rm $TEMPFILE
fi
