/*
 * Copyright (c) PSA.
 */

define([
        'require', 'module', '{angular}/angular'
        ], function(require, module, angular) {
	'use strict';

	var preparationListItems = angular.module('preparationList', [
			'ui.bootstrap', 'copCommon'
	]), config = module && module.config() || {};

	preparationListItems.factory('preparationService', [
			'$resource', function($resource) {

				 return{
					 preparationResource : $resource('preparationFile/:path/:vehicleFileId/:vehicleFileStatus',{
						 path : '@path',
						 vehicleFileId : '@vehicleFileId',
						 vehicleFileStatus : '@vehicleFileStatus'
					 },{
						 'getPreparation':{
							 mathod : 'GET',
							 params : {
								 path : 'preparationfilesdata'
							 }
						 },
						 'createPreparation' : {
							 method : 'POST',
							 params : {
								 path : 'PreparationFile'
							 }
						 },
						 'changeVehicleFileStatus' : {
							 method : 'POST',
							 params : {
								 path : 'vehiclefilestatus'
							 }
						},

					 }),
					 savepreparationResource : $resource('preparationFile/:path',{
						 path : '@path'
					 },{
						 'savePreparation' : {
							 method : 'POST',
							 params : {
								 path : 'preparationfile'
							 }
						 } 
					}),
					vehiclecountryResource : $resource('PaysReference/:path/:technicalCaseId', {

						path : '@path',
						technicalCaseId : '@technicalCaseId'
					}, {
						'getCountryList' : {
							method : 'GET',
							params : {
								path : 'countryList'
							},
							isArray : true
						}
					 }) 
				 }
			}
	]);



			 preparationListItems.controller('preparationController',
					 [
					  '$scope',
					  'NotificationService',
					  'CultureService',
					  'preparationService',
					  '$routeParams',
					  'AuthorizationService','$location', function($scope,NotificationService, CultureService,preparationService,$routeParams,authorizationService,$location) {

						  $scope.authorization = function() {

							  if(authorizationService.hasRole('seed-w20', 'POCARole') || authorizationService.hasRole('seed-w20','POCMRole')){
								  return true;
							  }else{
								  return false;
							  }

						  };

						  /** validation Regex **/
						  var INTEGER_REG = new RegExp("^[-]?[0-9]+$");
						  var FLOAT_REG = new RegExp("^[-]?[0-9/,.]+$");

						  var dateValidation = function ValidateDate(dt) {

							  var isValidDate = false;
							  var arr1 = dt.split('/');
							  if(arr1.length === 3){

								  var year = parseInt(arr1[2],10);
								  var month = parseInt(arr1[1],10);
								  var day = parseInt(arr1[0],10);

								  var isLeapYear = false;
								  if(year % 4 == 0)
									  isLeapYear = true;

								  if(month===4 || month===6|| month===9|| month===11){
									  if((day>=0 && day <= 30)){
										  isValidDate=true;
									  }
								  }else if((month !==2) && (day>=0 && day <= 31)){
									  isValidDate=true;
								  }

								  if(!isValidDate){
							if (isLeapYear) {
										  if(month==2 && (day>=0 && day <= 29))
											  isValidDate=true;
							} else {
										  if(month==2 && (day>=0 && day <= 28))
											  isValidDate=true;
									  }
								  }
							  }
							  return isValidDate;
						  }

						  var hourValidation = function(value){
							  var isValidHour = false;
							  var arr1 = value.split(':');

							  if(arr1.length===2){
								  var hour = parseInt(arr1[0],10);
								  var min = parseInt(arr1[1],10);

								  if(arr1[0].length===2 && hour<24 && arr1[1].length===2 && min <=59){
									  isValidHour = true;
								  }
							  }
							  return isValidHour;
						  };



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
				$scope.makeDrpdwnDisable = false;
						  /*** load preparationList ***/
						  $scope.loadPreparationList = function(){
							  preparationService.preparationResource.getPreparation({
								  vehicleFileId : $scope.vehicleFileId
							  },function(response){
								  $scope.preparationFile = response;
						if($scope.preparationFile.prepCheckListRepresentation!=null){
						for(var i = 0; i<$scope.preparationFile.prepCheckListRepresentation.length;i++){
						if($scope.preparationFile.prepCheckListRepresentation[i].typeOfList === 3 && $scope.preparationFile.prepCheckListRepresentation[i].preparationResultList[1].value==="no"){
							$scope.makeDrpdwnDisable = true;	
						}}}
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
						}, function(response) {
							$scope.countryList = response;
						})
						/*if($scope.checkList.preparationResultList[1].value==="no"){
							$scope.makeDrpdwnDisable = true;
						}*/
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
					}
					;
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
					}
					;
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
					}
					;
							  $scope.tabIndexActive(tabIndex);
						  };

						  $scope.saveOilLevelCheck = function(oilLevelCheck,tabIndex){
							  var oilCheckValidation = true;
					if (oilLevelCheck.preparationResultList[2].mandatory === true && (oilLevelCheck.preparationResultList[2].value === null || oilLevelCheck.preparationResultList[2].value === '')) {
						oilCheckValidation = false;
					}
					if (oilLevelCheck.preparationResultList[1].value === "yes") {
							  if(oilCheckValidation){
								  savePreparationList();
							  }else{
								  $('#mandatoryErrorModal').modal('show');
							  };
					} else {
						savePreparationList();
					}
							  $scope.tabIndexActive(tabIndex);
						  };

						  $scope.saveGasoline = function(gasolineObj,tabIndex){
							  var gasolineValidation = true;
							  gasolineValidation = mandatoryValidation(gasolineObj,gasolineValidation);

							  if(gasolineValidation){
								  savePreparationList();				
							  }else{
								  $('#mandatoryErrorModal').modal('show');
					}
					;
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
					}
					;
							  $scope.tabIndexActive(tabIndex);
						  };

						  /******************************************** actions ****************************************************/		
				$scope.actions = [
						{
							  'id' : 1,
							  'label' : 'Commencer la préparation'
						  },{
							  'id' : 2,
							  'label' : 'Envoyer en expertise'
						  },{
							  'id' : 4,
							  'label' : 'Envoyer en EVACOP'
						},
				];

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
//								  if($scope.inBasket === true){
//								  $('#expertiseStatus').modal('show')
//								  }else{
//								  NotificationService.notify(CultureService.localize('cop.preparationList.message.vfNotinBasket'))
//								  }
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
								 	$('#testEVACOP').modal('show')

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
				$scope.displayFields = false;
				$scope.tabClick = function(label) {
					if (label === "Contrôle technique ESSENCE" || label === "Contrôle technique DIESEL") {
						$scope.displayFields = true;
					} else {
						$scope.displayFields = false;
					}
				};
						  /*** change VehicleFile status to completePreparation ***/

						  $scope.completePreparation = function(){

							  changeVehicleFileStatus('Preparation complete');
						  }

						  /*** change VehicleFIle status to Test EVACOP ***/
						  $scope.changeToTestEVACOP = function(){
							  changeVehicleFileStatus('EVA COP test in progress');
							  setTimeout(function(){
								  window.location="#!/"
							  },300)
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

					  }
					  ]);

			 preparationListItems.directive('prepeditable', function() {
				 return {
					 restrict : 'E',
					 scope : {
						 model : '='
					 },
					 replace : false,
			template : '<span>' + '<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 ellipsis" style="padding:0px; max-height: 30px; overflow: hidden;text-overflow: ellipsis;">' + '<input type="text"  data-ng-model="model" ng-show="edit" ng-enter="edit=false" class="form-control custom-form-control-prep"></input>' + '<b class="custom-value-font"><span ng-show="!edit" title="{{model}}">{{model}}</span></b>' + '</div>' + '<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right">' + '<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 no-padding custom-status-color">' + '<span class="fa fa-2x" data-ng-class="{\'fa-asterisk\':model.length == \'0\',\'fa-check-circle\':model.length >= \'1\'}">' + '</span>' + '</div>' + '<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 no-padding">' + '<span ng-show="!edit"><span class="fa fa-pencil fa-2x"></span></span>' + '<span ng-show="edit"><span class="fa fa-floppy-o fa-2x" ng-click="edit=false"></span></span>' + '</div>' + '</div>' + '</span>',
					 link : function(scope, element) {
						 scope.edit = false;
						 element.find('.fa-pencil').bind('click', function() {
							 scope.$apply(scope.edit = true);
							 element.find('input').focus();
						 });
					 }
				 };
			 });

			 preparationListItems.directive('dualeditable', function() {
				 return {
					 restrict : 'E',
					 scope : {
						 model : '='
					 },
					 replace : false,
			template : '<span>' + '<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 ellipsis" style="padding:0px; max-height: 30px; overflow: hidden;text-overflow: ellipsis;">' + '<input type="text"  data-ng-model="model" ng-show="edit" ng-enter="edit=false" class="form-control custom-form-control-prep"></input>' + '<b class="custom-value-font"><span ng-show="!edit" title="{{model}}">{{model}}</span></b>' + '</div>' + '<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5 no-padding text-right">' + '<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 no-padding custom-status-color">' + '<span class="fa fa-2x" data-ng-class="{\'fa-asterisk\':model.length == \'0\',\'fa-check-circle\':model.length >= \'1\'}">' + '</span>' + '</div>' + '<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 no-padding">' + '<span ng-show="!edit"><span class="fa fa-pencil fa-2x"></span></span>' + '<span ng-show="edit"><span class="fa fa-floppy-o fa-2x" ng-click="edit=false"></span></span>' + '</div>' + '</div>' + '</span>',
					 link : function(scope, element) {
						 scope.edit = false;
						 element.find('.fa-pencil').bind('click', function() {
							 scope.$apply(scope.edit = true);
							 element.find('input').focus();
						 });
					 }
				 };
			 });

			 preparationListItems.directive('ngEnter', function() {
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

			 preparationListItems.directive('ngBlur', function() {
				 return function(scope, elem, attrs) {
					 elem.bind('blur', function() {
						 scope.$apply(attrs.ngBlur);
					 });
				 };
			 });

			 preparationListItems.filter('calcFilter',function() {
				 return function(text){
					 var str = text.replace("BVA", '');
					 return str;
				 }
	});

			 return {
				 angularModules : [
				                   'preparationList'
				                   ]
			 };

});
