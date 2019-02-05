SUMMARY = "A console-only image that fully supports the target device \
hardware."

IMAGE_FEATURES += "splash"

LICENSE = "MIT"

IMAGE_INSTALL = "\
   ${CORE_IMAGE_BASE_INSTALL} \
   i2c-tools-misc \
   i2c-tools \
   pciutils \
   can-utils \
   iproute2 \
   evtest \
   alsa-utils \
   fbida \
   wget \
   nano \
   python-subprocess \
   python-pyserial \
   python-argparse \
   python-pip \
   gdbserver \
   openssh-sftp-server \
   sqlite3 \
   connman \
   v4l-utils \
   packagegroup-fsl-gstreamer1.0 \
   packagegroup-fsl-gstreamer1.0-full \
   murata-binaries \
   iperf3 \
"

inherit core-image

EXTRA_IMAGE_FEATURES = " ssh-server-openssh"

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
