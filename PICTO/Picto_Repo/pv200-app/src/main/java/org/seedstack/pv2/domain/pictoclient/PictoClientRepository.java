/*
 * Creation : Apr 6, 2016
 */
package org.seedstack.pv2.domain.pictoclient;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.user.User;

public interface PictoClientRepository extends GenericRepository<PictoClient, Long> {
    List<PictoClient> getPictoByUserId(long id);

    // PictoClient getPictoClient(long adminId, long pictoId);
    PictoClient getPictoClient(User adminId, Picto pictoId);

    List<String> getWorkingAdminList(long pictoId);

    void resetDownloadFlag(User adminId, Picto pictoId);

    // void deletePictoClient(long adminId, long pictoId);
    void deletePictoClient(User user, Picto picto);

    void addPicto(Picto picto, User user);

    List<PictoClient> getPictoClientInfo(Long userId, Long pictoId);

    void updateOpenLocalImgFlag(Picto picto, User user, boolean isOpenLocalImgFlag);

    void resetOpenImgFlag(Long userId);
}
