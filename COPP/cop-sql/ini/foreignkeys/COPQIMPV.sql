ALTER TABLE COPQTMPV ADD CONSTRAINT COPQIMPV1 FOREIGN KEY (PGLIST_ID) REFERENCES COPQTPLL(ID);
ALTER TABLE COPQTMPV ADD CONSTRAINT COPQIMPV2 FOREIGN KEY (VTECHNOLOGY_ID) REFERENCES COPQTAVT(ID);