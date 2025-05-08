DESCRIPTION = "Add packages related to wireless/murata functionality"

inherit packagegroup

RDEPENDS:${PN} = " \
    murata-binaries \
    nxp-wlan-sdk \
    kernel-module-nxp-wlan \
    hostapd \
    hostap-conf \
    hostap-utils \
    wpa-supplicant \
"
