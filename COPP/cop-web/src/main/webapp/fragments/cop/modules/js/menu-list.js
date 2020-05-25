/*
 * Copyright (c) PSA.
 */

define([
        'require', 'module', '{angular}/angular'
        ], function(require, module, angular) {


	var menuListItems = angular.module('menuList', []);


	menuListItems.controller('menuListController', ['$scope',
	                                                'NotificationService',
	                                                'CultureService',
	                                                '$routeParams',
	                                                'AuthorizationService',
	                                                function($scope,NotificationService, CultureService,$routeParams,authorizationService) {

	    $scope.disableResultSet = true;
		/**
		 * Checks a value is not null and not empty
		 */
		$scope.isNotEmptyisNotNull = function(_param) {
			if (null === _param || "" === _param) {
				return false;
			}
			return true;
		};
		//-------- check for parameter passed in url
		$scope.checkforRoutes = function() {

			if ($scope.isNotEmptyisNotNull($routeParams.vehicleFileId) && $routeParams.vehicleFileId !== undefined) {
				$scope.vehicleFileId = $routeParams.vehicleFileId;
				$scope.inBasket = $routeParams.inBasket
			}
			if($routeParams.archivedVehicelFile!==undefined && $scope.isNotEmptyisNotNull($routeParams.archivedVehicelFile)){
			    $scope.vehicleFileId = $routeParams.archivedVehicelFile;
			    $('#historyTab').removeClass('active');
			    $('#history').removeClass('in active');
                $('#resultSetTab').addClass('active');
                $('#resultSet').addClass('in active');
                $scope.disableResultSet = false;
			}
			
		};

		$scope.checkforRoutes();
//		--------------Vehicle search ends---------




	}]);

	return {
		angularModules : [
		                  'menuList'
		                  ]
	};

});
