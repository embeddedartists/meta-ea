#!/bin/sh

echo "Setting up auto start of wired and wireless networks"
echo "after reboot. Make sure that /etc/wpa_supplicant.conf"
echo "contains valid credentials for the wireless network."
echo ""

if [ -e /etc/resolv.conf ]; then
	rm /etc/resolv.conf
fi
if [ -e /etc/tmpfiles.d/connman_resolvconf.conf ]; then
	rm /etc/tmpfiles.d/connman_resolvconf.conf
fi
systemctl stop connman
systemctl stop connman-env
systemctl disable connman
systemctl disable connman-env
ln -s /run/systemd/resolve/resolv.conf /etc/resolv.conf
systemctl stop wpa_supplicant
systemctl disable wpa_supplicant
systemctl enable wpa_supplicant@wlan0
systemctl enable systemd-resolved.service

