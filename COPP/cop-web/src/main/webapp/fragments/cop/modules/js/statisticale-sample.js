define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {
	var statisticalSampleModule = angular.module('StatisticalSample', []);

	statisticalSampleModule.factory('statisticalSampleService', [
			'$resource', function($resource) {
				return {
					statisticalSampleResource : $resource('statisticalsample/:path/:tvvLabel/:esLabel/:carFactoryLabel/:statisticalRuleLabel', {
						path : '@path',
						tvvLabel : '@tvvLabel',
						esLabel : '@esLabel',
						carFactoryLabel : '@carFactoryLabel',
						statisticalRuleLabel : '@statisticalRuleLabel'
					}, {
						'getallStatisticalSample' : {
							method : 'GET',
							params : {
								path : 'statisticalsamplelist'
							},
							isArray : true

						},
						'statisticalSample' : {
							mathod : 'GET',
							params : {
								path : 'statisticalsampleselected'
							},
							isArray : true
						}
					})
				}

			}
	]);

	statisticalSampleModule.factory('ExportStatisticalCurveService', [
			'$resource',

			function($resource) {
				return {
					ExportStatisticalCurveResource : $resource('ExportStatisticalCurveReference/:path', {
						path : '@path'
					}, {

						'saveExportStatisticalCurve' : {
							method : 'POST',
							params : {
								path : 'ExportStatisticalCurve'
							}

						},

						'sendPdf' : {
							method : 'POST',
							params : {
								path : 'ExportSCPdf'
							}

						},

						'sendExcel' : {
							method : 'POST',
							params : {
								path : 'ExportSCExcel'
							}

						},

					})
				};
			}
	]);

	statisticalSampleModule.controller('StatisticalSampleController', [
			'$scope', '$routeParams', 'statisticalSampleService', 'NotificationService', 'CultureService', 'AuthorizationService', 'ExportStatisticalCurveService', function($scope, $routeParams, statisticalSampleService, NotificationService, CultureService, authorizationService, ExportStatisticalCurveService) {

				$scope.authorization = function() {

					if (authorizationService.hasRole('seed-w20', 'POCBRole') || authorizationService.hasRole('seed-w20', 'POCCRole') || authorizationService.hasRole('seed-w20', 'POCERole') || authorizationService.hasRole('seed-w20', 'POCFRole') || authorizationService.hasRole('seed-w20', 'POCGRole') || authorizationService.hasRole('seed-w20', 'POCHRole')) {
						return true;
					} else {
						return false;
					}

				};
				$scope.statisticalSampleList = [];
				$scope.esLabelList = [];
				$scope.srLabelList = [];
				$scope.cfLabelList = [];
				$scope.statisticalSampleSelected = {};
				
				$scope.getStatisticalSample = function() {
					$scope.showValidation = false;
					if ($scope.tvvLabel !== undefined && $scope.tvvLabel !== '') {
						statisticalSampleService.statisticalSampleResource.getallStatisticalSample({
							tvvLabel : $scope.tvvLabel
						}, function(response) {
							if (response.length !== 0) {
								$scope.statisticalSample = [];
								$scope.statisticalSampleList = response;
								angular.forEach(response, function(sampleList) {
									var esLabelCheck = true;
									angular.forEach($scope.esLabelList, function(esLabels) {
										if (sampleList.esLabel === esLabels.esLabel) {
											esLabelCheck = false;
										}
									});
									if (esLabelCheck) {
										$scope.esLabelList.push({
											'esLabel' : sampleList.esLabel
										})
									}
									;

									var srLabelCheck = true;
									angular.forEach($scope.srLabelList, function(srLabels) {
										if (sampleList.statisticalRuleLabel === srLabels.statisticalRuleLabel) {
											srLabelCheck = false;
										}
									});
									if (srLabelCheck) {
										$scope.srLabelList.push({
											'statisticalRuleLabel' : sampleList.statisticalRuleLabel
										})
									}
									;
									var cfLabelCheck = true;
									angular.forEach($scope.cfLabelList, function(cfLabels) {
										if (sampleList.carFactoryRepresentation.carFactoryLabel === cfLabels.carFactoryLabel) {
											cfLabelCheck = false;
										}
									});
									if (cfLabelCheck) {
										$scope.cfLabelList.push({
											'carFactoryLabel' : sampleList.carFactoryRepresentation.carFactoryLabel
										})
									}
									;
								});
							} else {
								$scope.statisticalSampleList = [];
								$scope.esLabelList = [];
								$scope.srLabelList = [];
								$scope.cfLabelList = [];
								$scope.statisticalSample = [];
								$scope.errorMassage = CultureService.localize('cop.statisticalSmaple.message.valueNotFound');
									
									$('#ErrorModal').modal('show');
							}
						}, function() {

						});
					} else {
						$scope.statisticalSampleList = [];
						$scope.esLabelList = [];
						$scope.srLabelList = [];
						$scope.cfLabelList = [];
						$scope.statisticalSample = [];
						$scope.errorMassage = CultureService.localize('cop.statisticalSample.label.requiredTvv');
							
							$('#ErrorModal').modal('show');
					}

				};

				$scope.showValidation = false;
				$scope.checkValues = function(){
					
					if ($scope.statisticalSampleList.eslabelselect !== undefined && $scope.statisticalSampleList.carFactoryselect !== undefined && $scope.statisticalSampleList.statisticalRuleSelect !== undefined) {
						$scope.showValidation = true;
					}else{
						$scope.showValidation = false;
					}
				}
				
				$scope.selectedCriteria = function() {
					if ($scope.statisticalSampleList.eslabelselect !== undefined && $scope.statisticalSampleList.carFactoryselect !== undefined && $scope.statisticalSampleList.statisticalRuleSelect !== undefined) {
						var esLabelSelected = $scope.statisticalSampleList.eslabelselect.esLabel;
						var carFactoryLabelSelected = $scope.statisticalSampleList.carFactoryselect.carFactoryLabel;
						var statisticalRuleLabelSelected = $scope.statisticalSampleList.statisticalRuleSelect.statisticalRuleLabel;

						statisticalSampleService.statisticalSampleResource.statisticalSample({
							tvvLabel : $scope.tvvLabel,
							esLabel : esLabelSelected,
							carFactoryLabel : carFactoryLabelSelected,
							statisticalRuleLabel : statisticalRuleLabelSelected
							
						}, function(response) {

							$scope.statisticalSample = response;
								}, function() {
						});
					} else {
						$scope.errorMassage = CultureService.localize('cop.statisticalSmaple.message.required');
						
						$('#ErrorModal').modal('show');
						//NotificationService.notify(CultureService.localize('cop.statisticalSmaple.message.required'))
					}
				}
				$scope.isNotEmptyisNotNull = function(_param) {
					if (null === _param || "" === _param) {
						return false;
					}
					return true;
				};
				//		-------- check for parameter passed in url
				$scope.checkforRoutes = function() {
					if ($scope.isNotEmptyisNotNull($routeParams.tvvLabel) && $routeParams.tvvLabel !== undefined) {
						$scope.tvvLabel = $routeParams.tvvLabel;
					}
				};

				//-------------export stats curve 

				$scope.data = {};
				$scope.pollutantGasList = [];
				$scope.auth = true;
				$scope.selection = [];
				$scope.file = "pdf";
				$scope.checkFlag = false;
				$scope.allFlag = false;
				var flag=false;
				$scope.refresh = function() {
					$scope.selection = [];
					$scope.file = "pdf";
					$scope.checkFlag = false;
					 flag=false;
				
				}

				
			
				$scope.toggleSelection = function (pgName) {
					var idx = $scope.selection.indexOf(pgName);
					$scope.allFlag = false;

					// is currently selected
					if (idx > -1) {
						$scope.selection.splice(idx, 1);
					}

					// is newly selected
					else {
						$scope.selection.push(pgName);
					}
				
				};

				$scope.selectAll = function() {
									
					if(!flag){
						$scope.selection = $scope.pollutantGasList.map(function(pg) {
							return pg;
						});	
						flag=!flag;
					
					}
					else{
						$scope.selection=[];
						flag=!flag;
						$scope.allFlag = false;
					}
					

				}

				$scope.saveExportStatisticalCurve = function(objectToSend) {
									
					ExportStatisticalCurveService.ExportStatisticalCurveResource.saveExportStatisticalCurve(objectToSend, function(success) {

						NotificationService.notify(CultureService.localize('cop.specificCop.message.successMessage'));
						$scope.data = success;
						$scope.pollutantGasList = $scope.data.pgGasLabelList;
						$('#showSCBox').modal('show')

					}, function() {
						NotificationService.notify(CultureService.localize('cop.specificCop.message.error'));
					});

				}

				$scope.sendFile = function() {

					var objectToSend = {
						'statCurveData' : $scope.data,
						'choiceList' : $scope.selection
					};

				

					if ($scope.file === "pdf") {
				
						ExportStatisticalCurveService.ExportStatisticalCurveResource.sendPdf(objectToSend, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.message.successMessage'));
							window.open("./ExportStatisticalCurveReference/exportStatCurvePdf", "_self");
						

						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.message.error'));
						});

					}

					if ($scope.file === "excel") {

						ExportStatisticalCurveService.ExportStatisticalCurveResource.sendExcel(objectToSend, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.message.successMessage'));
							window.open("./ExportStatisticalCurveReference/exportStatCurveExcel", "_self");
							

						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.message.error'));
						});
					}
					
				}

				$scope.checkforRoutes();

		
				$scope.showPollutentSelect = function(object) {
					$scope.saveExportStatisticalCurve(object);
				
				}
			}
	]);

	return {
		angularModules : [
			'StatisticalSample'
		]
	};
});