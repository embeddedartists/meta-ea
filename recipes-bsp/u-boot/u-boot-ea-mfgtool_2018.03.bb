require u-boot-ea_${PV}.bb
require recipes-bsp/u-boot/u-boot-mfgtool.inc

SRCBRANCH = "ea_v2018.03"
SRC_URI = "git://github.com/embeddedartists/uboot-imx.git;branch=${SRCBRANCH} \
           "
SRCREV = "24adace8c724a4921fd3c9bbcf3aca45483f7368"

#
# Temporary fix. This is the last commit that the u-boot/spl can be used
# together with uuu to flash the target.
#
SRCREV_mx6ul = "261964dcf87373cf547f385cfb1a627ebcfe16ca"
