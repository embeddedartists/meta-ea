require u-boot-ea_${PV}.bb
require recipes-bsp/u-boot/u-boot-mfgtool.inc

# u-boot-mfgtool.inc incorrectly uses SPL_BINARY instead of SPL_BINARYNAME
# when constructing the SPL_IMAGE and SPL_SYMLINK variables which
# includes the spl/ prefix in the case of e.g. iMX8M Nano. The lines below
# corrects that behaviour
SPL_IMAGE = "${SPL_BINARYNAME}-${MACHINE}-mfgtool-${PV}-${PR}"
SPL_SYMLINK = "${SPL_BINARYNAME}-mfgtool-${MACHINE}"
