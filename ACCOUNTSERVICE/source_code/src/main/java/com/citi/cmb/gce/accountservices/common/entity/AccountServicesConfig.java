package com.citi.cmb.gce.accountservices.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ACCOUNT_STATUS_CONFIG")
public class AccountServicesConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONFIG_NAME")
	private String configName;
	
	@Column(name="CONFIG_VALUE")
	private String configValue;
	
	@Column(name="DESCRIPTION")
	private String description;

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
