package com.inetpsa.poc00.infrastructure.jpa.gloablreference;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyRepository;

/**
 * The Class ProjectCodeFamilyJpaRepository.
 */
public class ProjectCodeFamilyJpaRepository extends BaseJpaRepository<ProjectCodeFamily, Long> implements ProjectCodeFamilyRepository {

    /** The logger. */
    @Logging
    private Logger logger;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyRepository#saveProjectCodeFamily(com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public ProjectCodeFamily saveProjectCodeFamily(ProjectCodeFamily projectCodeFamily) {
        return super.save(projectCodeFamily);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyRepository#persistProjectCodeFamily(com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public ProjectCodeFamily persistProjectCodeFamily(ProjectCodeFamily projectCodeFamily) {

        return entityManager.merge(projectCodeFamily);

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyRepository#deleteAll(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public int deleteAll(long id) {
        logger.info("delete value from ProjectCodeFamily where id=" + id);

        try {
            return entityManager.createQuery("DELETE FROM ProjectCodeFamily where entityId=" + id).executeUpdate();
        } catch (Exception e) {
            logger.error(" Error occured while deleting data ", e);
            return 0;

        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyRepository#getAllProjectCodeFamily()
     */
    @Override
    public List<ProjectCodeFamily> getAllProjectCodeFamily() {

        TypedQuery<ProjectCodeFamily> query = entityManager.createQuery("SELECT distinct pcf FROM ProjectCodeFamily pcf", ProjectCodeFamily.class);

        List<ProjectCodeFamily> tgData = query.getResultList();

        return tgData;

    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public ProjectCodeFamily loadProjectCodeFamily(long projectFamilyId) {

        return super.load(projectFamilyId);
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public ProjectCodeFamily getProjecCodeFamilybyLabel(String projectFamilyLabel) {

        TypedQuery<ProjectCodeFamily> query = entityManager
                .createQuery("from ProjectCodeFamily pcf where CONCAT(pcf.projectCodeLabel,pcf.vehicleFamilyLabel) = ?1", ProjectCodeFamily.class);
        query.setParameter(1, projectFamilyLabel);

        List<ProjectCodeFamily> projectFamilyList = query.getResultList();

        if (!projectFamilyList.isEmpty())
            return projectFamilyList.get(0);

        return null;
    }

}
