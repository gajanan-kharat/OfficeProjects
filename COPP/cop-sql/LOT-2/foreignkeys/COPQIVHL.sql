ALTER TABLE COPQTVHL ADD CONSTRAINT COPQIVHL1 FOREIGN KEY (TECHINICAL_CASE_ID) REFERENCES COPQTTTC(ID);
ALTER TABLE COPQTVHL ADD CONSTRAINT COPQIVHL2 FOREIGN KEY (PARKING_LOT_ID) REFERENCES COPQTPKL(ID);
ALTER TABLE COPQTVHL ADD CONSTRAINT COPQIVHL3 FOREIGN KEY (VEHICLE_FACTORY_ID) REFERENCES COPQTFTY(FACTORY_ID);
ALTER TABLE COPQTVHL ADD CONSTRAINT COPQIVHL4 FOREIGN KEY (COUNTRY_ID) REFERENCES COPQTCTY(ID);

-- AS CORVET DATA TABLE IS CREATING AFTER THIS SQL, SO THIS BELOW FK WILL NOT BE CREATEE
-- ALTER TABLE COPQTVHL ADD CONSTRAINT COPQIVHL5 FOREIGN KEY (CORVET_DATA_ID) REFERENCES COPQTCVD(ID);