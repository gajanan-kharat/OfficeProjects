package com.inetpsa.poc00.domain.preparationchecklist;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.preparationfile.PreparationFile;
import com.inetpsa.poc00.domain.preparationresult.PreparationResult;

/**
 * The Class GenomeTVVRule.
 */
@Entity
@Table(name = "COPQTPCL")
public class PreparationCheckList extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long entityId;

    /** The label. */
    @Column(name = "LABEL")
    private String label;

    /** The enabled. */
    @Column(name = "ENABLED")
    private Boolean enabled;

    /** The order. */
    @Column(name = "ORDER_NUMBER")
    private int order;

    /** The description. */
    @Column(name = "DESCRIPTION")
    private String description;

    /** The type of list. */
    @Column(name = "TYPE_OF_LIST")
    private int typeOfList;

    /** The preparation result list. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preparationCheckList")
    private List<PreparationResult> preparationResultList = new ArrayList<>();

    /** The genome lcdv dictionary. */
    @ManyToOne
    @JoinColumn(name = "PREPARATION_FILE_ID")
    private PreparationFile preparationFile;

    /**
     * Constructor are in visibility package because only Factories can create aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    public PreparationCheckList() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seedstack.business.domain.BaseEntity#getEntityId()
     */
    @Override
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

    /**
     * Gets the label.
     * 
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label.
     * 
     * @param label the new label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the enabled.
     * 
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled.
     * 
     * @param enabled the new enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets the order.
     * 
     * @return the order
     */
    public int getOrder() {
        return order;
    }

    /**
     * Sets the order.
     * 
     * @param order the new order
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * 
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the preparation file.
     * 
     * @return the preparation file
     */
    public PreparationFile getPreparationFile() {
        return preparationFile;
    }

    /**
     * Sets the preparation file.
     * 
     * @param preparationFile the new preparation file
     */
    public void setPreparationFile(PreparationFile preparationFile) {
        this.preparationFile = preparationFile;
    }

    /**
     * Gets the preparation result list.
     * 
     * @return the preparation result list
     */
    public List<PreparationResult> getPreparationResultList() {
        return preparationResultList;
    }

    /**
     * Sets the preparation result list.
     * 
     * @param preparationResultList the new preparation result list
     */
    public void setPreparationResultList(List<PreparationResult> preparationResultList) {
        this.preparationResultList = preparationResultList;
    }

    /**
     * Gets the type of list.
     * 
     * @return the type of list
     */
    public int getTypeOfList() {
        return typeOfList;
    }

    /**
     * Sets the type of list.
     * 
     * @param typeOfList the new type of list
     */
    public void setTypeOfList(int typeOfList) {
        this.typeOfList = typeOfList;
    }

}
