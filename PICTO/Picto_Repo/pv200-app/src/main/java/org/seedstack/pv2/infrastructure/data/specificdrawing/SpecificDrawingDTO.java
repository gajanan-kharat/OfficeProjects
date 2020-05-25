package org.seedstack.pv2.infrastructure.data.specificdrawing;

import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;
import org.seedstack.pv2.infrastructure.data.pictofamily.PictoFamilyDTO;


/**
 * The Class SpecificDrawingDTO.
 */
public class SpecificDrawingDTO {

	/** The id. */
	private Long id;
	
	/** The name. */
	private String name;
	
	/** The comments FR. */
	private String commentsFR;
	
	/** The comments EN. */
	private String commentsEN;
	
	/** The family id. */
	private PictoFamilyDTO familyId;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@MatchingEntityId
	@MatchingFactoryParameter(index = 0)
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@MatchingFactoryParameter(index = 1)
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
	@MatchingFactoryParameter(index = 2)
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
	@MatchingFactoryParameter(index = 3)
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
	@MatchingFactoryParameter(index = 4)
	public PictoFamilyDTO getFamilyId() {
		return familyId;
	}

	/**
	 * Sets the family id.
	 *
	 * @param familyId the familyId to set
	 */
	public void setFamilyId(PictoFamilyDTO familyId) {
		this.familyId = familyId;
	}

}
