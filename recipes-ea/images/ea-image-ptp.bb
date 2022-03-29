DESCRIPTION = "Small image to be used for Production Tests."

LICENSE = "MIT"

MACHINEOVERRIDES =. "ea-ptp:"

IMAGE_INSTALL = "\
   ${CORE_IMAGE_BASE_INSTALL} \
   iperf3 \
   i2c-tools-misc \
   i2c-tools \
   pciutils \
   can-utils \
   iproute2 \
   evtest \
   alsa-utils \
   fbida \
   wget \
   ethtool \
   python-subprocess \
   python-pyserial \
   python-argparse \
   devregs \
"

IMAGE_INSTALL:append:imx7ulpea-ucom = " murata-binaries bluez5"
IMAGE_INSTALL:append:imx8mmea-ucom = " murata-binaries bluez5 firmware-imx-sdma imx-boot"
IMAGE_INSTALL:append:imx8mnea-ucom = " murata-binaries bluez5 firmware-imx-sdma imx-boot"
IMAGE_INSTALL:append:imx8mqea-com = " imx-boot"

inherit mfgtool-initramfs-image
