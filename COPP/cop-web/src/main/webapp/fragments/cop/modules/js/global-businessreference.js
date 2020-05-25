/*
 * 
 * 
 * Copyright (c) PSA.
 */
define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {
	var globalModule = angular.module('GlobalBusinessReference', [
			'ui.grid', 'ui.grid.selection', 'ui.grid.cellNav', 'ui.grid.edit', 'copCommon', 'specificCopModule', 'genomeData', 'coastdown'
	]);

	globalModule.controller('GlobalController', [
			'$scope', '$resource', '$http', '$location', '$route', '$routeParams', '$interval', '$templateCache', 'CarburantService', 'FamilleService', 'SilhouetteService', 'ConstructeurService', 'UsineService', 'PaysService', 'ReglementationService', 'FinalReductionService', 'MoturService', 'BolteService', 'ClaszService', 'PollutantService', 'FuelInjectionTypeService', 'VehicleTechnologyService', 'TypeApprovalAreaService', 'FactorCoeffApplicationTypeService', 'VehicleCoefficentsService', 'FuelService', 'CategoryService', 'CultureService', 'uiGridConstants', 'CoastdownService', 'HistoryService', 'NotificationService', 'coastdownUIGridService', 'AuthorizationService', 'CategoryClaszService', 'AuthenticationService', 'TireBrandService', 'LambdaDecisionParametersService', 'OpacityDecisionParametersService', 'CODecisionParametersService', 'TeamUsersService', 'FuelTypeService', 'TeamService', 'ParametersConstantsService',

			function($scope, $resource, $http, $location, $route, $routeParams, $interval, $templateCache, CarburantService, FamilleService, SilhouetteService, ConstructeurService, UsineService, PaysService, ReglementationService, FinalReductionService, MoturService, BolteService, ClaszService, PollutantService, FuelInjectionTypeService, VehicleTechnologyService, TypeApprovalAreaService, FactorCoeffApplicationTypeService, VehicleCoefficentsService, FuelService, CategoryService, CultureService, uiGridConstants, CoastdownService, HistoryService, NotificationService, coastdownUIGridService, authorizationService, CategoryClaszService, authenticationService, TireBrandService, LambdaDecisionParametersService, OpacityDecisionParametersService, CODecisionParametersService, TeamUsersService, FuelTypeService, TeamService, ParametersConstantsService

			) {

				/*----------------------------------------Authorization----------------------------------------*/
				var authentication = false;
				$scope.auth;

				if (authorizationService.hasRole('seed-w20', 'POCMRole')) {
					authentication = true;
				}

				$scope.authorization = function(role) {
					return authorizationService.hasRole('seed-w20', role);
				};

				$scope.auth = authentication;
				/**
				 * ********************************************************** Genome DATA ************************************************************
				 */
				// Initialization of arrays for tables
				$scope.Moteur = [];
				$scope.FuelTypedata = [];
				$scope.Famille = [];
				$scope.Silhouette = [];
				$scope.Constructeur = [];
				$scope.Usine = [];
				$scope.Pays = [];
				$scope.Reglementation = [];
				$scope.Bolte = [];
				$scope.FinalReduction = [];
				// to store slected values of tables
				$scope.MoteurSelected = [];
				$scope.CaburantSelected = [];
				$scope.FamilleSelected = [];
				$scope.SilhouetteSelected = [];
				$scope.ConstructeurSelected = [];
				$scope.UsineSelected = [];
				$scope.PaysSelected = [];
				$scope.ReglementationSelected = [];
				$scope.BolteSelected = [];
				$scope.FinalReductionSelected = [];

				// value to show in Moteur drop down
				$scope.FuelTypeForMoteur = [];
				$scope.FuelInjectionForMoture = [];
				$scope.isValidated = true;
				$scope.cancelledChanges = false;
				$scope.savedData = null;

				const
				genome = "genom";
				const
				specific = "specificCop";
				const
				costDown = "costDown";
				$scope.activeTab = genome;

				// flags for genome data

				$scope.isMotureValidate = true;
				$scope.iscarburentValidate = true;
				$scope.isFamilyValidate = true;
				$scope.isSilhouteValidate = true;
				$scope.isConstructValidate = true;
				$scope.isUsinValidate = true;
				$scope.isPayValidate = true;
				$scope.isReglementValidate = true;
				$scope.isBolteValidate = true;
				$scope.isReductionValidate = true;

				// header template
				var headertemplate = '<div class="ngHeaderText"><span>{{col.displayName}}</span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>';

				// Moteur Column defination

				// custom filter starts
                
                $scope.customFilterArray=function(objGrid)
                {
                   
                    $scope.arrTest = [];
                    var term;
                    var field;
                    for (var i = 0; i < objGrid.columns.length; i++) {
                        term = objGrid.columns[i].filter.term;
                       field = objGrid.columns[i].field;
                       var obj={'term':term,'field':field };
                       $scope.arrTest.push(obj);
                }
                };
             // custom filter starts
             $scope.customFilterCondition = function(input, cellValue,row,column) {
                 
                 for(var i=0;i<$scope.arrTest.length;i++)
                     {
                       if($scope.arrTest[i].field===column.field)
                           {
                            input=$scope.arrTest[i].term;
                          
                           }
                     }

                 if (cellValue !== null && cellValue !== undefined) {
                     
                     cellValue = cellValue.toString();
                     cellValue = cellValue.replace(/\s+/g, "").toLowerCase();

                    input = input.replace(/\s+/g, "").toLowerCase();
                    

                     if (input.toString().indexOf('~') ===-1 && input.toString().indexOf('?') !== -1) {

                         if (input.length  === cellValue.length) {
                            var str = input.split("?");

                             var str1 = str[0].slice(0, str[0].length - 1);
                             if (str1==="") {
                                 return cellValue.slice(-str[1].length) === str[1]
                             } else {
                                 if (cellValue.slice(0, str[0].length - 1) === str1 && cellValue.slice(-str[1].length) ===str[1])
                                     return cellValue;
                             }

                         }
                     } else if (input.toString().indexOf('~~') !== -1) {
                         var index = input.indexOf('~');
                         input = input.substr(0, index) + input.substr(index + 1);

                         if (cellValue.slice(0, input.length) === input)
                             return cellValue;
                     } else if (input.toString().indexOf('~') !== -1) {
                         str = input.split("~");
                         if (str[1].indexOf('?') !== -1) {
                             input = input.replace(/\~/g, "");
                             input = input.replace(/\\/g, "");

                             if (cellValue.slice(0, input.length) == input)
                                 return cellValue;
                         } else if (input.indexOf('') !== -1) {
                             input = input + '*';
                             input = input.replace(/\~/g, "");
                             input = input.replace(/\\/g, "");
                             if (cellValue == input)
                                 return cellValue;

                         }
                        } else if (input.indexOf('*') !== -1) {

                       input = input.replace(/\\/g, '');

                         str = input.split("*");
                      
                         var strArr = [];
                       
                         if (str.length > 2) {
                             var currValueToSearch = cellValue;
                             var isFilterOk = false;
                             for (var filterInputIndex = 0; filterInputIndex < str.length; filterInputIndex++) {
                                 var searchedChars = str[filterInputIndex];
                                 
                                 if(searchedChars !== ""){
                                     var indexOfSearchedChars = currValueToSearch.indexOf(searchedChars);
                                     if(indexOfSearchedChars > -1 ){
                                         currValueToSearch = currValueToSearch.substr(indexOfSearchedChars + searchedChars.length);
                                         isFilterOk = true;
                                     }else{
                                         isFilterOk =false;
                                         break;
                                     }
                                 }
                                
                             }
                             var lastCharOfSearcText = input.charAt(input.length - 1);
                             if(isFilterOk && lastCharOfSearcText !== '*'){
                                 if(cellValue.charAt(cellValue.length - 1) !== lastCharOfSearcText){
                                     isFilterOk = false;
                                 }
                                 
                             }
                             return isFilterOk;
                             
                            } else {
                             if(str[1]!="")
                                 {
                                  if (cellValue.slice(0, str[0].length) === str[0] && cellValue.slice(-str[1].length) === str[1])
                                 return cellValue;
                                 }
                             else
                                 {
                                 if (cellValue.slice(0, str[0].length) === str[0])
                                     return cellValue;
                                 }
                              }

                     }
                     else if (cellValue.indexOf(input) > -1) {

                         return cellValue;

                     }
                 }

               };
             // end of custom filter
				$scope.MoteurButton = true;
				$scope.MoteurGridOptions = {
					enableSelectAll : true,
					enableRowSelection : false,
					enableCellSelection : false,
					enableColumnResizing : true,
					enableSorting : false,
					enableFiltering : true,
					enableCellEditOnFocus : true,
					enableRowHeaderSelection : authentication,
					columnDefs : [

							{
								name : 'B0F',
								displayName : 'B0F',
								field : 'bofValue',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.bofValue
								},

							}, {
								name : 'DOC',
								displayName : 'DOC',
								field : 'docValue',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.docValue;
								},
							}, {
								name : 'KMAT',
								displayName : 'KMAT',
								field : 'kmat',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.kmat;
								},
							}, {
								name : 'GENOMELabelB0F',
								displayName : CultureService.localize('cop.genomedata.label.Label') + ' GENOME B0F',
								field : 'frLabelB0F',

								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.frLabelB0F;
								},
							}, {
								name : 'GENOMELabelDOC',
								displayName : CultureService.localize('cop.genomedata.label.Label') + ' GENOME DOC',
								field : 'frLabelDOC',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.frLabelDOC;
								},
							}, {
								name : 'Label',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'engineLabel',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.engineLabel;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>',
								enableCellEdit : authentication,
							}, {
								name : 'PowerCV',
								displayName : CultureService.localize('cop.genomedata.label.EnginePowerCV'),
								field : 'powerCv',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.powerCv;
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'PowerKW',
								displayName : CultureService.localize('cop.genomedata.label.EnginePowerKW'),
								field : 'powerKw',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.powerKw;
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'Torque',
								displayName : CultureService.localize('cop.genomedata.label.EngineTorque'),
								field : 'torque',
								minWidth : 80,
								// maxWidth : 90,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.torque;
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'Derogation',
								displayName : CultureService.localize('cop.genomedata.label.EngineDerogation'),
								field : 'labelDerogation',
								minWidth : 80,
								// maxWidth : 90,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.labelDerogation;
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'InjectionType',
								displayName : CultureService.localize('cop.genomedata.label.EngineFuelInjection'),
								field : 'fuelInjectionlabel',
								minWidth : 90,
								// maxWidth : 100,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.fuelInjectionlabel;
								},
								cellTemplate : '<input type="select" style="height:27px;" ng-disabled="true" ng-model="row.entity.fuelInjectionlabel"/>',
								editType : 'dropdown',
								editableCellTemplate : 'ui-grid/dropdownEditor',
								editDropdownOptionsArray : $scope.FuelInjectionForMoture,
								editDropdownIdLabel : 'label',
								editDropdownValueLabel : 'label',
								headerCellTemplate : headertemplate,
								enableCellEdit : authentication,

							}, {
								name : 'FuelType',
								displayName : CultureService.localize('cop.genomedata.label.EngineFuelType'),
								field : 'fuelTypelabel',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.fuelTypelabel;
								},
								cellTemplate : '<input type="select" style="height:27px;" ng-disabled="true" ng-model="row.entity.fuelTypelabel"/>',
								headerCellTemplate : headertemplate,
								minWidth : 100,
								// maxWidth : 250,
								editType : 'dropdown',
								editableCellTemplate : 'ui-grid/dropdownEditor',
								editDropdownOptionsArray : $scope.FuelTypeForMoteur,
								editDropdownIdLabel : 'fuelTypeLable',
								editDropdownValueLabel : 'fuelTypeLable',
								enableCellEdit : authentication,

							}, {
								name : 'delete row',
								displayName : '',
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.DeleteMoteurData(row)"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'Moteur',

					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;
						$scope.gridApi.selection.on.rowSelectionChanged($scope, function(row) {

							if (row.entity.engineLabel === '' || row.entity.engineLabel === null) {
								gridApi.selection.unSelectRow(row.entity);
							} else {
								$scope.MoteurSelected = gridApi.selection.getSelectedRows();
							}

							// to enable save button
							if ($scope.MoteurSelected.length > 0) {
								$scope.MoteurButton = false;
							} else {
								$scope.MoteurButton = true;
							}

						});
						$scope.gridApi.selection.on.rowSelectionChangedBatch($scope, function(rows) {

							for (var i = 0; i < rows.length; i++) {

								if (rows[i].entity.engineLabel === '' || rows[i].entity.engineLabel === null) {

									gridApi.selection.unSelectRow(rows[i].entity);
								}

							}
							$scope.MoteurSelected = gridApi.selection.getSelectedRows();

							// to enable save button
							if ($scope.MoteurSelected.length > 0) {
								$scope.MoteurButton = false;
							} else {
								$scope.MoteurButton = true;
							}
							;
						});

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {
							if (newValue !== oldValue)

								$scope.isMotureValidate = false;
							$scope.activeTab = "commonGenome";

							// setting fuelInjectionID
							// from drop down
							for (var i = 0; i < $scope.FuelInjectionForMoture.length; i++) {

								if ($scope.FuelInjectionForMoture[i]['label'] === rowentity.fuelInjectionlabel) {

									rowentity.fuelInjectionID = $scope.FuelInjectionForMoture[i]['entityId'];
									break;
								}
							}
							// setting fuelTypeID from
							// drop down
							for (var i = 0; i < $scope.FuelTypeForMoteur.length; i++) {
								if ($scope.FuelTypeForMoteur[i]['fuelTypeLable'] === rowentity.fuelTypelabel) {
									rowentity.fuelTypeID = $scope.FuelTypeForMoteur[i]['entityId'];
									break;
								}
							}

						});
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });

					}
				};

				$scope.carburantButton = true;
				// GENOMECarburator coulmn definition
				$scope.FuelTypeGridOptions = {
					enableSelectAll : true,
					enableRowSelection : false,
					enableSorting : false,
					enableColumnResizing : true,
					enableFiltering : true,
					multiSelect : true,
					noUnselect : false,
					enableRowHeaderSelection : authentication,
					columnDefs : [

							{
								name : 'DCD',
								displayName : 'DCD',
								field : 'dcd',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.dcd
								},
							}, {
								name : 'KMAT',
								displayName : 'KMAT',
								field : 'carbuKmat',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.carbuKmat
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
							}, {
								name : 'GENOMELabel',
								displayName : CultureService.localize('cop.genomedata.label.Label') + ' ' + 'GENOME DCD',
								field : 'carbuFRLable',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.carbuFRLable
								},
							}, {
								name : 'Label',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'fuelTypeLable',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.fuelTypeLable
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								enableCellEditOnFocus : true,
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 37,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.DeleteCarburantData(row)"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'FuelTypedata',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;
						$scope.gridApi.selection.on.rowSelectionChanged($scope, function(row) {
							if (row.entity.fuelTypeLable === '') {
								gridApi.selection.unSelectRow(row.entity);
							} else {
								$scope.CaburantSelected = gridApi.selection.getSelectedRows();
							}
							if ($scope.CaburantSelected.length > 0) {
								$scope.carburantButton = false;
							} else {
								$scope.carburantButton = true;
							}
							;
						});

						$scope.gridApi.selection.on.rowSelectionChangedBatch($scope, function(rows) {

							for (var i = 0; i < rows.length; i++) {

								if (rows[i].entity.fuelTypeLable === '') {

									gridApi.selection.unSelectRow(rows[i].entity);
								}

							}
							$scope.CaburantSelected = gridApi.selection.getSelectedRows();

							if ($scope.CaburantSelected.length > 0) {
								$scope.carburantButton = false;
							} else {
								$scope.carburantButton = true;
							}
							;
						});

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {
							if (newValue !== oldValue)
								$scope.iscarburentValidate = false;
							$scope.activeTab = "commonGenome";
						});
						
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
						

					}
				};

				$scope.FamilleButton = true; // to
				// disable
				// save
				// button
				// GENOMEFamily coulm definition
				$scope.FamilleGridOptions = {
					enableFiltering : true,
					enableCellEditOnFocus : true,
					enableSorting : false,
					enableColumnResizing : true,
					resizable : true,
					enableRowHeaderSelection : authentication,
					columnDefs : [

							{
								name : 'BOC',
								displayName : 'B0C',
								field : 'familleBOC',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								width : '18%',
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.familleBOC;
								}
							}, {
								name : 'KMAT',
								displayName : 'KMAT',
								field : 'familleKmat',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.familleKmat;
								}
							}, {
								name : 'GENOMELabel',
								displayName : CultureService.localize('cop.genomedata.label.Label') + ' ' + ' GENOME B0C',
								field : 'familleRLable',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.familleRLable;
								}
							}, {
								name : 'ProjetCode',
								displayName : CultureService.localize('cop.genomedata.label.Project'),
								field : 'projectCodeLabel',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.projectCodeLabel;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'VehicleFamily',
								displayName : CultureService.localize('cop.genomedata.label.Family'),
								field : 'vehicleFamilyLabel',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.vehicleFamilyLabel;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.DeleteFamilleData(row)"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'Famille',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						$scope.gridApi.selection.on.rowSelectionChanged($scope, function(row) {
							if (row.entity.projectCodeLabel === null || row.entity.projectCodeLabel === "" || row.entity.vehicleFamilyLabel === null || row.entity.vehicleFamilyLabel === "") {
								gridApi.selection.unSelectRow(row.entity);
							} else {
								$scope.FamilleSelected = gridApi.selection.getSelectedRows();
							}
							// to enable save button
							if ($scope.FamilleSelected.length > 0) {
								$scope.FamilleButton = false;
							} else {
								$scope.FamilleButton = true;
							}
							;

						});
						$scope.gridApi.selection.on.rowSelectionChangedBatch($scope, function(rows) {

							for (var i = 0; i < rows.length; i++) {

								if (rows[i].entity.projectCodeLabel === null || rows[i].entity.projectCodeLabel === "" || rows[i].entity.vehicleFamilyLabel === null || rows[i].entity.vehicleFamilyLabel === "") {

									gridApi.selection.unSelectRow(rows[i].entity);
								}

							}
							$scope.FamilleSelected = gridApi.selection.getSelectedRows();

							// to enable save button
							if ($scope.FamilleSelected.length > 0) {
								$scope.FamilleButton = false;
							} else {
								$scope.FamilleButton = true;
							}
							;
						});
						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {
							if (newValue !== oldValue)
								$scope.isFamilyValidate = false;
							$scope.activeTab = "commonGenome";
						});
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
					}

				};

				$scope.ShilhouetteButton = true; // to
				// disable
				// save
				// button
				// GenomeSilhouette column definition
				$scope.SilhouetteGridOptions = {
					enableFiltering : true,
					enableCellEditOnFocus : true,
					enableSorting : false,
					enableColumnResizing : true,
					enableRowHeaderSelection : authentication,
					columnDefs : [

							{
								name : 'B0D',
								displayName : 'B0D',
								field : 'silhouetteBOD',
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.silhouetteBOD;
								}
							}, {
								name : 'KMAT',
								displayName : 'KMAT',
								field : 'silhoutteKmat',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.silhoutteKmat;
								}
							}, {
								name : 'GENOMELabel',
								displayName : CultureService.localize('cop.genomedata.label.Label') + ' ' + 'GENOME B0D',
								field : 'silhoutteFRLable',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.silhoutteFRLable;
								}
							}, {
								name : 'Label',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'silhoutteLable',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.silhoutteFRLable;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.DeleteSilhouetteData(row)"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'Silhouette',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;
						// to get single seleted value
						$scope.gridApi.selection.on.rowSelectionChanged($scope, function(row) {

							if (row.entity.silhoutteLable === '') {
								gridApi.selection.unSelectRow(row.entity);
							} else {
								$scope.SilhouetteSelected = gridApi.selection.getSelectedRows();
							}
							// to enable save button
							if ($scope.SilhouetteSelected.length > 0) {
								$scope.ShilhouetteButton = false;
							} else {
								$scope.ShilhouetteButton = true;
							}
							;
						});
						// to get selectall value
						$scope.gridApi.selection.on.rowSelectionChangedBatch($scope, function(rows) {

							for (var i = 0; i < rows.length; i++) {

								if (rows[i].entity.silhoutteLable === '') {

									gridApi.selection.unSelectRow(rows[i].entity);
								}

							}
							$scope.SilhouetteSelected = gridApi.selection.getSelectedRows();

							// to enable save button
							if ($scope.SilhouetteSelected.length > 0) {
								$scope.ShilhouetteButton = false;
							} else {
								$scope.ShilhouetteButton = true;
							}
							;
						});
						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {
							if (newValue !== oldValue)
								$scope.isSilhouteValidate = false;
							$scope.activeTab = "commonGenome";
						});
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
					}
				};
				$scope.ConstructeurButton = true; // to
				// disable
				// save
				// Button

				// GenomeConstructeur column definition
				$scope.ConstructeurGridOptions = {
					enableFiltering : true,
					enableCellEditOnFocus : true,
					enableSorting : false,
					enableColumnResizing : true,
					enableRowHeaderSelection : authentication,
					columnDefs : [

							{
								name : 'B0B',
								displayName : 'B0B',
								field : 'constructeurBOB',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.constructeurBOB;
								}
							}, {
								name : 'KMAT',
								displayName : 'KMAT',
								field : 'constructeurKmat',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.constructeurKmat;
								}
							}, {
								name : 'GENOMELabel',
								displayName : CultureService.localize('cop.genomedata.label.Label') + ' ' + 'GENOME B0B ',
								field : 'constructeurFRLable',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.constructeurFRLable;
								}
							}, {
								name : 'CarBrand ',
								displayName : CultureService.localize('cop.genomedata.label.CarMakerLabel'),
								field : 'constructeurBrandLabel',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.constructeurBrandLabel;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'CarMaker',
								displayName : CultureService.localize('cop.genomedata.label.CarBrandLabel'),
								field : 'constructeurMakerLabel',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.constructeurMakerLabel;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.DeleteConstructeurData(row)"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'Constructeur',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;
						$scope.gridApi.selection.on.rowSelectionChanged($scope, function(row) {
							// to get single
							// slected value

							if (row.entity.constructeurBrandLabel === null || row.entity.constructeurBrandLabel === "" || row.entity.constructeurMakerLabel === null || row.entity.constructeurMakerLabel === "") {
								gridApi.selection.unSelectRow(row.entity);
							} else {
								$scope.ConstructeurSelected = gridApi.selection.getSelectedRows();
							}
							// to enable save
							// button
							if ($scope.ConstructeurSelected.length > 0) {
								$scope.ConstructeurButton = false;
							} else {
								$scope.ConstructeurButton = true;
							}
							;
						});
						// to get selectall value
						$scope.gridApi.selection.on.rowSelectionChangedBatch($scope, function(rows) {

							for (var i = 0; i < rows.length; i++) {

								if (rows[i].entity.constructeurBrandLabel === null || rows[i].entity.constructeurBrandLabel === "" || rows[i].entity.constructeurMakerLabel === null || rows[i].entity.constructeurMakerLabel === '') {

									gridApi.selection.unSelectRow(rows[i].entity);
								}

							}
							$scope.ConstructeurSelected = gridApi.selection.getSelectedRows();
							// to enable save button
							if ($scope.ConstructeurSelected.length > 0) {
								$scope.ConstructeurButton = false;
							} else {
								$scope.ConstructeurButton = true;
							}
							;

						});
						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {
							if (newValue !== oldValue)
								$scope.isConstructValidate = false;
							$scope.activeTab = "commonGenome";
						});
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
					}
				};
				$scope.UsinButton = true; // to
				// disable
				// save
				// button
				// GenomeUsine column definition
				$scope.UsineGridOptions = {

					enableFiltering : true,
					enableCellEditOnFocus : true,
					enableSorting : false,
					enableColumnResizing : true,
					enableHorizontalScrollbar : false,
					columnDefs : [

							{
								name : 'Label',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'carFactoryLabel',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.carFactoryLabel;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',

								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'DeleteRow',
								displayName : '',
								field : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.DeleteUsineData(row,grid.renderContainers.body.visibleRowCache.indexOf(row))"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'Usine',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {
							if (newValue !== oldValue) {
								rowentity.edited = true;
								$scope.isUsinValidate = false;
								$scope.activeTab = "specificCop";
							}
						});
						
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
					}
				};

				$scope.PayButton = true;
				// GenomePays column definition
				$scope.PaysGridOptions = {
					enableFiltering : true,
					enableCellEditOnFocus : true,
					enableSorting : false,
					enableCellEdit : true,
					enableColumnResizing : true,
					enableHorizontalScrollbar : false,
					columnDefs : [

							{
								name : 'Label',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'countryLable',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.countryLable;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',

								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.DeletePaysData(row,grid.renderContainers.body.visibleRowCache.indexOf(row))"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'Pays',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							if (newValue !== oldValue) {
								rowentity.edited = true;
								$scope.isPayValidate = false;
								$scope.activeTab = "specificCop";
							}
						});
						
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
					}
				};
				$scope.ReglemenrationButton = true; // to
				// desable
				// save
				// button
				// GenomeReglementation column
				// definition
				$scope.ReglementationGridOptions = {
					enableFiltering : true,
					enableCellEditOnFocus : true,
					enableSorting : false,
					enableColumnResizing : true,
					enableRowHeaderSelection : authentication,
					columnDefs : [

							{
								name : 'DKA',
								displayName : 'DKA',
								field : 'reglementationDKA',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.reglementationDKA;
								}
							}, {
								name : 'KMAT',
								displayName : 'KMAT',
								field : 'reglementationKmat',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.reglementationKmat;
								}
							}, {
								name : 'GENOMELabel',
								displayName : CultureService.localize('cop.genomedata.label.Label') + ' ' + ' GENOME DKA',
								field : 'reglementationFRLable',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.reglementationFRLable;
								}
							}, {
								name : 'Label',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'esLabel',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.esLabel;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'Description',
								displayName : 'Description',
								field : 'description',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.description;
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.DeleteReglementationData(row)"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'Reglementation',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;
						// get single select value
						$scope.gridApi.selection.on.rowSelectionChanged($scope, function(row) {

							if (row.entity.esLabel === null || row.entity.esLabel === "") {
								gridApi.selection.unSelectRow(row.entity);
							} else {
								$scope.ReglementationSelected = gridApi.selection.getSelectedRows();
							}
							// to enable save button
							if ($scope.ReglementationSelected.length > 0) {
								$scope.ReglemenrationButton = false;
							} else {
								$scope.ReglemenrationButton = true;
							}
							;
						});
						// to get selectall value
						$scope.gridApi.selection.on.rowSelectionChangedBatch($scope, function(rows) {

							for (var i = 0; i < rows.length; i++) {

								if (rows[i].entity.esLabel === null || rows[i].entity.esLabel === '') {

									gridApi.selection.unSelectRow(rows[i].entity);
								}

							}
							$scope.ReglementationSelected = gridApi.selection.getSelectedRows();

							// to enable save button
							if ($scope.ReglementationSelected.length > 0) {
								$scope.ReglemenrationButton = false;
							} else {
								$scope.ReglemenrationButton = true;
							}
							;
						});
						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {
							if (newValue !== oldValue)
								$scope.isReglementValidate = false;
							$scope.activeTab = "commonGenome";
						});
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
					}
				};
				$scope.BolteButton = true; // to
				// disable
				// save
				// button
				// GenomeBolte column definition
				$scope.BolteGridOptions = {
					enableFiltering : true,
					enableCellEditOnFocus : true,
					enableSorting : false,
					enableColumnResizing : true,
					enableRowHeaderSelection : authentication,
					columnDefs : [

							{
								name : 'DBM',
								displayName : 'DBM',
								field : 'dbmValue',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.dbmValue;
								}
							}, {
								name : 'B0G',
								displayName : 'B0G',
								field : 'b0gValue',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.b0gValue;
								}
							}, {
								name : 'KMAT',
								displayName : 'KMAT',
								field : 'kmat',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.kmat;
								}
							}, {
								name : 'GENOMELabelDBM',
								displayName : CultureService.localize('cop.genomedata.label.Label') + ' ' + 'GENOME DBM',
								field : 'dbmFRLabel',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.dbmFRLabel;
								}
							}, {
								name : 'GENOMELabelBOG',
								displayName : CultureService.localize('cop.genomedata.label.Label') + ' ' + 'GENOME BOG',
								field : 'bogFRLabel',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.bogFRLabel;
								}
							}, {
								name : 'Label',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'gearBoxLabel',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.gearBoxLabel;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'Type',
								displayName : 'Type',
								field : 'gearBoxType',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.gearBoxType;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.DeleteBolteData(row)"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'Bolte',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;
						// to get single select value
						$scope.gridApi.selection.on.rowSelectionChanged($scope, function(row) {

							if (row.entity.gearBoxLabel === '' || row.entity.gearBoxLabel === null || row.entity.gearBoxType === '' || row.entity.gearBoxType === null) {
								gridApi.selection.unSelectRow(row.entity);
							} else {
								$scope.BolteSelected = gridApi.selection.getSelectedRows();
							}
							// to enable save button
							if ($scope.BolteSelected.length > 0) {
								$scope.BolteButton = false;
							} else {
								$scope.BolteButton = true;
							}
							;
						});
						// to get selectall value
						$scope.gridApi.selection.on.rowSelectionChangedBatch($scope, function(rows) {

							for (var i = 0; i < rows.length; i++) {

								if (rows[i].entity.gearBoxLabel === '' || rows[i].entity.gearBoxType === '' || rows[i].entity.gearBoxType === '' || rows[i].entity.gearBoxType === null) {

									gridApi.selection.unSelectRow(rows[i].entity);
								}

							}
							$scope.BolteSelected = gridApi.selection.getSelectedRows();

							// to enable save button
							if ($scope.BolteSelected.length > 0) {
								$scope.BolteButton = false;
							} else {
								$scope.BolteButton = true;
							}
							;
						});
						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {
							if (newValue !== oldValue)
								$scope.isBolteValidate = false;
							$scope.activeTab = "commonGenome";
						});
						
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
					}
				};

				$scope.FianlReductionButton = true; // to
				// disable
				// save
				// button
				// GenomeFinalReduction column
				// definition
				$scope.FinalReductionGridOptions = {
					enableFiltering : true,
					enableCellEditOnFocus : true,
					enableSorting : false,
					enableColumnResizing : true,
					enableRowHeaderSelection : authentication,
					columnDefs : [

							{
								name : 'DCW',
								displayName : 'DCW',
								field : 'finalReductionDCW',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.finalReductionDCW;
								}
							}, {
								name : 'KMAT',
								displayName : 'KMAT',
								field : 'finalReductionKmat',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.finalReductionKmat;
								}
							}, {
								name : 'GENOMELabel',
								displayName : CultureService.localize('cop.genomedata.label.Label') + ' ' + 'GENOME DCW',
								field : 'finalReductionFRLable',
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								enableCellEdit : false,
								cellTooltip : function(row) {
									return row.entity.finalReductionFRLable;
								}
							}, {
								name : 'Label',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'finalReductionlabel',
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.finalReductionlabel;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								enableCellEdit : authentication,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.DeleteFinalReductionData(row)"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'FinalReduction',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;
						// to get single select value
						$scope.gridApi.selection.on.rowSelectionChanged($scope, function(row) {

							if (row.entity.finalReductionFRLable === '') {
								gridApi.selection.unSelectRow(row.entity);
							} else {
								$scope.FinalReductionSelected = gridApi.selection.getSelectedRows();
							}
							// to enable save button
							if ($scope.FinalReductionSelected.length > 0) {
								$scope.FianlReductionButton = false;
							} else {
								$scope.FianlReductionButton = true;
							}
							;
						});

						// to get slectall value
						$scope.gridApi.selection.on.rowSelectionChangedBatch($scope, function(rows) {

							for (var i = 0; i < rows.length; i++) {

								if (rows[i].entity.finalReductionFRLable === '') {

									gridApi.selection.unSelectRow(rows[i].entity);
								}

							}
							$scope.FinalReductionSelected = gridApi.selection.getSelectedRows();

							// to enable save button
							if ($scope.FinalReductionSelected.length > 0) {
								$scope.FianlReductionButton = false;
							} else {
								$scope.FianlReductionButton = true;
							}
							;
						});
						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {
							if (newValue !== oldValue)
								$scope.isReductionValidate = false;
							$scope.activeTab = "commonGenome";
						});
						
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
					}
				};

				/*
				 * --------------------Get values from DataBase--------------------------------------------------------------------------
				 */

				/* to get Moteur Data */
				$scope.getAllMoteurData = function() {

					this.MoteurButton = true;

					var MoteurResponce = null;
					MoteurResponce = MoturService.MoteurResource.getAllMoteurData(function() {
						$scope.Moteur = MoteurResponce;

						$scope.addFuelTypeLabel($scope.Moteur);
						$scope.addFuelInjectionLabel($scope.Moteur);

					}, function() {

					});

				};

				/* Save Moteur Data */
				$scope.saveSelectedMoteurData = function() {
					var tempList = [];

					for (var i = 0; i < $scope.MoteurSelected.length; i++) {
						var temp = {
							'tvvRuleIdB0F' : $scope.MoteurSelected[i]["tvvRuleIdB0F"],
							'tvvRuleIdDOC' : $scope.MoteurSelected[i]["tvvRuleIdDOC"],
							'engineEntityID' : $scope.MoteurSelected[i]["engineEntityID"],
							'engineLabel' : $scope.MoteurSelected[i]["engineLabel"],
							'powerKw' : $scope.MoteurSelected[i]["powerKw"],
							'powerCv' : $scope.MoteurSelected[i]["powerCv"],
							'torque' : $scope.MoteurSelected[i]["torque"],
							'labelDerogation' : $scope.MoteurSelected[i]["labelDerogation"],
							'fuelInjectionID' : $scope.MoteurSelected[i]["fuelInjectionID"],
							'fuelTypeID' : $scope.MoteurSelected[i]["fuelTypeID"],
							'bofValue' : 'bofValue'
						};
						tempList.push(temp);
					}

					var objectToSend = {
						'engineRepresentation' : tempList
					};
					MoturService.MoteurResource.saveAllMoteurData(objectToSend, function(sucess) {
						if (sucess.bofValue === null) {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.Engine') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion') + ' : ' + sucess.engineLabel);

						} else {

							NotificationService.notify(CultureService.localize('cop.genomedata.label.Engine') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
						}
						$scope.isMotureValidate = true;
						$scope.changesSaved(true);
						$scope.getAllMoteurData();
					}, function() {
						NotificationService.notify(CultureService.localize('cop.genomedata.label.Engine') + ' : ' + CultureService.localize('cop.specificCop.message.errorSave'));
					});

				},

				/* Delete Moteur Data */
				$scope.DeleteMoteurData = function(row) {
					if (row.entity.engineEntityID !== null) {
						var DeleteList = [];
						var temp = {
							'engineEntityID' : row.entity.engineEntityID,
						};

						DeleteList.push(temp);

						var objectToSend = {
							'engineRepresentation' : DeleteList
						};

						MoturService.MoteurResource.deleteMoteurData(objectToSend, function() {
							$scope.getAllMoteurData();
							NotificationService.notify(CultureService.localize('cop.genomedata.label.Engine') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
						}, function() {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.Engine') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					}
				},

				/* to get Carburant Data */
				$scope.getAllCarburantData = function() {

					this.carburantButton = true;
					$scope.FuelTypeForMoteur.splice(0, $scope.FuelTypeForMoteur.length);
					var CarburantResponce = null;
					CarburantResponce = CarburantService.CarburantResource.getAllCarburantData(function() {

						$scope.FuelTypedata = CarburantResponce;
						/* FuelType for Moture DropDown */

						CarburantService.CarburantResource.getAllFuelType(function(response) {
							angular.forEach(response, function(responseObj) {
								var temp = {
									'fuelTypeLable' : responseObj.fuelTypeLable,
									'entityId' : responseObj.entityId,
								}
								$scope.FuelTypeForMoteur.push(temp);
							})
						}, function() {

						});

						/*
						 * to add fueltype label in morteur
						 */

						$scope.addFuelTypeLabel = function(MorteurData) {
							for (var i = 0; i < MorteurData.length; i++) {
								for (var k = 0; k < $scope.FuelTypeForMoteur.length; k++) {

									if (MorteurData[i]["fuelTypeID"] === $scope.FuelTypeForMoteur[k]["entityId"] && $scope.FuelTypeForMoteur[k]["entityId"] !== null) {

										MorteurData[i]["fuelTypelabel"] = $scope.FuelTypeForMoteur[k]["fuelTypeLable"];
									}
								}
							}
						};

						$scope.getAllMoteurData();
					}, function() {

					});

				};

				/* Save Carburant Data */
				$scope.saveSelectedCarburantData = function() {
					var tempList = [];

					for (var i = 0; i < $scope.CaburantSelected.length; i++) {
						var temp = {
							'fuelTypeLable' : $scope.CaburantSelected[i]["fuelTypeLable"],
							'carbuTvvRuleId' : $scope.CaburantSelected[i]["carbuTvvRuleId"],
							'entityId' : $scope.CaburantSelected[i]["entityId"],
							'dcd' : 'dcd'
						};
						tempList.push(temp);
					}

					var objectToSend = {
						'fuelTypeRepresentationList' : tempList,
					};
					CarburantService.CarburantResource.saveAllCarburantData(objectToSend, function(sucess) {
						if (sucess.dcd === null) {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.FuelType') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion') + ' : ' + sucess.fuelTypeLable);

						} else {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.FuelType') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
						}
						$scope.iscarburentValidate = true;
						$scope.changesSaved(true);
						$scope.getAllCarburantData();
					}, function(error) {
						NotificationService.notify(CultureService.localize('cop.genomedata.label.FuelType') + ' : ' + CultureService.localize('cop.specificCop.message.errorSave'));
					});

				},

				/* Delete Carburant Data */
				$scope.DeleteCarburantData = function(row) {
					if (row.entity.entityId !== null) {
						var DeleteList = [];
						var temp = {
							'entityId' : row.entity.entityId,
						};

						DeleteList.push(temp);

						var objectToSend = {
							'fuelTypeRepresentationList' : DeleteList,
						};

						CarburantService.CarburantResource.deleteCarburantData(objectToSend, function(success) {
							$scope.getAllCarburantData();
							$scope.getAllFuel();
							NotificationService.notify(CultureService.localize('cop.genomedata.label.FuelType') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
						}, function(error) {
							$scope.getAllCarburantData();
							NotificationService.notify(CultureService.localize('cop.genomedata.label.FuelType') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					}
				},

				/* to get Famille Data */
				$scope.getAllFamilleData = function() {

					this.FamilleButton = true;
					var FamilleResponce = null;
					FamilleResponce = FamilleService.FamilleResource.getAllFamilleData(function() {
						$scope.Famille = FamilleResponce;

					}, function() {

					});
				};

				/* Save Famille Data */
				$scope.saveSelectedFamilleData = function() {
					var tempList = [];

					for (var i = 0; i < $scope.FamilleSelected.length; i++) {
						var temp = {

							'familleTvvRuleId' : $scope.FamilleSelected[i]["familleTvvRuleId"],
							'projectCodeLabel' : $scope.FamilleSelected[i]["projectCodeLabel"],
							'vehicleFamilyLabel' : $scope.FamilleSelected[i]["vehicleFamilyLabel"],
							'entityId' : $scope.FamilleSelected[i]["entityId"],
							'familleBOC' : 'familleBOC'
						};
						tempList.push(temp);
					}

					var objectToSend = {
						'projectCodeFamilyRepresentationList' : tempList,
					};
					FamilleService.FamilleResource.saveAllFamilleData(objectToSend, function(sucess) {
						if (sucess.familleBOC === null) {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.ProjectFamily') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion') + ' : ' + sucess.projectCodeLabel + ' and ' + sucess.vehicleFamilyLabel);

						} else {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.ProjectFamily') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
						}
						$scope.isFamilyValidate = true;
						$scope.changesSaved(true);
						$scope.getAllFamilleData();
					}, function() {
						NotificationService.notify(CultureService.localize('cop.genomedata.label.ProjectFamily') + ' : ' + CultureService.localize('cop.specificCop.message.errorSave'));
					});

				},
				/* Delete Famille Data */
				$scope.DeleteFamilleData = function(row) {
					if (row.entity.entityId !== null) {
						var DeleteList = [];
						var temp = {
							'entityId' : row.entity.entityId,
						};

						DeleteList.push(temp);

						var objectToSend = {
							'projectCodeFamilyRepresentationList' : DeleteList,
						};

						FamilleService.FamilleResource.deleteFamilleData(objectToSend, function() {
							$scope.getAllFamilleData();
							NotificationService.notify(CultureService.localize('cop.genomedata.label.ProjectFamily') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
						}, function() {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.ProjectFamily') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					}
				},

				/* to get Silhouette Data */
				$scope.getAllSilhouetteData = function() {

					this.ShilhouetteButton = true;
					var SilhouetteResponce = null;
					SilhouetteResponce = SilhouetteService.SilhouetteResource.getAllSilhouetteData(function() {
						$scope.Silhouette = SilhouetteResponce;

					}, function() {

					});
				};

				/* Save Silhouette Data */
				$scope.saveSelectedSilhouetteData = function() {
					var tempList = [];

					for (var i = 0; i < $scope.SilhouetteSelected.length; i++) {
						var temp = {

							'silhoutteTvvRuleId' : $scope.SilhouetteSelected[i]["silhoutteTvvRuleId"],
							'silhoutteLable' : $scope.SilhouetteSelected[i]["silhoutteLable"],
							'entityId' : $scope.SilhouetteSelected[i]["entityId"],
							'silhouetteBOD' : 'silhouetteBOD'
						};
						tempList.push(temp);
					}

					var objectToSend = {
						'bodyWorkRepresentationList' : tempList,
					};
					SilhouetteService.SilhouetteResource.saveAllSilhouetteData(objectToSend, function(sucess) {
						if (sucess.silhouetteBOD === null) {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.BodyWork') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion') + ' : ' + sucess.silhoutteLable);

						} else {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.BodyWork') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
						}
						$scope.isSilhouteValidate = true;
						$scope.changesSaved(true);
						$scope.getAllSilhouetteData();
					}, function(error) {

						NotificationService.notify(CultureService.localize('cop.genomedata.label.BodyWork') + ' : ' + CultureService.localize('cop.specificCop.message.errorSave'));
					});

				},
				/* Delete Silhouette Data */
				$scope.DeleteSilhouetteData = function(row) {
					if (row.entity.entityId !== null) {
						var DeleteList = [];
						var temp = {
							'entityId' : row.entity.entityId,
						};

						DeleteList.push(temp);

						var objectToSend = {
							'bodyWorkRepresentationList' : DeleteList,
						};

						SilhouetteService.SilhouetteResource.deleteSilhouetteData(objectToSend, function() {
							$scope.getAllSilhouetteData();
							NotificationService.notify(CultureService.localize('cop.genomedata.label.BodyWork') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
						}, function() {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.BodyWork') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					}
				},

				/* to get Constructeur Data */
				$scope.getAllConstructeurData = function() {

					this.ConstructeurButton = true;
					var ConstructeurResponce = null;
					ConstructeurResponce = ConstructeurService.ConstructeurResource.getAllConstructeurData(function() {
						$scope.Constructeur = ConstructeurResponce;

					}, function() {

					});
				};

				/* Save Constructeur Data */
				$scope.saveSelectedConstructeurData = function() {
					var tempList = [];

					for (var i = 0; i < $scope.ConstructeurSelected.length; i++) {
						var temp = {
							'makerLable' : $scope.ConstructeurSelected[i]["constructeurMakerLabel"],
							'brandLable' : $scope.ConstructeurSelected[i]["constructeurBrandLabel"],
							'constructeurTvvRuleId' : $scope.ConstructeurSelected[i]["constructeurTvvRuleId"],
							'entityId' : $scope.ConstructeurSelected[i]["entityId"],
							'constructeurBOB' : 'constructeurBOB'
						};
						tempList.push(temp);
					}

					var objectToSend = {
						'carBrandRepresentationList' : tempList,
					};
					ConstructeurService.ConstructeurResource.saveAllConstructeurData(objectToSend, function(success) {
						if (success.constructeurBOB === null) {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.CarBrand') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion') + ' : ' + success.brandLable + ' and ' + success.makerLable);

						} else {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.CarBrand') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));

						}
						$scope.isConstructValidate = true;
						$scope.changesSaved(true);
						$scope.getAllConstructeurData();
					}, function(error) {
						NotificationService.notify(CultureService.localize('cop.genomedata.label.CarBrand') + ' : ' + CultureService.localize('cop.specificCop.message.errorSave'));
					});

				},
				/* Delete Constructeur Data */
				$scope.DeleteConstructeurData = function(row) {
					if (row.entity.entityId !== null) {
						var DeleteList = [];
						var temp = {
							'entityId' : row.entity.entityId,
						};

						DeleteList.push(temp);

						var objectToSend = {
							'carBrandRepresentationList' : DeleteList,
						};

						ConstructeurService.ConstructeurResource.deleteConstructeurData(objectToSend, function() {
							$scope.getAllConstructeurData();
							NotificationService.notify(CultureService.localize('cop.genomedata.label.CarBrand') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
						}, function() {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.CarBrand') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					}
				},
				/* to get Usine Data */
				$scope.getAllUsineData = function() {

					this.UsinButton = true;
					var UsineResponce = null;
					UsineResponce = UsineService.UsineResource.getAllUsineData(function() {
						$scope.Usine = UsineResponce;

					}, function() {

					});
				};

				/* Save Usine Data */
				$scope.saveSelectedUsineData = function() {

					for (var i = 0; i < $scope.Usine.length; i++) {

						if ($scope.Usine[i].edited !== false) {
							$scope.UsineSelected.push($scope.Usine[i])
						}
					}
					if ($scope.UsineSelected.length > 0) {
						var objectToSend = {
							'carFactoryRepresentationList' : $scope.UsineSelected
						};
						UsineService.UsineResource.saveAllUsineData(objectToSend, function(sucess) {
							if (sucess.duplicate === true) {
								NotificationService.notify(CultureService.localize('cop.genomedata.label.Factory') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion') + ' : ' + sucess.carFactoryLabel);

							} else {

								NotificationService.notify(CultureService.localize('cop.genomedata.label.Factory') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));

							}
							$scope.isUsinValidate = true;
							$scope.changesSaved(true);
							$scope.UsineSelected.splice(0);
							$scope.getAllUsineData();
						}, function(error) {
							$scope.savingError = true;
							NotificationService.notify(CultureService.localize('cop.genomedata.label.Factory') + ' : ' + CultureService.localize('cop.specificCop.message.errorSave'));
						});

					}
				},
				/* Delete Usine Data */
				$scope.DeleteUsineData = function(row, index) {

					var entityIdToRemove = row.entity.entityId;
					if (angular.isNumber(entityIdToRemove)) {
						var DeleteList = [];
						var temp = {
							'entityId' : row.entity.entityId
						};

						DeleteList.push(temp);

						var objectToSend = {
							'carFactoryRepresentationList' : DeleteList
						};

						UsineService.UsineResource.deleteUsineData(objectToSend, function(sucess) {
							$scope.getAllUsineData();
							NotificationService.notify(CultureService.localize('cop.genomedata.label.Factory') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
						}, function(error) {
							if (error.status = 412) {
								$scope.errorMessage = CultureService.localize('cop.specificCop.message.errorSave');
								NotificationService.notify($scope.errorMessage);
							} else {
								$scope.errorMessage = CultureService.localize('cop.specificCop.message.errorSave');
								NotificationService.notify($scope.errorMessage);
							}

						});
					} else {
						$scope.Usine.splice(index, 1);
					}
				},
				/* to get Pays Data */

				$scope.getAllPaysData = function() {

					this.PayButton = true;
					var PaysResponce = PaysService.PaysResource.getAllPaysData(function() {
						$scope.Pays = PaysResponce;

					}, function() {

					});
				};

				/* Save Pays Data */
				$scope.saveSelectedPaysData = function() {
					var tempList = [];

					for (var i = 0; i < $scope.Pays.length; i++) {
						if ($scope.Pays[i].edited !== false) {

							$scope.PaysSelected.push($scope.Pays[i])
						}

					}

					if ($scope.PaysSelected.length > 0) {
						var objectToSend = {
							'countryRepresentationList' : $scope.PaysSelected
						};
						PaysService.PaysResource.saveAllPaysData(objectToSend, function(sucess) {

							if (sucess.duplicate === true) {
								NotificationService.notify(CultureService.localize('cop.genomedata.label.Country') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion') + ' : ' + sucess.countryLable);

							} else {

								NotificationService.notify(CultureService.localize('cop.genomedata.label.Country') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));

							}
							$scope.isPayValidate = true;
							$scope.PaysSelected.splice(0);
							$scope.getAllPaysData();
						}, function(error) {
							$scope.savingError = true;
							NotificationService.notify(CultureService.localize('cop.genomedata.label.Country') + ' : ' + CultureService.localize('cop.specificCop.message.errorSave'));
						});
					}
				},
				/* Delete Pays Data */
				$scope.DeletePaysData = function(row, index) {
					var entityIdToRemove = row.entity.entityId;
					if (angular.isNumber(entityIdToRemove)) {
						var DeleteList = [];
						var temp = {
							'entityId' : row.entity.entityId
						};

						DeleteList.push(temp);

						var objectToSend = {
							'countryRepresentationList' : DeleteList
						};

						PaysService.PaysResource.deletePaysData(objectToSend, function() {
							$scope.getAllPaysData();
							NotificationService.notify(CultureService.localize('cop.genomedata.label.Country') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
						}, function() {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.Country') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					} else {
						$scope.Pays.splice(index, 1);
					}
				},
				/* to get Reglementation Data */
				$scope.getAllReglementationData = function() {

					this.ReglemenrationButton = true;
					var ReglementationResponce = ReglementationService.ReglementationResource.getAllReglementationData(function() {
						$scope.Reglementation = ReglementationResponce;

					});
				};

				/* Save Reglementation Data */
				$scope.saveSelectedReglementationData = function() {
					var tempList = [];

					for (var i = 0; i < $scope.ReglementationSelected.length; i++) {
						var temp = {
							'esLabel' : $scope.ReglementationSelected[i]["esLabel"],
							'description' : $scope.ReglementationSelected[i]["description"],
							'reglementationTvvRuleId' : $scope.ReglementationSelected[i]["reglementationTvvRuleId"],
							'entityId' : $scope.ReglementationSelected[i]["entityId"],
							'reglementationDKA' : 'ReglementationDKA'
						};
						tempList.push(temp);
					}

					var objectToSend = {
						'emisssionStandardRepresentationList' : tempList
					};
					ReglementationService.ReglementationResource.saveAllReglementationData(objectToSend, function(sucess) {
						if (sucess.ReglementationDKA === null) {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.EmissionStandard') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion') + ' : ' + sucess.esLabel);

						} else {

							NotificationService.notify(CultureService.localize('cop.genomedata.label.EmissionStandard') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
						}
						$scope.isReglementValidate = true;
						$scope.changesSaved(true);
						$scope.getAllReglementationData();
					}, function(error) {

						NotificationService.notify(CultureService.localize('cop.genomedata.label.EmissionStandard') + ' : ' + CultureService.localize('cop.specificCop.message.errorSave'));
					});
				},
				/* Delete Reglementation Data */
				$scope.DeleteReglementationData = function(row) {
					if (row.entity.entityId !== null) {
						var DeleteList = [];
						var temp = {
							'entityId' : row.entity.entityId
						};

						DeleteList.push(temp);

						var objectToSend = {
							'emisssionStandardRepresentationList' : DeleteList
						};

						ReglementationService.ReglementationResource.deleteReglementationData(objectToSend, function() {
							$scope.getAllReglementationData();
							NotificationService.notify(CultureService.localize('cop.genomedata.label.EmissionStandard') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
						}, function() {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.EmissionStandard') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					}
				},
				/* to get Bolte Data */
				$scope.getAllBolteData = function() {
					this.BolteButton = true;
					var BolteResponce = null;
					BolteResponce = BolteService.BolteResource.getAllBolteData(function() {

						$scope.Bolte = BolteResponce;

					}, function() {

					});
				};

				/* Save Bolte Data */
				$scope.saveSelectedBolteData = function() {
					var tempList = [];

					for (var i = 0; i < $scope.BolteSelected.length; i++) {
						var temp = {
							'gearBoxLabel' : $scope.BolteSelected[i]["gearBoxLabel"],
							'gearBoxType' : $scope.BolteSelected[i]["gearBoxType"],
							'gearboxEntity' : $scope.BolteSelected[i]["gearboxEntity"],
							'dbmTVVRuleId' : $scope.BolteSelected[i]["dbmTVVRuleId"],
							'b0gTVVRuleEntity' : $scope.BolteSelected[i]["b0gTVVRuleEntity"],
							'b0gValue' : 'b0gValue'

						};
						tempList.push(temp);
					}

					var objectToSend = {
						'gearBoxRepresentationList' : tempList
					};
					BolteService.BolteResource.saveAllBolteData(objectToSend, function(sucess) {
						if (sucess.b0gValue === null) {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.Gear') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion') + ' : ' + sucess.gearBoxLabel);

						} else {

							NotificationService.notify(CultureService.localize('cop.genomedata.label.Gear') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
						}
						$scope.isBolteValidate = true;
						$scope.changesSaved(true);
						$scope.getAllBolteData();
					}, function(error) {
						$scope.savingError = true;
						NotificationService.notify(CultureService.localize('cop.genomedata.label.Gear') + ' : ' + CultureService.localize('cop.specificCop.message.errorSave'));
					});
				};
				/* Delete Bolte Data */
				$scope.DeleteBolteData = function(row) {
					if (row.entity.gearboxEntity !== null) {
						var DeleteList = [];
						var temp = {
							'gearboxEntity' : row.entity.gearboxEntity
						};

						DeleteList.push(temp);

						var objectToSend = {
							'gearBoxRepresentationList' : DeleteList
						};

						BolteService.BolteResource.deleteBolteData(objectToSend, function() {
							$scope.getAllBolteData();
							NotificationService.notify(CultureService.localize('cop.genomedata.label.Gear') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
						}, function() {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.Gear') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					}
				},
				/* to get FinalReduction Data */
				$scope.getAllFinalReductionData = function() {
					this.FianlReductionButton = true;
					var FinalReductionResponce = null;
					FinalReductionResponce = FinalReductionService.FinalReductionResource.getAllFinalReductionData(function() {
						$scope.FinalReduction = FinalReductionResponce;

					}, function() {

					});
				};

				/* Save FinalReduction Data */
				$scope.saveSelectedFinalReductionData = function() {
					var tempList = [];

					for (var i = 0; i < $scope.FinalReductionSelected.length; i++) {
						var temp = {
							'finalReductionlabel' : $scope.FinalReductionSelected[i]["finalReductionlabel"],
							'finalReductionTvvRuleId' : $scope.FinalReductionSelected[i]["finalReductionTvvRuleId"],
							'entityId' : $scope.FinalReductionSelected[i]["entityId"],
							'finalReductionDCW' : 'finalReductionDCW'
						};
						tempList.push(temp);
					}

					var objectToSend = {
						'finalReductionRepresentationList' : tempList
					};
					FinalReductionService.FinalReductionResource.saveAllFinalReductionData(objectToSend, function(sucess) {
						if (sucess.finalReductionDCW === null) {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.FinalReduction') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion') + ' : ' + sucess.finalReductionlabel);
						} else {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.FinalReduction') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
						}
						$scope.isReductionValidate = true;
						$scope.changesSaved(true);
						$scope.getAllFinalReductionData();
					}, function(error) {

						NotificationService.notify(CultureService.localize('cop.genomedata.label.FinalReduction') + ' : ' + CultureService.localize('cop.specificCop.message.errorSave'));
					});
				};
				/* Delete FinalReduction Data */
				$scope.DeleteFinalReductionData = function(row) {
					if (row.entity.entityId !== null) {
						var DeleteList = [];
						var temp = {
							'entityId' : row.entity.entityId
						};

						DeleteList.push(temp);

						var objectToSend = {
							'finalReductionRepresentationList' : DeleteList
						};

						FinalReductionService.FinalReductionResource.deleteFinalReductionData(objectToSend, function() {
							$scope.getAllFinalReductionData();
							NotificationService.notify(CultureService.localize('cop.genomedata.label.FinalReduction') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
						}, function() {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.FinalReduction') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					}
				},

				/* Save All Selecte Data */
				$scope.SaveAllGenomeData = function() {

					if ($scope.MoteurSelected.length !== 0) {
						$scope.saveSelectedMoteurData();
					}
					if ($scope.CaburantSelected.length !== 0) {
						$scope.saveSelectedCarburantData();
					}
					if ($scope.FamilleSelected.length !== 0) {
						$scope.saveSelectedFamilleData();
					}
					if ($scope.SilhouetteSelected.length !== 0) {
						$scope.saveSelectedSilhouetteData();
					}
					if ($scope.ConstructeurSelected.length !== 0) {
						$scope.saveSelectedConstructeurData();
					}

					if ($scope.ReglementationSelected.length !== 0) {
						$scope.saveSelectedReglementationData();
					}
					if ($scope.BolteSelected.length !== 0) {
						$scope.saveSelectedBolteData();
					}
					if ($scope.FinalReductionSelected.length !== 0) {
						$scope.saveSelectedFinalReductionData();
					}

				};

				/**
				 * ***************************************************Specific COP DATA******************************************************************************************
				 */

				$scope.Carburant = [];
				$scope.CarburantSelected = [];
				$scope.CarburantGridOptions = {
					enableFiltering : true,
					enableCellEditOnFocus : true,
					enableColumnResizing : true,
					enableHorizontalScrollbar : false,
					resizable : true,

					columnDefs : [

							{
								name : 'Libelle',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'label',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.label
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'

							}, {
								name : 'Description',
								displayName : 'Description',
								field : 'description',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.description
								},
								headerCellTemplate : headertemplate,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'

							}, {
								name : 'Fuel par dÃ©faut',
								displayName : 'Fuel par dÃ©faut',
								field : 'defaultFuel',
								enableCellEdit : false,
								enableFiltering : true,
								headerCellTemplate : headertemplate,
								cellTemplate : '<div class="ngSelectionCell"><input type="radio" ng-disabled="" ng-checked="grid.appScope.isChecked(row.entity.defaultFuel)" ng-click="grid.appScope.setSel(row)" /></div>',

							}, {
								name : 'Carburant',
								displayName : CultureService.localize('cop.genomedata.label.EngineFuelType'),
								field : 'fuelTypelabel',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								headerCellTemplate : headertemplate,
								cellTemplate : '<div><input type="select" ng-disabled="true" ng-model="row.entity.fuelTypelabel" ng-attr-title="{{row.entity.fuelTypelabel}}"/></div>',
								editType : 'dropdown',
								editableCellTemplate : 'ui-grid/dropdownEditor',
								editDropdownOptionsArray : $scope.FuelTypeForMoteur,
								editDropdownIdLabel : 'fuelTypeLable',
								editDropdownValueLabel : 'fuelTypeLable',

							}, {
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.deleteFuel(row, grid.renderContainers.body.visibleRowCache.indexOf(row))"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'Carburant',
					onRegisterApi : function(gridApi) {

						/* set gridApi on scope */
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							if (newValue !== oldValue) {
								rowentity.edited = true;
								$scope.isValidated = false;
								$scope.activeTab = "specificCop";
							}
							/*
							 * setting fuelTypeId from drop down
							 */
							for (var i = 0; i < $scope.FuelTypeForMoteur.length; i++) {
								if ($scope.FuelTypeForMoteur[i]['fuelTypeLable'] === rowentity.fuelTypelabel) {
									rowentity.fuelTypeId = $scope.FuelTypeForMoteur[i]['entityId'];

									break;
								}
							}

						});
						
						 
                        $scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });

					}
				};

				$scope.Classe = [];
				$scope.ClasseSelected = [];
				$scope.ClasseGridOptions = {
					enableSorting : false,
					enableFiltering : true,
					enableColumnResizing : true,
					enableCellEditOnFocus : true,
					enableHorizontalScrollbar : false,
					columnDefs : [

							{
								name : 'Libelle',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'label',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.label;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'Description',
								displayName : 'Description',
								field : 'description',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.description;
								},
								headerCellTemplate : headertemplate,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							},

							{
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.deleteClasz(row, grid.renderContainers.body.visibleRowCache.indexOf(row))"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'Classe',
					onRegisterApi : function(gridApi) {

						/* set gridApi on scope */
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							if (newValue !== oldValue) {
								rowentity.edited = true;
								$scope.isValidated = false;
								$scope.activeTab = "specificCop";
							}
						});
						
						  $scope.gridApi.core.on.filterChanged($scope, function () {
						    
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);
                            
                           
	                            

	                         });

					}
				};

				$scope.Categorie = [];
				$scope.CategorieSelected = [];
				$scope.ClaszForCategorie = [];
				$scope.CategorieGridOptions = {
					enableColumnResizing : true,
					enableSorting : false,
					enableFiltering : true,
					enableCellEditOnFocus : true,
					enableHorizontalScrollbar : false,
					columnDefs : [
							{
								name : 'Libelle',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'label',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.label;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'Description',
								displayName : 'Description',
								field : 'description',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.description;
								},
								headerCellTemplate : headertemplate,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:27px;" maxlength="255" ng-model="MODEL_COL_FIELD" ng-attr-title="{{row.entity.description}}"/></div>'
							}, {
								name : 'Classe',
								displayName : CultureService.localize('cop.specificCop.label.Class'),
								field : 'clasz_label',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.clasz_label;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								cellTemplate : '<div><input type="select" style="height:27px;" ng-disabled="true" ng-model="row.entity.clasz_label" ng-attr-title="{{row.entity.clasz_label}}"/></div>',
								editType : 'dropdown',
								editableCellTemplate : 'ui-grid/dropdownEditor',
								editDropdownOptionsArray : $scope.ClaszForCategorie,
								editDropdownIdLabel : 'label',
								editDropdownValueLabel : 'label',
							}, {
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.deleteCategory(row, grid.renderContainers.body.visibleRowCache.indexOf(row))"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}
					],
					data : 'Categorie',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							if (newValue !== oldValue) {
								rowentity.edited = true;
								$scope.isValidated = false;
								$scope.activeTab = "specificCop";
							}

							// setting fuelTypeId from
							// drop down
							for (var i = 0; i < $scope.ClaszForCategorie.length; i++) {
								if ($scope.ClaszForCategorie[i]['label'] === rowentity.clasz_label) {
									rowentity.clasz_Id = $scope.ClaszForCategorie[i]['entityId'];
									break;
								}
							}

						});
						
						 
                        $scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });

					}
				};

				$scope.FuelInjectionType = [];
				$scope.FuelInjectionTypeSelected = [];
				$scope.FuelInjectionTypeGridOptions = {
					enableSorting : true,
					enableFiltering : true,
					enableColumnResizing : true,
					enableRowHeaderSelection : true,
					enableCellEditOnFocus : true,
					enableHorizontalScrollbar : false,
					columnDefs : [

							{
								name : 'Libelle',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'label',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.label;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'Description',
								displayName : 'Description',
								field : 'description',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.description;
								},
								headerCellTemplate : headertemplate,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							},

							{
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.deleteFuelInjectionType(row, grid.renderContainers.body.visibleRowCache.indexOf(row))"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'FuelInjectionType',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							if (newValue !== oldValue) {
								rowentity.edited = true;
								$scope.isValidated = false;
								$scope.activeTab = "specificCop";
							}

						});
						
						 
                        $scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
						

					}
				};

				$scope.VehicleTechnology = [];
				$scope.VehicleTechnologySelected = [];
				$scope.VehicleTechnologyGridOptions = {
					enableSorting : true,
					enableFiltering : true,
					enableColumnResizing : true,
					enableRowHeaderSelection : true,
					enableCellEditOnFocus : true,
					enableHorizontalScrollbar : false,
					columnDefs : [

							{
								name : 'Libelle',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'label',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.label;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'Description',
								displayName : 'Description',
								field : 'description',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.description;
								},
								headerCellTemplate : headertemplate,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							},

							{
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.deleteVehicleTechnology(row, grid.renderContainers.body.visibleRowCache.indexOf(row))"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'VehicleTechnology',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							if (newValue !== oldValue) {
								rowentity.edited = true;
								$scope.isValidated = false;
								$scope.activeTab = "specificCop";
							}

						});
						
						 
                        $scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });

					}
				};

				$scope.ApprovalAreaType = [];
				$scope.TypeApprovalAreaSelected = [];
				$scope.ApprovalAreaTypeGridOptions = {
					enableSorting : false,
					enableFiltering : true,
					enableColumnResizing : true,
					enableRowHeaderSelection : true,
					enableCellEditOnFocus : true,
					enableHorizontalScrollbar : false,
					columnDefs : [

							{
								name : 'Libelle',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'label',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.label;
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'Description',
								displayName : 'Description',
								field : 'description',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.description;
								},
								headerCellTemplate : headertemplate,
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							},

							{
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.deleteTypeApprovalArea(row, grid.renderContainers.body.visibleRowCache.indexOf(row))"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}

					],
					data : 'ApprovalAreaType',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							if (newValue !== oldValue) {
								rowentity.edited = true;
								$scope.isValidated = false;
								$scope.activeTab = "specificCop";
							}

						});
						 
                        $scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });

					}
				};

				$scope.Polluants = [];
				$scope.PollutantSelected = [];
				$scope.PolluantsGridOptions = {
					enableSorting : false,
					enableFiltering : true,
					enableColumnResizing : true,
					enableCellEditOnFocus : true,
					enableHorizontalScrollbar : false,
					columnDefs : [

							{
								name : 'Libelle',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'label',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.label
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.deletePollutant(row, grid.renderContainers.body.visibleRowCache.indexOf(row))"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}
					],
					data : 'Polluants',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {
							if (newValue !== oldValue) {
								rowentity.edited = true;
								$scope.isValidated = false;
								$scope.activeTab = "specificCop";
							}

						});
						 
                        $scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });

					}
				};

				$scope.FactorApplication = [];
				$scope.FactorApplicationSelected = [];
				$scope.FactorApplicationGridOptions = {
					enableSorting : false,
					enableFiltering : true,
					enableColumnResizing : true,
					enableRowHeaderSelection : true,
					enableCellEditOnFocus : true,
					enableHorizontalScrollbar : false,
					columnDefs : [

							{
								name : 'Libelle',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'label',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.label
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.deleteFactorCoeffAppType(row, grid.renderContainers.body.visibleRowCache.indexOf(row))"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'
							}
					],
					data : 'FactorApplication',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							if (newValue !== oldValue) {
								rowentity.edited = true;
								$scope.isValidated = false;
								$scope.activeTab = "specificCop";
							}

						});
						
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
					}
				};

				$scope.VehicleCoefficients = [];
				$scope.VehicleCoefficientsSelected = [];
				$scope.VehicleCoefficientsGridOptions = {
					enableSorting : false,
					enableFiltering : true,
					enableColumnResizing : true,
					enableRowHeaderSelection : true,
					enableCellEditOnFocus : true,
					enableHorizontalScrollbar : false,
					columnDefs : [

							{
								name : 'Libelle',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'label',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},

								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								cellTemplate : '<div class="ui-grid-cell-contents"><input type="text" style="height:27px;" maxlength="255" ng-model="row.entity.label" ng-attr-title="{{row.entity.label}}" ng-change="grid.appScope.rowEdited(row)"/></div>'
							}, {
								name : 'Description',
								displayName : 'Description',
								field : 'description',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								headerCellTemplate : headertemplate,
								cellTemplate : '<div class="ui-grid-cell-contents"><input type="text" style="height:27px;" maxlength="255" ng-model="row.entity.description" ng-attr-title="{{row.entity.description}}" ng-change="grid.appScope.rowEdited(row)"/></div>'
							}, {
								name : 'Coefficient f0',
								displayName : 'Coefficient f0',
								field : 'f0Coeffiecient',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								CellTemplate : '<div class="ui-grid-cell-contents"><input type="text" style="height:27px;" maxlength="255" ng-pattern="/^[0-9]*$/" ng-model="row.entity.f0Coeffiecient" ng-attr-title="{{row.entity.f0Coeffiecient}}" ng-change="grid.appScope.rowEdited(row)"/></div>'
							}, {
								name : 'Coefficient f1',
								displayName : 'Coefficient f1',
								field : 'f1Coeffiecient',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								cellTemplate : '<div class="ui-grid-cell-contents"><input type="text" style="height:27px;" maxlength="255" ng-pattern="/^[0-9]*$/" ng-model="row.entity.f1Coeffiecient" ng-attr-title="{{row.entity.f1Coeffiecient}}" ng-change="grid.appScope.rowEdited(row)"/></div>'
							}, {
								name : 'Coefficient f2',
								displayName : 'Coefficient f2',
								field : 'f2Coeffiecient',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								cellTemplate : '<div class="ui-grid-cell-contents"><input type="text" style="height:27px;" maxlength="255" ng-pattern="/^[0-9]*$/" ng-model="row.entity.f2Coeffiecient" ng-attr-title="{{row.entity.f2Coeffiecient}}" ng-change="grid.appScope.rowEdited(row)"/></div>'
							}
					],
					data : 'VehicleCoefficients',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							if (newValue !== oldValue) {
								rowentity.edited = true;
								$scope.isValidated = false;
								$scope.activeTab = "specificCop";
							}

						});
						
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });

					}
				};
				/** **** On change of row value *** */
				$scope.rowEdited = function(row) {
					row.entity.edited = true;
				}

				$scope.tireBrand = [];
				$scope.tireBrandSelected = [];
				$scope.tireBrandGridOptions = {
					enableSorting : false,
					enableFiltering : true,
					enableColumnResizing : true,
					enableRowHeaderSelection : true,
					enableCellEditOnFocus : true,
					columnDefs : [

							{
								name : 'Libelle',
								displayName : CultureService.localize('cop.genomedata.label.Label'),
								field : 'label',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTooltip : function(row) {
									return row.entity.label
								},
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							}, {
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.deleteTireBrand(row, grid.renderContainers.body.visibleRowCache.indexOf(row))")"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'

							}
					],
					data : 'tireBrand',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							if (newValue !== oldValue) {
								rowentity.edited = true;
							}

						});
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
					}
				};

				/**
				 * ************LambdaDecisionParameters Grid****************************************
				 */

				$scope.lambdaDecisionParameters = [];
				$scope.lambdaDecisionParametersSelected = [];
				$scope.lambdaDecisionParametersGridOptions = {
					enableSorting : false,
					enableFiltering : true,
					enableColumnResizing : true,
					enableRowHeaderSelection : true,
					enableCellEditOnFocus : true,
					columnDefs : [
							{

								name : 'fuelTypeLabel',
								field : 'fuelTypeLabel',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								displayName : 'Type de carburant',
								cellTemplate : '<div><input type="select"   style="height:27px;" ng-disabled="true" ng-model="row.entity.fuelTypeLabel"  ng-attr-title="{{row.entity.fuelTypeLabel}}"/></div>',
								editType : 'dropdown',
								editableCellTemplate : 'ui-grid/dropdownEditor',
								editDropdownOptionsArray : $scope.fuelTypedataForLambdaDecisionParameters,
								editDropdownIdLabel : 'fuelTypeLabel',
								editDropdownValueLabel : 'fuelTypeLabel',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',

							},

							{
								name : 'lowerSymbol',
								displayName : '> ou >=',
								field : 'lowerSymbol',
								enableCellEdit : authentication,
								cellTemplate : '<div><input type="select" style="height:27px;" ng-disabled="true" ng-model="MODEL_COL_FIELD" ng-attr-title="{{col.displayName}} "/></div>',
								editType : 'dropdown',
								editableCellTemplate : 'ui-grid/dropdownEditor',
								editDropdownOptionsArray : $scope.lowerSymbolOptionsForLambdaDecisionParameters,
								editDropdownIdLabel : 'lowerLabel',
								editDropdownValueLabel : 'lowerLabel',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',

							},

							{
								name : 'lowerLimit',
								displayName : ' Borne inf',
								field : 'lowerLimit',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								cellTemplate : '<div><input type="text" style="height:27px;" ng-disabled="true" ng-model="MODEL_COL_FIELD"  required" ng-pattern="/^[0-9,]{1,7}(\.[0-9]+)?$/" frdecimal2/></div>',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="text" frdecimal2 ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"  ng-pattern="/^[0-9]{1,7}(\.[0-9]+)?$/" frdecimal2 required/></div>'
							},

							{
								name : 'higherSymbol',
								displayName : '< ou <=',
								field : 'higherSymbol',
								enableCellEdit : authentication,
								cellTemplate : '<div><input type="select" style="height:27px;" ng-disabled="true" ng-model="MODEL_COL_FIELD" ng-attr-title="{{col.displayName}} "/></div>',
								editType : 'dropdown',
								editableCellTemplate : 'ui-grid/dropdownEditor',
								editDropdownOptionsArray : $scope.higherSymbolOptionsForLambdaDecisionParameters,
								editDropdownIdLabel : 'higherLabel',
								editDropdownValueLabel : 'higherLabel',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',

							}, {
								name : 'higherLimit',
								displayName : 'Borne sup',
								field : 'higherLimit',
								enableCellEdit : authentication,
								filter : {
									condition : $scope.customFilterCondition
								},
								cellTemplate : '<div><input type="text" style="height:27px;" ng-disabled="true" ng-model="MODEL_COL_FIELD"  required" ng-pattern="/^[0-9,]{1,7}(\.[0-9]+)?$/" frdecimal2/></div>',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="txt" frdecimal2 ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"  ng-pattern="/^[0-9]{1,7}(\.[0-9]+)?$/" frdecimal2 required/></div>'
							},

							{
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.deleteLambdaDecisionParameters(row, grid.renderContainers.body.visibleRowCache.indexOf(row))")"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'

							}
					],
					data : 'lambdaDecisionParameters',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							if (newValue !== oldValue) {
								rowentity.edited = true;
							}

						});
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
					}
				};

				/**
				 * ************OpacityDecisionParametersGrid****************************************
				 */

				$scope.opacityDecisionParameters = [];
				$scope.opacityDecisionParametersSelected = [];
				$scope.opacityDecisionParametersGridOptions = {
					enableSorting : false,
					enableFiltering : true,
					enableColumnResizing : true,
					enableRowHeaderSelection : true,
					enableCellEditOnFocus : true,
					columnDefs : [
							{

								name : 'fuelTypeLabel',
								field : 'fuelTypeLabel',
								displayName : 'Type de carburant',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								cellTemplate : '<div><input type="select" style="height:27px;" ng-disabled="true" ng-model="row.entity.fuelTypeLabel" ng-attr-title="{{row.entity.fuelTypeLabel}}"/></div>',
								editType : 'dropdown',
								editableCellTemplate : 'ui-grid/dropdownEditor',
								editDropdownOptionsArray : $scope.fuelTypedataForOpacityDecisionParameters,
								editDropdownIdLabel : 'fuelTypeLabel',
								editDropdownValueLabel : 'fuelTypeLabel',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',

							},

							{
								name : 'lowerSymbol',
								displayName : '> ou >=',
								field : 'lowerSymbol',
								enableCellEdit : authentication,
								cellTemplate : '<div><input type="select" style="height:27px;" ng-disabled="true" ng-model="MODEL_COL_FIELD" ng-attr-title="{{col.displayName}} required"/></div>',
								editType : 'dropdown',
								editableCellTemplate : 'ui-grid/dropdownEditor',
								editDropdownOptionsArray : $scope.lowerSymbolOptionsForOpacityDecisionParameters,
								editDropdownIdLabel : 'lowerLabel',
								editDropdownValueLabel : 'lowerLabel',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',

							},

							{
								name : 'lowerLimit',
								displayName : ' Borne inf',
								field : 'lowerLimit',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								cellTemplate : '<div><input type="text" style="height:27px;" ng-disabled="true" ng-model="MODEL_COL_FIELD"  required" ng-pattern="/^[0-9,]{1,7}(\.[0-9]+)?$/" frdecimal2/></div>',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="text" frdecimal2 ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"  ng-pattern="/^[0-9]{1,7}(\.[0-9]+)?$/" frdecimal2 required/></div>'
							},

							{
								name : 'higherSymbol',
								displayName : '< ou <=',
								field : 'higherSymbol',
								enableCellEdit : authentication,
								cellTemplate : '<div><input type="select" style="height:27px;" ng-disabled="true" ng-model="MODEL_COL_FIELD" ng-attr-title="{{col.displayName}} required"/></div>',
								editType : 'dropdown',
								editableCellTemplate : 'ui-grid/dropdownEditor',
								editDropdownOptionsArray : $scope.higherSymbolOptionsForOpacityDecisionParameters,
								editDropdownIdLabel : 'higherLabel',
								editDropdownValueLabel : 'higherLabel',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',

							}, {
								name : 'higherLimit',
								displayName : 'Borne sup',
								field : 'higherLimit',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								cellTemplate : '<div><input type="text" style="height:27px;" ng-disabled="true" ng-model="MODEL_COL_FIELD"  required" ng-pattern="/^[0-9,]{1,7}(\.[0-9]+)?$/" frdecimal2/></div>',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="text" frdecimal2 ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"  ng-pattern="/^[0-9]{1,7}(\.[0-9]+)?$/" frdecimal2 required/></div>'
							},

							{
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.deleteOpacityDecisionParameters(row, grid.renderContainers.body.visibleRowCache.indexOf(row))")"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'

							}
					],
					data : 'opacityDecisionParameters',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							if (newValue !== oldValue) {
								rowentity.edited = true;
							}

						});
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
					}
				};

				/**
				 * ************CODecisionParameters Grid****************************************
				 */
				$scope.coDecisionParameters = [];
				$scope.coDecisionParametersSelected = [];
				$scope.coDecisionParametersGridOptions = {

					enableSorting : false,
					enableFiltering : true,
					enableColumnResizing : true,
					enableCellEditOnFocus : true,
					columnDefs : [

							{

								name : 'fuelTypeLabel',
								field : 'fuelTypeLabel',
								displayName : 'Type de carburant',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								cellTemplate : '<div><input type="select"  class="required" style="height:27px;" ng-disabled="true" ng-model="row.entity.fuelTypeLabel" ng-attr-title="{{row.entity.fuelTypeLabel}}"/></div>',
								editType : 'dropdown',
								editableCellTemplate : 'ui-grid/dropdownEditor',
								editDropdownOptionsArray : $scope.fuelTypedataForCoDecisionParameters,
								editDropdownIdLabel : 'fuelTypeLabel',
								editDropdownValueLabel : 'fuelTypeLabel',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
							},

							{
								name : 'lowerSymbol',
								displayName : '> ou >=',
								field : 'lowerSymbol',
								enableCellEdit : authentication,
								cellTemplate : '<div><input type="select" style="height:27px;" ng-disabled="true" ng-model="MODEL_COL_FIELD" ng-attr-title="{{col.displayName}} required"/></div>',
								editType : 'dropdown',
								editableCellTemplate : 'ui-grid/dropdownEditor',
								editDropdownOptionsArray : $scope.lowerSymbolOptionsForCoDecisionParameter,
								editDropdownIdLabel : 'lowerLabel',
								editDropdownValueLabel : 'lowerLabel',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',

							},

							{
								name : 'lowerLimit',
								displayName : 'Borne inf',
								field : 'lowerLimit',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								cellTemplate : '<div><input type="text" style="height:27px;" ng-disabled="true" ng-model="MODEL_COL_FIELD"  required" ng-pattern="/^[0-9,]{1,7}(\.[0-9]+)?$/" frdecimal2/></div>',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="text" frdecimal2 ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"  ng-pattern="/^[0-9]{1,7}(\.[0-9]+)?$/" frdecimal2 required/></div>'
							},

							{
								name : 'higherSymbol',
								displayName : '< ou <=',
								field : 'higherSymbol',
								enableCellEdit : authentication,
								cellTemplate : '<div><input type="select" style="height:27px;" ng-disabled="true" ng-model="MODEL_COL_FIELD" ng-attr-title="{{col.displayName}} required"/></div>',
								editType : 'dropdown',
								editableCellTemplate : 'ui-grid/dropdownEditor',
								editDropdownOptionsArray : $scope.higherSymbolOptionsForCoDecisionParameter,
								editDropdownIdLabel : 'higherLabel',
								editDropdownValueLabel : 'higherLabel',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',

							}, {
								name : 'higherLimit',
								displayName : 'Borne sup',
								field : 'higherLimit',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								cellTemplate : '<div><input type="text" style="height:27px;" ng-disabled="true" ng-model="MODEL_COL_FIELD"  required" ng-pattern="/^[0-9,]{1,7}(\.[0-9]+)?$/" frdecimal2/></div>',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="text" frdecimal2 ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"  ng-pattern="/^[0-9,]{1,7}(\.[0-9]+)?$/" frdecimal2 required/></div>'
							},

							{
								name : 'DeleteRow',
								displayName : '',
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.deleteCODecisionParameters(row, grid.renderContainers.body.visibleRowCache.indexOf(row))")"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'

							}
					],
					data : 'coDecisionParameters',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							if (newValue !== oldValue) {
								rowentity.edited = true;
							}

						});
						
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
					}
				};

				/**
				 * ************TEAMUSER Grid****************************************
				 */

				$scope.teamUsers = [];

				$scope.teamUsersSelected = [];
				$scope.teamUsersGridOptions = {
					enableSorting : false,
					enableFiltering : true,
					enableColumnResizing : true,
					enableRowHeaderSelection : true,
					enableCellEditOnFocus : true,
					columnDefs : [

							{

								name : 'teamLabel',
								field : 'teamLabel',
								displayName : 'Equipe',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								// headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}</span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text"
								// class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel"
								// ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}</span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							},

							{

								name : 'userId',
								field : 'userId',
								displayName : 'Identifiant',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							},

							{

								name : 'firstLastName',
								field : 'firstLastName',
								displayName : 'Prénom Nom',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableCellEdit : authentication,
								headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}</span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>',
								editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
							},

							{
								name : 'DeleteRow',
								displayName : '',
								filter : {
									condition : $scope.customFilterCondition
								},
								enableFiltering : false,
								enableSorting : false,
								enableCellEdit : false,
								headerCellTemplate : '<div class="ngHeaderText"></div>',
								width : 35,
								visible : authentication,
								cellTemplate : '<button class="btn primary" ng-click="grid.appScope.deleteTeamUsers(row, grid.renderContainers.body.visibleRowCache.indexOf(row))")"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>'

							}
					],
					data : 'teamUsers',
					onRegisterApi : function(gridApi) {

						// set gridApi on scope
						$scope.gridApi = gridApi;

						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							if (newValue !== oldValue) {
								rowentity.edited = true;
							}

							if (colDef.field === "userId") {

								var count = 0;
								for (var i = 0; i < $scope.teamUsers.length; i++) {
									if ($scope.teamUsers[i] !== undefined && newValue !== undefined && $scope.teamUsers[i].userId !== undefined && $scope.teamUsers[i].userId.toUpperCase() === newValue.toUpperCase()) {
										count = count + 1;
									}

									if (count >= 2) {
										NotificationService.notify(CultureService.localize('cop.specificCop.label.teamUsers') + ' : ' + CultureService.localize('cop.specificCop.message.duplicateVersion'));
										rowentity.userId = oldValue;
										break;
									}

								}

							}

						});
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
					}
				};

				/* Get All Clasz Data */
				$scope.getAllClasz = function() {
					$scope.ClaszForCategorie.splice(0, $scope.ClaszForCategorie.length);

					var ClaszData = ClaszService.ClaszResource.getAllClasz(function() {
						$scope.Classe = ClaszData;

						for (var i = 0; i < $scope.Classe.length; i++) {
							var temp = {
								'entityId' : $scope.Classe[i]["entityId"],
								'label' : $scope.Classe[i]["label"],

							};

							$scope.ClaszForCategorie.push(temp);

						}
						$scope.ClasseSelected = [];
					}, function() {

					});
				};

				/* Save Selected Clasz List */
				$scope.saveClasz = function() {

					$scope.ClasseSelected = [];

					var labelflag = true;

					for (var i = 0; i < $scope.Classe.length; i++) {
						if ($scope.Classe[i]['label'] !== '') {

							if ($scope.Classe[i]['edited'] === true) {

								$scope.ClasseSelected.push($scope.Classe[i]);
							}
						} else {
							labelflag = false;
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Class') + ' : ' + CultureService.localize('cop.coastdown.message.reuiredfield'));

							break;
						}

					}
					if (($scope.ClasseSelected.length > 0) && labelflag) {

						var objectToSend = {
							'claszRepresentationList' : $scope.ClasseSelected
						};

						ClaszService.ClaszResource.saveClasz(objectToSend, function(sucess) {
							if (sucess.label === null) {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.Class') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion'));
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.Class') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));

							}
							$scope.getAllClasz();
							$scope.getAllCategory();
						}, function(error) {
							$scope.getAllCategory();
						});

					}
				};

				/* Delete Seleted Clasz Row */
				$scope.deleteClasz = function(row, index) {

					var entityIdToRemove = row.entity.entityId;
					if (angular.isNumber(entityIdToRemove)) {

						ClaszService.ClaszResource.deleteClasz({
							entityId : entityIdToRemove
						}, function(Sucess) {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Class') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
							$scope.getAllClasz();
						}, function(error) {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Class') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					} else {
						$scope.Classe.splice(index, 1);
					}
				};

				$scope.getAllPollutantData = function() {
					var PollutantData = PollutantService.PollutantResource.getAllPollutantData(function() {
						$scope.Polluants = PollutantData;
						$scope.PollutantSelected = [];
					}, function() {

					});
				};

				/* Save Selected Pollutant List */
				$scope.savePollutant = function() {

					$scope.PollutantSelected = [];
					var labelflag = true;

					for (var i = 0; i < $scope.Polluants.length; i++) {

						if ($scope.Polluants[i]['label'] !== '') {

							if ($scope.Polluants[i]['edited'] === true) {

								$scope.PollutantSelected.push($scope.Polluants[i]);
							}
						} else {

							labelflag = false;
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Pollutant') + ' : ' + CultureService.localize('cop.coastdown.message.reuiredfield'));
							break;
						}

					}

					if ($scope.PollutantSelected.length > 0 && labelflag) {

						var objectToSend = {
							'pollutantRepresentationList' : $scope.PollutantSelected
						};

						PollutantService.PollutantResource.savePollutant(objectToSend, function(sucess) {
							if (sucess.label === null) {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.Pollutant') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion'));
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.Pollutant') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
							}
							$scope.getAllPollutantData();
						}, function(error) {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Pollutant') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
						});

					}

				};

				/* Delete Seleted Pollutant Row */
				$scope.deletePollutant = function(row, index) {

					var entityIdToRemove = row.entity.entityId;
					if (angular.isNumber(entityIdToRemove)) {
						PollutantService.PollutantResource.deletePollutant({
							entityId : entityIdToRemove
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Pollutant') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
							$scope.getAllPollutantData();
						},
						function(error) {
							if (error.status = 412) {
								$scope.errorMessage = CultureService.localize('cop.specificCop.message.errordelete');
								NotificationService.notify($scope.errorMessage);
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.Pollutant') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
								
							}
/* function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Pollutant') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
				*/		});
					} else {
						$scope.Polluants.splice(index, 1);
					}
				};
				$scope.getAllFuelInjectionType = function() {
					$scope.FuelInjectionForMoture.splice(0, $scope.FuelInjectionForMoture.length);

					var fuelInjectionType = FuelInjectionTypeService.FuelInjectionTypeResource.getFuelInjectionType(function() {
						$scope.FuelInjectionType = fuelInjectionType;
						$scope.FuelInjectionTypeSelected = [];

						/*
						 * to add fuelinjection label in morteur
						 */
						$scope.addFuelInjectionLabel = function(MorteurData) {

							for (var i = 0; i < MorteurData.length; i++) {
								for (var k = 0; k < $scope.FuelInjectionType.length; k++) {

									if (MorteurData[i]["fuelInjectionID"] === $scope.FuelInjectionType[k]["entityId"]) {
										MorteurData[i]["fuelInjectionlabel"] = $scope.FuelInjectionType[k]["label"];
									}
								}
							}
						};

						for (var i = 0; i < $scope.FuelInjectionType.length; i++) {
							var temp = {
								'entityId' : $scope.FuelInjectionType[i]["entityId"],
								'label' : $scope.FuelInjectionType[i]["label"],

							};

							var flag = true;
							for (var j = 0; j < $scope.FuelInjectionForMoture.length; j++) {
								if ($scope.FuelInjectionForMoture[j]['entityId'] === $scope.FuelInjectionType[i]["entityId"]) {

									$scope.FuelInjectionForMoture[j]['label'] = temp.label;
									flag = false;

									break;
								}

							}
							if (flag) {
								$scope.FuelInjectionForMoture.push(temp);
							}
						}

					}, function() {

					});
					$scope.getAllCarburantData();
				};

				/*
				 * Save Selected Fuel Injection Type List
				 */
				$scope.saveFuelInjectionType = function() {
					$scope.FuelInjectionTypeSelected = [];

					var labelflag = true;

					for (var i = 0; i < $scope.FuelInjectionType.length; i++) {
						if ($scope.FuelInjectionType[i]['label'] !== '') {

							if ($scope.FuelInjectionType[i]['edited'] === true) {

								$scope.FuelInjectionTypeSelected.push($scope.FuelInjectionType[i]);
							}
						} else {

							labelflag = false;
							NotificationService.notify(CultureService.localize('cop.genomedata.label.EngineFuelInjection') + ' : ' + CultureService.localize('cop.coastdown.message.reuiredfield'));
							break;
						}

					}
					if ($scope.FuelInjectionTypeSelected.length > 0 && labelflag) {

						var objectToSend = {
							'fuelInjectionTypeRepresentationList' : $scope.FuelInjectionTypeSelected
						};

						FuelInjectionTypeService.FuelInjectionTypeResource.saveFuelInjectionType(objectToSend, function(sucess) {
							if (sucess.label === null) {

								NotificationService.notify(CultureService.localize('cop.genomedata.label.EngineFuelInjection') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion'));
							} else {
								NotificationService.notify(CultureService.localize('cop.genomedata.label.EngineFuelInjection') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
							}
							$scope.getAllFuelInjectionType();
						}, function(error) {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.EngineFuelInjection') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
						});

					}
				};

				/*
				 * Delete Seleted Fuel Injection Type Row
				 */
				$scope.deleteFuelInjectionType = function(row, index) {

					var entityIdToRemove = row.entity.entityId;
					if (angular.isNumber(entityIdToRemove)) {
						FuelInjectionTypeService.FuelInjectionTypeResource.deleteFuelInjectionType({
							entityId : entityIdToRemove
						}, function() {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.EngineFuelInjection') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
							$scope.getAllFuelInjectionType();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.genomedata.label.EngineFuelInjection') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					} else {
						$scope.FuelInjectionType.splice(index, 1);
					}
				};

				$scope.getAllVehicleTechnology = function() {
					var vehicleTechnology = VehicleTechnologyService.VehicleTechnologyResource.getAllVehicleTechnology(function() {
						$scope.VehicleTechnology = vehicleTechnology;
						$scope.VehicleTechnologySelected = [];
					}, function() {
					});
				};

				/* Save Selected Vehicle Technology */
				$scope.saveVehicleTechnology = function() {
					$scope.VehicleTechnologySelected = [];

					var labelflag = true;

					for (var i = 0; i < $scope.VehicleTechnology.length; i++) {

						if ($scope.VehicleTechnology[i]['label'] !== '') {

							if ($scope.VehicleTechnology[i]['edited'] === true) {

								$scope.VehicleTechnologySelected.push($scope.VehicleTechnology[i]);
							}
						} else {
							labelflag = false;
							NotificationService.notify(CultureService.localize('cop.specificCop.label.VehicleTechnology') + ' : ' + CultureService.localize('cop.coastdown.message.reuiredfield'));

							break;
						}

					}
					if ($scope.VehicleTechnologySelected.length > 0 && labelflag) {

						var objectToSend = {
							'vechicleTechnologyRepresentationList' : $scope.VehicleTechnologySelected
						};

						VehicleTechnologyService.VehicleTechnologyResource.saveVehicleTechnology(objectToSend, function(sucess) {
							if (sucess.label === null) {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.VehicleTechnology') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion'));
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.VehicleTechnology') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
							}
							$scope.getAllVehicleTechnology();
						}, function(error) {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.VehicleTechnology') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
						});

					}
				};

				/* Delete Seleted Vehicle Technology Row */

				$scope.deleteVehicleTechnology = function(row, index) {

					var entityIdToRemove = row.entity.entityId;
					if (angular.isNumber(entityIdToRemove)) {
						VehicleTechnologyService.VehicleTechnologyResource.deleteVehicleTechnology({
							entityId : entityIdToRemove
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.VehicleTechnology') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
							$scope.getAllVehicleTechnology();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.VehicleTechnology') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					} else {
						$scope.VehicleTechnology.splice(index, 1);
					}
				};

				$scope.getAllTypeApprovalArea = function() {
					var allApprovalAreaType = TypeApprovalAreaService.TypeApprovalAreaResource.getAllTypeApprovalArea(function() {
						$scope.ApprovalAreaType = allApprovalAreaType;
						$scope.TypeApprovalAreaSelected = [];
					}, function() {

					});
				};

				/* Save Selected Type Approval Area */
				$scope.saveTypeApprovalArea = function() {
					$scope.TypeApprovalAreaSelected = [];
					var labelflag = true;

					for (var i = 0; i < $scope.ApprovalAreaType.length; i++) {
						if ($scope.ApprovalAreaType[i]['label'] !== '') {

							if ($scope.ApprovalAreaType[i]['edited'] === true) {

								$scope.TypeApprovalAreaSelected.push($scope.ApprovalAreaType[i]);
							}
						} else {
							labelflag = false;
							NotificationService.notify(CultureService.localize('cop.specificCop.label.TypeApproval') + ' : ' + CultureService.localize('cop.coastdown.message.reuiredfield'));

							break;
						}

					}
					if ($scope.TypeApprovalAreaSelected.length > 0 && labelflag) {

						var objectToSend = {
							'typeApprovalAreaRepresentationList' : $scope.TypeApprovalAreaSelected
						};

						TypeApprovalAreaService.TypeApprovalAreaResource.saveTypeApprovalArea(objectToSend, function(sucess) {
							if (sucess.label === null) {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.TypeApproval') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion'));
								$scope.initializeEMS(CultureService.localize('cop.genomedata.model.error'), CultureService.localize('cop.coastdown.message.duplicateVersion'));
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.TypeApproval') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
							}
							$scope.getAllTypeApprovalArea();
						}, function(error) {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.TypeApproval') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
						});

					}

				};

				/* Delete Seleted Type Approval Area Row */
				$scope.deleteTypeApprovalArea = function(row, index) {

					var entityIdToRemove = row.entity.entityId;
					if (angular.isNumber(entityIdToRemove)) {

						TypeApprovalAreaService.TypeApprovalAreaResource.deleteTypeApprovalArea({
							entityId : entityIdToRemove
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.TypeApproval') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
							$scope.getAllTypeApprovalArea();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.TypeApproval') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					} else {
						$scope.ApprovalAreaType.splice(index, 1);
					}
				};

				$scope.getAllFactorCoeffApplicationType = function() {
					var FactorApplication = FactorCoeffApplicationTypeService.FactorCoeffApplicationTypeResource.getAllFactorCoeffApplicationType(function() {
						$scope.FactorApplication = FactorApplication;
						$scope.FactorApplicationSelected = [];
					}, function() {

					});
				};

				/*
				 * Save Selected Factor Coeff Application Type
				 */
				$scope.saveFactorCoeffAppType = function() {
					$scope.FactorApplicationSelected = [];

					var labelflag = true;

					for (var i = 0; i < $scope.FactorApplication.length; i++) {
						if ($scope.FactorApplication[i]['label'] !== '') {

							if ($scope.FactorApplication[i]['edited'] === true) {

								$scope.FactorApplicationSelected.push($scope.FactorApplication[i]);
							}
						} else {
							labelflag = false;
							NotificationService.notify(CultureService.localize('cop.specificCop.label.FactorApplication') + ' : ' + CultureService.localize('cop.coastdown.message.reuiredfield'));

							break;
						}

					}

					if ($scope.FactorApplicationSelected.length > 0 && labelflag) {

						var objectToSend = {
							'factorCoeffAppTypeRepresentationList' : $scope.FactorApplicationSelected
						};

						FactorCoeffApplicationTypeService.FactorCoeffApplicationTypeResource.saveFactorCoeffAppType(objectToSend, function(sucess) {
							if (sucess.label === null) {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.FactorApplication') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion'));
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.FactorApplication') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
							}
							$scope.getAllFactorCoeffApplicationType();
						}, function(error) {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.FactorApplication') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
						});

					}

				};

				/*
				 * Delete Seleted Factor Coeff Application Row
				 */
				$scope.deleteFactorCoeffAppType = function(row, index) {

					var entityIdToRemove = row.entity.entityId;
					if (angular.isNumber(entityIdToRemove)) {

						FactorCoeffApplicationTypeService.FactorCoeffApplicationTypeResource.deleteFactorCoeffAppType({
							entityId : entityIdToRemove
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.FactorApplication') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
							$scope.getAllFactorCoeffApplicationType();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.FactorApplication') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					} else {
						$scope.FactorApplication.splice(index, 1);
					}
				};

				$scope.getAllVehicleCoefficents = function() {
					var VehicleCoefficients = VehicleCoefficentsService.VehicleCoefficentsResource.getAllVehicleCoefficents(function() {
						$scope.VehicleCoefficients = VehicleCoefficients;
						if ($scope.VehicleCoefficients != undefined && $scope.VehicleCoefficients.length < 1) {
							$scope.VehicleCoefficients.push({
								label : 'Vehicle Coefficient',
								f0Coeffiecient : 0,
								f1Coeffiecient : 0,
								f2Coeffiecient : 0
							});

						}
						$scope.VehicleCoefficientsSelected = [];
					}, function() {

					});
				};

				/* Save Selected Vehicle Coeff */
				$scope.saveVehicleCoefficents = function() {

					$scope.VehicleCoefficientsSelected = [];

					var labelflag = true;

					if ($scope.VehicleCoefficients[0]['label'] !== '' && $scope.VehicleCoefficients[0]['f0Coeffiecient'] !== null && $scope.VehicleCoefficients[0]['f1Coeffiecient'] !== null && $scope.VehicleCoefficients[0]['f2Coeffiecient'] !== null) {
						if ($scope.VehicleCoefficients[0]['edited'] === true) {

							$scope.VehicleCoefficientsSelected.push($scope.VehicleCoefficients[0]);
						}
					} else {
						labelflag = false;
						NotificationService.notify(CultureService.localize('cop.specificCop.label.VehicleCoefficient') + ' : ' + CultureService.localize('cop.coastdown.message.reuiredfield'));

					}
					if ($scope.VehicleCoefficientsSelected.length > 0 && labelflag) {

						for (var i = 0; i < $scope.VehicleCoefficientsSelected.length; i++) {
							var INTEGER_REG = new RegExp("^[-]?[0-9]+$");

							if (INTEGER_REG.test($scope.VehicleCoefficientsSelected[i]["f0Coeffiecient"]) && INTEGER_REG.test($scope.VehicleCoefficientsSelected[i]["f1Coeffiecient"]) && INTEGER_REG.test($scope.VehicleCoefficientsSelected[i]["f2Coeffiecient"]))

							{
								var objectToSend = {
									'vehicleCoeffRepresentationList' : $scope.VehicleCoefficientsSelected
								};

								VehicleCoefficentsService.VehicleCoefficentsResource.saveVehicleCoefficents(objectToSend, function(sucess) {
									NotificationService.notify(CultureService.localize('cop.specificCop.label.VehicleCoefficient') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));

									$scope.getAllVehicleCoefficents();

								}, function(error) {
									NotificationService.notify(CultureService.localize('cop.specificCop.label.VehicleCoefficient') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
								});
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.VehicleCoefficient') + ' : ' + CultureService.localize('cop.specificCop.message.numbercheck'));
								break;
							}
						}

					}
				};

				/* Delete Seleted Vehicle Coeff Row */
				$scope.deleteVehicleCoefficents = function(row, index) {

					var entityIdToRemove = row.entity.entityId;
					if (angular.isNumber(entityIdToRemove)) {

						VehicleCoefficentsService.VehicleCoefficentsResource.deleteVehicleCoefficents({
							entityId : entityIdToRemove
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.VehicleCoefficient') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
							// $scope.initializeEMS(CultureService.localize('cop.genomedata.model.success'),
							// CultureService.localize('cop.specificCop.message.delete'));
							$scope.getAllVehicleCoefficents();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.VehicleCoefficient') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
							// $scope.initializeEMS(CultureService.localize('cop.genomedata.model.error'),
							// CultureService.localize('cop.specificCop.message.errordelete'));
						});
					} else {
						$scope.VehicleCoefficients.splice(index, 1);
					}
				};

				$scope.getAllFuel = function() {
					var Carburant = FuelService.FuelResource.getAllFuel(function() {
						$scope.Carburant = Carburant;

						$scope.CarburantSelected = [];
					}, function() {

					});
				};

				/* Save Selected fuel */
				$scope.saveFuel = function() {

					$scope.CarburantSelected = [];

					var labelflag = true;

					for (var i = 0; i < $scope.Carburant.length; i++) {

						if ($scope.Carburant[i]['label'] !== '') {
							if ($scope.Carburant[i]['edited'] === true) {

								$scope.CarburantSelected.push($scope.Carburant[i])
							}
						} else {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Fuel') + ' : ' + CultureService.localize('cop.coastdown.message.reuiredfield'));
							labelflag = false;
							break;
						}

					}
					if ($scope.CarburantSelected.length > 0 && labelflag) {

						var objectToSend = {
							'fuelRepresentationList' : $scope.CarburantSelected
						};

						FuelService.FuelResource.saveFuel(objectToSend, function(sucess) {
							if (sucess.label === null) {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.Fuel') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion'));
								// $scope.initializeEMS(CultureService.localize('cop.genomedata.model.error'),
								// CultureService.localize('cop.coastdown.message.duplicateVersion'));
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.Fuel') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
							}
							$scope.getAllFuel();

						}, function(error) {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Fuel') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
						});

					}
				};

				/* Delete Seleted Fuel Row */
				$scope.deleteFuel = function(row, index) {

					var entityIdToRemove = row.entity.entityId;

					if (angular.isNumber(entityIdToRemove)) {

						FuelService.FuelResource.deleteFuel({
							entityId : entityIdToRemove
						}, function(Sucess) {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Fuel') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
							$scope.getAllFuel();
						}, function(Error) {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Fuel') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					} else {
						$scope.Carburant.splice(index, 1);
					}

				};

				$scope.getAllCategory = function() {
					var Categorie = CategoryService.CategoryResource.getAllCategory(function() {
						$scope.Categorie = Categorie;
						$scope.CategorieSelected = [];
					}, function() {
					});
				};

				/* Save Selected Category */
				$scope.saveCategory = function() {
					$scope.CategorieSelected = [];

					var labelflag = true;

					for (var i = 0; i < $scope.Categorie.length; i++) {

						if ($scope.Categorie[i]['label'] !== '' && !angular.isUndefined($scope.Categorie[i]['clasz_label'])) {

							if ($scope.Categorie[i]['edited'] === true) {

								$scope.CategorieSelected.push($scope.Categorie[i]);
							}
						} else {
							labelflag = false;
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Category') + ' : ' + CultureService.localize('cop.coastdown.message.reuiredfield'));
							break;
						}
					}
					if ($scope.CategorieSelected.length > 0 && labelflag) {

						var objectToSend = {
							'categoryRepresentationList' : $scope.CategorieSelected
						};

						CategoryService.CategoryResource.saveCategory(objectToSend, function(sucess) {
							if (sucess.label === null) {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.Category') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion'));
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.Category') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
							}
							$scope.getAllCategory();
						}, function(error) {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Category') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
						});

					}
				};

				/* Delete Seleted Category Row */
				$scope.deleteCategory = function(row, index) {
					
					if(row.entity.entityId === undefined){
						$scope.Categorie.splice(index, 1);
						return;
					}

					var entityIdToRemove = row.entity.entityId;
					var claszToRemove = row.entity.clasz.label;
					if (angular.isNumber(entityIdToRemove)) {

						CategoryClaszService.CategoryResource.deleteCategory({
							entityId : entityIdToRemove,
							claszLabel : claszToRemove

						}, function(sucess) {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Category') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
							$scope.Categorie.splice(index, 1);

						}, function(error) {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.Category') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					} else {

						$scope.Categorie.splice(index, 1);
					}

				};

				/** ************************TireBrand************************* */

				$scope.getAllTireBrands = function() {
					var tireBrand = TireBrandService.TireBrandResource.getAllTireBrands(function() {
						$scope.tireBrand = tireBrand;
						$scope.tireBrandSelected = [];
					}, function() {

						NotificationService.notify(CultureService.localize('cop.specificCop.label.tireBrand') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
					});
				};

				/* Save Selected TireBrand Type */
				$scope.saveTireBrand = function() {
					$scope.tireBrandSelected = [];

					var labelflag = true;

					for (var i = 0; i < $scope.tireBrand.length; i++) {
						if ($scope.tireBrand[i]['label'] !== '' && $scope.tireBrand[i]['label'] !== null && $scope.tireBrand[i]['label'] !== undefined) {

							if ($scope.tireBrand[i]['edited'] === true) {

								$scope.tireBrandSelected.push($scope.tireBrand[i]);
							}
						} else {
							labelflag = false;
							NotificationService.notify(CultureService.localize('cop.specificCop.label.tireBrand') + ' : ' + CultureService.localize('cop.coastdown.message.reuiredfield'));

							break;
						}

					}

					if ($scope.tireBrandSelected.length > 0 && labelflag) {

						var objectToSend = {
							'tireBrandRepresentationsList' : $scope.tireBrandSelected
						};

						TireBrandService.TireBrandResource.saveTireBrand(objectToSend, function(success) {
							if (success.label === null) {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.tireBrand') + ' : ' + CultureService.localize('cop.coastdown.message.duplicateVersion'));
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.tireBrand') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
							}
							$scope.getAllTireBrands();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.tireBrand') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
						});

					}

				};

				/*
				 * Delete Seleted TireBrand Application Row
				 */
				$scope.deleteTireBrand = function(row, index) {

					var entityIdToRemove = row.entity.entityId;
					if (angular.isNumber(entityIdToRemove)) {

						TireBrandService.TireBrandResource.deleteTireBrand({
							entityId : entityIdToRemove
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.tireBrand') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
							$scope.getAllTireBrands();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.tireBrand') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					} else {
						$scope.tireBrand.splice(index, 1);
					}
				};

				/** ************************lambdaDecisionParameters************************* */
				$scope.fuelTypedataForLambdaDecisionParameters = [];
				$scope.higherSymbolOptionsForLambdaDecisionParameters = [];
				$scope.lowerSymbolOptionsForLambdaDecisionParameters = [];
				$scope.fuelTypedataForLambdaDecisionParameters.splice(0, $scope.fuelTypedataForLambdaDecisionParameters.length);
				$scope.symbols = [];

				(function() {

					var symbols = ParametersConstantsService.ParametersConstantsResource.getAllParametersConstants(function() {
						$scope.symbols = symbols;
					})
				})();

				/** **get getAllLambdaDecisionParameters* */
				$scope.getAllLambdaDecisionParameters = function() {
					$scope.fuelTypedataForLambdaDecisionParameters.splice(0, $scope.fuelTypedataForLambdaDecisionParameters.length);
					var lambdaDecisionParameters = LambdaDecisionParametersService.LambdaDecisionParametersResource.getAllLambdaDecisionParameters(function() {
						$scope.lambdaDecisionParameters = lambdaDecisionParameters;

						(function _getAllfuelTypesNames() {

							var fuelTypedataForLambdaDecisionParameters = FuelTypeService.FuelTypeResource.getAllFuelTypeData(function() {

								for (var i = 0; i < fuelTypedataForLambdaDecisionParameters.length; i++) {
									var temp = {

										'fuelTypeLabel' : fuelTypedataForLambdaDecisionParameters[i]['fuelTypeLable']
									};

									$scope.fuelTypedataForLambdaDecisionParameters.push(temp);
								}
								$scope.lambdaDecisionParametersGridOptions.columnDefs[0].editDropdownOptionsArray = $scope.fuelTypedataForLambdaDecisionParameters;

							}, function() {

								NotificationService.notify(CultureService.localize('cop.specificCop.label.lambdaDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
							});
						}());
						$scope.lambdaDecisionParametersSelected = [];

						(function() {

							$scope.lowerSymbolOptionsForLambdaDecisionParameters = [
									{
										"lowerLabel" : $scope.symbols[0]
									}, {
										"lowerLabel" : $scope.symbols[1]
									}
							];
							$scope.higherSymbolOptionsForLambdaDecisionParameters = [
									{
										"higherLabel" : $scope.symbols[2]
									}, {
										"higherLabel" : $scope.symbols[3]
									}
							];

							$scope.lambdaDecisionParametersGridOptions.columnDefs[1].editDropdownOptionsArray = $scope.lowerSymbolOptionsForLambdaDecisionParameters;
							$scope.lambdaDecisionParametersGridOptions.columnDefs[3].editDropdownOptionsArray = $scope.higherSymbolOptionsForLambdaDecisionParameters;

						}());

					}, function() {

						NotificationService.notify(CultureService.localize('cop.specificCop.label.lambdaDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
					});
				};

				$scope.saveLambdaDecisionParameters = function() {
					$scope.lambdaDecisionParametersSelected = [];

					var labelflag = true;

					for (var i = 0; i < $scope.lambdaDecisionParameters.length; i++) {
						if ($scope.lambdaDecisionParameters[i]['fuelTypeLabel'] !== '' && $scope.lambdaDecisionParameters[i]['lowerLimit'] !== undefined && $scope.lambdaDecisionParameters[i]['higherLimit'] !== undefined && $scope.lambdaDecisionParameters[i]['lowerSymbol'] !== '' && $scope.lambdaDecisionParameters[i]['higherSymbol'] !== ''

						) {

							if ($scope.lambdaDecisionParameters[i]['edited'] === true) {

								$scope.lambdaDecisionParametersSelected.push($scope.lambdaDecisionParameters[i]);

							}
						} else {
							labelflag = false;
							NotificationService.notify(CultureService.localize('cop.specificCop.label.lambdaDecisionParameters') + ' : ' + CultureService.localize('cop.coastdown.message.reuiredfield'));

							break;
						}

					}

					if ($scope.lambdaDecisionParametersSelected.length > 0 && labelflag) {

						var objectToSend = {
							'lambdaDecisionParametersRepresentationsList' : $scope.lambdaDecisionParametersSelected
						};

						LambdaDecisionParametersService.LambdaDecisionParametersResource.saveLambdaDecisionParameters(objectToSend, function(success) {
							if (success.fuelTypeLabel === null) {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.lambdaDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.duplicateVersion'));
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.lambdaDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
							}
							$scope.getAllLambdaDecisionParameters();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.lambdaDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
						});

					}

				};

				/*
				 * Delete Seleted lambdaDecisionParameters Application Row
				 */
				$scope.deleteLambdaDecisionParameters = function(row, index) {

					var entityIdToRemove = row.entity.entityId;
					if (angular.isNumber(entityIdToRemove)) {

						LambdaDecisionParametersService.LambdaDecisionParametersResource.deleteLambdaDecisionParameters({
							entityId : entityIdToRemove
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.lambdaDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
							$scope.getAllLambdaDecisionParameters();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.lambdaDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					} else {
						$scope.lambdaDecisionParameters.splice(index, 1);
					}
				};

				/** ************************OpacityDecisionParameters************************* */

				$scope.fuelTypedataForOpacityDecisionParameters = [];
				$scope.higherSymbolOptionsForOpacityDecisionParameters = []
				$scope.lowerSymbolOptionsForOpacityDecisionParameters = []
				$scope.fuelTypedataForOpacityDecisionParameters.splice(0, $scope.fuelTypedataForOpacityDecisionParameters.length);

				$scope.getAllOpacityDecisionParameters = function() {
					$scope.fuelTypedataForOpacityDecisionParameters.splice(0, $scope.fuelTypedataForOpacityDecisionParameters.length);

					var opacityDecisionParameters = OpacityDecisionParametersService.OpacityDecisionParametersResource.getAllOpacityDecisionParameters(function() {
						$scope.opacityDecisionParameters = opacityDecisionParameters;

						(function _getAllfuelTypesNames() {

							var fuelTypedataForOpacityDecisionParameters = FuelTypeService.FuelTypeResource.getAllFuelTypeData(function() {

								for (var i = 0; i < fuelTypedataForOpacityDecisionParameters.length; i++) {
									var temp = {

										'fuelTypeLabel' : fuelTypedataForOpacityDecisionParameters[i]['fuelTypeLable']
									};

									$scope.fuelTypedataForOpacityDecisionParameters.push(temp);
								}
								$scope.opacityDecisionParametersGridOptions.columnDefs[0].editDropdownOptionsArray = $scope.fuelTypedataForOpacityDecisionParameters;

							}, function() {

								NotificationService.notify(CultureService.localize('cop.specificCop.label.opacityDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
							});
						}());
						$scope.opacityDecisionParametersSelected = [];
						(function() {
							$scope.lowerSymbolOptionsForOpacityDecisionParameters = [
									{
										"lowerLabel" : $scope.symbols[0]
									}, {
										"lowerLabel" : $scope.symbols[1]
									}
							];
							$scope.higherSymbolOptionsForOpacityDecisionParameters = [
									{
										"higherLabel" : $scope.symbols[2]
									}, {
										"higherLabel" : $scope.symbols[3]
									}
							];
							$scope.opacityDecisionParametersGridOptions.columnDefs[1].editDropdownOptionsArray = $scope.lowerSymbolOptionsForOpacityDecisionParameters;
							$scope.opacityDecisionParametersGridOptions.columnDefs[3].editDropdownOptionsArray = $scope.higherSymbolOptionsForOpacityDecisionParameters;

						}());
					}, function() {

						NotificationService.notify(CultureService.localize('cop.specificCop.label.opacityDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
					});
				};

				$scope.saveOpacityDecisionParameters = function() {
					$scope.opacityDecisionParametersSelected = [];

					var labelflag = true;

					for (var i = 0; i < $scope.opacityDecisionParameters.length; i++) {
						if ($scope.opacityDecisionParameters[i]['fuelTypeLabel'] !== '' && $scope.opacityDecisionParameters[i]['lowerLimit'] !== undefined && $scope.opacityDecisionParameters[i]['higherLimit'] !== undefined && $scope.opacityDecisionParameters[i]['lowerSymbol'] !== '' && $scope.opacityDecisionParameters[i]['higherSymbol'] !== '') {
							if ($scope.opacityDecisionParameters[i]['edited'] === true) {
								$scope.opacityDecisionParametersSelected.push($scope.opacityDecisionParameters[i]);

							}
						} else {
							labelflag = false;
							NotificationService.notify(CultureService.localize('cop.specificCop.label.opacityDecisionParameters') + ' : ' + CultureService.localize('cop.coastdown.message.reuiredfield'));

							break;
						}

					}

					if ($scope.opacityDecisionParametersSelected.length > 0 && labelflag) {

						var objectToSend = {
							'opacityDecisionParametersRepresentationsList' : $scope.opacityDecisionParametersSelected
						};

						OpacityDecisionParametersService.OpacityDecisionParametersResource.saveOpacityDecisionParameters(objectToSend, function(success) {
							if (success.fuelTypeLabel === null) {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.opacityDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.duplicateVersion'));
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.opacityDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
							}
							$scope.getAllOpacityDecisionParameters();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.opacityDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
						});

					}

				};

				/*
				 * Delete Seleted opacityDecisionParameters Application Row
				 */
				$scope.deleteOpacityDecisionParameters = function(row, index) {

					var entityIdToRemove = row.entity.entityId;
					if (angular.isNumber(entityIdToRemove)) {

						OpacityDecisionParametersService.OpacityDecisionParametersResource.deleteOpacityDecisionParameters({
							entityId : entityIdToRemove
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.opacityDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
							$scope.getAllOpacityDecisionParameters();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.opacityDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					} else {
						$scope.opacityDecisionParameters.splice(index, 1);
					}
				};

				/** ************************CODecisionParameters************************* */
				$scope.fuelTypedataForCoDecisionParameters = [];
				$scope.higherSymbolOptionsForCoDecisionParameters = [];
				$scope.lowerSymbolOptionsForCoDecisionParameters = [];
				/* get All CODecisionParameters */
				$scope.getAllCODecisionParameters = function() {
					$scope.fuelTypedataForCoDecisionParameters.splice(0, $scope.fuelTypedataForCoDecisionParameters.length);
					var coDecisionParameters = CODecisionParametersService.CODecisionParametersResource.getAllCODecisionParameters(function() {
						$scope.coDecisionParameters = coDecisionParameters;
						(function _getAllfuelTypesNames() {

							var fuelTypedataForCoDecisionParameters = FuelTypeService.FuelTypeResource.getAllFuelTypeData(function() {

								for (var i = 0; i < fuelTypedataForCoDecisionParameters.length; i++) {
									var temp = {
										/*
										 * 'entityId' : fuelTypedataForCoDecisionParameters[i]['entityId'],
										 */
										'fuelTypeLabel' : fuelTypedataForCoDecisionParameters[i]['fuelTypeLable']
									};

									$scope.fuelTypedataForCoDecisionParameters.push(temp);
								}
								$scope.coDecisionParametersGridOptions.columnDefs[0].editDropdownOptionsArray = $scope.fuelTypedataForCoDecisionParameters;

							}, function() {

								NotificationService.notify(CultureService.localize('cop.specificCop.label.CODecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
							});
						}());
						$scope.coDecisionParametersSelected = [];

						(function() {
							$scope.lowerSymbolOptionsForCoDecisionParameters = [
									{
										"lowerLabel" : $scope.symbols[0]
									}, {
										"lowerLabel" : $scope.symbols[1]
									}
							];
							$scope.higherSymbolOptionsForCoDecisionParameters = [
									{
										"higherLabel" : $scope.symbols[2]
									}, {
										"higherLabel" : $scope.symbols[3]
									}
							];

							$scope.coDecisionParametersGridOptions.columnDefs[1].editDropdownOptionsArray = $scope.lowerSymbolOptionsForCoDecisionParameters;
							$scope.coDecisionParametersGridOptions.columnDefs[3].editDropdownOptionsArray = $scope.higherSymbolOptionsForCoDecisionParameters;

						}());
					}, function() {

						NotificationService.notify(CultureService.localize('cop.specificCop.label.CODecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
					});
				};

				/*
				 * Save Selected saveCODecisionParameters Type
				 */
				$scope.saveCODecisionParameters = function() {
					$scope.coDecisionParametersSelected = [];

					var labelflag = true;

					for (var i = 0; i < $scope.coDecisionParameters.length; i++) {
						if ($scope.coDecisionParameters[i]['fuelTypeLabel'] !== '' && $scope.coDecisionParameters[i]['lowerLimit'] !== undefined && $scope.coDecisionParameters[i]['higherLimit'] !== undefined && $scope.coDecisionParameters[i]['lowerSymbol'] !== '' && $scope.coDecisionParameters[i]['higherSymbol'] !== '') {

							if ($scope.coDecisionParameters[i]['edited'] === true) {

								$scope.coDecisionParametersSelected.push($scope.coDecisionParameters[i]);

							}
						} else {
							labelflag = false;
							NotificationService.notify(CultureService.localize('cop.specificCop.label.coDecisionParameters') + ' : ' + CultureService.localize('cop.coastdown.message.reuiredfield'));

							break;
						}

					}

					if ($scope.coDecisionParametersSelected.length > 0 && labelflag) {

						var objectToSend = {
							'coDecisionParametersRepresentationsList' : $scope.coDecisionParametersSelected
						};

						CODecisionParametersService.CODecisionParametersResource.saveCODecisionParameters(objectToSend, function(success) {
							if (success.fuelTypeLabel === null) {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.coDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.duplicateVersion'));
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.coDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
							}
							$scope.getAllCODecisionParameters();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.coDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
						});

					}

				};

				/*
				 * Delete Seleted CODecisionParameters Application Row
				 */
				$scope.deleteCODecisionParameters = function(row, index) {

					var entityIdToRemove = row.entity.entityId;
					if (angular.isNumber(entityIdToRemove)) {

						CODecisionParametersService.CODecisionParametersResource.deleteCODecisionParameters({
							entityId : entityIdToRemove
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.coDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
							$scope.getAllCODecisionParameters();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.coDecisionParameters') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
						});
					} else {
						$scope.coDecisionParameters.splice(index, 1);
					}
				};

				/** ************************TeamUsers************************* */

				$scope.getAllTeamUsers = function() {
					var teamUsers = TeamUsersService.TeamUsersResource.getAllTeamUsers(function() {
						$scope.teamUsers = teamUsers;
					}, function() {

						NotificationService.notify(CultureService.localize('cop.specificCop.label.teamUsers') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
					});
				};

				/* Save Selected TeamUsers Type */
				$scope.saveTeamUsers = function() {
					$scope.teamUsersSelected = [];

					var labelflag = true;

					for (var i = 0; i < $scope.teamUsers.length; i++) {

						if ($scope.teamUsers[i]['userId'] !== '' && $scope.teamUsers[i]['userId'] !== undefined) {

							if ($scope.teamUsers[i]['edited'] === true) {
								$scope.teamUsersSelected.push($scope.teamUsers[i]);
							}
						} else {
							labelflag = false;

							NotificationService.notify(CultureService.localize('cop.specificCop.label.teamUsers') + ' : ' + CultureService.localize('cop.coastdown.message.reuiredfield'));

							break;
						}

					}

					if ($scope.teamUsersSelected.length > 0 && labelflag) {

						var objectToSend = {
							'teamUserRepresentationsList' : $scope.teamUsersSelected
						};

						TeamUsersService.TeamUsersResource.saveTeamUsers(objectToSend, function(success) {

							if (success.userId === null) {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.teamUsers') + ' : ' + CultureService.localize('cop.specificCop.message.duplicateVersion'));
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.label.teamUsers') + ' : ' + CultureService.localize('cop.specificCop.message.successMessage'));
							}
							$scope.getAllTeamUsers();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.teamUsers') + ' : ' + CultureService.localize('cop.specificCop.message.error'));
						});

					}

				};

				/*
				 * Delete Seleted TeamUsers Application Row
				 */
				$scope.deleteTeamUsers = function(row, index) {

					var entityIdToRemove = row.entity.userEntityId;
					if (angular.isNumber(entityIdToRemove)) {

						TeamUsersService.TeamUsersResource.deleteTeamUsers({
							entityId : entityIdToRemove
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.teamUsers') + ' : ' + CultureService.localize('cop.specificCop.message.delete'));
							$scope.getAllTeamUsers();
						}, function() {
							NotificationService.notify(CultureService.localize('cop.specificCop.label.teamUsers') + ' : ' + CultureService.localize('cop.specificCop.message.errordelete'));
					
						});
					} else {
						$scope.teamUsers.splice(index, 1);
					}
				};

				/**
				 * Function for adding row
				 */
				$scope.addRow = function(value) {
					if (value === 'Pays')
						$scope.Pays.push({
							countryLable : ''
						});
					if (value === 'Usine')
						$scope.Usine.push({
							carFactoryLabel : ''
						});

					if (value === 'Carburant')
						$scope.Carburant.push({
							label : ''
						});

					if (value === 'classe')
						$scope.Classe.push({
							label : ''
						});

					if (value === 'Category')
						$scope.Categorie.push({
							label : ''
						});

					if (value === 'FuelInjection')
						$scope.FuelInjectionType.push({
							label : ''
						});

					if (value === 'Pollutant')
						$scope.Polluants.push({
							label : ''
						});

					if (value === 'VehicleTechnology')
						$scope.VehicleTechnology.push({
							label : ''
						});

					if (value === 'TypeApprovalArea')
						$scope.ApprovalAreaType.push({
							label : ''
						});

					if (value === 'factor')
						$scope.FactorApplication.push({
							label : ''
						});

					if (value === 'VehicleCoeff')
						$scope.VehicleCoefficients.push({
							label : ''
						});

					if (value === 'tireBrand')
						$scope.tireBrand.push({
							label : ''
						});

					if (value === 'lambdaDecisionParameters')
						$scope.lambdaDecisionParameters.push({
							fuelTypeLabel : ''
						});

					if (value === 'opacityDecisionParameters')
						$scope.opacityDecisionParameters.push({
							fuelTypeLabel : ''
						});

					if (value === 'coDecisionParameters')
						$scope.coDecisionParameters.push({
							fuelTypeLabel : ''
						});

					if (value === 'teamUsers')
						$scope.teamUsers.push({

						});

				};

				// ON CHECK BOX CLICK EVEN
				$scope.onCheckBoxClick = function(row) {

					$scope.gridApi.selection.toggleRowSelection(row.entity);

				};
				// set radio button value
				$scope.setSel = function(row) {
					row.entity.edited = true;
					if (row.entity.defaultFuel === true) {
						row.entity.defaultFuel = false;

					} else {
						row.entity.defaultFuel = true;

					}
				};
				$scope.isChecked = function(id, row) {

					return id;

				};

				$scope.SaveAll = function() {

					$scope.saveFuel();

					$scope.saveClasz();

					$scope.saveCategory();

					$scope.saveFuelInjectionType();

					$scope.saveVehicleTechnology();

					$scope.saveTypeApprovalArea();

					$scope.savePollutant();

					$scope.saveFactorCoeffAppType();

					$scope.saveVehicleCoefficents();

					$scope.saveSelectedUsineData();

					$scope.saveSelectedPaysData();

					$scope.changesSaved(true);
					$scope.saveTireBrand();
					$scope.saveTeamUsers();
					$scope.saveCODecisionParameters();
					$scope.saveLambdaDecisionParameters();
					$scope.saveOpacityDecisionParameters();

				};

				/**
				 * ***********************************************Coast Down**********************************************************************************************************
				 */
				$scope.coastdownData = [];
				$scope.coastdownDatatoSave = [];
				$scope.coastdownGridOptions = {
					enableColumnResizing : true,
					enableFiltering : true,
					enableSorting : true,
					enableCellEditOnFocus : true,
					enableCellEdit : authentication,
					columnDefs : coastdownUIGridService.defination(),
					data : 'coastdownData',
					onRegisterApi : function(gridApi) {
					    $scope.gridApi = gridApi;
						gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

							$scope.Computed(rowentity, colDef);
							if (newValue !== oldValue) {
								$scope.isValidated = false;
							}
							$scope.activeTab = "costDown";

						});
						
						$scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });

					}
				};

				$scope.myFunct = function(keyEvent) {
					if (keyEvent.which === 13)
						$scope.saveCoastdowndata();
				};

				$scope.addRowCoastdown = function() {

					$scope.coastdownData.splice(0, 0, {
						'entityId' : 0,
						'version' : 'V1.0',
						'latestversion' : true
					});
				};

				$scope.updateRowCoatdown = function(row, rowindex) {

					$scope.coastdownData[rowindex]['latestversion'] = false;
					var oldvarsion = row.entity.version;

					var versionNum = oldvarsion.slice(1, oldvarsion.length - 2);
					var newversion = 'V' + (parseInt(versionNum) + 1) + '.0';
					var toUpdate = {
						'entityId' : 0,
						'pSAReference' : row.entity.pSAReference,
						'roadLaw' : row.entity.roadLaw,
						'inertia_value' : row.entity.inertia_value,
						'theoricalBenchF0' : row.entity.theoricalBenchF0,
						'theoricalBenchF1' : row.entity.theoricalBenchF1,
						'theoricalBenchF2' : row.entity.theoricalBenchF2,
						'computedBenchF0' : row.entity.computedBenchF0,
						'computedBenchF1' : row.entity.computedBenchF1,
						'computedBenchF2' : row.entity.computedBenchF2,
						'version' : newversion,
						'latestversion' : true
					};
					$scope.coastdownData.splice(rowindex + 1, 0, toUpdate);

				};

				$scope.Computed = function(rowentity, colDef) {

					rowentity.edited = true;

					if ($scope.VehicleCoefficients.length > 0) {

						var f0Coeffiecient = $scope.VehicleCoefficients[0]['f0Coeffiecient'];
						var f1Coeffiecient = $scope.VehicleCoefficients[0]['f1Coeffiecient'];
						var f2Coeffiecient = $scope.VehicleCoefficients[0]['f2Coeffiecient'];

						if ('Theoricalf0' === colDef.name) {
							if (f0Coeffiecient !== 0) {
								rowentity.computedBenchF0 = rowentity.theoricalBenchF0 / f0Coeffiecient;
							}
						}
						if ('Theoricalf1' === colDef.name) {
							if (f1Coeffiecient !== 0) {
								rowentity.computedBenchF1 = rowentity.theoricalBenchF1 / f1Coeffiecient;
							}
						}
						if ('Theoricalf2' === colDef.name) {
							if (f2Coeffiecient !== 0) {
								rowentity.computedBenchF2 = rowentity.theoricalBenchF2 / f2Coeffiecient;
							}
						}
					}
				};

				$scope.getAllCoastDown = function() {
					var coastdown = CoastdownService.CoastdownResource.getAllCoastdown(function() {
						$scope.coastdownData = coastdown;
						$scope.coastdownDatatoSave = [];
					}, function() {
					});
				};
				// $scope.testvariable = ;
				$scope.saveCoastdowndata = function() {
					$scope.coastdownDatatoSave = [];
					var valid = true;
					for (var count = 0; count < $scope.coastdownData.length; count++) {

						if ($scope.coastdownData[count]['edited'] === true) {

							if (angular.isString($scope.coastdownData[count]['pSAReference']) && angular.isString($scope.coastdownData[count]['roadLaw']) && angular.isNumber($scope.coastdownData[count]['inertia_value']) && angular.isNumber($scope.coastdownData[count]['theoricalBenchF0']) && angular.isNumber($scope.coastdownData[count]['theoricalBenchF1']) && angular.isNumber($scope.coastdownData[count]['theoricalBenchF2'])) {
								$scope.coastdownDatatoSave.push($scope.coastdownData[count]);
							} else {

								valid = false;
								NotificationService.notify(CultureService.localize('cop.coastdown.message.reuiredfield'));
								break;
							}
						}
					}

					if (valid) {

						var objectToSend = {
							'costdownRepresentationList' : $scope.coastdownDatatoSave
						};

						CoastdownService.CoastdownResource.saveAllCoastdown(objectToSend, function(responce) {

							if (responce.roadLaw === null) {
								NotificationService.notify(CultureService.localize('cop.coastdown.message.duplicateVersion'));
							} else {
								NotificationService.notify(CultureService.localize('cop.specificCop.message.successMessage'));

							}
							$scope.getAllCoastDown();

						}, function(error) {
							NotificationService.notify(CultureService.localize('cop.specificCop.message.error'));
						});
					}
					$scope.changesSaved(true);

				};

				$scope.refresh = function() {
					location.reload();
				};

				$scope.changesSaved = function(isValidated) {
					$scope.isValidated = isValidated;
					$scope.genomeDataChanged = $scope.isGenomeDataChanged();
					if (authentication && (!isValidated || !$scope.genomeDataChanged))
						$('#modificationModal').modal('show');
				};
				$scope.confirmModification = function() {

					$scope.isValidated = true;
					$scope.cancelledChanges = false;
					$scope.genomeDataChanged = true;
					$scope.changedGenomeData();

				};
				$scope.removeModifications = function() {
					$scope.changesSaved(true);
					$scope.isValidated = false;
					$scope.cancelledChanges = true;
					$scope.changedGenomeData();
					if ($scope.activeTab === "commonGenome") {
						$scope.commonGenometab();

					} else if ($scope.activeTab === "specificCop") {
						$scope.specificCoptab();
					} else if ($scope.activeTab === "costDown") {
						$scope.coastDowntab();
					}
				}

				$scope.isGenomeDataChanged = function() {
					if (!$scope.isMotureValidate || !$scope.iscarburentValidate || !$scope.isFamilyValidate || !$scope.isSilhouteValidate || !$scope.isConstructValidate || !$scope.isReglementValidate || !$scope.isBolteValidate || !$scope.isReductionValidate)

						return false;

					else
						return true;

				}
				$scope.changedGenomeData = function() {
					$scope.isMotureValidate = true;
					$scope.iscarburentValidate = true;
					$scope.isFamilyValidate = true;

					$scope.isSilhouteValidate = true;
					$scope.isConstructValidate = true;
					$scope.isUsinValidate = true;
					$scope.isPayValidate = true;
					$scope.isReglementValidate = true;
					$scope.isBolteValidate = true;
					$scope.isReductionValidate = true;
				};
				$scope.initializeEMS = function(msgHeader, msgBody) {
					$scope.msgHeader = msgHeader;
					$scope.msgBody = msgBody;
					$('#modelmsgbox').modal('show');
				};

				$scope.slideToggle = function() {

					$("#wrapper").toggleClass("toggled");
					$(".slide-resize").toggleClass("col-lg-10");
					$("#menu-toggle").toggleClass('fa-chevron-right');
				};
				$("#gbr").css('background-color', '#DBDBDB', 'color', '#fff');

				$scope.specificCoptab = function() {
					$scope.changesSaved($scope.isValidated);
					$('.specificCop').addClass('active in');
					$('.specficCopTitle').addClass('active in');
					$('#scop').addClass('active in');
					$('#cdown').removeClass('active in');
					$('#cgenome').removeClass('active in');
					$('.commonGenome').removeClass('active in');
					$('.commonGenomeTitle').removeClass('active in');
					$('.coastDown').removeClass('active in');
					$('.coastDownTitle').removeClass('active in');

					/**
					 * *** Loading Data for Specific Cop ***
					 */
					if ($scope.cancelledChanges)
						return;
					$scope.getAllFuel();
					$scope.getAllClasz();
					$scope.getAllCategory();
					$scope.getAllFuelInjectionType();
					$scope.getAllVehicleTechnology();
					$scope.getAllTypeApprovalArea();
					$scope.getAllPollutantData();
					$scope.getAllFactorCoeffApplicationType();
					$scope.getAllVehicleCoefficents();
					$scope.getAllUsineData();
					$scope.getAllPaysData();
					$scope.getAllTireBrands();
					$scope.getAllLambdaDecisionParameters();
					$scope.getAllOpacityDecisionParameters();
					$scope.getAllCODecisionParameters();
					$scope.getAllTeamUsers();

				};
				$scope.specificCoptab();

				$scope.commonGenometab = function() {
					// $scope.activeTab =
					// "commonGenome";
					$scope.changesSaved($scope.isValidated);
					$('.commonGenome').addClass('active in');
					$('.commonGenomeTitle').addClass('active in');
					$('#cgenome').addClass('active in');
					$('#cdown').removeClass('active in');
					$('#scop').removeClass('active in');
					$('.specificCop').removeClass('active in');
					$('.specficCopTitle').removeClass('active in');
					$('.coastDown').removeClass('active in');
					$('.coastDownTitle').removeClass('active in');

					/** *** Loading Data for GENOME *** */
					if ($scope.cancelledChanges)
						return;
					$scope.getAllMoteurData();
					$scope.getAllCarburantData();
					$scope.getAllFamilleData();
					$scope.getAllSilhouetteData();
					$scope.getAllConstructeurData();

					$scope.getAllReglementationData();
					$scope.getAllBolteData();
					$scope.getAllFinalReductionData();

				};

				$scope.coastDowntab = function() {
					// $scope.activeTab = "coastDown";
					$scope.changesSaved($scope.isValidated);
					$('.coastDown').addClass('active in');
					$('.coastDownTitle').addClass('active in');
					$('#cdown').addClass('active in');
					$('#cgenome').removeClass('active in');
					$('#scop').removeClass('active in');
					$('.commonGenome').removeClass('active in');
					$('.commonGenomeTitle').removeClass('active in');
					$('.specificCop').removeClass('active in');
					$('.specficCopTitle').removeClass('active in');

					/**
					 * *** Loading Data for CoastDown ***
					 */
					if ($scope.cancelledChanges)
						return;
					$scope.getAllCoastDown();

				};

				/*------------------------------------------History-------------------------------------------------*/

				$scope.HistoryGridOptions = {
					enableColumnResizing : true,
					enableFiltering : true,
					enableSorting : true,
					enableHorizontalScrollbar : false,
					columnDefs : [

							{
								name : 'date',
								displayName : CultureService.localize('cop.history.date'),
								type : 'date',
								field : 'updationDate',
								cellFilter : 'date:\'dd/MM/yyyy HH:mm:ss\'',
								cellTooltip : function(row) {
									return row.entity.updationDate;
								},
								enableCellEdit : false
							}, {
								name : 'userId',
								displayName : CultureService.localize('cop.history.userId'),
								field : 'userId',
								cellTooltip : function(row) {
									return row.entity.userId;
								},
								enableCellEdit : false
							}, {
								name : 'userProfile',
								displayName : CultureService.localize('cop.history.userProfile'),
								field : 'userProfile',
								cellTooltip : function(row) {
									return row.entity.userProfile;
								},
								enableCellEdit : false
							}, {
								name : 'description',
								displayName : CultureService.localize('cop.history.description'),
								field : 'description',
								cellTooltip : function(row) {
									return row.entity.description;
								},
								enableCellEdit : false
							}, {
								name : 'oldValue',
								displayName : CultureService.localize('cop.history.oldValue'),
								field : 'oldValue',
								cellTooltip : function(row) {
									return row.entity.oldValue;
								},
								enableCellEdit : false
							}, {
								name : 'newValue',
								displayName : CultureService.localize('cop.history.newValue'),
								field : 'newValue',
								cellTooltip : function(row) {
									return row.entity.newValue;
								},
								enableCellEdit : false
							},

					],
					data : 'allHistory',

				};

				/* to get History Data */
				$scope.allHistory = [];

				$scope.getAllHistory = function(screenId) {
					var historyResponse = HistoryService.HistroyResource.historyData({
						screenId : screenId
					}, function() {
						$scope.allHistory = historyResponse;

					}, function() {

					});
				};

				$scope.showHistoryData = function(screenId) {
					$scope.getAllHistory(screenId);
					$('#showHistoryBox').modal('show');
				};

			}

	]);

	return {
		angularModules : [
			'GlobalBusinessReference'
		]
	};
});