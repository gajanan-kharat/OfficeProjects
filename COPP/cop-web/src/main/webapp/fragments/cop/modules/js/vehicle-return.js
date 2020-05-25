/**
 * 
 */

define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {

	var returnVehicle = angular.module('returnVehicle', [
		'copCommon'
	]);

	returnVehicle.factory('ReturnVehicleService', [
			'$resource', function($resource) {
				return {
					ReturnVehicleResource : $resource('return/:path/:vehicleFileId', {
						path : '@path',
						vehicleFileId : '@vehicleFileId'
					}, {
						'getAllReturnVehicle' : {
							method : 'GET',
							params : {
								path : 'returnVehicles'
							},
							isArray : true
						},
						'returnVehicle' : {
							method : 'POST',
							params : {
								path : 'returnVehicle'
							}
						}

					})
				}
			}
	])

	returnVehicle.controller('ReturnVehicleController', [
			'$scope', '$modal', '$routeParams', 'HistoryService', 'AuthorizationService', 'CultureService', 'NotificationService', 'ReturnVehicleService', function($scope, $modal, $routeParams, HistoryService, authorizationService, cultureService, NotificationService, returnVehicleService) {

				$scope.authentication = true;
				if (authorizationService.hasRole('seed-w20', 'POCBRole') || authorizationService.hasRole('seed-w20', 'POCCRole')) {
					$scope.authentication = false;
				}

				$scope.returnVehiclesList = []

				var getReturnVehicles = function() {

					returnVehicleService.ReturnVehicleResource.getAllReturnVehicle(function(response) {
						$scope.returnVehiclesList = response;
						$scope.typeTestGroup = [];

						for (var i = 0; i < response.length; i++) {

							var typeTestCheck = true;
							for (var j = 0; j < $scope.typeTestGroup.length; j++) {
								if ($scope.typeTestGroup[j].typeOfTestLabel === response[i].typeOfTestLabel) {
									$scope.typeTestGroup[j].vehicleFileDetail.push(response[i]);
									typeTestCheck = false;
									break;
								}
							}

							if (typeTestCheck) {
								var temp = {
									typeOfTestLabel : response[i].typeOfTestLabel,
									vehicleFileDetail : [
										response[i]
									]
								};
								$scope.typeTestGroup.push(temp);
							}
						}

					}, function() {

					})
				};
				getReturnVehicles();

				$scope.deleteConfirmation = function(vehicleFileId) {
					$scope.vfEntityId = vehicleFileId;
					$('#deleteVehicle').modal('show');
				};

				$scope.returnVehicleFile = function() {
					returnVehicleService.ReturnVehicleResource.returnVehicle({
						vehicleFileId : $scope.vfEntityId
					}, function() {
						getReturnVehicles();
						NotificationService.notify("VehicleFile Successfully Returned")
					}, function() {
						NotificationService.notify("Vehicle File can not be returned, Previous status is unknown of Vehicle File")
					})

				};
				
				$scope.showAddGPLModel = function() {
					alert("Inside Method");
					$('#vehicleCreate').modal('show');
				};
				
			}

	]);

	return {
		angularModules : [
			'returnVehicle'
		]
	};
});