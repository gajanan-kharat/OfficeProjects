-- PSA Robust Supply Use Case

-- Table OPLQT RSU (EdsRobustSupplyUseCase)
alter table OPLQTRSU add RSUC_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNRSU
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTIRSU
  before insert on OPLQTRSU
  for each row
begin
  select OPLQNRSU.nextval
  into :new.RSUC_INSERT_ORDER
  from dual;
end;
/

-- =============================================================

-- PSA Mesure Supply
 
-- Table OPLQT SMP (EdsConsSupProfile)
alter table OPLQTSMP add JCSP_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNSMP
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTISMP
  before insert on OPLQTSMP
  for each row
begin
  select OPLQNSMP.nextval
  into :new.JCSP_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT UMC (EdsCourantBloqueCourantDysfonctionnement)
alter table OPLQTUMC add JCSMCBCDEDS_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNUMC
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTIUMC
  before insert on OPLQTUMC
  for each row
begin
  select OPLQNUMC.nextval
  into :new.JCSMCBCDEDS_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT MMM (EdsModeMontage)
alter table OPLQTMMM add JCSMMMEDS_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNMMM
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTIMMM
  before insert on OPLQTMMM
  for each row
begin
  select OPLQNMMM.nextval
  into :new.JCSMMMEDS_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT PMC (EdsCourantNominaleActivation)
alter table OPLQTPMC add JCSMCNAEDS_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNPMC
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTIPMC
  before insert on OPLQTPMC
  for each row
begin
  select OPLQNPMC.nextval
  into :new.JCSMCNAEDS_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT SMC (EdsCourantAppelleActivation)
alter table OPLQTSMC add JCSMCAAEDS_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNSMC
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTISMC
  before insert on OPLQTSMC
  for each row
begin
  select OPLQNSMC.nextval
  into :new.JCSMCAAEDS_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT MCM (EdsCourantMiseSousTension)
alter table OPLQTMCM add JCSMCMSTEDS_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNMCM
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTIMCM
  before insert on OPLQTMCM
  for each row
begin
  select OPLQNMCM.nextval
  into :new.JCSMCMSTEDS_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT SMR (EdsReseauVeilleReveilleOrganeInactif)
alter table OPLQTSMR add JCSMRVROIEDS_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNSMR
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTISMR
  before insert on OPLQTSMR
  for each row
begin
  select OPLQNSMR.nextval
  into :new.JCSMRVROIEDS_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT MMP (EdsModeParc)
alter table OPLQTMMP add JCSMMPEDS_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNMMP
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTIMMP
  before insert on OPLQTMMP
  for each row
begin
  select OPLQNMMP.nextval
  into :new.JCSMMPEDS_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT SMT (ConsolidateSupplyEdsTension)
alter table OPLQTSMT add JCSU_CSU_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNSMT
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTISMT
  before insert on OPLQTSMT
  for each row
begin
  select OPLQNSMT.nextval
  into :new.JCSU_CSU_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
-- PSA Theorical Supply

-- Table OPLQT STP (EdsConsSupProfile)
alter table OPLQTSTP add JCSP_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNSTP
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTISTP
  before insert on OPLQTSTP
  for each row
begin
  select OPLQNSTP.nextval
  into :new.JCSP_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT UTC (EdsCourantBloqueCourantDysfonctionnement)
alter table OPLQTUTC add JCSTCBCDEDS_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNUTC
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTIUTC
  before insert on OPLQTUTC
  for each row
begin
  select OPLQNUTC.nextval
  into :new.JCSTCBCDEDS_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT TMP (EdsModeParc)
alter table OPLQTTMP add JCSTMPEDS_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNTMP
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTITMP
  before insert on OPLQTTMP
  for each row
begin
  select OPLQNTMP.nextval
  into :new.JCSTMPEDS_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT STR (EdsReseauVeilleReveilleOrganeInactif)
alter table OPLQTSTR add JCSTRVROIEDS_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNSTR
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTISTR
  before insert on OPLQTSTR
  for each row
begin
  select OPLQNSTR.nextval
  into :new.JCSTRVROIEDS_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT TCM (EdsCourantMiseSousTension)
alter table OPLQTTCM add JCSTCMSTEDS_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNTCM
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTITCM
  before insert on OPLQTTCM
  for each row
begin
  select OPLQNTCM.nextval
  into :new.JCSTCMSTEDS_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT TMM (EdsModeMontage)
alter table OPLQTTMM add JCSTMMEDS_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNTMM
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTITMM
  before insert on OPLQTTMM
  for each row
begin
  select OPLQNTMM.nextval
  into :new.JCSTMMEDS_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT PTC (EdsCourantNominaleActivation)
alter table OPLQTPTC add JCSTCNAEDS_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNPTC
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTIPTC
  before insert on OPLQTPTC
  for each row
begin
  select OPLQNPTC.nextval
  into :new.JCSTCNAEDS_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT STC (EdsCourantAppelleActivation)
alter table OPLQTSTC add JCSTCAAEDS_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNSTC
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTISTC
  before insert on OPLQTSTC
  for each row
begin
  select OPLQNSTC.nextval
  into :new.JCSTCAAEDS_INSERT_ORDER
  from dual;
end;
/

-- =============================================================
 
-- Table OPLQT STT (ConsolidateSupplyEdsTension)
alter table OPLQTSTT add JCSU_CSU_INSERT_ORDER INT ;

-- Order sequence
create sequence OPLQNSTT
 start with     1
 increment by   1
 order
 nocache
 nocycle;

-- Trigger to set order
create trigger OPLTISTT
  before insert on OPLQTSTT
  for each row
begin
  select OPLQNSTT.nextval
  into :new.JCSU_CSU_INSERT_ORDER
  from dual;
end;
/



