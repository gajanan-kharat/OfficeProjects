/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.rest.statisticalcalculationrule;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.google.inject.Inject;
import com.inetpsa.poc00.Config;

@Path("/StatisticalCalculationRule")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class StatisticalCalculationRuleResource {

    @Inject
    private StatisticalcalFinder statisticalCalRuleFinder;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/StatisticalCalculation")
    public Response getAllStatisticalCalcRules() {

        List<StatisticalCalculationRuleRepresentation> statisticalRuleList = statisticalCalRuleFinder.getAllstatisticalRule();
        return Response.ok(statisticalRuleList).build();
    }

}
