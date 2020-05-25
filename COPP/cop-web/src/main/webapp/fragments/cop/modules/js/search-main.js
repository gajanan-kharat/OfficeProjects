define([
		'require', 'module', '{angular}/angular', '{w20-core}/modules/ui'
], function(require, module, angular) {
	'use strict';
	var search = angular.module('search', [
			'ui.bootstrap', 'copCommon', 'EditTechnicalGroup', 'EditRegulationGroup'
	]), config = module && module.config() || {};

	search.factory('EditTGService', [
			'$rootScope', function($rootScope) {

				var editedTechnicalGroup = {};

				return editedTechnicalGroup;
			}
	]);
	search.factory('EditRGService', [
			'$rootScope', function($rootScope) {

				var editRegulationGroup = {};
				return editRegulationGroup;
			}
	]);
	search.factory('EditRegulationGroupService', [
			'$rootScope', function($rootScope) {

				var editRegulationGroup = {};
				return editRegulationGroup;
			}
	]);

	search.factory('SearchTvvService', [
			'$resource',

			function($resource) {
				return {
					TvvResource : $resource('tvvResource/:path/:searchLabel', {

						searchLabel : '@searchLabel'

					}, {

						'findTVVsByLabel' : {
							method : 'GET',
							params : {
								path : 'TVVs'
							},
							isArray : true
						}

					})
				};
			}
	]);

	search.factory('SearchTechnicalGroupService', [
			'$resource',

			function($resource) {
				return {
					TGResource : $resource('TechnicalGroup/:path', {
						path : '@path'

					}, {
						'getSearchedTechnicalGroups' : {
							method : 'POST',
							params : {
								path : 'TechnicalGroupsByCriteria'
							},
							isArray : true
						},
						'TechnicalGroupForTVV' : {
							method : 'POST',
							params : {
								path : 'TechnicalGroupForTVV'
							},
							isArray : true
						},
					})
				};
			}
	]);

	search.factory('TvvSearchService', [
			'$resource',

			function($resource) {
				return {
					SearchTvvResource : $resource('tvvResource/:path', {

					}, {

						'findByCriteria' : {
							method : 'POST',
							params : {
								path : 'TVVsByCriteria'
							},
							isArray : true
						}

					})
				};
			}
	]);

	search.factory('SearchRegulationGroupService', [
			'$resource', function($resource) {
				return {
					RGResource : $resource('RegulationGroup/:path', {

						path : '@path'

					}, {

						'searchRegulationGroup' : {
							method : 'POST',
							params : {
								path : 'RegulationGroupsByCriteria'
							},
							isArray : true

						},
						'RegulationGroupForTG' : {
							method : 'POST',
							params : {
								path : 'RegulationGroupForTG'
							},
							isArray : false
						},

					})
				}
			}
	]);

	search.factory('LoadRegulationGroupService', [

			'$resource', function($resource) {

				return {

					RegulationResource : $resource('RegulationGroup/:path/:id', {

						path : '@path',
						id : 'id'

					}, {
						'loadRegulationGroup' : {
							method : 'GET',
							params : {
								path : 'RegulationGroupData'
							}

						}

					})

				};

			}
	]);

	/**
	 * 
	 */

	// -------------------------------------COMMON SEARCH START---------------------------------------------------------------
	search.controller('SearchController', [
			'$scope', '$modal', '$http', 'NotificationService', 'CultureService', 'CountryService', 'ProjectFamilyService', 'EngineService', 'GearBoxService', 'ESCommonService', 'BodyWorkService', 'CarBrandService', 'FuelTypeService', 'FuelCommonService', 'ValuedInertiaService', 'ValuedCoastDownService', 'PGLabelService', 'PGLimitService', 'SearchTvvService', 'TvvSearchService', 'AreaService', 'SearchTechnicalGroupService', 'TGService', '$location', 'ManageTvvService', 'EditTGService', 'TVVUtilService', 'EditRGService', 'SearchRegulationGroupService', 'RGService', 'SelectedTGService', 'TvvService', 'LoadRegulationGroupService', '$routeParams','AuthorizationService','$controller','VehicleReceptionSharedService', function($scope, $modal, $http, NotificationService, CultureService, CountryService, ProjectFamilyService, EngineService, GearBoxService, ESCommonService, BodyWorkService, CarBrandService, FuelTypeService, FuelCommonService, ValuedInertiaService, ValuedCoastDownService, PGLabelService, PGLimitService, SearchTvvService, TvvSearchService, AreaService, SearchTechnicalGroupService, TGService, $location, ManageTvvService, EditTGService, TVVUtilService, EditRGService, SearchRegulationGroupService, RGService, SelectedTGService, TvvService, LoadRegulationGroupService, $routeParams,authorizationService,$controller,VehicleReceptionSharedService) {

				$scope.tvvActive = true;
				$scope.tgActive = false;
				$scope.rgActive = false;
				$scope.searchTvvlabel = null;
				$scope.searchRepresentation = {};
				$scope.regulationGroup = EditRGService.editRegulationGroup;
				
				/*
				 * $scope.emissionStandard = {}; $scope.emissionStandard.selected = []; $scope.inertia = {}; $scope.inertia.selected = []; $scope.project = {}; $scope.project.selected = []; $scope.bodyWork = {}; $scope.bodyWork.selected = [];
				 * $scope.engine = {}; $scope.engine.selected = []; $scope.gearBox = {}; $scope.gearBox.selected = []; $scope.fuel = {}; $scope.fuel.selected = []; $scope.coastDown = {}; $scope.coastDown.selected = []; $scope.minLimit = {};
				 * $scope.minLimit.selected = []; $scope.maxLimit = {}; $scope.maxLimit.selected = []; $scope.typeApprovalArea = {}; $scope.typeApprovalArea.selected = []; $scope.country = {}; $scope.country.selected = [];
				 */
				$scope.searchRepresentation.pcFamilyList = [];
				$scope.searchRepresentation.bodyWorkList = [];
				$scope.searchRepresentation.engineList = [];
				$scope.searchRepresentation.inertiaLIst = [];
				$scope.searchRepresentation.coastDownList = [];
				$scope.searchRepresentation.pgMinLimits = [];
				$scope.searchRepresentation.pgMaxLimits = [];
				$scope.searchRepresentation.emsList = [];
				$scope.searchRepresentation.countryList = [];
				$scope.searchRepresentation.fuelList = [];
				$scope.searchRepresentation.typeApprovalAreaList = [];
				$scope.searchRepresentation.gearBoxList = [];
				$scope.tvvselectList = [];
				$scope.selectedTvvlist = [];
				$scope.showAddTgButton = false;
				
				if(VehicleReceptionSharedService.getFlag()){
				    $scope.tvvActive = false;
	                    $('#tvv').removeClass('active');
	                    $('#tvvTab').removeClass('active');
	                    $('#searchVehicles').addClass('active');
	                    $('#vehicleTab').addClass('active');
				}
				
			      $controller('VehicleFileController', {$scope: $scope})
			      //inside scope VehicleFileController scope is available
			      
				$scope.sortOption = {};
				
			    $scope.authorization = function(role) {
                    return authorizationService.hasRole('seed-w20', role);
                };
              
				$scope.showTvvButton = false;
				$scope.technicalGroup = EditTGService.editedTechnicalGroup;
				if ($scope.technicalGroup !== undefined && $scope.technicalGroup !==null) {
					if ($scope.technicalGroup.addTvvForTg === true)
						$scope.showTvvButton = $scope.technicalGroup.showAddTvvButton;
					else
						$scope.showTvvButton = false;
				}
				else
					$scope.showTvvButton = false;

				/**
				 * This is called when Tvv Tab is active
				 */
				$scope.tgselectList = [];
				$scope.selectedTgList = [];
				if ($scope.regulationGroup !== undefined && $scope.regulationGroup !== null)
					if ($scope.regulationGroup.addTgForRg === true)
						$scope.showAddTgButton = $scope.regulationGroup.showAddTg;
					else
						$scope.showAddTgButton = false;
				$scope.tvv = function() {
					$scope.btnVisibile = false;

					$scope.tvvActive = true;
					$scope.tgActive = false;
					$scope.rgActive = false;

				}

				if ($scope.showAddTgButton === true) {

					$scope.tvvActive = false;
					$scope.tgActive = true;
					$scope.rgActive = false;
					$scope.btnTitle = CultureService.localize('cop.search.button.createTG');
					$scope.btnVisibile = true;
				}

				/**
				 * This is called when TG Tab is active
				 */
				$scope.envelopee = function() {
					$scope.btnTitle = CultureService.localize('cop.search.button.createTG');

					$scope.btnVisibile = true;
					$scope.tvvActive = false;
					$scope.tgActive = true;
					$scope.rgActive = false;
				}
				/**
				 * This is called when RG Tab is active
				 */
				$scope.famille = function() {
					$scope.btnTitle = CultureService.localize('cop.search.button.createRG');
					$scope.tvvActive = false;
					$scope.tgActive = false;
					$scope.rgActive = true;

					$scope.btnVisibile = true;
				}
				$scope.sort = function(option) {
					if ('alphabet' === option) {
						$scope.searchRepresentation.sortAlphabetically = true;
						$scope.searchRepresentation.sortByDate = false;
						$scope.searchTvv();
					} else if (option === 'date') {
						$scope.searchRepresentation.sortByDate = true;
						$scope.searchRepresentation.sortAlphabetically = false;
						$scope.searchTvv();
					}
				}
				/**
				 * This method fetch All Lists For dropdowns in header part
				 */
				$scope.fetchAllLists = function() {
					ProjectFamilyService.ProjectFamilyResource.getAllProjectFamilyNames(function(response) {
						$scope.projectFamilyList = response;
					

					},

					function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					EngineService.EngineResource.getAllEngineNames(function(response) {
						$scope.engineList = response;
                        
					},

					function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeErrorr'));
					});
					GearBoxService.GearBoxResource.getAllGearBoxNames(function(response) {
						$scope.gearBoxList = response;

					},

					function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});

					ESCommonService.ESResource.getAllEmissionStandardNames(function(response) {
						$scope.emissionStandardList = response;

					},

					function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					BodyWorkService.BodyWorkResource.getAllBodyWorkNames(function(response) {
						$scope.bodyWorkList = response;

					},

					function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					/*
					 * CarBrandService.CarBrandResource.getAllCarBrandData(function(response) { $scope.carBrandList = response; },
					 * 
					 * function(error) { NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError')); });
					 */
					FuelCommonService.FuelResource.getAllFuelNames(function(response) {
						$scope.fuelList = response;

					},

					function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});

					ValuedInertiaService.InertiaResource.getAllValuedInertia(function(response) {
						$scope.inertiaList = response;

					},

					function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					ValuedCoastDownService.CoastDownResource.getAllCoastdown(function(response) {
						$scope.coastDownList = response;
					}, function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					PGLimitService.PGLimitResource.getAllPGLimitsForlabel({
						label : 'Lim_CO2_Mini',
						limit : 'MIN'
					}, function(response) {
						$scope.minLimitList = response;
					}, function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					PGLimitService.PGLimitResource.getAllPGLimitsForlabel({
						label : 'Lim_CO2_Maxi',
						limit : 'MAX'
					}, function(response) {
						$scope.maxLimitList = response;
					}, function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					CountryService.CountryResource.getAllCountryNames({

					}, function(response) {
						$scope.countryList = response;
					}, function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});
					AreaService.AreaResource.getAllAreaNames({

					}, function(response) {
						$scope.typeApprovalAreaList = response;
					}, function() {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});

				};
				$scope.initializeAllLists = function() {
					$scope.fetchAllLists();

				};
				$scope.initializeAllLists();
				$scope.testModal = function() {
				    var url;
					if ($scope.tgActive) {
						 url = "/cop/CreateTechnicalGroup";
						$location.url(url);
					} else {
						 url = "/cop/CreateRegulationGroup";
						$location.url(url);
					}

				};

				$scope.resetAllFields = function() {
					$scope.searchTvvlabel = null;
					$scope.searchRepresentation = {};
					$scope.tvvList = [];
				};
				// -------------------------COMMON SEARCH END---------------------------------------------------------------------------------------------------
				// --------------------------------------------TVV SEARCH START-----------------------------------------------------------------------------------------------
				/**
				 * Search TVV based on label
				 */
				$scope.searchTvvWithLabel = function() {
					if ($scope.searchTvvlabel != null) {
						var labelToSend = $scope.searchTvvlabel;
						while (labelToSend.indexOf('~?') !== -1) {
							labelToSend = labelToSend.replace('~?', '\?');

						}
						while (labelToSend.indexOf('?') !== -1) {
							labelToSend = labelToSend.replace('?', '_');

						}

						SearchTvvService.TvvResource.findTVVsByLabel({
							searchLabel : labelToSend
						}, function(response) {
							if (response === null || response.length === 0) {
								$scope.errorMessage = CultureService.localize('cop.search.message.noResult');
								$('#errorDisplayModal').modal('show');

							} else {
								$scope.tvvList = response;
							}
						}, function() {
							$scope.tvvList = [];
							$scope.errorMessage = CultureService.localize('cop.search.message.searchError');
							$('#errorDisplayModal').modal('show');
						});
					}

				};
				/**
				 * This method calls TvvResource to search for TVV based on given criteria
				 */
				$scope.searchTvv = function() {
					// Replace things in Search
					$scope.searchRepresentation.tvvLabel = null;
					if ($scope.searchTvvlabel != null && $scope.searchTvvlabel.length > 0) {
						$scope.searchRepresentation.tvvLabel = $scope.searchTvvlabel;
						while ($scope.searchRepresentation.tvvLabel.indexOf('~?') !== -1) {
							$scope.searchRepresentation.tvvLabel = $scope.searchRepresentation.tvvLabel.replace('~?', '\%');

						}
						while ($scope.searchRepresentation.tvvLabel.indexOf('?') !== -1) {
							$scope.searchRepresentation.tvvLabel = $scope.searchRepresentation.tvvLabel.replace('?', '_');

						}
						while ($scope.searchRepresentation.tvvLabel.indexOf('%') !== -1) {
                            $scope.searchRepresentation.tvvLabel = $scope.searchRepresentation.tvvLabel.replace('%', '\?');

                        }
					}

					
					if ($scope.technicalGroup !== undefined  && $scope.technicalGroup !== null) {
						if ($scope.technicalGroup.addTvvForTg === true) {
							$scope.searchRepresentation.searchForTg = true;
						}
					}
					TvvSearchService.SearchTvvResource.findByCriteria($scope.searchRepresentation, function(success) {
						if (success === null || success.length === 0) {
							$scope.errorMessage = CultureService.localize('cop.search.message.noResult');
							$('#errorDisplayModal').modal('show');

							if ($scope.technicalGroup != null) {
								

							}
						}
						$scope.tvvList = success;
						for ( var i = 0; i < $scope.tvvList.length; i++) {
							var temp = new Date($scope.tvvList[i].modificationDate);
							var singleMonth = "";
							var singleDay = "";
							var month=temp.getMonth()+1;
						    var day= temp.getDate();
							if (month.toString().length< 2)
								singleMonth = 0;
							if (day.toString().length<2)
								singleDay = 0;

							var displayDate = singleDay + "" + (temp.getDate()) + "/" + singleMonth + (temp.getMonth() + 1) + "/" + temp.getFullYear();
							$scope.tvvList[i].displayDate = displayDate;

						}
						$scope.searchRepresentation.tvvLabel = null;

					}, function() {
						$scope.errorMessage = CultureService.localize('cop.search.message.searchError');
						$('#errorDisplayModal').modal('show');

					});

				};
				$scope.editTVV = function(tvvObj) {
					ManageTvvService.savedTvv = tvvObj;
					var url = "/cop/EditTvv";
					$location.url(url);
				};

				// -------------------------------------------------------------- Search Technical Group -------------------------------------------------------------------//
				/**
				 * This is method for Technical Group Search
				 */

				$scope.sortTG = function(option) {
					$scope.selectedAction=false;
					if ('alphabet' === option) {
						$scope.sortOption.sortAlphabetically = true;
						$scope.sortOption.sortByDate = false;
						$scope.searchTG();
					} else if (option === 'date') {
						$scope.sortOption.sortByDate = true;
						$scope.sortOption.sortAlphabetically = false;
						$scope.searchTG();
					}
				}
				$scope.resetAllFieldsTG = function() {
					$scope.searchTGlabel = null;
					$scope.searchRepresentation = {};
					$scope.tgList = [];
				};
				$scope.searchTG = function() {
					var labelToSend = null;
					if ($scope.searchTGlabel != null) {
						labelToSend = $scope.searchTGlabel;
						while (labelToSend.indexOf('~?') !== -1) {
							labelToSend = labelToSend.replace('~?', '\%');

						}
						while (labelToSend.indexOf('?') !== -1) {
							labelToSend = labelToSend.replace('?', '_');

						}
						while (labelToSend.indexOf('%') !== -1) {
                            labelToSend = labelToSend.replace('%', '\?');

                        }
					}
					var objTosend = {
						"tvvSearchRepresentation" : $scope.searchRepresentation,
						"searchLable" : labelToSend,
						'sortAlphabetically' : $scope.sortOption.sortAlphabetically,
						'sortByDate' : $scope.sortOption.sortByDate,

					};
					$scope.searchForTgRepresentation = objTosend;
					if ($scope.regulationGroup !== undefined && $scope.regulationGroup !== null) {
						if ($scope.regulationGroup.addTgForRg === true) {
							$scope.searchForTgRepresentation.searchForRG = true;

						}

					}

					SearchTechnicalGroupService.TGResource.getSearchedTechnicalGroups($scope.searchForTgRepresentation,

					function(response) {
						if (response === null || response.length === 0) {
							$scope.errorMessage = CultureService.localize('cop.search.message.noResult');
							$('#errorDisplayModal').modal('show');
							$scope.tgList = response;

						} else {
							$scope.tgList = response;

							for ( var i = 0; i < $scope.tgList.length; i++) {
								var temp = new Date($scope.tgList[i].modificationDate);
								var singleMonth = "";
								var singleDay = "";
								var month=temp.getMonth()+1;
							    var day= temp.getDate();
								if (month.toString().length< 2)
									singleMonth = 0;
								if (day.toString().length<2)
									singleDay = 0;
								var displayDate = singleDay + "" + temp.getDate() + "/" + singleMonth + (temp.getMonth() + 1) + "/" + temp.getFullYear();
								$scope.tgList[i].displayDate = displayDate;
							}

							$scope.noOfResultFound = $scope.tgList.length;
						}
						/*
						 * if ($scope.regulationGroup != undefined || $scope.regulationGroup != null) { $scope.regulationGroup.addTgForRg = false; }
						 */
					});

				};
				/**
				 * This is method for Edit Technical Group navigation After Search
				 */
				$scope.editTechnicalGroup = function(technicalGroup) {

					var objTosend = {

						"entityId" : technicalGroup.entityId,
						"description" : technicalGroup.description,
						"label" : technicalGroup.label,
						"version" : technicalGroup.version,
						"available" : technicalGroup.available,
						"techgroupstatus" : technicalGroup.techgroupstatus,
						"valid" : technicalGroup.valid,
						"samplingLabel" : technicalGroup.samplingLabel,
						"creationDate" : technicalGroup.creationDate,
						"modificationDate" : technicalGroup.modificationDate

					};
					if ($scope.regulationGroup !== undefined && $scope.regulationGroup !== null) {
						$scope.regulationGroup.showAddTg = false;
						$scope.searchForTgRepresentation.searchForRG = false;
					}

					TGService.savedTechnicalGroup = objTosend;
					var url = "/cop/EditTechnicalGroup";
					$location.url(url);
				};
				$scope.myFunction = function(keyEvent) {
					if (keyEvent.which === 13) {
						if ($scope.tvvActive)
							$scope.searchTvv();
						else if ($scope.tgActive)
							$scope.searchTG();
						else if ($scope.rgActive)
							$scope.searchRG();
					}

				};

				/** -------------------------------------------------------------ADD TVV (TG)------------------------------------------ */
				$scope.addTvvToTechnicalGroup = function(tvvObject) {
					if ($scope.technicalGroup != null) {
						var existingTVVsInTG = $scope.technicalGroup.tvvRepresentations;

						var isTvvAlreadyPresent = false;
						if(existingTVVsInTG!==undefined && existingTVVsInTG!==null)
						    {

						for ( var i = 0; i < existingTVVsInTG.length; i++) {
							if (existingTVVsInTG[i].entityId === tvvObject.entityId) {
								$scope.technicalGroup.tvvRepresentations.splice(i, 1);
								isTvvAlreadyPresent = true;
								break;
							}

						}
					}
						if (!isTvvAlreadyPresent) {
						    if($scope.technicalGroup.tvvRepresentations!==undefined && $scope.technicalGroup.tvvRepresentations!==null){
							$scope.technicalGroup.tvvRepresentations.push(tvvObject);
							$('#card' + tvvObject.entityId).addClass('custom-card-active');
						    }

						} else {
							$('#card' + tvvObject.entityId).removeClass('custom-card-active');

						}
					}

				};

				$scope.saveTgforSelectedTVV = function() {
					$scope.technicalGroup.showTvvButton=false;
					TGService.savedTechnicalGroup = $scope.technicalGroup;
					$scope.searchRepresentation.searchForTg = false;

					var url = "/cop/EditTechnicalGroup";
					$location.url(url);

				};
				/** ------------------------------------------AddTG-------------------------------------------------------------------------------------------------- */

				$scope.resetAllFieldsRG = function() {
					$scope.searchRGlabel = null;
					$scope.searchRepresentation = {};
					$scope.rgList = [];
				};
				$scope.addTG = function(tgObject) {

					var ispresent = false;
					if ($scope.tgselectList.length != 0) {
						for ( var i = 0; i < $scope.tgselectList.length; i++) {

							if (tgObject.entityId === $scope.tgselectList[i].entityId) {
								$scope.tgselectList[i].tgSelected = !$scope.tgselectList[i].tgSelected;
								ispresent = true;
								break;
							}
						}
						if (ispresent ===false) {
							tgObject.tgSelected = !tgObject.tgSelected;

							$scope.tgselectList.push(tgObject);
						}
					} else {
						tgObject.tgSelected = !tgObject.tgSelected;
						$scope.tgselectList.push(tgObject);
					}
					if (tgObject.tgSelected === true) {
						$('#card' + tgObject.entityId).addClass('custom-card-active');
					}
					if (tgObject.tgSelected === false) {
						$('#card' + tgObject.entityId).removeClass('custom-card-active');
					}

				};

				$scope.saveRGforSelectedTG = function(validateForEmissionStandard) {

					for ( var i = 0; i < $scope.tgselectList.length; i++) {
						if ($scope.tgselectList[i].tgSelected === true) {
							$scope.selectedTgList.push($scope.tgselectList[i]);

						}
					}
					var objectTosave = {
						'regulationGroupRepresentation' : $scope.regulationGroup,
						'tgRepresentationList' : $scope.selectedTgList,
						'tvvObject' : null,
						'validateForEmissionStandard' : validateForEmissionStandard
					};

					SearchRegulationGroupService.RGResource.RegulationGroupForTG(objectTosave, function(success) {

						if (success.tvvObject == null) {
							NotificationService.notify(CultureService.localize('cop.regulationGroup.message.save'));
							$scope.regulationGroup.showAddTg=false;
							SelectedTGService.selectedTGLIst = $scope.selectedTgList
							RGService.savedRegulationGroup = $scope.regulationGroup;
							var url = "/cop/EditRegulationGroup";
							$location.url(url);
						} else {
							$scope.tvvObject = success.tvvObject;
							$('#WarningModal').modal('show');
						}

					}, function(error) {
						if (error.status === 412) {

							$('#WarningModal').modal('show');

						}
					})

				};

				/** -----------------------------------------Search Regulation group--------------------------------- */

				$scope.sortRG = function(option) {
					if ('alphabet' === option) {
						$scope.sortOption.sortAlphabetically = true;
						$scope.sortOption.sortByDate = false;
						$scope.searchRG();
					} else if (option === 'date') {
						$scope.sortOption.sortByDate = true;
						$scope.sortOption.sortAlphabetically = false;
						$scope.searchRG();
					}
				}

				$scope.searchRG = function() {
					var labelToSend = "";
					if ($scope.searchRGlabel != null) {
						labelToSend = $scope.searchRGlabel;
						while (labelToSend.indexOf('~?') !== -1) {
							labelToSend = labelToSend.replace('~?', '\%');

						}
						while (labelToSend.indexOf('?') !== -1) {
							labelToSend = labelToSend.replace('?', '_');

						}
						while (labelToSend.indexOf('%') !== -1) {
                            labelToSend = labelToSend.replace('%', '\?');

                        }
					}
					var objTosend = {
						"tvvSearchRepresentation" : $scope.searchRepresentation,
						"searchLable" : labelToSend,
						'sortAlphabetically' : $scope.sortOption.sortAlphabetically,
						'sortByDate' : $scope.sortOption.sortByDate,

					};
					SearchRegulationGroupService.RGResource.searchRegulationGroup(objTosend, function(response) {
						if (response === null || response.length === 0) {
							$scope.errorMessage = CultureService.localize('cop.search.message.noResult');
							$('#errorDisplayModal').modal('show');

						} else {
							$scope.rgList = response;

							for ( var i = 0; i < $scope.rgList.length; i++) {

								var temp = new Date($scope.rgList[i].modificationDate);
								var singleMonth = "";
								var singleDay = "";
								var month=temp.getMonth()+1;
							    var day= temp.getDate();
								if (month.toString().length< 2)
									singleMonth = 0;
								if (day.toString().length<2)
									singleDay = 0;

								var displayDate = singleDay + "" + temp.getDate() + "/" + singleMonth + (temp.getMonth() + 1) + "/" + temp.getFullYear();
								$scope.rgList[i].displayDate = displayDate;
							}
							$scope.noOfResultFound = $scope.rgList.length;
						}
					})

				};

				$scope.editRegulationGroup = function(regulationGroup) {
					var regulationToSend = regulationGroup;
					LoadRegulationGroupService.RegulationResource.loadRegulationGroup({
						id : regulationToSend.entityId
					}, function(response) {
						RGService.savedRegulationGroup = response;
						var url = "/cop/EditRegulationGroup";
						$location.url(url);
					});

				};

				// ----------------------------------------------------------TVV SEARCH END--------------------------------------------------------------------------------
				// ----------------------------------------------ROUTE CHANGE-----------------------------------------

				$scope.$on('$routeChangeStart', function(next, current) {

					EditTGService.editedTechnicalGroup = {};
					$scope.showTvvButton = false;
					EditRGService.editRegulationGroup = null;
					$scope.showAddTgButton = false;

				});

				// ----------------------------------------------ROUTE CHANGE-----------------------------------------
				$scope.slideToggle = function() {
					$("#wrapper").toggleClass("toggled");
					$("#menu-toggle").toggleClass('fa-chevron-right');
					$("#content-middle").toggleClass("hidden-xs");
				};

				$("#esFactor").css('background-color', '#DBDBDB', 'color', '#fff');

			}

	]);
	search.directive('editable', function() {
		return {
			restrict : 'E',
			scope : {
				model : '='
			},
			replace : false,
			template : '<span>' + '<div class="col-lg-6 card-text-overflow " style="padding:5px 0px; height: 30px;">' + '<input type="text" ng-model="model" ng-show="edit" ng-enter="edit=false" class="form-control"></input>' + '<b><span ng-show="!edit" title="{{model}}" style=" font-size:20px;">{{model}}</span></b>' + '</div>' + '<div class="col-lg-6" style="text-align:right; padding:5px;">' + '<span ng-show="!edit">&nbsp;<span class="fa fa-pencil fa-2x"></span></span>' + '</div>' + '</span>',
			link : function(scope, element, attrs) {
				scope.edit = false;
				element.bind('click', function() {
					scope.$apply(scope.edit = true);
					element.find('input').focus();
				});
			}
		};
	});

	search.directive('ngEnter', function() {
		return function(scope, element, attrs) {

			element.bind('keypress', function(e) {
				if (e.charCode === 13 || e.keyCode === 13) {
					scope.$apply(attrs.ngEnter);
				}
			});

		};
	});

	search.directive('editcoast', function() {
		return {
			restrict : 'E',
			scope : {
				model : '='
			},
			replace : false,
			template : '<span>' + '<div class="col-lg-6 card-text-overflow"  style="padding:5px 0px; height: 30px;">' + '<input type="number" style="height: 20px !important;padding:0px !important;" ng-model="model" ng-show="edit" ng-enter="edit=false" class="form-control"></input>' + '<b><span ng-show="!edit" style="-ms-word-wrap: break-word;">{{model}}</span></b>' + '</div>' + '<div class="col-lg-6" style="text-align:right; padding:5px;">' + '<span ng-show="!edit">&nbsp;<span class="fa fa-pencil custom-fa-size"></span></span>' + '</div>' + '</span>',
			link : function(scope, element, attrs) {
				scope.edit = false;
				element.bind('click', function() {
					scope.$apply(scope.edit = true);
					element.find('input').focus();
				});
			}
		};
	});

	return {
		angularModules : [
			'search'
		]
	};
});