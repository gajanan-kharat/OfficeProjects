/*
 * Copyright (c) PSA.
 */
define([
		'require', '{angular}/angular', '[text]!{cop}/templates/cop-header.html', '[text]!{cop}/templates/cop-sidebar.html', '[text]!{cop}/templates/cop-search.html', '[text]!{cop}/templates/cop-history.html',
], function(require, angular, headerTemplate, sidebarTemplate, searchTemplate, historyTemplate) {
	'use strict';

	var copDirectives = angular.module('copManagementDirectives', []);

	copDirectives.directive('copHeader', [
			'AuthenticationService', function(authenticationService) {
				return {
					restrict : 'A',
					template : headerTemplate,
					replace : true,
					scope : true,
					link : function(scope) {
						scope.userInfo = function() {
							var userDetails = authenticationService.subjectPrincipals();
							if (userDetails != undefined) {
								return userDetails.fullName;
							}
						};
						/** ***************************************** */
						scope.slideToggle = function() {

							$("#wrapper").toggleClass("toggled");
							$(".slide-resize").toggleClass("col-lg-10");
							$("#menu-toggle").toggleClass('fa-chevron-right');
						};

					}
				};
			}
	]);

	copDirectives.directive('copSidebar', [
			'AuthorizationService', function(authorizationService) {
				return {
					restrict : 'A',
					template : sidebarTemplate,
					replace : true,
					scope : true,
					link : function(scope) {
						scope.hasCopRole = function(seedW20, role) {
							return authorizationService.hasRole(seedW20, role);
							//return true;
						   
						};

					}
				};
			}
	]);

	copDirectives.directive('copSearch', [
		function() {
			return {
				restrict : 'A',
				template : searchTemplate,
				replace : true
			};
		}
	]);

	copDirectives.directive('copHistory', [
		function() {
			return {
				restrict : 'A',
				template : historyTemplate,
				replace : true
			};
		}
	]);

	/*
	 * copDirectives.directive('copEditableCard', [ function() { return { restrict : 'A', template : editableCardTemplate, replace : true }; } ]);
	 */
	return {
		angularModules : [
			'copManagementDirectives'
		]
	};
});
