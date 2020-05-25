define([
		'require', 'module', '{angular}/angular', '{w20-core}/modules/ui',
], function(require, module, angular) {
	'use strict';
	var TVVModule = angular.module('TVVModule', [
			'ui.bootstrap', 'copCommon'
	]), config = module && module.config() || {};

	TVVModule.factory('CreateTvvService', [
			'$resource',

			function($resource) {
				return {
					TvvResource : $resource('tvvResource/:path/:entityId', {
						path : '@path',
						entityId : '@entityId'

					}, {

						'createTVV' : {
							method : 'POST',
							params : {
								path : 'TvvNew'
							},
							isArray : false
						}

					})
				};
			}
	]);
	TVVModule.factory('TvvDataService', [
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
	TVVModule.factory('StatusService', [
			'$resource',

			function($resource) {
				return {
					statusResource : $resource('Status/:path/:label', {
						path : '@path',
						entityId : '@entityId'

					}, {

						'getStatus' : {
							method : 'GET',
							params : {
								path : 'statusForLabel'
							},
							isArray : false
						}

					})
				};
			}
	]);

	TVVModule.factory('ManageTvvService', [
			'$rootScope', function($rootScope) {

				var savedTVV = {};

				return savedTVV;
			}
	]);

	TVVModule.controller('TVVModuleController', [
			'$scope', '$modal', 'CultureService', 'NotificationService', '$window', '$location', 'StatusService', 'ProjectFamilyService', 'EngineService', 'GearBoxService', 'ESCommonService', 'BodyWorkService', 'CarBrandService', 'FuelTypeService', 'TVVUtilService', 'CreateTvvService', 'FuelCommonService', 'ManageTvvService', 'TvvDataService', 'CategoryCommonService', 'VTService', 'FactoryCommonService','FuelInjectionTypeService', function($scope, $modal, CultureService, NotificationService, $window, $location, StatusService, ProjectFamilyService, EngineService, GearBoxService, ESCommonService, BodyWorkService, CarBrandService, FuelTypeService, TVVUtilService, CreateTvvService, FuelCommonService, ManageTvvService, TvvDataService, CategoryCommonService, VTService, FactoryCommonService,FuelInjectionTypeService
			) {
				$scope.selectedOption = null;
				$scope.enableSearchButton = false;
				var today = new Date();
				var dd = today.getDate();
				var mm = today.getMonth() + 1; // January is 0!
				var yyyy = today.getFullYear();

				if (dd < 10) {
					dd = '0' + dd;
				}

				if (mm < 10) {
					mm = '0' + mm;
				}

				$scope.sysDate = dd + '/' + mm + '/' + yyyy;
				$scope.mandatoryFieldsCompleted = false;
				$scope.projectFamily = null;
				$scope.fuelInjection=null;
				$scope.engine = null;
				$scope.gearBoxType = null;
				$scope.emissionStandard = null;
				$scope.bodyWork = null;
				$scope.carBrands = [];
				$scope.categoryList = [];
				$scope.vehicalTechList = [];
				$scope.factories = [];
				
				$scope.callOption = function(selectedOption) {
					if (selectedOption == "SCRATCH") {
						$scope.enableSearchButton = false;
						$scope.resetLists();
						$scope.initializeAllLists();
					} else if (selectedOption == "GENOME") {
						// $scope.getGnomeDataForTVV();
						$scope.resetLists();
						$scope.enableSearchButton = true;
					}
				};

				$scope.getStatusForTvv = function() {
					StatusService.statusResource.getStatus({
						'label' : "DRAFT"
					}, function(response) {
						$scope.status = response;

					},

					function(error) {
						if (error.status == 412)
							$scope.errorDisplayMessage = CultureService.localize('cop.tvv.message.statusError');
						else
							NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
						$('#TvvErrorModal').modal('show');

					});
				};
				$scope.resetLists = function() {
					$scope.fuelList = [];
					$scope.projectFamilyList = [];
					$scope.engineList = [];
					$scope.gearBoxList = [];
					$scope.bodyWorkList = [];
					$scope.carBrandList = [];
					$scope.emsList = [];
					$scope.projectFamily = null;
					$scope.gearBoxType = null;
					$scope.engine = null;
					$scope.bodyWork = null;
					$scope.fuel = null;

					$scope.carBrands = [];
					$scope.emissionStandard = null;
					$scope.categoryList = [];
					$scope.vehicalTechList = [];
					$scope.factories = [];
					$scope.factoryList = [];
					$scope.fuelinjectionList=[];
					

				}
				$scope.getStatusForTvv();
				$scope.fuel = null;

				$scope.setProjectFamily = function(projectFamily) {
					$scope.projectFamily = projectFamily;
				};

				$scope.setEngine = function(engine) {
					$scope.engine = engine;
				};
				$scope.setGearBox = function(gearBox) {
					$scope.gearBoxType = gearBox;
				};

				$scope.setEmissionStandard = function(emissionStandard) {
					$scope.emissionStandard = emissionStandard;
				};
				$scope.setBodyWork = function(bodyWork) {
					$scope.bodyWork = bodyWork;
				};
				$scope.setCarBrand = function(carBrand) {
					$scope.carBrands = carBrand;
				};
				$scope.setFuel = function(fuel) {
					$scope.fuel = fuel;
				};

				$scope.setCategry = function(category) {
					$scope.category = category;
				};

				$scope.setVehicle = function(vehicle) {
					$scope.vehical = vehicle;
				};
				$scope.setFactory = function(factories) {
					$scope.factories = factories;
				};
				$scope.setFuelInjection=function(fuelInjection)
				{
				    $scope.fuelInjection=fuelInjection;
				};
				$scope.fetchAllLists = function() {
					ProjectFamilyService.ProjectFamilyResource.getAllProjectFamilies(function(response) {
						$scope.projectFamilyList = response;

					},

					function(error) {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					EngineService.EngineResource.getAllEngineData(function(response) {
						$scope.engineList = response;

					},

					function(error) {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeErrorr'));
					});
					GearBoxService.GearBoxResource.getAllGearBoxData(function(response) {
						$scope.gearBoxList = response;

					},

					function(error) {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});

					ESCommonService.ESResource.getValidEmissionStandards(function(response) {
						$scope.emsList = response;

					},

					function(error) {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					BodyWorkService.BodyWorkResource.getAllBodyWorkData(function(response) {
						$scope.bodyWorkList = response;

					},

					function(error) {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					CarBrandService.CarBrandResource.getAllCarBrandData(function(response) {
						$scope.carBrandList = response;

					},

					function(error) {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					FuelCommonService.FuelResource.getAllFuel(function(response) {
						$scope.fuelList = response;

					},

					function(error) {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					CategoryCommonService.CategoryResource.getAllCategoriesForTvv(function(response) {
						$scope.categoryList = response;
					},

					function(error) {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});

					VTService.VehicleTechnologiesResource.getAllVehicleTechnologies(function(response) {
						$scope.vehicalTechList = response;

					},

					function(error) {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					FactoryCommonService.FactoryResource.getFactories(function(response) {
						$scope.factoryList = response;
					}, function(error) {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					 FuelInjectionTypeService.FuelInjectionTypeResource.getFuelInjectionType(function(response) {
	                        $scope.fuelinjectionList = response;
	                 },  function(error) {
	                   NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
	                    });

					// UNCOMMENT THIS WHEN CREATE TVV FOR GENOME DATA WILL BE IMPLEMENTED
					/*
					 * TvvDataService.TvvResource.getGnomeDataForTVV({ 'tvvLabel' : "TVVCOD" }, function(response) { $scope.tvvDataRepresentation = response; $scope.fuelList = $scope.tvvDataRepresentation.fuelList; $scope.projectFamilyList =
					 * $scope.tvvDataRepresentation.pcFamilyList; $scope.engineList = $scope.tvvDataRepresentation.engineList; $scope.gearBoxList = $scope.tvvDataRepresentation.gearBoxList; $scope.bodyWorkList =
					 * $scope.tvvDataRepresentation.bodyWorklist; $scope.carBrandList = $scope.tvvDataRepresentation.carBrandList; $scope.emsList = $scope.tvvDataRepresentation.emissionStandardList; },
					 * 
					 * function(error) { NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError')); });
					 */

				};

				$scope.getGnomeDataForTVV = function() {
				    
					if ($scope.tvvLabel == undefined || $scope.tvvLabel == null || $scope.tvvLabel.length < 6) {
						$scope.errorDisplayMessage = CultureService.localize('cop.tvv.message.tvvLabelError');
						$('#TvvErrorModal').modal('show');
						// $scope.selectedOption = null;
						return;
					}
					$scope.copLoader = true;
					TvvDataService.TvvResource.getGnomeDataForTVV({
						'tvvLabel' : $scope.tvvLabel
					}, function(response) {
						$scope.tvvDataRepresentation = response;
						$scope.fuelList = $scope.tvvDataRepresentation.fuelList;
						$scope.projectFamilyList = $scope.tvvDataRepresentation.pcFamilyList;
						$scope.engineList = $scope.tvvDataRepresentation.engineList;
						$scope.gearBoxList = $scope.tvvDataRepresentation.gearBoxList;
						$scope.bodyWorkList = $scope.tvvDataRepresentation.bodyWorklist;
						$scope.carBrandList = $scope.tvvDataRepresentation.carBrandList;
						$scope.emsList = $scope.tvvDataRepresentation.emissionStandardList;
						$scope.categoryList = $scope.tvvDataRepresentation.categoryList;
						$scope.vehicalTechList = $scope.tvvDataRepresentation.vtList;
						$scope.factoryList = $scope.tvvDataRepresentation.factoryList;
						$scope.fuelinjectionList=$scope.tvvDataRepresentation.fuelInjectionList;
						$scope.copLoader = false;
					},

					function(error) {
					    $scope.copLoader = false;
						if (error.status == 412) {
							$scope.errorDisplayMessage = CultureService.localize('cop.tvv.message.noGenomeData');
						}
						$scope.errorDisplayMessage = CultureService.localize('cop.tvv.message.noGenomeData');
						$('#TvvErrorModal').modal('show');
					});

				}
				$scope.initializeAllLists = function() {
					$scope.fetchAllLists();
				//	$('#addEMSModal').modal('show');
				};

				$scope.checkMandatoryFields = function() {
					if ($scope.projectFamily != null && $scope.engine != null && $scope.gearBoxType != null && $scope.emissionStandard != null && $scope.bodyWork != null && $scope.carBrands.length != 0 && $scope.fuel != null && $scope.category != null && $scope.vehical != null && $scope.factories.length != 0 && $scope.fuelInjection!=null)
						$scope.mandatoryFieldsCompleted = true;
					else
						$scope.mandatoryFieldsCompleted = false;
				};
				$scope.checkLabel = function() {
					if ($scope.tvvLabel == null || $scope.tvvLabel.length == 0) {
						document.getElementById("tvvLabel").style.borderColor = "red";

					} else {
						var tvvObj = {
								'tvvLabel' : $scope.tvvLabel 
						}
						TVVUtilService.TvvResource.getTVVsWithLabel(tvvObj, function(response) {
							if (response == null || response.length == 0)
								$scope.createTVV();
							else {
								$scope.errorDisplayMessage = CultureService.localize('cop.tvv.message.duplicateLabel');
								$('#TvvErrorModal').modal('show');

							}
						}, function(error) {
							NotificationService.notify(CultureService.localize('cop.tvv.message.creationError'));
						});
					}
				};
				$scope.createTVV = function() {

					$scope.errorMessage = [];
					var validationSuccessful = true;
					if ($scope.projectFamily == null) {
						$scope.errorMessage.push("'Code projet / Famille'  " + CultureService.localize('cop.tvv.message.validateCreation'));
						validationSuccessful = false;
					}
					if ($scope.engine == null) {
						$scope.errorMessage.push("'Moteur' " + CultureService.localize('cop.tvv.message.validateCreation'));
						validationSuccessful = false;
					}
					if ($scope.gearBoxType == null) {
						$scope.errorMessage.push("'Boîte de vitesse' " + CultureService.localize('cop.tvv.message.validateCreation'));
						validationSuccessful = false;
					}
					if ($scope.bodyWork == null) {
						$scope.errorMessage.push("'Carrosserie' " + CultureService.localize('cop.tvv.message.validateCreation'));
						validationSuccessful = false;
					}
					if ($scope.emissionStandard == null) {
						$scope.errorMessage.push("'Réglementation' " + CultureService.localize('cop.tvv.message.validateCreation'));
						validationSuccessful = false;
					}

					if ($scope.fuel == null) {
						$scope.errorMessage.push("Carburant " + CultureService.localize('cop.tvv.message.validateCreation'));
						validationSuccessful = false;
					}
					if ($scope.carBrands == null || $scope.carBrands.length == 0) {
						$scope.errorMessage.push("Constructeur / Marque " + CultureService.localize('cop.tvv.message.validateCreation'));
						validationSuccessful = false;
					}

					if ($scope.category == null || $scope.category.length == 0) {
						$scope.errorMessage.push("Catégorie " + CultureService.localize('cop.tvv.message.validateCreation'));
						validationSuccessful = false;
					}

					if ($scope.vehical == null || $scope.vehical.length == 0) {
						$scope.errorMessage.push("Vehicle " + CultureService.localize('cop.tvv.message.validateCreation'));
						validationSuccessful = false;
					}
					if ($scope.factories == null || $scope.factories.length == 0) {
						$scope.errorMessage.push("UP " + CultureService.localize('cop.tvv.message.validateCreation'));
						validationSuccessful = false;
					}
					if($scope.fuelInjection==null || $scope.fuelInjection.length==0){
					    $scope.errorMessage.push("Type d'injection" + CultureService.localize('cop.tvv.message.validateCreation'));
					    validationSuccessful = false;
					}
					if (validationSuccessful) {
						var tvvObj = {
							'projectCodeFamily' : $scope.projectFamily,
							'gearBox' : $scope.gearBoxType,
							'engine' : $scope.engine,
							'bodyWork' : $scope.bodyWork,
							'fuel' : $scope.fuel,
							'label' : $scope.tvvLabel,
							'version' : '1.0',
							'carBrandList' : $scope.carBrands,
							'emissionStandard' : $scope.emissionStandard,
							'category' : $scope.category,
							'vehicalTechnology' : $scope.vehical,
							'carFactoryList' : $scope.factories,
							'fuelInjection':$scope.fuelInjection
							};
						CreateTvvService.TvvResource.createTVV(tvvObj, function(response) {
							NotificationService.notify(CultureService.localize('cop.tvv.message.creationSuccess'));
							ManageTvvService.savedTvv = response;
							var url = "/cop/EditTvv";
							$location.url(url);
						},

						function(error) {
							if (error.status == 412)
								$scope.errorDisplayMessage = CultureService.localize('cop.tvv.message.statusError');
							else
								$scope.errorDisplayMessage = CultureService.localize('cop.tvv.message.creationError');
							$('#TvvErrorModal').modal('show');
						});
					}

					else {
						$('#validationMessageModal').modal('show');
					}

				};
			}
	]);

	return {
		angularModules : [
			'TVVModule'
		]
	};
});