package com.inetpsa.pv2.infrastructure.finders;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.pv2.domain.color.Color;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.rest.color.ColorAssembler;
import com.inetpsa.pv2.rest.color.ColorFinder;
import com.inetpsa.pv2.rest.color.ColorRepresentation;

/**
 * Class :ColorFinder.
 */
public class JpaColorFinder implements ColorFinder {

    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(JpaColorFinder.class);

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /** The m color assembler. */
    @Inject
    private ColorAssembler colorAssembler;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.color.ColorFinder#getAllColor()
     */
    @Override
    public List<ColorRepresentation> getAllColor() {
        logger.info("Start: Query to get all Color");

        String contentOfJpqlQuery = "select distinct objColor from Color objColor";

        List<ColorRepresentation> colorRepresenationList = new ArrayList<ColorRepresentation>();
        try {
            TypedQuery<Color> typedQuery = entityManager.createQuery(contentOfJpqlQuery, Color.class);
            List<Color> colorList = typedQuery.getResultList();

            if (colorList != null && !colorList.isEmpty()) {
                for (Color color : colorList) {
                    ColorRepresentation colorRepresentation = new ColorRepresentation(false);
                    colorAssembler.doAssembleDtoFromAggregate(colorRepresentation, color);
                    colorRepresenationList.add(colorRepresentation);
                }
            }
        } catch (Exception e) {
            logger.error("Exception occure while fetching the all categories:", e);
            colorRepresenationList = null;
        }
        logger.info(" Finish: return the all categories object");
        return colorRepresenationList;
    }

}
