ALTER TABLE COPQTMTC ADD CONSTRAINT COPQIMTC1 FOREIGN KEY (TVV_ID) REFERENCES COPQTTVV(ID);
ALTER TABLE COPQTMTC ADD CONSTRAINT COPQIMTC2 FOREIGN KEY (COUNTRY_ID) REFERENCES COPQTCTY(ID);