require u-boot-ea_${PV}.bb
require recipes-bsp/u-boot/u-boot-mfgtool.inc

SRCBRANCH = "ea_v2016.03_4.1.15_2.0.0"
SRC_URI = "git://github.com/embeddedartists/uboot-imx.git;branch=${SRCBRANCH} \
           "
SRCREV = "eba1a9e834374585070ba4824c0c2b4b5421f4fb"
