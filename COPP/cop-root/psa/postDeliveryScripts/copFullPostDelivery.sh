#!/bin/ksh

if [[ $(echo $1 | grep 'dev' | wc -l) == 1 ]]
    then
         cd /usersdev2/poc
         rm -rf /usersdev2/poc/data/cop-batch
         rm -rf /usersdev2/poc/data/cop-web
         rm -rf /usersdev2/poc/lib/java
         rm -rf /usersdev2/poc/sql
         rm -rf /usersdev2/poc/J2EE
         rm -rf /usersdev2/poc/delivery_install
         rm -rf /usersdev2/poc/scripts/

         [ ! -d /usersdev2/poc/data/cop-batch ] && mkdir -p /usersdev2/poc/data/cop-batch
         [ ! -d /usersdev2/poc/data/cop-web ] && mkdir -p /usersdev2/poc/data/cop-web
         [ ! -d /usersdev2/poc/lib/java ] && mkdir -p /usersdev2/poc/lib/java
         [ ! -d /usersdev2/poc/sql ] && mkdir -p /usersdev2/poc/sql
         [ ! -d /usersdev2/poc/J2EE ] && mkdir -p /usersdev2/poc/J2EE
         [ ! -d /usersdev2/poc/delivery_install ]  && mkdir -p  /usersdev2/poc/delivery_install
         [ ! -d /usersdev2/poc/scripts/ ] && mkdir -p /usersdev2/poc/scripts/

         cd /usersdev2/poc/delivery_install
         tar -xf /usersdev2/poc/poc00etuind/$1

         mv ./J2EE/* /usersdev2/poc/J2EE
         mv ./lib/java/* /usersdev2/poc/lib/java
         mv ./scripts/* /usersdev2/poc/scripts/
         mv ./sql/* /usersdev2/poc/sql
         mv ./valorisation/batch/templates/*  /usersdev2/poc/data/cop-batch/
         mv ./valorisation/web/templates/* /usersdev2/poc/data/cop-web/
         
         cd  /usersdev2/poc/
         rm -rf ./delivery_install
         
         # Copy War and Deploy It to the TomCat Server
         
        cp ./J2EE/*.war /users/poc00/tom70/installableApps/
        cp ./J2EE/poc00.xml /users/poc00/tom70/
        
        /usersdev2/poc/delivery/restartTom.sh
         
         

    else
        printf "Pas de deploiment pour cette version (deploiement uniquement pour *dev.tar)"
fi
