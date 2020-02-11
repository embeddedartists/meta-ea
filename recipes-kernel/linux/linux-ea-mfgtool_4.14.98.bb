require linux-ea_${PV}.bb
require recipes-kernel/linux/linux-mfgtool.inc

SRCBRANCH = "ea_4.14.98"
SRC_URI = "git://github.com/embeddedartists/linux-imx.git;protocol=git;branch=${SRCBRANCH}"

SRCREV = "b1e8a1665819ff8027149f44f06275edfec4c885"
