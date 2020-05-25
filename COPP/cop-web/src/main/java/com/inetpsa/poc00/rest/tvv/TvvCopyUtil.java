/*
 * Creation : Jun 15, 2016
 */
package com.inetpsa.poc00.rest.tvv;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechDataFactory;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDLFactory;
import com.inetpsa.poc00.rest.tvvvaluedtdl.TvvValuedTDLFinder;
import com.inetpsa.poc00.rest.tvvvaluedtdl.ValuedGenericDataFinder;

/**
 * The Class TvvCopyUtil.
 */
public class TvvCopyUtil {
	
	/** The tvv valued TDL finder. */
	@Inject
	private TvvValuedTDLFinder tvvValuedTDLFinder;
	
	/** The tvv valued TDL factory. */
	@Inject
	private TvvValuedTvvDepTDLFactory tvvValuedTDLFactory;
	
	/** The valued data finder. */
	@Inject
	private ValuedGenericDataFinder valuedDataFinder;
	
	/** The valued data factory. */
	@Inject
	private TvvValuedTechDataFactory valuedDataFactory;

	// private static TvvCopyUtil instance;

	/**
	 * Copy tvv valued TDL lists.
	 *
	 * @param tvvId the tvv id
	 * @param newObj the new obj
	 * @return the list
	 */
	public List<TvvValuedTvvDepTDL> copyTvvValuedTDLLists(long tvvId, TVV newObj) {

		List<TvvValuedTvvDepTDL> tdlList = tvvValuedTDLFinder.getAllValuedTDL(tvvId);
		List<TvvValuedTvvDepTDL> copiedList = new ArrayList<TvvValuedTvvDepTDL>();
		for (TvvValuedTvvDepTDL oldTDL : tdlList) {
			TvvValuedTvvDepTDL copiedTDL = tvvValuedTDLFactory.createTvvValuedTvvDepTDL(oldTDL);
			copiedTDL.setValuedTechnicalData(new ArrayList<TvvValuedTechData>());
			for (TvvValuedTechData genericData : valuedDataFinder.getAllValuedDataForList(oldTDL.getEntityId())) {
				TvvValuedTechData copiedData = valuedDataFactory.createValuedGenericTechData(genericData);
				copiedData.setTvvDepTDL(copiedTDL);
				copiedTDL.getValuedTechnicalData().add(copiedData);
			}

			// copiedTDL = tvvDepTDLRepository.saveTvvDepTDL(copiedTDL);
			copiedTDL.setTvvObj(newObj);
			copiedList.add(copiedTDL);

		}
		return copiedList;

	}

}
