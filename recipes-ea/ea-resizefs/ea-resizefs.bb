SUMMARY = "Resizes rootfs to use all available space"
DESCRIPTION = "Script and service for one-time expansion of rootfs, needed after flashing a wic file."
LICENSE = "MIT"
LIC_FILES_CHKSUM="file://${WORKDIR}/LICENSE;md5=0835ade698e0bcf8506ecda2f7b4f302"

RDEPENDS_${PN} = "e2fsprogs-resize2fs parted"

SRC_URI = "file://ea-resizefs.sh \
           file://resizefs.service \
           file://LICENSE \
           "

do_install () {
	install -d ${D}/${sbindir}
	install -m 0755 ${WORKDIR}/*.sh ${D}/${sbindir}

	install -d ${D}${systemd_unitdir}/system/
	install -m 0644 ${WORKDIR}/resizefs.service ${D}${systemd_unitdir}/system
}

SYSTEMD_SERVICE_${PN} = "resizefs.service"

PACKAGES = "${PN}"
FILES_${PN} = "/"

inherit allarch systemd
