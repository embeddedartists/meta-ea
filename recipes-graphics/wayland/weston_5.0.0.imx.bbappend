#SUMMARY = "Weston, a Wayland compositor, i.MX fork"
#DESCRIPTION = "Weston is the reference implementation of a Wayland compositor"
#HOMEPAGE = "http://wayland.freedesktop.org"
#LICENSE = "MIT"
#LIC_FILES_CHKSUM = "file://COPYING;md5=d79ee9e66bb0f95d3386a7acae780b70 \
#                    file://libweston/compositor.c;endline=26;md5=f47553ae598090444273db00adfb5b66"

SRC_URI_remove = "file://0003-weston-touch-calibrator-Advertise-the-touchscreen-ca.patch"

do_install_append() {
    WESTON_INI_DEST_DIR=${D}${sysconfdir}/xdg/weston
    cd ${WESTON_INI_DEST_DIR}
    uncomment "\\[output\\]"         weston.ini
    uncomment "name=HDMI-A-1"        weston.ini
    sed -i 's/#mode=1920x1080@60/mode=1280x720@60/' weston.ini
    cd -
}
