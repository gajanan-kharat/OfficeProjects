ALTER TABLE COPQTPLM ADD CONSTRAINT COPQIPLM1 FOREIGN KEY (STATUSNATURE_ID) REFERENCES COPQTMSN (ID);
ALTER TABLE COPQTPLM ADD CONSTRAINT COPQIPLM2 FOREIGN KEY (PORGASLIMIT_ID) REFERENCES COPQTPLT (ID);
