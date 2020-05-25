/*
 * Copyright (c) PSA.
 */

define([
        'require', 'module', '{angular}/angular'
        ], function(require, module, angular) {
    'use strict';

    var testConditions = angular.module('testConditions',
            [
             'ui.bootstrap', 'copCommon']), 
             config = module && module.config() || {};



          


             testConditions.controller('testConditionsController',
                     [
                      '$scope',
                      '$routeParams',
                      'NotificationService',
                      'CultureService',
                      'preparationService',
                      'AuthorizationService',function($scope,$routeParams,NotificationService, CultureService,preparationService,authorizationService) {

                          $scope.authorization = function() {

                              if(authorizationService.hasRole('seed-w20', 'POCARole') || authorizationService.hasRole('seed-w20','POCMRole')){
                                  return true;
                              }else{
                                  return false;
                              }

                          };

                          $scope.tcList = [{
                              'label' : 'Phase 1',
                              'Value' : '9'

                          },
                          {
                              'label' : 'Phase 2',
                              'Value' : '9'
                              
                          },
                          {
                              'label' : 'Phase 3',
                              'Value' : '9'
                              
                          },
                          {
                              'label' : 'Phase 4',
                              'Value' : '9'
                              
                          },                          {
                              'label' : 'Phase 5',
                              'Value' : '9'
                              
                          },                          {
                              'label' : 'Phase 6',
                              'Value' : '9'
                              
                          },                          {
                              'label' : 'Phase 7',
                              'Value' : '9'
                              
                          },                          {
                              'label' : 'Phase 8',
                              'Value' : '9'
                              
                          },                          {
                              'label' : 'Phase 9',
                              'Value' : '9'
                              
                          },                          {
                              'label' : 'Phase 10',
                              'Value' : '9'
                              
                          },                          {
                              'label' : 'Phase 11',
                              'Value' : '9'
                              
                          },                          {
                              'label' : 'Phase 12',
                              'Value' : '9'
                              
                          },                          {
                              'label' : 'Phase 13',
                              'Value' : '9'
                              
                          },
                          ]

                          $scope.tabActive = function(){
                              setTimeout(function(){
                                  var typeOfListTab = angular.element( document.querySelectorAll( '.preperationList0' ) );
                                  typeOfListTab.addClass('active');   

                                  var typeOfListIdTab = angular.element( document.querySelectorAll( '#preperationList0' ) );
                                  typeOfListIdTab.addClass('active in');   


                              },1000);
                          }

                          $scope.tabIndexActive = function(tabIndex){
                              setTimeout(function(){
                                  var typeOfListTab = angular.element( document.querySelectorAll( '.preperationList'+tabIndex ) );
                                  typeOfListTab.addClass('active');   

                                  var typeOfListIdTab = angular.element( document.querySelectorAll( '#preperationList'+tabIndex ) );
                                  typeOfListIdTab.addClass('active in');   
                              },1000);
                          };

                          $scope.tabActive();

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
                          };




                          /*** load preparationList ***/
                          $scope.loadPreparationList = function(){
                              preparationService.preparationResource.getPreparation({
                                  vehicleFileId : $scope.vehicleFileId
                              },function(response){
                                  $scope.preparationFile = response;
                                  /** adding option to actions **/
                                  if(($scope.preparationFile.vehicleFileStatus.trim()).toUpperCase() ==='PREPARATION IN PROGRESS'){
                                      var actionCheck = true;
                                      for(var i =0; i<$scope.actions.length; i++){
                                          if($scope.actions[i].id===3){
                                              actionCheck = false;
                                              break;
                                          }

                                      }
                                      if(actionCheck){
                                          $scope.actions.push({
                                              'id' : 3,
                                              'label' : 'Terminer la préparation'
                                          })
                                      }
                                  }
                                  preparationService.vehiclecountryResource.getCountryList({
                                      technicalCaseId : $scope.preparationFile.vehicleFileRepresentation.vehicleRepresentation.technicalCaseId
                                  },function(response){
                                      $scope.countryList = response;
                                  })

                              },function(){
                              });
                          };
                          $scope.loadPreparationList();

                          /** validation mandatory value **/
                          var mandatoryValidation = function(ValidateObj,validationChek){ 

                              for(var i=0; i<ValidateObj.preparationResultList.length; i++){
                                  if(ValidateObj.preparationResultList[i].mandatory===true && (ValidateObj.preparationResultList[i].value===null || ValidateObj.preparationResultList[i].value=== '')){

                                      validationChek = false;
                                      break;
                                  }
                              };
                              return validationChek;
                          };
                          $scope.errorMSG="";

                          /*** Validating dataType ***/
                          var dataTypeValidation = function(){
                              var datatypeCheck = true;


                              for(var i=0; i<$scope.preparationFile.prepCheckListRepresentation.length; i++){

                                  var prepCheckList = $scope.preparationFile.prepCheckListRepresentation[i].preparationResultList;

                                  for(var j=0; j<prepCheckList.length; j++){
                                      if(prepCheckList[j].value!= undefined && prepCheckList[j].value!=null && prepCheckList[j].value!=''){

                                          if(prepCheckList[j].dataType.toUpperCase() === 'INTEGER'){
                                              if (!INTEGER_REG.test(prepCheckList[j].value)) {
                                                  $scope.errorMSG = prepCheckList[j].dataType + ' ' +CultureService.localize('cop.preparationList.modal.list')+$scope.preparationFile.prepCheckListRepresentation[i].label + ' - '+prepCheckList[j].label;
                                                  datatypeCheck = false;
                                                  break;
                                              }
                                          }else if(prepCheckList[j].dataType.toUpperCase() === 'FLOAT'){
                                              if (!FLOAT_REG.test(prepCheckList[j].value)) {
                                                  $scope.errorMSG =  prepCheckList[j].dataType + ' '+CultureService.localize('cop.preparationList.modal.list') +$scope.preparationFile.prepCheckListRepresentation[i].label + ' - '+prepCheckList[j].label;
                                                  datatypeCheck = false;
                                                  break;
                                              }

                                          }else if(prepCheckList[j].dataType.toUpperCase() === 'DATE'){

                                              datatypeCheck = dateValidation(prepCheckList[j].value)
                                              if(!datatypeCheck){
                                                  $scope.errorMSG =  prepCheckList[j].dataType + ' '+CultureService.localize('cop.preparationList.modal.list') +$scope.preparationFile.prepCheckListRepresentation[i].label + ' - '+prepCheckList[j].label;
                                                  break;
                                              }
                                          }else if(prepCheckList[j].dataType.toUpperCase() === 'HOUR'){
                                              datatypeCheck = hourValidation(prepCheckList[j].value);
                                              if(!datatypeCheck){
                                                  $scope.errorMSG =  prepCheckList[j].dataType + ' '+CultureService.localize('cop.preparationList.modal.list') +$scope.preparationFile.prepCheckListRepresentation[i].label + ' - '+prepCheckList[j].label;
                                                  break;
                                              }
                                          }

                                      }
                                  }
                                  if(!datatypeCheck){
                                      $('#dataType').modal('show');
                                      break;
                                  }
                              }
                              return datatypeCheck
                          }
                          /***** save preparation List function *****/
                          var savePreparationList = function(){
                              var validationCheck = dataTypeValidation();
                              if(validationCheck){
                                  preparationService.savepreparationResource.savePreparation($scope.preparationFile,function(){
                                      NotificationService.notify(CultureService.localize('cop.testCondition.message.successMessage'))
                                      $scope.loadPreparationList();
                                  },function(){
                                  });
                              }
                          };

                          /***** save preparation-parametrized *****/
                          $scope.saveParametrized = function(parametrizedObj,tabIndex){

                              var parametrizedValidation = true;

                              parametrizedValidation = mandatoryValidation(parametrizedObj,parametrizedValidation);

                              if(parametrizedValidation){

                                  savePreparationList();
                              }else{
                                  $('#mandatoryErrorModal').modal('show')
                              };    
                              $scope.tabIndexActive(tabIndex);
                          };


                          /**** save preparation-calculators ******/

                          $scope.saveCalculators = function(calculatorsBVA, calculatorsMoter,tabIndex){


                              var calculatorsValidation = true;

                              calculatorsValidation = mandatoryValidation(calculatorsBVA,calculatorsValidation);

                              calculatorsValidation = mandatoryValidation(calculatorsMoter,calculatorsValidation)

                              if(calculatorsValidation){
                                  savePreparationList();
                              }else{
                                  $('#mandatoryErrorModal').modal('show');
                              };    
                              $scope.tabIndexActive(tabIndex);
                          };

                          $scope.saveOilLevelCheck = function(oilLevelCheck,tabIndex){
                              var oilCheckValidation = true;
                              oilCheckValidation = mandatoryValidation(oilLevelCheck,oilCheckValidation);

                              if(oilCheckValidation){
                                  savePreparationList();
                              }else{
                                  $('#mandatoryErrorModal').modal('show');
                              };
                              $scope.tabIndexActive(tabIndex);
                          };

                          $scope.saveGasoline = function(gasolineObj,tabIndex){
                              var gasolineValidation = true;
                              gasolineValidation = mandatoryValidation(gasolineObj,gasolineValidation);

                              if(gasolineValidation){
                                  savePreparationList();                
                              }else{
                                  $('#mandatoryErrorModal').modal('show');
                              };
                              $scope.tabIndexActive(tabIndex);
                          };

                          $scope.saveDiesel = function(dieselObj,tabIndex){
                              var dieselValidation = true;
                              if(dieselObj.preparationResultList[11].value === null || dieselObj.preparationResultList[11].value===''){
                                  dieselObj.preparationResultList[11].value = 0;
                              }

                              dieselValidation = mandatoryValidation(dieselObj,dieselValidation);
                              if(dieselValidation){
                                  savePreparationList();                
                              }else{
                                  $('#mandatoryErrorModal').modal('show');
                              };
                              $scope.tabIndexActive(tabIndex);
                          };

                          /******************************************** actions ****************************************************/       
                          $scope.actions = [{
                              'id' : 1,
                              'label' : 'Commencer la préparation'
                          },{
                              'id' : 2,
                              'label' : 'Envoyer en expertise'
                          },{
                              'id' : 4,
                              'label' : 'Envoyer en EVACOP'
                          },];

                          if(authorizationService.hasRole('seed-w20', 'POCMRole')){
                              $scope.actions.push({
                                  'id' : 5,
                                  'label' : 'Repasser en statut ‘préparation en cours'
                              })
                          };

                          var createPrapationList = function(){
                              preparationService.preparationResource.createPreparation({
                                  vehicleFileId : $scope.vehicleFileId
                              },function(response){
                                  $scope.preparationFile = response;
                                  NotificationService.notify(CultureService.localize('cop.testCondition.message.successMessage'));
                              },function(){

                              });
                          }

                          /** prepapration complete save Check **/
                          var preparationCompleteCheck = function(){

                              var validationCheck = dataTypeValidation();
                              if(validationCheck){
                                  preparationService.savepreparationResource.savePreparation($scope.preparationFile,function(){
                                      $('#completePreparation').modal('show')
                                      $scope.loadPreparationList();

                                  },function(){
                                  });
                              }
                          }

                          /*** action on selection ***/
                          $scope.actionSelected = function(selected){
                              if(selected.id === 1){
                                  if($scope.inBasket === 'true'){
                                      if($scope.preparationFile.entityId === null ){
                                          createPrapationList();
                                          $scope.tabActive();
                                      }else{
                                          $('#plExistErrorModal').modal('show')
                                      }
                                  }else{
                                      $('#inBasketErrorModal').modal('show')
                                  }
                              } else if(selected.id === 2){
//                                if($scope.inBasket === true){
//                                $('#expertiseStatus').modal('show')
//                                }else{
//                                NotificationService.notify(CultureService.localize('cop.preparationList.message.vfNotinBasket'))
//                                }
                              } else if(selected.id === 3){
                                  if($scope.inBasket === 'true'){
                                      var checkMandatory = true;
                                      angular.forEach($scope.preparationFile.prepCheckListRepresentation,function(preparationFileObj){
                                          if(preparationFileObj.enabled === true)
                                              checkMandatory = mandatoryValidation(preparationFileObj,checkMandatory);


                                      });
                                      if(checkMandatory){
                                          preparationCompleteCheck();
                                      }else{
                                          $('#mandatoryErrorModal').modal('show');
                                      }
                                  }else{
                                      $('#inBasketErrorModal').modal('show')

                                  }
                              } else if(selected.id === 4){
                                  //    $('#testEVACOP').modal('show')

                              } else if(selected.id === 5){

                                  if($scope.preparationFile.entityId ===null){
                                      createPrapationList();
                                  }else{
                                      $('#plExistErrorModal').modal('show')
                                  }
                              }


                          };

                          /** change VehicleFile status **/
                          var changeVehicleFileStatus = function(status){
                              preparationService.preparationResource.changeVehicleFileStatus({
                                  vehicleFileId : $scope.vehicleFileId,
                                  vehicleFileStatus : status
                              },function(){
                                  $scope.loadPreparationList();
                                  NotificationService.notify(CultureService.localize('cop.testCondition.message.successMessage'))

                              })
                          };
                          /*** change VehicleFile status to expertise ***/
                          $scope.changeToExpertise = function(){
                              changeVehicleFileStatus('In expertise');
                          };

                          /*** change VehicleFile status to completePreparation ***/

                          $scope.completePreparation = function(){

                              changeVehicleFileStatus('Preparation complete');
                          }

                          /*** change VehicleFIle status to Test EVACOP ***/
                          $scope.changeToTestEVACOP = function(){
                              changeVehicleFileStatus('EVA COP test in progress');
                          };

                          $scope.changeCO = function(checkList){
                              if($scope.preparationFile.coDecisionLowerLim<checkList.preparationResultList[3].value && $scope.preparationFile.coDecisionHigherLim>checkList.preparationResultList[3].value){
                                  checkList.preparationResultList[7].value = 'Accepté';
                              }else{
                                  checkList.preparationResultList[7].value = 'Refusé';
                              }
                          };

                          $scope.changeLamda = function(checkList){
                              if($scope.preparationFile.lambdaDecisionLowerLim<checkList.preparationResultList[6].value && $scope.preparationFile.lambdaDecisionHigherLim>checkList.preparationResultList[6].value){
                                  checkList.preparationResultList[8].value = 'Accepté';
                              }else{
                                  checkList.preparationResultList[8].value = 'Refusé';
                              }
                          };
                          $scope.dieselAverage = function(preparationResultList){

                              var avg = 0;
                              if(!isNaN(preparationResultList[0].value) && preparationResultList[0].value !== null && preparationResultList[0].value !== ''){
                                  avg += parseFloat(preparationResultList[0].value)
                              };
                              if(!isNaN(preparationResultList[1].value) && preparationResultList[1].value !== null && preparationResultList[1].value !== ''){
                                  avg += parseFloat(preparationResultList[1].value)
                              };
                              if(!isNaN(preparationResultList[2].value) && preparationResultList[2].value !== null && preparationResultList[2].value !== ''){
                                  avg += parseFloat(preparationResultList[2].value)
                              };
                              if(!isNaN(preparationResultList[3].value) && preparationResultList[3].value !== null && preparationResultList[3].value !== ''){
                                  avg += parseFloat(preparationResultList[3].value)
                              };

                              preparationResultList[4].value = avg/4;

                              if($scope.preparationFile.opacDecHigherLim >preparationResultList[4].value && $scope.preparationFile.opacDecLowerLim<preparationResultList[4].value){
                                  preparationResultList[4].conformity = true;
                              }else{
                                  preparationResultList[4].conformity = false;
                              }


                          };

                          $scope.createNewSpecificTestCondition = function(){
                              angular.element(document.querySelector("#createNewSpecificTestCondition")).modal('show');
                          } 
                      }
                      ]);


             

             return {
                 angularModules : [
                                   'testConditions'
                                   ]
             };

});
