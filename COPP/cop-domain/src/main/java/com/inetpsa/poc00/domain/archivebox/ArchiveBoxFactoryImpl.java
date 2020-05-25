package com.inetpsa.poc00.domain.archivebox;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class ArchiveBoxFactoryImpl.
 */
public class ArchiveBoxFactoryImpl extends BaseFactory<ArchiveBox> implements ArchiveBoxFactory {

	/* (non-Javadoc)
	 * @see com.inetpsa.poc.domain.archivebox.ArchiveBoxFactory#createArchiveBox()
	 */
	@Override
	public ArchiveBox createArchiveBox() {

		return new ArchiveBox();
	}

}
