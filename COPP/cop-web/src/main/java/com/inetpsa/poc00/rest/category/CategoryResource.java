package com.inetpsa.poc00.rest.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.category.CategoryService;
import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.category.CategoryFactory;
import com.inetpsa.poc00.domain.category.CategoryRepository;
import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.clasz.ClaszFactory;
import com.inetpsa.poc00.domain.clasz.ClaszRepository;
import com.inetpsa.poc00.rest.clasz.ClaszAssembler;
import com.inetpsa.poc00.rest.clasz.ClaszFinder;
import com.inetpsa.poc00.rest.clasz.ClaszRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvFinder;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class CategoryResource.
 */
@Path("/categoryReference")
@JpaUnit(Config.JPA_UNIT)
public class CategoryResource {

	/** The category finder. */
	@Inject
	CategoryFinder categoryFinder;

	/** The category factory. */
	@Inject
	CategoryFactory categoryFactory;

	/** The category assembler. */
	@Inject
	CategoryAssembler categoryAssembler;

	/** The category repo. */
	@Inject
	CategoryRepository categoryRepo;

	/** The clasz finder. */
	@Inject
	ClaszFinder claszFinder;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The clasz repo. */
	@Inject
	ClaszRepository claszRepo;

	/** The clasz assembler. */
	@Inject
	ClaszAssembler claszAssembler;

	/** The clasz factory. */
	@Inject
	ClaszFactory claszFactory;

	/** The category service. */
	@Inject
	CategoryService categoryService;

	/** The tvv finder. */
	@Inject
	TvvFinder tvvFinder;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the all category.
	 *
	 * @return the all category
	 */
	@GET
	@Path("/CategoryList")
	@Transactional
	public Response getAllCategory() {

		List<CategoryRepresentation> categoryList;

		categoryList = categoryFinder.getAllCategory();

		return Response.ok(categoryList).build();
	}

	/**
	 * Save category.
	 * 
	 * @param categoryDto the category dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Categories")
	@Transactional
	public Response saveCategory(CategoryDto categoryDto) {

		CategoryRepresentation categoryRepresentationResponse = categoryDto.getCategoryRepresentationList().get(0);
		Map<String, Category> categoryMap = new HashMap<String, Category>();

		for (CategoryRepresentation categoryRepObj : categoryDto.getCategoryRepresentationList()) {
			List<Category> categoryList = categoryFinder.findCategoryByLabel(categoryRepObj.getLabel());
			Category category;

			if (!categoryList.isEmpty()) {
				if (categoryMap.size() != 0) {
					category = categoryRepo.load(categoryList.get(0).getEntityId());
					Category categoriesTosave = categoryMap.get(categoryRepObj.getLabel());
					if (categoriesTosave != null) {
						categoriesTosave.getClasz().addAll(category.getClasz());
					} else {
						Category object = categoryFactory.createCategory();
						categoryAssembler.doMergeAggregateWithDto(object, categoryRepObj);
						object.getClasz().addAll(category.getClasz());
						categoriesTosave = object;
					}
					categoryMap.put(categoriesTosave.getLabel(), categoriesTosave);
				} else {
					category = categoryRepo.load(categoryList.get(0).getEntityId());
					Clasz clasz = claszRepo.load(categoryRepObj.getClasz_Id());
					if (category.getEntityId() == categoryRepObj.getEntityId()) {

						if (!category.getClasz().contains(clasz))
							category.getClasz().remove(categoryRepObj.getClasz());
					}
					category.getClasz().add(clasz);
					category.setDescription(categoryRepObj.getDescription());
					categoryMap.put(category.getLabel(), category);
				}
			} else {
				categoryRepObj.getClaszRepresentation().add(new ClaszRepresentation(categoryRepObj.getClasz_Id()));
				Category obj = categoryFactory.createCategory();
				categoryAssembler.doMergeAggregateWithDto(obj, categoryRepObj);
				if (categoryMap.size() == 0)
					categoryMap.put(obj.getLabel(), obj);
				else {
					Category categoriesTosave = categoryMap.get(categoryRepObj.getLabel());
					if (categoriesTosave != null)
						categoriesTosave.getClasz().addAll(obj.getClasz());
					else
						categoriesTosave = obj;
					categoryMap.put(categoriesTosave.getLabel(), categoriesTosave);
				}
			}
		}
		for (Map.Entry<String, Category> entry : categoryMap.entrySet()) {
			Category obj = categoryService.saveCategory(entry.getValue());
			CategoryRepresentation catrepresent = new CategoryRepresentation();
			categoryAssembler.doAssembleDtoFromAggregate(catrepresent, obj);

			categoryRepresentationResponse = catrepresent;
		}

		return Response.ok(categoryRepresentationResponse).build();

	}

	/**
	 * Delete category.
	 *
	 * @param entityId the entity id
	 * @param claszLabel the clasz label
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/Category/{entityId}/{claszLabel}")
	@Transactional(rollbackOn = Exception.class)
	public Response deleteCategory(@PathParam("entityId") Long entityId, @PathParam("claszLabel") String claszLabel) {

		boolean deleted = categoryService.deleteCategory(entityId, claszLabel);

		if (deleted)
			return Response.ok().build();

		logger.warn("Can't delete Category as used in other table : foreign key constraint");
		return Response.status(Response.Status.PRECONDITION_FAILED).build();

	}

	/**
	 * Gets the categories.
	 * 
	 * @return the categories
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/AllCategories")
	@Transactional
	public Response getCategories() {
		List<CategoryRepresentation> listToReturn = categoryFinder.getAllCategories();

		return Response.ok(listToReturn).build();

	}

	/**
	 * Gets the all categories for es.
	 * 
	 * @param emsId the ems id
	 * @return the all categories for es
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/AllCategories/{emsId}")
	@Transactional
	public Response getAllCategoriesForES(@PathParam("emsId") String emsId) {
		List<Category> listToReturn;
		List<CategoryRepresentation> categoryList = new ArrayList<CategoryRepresentation>();
		if (emsId != null) {
			long id = Long.parseLong(emsId);
			listToReturn = categoryFinder.getAllCategoriesForES(id);
			for (Category category : listToReturn) {

				CategoryRepresentation object = new CategoryRepresentation();
				categoryAssembler.assembleDtoFromAggregate(object, category);
				categoryList.add(object);
			}

		}

		return Response.ok(categoryList).build();

	}

	/**
	 * Gets the all categories for emission.
	 *
	 * @return the all categories for emission
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/AllCategoriesES")
	@Transactional
	public Response getAllCategoriesForEmission() {
		List<CategoryRepresentation> categoryList = new ArrayList<CategoryRepresentation>();
		List<Category> listToReturn = categoryFinder.getCategories();
		for (Category category : listToReturn) {
			CategoryRepresentation object = new CategoryRepresentation();
			categoryAssembler.assembleDtoFromAggregate(object, category);
			categoryList.add(object);
		}

		return Response.ok(categoryList).build();

	}

	/**
	 * Gets the all categories for tvv.
	 *
	 * @return the all categories for tvv
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/AllCategoriesTvv")
	@Transactional
	public Response getAllCategoriesForTvv() {
		List<CategoryRepresentation> categoryList = new ArrayList<CategoryRepresentation>();
		List<Category> listToReturn = categoryFinder.getCategories();
		for (Category category : listToReturn) {
			for (Clasz clasz : category.getClasz()) {

				CategoryRepresentation object = new CategoryRepresentation();
				ClaszRepresentation claszrepresent = new ClaszRepresentation();

				categoryAssembler.assembleDtoFromAggregate(object, category);
				object.setClasz_label(clasz.getLabel());
				categoryList.add(object);

			}

		}

		return Response.ok(categoryList).build();

	}
}
