LICENSE = "MIT"
LIC_FILES_CHKSUM="file://LICENSE;md5=0835ade698e0bcf8506ecda2f7b4f302"
DEPENDS = "u-boot-mkimage-native"

PV = "1.0"

SRC_URI = " \
    file://bootscript.txt \
    file://bootscript_imx8.txt \
    file://LICENSE \
    "

S = "${WORKDIR}"

BOOTSCRIPT = "${WORKDIR}/bootscript.txt"
BOOTSCRIPT_mx8 = "${WORKDIR}/bootscript_imx8.txt"

inherit deploy

do_mkimage () {
    uboot-mkimage -A arm -O linux -T script -C none -a 0 -e 0 \
                  -n "EA BootScript" -d ${BOOTSCRIPT} \
                  ${S}/boot.scr
}

addtask mkimage after do_compile before do_install

do_deploy () {
    install -d ${DEPLOYDIR}/${MACHINE}
    install -m 0644 -t ${DEPLOYDIR} ${S}/*.scr
    install -m 0644 -t ${DEPLOYDIR} ${BOOTSCRIPT}
}

addtask deploy after do_install before do_build

do_compile[noexec] = "1"
do_install[noexec] = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
