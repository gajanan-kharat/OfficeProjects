define([ 'require', 'module', '{angular}/angular' ], function(require, module, angular) {
    var vehicleModule = angular.module('Vehicle', [ 'copCommon' ]);

    vehicleModule.factory('VehicalService', [ '$resource', function($resource) {
        return {
            vehicleResource : $resource('vehicle/:path/:tvvLabel/:countermark/:chassis/:registration/:typeOfTestId/:vehicleFileId/:tvvId', {
                path : '@path',
                tvvLabel : '@tvvLabel',
                countermark : '@countermark',
                chassis : '@chassis',
                registration : '@registration',
                typeOfTestId : '@typeOfTestId',
                vehicleFileId : '@vehicleFileId',
                tvvId : '@tvvId'
            }, {
                'tvvList' : {
                    method : 'GET',
                    params : {
                        path : 'tvvList'
                    },
                    isArray : true
                },
                'getVehicle' : {
                    method : 'GET',
                    params : {
                        path : 'vehicledetail'
                    },
                    isArray : true
                },

                'getTVVFactory' : {
                    method : 'GET',
                    params : {
                        path : 'tvvfactory'
                    },
                    isArray : true
                }

            }),
            vehicleListResource : $resource('vehicle/:path', {
                path : '@path'
            }, {
                'saveVehicle' : {
                    method : 'POST',
                    params : {
                        path : 'vehiclelist'

                    }
                }
            })
        }
    } ]);

    vehicleModule.factory('VehicleBasketService', [ '$resource', function($resource) {
        return {
            vehicleBasketResource : $resource('basket/:path/:userId', {
                path : '@path',
                userId : '@userId'
            }, {
                'getBasket' : {
                    method : 'GET',
                    params : {
                        path : 'BasketCount'
                    }
                }
            })
        }
    } ]);

    vehicleModule.controller('VehicleController',
            [
                    '$scope',
                    'AuthorizationService',
                    'AuthenticationService',
                    'VehicalService',
                    'NotificationService',
                    'CultureService',
                    'VehicleBasketService',
                    'GetTypeOfTestService',
                    'BasketService',
                    function($scope, authorizationService, authenticationService, VehicalService, NotificationService, CultureService,
                            VehicleBasketService, GetTypeOfTestService, BasketService) {

                        $scope.authorization = function() {

                            if (authorizationService.hasRole('seed-w20', 'POCARole') || authorizationService.hasRole('seed-w20', 'POCERole')
                                    || authorizationService.hasRole('seed-w20', 'POCFRole') || authorizationService.hasRole('seed-w20', 'POCGRole')
                                    || authorizationService.hasRole('seed-w20', 'POCHRole')) {
                                return true;
                            } else {
                                return false;
                            }

                        };

                        $scope.isReadOnly = function() {
                            if ($scope.authorization()) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        $scope.currentDate = new Date();
                        $scope.vehicleButton = true;

                        var getBasket = function() {

                            VehicleBasketService.vehicleBasketResource.getBasket({
                                userId : authenticationService.subjectPrincipals().userId
                            }, function(response) {
                                $scope.vehicleCount = response.vehicleCount;
                            }, function() {
                            })

                        };

                        /* Load data at page loading */
                        var loadData = function() {
                            /* * Function for loading TypeOfTest data */
                            GetTypeOfTestService.getTypeOfTest(function(response) {
                                $scope.typeOfTest = response;
                            }, function() {
                                NotificationService.notify(CultureService.localize("cop.settings.typeOfTest.load.errorMessage"));
                            }

                            );

                            getBasket();
                        };

                        loadData();

                        $scope.onTestTypeSelect = function() {
                            $scope.tvvObjList = [];
                            $scope.vehicleButton = true;
                        }

                        /* serch for Tvv */
                        $scope.searchTVV = function() {
                            if ($scope.tvvLabel !== '' && $scope.tvvLabel !== undefined && $scope.typeOfTest.selected !== undefined) {
                                if(($scope.countermark==''||$scope.countermark==undefined )&& ($scope.chassis==''||$scope.chassis==undefined) && ($scope.registration==''||$scope.registration==undefined))
                                {
                                 $scope.errorMessage = CultureService.localize('cop.vehicle.notification.requiredcheckerror');
                                 $('#vehicleErrorDisplayModal').modal('show');
                                 return;
                                }
                                VehicalService.vehicleResource.tvvList({
                                    typeOfTestId : $scope.typeOfTest.selected.typeOfTestId,
                                    tvvLabel : $scope.tvvLabel
                                }, function(response) {
                                    if (response.length !== 0) {
                                        $scope.tvvObjList = response;
                                    } else {
                                        $scope.tvvObjList = [];
                                        $scope.errorMessage = CultureService.localize('cop.vehicle.message.noTVVfound');
                                        $('#vehicleErrorDisplayModal').modal('show');
                                    }

                                }, function() {
                                    $scope.tvvObjList = [];
                                })
                            } else {

                                $scope.errorMessage = CultureService.localize('cop.coastdown.message.reuiredfield');
                                $('#vehicleErrorDisplayModal').modal('show');
                            }
                            ;
                        }
                        $scope.selectTvv = function(selectedTvvId, slectedTvv) {
                            $scope.slectedTvv = slectedTvv;

                            var myClass = angular.element(document.querySelector('#' + selectedTvvId));
                            var selected = true;
                            if ($(myClass).hasClass('vehicle-custom-border')) {
                                $(myClass).removeClass('vehicle-custom-border');
                                selected = false;
                                $scope.vehicleButton = true;
                            }

                            $('.tvvObjClass').removeClass('vehicle-custom-border');

                            if (selected) {
                                $(myClass).addClass('vehicle-custom-border');
                                $scope.vehicleButton = false;
                            }
                            VehicalService.vehicleResource.getTVVFactory({
                                tvvId : $scope.slectedTvv.entityId
                            }, function(responce) {

                                $scope.tvvFactoryList = responce;
                            }, function() {

                            });

                        }

                        $scope.countermark = '';
                        $scope.showCountermark = false;
                        $scope.chassis = '';
                        $scope.showChassis = false;
                        $scope.registration = '';
                        $scope.showRegistration = false;
                        $scope.tvvLabel = '';

                        $scope.validateVehicle = function() {

                            var countermarkTOSend = "null";
                            var chassisToSend = "null";
                            var registrationToSend = "null"
                            if (($scope.countermark.length > 0 || $scope.chassis.length > 0 || $scope.registration.length > 0)
                                    && $scope.tvvLabel.length > 0) {
                                if ($scope.countermark.trim().length > 0) {
                                    $scope.showCountermark = true;
                                    countermarkTOSend = $scope.countermark;
                                }
                                if ($scope.chassis.trim().length > 0) {
                                    $scope.showChassis = true;
                                    chassisToSend = $scope.chassis;
                                }
                                if ($scope.registration.trim().length > 0) {
                                    $scope.showRegistration = true;
                                    registrationToSend = $scope.registration;
                                }

                                VehicalService.vehicleResource.getVehicle({
                                    countermark : countermarkTOSend,
                                    chassis : chassisToSend,
                                    registration : registrationToSend,
                                    typeOfTestId : $scope.typeOfTest.selected.typeOfTestId

                                }, function(response) {

                                    if (response.length === 0) {
                                        $scope.modelYear = '';
                                        $scope.mco2Label = '';
                                        $scope.tvvFactoryList.selected = '';
                                        $('#vehicleCreate').modal('show');
                                    } else {
                                        $scope.allVehicleFiles = [];
                                        $scope.receivedFiles = response
                                        for (var i = 0; i < $scope.receivedFiles.length; i++) {
                                            if ($scope.receivedFiles[i].typeOfTestId !== $scope.typeOfTest.selected.typeOfTestId) {
                                                $scope.allVehicleFiles.push($scope.receivedFiles[i]);
                                            }
                                        }
                                        $('#vehicleDetail').modal('show');
                                    }
                                }, function() {
                                    $scope.errorMessage = CultureService.localize('cop.vehicle.messege.creatingvehiclefile.error');
                                    $('#vehicleErrorDisplayModal').modal('show');
                                })

                            } else {
                                $scope.errorMessage = CultureService.localize('cop.vehicle.notification.requiredcheckerror');
                                $('#vehicleErrorDisplayModal').modal('show');
                            }
                        };

                        $scope.refresh = function() {
                            $scope.countermark = "";
                            $scope.chassis = "";
                            $scope.registration = "";
                            $scope.tvvLabel = "";
                            $scope.typeOfTest.selected = "";
                            $scope.tvvObjList = [];

                        };

                        $scope.saveVehicle = function() {
                            if ($scope.modelYear !== undefined && $scope.modelYear !== '' && $scope.mco2Label !== undefined
                                    && $scope.mco2Label !== '' && $scope.tvvFactoryList.selected !== undefined && $scope.tvvFactoryList.selected !=='') {
                                var countermarkTOSend = $scope.countermark;
                                var chassisToSend = $scope.chassis;
                                var registrationToSend = $scope.registration;
                                if (!$scope.countermark.length > 0) {
                                    countermarkTOSend = null;
                                }

                                if (!$scope.registration.length > 0) {
                                    registrationToSend = null;
                                }
                                if (!$scope.chassis.length > 0) {
                                    chassisToSend = null;
                                }

                               
                                VehicalService.vehicleListResource.saveVehicle({

                                    'counterMark' : countermarkTOSend,
                                    'registrationNumber' : registrationToSend,
                                    'chasisNumber' : chassisToSend,
                                    'carFactoryId' : $scope.tvvFactoryList.selected.entityId,
                                    'modelYear' : $scope.modelYear,
                                    'mCO2I' : ($scope.mco2Label.toString()).replace(/,/g,"."),
                                    'typeOfTestId' : $scope.typeOfTest.selected.typeOfTestId,
                                    'technicalCaseId' : $scope.slectedTvv.technicalCaseId

                                }, function() {
                                    NotificationService.notify(CultureService.localize('cop.vehicle.message.vehicleSveSuccess'));

                                    $scope.modelYear = '';
                                    $scope.mco2Label = '';
                                    $scope.tvvFactoryList.selected = '';
                                }, function() {
                                    $scope.errorMessage = CultureService.localize('cop.basket.message.addBasketError');
                                    $('#vehicleErrorDisplayModal').modal('show');

                                })
                                $('#vehicleCreate').modal('hide');

                            }else{
                            	 $scope.errorMessage = CultureService.localize('cop.vehicle.model.vehicleCreatRequired');
                                 $('#vehicleErrorDisplayModal').modal('show');
                            }
                           
                        };

                        $scope.showAddToBasket = function() {
                            $('#AddToBasket').modal('show')
                        };

                        $scope.addToBasket = function() {
                            var vehicleFileId = [];
                            for (var i = 0; i < $scope.receivedFiles.length; i++) {
                                if ($scope.receivedFiles[i].typeOfTestId === $scope.typeOfTest.selected.typeOfTestId) {
                                    vehicleFileId.push($scope.receivedFiles[i].entityId);
                                }
                            }

                            BasketService.vehicleFileResource.addBasket({
                                'vehicleFileIds' : vehicleFileId

                            }, function() {
                                getBasket();
                                NotificationService.notify(CultureService.localize('cop.basket.message.addBasketSuccess'));
                               

                            }, function() {
                                $scope.errorMessage = CultureService.localize('cop.vehicleFile.basket.error');
                                $('#vehicleErrorDisplayModal').modal('show');
                            })

                        };
                        $scope.myFunction = function(keyEvent) {
                            if (keyEvent.which === 13) {
                                $scope.searchTVV()

                            }
                            ;
                        };
                        
                        /* validation for float */
                        $scope.floatCheck = function(value){
                            var INTEGER_REG = new RegExp("^[-]?[0-9/,.]+$");
                            if(!INTEGER_REG.test(value)){
                                value = value.substring(0,value.length - 1);
                            }
                            return value;
                        };
                        

                    } ]);

    return {
        angularModules : [ 'Vehicle' ]
    };
});