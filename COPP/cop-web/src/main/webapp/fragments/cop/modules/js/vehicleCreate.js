/*
 * Copyright (c) PSA.
 */

define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {
	'use strict';

	var vehicleCreationListItems = angular.module('vehicleCreationList', [
			'ui.bootstrap', 'copCommon'
	]);

	vehicleCreationListItems.factory('vehicleUpdateService', [
			'$resource', function($resource) {

				return {
					vehicleResource : $resource('vehicle/:path/:vehicleFileId', {
						path : '@path',
						vehicleFileId : '@vehicleFileId'

					}, {
						'getVehicle' : {
							mathod : 'GET',
							params : {
								path : 'vehicledetail'
							}
						}
					})
				}
			}
	]);
	vehicleCreationListItems.factory('CarFactoryListService', [
			'$resource', function($resource) {

				return {

					carFactoryListResource : $resource('vehicle/:path/:tcId', {
						path : '@path',
						tcId : '@tcId'
					}, {
						'getTVVFactory' : {
							method : 'GET',
							params : {
								path : 'tvvfactorybytc'

							},
							isArray : true
						}
					})
				}
			}
	]);
	vehicleCreationListItems.factory('UpdateVehicleService', [
			'$resource', function($resource) {

				return {

					updateVehicleResource : $resource('vehicle/:path', {
						path : '@path'
					}, {
						'updateVehicle' : {
							method : 'POST',
							params : {
								path : 'vehicleobject'

							},
							isArray : false
						}
					})
				}
			}
	]);
	vehicleCreationListItems.controller('vehicleCreateController', [
			'$scope', 'NotificationService', 'CultureService', '$routeParams', 'AuthorizationService', 'vehicleUpdateService', 'CarFactoryListService', 'UpdateVehicleService', function($scope, NotificationService, CultureService, $routeParams, authorizationService, vehicleUpdateService, carFactoryListService, UpdateVehicleService) {
				$scope.tvvFactoryList = [];
				var technicalCaseId = null;
				$scope.vehicle = {};
				var isEdited = false;
				$scope.vehicleFile = {};
				$scope.disableEdit = false;
				$scope.placeHolderFac = "";
				$scope.authorization = function() {

					if (authorizationService.hasRole('seed-w20', 'POCERole') || authorizationService.hasRole('seed-w20', 'POCFRole') || authorizationService.hasRole('seed-w20', 'POCHRole') || authorizationService.hasRole('seed-w20', 'POCGRole')) {
						return false;
					}
					return true;

				};

				/** * load vehicle file data ** */
				$scope.loadVehicleFile = function() {
					vehicleUpdateService.vehicleResource.getVehicle({
						vehicleFileId : $scope.vehicleFileId
					}, function(response) {
						$scope.vehicleFile = response;
						$scope.vehicle = response.vehicleRepresentation;
						technicalCaseId = $scope.vehicle.technicalCaseId;
						$scope.loadFactoryList();
						if ($scope.vehicle.carFactoryrepresentation !== null && $scope.vehicle.carFactoryrepresentation.entityId !== null) {
							$scope.placeHolderFac = $scope.vehicle.carFactoryrepresentation.carFactoryLabel;

						} else {
							$scope.placeHolderFac = "Select Factory";
						}
						if ($scope.vehicleFile.vehicleFileStatusLabelEng === "Archived" || $scope.inBasket === "false" || $scope.authorization === true) {
							$scope.disableEdit = true;
						}

					}, function() {
					});
				};

				$scope.loadVehicleFile();
				$scope.loadFactoryList = function() {
					carFactoryListService.carFactoryListResource.getTVVFactory({
						tcId : technicalCaseId
					}, function(responce) {
						$scope.tvvFactoryList = responce;
					}, function() {
					});

				};
				$scope.updateVehicle = function() {
					if (isEdited) {
						UpdateVehicleService.updateVehicleResource.updateVehicle($scope.vehicle, function(response) {
							$scope.vehicle = response;
							isEdited = false;
							NotificationService.notify(CultureService.localize('cop.vehicle.update.successmsg'));
						}, function() {
						});
					}
				};
				$scope.onFactoryChange = function(factoryObject) {
					isEdited = true;
					$scope.vehicle.carFactoryId = factoryObject.entityId;
				}
				$scope.onValueChange = function() {
					isEdited = true;
				}
				/* validation for float */
				$scope.floatCheck = function(value) {
					var INTEGER_REG = new RegExp("^[-]?[0-9/,.]+$");
					if (!INTEGER_REG.test(value)) {
						value = value.substring(0, value.length - 1);
					} else
						isEdited = true;
					return value;
				};
			}
	]);

	return {
		angularModules : [
			'vehicleCreationList'
		]
	};

});
