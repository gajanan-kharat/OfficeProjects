package com.inetpsa.poc00.infrastructure.data.genomelcdvdictionary;

import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;

/**
 * @author
 */
public class GenomeLCDVDictionaryDTO {
	private Long id;
	private String name;

	@MatchingEntityId
	@MatchingFactoryParameter(index = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@MatchingFactoryParameter(index = 1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
