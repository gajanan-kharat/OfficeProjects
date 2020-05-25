define([
        'require', 'module', '{angular}/angular'
], function(require, module, angular) {

    var receptionFile = angular.module('ReceptionFile', []);

    receptionFile.factory('ReceptionFileService', [
            '$resource',

            function($resource) {
                return {
                    ReceptionFileResource : $resource('ReceptionFileReference/:path/:entityId', {
                        path : '@path',
                        entityId : '@entityId'

                    }, {
                        'getAllReceptionFile' : {
                            method : 'GET',
                            params : {
                                path : 'AllReceptionFile'
                            },
                            isArray : true
                        },

                        'saveReceptionFile' : {
                            method : 'POST',
                            params : {
                                path : 'ReceptionFile'
                            }

                        },

                        'getUserRepresentation' : {
                            method : 'GET',
                            params : {
                                path : 'UserRepresentation'
                            }

                        },
                        'genreatePdfOfReceptionFiles' : {
                            method : 'POST',
                            params : {
                                path : 'ReceptionFilePdfGenerate'
                            },

                        },
                        'sendReceptionFilePdfEmail' : {
                            method : 'POST',
                            params : {
                                path : 'ReceptionFilePdfEmail'
                            },

                        },

                        'searchLdapUserUsingEmail' : {
                            method : 'GET',
                            params : {
                                path : 'LDAPUserSearch'
                            },
                            isArray : true

                        },'getVehicleFile' : {
                            method : 'GET',
                            params : {
                                path : 'VehicleFileReceptionFile'
                            },
                    
                        }

                    })
                };
            }
    ]);
    
    receptionFile.service('VehicleReceptionSharedService',function(){
        var fromReceptionToVehicleFlag=false;
        var addedVehicleFile;

        return {
            getFlag: function () {
                return fromReceptionToVehicleFlag;
            },
            setFlag: function(value) {
                fromReceptionToVehicleFlag= value;
            },
            getVehicleFile  :function(){
                return addedVehicleFile;
            },
            setVehicleFile  :function(value){
                addedVehicleFile=value;
            }
            
            
        };
    });

				

    receptionFile.controller('ReceptionFileController', [
            '$scope', '$resource', '$http', '$location', '$route', '$routeParams', '$interval', '$templateCache', 'ReceptionFileService', 'NotificationService', 'CultureService', 'AuthenticationService', 'HistoryService', 'AuthorizationService','VehicleReceptionSharedService', function($scope, $resource, $http, $location, $route, $routeParams, $interval, $templateCache, ReceptionFileService, NotificationService, CultureService, authenticationService, HistoryService, authorizationService,VehicleReceptionSharedService) {

                $scope.auth = true;
                $scope.email = {};
                $scope.email.selected = [];
                $scope.email.validUserList = [];
                $scope.date = new Date();
                $scope.receptionFileList=[];
                $scope.vehicleFile={};
                $scope.isValidated = true;

                if (authorizationService.hasRole('seed-w20', 'POCBRole') || authorizationService.hasRole('seed-w20', 'POCCRole') || authorizationService.hasRole('seed-w20', 'POCMRole')|| authorizationService.hasRole('seed-w20', 'POCNRole')|| authorizationService.hasRole('seed-w20', 'POCORole')) {
                    $scope.auth = false;
                }

                var getUserRepresentation = function() {

                    ReceptionFileService.ReceptionFileResource.getUserRepresentation({
                        entityId : authenticationService.subjectPrincipals().userId
                    }, function(response) {
                        $scope.userTeamRepresentation = response;

                    }, function() {

                        NotificationService.notify(CultureService.localize('cop.specificCop.message.error'));
                    })
                };

            function getVehicleFileData(vehicleFileEntityId){
                    var  receptionFile = ReceptionFileService.ReceptionFileResource.getVehicleFile({
                            entityId : vehicleFileEntityId
                        }, function(success) {
                            if(receptionFile.vehicleFileEntityId !== null){
                            var vehicleCheck = true;
                            for(var i=0; i< $scope.receptionFileList.length; i++){
                                if(receptionFile.vehicleFileEntityId === $scope.receptionFileList[i].vehicleFileEntityId){
                                    vehicleCheck = false;
                                    break;
                                }
                            }
                            if(vehicleCheck){
                            $scope.receptionFileList.push(receptionFile);
                            }
                            }
                        },  function() {
                            NotificationService.notify(CultureService.localize('cop.specificCop.message.error'));
                        });
                
            }           
                var getVehicleFileSearchData=function(){
                    var vFiles=VehicleReceptionSharedService.getVehicleFile();
                    for ( var v in vFiles) {
                        getVehicleFileData(vFiles[v]);
                        }
                
                };
                
                 
                
                $scope.getAllReceptionFile = function() {
                    $scope.receptionFileList = [];
                    var receptionFileList = ReceptionFileService.ReceptionFileResource.getAllReceptionFile({
                        entityId : authenticationService.subjectPrincipals().userId
                    }, function() {
                        if($scope.receptionFileList.length<=0){
                            
                            $scope.receptionFileList = receptionFileList;
                        }
                        else{
                            var tempArray=  $scope.receptionFileList ;
                            $scope.receptionFileList=receptionFileList;
                            for(var i in tempArray){
                                $scope.receptionFileList.push(tempArray[i]);
                            }
                        }
                        $scope.numberOfVechicleFile = CultureService.localize('cop.receptionFile.vehicleFileList.count') + receptionFileList.length;
                        $scope.receptionFileListSelected = [];
                        if(VehicleReceptionSharedService.getFlag()){    
                            getVehicleFileSearchData();
                            VehicleReceptionSharedService.setFlag(false);
                        }
                    }, function() {

                        NotificationService.notify(CultureService.localize('cop.specificCop.message.error'));
                    });
                    getUserRepresentation();
                    
                };

            
                
                $scope.receptionFileGrid = {
                    enableColumnMenus : false,
                    enableCellEditOnFocus : true,
                    enableColumnResizing : true,
                    columnDefs : [
                            {
                                name : 'CounterMark',
                                displayName : 'Contremarque',
                                field : 'counterMark',
                                enableCellEdit : false,
                                cellTooltip : function(row) {
                                    return row.entity.counterMark
                                },
                                headerTooltip : function(col) {
                                    return col.displayName;
                                }
                            }, {
                                name : 'chasisNumber',
                                displayName : 'Chassis',
                                field : 'chasisNumber',
                                enableCellEdit : false,
                                cellTooltip : function(row) {
                                    return row.entity.chasisNumber
                                },
                                headerTooltip : function(col) {
                                    return col.displayName;
                                }
                            }, {
                                name : 'registration',
                                displayName : 'Immatriculation',
                                field : 'registration',
                                enableCellEdit : false,
                                cellTooltip : function(row) {
                                    return row.entity.registration
                                },
                                headerTooltip : function(col) {
                                    return col.displayName;
                                }
                            }, {
                                name : 'vehicle',
                                displayName : 'Véhicule',
                                field : 'vehicle',
                                enableCellEdit : false,
                                cellTooltip : function(row) {
                                    return row.entity.vehicle
                                },
                                headerTooltip : function(col) {
                                    return col.displayName;
                                }
                            }, {
                                name : 'genderTest',
                                displayName : 'Genre d’essai',
                                field : 'genderTest',
                                enableCellEdit : false,
                                cellTooltip : function(row) {
                                    return row.entity.genderTest
                                },
                                headerTooltip : function(col) {
                                    return col.displayName;
                                }
                            }, {
                                name : 'userIdLastNameFirstName',
                                displayName : 'User : NOM, Prénom',
                                field : 'userIdLastNameFirstName',
                                enableCellEdit : false,
                                cellTooltip : function(row) {
                                    return row.entity.userIdLastNameFirstName
                                },
                                headerTooltip : function(col) {
                                    return col.displayName;
                                }
                            }, {
                                name : 'reservation',
                                displayName : 'Réserve',
                                field : 'reservation',
                                enableCellEdit : $scope.auth,
                                cellTooltip : function(row) {
                                    return row.entity.reservation
                                },
                                headerTooltip : function(col) {
                                    return col.displayName;
                                },
                                editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
                            }, {
                                name : 'parkingNumber',
                                displayName : 'N° Box',
                                field : 'parkingNumber',
                                enableCellEdit : $scope.auth,
                                cellTooltip : function(row) {
                                    return row.entity.parkingNumber
                                },
                                headerTooltip : function(col) {
                                    return col.displayName;
                                },
                                editableCellTemplate : '<div class="ui-grid-cell-contents"><input type="INPUT_TYPE" ui-grid-editor style="height:28px;"  maxlength="255" ng-model="MODEL_COL_FIELD"/></div>'
                            },
                    ],

                    data : 'receptionFileList',
                    onRegisterApi : function(gridApi) {

                        // set gridApi on scope
                        $scope.gridApi = gridApi;

                        gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {
								$scope.isValidated = false;
                            if (newValue !== oldValue) {
                                rowentity.edited = true;

                            }

                        });

                    }

                };
					$scope.isValidated = true;
                $scope.saveReceptionFile = function() {
                    $scope.receptionFileListSelected = [];
                    VehicleReceptionSharedService.setFlag(false);
                    for (var i = 0; i < $scope.receptionFileList.length; i++) {
                        if ($scope.receptionFileList[i]['edited'] === true) {
                            $scope.receptionFileListSelected.push($scope.receptionFileList[i]);
                        }
                    }

                    if ($scope.receptionFileListSelected.length > 0) {

                        var objectToSend = {
                            'receptionFileRepresentationsList' : $scope.receptionFileListSelected
                        };

                        ReceptionFileService.ReceptionFileResource.saveReceptionFile(objectToSend, function(success) {

                            NotificationService.notify(CultureService.localize('cop.specificCop.message.successMessage'));
                            
                            $scope.getAllReceptionFile();
                        }, function() {
                            NotificationService.notify(CultureService.localize('cop.specificCop.message.error'));
                        });

                    }

                };

                $scope.sendEmailToValidUser = function() {

                    if ($scope.email.validUserList !== undefined && $scope.email.validUserList.length > 0) {
                        $scope.receptionFileList[0].emailIdList = $scope.email.validUserList;

                        var objectToSend = {
                            'receptionFileRepresentationsList' : $scope.receptionFileList
                        };

                        ReceptionFileService.ReceptionFileResource.sendReceptionFilePdfEmail(objectToSend, function() {
                            NotificationService.notify(CultureService.localize('cop.receptionFile.pdf.mail.message.success'));
                            $scope.email.validUserList = [];

                        }, function() {

                            NotificationService.notify(CultureService.localize('cop.specificCop.message.error'));
                        });
                    } else {
                        NotificationService.notify(CultureService.localize('cop.receptionFile.mail.receipt.error'));
                    }

                };

                $scope.sendPdfToValidUser = function() {
                    if ($scope.email.selected.length > 0) {
                        if ($scope.receptionFileList.length > 0) {

                            var objectToSend = {
                                'receptionFileRepresentationsList' : $scope.receptionFileList
                            };

                            ReceptionFileService.ReceptionFileResource.genreatePdfOfReceptionFiles(objectToSend, function(success) {

                                NotificationService.notify(CultureService.localize('cop.receptionFile.pdf.generation.success'));

                                if ($scope.email.selected !== undefined) {
                                    $scope.sendEmailToValidUser();
                                }

                            }, function() {
                                NotificationService.notify(CultureService.localize('cop.receptionFile.pdf.generation.error'));
                            });

                        } else {
                            NotificationService.notify(CultureService.localize('cop.receptionFile.mail.emptyList'));
                        }
                    } else {
                        NotificationService.notify(CultureService.localize('cop.receptionFile.valid.user.error'));
                    }

                };

                $scope.searchValidUser = function(value) {

                    if (value.length > 3) {
					$scope.email.validUserList = [];
                        ReceptionFileService.ReceptionFileResource.searchLdapUserUsingEmail({
                            entityId : value
                        }, function(response) {
						
                            if (response.length > 0 && response != null) {
                                for (var i = 0; i < response.length; i++) {
                                    if ($scope.email.validUserList.indexOf(response[i]) < 0)
                                        $scope.email.validUserList.push(response[i]);
                                }
                            }

                        }, function() {
                            NotificationService.notify(CultureService.localize('cop.specificCop.message.error'));
                        });
                    }
                };

                $scope.sendReceptionMail = function() {
                    $scope.email.selected = [];
                    $('#showReceptionMailBox').modal('show');
                };

                $scope.refresh = function() {
                    location.reload();
                };
				$scope.sendMailOnCondition = function() {
				    if(!$scope.isValidated){
				        $('#showModificationModal').modal('show');
				    }
				    else{
				        $scope.sendReceptionMail();
				    }


				};
				
				$scope.sendReceptionMail = function(){
                    $scope.email.selected = [];
                    $('#showReceptionMailBox').modal('show');
				}
				
				$scope.saveModifiedData = function(){
				    $scope.saveAll()
				    $('#showModificationModal').modal('hide');
				    setTimeout(function(){
				        $('#showReceptionMailBox').modal('show');
				    },1000)
				}

                $scope.saveAll = function() {

                    $scope.saveReceptionFile();

                }
                
                
                $scope.searchVehicle = function(){
                    VehicleReceptionSharedService.setFlag(true);
                    $location.path("cop/SearchMain");
                 }

            }
    ]);

    return {
        angularModules : [
            'ReceptionFile'
        ]
    };
});
