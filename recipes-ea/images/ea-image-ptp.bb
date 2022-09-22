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
   wget \
   ethtool \
   python-subprocess \
   python-pyserial \
   python-argparse \
   devregs \
   memtester \
"

# imx7dea-com, adding murata-binaries for the 1XL detection for PCIe
# IMAGE_INSTALL_append_imx7dea-com = " murata-binaries nxp-wlan-sdk kernel-module-nxp89xx linux-firmware-nxp89xx"
IMAGE_INSTALL_append_imx7ulpea-ucom = " murata-binaries bluez5"
IMAGE_INSTALL_append_imx8mmea-ucom = " murata-binaries bluez5 imx-boot backporttool-linux"
IMAGE_INSTALL_append_imx8mnea-ucom = " murata-binaries bluez5 imx-boot"
IMAGE_INSTALL_append_imx8mqea-com = " imx-boot"

inherit mfgtool-initramfs-image
