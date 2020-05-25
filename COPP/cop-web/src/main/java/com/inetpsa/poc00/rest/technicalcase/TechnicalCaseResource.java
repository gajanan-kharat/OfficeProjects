package com.inetpsa.poc00.rest.technicalcase;

import javax.ws.rs.Path;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;

@Path("/TechnicalCases")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class TechnicalCaseResource {

}
