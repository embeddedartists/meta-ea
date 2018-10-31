# Copyright (C) 2013, 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)
#

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"


FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://interfaces \
"

FILES_DIR := "${THISDIR}/files"

ROOTFS_POSTPROCESS_COMMAND += "add_my_own_files; "

add_my_own_files () {
        mkdir -p ${WORKDIR}/rootfs/etc/network
        cp ${FILES_DIR}/interfaces ${WORKDIR}/rootfs/etc/network
}
