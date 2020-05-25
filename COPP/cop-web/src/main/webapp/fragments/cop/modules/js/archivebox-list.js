/**
 * 
 */

define([
        'require', 'module', '{angular}/angular'
        ], function(require, module, angular) {

    var archiveBoxList = angular.module('archiveBoxList', ['copCommon']);
   
    archiveBoxList.factory('archiveBoxList',['$resource',function($resource){
        return{
            archiveBoxResource : $resource('archiveboxs/:path',{
                path : '@path'
            },{
                'getArchive' : {
                    method : 'GET',
                    params : {
                        path : 'archiveboxlist'
                    },
                    isArray : true
                }
            })
        }
    }]);

    archiveBoxList.controller('archiveBoxListController', [
                                                           '$scope','$location','$window', '$modal', '$routeParams', 'HistoryService', 'AuthorizationService', 'CultureService', 'NotificationService','archiveBoxList', function($scope,$location,$window, $modal, $routeParams, HistoryService, authorizationService, CultureService, NotificationService,archiveBoxList) {

                   $scope.authentication = true;
                   if (authorizationService.hasRole('seed-w20', 'POCBRole') || authorizationService.hasRole('seed-w20', 'POCCRole')) {
                       $scope.authentication = false;
                   }

                archiveBoxList.archiveBoxResource.getArchive(function(response){
                    $scope.archiveBoxList = response
                })
                   
                $scope.toResultSet = function(vehicleId){
                    window.location="#!/cop/menu-list?archivedVehicelFile="+vehicleId;
                }
                
                $scope.filterArchiveBox = function(){
                    
                }
                
                   $scope.refresh = function(){
                       location.reload();
                   };
              
                   
               }]);

    return {
        angularModules : [
                          'archiveBoxList'
                          ]
    };
});