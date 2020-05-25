package com.inetpsa.pv2.rest.pictofamily;

import java.math.BigInteger;
import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.user.User;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.pv2.rest.type.TypeRepresentation;


/**
 * The Interface PictoFamilyFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface PictoFamilyFinder {

	/**
	 * Get all getFamilyInfoByRefnum.
	 *
	 * @param refnum the refnum
	 * @param user the user
	 * @return PictoFamilyRepresentation list
	 */
	PictoFamilyRepresentation getFamilyInfoByRefnum(String refnum, User user);

	/**
	 * Download single PDF.
	 *
	 * @param refnum the refnum
	 * @return the picto family representation
	 */
	PictoFamilyRepresentation downloadSinglePDF(String refnum);

	/**
	 * Download multi PDF.
	 *
	 * @param refnum the refnum
	 * @return the list
	 */
	List<PictoFamilyRepresentation> downloadMultiPDF(String refnum);

	/**
	 * Gets the all types.
	 *
	 * @return the all types
	 */
	List<TypeRepresentation> getAllTypes();

	/**
	 * Gets the max version for reference.
	 *
	 * @return the max version for reference
	 */
	Long getMaxVersionForReference();

	/**
	 * Removes the favorite.
	 *
	 * @param userId the user id
	 * @param PictoFamilyId the picto family id
	 */
	public void removeFavorite(Long userId, Long PictoFamilyId);

	/**
	 * Gets the fav picto I dby user.
	 *
	 * @param userId the user id
	 * @return the fav picto I dby user
	 */
	public List<BigInteger> getFavPictoIDbyUser(Long userId);

	/**
	 * Gets the cart picto I dby user.
	 *
	 * @param userId the user id
	 * @return the cart picto I dby user
	 */
	public List<BigInteger> getCartPictoIDbyUser(Long userId);

}
