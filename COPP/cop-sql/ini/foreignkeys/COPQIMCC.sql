ALTER TABLE COPQTMCC ADD CONSTRAINT COPQIMCC1 FOREIGN KEY (CATEGORY_ID) REFERENCES COPQTACT(ID);
ALTER TABLE COPQTMCC ADD CONSTRAINT COPQIMCC2 FOREIGN KEY (CLASZ_ID) REFERENCES COPQTACS(ID);