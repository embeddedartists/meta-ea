#!/bin/sh

ifconfig wlan0 192.168.1.1 netmask 255.255.255.0 broadcast 192.168.1.255 up
mkdir -p /var/lib/misc/
touch /var/lib/misc/udhcpd.leases
