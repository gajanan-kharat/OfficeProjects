package com.inetpsa.pv2.rest.picto;

import java.util.List;

/**
 * The Class ManageImportDTO.
 */
public class ManageImportDTO {

	/** The import picto. */
	private List<ImportData> importPicto;

	/**
	 * Gets the import picto.
	 *
	 * @return the importPicto
	 */
	public List<ImportData> getImportPicto() {
		return importPicto;
	}

	/**
	 * Sets the import picto.
	 *
	 * @param importPicto the importPicto to set
	 */
	public void setImportPicto(List<ImportData> importPicto) {
		this.importPicto = importPicto;
	}
}
