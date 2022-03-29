SUMMARY = "Miscellaneous files for the base system"
DESCRIPTION = "The ea-files package adds some files referenced in documentation."
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM="file://LICENSE;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://10-wired.network \
           file://20-wireless-wlan0.network \
           file://25-wireless-mlan0.network \
           file://wpa_supplicant@mlan0.service \
           file://wpa_supplicant@wlan0.service \
           file://hostapd@uap0.service \
           file://hostapd@wlan1.service \
           file://autostart_hostapd.sh \
           file://optimize_for_iperf3.sh \
           file://telnetd.service \
           file://apd_service_aid.sh \
           file://bluetooth_up.sh \
           file://LICENSE \
           "

S = "${WORKDIR}"

EA_FILES_644 ?= ""
EA_FILES_755 ?= ""
EA_FILES_DIRS ?= ""

do_install () {
	install -m 0755 -d ${D}${sysconfdir}/systemd/network
	install -m 0755 -d ${D}${systemd_system_unitdir}
	install -m 0755 -d ${D}/opt
	install -m 0755 -d ${D}/opt/ea

	#
	# Process a declaration like this in local.conf:
	#
	# EA_FILES_DIRS = "\
	#   /missing/folder/one \
	#   /opt/tmp/bob/two \
	# "
	#
	# Each folder in the list is created with the mode 755
	# meaning rwx-r-xr-x
	#
	for d in ${EA_FILES_DIRS}; do
		install -m 0755 -d ${D}${d}
	done

	install -m 0644 ${WORKDIR}/10-wired.network ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/20-wireless-wlan0.network ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/25-wireless-mlan0.network ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/wpa_supplicant@mlan0.service ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/wpa_supplicant@wlan0.service ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/hostapd@uap0.service ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/hostapd@wlan1.service ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/telnetd.service ${D}${systemd_system_unitdir}
	install -m 0755 ${WORKDIR}/autostart_hostapd.sh ${D}/opt/ea/
	install -m 0755 ${WORKDIR}/bluetooth_up.sh ${D}/opt/ea/
	install -m 0755 ${WORKDIR}/apd_service_aid.sh ${D}/opt/ea/
	install -m 0755 ${WORKDIR}/optimize_for_iperf3.sh ${D}/opt/ea/

	#
	# Process a declaration like this in local.conf:
	#
	# EA_FILES_644 = "\
	#   .profile:/home/root/.profile \
	# "
	#
	# If the part before the ':' starts with a / then it is considered
	# an absolute path, otherwise the build dir is prepended.
	# The part after the ':' is the destination file name.
	#
	# Note: The destination folder must exist. If you get build
	#       errors try adding it to the EA_FILES_DIRS list like
	#       the example above
	# Note: The 644 or 755 in the constant name represents the
	#       mode of the created file where 644 is rw-r--r-- and
	#       755 is rwxr-xr-x
	#
	for d in ${EA_FILES_644}; do
		f_in=$(echo "${d}" | cut -d":" -f1)
		f_out=$(echo "$d" | cut -d":" -f2)
		if [ "${f_in#/}" = "${f_in}" ]] ;
		then
			install -m 0644 ${TOPDIR}/${f_in} ${D}${f_out}
		else
			install -m 0644 ${f_in} ${D}${f_out}
		fi
	done
	for d in ${EA_FILES_755}; do
		f_in=$(echo "${d}" | cut -d":" -f1)
		f_out=$(echo "$d" | cut -d":" -f2)
		if [ "${f_in#/}" = "${f_in}" ]] ;
		then
			install -m 0755 ${TOPDIR}/${f_in} ${D}${f_out}
		else
			install -m 0755 ${f_in} ${D}${f_out}
		fi
	done
}

PACKAGES = "${PN}"
FILES:${PN} = "/"

