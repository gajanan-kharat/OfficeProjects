#!/bin/ksh

_prd=pv2
_occ=00
_cdd_home=/usersdev2/${_prd}
_tom_home=/users/${_prd}${_occ}/tom70

# we clean up the notification file created before
_ready_file=${_tom_home}/.maven-files/ready-to-deploy.ok
if [ -f ${_ready_file} ]; then
        rm ${_ready_file}
fi

# We clear the delivery directories to remove the old files
[ -d "${_cdd_home}/delivery/pv200" ] && rm -rf ${_cdd_home}/delivery/pv200

# Then we explode the delivered archive
cd ${_cdd_home}/delivery/
tar -xf ${_cdd_home}/pv200etuind/$1

# We copy war to the j2ee folder
cp -r ${_cdd_home}/delivery/pv200/script/web/* ${_tom_home}/installableApps/
mv ${_tom_home}/installableApps/${_prd}${_occ}.xml ${_tom_home}/


# everything is OK, we create a new notification file to indicate that we're ready
# to deploy
touch ${_ready_file}

# And finally, we clean up all the files
#[ -f "${_cdd_home}/delivery/nohup.out" ] && rm ${_cdd_home}/delivery/nohup.out

exit 0

