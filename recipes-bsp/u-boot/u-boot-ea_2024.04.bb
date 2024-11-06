# Copyright (C) 2024 Embedded Artists

require recipes-bsp/u-boot/u-boot.inc
require recipes-bsp/u-boot/u-boot-ea-common_${PV}.inc

PROVIDES += "u-boot u-boot-mfgtool"

BOOT_TOOLS = "imx-boot-tools"

#
# Got build errors when using 'devtool modify u-boot-ea'.
# In poky/scripts/lib/devtool/standard.py there is a do_configure:append
# that will copy .config if KCONFIG_CONFIG_ENABLE_MENUCONFIG is true
# The path to .config however seem to get incorrect
#
# Setting KCONFIG_CONFIG_ENABLE_MENUCONFIG to false solves the problem.
#
KCONFIG_CONFIG_ENABLE_MENUCONFIG = "false"

do_deploy:append:mx8m-nxp-bsp () {
    # Deploy the mkimage, u-boot-nodtb.bin and fsl-imx8mq-XX.dtb for mkimage to generate boot binary
    if [ -n "${UBOOT_CONFIG}" ] && [ "${UBOOT_CONFIG}" != "mfgtool" ]
    then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]
                then
                    install -d ${DEPLOYDIR}/${BOOT_TOOLS}
                    install -m 0777 ${B}/${config}/arch/arm/dts/${UBOOT_DTB_NAME}  ${DEPLOYDIR}/${BOOT_TOOLS}
                    install -m 0777 ${B}/${config}/u-boot-nodtb.bin  ${DEPLOYDIR}/${BOOT_TOOLS}/u-boot-nodtb.bin-${MACHINE}-${type}
                fi
            done
            unset  j
        done
        unset  i
    fi

}

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"

UBOOT_NAME:mx6-nxp-bsp = "u-boot-${MACHINE}.bin-${UBOOT_CONFIG}"
UBOOT_NAME:mx7-nxp-bsp = "u-boot-${MACHINE}.bin-${UBOOT_CONFIG}"
UBOOT_NAME:mx8-nxp-bsp = "u-boot-${MACHINE}.bin-${UBOOT_CONFIG}"


