/*
 * Copyright (c) PSA.
 */

define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {
	'use strict';

	var referentialManagement = angular.module('CopManagement', [
			'ui.bootstrap', 'copCommon'
	]), config = module && module.config() || {};

	referentialManagement.controller('HomeController', [
			'$scope', '$window', 'ExportService', 'CultureService', 'NotificationService', function($scope, $window, ExportService, CultureService, NotificationService) {

				// This Method is being called from Cop-SideBar.html
				$scope.exportBCE = function() {
					window.open("./export/exportBCE", "_self");
				};

	                if (navigator.onLine) {
	               
	                    //cache data : save the data
	                    // redirect to Online Page 
	                    console.log('ONLINE');
	                } else {
	              
	                    $window.location.href = 'fragments/cop/views/offline-views/offline-home.html';
	                    console.log('OFFLINE');
	                }

				
				if (window.applicationCache) {
					 var oAppCache = window.applicationCache;
					    oAppCache.onerror = function(e) {
					      alert(e); // Outputs [object Event], I use this row as a breakpoint target
					    };
				}
				
			

			}
	]);

	return {
		angularModules : [
			'CopManagement'
		]
	};

});
