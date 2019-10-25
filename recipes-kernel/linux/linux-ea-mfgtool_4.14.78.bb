require linux-ea_${PV}.bb
require recipes-kernel/linux/linux-mfgtool.inc

SRCBRANCH = "ea_4.14.78"
SRC_URI = "git://github.com/embeddedartists/linux-imx.git;protocol=git;branch=${SRCBRANCH}"

SRCREV = "61703642a631f49ad5c1c7a5ef53e56d0c862a6e"
