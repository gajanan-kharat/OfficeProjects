/**
 * 
 */

define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {

	var vehicleFileHistory = angular.module('VehicleFileHistory', [
		'copCommon'
	]);

	vehicleFileHistory.controller('VehicleFileHistoryController', [
			'$scope', '$modal', '$routeParams', 'HistoryService', 'AuthorizationService', 'CultureService', 'NotificationService', function($scope, $modal, $routeParams, HistoryService, authorizationService, cultureService, NotificationService) {

				$scope.authentication = true;
				if (authorizationService.hasRole('seed-w20', 'POCBRole') || authorizationService.hasRole('seed-w20', 'POCCRole')) {
					$scope.authentication = false;
				}
				
				$scope.vehicleFileHistoryObject = {};
				$scope.vehicleFileHistoryObject.vehicleFileHistory = [];
				$scope.vehicleFileHistoryObject.vehicleFileHistorySelected = [];
				$scope.getVehicleFileHistory = function(screenId, vehicleFileId) {
					var historyRepresentation = {
							'screenId' : screenId,
							'vehicleFileId' : vehicleFileId
					};

					HistoryService.HistroyResource.historyData(historyRepresentation, function(success) {
						$scope.vehicleFileHistoryObject.vehicleFileHistory = success;

					}, function() {
						NotificationService.notify(cultureService.localize('cop.specificCop.message.error'));

					});

				};

				$scope.getVehicleFileHistory("VEHICLE_FILE", $scope.vehicleFileId);
				var cellToolTipTemplate = '<div class="ui-grid-cell-contents" title="{{COL_FIELD}}">{{ COL_FIELD }}</div>';
				$scope.vehicleFileHistoryObject.vehicleFileHistoryGridOptions = {
					enableColumnResizing : true,
					enableFiltering : true,
					enableSorting : true,
					columnDefs : [
							{
								name : 'date',
								displayName : cultureService.localize('cop.history.date'),
								type : 'date',
								field : 'updationDate',
								cellFilter : 'date:\'dd/MM/yyyy \'',
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.updationDate;
								}
							}, {
								name : 'userId',
								displayName : cultureService.localize('cop.history.userId'),
								field : 'userId',
								enableCellEdit : false,
								cellTemplate : cellToolTipTemplate
							}, {
								name : 'userProfile',
								displayName : cultureService.localize('cop.history.userProfile'),
								field : 'userProfile',
								enableCellEdit : false,
								cellTemplate : cellToolTipTemplate
							}, {
								name : 'description',
								displayName : cultureService.localize('cop.history.description'),
								field : 'description',
								enableCellEdit : false,
								cellTemplate : cellToolTipTemplate
							}, {
								name : 'commentarie',
								displayName : 'Commentaire',
								field : 'newValue',
								enableCellEdit : false,
								cellTemplate : cellToolTipTemplate
							},

					],
					data : 'vehicleFileHistoryObject.vehicleFileHistory'
				};

			}
	]);

	return {
		angularModules : [
			'VehicleFileHistory'
		]
	};
});