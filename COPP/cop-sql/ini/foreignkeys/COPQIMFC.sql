ALTER TABLE COPQTMFC ADD CONSTRAINT COPQIMFC1 FOREIGN KEY (FCLISTID) REFERENCES COPQTFLS(ID);
ALTER TABLE COPQTMFC ADD CONSTRAINT COPQIMFC2 FOREIGN KEY (CATEGORYID) REFERENCES COPQTACT(ID);