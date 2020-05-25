DELIMITER $$

CREATE PROCEDURE COPQPREF()
BEGIN
  INSERT INTO COPQTVEN(BOF_LCDVCODEVALUE, DOC_LCDVCODEVALUE, KMAT, BOF_FRLABEL, DOC_FRLABEL, BOF_ENTITYID, DOC_ENTITYID)
  SELECT TVVRULE_B0F.LCDV_CODE_VALUE,
         TVVRULE_DOC.LCDV_CODE_VALUE,
         TVVRULE_B0F.KMAT,
         CODEVALUE_B0F.FR_LABEL,
         CODEVALUE_DOC.FR_LABEL,
         TVVRULE_B0F.TVV_RULE_ID,
         TVVRULE_DOC.TVV_RULE_ID
  FROM
        COPQTGTV TVVRULE_B0F ,
        COPQTGTV TVVRULE_DOC ,
        COPQTGCV CODEVALUE_B0F,
        COPQTGCV CODEVALUE_DOC
WHERE
    TVVRULE_B0F.LCDV_CODE_NAME = 'B0F' 
AND TVVRULE_DOC.LCDV_CODE_NAME = 'DOC' 
AND TVVRULE_B0F.KMAT = TVVRULE_DOC.KMAT 
AND TVVRULE_B0F.RULE_IDENTIFIANT = TVVRULE_DOC.RULE_IDENTIFIANT
AND TVVRULE_B0F.LCDV_CODE_VALUE = CODEVALUE_B0F.LCDV_CODE_VALUE 
AND TVVRULE_B0F.LCDV_CODE_VALUE_ID = CODEVALUE_B0F.LCDV_CODE_VALUE_ID
AND TVVRULE_DOC.LCDV_CODE_VALUE_ID = CODEVALUE_DOC.LCDV_CODE_VALUE_ID
AND TVVRULE_DOC.LCDV_CODE_VALUE = CODEVALUE_DOC.LCDV_CODE_VALUE;

INSERT INTO COPQTVGB(BOG_LCDVCODEVALUE, DBM_LCDVCODEVALUE, KMAT, BOG_FRLABEL, DBM_FRLABEL, BOG_ENTITYID, DBM_ENTITYID)
 SELECT 
    TVVRULE_B0G_.LCDV_CODE_VALUE ,
    TVVRULE_DBM.LCDV_CODE_VALUE ,
    TVVRULE_DBM.KMAT ,
    CODEVALUE_B0G.FR_LABEL ,
    CODEVALUE_DBM.FR_LABEL ,
    TVVRULE_B0G_.TVV_RULE_ID,
	TVVRULE_DBM.TVV_RULE_ID
FROM
    COPQTGTV TVVRULE_DBM,
    COPQTGCV CODEVALUE_DBM ,
    COPQTGTV TVVRULE_B0G_,
    COPQTGCV CODEVALUE_B0G
WHERE
    TVVRULE_DBM.LCDV_CODE_VALUE_ID = CODEVALUE_DBM.LCDV_CODE_VALUE_ID 
    AND TVVRULE_B0G_.LCDV_CODE_VALUE_ID = CODEVALUE_B0G.LCDV_CODE_VALUE_ID 
    AND TVVRULE_DBM.LCDV_CODE_NAME = 'DBM' 
    AND TVVRULE_B0G_.LCDV_CODE_NAME = 'B0G' 
    AND TVVRULE_DBM.KMAT = TVVRULE_B0G_.KMAT 
    AND TVVRULE_DBM.RULE_IDENTIFIANT = TVVRULE_B0G_.RULE_IDENTIFIANT;  
END