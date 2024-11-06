# Copyright (C) 2023 Embedded Artists

DESCRIPTION = "U-Boot for Embedded Artists i.MX based boards."

DEPENDS += "flex-native bison-native"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCBRANCH = "ea_v2024.04"
SRC_URI = "git://github.com/embeddedartists/uboot-imx.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "fe0a28d6b6d22b4e7142c222891c1c0cd33a8533"

S = "${WORKDIR}/git"

inherit fsl-u-boot-localversion

LOCALVERSION ?= "-${SRCBRANCH}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

