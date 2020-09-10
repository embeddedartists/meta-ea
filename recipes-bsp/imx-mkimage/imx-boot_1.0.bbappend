# The imx-mkimage makefile requires a dtb file named (PLAT)-evk.dtb
# where PLAT is the platform name, such as imx8mq. 
# Copying and renaming the EA dtb file.
do_compile_prepend() {
	echo "Copying DTB"
	echo ${DEPLOY_DIR_IMAGE}
	echo ${BOOT_TOOLS}
	if [ -f ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/imx8mq-ea-com-kit_v2.dtb ]; then
	  cp ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/imx8mq-ea-com-kit_v2.dtb ${S}/iMX8M/imx8mq-evk.dtb
	fi
	if [ -f ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/imx8mm-ea-ucom-kit_v2.dtb ]; then
	  cp ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/imx8mm-ea-ucom-kit_v2.dtb ${S}/iMX8M/imx8mm-evk.dtb
	fi
	if [ -f ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/imx8mn-ea-ucom-kit_v2.dtb ]; then
	  cp ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/imx8mn-ea-ucom-kit_v2.dtb ${S}/iMX8M/imx8mn-ddr4-evk.dtb
	fi
}
