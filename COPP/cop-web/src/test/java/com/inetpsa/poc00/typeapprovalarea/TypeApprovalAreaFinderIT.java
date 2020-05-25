/*
 * Creation : Jan 13, 2017
 */
package com.inetpsa.poc00.typeapprovalarea;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalArea;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaFactory;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaRepository;
import com.inetpsa.poc00.rest.typeapprovalarea.TypeApprovalAreaFinder;
import com.inetpsa.poc00.rest.typeapprovalarea.TypeApprovalAreaRepresentation;

/**
 * The Class TypeApprovalAreaFinderIT.
 */
@RunWith(SeedITRunner.class)
public class TypeApprovalAreaFinderIT {

	/** The type approval area factory. */
	@Inject
	TypeApprovalAreaFactory typeApprovalAreaFactory;

	/** The type approval area repository. */
	@Inject
	TypeApprovalAreaRepository typeApprovalAreaRepository;

	/** The type approval area finder. */
	@Inject
	TypeApprovalAreaFinder typeApprovalAreaFinder;

	/**
	 * Test get all type approval area.
	 */
	@Test
	public final void testGetAllTypeApprovalArea() {
		TypeApprovalArea typeApprovalAreaObj = typeApprovalAreaFactory.createTypeApprovalArea();
		typeApprovalAreaObj.setLabel("TypeApprovalAreaLabel" + Calendar.getInstance().getTime());
		typeApprovalAreaRepository.saveTypeApprovalArea(typeApprovalAreaObj);
		List<TypeApprovalAreaRepresentation> typeApprovalAreaList = typeApprovalAreaFinder.getAllTypeApprovalArea();
		assertNotNull(typeApprovalAreaList);
	}

	/**
	 * Test get all area names.
	 */
	@Test
	public final void testGetAllAreaNames() {
		TypeApprovalArea typeApprovalAreaObj = typeApprovalAreaFactory.createTypeApprovalArea();
		typeApprovalAreaObj.setLabel("TypeApprovalAreaLabel1" + Calendar.getInstance().getTime());
		typeApprovalAreaRepository.saveTypeApprovalArea(typeApprovalAreaObj);
		List<String> typeApprovalAreaList = typeApprovalAreaFinder.getAllAreaNames();
		assertNotNull(typeApprovalAreaList);
	}

	/**
	 * Test find type approval area data by label.
	 */
	@Test
	public final void testFindTypeApprovalAreaDataByLabel() {
		TypeApprovalArea typeApprovalAreaObj = typeApprovalAreaFactory.createTypeApprovalArea();
		typeApprovalAreaObj.setLabel("TypeApprovalAreaLabel2" + Calendar.getInstance().getTime());
		TypeApprovalArea typeApprovalAreaSavea = typeApprovalAreaRepository.saveTypeApprovalArea(typeApprovalAreaObj);
		List<TypeApprovalAreaRepresentation> typeApprovalAreaList = typeApprovalAreaFinder.findTypeApprovalAreaDataByLabel(typeApprovalAreaSavea.getLabel());
		assertNotNull(typeApprovalAreaList);
	}

	/**
	 * Test get type approval area data.
	 */
	@Test
	public final void testGetTypeApprovalAreaData() {
		TypeApprovalArea typeApprovalAreaObj = typeApprovalAreaFactory.createTypeApprovalArea();
		typeApprovalAreaObj.setLabel("TypeApprovalAreaLabel3" + Calendar.getInstance().getTime());
		typeApprovalAreaRepository.saveTypeApprovalArea(typeApprovalAreaObj);
		List<TypeApprovalAreaRepresentation> typeApprovalAreaList = typeApprovalAreaFinder.getTypeApprovalAreaData();
		assertNotNull(typeApprovalAreaList);
	}

	/**
	 * Test get all type approval area for rg.
	 */
	@Test
	public final void testGetAllTypeApprovalAreaForRg() {
		TypeApprovalArea typeApprovalAreaObj = typeApprovalAreaFactory.createTypeApprovalArea();
		typeApprovalAreaObj.setLabel("TypeApprovalAreaLabel4" + Calendar.getInstance().getTime());
		typeApprovalAreaRepository.saveTypeApprovalArea(typeApprovalAreaObj);
		List<TypeApprovalAreaRepresentation> typeApprovalAreaList = typeApprovalAreaFinder.getAllTypeApprovalAreaForRg();
		assertNotNull(typeApprovalAreaList);
	}

}
