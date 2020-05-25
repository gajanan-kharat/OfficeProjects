export LESSCHARSET=iso-8859-1
export LANG=fr_FR.UTF-8
export JAVA_HOME=/soft/jdk1.7.0_07/jre/
export PATH=$PATH:$JAVA_HOME/bin

# PLD = PROJECT_LIBRARY DIRECTORY
RACINE=/usersdev2/poc
PLD=$RACINE/lib/java/
LOG_PATH=$RACINE/log/

JAR_NAME=cop-batch

# Les fichiers de configuration
PROJET_LIB=$RACINE/data/cop-batch

NUMBERJAR=`ls $PLD/$JAR_NAME*jar | wc -l`
if [ $NUMBERJAR -ne 1 ]
then
   echo "ERROR: 1 seul fichier de type $JAR_NAME*jar doit etre present dans $PLD"
   echo "       Actuellement, $NUMBERJAR fichiers sont presents!!!"
   for batchJava  in `ls $PLD/$JAR_NAME*jar`
   do
   echo "       --> $batchJava"
   done
   exit 2
fi
for batchJava  in `ls $PLD/$JAR_NAME*`
do
        PROJET_LIB=$PROJET_LIB:$batchJava
done
export PROJET_LIB


$JAVA_HOME/bin/java -d64 -Xms512m -Xmx4096m -cp $PROJET_LIB -DUNXLOG=$LOG_PATH -Duser.language=fr  org.seedstack.seed.cli.SeedRunner run-job --job genomeBatchJob & 

RC=$?
echo "  Fin du batch Java. Code Retour = $RC"
echo "*********************************************************************"

# si pb detecte, on retourne 3
if [ $RC != 0 ]; then
    echo " ********************* BATCH EN ERREUR *********************"
        exit 3
fi

# On retourne 0 si tout s'est bien passe
echo " ********************* BATCH CORRECT *********************"
exit 0

