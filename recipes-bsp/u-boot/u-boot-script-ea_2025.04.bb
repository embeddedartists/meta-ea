require u-boot-ea-common_${PV}.inc
DEPENDS = "u-boot-mkimage-native"

SRC_URI += " \
    file://bootscript.txt \
    file://bootscript_imx8.txt \
"

BOOTSCRIPT = "bootscript.txt"
BOOTSCRIPT:mx8m-nxp-bsp = "bootscript_imx8.txt"
BOOTSCRIPT:mx93-nxp-bsp = "bootscript_imx8.txt"

UIMAGE_ARCH = "arm"
UIMAGE_ARCH:aarch64 = "arm64"

inherit uboot-config
inherit deploy

do_mkimage () {
    uboot-mkimage -A ${UIMAGE_ARCH} -O linux -T script -C none -a 0 -e 0 \
                  -n "EA BootScript" -d "${UNPACKDIR}/${BOOTSCRIPT}" \
                  ${S}/boot.scr
}

addtask mkimage after do_compile before do_install

do_compile[noexec] = "1"

do_install () {
    install -D -m 0644 ${S}/boot.scr ${D}/boot.scr
    install -D -m 0644 ${UNPACKDIR}/${BOOTSCRIPT} ${D}/bootscript.txt
}

do_deploy () {
    install -D -m 0644 ${D}/boot.scr ${DEPLOYDIR}/boot.scr
    install -D -m 0644 ${D}/bootscript.txt ${DEPLOYDIR}/bootscript.txt
}

addtask deploy after do_install before do_build

FILES:${PN} += "/"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
