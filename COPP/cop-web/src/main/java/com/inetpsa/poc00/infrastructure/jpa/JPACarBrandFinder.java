package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.carbrand.CarBrandFactory;
import com.inetpsa.poc00.domain.carbrand.CarBrandRepository;
import com.inetpsa.poc00.rest.carbrand.CarBrandFinder;
import com.inetpsa.poc00.rest.carbrand.CarBrandRepresentation;

/**
 * The Class JPACarBrandFinder.
 */
public class JPACarBrandFinder implements CarBrandFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    @Inject
    private CarBrandRepository carBrandRepo;

    @Inject
    private CarBrandFactory carBrandFactory;

    /** The logger. */
    @Logging
    private Logger logger;

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.carbrand.CarBrandFinder#findAllConstructeurData()
     */
    /*
     * @see com.inetpsa.poc00.rest.referential.globalReference.CarBrandFinder#findAllConstructeurData() get CarBrand Data
     */
    @Override
    public List<CarBrandRepresentation> findAllConstructeurData() {

        logger.info("querry running to get CarBrand value");
        TypedQuery<CarBrandRepresentation> query = entityManager.createQuery(
                "select new " + CarBrandRepresentation.class.getName()
                        + "(t1.entityId,t1.lcdvCodeValue,t1.kmat,t1.genomeLcdvCodeValue.frLabel,cb.brandLabel,cb.makerLabel,cb.entityId)"
                        + " from GenomeTVVRule t1 left join t1.carBrand cb"
                        + " where t1.lcdvCodeName='B0B' and t1.lcdvCodeValue = t1.genomeLcdvCodeValue.lcdvCodeValue GROUP BY t1.lcdvCodeValue,t1.kmat ORDER BY t1.entityId asc",
                CarBrandRepresentation.class);

        return query.getResultList();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.carbrand.CarBrandFinder#findAllCarBrandData()
     */
    @Override
    public List<CarBrandRepresentation> findCarBrandDataByLabel(String brandLable, String makerLable) {
        logger.info("querry running to check if label present");

        TypedQuery<CarBrandRepresentation> query = entityManager.createQuery("select new " + CarBrandRepresentation.class.getName()
                + "(cb.entityId) from CarBrand cb where cb.brandLabel='" + brandLable + "' and cb.makerLabel='" + makerLable + "'",
                CarBrandRepresentation.class);

        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.carbrand.CarBrandFinder#findAllCarBrandData()
     */
    @Override
    public List<CarBrandRepresentation> findAllCarBrandData() {
        TypedQuery<CarBrandRepresentation> query = entityManager.createQuery("select new " + CarBrandRepresentation.class.getName()
                + "(cb.entityId,cb.brandLabel,cb.makerLabel,cb.genomeTvvRule.lcdvCodeValue)" + " from CarBrand cb", CarBrandRepresentation.class);

        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.carbrand.CarBrandFinder#getCarBrandsForTVV(long)
     */
    @Override
    public List<CarBrandRepresentation> getCarBrandsForTVV(long tvvId) {
        TypedQuery<CarBrandRepresentation> query = entityManager.createQuery("select new " + CarBrandRepresentation.class.getName()
                + "(cb.entityId,cb.brandLabel,cb.makerLabel)" + " from CarBrand cb INNER JOIN cb.tvvSet t where t.entityId=" + tvvId,
                CarBrandRepresentation.class);
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.carbrand.CarBrandFinder#getAllCarBrandDataForTVV(java.util.List)
     */
    @Override
    public List<CarBrandRepresentation> getAllCarBrandDataForTVV(List<String> kmatList) {

        TypedQuery<CarBrandRepresentation> query = entityManager.createQuery(
                "select new " + CarBrandRepresentation.class.getName()
                        + "(cb.entityId,cb.brandLabel,cb.makerLabel,cb.genomeTvvRule.lcdvCodeValue) from CarBrand cb where cb.genomeTvvRule.kmat in (:kmatList) AND cb.genomeTvvRule.lcdvCodeName = 'B0B' ",
                CarBrandRepresentation.class);
        query.setParameter("kmatList", kmatList);
        List<CarBrandRepresentation> carBrandResult = query.getResultList();

        if (carBrandResult == null || carBrandResult.isEmpty()) {
            TypedQuery<String> temp = entityManager.createQuery(
                    "select distinct codeValue.frLabel from GenomeLCDVDictionary d join d.genomeLcdvCodeList lcdvcode join lcdvcode.genomeLcdvCodeValueList codeValue where lcdvcode.codeName = 'B0B' AND d.kmat in (:kmatList)",
                    String.class);
            temp.setParameter("kmatList", kmatList);

            List<String> frLabel = temp.getResultList();

            for (String brandLabel : frLabel) {
                TypedQuery<CarBrandRepresentation> carBrandLabel = entityManager.createQuery("select new " + CarBrandRepresentation.class.getName()
                        + "(cb.entityId) from CarBrand cb where cb.brandLabel='" + brandLabel + "' ", CarBrandRepresentation.class);

                List<CarBrandRepresentation> obj = carBrandLabel.getResultList();

                if (obj == null || obj.isEmpty()) {
                    CarBrand cb = carBrandFactory.createCarBrand();
                    cb.setBrandLabel(brandLabel);
                    carBrandRepo.saveCarBrand(cb);
                }
            }

            TypedQuery<CarBrand> tempQuery = entityManager.createQuery(
                    "select cb from GenomeLCDVDictionary d join d.genomeLcdvCodeList lcdvcode join lcdvcode.genomeLcdvCodeValueList codeValue, CarBrand cb  where lcdvcode.codeName = 'B0B' AND d.kmat in (:kmatList) AND cb.brandLabel = codeValue.frLabel ",
                    CarBrand.class);
            tempQuery.setParameter("kmatList", kmatList);
            List<CarBrand> carBrandList = tempQuery.getResultList();

            carBrandResult = new ArrayList<>();

            List<Long> entityId = new ArrayList<>();

            for (CarBrand obj : carBrandList) {
                String lcdvCodeValue;
                if (!entityId.contains(obj.getEntityId())) {
                    CarBrandRepresentation cbr = new CarBrandRepresentation();
                    cbr.setEntityId(obj.getEntityId());
                    cbr.setBrandLable(obj.getBrandLabel());
                    cbr.setMakerLable(obj.getMakerLabel());
                    String makerLabel = obj.getMakerLabel();
                    if (makerLabel == null) {
                        makerLabel = "";
                    }
                    if (obj.getGenomeTvvRule() != null)
                        lcdvCodeValue = obj.getGenomeTvvRule().getLcdvCodeValue() + "-";
                    else {
                        lcdvCodeValue = "";
                    }
                    cbr.setDisplayLabel(lcdvCodeValue + obj.getBrandLabel() + "-" + makerLabel);

                    carBrandResult.add(cbr);
                    entityId.add(obj.getEntityId());
                }

            }

        }

        return carBrandResult;

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.carbrand.CarBrandFinder#findAllCarBrandLabel()
     */
    @Override
    public List<String> findAllCarBrandLabel() {
        TypedQuery<String> query = entityManager.createQuery("select DISTINCT CONCAT(cb.brandLabel,cb.makerLabel) as carBrand from CarBrand cb",
                String.class);

        return query.getResultList();
    }
}
