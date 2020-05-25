-- CORVET DATA
CREATE TABLE COPQTCVD(
       ID                    BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,
       TVV                   VARCHAR(255),
       NRE                   VARCHAR(255),
       WLTP_MASSE            VARCHAR(255),
       WLTP_SCX              VARCHAR(255),
       WLTP_CRR              VARCHAR(255),
       WLTP_F0               VARCHAR(255),
       WLTP_F1               VARCHAR(255),
       WLTP_F2               VARCHAR(255),
       NEDC_MASSE            VARCHAR(255),
       NEDC_SCX              VARCHAR(255),
       NEDC_CRR              VARCHAR(255),
       NEDC_F0               VARCHAR(255),
       NEDC_F1               VARCHAR(255),
       NEDC_F2               VARCHAR(255),
       LCDV_VERSION          VARCHAR(255),
       WLTP_CO2_VIN_MOYEN    DOUBLE
       
);