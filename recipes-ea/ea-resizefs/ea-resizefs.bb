SUMMARY = "Resizes rootfs to use all available space"
DESCRIPTION = "Script and service for one-time expansion of rootfs, needed after flashing a wic file."
LICENSE = "MIT"
LIC_FILES_CHKSUM="file://${WORKDIR}/LICENSE;md5=0835ade698e0bcf8506ecda2f7b4f302"

RDEPENDS_${PN} = "e2fsprogs-resize2fs parted"

SRC_URI = "file://ea-resizefs.txt \
           file://LICENSE \
           "

do_install () {
	install -d ${D}/opt
	install -d ${D}/opt/ea
	install -m 0755 ${WORKDIR}/ea-resizefs.txt ${D}/opt/ea
}


pkg_postinst_ontarget_${PN} () {
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
   parted $devpath -ms rm $PART_NUM

   # Create a new partition with the old partition's start sector
   # but using 100% of the available space as size.
   parted $devpath -ms unit s mkpart primary $PART_START 100%

   resize2fs ${devpath}p${PART_NUM}

   old=`df -h ${devpath}p${PART_NUM} | grep /dev/ |awk '{print $2}'`
   echo "Finished resizing ${devpath}p${PART_NUM}. New size ${old}" >> /tmp/ea-resizefs.log
}

PACKAGES = "${PN}"
FILES_${PN} = "/"
