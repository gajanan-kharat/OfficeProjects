package org.seedstack.pv2.domain.picto;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.user.User;

/**
 * Repository interface of Picto.
 */

public interface PictoRepository extends GenericRepository<Picto, Long> {

    /**
     * Saves the picto.
     * 
     * @param picto the picto to save
     * @return the Picto
     */
    Picto savePicto(Picto picto);

    /**
     * Persists the picto.
     * 
     * @param picto the picto to persist
     */
    void persistPicto(Picto picto);
	
	/**
	 * Get Picto information by family id.
	 *
	 * @param pictoFamilyID
	 * @param variant
	 * @return the Picto
	 */
	
	Picto findAllPictoByFamilyID(PictoFamily  pictoFamilyID,String variant, String version);

    /*
     * get list of all pictos
     */
    List<Picto> getAllPictos();
    
    /**
     * @param user
     * @param pictoId
     */
    //void updateAIFileInfo(User user, Long pictoId);
    
    void updateAIFileInfo(User user, Picto picto);
    
    /**
     * @param pictoId
     * @return
     */
    Picto getPictoById(Long pictoId);
    
    

}
