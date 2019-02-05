# Copyright (C) 2017 Embedded Artists

DESCRIPTION = "U-Boot for Embedded Artists i.MX based boards."
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCBRANCH = "ea_v2018.03"
SRC_URI = "git://github.com/embeddedartists/uboot-imx.git;branch=${SRCBRANCH} \
           "
SRCREV = "df1fb32a3cea0f1448b15e30c5d66b58c4189380"

S = "${WORKDIR}/git"

inherit fsl-u-boot-localversion

LOCALVERSION ?= "-1.0.0"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6|mx6ul|mx7)"

