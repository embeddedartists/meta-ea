# Copyright (C) 2013, 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)
# 

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
FRAGMENT_FILE := "${THISDIR}/fragment_mfg.cfg"

do_configure_append() {
  cat ${FRAGMENT_FILE} >> ${S}/.config
}

SRC_URI += "file://0001-add-EA-COM-Board-support-to-linux.patch" 

