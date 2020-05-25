/**
 * 
 */

define([
        'require', 'module', '{angular}/angular'
        ], function(require, module, angular) {

    var archiveVehicle = angular.module('archiveVehicle', [
                                                           'copCommon'
                                                           ]);

    archiveVehicle.factory('archiveService',['$resource', function($resource){
        return{
            vehicleResource : $resource('vehicle/:path', {
                path : '@path'
                
            }, {
                'getModelYear' : {
                    method : 'GET',
                    params : {
                        path : 'modelyear'
                    },
                    isArray : true
                }
            }),
            vehicleFileResource : $resource('vehiclefile/:path/:chassis/:counterMark/:registration/:typeOfTestId/:archiveBoxId/:vehicleFileId',{ 
              path : '@path',
             chassis : '@chassis',
             counterMark : '@counterMark',
             registration : '@registration',
             typeOfTestId : '@typeOftestId',
             archiveBoxId : '@archiveBoxId',
             vehicleFileId : '@vehicleFileId'
         },{
             'getVehicleFile' : {
                 method : 'GET',
                 params : {
                     path : 'vehicleforarchive'
                 }
             },
             'removeVehicleFile' : {
                 method : 'POST',
                 params : {
                     path : 'vehicleforarchive'
                 }
             }
         }),
            achiveResource : $resource('archiveboxs/:path/:typeOfTestId/:modelYear/:projectFamilyLabel/:fuelLabel/:archiveBoxNumber/:archiveBoxId',{
                path : '@path',
                typeOfTestId : '@typeOfTestId',
                modelYear : '@modelYear',
                projectFamilyLabel : '@projectFamilyLabel',
                fuelLabel : '@fuelLabel',
                archiveBoxNumber: '@archiveBoxNumber',
                archiveBoxId : '@archiveBoxId'
            },{
                'createArchive' : {
                    method : 'POST',
                    params : {
                        path : 'archivebox'
                    }
                },
                'getArchiveBox' : {
                    method : 'GET',
                    params : {
                        path : 'archivebox'
                    }
                },
                'getArchiveBoxByFields' : {
                    method : 'GET',
                    params : {
                        path : 'archiveboxbyfields'
                    }
                },
                'closeArchiveBox' : {
                    method : 'POST',
                    params : {
                        path : 'archive'
                    }
                }
            })
        } 
    }]) 
    archiveVehicle.controller('ArchiveVehicleController', [
                                                           '$scope', '$modal', '$routeParams', 'HistoryService', 'AuthorizationService', 'CultureService', 'NotificationService','GetTypeOfTestService','archiveService','ProjectFamilyService','FuelCommonService', function($scope, $modal, $routeParams, HistoryService, authorizationService, CultureService, NotificationService, GetTypeOfTestService, archiveService, ProjectFamilyService, FuelCommonService) {

                                                               $scope.authentication = true;
                                                               if (authorizationService.hasRole('seed-w20', 'POCBRole') || authorizationService.hasRole('seed-w20', 'POCCRole')) {
                                                                   $scope.authentication = false;
                                                               }

                                                               var dropDownArchive = function(){
                                                                   /*** Function for loading TypeOfTest data */
                                                                   GetTypeOfTestService.getTypeOfTest(function(response) {
                                                                       $scope.typeOfTestList = response;
                                                                   }, function() {
                                                                       NotificationService.notify(CultureService.localize("cop.settings.typeOfTest.load.errorMessage"));
                                                                   }

                                                                   );

                                                                   /*** Function to load ModelYear list **/
                                                                   archiveService.vehicleResource.getModelYear(function(response){
                                                                       $scope.modelYearList = response;
                                                                   },function(){
                                                                       NotificationService.notify(CultureService.localize("cop.settings.typeOfTest.load.errorMessage"));
                                                                   })

                                                                   /*** Function to load projectCodeFamily list */
                                                                   ProjectFamilyService.ProjectFamilyResource.getAllProjectFamilyNames(function(response){
                                                                       $scope.projectFamilyList = response;
                                                                   },function(){
                                                                       NotificationService.notify(CultureService.localize("cop.settings.typeOfTest.load.errorMessage"));
                                                                   })

                                                                   /** Function to load Fuel list */
                                                                   FuelCommonService.FuelResource.getAllFuelNames(function(response){
                                                                       $scope.fuelList = response;
                                                                   },function(){
                                                                       NotificationService.notify(CultureService.localize("cop.settings.typeOfTest.load.errorMessage"));
                                                                   })
                                                                   $scope.archiveBoxNumber = null;
                                                                   
                                                               };
                                                               dropDownArchive();
                                                               
                                                               $scope.showArchiveCreate = function(){
                                                                   dropDownArchive();
                                                                   
                                                                   $('#createArchiveModal').modal('show');
                                                               }

                                                               $scope.createArchive = function(){
                                                                  
                                                                   if($scope.typeOfTestList.selected !== undefined && $scope.modelYearList.selected !== undefined && $scope.projectFamilyList.selected !== undefined && $scope.fuelList.selected !== undefined){
                                                                       archiveService.achiveResource.createArchive({
                                                                           typeOfTestId : $scope.typeOfTestList.selected.typeOfTestId,
                                                                           modelYear : $scope.modelYearList.selected,
                                                                           projectFamilyLabel : $scope.projectFamilyList.selected,
                                                                           fuelLabel : $scope.fuelList.selected
                                                                       }, function(response){
                                                                           if(response.alreadyExist){
                                                                               $scope.errorMessage = CultureService.localize("cop.archive.message.cartoon") + response.archiveBoxNumber + CultureService.localize("cop.archive.message.archiveExist");
                                                                               $('#archiveErrorModal').modal('show');
                                                                           }else{
                                                                           $scope.archiveBoxNumber = response.archiveBoxNumber
                                                                           }
                                                                       },function(){
                                                                       });
                                                                   }else{
                                                                       $scope.errorMessage = CultureService.localize("cop.archive.message.archiveCreateRequired");
                                                                       $('#archiveErrorModal').modal('show');
                                                                   }
                                                               }
                                                               
                                                               $scope.searchArchiveBox = function(){
                                                                   if($scope.archiveBoxNumber !== null && $scope.archiveBoxNumber !== ''){
                                                                   archiveService.achiveResource.getArchiveBox({
                                                                       archiveBoxNumber : $scope.archiveBoxNumber
                                                                   }, function(response){
                                                                       $scope.archiveBoxLoaded = response;
                                                                   },function(){
                                                                       $scope.archiveBoxLoaded = undefined;
                                                                       $scope.errorMessage = CultureService.localize("cop.archive.message.noCaedboard");
                                                                       $('#archiveErrorModal').modal('show');
                                                                   })
                                                                   }else{
                                                                       $scope.errorMessage = CultureService.localize("cop.archive.message.archiveBoxRequired");
                                                                       $('#archiveErrorModal').modal('show');
                                                                   }
                                                               }
                                                               
                                                               $scope.loadArchive = function(){
                                                                   if($scope.archiveBoxLoaded !== undefined && $scope.archiveBoxLoaded.entityId !== null)
                                                                       $scope.archiveBox = $scope.archiveBoxLoaded;
                                                                   else
                                                                       $scope.archiveBox = null;
                                                                   
                                                                   $scope.archiveBoxNumber = null;
                                                                   $scope.archiveBoxLoaded = undefined;
                                                               }
                                                               
                                                               $scope.archiveBoxValider = function(){
                                                                   
                                                                   if($scope.typeOfTestList.selectedSearch !== undefined && $scope.modelYearList.selectedSearch !== undefined && $scope.projectFamilyList.selectedSearch !== undefined && $scope.fuelList.selectedSearch !== undefined){
                                                                        archiveService.achiveResource.getArchiveBoxByFields({
                                                                           typeOfTestId : $scope.typeOfTestList.selectedSearch.typeOfTestId,
                                                                           modelYear : $scope.modelYearList.selectedSearch,
                                                                           projectFamilyLabel : $scope.projectFamilyList.selectedSearch,
                                                                           fuelLabel : $scope.fuelList.selectedSearch
                                                                       }, function(response){
                                                                           $scope.archiveBox = response;
                                                                       },function(){
                                                                           $scope.archiveBox = null;
                                                                           $scope.errorMessage = CultureService.localize("cop.archive.message.noCaedboard");
                                                                           $('#archiveErrorModal').modal('show');
                                                                           
                                                                       });
                                                                   }else{
                                                                       $scope.errorMessage = CultureService.localize("cop.archive.message.archiveCreateRequired");
                                                                       $('#archiveErrorModal').modal('show');
                                                                   };
                                                                   
                                                               };
                                                               $scope.chassis = '';
                                                               $scope.counterMark = '';
                                                               $scope.registration = '';
                                                               $scope.addVehicleFile = function(){
                                                                   
                                                                   if($scope.archiveBox !== undefined && $scope.archiveBox !== null){
                                                                   if($scope.chassis !== '' || $scope.counterMark !=='' || $scope.registration !==''){
                                                                       
                                                                       if($scope.chassis ==='')
                                                                           $scope.chassis = 'null';
                                                                       if($scope.counterMark ==='')
                                                                           $scope.counterMark = 'null';
                                                                       if($scope.registration === '')
                                                                           $scope.registration = 'null';
                                                                               
                                                                        
                                                                       archiveService.vehicleFileResource.getVehicleFile({
                                                                         chassis :  $scope.chassis,
                                                                         counterMark : $scope.counterMark,
                                                                         registration : $scope.registration,
                                                                         typeOfTestId : $scope.archiveBox.typeOfTestRepresentation.typeOfTestId,
                                                                         archiveBoxId : $scope.archiveBox.entityId
                                                                     },function(response){
                                                                         $scope.archiveBox.vehicleFilesRepresentation.push(response)
                                                                         $scope.chassis = '';
                                                                         $scope.counterMark = '';
                                                                         $scope.registration = '';
                                                                     }, function(){
                                                                         $scope.chassis = '';
                                                                         $scope.counterMark = '';
                                                                         $scope.registration = '';
                                                                         $scope.errorMessage = CultureService.localize("cop.archive.message.noVehicle");
                                                                         $('#archiveErrorModal').modal('show');
                                                                     });
                                                                   }else{
                                                                       $scope.errorMessage = CultureService.localize('cop.vehicle.notification.requiredcheckerror');
                                                                       $('#archiveErrorModal').modal('show');
                                                                   }
                                                                   }else{
                                                                       $scope.errorMessage = CultureService.localize('cop.archibve.message.noArchiveSelected');
                                                                       $('#archiveErrorModal').modal('show');
                                                                   }
                                                                   
                                                               };
                                                               $scope.removeVehicleFileModal = function(vehicleFile, index){
                                                                   
                                                               $scope.vehicleFileObj = vehicleFile;
                                                               $scope.indexToRemove = index;
                                                               
                                                               if($scope.vehicleFileObj.vehicleRepresentation.registrationNumber!== null){
                                                                   
                                                                   $scope.removeConfirmation = CultureService.localize("cop.archive.message.removeVehicle1") + $scope.vehicleFileObj.vehicleRepresentation.registrationNumber + CultureService.localize("cop.archive.message.removeVehicle4");
                                                               }else if(vehicleFile.vehicleRepresentation.chasisNumber !== null){
                                                                   
                                                                   $scope.removeConfirmation = CultureService.localize("cop.archive.message.removeVehicle2") + $scope.vehicleFileObj.vehicleRepresentation.registrationNumber + CultureService.localize("cop.archive.message.removeVehicle4");
                                                               }else{
                                                                   $scope.removeConfirmation = CultureService.localize("cop.archive.message.removeVehicle3") + $scope.vehicleFileObj.vehicleRepresentation.registrationNumber + CultureService.localize("cop.archive.message.removeVehicle4");
                                                               }
                                                               
                                                               $('#vehicleFileRemove').modal('show');
                                                                   
                                                               };
                                                               
                                                               $scope.removeVehicleFile = function(){
                                                                   archiveService.vehicleFileResource.removeVehicleFile({
                                                                       vehicleFileId : $scope.vehicleFileObj.entityId
                                                                       
                                                                   },function(){
                                                                       $scope.archiveBox.vehicleFilesRepresentation.splice($scope.indexToRemove, 1); 
                                                                   }, function(){
                                                                       
                                                                   });
                                                               };
                                                               
                                                               $scope.archiveCloseConfirmation = function(){
                                                                   if($scope.archiveBoxLoaded !== undefined && $scope.archiveBoxLoaded.entityId !== null){
                                                                   $scope.removeConfirmation = CultureService.localize("cop.archive.message.archiveCloseConfirmation")+$scope.archiveBoxLoaded.archiveBoxNumber +' ?';
                                                                 
                                                                   $('#archiveCloseModal').modal('show');
                                                                   }
                                                               };
                                                               
                                                               $scope.archiveClose = function(){
                                                                   archiveService.achiveResource.closeArchiveBox({
                                                                       archiveBoxId : $scope.archiveBoxLoaded.entityId
                                                                   },function(){
                                                                       if($scope.archiveBox !== undefined && $scope.archiveBox !== null && $scope.archiveBox.entityId === $scope.archiveBoxLoaded.entityId)
                                                                           $scope.archiveBox = null;
                                                                       
                                                                       $scope.archiveBoxLoaded = undefined;
                                                                       $scope.archiveBoxNumber = null;
                                                                   },function(){
                                                                       
                                                                   })
                                                                   
                                                               };
                                                               
                                                               $scope.refresh = function(){
                                                                   location.reload();
                                                               };
                                                               
                                                           }]);

    return {
        angularModules : [
                          'archiveVehicle'
                          ]
    };
});