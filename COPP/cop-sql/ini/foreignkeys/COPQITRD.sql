ALTER TABLE COPQTTRD ADD CONSTRAINT COPQIRD1 FOREIGN KEY (TVV_RULE_ID) REFERENCES COPQTGTV (TVV_RULE_ID);
ALTER TABLE COPQTTRD ADD CONSTRAINT COPQIRD2 FOREIGN KEY (LCDV_DICTIONARY_ID) REFERENCES COPQTGDY (LCDV_DICTIONARY_ID);