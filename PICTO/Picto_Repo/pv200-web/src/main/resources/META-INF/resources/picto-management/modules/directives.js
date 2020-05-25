/*
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
define([
        'require',
        '{angular}/angular',
        '[text]!{picto-management}/templates/pictoNavbar.html',
        //'[text]!{picto-management}/templates/pictoSidebar.html',
        '[text]!{picto-management}/templates/pictoRightbar.html',
        '[text]!{picto-management}/templates/pictoCatalog.html',
        '[text]!{picto-management}/templates/pictoInfo.html',
        '[text]!{picto-management}/templates/pictoSpecificDrawings.html',
        '[text]!{picto-management}/templates/pictoRules.html',
        '[text]!{picto-management}/templates/pictoOptions.html',
        '[text]!{picto-management}/templates/pictoItem.html',
        '[text]!{picto-management}/templates/pictoIncartinfo.html',
        '[text]!{picto-management}/templates/pictoIncartitem.html'
        ], function (require, angular, navbarTemplate, rightbarTemplate, catalogTemplate, infoTemplate, specificdrawingsTemplate, rulesTemplate, optionsTemplate, itemTemplate,incartinfoTemplate, incartitemTemplate) {
	'use strict';

	var pictoDirectives = angular.module('pictoManagementDirectives', []);

	pictoDirectives.directive('pictoNavbar', [function() {
		return {
			restrict: 'A',
			template: navbarTemplate,
			replace: true
		};
	}]);


	pictoDirectives.directive('pictoRightbar', [function() {
		return {
			restrict: 'A',
			template: rightbarTemplate,
			replace: true
		};
	}]);

	pictoDirectives.directive('pictoCatalog', [function() {
		return {
			restrict: 'A',
			template: catalogTemplate,
			replace: true
		};
	}]);

	pictoDirectives.directive('pictoInfo', [function() {
		return {
			restrict: 'A',
			template: infoTemplate,
			replace: true
		};
	}]);

	pictoDirectives.directive('pictoSpecificdrawings', [function() {
		return {
			restrict: 'A',
			template: specificdrawingsTemplate,
			replace: true
		};
	}]);

	pictoDirectives.directive('pictoRules', [function() {
		return {
			restrict: 'A',
			template: rulesTemplate,
			replace: true
		};
	}]);

	pictoDirectives.directive('pictoOptions', [function() {
		return {
			restrict: 'A',
			template: optionsTemplate,
			replace: true
		};
	}]);

	pictoDirectives.directive('pictoItem', [function() {
		return {
			restrict: 'A',
			template: itemTemplate,
			replace: true
		};
	}]);

	pictoDirectives.directive('pictoIncartinfo', [function() {
		return {
			restrict: 'A',
			template: incartinfoTemplate,
			replace: true
		};
	}]);

	pictoDirectives.directive('pictoIncartitem', [function() {
		return {
			restrict: 'A',
			template: incartitemTemplate,
			replace: true
		};
	}]);



	return {
		angularModules: ['pictoManagementDirectives']
	};
});
