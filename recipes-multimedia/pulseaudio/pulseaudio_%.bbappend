# Change the default pulseaudio configuration from 44100Hz to 48000Hz on the iMX6 UltraLite 

do_install_append_mx6ul () {
  if [ -e ${D}/${sysconfdir}/pulse/daemon.conf ]; then
    sed -e "s:.*default-sample-rate.*:default-sample-rate = 48000:g" \
        -i ${D}/${sysconfdir}/pulse/daemon.conf
  fi
}
