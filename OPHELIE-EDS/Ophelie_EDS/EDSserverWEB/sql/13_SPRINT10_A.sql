  -- Table OPLQT Eds Connect Chs
  CREATE TABLE "OPL"."OPLQTECC" 
   (    "EDS_ID" VARCHAR2(36 BYTE) NOT NULL ENABLE, 
    "COMPONENT_ID" VARCHAR2(255 BYTE) NOT NULL ENABLE, 
     PRIMARY KEY ("EDS_ID", "COMPONENT_ID"));
 
  -- New Column to Describe the Pins
     
  ALTER TABLE "OPL"."OPLQTCHS"
    ADD "DESCRIBE_CAV" VARCHAR2(255);