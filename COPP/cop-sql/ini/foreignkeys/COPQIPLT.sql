ALTER TABLE COPQTPLT ADD CONSTRAINT COPQIPLT1 FOREIGN KEY (LABEL_ID) REFERENCES COPQTPGL(ID);
ALTER TABLE COPQTPLT ADD CONSTRAINT COPQIPLT2 FOREIGN KEY (PGLIST_ID) REFERENCES COPQTPLL(ID);