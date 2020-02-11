require u-boot-ea_${PV}.bb
require recipes-bsp/u-boot/u-boot-mfgtool.inc

SRCBRANCH = "ea_v2018.03"
SRC_URI = "git://github.com/embeddedartists/uboot-imx.git;branch=${SRCBRANCH} \
           "
SRCREV = "6beccee5cc89a077eeaa3e8fecc4dde7a7a2a614"

