#!/bin/sh

VERSION="1.0"

function current() {
  echo ""
  echo "Current setup:"

  echo "  wpa_supplicant@mlan0 is `systemctl is-enabled wpa_supplicant@mlan0`"
  echo "  wpa_supplicant@wlan0 is `systemctl is-enabled wpa_supplicant@wlan0`"
  echo "  hostapd is `systemctl is-enabled hostapd`"
  echo "  udhcped is `systemctl is-enabled udhcpd`"
  echo ""
}

function handle_services() {
  enable=$1

  # Always disable both wpa_supplicant services.
  # User has to run switch_module.sh to reenable normal
  # Wi-Fi usage.
  systemctl disable wpa_supplicant@mlan0
  systemctl disable wpa_supplicant@wlan0

  # Enable or disable hostapd + udhcpd services
  if ($enable); then
    echo "Enabling hostapd and udhcpd"
    systemctl enable hostapd
    systemctl enable udhcpd
  else
    echo "Disabling hostapd and udhcpd"
    systemctl disable hostapd
    systemctl disable udhcpd

    echo ""
    echo "Hostapd has been disabled. Don't forget to run"
    echo "switch_module.sh to enable normal Wi-Fi use"
    echo ""
  fi
}

function usage() {
  echo ""
  echo "Version: $VERSION"
  echo ""
  echo "Usage:"
  echo "  $0  [enable|disable]"
  echo ""
}

if [[ $# -eq 0 ]]; then
  current
  usage
  exit 1
fi

case $1 in
  enable)
    handle_services true
    ;;
  disable)
    handle_services false
    ;;
  *)
    current
    usage
    ;;
esac
