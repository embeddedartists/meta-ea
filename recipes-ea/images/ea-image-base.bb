SUMMARY = "A console-only image that fully supports the target device \
hardware."

IMAGE_FEATURES += "splash ssh-server-openssh"

LICENSE = "MIT"

IMAGE_FEATURES += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston','', d)} \
"

IMAGE_INSTALL = "\
   ${CORE_IMAGE_BASE_INSTALL} \
   i2c-tools-misc \
   i2c-tools \
   pciutils \
   can-utils \
   iproute2 \
   evtest \
   alsa-utils \
   wget \
   nano \
   python-subprocess \
   python-pyserial \
   python-argparse \
   python-pip \
   gdbserver \
   openssh-sftp-server \
   sqlite3 \
   v4l-utils \
   packagegroup-fsl-gstreamer1.0 \
   packagegroup-fsl-gstreamer1.0-full \
   bluez5 \
   bluez5-noinst-tools \
   bluez5-obex \
   openobex \
   obexftp \
   glibc-gconv-utf-16 \
   glibc-utils \
   iperf3 \
   tslib \
   tslib-tests \
   tslib-calibrate \
   tslib-dev \
   mtdev \
   ea-files \
   mmc-utils \
   memtester \
   screen \
   u-boot-fw-utils \
   u-boot-script-ea \
   libgpiod \
   libgpiod-tools \
   nxp-wlan-sdk \
   kernel-module-nxp89xx \
   murata-binaries \
   cyw-supplicant \
   cyw-hostapd \
   hostap-conf \
   hostap-utils \
   hostapd \
   backporttool-linux \
"


IMAGE_INSTALL_append_imx8mnea-ucom = "\
   ea-resizefs \
"

inherit core-image

# User/Group modifications"
# - Adding user 'tester' without password"
# - Setting password for user 'root' to 'pass'"
# - For more options see extrausers.bbclass"
inherit extrausers
EXTRA_USERS_PARAMS = " \
  useradd -p '' tester; \
  usermod -s /bin/sh tester; \
  usermod -P 'pass' root \
"
