ALTER TABLE PV2QTFAV ADD CONSTRAINT PV2QIFA1 FOREIGN KEY (PFM_ID) REFERENCES PV2QTPFM(ID);
ALTER TABLE PV2QTFAV ADD CONSTRAINT PV2QIFA2 FOREIGN KEY (USR_ID) REFERENCES PV2QTUSR(ID);