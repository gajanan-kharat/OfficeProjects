ALTER TABLE COPQTMPS ADD CONSTRAINT COPQIMPS1 FOREIGN KEY (PGLIST_ID) REFERENCES COPQTPLL (ID);
ALTER TABLE COPQTMPS ADD CONSTRAINT COPQIMPS2 FOREIGN KEY (CLASZ_ID) REFERENCES COPQTACS (ID);
