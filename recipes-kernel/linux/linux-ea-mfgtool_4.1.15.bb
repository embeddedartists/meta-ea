
SUMMARY = "Produces a Manufacturing Tool compatible Linux Kernel"

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEFCONFIG_FILE := "${THISDIR}/${PN}-${PV}/defconfig"

SRC_URI = "git://github.com/embeddedartists/linux-imx.git;protocol=git;branch=${SRCBRANCH}"

LOCALVERSION = "-1.2.0"
SRCBRANCH = "ea_imx_4.1.15_1.0.0"
SRCREV = "be76c9fe39d61e3db432bbe16bb2cc41d6e95b5b"
DEPENDS += "lzop-native bc-native"

COMPATIBLE_MACHINE = "(mx6|mx6ul|mx7)"

do_configure_prepend() {
    cp ${DEFCONFIG_FILE} ${B}/../defconfig
    #echo "EA: In do_configure_prepend: ${DEFCONFIG_FILE}"
}

require recipes-kernel/linux/linux-mfgtool.inc

do_deploy () {
    install -d ${DEPLOY_DIR_IMAGE}
    install  arch/arm/boot/zImage ${DEPLOY_DIR_IMAGE}/zImage_mfgtool
}

