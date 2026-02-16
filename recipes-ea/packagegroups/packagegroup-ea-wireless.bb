DESCRIPTION = "Add packages related to wireless/murata functionality"

inherit packagegroup

RDEPENDS:${PN} = " \
    murata-binaries \
    kernel-module-nxp-wlan \
    hostapd \
    hostap-conf \
    hostap-utils \
    wpa-supplicant \
    net-tools \
    cyw-hostapd \
    cyw-supplicant \
"
