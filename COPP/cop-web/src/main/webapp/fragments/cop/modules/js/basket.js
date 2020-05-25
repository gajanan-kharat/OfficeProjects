define([
        'require', 'module', '{angular}/angular'
], function(require, module, angular) {
    var basketModule = angular.module('Basket', []);
    
    basketModule.factory('BasketDetailService',['$resource',function($resource){
        return{
            basketResource : $resource('basket/:path/:userId/:vehicleFileId',{
                path : '@path',
                userId : '@userId',
                vehicleFileId : '@vehicleFileId'
            },{
                'getBasket' : {
                    method : 'GET',
                    params : {
                        path : 'basketdetails'
                    },
                    isArray : true
                },
                'deleteBasket' : {
                    method : 'POST',
                    params : {
                        path: 'baskets'
                    }
                },  
                'getOfflineData' : {
                    method : 'GET',
                    params : {
                        path : 'offlineData'
                    },
                    isArray : false
                }
                
            }) 
        }
    }])
    
    
    //Factory for indexedDb
             basketModule.factory('typeTestGroupIdb',[ '$window', '$q', function($window, $q){
                 var indexedDB = $window.indexedDB;
                 var db=null;
                 var lastIndex=0;


                 var open = function(){
                     var deferred = $q.defer();
                     var version = 1;
                     var request = indexedDB.open("typeTestGroup", version);

                     request.onupgradeneeded = function(e) {
                         db = e.target.result;

                         e.target.transaction.onerror = indexedDB.onerror;

                         if(db.objectStoreNames.contains("typeTestGroup")) {
                             db.deleteObjectStore("typeTestGroup");
                         }

                         var store = db.createObjectStore("typeTestGroup",
                                 {keyPath: "entityId"});
                     };

                     request.onsuccess = function(e) {
                         db = e.target.result;
                         deferred.resolve();
                     };

                     request.onerror = function(){
                         deferred.reject();
                     };

                     return deferred.promise;
                 };


                 var getPreparationFile = function(){
                     var deferred = $q.defer();

                     if(db === null){
                         deferred.reject("IndexDB is not opened yet!");
                     }
                     else{
                         var trans = db.transaction(["typeTestGroup"], "readwrite");
                         var store = trans.objectStore("typeTestGroup");

                         // Get everything in the store;
                         var keyRange = IDBKeyRange.lowerBound(0);
                         var cursorRequest = store.openCursor(keyRange);
                         var typeTestGroup = [];
                         cursorRequest.onsuccess = function(e) {
                             var result = e.target.result;

                             if(result === null || result === undefined)
                             {
                                 deferred.resolve(typeTestGroup);
                             }
                             else{
                                 typeTestGroup.push(result.value);
                                 if(result.value.id > lastIndex){
                                     lastIndex=result.value.id;
                                 }
                               result.continue();
                             }

                         };

                         cursorRequest.onerror = function(e){
                             deferred.reject("Something went wrong!!!");
                         };
                     }

                     return deferred.promise;

                 };

                 var addPreparationFile = function(responseData){
                     var deferred = $q.defer();
                     var typeTestGroup = [];

                     if(db === null){
                         deferred.reject("IndexDB is not opened yet!");
                     }
                     else{
                         var trans = db.transaction(["typeTestGroup"], "readwrite");
                         var store = trans.objectStore("typeTestGroup");
                         
                         for(var i=0; i<responseData.length;i++){
                             
                             var request = store.put(responseData[i]);
                         }

                     }


                     request.onsuccess = function() {
                         deferred.resolve();

                     };

                     request.onerror = function() {
                         deferred.reject("Todo item couldn't be added!");
                     };


                     return deferred.promise;
                 };
                 

                 return {
                     open: open,
                     getPreparationFile: getPreparationFile,
                     addPreparationFile: addPreparationFile
                 };

             }]);
    
    

    basketModule.controller('BasketController', ['$scope','$location','$window','BasketDetailService','AuthenticationService','AuthorizationService','NotificationService','CultureService','typeTestGroupIdb','syncDataService', function($scope,$location,$window,BasketService,authenticationService,authorizationService,NotificationService,CultureService,typeTestGroupIdb,syncDataService) {
        
        
        $scope.disableEdit = function() {
            
            if(authorizationService.hasRole('seed-w20', 'POCARole') || authorizationService.hasRole('seed-w20','POCERole')||authorizationService.hasRole('seed-w20','POCFRole') ||authorizationService.hasRole('seed-w20','POCGRole') ||authorizationService.hasRole('seed-w20','POCHRole')||authorizationService.hasRole('seed-w20','POCBRole')||authorizationService.hasRole('seed-w20','POCCRole')||authorizationService.hasRole('seed-w20','POCDRole')){
                return false;
              
            }else{
                return true;
            }
           
        };
        
        
        
        
        $scope.authorization = function() {
 
            if(authorizationService.hasRole('seed-w20', 'POCARole') || authorizationService.hasRole('seed-w20','POCERole')||authorizationService.hasRole('seed-w20','POCFRole') ||authorizationService.hasRole('seed-w20','POCGRole') ||authorizationService.hasRole('seed-w20','POCHRole')||authorizationService.hasRole('seed-w20','POCBRole')||authorizationService.hasRole('seed-w20','POCCRole')||authorizationService.hasRole('seed-w20','POCDRole')){
            	return true;
            }else{
                return false;
            }
           
        };
        
        $scope.toSearchVehicles = function(){
            $location.url("/cop/SearchMain");
            setTimeout(function(){
                
                var tvvTab = angular.element( document.querySelectorAll( '#tvvTab' ) );
                tvvTab.removeClass('active');   
                
                var tvvTabContent = angular.element( document.querySelectorAll( '#tvv' ) );
                tvvTabContent.removeClass('active');  
                
                
                var vehicleTab = angular.element( document.querySelectorAll( '#vehicleTab' ) );
                vehicleTab.addClass('active');   
                
                var vehicleTabContent = angular.element( document.querySelectorAll( '#searchVehicles' ) );
                vehicleTabContent.addClass('active');   


            },1000);
        }
        $scope.responseData=[];
       var getBasket = function(){
           BasketService.basketResource.getBasket({
               userId: authenticationService.subjectPrincipals().userId
           },function(response){
               $scope.responseData = response;
               $scope.allVehicleInBasketCount = response.length;
               $scope.typeTestGroup = [];
               for(var i=0; i<response.length;i++){
                   var typeTestCheck = true;
                   for(var j=0; j<$scope.typeTestGroup.length;j++){
                       if($scope.typeTestGroup[j].typeOfTestLabel=== response[i].typeOfTestLabel){
                           $scope.typeTestGroup[j].basketDetail.push(response[i]);
                           typeTestCheck = false;
                           break;
                       }
                   }
                  if(typeTestCheck) {
                      var temp = {typeOfTestLabel:response[i].typeOfTestLabel,
                                  basketDetail :[
                                      response[i]
                                  ]
                      };
                      $scope.typeTestGroup.push(temp);
                  }
               }
           },function(){
               
           })   
       };
       
       getBasket();
       
       $scope.deleteConfirmation = function(basket){
         $scope.basketForDelete = basket;
           
           $('#deleteVehicle').modal('show')
       };
       $scope.deleteBasket = function(){
           
           BasketService.basketResource.deleteBasket({
               vehicleFileId : $scope.basketForDelete.entityId
           },function(){
               getBasket();
               NotificationService.notify(CultureService.localize('cop.basket.message.basketdeleteSucess'))
           },function(){
               NotificationService.notify(CultureService.localize('cop.basket.message.basketdeleteError'))
           })
           
       };
       
       $scope.dblClickVehicleFile = function(basket){
           window.location="#!/cop/menu-list?vehicleFileId="+basket.entityId+'&&inBasket='+true;;
       }
       
       
       //CRUD operations
       $scope.preparationFileCaching = function(){
           typeTestGroupIdb.getPreparationFile().then(function(data){
              $scope.typeTestGroups=data;
              $('#customNotifier').modal('show');
              $scope.message = 'cop.basket.message.dataCacheSuccess';
           }, function(err){
               $window.alert(err);
               $('#customNotifier').modal('show');
               $scope.message = 'cop.basket.message.dataCacheFailed';
           });        
           
           //Hit service to get
           BasketService.basketResource.getOfflineData({
               userId: authenticationService.subjectPrincipals().userId
           }, function(response) {
                $scope.userTeamRepresentation = response;
                    $scope.offlineRepresentation = [];
                    
                    $scope.offlineRepresentation.push($scope.userTeamRepresentation);
                    
                    typeTestGroupIdb.addPreparationFile($scope.offlineRepresentation).then(function(){
                        
                        
                    }, function(err){
                        $window.alert(err);
                    
                });

            }, function() {

                NotificationService.notify(CultureService.localize('cop.specificCop.message.error'));
            });
           

       };
     
       $scope.refreshList = function(){
           $scope.preparationFileCaching();
           
           typeTestGroupIdb.getPreparationFile().then(function(data){
               
      //         $scope.typeTestGroup=data;
               
               
           }, function(err){
               $window.alert(err);
           });

       };

       $scope.init = function(){
           
           typeTestGroupIdb.open().then(function(){
               $scope.refreshList();
           });
          

       }
        $scope.basketCaching = function(){
            $scope.init();
        }
        
        $scope.syncData = function(){
            $scope.copLoader = true;
            typeTestGroupIdb.open().then(function(){
            typeTestGroupIdb.getPreparationFile().then(function(data){
                if(data == ''){
                    $scope.copLoader = false;
                    $('#customNotifier').modal('show');
                    $scope.message = 'cop.testCondition.message.errorMessage';
                }
                else{
                    $scope.syncIndexDd=data;
                    delete $scope.syncIndexDd[0].$promise;
                    delete $scope.syncIndexDd[0].$resolved;
                    syncDataService.offlineResource.saveOfflineChanges($scope.syncIndexDd[0], function(){
                        $('#customNotifier').modal('show');
                        $scope.message = 'cop.testCondition.message.successMessage';
                        $scope.copLoader = false;
                    }) 
                }

             }, function(err){
                 $window.alert(err);
                 $('#customNotifier').modal('show');
                 $scope.message = 'cop.testCondition.message.errorMessage';
             });   
            });
        };
           
       //function for button to check network status
       $scope.checkNetwork = function(){
           if (navigator.onLine) {
               $('#onlineMessage').modal('show');
           } else {
               NotificationService.notify(CultureService.localize('cop.basket.message.offlineNow'))
               window.location = 'fragments/cop/views/offline-views/offline-home.html';
           }

       };
       
       
    }]);

            return {
                angularModules : [
                    'Basket'
                ]
            };
        });