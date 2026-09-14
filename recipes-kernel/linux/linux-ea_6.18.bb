# Copyright (C) 2017-2026 Embedded Artists AB
# Released under the MIT license (see COPYING.MIT for the terms)
#
# SPDX-License-Identifier: MIT
#

SUMMARY = "Linux Kernel provided by Embedded Artists but based on NXP's kernel"
DESCRIPTION = "Linux Kernel for Embedded Artists i.MX based COM boards. \
The kernel is based on the kernel provided by NXP."

require recipes-kernel/linux/linux-imx.inc

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "coreutils-native"

SRC_URI = "${LINUX_IMX_SRC}"
LINUX_IMX_SRC ?= "git://github.com/embeddedartists/linux-imx.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "ea_6.18.y"
KBRANCH = "${SRCBRANCH}"
SRCREV = "ce26bfd463bfdde730c7a969d191c72cbc00b01a"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "6.18.20"
KERNEL_VERSION_SANITY_SKIP = "1"
LOCALVERSION = "-2.0.0"

KBUILD_DEFCONFIG:mx6-generic-bsp = "ea_imx_defconfig"
KBUILD_DEFCONFIG:mx7-generic-bsp = "ea_imx_defconfig"
KBUILD_DEFCONFIG:mx8-generic-bsp = "ea_imx8_defconfig"
KBUILD_DEFCONFIG:mx9-generic-bsp = "ea_imx8_defconfig"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
