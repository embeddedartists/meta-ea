# Copyright (C) 2023 Embedded Artists

DESCRIPTION = "U-Boot for Embedded Artists i.MX based boards."

DEPENDS += "flex-native bison-native gnutls-native"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCBRANCH = "ea_v2025.04"
SRC_URI = "git://github.com/embeddedartists/uboot-imx.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "407773e0a12effc174bdfd44ecd73100d6f47b66"

S = "${WORKDIR}/git"

inherit fsl-u-boot-localversion

LOCALVERSION ?= "-${SRCBRANCH}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

