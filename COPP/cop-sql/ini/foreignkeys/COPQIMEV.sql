ALTER TABLE COPQTMEV ADD CONSTRAINT COPQIMEV1 FOREIGN KEY (EMS_ID) REFERENCES COPQTAES(ID);
ALTER TABLE COPQTMEV ADD CONSTRAINT COPQIMEV2 FOREIGN KEY (VTECHNOLOGY_ID) REFERENCES COPQTAVT(ID);