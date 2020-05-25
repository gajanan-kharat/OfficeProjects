
-- Current report type change
-- Temp
ALTER TABLE OPLQTSPP ADD (CSPP_MESURE_TEMP_TMP VARCHAR(255));
UPDATE OPLQTSPP SET CSPP_MESURE_TEMP_TMP = TO_CHAR(CSPP_MESURE_TEMP);

ALTER TABLE OPLQTSPP DROP COLUMN CSPP_MESURE_TEMP;
ALTER TABLE OPLQTSPP RENAME COLUMN CSPP_MESURE_TEMP_TMP TO CSPP_MESURE_TEMP;

-- Y Axis
ALTER TABLE OPLQTSPP ADD (CSPP_Y_AXIS_VALUE_TMP VARCHAR(255));
UPDATE OPLQTSPP SET CSPP_Y_AXIS_VALUE_TMP = TO_CHAR(CSPP_Y_AXIS_VALUE);

ALTER TABLE OPLQTSPP DROP COLUMN CSPP_Y_AXIS_VALUE;
ALTER TABLE OPLQTSPP RENAME COLUMN CSPP_Y_AXIS_VALUE_TMP TO CSPP_Y_AXIS_VALUE;

-- Time Axis
ALTER TABLE OPLQTSPP ADD (CSPP_TIME_AXIS_VALUE_TMP VARCHAR(255));
UPDATE OPLQTSPP SET CSPP_TIME_AXIS_VALUE_TMP = TO_CHAR(CSPP_TIME_AXIS_VALUE);

ALTER TABLE OPLQTSPP DROP COLUMN CSPP_TIME_AXIS_VALUE;
ALTER TABLE OPLQTSPP RENAME COLUMN CSPP_TIME_AXIS_VALUE_TMP TO CSPP_TIME_AXIS_VALUE;

-- Source impedance
ALTER TABLE OPLQTSPP ADD (CSPP_SOURCE_IMPEDANCE_TMP VARCHAR(255));
UPDATE OPLQTSPP SET CSPP_SOURCE_IMPEDANCE_TMP = TO_CHAR(CSPP_SOURCE_IMPEDANCE);

ALTER TABLE OPLQTSPP DROP COLUMN CSPP_SOURCE_IMPEDANCE;
ALTER TABLE OPLQTSPP RENAME COLUMN CSPP_SOURCE_IMPEDANCE_TMP TO CSPP_SOURCE_IMPEDANCE;
