define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {
	var TechnicalGroup = angular.module('TechnicalGroup', [
			'ngResource', 'copCommon'
	]);
	TechnicalGroup.factory('TechnicalGroupService', [
			'$resource', function($resource) {
				return {
					TechnicalGroupResource : $resource('TechnicalGroup/:path/:entityId', {

						entityId : '@entityId',
						path : '@path',

					}, {
						'saveTechnicalGroup' : {
							method : 'POST',
							params : {

								path : 'TechnicalGroupNew'

							},

						},

						'deleteTechnicalGroup' : {
							method : 'GET',
							params : {
								path : 'delete'
							},
							isArray : false
						},
						'getTechnicalGroup' : {
							method : 'GET',
							params : {
								path : 'TechnicalGroup'
							},
							isArray : false
						},
						'getMaxVersion' : {
							method : 'POST',
							params : {
								path : 'MaxVersion'
							},

						},
						'getStatusForTechnicalGroup' : {
							method : 'GET',
							params : {
								path : 'TechnicalGroupStatus'
							},
							isArray : true
						},
						'deleteTechnicalGroupInRg' : {
							method : 'GET',
							params : {
								path : 'delete'
							},
							isArray : true
						}

					})
				};

			}
	]);

	TechnicalGroup.controller('TechnicalGroupController', [
			'$scope', 'TechnicalGroupService', 'CultureService', '$location', 'TGService', 'NotificationService', 'AuthorizationService', function($scope, TechnicalGroupService, CultureService, $location, TGService, NotificationService, authorizationService) {

				/*----------------------------------------Authorization----------------------------------------*/
				$scope.authorization = function(role) {
					return authorizationService.hasRole('seed-w20', role);
				};

				/*-----------------------------------------------*/

				$('#createTechnicalGroupModal').modal('show');

				$scope.changeTechnicalGroupName = function() {

					document.getElementById("tgLabel").style.border = "1px solid #ccc";
				};

				$scope.addTechnicalGroup = function() {
					if ($scope.technicalGroup == undefined || $scope.technicalGroup.label == "" || $scope.technicalGroup.label == undefined)

						return;
					var objToSave = {

						"description" : $scope.technicalGroup.description,
						"label" : $scope.technicalGroup.label

					}

					TechnicalGroupService.TechnicalGroupResource.saveTechnicalGroup(objToSave, function(response) {
						$scope.technicalGroup = response;
						if ($scope.technicalGroup.available == true) {

							// NotificationService.notify(CultureService.localize("cop.technicalgroup.addStatus.duplicateerrorMessage"));

							document.getElementById("tgLabel").style.border = "2px solid red";
							$('#modifyTechnicalGroupModal').modal('show');

						} else {
							$('#createTechnicalGroupModal').modal('hide');

							TGService.savedTechnicalGroup = $scope.technicalGroup;
							var url = "/cop/EditTechnicalGroup";
							$location.url(url);
						}

					}, function(error) {
						NotificationService.notify(CultureService.localize("cop.technicalgroup.addStatus.errorMessage"));
					});
				};
				$scope.hideModifyTechnicalGroupModal = function() {
					$('#modifyTechnicalGroupModal').modal('hide');
				};
				$scope.cancelCreation = function() {

					if ($scope.technicalGroup != undefined && ($scope.technicalGroup.label != undefined || $scope.technicalGroup.label != null || $scope.technicalGroup.description != undefined || $scope.technicalGroup.description != null))
						$('#cancelCreationModal').modal('show');
					else
						$('#createTechnicalGroupModal').modal('hide');
				};

				$scope.showEditTechGroupModal = function() {
					$('#modifyTechnicalGroupModal').modal('hide');
					$('#createTechnicalGroupModal').modal('hide');
					TGService.savedTechnicalGroup = $scope.technicalGroup;
					var url = "/cop/EditTechnicalGroup";
					$location.url(url);

				}

				$scope.editModal = function() {
					$('#createTechnicalGroupModal').modal('show');
				};
				$scope.continueCreation = function() {
					$('#createTechnicalGroupModal').modal('hide');
				}

			}
	]);

	return {
		angularModules : [
			'TechnicalGroup'
		]
	};
});