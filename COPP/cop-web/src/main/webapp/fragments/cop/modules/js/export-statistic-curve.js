define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {

	
	var exportStatisticalCurve = angular.module('ExportStatisticalCurve', []);

	exportStatisticalCurve.factory('ExportStatisticalCurveService', [
			'$resource',

			function($resource) {
				return {
					ExportStatisticalCurveResource : $resource('ExportStatisticalCurveReference/:path', {
						path : '@path'
					}, {

						'saveExportStatisticalCurve' : {
							method : 'POST',
							params : {
								path : 'ExportStatisticalCurve'
							}

						},
						
						'sendPdf' : {
							method : 'POST',
							params : {
								path : 'ExportSCPdf'
							}

						},
						
						'sendExcel' : {
							method : 'POST',
							params : {
								path : 'ExportSCExcel'
							}

						},
						

						
					})
				};
			}
	]);

	exportStatisticalCurve.controller('ExportStatisticalCurveController', [
			'$scope', '$resource', '$http', '$location', '$route', '$routeParams', '$interval', '$templateCache', 'ExportStatisticalCurveService', 'NotificationService', 'CultureService', 'AuthenticationService', 'HistoryService','AuthorizationService', function($scope, $resource, $http, $location, $route, $routeParams, $interval, $templateCache, ExportStatisticalCurveService, NotificationService, CultureService, authenticationService, HistoryService,authorizationService) {
				$scope.data={};
				$scope.pollutantGasList=[];
				var test=false;
				$scope.auth = true;
				$scope.selection = [];
				$scope.file="pdf";
			
				$scope.showScBox = function(){
					$scope.pollutantGasList=$scope.data.tvvValuedPollutantGasLimitList;
			           $('#showSCBox').modal('show')
			       };

				  // toggle selection for a given fruit by name
				  $scope.toggleSelection = function toggleSelection(pgName) {
				    var idx = $scope.selection.indexOf(pgName);
				  
				    // is currently selected
				    if (idx > -1) {
				      $scope.selection.splice(idx, 1);
				    }

				    // is newly selected
				    else {
				      $scope.selection.push(pgName);
				    }
				  };
				
				
				  $scope.selectAll=function(){
					  if(!test){
						  $scope.selection= $scope.pollutantGasList.map(function(pg) {return pg.pgLabel.label;});
						  test=!test;
					  }else{
						  $scope.selection=[];
					  }
	 
				  }
				
				
				
				$scope.saveExportStatisticalCurve = function() {
					var objectToSend = {
							'entityId' : 1
						};

			ExportStatisticalCurveService.ExportStatisticalCurveResource.saveExportStatisticalCurve(objectToSend, function(success) {

									NotificationService.notify(CultureService.localize('cop.specificCop.message.successMessage'));
									$scope.data=success;
									
									$scope.showScBox ();
						
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.message.error'));
						});

					}
				
				$scope.sendFile=function(){
			
					var objectToSend = {
							'statCurveData' : $scope.data,
							'choiceList':  $scope.selection
						};
					
					if($scope.file==="pdf"){
						
						ExportStatisticalCurveService.ExportStatisticalCurveResource.sendPdf(objectToSend,function(){
							window.open("./ExportStatisticalCurveReference/exportStatCurvePdf", "_self");
							NotificationService.notify(CultureService.localize('cop.specificCop.message.successMessage'));
						},function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.message.error'));
						});

					}
					
					if ($scope.file==="excel"){
					
						ExportStatisticalCurveService.ExportStatisticalCurveResource.sendExcel(objectToSend,function(){
							window.open("./ExportStatisticalCurveReference/exportStatCurveExcel", "_self");	
							NotificationService.notify(CultureService.localize('cop.specificCop.message.successMessage'));
							
						},function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.message.error'));
						});
					}
					
					
				}
				
				

		$scope.saveExportStatisticalCurve();
		
			}
	]);

	return {
		angularModules : [
			'ExportStatisticalCurve'
		]
	};
});
