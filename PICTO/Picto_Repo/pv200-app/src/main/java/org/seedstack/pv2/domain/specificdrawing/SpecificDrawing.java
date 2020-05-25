package org.seedstack.pv2.domain.specificdrawing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;

/**
 * The Class SpecificDrawing.
 */
@Entity
@Table(name = "PV2QTDRA")
public class SpecificDrawing extends BaseAggregateRoot<Long> {

	/** Specific Drawing id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** Specific Drawing name. */
	@Column(name = "NAME")
	private String name;

	/** Comments in French. */
	@Column(name = "COMMENTS_FR")
	private String commentsFR;

	/** Comments in English. */
	@Column(name = "COMMENTS_EN")
	private String commentsEN;

	/** Picto family Id. */
	@ManyToOne
	@JoinColumn(name = "PFM_ID")
	private PictoFamily familyId;

	/** The specific draw location. */
	@Column(name = "SPECIFICDRAW_FILE")
	private String specificDrawFile;

	/**
	 * Constructor.
	 */

	public SpecificDrawing() {

	}

	/**
	 * Instantiates a new specific drawing.
	 *
	 * @param id the id
	 * @param name the name
	 * @param familyId the family id
	 */
	SpecificDrawing(Long id, String name, PictoFamily familyId) {
		this.id = id;
		this.name = name;
		this.familyId = familyId;
	}

	/**
	 * Instantiates a new specific drawing.
	 *
	 * @param id the id
	 * @param name the name
	 * @param commentFR the comment FR
	 * @param commentEN the comment EN
	 * @param familyId the family id
	 */
	SpecificDrawing(Long id, String name, String commentFR, String commentEN, PictoFamily familyId) {
		this.id = id;
		this.name = name;
		this.commentsFR = commentFR;
		this.commentsEN = commentEN;
		this.familyId = familyId;
	}

	/**
	 * Gets the entity id.
	 *
	 * @return the drawingID
	 */
	public Long getEntityId() {
		return id;
	}

	/**
	 * Sets the entity id.
	 *
	 * @param drawingId the new entity id
	 */
	public void setEntityId(Long drawingId) {
		this.id = drawingId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the comments FR.
	 *
	 * @return the commentsFR
	 */
	public String getCommentsFR() {
		return commentsFR;
	}

	/**
	 * Sets the comments FR.
	 *
	 * @param commentsFR the commentsFR to set
	 */
	public void setCommentsFR(String commentsFR) {
		this.commentsFR = commentsFR;
	}

	/**
	 * Gets the comments EN.
	 *
	 * @return the commentsEN
	 */
	public String getCommentsEN() {
		return commentsEN;
	}

	/**
	 * Sets the comments EN.
	 *
	 * @param commentsEN the commentsEN to set
	 */
	public void setCommentsEN(String commentsEN) {
		this.commentsEN = commentsEN;
	}

	/**
	 * Gets the family id.
	 *
	 * @return the familyId
	 */
	public PictoFamily getFamilyId() {
		return familyId;
	}

	/**
	 * Sets the family id.
	 *
	 * @param familyId the familyId to set
	 */
	public void setFamilyId(PictoFamily familyId) {
		this.familyId = familyId;
	}

	/**
	 * Gets the specific draw location.
	 *
	 * @return the specificDrawLocation
	 */
	public String getSpecificDrawFile() {
		return specificDrawFile;
	}

	/**
	 * Sets the specific draw location.
	 *
	 * @param specificDrawFile the new specific draw file
	 */
	public void setSpecificDrawFile(String specificDrawFile) {
		this.specificDrawFile = specificDrawFile;
	}

}
