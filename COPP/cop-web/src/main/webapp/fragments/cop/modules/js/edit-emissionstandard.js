define([
		'require', 'module', '{angular}/angular', '{w20-core}/modules/ui'
], function(require, module, angular) {
	'use strict';
	var esDetailsModule = angular.module('esDetailsModule', [
			'ui.bootstrap', 'copCommon'
	]), config = module && module.config() || {};
	esDetailsModule.factory('ESService', [
			'$rootScope', function($rootScope) {

				var savedEmissionStandard = {};

				return savedEmissionStandard;
			}
	]);

	esDetailsModule.service('SelectedPgLabelsService', function() {

		var selectedPgLabels = [];
		var selectedPgLabelsForFCoff = [];
		var availablePgLabels = [];

		return {
			getSelectedPgLabels : function() {
				return selectedPgLabels;
			},
			setSelectedPgLabels : function(value, id) {
				var object = {};
				object.label = value;
				object.id = id;
				selectedPgLabels.push(object);
			},
			updateSelectedPgLabels : function(array) {
				selectedPgLabels = array;
			},
			getAvailablePgLabels : function() {
				return availablePgLabels;
			},
			setAvailablePgLabels : function(value) {
				availablePgLabels.push(value);
			},
			getSelectedPgLabelsForFCoff : function() {
				return selectedPgLabelsForFCoff;
			},
			setSelectedPgLabelsForFCoff : function(value, id) {
				var object = {};
				object.label = value;
				object.id = id;
				selectedPgLabelsForFCoff.push(object);
			},
			updateSelectedPgLabelsForFCoff : function(array) {
				selectedPgLabelsForFCoff = array;
			},

		};
	});

	esDetailsModule.factory('EMSService1', [
			'$resource',

			function($resource) {
				return {
					EmissionStandardResource : $resource('emissionStandard/:path/:entityId', {
						path : '@path',
						entityId : '@entityId'
					}, {

						'getAllEmissionStandards' : {

							method : 'GET',
							params : {
								path : 'AllEmissionStandards'
							},
							isArray : true
						},
						'getAllEmissionStandardsForRG' : {
							method : 'GET',
							params : {
								path : 'EmissionStandardsForRG'
							},
							isArray : true
						}
					})
				};
			}
	]);

	esDetailsModule.factory('FCAppTypeService', [
			'$resource',

			function($resource) {
				return {
					FCAppResource : $resource('fcAppType/:path/:entityId', {
						path : '@path',
						entityId : '@entityId',

					}, {

						'getAllFCApplicationTypes' : {
							method : 'Get',
							params : {
								path : 'FactorCoeffApplicationTypes'
							},
							isArray : true
						}

					})
				};
			}
	]);

	esDetailsModule.factory('PGLabelService', [
			'$resource',

			function($resource) {
				return {
					PGLabelResource : $resource('pollutantReference/:path/:entityId', {
						path : '@path',
						entityId : '@entityId'
					}, {

						/*
						 * 'getAllPGLables' : { method : 'GET', params : { path : 'PollutantGasLables' }, isArray : true },
						 */
						'getAllUnUsedPGLables' : {
							method : 'GET',
							params : {
								path : 'UnUsedPollutantGasLabels'
							},
							isArray : true
						}

					})
				};
			}
	]);

	esDetailsModule.factory('FCListService', [
			'$resource',

			function($resource) {
				return {
					FCListResource : $resource('factorOrCoeff/:path/:entityId/:emsId', {
						path : '@path',
						entityId : '@entityId',
						emsId : '@emsId'
					}, {

						'getEsDependentLists' : {
							method : 'Get',
							params : {
								path : 'FactorCoefficentLists'
							},
							isArray : true
						},
						'savefactorCoefficientListChanges' : {
							method : 'POST',
							params : {
								path : 'FactorCoefficentLists'
							},
							isArray : true
						},
						'checkFCApplicationTypes' : {
							method : 'POST',
							params : {
								path : 'FCApplicationTypes'
							},
							isArray : true
						},
						'getUnUsedPGLabelsForfactorOrCoeff' : {
							method : 'GET',
							params : {
								path : 'UnUsedPGLabelsForfactorOrCoeff'
							},
							isArray : true
						}

					})
				};
			}
	]);

	// Emission Standard details Controller Definition
	esDetailsModule.controller('esDetailsModuleController', [

			'$scope', '$modal', '$routeParams', 'EmsDepTDLService', 'EmsDepTCLService', 'EmissionStandardService', 'VTService', 'FuelITypeService', 'CategoryCommonService', 'FuelCommonService', 'EMSService1', 'FCAppTypeService', 'PGLabelService', 'FCListService', 'PGListService', 'ESCommonService', 'CultureService', 'ESService', 'NotificationService', 'DataTypeService', 'HistoryService', 'AuthorizationService', 'SelectedPgLabelsService', function($scope, $modal, $routeParams, EmsDepTDLService, EmsDepTCLService, EmissionStandardService, VTService, FuelITypeService, CategoryCommonService, FuelCommonService, EMSService1, FCAppTypeService, PGLabelService, FCListService, PGListService, ESCommonService, cultureService, ESService, NotificationService, DataTypeService, HistoryService, authorizationService, SelectedPgLabelsService) {

				/*----------------------------------------Authorization----------------------------------------*/
				var authentication = false;
				if (authorizationService.hasRole('seed-w20', 'POCMRole')) {
					authentication = true;
				}
				$scope.authorization = function(role) {
					return authorizationService.hasRole('seed-w20', role);
				};
				/*---------------------------------------------------------------------------------------*/

				$scope.test = "Dummy Title";
				$scope.title = "Title";
				$scope.technicalDatalists = [];
				$scope.tempList = [];
				$scope.emissionStandard = {};
				$scope.eStandard = {};
				$scope.eStandard.selected = ESService.savedEmissionStandard;

				$scope.technicalDataActive = true;
				$scope.testConditionlists = [];
				$scope.selectedAction = null;
				$scope.selectedPgLabels = [];
				$scope.fcPgLabelList = [];
				$scope.temp = [];

				$scope.actions = [

						{
							'label' : cultureService.localize("cop.ems.label.copy"),
							'value' : 'copy'
						}, {
							'label' : cultureService.localize("cop.ems.label.changeStatus"),
							'value' : 'changeStatus'
						}, {
							'label' : cultureService.localize("cop.ems.label.delete"),
							'value' : 'delete'
						}
				];

				// ---------------------------COMMON( TAB CONTROL and Actions)--------------------------------------------------------------------

				$scope.showTechnicalData = function() {
					$scope.showSaveTCConfirmation(false);
					$scope.showSavePGConfirmation(false);
					$scope.showSaveFCConfirmation(false);
					$scope.technicalDataActive = true;

					$scope.testConditionActive = false;
					$scope.factorsActive = false;
					$scope.limitsActive = false;
				};

				$scope.showTestConditions = function() {
					$scope.showSaveConfirmation(false);
					$scope.showSavePGConfirmation(false);
					$scope.showSaveFCConfirmation(false);

					$scope.technicalDataActive = false;
					$scope.testConditionActive = true;
					$scope.factorsActive = false;
					$scope.limitsActive = false;
				};
				$scope.showFactors = function() {
					$scope.showSaveTCConfirmation(false);
					$scope.showSaveConfirmation(false);
					$scope.showSavePGConfirmation(false);
					$scope.technicalDataActive = false;
					$scope.testConditionActive = false;
					$scope.factorsActive = true;
					$scope.limitsActive = false;

				}
				$scope.showLimits = function() {
					$scope.showSaveTCConfirmation(false);
					$scope.showSaveConfirmation(false);
					$scope.showSaveFCConfirmation(false);
					$scope.technicalDataActive = false;
					$scope.testConditionActive = false;
					$scope.factorsActive = false;
					$scope.limitsActive = true;
				}

				/**
				 * This method handles actions
				 */
				$scope.takeAction = function(selectedAction) {
					$scope.selectedAction = selectedAction;

					if ($scope.selectedAction === "copy") {
						$scope.showCopyModal();
					} else if ($scope.selectedAction === "delete") {
						$('#deleteEMSlModal').modal('show');
					} else if ($scope.selectedAction === "changeStatus") {

						$('#validEMSlModal').modal('show');

					}
					$scope.selectedAction = false;
				}
				$scope.showCopyModal = function() {
					$('#copyEmsModal').modal('show');
				};
				$scope.changeEmissionName = function() {

					document.getElementById("eslabelId").style.border = "1px solid #ccc";
				};

				$scope.checkLabel = function() {
					if ($scope.eslabel !== undefined && $scope.eslabel != null && $scope.eslabel.length > 0) {
						ESCommonService.ESResource.getEmissionStandardsWithLabel({
							label : $scope.eslabel
						}, function(response) {
							if (response === null || response.length === 0)
								$scope.copySelectedEms();
							else {
								$('#duplicateLabelModal').modal('show');
								document.getElementById("eslabelId").style.border = "2px solid red";
							}
						}, function() {
						});
					} else
						NotificationService.notify(cultureService.localize('cop.common.message.mandatoryFields'));

				};

				$scope.copySelectedEms = function() {

					var esToCreate = $scope.emissionStandard;
					esToCreate.esLabel = $scope.eslabel;
					esToCreate.description = $scope.esDescription;
					ESCommonService.ESResource.copyEmisssionStandard(esToCreate, function(response) {
						NotificationService.notify(cultureService.localize('cop.ems.message.copySuccess'));

						$scope.eStandard.selected = response;
						$scope.initializeEMS();

					}, function() {

						NotificationService.notify(cultureService.localize('cop.ems.message.copyError'));
					});
					$scope.eslabel = "";
					$scope.esDescription = "";
				};
				$scope.deleteEmissionStandard = function() {
					ESCommonService.ESResource.deleteEmisssionStandard($scope.emissionStandard, function() {
						NotificationService.notify(cultureService.localize('cop.ems.message.deleteSuccess'));
						$scope.eStandard.selected = null;
						$scope.initializeEMS();
					}, function(error) {

						if (error.status === 412)
							NotificationService.notify(cultureService.localize('cop.ems.message.validDeleteError'));
						else

							NotificationService.notify(cultureService.localize('cop.ems.message.deleteError'));
					});

				};
				$scope.changeEmsStatus = function() {

					ESCommonService.ESResource.changeEmissionStandardStatus($scope.emissionStandard, function(response) {
						NotificationService.notify(cultureService.localize('cop.ems.message.validSuccess'));
						var newEms = response;
						$scope.emsList.splice($scope.indexSelected, 1);

						$scope.emsList[$scope.emsList.length] = newEms;
						$scope.eStandard.selected = newEms;
						$scope.setEms(newEms);
						$scope.initializeEMS();
					}, function() {

						NotificationService.notify(cultureService.localize('cop.ems.message.validError'));
					});
				}
				// -----------------------------------------------------------------------------------------------------------------

				// --------------------------------TECHNICAL DATA LIST--------------------------------------------------------------
				/**
				 * THis method fetches all EmissionStandard data initially
				 */
				$scope.initializeEMS = function() {

					EMSService1.EmissionStandardResource.getAllEmissionStandards(function(response) {

						$scope.emsList = response;
						$scope.listsAvail = true;
						// set first item in list as selected item initially
						if ($scope.emsList === null || $scope.emsList.length === 0)
							NotificationService.notify(cultureService.localize('cop.ems.message.emptyEmissionStandard'));
						else if ($scope.eStandard.selected !== undefined && $scope.eStandard.selected !== null) {
							$scope.setEms($scope.eStandard.selected);
						} else {
							$scope.setEms($scope.emsList[0]);
						}

					}, function() {
						NotificationService.notify(cultureService.localize('cop.testCondition.message.fetchError'));
					});
				};
				// Initialize emission standard data initially
				$scope.initializeEMS();

				/**
				 * set emission standard in scope and fetch all the lists corresponding to emission standard
				 */
				$scope.setEms = function(selectedEms) {
					$scope.indexSelected = $scope.emsList.indexOf(selectedEms);
					$scope.eStandard.selected = selectedEms;
					$scope.emissionStandard = selectedEms;
					$scope.isStatusValid = false;
					if (selectedEms.status.label.toUpperCase() === "VALID")
						$scope.isStatusValid = true;
					$scope.getEmsDependentLists(selectedEms.entityId);
					$scope.initializeTCLists(selectedEms.entityId);
					$scope.initializeFCLists(selectedEms.entityId);
					$scope.initializePGLists(selectedEms.entityId);
					$scope.fetchAllLists();
				};

				/**
				 * THis method fetches all EmsDepTDL data initially
				 */
				$scope.getEmsDependentLists = function(emsId) {

					EmsDepTDLService.EmsDepTDLResource.getEsDependentLists({
						emsId : emsId
					}, function(response) {

						$scope.technicalDatalists = response;
						$scope.listsAvail = true;
						// Assignment for Cancel Button
						$scope.technicalDatalistsCopy = angular.copy($scope.technicalDatalists);

					}, function(error) {
						NotificationService.notify(cultureService.localize('cop.testCondition.message.fetchError'));
					});
				};

				// this method displays a modal while adding TDlist
				$scope.showAddListModal = function() {

					$('#addListModal').modal('show');

				};
				// This method adds TD list to display (it is not actually saved)
				$scope.addList = function() {

					if ($scope.Listlabel !== undefined && $scope.Listlabel !== null && $scope.Listlabel.length > 0) {

						var duplicatcheck = true;
						for (var i = 0; i < $scope.technicalDatalists.length; i++) {
							if ($scope.Listlabel.toUpperCase() === ($scope.technicalDatalists[i]['label']).toUpperCase()) {
								duplicatcheck = false;
								break;
							}
						}
						if (duplicatcheck) {
							EmsDepTDLService.EmsDepTDLResource.checkEsDependentLists({
								entityId : $scope.eStandard.selected.entityId,
								emsId : $scope.Listlabel
							}, function(response) {
								$scope.technicalDatalists.push({
									'label' : $scope.Listlabel,
									'description' : $scope.ListDescription,
									modifiedFlag : "INSERT",
									emissionStandard : $scope.emissionStandard,
									genericTechnicalData : []
								});
								$scope.Listlabel = "";
								$scope.ListDescription = "";
							}, function(error) {
								NotificationService.notify(cultureService.localize('cop.emissionStandard.message.duplicate'));
							});
						} else {
							NotificationService.notify(cultureService.localize('cop.emissionStandard.message.duplicate'));
						}

					} else
						NotificationService.notify(cultureService.localize('cop.common.message.mandatoryFields'));

				};
				// This method displays modal while adding generic technical data item to list
				$scope.showAddModal = function(list) {

					$scope.selectedList = list;
					$('#addDatalModal').modal('show');

				};
				//
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
						var technicalData = {
							'label' : $scope.itemlabel,
							'description' : $scope.itemDescription,
							'unit' : {}
						};
						$scope.selectedList.genericTechnicalData.push(technicalData);
						if ($scope.selectedList.entityId !== null && $scope.selectedList.entityId !== 0 && $scope.selectedList.entityId !== undefined)
							$scope.selectedList.modifiedFlag = "UPDATE";
						$scope.itemlabel = "";
						$scope.itemDescription = "";
					} else
						NotificationService.notify(cultureService.localize('cop.common.message.mandatoryFields'));

				};
				// shpows item while deleting generic technical data
				$scope.showDeleteItemModal = function(list, label, index) {
					$scope.selectedList = list;
					$scope.dataDeleteLabel = label;
					$scope.selectedDataItemIndex = index;
					$('#deleteDatalModal').modal('show');
				};
				// removes generic technical data
				$scope.removeDataItem = function() {
					$scope.selectedList.modifiedFlag = "UPDATE";
					$scope.selectedList.genericTechnicalData[$scope.selectedDataItemIndex]["isDeleted"] = "TRUE";

					var ele = document.getElementById($scope.selectedList.genericTechnicalData[$scope.selectedDataItemIndex]["label"] + '-' + $scope.selectedDataItemIndex);
					if (ele !== undefined && ele !== null) {
						// TODO: Why is this required?
						ele.setAttribute('class', 'label label-danger');
					}

				};
				// RETURNS css to indicate temprary deleted data item
				$scope.getClass = function(condition) {

					if (condition.isDeleted === "TRUE")
						return "label label-default";
					return "label label-danger";
					if (!$scope.$$phase) {
						$scope.$apply();
					}
				};
				// set modified flag in TDL object
				$scope.setModifiedFlag = function(list) {

					$scope.selectedList = list;
					if ($scope.selectedList.entityId !== null && $scope.selectedList.entityId !== 0 && $scope.selectedList.entityId !== undefined)
						$scope.selectedList.modifiedFlag = "UPDATE";
					else
						$scope.selectedList.modifiedFlag = "INSERT";
				};
				$scope.datatypeValidation = function(dataItem) {
					var INTEGER_REG = new RegExp("^[-]?[0-9]+$");

					var FLOAT_REG = new RegExp("^[-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?");

					if (!angular.isUndefined(dataItem.dataType) && dataItem.defaultValue !== null && !angular.isUndefined(dataItem.defaultValue)) {
						if (dataItem.dataType === 'INTEGER') {
							if (!INTEGER_REG.test(dataItem.defaultValue)) {
								NotificationService.notify(cultureService.localize('cop.tvv.message.numRequired'));
								dataItem.defaultValue = '';
							}
						} else if (dataItem.dataType === 'REAL') {
							if (!FLOAT_REG.test(dataItem.defaultValue)) {
								NotificationService.notify(cultureService.localize('cop.tvv.message.floatRequired'));
								dataItem.defaultValue = '';
							}
						} else if (dataItem.dataType === 'BOOLEAN') {
							if (dataItem.defaultValue.toUpperCase() === 'Y') {
								dataItem.defaultValue = 'YES'
							} else if (dataItem.defaultValue.toUpperCase() === 'O') {
								dataItem.defaultValue = 'OUI'
							} else if (dataItem.defaultValue.toUpperCase() === 'N') {
								dataItem.defaultValue = 'NON'
							} else {
								NotificationService.notify(cultureService.localize('cop.tvv.message.boolRequired'));
								dataItem.defaultValue = '';
							}

						}
					}

				}
				// show modal while saving TDL
				$scope.showSaveConfirmation = function(isValidated) {

					$scope.listModified = false;

					for (var i = 0; i < $scope.tempList.length; i++)
						$scope.technicalDatalists.push($scope.tempList[i]);
					if ($scope.technicalDatalists == null)
						return;
					for (var j = 0; j < $scope.technicalDatalists.length; j++) {
						if ($scope.technicalDatalists[j].modifiedFlag != null && $scope.technicalDatalists[j].modifiedFlag !== "") {
							$scope.listModified = true;
							break;
						}
					}

					if (isValidated) {
						if ($scope.listModified) {
							$('#ConfirmSaveModal').modal('show');
						} else {
							$scope.errorMessage = cultureService.localize('cop.common.message.noModifications');
							$('#errorDisplayModal').modal('show');

						}
					}
					if (authentication && (!isValidated && $scope.listModified))

						$('#TdlModificationModal').modal('show');

				};
				$scope.saveTdlModifications = function() {
					$scope.saveChanges();
				};
				$scope.removeTdlModifications = function() {
					$scope.cancelModifiedESTDChanges();
					$('#TdlModificationModal').modal('hide');
				};

				// Changes on Emission TD Cancel Button
				$scope.CancelConfirmationESTDL = function() {
					$scope.dataNotChanged = angular.equals($scope.technicalDatalists, $scope.technicalDatalistsCopy);
					if (!$scope.dataNotChanged) {
						$('#ConfirmCancelESTDModal').modal('show');
					}

				};
				$scope.cancelModifiedESTDChanges = function() {
					$scope.technicalDatalists = angular.copy($scope.technicalDatalistsCopy);
				};

				// End of Changes on Cancel Button
				/**
				 * This method saves all modifications (INSERT|UPDATE|DELETE) for TDL
				 */
				$scope.saveChanges = function() {

					if ($scope.emissionStandard.status.label.toLowerCase() === "valid" && $scope.listModified)
						$scope.newVersion1 = true;

					var objectToSend = {
						'emsDepTDLRepresentationList' : $scope.technicalDatalists,
						'changeEmsVersion' : $scope.newVersion1
					};
					$scope.tempList = [];
					EmsDepTDLService.EmsDepTDLResource.saveAllChanges(objectToSend, function(success) {
						$scope.technicalDatalists = success;
						NotificationService.notify(cultureService.localize('cop.testCondition.message.successMessage'));
						$scope.newVersion1 = false;
						if ($scope.technicalDatalists.length > 0) {
							var newEms = $scope.technicalDatalists[0]["emissionStandard"];
							$scope.emsList[$scope.emsList.length] = newEms;
							$scope.eStandard.selected = $scope.emsList[$scope.emsList.length - 1];
							$scope.setEms(newEms);
						} else {
							$scope.setEms($scope.emissionStandard);
						}
						$scope.initializeEMS();

					}, function(error) {
						$scope.newVersion1 = false;
						NotificationService.notify(cultureService.localize('cop.testCondition.message.errorMessage'));
					});
					$scope.listModified = false;
				};

				// ---------------------------------------------TEST CONDITION LIST------------------------------------------------------------------------
				$scope.dataTypes = [];

				$scope.getDataTypes = function() {
					DataTypeService.dataTypeResource.getAllDataTypes(function(success) {
						$scope.dataTypes = success;
					}, function() {
					})
				};
				$scope.getDataTypes();
				/**
				 * THis method fetches all emsDepTCL data based on slected emission standard
				 */
				$scope.initializeTCLists = function(emsId) {
					EmsDepTCLService.emsDepTCLResource.getEsDependentLists({
						emsId : emsId
					}, function(response) {

						$scope.testConditionlists = response;
						$scope.listsAvail = true;

						// Assignment for Cancel Button
						$scope.testConditionlistsCopy = angular.copy($scope.testConditionlists);

					}, function(error) {

						NotificationService.notify(cultureService.localize('cop.testCondition.message.fetchError'));
					});
				};

				// this method displays a modal while adding TCL
				$scope.showAddTCLListModal = function() {

					$('#addTCListModal').modal('show');

				};

				$scope.showDeleteTCListModal = function(list, label, index) {
					$scope.indexTCToBeDeleted = index;
					$scope.selectedTCList = list;
					$scope.deleteListTCLabel = label;
					$('#ConfirmDeleteTCModal').modal('show');

				};
				// This method adds list to display (it is not actually saved)
				$scope.addTCLList = function() {

					if ($scope.TCListlabel !== undefined && $scope.TCListlabel !== null && $scope.TCListlabel.length > 0) {
						var duplicatcheck = true;
						for (var i = 0; i < $scope.testConditionlists.length; i++) {
							if ($scope.TCListlabel.toUpperCase() === ($scope.testConditionlists[i]['label']).toUpperCase()) {
								duplicatcheck = false;
								break;
							}
						}
						if (duplicatcheck) {
							EmsDepTCLService.emsDepTCLResource.checkEsDependentLists({
								entityId : $scope.eStandard.selected.entityId,
								emsId : $scope.TCListlabel
							}, function(response) {
								$scope.testConditionlists.push({
									'label' : $scope.TCListlabel,
									'description' : $scope.TCListDescription,
									emissionStandard : $scope.emissionStandard,
									modifiedFlag : "INSERT",
									genericTestCondition : []
								});
								$scope.TCListlabel = "";
								$scope.TCListDescription = "";

							}, function(error) {
								NotificationService.notify(cultureService.localize('cop.emissionStandard.message.duplicate'));
							});

						} else {
							NotificationService.notify(cultureService.localize('cop.emissionStandard.message.duplicate'));
						}
					} else
						NotificationService.notify(cultureService.localize('cop.common.message.mandatoryFields'));
				};
				// This method displays modal while adding generic test condition item to list
				$scope.showAddConditionModal = function(list) {

					$scope.selectedTCList = list;
					$('#addconditionModal').modal('show');

				};
				// This method removes list from display
				$scope.removeTCList = function() {
					$scope.testConditionlists[$scope.indexTCToBeDeleted]["modifiedFlag"] = "DELETE";
					$scope.tempList.push($scope.testConditionlists[$scope.indexTCToBeDeleted]);
					$scope.testConditionlists.splice($scope.indexTCToBeDeleted, 1);

				};
				// //This method adds generic test condition to list for display (it is not actually saved)
				$scope.addCondition = function() {
					if ($scope.TCitemlabel !== undefined && $scope.TCitemlabel !== null && $scope.TCitemlabel.length > 0) {
						var testCondition = {
							'label' : $scope.TCitemlabel,
							'description' : $scope.TCitemDescription,
							'unit' : {}
						};
						if ($scope.selectedTCList.genericTestCondition == null)
							$scope.selectedTCList.genericTestCondition = [];
						$scope.selectedTCList.genericTestCondition.push(testCondition);
						if ($scope.selectedTCList.entityId !== null && $scope.selectedTCList.entityId !== 0 && $scope.selectedTCList.entityId !== undefined)
							$scope.selectedTCList.modifiedFlag = "UPDATE";
						$scope.TCitemlabel = "";
						$scope.TCitemDescription = "";

					} else
						NotificationService.notify(cultureService.localize('cop.common.message.mandatoryFields'));

				};
				// shows modal while deelting condition
				$scope.showDeleteConditionModal = function(list, label, index) {
					$scope.selectedTCList = list;
					$scope.selectedDataItemIndex = index;
					$scope.deleteConditionLabel = label;
					$('#deleteConditionlModal').modal('show');
				};
				// removes condition from list
				$scope.removeCondition = function() {

					$scope.selectedTCList.modifiedFlag = "UPDATE";
					$scope.selectedTCList.genericTestCondition[$scope.selectedDataItemIndex]["isDeleted"] = "TRUE";

					var ele = document.getElementById($scope.selectedTCList.genericTestCondition[$scope.selectedDataItemIndex]["label"] + '-' + $scope.selectedDataItemIndex);
					if (ele !== undefined && ele !== null) {
						// TODO: Why is this required?
						ele.setAttribute('class', 'label label-danger');
					}

				};

				$scope.getClass = function(condition) {
					NotificationService.notify("here");
					if (condition.isDeleted === "TRUE")
						return "label label-default";
					return "label label-danger";
					if (!$scope.$$phase) {
						$scope.$apply();
					}
				};
				// sets modified flag in TCL
				$scope.setModifiedTCFlag = function(list) {
					$scope.selectedTCList = list;
					if ($scope.selectedTCList.entityId !== null && $scope.selectedTCList.entityId !== 0 && $scope.selectedTCList.entityId !== undefined)
						$scope.selectedTCList.modifiedFlag = "UPDATE";
					else
						$scope.selectedTCList.modifiedFlag = "INSERT";
				};
				// shows modal while saving TCL
				$scope.showSaveTCConfirmation = function(isValidated) {
					$scope.listTCModified = false;
					for (var i = 0; i < $scope.tempList.length; i++)
						$scope.testConditionlists.push($scope.tempList[i]);
					if ($scope.testConditionlists == null)
						return;
					for (var j = 0; j < $scope.testConditionlists.length; j++) {
						if ($scope.testConditionlists[j].modifiedFlag != null && $scope.testConditionlists[j].modifiedFlag !== "") {
							$scope.listTCModified = true;
							break;
						}
					}
					if (isValidated) {
						if ($scope.listTCModified)
							$('#ConfirmSaveTCLModal').modal('show');

						else {
							$scope.errorMessage = cultureService.localize('cop.common.message.noModifications');
							$('#errorDisplayModal').modal('show');

						}
					}
					if (authentication && (!isValidated && $scope.listTCModified))
						$('#TclModificationModal').modal('show');
				};

				$scope.saveTclModification = function() {
					$scope.saveTCLChanges();
				};
				$scope.removeTclModification = function() {
					$scope.cancelModifiedESTCChanges();
					$('#TclModificationModal').modal('hide');
				};
				// Changes on Emission TC Cancel Button
				$scope.CancelConfirmationESTCL = function() {
					$scope.dataNotChanged = angular.equals($scope.testConditionlists, $scope.testConditionlistsCopy);
					if (!$scope.dataNotChanged) {
						$('#ConfirmCancelESTCModal').modal('show');
					}

				};
				$scope.cancelModifiedESTCChanges = function() {
					$scope.testConditionlists = angular.copy($scope.testConditionlistsCopy);
				};

				// End of Changes on Cancel Button
				/**
				 * This method saves all modifications (INSERT|UPDATE|DELETE)
				 */
				$scope.saveTCLChanges = function() {

					if ($scope.emissionStandard.status.label.toLowerCase() === "valid" && $scope.listTCModified)
						$scope.newVersion2 = true;

					$scope.tempList = [];

					var objectToSend = {
						'emsDepTCLRepresentationList' : $scope.testConditionlists,
						'changeEmsVersion' : $scope.newVersion2
					};

					EmsDepTCLService.emsDepTCLResource.saveAllChanges(objectToSend, function(success) {
						$scope.testConditionlists = success;
						NotificationService.notify(cultureService.localize('cop.testCondition.message.successMessage'));
						$scope.newVersion2 = false;
						if ($scope.testConditionlists.length > 0) {
							var newEms = $scope.testConditionlists[0]["emissionStandard"];
							$scope.emsList[$scope.emsList.length] = newEms;
							$scope.eStandard.selected = $scope.emsList[$scope.emsList.length - 1];
							$scope.setEms(newEms);
						} else

							$scope.setEms($scope.emissionStandard);

					}, function(error) {
						$scope.newVersion2 = false;
						NotificationService.notify(cultureService.localize('cop.testCondition.message.errorMessage'));
					});
					$scope.listTCModified = false;
				};

				// ---------------------------------------FACTOR OR COFFICIENT LIST-----------------------------------------------------------
				$scope.test = "Dummy Title";
				$scope.selectOpt1 = "saisir un TVV";
				$scope.selectOpt2 = "saisir un code envelope";
				$scope.selectOpt3 = "saisir un famille";

				$scope.vehicleTList = [];
				$scope.fuelITList = [];
				$scope.categoryList = [];
				$scope.fuelList = [];
				$scope.vehicleTechnology = null;
				$scope.injectionType = null;
				$scope.category = null;
				$scope.factorsList = [];
				$scope.classList = [];

				$scope.carburant = null;
				$scope.setCorburant = function(corburant, list) {

					if ($scope.carburant !== corburant) {
						$scope.carburant = corburant;
						if ($scope.factorsActive) {
							$scope.setModifiedFCFlag(list);
							// list.fuels.push(corburant);
						} else {
							$scope.setModifiedPGFlag(list);
							// list.fuels.push(corburant);
						}
					}
				};
				$scope.setVTechnology = function(Vtechnology, list) {
					if ($scope.vehicleTechnology !== Vtechnology) {
						$scope.vehicleTechnology = Vtechnology;
						if ($scope.factorsActive) {
							$scope.setModifiedFCFlag(list);
							// list.vehicleTechnologySet.push(Vtechnology);
						} else {
							$scope.setModifiedPGFlag(list);
							// list.vehicleTechnologySet.push(Vtechnology);
						}
					}
				};

				$scope.setInjectionType = function(itype, list) {
					if ($scope.injectionType !== itype) {
						$scope.injectionType = itype;
						if ($scope.factorsActive) {
							$scope.setModifiedFCFlag(list);

						} else {
							$scope.setModifiedPGFlag(list);

						}
					}
				};
				$scope.setCategory = function(cat, list) {
					if ($scope.category !== cat) {
						$scope.category = cat;
						if ($scope.factorsActive) {
							$scope.setModifiedFCFlag(list);

						} else {
							$scope.setModifiedPGFlag(list);

						}
					}
				};

				$scope.setClass = function(clasz, list) {
					if ($scope.clasz !== clasz) {
						$scope.clasz = clasz;
						if ($scope.factorsActive) {
							$scope.setModifiedFCFlag(list);

						} else {
							$scope.setModifiedPGFlag(list);

						}
					}
				};
				$scope.setApplicationType = function(appType, list) {
					if ($scope.applicationType !== appType) {
						$scope.applicationType = appType;
						list.fcApplicationTypes.push(appType);
						$scope.setModifiedFCFlag(list);
					}
				};

				$scope.checkFlag = function(pgLabel) {
					var selectedPgLabels = SelectedPgLabelsService.getSelectedPgLabels();

					for (var i = 0; i <= selectedPgLabels.length; i++) {
						if (selectedPgLabels[i] !== undefined && selectedPgLabels[i] !== null) {

							if (selectedPgLabels[i].id === $scope.eStandard.selected.entityId) {
								if (selectedPgLabels[i].label === pgLabel) {
									selectedPgLabels.splice(i, 1);
									SelectedPgLabelsService.updateSelectedPgLabels(selectedPgLabels);
								}
							}
						}
					}

				}
				$scope.checkVisible = function(pgLabel, list) {
					// $scope.flag=false;
					var flag;
					var selectedPgLabels = SelectedPgLabelsService.getSelectedPgLabels();
					if (pgLabel !== undefined) {
						for (var i = 0; i <= selectedPgLabels.length; i++) {
							if (selectedPgLabels[i] !== undefined && selectedPgLabels[i] !== null) {
								// if (pgl.label === pgLabel.label && pgl.id === $scope.eStandard.selected.entityId) {
								if (selectedPgLabels[i].id === $scope.eStandard.selected.entityId) {
									if (selectedPgLabels[i].label === pgLabel.label) {

										return true;
									}
								}
								// }
							}
						}

					}

				}

				$scope.baseValueChange = function(oldVal, newVal) {
					if (oldVal !== undefined)
						$scope.unSetPGLabel(oldVal.label);

				}

				$scope.baseValueChangeForFCoff = function(oldVal, newVal) {
					if (oldVal !== undefined)
						$scope.unSetPGLabelForFCoff(oldVal.label);

				}

				$scope.setPGLabel = function(pgLabel, list) {

					var selectedPgLabels = SelectedPgLabelsService.getSelectedPgLabels();

					var flag;
					for ( var i in list.pollutantGasLimit) {

						if (pgLabel !== undefined && list.pollutantGasLimit[i].pgLabel !== undefined) {

							if (pgLabel.label === list.pollutantGasLimit[i].pgLabel.label) {
								if (selectedPgLabels.length == 0) {
									SelectedPgLabelsService.setSelectedPgLabels(list.pollutantGasLimit[i].pgLabel.label, $scope.eStandard.selected.entityId);
									setTemp(list.pollutantGasLimit[i].pgLabel.label, list.pollutantGasLimit[i].pgLabel);
								} else {
									selectedPgLabels.forEach(function(pgl) {
										if (pgl.label === pgLabel.label) {
										} else {
											flag = true;
										}
									});

									if (flag) {
										SelectedPgLabelsService.setSelectedPgLabels(list.pollutantGasLimit[i].pgLabel.label, $scope.eStandard.selected.entityId);
										setTemp(list.pollutantGasLimit[i].pgLabel.label, list.pollutantGasLimit[i].pgLabel);
									} else {

									}
								}

							}

						}

					}
					if ($scope.factorsActive) {
						$scope.setModifiedFCFlag(list);

					} else {
						$scope.setModifiedPGFlag(list);

					}
				}

				function setTemp(label, index) {
					var object = {};
					object.label = label;
					object.pgLabel = index
					$scope.temp.push(object);
				}
				$scope.unSetPGLabel = function(pgLabel) {
					var selectedPgLabels = SelectedPgLabelsService.getSelectedPgLabels();

					for (var i = 0; i <= selectedPgLabels.length; i++) {
						if (selectedPgLabels[i] !== undefined && selectedPgLabels[i] !== null) {

							if (selectedPgLabels[i].id === $scope.eStandard.selected.entityId) {
								if (selectedPgLabels[i].label === pgLabel) {
									selectedPgLabels.splice(i, 1);
									SelectedPgLabelsService.updateSelectedPgLabels(selectedPgLabels);
								}
							}
						}
					}

				}

				$scope.checkVisibleForFCoff = function(pgLabel, list) {

					var selectedPgLabels = SelectedPgLabelsService.getSelectedPgLabelsForFCoff();
					if (pgLabel !== undefined) {
						for (var i = 0; i < selectedPgLabels.length; i++) {
							if (selectedPgLabels[i] !== undefined && selectedPgLabels[i] !== null) {
								if (selectedPgLabels[i].id === $scope.eStandard.selected.entityId) {
									if (selectedPgLabels[i].label === pgLabel.label) {
										return true
									}
								}

							}
						}

					}

				}

				$scope.setPGLabelForFCoff = function(pgLabel, list) {

					var selectedPgLabels = SelectedPgLabelsService.getSelectedPgLabelsForFCoff();
					var flag;
					for ( var i in list.factorOrCoeffiecients) {
						if (pgLabel !== undefined && list.factorOrCoeffiecients[i].pgLabel !== undefined) {
							if (pgLabel.label === list.factorOrCoeffiecients[i].pgLabel.label) {
								if (selectedPgLabels.length == 0) {
									SelectedPgLabelsService.setSelectedPgLabelsForFCoff(list.factorOrCoeffiecients[i].pgLabel.label, $scope.eStandard.selected.entityId);
								} else {
									selectedPgLabels.forEach(function(pgl) {
										if (pgl.label === pgLabel.label) {
											flag = false;
										} else {
											flag = true;
										}
									});

									if (flag) {
										SelectedPgLabelsService.setSelectedPgLabelsForFCoff(list.factorOrCoeffiecients[i].pgLabel.label, $scope.eStandard.selected.entityId);

									} else {

									}
								}

							}

						}

					}
					if ($scope.factorsActive) {
						$scope.setModifiedFCFlag(list);

					} else {
						$scope.setModifiedPGFlag(list);

					}

				}

				$scope.unSetPGLabelForFCoff = function(pgLabel) {
					var selectedPgLabels = SelectedPgLabelsService.getSelectedPgLabelsForFCoff();
					for (var i = 0; i <= selectedPgLabels.length; i++) {
						if (selectedPgLabels[i] !== undefined && selectedPgLabels[i] !== null) {
							if (selectedPgLabels[i].id === $scope.eStandard.selected.entityId) {
								if (selectedPgLabels[i].label === pgLabel) {
									selectedPgLabels.splice(i, 1);
									SelectedPgLabelsService.updateSelectedPgLabelsForFCoff(selectedPgLabels);
								}
							}
						}
					}
				}

				$scope.initializeFCLists = function(emsId) {
					FCListService.FCListResource.getEsDependentLists({
						emsId : emsId
					}, function(response) {

						$scope.factorsList = response;
						$scope.listsAvail = true;
						// Assignment for Cancel Button
						$scope.factorsListCopy = angular.copy($scope.factorsList);

						FCListService.FCListResource.getUnUsedPGLabelsForfactorOrCoeff({
							entityId : $scope.emissionStandard.entityId
						}, function(success) {

							$scope.fcPgLabelList = success;
						}, function(error) {

						});

					}, function(error) {

						NotificationService.notify(cultureService.localize('cop.testCondition.message.fetchError'));
					});
				};
				$scope.fetchAllLists = function() {
					VTService.VehicleTechnologiesResource.getAllVTechnologiesForES({
						emsId : $scope.emissionStandard.entityId
					}, function(response) {
						$scope.vehicleTList = response;

					},

					function(error) {

					});

					FuelITypeService.FuelInjectionTypesResource.getAllFITypesForES({
						emsId : $scope.emissionStandard.entityId
					}, function(response) {
						$scope.fuelITList = response;

					},

					function(error) {

					});
					CategoryCommonService.CategoryResource.getAllCategoriesForES({
						emsId : $scope.emissionStandard.entityId
					}, function(response) {
						$scope.categoryList = response;
						$scope.classList = [];
						for (var i = 0; i < $scope.categoryList.length; i++) {
							var claszList = $scope.categoryList[i].claszRepresentation;
							for (var j = 0; j < claszList.length; j++) {

								for (var k = 0; k < $scope.classList.length; k++) {
									if (claszList[j].label == $scope.classList[k].label) {
										$scope.classList.splice(k, 1);
									}
								}

								$scope.classList.push(claszList[j]);
							}

						}

					},

					function(error) {

					});
					FuelCommonService.FuelResource.getAllFuelsForES({
						emsId : $scope.emissionStandard.entityId
					}, function(response) {
						$scope.fuelList = response;

					},

					function(error) {

					});
					FCAppTypeService.FCAppResource.getAllFCApplicationTypes(function(success) {
						$scope.applicationTypes = success;
					}, function(error) {

					});
					PGLabelService.PGLabelResource.getAllUnUsedPGLables({
						entityId : $scope.emissionStandard.entityId
					}, function(success) {
						$scope.pgLabellist = success;
					}, function(error) {

					});
				};

				// this method displays a modal while adding list
				$scope.showAddFCListModal = function() {

					$('#addFCListModal').modal('show');

				};
				$scope.addFactorList = function() {

					if ($scope.fcListlabel !== undefined && $scope.fcListlabel !== null && $scope.fcListlabel.length > 0) {

						var duplicatcheck = true;
						for (var i = 0; i < $scope.factorsList.length; i++) {
							if ($scope.fcListlabel.toUpperCase() === ($scope.factorsList[i]['label']).toUpperCase()) {
								duplicatcheck = false;
								break;
							}
						}
						if (duplicatcheck) {

							FCListService.FCListResource.checkFCApplicationTypes({
								entityId : $scope.eStandard.selected.entityId,
								emsId : $scope.fcListlabel
							}, function(response) {

								$scope.factorsList.push({
									'label' : $scope.fcListlabel,
									'description' : $scope.fcListDescription,
									factorOrCoeffiecients : [],
									modifiedFlag : "INSERT",
									emissionStandard : $scope.emissionStandard,
									fcApplicationTypes : [],
									fuels : [],
									categories : [],
									fuelInjectionTypes : [],
									vehicleTechnologySet : [],
									classes : []
								});
								$scope.fcListlabel = "";
								$scope.fcListDescription = "";
							}, function(error) {
								NotificationService.notify(cultureService.localize('cop.emissionStandard.message.duplicate'));
							});
						} else {
							NotificationService.notify(cultureService.localize('cop.emissionStandard.message.duplicate'));
						}

					} else
						NotificationService.notify(cultureService.localize('cop.common.message.mandatoryFields'));
				};

				$scope.showFactorModal = function(list, index) {
					$scope.selectedList = list;
					$scope.selectedDataItemIndex = index;
					$('#deleteDatalModal').modal('show');
				};

				$scope.showDeleteFactorModal = function(list, index, label) {
					$scope.selectedFCList = list;
					$scope.selectedFactorIndex = index;
					$scope.selectedFCLabel = label;
					$scope.unSetPGLabelForFCoff(label);
					$('#deleteFactorlModal').modal('show');
				};

				$scope.removeFactor = function() {
					$scope.selectedFCList.modifiedFlag = "UPDATE";
					$scope.selectedFCList.factorOrCoeffiecients[$scope.selectedFactorIndex]["isDeleted"] = "TRUE";

				};
				$scope.showDeleteFCListModal = function(fclist, label, index) {
					$scope.indexFCToBeDeleted = index;
					$scope.selectedFCList = fclist;
					$scope.deleteListFCLabel = label;
					var array=[];
				SelectedPgLabelsService.updateSelectedPgLabelsForFCoff(array);
					$('#ConfirmDeleteFCModal').modal('show');

				}
				// This method removes list from display
				$scope.removeFactorList = function() {
					$scope.factorsList[$scope.indexFCToBeDeleted]["modifiedFlag"] = "DELETE";
					$scope.tempList.push($scope.factorsList[$scope.indexFCToBeDeleted]);
					$scope.factorsList.splice($scope.indexFCToBeDeleted, 1);
				};

				$scope.addFactor = function(list) {

					$scope.selectedFCList = list;
					if ($scope.selectedFCList.factorOrCoeffiecients !== null)
						$scope.selectedFCList.factorOrCoeffiecients.push({

						});
					else {
						$scope.selectedFCList.factorOrCoeffiecients = [];
						$scope.selectedFCList.factorOrCoeffiecients.push({

						});

					}

				};
				$scope.getClass = function(condition) {
					NotificationService.notify("here");
					if (condition.isDeleted === "TRUE")
						return "label label-default";
					return "label label-danger";
					if (!$scope.$$phase) {
						$scope.$apply();
					}
				};
				// sets modified flag in TCL
				$scope.setModifiedFCFlag = function(list) {
					$scope.selectedFCList = list;
					if ($scope.selectedFCList.entityId !== null && $scope.selectedFCList.entityId !== 0 && $scope.selectedFCList.entityId !== undefined)
						$scope.selectedFCList.modifiedFlag = "UPDATE";
					else
						$scope.selectedFCList.modifiedFlag = "INSERT";
				};
				// shows modal while saving TCL
				$scope.showSaveFCConfirmation = function(isValidated) {
					$scope.fcList = [];
					$scope.mandatoryCheckFCPassed = true;
					for (var i = 0; i < $scope.factorsList.length; i++) {
						var factor = $scope.factorsList[i];

						if (factor.fcApplicationTypes.length === 0 || factor.fuels.length === 0 || factor.categories.length === 0 || factor.classes.length === 0 || factor.fuelInjectionTypes.length == 0 || factor.vehicleTechnologySet.length == 0) {
							$scope.mandatoryCheckFCPassed = false;

						} else {
							var factorOrCoeffList = factor.factorOrCoeffiecients;
							for (var fcCount = 0; fcCount < factorOrCoeffList.length; fcCount++) {
								var factorOrCoeff = factorOrCoeffList[fcCount];
								if (factorOrCoeff.pgLabel === undefined || factorOrCoeff.defaultValue === undefined || isNaN(factorOrCoeff.defaultValue) || factorOrCoeff.fcLabel === undefined) {
									$scope.mandatoryCheckFCPassed = false;
								} else if (factorOrCoeff.fcLabel !== undefined && factorOrCoeff.fcLabel !== null && ((factorOrCoeff.fcLabel.label === undefined || factorOrCoeff.fcLabel.label === null))) {
									$scope.mandatoryCheckFCPassed = false;
								}
							}

						}

						/*
						 * if (!mandatoryCheckPassed) { NotificationService.notify("Please fill all the mandatory fields before saving"); return; }
						 */
						$scope.fcList.push($scope.factorsList[i]);

					}

					for (var i = 0; i < $scope.tempList.length; i++) {

						$scope.fcList.push($scope.tempList[i]);

					}
					$scope.listFCModified = false;
					if ($scope.fcList == null)
						return;
					for (var j = 0; j < $scope.fcList.length; j++) {
						if ($scope.fcList[j].modifiedFlag != null && $scope.fcList[j].modifiedFlag !== "") {
							$scope.listFCModified = true;
							break;
						}
					}
					if (isValidated) {
						if (!$scope.mandatoryCheckFCPassed)
							$scope.showMissingFields();

						else {
							if ($scope.listFCModified) {

								$('#ConfirmSaveFCLModal').modal('show');
								var array=[];
								SelectedPgLabelsService.updateSelectedPgLabelsForFCoff(array);
							} else {
								$scope.errorMessage = cultureService.localize('cop.common.message.noModifications');
								$('#errorDisplayModal').modal('show');
							}

						}
					}
					if (authentication && (!isValidated && $scope.listFCModified)) {
						$('#FactureModificationModal').modal('show');
					}
					
					
				
				};
				$scope.showMissingFields = function() {
					$scope.message = [];
					var factorList = $scope.fcList;
					for (var i = 0; i < factorList.length; i++) {
						var errorList = $scope.validate(factorList[i]);
						if (errorList !== null && errorList.length !== 0) {
							var obj = {
								label : factorList[i].label,
								errors : errorList
							};
							$scope.message.push(obj);

						}

					}
					if ($scope.message != null)
						$('#validationErrorModal').modal('show');
				};
				$scope.saveFactureModifications = function() {
				/*	var array=[];
					SelectedPgLabelsService.updateSelectedPgLabelsForFCoff(array);*/
					$scope.saveFCLChanges();
				};
				$scope.removeFactureModifications = function() {
					var array=[];
					SelectedPgLabelsService.updateSelectedPgLabelsForFCoff(array);
					$scope.cancelModifiedESFCChanges();
					$('#FactureModificationModal').modal('hide');

				};
				// Changes on Emission FC Cancel Button
				$scope.CancelConfirmationESFC = function() {
					$scope.dataNotChanged = angular.equals($scope.factorsList, $scope.factorsListCopy);
					if (!$scope.dataNotChanged) {
						$('#ConfirmCancelESFCModal').modal('show');
					}
				};
				$scope.cancelModifiedESFCChanges = function() {
					$scope.factorsList = angular.copy($scope.factorsListCopy);
				};

				// End of Changes on Cancel Button
				/**
				 * This method saves all modifications (INSERT|UPDATE|DELETE)
				 */
				$scope.saveFCLChanges = function() {

					$scope.tempList = [];

					if ($scope.emissionStandard.status.label.toLowerCase() === "valid" && $scope.listFCModified) {
						$scope.newVersion4 = true;
					}
					for (var i = 0; i < $scope.fcList.length; i++) {

						if ($scope.fcList[i] !== undefined)
							for (var j = 0; j <= $scope.fcList[i].factorOrCoeffiecients.length; j++) {
								if ($scope.fcList[i].factorOrCoeffiecients[j] !== undefined && $scope.fcList[i].factorOrCoeffiecients[j].pgLabel !== undefined) {

									if ($scope.fcList[i].factorOrCoeffiecients[j].isDeleted === "TRUE") {

										$scope.fcList[i].factorOrCoeffiecients.splice(j, 1);
									}

								}
							}

					}

					var objectToSend = {
						'fcListRepresentation' : $scope.fcList,
						'changeEmsVersion' : $scope.newVersion4
					};
					if ($scope.mandatoryCheckFCPassed) {

						FCListService.FCListResource.savefactorCoefficientListChanges(objectToSend, function(success) {
							$scope.factorsList = success;
							NotificationService.notify(cultureService.localize('cop.testCondition.message.successMessage'));
							if ($scope.factorsList.length > 0) {
								$scope.newVersion4 = false;
								var newEms = $scope.factorsList[0]["emissionStandard"];
								$scope.emsList[$scope.emsList.length] = newEms;
								$scope.eStandard.selected = $scope.emsList[$scope.emsList.length - 1];
								$scope.setEms(newEms);
							} else
								$scope.setEms($scope.emissionStandard);

						}, function(error) {
							$scope.newVersion4 = false;
							NotificationService.notify(cultureService.localize('cop.testCondition.message.errorMessage'));
						});
					}
					;
					$scope.listFCModified = false;
				}
				$scope.validate = function(factor) {
					var messageArray = [];
					if (factor.label == null)
						messageArray.push(cultureService.localize('cop.factor.label.required'));
					if (factor.fcApplicationTypes === undefined || factor.fcApplicationTypes.length === 0)
						messageArray.push(cultureService.localize('cop.factor.ApplicationType.required'));
					if (factor.fuels === undefined || factor.fuels.length === 0)
						messageArray.push(cultureService.localize('cop.factor.fuel.required'));
					if (factor.categories === undefined || factor.categories.length === 0)
						messageArray.push(cultureService.localize('cop.factor.catagories.required'));
					if (factor.classes === undefined || factor.classes.length === 0)
						messageArray.push(cultureService.localize('cop.factor.classes.required'));
					if (factor.vehicleTechnologySet === undefined || factor.vehicleTechnologySet.length === 0)
						messageArray.push(cultureService.localize('cop.factor.vehicalTechnology.required'));
					if (factor.fuelInjectionTypes === undefined || factor.fuelInjectionTypes.length === 0)
						messageArray.push(cultureService.localize('cop.factor.fuelInjuction.required'));
					if (factor.factorOrCoeffiecients.length > 0) {
						{
							for (var j = 0; j < factor.factorOrCoeffiecients.length; j++) {
								var factorOrCoeffiecients = factor.factorOrCoeffiecients[j];
								if (factorOrCoeffiecients.pgLabel === undefined || factorOrCoeffiecients.pgLabel.label === undefined || factorOrCoeffiecients.pgLabel.label === null)
									messageArray.push(cultureService.localize('cop.factor.Pollutant.required'));
								if (factorOrCoeffiecients.fcLabel === undefined || factorOrCoeffiecients.fcLabel.label === undefined || factorOrCoeffiecients.fcLabel.label === null)
									if (factorOrCoeffiecients.pgLabel !== undefined)
										messageArray.push(factorOrCoeffiecients.pgLabel.label + " : " + cultureService.localize('cop.factor.factorlabel.required'));
								if (factorOrCoeffiecients.defaultValue === undefined || factorOrCoeffiecients.defaultValue === null)
									if (factorOrCoeffiecients.pgLabel !== undefined)
										messageArray.push(factorOrCoeffiecients.pgLabel.label + " : " + cultureService.localize('cop.factor.defaultValue.required'));

							}
						}
						return messageArray;

					}
				}
				// -----------------------------END FACTOR OR COEFFIECIENT LIST-------------------------------------------------

				// ----------------------------START POLLUTANT OR GAS LIMIT LIST-----------------------------+-------------------
				$scope.minSymbolList = [
						">", ">="
				];
				$scope.maxSymbolList = [
						"<", "=<"
				];
				$scope.limitsList = [];
				$scope.initializePGLists = function(emsId) {
					PGListService.PGListResource.getEsDependentLists({
						emsId : emsId
					}, function(response) {

						$scope.limitsList = response;
						$scope.listsAvail = true;
						// Assignment for Cancel Button
						$scope.limitsListCopy = angular.copy($scope.limitsList);

					}, function(error) {

						NotificationService.notify(cultureService.localize('cop.testCondition.message.fetchError'));
					});
				};

				// this method displays a modal while adding list
				$scope.showAddPGListModal = function() {

					$('#addPGListModal').modal('show');

				};
				$scope.addLimitList = function() {

					if ($scope.pgListlabel !== undefined && $scope.pgListlabel !== null && $scope.pgListlabel.length > 0) {

						var duplicatcheck = true;
						for (var i = 0; i < $scope.limitsList.length; i++) {
							if ($scope.pgListlabel.toUpperCase() === ($scope.limitsList[i]['label']).toUpperCase()) {
								duplicatcheck = false;
								break;
							}
						}
						if (duplicatcheck) {
							PGListService.PGListResource.checkPGLists({
								entityId : $scope.eStandard.selected.entityId,
								emsId : $scope.pgListlabel
							}, function(response) {

								$scope.limitsList.push({
									'label' : $scope.pgListlabel,
									'description' : $scope.pgListDescription,
									pollutantGasLimit : [],
									modifiedFlag : "INSERT",
									emissionStandard : $scope.emissionStandard,
									fuels : [],
									categories : [],
									fuelInjectionTypes : [],
									vehicleTechnologySet : [],
									classes : []
								});
								$scope.pgListlabel = "";
								$scope.pgListDescription = "";
							}, function(error) {
								NotificationService.notify(cultureService.localize('cop.emissionStandard.message.duplicate'));
							});
						} else {
							NotificationService.notify(cultureService.localize('cop.emissionStandard.message.duplicate'));
						}
					} else
						NotificationService.notify(cultureService.localize('cop.common.message.mandatoryFields'));
					// $scope.factorsAvail = true;
				};

				$scope.showDeleteLimitModal = function(list, index, label) {

					if (label !== undefined) {

						$scope.unSetPGLabel(label.label);
					}
					$scope.selectedPGList = list;
					$scope.selectedLimitIndex = index;
					$scope.selectedPgLabel = label;
					$('#deletePollutantlModal').modal('show');
				};
				//
				$scope.removePollutant = function() {
					$scope.selectedPGList.modifiedFlag = "UPDATE";
					$scope.selectedPGList.pollutantGasLimit[$scope.selectedLimitIndex]["isDeleted"] = "TRUE";

				};

				$scope.showDeletePGListModal = function(list, label, index) {

					$scope.indexPGToBeDeleted = index;
					$scope.selectedPGList = list;
					$scope.deleteListPGLabel = label;
					var array=[];
					SelectedPgLabelsService.updateSelectedPgLabels(array);
					$('#ConfirmDeletePGModal').modal('show');

				};

				// This method removes list from display
				$scope.removeLimitList = function(index) {
					$scope.limitsList[$scope.indexPGToBeDeleted]["modifiedFlag"] = "DELETE";
					$scope.tempList.push($scope.limitsList[$scope.indexPGToBeDeleted]);
					$scope.limitsList.splice($scope.indexPGToBeDeleted, 1);
				};

				$scope.addPollutant = function(list) {
					$scope.selectedPGList = list;
					if ($scope.selectedPGList.pollutantGasLimit !== undefined && $scope.selectedPGList.pollutantGasLimit !== null) {

						$scope.selectedPGList.pollutantGasLimit.push({

						});
					} else {
						$scope.selectedPGList.pollutantGasLimit = [];
						$scope.selectedPGList.pollutantGasLimit.push({

						});
					}
				};
				$scope.getClass = function(condition) {
					NotificationService.notify("here");
					if (condition.isDeleted === "TRUE")
						return "label label-default";
					return "label label-danger";
					if (!$scope.$$phase) {
						$scope.$apply();
					}
				};
				// sets modified flag in TCL
				$scope.setModifiedPGFlag = function(list) {
					$scope.selectedPGList = list;
					if ($scope.selectedPGList.entityId !== null && $scope.selectedPGList.entityId !== 0 && $scope.selectedPGList.entityId !== undefined)
						$scope.selectedPGList.modifiedFlag = "UPDATE";
					else
						$scope.selectedPGList.modifiedFlag = "INSERT";
				};
				// shows modal while saving TCL
				$scope.showSavePGConfirmation = function(isValidated) {
					$scope.listPGModified = false;
					$scope.pgList = [];
					for (var i = 0; i < $scope.limitsList.length; i++) {
						$scope.pgList.push($scope.limitsList[i]);
					}

					for (var i = 0; i < $scope.tempList.length; i++) {
						$scope.pgList.push($scope.tempList[i]);
					}
					if ($scope.pgList == null)
						return;
					for (var j = 0; j < $scope.pgList.length; j++) {
						if ($scope.pgList[j].modifiedFlag != null && $scope.pgList[j].modifiedFlag !== "") {
							$scope.listPGModified = true;
							break;
						}
					}
					if (isValidated) {
						if ($scope.listPGModified)
							$('#ConfirmSavePGLModal').modal('show');

						else {
							$scope.errorMessage = cultureService.localize('cop.common.message.noModifications');
							$('#errorDisplayModal').modal('show');

						}
					}
					if (authentication && (!isValidated && $scope.listPGModified))
						$('#LimitModificationModal').modal('show');
				};

				$scope.saveLimiteModifications = function() {
					var array=[];
					SelectedPgLabelsService.updateSelectedPgLabels(array);
					$scope.savePGLChanges();
				};
				$scope.removeLimiteModifications = function() {
					var array=[];
					SelectedPgLabelsService.updateSelectedPgLabels(array);
					$scope.cancelModifiedESPGChanges();
					$('#LimitModificationModal').modal('hide');
				};

				// Changes on Emission PG Cancel Button
				$scope.CancelConfirmationESPG = function() {
					$scope.dataNotChanged = angular.equals($scope.limitsList, $scope.limitsListCopy);
					if ($scope.dataNotChanged === false) {
						$('#ConfirmCancelESPGModal').modal('show');
					}
				};
				$scope.cancelModifiedESPGChanges = function() {
					$scope.limitsList = angular.copy($scope.limitsListCopy);
				};

				// End of Changes on Cancel Button

				$scope.cancelCreation = function(label, description, modalName) {
					$scope.label = label;
					$scope.modaName = modalName;
					if (label !== undefined && label !== null && label.trim() !== '' || description !== undefined && description !== null && description.trim() !== '')
						$('#cancelCreationModal').modal('show');
					else
						$('#' + $scope.modaName).modal('hide');
				};
				$scope.continueCreation = function() {

					$('#' + $scope.modaName).modal('hide');

				}

				/**
				 * This method saves all modifications (INSERT|UPDATE|DELETE)
				 */
				$scope.savePGLChanges = function() {
					var mandatoryCheckComplete = true;

					for (var i = 0; i < $scope.pgList.length; i++) {
						if ($scope.pgList[i].fuels.lengh === 0 || $scope.pgList[i].categories.length === 0 || $scope.pgList[i].classes.length === 0 || $scope.pgList[i].fuelInjectionTypes.length === 0 || $scope.pgList[i].vehicleTechnologySet.length === 0) {
							mandatoryCheckComplete = false;
						}

						if ($scope.pgList[i] !== undefined)
							for (var j = 0; j <= $scope.pgList[i].pollutantGasLimit.length; j++) {

								if ($scope.pgList[i].pollutantGasLimit[j] === undefined) {

								}

								if ($scope.pgList[i].pollutantGasLimit[j] !== undefined && $scope.pgList[i].pollutantGasLimit[j].pgLabel === undefined) {
									mandatoryCheckComplete = false;
								}

								if ($scope.pgList[i].pollutantGasLimit[j] !== undefined && $scope.pgList[i].pollutantGasLimit[j].pgLabel !== undefined) {

									if ($scope.pgList[i].pollutantGasLimit[j].isDeleted === "TRUE") {

										$scope.pgList[i].pollutantGasLimit.splice(j, 1);

									}

								}

							}

					}

					if (mandatoryCheckComplete) {

						$scope.tempList = [];
						$scope.listPGModified = false;
						for (var j = 0; j < $scope.pgList.length; j++) {
							if ($scope.pgList[j].modifiedFlag != null && $scope.pgList[j].modifiedFlag !== "") {
								$scope.listPGModified = true;
								break;
							}
						}
						if ($scope.emissionStandard.status.label.toLowerCase() === "valid" && $scope.listPGModified === true)
							$scope.newVersion3 = true;
						var objectToSend = {
							'pgList' : $scope.pgList,
							'changeEmsVersion' : $scope.newVersion3
						};
						PGListService.PGListResource.savePollutantGasListChanges(objectToSend, function(success) {
							$scope.limitsList = success;
							NotificationService.notify(cultureService.localize('cop.testCondition.message.successMessage'));
							$scope.newVersion3 = false;
							if ($scope.limitsList.length > 0) {

								var newEms = $scope.limitsList[0]["emissionStandard"];
								$scope.emsList[$scope.emsList.length] = newEms;
								$scope.eStandard.selected = $scope.emsList[$scope.emsList.length - 1];
								$scope.setEms(newEms);
								$scope.showLimits();

							} else {
								$scope.setEms($scope.emissionStandard);

							}
						}, function(error) {
							$scope.newVersion3 = false;
							NotificationService.notify(cultureService.localize('cop.testCondition.message.errorMessage'));
						});

					} else {
						NotificationService.notify(cultureService.localize('cop.common.message.mandatoryFields'));
					}
					$scope.listPGModified = false;

				};
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
				$scope.slideToggle = function() {
					$("#wrapper").toggleClass("toggled");
					$("#menu-toggle").toggleClass('fa-chevron-right');
					$("#content-middle").toggleClass("hidden-xs");
					$(".slide-resize").toggleClass("col-lg-10");
				};

				$("#emsDetail").css('background-color', '#DBDBDB', 'color', '#fff');

			}
	]);

	esDetailsModule.directive('editlabeles', function() {
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

	esDetailsModule.directive('ngEnter', function() {
		return function(scope, element, attrs) {

			element.bind('keypress', function(e) {
				if (e.charCode === 13 || e.keyCode === 13) {
					scope.$apply(attrs.ngEnter);
				}
			});

		};
	});

	esDetailsModule.filter('decimal2comma', [
		function() {
			return function(input) {
				var ret = (input) ? input.toString().replace(".", ",") : null;
				if (ret) {
					var decArr = ret.split(",");

					if (decArr.length > 1) {
						var dec = decArr[1].length;
						if (dec === 1) {
							ret += "0";
						}
					}// this is to show prices like 12,20 and not 12,2
				}
				return ret;
			};
		}
	]);

	esDetailsModule.filter('comma2decimal', [
		function() {
			return function(input) {
				var ret = (input) ? input.toString().trim().replace(",", ".") : null;
				return parseFloat(ret);
			};
		}
	]);

	esDetailsModule.directive('frdecimal', [
			'$filter', function($filter) {

				return {
					restrict : 'A',
					require : 'ngModel',
					link : function(scope, element, attrs, ngModelController) {

						ngModelController.$parsers.push(function(data) {
							data = $filter('comma2decimal')(data);
							return data;

						});

						ngModelController.$formatters.push(function(data) {
							data = $filter('decimal2comma')(data);
							return data;

						});
					}
				};
			}
	]);

	return {
		angularModules : [
			'esDetailsModule'
		]
	};
});
