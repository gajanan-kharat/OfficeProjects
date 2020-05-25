ALTER TABLE COPQTACT DROP FOREIGN KEY COPQIACT1;
ALTER TABLE COPQTACT  DROP COLUMN CLASZ_ID;

/*Table for Many to Many relationship between Class and Category*/
CREATE TABLE COPQTMCC (
   CATEGORY_ID BIGINT(20) DEFAULT NULL,
   CLASZ_ID BIGINT(20) DEFAULT NULL
 );

 /*Foreign keys for Class and Category*/
ALTER TABLE COPQTMCC ADD CONSTRAINT COPQIMCC1 FOREIGN KEY (CATEGORY_ID) REFERENCES COPQTACT(ID);
ALTER TABLE COPQTMCC ADD CONSTRAINT COPQIMCC2 FOREIGN KEY (CLASZ_ID) REFERENCES COPQTACS(ID);