/*
 * Creation : Jun 8, 2016
 */
package com.inetpsa.poc00.domain.valuedinertia;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.tvvvaluedcoastdown.TvvValuedCoastDown;

/**
 * The Class ValuedInertia.
 */

@Entity
@Table(name = "COPQTVIA")
public class ValuedInertia extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	protected long entityId;

	/** The value. */
	@Column(name = "VALUE")
	private int value;

	/** The costdown. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "inertia")
	private Set<TvvValuedCoastDown> costdown;

	/**
	 * Instantiates a new valued inertia.
	 */
	public ValuedInertia() {
		super();
	}

	/**
	 * Instantiates a new valued inertia.
	 *
	 * @param inertia the inertia
	 */
	public ValuedInertia(ValuedInertia inertia) {
		this.entityId = 0;
		this.value = inertia.value;
	}

	/**
	 * {@inheritDoc}
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
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value the new value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Gets the costdown.
	 * 
	 * @return the costdown
	 */
	public Set<TvvValuedCoastDown> getCostdown() {
		return costdown;
	}

	/**
	 * Sets the costdown.
	 * 
	 * @param costdown the new costdown
	 */
	public void setCostdown(Set<TvvValuedCoastDown> costdown) {
		this.costdown = costdown;
	}
	
	

	/**
	 * to String implementation.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return DomainConstants.EMPTY +  value;
	}
}
