require u-boot-ea-common_${PV}.inc

SUMMARY = "U-Boot bootloader fw_printenv/setenv utilities"
DEPENDS_append = " mtd-utils bison-native"

SRC_URI += " \
   file://fw_env.config \
   file://fw_unlock_mmc.sh \
"

INSANE_SKIP_${PN} = "already-stripped"
EXTRA_OEMAKE_class-target = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${CC} ${CFLAGS} ${LDFLAGS}" HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}" V=1'
EXTRA_OEMAKE_class-cross = 'ARCH=${TARGET_ARCH} CC="${CC} ${CFLAGS} ${LDFLAGS}" V=1'

inherit uboot-config

do_compile () {
    oe_runmake ${UBOOT_MACHINE}
    oe_runmake envtools
}

do_install () {
    install -Dm 0755 ${S}/tools/env/fw_printenv   ${D}${base_sbindir}/fw_printenv
    install -Dm 0755 ${S}/tools/env/fw_printenv   ${D}${base_sbindir}/fw_setenv
    install -Dm 0644 ${WORKDIR}/fw_env.config     ${D}${sysconfdir}/fw_env.config
    install -Dm 0644 ${WORKDIR}/fw_unlock_mmc.sh  ${D}${sysconfdir}/profile.d/fw_unlock_mmc.sh
}

do_install_class-cross () {
    install -d ${D}${bindir_cross}
    install -m 755 ${S}/tools/env/fw_printenv ${D}${bindir_cross}/fw_printenv
    install -m 755 ${S}/tools/env/fw_printenv ${D}${bindir_cross}/fw_setenv
}

SYSROOT_PREPROCESS_FUNCS_class-cross = "uboot_fw_utils_cross"
uboot_fw_utils_cross() {
    sysroot_stage_dir ${D}${bindir_cross} ${SYSROOT_DESTDIR}${bindir_cross}
}

RPROVIDES_${PN} += "u-boot-fw-utils"

BBCLASSEXTEND = "cross"

pkg_postinst_ontarget_${PN} () {
    # Environment in eMMC is located in /dev/mmcblk*boot0 at offset (2Mb-8Kb).
    # See CONFIG_ENV_OFFSET in the configuration file for the u-boot.
    dev=`ls /dev/mmcblk*boot*`
    dev=($dev)
    dev=${dev[0]}
    DISK=$dev
    CONFIG_ENV_SIZE=8192 # 0x2000, 8Kb
    CONFIG_ENV_OFFSET=2097152 # 0x200000, 2Mb
    CONFIG_ENV_OFFSET=`expr $CONFIG_ENV_OFFSET - $CONFIG_ENV_SIZE`
    printf "%s\t0x%X\t0x%X\n" $DISK $CONFIG_ENV_OFFSET $CONFIG_ENV_SIZE >> "/etc/fw_env.config"
}
