ALTER TABLE COPQTTRC ADD CONSTRAINT COPQITRC1 FOREIGN KEY (TVV_RULE_ID) REFERENCES COPQTGTV (TVV_RULE_ID);
ALTER TABLE COPQTTRC ADD CONSTRAINT COPQITRC2 FOREIGN KEY (LCDV_CODE_ID) REFERENCES COPQTGCO (LCDV_CODE_ID);