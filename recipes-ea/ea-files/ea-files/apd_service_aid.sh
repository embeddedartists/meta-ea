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
    ifconfig uap0 192.168.5.1 up
    cd $1
    ./uaputl.exe -i uap0 sys_cfg_rates 0x8C 0x98 0xB0 0x12 0x24 0x48 0x60 0x6C
    ./uaputl.exe -i uap0 vhtcfg 2 3 1 0x33D07130 0xFFFE 0xFFFE
    ./uaputl.exe -i uap0 sys_cfg_channel 44 2
    ./uaputl.exe -i uap0 sys_cfg_ssid Test_SSID
    ./uaputl.exe -i uap0 bss_start
    ./uaputl.exe sys_config
    cd -
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
  nxp)
    echo "Starting..."
    start_hostapd_nxp /usr/share/nxp_wireless/bin_mxm_wifiex/
    echo "Done"
    ;;
  nxp_1ym_pcie)
    echo "Starting..."
    start_hostapd_nxp /usr/share/nxp_wireless/bin_pcie8997/
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

