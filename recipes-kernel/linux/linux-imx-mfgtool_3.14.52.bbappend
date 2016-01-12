# Copyright (C) 2013, 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)
# 

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
FRAGMENT_FILE := "${THISDIR}/fragment_mfg.cfg"

do_configure_append() {
  cat ${FRAGMENT_FILE} >> ${B}/.config
}

do_configure_append_mx6ul() {
  echo "CONFIG_FSL_OTP=y" >> ${B}/.config
}

SRC_URI += "file://0001-add-EA-COM-Board-support-to-linux_3_14_52.patch" 

