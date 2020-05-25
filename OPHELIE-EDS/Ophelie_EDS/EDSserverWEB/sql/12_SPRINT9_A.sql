-- [EX_CONN_94]

-- New EDS comment type
ALTER TABLE OPLQTEDS ADD EDS_DESCRIPTION VARCHAR2(256);

-- Set the comment to the old EDS name
UPDATE OPLQTEDS A
SET EDS_DESCRIPTION = (SELECT B.EDS_NAME
                       FROM OPLQTEDS B
                       WHERE A.EDS_ID = B.EDS_ID);

-- Also remove the old name
UPDATE OPLQTEDS SET EDS_NAME = '-';

--- Commented
--- Explanation: The order of the strings influences the order that is shown in the menu as the specification required the "Connectivity" node 
--- to be after "Closed Stage" the next query would just add it to the end.
--- Effect: Add the Connectivity node to the end of the menu of each EDS model 
--- UPDATE OPLQTCOT 
--- set CT_BTTBT_OK_FORMSET = CT_BTTBT_OK_FORMSET || ' eds-connectivity-association  eds-connectivity-bsxlink eds-connectivity-cavitydef';


--- Add the 'Connectivity' node to the specific order. It should be checked if it's the same CT_ID as ours and if there weren't any change on their side

UPDATE OPLQTCOT
set CT_BTTBT_OK_FORMSET = 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-robust-cc eds-profil-mission eds-consolidate-cc eds-psa-measure-cc eds-connectivity-association eds-connectivity-bsxlink eds-connectivity-cavitydef eds-version-history eds-attachments'
where CT_ID = '2c357add-d69a-469c-8136-a6310cf32b07';

UPDATE OPLQTCOT
set CT_BTTBT_OK_FORMSET = 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-robust-cc eds-comportement-mission eds-profil-mission eds-consolidate-cc eds-comportement-consolide eds-profil-activation eds-psa-measure-cc eds-connectivity-association eds-connectivity-bsxlink eds-connectivity-cavitydef eds-version-history eds-attachments'
where CT_ID = '3bd851e2-960c-4287-8eb2-efb008d092e5';

UPDATE OPLQTCOT
set CT_BTTBT_OK_FORMSET = 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-tension-alimentation-preliminaire eds-robust-cc eds-comportement-mission eds-profil-mission eds-consolidate-cc eds-tension-alimentation-consolide eds-comportement-consolide eds-profil-activation eds-psa-measure-cc eds-connectivity-association eds-connectivity-bsxlink eds-connectivity-cavitydef eds-version-history eds-attachments'
where CT_ID = '5649949c-6663-4745-8636-52909ddad9a6';

UPDATE OPLQTCOT
set CT_BTTBT_OK_FORMSET = 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-tension-alimentation-preliminaire eds-robust-cc eds-comportement-mission eds-profil-mission eds-consolidate-cc eds-tension-alimentation-consolide eds-comportement-consolide eds-profil-activation eds-psa-measure-cc eds-connectivity-association eds-connectivity-bsxlink eds-connectivity-cavitydef eds-version-history eds-attachments'
where CT_ID = '7a7a5a4e-03ec-491d-aab5-86ecbf843786';

UPDATE OPLQTCOT
set CT_BTTBT_OK_FORMSET = 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-tension-alimentation-preliminaire eds-robust-cc eds-comportement-mission eds-profil-mission eds-consolidate-cc eds-tension-alimentation-consolide eds-comportement-consolide eds-profil-activation eds-cse eds-psa-measure-cc eds-connectivity-association eds-connectivity-bsxlink eds-connectivity-cavitydef eds-version-history eds-attachments'
where CT_ID = '91af56c2-1a55-43fc-9711-855aff2fbfac';

UPDATE OPLQTCOT
set CT_BTTBT_OK_FORMSET = 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-robust-cc eds-comportement-mission eds-consolidate-cc eds-comportement-consolide eds-cse eds-defaillance-veille-reveil eds-psa-measure-cc eds-connectivity-association eds-connectivity-bsxlink eds-connectivity-cavitydef eds-version-history eds-attachments'
where CT_ID = '93272607-2e32-445b-bf31-986186811b77';

UPDATE OPLQTCOT
set CT_BTTBT_OK_FORMSET = 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-tension-alimentation-preliminaire eds-robust-cc eds-comportement-mission eds-profil-mission eds-consolidate-cc eds-tension-alimentation-consolide eds-comportement-consolide eds-profil-activation eds-cse eds-defaillance-veille-reveil eds-psa-measure-cc eds-connectivity-association eds-connectivity-bsxlink eds-connectivity-cavitydef eds-version-history eds-attachments'
where CT_ID = 'a2dded37-ed35-41ce-a3f5-10cfdcbacc45';

UPDATE OPLQTCOT
set CT_BTTBT_OK_FORMSET = 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-tension-alimentation-preliminaire eds-robust-cc eds-comportement-mission eds-profil-mission eds-consolidate-cc eds-tension-alimentation-consolide eds-comportement-consolide eds-profil-activation eds-cse eds-defaillance-veille-reveil eds-psa-measure-cc eds-connectivity-association eds-connectivity-bsxlink eds-connectivity-cavitydef eds-version-history eds-attachments'
where CT_ID = 'b4fcfc76-e1d8-468c-8f9e-73054d1c18b7';

UPDATE OPLQTCOT
set CT_BTTBT_OK_FORMSET = 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-tension-alimentation-preliminaire eds-robust-cc eds-comportement-mission eds-consolidate-cc eds-tension-alimentation-consolide eds-comportement-consolide eds-psa-measure-cc eds-connectivity-association eds-connectivity-bsxlink eds-connectivity-cavitydef eds-version-history eds-attachments'
where CT_ID = 'c9f0bb30-1b94-40c2-8c8a-91a2fd1729dd';

UPDATE OPLQTCOT
set CT_BTTBT_OK_FORMSET = 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-robust-cc eds-consolidate-cc eds-profil-activation eds-psa-measure-cc eds-connectivity-association eds-connectivity-bsxlink eds-connectivity-cavitydef eds-version-history eds-attachments'
where CT_ID = 'fb082bde-33db-43c2-8a53-b5e186606f76';
