# Copyright (C) 2017 Embedded Artists

DESCRIPTION = "U-Boot for Embedded Artists i.MX based boards."

DEPENDS += "flex-native bison-native"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCBRANCH = "ea_v2020.04"
SRC_URI = "git://github.com/embeddedartists/uboot-imx.git;protocol=https;branch=${SRCBRANCH} \
           "
SRCREV = "f844127b296cb60963bebf75dc0feb7409e8fb38"

S = "${WORKDIR}/git"

inherit fsl-u-boot-localversion

LOCALVERSION ?= "-2.1.0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

