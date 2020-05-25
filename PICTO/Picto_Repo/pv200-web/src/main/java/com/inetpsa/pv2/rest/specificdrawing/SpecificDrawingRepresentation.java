package com.inetpsa.pv2.rest.specificdrawing;

import java.util.ArrayList;
import java.util.List;

import com.inetpsa.pv2.rest.pictofamily.PictoFamilyRepresentation;


/**
 * The Class SpecificDrawingRepresentation.
 */
public class SpecificDrawingRepresentation {

	/** The id. */
	private Long id;

	/** The name. */
	private String name;

	/** The comments FR. */
	private String commentsFR;

	/** The comments EN. */
	private String commentsEN;

	/** The family id. */
	private PictoFamilyRepresentation familyId;

	/** The specific draw location. */
	private String specificDrawFile;

	/** The specific draw file. */
	private List<Object> specificDrawFileInfo = new ArrayList<Object>();

	/** The specfic draw url. */
	private String specficDrawUrl;

	/**
	 * Instantiates a new specific drawing representation.
	 */
	public SpecificDrawingRepresentation() {

	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
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
	public PictoFamilyRepresentation getFamilyId() {
		return familyId;
	}

	/**
	 * Sets the family id.
	 *
	 * @param familyId the familyId to set
	 */
	public void setFamilyId(PictoFamilyRepresentation familyId) {
		this.familyId = familyId;
	}

	/**
	 * Gets the specific draw file.
	 *
	 * @return the specificDrawFile
	 */
	public List<Object> getSpecificDrawFileInfo() {
		return specificDrawFileInfo;
	}

	/**
	 * Sets the specific draw file.
	 *
	 * @param specificDrawFileInfo the new specific draw file info
	 */
	public void setSpecificDrawFileInfo(List<Object> specificDrawFileInfo) {
		this.specificDrawFileInfo = specificDrawFileInfo;
	}

	/**
	 * Gets the specific draw file.
	 *
	 * @return the specificDrawFile
	 */
	public String getSpecificDrawFile() {
		return specificDrawFile;
	}

	/**
	 * Sets the specific draw File.
	 *
	 * @param specificDrawFile the specificDrawFile to set
	 */
	public void setSpecificDrawFile(String specificDrawFile) {
		this.specificDrawFile = specificDrawFile;
	}

	/**
	 * Gets the specfic draw url.
	 *
	 * @return the specficDrawUrl
	 */
	public String getSpecficDrawUrl() {
		return specficDrawUrl;
	}

	/**
	 * Sets the specfic draw url.
	 *
	 * @param specficDrawUrl the specficDrawUrl to set
	 */
	public void setSpecficDrawUrl(String specficDrawUrl) {
		this.specficDrawUrl = specficDrawUrl;
	}
}
