package com.inetpsa.eds.dao.model;

// Generated 1 ao�t 2012 15:24:34 by Hibernate Tools 3.2.1.GA

import com.inetpsa.eds.dao.I_DriftableSupply;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * EdsPrimarySupply generated by hbm2java
 */
@XmlType
public class EdsPrimarySupply implements java.io.Serializable, I_DriftableSupply, Comparable<EdsPrimarySupply> {
    /**
     * String Variable to hold the value for Column PSEDS_ID of Table OPLQTPRS
     */
    private String psedsId;
    /**
     * String Variable to hold the value for Column PSEDS_REF of Table OPLQTPRS
     */
    private String psedsRef;
    /**
     * String Variable to hold the value for Column PSEDS_SUPPLY_NAME of Table OPLQTPRS
     */
    private String psedsSupplyName;
    /**
     * String Variable to hold the value for Column PSEDS_TBT_BT of Table OPLQTPRS
     */
    private String psedsTbtBt;
    /**
     * Float Variable to hold the value for Column PSEDS_I_VEILLE of Table OPLQTPRS
     */
    private Float psedsIVeille;
    /**
     * Float Variable to hold the value for Column PSEDS_I_REVEILLE_INACTIF of Table OPLQTPRS
     */
    private Float psedsIReveilleInactif;
    /**
     * Float Variable to hold the value for Column PSEDS_I_NOM_STAB of Table OPLQTPRS
     */
    private Float psedsINomStab;
    /**
     * Float Variable to hold the value for Column PSEDS_I_PIRECAS_STAB of Table OPLQTPRS
     */
    private Float psedsIPirecasStab;
    /**
     * String Variable to hold the value for Column PSEDS_SUPPLY_NAME_IFORM_BY of Table OPLQTPRS
     */
    private String psedsSupplyNameIformBy;
    /**
     * String Variable to hold the value for Column PSEDS_SUPPLY_TYPE_SUPPLY_NAME_IFORM_BY of Table OPLQTPRS
     */
    private String psedsSupplyTypeSupplyNameIformBy;
    /**
     * String Variable to hold the value for Column PSEDS_I_VEILLE_IFORM_BY of Table OPLQTPRS
     */
    private String psedsIVeilleIformBy;
    /**
     * String Variable to hold the value for Column PSEDS_REVEILLE_IFORM_BY of Table OPLQTPRS
     */
    private String psedsReveilleIformBy;
    /**
     * String Variable to hold the value for Column PSEDS_I_NOM_STAB_IFORM_BY of Table OPLQTPRS
     */
    private String psedsINomStabIformBy;
    /**
     * String Variable to hold the value for Column PSEDS_I_PIRECAS_STAB_IFORM_BY of Table OPLQTPRS
     */
    private String psedsIPirecasStabIformBy;
    /**
     * String Variable to hold the value for Column PSEDS_SUPPLY_NAME_COMMENT of Table OPLQTPRS
     */
    private String psedsSupplyNameComment;
    /**
     * String Variable to hold the value for Column PSEDS_SUPPLY_TYPE_SUPPLY_NAME_COMMENT of Table OPLQTPRS
     */
    private String psedsSupplyTypeSupplyNameComment;
    /**
     * String Variable to hold the value for Column PSEDS_I_VEILLE_COMMENT of Table OPLQTPRS
     */
    private String psedsIVeilleComment;
    /**
     * String Variable to hold the value for Column PSEDS_REVEILLE_COMMENT of Table OPLQTPRS
     */
    private String psedsReveilleComment;
    /**
     * String Variable to hold the value for Column PSEDS_I_NOM_STAB_COMMENT of Table OPLQTPRS
     */
    private String psedsINomStabComment;
    /**
     * String Variable to hold the value for Column PSEDS_I_PIRECAS_COMMENT of Table OPLQTPRS
     */
    private String psedsIPirecasComment;
    /**
     * Variable to hold the value for Column PSEDS_W_ID of Table OPLQTPRS
     */
    private EdsWording wording;
    /**
     * Variable to hold value of current user
     */
    private String curentUser;
    /**
     * int Variable to hold the value for Column PS_RANK of Table OPLQTPRS
     */
    private int psRank;

    /**
     * Default Constructor
     */
    public EdsPrimarySupply() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param psedsId
     */
    public EdsPrimarySupply(String psedsId) {
        this.psedsId = psedsId;
    }

    /**
     * Parameterized Constructor
     * 
     * @param psedsId Eds Primary supply Id
     * @param psedsSupplyName Eds Primary supply name
     * @param psedsTbtBt TBT/BT
     * @param psedsIVeille Iasleep
     * @param psedsIReveilleInactif Iawake mode non functioning mode
     * @param psedsINomStab Inom stab
     * @param psedsIPirecasStab Iworst stab
     * @param psedsSupplyNameIformBy Power supply name Iform By
     * @param psedsSupplyTypeSupplyNameIformBy power supply type Iform By
     * @param psedsIVeilleIformBy Iasleep Iform By
     * @param psedsReveilleIformBy Iawake mode non functioning Iform By
     * @param psedsINomStabIformBy Inom stab Iform By
     * @param psedsIPirecasStabIformBy Iworst stab Iform By
     * @param psedsSupplyNameComment Supply name comment
     * @param psedsSupplyTypeSupplyNameComment Supply type comment
     * @param psedsIVeilleComment Iasleep comment
     * @param psedsReveilleComment Iawake mode non functioning mode comment
     * @param psedsINomStabComment Inom stab comment
     * @param psedsIPirecasComment Iworst case comment
     */
    public EdsPrimarySupply(String psedsId, String psedsSupplyName, String psedsTbtBt, Float psedsIVeille, Float psedsIReveilleInactif,
            Float psedsINomStab, Float psedsIPirecasStab, String psedsSupplyNameIformBy, String psedsSupplyTypeSupplyNameIformBy,
            String psedsIVeilleIformBy, String psedsReveilleIformBy, String psedsINomStabIformBy, String psedsIPirecasStabIformBy,
            String psedsSupplyNameComment, String psedsSupplyTypeSupplyNameComment, String psedsIVeilleComment, String psedsReveilleComment,
            String psedsINomStabComment, String psedsIPirecasComment) {
        this.psedsId = psedsId;
        this.psedsSupplyName = psedsSupplyName;
        this.psedsTbtBt = psedsTbtBt;
        this.psedsIVeille = psedsIVeille;
        this.psedsIReveilleInactif = psedsIReveilleInactif;
        this.psedsINomStab = psedsINomStab;
        this.psedsIPirecasStab = psedsIPirecasStab;
        this.psedsSupplyNameIformBy = psedsSupplyNameIformBy;
        this.psedsSupplyTypeSupplyNameIformBy = psedsSupplyTypeSupplyNameIformBy;
        this.psedsIVeilleIformBy = psedsIVeilleIformBy;
        this.psedsReveilleIformBy = psedsReveilleIformBy;
        this.psedsINomStabIformBy = psedsINomStabIformBy;
        this.psedsIPirecasStabIformBy = psedsIPirecasStabIformBy;
        this.psedsSupplyNameComment = psedsSupplyNameComment;
        this.psedsSupplyTypeSupplyNameComment = psedsSupplyTypeSupplyNameComment;
        this.psedsIVeilleComment = psedsIVeilleComment;
        this.psedsReveilleComment = psedsReveilleComment;
        this.psedsINomStabComment = psedsINomStabComment;
        this.psedsIPirecasComment = psedsIPirecasComment;

    }

    /**
     * Parameterized Constructor
     * 
     * @param otherSupply
     */
    public EdsPrimarySupply(EdsPrimarySupply otherSupply) {

        this.psedsId = UUID.randomUUID().toString();
        this.psedsRef = otherSupply.psedsRef;
        this.psedsSupplyName = otherSupply.psedsSupplyName;
        this.psedsTbtBt = otherSupply.psedsTbtBt;
        this.psedsIVeille = otherSupply.psedsIVeille;
        this.psedsIReveilleInactif = otherSupply.psedsIReveilleInactif;
        this.psedsINomStab = otherSupply.psedsINomStab;
        this.psedsIPirecasStab = otherSupply.psedsIPirecasStab;
        this.psedsSupplyNameIformBy = otherSupply.psedsSupplyNameIformBy;
        this.psedsSupplyTypeSupplyNameIformBy = otherSupply.psedsSupplyTypeSupplyNameIformBy;
        this.psedsIVeilleIformBy = otherSupply.psedsIVeilleIformBy;
        this.psedsReveilleIformBy = otherSupply.psedsReveilleIformBy;
        this.psedsINomStabIformBy = otherSupply.psedsINomStabIformBy;
        this.psedsIPirecasStabIformBy = otherSupply.psedsIPirecasStabIformBy;
        this.psedsSupplyNameComment = otherSupply.psedsSupplyNameComment;
        this.psedsSupplyTypeSupplyNameComment = otherSupply.psedsSupplyTypeSupplyNameComment;
        this.psedsIVeilleComment = otherSupply.psedsIVeilleComment;
        this.psedsReveilleComment = otherSupply.psedsReveilleComment;
        this.psedsINomStabComment = otherSupply.psedsINomStabComment;
        this.psedsIPirecasComment = otherSupply.psedsIPirecasComment;
        this.wording = otherSupply.wording;

    }

    /**
     * Function to get Isleep current
     * 
     * @return null
     */
    public Float getISleepCurrent() {
        return getPsedsIVeille();
    }

    /**
     * Function to get null
     * 
     * @return null
     */
    public Float getIAwake12_5() {
        return null;
    }

    /**
     * Function to get psedsIReveilleInactif
     * 
     * @return the Value of psedsIReveilleInactif
     */
    public Float getIAwake13_5() {
        return getPsedsIReveilleInactif();
    }

    /**
     * Function to get null
     * 
     * @return null
     */
    public Float getINomStab12_5() {
        return null;
    }

    /**
     * Function to get psedsINomStab
     * 
     * @return the Value of psedsINomStab
     */
    public Float getINomStab13_5() {
        return getPsedsINomStab();
    }

    /**
     * Function to get null
     * 
     * @return null
     */
    public Float getIWorstStab12_5() {
        return null;
    }

    /**
     * Function to get psedsIPirecasStab
     * 
     * @return the Value of psedsIPirecasStab
     */
    public Float getIWorstStab13_5() {
        return getPsedsIPirecasStab();
    }

    /**
     * Function to get psedsId
     * 
     * @return the Value of psedsId
     */
    @XmlAttribute(name = "id")
    public String getPsedsId() {
        return this.psedsId;
    }

    /**
     * Function to set psedsId
     * 
     * @param psedsId
     */
    public void setPsedsId(String psedsId) {
        this.psedsId = psedsId;
    }

    /**
     * Function to get psedsRef
     * 
     * @return the Value of psedsRef
     */
    @XmlAttribute(name = "ref")
    public String getPsedsRef() {
        return psedsRef;
    }

    /**
     * Function to set psedsRef
     * 
     * @param psedsRef Eds reference
     */
    public void setPsedsRef(String psedsRef) {
        this.psedsRef = psedsRef;
    }

    /**
     * Function to get psedsSupplyName
     * 
     * @return the Value of psedsSupplyName
     */
    @XmlAttribute(name = "name")
    public String getPsedsSupplyName() {
        return this.psedsSupplyName;
    }

    /**
     * Function to set psedsSupplyName
     * 
     * @param psedsSupplyName supply name
     */
    public void setPsedsSupplyName(String psedsSupplyName) {
        this.psedsSupplyName = psedsSupplyName;
    }

    /**
     * Function to get psedsTbtBt
     * 
     * @return the Value of psedsTbtBt
     */
    @XmlAttribute(name = "bt_tbt")
    public String getPsedsTbtBt() {
        return this.psedsTbtBt;
    }

    /**
     * Function to set psedsTbtBt
     * 
     * @param psedsTbtBt TBT/BT
     */
    public void setPsedsTbtBt(String psedsTbtBt) {
        this.psedsTbtBt = psedsTbtBt;
    }

    /**
     * Function to get psedsIVeille
     * 
     * @return the Value of psedsIVeille
     */
    @XmlElement(name = "i-standby")
    public Float getPsedsIVeille() {
        return this.psedsIVeille;
    }

    /**
     * Function to set psedsIVeille
     * 
     * @param psedsIVeille Iasleep
     */
    public void setPsedsIVeille(Float psedsIVeille) {
        if (this.psedsIVeille != null) {
            if (!this.psedsIVeille.equals(psedsIVeille)) {
                setPsedsIVeilleIformBy(curentUser);
            }
        }
        this.psedsIVeille = psedsIVeille;
    }

    /**
     * Function to get psedsIReveilleInactif
     * 
     * @return the Value of psedsIReveilleInactif
     */
    @XmlElement(name = "i-wakeup-inactive")
    public Float getPsedsIReveilleInactif() {
        return this.psedsIReveilleInactif;
    }

    /**
     * Function to set psedsIReveilleInactif
     * 
     * @param psedsIReveilleInactif Iawake mode non functioning mode
     */
    public void setPsedsIReveilleInactif(Float psedsIReveilleInactif) {
        if (this.psedsIReveilleInactif != null) {
            if (!this.psedsIReveilleInactif.equals(psedsIReveilleInactif)) {
                setPsedsReveilleIformBy(curentUser);
            }
        }
        this.psedsIReveilleInactif = psedsIReveilleInactif;
    }

    /**
     * Function to get psedsINomStab
     * 
     * @return the Value of psedsINomStab
     */
    @XmlElement(name = "i-nom-stab")
    public Float getPsedsINomStab() {
        return this.psedsINomStab;
    }

    /**
     * Function to set psedsINomStab
     * 
     * @param psedsINomStab Inom stab
     */
    public void setPsedsINomStab(Float psedsINomStab) {
        if (this.psedsINomStab != null) {
            if (!this.psedsINomStab.equals(psedsINomStab)) {
                setPsedsINomStabIformBy(curentUser);
            }
        }
        this.psedsINomStab = psedsINomStab;
    }

    /**
     * Function to get psedsIPirecasStab
     * 
     * @return the Value of psedsIPirecasStab
     */
    @XmlElement(name = "i-worst-stab")
    public Float getPsedsIPirecasStab() {
        return this.psedsIPirecasStab;
    }

    /**
     * Function to set psedsIPirecasStab
     * 
     * @param psedsIPirecasStab Iworst case stab
     */
    public void setPsedsIPirecasStab(Float psedsIPirecasStab) {
        if (this.psedsIPirecasStab != null) {
            if (!this.psedsIPirecasStab.equals(psedsIPirecasStab)) {
                setPsedsIPirecasStabIformBy(curentUser);
            }
        }
        this.psedsIPirecasStab = psedsIPirecasStab;
    }

    /**
     * Function to get psedsSupplyNameIformBy
     * 
     * @return the Value of psedsSupplyNameIformBy
     */
    @XmlTransient
    public String getPsedsSupplyNameIformBy() {
        return this.psedsSupplyNameIformBy;
    }

    /**
     * Function to set psedsSupplyNameIformBy
     * 
     * @param psedsSupplyNameIformBy Supply name Iform By
     */
    public void setPsedsSupplyNameIformBy(String psedsSupplyNameIformBy) {
        this.psedsSupplyNameIformBy = psedsSupplyNameIformBy;
    }

    /**
     * Function to get psedsSupplyTypeSupplyNameIformBy
     * 
     * @return the Value of psedsSupplyTypeSupplyNameIformBy
     */
    @XmlTransient
    public String getPsedsSupplyTypeSupplyNameIformBy() {
        return this.psedsSupplyTypeSupplyNameIformBy;
    }

    /**
     * Function to set psedsSupplyTypeSupplyNameIformBy
     * 
     * @param psedsSupplyTypeSupplyNameIformBy Supply type Iform by
     */
    public void setPsedsSupplyTypeSupplyNameIformBy(String psedsSupplyTypeSupplyNameIformBy) {
        this.psedsSupplyTypeSupplyNameIformBy = psedsSupplyTypeSupplyNameIformBy;
    }

    /**
     * Function to get psedsIVeilleIformBy
     * 
     * @return the Value of psedsIVeilleIformBy
     */
    @XmlTransient
    public String getPsedsIVeilleIformBy() {
        return this.psedsIVeilleIformBy;
    }

    /**
     * Function to set psedsIVeilleIformBy
     * 
     * @param psedsIVeilleIformBy Iasleep Iform by
     */
    public void setPsedsIVeilleIformBy(String psedsIVeilleIformBy) {
        this.psedsIVeilleIformBy = psedsIVeilleIformBy;
    }

    /**
     * Function to get psedsReveilleIformBy
     * 
     * @return the Value of psedsReveilleIformBy
     */
    @XmlTransient
    public String getPsedsReveilleIformBy() {
        return this.psedsReveilleIformBy;
    }

    /**
     * Function to set psedsReveilleIformBy
     * 
     * @param psedsReveilleIformBy Iawake non functioning mode Iform by
     */
    public void setPsedsReveilleIformBy(String psedsReveilleIformBy) {
        this.psedsReveilleIformBy = psedsReveilleIformBy;
    }

    /**
     * Function to get psedsINomStabIformBy
     * 
     * @return the Value of psedsINomStabIformBy
     */
    @XmlTransient
    public String getPsedsINomStabIformBy() {
        return this.psedsINomStabIformBy;
    }

    /**
     * Function to set psedsINomStabIformBy
     * 
     * @param psedsINomStabIformBy Inom stab Iform By
     */
    public void setPsedsINomStabIformBy(String psedsINomStabIformBy) {
        this.psedsINomStabIformBy = psedsINomStabIformBy;
    }

    /**
     * Function to get psedsIPirecasStabIformBy
     * 
     * @return the Value of psedsIPirecasStabIformBy
     */
    @XmlTransient
    public String getPsedsIPirecasStabIformBy() {
        return this.psedsIPirecasStabIformBy;
    }

    /**
     * Function to set psedsIPirecasStabIformBy
     * 
     * @param psedsIPirecasStabIformBy Iworst case Iform By
     */
    public void setPsedsIPirecasStabIformBy(String psedsIPirecasStabIformBy) {
        this.psedsIPirecasStabIformBy = psedsIPirecasStabIformBy;
    }

    /**
     * Function to get psedsSupplyNameComment
     * 
     * @return the Value of psedsSupplyNameComment
     */
    public String getPsedsSupplyNameComment() {
        return this.psedsSupplyNameComment;
    }

    /**
     * Function to set psedsSupplyNameComment
     * 
     * @param psedsSupplyNameComment Supply name comment
     */
    public void setPsedsSupplyNameComment(String psedsSupplyNameComment) {
        this.psedsSupplyNameComment = psedsSupplyNameComment;
    }

    /**
     * Function to get psedsSupplyTypeSupplyNameComment
     * 
     * @return the Value of psedsSupplyTypeSupplyNameComment
     */
    public String getPsedsSupplyTypeSupplyNameComment() {
        return this.psedsSupplyTypeSupplyNameComment;
    }

    /**
     * Function to set psedsSupplyTypeSupplyNameComment
     * 
     * @param psedsSupplyTypeSupplyNameComment supply type comment
     */
    public void setPsedsSupplyTypeSupplyNameComment(String psedsSupplyTypeSupplyNameComment) {
        this.psedsSupplyTypeSupplyNameComment = psedsSupplyTypeSupplyNameComment;
    }

    /**
     * Function to get psedsIVeilleComment
     * 
     * @return the Value of psedsIVeilleComment
     */
    public String getPsedsIVeilleComment() {
        return this.psedsIVeilleComment;
    }

    /**
     * Function to set psedsIVeilleComment
     * 
     * @param psedsIVeilleComment Iasleep comment
     */
    public void setPsedsIVeilleComment(String psedsIVeilleComment) {
        this.psedsIVeilleComment = psedsIVeilleComment;
    }

    /**
     * Function to get psedsReveilleComment
     * 
     * @return the Value of psedsReveilleComment
     */
    public String getPsedsReveilleComment() {
        return this.psedsReveilleComment;
    }

    /**
     * Function to set psedsReveilleComment
     * 
     * @param psedsReveilleComment Iawake non functioning mode comment
     */
    public void setPsedsReveilleComment(String psedsReveilleComment) {
        this.psedsReveilleComment = psedsReveilleComment;
    }

    /**
     * Function to get psedsINomStabComment
     * 
     * @return the Value of psedsINomStabComment
     */
    public String getPsedsINomStabComment() {
        return this.psedsINomStabComment;
    }

    /**
     * Function to set psedsINomStabComment
     * 
     * @param psedsINomStabComment Inom stab comment
     */
    public void setPsedsINomStabComment(String psedsINomStabComment) {
        this.psedsINomStabComment = psedsINomStabComment;
    }

    /**
     * Function to get psedsIPirecasComment
     * 
     * @return the Value of psedsIPirecasComment
     */
    public String getPsedsIPirecasComment() {
        return this.psedsIPirecasComment;
    }

    /**
     * Function to set psedsIPirecasComment
     * 
     * @param psedsIPirecasComment Iworst case stab comment
     */
    public void setPsedsIPirecasComment(String psedsIPirecasComment) {
        this.psedsIPirecasComment = psedsIPirecasComment;
    }

    /**
     * Function to get psRank
     * 
     * @return the Value of psRank
     */
    @XmlTransient
    public int getPsRank() {
        return psRank;
    }

    /**
     * Function to set psRank
     * 
     * @param psRank Rannk
     */
    public void setPsRank(int psRank) {
        this.psRank = psRank;
    }

    /**
     * Function to get wording
     * 
     * @return the Value of wording
     */
    public EdsWording getWording() {
        return wording;
    }

    /**
     * Function to set wording
     * 
     * @param wording Eds wording
     */
    public void setWording(EdsWording wording) {
        if (this.wording != null) {
            if (!this.wording.equals(wording) && curentUser != null) {
                setPsedsSupplyTypeSupplyNameIformBy(curentUser);
            }
        }
        this.wording = wording;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.psedsSupplyName;
    }

    /**
     * Function to compare EdsPrimarySupply
     * 
     * @param o EdsPrimarySupply
     * @return difference between EdsPrimarySupplies
     */
    public int compareTo(EdsPrimarySupply o) {
        return this.getPsRank() - o.getPsRank();
    }

    /**
     * Function to get all saved items
     * 
     * @return all saved items
     */
    public List getAllItemsToSave() {
        return Collections.singletonList((Object) this);
    }

    /**
     * Function to set curentUser
     * 
     * @param curentUser User
     */
    public void setCurentUser(String curentUser) {
        this.curentUser = curentUser;
    }

}
