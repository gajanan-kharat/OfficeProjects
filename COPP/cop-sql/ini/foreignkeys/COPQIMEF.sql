ALTER TABLE COPQTMEF ADD CONSTRAINT COPQIMEF1 FOREIGN KEY (EMS_ID) REFERENCES COPQTAES(ID);
ALTER TABLE COPQTMEF ADD CONSTRAINT COPQIMEF2 FOREIGN KEY (FUEL_ID) REFERENCES COPQTAFL(ID);