/**
 * Copyright (c) PSA.
 */
define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {

	var coastdown = angular.module('coastdown', []);

	coastdown.factory('CoastdownService', [
			'$resource',

			function($resource) {
				return {
					CoastdownResource : $resource('CoastdownReference/:path', {
						path : '@path',

					}, {
						'getAllCoastdown' : {
							method : 'GET',
							params : {
								path : 'Coastdowns'

							},
							isArray : true
						},
						'saveAllCoastdown' : {
							method : 'POST',
							params : {
								path : 'Coastdowns'
							}
						},

					})
				};
			}
	]);

	coastdown.factory('coastdownUIGridService', [
			'CultureService', function(CultureService) {
				var uigridbody = [];
				uigridbody.defination = function() {
					return [

							{
								name : 'PSAReference',
								displayName : CultureService.localize('cop.coastdown.label.codepsa'),
								field : 'pSAReference',
								cellEditableCondition : function($scope) {
									if (($scope.row.entity.entityId !== 0) || ($scope.row.entity.version !== "V1.0")) {
										return false;
									} else {
										return true;
									}
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-class="{ \'sortable\': sortable }">' + '<div class="ui-grid-cell-contents" col-index="renderIndex">' + '<span ui-grid-visible="col.sort.direction" ng-class="{ \'ui-grid-icon-up-dir\': col.sort.direction == asc, \'ui-grid-icon-down-dir\': col.sort.direction == desc, \'ui-grid-icon-blank\': !col.sort.direction }">' + '</span>' + '</div>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								cellTemplate : '<div class="defultFuelRadio" ng-attr-title="{{row.entity.pSAReference}}"><input type="text"   style="height:27px;" class="coastdowntext" ng-model="row.entity.pSAReference" ng-disabled=((row.entity.entityId!==0)||(row.entity.version!=="V1.0")) required/><button ng-show="grid.appScope.auth" ng-if=((row.entity.latestversion===true)&&(row.entity.entityId!==0)) class="btn pull-right coastdownbutton1 fa fa-pencil" ng-click="grid.appScope.updateRowCoatdown(row, grid.renderContainers.body.visibleRowCache.indexOf(row))"></button></div>'
							}, {
								name : 'Rodelaw',
								displayName : CultureService.localize('cop.coastdown.label.roadlaw'),
								field : 'roadLaw',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-class="{ \'sortable\': sortable }">' + '<div class="ui-grid-cell-contents" col-index="renderIndex">' + '<span ui-grid-visible="col.sort.direction" ng-class="{ \'ui-grid-icon-up-dir\': col.sort.direction == asc, \'ui-grid-icon-down-dir\': col.sort.direction == desc, \'ui-grid-icon-blank\': !col.sort.direction }">' + '</span>' + '</div>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								cellEditableCondition : function($scope) {
									if ($scope.row.entity.latestversion !== true) {
										return false;
									} else {
										return true;
									}
								},
								
								cellTemplate : '<div ng-attr-title="{{row.entity.roadLaw}}"><input type="text" style="height:27px;" maxlength="255" ng-model="row.entity.roadLaw" ng-disabled=(row.entity.latestversion!==true)  required/></div>'
							}, {
								name : 'Inertia',
								displayName : CultureService.localize('cop.search.label.inertia'),
								field : 'inertia_value',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-class="{ \'sortable\': sortable }">' + '<div class="ui-grid-cell-contents" col-index="renderIndex">' + '<span ui-grid-visible="col.sort.direction" ng-class="{ \'ui-grid-icon-up-dir\': col.sort.direction == asc, \'ui-grid-icon-down-dir\': col.sort.direction == desc, \'ui-grid-icon-blank\': !col.sort.direction }">' + '</span>' + '</div>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								cellEditableCondition : function($scope) {
									if ($scope.row.entity.entityId !== 0) {
										return false;
									} else {
										return true;
									}
								},
								type : 'number',
								cellTemplate : '<div ng-attr-title="{{row.entity.inertia_value}}"><input type="text" style="height:27px;" maxlength="11" ng-model="row.entity.inertia_value" ng-disabled="row.entity.entityId!==0" ng-change="grid.appScope.Computed(row,4)" required/></div>'
							}, {
								name : 'Theoricalf0',
								displayName : CultureService.localize('cop.coastdown.label.theoricalf0'),
								field : 'theoricalBenchF0',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-class="{ \'sortable\': sortable }">' + '<div class="ui-grid-cell-contents" col-index="renderIndex">' + '<span ui-grid-visible="col.sort.direction" ng-class="{ \'ui-grid-icon-up-dir\': col.sort.direction == asc, \'ui-grid-icon-down-dir\': col.sort.direction == desc, \'ui-grid-icon-blank\': !col.sort.direction }">' + '</span>' + '</div>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								cellEditableCondition : function($scope) {
									if ($scope.row.entity.latestversion !== true) {
										return false;
									} else {
										return true;
									}
								},
								type : 'number',
								cellTemplate : '<div ng-attr-title="{{row.entity.theoricalBenchF0}}"><input style="height:27px;" maxlength="49" ng-model="row.entity.theoricalBenchF0" ng-disabled=(row.entity.latestversion!==true) ng-change="grid.appScope.Computed(row,0)" required/></div>'
							}, {
								name : 'Theoricalf1',
								displayName : CultureService.localize('cop.coastdown.label.theoricalf1'),
								field : 'theoricalBenchF1',
								cellEditableCondition : function($scope) {
									if ($scope.row.entity.latestversion !== true) {
										return false;
									} else {
										return true;
									}
								},
								type : 'number',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-class="{ \'sortable\': sortable }">' + '<div class="ui-grid-cell-contents" col-index="renderIndex">' + '<span ui-grid-visible="col.sort.direction" ng-class="{ \'ui-grid-icon-up-dir\': col.sort.direction == asc, \'ui-grid-icon-down-dir\': col.sort.direction == desc, \'ui-grid-icon-blank\': !col.sort.direction }">' + '</span>' + '</div>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								cellTemplate : '<div ng-attr-title="{{row.entity.theoricalBenchF1}}"><input type="text" style="height:34px;" maxlength="49" ng-model="row.entity.theoricalBenchF1" ng-disabled=(row.entity.latestversion!==true) ng-change="grid.appScope.Computed(row,1)" required/></div>'
							}, {
								name : 'Theoricalf2',
								displayName : CultureService.localize('cop.coastdown.label.theoricalf2'),
								field : 'theoricalBenchF2',
								cellEditableCondition : function($scope) {
									if ($scope.row.entity.latestversion !== true) {
										return false;
									} else {
										return true;
									}
								},
								type : 'number',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-class="{ \'sortable\': sortable }">' + '<div class="ui-grid-cell-contents" col-index="renderIndex">' + '<span ui-grid-visible="col.sort.direction" ng-class="{ \'ui-grid-icon-up-dir\': col.sort.direction == asc, \'ui-grid-icon-down-dir\': col.sort.direction == desc, \'ui-grid-icon-blank\': !col.sort.direction }">' + '</span>' + '</div>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								cellTemplate : '<div ng-attr-title="{{row.entity.theoricalBenchF2}}"><input type="text" style="height:27px;" maxlength="49" ng-model="row.entity.theoricalBenchF2" ng-disabled=(row.entity.latestversion!==true) ng-change="grid.appScope.Computed(row,2)" required/></div>'
							}, {
								name : 'Computedf0',
								displayName : CultureService.localize('cop.coastdown.label.Computedf0'),
								field : 'computedBenchF0',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}</span>' + '<div ng-class="{ \'sortable\': sortable }">' + '<div class="ui-grid-cell-contents" col-index="renderIndex">' + '<span ui-grid-visible="col.sort.direction" ng-class="{ \'ui-grid-icon-up-dir\': col.sort.direction == asc, \'ui-grid-icon-down-dir\': col.sort.direction == desc, \'ui-grid-icon-blank\': !col.sort.direction }">' + '</span>' + '</div>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								enableCellEdit : false,
								type : 'number',
								cellTemplate : '<div ng-attr-title="{{row.entity.computedBenchF0}}"><input type="text" style="height:27px;" maxlength="255" ng-model="row.entity.computedBenchF0" ng-disabled="true"/></div>'
							}, {
								name : 'Computedf1',
								displayName : CultureService.localize('cop.coastdown.label.Computedf1'),
								field : 'computedBenchF1',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}</span>' + '<div ng-class="{ \'sortable\': sortable }">' + '<div class="ui-grid-cell-contents" col-index="renderIndex">' + '<span ui-grid-visible="col.sort.direction" ng-class="{ \'ui-grid-icon-up-dir\': col.sort.direction == asc, \'ui-grid-icon-down-dir\': col.sort.direction == desc, \'ui-grid-icon-blank\': !col.sort.direction }">' + '</span>' + '</div>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								enableCellEdit : false,
								type : 'number',
								cellTemplate : '<div ng-attr-title="{{row.entity.computedBenchF1}}"><input type="text" style="height:27px;" maxlength="255" ng-model="row.entity.computedBenchF1" ng-disabled="true"/></div>'
							}, {
								name : 'Computedf2',
								displayName : CultureService.localize('cop.coastdown.label.Computedf2'),
								field : 'computedBenchF2',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}</span>' + '<div ng-class="{ \'sortable\': sortable }">' + '<div class="ui-grid-cell-contents" col-index="renderIndex">' + '<span ui-grid-visible="col.sort.direction" ng-class="{ \'ui-grid-icon-up-dir\': col.sort.direction == asc, \'ui-grid-icon-down-dir\': col.sort.direction == desc, \'ui-grid-icon-blank\': !col.sort.direction }">' + '</span>' + '</div>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								enableCellEdit : false,
								type : 'number',
								cellTemplate : '<div ng-attr-title="{{row.entity.computedBenchF2}}"><input type="text" style="height:27px;" maxlength="255" ng-model="row.entity.computedBenchF2" ng-disabled="true"/></div>'
							}, {
								name : 'Version',
								displayName : CultureService.localize('cop.coastdown.label.Version'),
								field : 'version',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}</span>' + '<div ng-class="{ \'sortable\': sortable }">' + '<div class="ui-grid-cell-contents" col-index="renderIndex">' + '<span ui-grid-visible="col.sort.direction" ng-class="{ \'ui-grid-icon-up-dir\': col.sort.direction == asc, \'ui-grid-icon-down-dir\': col.sort.direction == desc, \'ui-grid-icon-blank\': !col.sort.direction }">' + '</span>' + '</div>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								enableCellEdit : false,
								cellTemplate : '<div ng-attr-title="{{row.entity.version}}"><input type="text" style="height:27px;" maxlength="255" ng-model="row.entity.version" ng-disabled="true"/></div>'
							}

					];
				};
				return uigridbody;
			}
	]);

	return {
		angularModules : [
			'coastdown'
		]
	};
});