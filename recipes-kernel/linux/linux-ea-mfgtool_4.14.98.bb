require linux-ea_${PV}.bb
require recipes-kernel/linux/linux-mfgtool.inc

SRCBRANCH = "ea_4.14.98"
SRC_URI = "git://github.com/embeddedartists/linux-imx.git;protocol=git;branch=${SRCBRANCH}"

SRCREV = "9fba964fd0a6ff4f44082ed1edeaa7104e3a7024"
