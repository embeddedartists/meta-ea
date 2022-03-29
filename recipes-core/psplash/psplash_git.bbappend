FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://psplash-colors.h"

SPLASH_IMAGES = "file://psplash-poky-img.png;outsuffix=default"

do_configure:append () {
	cd ${S}
	cp ../psplash-colors.h ./
}
