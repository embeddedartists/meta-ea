
SUMMARY = "Produces a Manufacturing Tool compatible Linux Kernel"

require linux-ea_${PV}.bb
require recipes-kernel/linux/linux-mfgtool.inc

#do_configure_prepend() {
#    cp ${S}/arch/arm/configs/imx_v7_mfg_defconfig ${B}/.config
#    cp ${S}/arch/arm/configs/imx_v7_mfg_defconfig ${B}/../defconfig
#}

do_deploy () {
    install -d ${DEPLOY_DIR_IMAGE}
    install  arch/arm/boot/zImage ${DEPLOY_DIR_IMAGE}/zImage_mfgtool
}

