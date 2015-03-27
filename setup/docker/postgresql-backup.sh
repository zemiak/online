#!/bin/bash

# backup directory can be a file server share that the PgAgent daemon account has access to
BACKUPDIR="/mnt/backup"
PGUSER="postgres"
PGBIN="/usr/bin"
TEMPFILE="/tmp/errdb"
ERR=0
ERRDB=""
echo "">$TEMPFILE

thedate=`date +%Y%m%d%H`
themonth=`date +%Y%m`

# put the names of the databases you want to create an individual backup below
dbs=(movies)

# iterate thru dbs in dbs array and backup each one
for db in ${dbs[@]}
do
  # custom output format for pg_restore, compressed by default, also with BLOBs
  $PGBIN/pg_dump --user=$PGUSER --blobs --format=p --inserts $db >"$BACKUPDIR/$thedate-$db.plain" 2>>$TEMPFILE
  ERR1=$?
  bzip2 "$BACKUPDIR/$thedate-$db.plain"
  
  if [ "$ERR1" != "0" ] ; then
      ERR=1
      ERRDB="$db $ERRDB"
  fi
done

# erase backups from last month
lastmonth=`date -d last-month +%Y%m`
rm -f $BACKUPDIR/$lastmonth* >/dev/null 2>/dev/null

if [ "$ERR" == "0" ] ; then
    echo Done >/dev/null
    #echo "backup for $thedate done" | mail -s "postgresql/mekbuk-server backup OK" zemiak@gmail.com >/dev/null 2>/dev/null
else
    echo "">>$TEMPFILE
    echo "">>$TEMPFILE
    echo "">>$TEMPFILE
    echo "">>$TEMPFILE

    echo "pgsql backup for $thedate FAILED for $ERRDB" >>$TEMPFILE
    cat $TEMPFILE | mail -s "pgsql backup FAILED" zemiak@gmail.com >/dev/null 2>/dev/null
    rm $TEMPFILE
fi
