require linux-ea_${PV}.bb
require recipes-kernel/linux/linux-mfgtool.inc

SRCBRANCH = "ea_4.14.78"
SRC_URI = "git://github.com/embeddedartists/linux-imx.git;protocol=git;branch=${SRCBRANCH}"

SRCREV = "feee0e5b01a2251c3643621c354f472f1e10d99f"

KERNEL_DEVICETREE = "imx7ulpea-ucom-ptp.dtb \
                     imx7ulpea-ucom-ptp-1lv.dtb \
"
