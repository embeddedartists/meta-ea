require linux-ea_${PV}.bb
require recipes-kernel/linux/linux-mfgtool.inc

SRCBRANCH = "ea_4.14.78"
SRC_URI = "git://github.com/embeddedartists/linux-imx.git;protocol=git;branch=${SRCBRANCH}"

SRCREV = "026a790c68bfb0f9b5cc58a7b3ae784d0c856cf3"
