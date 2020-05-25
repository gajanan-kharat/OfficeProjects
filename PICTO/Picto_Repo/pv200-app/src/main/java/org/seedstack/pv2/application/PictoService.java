package org.seedstack.pv2.application;

import java.util.List;

import org.seedstack.business.Service;
import org.seedstack.pv2.infrastructure.data.picto.PictoDTO;
import org.seedstack.pv2.infrastructure.data.pictofamily.PictoFamilyDTO;

/**
 * Picto application service methods.
 */

@Service
public interface PictoService {

	/**
	 * Start the data migration.
	 */

	public void startDataMigartion();

	/**
	 * Update picto family.
	 *
	 * @param picFamily the pic family
	 * @return true, if successful
	 */
	public boolean updatePictoFamily(PictoFamilyDTO picFamily);


	/**
	 * Gets the all pictos.
	 *
	 * @return the all pictos
	 */
	List<PictoDTO> getAllPictos();

}
