package com.inetpsa.pv2.rest.supercategory;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.rest.category.CategoryRepresentation;
import com.inetpsa.pv2.rest.picto.PictoFinder;
import com.inetpsa.pv2.rest.pictoclient.PictoClientResource;


/**
 * The Class SuperCategoryResource.
 */
@Path("/superCategory")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class SuperCategoryResource {

	/** The super category finder. */
	@Inject
	private SuperCategoryFinder superCategoryFinder;

	/** The logger. */
	@Logging
	private static Logger logger = LoggerFactory.getLogger(PictoClientResource.class);
	
	   @Inject
	    private PictoFinder pictoFinder;


	/**
	 * Gets the all super category.
	 *
	 * @param info the info
	 * @return the all super category
	 */
	@GET
	@Path("/getAllSuperCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSuperCategory(@Context UriInfo info) {
		List<SuperCategoryRepresentation> l_SuperCategory = superCategoryFinder.getSuperCategory();
		if (l_SuperCategory == null || l_SuperCategory.isEmpty() == true)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(l_SuperCategory).build();

	}

	/**
	 * Gets the all category.
	 *
	 * @param info the info
	 * @return the all category
	 */
	@GET
	@Path("/getCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCategory(@Context UriInfo info) {
		String superCategoryName = info.getQueryParameters().getFirst("id");

		List<CategoryRepresentation> l_Categories = superCategoryFinder.getCategoriesBySuperCategoryName(superCategoryName);
		if (l_Categories == null || l_Categories.isEmpty() == true)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(l_Categories).build();

	}
	
	@GET
	@Path("/getCategoryList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCategoryList(@Context UriInfo info) {
		
		List<CategoryRepresentation> l_Categories = pictoFinder.getAllCategories();
		if (l_Categories == null || l_Categories.isEmpty() == true)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(l_Categories).build();

	}

}
