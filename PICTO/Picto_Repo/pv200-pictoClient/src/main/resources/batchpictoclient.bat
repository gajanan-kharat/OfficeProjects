@echo off

REM Set relase location. It should contain the following folders : data 

REM add data folder to classpath
REM SET DATA_LOC=D:\thick_client_jars\PSA\trunk\pv200-pictoClient\src\main\resources\properties
SET JAR_LOC=D:\thick_client_jars\PSA\trunk\pv200-pictoClient\target\pv200-pictoClient-1.0.2-SNAPSHOT.jar
SET CLASSPATH="C:\Program Files\Java\jdk1.7.0_40\bin"
SET CLASSPATH=%CLASSPATH%;%DATA_LOC%;%JAR_LOC%
SET CLASSPATH=%CLASSPATH%;"C:/Program Files/Java/jre7/lib/jfxrt.jar"

REM echo %classpath%
java -classpath %classpath% com.inetpsa.pv2.batch.ThickClient
REM java -classpath %classpath% org.seedstack.seed.cli.SeedRunner run-job --job thickClientJob

							


