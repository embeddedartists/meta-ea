# Copyright (C) 2013, 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)
#

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
FILES_TO_COPY := "${THISDIR}/files_mfg"

ROOTFS_POSTPROCESS_COMMAND += "add_my_own_files; "

add_my_own_files() {
	rsync -a ${FILES_TO_COPY}/*  ${WORKDIR}/rootfs
}
