PACKAGECONFIG:append = " \
    ${@bb.utils.contains('BBFILE_COLLECTIONS', 'qt6-layer', 'qt6', '', d)} \
"
