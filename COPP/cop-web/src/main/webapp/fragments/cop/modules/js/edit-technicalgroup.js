define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {
	var EditTechnicalGroup = angular.module('EditTechnicalGroup', [
			'ngResource', 'copCommon', 'TechnicalGroup', 'EditSamplingApp', 'Settings', 'search', 'EditRegulationGroup', 'EditSamplingApp', 'RegulationGroup'
	]);
	EditTechnicalGroup.factory('TGService', [
			'$rootScope', function($rootScope) {

				var savedTechnicalGroup = {};

				return savedTechnicalGroup;
			}
	]);
	EditTechnicalGroup.factory('TvvService', [
			'$rootScope',

			function($rootScope) {

				var savedTVV = {};

				return savedTVV;
			}
	]);

	EditTechnicalGroup.factory('EditTechnicalGroupService', [

			'$resource', function($resource) {
				return {
					EditTechnicalGroupResource : $resource('TechnicalGroup/:path/:id', {

						path : '@path',
						id : '@id'

					},

					{
						'saveEditedTechnicalGroup' : {
							method : 'POST',
							params : {

								path : 'TechnicalGroup'

							},

						},
						'loadSamplingRule' : {

							method : 'GET',
							params : {

								path : 'SamplingRule'

							},
							isArray : true
						},
						'setTvvWorstCase' : {
							method : 'POST',
							params : {

								path : 'TvvWorstCase'

							},
							isArray : true

						},
						'loadSelectedTvv' : {

							method : 'GET',
							params : {
								path : 'TVVs'
							},
							isArray : true
						},
						'loadRegulationGroup' : {

							method : 'GET',
							params : {
								path : 'RegulationGroup'
							},
							isArray : true

						},
						'deleteTvv':{
						    method : 'POST',
						    params : {
                                path : 'TvvDelete'
                            },
                            isArray : true
						    
						}

					}

					)
				};

			}

	]);

	EditTechnicalGroup.controller('EditTechnicalGroupController', [

			'$scope', 'CultureService', 'TGService', 'TechnicalGroupService', 'EditTechnicalGroupService', 'GetStatusService', 'EditTGService', '$location', 'TvvService', 'NotificationService', 'RGService', 'GetSamplingService', 'RegulationGroupService', 'ManageTvvService','AuthorizationService', function($scope, CultureService, TGService, TechnicalGroupService, EditTechnicalGroupService, GetStatusService, EditTGService, $location, TvvService, NotificationService, RGService, GetSamplingService, RegulationGroupService, ManageTvvService, authorizationService) {

				
				$scope.authorization = function(role) {
					return authorizationService.hasRole('seed-w20', role);
				};
				
				$scope.loadTechnicalGroup=function(entityId)
                {
                	TechnicalGroupService.TechnicalGroupResource.getTechnicalGroup({entityId:$scope.technicalGroup.entityId},function(response)
							{
						      $scope.technicalGroupCopy=response;
						    
						    	  
							},
							function(error) {

								// NotificationService.notify(CultureService.localize("cop.editTechnicalGroup.delete.errorMessage"));

							}
							);
                };
				$scope.technicalGroup = TGService.savedTechnicalGroup;
				
				

				if (TGService.savedTechnicalGroup === undefined) {
					var url = "/cop/SearchMain";
					$location.url(url);
					return;
				} else {
					$scope.technicalGroup = TGService.savedTechnicalGroup;
					$scope.loadTechnicalGroup($scope.technicalGroup.entityId);
					
				
				}
                
				$scope.loadRegulationGroups = function() {

					RegulationGroupService.RegulationGroupResource.getAllRegulationGroups(function(response) {
						$scope.regulationGroupList = response;
					}, function(error) {
						NotificationService.notify(CultureService.localize('cop.tvv.message.initializeError'));
					});

				};
				$scope.loadRegulationGroups();

				$scope.getTechnicalGroup = function(entityId) {
					TechnicalGroupService.TechnicalGroupResource.getTechnicalGroup({
						entityId : entityId
					}, function(response) {

						$scope.technicalGroup = response;
					

						// changes made for cancel
						$scope.technicalGroupCopy = angular.copy($scope.technicalGroup);

						// NotificationService.notify(CultureService.localize("cop.editTechnicalGroup.delete.successMessage"));

					}, function(error) {

						// NotificationService.notify(CultureService.localize("cop.editTechnicalGroup.delete.errorMessage"));

					});
				};

				$scope.loadSamplingRules = function() {
					

					GetSamplingService.getUniqueSamplingLabels(function(response) {

						$scope.samplingDropdown = response;

						if (!$scope.technicalGroup.addTvvForTg) {
							$scope.getTechnicalGroup($scope.technicalGroup.entityId);
						}
						$scope.technicalGroup.addTvvForTg = false;
						$scope.populateTGData();

					}, function(error) {
						NotificationService.notify(CultureService.localize("cop.editTechnicalGroup.sampling.loaderrorMessage"));
					});

				};

				$scope.loadStatusForTG = function() {
					TechnicalGroupService.TechnicalGroupResource.getStatusForTechnicalGroup(function(response) {
						$scope.technicalGroupStatus = response;
						$scope.loadSamplingRules();
					});
				};

				$scope.loadSelectedTvv=function(technicalGroup)
				{
				    EditTechnicalGroupService.EditTechnicalGroupResource.loadSelectedTvv({id:technicalGroup.entityId},function(response){
				        technicalGroup.tvvRepresentations=response;
				    });
				};
				$scope.populateTGData = function() {
					
					for ( var i = 0; i < $scope.samplingDropdown.length; i++) {
						if ($scope.samplingDropdown[i].label === $scope.technicalGroup.samplingLabel) {
							$scope.setSelectedSamplingRule($scope.technicalGroup.samplingLabel, $scope.samplingDropdown[i].descriptions);

						}
					}
				};

				$scope.actions = [
					CultureService.localize("cop.actions.options.delete")
				];

				const
				supprimer = "Supprimer";
				$scope.samplingDropdown = [];
				$scope.tvvselectList = [];
				$scope.selectedTvvlist = [];
				$scope.loadStatusForTG();

				$scope.selectedAction = undefined;
				$scope.takeAction = function() {
					// $scope.selectedAction.selected = undefined;
					$scope.selectedAction = false;
					if ($scope.technicalGroup != undefined) {
						if ($scope.technicalGroup.label != "" || $scope.technicalGroup.version != "" || $scope.technicalGroup.techgroupstatus.label != "") {
							$('#editTechnicalGroupModal').modal('show');
						}
					}
				};
				$scope.deleteTechGroup = function() {
					TechnicalGroupService.TechnicalGroupResource.deleteTechnicalGroup({
						entityId : $scope.technicalGroup.entityId
					}, function(response) {

						$('#editTechnicalGroupModal').modal('hide');
						$scope.technicalGroup.label = "";
						$scope.technicalGroup.version = "";
						$scope.technicalGroup.techgroupstatus.label = "";
						NotificationService.notify(CultureService.localize("cop.editTechnicalGroup.delete.successMessage"));

						var url = "/cop/SearchMain";
						$location.url(url);
						return;

					}, function(error) {

						NotificationService.notify(CultureService.localize("cop.editTechnicalGroup.delete.errorMessage"));

					});
				};

				$scope.editRegulationGroup = function() {
					RGService.savedRegulationGroup = $scope.technicalGroup.regulationGroupRepresent;
					var url = "/cop/EditRegulationGroup";
					$location.url(url);

				};

				/*
				 * $scope.selectedStatus = $scope.technicalGroup.techgroupstatus; $scope.setSelectedStatus = function(selectedStatus) { $scope.selectedStatus = selectedStatus; }
				 */

				$scope.showEditStatusVersionModal = function() {
					$scope.selectedStatus = $scope.technicalGroup.techgroupstatus;
					$('#editTechnicalVersionStatusModal').modal('show');
				};

				$scope.setSelectedVersionandStatus = function() {
					$('#editTechnicalVersionStatusModal').modal('hide');
					$scope.selectedStatus = $scope.technicalGroup.techgroupstatus;
					if ($scope.isVersionCreated) {

						TechnicalGroupService.TechnicalGroupResource.getMaxVersion({
							"label" : $scope.technicalGroup.label
						}, function(response) {

							$scope.technicalGroup.version = response.version;
							$scope.technicalGroup.newVersion = true;
						});
						$scope.isVersionCreated = false;
					}
					;
				};

				$scope.cancelVersionAndStatus = function() {
					$('#editTechnicalVersionStatusModal').modal('hide');
					$scope.technicalGroup.techgroupstatus = $scope.selectedStatus;

				};

				$scope.setSelectedSamplingRule = function(samplingRule, descriptions) {

					$scope.technicalGroup.samplingLabel = samplingRule;
					$scope.descriptions = descriptions;

				};
				$scope.addTvv = function() {
					$scope.technicalGroup.addTvvForTg = true;
					$scope.technicalGroup.showAddTvvButton = true;
					EditTGService.editedTechnicalGroup = $scope.technicalGroup;
					var url = "/cop/SearchMain";
					$location.url(url);
				};
				$scope.deleteTvv=function(tvvObj)
				{
				    EditTechnicalGroupService.EditTechnicalGroupResource.deleteTvv(tvvObj, function() {
                        NotificationService.notify(CultureService.localize("cop.editTechnicalGroup.delete.successMessage"));
                        $scope.loadSelectedTvv($scope.technicalGroup);
                       
                    }
				      
				    );
				};
				$scope.saveEditedTechnicalGroup = function() {
					if ($scope.technicalGroup.techgroupstatus !== undefined && $scope.technicalGroup.techgroupstatus.label === 'Valid') {
						if ($scope.technicalGroup.regulationGroupRepresent !== undefined && $scope.technicalGroup.regulationGroupRepresent != null && $scope.technicalGroup.regulationGroupRepresent.regulationgroupstatus !== undefined) {
							if ($scope.technicalGroup.regulationGroupRepresent.regulationgroupstatus.label !== 'Valid') {
								NotificationService.notify(CultureService.localize("cop.editTechnicalGroup.rg.status.error"));
								return;
							}
						}

					}

					EditTechnicalGroupService.EditTechnicalGroupResource.saveEditedTechnicalGroup($scope.technicalGroup, function(response) {

						$scope.technicalGroup = response;
						$scope.populateTGData();
						// changes made for cancel
						$scope.technicalGroupCopy = angular.copy($scope.technicalGroup);
						NotificationService.notify(CultureService.localize("cop.editTechnicalGroup.save.successMessage"));

					},

					function(error) {
						if (error.status == 412) {
							$scope.errorMessage = CultureService.localize("cop.editTechnicalGroup.worstcase.errorMessage");
						} else {
							$scope.errorMessage = CultureService.localize("cop.editTechnicalGroup.save.errorMessage");
						}
						$('#errorDisplayModal').modal('show');
					}

					);
				};

				$scope.selectWorstCaseModal = function() {
					$('#worstCaseModal').modal('show');
				};
				
				
				$scope.selectWorstCase = function(tvvObject) {
					// if($scope.tvvselectList.length!=0)
					// {
				    $scope.oldtvvRepresentation=$scope.technicalGroup.tvvRepresentations;
					var matchedEntityId = undefined;
				 $scope.oldWorstCaseList=[];
					for ( var i = 0; i < $scope.technicalGroup.tvvRepresentations.length; i++) {
					    var oldWorstCase=$scope.technicalGroup.tvvRepresentations[i].worstcaseSelected;
					    $scope.oldWorstCaseList.push(oldWorstCase);
						$('#card' + $scope.technicalGroup.tvvRepresentations[i].entityId).removeClass('custom-card-active');
						$scope.technicalGroup.tvvRepresentations[i].showWorstCaseicon = 0;
						if (tvvObject.entityId == $scope.technicalGroup.tvvRepresentations[i].entityId) {
							$scope.technicalGroup.tvvRepresentations[i].worstcaseSelected = !$scope.technicalGroup.tvvRepresentations[i].worstcaseSelected;
							var worstCaseMatched = $scope.technicalGroup.tvvRepresentations[i].worstcaseSelected;
							if (worstCaseMatched) {
								$scope.technicalGroup.tvvRepresentations[i].showWorstCaseicon = 1;
								matchedEntityId = tvvObject.entityId;
							}
							;

						}
						;
						 
						
					};
					
					

					if (matchedEntityId != undefined) {
						$('#card' + matchedEntityId).addClass('custom-card-active');
						tvvObject.showWorstCaseicon = 1;
					}
					;

				};
				
				$scope.cancelWorstCase=function()
                {
				    for ( var i = 0; i < $scope.technicalGroup.tvvRepresentations.length; i++) {
				        
				    if($scope.oldWorstCaseList !== undefined && $scope.oldWorstCaseList !== null) {
				        $scope.technicalGroup.tvvRepresentations[i].worstcaseSelected= $scope.oldWorstCaseList[i];
				    }
				    
				    }
                };

				$scope.settWorstCase = function() {
					$scope.technicalGroup.worstCaseset = true;
					$('#worstCaseModal').modal('hide');

				};

				// Changes on TG Cancel Button
				$scope.CancelOnConfirmationTG = function() {
				
					$scope.dataNotChanged = angular.equals($scope.technicalGroup, $scope.technicalGroupCopy);
					if (!$scope.dataNotChanged ) 
						{
						 $('#ConfirmCancelTGModal').modal('show');
						}
					

					};

			
				$scope.editTVV = function(tvvObj) {
					ManageTvvService.savedTvv = tvvObj;
					var url = "/cop/EditTvv";
					$location.url(url);
				};
				$scope.cancelModifiedTGChanges = function() {
					$scope.technicalGroup = $scope.technicalGroupCopy;
					$scope.populateTGData();
					if ($scope.technicalGroup.samplingLabel == undefined || $scope.technicalGroup.samplingLabel == null) {
						$scope.descriptions = "";
					}
					$scope.loadTechnicalGroup($scope.technicalGroup.entityId);
				};
				// End of Changes on Cancel Button

			}
	]);

	return {
		angularModules : [
			'EditTechnicalGroup'
		]
	};
});