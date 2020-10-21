#!/bin/sh

VERSION="1.0"

function usage() {
  echo ""
  echo "Version: $VERSION"
  echo ""
  echo "Usage:"
  echo "  $0 [noscan]"
  echo ""
}

function get_uart() {
  model=$(tr -d '\0' </proc/device-tree/model)
  if [[ $model == *'i.MX6 DualLite'* ]]; then
    echo /dev/ttymxc4
  elif [[ $model == *'i.MX6 UltraLite'* ]]; then
    echo /dev/ttymxc1
  elif [[ $model == *'i.MX6 SoloX'* ]]; then
    echo /dev/ttymxc1
  elif [[ $model == *'i.MX6 Quad'* ]]; then
    echo /dev/ttymxc4
  elif [[ $model == *'i.MX7 Dual COM'* ]]; then
    echo /dev/ttymxc1
  elif [[ $model == *'iMX7 Dual uCOM'* ]]; then
    echo /dev/ttymxc1
  elif [[ $model == *'i.MX7ULP'* ]]; then
    echo /dev/ttyLP2
  elif [[ $model == *'i.MX8MM'* ]]; then
    echo /dev/ttymxc0
  elif [[ $model == *'i.MX8M Nano'* ]]; then
    echo /dev/ttymxc0
  elif [[ $model == *'i.MX8MQ'* ]]; then
    echo /dev/ttymxc1
  else
    echo "Unknown model"
    exit 2
  fi
}

do_scan=true
if [[ $# -eq 1 ]]; then
  if [[ "$1" != "noscan" ]]; then
    usage
    exit 1
  fi
  do_scan=false
elif [[ $# -ne 0 ]]; then
  usage
  exit 1
fi

btuart=$(get_uart)
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
    hciattach $btuart bcm43xx 3000000 flow
    hciconfig hci0 up
    if ($do_scan); then
      hciconfig hci0 piscan
      hcitool scan
      echo ""
      echo "To run a scan again, use hcitool scan"
      echo ""
    fi
    ;;
  nxp|nxp_1ym_pcie)
    hciattach $btuart any 115200 flow
    hciconfig hci0 up
    hcitool -i hci0 cmd 0x3f 0x0009 0xc0 0xc6 0x2d 0x00
    killall hciattach
    hciattach $btuart any -s 3000000 3000000 flow
    hciconfig hci0 up
    if ($do_scan); then
      hciconfig hci0 piscan
      hciconfig hci0 noencrypt
      hcitool scan
      echo ""
      echo "To run a scan again, use hcitool scan"
      echo ""
    fi
    ;;
  nxp_1ym_sdio)
    insmod /usr/share/nxp_wireless/bin_sd8997_bt/bt8997.ko
    hciconfig hci0 up
    hcitool -i hci0 cmd 3f ee 01 XX
    if ($do_scan); then
      hcitool scan
      echo ""
      echo "To run a scan again, use hcitool scan"
      echo ""
    fi
    ;;
  *)
    usage
    ;;
esac

