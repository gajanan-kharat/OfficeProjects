define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {
	var RegulationGroup = angular.module('RegulationGroup', [
			'ngResource', 'copCommon', 'EditRegulationGroup'
	]);

	RegulationGroup.factory('RegulationGroupService', [
			'$resource', function($resource) {
				return {
					RegulationGroupResource : $resource('RegulationGroup/:path/:entityId', {

						path : '@path',
						entityId : '@entityId'

					}, {
						'saveRegulationGroup' : {
							method : 'POST',
							params : {

								path : 'RegulationGroupNew'

							},

						},
						'saveEditedRegulationGroup' : {

							method : 'POST',
							params : {

								path : 'RegulationGroup'

							},

						},
						'deleteRegulationGroup' : {
							method : 'GET',
							params : {

								path : 'RegulationGrp'

							},
							isArray : true
						},
						'getAllRegulationGroups' : {
							method : 'Get',
							params : {
								path : 'AllRegulationGroups'
							},
							isArray : true
						}
					})
				};
			}
	])

	RegulationGroup.controller("RegulationGroupController", [
			'$scope', 'RegulationGroupService', 'CultureService', 'RGService', '$location', 'NotificationService', function($scope, RegulationGroupService, CultureService, RGService, $location, NotificationService) {
				$('#createRegulationGroupModal').modal('show');

				$scope.changeRegulationGroupName = function() {

					document.getElementById("rgLabel").style.border = "1px solid #ccc";
				};
				$scope.addRegulationGroup = function() {
					if ($scope.regulationGroup == undefined || $scope.regulationGroup.label == "" || $scope.regulationGroup.label == undefined)

						return;
					var objToSave = {

						"description" : $scope.regulationGroup.description,
						"label" : $scope.regulationGroup.label

					}
					RegulationGroupService.RegulationGroupResource.saveRegulationGroup(objToSave, function(response) {
						$scope.regulationGroup = response;
						if ($scope.regulationGroup.available == true) {

							$('#modifyrRegulationGroupModal').modal('show');
							document.getElementById("rgLabel").style.border = "2px solid red";

						} else {
							NotificationService.notify(CultureService.localize('cop.regulationGroup.message.save'));
							$('#createRegulationGroupModal').modal('hide');
							RGService.savedRegulationGroup = $scope.regulationGroup;
							var url = "/cop/EditRegulationGroup";
							$location.url(url);
						}
					}, function(error) {
						NotificationService.notify(CultureService.localize("cop.regulationGroup.addStatus.errorMessage"));
					})
				};
				$scope.hideCreateRGModal = function() {
					$('#modifyrRegulationGroupModal').modal('hide');
				};
				$scope.cancelCreation = function() {
					  if($scope.regulationGroup!=undefined && ($scope.regulationGroup.label!=undefined||$scope.regulationGroup.label!=null||$scope.regulationGroup.description!=undefined||$scope.regulationGroup.description!=null))
							$('#cancelCreationModal').modal('show');
						    else
						    $('#createRegulationGroupModal').modal('hide');
				};
				$scope.showEditRegulationGroupModal = function() {
				    $('#modifyrRegulationGroupModal').modal('hide');
				    $('#createRegulationGroupModal').modal('hide');
					RGService.savedRegulationGroup = $scope.regulationGroup;
					var url = "/cop/EditRegulationGroup";
					$location.url(url);
				};
				$scope.createRg = function() {
					$('#createRegulationGroupModal').modal('show');
				};
				$scope.continueCreation=function()
				{
					 $('#createRegulationGroupModal').modal('hide');
				}

			}
	])
	return {
		angularModules : [
			'RegulationGroup'
		]
	};
});