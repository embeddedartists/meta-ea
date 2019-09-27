require u-boot-ea_${PV}.bb
require recipes-bsp/u-boot/u-boot-mfgtool.inc

SRCBRANCH = "ea_v2018.03"
SRC_URI = "git://github.com/embeddedartists/uboot-imx.git;branch=${SRCBRANCH} \
           "
SRCREV = "86c53eb3421b28a2799936dc88d5366c48797b2a"

#
# Temporary fix. This is the last commit that the u-boot/spl can be used
# together with uuu to flash the target.
#
SRCREV_mx6ul = "261964dcf87373cf547f385cfb1a627ebcfe16ca"
