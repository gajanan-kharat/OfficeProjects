ALTER TABLE COPQTVHF ADD CONSTRAINT COPQIVHF1 FOREIGN KEY (VEHICLE_ID) REFERENCES COPQTVHL(ID);
ALTER TABLE COPQTVHF ADD CONSTRAINT COPQIVHF2 FOREIGN KEY (VFILE_STATUS_ID) REFERENCES COPQTVFS(ID);
ALTER TABLE COPQTVHF ADD CONSTRAINT COPQIVHF3 FOREIGN KEY (SCHEDULE_ID) REFERENCES COPQTSDL(ID);
ALTER TABLE COPQTVHF ADD CONSTRAINT COPQIVHF4 FOREIGN KEY (RECEPTION_FILE_ID) REFERENCES COPQTRCF(ID);
ALTER TABLE COPQTVHF ADD CONSTRAINT COPQIVHF5 FOREIGN KEY (RESTITUTION_FILE_ID) REFERENCES COPQTRTF(ID);
ALTER TABLE COPQTVHF ADD CONSTRAINT COPQIVHF6 FOREIGN KEY (TYPE_TEST_ID) REFERENCES COPQTTOT(ID);
ALTER TABLE COPQTVHF ADD CONSTRAINT COPQIVHF7 FOREIGN KEY (ARCHIVE_BOX_ID) REFERENCES COPQTARB(ID);
ALTER TABLE COPQTVHF ADD CONSTRAINT COPQIVHF8 FOREIGN KEY (BASKET_ID) REFERENCES COPQTBKT(ID);
ALTER TABLE COPQTVHF ADD CONSTRAINT COPQIVHF9 FOREIGN KEY (PREVIOUS_VF_STATUS_ID) REFERENCES COPQTVFS(ID);