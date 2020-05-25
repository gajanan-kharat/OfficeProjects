/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.rest.statisticalcurves;

import java.util.List;

public class ManageExportStatisticalCurvesDataRepresentationDto {

	private ExportStatisticalCurvesDataRepresentation statCurveData;

	private List<String> choiceList;

	private List<ExportStatisticalCurvesDataRepresentation> exportStatisticalCurvesDataRepresentationDtoList;

	public ExportStatisticalCurvesDataRepresentation getStatCurveData() {
		return statCurveData;
	}

	public void setStatCurveData(ExportStatisticalCurvesDataRepresentation statCurveData) {
		this.statCurveData = statCurveData;
	}

	public List<String> getChoiceList() {
		return choiceList;
	}

	public void setChoiceList(List<String> choiceList) {
		this.choiceList = choiceList;
	}

	public List<ExportStatisticalCurvesDataRepresentation> getExportStatisticalCurvesDataRepresentationDtoList() {
		return exportStatisticalCurvesDataRepresentationDtoList;
	}

	public void setExportStatisticalCurvesDataRepresentationDtoList(List<ExportStatisticalCurvesDataRepresentation> exportStatisticalCurvesDataRepresentationDtoList) {
		this.exportStatisticalCurvesDataRepresentationDtoList = exportStatisticalCurvesDataRepresentationDtoList;
	}

}
