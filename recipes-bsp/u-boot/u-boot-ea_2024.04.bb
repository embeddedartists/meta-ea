# Copyright (C) 2024 Embedded Artists

require recipes-bsp/u-boot/u-boot.inc
require recipes-bsp/u-boot/u-boot-ea-common_${PV}.inc

PROVIDES += "u-boot u-boot-mfgtool"

inherit uuu_bootloader_tag
# The UUU tag goes on the boot partition. For 8+, the boot partition image
# is imx-boot, so disable UUU-tagging here
UUU_BOOTLOADER:mx8-generic-bsp = ""
UUU_BOOTLOADER:mx9-generic-bsp = ""


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
                    install -m 0777 ${B}/${config}/u-boot-nodtb.bin  ${DEPLOYDIR}/${BOOT_TOOLS}/u-boot-nodtb.bin-${MACHINE}-${type}
                    UBOOT_DTB_NAME_FLAGS="${type}:${UBOOT_DTB_NAME}"
                    for key_value in ${UBOOT_DTB_NAME_FLAGS}; do
                        local type_key="${key_value%%:*}"
                        local dtb_name="${key_value#*:}"
                        if [ "$type_key" = "$type" ]
                        then
                            bbnote "UBOOT_CONFIG = $type, UBOOT_DTB_NAME = $dtb_name"
                            # There is only one ${dtb_name}, the first one. All the other are with the type appended
                            if [ ! -f "${DEPLOYDIR}/${BOOT_TOOLS}/${dtb_name}" ]; then
                                install -m 0644 ${B}/${config}/arch/arm/dts/${dtb_name}  ${DEPLOYDIR}/${BOOT_TOOLS}/${dtb_name}
                            else
                                bbwarn "Use custom wks.in for $dtb_name = $type"
                            fi
                            install -m 0644 ${B}/${config}/arch/arm/dts/${dtb_name}  ${DEPLOYDIR}/${BOOT_TOOLS}/${dtb_name}-${type}
                        fi
                        unset type_key
                        unset dtb_name
                    done

                    unset UBOOT_DTB_NAME_FLAGS
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


