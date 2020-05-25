package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyFinder;
import com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyRepresentation;

public class JPAProjectCodeFamilyFinder implements ProjectCodeFamilyFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /** The logger. */
    @Logging
    private Logger logger;

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyFinder#findAllFamilleData()
     */
    // get ProjectCodeFamilyData
    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyFinder#findAllFamilleData()
     */
    @Override
    public List<ProjectCodeFamilyRepresentation> findAllFamilleData() {

        TypedQuery<ProjectCodeFamilyRepresentation> query = entityManager.createQuery(
                "select new " + ProjectCodeFamilyRepresentation.class.getName()
                        + "(t1.lcdvCodeValue,t1.kmat,t1.genomeLcdvCodeValue.frLabel,pf.projectCodeLabel,pf.vehicleFamilyLabel,t1.entityId,pf.entityId)"
                        + " from GenomeTVVRule t1 left join t1.projectCodeFamily pf "
                        + "where t1.lcdvCodeName='B0C' and t1.lcdvCodeValue = t1.genomeLcdvCodeValue.lcdvCodeValue GROUP BY t1.lcdvCodeValue,t1.kmat ORDER BY t1.entityId asc",
                ProjectCodeFamilyRepresentation.class);

        return query.getResultList();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyFinder#findProjectCodeFamilyDataByLabel(java.lang.String, java.lang.String)
     */
    @Override
    public List<ProjectCodeFamilyRepresentation> findProjectCodeFamilyDataByLabel(String projectCodeLabel, String vehicleFamilyLabel) {
        logger.info("querry running to check if label present");

        TypedQuery<ProjectCodeFamilyRepresentation> query = entityManager.createQuery("select new " + ProjectCodeFamilyRepresentation.class.getName()
                + "(pcf.entityId) from ProjectCodeFamily pcf where pcf.projectCodeLabel='" + projectCodeLabel + "' and pcf.vehicleFamilyLabel='"
                + vehicleFamilyLabel + "'", ProjectCodeFamilyRepresentation.class);

        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyFinder#getAllProjectFamilies()
     */
    @Override
    public List<ProjectCodeFamilyRepresentation> getAllProjectFamilies() {
        TypedQuery<ProjectCodeFamilyRepresentation> query = entityManager.createQuery(
                "select new " + ProjectCodeFamilyRepresentation.class.getName()
                        + "(pf.entityId,pf.projectCodeLabel,pf.vehicleFamilyLabel,pf.genomeTvvRule.lcdvCodeValue) from ProjectCodeFamily pf ",
                ProjectCodeFamilyRepresentation.class);
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyFinder#getAllProjectFamilyNames()
     */
    @Override
    public List<String> getAllProjectFamilyNames() {
        TypedQuery<String> query = entityManager.createQuery(
                "select DISTINCT CONCAT(pf.projectCodeLabel,pf.vehicleFamilyLabel) as PCFamilyLabel from ProjectCodeFamily pf ", String.class);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyFinder#getAllProjectFamiliesForTvv(java.lang.String)
     */
    @Override
    public List<ProjectCodeFamilyRepresentation> getAllProjectFamiliesForTvv(List<String> kmatList) {
        TypedQuery<ProjectCodeFamilyRepresentation> query = entityManager.createQuery(
                "select  new " + ProjectCodeFamilyRepresentation.class.getName()
                        + "(pf.entityId,pf.projectCodeLabel,pf.vehicleFamilyLabel,pf.genomeTvvRule.lcdvCodeValue) from ProjectCodeFamily pf where pf.genomeTvvRule.kmat in (:kmatList) AND pf.genomeTvvRule.lcdvCodeName = 'B0C' ",
                ProjectCodeFamilyRepresentation.class);
        query.setParameter("kmatList", kmatList);
        return query.getResultList();
    }

}
