#!/bin/sh

# Locate mmc device with a boot0 partition and extract device path
dev=`ls /dev/mmcblk*boot*`
dev=($dev)
dev=${dev[0]}
devpath=${dev%boot*}

# Find last partition
PART_NUM=$(parted $devpath -ms unit s p | tail -n 1 | cut -f 1 -d:)

# Extract the partition's start sector
PART_START=$(parted $devpath -ms unit s p | grep "^${PART_NUM}" | cut -f 2 -d:)

disk_size=`cat /sys/block/${devpath#/dev/}/size`
part_off=`cat /sys/block/${devpath#/dev/}/${devpath#/dev/}p${PART_NUM}/start`
part_size=`cat /sys/block/${devpath#/dev/}/${devpath#/dev/}p${PART_NUM}/size`
left=`expr $disk_size - $part_off - $part_size`
echo "DBG: $devpath, $PART_NUM, $PART_START, $disk_size, $part_off, $part_size, $left"  >> /tmp/ea-resizefs.log

# Skip resizing if close to max size already
if [ $left -lt 10240 ]; then
    echo "Skipping resizing of ${devpath}p${PART_NUM} - only $left blocks left"  >> /tmp/ea-resizefs.log
    exit 0
 fi

old=`df -h ${devpath}p${PART_NUM} | grep /dev/ |awk '{print $2}'`
echo "Resizing ${devpath}p${PART_NUM}. Old size ${old}" >> /tmp/ea-resizefs.log

# Remove the last partition
echo "Remove the last partition with parted" >> /tmp/ea-resizefs.log
parted $devpath -ms rm $PART_NUM >> /tmp/ea-resizefs.log 2>&1

# Create a new partition with the old partition's start sector
# but using 100% of the available space as size.
echo "Create new partition $devpath $PART_START" >> /tmp/ea-resizefs.log
parted $devpath -ms unit s mkpart primary $PART_START 100% >> /tmp/ea-resizefs.log 2>&1

echo "Calling resize2fs ${devpath}p${PART_NUM}" >> /tmp/ea-resizefs.log
resize2fs ${devpath}p${PART_NUM} >> /tmp/ea-resizefs.log 2>&1

old=`df -h ${devpath}p${PART_NUM} | grep /dev/ |awk '{print $2}'`
echo "Finished resizing ${devpath}p${PART_NUM}. New size ${old}" >> /tmp/ea-resizefs.log

# Remove the service. Should only be run once
systemctl --no-reload disable resizefs.service

