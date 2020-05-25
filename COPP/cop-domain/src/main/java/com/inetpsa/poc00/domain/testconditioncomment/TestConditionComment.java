/*
 * Creation : Feb 2, 2017
 */
package com.inetpsa.poc00.domain.testconditioncomment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCL;
import com.inetpsa.poc00.domain.user.User;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Class TestConditionComment.
 */
@Entity
@Table(name = "COPQTTCC")
public class TestConditionComment extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	protected Long entityId;

	/** The value. */
	@Column(name = "COMMENT")
	private String comment;

	/** The update date. */
	@Column(name = "UPDATE_DATE")
	private Date updateDate = new Date();

	/** The genome lcdv dictionary. */
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	/** The genome lcdv dictionary. */
	@ManyToOne
	@JoinColumn(name = "VEHICLE_FILE_ID")
	private VehicleFile vehicleFile;

	/** The genome lcdv dictionary. */
	@ManyToOne
	@JoinColumn(name = "VLD_TVVDEP_TCL_ID")
	private TvvValuedTvvDepTCL tvvVldTvvDepTCL;

	/** The genome lcdv dictionary. */
	@ManyToOne
	@JoinColumn(name = "VLD_ESDEP_TCL_ID")
	private TvvValuedEsDepTCL tvvVldEsDepTCL;

	/**
	 * Instantiates a new test condition comment.
	 */
	public TestConditionComment() {
		super();
	}

	/**
	 * Modify the last Update Date for all existing Row while updating.
	 */
	@PreUpdate
	public void setLastUpdate() {
		this.updateDate = new Date();
	}

	/**
	 * Getter entityId.
	 *
	 * @return the entityId
	 */
	@Override
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Setter entityId.
	 *
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Getter comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Setter comment.
	 *
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Getter updateDate.
	 *
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * Getter user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setter user.
	 *
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Getter vehicleFile.
	 *
	 * @return the vehicleFile
	 */
	public VehicleFile getVehicleFile() {
		return vehicleFile;
	}

	/**
	 * Setter vehicleFile.
	 *
	 * @param vehicleFile the vehicleFile to set
	 */
	public void setVehicleFile(VehicleFile vehicleFile) {
		this.vehicleFile = vehicleFile;
	}

	/**
	 * Getter tvvVldTvvDepTCL.
	 *
	 * @return the tvvVldTvvDepTCL
	 */
	public TvvValuedTvvDepTCL getTvvVldTvvDepTCL() {
		return tvvVldTvvDepTCL;
	}

	/**
	 * Setter tvvVldTvvDepTCL.
	 *
	 * @param tvvVldTvvDepTCL the tvvVldTvvDepTCL to set
	 */
	public void setTvvVldTvvDepTCL(TvvValuedTvvDepTCL tvvVldTvvDepTCL) {
		this.tvvVldTvvDepTCL = tvvVldTvvDepTCL;
	}

	/**
	 * Getter tvvVldEsDepTCL.
	 *
	 * @return the tvvVldEsDepTCL
	 */
	public TvvValuedEsDepTCL getTvvVldEsDepTCL() {
		return tvvVldEsDepTCL;
	}

	/**
	 * Setter tvvVldEsDepTCL.
	 *
	 * @param tvvVldEsDepTCL the tvvVldEsDepTCL to set
	 */
	public void setTvvVldEsDepTCL(TvvValuedEsDepTCL tvvVldEsDepTCL) {
		this.tvvVldEsDepTCL = tvvVldEsDepTCL;
	}

}
