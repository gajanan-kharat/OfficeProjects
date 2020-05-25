/*
 * Copyright (c) PSA.
 */
define([
		'require', 'module', '{angular}/angular', '{w20-core}/modules/ui'
], function(require, module, angular) {
	'use strict';
	var copCommon = angular.module('copCommon', [
		'ui.bootstrap'
	]), config = module && module.config() || {};
	copCommon.factory('VTService', [
			'$resource',

			function($resource) {
				return {
					VehicleTechnologiesResource : $resource('vehicleTechnologyReference/:path/:entityId/:emsId', {
						path : '@path',
						entityId : '@entityId',
						emsId : '@emsId'
					}, {

						'getAllVehicleTechnologies' : {
							method : 'GET',
							params : {
								path : 'AllVehicleTechnologies'
							},
							isArray : true
						},

						'getAllVTechnologiesForES' : {
							method : 'GET',
							params : {
								path : 'AllVehicleTechnologies'
							},
							isArray : true
						},

					})
				};
			}
	]);
	copCommon.factory('FuelITypeService', [
			'$resource',

			function($resource) {
				return {
					FuelInjectionTypesResource : $resource('fuelInjectTypeReference/:path/:entityId/:emsId', {
						path : '@path',
						entityId : '@entityId',
						emsId : '@emsId'
					}, {

						'getAllFuelInjectionTypes' : {
							method : 'GET',
							params : {
								path : 'AllFuelInjectionTypes'
							},
							isArray : true
						},
						'getAllFITypesForES' : {
							method : 'GET',
							params : {
								path : 'AllFuelInjectionTypes'
							},
							isArray : true
						},

					})
				};
			}
	]);

	copCommon.factory('CategoryCommonService', [
			'$resource',

			function($resource) {
				return {
					CategoryResource : $resource('categoryReference/:path/:entityId/:emsId', {
						path : '@path',
						entityId : '@entityId',
						emsId : '@emsId'
					}, {

						'getAllCategories' : {
							method : 'GET',
							params : {
								path : 'AllCategories'
							},
							isArray : true
						},
						'getAllCategoriesForES' : {
							method : 'GET',
							params : {
								path : 'AllCategories'
							},
							isArray : true
						},
						'getAllCategoriesForEmission' : {
							method : 'GET',
							params : {
								path : 'AllCategoriesES'
							},
							isArray : true
						},
						'getAllCategoriesForTvv' : {
							method : 'GET',
							params : {
								path : 'AllCategoriesTvv'
							},
							isArray : true
						},

					})
				};
			}
	]);

	copCommon.factory('FuelCommonService', [
			'$resource',

			function($resource) {
				return {
					FuelResource : $resource('fuelReference/:path/:entityId/:emsId', {
						path : '@path',
						entityId : '@entityId',
						emsId : '@emsId'
					}, {

						'getAllFuels' : {
							method : 'GET',
							params : {
								path : 'Fuels'
							},
							isArray : true
						},
						'getAllFuelsForES' : {
							method : 'GET',
							params : {
								path : 'Fuels'
							},
							isArray : true
						},
						'getAllFuel' : {
							method : 'GET',
							params : {
								path : 'AllFuels'
							},
							isArray : true
						},

						'getAllFuelNames' : {
							method : 'GET',
							params : {
								path : 'fuelNames'
							},
							isArray : true
						},
						'getAllFuelData' : {
							method : 'GET',
							params : {
								path : 'fuelData'
							},
							isArray : true
						},

					})
				};
			}
	]);

	copCommon.factory('ESCommonService', [
			'$resource',

			function($resource) {
				return {
					ESResource : $resource('emissionStandard/:path/:label', {
						path : '@path',
						label : '@label'

					}, {

						'copyEmisssionStandard' : {
							method : 'POST',
							params : {
								path : 'copy'
							},
							isArray : false
						},
						'getEmissionStandardsWithLabel' : {
							method : 'GET',
							params : {
								path : 'EmissionStandards'
							},
							isArray : true
						},
						'deleteEmisssionStandard' : {
							method : 'POST',
							params : {
								path : 'delete'
							},
							isArray : false
						},
						'changeEmissionStandardStatus' : {
							method : 'POST',
							params : {
								path : 'EmissionStandardStatus',
								isArray : false
							}
						},
						'getAllEmissionStandardNames' : {
							method : 'GET',
							params : {
								path : 'emissionStandardNames'
							},
							isArray : true
						},
						'getAllEmissionStandardData' : {
							method : 'GET',
							params : {
								path : 'emissionStandardData'
							},
							isArray : true
						},
						'getValidEmissionStandards' : {

							method : 'GET',
							params : {
								path : 'emissionStandardsValid'
							},
							isArray : true
						},

					})
				};
			}
	]);

	// EMSService is also avilable in EMISSIONSTANDARDMAIN.JS
	// Maintaining the Same code to avoid any issue
	// TODO : REMOVE THE SERVICE FROM ANY ONE OF THE FILE
	copCommon.factory('EMSService', [
			'$resource',

			function($resource) {
				return {
					EmissionStandardResource : $resource('emissionStandard/:path/:entityId', {
						path : '@path',
						entityId : '@entityId'
					}, {

						'getAllEmissionStandards' : {

							method : 'GET',
							params : {
								path : 'AllEmissionStandards'
							},
							isArray : true
						},
						'getAllEmissionStandardsForRG' : {
							method : 'GET',
							params : {
								path : 'EmissionStandardsForRG'
							},
							isArray : true
						}
					})
				};
			}
	]);

	copCommon.factory('EmsDepTDLService', [
			'$resource',

			function($resource) {
				return {
					EmsDepTDLResource : $resource('emsDepTDL/:path/:entityId/:emsId', {
						path : '@path',
						entityId : '@entityId',
						emsId : '@emsId'

					}, {

						'saveAllChanges' : {
							method : 'POST',
							params : {
								path : 'emsDependentTDL'

							},
							isArray : true
						},
						'getEsDependentLists' : {
							method : 'GET',
							params : {
								path : 'emsDependentTDL'
							},
							isArray : true
						},
						'checkEsDependentLists' : {
							method : 'post',
							params : {
								path : 'emsDependentTDLLabel'
							},

						},

					})
				};
			}
	]);
	copCommon.factory('EmsDepTCLService', [
			'$resource',

			function($resource) {
				return {
					emsDepTCLResource : $resource('emsDepTCL/:path/:entityId/:emsId', {
						path : '@path',
						entityId : '@entityId',
						emsId : '@emsId'
					}, {

						'saveAllChanges' : {
							method : 'POST',
							params : {
								path : 'emsDependentTCL'

							},
							isArray : true
						},
						'getEsDependentLists' : {
							method : 'GET',
							params : {
								path : 'emsDependentTCL'
							},
							isArray : true
						},
						'checkEsDependentLists' : {
							method : 'POST',
							params : {
								path : 'emsDependentTCLLabel'
							},
							isArray : true
						}

					})
				};
			}
	]);

	copCommon.factory('PGListService', [
			'$resource',

			function($resource) {
				return {
					PGListResource : $resource('pollutantGas/:path/:entityId/:emsId', {
						path : '@path',
						entityId : '@entityId',
						emsId : '@emsId'
					}, {

						'getEsDependentLists' : {
							method : 'Get',
							params : {
								path : 'pollutantGasList'
							},
							isArray : true
						},
						'savePollutantGasListChanges' : {
							method : 'POST',
							params : {
								path : 'pollutantGasList'
							},
							isArray : true
						},
						'checkPGLists' : {
							method : 'POST',
							params : {
								path : 'EmsDependentPGLists'
							},
							isArray : true
						}

					})
				};
			}
	]);

	copCommon.factory('ProjectFamilyService', [
			'$resource',

			function($resource) {
				return {
					ProjectFamilyResource : $resource('FamilleReference/:path/:entityId/', {
						path : '@path',
						entityId : '@entityId'

					}, {

						'getAllProjectFamilies' : {
							method : 'Get',
							params : {
								path : 'AllProjectFamilies'
							},
							isArray : true
						},
						'getAllProjectFamilyNames' : {
							method : 'Get',
							params : {
								path : 'projectFamilyNames'
							},
							isArray : true
						}

					})
				};
			}
	]);

	copCommon.factory('EngineService', [
			'$resource',

			function($resource) {
				return {
					EngineResource : $resource('MoteurReference/:path/:entityId/', {
						path : '@path',
						entityId : '@entityId'

					}, {

						'getAllEngineData' : {
							method : 'Get',
							params : {
								path : 'engines'
							},
							isArray : true
						},
						'getAllEngineNames' : {
							method : 'Get',
							params : {
								path : 'engineNames'
							},
							isArray : true
						},
						'engineData' : {
							method : 'Get',
							params : {
								path : 'engineData'
							},
							isArray : true
						}

					})
				};
			}
	]);

	copCommon.factory('GearBoxService', [
			'$resource',

			function($resource) {
				return {
					GearBoxResource : $resource('BolteReference/:path/:entityId/', {
						path : '@path',
						entityId : '@entityId'

					}, {

						'getAllGearBoxData' : {
							method : 'Get',
							params : {
								path : 'gearBoxes'
							},
							isArray : true
						},
						'getAllGearBoxNames' : {
							method : 'Get',
							params : {
								path : 'gearBoxNames'
							},
							isArray : true
						},

					})
				};
			}
	]);
	copCommon.factory('BodyWorkService', [
			'$resource',

			function($resource) {
				return {
					BodyWorkResource : $resource('SilhouetteReference/:path/:entityId/', {
						path : '@path',
						entityId : '@entityId'

					}, {

						'getAllBodyWorkData' : {
							method : 'Get',
							params : {
								path : 'bodyWorks'
							},
							isArray : true
						},
						'getAllBodyWorkNames' : {
							method : 'Get',
							params : {
								path : 'bodyWorkNames'
							},
							isArray : true

						}

					})
				};
			}
	]);

	copCommon.factory('CarBrandService', [
			'$resource',

			function($resource) {
				return {
					CarBrandResource : $resource('ConstructeurReference/:path/:entityId/', {
						path : '@path',
						entityId : '@entityId'

					}, {

						'getAllCarBrandData' : {
							method : 'Get',
							params : {
								path : 'carBrands'
							},
							isArray : true
						}

					})
				};
			}
	]);

	copCommon.factory('FuelTypeService', [
			'$resource',

			function($resource) {
				return {
					FuelTypeResource : $resource('FuelTypeReference/:path/:entityId/', {
						path : '@path',
						entityId : '@entityId'

					}, {

						'getAllFuelTypeData' : {
							method : 'Get',
							params : {
								path : 'fuelTypes'
							},
							isArray : true
						}

					})
				};
			}
	]);

	copCommon.factory('ValuedInertiaService', [

			'$resource',

			function($resource) {
				return {
					InertiaResource : $resource('valuedInertia/:path/:entityId/', {
						path : '@path',
						entityId : '@entityId'

					}, {

						'getAllValuedInertia' : {
							method : 'Get',
							params : {
								path : 'allValues'
							},
							isArray : true
						}

					})
				};
			}
	]);
	copCommon.factory('ValuedCoastDownService', [
			'$resource',

			function($resource) {
				return {
					CoastDownResource : $resource('valuedCoastdown/:path', {
						path : '@path',

					}, {
						'getAllCoastdown' : {
							method : 'GET',
							params : {
								path : 'coastDownLabels'

							},
							isArray : true
						},

					})
				};
			}
	]);

	copCommon.factory('PGLimitService', [
			'$resource',

			function($resource) {
				return {
					PGLimitResource : $resource('pollutantGasLimit/:path/:label/:limit', {
						path : '@path',
						label : '@label',
						limit : '@limit'
					}, {

						'getAllPGLimitsForlabel' : {
							method : 'Get',
							params : {
								path : 'PGLimit'
							},
							isArray : true
						}

					})
				};
			}
	]);
	copCommon.factory('CountryService', [
			'$resource',

			function($resource) {
				return {
					CountryResource : $resource('PaysReference/:path', {

					}, {

						'getAllCountryNames' : {
							method : 'Get',
							params : {
								path : 'countryNames'
							},
							isArray : true
						},
						'getAllCountryData' : {
							method : 'Get',
							params : {
								path : 'CountryData'
							},
							isArray : true
						}

					})
				};
			}
	]);

	copCommon.factory('AreaService', [
			'$resource',

			function($resource) {
				return {
					AreaResource : $resource('typeApprovalAreaReference/:path', {

					}, {

						'getAllAreaNames' : {
							method : 'Get',
							params : {
								path : 'areaNames'
							},
							isArray : true
						},
						'getAllAreaData' : {
							method : 'Get',
							params : {
								path : 'typeApprovalAreaData'
							},
							isArray : true
						}

					})
				};
			}
	]);

	copCommon.factory('TVVUtilService', [
			'$resource',

			function($resource) {
				return {
					TvvResource : $resource('tvvResource/:path/:entityId/:label', {
						path : '@path',
						entityId : '@entityId',
						label : '@label'

					}, {

						'getTVVsWithLabel' : {
							method : 'POST',
							params : {
								path : 'tvv'
							},
							isArray : true
						},
						'getAllTvvData' : {
							method : 'GET',
							params : {
								path : 'tvvDetails'
							},
							isArray : false

						},
						'saveTVV' : {
							method : 'POST',
							params : {
								path : 'Tvv'
							},
							isArray : false

						},

					})
				};
			}
	]);

	copCommon.factory('TVVService', [
			'$resource',

			function($resource) {
				return {
					TvvResource : $resource('tvvResource/:path', {

					}, {

						'copyTvv' : {
							method : 'POST',
							params : {
								path : 'tvvDuplicate'
							},
							isArray : false
						}

					})
				};
			}
	]);

	copCommon.factory('DataTypeService', [
			'$resource',

			function($resource) {
				return {
					dataTypeResource : $resource('genericData/:path', {

					}, {

						'getAllDataTypes' : {
							method : 'Get',
							params : {
								path : 'dataTypes'
							},
							isArray : true
						},
						'getAllDataTypesforPFS' : {
							method : 'Get',
							params : {
								path : 'pfsdataTypes'
							},
							isArray : true
						}

					})
				};
			}
	]);

	/* History Factory */
	copCommon.factory('HistoryService', [
			'$resource',

			function($resource) {
				return {
					HistroyResource : $resource('traceabilityReference/:path/', {
						path : '@path',

					}, {
						'historyData' : {
							method : 'POST',
							params : {
								path : 'historyData'
							},
							isArray : true
						}

					})
				};
			}
	]);

	/* Export BCE */
	copCommon.factory('ExportService', [
			'$resource', function($resource) {
				return {
					ExportResource : $resource('export/:path', {
						path : '@path',

					}, {
						'exportBCE' : {
							method : 'GET',
							params : {
								path : 'exportBCE'
							},
						}

					})
				};
			}
	]);
	
	/* PreparationFile PDF */
	copCommon.factory('PreparationListPdfService', [
	                        			'$resource', function($resource) {
	                        				return {
	                        					PdfResource : $resource('preparationFile/:path/:vehicleFileid', {
	                        						path : '@path',
	                        						vehicleFileId : '@vehicleFileId', 

	                        					}, {
	                        						'ReceptionFilePdfGenerate' : {
	                        							method : 'GET',
	                        							params : {
	                        								path : 'ReceptionFilePdfGenerate'
	                        							},
	                        						}

	                        					})
	                        				};
	                        			}
	                        	]);

	copCommon.factory('StatusCommonService', [
			'$resource', function($resource) {
				return {
					StatusResource : $resource('Status/:path/', {
						path : '@path'

					}, {
						'statusTestNatures' : {
							method : 'GET',
							params : {
								path : 'statusTestNatures'
							},
							isArray : true
						}

					})
				};
			}
	]);
	copCommon.factory('FactoryCommonService', [
			'$resource', function($resource) {
				return {
					FactoryResource : $resource('UsineReference/:path/', {
						path : '@path'

					}, {
						'getFactories' : {
							method : 'GET',
							params : {
								path : 'Factories'
							},
							isArray : true
						}

					})
				};
			}
	]);

	copCommon.factory('syncDataService', [
	                                           '$resource', function($resource) {
	                                               return {
	                                                   offlineResource : $resource('basket/:path/', {
	                                                       path : '@path'

	                                                   }, {
	                                                       'saveOfflineChanges' : {
	                                                           method : 'POST',
	                                                           params : {
	                                                               path : 'syncOfflineData'
	                                                           }
	                                                          
	                                                       }

	                                                   })
	                                               };
	                                           }
	                                           ]);

	copCommon.filter('decimal2comma', [
		function() {
			return function(input) {
				var ret = (input) ? input.toString().replace(".", ",") : null;
				if (ret) {
					var decArr = ret.split(",");

					if (decArr.length > 1) {
						var dec = decArr[1].length;
						if (dec === 1) {
							ret += "0";
						}
					}// this is to show prices like 12,20 and not 12,2
				}
				return ret;
			};
		}
	]);

	copCommon.filter('comma2decimal', [
		function() {
			return function(input) {
				var ret = (input) ? input.toString().trim().replace(",", ".") : null;
				return parseFloat(ret);
			};
		}
	]);

	copCommon.directive('frdecimal2', [
			'$filter', function($filter) {

				return {
					restrict : 'A',
					require : 'ngModel',
					link : function(scope, element, attrs, ngModelController) {

						ngModelController.$parsers.push(function(data) {
							data = $filter('comma2decimal')(data);
							return data;

						});

						ngModelController.$formatters.push(function(data) {
							data = $filter('decimal2comma')(data);
							return data;

						});
					}
				};
			}
	]);
	
    if (navigator.onLine) {
        
        // Do nothing when online.
        
    } else {
  
        $window.location.href = 'fragments/cop/views/offline-views/offline-home.html';
    }

    window.applicationCache.addEventListener('checking',logEvent,false);
    window.applicationCache.addEventListener('noupdate',logEvent,false);
    window.applicationCache.addEventListener('downloading',logEvent,false);
    window.applicationCache.addEventListener('cached',logEvent,false);
    window.applicationCache.addEventListener('updateready',logEvent,false);
    window.applicationCache.addEventListener('obsolete',logEvent,false);
    window.applicationCache.addEventListener('error',logEvent,false);


    function logEvent(event) {
        setTimeout(function(){
            if(event.type === "updateready" || "downloading" ){
                window.applicationCache.swapCache();
                setTimeout(function(){
                    window.location.reload();
                },3200);
            }
        },4200);
    }


	
	/*
	 * copCommon.controller('copCommonController', [ '$scope', '$modal', '$http', function($scope, $modal, $http, scope, element, attributes) { } ]);
	 */
	return {
		angularModules : [
			'copCommon'
		]
	};
});
