define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {
	var globalModule = angular.module('resulttestpollutant', []);
	// Calling resource For vehicle search STARTS------------------
	globalModule.factory('SearchTestResultsService', [
			'$resource', function($resource) {
				return {
					SearchTestResultsResource : $resource('resulttestpollutant/:path/:vehicleFileId', {
						path : '@path',
						vehicleFileId : '@vehicleFileId'
					}, {
						'searchTestResult' : {
							method : 'GET',
							params : {
								path : 'resultpollutant'

							},
							isArray : false
						}
					})
				};
			}
	]);

	// ------------------ENDS------

	// Calling resource For setting kept in statistical sample flag STARTS------------------
	globalModule.factory('ChangeKeptInStatisSampleService', [
			'$resource', function($resource) {
				return {
					ChangeKeptInStatisSampleResource : $resource('resulttestpollutant/:path', {
						path : '@path'

					}, {
						'changeKeptInStatisSample' : {
							method : 'POST',
							params : {
								path : 'keptinstatisticalsample'

							},
							isArray : false
						}
					})
				};
			}
	]);

	// ------------------ENDS------
	// Calling resource For vehicle data STARTS------------------
	globalModule.factory('GetVehicleFileDataService', [
			'$resource', function($resource) {
				return {
					GetVehicleFileDataResource : $resource('resulttestpollutant/:path/:vehicleFileId', {
						path : '@path',
						vehicleFileId : '@vehicleFileId'

					}, {
						'getVehicleFileData' : {
							method : 'GET',
							params : {
								path : 'vehiclefiledata'

							},
							isArray : false
						}
					})
				};
			}
	]);

	// ------------------ENDS------
	// Calling resource to generate PDF STARTS------------------
	globalModule.factory('GeneratePDFService', [
			'$resource', function($resource) {
				return {
					GeneratePDFResource : $resource('resulttestpollutant/:path', {
						path : '@path'

					}, {
						'generatePdfFile' : {
							method : 'POST',
							params : {
								path : 'resultsetpdf'

							},
							isArray : false
						}
					})
				};
			}
	]);

	// ------------------ENDS------
	// Change vehiclefilestatus STARTS------------------
	globalModule.factory('VehiclefileStatusService', [
			'$resource', function($resource) {
				return {
					VehiclefileStatusResource : $resource('resulttestpollutant/:path/:vehicleFileId', {
						path : '@path',
						vehicleFileId : '@vehicleFileId'

					}, {
						'changeVehiclefileStatus' : {
							method : 'GET',
							params : {
								path : 'countertest'

							},
							isArray : false
						}
					})
				};
			}
	]);

	// ------------------ENDS------
	// Change in_quarantaine value STARTS------------------
	globalModule.factory('InQuarantineService', [
			'$resource', function($resource) {
				return {
					InQuarantineResource : $resource('resulttestpollutant/:path', {
						path : '@path'

					}, {
						'changeInQuarantineVal' : {
							method : 'POST',
							params : {
								path : 'inqurantine'

							},
							isArray : true
						}
					})
				};
			}
	]);

	// ------------------ENDS------
	// Calling resource For vehicle search STARTS------------------
	globalModule.factory('SaveResultSet', [
			'$resource', function($resource) {
				return {
					saveResultsetResource : $resource('resulttestpollutant/:path', {
						path : '@path'

					}, {
						'saveResultset' : {
							method : 'POST',
							params : {
								path : 'resultpollutant'

							},
							isArray : false
						}
					})
				};
			}
	]);
	// ------------------ENDS------
	// Calling resource to calculate decision STARTS------------------
	globalModule.factory('CalculateDecisionService', [
			'$resource', function($resource) {
				return {
					calculateDecisionResource : $resource('resulttestpollutant/:path', {
						path : '@path'

					}, {
						'calculateResult' : {
							method : 'POST',
							params : {
								path : 'statisticaldecision'

							},
							isArray : false
						}
					})
				};
			}
	]);

	globalModule.controller('ResultTestPollutantController', [
			'$scope', '$location', '$routeParams', '$modal', 'NotificationService', 'CultureService', 'AuthorizationService', 'SearchTestResultsService', 'SaveResultSet', 'GetVehicleFileDataService', 'VehiclefileStatusService', 'InQuarantineService', 'CalculateDecisionService', 'GeneratePDFService', 'ChangeKeptInStatisSampleService',

			function($scope, $location, $routeParams, $modal, NotificationService, CultureService, authorizationService, SearchTestResultsService, SaveResultSet, GetVehicleFileDataService, VehiclefileStatusService, InQuarantineService, CalculateDecisionService, GeneratePDFService, ChangeKeptInStatisSampleService) {

				/*----------------------------------------Authorization----------------------------------------*/
				$scope.checkAuthorization = function() {
					if ($scope.authorization('POCBRole') || $scope.authorization('POCHRole')) {
						return false;
					}
					return true;
				}
				$scope.disableEdit = false;
				$scope.vehicleFile = $scope.vehicleFileId;
				$scope.selectedResultSet = null;
				$scope.showInquarantain = false;
				$scope.resultSetData = [];
				$scope.vehicleFileData = {};
				$scope.restrictAccess = true;
				$scope.resultSetEdited = false;
				$scope.referenceSantorin = null;
				$scope.decisionResult = null;
				$scope.resultSetListData = null;
				$scope.vehStatisticalDecision = null;
				$scope.statisticalRuleLabel = null;
				$scope.selectedForHighlight = false;
				$scope.readonlyAccess = true;
				$scope.resultSetDataCopy = [];
				var tempList = [];
				$scope.authorization = function(role) {
					return authorizationService.hasRole('seed-w20', role);

				};
				$scope.hasRole = function(seedW20, role) {
					return authorizationService.hasRole(seedW20, role);

				};

				if ($scope.authorization('POCHRole') || $scope.authorization('POCBRole')) {
					$scope.readonlyAccess = false;
					$scope.actionList = [
							CultureService.localize('cop.resulttest.action.menu.envoyer'), CultureService.localize('cop.resulttest.action.menu.quarantaine'), CultureService.localize('cop.resulttest.action.menu.removequarantaine'), CultureService.localize('cop.resulttest.action.menu.statistiques'), CultureService.localize('cop.resulttest.action.menu.countertest'), CultureService.localize('cop.resulttest.action.menu.imprimer')
					];
				} else {
					$scope.actionList = [
							CultureService.localize('cop.resulttest.action.menu.statistiques'), CultureService.localize('cop.resulttest.action.menu.imprimer')
					];
				}
				$scope.obdTest = [
						CultureService.localize('cop.resulttest.obdTest.correct'), CultureService.localize('cop.resulttest.obdTest.notcorrect'), CultureService.localize('cop.resulttest.obdTest.notPerformed')
				];
				$scope.benchCell = [
						'1', '2', '3', '4', '5', '6'
				];

				// ----------Save Test Result
				// STARTS-----------
				$scope.checkSaveCondition = function() {

					if ($scope.selectedResultSet !== null) {
						if ($scope.resultSetEdited) {
							$('#confirmationModal').modal('show');
						} else {
							$scope.calculateResult();
						}
					} else {
						$scope.saveTestResultValues();
					}
				};

				// ----------ENDS-----------
				// -----------Save Result Set Values
				// STARTS-----
				$scope.saveTestResultValues = function() {
					if ($scope.referenceSantorin == null) {
						$scope.errorMsg = CultureService.localize('cop.resulttest.error.msg.referencesantorin');
						$('#errorModal').modal('show');
						return;
					}

					for (var i = 0; i < $scope.resultSetData.length; i++) {
						$scope.resultSetData[i].referenceSantorin = $scope.referenceSantorin;
						for (var j = 0; j < $scope.resultSetData[i].pollutantGasTestResultRepresentationList.length; j++) {
							if ($scope.resultSetData[i].pollutantGasTestResultRepresentationList[j].value != null) {
								$scope.resultSetData[i].pollutantGasTestResultRepresentationList[j].value = $scope.resultSetData[i].pollutantGasTestResultRepresentationList[j].value.toString().replace(/,/g, ".");
							}
						}
						if ($scope.resultSetData[i].resultSetModified) {
							tempList.push($scope.resultSetData[i]);
						}

					}
					if(tempList.length!==0){
						$scope.callResourceToSave();
						}

				}
				$scope.callResourceToSave = function (){
					$scope.resultSetListData = SaveResultSet.saveResultsetResource.saveResultset(tempList, function() {
						NotificationService.notify(CultureService.localize('cop.resulttest.save.success.msg'));
						$scope.resultSetData = $scope.resultSetListData.listResultSet;
						for (var i = 0; i < $scope.resultSetData.length; i++) {
							for (var j = 0; j < $scope.resultSetData[i].pollutantGasTestResultRepresentationList.length; j++) {
								if ($scope.resultSetData[i].pollutantGasTestResultRepresentationList[j].value != null) {
									$scope.resultSetData[i].pollutantGasTestResultRepresentationList[j].value = $scope.resultSetData[i].pollutantGasTestResultRepresentationList[j].value.toString().replace(".", ",");
								}
							}
						}
						$scope.vehicleFileData = $scope.resultSetListData.vehicleFileRepresentation;
						$scope.referenceSantorin = $scope.vehicleFileData.referencesantorin;
						$scope.resultSetEdited = false;
						tempList = [];
						setTimeout(function() {
							for (var k = 0; k < $scope.resultSetData.length; k++) {
								if ($scope.resultSetData[k].keptInStatisticalSample) {
									$scope.selectedForHighlight = true;
									$('.borderBox').removeClass('custom-green-box');
									$('#borderBox' + $scope.resultSetData[k].entityId).addClass('custom-green-box');
									break;
								}
							}
							if ($scope.selectedResultSet != null) {
								if ($scope.selectedForHighlight) {
									$('.borderBox').removeClass('custom-green-box');
									$('#borderBox' + $scope.selectedResultSet.entityId).addClass('custom-green-box');
								}
								document.getElementById('radioBtn' + $scope.selectedResultSet.entityId).checked = true;
							}
						}, 50);
					});
				}
				// --------------------------ENDS---------------
				// --------------Calculate result
				// STARTS--------------
				$scope.calculateResult = function() {

					if ($scope.selectedResultSet.inQuarantine === true) {
						$scope.restrictAccess = true;
						$scope.errorMsg = CultureService.localize('cop.resulttest.modal.calculation.error');
						$('#errorModal').modal('show');

					} else if ($scope.selectedResultSet.pollutantGasTestResultRepresentationList[0].statisticalDecision != null) {
						$scope.errorMsg = CultureService.localize('cop.resultset.calcualtedresult.error.msg');
						$('#errorModal').modal('show');
					} else {
						var objTosend = [];
						for (var i = 0; i < $scope.resultSetData.length; i++) {
							if ($scope.resultSetData[i].entityId === $scope.selectedResultSet.entityId) {
								$scope.resultSetData[i].keptInStatisticalSample = true;
								objTosend.push($scope.resultSetData[i]);
							} else if ($scope.resultSetData[i].entityId !== null) {
								$scope.resultSetData[i].keptInStatisticalSample = false;
								objTosend.push($scope.resultSetData[i]);
							}
						}
						$scope.decisionResult = CalculateDecisionService.calculateDecisionResource.calculateResult(objTosend, function() {
							$scope.selectedResultSet = $scope.decisionResult;
							$scope.vehStatisticalDecision = $scope.decisionResult.vehFileStatisticalDecision;
							$scope.statisticalRuleLabel = $scope.decisionResult.statisticalcalcRuleLabel;
							NotificationService.notify(CultureService.localize('cop.resulttest.save.success.msg'));
							$scope.resultSetEdited = false;
							$scope.getDataForVehicle();
						}, function(error) {
							if (error.status === 406) {
								$scope.errorMsg = CultureService.localize('cop.resultset.nostatisticalrule.error.msg');
								$('#errorModal').modal('show');
							} else {
								$scope.errorMsg = CultureService.localize('cop.resultset.calculation.error.msg');
								$('#errorModal').modal('show');
							}

						});
					}
					$scope.updatequarantineAttr($scope.selectedResultSet);
				}

				// -------------------Calculate result
				// ENDS----------
				$scope.getDataForVehicle = function() {
					$scope.resultSetListData = SearchTestResultsService.SearchTestResultsResource.searchTestResult({
						vehicleFileId : $scope.vehicleFile
					}, function() {
						$scope.resultSetData = $scope.resultSetListData.listResultSet;
						$scope.resultSetDataCopy = $scope.resultSetData;
						 if( $scope.resultSetData!==undefined && $scope.resultSetData!==null){
						for (var i = 0; i < $scope.resultSetData.length; i++) {
							for (var j = 0; j < $scope.resultSetData[i].pollutantGasTestResultRepresentationList.length; j++) {
								if ($scope.resultSetData[i].pollutantGasTestResultRepresentationList[j].value !== null) {
									$scope.resultSetData[i].pollutantGasTestResultRepresentationList[j].value = $scope.resultSetData[i].pollutantGasTestResultRepresentationList[j].value.toString().replace(".", ",");
								}
							}
						}
						 
						$scope.vehicleFileData = $scope.resultSetListData.vehicleFileRepresentation;
						$scope.vehStatisticalDecision = $scope.vehicleFileData.vehFileStatisticalDecision;
						$scope.statisticalRuleLabel = $scope.resultSetData[0].statisticalcalcRuleLabel;
						$scope.referenceSantorin = $scope.vehicleFileData.referencesantorin;
						tempList = [];
						 }
						setTimeout(function() {
						   if($scope.resultSetData!==undefined && $scope.resultSetData!==null){
							for (var k = 0; k < $scope.resultSetData.length; k++) {
							    
								if ($scope.resultSetData[k].keptInStatisticalSample) {
									$scope.selectedForHighlight = true;
									$('.borderBox').removeClass('custom-green-box');
									$('#borderBox' + $scope.resultSetData[k].entityId).addClass('custom-green-box');
									break;
								}
							}
						}

							if ($scope.selectedResultSet != null) {
								if ($scope.selectedForHighlight) {
									$('#borderBox' + $scope.selectedResultSet.entityId).addClass('custom-green-box');
								}
								document.getElementById('radioBtn' + $scope.selectedResultSet.entityId).checked = true;

							}
						}, 10);
					});
				};
				$scope.getDataForVehicle();

				$scope.updatequarantineAttr = function(resultSetData) {
					$scope.selectedResultSet = resultSetData;
					if ($scope.selectedResultSet !== null) {

						$scope.restrictAccess = false;
						if ($scope.selectedResultSet.inQuarantine === true) {
							$scope.restrictAccess = true;
							$scope.showInquarantain = true;
						} else {
							$scope.restrictAccess = false;
							$scope.showInquarantain = false;
						}
					}
				}
				$scope.resultInGreen = function() {
					$scope.selectedForHighlight = true;
					$('.borderBox').removeClass('custom-green-box');
					$('#borderBox' + $scope.selectedResultSet.entityId).addClass('custom-green-box');
				}

				$scope.onActionChange = function(selectedAction) {

					if (selectedAction === CultureService.localize('cop.resulttest.action.menu.statistiques')) {
					    
					     $location.url("/cop/StatisticaleSample?tvvLabel="+$scope.vehicleFileData.tvvLabel);
					}
					if (selectedAction === CultureService.localize('cop.resulttest.action.menu.countertest')) {
						$scope.vehicleFileData = VehiclefileStatusService.VehiclefileStatusResource.changeVehiclefileStatus({
							vehicleFileId : $scope.vehicleFile
						}, function() {
						});
					}
					if (selectedAction === CultureService.localize('cop.resulttest.action.menu.quarantaine')) {
						if ($scope.selectedResultSet !== null) {
							$scope.selectedResultSet.inQuarantine = true;
							InQuarantineService.InQuarantineResource.changeInQuarantineVal($scope.selectedResultSet, function() {
							});

						} else {
							$scope.errorMsg = CultureService.localize('cop.resulttest.error.msg');
							$('#errorModal').modal('show');
						}
					}
					if (selectedAction === CultureService.localize('cop.resulttest.action.menu.removequarantaine')) {
						if ($scope.selectedResultSet !== null) {
							$scope.selectedResultSet.inQuarantine = false;
							InQuarantineService.InQuarantineResource.changeInQuarantineVal($scope.selectedResultSet, function() {
							});
						} else {
							$scope.errorMsg = CultureService.localize('cop.resulttest.error.msg');
							$('#errorModal').modal('show');
						}
					}
					if (selectedAction === CultureService.localize('cop.resulttest.action.menu.imprimer')) {
						if ($scope.selectedResultSet !== null) {
							GeneratePDFService.GeneratePDFResource.generatePdfFile($scope.selectedResultSet, function() {
								window.open("./resulttestpollutant/resultsetpdfExport", "_self");
							});
						} else {
							$scope.errorMsg = CultureService.localize('cop.resulttest.error.msg');
							$('#errorModal').modal('show');
						}

					}
					$scope.updatequarantineAttr($scope.selectedResultSet);
				}
				$scope.refreshConfirmation = function() {
					if ($scope.resultSetEdited) {
						$('#unsavedChangesPopup').modal('show');
					} else {
						$scope.refreshData();
					}
				}
				$scope.refreshData = function() {
					$scope.selectedResultSet = null;
					$scope.getDataForVehicle();
				}
				$scope.dropdownValChanged = function(indexOfResultSet) {
					for (var i = 0; i < $scope.resultSetData.length; i++) {
						if (i === indexOfResultSet) {
							$scope.resultSetData[i].resultSetModified = true;
						}
					}
					$scope.resultSetEdited = true;
				}
				$scope.dirtyCheck = function(value, indexOfResultSet) {
					if (value) {
						var INTEGER_REG = new RegExp("^[-]?[0-9/,.]+$");
						if (!INTEGER_REG.test(value)) {
							value = value.slice(0, -1);
						}
						$scope.resultSetEdited = true;
						for (var i = 0; i < $scope.resultSetData.length; i++) {
							if (i === indexOfResultSet) {
								$scope.resultSetData[i].resultSetModified = true;
							}
						}

						return value;
					}
				}

				$scope.editable = true;
				$scope.edit = function() {
					this.editable = false;
					setTimeout(function() {
						$(".editableInput").focus();
					}, 400);
				}

				$scope.save = function() {
					this.editable = true;
				}

				$scope.enterSave = function() {
					this.editable = true;
				};

				$scope.colourClass = function(decisionVal) {

					if (decisionVal != null) {
						if (decisionVal.indexOf('MD') !== -1) {
							return 'color-grey';
						}
						if (decisionVal.indexOf('R') !== -1) {
							return 'color-red';
						}
						if (decisionVal.indexOf('I') !== -1) {
							return 'color-orange';
						}
						if (decisionVal.indexOf('A') !== -1) {
							return 'color-green';
						}
					}

				};

			}
	]);

	globalModule.directive('ngBlur', function() {
		return function(scope, elem, attrs) {
			elem.bind('blur', function() {
				scope.$apply(attrs.ngBlur);
			});
		};
	});

	return {
		angularModules : [
			'resulttestpollutant'
		]
	};
});