# Copyright (C) 2017 Embedded Artists

DESCRIPTION = "U-Boot for Embedded Artists i.MX based boards."

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCBRANCH = "ea_v2018.03"
SRC_URI = "git://github.com/embeddedartists/uboot-imx.git;branch=${SRCBRANCH} \
           "
SRCREV = "7dfd76e39bec9c89f6196b5c9dc5c2a31acd3bf3"

S = "${WORKDIR}/git"

inherit fsl-u-boot-localversion

LOCALVERSION ?= "-2.0.0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

