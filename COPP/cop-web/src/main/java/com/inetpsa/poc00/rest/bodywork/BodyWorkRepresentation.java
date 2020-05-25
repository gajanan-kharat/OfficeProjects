package com.inetpsa.poc00.rest.bodywork;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;

/**
 * The Class BodyWorkRepresentation.
 */
public class BodyWorkRepresentation {

    /** The rule ID. */
    private Long ruleID;

    /** The lable. */
    private String lable;

    /** The silhouette BOD. */
    private String silhouetteBOD;

    /** The silhoutte kmat. */
    private String silhoutteKmat;

    /** The silhoutte FR lable. */
    private String silhoutteFRLable;

    /** The silhoutte tvv rule id. */
    private Long silhoutteTvvRuleId;

    /** The silhoutte lable. */
    private String silhoutteLable;

    /** The tvv rule id. */
    @JsonIgnore
    private GenomeTVVRule tvvRuleId;

    /** The entity id. */
    private Long entityId;

    /**
     * Instantiates a new body work representation.
     *
     * @param genomelable the genomelable
     * @param kmat the kmat
     * @param frLabel the FR lable
     * @param silhoutteTvvRuleId the silhoutte tvv rule id
     * @param lable the lable
     * @param entityId the entity id
     */
    public BodyWorkRepresentation(String genomelable, String kmat, String frLabel, Long silhoutteTvvRuleId, String lable, Long entityId) {

        this.silhouetteBOD = genomelable;
        this.silhoutteKmat = kmat;
        this.silhoutteFRLable = frLabel;
        if (lable == null) {
            lable = silhoutteFRLable;
        }

        this.silhoutteLable = lable;
        this.silhoutteTvvRuleId = silhoutteTvvRuleId;
        this.entityId = entityId;

    }

    /**
     * Instantiates a new body work representation.
     *
     * @param entityId the entity id
     * @param lable the lable
     */
    public BodyWorkRepresentation(Long entityId, String lable, String b0DValue) {

        this.setEntityId(entityId);

        this.silhoutteLable = b0DValue + "-" + lable;

    }

    /**
     * Instantiates a new body work representation.
     *
     * @param entityId the entity id
     * @param lable the lable
     */
    public BodyWorkRepresentation(Long entityId, String lable) {

        this.setEntityId(entityId);

        this.silhoutteLable = lable;

    }

    /**
     * Instantiates a new body work representation.
     *
     * @param entityId the entity id
     */
    public BodyWorkRepresentation(Long entityId) {

        this.entityId = entityId;

    }

    /**
     * Instantiates a new body work representation.
     */
    public BodyWorkRepresentation() {
        // Default Constructor
    }

    /**
     * Gets the rule ID.
     *
     * @return the rule ID
     */
    public Long getRuleID() {
        return ruleID;
    }

    /**
     * Sets the rule ID.
     *
     * @param ruleID the new rule ID
     */
    public void setRuleID(Long ruleID) {
        this.ruleID = ruleID;
    }

    /**
     * Gets the lable.
     *
     * @return the lable
     */
    public String getLable() {
        return lable;
    }

    /**
     * Sets the lable.
     *
     * @param lable the new lable
     */
    public void setLable(String lable) {
        this.lable = lable;
    }

    /**
     * Gets the tvv rule id.
     *
     * @return the tvv rule id
     */
    public GenomeTVVRule getTvvRuleId() {
        return tvvRuleId;
    }

    /**
     * Sets the tvv rule id.
     *
     * @param tvvRuleId the new tvv rule id
     */
    public void setTvvRuleId(GenomeTVVRule tvvRuleId) {
        this.tvvRuleId = tvvRuleId;
    }

    /**
     * Gets the silhouette BOD.
     *
     * @return the silhouette BOD
     */
    public String getSilhouetteBOD() {
        return silhouetteBOD;
    }

    /**
     * Sets the silhouette BOD.
     *
     * @param silhouetteBOD the new silhouette BOD
     */
    public void setSilhouetteBOD(String silhouetteBOD) {
        this.silhouetteBOD = silhouetteBOD;
    }

    /**
     * Gets the silhoutte kmat.
     *
     * @return the silhoutte kmat
     */
    public String getSilhoutteKmat() {
        return silhoutteKmat;
    }

    /**
     * Sets the silhoutte kmat.
     *
     * @param silhoutteKmat the new silhoutte kmat
     */
    public void setSilhoutteKmat(String silhoutteKmat) {
        this.silhoutteKmat = silhoutteKmat;
    }

    /**
     * Gets the silhoutte FR lable.
     *
     * @return the silhoutte FR lable
     */
    public String getSilhoutteFRLable() {
        return silhoutteFRLable;
    }

    /**
     * Sets the silhoutte FR lable.
     *
     * @param silhoutteFRLable the new silhoutte FR lable
     */
    public void setSilhoutteFRLable(String silhoutteFRLable) {
        this.silhoutteFRLable = silhoutteFRLable;
    }

    /**
     * Gets the silhoutte tvv rule id.
     *
     * @return the silhoutte tvv rule id
     */
    public Long getSilhoutteTvvRuleId() {
        return silhoutteTvvRuleId;
    }

    /**
     * Sets the silhoutte tvv rule id.
     *
     * @param silhoutteTvvRuleId the new silhoutte tvv rule id
     */
    public void setSilhoutteTvvRuleId(Long silhoutteTvvRuleId) {
        this.silhoutteTvvRuleId = silhoutteTvvRuleId;
    }

    /**
     * Gets the silhoutte lable.
     *
     * @return the silhoutte lable
     */
    public String getSilhoutteLable() {
        return silhoutteLable;
    }

    /**
     * Sets the silhoutte lable.
     *
     * @param silhoutteLable the new silhoutte lable
     */
    public void setSilhoutteLable(String silhoutteLable) {
        this.silhoutteLable = silhoutteLable;
    }

    /**
     * Gets the entity id.
     *
     * @return the entity id
     */
    public Long getEntityId() {
        return entityId;
    }

    /**
     * Sets the entity id.
     *
     * @param entityId the new entity id
     */
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

}
