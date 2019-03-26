SUMMARY = "Miscellaneous files for the base system"
DESCRIPTION = "The ea-files package adds some files referenced in documentation."
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM="file://LICENSE;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://wired.network \
           file://wireless.network \
           file://wpa_supplicant@wlan0.service \
           file://autostart_network.sh \
           file://optimize_for_iperf3.sh \
           file://telnetd.service \
           file://LICENSE \
           "

S = "${WORKDIR}"

EA_FILES_644 ?= ""
EA_FILES_755 ?= ""
EA_FILES_DIRS ?= ""

do_install () {
	install -m 0755 -d ${D}${sysconfdir}/systemd/network
	install -m 0755 -d ${D}${sysconfdir}/systemd/system
	install -m 0755 -d ${D}${libdir}/systemd/system
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

	install -m 0644 ${WORKDIR}/wired.network ${D}${sysconfdir}/systemd/network/wired.network
	install -m 0644 ${WORKDIR}/wireless.network ${D}${sysconfdir}/systemd/network/wireless.network
	install -m 0644 ${WORKDIR}/wpa_supplicant@wlan0.service ${D}${sysconfdir}/systemd/system/wpa_supplicant@wlan0.service
	install -m 0755 ${WORKDIR}/autostart_network.sh ${D}/opt/ea/autostart_network.sh
	install -m 0755 ${WORKDIR}/autostart_network.sh ${D}/opt/ea/optimize_for_iperf3.sh
	install -m 0644 ${WORKDIR}/telnetd.service ${D}${libdir}/systemd/system/telnetd.service

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
FILES_${PN} = "/"

