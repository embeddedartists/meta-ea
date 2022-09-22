# Copyright (C) 2017 Embedded Artists AB
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided by Embedded Artists but based on NXP's kernel"
DESCRIPTION = "Linux Kernel for Embedded Artists i.MX based COM boards. \
The kernel is based on the kernel provided by NXP."

require recipes-kernel/linux/linux-imx.inc

SRC_URI = "git://github.com/embeddedartists/linux-imx.git;protocol=https;branch=${SRCBRANCH}"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

LOCALVERSION = "-lts-5.15.32"
SRCBRANCH = "ea_5.15.y"
SRCREV = "4ff00de0901c92c11589aec543873a2307ee5fb3"
DEPENDS += "lzop-native bc-native"

SRC_URI += "file://0001-uapi-Add-ion.h-to-userspace.patch"

LINUX_VERSION = "5.15.32"

DEFAULT_PREFERENCE = "1"

DO_CONFIG_EA_IMX_COPY = "no"
DO_CONFIG_EA_IMX_COPY:mx6-nxp-bsp = "yes"
DO_CONFIG_EA_IMX_COPY:mx7-nxp-bsp = "yes"
DO_CONFIG_EA_IMX_COPY:mx8-nxp-bsp = "no"

# Add setting for LF Mainline build
IMX_KERNEL_CONFIG_AARCH32 = "ea_imx_defconfig"
IMX_KERNEL_CONFIG_AARCH64 = "ea_imx8_defconfig"
KBUILD_DEFCONFIG ?= ""
KBUILD_DEFCONFIG:mx6-nxp-bsp = "${IMX_KERNEL_CONFIG_AARCH32}"
KBUILD_DEFCONFIG:mx7-nxp-bsp = "${IMX_KERNEL_CONFIG_AARCH32}"
KBUILD_DEFCONFIG:mx8-nxp-bsp = "${IMX_KERNEL_CONFIG_AARCH64}"

addtask copy_defconfig after do_unpack before do_preconfigure
do_copy_defconfig () {
    install -d ${B}

    if [ ${DO_CONFIG_EA_IMX_COPY} = "yes" ]; then
        # copy latest ea_imx_defconfig to use
        mkdir -p ${B}
        cp ${S}/arch/arm/configs/${IMX_KERNEL_CONFIG_AARCH32} ${B}/.config
        cp ${S}/arch/arm/configs/${IMX_KERNEL_CONFIG_AARCH32} ${B}/../defconfig
    else
        # copy latest defconfig to use for mx8
        mkdir -p ${B}
        cp ${S}/arch/arm64/configs/${IMX_KERNEL_CONFIG_AARCH64} ${B}/.config
        cp ${S}/arch/arm64/configs/${IMX_KERNEL_CONFIG_AARCH64} ${B}/../defconfig
    fi
}

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"

EXTRA_OEMAKE:append:mx6-nxp-bsp = " ARCH=arm"
EXTRA_OEMAKE:append:mx7-nxp-bsp = " ARCH=arm"
EXTRA_OEMAKE:append:mx8-nxp-bsp = " ARCH=arm64"

