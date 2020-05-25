#!/bin/ksh

_prd=opg
_occ=00
_cdd_home=/usersdev2/${_prd}
_tom_home=/users/${_prd}${_occ}/tom70

# We search for the notification file
_ready_file=${_tom_home}/.maven-files/ready-to-deploy.ok

# we will loop 3 times to wait a little bit, if the script post-processing has not yet finished
_count=0
while ( ! [ -f ${_ready_file} ] && [ ${_count} < 3 ] ); do
        sleep 60
        _count=$(( ${_count} + 1 ))
done

if [ -f ${_ready_file} ]; then
        # we are OK : returning 0 tells that the deployment can start
        exit 0
else
        # we cannot start the deployment...
        echo "Impossible to launch the deployment because all delivery files are not ready yet."
        # returning 1 tells that the deployment should not occur
        exit 1
fi

