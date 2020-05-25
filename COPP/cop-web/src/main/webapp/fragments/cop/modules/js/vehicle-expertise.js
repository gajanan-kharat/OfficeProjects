define([
		'require', 'module', '{angular}/angular', '{w20-core}/modules/ui'
], function(require, module, angular) {
	'use strict';
	var vehicleExpertiseModule = angular.module('vehicleExpertiseModule', [
			'ui.bootstrap', 'copCommon'
	]);
	

	vehicleExpertiseModule.factory('VehicleExpertiseActionService',['$resource',function($resource){
		return {
			
			vehicleExpertiseActionResource :$resource('VehicleExpertise/:path/:entityId/:comment',{
				path:'@path',
				entityId:'@entityId',
				comment:'@comment'
			},{				
				setComplete :{
					method:'POST',
					params:{
						path:'vehicleComplete'
					}
				}
				
			})
			
		};
	}]);
	vehicleExpertiseModule.controller('vehicleExpertiseController', [

	                                             			'$scope', '$modal', '$routeParams', 'CultureService'
	                                             			, 'NotificationService', 'DataTypeService', 'HistoryService', 'AuthorizationService', 'VehicleExpertiseActionService', 
	                                             			function($scope, $modal, $routeParams,CultureService, NotificationService,
	                                             					DataTypeService, HistoryService, authorizationService, VehicleExpertiseActionService) {

	                                             				/*----------------------------------------Authorization----------------------------------------*/
	                                             				var authentication = false;
	                                             				if (authorizationService.hasRole('seed-w20', 'POCMRole')) {
	                                             					authentication = true;
	                                             				}
	                                             				$scope.authorization = function(role) {
	                                             					return authorizationService.hasRole('seed-w20', role);
	                                             				};
	                                             				
	                                             				
	                                             				
	                                             				$scope.action={};
	                                             				$scope.actionList=[{label:'cop.vehicle.expertise.complete'},
	                                             				                   {label:'cop.vehicle.expertise.quarantine'},
	                                             				                   {label:'cop.vehicle.expertise.returnVehicle'},
	                                             				                   {label:'cop.vehicle.expertise.removeQuarantine'}];
	                                             				
	                                             				
	                                             				$scope.setAction=function(value){
	                                             					alert("value--->"+JSON.stringify(value));
	                                             					alert("$scope.vehicleFileId--->"+JSON.stringify($scope.vehicleFileId));
	                                             					
	                                             					
	                                             					switch (value.label) {
	                                             					  case 'cop.vehicle.expertise.complete':
	                                             						 VehicleExpertiseActionService.vehicleExpertiseActionResource.setComplete({
	                                             							 entityId:$scope.vehicleFileId,
	                                             							 comment:"cop.vehicle.expertise.complete"},function(){
	                                             							 
	                                             						 },function(error){
	                                             							NotificationService.notify(CultureService.localize('cop.ems.message.copyError'));
	                                             						 });
	                                             					    break;
	                                             					  case 'cop.vehicle.expertise.quarantine':
	                                             					    console.log('Apples are $0.32 a pound.');
	                                             					    break;
	                                             					  case 'cop.vehicle.expertise.returnVehicle':
	                                             					    console.log('Bananas are $0.48 a pound.');
	                                             					    break;
	                                             					  case 'cop.vehicle.expertise.removeQuarantine':
	                                             					    console.log('Cherries are $3.00 a pound.');
	                                             					    break;
	                                             					
	                                             				/*
																 * default: console.log('Sorry, we are out of ' + expr + '.');
																 */
	                                             					}
	
	                                             					
	                                             				}
	                                             				
	                                             				
	                                             				
	                                             				
	                                             				
	                                             			}]);
	
	
	

	return {
		angularModules : [
			'vehicleExpertiseModule'
		]
	};
});
