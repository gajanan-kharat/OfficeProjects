/**
 * Copyright (c) 2016, PSA
 */
package org.seedstack.pv2.domain.supercategory;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.pv2.domain.category.Category;

/**
 * @author sanjayme
 *
 */

@Entity
@Table(name = "PV2QTSCT")
public class SuperCategory extends BaseAggregateRoot<Long> {

	@Id
	@Column(name = "ID")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Long superCategoryId;

	/**
	 * category Name
	 */
	@Column(name = "SUPER_CATEGORY")
	private String name;

	/**
	 * parent Category
	 */
	@OneToMany(mappedBy = "superCategory", targetEntity = Category.class)
	// fetch = FetchType.LAZY
	private List<Category> categories;

	/**
	 * Constructor in visibility package because only Factories can create
	 * aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public SuperCategory() {
	}

	/**
	 * Constructor.
	 *
	 * @param name
	 *            the category name
	 */
	SuperCategory(String name) {
		this.name = name;
	}

	/**
	 * Constructor.
	 *
	 * @param superCategoryId
	 *            the category id
	 * @param name
	 *            the category name
	 */
	public SuperCategory(Long categoryId, String name) {
		super();
		this.superCategoryId = categoryId;
		this.name = name;
	}

	@Override
	public Long getEntityId() {
		return superCategoryId;
	}

	/**
	 * Setter entityId.
	 *
	 * @param entityId
	 *            the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.superCategoryId = entityId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the categories
	 */
	public List<Category> getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

}
