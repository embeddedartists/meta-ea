#!/bin/sh

VERSION="1.0"

function usage() {
  echo ""
  echo "Version: $VERSION"
  echo ""
  echo "Usage:"
  echo "  $0  [enable|disable]"
  echo ""
}

function cleanup() {
  # Always disable both wpa_supplicant services.
  # User has to run switch_module.sh to reenable normal
  # Wi-Fi usage.
  systemctl disable wpa_supplicant@mlan0
  systemctl disable wpa_supplicant@wlan0

  # Disable default hostapd service
  systemctl disable hostapd
}

function fix_udhcpd() {
  ifname=$1

  # Copy default settigs but skip interface name and max_leases
  grep -Ev "^interface|^max_leases" /etc/udhcpd.conf > /etc/udhcpd_${ifname}.conf
  echo "max_leases 50" >> /etc/udhcpd_${ifname}.conf
  echo "interface ${ifname}" >> /etc/udhcpd_${ifname}.conf
  sed -i 's/192.168.1./192.168.5./g' /etc/udhcpd_${ifname}.conf

  # To get rid of warning about missing file
  mkdir -p /var/lib/misc/
  touch /var/lib/misc/udhcpd.leases
}

function setup_cypress() {
  enable=$1

  cleanup
  systemctl disable hostapd@uap0

  if ($enable); then
    # Preparing for DHCP server
    fix_udhcpd wlan1

    # Change interface name in hostapd.conf
    sed -i '/^interface=/cinterface=wlan1' /etc/hostapd.conf

    echo "Enabling hostapd (includes DHCP)"
    systemctl enable hostapd@wlan1
  else
    systemctl disable hostapd@wlan1

    echo ""
    echo "Hostapd has been disabled. Don't forget to run"
    echo "switch_module.sh to enable normal Wi-Fi use"
    echo ""
  fi
}

function setup_nxp() {
  enable=$1

  cleanup
  systemctl disable hostapd@wlan1

  if ($enable); then
    # Preparing for DHCP server
    fix_udhcpd uap0

    echo "Enabling hostapd (includes DHCP)"
    systemctl enable hostapd@uap0
  else
    systemctl disable hostapd@uap0

    echo ""
    echo "Hostapd has been disabled. Don't forget to run"
    echo "switch_module.sh to enable normal Wi-Fi use"
    echo ""
  fi
}

if [[ $# -eq 0 ]]; then
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
    case $1 in
      enable)
        setup_cypress true
        ;;
      disable)
        setup_cypress false
        ;;
      *)
        usage
        ;;
    esac
    ;;
  nxp|nxp_1ym_pcie)
    case $1 in
      enable)
        setup_nxp true
        ;;
      disable)
        setup_nxp false
        ;;
      *)
        usage
        ;;
    esac
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
