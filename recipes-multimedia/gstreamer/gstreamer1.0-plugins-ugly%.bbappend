# Copyright (C) 2013, 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

#EXTRA_OECONF += " --enable-x264 "

PACKAGECONFIG = " \
    orc a52dec lame mad mpeg2dec \
    x264 \
    "

#do_configure_append() {
#    cat /home/anders/projects/yocto/ea_own2/sources/meta-ea-bsp/recipes-kernel/linux/fragment.cfg >> ${S}/.config
#}

