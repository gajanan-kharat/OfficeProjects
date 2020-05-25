/*
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {
	'use strict';

	var editTechnicalData = angular.module('editTechnicalData', [
			'ui.bootstrap', 'copCommon'
	]), config = module && module.config() || {};

	editTechnicalData.factory('TvvValuedTDLService', [
			'$resource',

			function($resource) {
				return {
					ValuedTDLResource : $resource('tvvValuedTDL/:path/:tvvId', {
						tvvId : '@tvvId'
					}, {

						'getAllTDL' : {
							method : 'GET',
							params : {
								path : 'allValuedTDL'
							},
							isArray : true
						}

					})
				};
			}
	]);
	editTechnicalData.factory('TvvValuedEsDepTDLService', [
			'$resource',

			function($resource) {
				return {
					ValuedEsTDLResource : $resource('tvvValuedEsDepTDL/:path/:tvvId/:emsId', {
						tvvId : '@tvvId',
						emsId : '@emsId'
					}, {

						'getAllEsDepTDL' : {
							method : 'GET',
							params : {
								path : 'allEsDepTDL'
							},
							isArray : true
						}

					})
				};
			}
	]);
	editTechnicalData.factory('SaveTvvService', [
			'$resource',

			function($resource) {
				return {
					TvvResource : $resource('tvvResource/:path/', {
						path : '@path'

					}, {

						'updateTVV' : {
							method : 'POST',
							params : {
								path : 'Tvv'
							},
							isArray : false
						},
						'createNewVersion' : {
							method : 'POST',
							params : {
								path : 'tvvVersion'
							},
							isArray : false
						},
						'deleteTVV' : {
							method : 'POST',
							params : {
								path : 'delete'
							},
							isArray : false
						},
						'changeTvvStatus' : {
							method : 'POST',
							params : {
								path : 'tvvStatus'
							},
							isArray : false
						}

					})
				};
			}
	]);
	editTechnicalData.factory('TvvValuedTCLService', [
			'$resource',

			function($resource) {
				return {
					ValuedTDLResource : $resource('tvvValuedTCL/:path/:tvvId', {
						path : '@path',
						tvvId : '@tvvId',

					}, {

						'getAllTCL' : {
							method : 'GET',
							params : {
								path : 'allValuedTCL'
							},
							isArray : true
						}

					})
				};
			}
	]);
	editTechnicalData.factory('TvvValuedEsDepTCLService', [
			'$resource',

			function($resource) {
				return {
					ValuedEsTCLResource : $resource('tvvValuedEsDepTCL/:path/:tvvId/:emsId', {
						path : '@path',
						tvvId : '@tvvId',
						emsId : '@emsId'
					}, {

						'getAllEsDepTCL' : {
							method : 'GET',
							params : {
								path : 'allEsDepTCL'
							},
							isArray : true
						}

					})
				};
			}
	]);

	editTechnicalData.factory('TvvValuedEsDepPGLService', [
			'$resource',

			function($resource) {
				return {
					ValuedEsPGLResource : $resource('tvvValuedEsDepPGL/:path/:tvvId/:emsId', {
						tvvId : '@tvvId',
						emsId : '@emsId'
					}, {

						'getAllEsDepPGL' : {
							method : 'GET',
							params : {
								path : 'allEsDepPGL'
							},
							isArray : true
						}

					})
				};
			}
	]);
	/**
	 * Controller for edit TVV Screens
	 */
	editTechnicalData.factory('TvvCoastDownService', [
			'$resource',

			function($resource) {
				return {
					CoastDownResource : $resource('CoastdownReference/:path/:inertia', {
						path : '@path',
						inertia : '@inertia'

					}, {

						'getAllCoastDownData' : {
							method : 'GET',
							params : {
								path : 'coastdownValue'
							},
							isArray : true
						}

					})
				};
			}
	]);

	editTechnicalData.factory('FinalReductionRatioService', [
			'$resource',

			function($resource) {
				return {
					FinalReductionRatioResource : $resource('FinalReductionReference/:path/:tvvLabel', {
						path : '@path',
						tvvLabel : '@tvvLabel'

					}, {
						'getAllFinalReductionData' : {
							method : 'GET',
							params : {
								path : 'FinalReductionRatio'

							},
							isArray : true
						}
					})
				};
			}
	]);

	editTechnicalData.factory('VehicleFileService', [
			'$resource', function($resource) {
				return {
					VehicleFileResource : $resource('vehiclefile/:path/:vehicleFileId', {
						path : '@path',
						vehicleFileId : '@vehicleFileId'

					}, {
						'getTVV' : {
							method : 'GET',
							params : {
								path : 'tvv'

							},
							isArray : false
						}
					})
				};
			}
	]);
	editTechnicalData.factory('TvvDropDownService', [
			'$resource',

			function($resource) {
				return {
					TvvResource : $resource('tvvResource/:path/:tvvLabel', {
						path : '@path',
						tvvLabel : '@tvvLabel'

					}, {

						'getGnomeDataForTVV' : {
							method : 'GET',
							params : {
								path : 'tvvGnomeData'
							},
							isArray : false
						}

					})
				};
			}
	]);

	editTechnicalData.factory('TvvFactorCoeffService', [
			'$resource',

			function($resource) {
				return {
					TvvFactorCoeffResource : $resource('tvvResource/:path/:tvvId', {
						path : '@path',
						tvvId : '@tvvId'

					}, {

						'getTvvFactorCoeffList' : {
							method : 'GET',
							params : {
								path : 'tvvvaluedesdepfactor'
							},
							isArray : true
						}

					})
				};
			}
	]);

	editTechnicalData.factory('TvvSamplingRuleService', [
			'$resource',

			function($resource) {
				return {
					TvvSamplingRuleResource : $resource('tvvResource/:path/:tvvId', {
						path : '@path',
						tvvId : '@tvvId'

					}, {

						'getTvvSamplingRuleList' : {
							method : 'GET',
							params : {
								path : 'tvvsamplingrule'
							},
							isArray : false
						}

					})
				};
			}
	]);
	editTechnicalData.factory('SaveTvvFactorCoeffService', [
			'$resource',

			function($resource) {
				return {
					SaveTvvFactorCoeffResource : $resource('tvvResource/:path', {
						path : '@path'

					}, {

						'saveTvvFactorCoeffList' : {
							method : 'POST',
							params : {
								path : 'tvvfactorcoeff'
							},
							isArray : false
						}

					})
				};
			}
	]);

	editTechnicalData.factory('SaveTvvSamplingRuleService', [
			'$resource',

			function($resource) {
				return {
					SaveTvvSamplingRuleResource : $resource('tvvResource/:path/:tvvId/:sampleLabel', {
						path : '@path',
						tvvId : '@tvvId',
						sampleLabel : '@sampleLabel'

					}, {

						'saveTvvSamplingRule' : {
							method : 'GET',
							params : {
								path : 'tvvsamplingrulelabel'
							},
							isArray : false
						}

					})
				};
			}
	]);

	editTechnicalData.controller('editController', [

			'$scope', '$modal', '$http', '$location', 'NotificationService', 'CultureService', 'ProjectFamilyService', 'BodyWorkService', 'EngineService', 'GearBoxService', 'FuelCommonService', 'ESCommonService', 'CountryService', 'AreaService', 'CarBrandService', 'TvvValuedTDLService', 'ManageTvvService', 'TvvValuedEsDepTDLService', 'TvvService', 'SaveTvvService', 'TvvValuedTCLService', 'TvvValuedEsDepTCLService', 'TvvValuedEsDepPGLService', 'TvvCoastDownService', 'TVVUtilService', 'FinalReductionRatioService', 'StatusCommonService', 'HistoryService', 'AuthorizationService', 'CategoryCommonService', 'VTService', 'FactoryCommonService', 'TVVService', 'FuelInjectionTypeService', 'VehicleFileService', 'TvvDropDownService', 'PaysService', 'TvvFactorCoeffService', 'SaveTvvFactorCoeffService', 'TvvSamplingRuleService', 'SaveTvvSamplingRuleService', function($scope, $modal, $http, $location, NotificationService, CultureService, ProjectFamilyService, BodyWorkService, EngineService, GearBoxService, FuelCommonService, ESCommonService, CountryService, AreaService, CarBrandService, TvvValuedTDLService, ManageTvvService, TvvValuedEsDepTDLService, TvvService, SaveTvvService, TvvValuedTCLService, TvvValuedEsDepTCLService, TvvValuedEsDepPGLService, TvvCoastDownService, TVVUtilService, FinalReductionRatioService, StatusCommonService, HistoryService, authorizationService, CategoryCommonService, VTService, FactoryCommonService, TVVService, FuelInjectionTypeService, VehicleFileService, TvvDropDownService, PaysService, TvvFactorCoeffService, SaveTvvFactorCoeffService, TvvSamplingRuleService, SaveTvvSamplingRuleService) {

				/*----------------------------------------Authorization----------------------------------------*/
				var authentication = false;

				if (authorizationService.hasRole('seed-w20', 'POCHRole')) {
					authentication = true;
				}
				$scope.authorization = function(role) {
					return authorizationService.hasRole('seed-w20', role);
				};
				$scope.searchRepresentation = {};
				$scope.selectedCarBrand = {};
				$scope.selectedCarBrand.selectedList = [];
				$scope.selectedCarFactory = {};
				$scope.selectedCarFactory.selectedList = [];
				$scope.selectedCountry = {};
				$scope.selectedCountry.selectedList = [];
				$scope.savedTvv = null;
				$scope.savedTDLists = null;
				$scope.finalRuductionValue = 1;
				$scope.tvvValuedFactorCoeffList = [];
				$scope.actions = [
						{
							'label' : CultureService.localize("cop.tvv.label.copy"),
							'value' : 'copy'
						}, {
							'label' : CultureService.localize("cop.tvv.label.create"),
							'value' : 'createNewTvv'
						}, {
							'label' : CultureService.localize("cop.tvv.label.version"),
							'value' : 'version'
						}, {
							'label' : CultureService.localize("cop.tvv.label.changeStatus"),
							'value' : 'changeStatus'
						}, {
							'label' : CultureService.localize("cop.tvv.label.delete"),
							'value' : 'delete'
						}, {
							'label' : CultureService.localize("cop.tvv.label.history"),
							'value' : 'history'
						},
				];

				// For fetching tvv using vehicle id from service if $scope.vehicleFileId having a value
				$scope.loadTvvId = function() {
					VehicleFileService.VehicleFileResource.getTVV({
						vehicleFileId : $scope.vehicleFileId

					}, function(response) {
						$scope.tvvObject = response;

						$scope.savedTvv = response;
						// changes Made for cancel button
						$scope.tvvObjectCopy = angular.copy($scope.tvvObject);
						if ($scope.tvvObject.valuedCoastDown !== null && $scope.tvvObject.valuedCoastDown.inertia_value !== undefined)
							$scope.coastDownLabel = $scope.tvvObject.valuedCoastDown.inertia_value + " " + $scope.tvvObject.valuedCoastDown.pSAReference + $scope.tvvObject.valuedCoastDown.version;
						$scope.selectedCarBrand.selectedList = $scope.tvvObject.carBrandList;
						$scope.selectedCarFactory.selectedList = $scope.tvvObject.carFactoryList;
						$scope.selectedCountry.selectedList = $scope.tvvObject.countryList;
						// Set DATE on Screen
						var temp = new Date($scope.tvvObject.modificationDate);
						var displayDate = temp.getDate() + "/" + (temp.getMonth() + 1) + "/" + temp.getFullYear();
						$scope.modificationDate = displayDate;
						$scope.initializeData();
						if ($scope.tvvObject.finalReductionRatio !== null) {
							$scope.filnalReductionselected = 1;
						} else {
							$scope.filnalReductionselected = 0;
						}

					}, function() {
					});
				};

				$scope.intializeTVVForVehicleFile = function() {
					if ($scope.vehicleFileId !== null) {
						$scope.loadTvvId();

					}
				}
				$scope.intializeTVVForVehicleFile();

				$scope.disableFeild = function(role) {
					if ($scope.vehicleFileId === undefined || $scope.vehicleFileId === null) {
						return authorizationService.hasRole('seed-w20', role);
					} else {
						return false;
					}
				}

				$scope.disableFeild();

				/** ******************************************ACTIONS ON TVV********************************************************************* */

				$scope.takeAction = function(selectedAction) {
					$scope.selectedAction = selectedAction;
					if ($scope.selectedAction === "copy") {
						$scope.showCopyModal();
					} else if ($scope.selectedAction === "delete") {
						$('#deleteConfirmationModal').modal('show');
					} else if ($scope.selectedAction === "version") {

						$('#tvvVersionModal').modal('show');

					} else if ($scope.selectedAction === "createNewTvv") {

						var url = "/cop/CreateTvv";
						$location.url(url);

					} else if ($scope.selectedAction === "changeStatus") {

						$('#StatusChangeModal').modal('show');

					} else if ($scope.selectedAction === "history") {

						$scope.getAllHistory("TVV", $scope.tvvObject.entityId);

					}

					$scope.selectedAction = false;
				};
				/*
				 * $scope.addToCBList = function(selectedItem) {
				 * 
				 * $scope.selectedCarBrand.selectedList.push(selectedItem); $scope.tvvObject.carBrandList.push(selectedItem); } $scope.addToCarFactoryList = function(selectedItem) { alert(selectedItem + "--selectedItem");
				 * $scope.selectedCarFactory.selectedList.push(selectedItem); $scope.tvvObject.carFactoryList.push(selectedItem); }
				 */

				$scope.showCopyModal = function() {
					$('#copyTVVModal').modal('show');
				};

				/**
				 * This method checks if the entered TVV label is already available for some other TVV or not.
				 */
				$scope.checkTvvLabel = function() {
					if ($scope.tvvCopyLabel === null || $scope.tvvCopyLabel.length === 0) {
						document.getElementById("tvvCopyLabel").style.borderColor = "red";

					} else {
						var tvvObj = {
							'tvvLabel' : $scope.tvvCopyLabel
						};
						TVVUtilService.TvvResource.getTVVsWithLabel(tvvObj, function(response) {
							if (response === null || response.length === 0)
								$scope.copyTVV();
							else {
								$('#duplicateLabelModal').modal('show');

							}
						}, function() {
							$scope.errorMessage = CultureService.localize('cop.tvv.message.creationError');
							$('#errorDisplayModal').modal('show');

						});
					}
				};

				$scope.copyTVV = function() {
					var obj = {
						'entityId' : $scope.tvvObject.entityId,
						'label' : $scope.tvvCopyLabel
					};
					TVVService.TvvResource.copyTvv(obj, function(response) {
						$scope.tvvObject = response;
						// changes Made for cancel button
						$scope.tvvObjectCopy = angular.copy($scope.tvvObject);
						NotificationService.notify(CultureService.localize('cop.tvv.message.copySuccess'));

					}, function() {
						$scope.errorMessage = CultureService.localize('cop.tvv.message.copyError');
						$('#errorDisplayModal').modal('show');
					});

				};
				$scope.createNewVersion = function() {
					SaveTvvService.TvvResource.createNewVersion($scope.tvvObject, function(response) {
						$scope.tvvObject = response;
						// changes Made for cancel button
						$scope.tvvObjectCopy = angular.copy($scope.tvvObject);
						NotificationService.notify(CultureService.localize('cop.tvv.message.versionSuccess'));

					}, function() {
						$scope.errorMessage = CultureService.localize('cop.tvv.message.versionError');
						$('#errorDisplayModal').modal('show');

					});

				};
				$scope.deleteTVV = function() {
					SaveTvvService.TvvResource.deleteTVV($scope.tvvObject, function() {

						NotificationService.notify(CultureService.localize('cop.tvv.message.deleteSuccess'));
						var url = "/cop/SearchMain";
						$location.url(url);
					}, function(error) {
						if (error.status = 412) {
							$scope.errorMessage = CultureService.localize('cop.tvv.message.validDelete');
							$('#errorDisplayModal').modal('show');
						} else {
							$scope.errorMessage = CultureService.localize('cop.tvv.message.deleteError');
							$('#errorDisplayModal').modal('show');
						}

					});

				};
				$scope.changeTvvStatus = function() {
					SaveTvvService.TvvResource.changeTvvStatus($scope.tvvObject, function(success) {
						if (success.validationStatus === undefined || success.validationStatus == null) {
							$scope.tvvObject = success;
							// changes Made for cancel button
							$scope.tvvObjectCopy = angular.copy($scope.tvvObject);
							NotificationService.notify(CultureService.localize('cop.tvv.message.validSuccess'));
						} else {
							$scope.errorMessageArray = success.validationResponseList;
							$scope.errorMessage = CultureService.localize('cop.tvv.message.statusValidation');
							$('#mandatoryValidationErrorModal').modal('show');
						}
					}, function() {
						$scope.errorMessage = CultureService.localize('cop.tvv.message.validError');
						$('#errorDisplayModal').modal('show');
					});
				}

				// -------------------------------------------INITIALIAZTION OF HEADER (ALL DROPDOWNS)----------------------------------------
				/* to get ProjectFamily Data Data */

				$scope.getStatusNatureData = function() {

					/* Function for loading Status data STARTS */
					StatusCommonService.StatusResource.statusTestNatures(

					function(response) {
						$scope.statusList = response;

						for (var i = 0; i < $scope.statusList.length; i++)
							$scope.statusList[i].displayLabel = $scope.statusList[i].guiLabel + "-" + $scope.statusList[i].testNatureLabel;

					}, function() {
						NotificationService.notify(CultureService.localize("cop.settings.status.load.errorMessage"));
					}

					);
					/* Function for loading Status data ENDS */
				};
				$scope.getStatusNatureData();

				$scope.Fuel = [];
				$scope.ProjectFamily = [];
				$scope.Engine = [];
				$scope.GearBox = [];
				$scope.BodyWork = [];
				$scope.carBrandList = [];
				$scope.EmissionStandard = [];
				$scope.categoryData = [];
				$scope.vehicleTechData = [];
				$scope.factoryList = [];
				$scope.fuelinjectionList = [];
				$scope.Area = [];
				$scope.InitilaizeDropDown = function() {

					// if ($scope.tvvObject.label == undefined || $scope.tvvObject.label == null || $scope.tvvObject.label.length < 6) {
					// $scope.errorDisplayMessage = CultureService.localize('cop.tvv.message.tvvLabelError');
					// $('#TvvErrorModal').modal('show');
					// // $scope.selectedOption = null;
					// return;
					// }

					TvvDropDownService.TvvResource.getGnomeDataForTVV({
						'tvvLabel' : $scope.tvvObject.label
					}, function(response) {

						$scope.tvvDataRepresentation = response;
						$scope.Fuel = $scope.tvvDataRepresentation.fuelList;
						$scope.ProjectFamily = $scope.tvvDataRepresentation.pcFamilyList;
						$scope.Engine = $scope.tvvDataRepresentation.engineList;
						$scope.GearBox = $scope.tvvDataRepresentation.gearBoxList;
						$scope.BodyWork = $scope.tvvDataRepresentation.bodyWorklist;
						$scope.carBrandList = $scope.tvvDataRepresentation.carBrandList;
						$scope.EmissionStandard = $scope.tvvDataRepresentation.emissionStandardList;
						$scope.categoryData = $scope.tvvDataRepresentation.categoryList;
						$scope.vehicleTechData = $scope.tvvDataRepresentation.vtList;
						// $scope.factoryList = $scope.tvvDataRepresentation.factoryList;
						$scope.fuelinjectionList = $scope.tvvDataRepresentation.fuelInjectionList;
					}, function(error) {
						if (error.status == 412) {
							$scope.errorDisplayMessage = CultureService.localize('cop.tvv.message.noGenomeData');
						}
						$scope.errorDisplayMessage = CultureService.localize('cop.tvv.message.noGenomeData');
						$('#TvvErrorModal').modal('show');
					});

				}
				/* To Do this needs to be removed after testing */
				// $scope.ProjectFamily = [];
				// $scope.getAllProjectFamilyData = function() {
				//
				// var ProjectFamilyResponce = null;
				// ProjectFamilyResponce = ProjectFamilyService.ProjectFamilyResource.getAllProjectFamilies(function() {
				// $scope.ProjectFamily = ProjectFamilyResponce;
				//
				// }, function() {
				// NotificationService.notify(CultureService.localize('cop.tvv.message.initializeErrorr'));
				// });
				// };
				//
				// $scope.getAllProjectFamilyData();
				//
				// /* to get BodyWork Data Data */
				// $scope.BodyWork = [];
				// $scope.getAllBodyWorkData = function() {
				//
				// var BodyWorkResponce = null;
				// BodyWorkResponce = BodyWorkService.BodyWorkResource.getAllBodyWorkData(function() {
				// $scope.BodyWork = BodyWorkResponce;
				//
				// }, function() {
				// NotificationService.notify(CultureService.localize('cop.tvv.message.initializeErrorr'));
				// });
				// };
				//
				// $scope.getAllBodyWorkData();
				//
				// /* to get Engine Data Data */
				// $scope.Engine = [];
				// $scope.getAllEngineData = function() {
				//
				// var EngineResponce = null;
				// EngineResponce = EngineService.EngineResource.engineData(function() {
				// $scope.Engine = EngineResponce;
				//
				// }, function() {
				// NotificationService.notify(CultureService.localize('cop.tvv.message.initializeErrorr'));
				// });
				// };
				// $scope.getAllEngineData();
				//
				// /* to get GearBox Data Data */
				// $scope.GearBox = [];
				// $scope.getAllGearBoxData = function() {
				//
				// var GearBoxResponce = null;
				// GearBoxResponce = GearBoxService.GearBoxResource.getAllGearBoxData(function() {
				// $scope.GearBox = GearBoxResponce;
				//
				// }, function() {
				// NotificationService.notify(CultureService.localize('cop.tvv.message.initializeErrorr'));
				// });
				// };
				// $scope.getAllGearBoxData();
				// $scope.getAllCarBrandData = function() {
				// CarBrandService.CarBrandResource.getAllCarBrandData(function(response) {
				// $scope.carBrandList = response;
				//
				// },
				//
				// function(error) {
				// NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
				// });
				// };
				// $scope.getAllCarBrandData();
				//
				// /* to get Fuel Data */
				// $scope.Fuel = [];
				// $scope.getAllFuelData = function() {
				//
				// var FuelResponce = null;
				// FuelResponce = FuelCommonService.FuelResource.getAllFuelData(function() {
				// $scope.Fuel = FuelResponce;
				//
				// }, function() {
				// NotificationService.notify(CultureService.localize('cop.tvv.message.initializeErrorr'));
				// });
				// };
				//
				// $scope.getAllFuelData();
				//
				// /* to get EmissionStandard Data */
				// $scope.EmissionStandard = [];
				// $scope.getAllEmissionStandardData = function() {
				//
				// ESCommonService.ESResource.getValidEmissionStandards(function(response) {
				// $scope.EmissionStandard = response;
				//
				// },
				//
				// function() {
				// NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
				// });
				//
				// };
				// $scope.getAllEmissionStandardData();
				/* to get Country Data */
				/*
				 * $scope.Country = []; $scope.getAllCountryData = function() {
				 * 
				 * var CountryResponce = null; CountryResponce = CountryService.CountryResource.getAllCountryData(function() { $scope.Country = CountryResponce; }, function() {
				 * 
				 * }); }; $scope.getAllCountryData();
				 */

				/* to get Area Data */

				/* to get category */
				// $scope.categoryData = [];
				// $scope.getAllCategoryData = function() {
				// var categoryResponce = null;
				// categoryResponce = CategoryCommonService.CategoryResource.getAllCategoriesForTvv(function() {
				//
				// $scope.categoryData = categoryResponce;
				//
				// }, function() {
				//
				// });
				// };
				// $scope.getAllCategoryData();
				//
				// $scope.vehicleTechData = [];
				// $scope.getAllVehicleTechData = function() {
				// var vehicalTechResponse = null;
				// vehicalTechResponse = VTService.VehicleTechnologiesResource.getAllVehicleTechnologies(function() {
				//
				// $scope.vehicleTechData = vehicalTechResponse;
				// }, function() {
				//
				// });
				// };
				// $scope.getAllVehicleTechData();
				//
				// $scope.getAllFuelInjectionType = function() {
				//
				// FuelInjectionTypeService.FuelInjectionTypeResource.getFuelInjectionType(function(response) {
				// $scope.fuelinjectionList = response;
				//
				// }, function() {
				// NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
				// });
				// }
				// $scope.getAllFuelInjectionType();
				$scope.getspecificCopList = function() {
					/** * Load Factory Data ** */

					FactoryCommonService.FactoryResource.getFactories(function(response) {
						$scope.factoryList = response;
					}, function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});

					/** * Load country Data ** */
					PaysService.PaysResource.getAllPaysData(function(response) {
						$scope.countryDataList = response;

					}, function() {

					});
					/** * Load Area Data ** */
					var AreaResponce = null;
					AreaResponce = AreaService.AreaResource.getAllAreaData(function() {
						$scope.Area = AreaResponce;

					}, function() {

					});
				}
				$scope.getspecificCopList();

				/**
				 * ----------------------------------INTIALIZATION DONE---------------------------------------------------------------------------------------
				 */

				/**
				 * This method is used for validation user entered value against Data Type of Technical DAta or Test Condition
				 */
				$scope.ValidateData = function(entity) {
					var INTEGER_REG = new RegExp("^[-]?[0-9]+$");
					var FLOAT_REG = new RegExp("^[-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?");
					if (entity.dataType != null) {
						if (entity.dataType.toUpperCase() === "INTEGER") {
							var value = entity.defaultValue;
							var flag = INTEGER_REG.test(value);
							if (!flag)
								return (CultureService.localize('cop.tvv.message.numRequired'));
							else
								return null;

						} else if (entity.dataType.toUpperCase() === "FLOAT") {
							var flag1 = FLOAT_REG.test(entity.defaultValue);
							if (!flag1)
								return (CultureService.localize('cop.tvv.message.floatRequired'));
							else
								return null;
						} else if (entity.dataType.toUpperCase() === "BOOLEAN" && entity.defaultValue !== null) {
							if (entity.defaultValue.toUpperCase() !== "YES" && entity.defaultValue.toUpperCase() !== "NO" && entity.defaultValue.toUpperCase() !== "OUI" && entity.defaultValue.toUpperCase() !== "NON")
								return (CultureService.localize('cop.tvv.message.boolRequired'));
							else
								return null;
						} else

							return null;

					}
				}

				$scope.callValidate = function(listData, index) {

				}
				// ** ---------------------------------------------------------------------------------------------------------------------------------*/

				$scope.tvvObject = ManageTvvService.savedTvv;

				$scope.technicalLists = [];
				/**
				 * This method fetches TVV data initially to display on screen
				 */
				$scope.getTvvData = function() {
					if ($scope.tvvObject === undefined || $scope.tvvObject === null) {
						if ($scope.vehicleFileId === undefined || $scope.vehicleFileId === null) {
							var url = "/cop/SearchMain";
							$location.url(url);
						}

					} else {
						$scope.tvvObject.carFactoryList = [];
						TVVUtilService.TvvResource.getAllTvvData({
							'entityId' : $scope.tvvObject.entityId

						}, function(success) {
							$scope.tvvObject = success;
							$scope.savedTvv = success;
							$scope.InitilaizeDropDown();
							// changes Made for cancel button
							$scope.tvvObjectCopy = angular.copy($scope.tvvObject);
							if ($scope.tvvObject.valuedCoastDown !== null && $scope.tvvObject.valuedCoastDown.inertia_value !== undefined)
								$scope.coastDownLabel = $scope.tvvObject.valuedCoastDown.inertia_value + " " + $scope.tvvObject.valuedCoastDown.pSAReference + $scope.tvvObject.valuedCoastDown.version;
							$scope.selectedCarBrand.selectedList = $scope.tvvObject.carBrandList;
							$scope.selectedCarFactory.selectedList = $scope.tvvObject.carFactoryList;
							$scope.selectedCountry.selectedList = $scope.tvvObject.countryList;
							// Set DATE on Screen
							var temp = new Date($scope.tvvObject.modificationDate);
							var displayDate = temp.getDate() + "/" + (temp.getMonth() + 1) + "/" + temp.getFullYear();
							$scope.modificationDate = displayDate;
							$scope.initializeData();
							if ($scope.tvvObject.finalReductionRatio !== null) {
								$scope.filnalReductionselected = 1;
							} else {
								$scope.filnalReductionselected = 0;
							}
						}, function() {

							$scope.errorMessage = CultureService.localize('cop.tvv.message.loadError');
							$('#errorDisplayModal').modal('show');
						});
					}
				};

				// This method fetches Technical Data Lists
				$scope.initializeData = function() {

					var temp = new Date($scope.tvvObject.modificationDate);
					var singleMonth = "";
					var singleDay = "";
					var month = temp.getMonth() + 1;
					var day = temp.getDate();
					if (month.toString().length < 2)
						singleMonth = 0;
					if (day.toString().length < 2)
						singleDay = 0;

					$scope.displayDate = singleDay + "" + (temp.getDate()) + "/" + singleMonth + (temp.getMonth() + 1) + "/" + temp.getFullYear();

					if ($scope.tvvObject.emissionStandard != null) {
						$scope.technicalLists = [];
						TvvValuedTDLService.ValuedTDLResource.getAllTDL({
							'tvvId' : $scope.tvvObject.entityId
						}, function(success) {
							var tvvValuedTDLList = success;
							for (var i = 0; i < tvvValuedTDLList.length; i++) {
								$scope.editDefaultValue(tvvValuedTDLList[i]);
								$scope.technicalLists.push(tvvValuedTDLList[i]);

							}
							// changes made for cancel button
							$scope.technicalListsCopy = angular.copy($scope.technicalLists);
						}, function() {
						});
						TvvValuedEsDepTDLService.ValuedEsTDLResource.getAllEsDepTDL({
							'tvvId' : $scope.tvvObject.entityId,
							'emsId' : $scope.tvvObject.emissionStandard.entityId
						}, function(success) {
							var tvvValuedEsDepTDLList = success;
							if (tvvValuedEsDepTDLList != null) {
								for (var i = 0; i < tvvValuedEsDepTDLList.length; i++) {
									$scope.editDefaultValue(tvvValuedEsDepTDLList[i]);
									$scope.technicalLists.push(tvvValuedEsDepTDLList[i]);

								}
							}
						}, function(error) {
							NotificationService.notify(CultureService.localize('cop.tvv.message.initializeErrorr'));
						});
						$scope.savedTDLists = $scope.technicalLists;

					} else {
						$scope.errorMessage = CultureService.localize('cop.tvv.message.loadError');
						$('#errorDisplayModal').modal('show');
					}
				};

				$scope.getTvvData();

				// This method will keep track of edited field
				$scope.editDefaultValue = function(list) {

					list.editedValues = 0;

					if (list.valuedGenericData != null && list.valuedGenericData.length > 0) {
						list.valuedGenericDataLength = list.valuedGenericData.length;
						for (var i = 0; i < list.valuedGenericData.length; i++) {
							if (list.valuedGenericData[i].defaultValue != null && list.valuedGenericData[i].defaultValue.length > 0) {

								list.editedValues = list.editedValues + 1;

							}
						}
					} else
						list.valuedGenericDataLength = 0;
				};
				// This method updates technical data list
				$scope.updateTDL = function() {

					$scope.tvvObject.carBrandList = $scope.selectedCarBrand.selectedList;
					$scope.tvvObject.carFactoryList = $scope.selectedCarFactory.selectedList;
					$scope.tvvObject.countryList = $scope.selectedCountry.selectedList;
					$scope.tvvObject.tvvValuedTvvDepTDL = [];
					$scope.tvvObject.tvvValuedEsDepTDL = [];
					var validationPassed = true;
					$scope.errorMessage = CultureService.localize('cop.tvv.message.validationfailed');
					$scope.errorMessageArray = [];
					for (var i = 0; i < $scope.technicalLists.length; i++) {

						var errorList = 1;
						if ($scope.technicalLists[i].valuedGenericData != null) {
							for (var j = 0; j < $scope.technicalLists[i].valuedGenericData.length; j++) {
								var messaage = $scope.ValidateData($scope.technicalLists[i].valuedGenericData[j]);
								if (messaage != null) {
									$scope.errorMessageArray.push("List : " + $scope.technicalLists[i].label + " Data: " + $scope.technicalLists[i].valuedGenericData[j].label + "-" + messaage);

									validationPassed = false;
									errorList = errorList + 1;

								}
							}
						}
						if ($scope.technicalLists[i].typeOfList === "TvvValuedTDL")
							$scope.tvvObject.tvvValuedTvvDepTDL.push($scope.technicalLists[i]);
						else
							$scope.tvvObject.tvvValuedEsDepTDL.push($scope.technicalLists[i]);
					}

					if (!validationPassed) {
						$('#validationErrorModal').modal('show');
					} else {

						SaveTvvService.TvvResource.updateTVV($scope.tvvObject, function(success) {
							$scope.tvvObject = success;
							// changes Made for cancel button

							if ($scope.tvvObject.valuedCoastDown !== null && $scope.tvvObject.valuedCoastDown.inertia_value !== undefined)
								$scope.coastDownLabel = $scope.tvvObject.valuedCoastDown.inertia_value + " " + $scope.tvvObject.valuedCoastDown.pSAReference + $scope.tvvObject.valuedCoastDown.version;
							$scope.selectedCarBrand.selectedList = $scope.tvvObject.carBrandList;
							$scope.selectedCarFactory.selectedList = $scope.tvvObject.carFactoryList;
							$scope.selectedCountry.selectedList = $scope.tvvObject.countryList;
							var temp = new Date($scope.tvvObject.modificationDate);
							var displayDate = temp.getDate() + "/" + (temp.getMonth() + 1) + "/" + temp.getFullYear();
							$scope.modificationDate = displayDate;
							NotificationService.notify(CultureService.localize('cop.tvv.message.editSuccess'));
							// This is called to refresh currently displayed list,in case if there is change.List may change in case if emission standard is changed
							if ($scope.tvvObjectCopy.emissionStandard.entityId !== $scope.tvvObject.emissionStandard.entityId)
								$scope.initializeData();
							$scope.tvvObjectCopy = angular.copy($scope.tvvObject);
						}, function() {
							$scope.errorMessage = CultureService.localize('cop.tvv.message.editError');
							$('#errorDisplayModal').modal('show');
						});
					}

				}
				// Changes on TD Cancel Button
				$scope.CancelOnConfirmationTDL = function() {
					$scope.dataNotChanged = angular.equals($scope.technicalLists, $scope.technicalListsCopy);
					$scope.dataNotChangedfortvv = angular.equals($scope.tvvObject, $scope.tvvObjectCopy);

					if ($scope.dataNotChanged === false || $scope.dataNotChangedfortvv === false) {
						$('#ConfirmCancelTDModal').modal('show');
					}

				};
				$scope.cancelModifiedTDChanges = function() {
					$scope.technicalLists = angular.copy($scope.technicalListsCopy);
					$scope.tvvObject = angular.copy($scope.tvvObjectCopy);
				};
				// End of Changes on Cancel Button

				// to check finalReduction value selected or not
				$scope.finalReductionSelectvalue = function() {
					$scope.filnalReductionselected = 1;
				}

				/** ********************************************TAB CONDTION LIST**************************************************************************** */

				$scope.AllCoastDowndata = [];
				$scope.DistinctCoastDownData = [];
				$scope.DistinctInertia = [];
				$scope.getAllCoastDownData = function() {
					TvvCoastDownService.CoastDownResource.getAllCoastDownData({
						'inertia' : 0

					}, function(success) {
						$scope.AllCoastDowndata = success;
						// changes Made for Cancel Button
						$scope.AllCoastDowndataCopy = angular.copy($scope.AllCoastDowndata);
						$scope.DistinctCoastDownData.push({
							"pSAReference" : 'no Value',
							"version" : ''
						})

						$scope.DistinctInertia.push({
							"inertia_value" : 'no Value'

						})
						for (var i = 0; i < $scope.AllCoastDowndata.length; i++) {
							var uniqCheckCoastDown = true;
							var uniqCheckInerrtia = true;

							for (var j = 0; j < $scope.DistinctCoastDownData.length; j++) {
								if ($scope.AllCoastDowndata[i]["pSAReference"] === $scope.DistinctCoastDownData[j]["pSAReference"] && $scope.AllCoastDowndata[i]["version"] === $scope.DistinctCoastDownData[j]["version"]) {
									uniqCheckCoastDown = false;
									break;
								}

							}
							if (uniqCheckCoastDown) {
								$scope.DistinctCoastDownData.push($scope.AllCoastDowndata[i]);
							}

							for (var k = 0; k < $scope.DistinctInertia.length; k++) {
								if ($scope.AllCoastDowndata[i]["inertia_value"] === $scope.DistinctInertia[k]["inertia_value"]) {
									uniqCheckInerrtia = false;
									break;
								}

							}

							if (uniqCheckInerrtia) {
								$scope.DistinctInertia.push($scope.AllCoastDowndata[i]);
							}
						}

						if ($scope.tvvObject.valuedCoastDown !== undefined && $scope.tvvObject.valuedCoastDown != null) {
							$scope.coastDownvalue = $scope.tvvObject.valuedCoastDown;
							$scope.inertiaValue = $scope.coastDownvalue;
							$scope.CoastDownChange($scope.coastDownvalue);
							$scope.InertiaChange($scope.inertiaValue);

						}

					}, function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeErrorr'));
					});

				};

				$scope.CoastDownChange = function(selectedCoatdown) {
					$scope.DistinctInertia = [];

					$scope.DistinctInertia.push({
						"inertia_value" : 'no Value'

					})
					$scope.coastDownvalue = selectedCoatdown;

					if ($scope.coastDownvalue.pSAReference !== 'no Value') {

						for (var j = 0; j < $scope.AllCoastDowndata.length; j++) {

							if ($scope.coastDownvalue["pSAReference"] === $scope.AllCoastDowndata[j]["pSAReference"] && $scope.coastDownvalue["version"] === $scope.AllCoastDowndata[j]["version"]) {

								$scope.DistinctInertia.push($scope.AllCoastDowndata[j]);

							}
						}
					} else {

						for (var i = 0; i < $scope.AllCoastDowndata.length; i++) {
							var uniqCheckInerrtia = true;

							for (var k = 0; k < $scope.DistinctInertia.length; k++) {
								if ($scope.AllCoastDowndata[i]["inertia_value"] === $scope.DistinctInertia[k]["inertia_value"]) {
									uniqCheckInerrtia = false;
									break;
								}

							}

							if (uniqCheckInerrtia) {
								$scope.DistinctInertia.push($scope.AllCoastDowndata[i]);
							}
						}
					}

				}
				// This is called when inertia value in dropdown is changed
				$scope.InertiaChange = function(selectedInertia) {
					$scope.DistinctCoastDownData = [];
					$scope.DistinctCoastDownData.push({
						"pSAReference" : 'no Value',
						"version" : ''
					})
					$scope.coastDownvalue = selectedInertia
					if ($scope.coastDownvalue.inertia_value !== 'no Value') {
						for (var k = 0; k < $scope.AllCoastDowndata.length; k++) {

							if ($scope.coastDownvalue["inertia_value"] === $scope.AllCoastDowndata[k]["inertia_value"]) {
								$scope.DistinctCoastDownData.push($scope.AllCoastDowndata[k]);
							}
						}
					} else {
						for (var i = 0; i < $scope.AllCoastDowndata.length; i++) {
							var uniqCheckCoastDown = true;

							for (var j = 0; j < $scope.DistinctCoastDownData.length; j++) {
								if ($scope.AllCoastDowndata[i]["pSAReference"] === $scope.DistinctCoastDownData[j]["pSAReference"] && $scope.AllCoastDowndata[i]["version"] === $scope.DistinctCoastDownData[j]["version"]) {
									uniqCheckCoastDown = false;
									break;
								}

							}
							if (uniqCheckCoastDown) {
								$scope.DistinctCoastDownData.push($scope.AllCoastDowndata[i]);
							}

						}
					}

				}
				$scope.testConditionLists = [];
				// This method keeps track of Changes in Generic test condition

				// This initializes test condition lists
				$scope.initializeTVVDepTCL = function() {
					if ($scope.tvvObject.emissionStandard != null) {
						$scope.testConditionLists = [];
						TvvValuedTCLService.ValuedTDLResource.getAllTCL({
							'tvvId' : $scope.tvvObject.entityId

						}, function(success) {
							var TVVDepTCLlist = success;
							for (var i = 0; i < TVVDepTCLlist.length; i++) {

								$scope.testConditionLists.push(TVVDepTCLlist[i]);
								$scope.editDefaultConditionValue(TVVDepTCLlist[i]);
							}
							// Change made for Cancel Button
							$scope.testConditionListsCopy = angular.copy($scope.testConditionLists);
						}, function() {

						});

						TvvValuedEsDepTCLService.ValuedEsTCLResource.getAllEsDepTCL({
							'tvvId' : $scope.tvvObject.entityId,
							'emsId' : $scope.tvvObject.emissionStandard.entityId

						}, function(success) {
							var tvvValuedEsDepTCLList = success;
							for (var i = 0; i < tvvValuedEsDepTCLList.length; i++) {

								$scope.testConditionLists.push(tvvValuedEsDepTCLList[i]);
								$scope.editDefaultConditionValue(tvvValuedEsDepTCLList[i]);
							}
							// Change made for Cancel Button
							$scope.testConditionListsCopy = angular.copy($scope.testConditionLists);
						}, function() {
						});
					} else {
						$scope.errorMessage = CultureService.localize('cop.tvv.message.loadError');
						$('#errorDisplayModal').modal('show');
					}

				};
				/**
				 * 
				 */
				$scope.editDefaultConditionValue = function(list) {

					list.editedValues = 0;
					if (list.genericTestCondition != null && list.genericTestCondition.length > 0) {
						list.genericTestConditionLength = list.genericTestCondition.length;
						for (var i = 0; i < list.genericTestCondition.length; i++) {

							if (list.genericTestCondition[i].defaultValue != null && list.genericTestCondition[i].defaultValue.length > 0) {

								list.editedValues = list.editedValues + 1;

							}
						}
					} else
						list.genericTestConditionLength = 0;
				};
				/**
				 * This method updates Test condition lists
				 */
				$scope.updateTCL = function() {

					$scope.tvvObject.carBrandList = $scope.selectedCarBrand.selectedList;
					$scope.tvvObject.carFactoryList = $scope.selectedCarFactory.selectedList;
					$scope.tvvObject.countryList = $scope.selectedCountry.selectedList;
					if ($scope.coastDownvalue !== undefined && $scope.coastDownvalue !== null)
						$scope.tvvObject.coastDownToSave = $scope.coastDownvalue;

					var validationPassed = true;
					$scope.tvvObject.tvvValuedTvvDepTCL = [];
					$scope.tvvObject.tvvValuedEsDepTCL = [];

					$scope.errorMessageArray = [];
					for (var i = 0; i < $scope.testConditionLists.length; i++) {

						$scope.errorMessage = CultureService.localize('cop.tvv.message.validationfailed');
						if ($scope.testConditionLists[i].genericTestCondition != null) {
							for (var j = 0; j < $scope.testConditionLists[i].genericTestCondition.length; j++) {

								var messaage = $scope.ValidateData($scope.testConditionLists[i].genericTestCondition[j]);
								if (messaage !== null) {
									$scope.errorMessageArray.push("List : " + $scope.testConditionLists[i].label + " Data: " + $scope.testConditionLists[i].genericTestCondition[j].label + "-" + messaage);

									validationPassed = false;

								}
							}
						}

						if ($scope.testConditionLists[i].typeOfList === "TvvValuedTCL")

							$scope.tvvObject.tvvValuedTvvDepTCL.push($scope.testConditionLists[i]);
						else
							$scope.tvvObject.tvvValuedEsDepTCL.push($scope.testConditionLists[i]);
					}
					if (!validationPassed) {
						$('#validationErrorModal').modal('show');
					} else {

						SaveTvvService.TvvResource.updateTVV($scope.tvvObject, function(success) {
							$scope.tvvObject = success;
							// changes Made for cancel button

							if ($scope.tvvObject.valuedCoastDown !== null && $scope.tvvObject.valuedCoastDown.inertia_value !== undefined)
								$scope.coastDownLabel = $scope.tvvObject.valuedCoastDown.inertia_value + " " + $scope.tvvObject.valuedCoastDown.pSAReference + $scope.tvvObject.valuedCoastDown.version;
							$scope.selectedCarBrand.selectedList = $scope.tvvObject.carBrandList;
							$scope.selectedCarFactory.selectedList = $scope.tvvObject.carFactoryList;
							$scope.selectedCountry.selectedList = $scope.tvvObject.countryList;
							var temp = new Date($scope.tvvObject.modificationDate);
							var displayDate = temp.getDate() + "/" + (temp.getMonth() + 1) + "/" + temp.getFullYear();
							$scope.modificationDate = displayDate;
							// This is called to refresh currently displayed list,in case if there is change.List may change in case if emission standard is changed
							if ($scope.tvvObjectCopy.emissionStandard.entityId !== $scope.tvvObject.emissionStandard.entityId)
								$scope.initializeTVVDepTCL();
							$scope.tvvObjectCopy = angular.copy($scope.tvvObject);

							NotificationService.notify(CultureService.localize('cop.tvv.message.editSuccess'));
						}, function() {
							$scope.errorMessage = CultureService.localize('cop.tvv.message.editError');
							$('#errorDisplayModal').modal('show');
						});
					}

				}
				// Changes on TC Cancel Button
				$scope.CancelOnConfirmationTCL = function() {
					$scope.dataNotChanged = angular.equals($scope.testConditionLists, $scope.testConditionListsCopy);
					$scope.dataNotChangedfortvv = angular.equals($scope.tvvObject, $scope.tvvObjectCopy);

					if (!$scope.dataNotChanged || !$scope.dataNotChangedfortvv) {
						$('#ConfirmCancelTCModal').modal('show');
					}

				};
				$scope.cancelModifiedTCChanges = function() {
					$scope.testConditionLists = angular.copy($scope.testConditionListsCopy);
					$scope.tvvObject = angular.copy($scope.tvvObjectCopy);
					$scope.AllCoastDowndata = angular.copy($scope.AllCoastDowndataCopy);

				};
				// End of Changes on Cancel Button
				/** ************************************************************************************TAB POLLUTANT OR GAS LIST***************************************************************** */
				$scope.initializePGL = function() {
					if ($scope.tvvObject.emissionStandard != null) {
						TvvValuedEsDepPGLService.ValuedEsPGLResource.getAllEsDepPGL({
							'tvvId' : $scope.tvvObject.entityId,
							'emsId' : $scope.tvvObject.emissionStandard.entityId
						// $scope.tvvObject.emissionStandardList[0].entityId

						}, function(success) {
							$scope.tvvValuedEsDepPGLList = success;
							for (var i = 0; i < $scope.tvvValuedEsDepPGLList.length; i++) {
								$scope.editDefaultPollutantValue($scope.tvvValuedEsDepPGLList[i]);

							}
							// Change made for Cancel Button
							$scope.tvvValuedEsDepPGLListCopy = angular.copy($scope.tvvValuedEsDepPGLList);

						}, function() {
						});

					} else {
						$scope.errorMessage = CultureService.localize('cop.tvv.message.loadError');
						$('#errorDisplayModal').modal('show');
					}
				};
				/** *****************************************************TAB Factors********************************************************************** */
				// Initialize tvv valued factor
				$scope.enableFactorEdit = false;
				$scope.factorEditCheck = function() {
					if (authorizationService.hasRole('seed-w20', 'POCHRole') || authorizationService.hasRole('seed-w20', 'POCERole') ||authorizationService.hasRole('seed-w20', 'POCFRole')  ||authorizationService.hasRole('seed-w20', 'POCGRole')) {
					
						$scope.enableFactorEdit = true;
						
					}
				};
				$scope.tvvValuedFCDropdownVal = [
						'OUI', 'NON'
				];

				$scope.dropDownVal = "";
				$scope.tempFactorCoeffListToSave = [];

				$scope.tvvValuedFactor = function() {
					$scope.factorEditCheck();	
					TvvFactorCoeffService.TvvFactorCoeffResource.getTvvFactorCoeffList({
						'tvvId' : $scope.tvvObject.entityId
					}, function(data) {
						var response = data;

						$scope.tvvValuedFactorCoeffList = response;
						for (var i = 0; i < $scope.tvvValuedFactorCoeffList.length; i++) {
							if($scope.tvvValuedFactorCoeffList[i].factorOrCoeffiecients != null) {
							for (var j = 0; j < $scope.tvvValuedFactorCoeffList[i].factorOrCoeffiecients.length; j++) {
								$scope.tvvValuedFactorCoeffList[i].factorOrCoeffiecients[j].defaultValue = $scope.tvvValuedFactorCoeffList[i].factorOrCoeffiecients[j].defaultValue.toString().replace(".", ",");
							}
						}
					}
						$scope.tempFactorCoeffListToSave = [];
					}, function() {
						$scope.errorMessage = CultureService.localize('cop.tvv.message.loadError');
						$('#errorDisplayModal').modal('show');
					});

				};
				// Save tvv valued factor coefficient

				$scope.updateFCL = function() {

					$scope.tvvObject.carBrandList = $scope.selectedCarBrand.selectedList;
					$scope.tvvObject.carFactoryList = $scope.selectedCarFactory.selectedList;
					$scope.tvvObject.countryList = $scope.selectedCountry.selectedList;
					if ($scope.coastDownvalue !== undefined && $scope.coastDownvalue !== null)
						$scope.tvvObject.coastDownToSave = $scope.coastDownvalue;

					$scope.tvvObject.tvvValuedTvvDepTCL = [];
					$scope.tvvObject.tvvValuedEsDepTCL = [];

					$scope.errorMessageArray = [];

					if ($scope.tempFactorCoeffListToSave.length > 0) {
						for (var j = 0; j < $scope.tvvValuedFactorCoeffList.length; j++) {
							for (var i = 0; i < $scope.tvvValuedFactorCoeffList[j].factorOrCoeffiecients.length; i++) {
								$scope.tvvValuedFactorCoeffList[j].factorOrCoeffiecients[i].defaultValue = $scope.tvvValuedFactorCoeffList[j].factorOrCoeffiecients[i].defaultValue.toString().replace(",", ".");
							}
						}
						//factormeha
						$scope.tvvObject.tvvValuedEsDepFCL = $scope.tvvValuedFactorCoeffList;
					}
						SaveTvvService.TvvResource.updateTVV($scope.tvvObject, function(success) {
							$scope.tvvObject = success;
							// changes Made for cancel button
							if ($scope.tvvObject.valuedCoastDown !== null && $scope.tvvObject.valuedCoastDown.inertia_value !== undefined)
								$scope.coastDownLabel = $scope.tvvObject.valuedCoastDown.inertia_value + " " + $scope.tvvObject.valuedCoastDown.pSAReference + $scope.tvvObject.valuedCoastDown.version;
							$scope.selectedCarBrand.selectedList = $scope.tvvObject.carBrandList;
							$scope.selectedCarFactory.selectedList = $scope.tvvObject.carFactoryList;
							$scope.selectedCountry.selectedList = $scope.tvvObject.countryList;
							var temp = new Date($scope.tvvObject.modificationDate);
							var displayDate = temp.getDate() + "/" + (temp.getMonth() + 1) + "/" + temp.getFullYear();
							$scope.modificationDate = displayDate;
							// This is called to refresh currently displayed list,in case if there is change.List may change in case if emission standard is changed
							if ($scope.tvvObjectCopy.emissionStandard.entityId !== $scope.tvvObject.emissionStandard.entityId)
								$scope.initializeTVVDepTCL();
							$scope.tvvObjectCopy = angular.copy($scope.tvvObject);

							NotificationService.notify(CultureService.localize('cop.tvv.message.editSuccess'));
						}, function() {
							$scope.errorMessage = CultureService.localize('cop.tvv.message.editError');
							$('#errorDisplayModal').modal('show');
						});

					

				}

				$scope.updateSamplingRule = function() {

					$scope.tvvObject.carBrandList = $scope.selectedCarBrand.selectedList;
					$scope.tvvObject.carFactoryList = $scope.selectedCarFactory.selectedList;
					$scope.tvvObject.countryList = $scope.selectedCountry.selectedList;
					if ($scope.coastDownvalue !== undefined && $scope.coastDownvalue !== null)
						$scope.tvvObject.coastDownToSave = $scope.coastDownvalue;

					$scope.tvvObject.tvvValuedTvvDepTCL = [];
					$scope.tvvObject.tvvValuedEsDepTCL = [];

					$scope.errorMessageArray = [];

					$scope.tvvObject.sampleLabel = $scope.sampleLabelSelectedforTVV;

					SaveTvvService.TvvResource.updateTVV($scope.tvvObject, function(success) {
						$scope.tvvObject = success;
						// changes Made for cancel button

						if ($scope.tvvObject.valuedCoastDown !== null && $scope.tvvObject.valuedCoastDown.inertia_value !== undefined)
							$scope.coastDownLabel = $scope.tvvObject.valuedCoastDown.inertia_value + " " + $scope.tvvObject.valuedCoastDown.pSAReference + $scope.tvvObject.valuedCoastDown.version;
						$scope.selectedCarBrand.selectedList = $scope.tvvObject.carBrandList;
						$scope.selectedCarFactory.selectedList = $scope.tvvObject.carFactoryList;
						$scope.selectedCountry.selectedList = $scope.tvvObject.countryList;
						var temp = new Date($scope.tvvObject.modificationDate);
						var displayDate = temp.getDate() + "/" + (temp.getMonth() + 1) + "/" + temp.getFullYear();
						$scope.modificationDate = displayDate;
						// This is called to refresh currently displayed list,in case if there is change.List may change in case if emission standard is changed
						if ($scope.tvvObjectCopy.emissionStandard.entityId !== $scope.tvvObject.emissionStandard.entityId)
							$scope.initializeTVVDepTCL();
						$scope.tvvObjectCopy = angular.copy($scope.tvvObject);

						NotificationService.notify(CultureService.localize('cop.tvv.message.editSuccess'));
					}, function() {
						$scope.errorMessage = CultureService.localize('cop.tvv.message.editError');
						$('#errorDisplayModal').modal('show');
					});

				}

				/** *****************************************************TAB Sampling Rule********************************************************************** */
				// get sampling rule list for tvv
				$scope.samplingRuleLabelList = [
					'None'
				];
				$scope.samplingRuleList = {};
				$scope.selectedSamplingRule = "";
				
				$scope.getSamplingRuleForTVV = function() {
					$scope.selectedSamplingRule = $scope.tvvObject.sampleLabel;
					$scope.samplingRuleLabelList = [
						'None'
					];
					$scope.samplingRuleList = {};
					$scope.descriptionSampleRule = [];
					
					$scope.sampleLabelSelectedforTVV = "";
					TvvSamplingRuleService.TvvSamplingRuleResource.getTvvSamplingRuleList({
						'tvvId' : $scope.tvvObject.entityId
					}, function(data) {
						var response = data;
						$scope.samplingRuleList = response;
						for (var i = 0; i < $scope.samplingRuleList.samplingRuleRepresentationList.length; i++) {
							$scope.samplingRuleLabelList.push($scope.samplingRuleList.samplingRuleRepresentationList[i].label);
						}
						for (var j = 0; j < $scope.samplingRuleList.samplingRuleRepresentationList.length; j++) {
							if ($scope.samplingRuleList.samplingRuleRepresentationList[j].label === $scope.selectedSamplingRule) {
								$scope.descriptionSampleRule = $scope.samplingRuleList.samplingRuleRepresentationList[j].descriptions;
							}
						}
					}, function() {
						$scope.errorMessage = CultureService.localize('cop.tvv.message.loadError');
						$('#errorDisplayModal').modal('show');
					});
				}

				$scope.descriptionSampleRule = [];
				$scope.sampleLabelSelectedforTVV = "";
				$scope.samplingRuleChange = function(label) {
					$scope.sampleLabelSelectedforTVV = label;
					for (var i = 0; i < $scope.samplingRuleList.samplingRuleRepresentationList.length; i++) {
						if ($scope.samplingRuleList.samplingRuleRepresentationList[i].label === label) {
							$scope.descriptionSampleRule = $scope.samplingRuleList.samplingRuleRepresentationList[i].descriptions;
						}
					}
					if (label === "None") {
						$scope.descriptionSampleRule = [];
					}
				}

				$scope.validateSamplingRule = function() {

					SaveTvvSamplingRuleService.SaveTvvSamplingRuleResource.saveTvvSamplingRule({
						'tvvId' : $scope.tvvObject.entityId
					}, {
						'sampleLabel' : $scope.sampleLabelSelectedforTVV
					}, function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.editSuccess'));
					}, function() {
						$scope.errorMessage = CultureService.localize('cop.tvv.message.editError');
						$('#errorDisplayModal').modal('show');
					});

				}

				// This method keeps track of changes in pollutant value
				$scope.editDefaultPollutantValue = function(list) {
					list.editedValues = 0;
					if (list.pollutantGasLimit != null && list.pollutantGasLimit.length > 0) {
						for (var i = 0; i < list.pollutantGasLimit.length; i++) {
							if (list.pollutantGasLimit[i].tvvDisplayValue != null && list.pollutantGasLimit[i].tvvDisplayValue.length > 0) {
								list.editedValues = list.editedValues + 1;
							}
						}
					}
				};
				/**
				 * This method updates TVV with new Pollutant Gas Limit values
				 */

				$scope.updatePGL = function() {

					$scope.tvvObject.carBrandList = $scope.selectedCarBrand.selectedList;
					$scope.tvvObject.carFactoryList = $scope.selectedCarFactory.selectedList;
					$scope.tvvObject.countryList = $scope.selectedCountry.selectedList;
					if ($scope.tvvValuedEsDepPGLList != null) {
						for (var i = 0; i < $scope.tvvValuedEsDepPGLList.length; i++) {

							$scope.tvvObject.tvvValuedEsDepPGL.push($scope.tvvValuedEsDepPGLList[i]);
						}
					}

					SaveTvvService.TvvResource.updateTVV($scope.tvvObject, function(success) {
						$scope.tvvObject = success;
						// changes Made for cancel button

						if ($scope.tvvObject.valuedCoastDown !== null && $scope.tvvObject.valuedCoastDown.inertia_value !== undefined)
							$scope.coastDownLabel = $scope.tvvObject.valuedCoastDown.inertia_value + " " + $scope.tvvObject.valuedCoastDown.pSAReference + $scope.tvvObject.valuedCoastDown.version;
						$scope.selectedCarBrand.selectedList = $scope.tvvObject.carBrandList;
						$scope.selectedCarFactory.selectedList = $scope.tvvObject.carFactoryList;
						$scope.selectedCountry.selectedList = $scope.tvvObject.countryList;
						// This is called to refresh currently displayed list,in case if there is change.List may change in case if emission standard is changed
						if ($scope.tvvObjectCopy.emissionStandard.entityId !== $scope.tvvObject.emissionStandard.entityId)
							$scope.initializePGL();
						$scope.tvvObjectCopy = angular.copy($scope.tvvObject);

						var temp = new Date($scope.tvvObject.modificationDate);
						var displayDate = temp.getDate() + "/" + (temp.getMonth() + 1) + "/" + temp.getFullYear();
						$scope.modificationDate = displayDate;

						NotificationService.notify(CultureService.localize('cop.tvv.message.editSuccess'));
					}, function() {
						$scope.errorMessage = CultureService.localize('cop.tvv.message.editError');
						$('#errorDisplayModal').modal('show');
					});

				};
				// Changes on PG Cancel Button
				$scope.CancelOnConfirmationPGL = function() {
					$scope.dataNotChanged = angular.equals($scope.tvvValuedEsDepPGLList, $scope.tvvValuedEsDepPGLListCopy);
					$scope.dataNotChangedfortvv = angular.equals($scope.tvvObject, $scope.tvvObjectCopy);

					if (!$scope.dataNotChanged || !$scope.dataNotChangedfortvv) {
						$('#ConfirmCancelPGModal').modal('show');
					}

				};
				$scope.cancelModifiedPGChanges = function() {
					$scope.tvvValuedEsDepPGLList = angular.copy($scope.tvvValuedEsDepPGLListCopy);
					$scope.tvvObject = angular.copy($scope.tvvObjectCopy);
					/*
					 * $(document).ready(function() { setTimeout(function() { $("#limit0").addClass("active"); $(".limit0").addClass("active in"); }, 1000); });
					 */
				};
				// End of Changes on Cancel Button
				/** ********************************************************************************************************************** */
				/** ********************************* FinalReductionRatio **************************************************************** */
				$scope.FRRList;
				$scope.getFRRValue = function() {

					var FRRValue = null;
					FRRValue = FinalReductionRatioService.FinalReductionRatioResource.getAllFinalReductionData({
						'tvvLabel' : $scope.tvvObject.label
					}, function() {
						$scope.FRRList = FRRValue;

					}, function() {

					});
				};
				if ($scope.tvvObject !== undefined && $scope.tvvObject !== null)
					$scope.getFRRValue();

				/** ******************************************************************************************************************** */

				$scope.editable = true;
				$scope.disableInput = false;
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
				$scope.cancelCreation = function(label, modalName) {
					$scope.label = label;
					$scope.modaName = modalName;
					if (label !== undefined && label !== null && label.trim() !== '')
						$('#cancelCreationModal').modal('show');
					else
						$('#' + $scope.modaName).modal('hide');
				};
				$scope.continueCreation = function() {

					$('#' + $scope.modaName).modal('hide');

				}

				/*------------------------------------------History-------------------------------------------------*/
				var cellToolTipTemplate = '<div class="ui-grid-cell-contents" title="{{COL_FIELD}}">{{ COL_FIELD }}</div>';
				$scope.HistoryGridOptions = {
					enableColumnResizing : true,
					enableFiltering : true,
					enableSorting : true,
					columnDefs : [

							{
								name : 'date',
								displayName : CultureService.localize('cop.history.date'),
								type : 'date',
								field : 'updationDate',
								cellFilter : 'date:\'dd/MM/yyyy HH:mm:ss\'',
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.updationDate;
								}
							}, {
								name : 'userId',
								displayName : CultureService.localize('cop.history.userId'),
								field : 'userId',
								enableCellEdit : false,
								cellTemplate : cellToolTipTemplate
							}, {
								name : 'userProfile',
								displayName : CultureService.localize('cop.history.userProfile'),
								field : 'userProfile',
								enableCellEdit : false,
								cellTemplate : cellToolTipTemplate
							}, {
								name : 'description',
								displayName : CultureService.localize('cop.history.description'),
								field : 'description',
								enableCellEdit : false,
								cellTemplate : cellToolTipTemplate
							}, {
								name : 'oldValue',
								displayName : CultureService.localize('cop.history.oldValue'),
								field : 'oldValue',
								enableCellEdit : false,
								cellTemplate : cellToolTipTemplate
							}, {
								name : 'newValue',
								displayName : CultureService.localize('cop.history.newValue'),
								field : 'newValue',
								enableCellEdit : false,
								cellTemplate : cellToolTipTemplate
							},

					],
					data : 'allHistory',

				};

				/* to get History Data */
				$scope.allHistory = [];
				$scope.getAllHistory = function(screenId, tvvEntityId) {

					var historyRepresentation = {
						'screenId' : screenId,
						'vehicleFileId' : "null",
						'tvvEntityId' : tvvEntityId
					};

					HistoryService.HistroyResource.historyData(historyRepresentation, function(success) {
						$scope.allHistory = success;
						$('#showHistoryBox').modal('show');
					}, function() {

					});
				};

				$scope.numFormatCheck = function(value, entityId) {
					for (var i = 0; i < $scope.tvvValuedFactorCoeffList.length; i++) {
						for (var j = 0; j < $scope.tvvValuedFactorCoeffList[i].factorOrCoeffiecients.length; j++) {
							if ($scope.tvvValuedFactorCoeffList[i].factorOrCoeffiecients[j].entityId === entityId) {
								$scope.tempFactorCoeffListToSave.push($scope.tvvValuedFactorCoeffList[i]);
							}
						}
					}
					if (value) {
						var INTEGER_REG = new RegExp("^[-]?[0-9/,.]+$");
						if (!INTEGER_REG.test(value)) {
							value = value.slice(0, -1);
						}
						$scope.resultSetEdited = true;

						return value;
					}
				}

				$(document).ready(function() {
					setTimeout(function() {
						$(".factor0").addClass("active");
						$("#factor0").addClass("active in");
					}, 3000);
				});
			}
	]);

	editTechnicalData.directive('ngEnter', function() {
		return function(scope, element, attrs) {
			element.bind("keydown keypress", function(event) {
				if (event.which === 13) {
					scope.$apply(function() {
						scope.$eval(attrs.ngEnter);
					});

					event.preventDefault();
				}
			});
		};
	});

	editTechnicalData.directive('ngBlur', function() {
		return function(scope, elem, attrs) {
			elem.bind('blur', function() {
				scope.$apply(attrs.ngBlur);
			});
		};
	});

	/*
	 * editTechnicalData.directive('ngFocus', function($timeout) { return function(scope, elem, attrs) { scope.$watch(attrs.ngFocus, function(newval) { if (newval) { $timeout(function() { elem[0].focus(); }, 0, false); } }); }; });
	 */

	return {
		angularModules : [
			'editTechnicalData'
		]
	};

});
