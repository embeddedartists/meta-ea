
INI_UNCOMMENT_ASSIGNMENTS:append = " \
    \\[output\\] \
    name=HDMI-A-1 \
    mode=1920x1080@60 \
"

do_install:append() {
    WESTON_INI_DEST_DIR=${D}${sysconfdir}/xdg/weston
    cd ${WESTON_INI_DEST_DIR}
    sed -i 's/mode=1920x1080@60/mode=1280x720@60/' weston.ini
    cd -
}
