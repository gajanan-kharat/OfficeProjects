/**
 * 
 */

define([
        'require', 'module', '{angular}/angular'
        ], function(require, module, angular) {

    var archiveSearch = angular.module('archiveSearch', ['copCommon']);
    
    archiveSearch.factory('archiveSearchService',['$resource', function($resource){
        return{
            archiveSearchResource : $resource('vehiclefile/:path/:chassis/:counterMark/:registration',{
                path : '@path',
                chassis : '@chassis',
                counterMark : '@counterMark',
                registration : '@registration'
                
            },{
                'getArchiveList' : {
                    method : 'GET',
                    params : {
                        path : 'vehiclefilelist'
                    },
                    isArray : true
                }
            })
        }
    }])

    archiveSearch.controller('archiveSearchController', [
                                                           '$scope','$location','$window', '$modal', '$routeParams', 'HistoryService', 'AuthorizationService', 'CultureService', 'NotificationService','archiveSearchService', function($scope,$location,$window, $modal, $routeParams, HistoryService, authorizationService, CultureService, NotificationService,archiveSearchService) {

                   $scope.authentication = true;
                   if (authorizationService.hasRole('seed-w20', 'POCBRole') || authorizationService.hasRole('seed-w20', 'POCCRole')) {
                       $scope.authentication = false;
                   }

                   $scope.chassis = '';
                   $scope.counterMark = '';
                   $scope.registration = '';
                   $scope.searchArchiveCardBoard = function(){
                       if($scope.chassis !== '' || $scope.counterMark !== '' || $scope.registration !== ''){
                           var chassisToSend = 'null';
                           var counterMarkToSend = 'null';
                           var registrationToSend = 'null';
                           
                           if($scope.chassis !== '')
                               chassisToSend = $scope.chassis;
                           if($scope.counterMark !== '')
                               counterMarkToSend = $scope.counterMark;
                           if($scope.registration !== '')
                               registrationToSend = $scope.registration;
                           
                           archiveSearchService.archiveSearchResource.getArchiveList({
                               chassis : chassisToSend,
                               counterMark : counterMarkToSend,
                               registration : registrationToSend
                           },function(response){
                               $scope.archiveVehicleFileList = response
                               
                           },function(){
                               $scope.archiveVehicleFileList = [];
                               $scope.errorMessage = CultureService.localize("cop.archive.message.noVehicle");
                               $('#archiveErrorModal').modal('show');
                           })
                       }else{
                           $scope.errorMessage = CultureService.localize('cop.vehicle.notification.requiredcheckerror');
                           $('#archiveErrorModal').modal('show');
                       }
                       
                   };
                   
                   $scope.toResultSet = function(vehicleId){
                       window.location="#!/cop/menu-list?archivedVehicelFile="+vehicleId;
                   }
                   
                   $scope.refresh = function(){
                       location.reload();
                   }
              
                   
               }]);

    return {
        angularModules : [
                          'archiveSearch'
                          ]
    };
});