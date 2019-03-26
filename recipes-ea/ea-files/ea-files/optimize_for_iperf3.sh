#!/bin/sh

wl -i $1 frameburst 1
wl -i $1 PM 0
echo performance > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor
cat /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_cur_freq
wl -i $1 scansuppress
wl -i $1 scansuppress 1

echo "Done with configuration"
echo "Run performance test with:"
echo
echo "iperf3 -c 192.168.50.2 -i 5 -t 20 -P 4"
echo "iperf3 -c bouygues.iperf.fr -i 5 -t 20 -P 4"
echo "iperf3 -c ping.online.net -i 5 -t 20 -P 4"
echo "iperf3 -c speedtest.serverius.net -p 5002 -i 5 -t 20 -P 4"
echo "iperf3 -c iperf.eenet.ee -p 5201 -i 5 -t 20 -P 4"
echo "iperf3 -c iperf.volia.net -p 5201 -i 5 -t 20 -P 4"
echo
echo "or another server from https://iperf.fr/iperf-servers.php"
echo

