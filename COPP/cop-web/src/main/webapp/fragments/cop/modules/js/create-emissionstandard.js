define([
		'require', 'module', '{angular}/angular', '{w20-core}/modules/ui'
], function(require, module, angular) {
	'use strict';
	var EMSModule = angular.module('EMSModule', [
			'ui.bootstrap', 'copCommon'
	]), config = module && module.config() || {};

	EMSModule.factory('EmissionStandardService', [
			'$resource',

			function($resource) {
				return {
					EmissionStandardResource : $resource('emissionStandard/:path/:entityId', {
						path : '@path',
						entityId : '@entityId'
					}, {

						'createEmissionStandard' : {
							method : 'POST',
							params : {
								path : 'EmissionStandard'
							},
							isArray : false
						},

					})
				};
			}
	]);

	EMSModule.factory('ESService', [
			'$rootScope', function($rootScope) {

				var savedEmissionStandard = {}

				return savedEmissionStandard;
			}
	]);

	EMSModule.controller('EMSModuleController', [
			'$scope', '$modal', 'VTService', 'FuelITypeService', 'CategoryCommonService', 'FuelCommonService', 'EmissionStandardService', 'ESService', 'CultureService', 'ESCommonService', '$window', '$location', 'NotificationService', function($scope, $modal, VTService, FuelITypeService, CategoryCommonService, FuelCommonService, EmissionStandardService, ESService, CultureService, ESCommonService, $window, $location, NotificationService, scope, element, attributes) {

				$scope.esLabel = null;
				$scope.fetchAllLists = function() {
					VTService.VehicleTechnologiesResource.getAllVehicleTechnologies(function(response) {
						$scope.vehicleTList = response;

					},

					function(error) {
						NotificationService.notify(CultureService.localize('cop.eS.message.error'));
					});

					FuelITypeService.FuelInjectionTypesResource.getAllFuelInjectionTypes(function(response) {
						$scope.fuelITList = response;

					},

					function(error) {
						NotificationService.notify(CultureService.localize('cop.eS.message.error'));
					});
					CategoryCommonService.CategoryResource.getAllCategoriesForEmission(function(response) {
						$scope.categoryList = response;

					},

					function(error) {
						NotificationService.notify(CultureService.localize('cop.eS.message.error'));
					});
					FuelCommonService.FuelResource.getAllFuels(function(response) {
						$scope.fuelList = response;

					},

					function(error) {
						NotificationService.notify(CultureService.localize('cop.eS.message.error'));
					});
				};
				$scope.initializeEMS = function() {
					$scope.fetchAllLists();
					$('#addEMSModal').modal('show');
				};
				$scope.initializeEMS();

				$scope.vehicleTList = [];
				$scope.vehicleTList.selected = [];

				$scope.fuelITList = [];
				$scope.fuelITList.selected = [];

				$scope.categoryList = [];
				$scope.categoryList.selected = [];

				$scope.fuelList = [];
				$scope.fuelList.selected = [];

				$scope.checkLabel = function() {

					if ($scope.esLabel == null || $scope.fuelList.selected == undefined || $scope.vehicleTList.selected == undefined || $scope.fuelITList.selected == undefined || $scope.categoryList.selected == undefined) {
						$('#mandatoryFieldsModal').modal('show');
					} else {
						$('#addEMSModal').modal('hide');
						ESCommonService.ESResource.getEmissionStandardsWithLabel({
							label : $scope.esLabel
						}, function(response) {
							if (response == null || response.length == 0)
								{
								
								$scope.createEMS();
								}
							else {
								$('#duplicateLabelModal').modal('show');
								document.getElementById("emissionLabel").style.border = "2px solid red";

							}
						}, function(error) {
							$scope.errorMessage = CultureService.localize('cop.emissionStandard.message.error');

							$('#errorEsDisplayModal').modal('show');
						});
						
					}

				}

				$scope.changeEmissionStandardName = function() {

					document.getElementById("emissionLabel").style.border = "1px solid #ccc";
				};
				$scope.createEMS = function() {
					$('#addEMSModal').modal('hide');
					$('body').removeClass('modal-open');
					$('.modal-backdrop').remove();
					var emissionStandard = {

						description : $scope.description,

						esLabel : $scope.esLabel,

						vehicleTechnologySet : $scope.createListArray($scope.vehicleTList.selected),
						fuelInjectionTypes : $scope.createListArray($scope.fuelITList.selected),
						categories : $scope.createListArray($scope.categoryList.selected),

						fuels : $scope.createListArray($scope.fuelList.selected)

					};
					EmissionStandardService.EmissionStandardResource.createEmissionStandard(emissionStandard, function(success) {
						if (success.esLabel) {
						    ESService.savedEmissionStandard=success;
							var url = "/cop/EditEmissionStandard";
							$location.url(url);
						} else {
							var emsObj = success;
							
							ESService.savedEmissionStandard = emsObj;
							var url = "/cop/EditEmissionStandard";
							$location.url(url);
						}
						
						
					}, function(error) {
						if (error.status == 412) {
							$scope.errorMessage = CultureService.localize('cop.emissionStandard.message.statusError');
						} else
							$scope.errorMessage = CultureService.localize('cop.emissionStandard.message.error');

						$('#errorEsDisplayModal').modal('show');

					});
					
				};

				$scope.goToEditScreen = function() {
					
					var url = "/cop/EditEmissionStandard";
					$location.url(url);
				}
				$scope.createListArray = function(selectedList) {
					$scope.listArrays = [];
				
					for ( var index = 0; index < selectedList.length; index++) {
						
						$scope.listArrays.push(new AdminObject(selectedList[index].label, selectedList[index].description, selectedList[index].entityId));
					}
					return $scope.listArrays;
				};


				$scope.cancelCreation = function(label,description,modalName) {
					$scope.label=label;
					$scope.modaName=modalName;
				    if(label!=undefined && label!=null && label.trim()!=''  ||description!=undefined && description!=null && description.trim()!='' )
					$('#cancelCreationModal').modal('show');
				    else
				    $('#'+$scope.modaName).modal('hide');
			};
			$scope.continueCreation=function()
			{
				
				 $('#'+$scope.modaName).modal('hide');
				 
			};
				function AdminObject(label, description, entityId) {
					this.label = label;
					this.description = description;
					this.entityId = entityId;
				}
				;

			}
	]);

	return {
		angularModules : [
			'EMSModule'
		]
	};
});