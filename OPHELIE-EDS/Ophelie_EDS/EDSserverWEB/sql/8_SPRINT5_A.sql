-- [Ex_Fich_47]
-- Add a comment on each power supply presents in the pilot of drifts
ALTER TABLE EDS_SUPPLY
    ADD SEDS_DRIFT_COMMENT VARCHAR(1024);
    
-- [Ex_Fich_59]
-- Remove an Actor from the Validation Table.
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA DROP (HVFD_RE_CONSOLIDATE_ID, HVFD_RE_CLOSED_ID);