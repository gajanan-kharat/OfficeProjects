package com.inetpsa.poc00.rest.offline;

import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;

/**
 * The Class OfflineResource.
 */
@Path("/offline")
@JpaUnit(Config.JPA_UNIT)
@Transactional
public class OfflineResource {

    /** The temp path. */
    @Configuration("com.inetpsa.cop.export.exportbce.tempfiledirectory")
    private String tempPath;

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(OfflineResource.class);

    /**
     * Download manifest.
     *
     * @return the response
     */
    @GET
    @Path("/manifest")
    @Produces("text/cache-manifest")
    public Response downloadManifest() {
    	
    	logger.info("Loading Cache File");
    	
    	InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("offline.appcache");
      
    	// Creating Response and Adding File to Response
        ResponseBuilder response = Response.ok(inputStream, "text/cache-manifest");

        // Setting Response header
        response.header("Content-Disposition", "attachment; filename=\"" + "offline.appcache" + "\"");

        return response.build();

    }
    
}
