ALTER TABLE COPQTVPG ADD CONSTRAINT COPQIVPG1 FOREIGN KEY (LABEL_ID) REFERENCES COPQTPGL(ID);
ALTER TABLE COPQTVPG ADD CONSTRAINT COPQIVPG2 FOREIGN KEY (PGLIST_ID) REFERENCES COPQTVPL(ID);
ALTER TABLE COPQTVPG ADD CONSTRAINT COPQIVPG3 FOREIGN KEY (POLLUTANT_ID) REFERENCES COPQTPLT(ID);