ALTER TABLE COPQTMFE ADD CONSTRAINT COPQIMFE1 FOREIGN KEY (FCLIST_ID) REFERENCES COPQTFLS(ID);
ALTER TABLE COPQTMFE ADD CONSTRAINT COPQIMFE2 FOREIGN KEY (EMS_ID) REFERENCES COPQTAES(ID);