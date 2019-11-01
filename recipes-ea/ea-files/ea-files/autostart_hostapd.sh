#!/bin/sh

echo "Setting up auto start of wireless network card in"
echo "access point mode after reboot. Settings are in"
echo "/etc/hostapd.conf, /etc/udhcpd.conf and"
echo "/opt/ea/start_wifi_for_hostap.sh."
echo ""

if [ -e /etc/resolv.conf ]; then
	rm /etc/resolv.conf
fi
if [ -e /etc/tmpfiles.d/connman_resolvconf.conf ]; then
	rm /etc/tmpfiles.d/connman_resolvconf.conf
fi
if [ -e /lib/systemd/system/hostapd.service ]; then
	sed -i 's/^Unit]/[Unit]/' /lib/systemd/system/hostapd.service
fi
systemctl stop connman
systemctl stop connman-env
systemctl disable connman
systemctl disable connman-env
ln -s /run/systemd/resolve/resolv.conf /etc/resolv.conf
systemctl stop wpa_supplicant
systemctl disable wpa_supplicant
systemctl disable wpa_supplicant@wlan0
systemctl enable systemd-resolved.service
systemctl enable hostapd.service
systemctl enable udhcpd.service

