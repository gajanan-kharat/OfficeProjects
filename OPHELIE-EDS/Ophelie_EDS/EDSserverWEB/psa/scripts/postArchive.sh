#!/bin/ksh

_prd=opl
_occ=00
_cdd_home=/usersdev2/${_prd}
_tom_home=/users/${_prd}${_occ}/tom70

# we clean up the notification file created before
_ready_file=${_tom_home}/.maven-files/ready-to-deploy.ok
if [ -f ${_ready_file} ]; then
        rm ${_ready_file}
fi

# We clear the delivery directories to remove the old files
[ -d "${_cdd_home}/delivery/data" ] && rm -rf ${_cdd_home}/delivery/data
[ -d "${_cdd_home}/delivery/j2ee" ] && rm -rf ${_cdd_home}/delivery/j2ee
[ -d "${_cdd_home}/delivery/lib" ] && rm -rf ${_cdd_home}/delivery/lib
[ -d "${_cdd_home}/delivery/sql" ] && rm -rf ${_cdd_home}/delivery/sql
[ -d "${_cdd_home}/delivery/valuation" ] && rm -rf ${_cdd_home}/delivery/valuation

# Then we explode the delivered archive
cd ${_cdd_home}/delivery/
tar -xf ${_cdd_home}/delivery/$1

# We copy war to the j2ee folder
cp -r ${_cdd_home}/delivery/j2ee/* ${_tom_home}/installableApps/
mv ${_tom_home}/installableApps/${_prd}${_occ}.xml ${_tom_home}

# We lib content to the tomcat lib
cp -r ${_cdd_home}/delivery/lib/* ${_tom_home}/lib/

# We copy the files externalized for DEV into the
# directory which has been configured to extend the server classpath
[ -d "${_cdd_home}/data" ] && rm -rf ${_cdd_home}/data
cp -r ${_cdd_home}/delivery/data ${_cdd_home}/data

# everything is OK, we create a new notification file to indicate that we're ready
# to deploy
touch ${_ready_file}

# And finally, we clean up all the files
[ -f "${_cdd_home}/delivery/$1" ] && rm ${_cdd_home}/delivery/$1
[ -f "${_cdd_home}/delivery/nohup.out" ] && rm ${_cdd_home}/delivery/nohup.out

exit 0

