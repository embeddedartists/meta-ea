#!/bin/sh

VERSION="1.0"

function usage() {
  echo ""
  echo "Version: $VERSION"
  echo ""
  echo "Usage:"
  echo "  $0"
  echo ""
}

function start_hostapd_cypress() {
    ifconfig wlan0 192.168.1.1 up
    iw dev wlan0 interface add wlan1 type __ap
    ifconfig wlan1 192.168.5.1 up
    hostapd -B /etc/hostapd.conf
    /usr/sbin/udhcpd -f -S /etc/udhcpd_wlan1.conf
}

function start_hostapd_nxp() {
    ifconfig mlan0 192.168.1.1 up
    iw dev mlan0 interface add uap0 type __ap
    ifconfig uap0 192.168.5.1 up
    hostapd -B /etc/hostapd.conf
    /usr/sbin/udhcpd -f -S /etc/udhcpd_uap0.conf
}

if [[ $# -ne 0 ]]; then
  usage
  exit 1
fi

module=$(fw_printenv bt_hint)
success=$?
if [ $? -ne 0 ]; then
  echo ""
  echo "You must run the switch_module.sh script and then reboot"
  echo "before running this script to setup required variables"
  echo ""
  usage
  exit 1
fi
module=${module/bt_hint=/}

case $module in
  cypress)
    echo "Starting..."
    start_hostapd_cypress
    echo "Done"
    ;;
  nxp|nxp_1ym_pcie|nxp_1xl_pcie)
    echo "Starting..."
    start_hostapd_nxp
    echo "Done"
    ;;
  nxp_1ym_sdio)
    echo ""
    echo "Not supported yet"
    echo ""
    ;;
  *)
    usage
    ;;
esac

