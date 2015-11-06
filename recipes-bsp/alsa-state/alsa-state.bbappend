# Change the default asound configuration from 44100Hz to 48000Hz on the iMX6 UltraLite 

do_install_append_mx6ul () {
  if [ -e ${D}/${sysconfdir}/asound.conf ]; then
    sed -e "s:playback\.pcm.*:playback.pcm \"dmix_48000\":g" \
        -e "s:capture\.pcm.*:capture.pcm \"dsnoop_48000\":g" \
        -i ${D}/${sysconfdir}/asound.conf
  fi
}
