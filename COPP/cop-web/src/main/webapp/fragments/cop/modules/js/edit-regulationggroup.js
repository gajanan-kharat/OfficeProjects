define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {
	var EditRegulationGroup = angular.module('EditRegulationGroup', [
			'ngResource', 'copCommon', 'GlobalBusinessReference', 'esDetailsModule', 'TechnicalGroup', 'RegulationGroup', 'search'
	]);
	EditRegulationGroup.factory('RGService', [
			'$rootScope', function($rootScope) {

				var savedRegulationGroup = {};

				return savedRegulationGroup;
			}
	]);
	EditRegulationGroup.factory('SelectedTGService', [

			'$rootScope', function($rootScope) {

				var selectedTGLIst = {};

				return selectedTGLIst;
			}
	]);

	EditRegulationGroup.factory('RGvaluedTestConditionService', [
			'$resource', function($resource) {

				return {
					ReValuedTestConditionResource : $resource('RegulationGroup/:path/:id', {

						path : '@path',
						id : '@id'

					}, {
						'loadRGValuedTCl' : {

							method : 'POST',
							params : {

								path : 'RegulationGroupValidTCL'

							},
							isArray : true
						},

					})
				};

			}
	]);
	
	

    EditRegulationGroup.factory('StatisticalCalRuleService', [
            '$resource', function($resource) {

                return {
                    StatisticalRuleResource : $resource('StatisticalCalculationRule/:path', {

                        path : '@path'
                        }, {
                        'getAllStatisticalCalcRules' : {

                            method : 'GET',
                            params : {

                                path :'StatisticalCalculation'

                            },
                            isArray : true
                        },

                    })
                };

            }
    ]);


	EditRegulationGroup.factory('WLTPService', [
			'$resource', function($resource) {

				return {
					WltpResource : $resource('WLTPVLowHighData/:path', {

						path : '@path'

					}, {
						'saveWLTP' : {

							method : 'POST',
							params : {

								path : 'WLTPVLowHighData'

							},

						},

					})
				};

			}
	]);
	EditRegulationGroup.factory('EditRgService', [
			'$resource', function($resource) {

				return {
					EditRegulationGroupResource : $resource('RegulationGroup/:path/:id', {

						path : '@path',
						id : '@id'

					}, {
						'saveEditedRegulationGroup' : {

							method : 'POST',
							params : {

								path : 'RegulationGroup'

							},

						},
						'loadSelectedTechGroup' : {

							method : 'GET',
							params : {

								path : 'TechnicalGroups'

							},
							isArray : true
						},
						'getMaxVersion' : {
							method : 'POST',
							params : {

								path : 'MaxVersion'

							}
						},
						'deleteTechnicalGroup' : {
                            method : 'POST',
                            params : {

                                path : 'RegulationGrp'

                            },
						isArray : true
                        }
					})
				};

			}
	]);

	EditRegulationGroup.controller("EditRegulationGroupController", [
			'$scope', 'TypeApprovalAreaService', 'EMSService', 'RGService', 'TechnicalGroupService', 'RegulationGroupService', 'EditRGService', '$location', 'SelectedTGService', 'EditRgService', 'WLTPService', 'RGvaluedTestConditionService', 'NotificationService', 'CultureService', 'TGService', 'AuthorizationService','StatisticalCalRuleService', function($scope, TypeApprovalAreaService, EMSService, RGService, TechnicalGroupService, RegulationGroupService, EditRGService, $location, SelectedTGService, EditRgService, WLTPService, RGvaluedTestConditionService, NotificationService, CultureService, TGService, authorizationService,StatisticalCalRuleService) {

				/*----------------------------------------Authorization----------------------------------------*/
				$scope.authorization = function(role) {
					return authorizationService.hasRole('seed-w20', role);
				};

				/*---------------------------------------------------------------------------------*/
				$scope.actions = [
					CultureService.localize("cop.actions.options.delete")
				];
				const
				supprimer = "Supprimer";
				$scope.regulationGroup = RGService.savedRegulationGroup;
				// changes made for cancel button
				$scope.regulationGroupCopy = angular.copy($scope.regulationGroup);

				$scope.editDefaultConditionValue = function(list) {

					list.editedValues = 0;
					for (var i = 0; i < list.rgValuedGenericTestConditionrepresent.length; i++) {
						if (list.rgValuedGenericTestConditionrepresent[i].defaultValue != null && list.rgValuedGenericTestConditionrepresent[i].defaultValue.length > 0) {

							list.editedValues = list.editedValues + 1;

						}
					}
				};

				$scope.tgList = SelectedTGService.selectedTGLIst;
				if (RGService.savedRegulationGroup == undefined) {
					var url = "/cop/SearchMain";
					$location.url(url);
					return;
				} else
					$scope.regulationGroup = RGService.savedRegulationGroup;

				$scope.initialize = function() {
					$scope.loadTypeAprovalArea();
					$scope.loadEmissionStandard();
					$scope.loadStatusForRG();
					$scope.loadSelectedTG();
					$scope.loadDefaultConditionValues();
					$scope.loadStatisticalCalRule();
					
					
				};

				$scope.loadTypeAprovalArea = function() {
					TypeApprovalAreaService.TypeApprovalAreaResource.getAllTypeApprovalAreaForRG(function(response) {
						$scope.typeApprovalArea = response;
						// changes made for cancel button
						$scope.typeApprovalAreaCopy = angular.copy($scope.typeApprovalArea);
					})
				};
				$scope.loadEmissionStandard = function() {
					EMSService.EmissionStandardResource.getAllEmissionStandardsForRG(function(response) {
						$scope.emissionStandard = response;
						// changes made for cancel button
						$scope.emissionStandardCopy = angular.copy($scope.emissionStandard);
					})
				};
				$scope.loadDefaultConditionValues=function()
				{
					if ($scope.regulationGroup.rgValuedEsDepTCL != null && $scope.regulationGroup.rgValuedEsDepTCL.length > 0) {
						for (var i = 0; i < $scope.regulationGroup.rgValuedEsDepTCL.length; i++)
							$scope.editDefaultConditionValue($scope.regulationGroup.rgValuedEsDepTCL[i]);
					}
				};
				
				$scope.loadStatisticalCalRule=function()
				{
				    StatisticalCalRuleService.StatisticalRuleResource.getAllStatisticalCalcRules(function(response){
				          
				        $scope.statisticalRule=response;
				        
				        $scope.statisticalRuleCopy=angular.copy( $scope.statisticalRule);
				        
				    })
				};
				$scope.showEditStatusVersionModal = function() {
					$('#editRegulationVersionStatusModal').modal('show');
				};

				$scope.setSelectedVersionandStatus = function() {

					$('#editRegulationVersionStatusModal').modal('hide');
					// $scope.regulationGroup.regulationgroupstatus = $scope.selectedStatus;
					if ($scope.isVersionCreated == true) {

						EditRgService.EditRegulationGroupResource.getMaxVersion($scope.regulationGroup, function(response) {

							$scope.regulationGroup.version = response.version;
							$scope.regulationGroup.newRgVersion = true;

						});
						$scope.isVersionCreated = false;

					}
				};
				$scope.loadStatusForRG = function() {
					TechnicalGroupService.TechnicalGroupResource.getStatusForTechnicalGroup(function(response) {
						$scope.regulationGroupStatus = response
					});
				};
				$scope.setSelectedStatus = function(selectedStatus) {

					$scope.selectedStatus = selectedStatus;
				};
				$scope.deleteRegulationGroup = function() {
					RegulationGroupService.RegulationGroupResource.deleteRegulationGroup({
						entityId : $scope.regulationGroup.entityId
					}, function(response) {
						NotificationService.notify(CultureService.localize('cop.regulationGroup.delete.successmessage'));
						$('#editRegulationVersionStatusModal').modal('hide');
						$scope.regulationGroup.version = "";
						$scope.regulationGroup.label = "";
						$scope.regulationGroup.regulationgroupstatus.label = "";
						var url = "/cop/SearchMain";
						$location.url(url);
					});
				};
				$scope.takeAction = function() {
					$scope.selectedAction = false;
					// if ($scope.selectedAction == supprimer) {
					if ($scope.regulationGroup != undefined) {
						if ($scope.regulationGroup.label != "" || $scope.regulationGroup.version != "" || $scope.regulationGroup.regulationgroupstatus.label != "") {
							$('#editRegulationGroupModal').modal('show');
						}
					}
					// }
				};
				$scope.loadSelectedTG = function() {
					if ($scope.regulationGroup != null && $scope.regulationGroup.entityId != null) {
						EditRgService.EditRegulationGroupResource.loadSelectedTechGroup({
							id : $scope.regulationGroup.entityId
						}, function(response) {
							$scope.tgList = response;
							// changes made for cancel button
							$scope.tgListCopy = angular.copy($scope.tgList);
						});
					} else {
						var url = "/cop/SearchMain";
						$location.url(url);
					}

				}
				$scope.addTG = function() {

					$scope.regulationGroup.addTgForRg = true;
					$scope.regulationGroup.showAddTg = true;
					EditRGService.editRegulationGroup = $scope.regulationGroup;

					var url = "/cop/SearchMain";
					$location.url(url);
					setTimeout(function() {
						$('#envelopeTab').addClass('active');
						$('#tvvTab').removeClass('active');
						$('#envelope').addClass('active');
						$('#tvv').removeClass('active');

						// $('#tvvTab').addClass('disabled');
						//						
						// $("#tabTvvList").removeAttr('data-toggle');
						// $("#tabTvvList").removeAttr('data-ng-click');
						// $("#tabTvvList").removeAttr('href');
						//						
						// $("#tabFamList").removeAttr('data-toggle');
						// $("#tabFamList").removeAttr('data-ng-click');
						// $("#tabFamList").removeAttr('href');
						// $("#tabFamList").removeAttr('data-target');

					}, 200);
				};
				$scope.setTypeApprovalArea = function(typeApprovalobj) {

					$scope.regulationGroup.typeApprovalArea = typeApprovalobj;
				};
				$scope.setEmissionStandard = function(emissionObj) {

					$scope.regulationGroup.emissionStandardforRg = emissionObj;
					// $scope.loadRegulationValuedTCL(emissionObj.entityId);
				};
				$scope.editRGDefaultValue = function(RGEsdTCList, index) {

				};
				$scope.setStatisticalRule=function(statisticalObject)
				{
				    $scope.regulationGroup.statisticalRuleRepresentation=statisticalObject;
				};
				
				
				$scope.validate=function()
				{
				   
				    if($scope.regulationGroup.regulationgroupstatus.label.toUpperCase()==="VALID" && $scope.regulationGroup.statisticalRuleRepresentation!==undefined && $scope.regulationGroup.statisticalRuleRepresentation!==null && $scope.regulationGroup.oldStatisticalObject!==undefined && $scope.regulationGroup.oldStatisticalObject!==null)
                      {
				        
                        if( $scope.regulationGroup.statisticalRuleRepresentation.label!==$scope.regulationGroup.oldStatisticalObject.label)
                            {
                              $('#createRegulationGroupVersionModal').modal('show');
                            }
                        else
                            $scope.saveEditedRegulationGroup(); 
                      }
				    else
				        $scope.saveEditedRegulationGroup();
				};
				$scope.createNewVersion=function()
				{
				    $scope.regulationGroup.newRgVersion=true;
				    $scope.saveEditedRegulationGroup();
				};
				$scope.setOldStatisticalRule=function()
				{
				    $scope.regulationGroup.newRgVersion=false;
				    $scope.regulationGroup.statisticalRuleRepresentation=$scope.regulationGroup.oldStatisticalObject;
				};
				$scope.saveEditedRegulationGroup = function() {
				   

					$scope.regulationGroup.wltpRepresentation = $scope.getWltpData();
					
					
				
					

					EditRgService.EditRegulationGroupResource.saveEditedRegulationGroup($scope.regulationGroup, function(response) {
						$scope.regulationGroup = response;
						if ($scope.regulationGroup.rgValuedEsDepTCL != null && $scope.regulationGroup.rgValuedEsDepTCL.length > 0) {
							for (var i = 0; i < $scope.regulationGroup.rgValuedEsDepTCL.length; i++)
								$scope.editDefaultConditionValue($scope.regulationGroup.rgValuedEsDepTCL[i]);
						}
						
						NotificationService.notify(CultureService.localize('cop.regulationGroup.message.save'));
						$scope.loadSelectedTG();

					},
					function(error) {

					    if (error.status = 412)
					        {
					        $scope.errorMessage = CultureService.localize('cop.regulationGroup.statisticalRule.errorMessage');
                            $('#errorDisplayModal').modal('show');
					        }

                    });

				};
				/**
				 * 
				 */
				$scope.editTechnicalGroup = function(technicalGroup) {

					var objTosend = {

						"entityId" : technicalGroup.entityId,
						"description" : technicalGroup.description,
						"label" : technicalGroup.label,
						"version" : technicalGroup.version,
						"available" : technicalGroup.available,
						"techgroupstatus" : technicalGroup.techgroupstatus,
						"valid" : technicalGroup.valid,
						"samplingLabel" : technicalGroup.samplingLabel,
						"creationDate" : technicalGroup.creationDate,
						"modificationDate" : technicalGroup.modificationDate

					}
					TGService.savedTechnicalGroup = objTosend;
					var url = "/cop/EditTechnicalGroup";
					$location.url(url);
				}
				$scope.deleteTechnicalGroupInRg = function(technicalGroup) {
				    EditRgService.EditRegulationGroupResource.deleteTechnicalGroup(technicalGroup, function() {
						NotificationService.notify(CultureService.localize("cop.editTechnicalGroup.delete.successMessage"));
						$scope.loadSelectedTG();
					});

				};

				$scope.getWltpData = function() {
					if ($scope.regulationGroup.wltpRepresentation != undefined || $scope.regulationGroup.wltpRepresentation != null) {
						var objToSave = {
							"entityId" : $scope.regulationGroup.wltpRepresentation.entityId,
							"label" : $scope.regulationGroup.wltpRepresentation.label,
							"version" : $scope.regulationGroup.wltpRepresentation.version,
							"crrvHigh" : $scope.regulationGroup.wltpRepresentation.crrvHigh,
							"crrvInd" : $scope.regulationGroup.wltpRepresentation.crrvInd,
							"crrvLow" : $scope.regulationGroup.wltpRepresentation.crrvLow,
							"f0vHigh" : $scope.regulationGroup.wltpRepresentation.f0vHigh,
							"f0vInd" : $scope.regulationGroup.wltpRepresentation.f0vInd,
							"f0vLow" : $scope.regulationGroup.wltpRepresentation.f0vLow,
							"f1vHigh" : $scope.regulationGroup.wltpRepresentation.f1vHigh,
							"f1vInd" : $scope.regulationGroup.wltpRepresentation.f1vInd,
							"f1vLow" : $scope.regulationGroup.wltpRepresentation.f1vLow,
							"f2vHigh" : $scope.regulationGroup.wltpRepresentation.f2vHigh,
							"f2vInd" : $scope.regulationGroup.wltpRepresentation.f2vInd,
							"f2vLow" : $scope.regulationGroup.wltpRepresentation.f2vLow,
							"scxVHigh" : $scope.regulationGroup.wltpRepresentation.scxVHigh,
							"scxVInd" : $scope.regulationGroup.wltpRepresentation.scxVInd,
							"scxVLow" : $scope.regulationGroup.wltpRepresentation.scxVLow,
							"masseVhigh" : $scope.regulationGroup.wltpRepresentation.masseVhigh,
							"massVInd" : $scope.regulationGroup.wltpRepresentation.massVInd,
							"massVLow" : $scope.regulationGroup.wltpRepresentation.massVLow
						};
					}
					return objToSave;

				};
				$scope.loadRegulationValuedTCL = function(emsId) {
					RGvaluedTestConditionService.ReValuedTestConditionResource.loadRGValuedTCl({
						id : emsId
					}, function(response) {
						$scope.regulationGroup.rgValuedEsDepTCL = response;

					});
				}

				$scope.checkkk = function() {

					$('#createTechnicalGroupModal').modal('show');
				}
				$scope.editable = true;

				$scope.edit = function() {
					this.editable = false;
					setTimeout(function() {
						$(".editableInput").focus();
					}, 400);
				}

				$scope.save = function() {
					this.editable = true;
				}

				$scope.enterSave = function() {
					this.editable = true;
				}
				// Changes on TG Cancel Button
				$scope.CancelOnConfirmationTG = function() {
					$scope.dataNotChanged = angular.equals($scope.regulationGroup, $scope.regulationGroupCopy);
					$scope.dataNotChangedApproval = angular.equals($scope.typeApprovalArea, $scope.typeApprovalAreaCopy);
					$scope.dataNotChangedES = angular.equals($scope.emissionStandard, $scope.emissionStandardCopy);
					$scope.dataNotChangedTG = angular.equals($scope.tgList, $scope.tgListCopy);
					$scope.dataNotChangedStatistic=angular.equals($scope.statisticalRule,$scope.statisticalRuleCopy);
					if ($scope.dataNotChanged == false || $scope.dataNotChangedApproval == false || $scope.dataNotChangedES == false || $scope.dataNotChangedTG == false || $scope.dataNotChangedStatistic == false) {
						$('#ConfirmCancelTGModal').modal('show');
					}

					
				};
				$scope.cancelModifiedTGChanges = function() {
					$scope.regulationGroup = angular.copy($scope.regulationGroupCopy);
					$scope.typeApprovalArea = angular.copy($scope.typeApprovalAreaCopy);
					$scope.emissionStandard = angular.copy($scope.emissionStandardCopy);
					$scope.tgList = angular.copy($scope.tgListCopy);
				};
				// End of Changes on Cancel Button

			}
	]);

	EditRegulationGroup.directive('regeditable', function() {
		return {
			restrict : 'E',
			scope : {
				model : '='
			},
			replace : false,
			template : '<span>' + '<div class="col-lg-10" style="padding:5px 0px; max-width: 15vw; max-height: 30px; overflow: hidden;text-overflow: ellipsis;">' + '<input type="number"  ng-model="model" ng-show="edit" ng-enter="edit=false" class="custom-form-control"></input>' + '<b><span ng-show="!edit" class="editable-text" title="{{model}}">{{model}}</span></b>' + '</div>' + '<div class="col-lg-2" style="text-align:right; padding:0px 5px;">' + '<span ng-show="!edit">&nbsp;<span class="fa fa-pencil fa-2x"></span></span>' + '</div>' + '</span>',
			link : function(scope, element, attrs) {
				scope.edit = false;
				element.bind('click', function() {
					scope.$apply(scope.edit = true);
					element.find('input').focus();
				});
			}
		};
	});

	EditRegulationGroup.directive('ngEnter', function() {
		return function(scope, element, attrs) {
			element.bind("keydown keypress", function(event) {
				if (event.which === 13) {
					scope.$apply(function() {
						scope.$eval(attrs.ngEnter);
					});

					event.preventDefault();
				}
			});
		};
	});

	EditRegulationGroup.directive('ngBlur', function() {
		return function(scope, elem, attrs) {
			elem.bind('blur', function() {
				scope.$apply(attrs.ngBlur);
			});
		};
	});

	return {
		angularModules : [
			'EditRegulationGroup'
		]
	};
});
