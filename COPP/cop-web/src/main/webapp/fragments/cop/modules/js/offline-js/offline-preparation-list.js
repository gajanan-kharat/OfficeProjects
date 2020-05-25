

offlineApp.controller('offlinePreparationController', [ '$scope','$window','typeTestGroupIdb','$controller', function($scope,$window,typeTestGroupIdb,$controller){

    var INTEGER_REG = new RegExp("^[-]?[0-9]+$");
    var FLOAT_REG = new RegExp("^[-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?");
    
	$scope.slideToggle = function() {

		$("#wrapper").toggleClass("toggled");
		$(".slide-resize").toggleClass("col-lg-10");
		$("#menu-toggle").toggleClass('fa-chevron-right');
	};
	
	
	  $controller('offlineMenuListController', {$scope: $scope})
	  //inside scope offlineMenuListController scope is available
	  
    //operations
	$scope.makeDrpdwnDisable = false;	
    $scope.preparationFileCaching = function(){
	      
        typeTestGroupIdb.getPreparationFile().then(function(data){
            $scope.offlineRepresentationData=data;
            for(var i=0; i<=$scope.offlineRepresentationData[0].vehicleFileRepList.length; i++){
            if($scope.offlineRepresentationData[0].vehicleFileRepList[i].entityId == $scope.vehicleFileId){
                $scope.preparationFile = $scope.offlineRepresentationData[0].vehicleFileRepList[i].prepFileRepresentation;
                if($scope.preparationFile.prepCheckListRepresentation!=null){
                	for(var j = 0; j<$scope.preparationFile.prepCheckListRepresentation.length;j++){
                		if($scope.preparationFile.prepCheckListRepresentation[j].typeOfList === 3 && $scope.preparationFile.prepCheckListRepresentation[j].preparationResultList[1].value==="no"){
                			$scope.makeDrpdwnDisable = true;	
                		}}}
                if($scope.offlineRepresentationData[0].vehicleFileRepList[i].prepFileRepresentation.entityId === null || $scope.offlineRepresentationData[0].vehicleFileRepList[i].prepFileRepresentation.entityId === undefined){

                   $('#noPrepation').modal('show');
                   $scope.errorMessage = "Le fichier de préparation n'est pas créé pour ce véhicule, les actions sont désactivées en mode hors connexion.";
                   
                }
            }
            }
          


           

        }, function(err){
            $window.alert(err);
        });
//        typeTestGroupIdb.addVehicles($scope.vehicleData).then(function(){
//        }, function(err){
//            $window.alert(err);
//        
//    });
    };
    

  
    $scope.refreshList = function(){
        $scope.preparationFileCaching();
        
        typeTestGroupIdb.getPreparationFile().then(function(data){
            
            
        }, function(err){
            $window.alert(err);
        });

    };

    $scope.init = function(){
        
        typeTestGroupIdb.open().then(function(){
            $scope.refreshList();
        });
       

    }
     
     $scope.init();

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
	  
	  $scope.redirectToHome = function(){
	      $window.location.href = 'offline-home.html';
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
      };
      
      
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
      
      
      /*** Validating dataType ***/
      var dataTypeValidation = function(){
          var datatypeCheck = true;


          for(var i=0; i<$scope.preparationFile.prepCheckListRepresentation.length; i++){

              var prepCheckList = $scope.preparationFile.prepCheckListRepresentation[i].preparationResultList;
              
              for(var j=0; j<prepCheckList.length; j++){
                  if(prepCheckList[j].value!= undefined && prepCheckList[j].value!=null){

                      if(prepCheckList[j].dataType === 'INTEGER'){
                          if (!INTEGER_REG.test(prepCheckList[j].value)) {
                              
                              
                              $scope.errorMSG = $scope.preparationFile.prepCheckListRepresentation[i].label + ' - '+prepCheckList[j].label;
                             // alert('int')
                              datatypeCheck = false;
                              break;
                          }
                      }else if(prepCheckList[j].dataType === 'FLOAT'){
                          if (!FLOAT_REG.test(prepCheckList[j].value)) {
                              $scope.errorMSG = $scope.preparationFile.prepCheckListRepresentation[i].label + ' - '+prepCheckList[j].label;
                              datatypeCheck = false;
                              break;
                          }

                      }

                  }
              }
              if(!datatypeCheck){
                  $('#errorMsg').modal('show');
                  $scope.errorMessage = "Type de données et données invalides.";
                  break;
              }
          }
          return datatypeCheck
      }

      /***** save preparation List function *****/
      var savePreparationList = function(){
          var validationCheck = dataTypeValidation();
          if(validationCheck){
              for(var i=0; i<$scope.offlineRepresentationData[0].vehicleFileRepList.length; i++){
                  
                  if($scope.offlineRepresentationData[0].vehicleFileRepList[i].entityId === $scope.vehicleFileId){
                      $scope.offlineRepresentationData[0].vehicleFileRepList[i].prepFileRepresentation = $scope.preparationFile;
                  }
              }
              typeTestGroupIdb.addPreparationFile($scope.offlineRepresentationData).then(function(){
                  $('#errorMsg').modal('show');
                  $scope.errorMessage = "Données enregistrées avec succès dans votre base de données locale.";
              }, function(err){
                  $window.alert(err);
                  $('#errorMsg').modal('show');
                  $scope.errorMessage = "Impossible d'enregistrer vos modifications dans le db local. Veuillez réessayer.";
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
              $('#errorMsg').modal('show');
              $scope.errorMessage = "Tous les champs marqués d’une astérisque sont obligatoires.";
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
              $('#errorMsg').modal('show');
              $scope.errorMessage = "Tous les champs marqués d’une astérisque sont obligatoires.";
          };    
          $scope.tabIndexActive(tabIndex);
      };

		$scope.saveOilLevelCheck = function(oilLevelCheck, tabIndex) {
			var oilCheckValidation = true;
			if (oilLevelCheck.preparationResultList[2].mandatory === true && (oilLevelCheck.preparationResultList[2].value === null || oilLevelCheck.preparationResultList[2].value === '')) {
				oilCheckValidation = false;
			}
			if (oilLevelCheck.preparationResultList[1].value === "yes") {
				if (oilCheckValidation) {
					savePreparationList();
				} else {
					$('#errorMsg').modal('show');
					$scope.errorMessage = "Tous les champs marqués d’une astérisque sont obligatoires.";
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
              $('#errorMsg').modal('show');
              $scope.errorMessage = "Tous les champs marqués d’une astérisque sont obligatoires.";
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
              $('#errorMsg').modal('show');
              $scope.errorMessage = "Tous les champs marqués d’une astérisque sont obligatoires.";
          };
          $scope.tabIndexActive(tabIndex);
      };
		$scope.displayFields = false;
		$scope.tabClick = function(label) {
			if (label === "Contrôle technique ESSENCE" || label === "Contrôle technique DIESEL") {
				$scope.displayFields = true;
			} else {
				$scope.displayFields = false;
			}
		};
      $scope.dieselAverage = function(preparationResultList){
          
          var avg = 0;
          if(!isNaN(preparationResultList[0].value) && preparationResultList[0].value !== null && preparationResultList[0].value !== ''){
              avg += parseInt(preparationResultList[0].value)
          };
          if(!isNaN(preparationResultList[1].value) && preparationResultList[1].value !== null && preparationResultList[1].value !== ''){
              avg += parseInt(preparationResultList[1].value)
          };
          if(!isNaN(preparationResultList[2].value) && preparationResultList[2].value !== null && preparationResultList[2].value !== ''){
              avg += parseInt(preparationResultList[2].value)
          };
          if(!isNaN(preparationResultList[3].value) && preparationResultList[3].value !== null && preparationResultList[3].value !== ''){
              avg += parseInt(preparationResultList[3].value)
          };
          
             preparationResultList[4].value = avg/4;
          
          
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
}]);

offlineApp.directive('prepeditable', function() {
    return {
        restrict : 'A',
        scope : {
            model : '='
        },
        replace : false,
        template : '<span>' + '<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 ellipsis" style="padding:0px; max-height: 30px; overflow: hidden;text-overflow: ellipsis;">' + 
        '<input type="text"  data-ng-model="model" ng-show="edit" ng-enter="edit=false" class="form-control custom-form-control-prep"></input>' + '<b class="custom-value-font"><span ng-show="!edit" title="{{model}}">{{model}}</span></b>' + '</div>' + 
        '<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right">' +
        '<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 no-padding custom-status-color">'+'<span class="fa fa-2x" data-ng-class="{\'fa-asterisk\':model.length == \'0\',\'fa-check-circle\':model.length >= \'1\'}">' + '</span>' +'</div>'+
        '<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 no-padding">'+
        '<span ng-show="!edit"><span class="fa fa-pencil fa-2x"></span></span>' +  '<span ng-show="edit"><span class="fa fa-floppy-o fa-2x" ng-click="edit=false"></span></span>' + '</div>' + '</div>'+ '</span>',
        link : function(scope, element) {
            scope.edit = false;
            element.find('.fa-pencil').bind('click', function() {
                scope.$apply(scope.edit = true);
                element.find('input').focus();
            });
        }
    };
});

offlineApp.directive('dualeditable', function() {
    return {
        restrict : 'A',
        scope : {
            model : '='
        },
        replace : false,
        template : '<span>' + '<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 ellipsis" style="padding:0px; max-height: 30px; overflow: hidden;text-overflow: ellipsis;">' + 
        '<input type="text"  data-ng-model="model" ng-show="edit" ng-enter="edit=false" class="form-control custom-form-control-prep"></input>' + '<b class="custom-value-font"><span ng-show="!edit" title="{{model}}">{{model}}</span></b>' + '</div>' + 
        '<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5 no-padding text-right">' +
        '<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 no-padding custom-status-color">'+'<span class="fa fa-2x" data-ng-class="{\'fa-asterisk\':model.length == \'0\',\'fa-check-circle\':model.length >= \'1\'}">' + '</span>' +'</div>'+
        '<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 no-padding">'+
        '<span ng-show="!edit"><span class="fa fa-pencil fa-2x"></span></span>' +  '<span ng-show="edit"><span class="fa fa-floppy-o fa-2x" ng-click="edit=false"></span></span>' + '</div>' + '</div>'+ '</span>',
        link : function(scope, element) {
            scope.edit = false;
            element.find('.fa-pencil').bind('click', function() {
                scope.$apply(scope.edit = true);
                element.find('input').focus();
            });
        }
    };
});

offlineApp.directive('ngEnter', function() {
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

offlineApp.directive('ngBlur', function() {
    return function(scope, elem, attrs) {
        elem.bind('blur', function() {
            scope.$apply(attrs.ngBlur);
        });
    };
});

offlineApp.filter('calcFilter',function() {
    return function(text){
        var str = text.replace("BVA", '');
        return str;
    }
}
);
