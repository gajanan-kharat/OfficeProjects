package com.inetpsa.poc00.domain.archivebox;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating ArchiveBox objects.
 */
public interface ArchiveBoxFactory extends GenericFactory<ArchiveBox> {

	/**
	 * Creates a new ArchiveBox object.
	 * 
	 * @return the archive box
	 */
	ArchiveBox createArchiveBox();
}
