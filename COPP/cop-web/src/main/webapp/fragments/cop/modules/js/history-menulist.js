/*
 * Copyright (c) PSA.
 */

define([
        'require', 'module', '{angular}/angular'
        ], function(require, module, angular) {
	'use strict';

	var historyListItems = angular.module('historyList', [
	                                                              'ui.bootstrap', 'copCommon'
	                                                              ]), config = module && module.config() || {};

	                                                              historyListItems.controller('historyController', [
	                                                                                                                        '$scope', function($scope) {

	                                                                                                                        	$scope.title="historylist Title";
	                                                                                                                        }
	                                                                                                                        ]);

	                                                              return {
	                                                            	  angularModules : [
	                                                            	                    'historyList'
	                                                            	                    ]
	                                                              };

});
