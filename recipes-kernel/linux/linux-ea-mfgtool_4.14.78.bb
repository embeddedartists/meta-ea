require linux-ea_${PV}.bb
require recipes-kernel/linux/linux-mfgtool.inc

SRCBRANCH = "ea_4.14.78"
SRC_URI = "git://github.com/embeddedartists/linux-imx.git;protocol=git;branch=${SRCBRANCH}"

SRCREV = "1eb458c552bbaaa57481ccce1a2fff87368014b8"
