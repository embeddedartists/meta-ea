require u-boot-ea_${PV}.bb
require recipes-bsp/u-boot/u-boot-mfgtool.inc

SRCBRANCH = "ea_v2018.03"
SRC_URI = "git://github.com/embeddedartists/uboot-imx.git;branch=${SRCBRANCH} \
           "
SRCREV = "daeed9c858327072870103ec1d4f821df736116a"
