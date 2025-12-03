RRECOMMENDS:${PN}-plugins:append = " \
    ${@bb.utils.contains('DISTRO_FEATURES','wayland',' qtwayland','',d)} \
"

