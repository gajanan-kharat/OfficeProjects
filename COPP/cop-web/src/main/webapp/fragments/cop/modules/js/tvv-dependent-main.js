define([
		'require', 'module', '{angular}/angular', '{w20-core}/modules/ui'
], function(require, module, angular) {
	'use strict';
	var TvvStructure = angular.module('TvvStructure', [
			'ui.bootstrap', 'copCommon'
	]), config = module && module.config() || {};

	TvvStructure.factory('TVVDepTDLService', [
			'$resource',

			function($resource) {
				return {
					TvvDepTDLResource : $resource('tvvDepTDL/:path/:entityId/:label', {
						path : '@path',
						entityId : '@entityId',
						label : '@label'
					}, {

						'getAllLists' : {
							method : 'GET',
							params : {
								path : 'TvvDependentTDLists'
							},
							isArray : true
						},

						'saveAllChanges' : {
							method : 'POST',
							params : {
								path : 'TvvDependentTDLists'

							},
							isArray : true
						},
						'checkTvvDepTDL' : {
							method : 'POST',
							params : {
								path : 'TvvDepTDLLabel'

							},
							isArray : true
						},
						'checkTvvDepTDLData' : {
							method : 'POST',
							params : {
								path : 'TvvDepTDLData'

							},
							isArray : true
						},

					})
				};
			}
	]);

	TvvStructure.factory('TVVDepTCLService', [
			'$resource',

			function($resource) {
				return {
					TvvDepTCLResource : $resource('tvvDepTCL/:path/:entityId/:label', {
						path : '@path',
						entityId : '@entityId',
						label : '@label'
					}, {

						'getAllLists' : {
							method : 'GET',
							params : {
								path : 'TvvDependentTCLists'
							},
							isArray : true
						},

						'saveAllChanges' : {
							method : 'POST',
							params : {
								path : 'TvvDependentTCLists'

							},
							isArray : true
						},
						'checkTvvDepTCL' : {
							method : 'POST',
							params : {
								path : 'TvvDepTCLLabel'

							},
							isArray : true
						},

						'checkTvvDepTCLData' : {
							method : 'POST',
							params : {
								path : 'TvvDepTCLData'

							},
							isArray : true
						},

					})
				};
			}
	]);

	/**
	 * Controller for TVV dependent Technical Data List
	 */
	TvvStructure.controller('TvvStructureController', [
			'$scope', '$modal', 'TVVDepTCLService', 'TVVDepTDLService', '$window', 'CultureService', 'DataTypeService', 'NotificationService', 'HistoryService', 'AuthorizationService', function($scope, $modal, TVVDepTCLService, TVVDepTDLService, $window, cultureService, DataTypeService, NotificationService, HistoryService, authorizationService) {

				/*----------------------------------------Authorization----------------------------------------*/
				$scope.authorization = function(role) {
					return authorizationService.hasRole('seed-w20', role);
				};
				/*------------------------------------------------------------------------------------------------*/

				// $scope.technicalDatalists = [];
				$scope.tempList = [];
				$scope.getDataTypes = function() {
					DataTypeService.dataTypeResource.getAllDataTypes(function(success) {
						$scope.dataTypes = success;
					}, function() {
					})
				};
				$scope.getDataTypes();
				$scope.TvvStructure = true;
				$scope.showTechnicalData = function() {
					$scope.TvvStructure = true;
					$scope.testConditionActive = false;
					$scope.initializeTDLists();

				};

				$scope.showTestConditions = function() {
					$scope.TvvStructure = false;
					$scope.testConditionActive = true;

				};

				/**
				 * THis method fetches all TvvDepTDL data initially
				 */
				$scope.initializeTDLists = function() {

					TVVDepTDLService.TvvDepTDLResource.getAllLists(function(response) {

						$scope.technicalDatalists = response;
						$scope.technicalDatalistsCopy = angular.copy($scope.technicalDatalists);
						if ($scope.technicalDatalists != null && $scope.technicalDatalists.size != 0)
							$scope.listsAvail = true;
						else
							$scope.listsAvail = false

					}, function(error) {
						NotificationService.notify(cultureService.localize('cop.testCondition.message.fetchError'));
					});
				};
				$scope.initializeTDLists();

				// this method displays a modal while adding list
				$scope.showAddListModal = function() {

					$('#addListModal').modal('show');

				};
				// This method adds list to display (it is not actually saved)
				$scope.addList = function() {

					if ($scope.Listlabel !== undefined && $scope.Listlabel !== null && $scope.Listlabel.length > 0) {
						var labelFlag = true;
						for (var i = 0; i < $scope.technicalDatalists.length; i++) {
							if (($scope.technicalDatalists[i]['label']).toUpperCase() === $scope.Listlabel.toUpperCase()) {
								labelFlag = false;
								break;
							}
						}
						if (labelFlag) {
							TVVDepTDLService.TvvDepTDLResource.checkTvvDepTDL({
								entityId : $scope.Listlabel
							}, function(responce) {
								$scope.technicalDatalists.push({
									'label' : $scope.Listlabel,
									'description' : $scope.ListDescription,
									modifiedFlag : "INSERT",
									genericTechnicalData : []
								});
								$scope.Listlabel = "";
								$scope.ListDescription = "";

							}, function(error) {
								NotificationService.notify(cultureService.localize('cop.coastdown.message.duplicateVersion'));
							}

							);
						} else {
							NotificationService.notify(cultureService.localize('cop.coastdown.message.duplicateVersion'));
						}
					} else
						NotificationService.notify(cultureService.localize('cop.common.message.mandatoryFields'));

					$scope.listsAvail = true;
				};
				// This method displays modal while adding generic technical data item to list
				$scope.showAddModal = function(list) {

					$scope.selectedList = list;
					$('#addDatalModal').modal('show');

				};

				$scope.showDeleteListModal = function(list, label, index) {
					$scope.indexToBeDeleted = index;
					$scope.selectedList = list;
					$scope.deleteListLabel = label;
					$('#ConfirmDeleteModal').modal('show');

				};
				// This method removes list from display
				$scope.removeList = function() {
					$scope.technicalDatalists[$scope.indexToBeDeleted]["modifiedFlag"] = "DELETE";
					$scope.tempList.push($scope.technicalDatalists[$scope.indexToBeDeleted]);
					$scope.technicalDatalists.splice($scope.indexToBeDeleted, 1);

				};
				// //This method adds generic technical data to list for display (it is not actually saved)
				$scope.addDataItem = function() {

					if ($scope.itemlabel !== undefined && $scope.itemlabel !== null && $scope.itemlabel.length > 0) {

						var labelflag = true;
						for (var i = 0; i < $scope.selectedList.genericTechnicalData.length; i++) {
							if (($scope.selectedList.genericTechnicalData[i]['label']).toUpperCase() === $scope.itemlabel.toUpperCase()) {

								labelflag = false;
								break;
							}
						}
						if (labelflag) {

							var labelCheckInTable = true;
							if (!angular.isUndefined($scope.selectedList.entityId)) {

								TVVDepTDLService.TvvDepTDLResource.checkTvvDepTDLData({
									entityId : $scope.selectedList.entityId,
									label : $scope.itemlabel
								}, function(responce) {
									labelCheckInTable = true;
								}, function(error) {
									labelCheckInTable = false;

								});
							}
							if (labelCheckInTable) {
								var technicalData = {
									'label' : $scope.itemlabel,
									'description' : $scope.itemDescription,
									'unit' : {}
								};
								$scope.itemlabel = "";
								$scope.itemDescription = "";

								$scope.selectedList.genericTechnicalData.push(technicalData);
								if ($scope.selectedList.entityId != null && $scope.selectedList.entityId != 0)
									$scope.selectedList.modifiedFlag = "UPDATE";
							} else {
								NotificationService.notify(cultureService.localize('cop.coastdown.message.duplicateVersion'));
							}
						} else {
							NotificationService.notify(cultureService.localize('cop.coastdown.message.duplicateVersion'));
						}
					} else
						NotificationService.notify(cultureService.localize('cop.common.message.mandatoryFields'));

				};
				$scope.showDeleteItemModal = function(list, label, index) {
					$scope.selectedList = list;
					$scope.selectedDataItemIndex = index;
					$scope.deleteLabel = label;
					$('#deleteDatalModal').modal('show');
				};
				//
				$scope.removeDataItem = function() {
					$scope.selectedList.modifiedFlag = "UPDATE";
					$scope.selectedList.genericTechnicalData[$scope.selectedDataItemIndex]["isDeleted"] = "TRUE";

					var ele = document.getElementById($scope.selectedList.genericTechnicalData[$scope.selectedDataItemIndex]["label"] + '-' + $scope.selectedDataItemIndex);
					if (ele !== undefined && ele !== null) {
						ele.setAttribute('class', 'label label-danger');
					}

				};
				$scope.editItemLabel = function(list, dataItem) {

				};
				$scope.getClass = function(dataItem) {

					if (dataItem.isDeleted == "TRUE")
						return "label label-default";
					return "label label-danger";
					if (!$scope.$$phase) {
						$scope.$apply();
					}
				};
				//
				$scope.setModifiedFlag = function(list) {
					$scope.selectedList = list;
					if ($scope.selectedList.entityId != null && $scope.selectedList.entityId != 0)
						$scope.selectedList.modifiedFlag = "UPDATE";
					else
						$scope.selectedList.modifiedFlag = "INSERT";
				};
				//
				$scope.showSaveConfirmation = function() {
					$('#ConfirmSaveModal').modal('show');
				};

				// Changes on TD Cancel Button
				$scope.CancelOnConfirmationTDL = function() {
					$scope.dataNotChanged = angular.equals($scope.technicalDatalists, $scope.technicalDatalistsCopy);
					if ($scope.dataNotChanged == false) {
						$('#ConfirmCancelTDModal').modal('show');
					}

				};
				$scope.cancelModifiedTDChanges = function() {
					$scope.technicalDatalists = angular.copy($scope.technicalDatalistsCopy);
				};

				// End of Changes on Cancel Button
				/**
				 * This method saves all modifications (INSERT|UPDATE|DELETE)
				 */
				$scope.saveChanges = function() {
					for (var i = 0; i < $scope.tempList.length; i++)
						$scope.technicalDatalists.push($scope.tempList[i]);
					$scope.tempList = [];
					var objectToSend = {
						'tvvDepTDLRepresentationList' : $scope.technicalDatalists
					};
					TVVDepTDLService.TvvDepTDLResource.saveAllChanges(objectToSend, function() {
						$scope.initializeTDLists();
						NotificationService.notify(cultureService.localize('cop.testCondition.message.successMessage'));
					}, function(error) {
						if (error.status === 304) {
							$scope.initializeTDLists();
							NotificationService.notify(cultureService.localize('cop.testCondition.message.deleteError'));
						} else
							NotificationService.notify(cultureService.localize('cop.testCondition.message.errorMessage'));
					});
				};

				//

				$scope.testConditionlists = [];
				$scope.tempTCList = [];

				/**
				 * THis method fetches all TvvDepTCL data initially
				 */
				$scope.initializeTCLists = function() {
					TVVDepTCLService.TvvDepTCLResource.getAllLists(function(response) {

						$scope.testConditionlists = response;
						$scope.testConditionlistsCopy = angular.copy($scope.testConditionlists);
						if ($scope.testConditionlists != null && $scope.testConditionlists.size != 0)
							$scope.listsTCAvail = true;
						else
							$scope.listsTCAvail = false;

					}, function() {

						NotificationService.notify(cultureService.localize('cop.testCondition.message.fetchError'));
					});
				};
				$scope.initializeTCLists();

				// this method displays a modal while adding list
				$scope.showAddTCListModal = function() {

					$('#addTCListModal').modal('show');

				};
				// This method adds list to display (it is not actually saved)
				$scope.addTCList = function() {
					if ($scope.TCListlabel !== undefined && $scope.TCListlabel !== null && $scope.TCListlabel.length > 0) {

						var labelFlag = true;
						for (var i = 0; i < $scope.testConditionlists.length; i++) {
							if (($scope.testConditionlists[i]['label']).toUpperCase() === $scope.TCListlabel.toUpperCase()) {
								labelFlag = false;
								break;
							}
						}
						if (labelFlag) {
							TVVDepTCLService.TvvDepTCLResource.checkTvvDepTCL({
								entityId : $scope.TCListlabel
							}, function() {
								$scope.testConditionlists.push({
									'label' : $scope.TCListlabel,
									'description' : $scope.TCListDescription,
									modifiedFlag : "INSERT",
									genericTestCondition : []
								});
								$scope.TCListlabel = "";
								$scope.TCListDescription = "";
							}, function() {
								NotificationService.notify(cultureService.localize('cop.coastdown.message.duplicateVersion'));
							}

							);

						} else {
							NotificationService.notify(cultureService.localize('cop.coastdown.message.duplicateVersion'));
						}

					} else
						NotificationService.notify(cultureService.localize('cop.common.message.mandatoryFields'));

					$scope.listsTCAvail = true;
				};
				// This method displays modal while adding generic technical data item to list
				$scope.showTCListAddModal = function(list) {

					$scope.selectedTCList = list;
					$('#addConditionlModal').modal('show');

				};

				$scope.showDeleteTCListModal = function(list, label, index) {
					$scope.indexTCToBeDeleted = index;
					$scope.selectedTCList = list;
					$scope.deleteTCListLabel = label;
					$('#ConfirmDeleteTCModal').modal('show');

				};
				// This method removes list from display
				$scope.removeTCList = function() {
					$scope.testConditionlists[$scope.indexTCToBeDeleted]["modifiedFlag"] = "DELETE";
					$scope.tempTCList.push($scope.testConditionlists[$scope.indexTCToBeDeleted]);
					$scope.testConditionlists.splice($scope.indexTCToBeDeleted, 1);

				};
				// //This method adds generic technical data to list for display (it is not actually saved)
				$scope.addCondition = function() {

					if ($scope.itemlabel !== undefined && $scope.itemlabel !== null && $scope.itemlabel.length > 0) {
						var labelFlag = true;
						for (var i = 0; i < $scope.selectedTCList.genericTestCondition.length; i++) {
							if (($scope.selectedTCList.genericTestCondition[i]['label']).toUpperCase() === $scope.itemlabel.toUpperCase()) {
								labelFlag = false;
								break;
							}
						}

						if (labelFlag) {
							var labelCheckInTable = true;

							TVVDepTCLService.TvvDepTCLResource.checkTvvDepTCLData({
								entityId : $scope.selectedTCList.entityId,
								label : $scope.itemlabel
							}, function() {
								labelCheckInTable = true;
							}, function() {
								labelCheckInTable = false;
							})
							if (labelCheckInTable) {
								var testCondition = {
									'label' : $scope.itemlabel,
									'description' : $scope.itemDescription,
									'unit' : {}
								};
								$scope.itemlabel = "";
								$scope.itemDescription = "";

								if ($scope.selectedTCList.genericTestCondition == null)
									$scope.selectedTCList.genericTestCondition = [];
								$scope.selectedTCList.genericTestCondition.push(testCondition);
								if ($scope.selectedTCList.entityId != null && $scope.selectedTCList.entityId != 0)
									$scope.selectedTCList.modifiedFlag = "UPDATE";

							} else {
								NotificationService.notify(cultureService.localize('cop.coastdown.message.duplicateVersion'));
							}
						} else {
							NotificationService.notify(cultureService.localize('cop.coastdown.message.duplicateVersion'));
						}
					} else
						NotificationService.notify(cultureService.localize('cop.common.message.mandatoryFields'));

				};

				$scope.showDeleteConditionModal = function(list, label, index) {
					$scope.selectedTCList = list;
					$scope.selectedDataItemIndex = index;
					$scope.deleteLabel = label;

					$('#deleteConditionModal').modal('show');
				};
				//
				$scope.removeCondition = function() {

					$scope.selectedTCList.modifiedFlag = "UPDATE";
					$scope.selectedTCList.genericTestCondition[$scope.selectedDataItemIndex]["isDeleted"] = "TRUE";

					var ele = document.getElementById($scope.selectedTCList.genericTestCondition[$scope.selectedDataItemIndex]["label"] + '-' + $scope.selectedDataItemIndex);
					if (ele !== undefined && ele !== null) {
						ele.setAttribute('class', 'label label-danger');
					}

				};
				$scope.editItemLabel = function(list, dataItem) {

				};
				$scope.getClass = function(dataItem) {

					if (dataItem.isDeleted == "TRUE")
						return "label label-default";
					return "label label-danger";
					if (!$scope.$$phase) {
						$scope.$apply();
					}
				};
				//
				$scope.setModifiedTCFlag = function(list) {
					$scope.selectedTCList = list;
					if ($scope.selectedTCList.entityId != null && $scope.selectedTCList.entityId != 0)
						$scope.selectedTCList.modifiedFlag = "UPDATE";
					else
						$scope.selectedTCList.modifiedFlag = "INSERT";
				};
				//
				$scope.showSaveTCLConfirmation = function() {
					$('#ConfirmSaveTCModal').modal('show');
				};

				// Changes on TC Cancel Button
				$scope.CancelOnConfirmationTCL = function() {
					$scope.dataNotChanged = angular.equals($scope.testConditionlists, $scope.testConditionlistsCopy);
					if ($scope.dataNotChanged == false) {
						$('#ConfirmCancelTCModal').modal('show');
					}

				};
				$scope.cancelModifiedTCChanges = function() {
					$scope.testConditionlists = angular.copy($scope.testConditionlistsCopy);
				};

				// End of Changes on Cancel Button
				/**
				 * This method saves all modifications (INSERT|UPDATE|DELETE)
				 */
				$scope.saveTCLChanges = function() {
					for (var i = 0; i < $scope.tempTCList.length; i++)
						$scope.testConditionlists.push($scope.tempTCList[i]);
					$scope.tempTCList = [];
					var objectToSend = {
						'tvvDepTCLRepresentationList' : $scope.testConditionlists
					};
					TVVDepTCLService.TvvDepTCLResource.saveAllChanges(objectToSend, function() {
						$scope.initializeTCLists();
						NotificationService.notify(cultureService.localize('cop.testCondition.message.successMessage'));
					}, function(error) {
						if (error.status === 304) {
							$scope.initializeTDLists();
							NotificationService.notify(cultureService.localize('cop.testCondition.message.deleteError'));
						} else
							NotificationService.notify(cultureService.localize('cop.testCondition.message.errorMessage'));

					});
				};
				/**
				 * This method shows pop-up on cancel button
				 */
				$scope.cancelCreation = function(label, description, modalName) {
					$scope.label = label;
					$scope.modaName = modalName;
					if (label != undefined && label != null && label.trim() != '' || description != undefined && description != null && description.trim() != '')
						$('#cancelCreationModal').modal('show');
					else
						$('#' + $scope.modaName).modal('hide');
				};
				$scope.continueCreation = function() {

					$('#' + $scope.modaName).modal('hide');

				}
				/** End */
				/*------------------------------------------History-------------------------------------------------*/
				var cellToolTipTemplate = '<div class="ui-grid-cell-contents" title="{{COL_FIELD}}">{{ COL_FIELD }}</div>';
				$scope.HistoryGridOptions = {
					enableColumnResizing : true,
					enableFiltering : true,
					enableSorting : true,
					columnDefs : [

							{
								name : 'date',
								displayName : cultureService.localize('cop.history.date'),
								type : 'date',
								field : 'updationDate',
								cellFilter : 'date:\'dd/MM/yyyy HH:mm:ss\'',
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
								name : 'oldValue',
								displayName : cultureService.localize('cop.history.oldValue'),
								field : 'oldValue',
								enableCellEdit : false,
								cellTemplate : cellToolTipTemplate
							}, {
								name : 'newValue',
								displayName : cultureService.localize('cop.history.newValue'),
								field : 'newValue',
								enableCellEdit : false,
								cellTemplate : cellToolTipTemplate
							},

					],
					data : 'allHistory',

				};

				/* to get History Data */
				$scope.allHistory = [];
				$scope.getAllHistory = function(screenId) {

					var historyRepresentation = {
						'screenId' : screenId,
						'vehicleFileId' : "null"
					};

					HistoryService.HistroyResource.historyData(historyRepresentation, function(success) {
						$scope.allHistory = success;
						$('#showHistoryBox').modal('show');
					}, function(error) {

					});
				};
				// ----------------------HISTORY END------------------------------------------------
				//
				$scope.slideToggle = function() {

					$("#wrapper").toggleClass("toggled");
					$(".slide-resize").toggleClass("col-lg-10");
					$("#menu-toggle").toggleClass('fa-chevron-right');
				};

				$("#TvvStructure").css('background-color', '#DBDBDB', 'color', '#fff');

			}
	]);

	TvvStructure.directive('editlabel', function() {
		return {
			restrict : 'E',
			scope : {
				model : '='
			},
			replace : false,
			template : '<span>' + '<div class="col-lg-9" style="max-height: 25px; overflow: hidden;text-overflow: ellipsis;">' + '<input type="text" ng-model="model" ng-show="edit" ng-enter="edit=false" class="custom-form-control"></input>' + '<b><span ng-show="!edit" class="editable-text" title="{{model}}">{{model}}</span></b>' + '</div>' + '<div class="col-lg-2" style="text-align:right; padding:0px 5px;">' + '<span ng-show="!edit">&nbsp;<span class="fa fa-pencil"></span></span>' + '</div>' + '</span>',
			link : function(scope, element, attrs) {
				scope.edit = false;
				element.bind('click', function() {
					scope.$apply(scope.edit = true);
					element.find('input').focus();
				});
			}
		};
	});

	TvvStructure.directive('ngEnter', function() {
		return function(scope, element, attrs) {

			element.bind('keypress', function(e) {
				if (e.charCode === 13 || e.keyCode === 13) {
					scope.$apply(attrs.ngEnter);
				}
			});

		};
	});

	return {
		angularModules : [
			'TvvStructure'
		]
	};
});