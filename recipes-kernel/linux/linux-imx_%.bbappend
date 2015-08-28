# Copyright (C) 2013, 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)
# cat /home/anders/projects/yocto/ea_own2/sources/meta-ea-bsp/recipes-kernel/linux/fragment.cfg >> ${S}/.config
# 

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
FRAGMENT_FILE := "${THISDIR}/fragment.cfg"

do_configure_append() {
  cat ${FRAGMENT_FILE} >> ${S}/.config
}



SRC_URI += "file://0001-add-imx6sx-COM-Board-support.patch" 

