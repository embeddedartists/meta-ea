require u-boot-ea-common_${PV}.inc
DEPENDS = "u-boot-mkimage-native"

SRC_URI += " \
    file://bootscript.txt \
    file://bootscript_imx8.txt \
"

BOOTSCRIPT = "${WORKDIR}/bootscript.txt"
BOOTSCRIPT_mx8 = "${WORKDIR}/bootscript_imx8.txt"

inherit uboot-config
inherit deploy

do_mkimage () {
    uboot-mkimage -A arm -O linux -T script -C none -a 0 -e 0 \
                  -n "EA BootScript" -d ${BOOTSCRIPT} \
                  ${S}/boot.scr
}

addtask mkimage after do_compile before do_install

do_compile[noexec] = "1"

do_install () {
    install -D -m 0644 ${S}/boot.scr ${D}/boot.scr
    install -D -m 0644 ${BOOTSCRIPT} ${D}/bootscript.txt
}

do_deploy () {
    install -D -m 0644 ${D}/boot.scr ${DEPLOYDIR}/boot.scr
    install -D -m 0644 ${D}/bootscript.txt ${DEPLOYDIR}/bootscript.txt
}

addtask deploy after do_install before do_build

FILES_${PN} += "/"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
