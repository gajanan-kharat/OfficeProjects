define([ 'require', 'module', '{angular}/angular' ], function(require, module, angular) {

	var ruleParameters = angular.module('statisticalRuleParameter',[]);

	ruleParameters.factory('ruleParameterService',['$resource', function($resource){
		return{

			ruleParameterResource : $resource('statisticalruleparameter/:path/:statisticalRuleId',{
				path : '@path',
				statisticalRuleId : '@statisticalRuleId'
			},{
				'getStatisticalRule' : {
					method : 'GET',
					params : {
						path : 'statisticalrulelist'
					},
					isArray : true
				},
				'getPollutantGas' : {
					method : 'GET',
					params : {
						path : 'polluntantgas'
					},
					isArray : true
				},
				'getStatisticalParameter' : {
					mathod : 'GET',
					params : {
						path : 'statisticalparameter'
					},
					isArray : true
				},
				'saveStatisticalParameter' : {
					method : 'POST',
					params : {
						path : 'statisticalparameter'
					}
				}

			})  
		}
	}]);

	ruleParameters.controller('statisticalRuleParameterController',['$scope','ruleParameterService','NotificationService','CultureService','HistoryService','AuthenticationService','AuthorizationService', function($scope,ruleParameterService, NotificationService, CultureService, HistoryService,authenticationService,authorizationService){


		 $scope.authorization = function() {
			 
	            if(authorizationService.hasRole('seed-w20', 'POCNRole')){
	            	return true;
	            }else{
	                return false;
	            }
	           
	        };
	        $scope.cahnge = true;
	        
		$scope.loadStatisticalRule = function(){
			ruleParameterService.ruleParameterResource.getStatisticalRule(function(response){
				$scope.statisticalRuleList = response;

			},function(){

			});
		};
		$scope.loadStatisticalRule();

		$scope.loadPollutantGas = function(){
			ruleParameterService.ruleParameterResource.getPollutantGas(function(response){
				$scope.pollutantGasList = response;
			},function(){

			});
		}
		$scope.loadPollutantGas();
		/* flag to make fields editable */
		$scope.fieldsDisabled = true;

		/* on selection of statisticalRule */
		$scope.statisticalRuleSelection = function(statisticalRule){

			$scope.loadPollutantGas();

			$scope.statisticalRuleLabel = statisticalRule.label;
			$scope.statisticalRuleId = statisticalRule.entityId;


			$scope.fieldsDisabled = false;
			if($scope.statisticalRuleLabel === 'WLTP-1'){
				$scope.factorMandatory = true;
			}else{
				$scope.factorMandatory = false;
			}

			if($scope.statisticalRuleLabel === 'JAPAN-1'){
				$scope.limitMandatory = true;
			}else{
				$scope.limitMandatory = false;
			};

			ruleParameterService.ruleParameterResource.getStatisticalParameter({
				statisticalRuleId : $scope.statisticalRuleId
			},function(statisticalPara){
				for(var pglIndex=0; pglIndex< $scope.pollutantGasList.length; pglIndex++){

					for(var spIndex=0; spIndex< statisticalPara.length ; spIndex++){
						if($scope.pollutantGasList[pglIndex].entityId === statisticalPara[spIndex].pollutantGasLabelRep.entityId){
							var obj = {
									'entityId' : $scope.pollutantGasList[pglIndex].entityId,
									'label' : $scope.pollutantGasList[pglIndex].label,
									'uncertainityFactor' : statisticalPara[spIndex].uncertainityFactor,
									'limit1': statisticalPara[spIndex].limit1,
									'limit2': statisticalPara[spIndex].limit2,
									'edited': false,
									'scpEntityID' : statisticalPara[spIndex].entityId
							};
							$scope.pollutantGasList.splice(pglIndex, 1,obj);
						}

					};
				};

			},function(){

			})
		};

		$scope.validateRuleParameter = function(){

			$scope.statisticalParaToSave = [];


			var validationCheck = true;

			if($scope.statisticalRuleLabel === 'WLTP-1'){
				for(var i=0; i<$scope.pollutantGasList.length; i++){

					if($scope.pollutantGasList[i].uncertainityFactor === ''|| $scope.pollutantGasList[i].uncertainityFactor=== undefined){
						validationCheck = false;
						$scope.errorMassage = CultureService.localize('cop.ruleParameter.modal.reuiredfactor');
						break;
					}
				}
			};
			if($scope.statisticalRuleLabel === 'JAPAN-1'){
				for(var j=0; j<$scope.pollutantGasList.length; j++){

					if($scope.pollutantGasList[j].limit1 === '' || $scope.pollutantGasList[j].limit1=== undefined || $scope.pollutantGasList[j].limit2 === '' || $scope.pollutantGasList[j].limit2=== undefined){
						validationCheck = false;
						$scope.errorMassage = CultureService.localize('cop.ruleParameter.modal.reuiredLimit');
						break;
					}
				}
			};



			if(validationCheck){
				
				for(var pglIndex=0; pglIndex< $scope.pollutantGasList.length; pglIndex++){
					if($scope.pollutantGasList[pglIndex].edited === true){
						var uncertainityFactorValue, limit1Value, limit2Value;
						if($scope.pollutantGasList[pglIndex].uncertainityFactor !== null && $scope.pollutantGasList[pglIndex].uncertainityFactor !== undefined)
							uncertainityFactorValue = ($scope.pollutantGasList[pglIndex].uncertainityFactor.toString()).replace(/,/g,".");
						
						if($scope.pollutantGasList[pglIndex].limit1 !== null && $scope.pollutantGasList[pglIndex].limit1 !== undefined)
							limit1Value = ($scope.pollutantGasList[pglIndex].limit1.toString()).replace(/,/g,".");
						
						if($scope.pollutantGasList[pglIndex].limit2 !== null && $scope.pollutantGasList[pglIndex].limit2 !== undefined)
							limit2Value = ($scope.pollutantGasList[pglIndex].limit2.toString()).replace(/,/g,".");
						
						var tempObj = {
								'entityId' : $scope.pollutantGasList[pglIndex].scpEntityID,
								'uncertainityFactor' : uncertainityFactorValue,
								'limit1' : limit1Value,
								'limit2' : limit2Value,
								'pglEntityId' : $scope.pollutantGasList[pglIndex].entityId,
								'scrEntityId' : $scope.statisticalRuleId
						}

						$scope.statisticalParaToSave.push(tempObj);

					}
				}
				if($scope.statisticalParaToSave.length>0){
					$('#saveConfirmation').modal('show')
					

				}
			} else{
				$('#ErrorModal').modal('show');
			}
		};
		
		$scope.saveRuleParameter = function(){
			ruleParameterService.ruleParameterResource.saveStatisticalParameter({
				'statisticalParameterList' : $scope.statisticalParaToSave
			}, function(){
				NotificationService.notify(CultureService.localize("cop.testCondition.message.successMessage")); 
			},function(){

			})
		}

		$scope.refresh = function() {
			location.reload();
		}; 
/* validation for number */
		$scope.numValidation = function(value){
			var INTEGER_REG = new RegExp("^[-]?[0-9/,.]+$");
			if(!INTEGER_REG.test(value)){
				value = value.substring(0,value.length - 1);
			}
			return value;
		};
		
		  /*------------------------------------------History-------------------------------------------------*/

        $scope.HistoryGridOptions = {
            enableColumnResizing: true,
            enableFiltering: true,
            enableSorting: true,
            enableHorizontalScrollbar: false,
            columnDefs: [

                {
                    name: 'date',
                    displayName: CultureService.localize('cop.history.date'),
                    type: 'date',
                    field: 'updationDate',
                    cellFilter: 'date:\'dd/MM/yyyy HH:mm:ss\'',
                    cellTooltip: function(row) {
                        return row.entity.updationDate;
                    },
                    enableCellEdit: false
                }, {
                    name: 'userId',
                    displayName: CultureService.localize('cop.history.userId'),
                    field: 'userId',
                    cellTooltip: function(row) {
                        return row.entity.userId;
                    },
                    enableCellEdit: false
                }, {
                    name: 'userProfile',
                    displayName: CultureService.localize('cop.history.userProfile'),
                    field: 'userProfile',
                    cellTooltip: function(row) {
                        return row.entity.userProfile;
                    },
                    enableCellEdit: false
                }, {
                    name: 'description',
                    displayName: CultureService.localize('cop.history.description'),
                    field: 'description',
                    cellTooltip: function(row) {
                        return row.entity.description;
                    },
                    enableCellEdit: false
                }, {
                    name: 'oldValue',
                    displayName: CultureService.localize('cop.history.oldValue'),
                    field: 'oldValue',
                    cellTooltip: function(row) {
                        return row.entity.oldValue;
                    },
                    enableCellEdit: false
                }, {
                    name: 'newValue',
                    displayName: CultureService.localize('cop.history.newValue'),
                    field: 'newValue',
                    cellTooltip: function(row) {
                        return row.entity.newValue;
                    },
                    enableCellEdit: false
                },

            ],
            data: 'allHistory',

        };

        /* to get History Data */
        $scope.allHistory = [];

        $scope.getAllHistory = function(screenId) {
	var historyResponse = HistoryService.HistroyResource.historyData({
                screenId: screenId
            }, function() {
                $scope.allHistory = historyResponse;

            }, function() {

            });
        };

        $scope.showHistoryData = function(screenId) {
            $scope.getAllHistory(screenId);
            $('#showHistoryBox').modal('show');
        };

	}]);


	return {
		angularModules : [ 'statisticalRuleParameter' ]
	};
});