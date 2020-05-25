define([ 'require', 'module', '{angular}/angular' ], function(require, module, angular) {
    var globalModule = angular.module('VehicleFile', ['ReceptionFile']);
    globalModule.factory('VehicleSearchService', [ '$resource', function($resource) {
        return {
            vehicleSearchResource : $resource('vehiclefile/:path', {
                path : '@path'
            }, {
                'getVehicleSearchRep' : {
                    method : 'GET',
                    params : {
                        path : 'seachvehiclefile'
                    }
                }
            })
        };
    } ]);
    // Calling resource For vehicle search STARTS------------------
    globalModule.factory('SearchVehicleService', [ '$resource', function($resource) {
        return {
            searchVehicleResource : $resource('vehiclefile/:path', {
                path : '@path'

            }, {
                'searchVehicle' : {
                    method : 'POST',
                    params : {
                        path : 'seachvehiclefile'

                    },
                    isArray : true
                }
            })
        };
    } ]);
    // ------------------ENDS------
    // -------------Add to basket Code starts ------------------
    globalModule.factory('BasketService', [ '$resource', function($resource) {
        return {
            vehicleFileResource : $resource('vehiclefile/:path', {
                path : '@path'
            }, {
                'addBasket' : {
                    method : 'POST',
                    params : {
                        path : 'basket'
                    },
                    isArray : false
                }
            })
        }
    } ])

    // -------------------------ENDS-------------

    globalModule
            .controller('VehicleFileController',
                    [
                            '$scope',
                            '$location',
                            '$routeParams',
                            'NotificationService',
                            'CultureService',
                            'VehicleSearchService',
                            'SearchVehicleService',
                            'BasketService',
                            'AuthorizationService',
                            'VehicleReceptionSharedService',
                            function($scope, $location, $routeParams, NotificationService, CultureService, VehicleSearchService,
                                    SearchVehicleService, BasketService,authorizationService,VehicleReceptionSharedService) {

                                /*----------------------------------------Authorization----------------------------------------*/

                                $scope.authorization = function(role) {
                                    return authorizationService.hasRole('seed-w20', role);
                                };
                                $scope.hasCopRole = function(seedW20, role) {
                                    return authorizationService.hasRole(seedW20, role);
                                   
                                };
                                /*-----------------------------------------------------------------------------*/
                                $scope.projectCodeFamilyList = [];
                                $scope.emsIdList = [];
                                $scope.bodyWorkList = [];
                                $scope.brandIdList = [];
                                $scope.engineIdList = [];
                                $scope.gearBoxIdList = [];
                                $scope.fuelTypeIdList = [];
                                $scope.fuelIdList = [];
                                $scope.countryIdList = [];
                                $scope.modelYrList = [];
                                $scope.statusIdList = [];
                                $scope.filteredVehicleItems = [];
                                
                                $scope.statusList = [];
                                $scope.modelYear = [];
                                $scope.countryList = [];
                                $scope.fuelList = [];
                                $scope.fuelTypeList = [];
                                $scope.gearBoxList = [];
                                $scope.engineList = [];
                                $scope.emsList = [];
                                $scope.carBrandList = [];
                                $scope.bodyWorkList = [];
                                $scope.projectCodeFamilyList = [];
                                $scope.selectedVehicleFileIds = [];

                                // ------------------To get filter values
                                // STARTS--------------

                                $scope.getVehicleSearchRep = function() {
                                    $scope.vehicleModelRepresentation = VehicleSearchService.vehicleSearchResource.getVehicleSearchRep(function() {
                                    }, function() {
                                    });
                                };
                                
                                $scope.getVehicleSearchFlags = function(){
                                    $scope.rgActive = false;
                                    $scope.tgActive = false;
                                    $scope.tvvActive = false;
                                }
                                $scope.getVehicleSearchRep();
                                // ------------ vehicleSearchRepresentation to
                                // send to resource for
                                // filter=----------------------
                                $scope.vehicleSearchRepresentation = {
                                    tvvLabel : null,
                                    projectCodeFamilyList : [],
                                    bodyWorkList : [],
                                    carBrandList : [],
                                    emsList : [],
                                    engineList : [],
                                    gearBoxList : [],
                                    fuelTypeList : [],
                                    fuelList : [],
                                    countryList : [],
                                    modelYear : [],
                                    counterMark : null,
                                    chasisNumber : null,
                                    registrationNumber : null,
                                    statusList : [],
                                    userId : null,
                                };
                                // -------setting different values selected from
                                // different dropdown
                                $scope.setProjectVehicle = function(projectCodeLabel) {
                                    $scope.projectCodeFamilyList.push(projectCodeLabel);
                                };
                                $scope.removeProjectVehicle = function(projectCodeLabel) {
                                    for (var i = 0; i < $scope.projectCodeFamilyList.length; i++) {
                                        if ($scope.projectCodeFamilyList[i] === projectCodeLabel) {
                                            $scope.projectCodeFamilyList.splice(i, 1);
                                            break;
                                        }
                                    }

                                };
                                $scope.setEmsId = function(emsId) {
                                    $scope.emsIdList.push(emsId);
                                };
                                $scope.removeEmsId = function(emsId) {
                                    for (var i = 0; i < $scope.emsIdList.length; i++) {
                                        if ($scope.emsIdList[i] === emsId) {
                                            $scope.emsIdList.splice(i, 1);
                                            break;
                                        }
                                    }

                                };
                                $scope.setBodyWork = function(bodyworkId) {
                                    $scope.bodyWorkList.push(bodyworkId);
                                };
                                $scope.removeBodyWork = function(bodyworkId) {
                                    for (var i = 0; i < $scope.bodyWorkList.length; i++) {
                                        if ($scope.bodyWorkList[i] === bodyworkId) {
                                            $scope.bodyWorkList.splice(i, 1);
                                            break;
                                        }
                                    }

                                };
                                $scope.setCarBrand = function(carBrandId) {
                                    $scope.brandIdList.push(carBrandId);
                                };
                                $scope.removeCarBrand = function(carBrandId) {
                                    for (var i = 0; i < $scope.brandIdList.length; i++) {
                                        if ($scope.brandIdList[i] === carBrandId) {
                                            $scope.brandIdList.splice(i, 1);
                                            break;
                                        }
                                    }

                                };
                                $scope.setEngine = function(engineId) {
                                    $scope.engineIdList.push(engineId);
                                };

                                $scope.removeEngine = function(engineId) {
                                    for (var i = 0; i < $scope.engineIdList.length; i++) {
                                        if ($scope.engineIdList[i] === engineId) {
                                            $scope.engineIdList.splice(i, 1);
                                            break;
                                        }
                                    }

                                };
                                $scope.setGearBox = function(gearBoxId) {
                                    $scope.gearBoxIdList.push(gearBoxId);
                                };
                                $scope.removeGearBox = function(gearBoxId) {
                                    for (var i = 0; i < $scope.gearBoxIdList.length; i++) {
                                        if ($scope.gearBoxIdList[i] === gearBoxId) {
                                            $scope.gearBoxIdList.splice(i, 1);
                                            break;
                                        }
                                    }

                                };
                                $scope.setFuelType = function(fuelTypeId) {
                                    $scope.fuelTypeIdList.push(fuelTypeId);
                                };
                                $scope.removeFuelType = function(fuelTypeId) {
                                    for (var i = 0; i < $scope.fuelTypeIdList.length; i++) {
                                        if ($scope.fuelTypeIdList[i] === fuelTypeId) {
                                            $scope.fuelTypeIdList.splice(i, 1);
                                            break;
                                        }
                                    }

                                };
                                $scope.setFuel = function(fuelId) {
                                    $scope.fuelIdList.push(fuelId);
                                };
                                $scope.removeFuel = function(fuelId) {
                                    for (var i = 0; i < $scope.fuelIdList.length; i++) {
                                        if ($scope.fuelIdList[i] === fuelId) {
                                            $scope.fuelIdList.splice(i, 1);
                                            break;
                                        }
                                    }

                                };
                                $scope.setCountry = function(countryId) {
                                    $scope.countryIdList.push(countryId);
                                };
                                $scope.removeCountry = function(countryId) {
                                    for (var i = 0; i < $scope.countryIdList.length; i++) {
                                        if ($scope.countryIdList[i] === countryId) {
                                            $scope.countryIdList.splice(i, 1);
                                            break;
                                        }
                                    }

                                };
                                $scope.setModelYear = function(modelYear) {
                                    $scope.modelYrList.push(modelYear);
                                };
                                $scope.removeModelYear = function(modelYr) {
                                    for (var i = 0; i < $scope.modelYrList.length; i++) {
                                        if ($scope.modelYrList[i] === modelYr) {
                                            $scope.modelYrList.splice(i, 1);
                                            break;
                                        }
                                    }
                                };
                                $scope.setStatus = function(statusId) {
                                    $scope.statusIdList.push(statusId);
                                };
                                $scope.removeStatus = function(statusId) {
                                    for (var i = 0; i < $scope.statusIdList.length; i++) {
                                        if ($scope.statusIdList[i] === statusId) {
                                            $scope.statusIdList.splice(i, 1);
                                            break;
                                        }
                                    }

                                };
                                // ---------------------
                                // ----------------- condition to check any
                                // filter is selected or not STARTS ----------
                                $scope.flag=VehicleReceptionSharedService.getFlag();
                                $scope.checkConditions = function() {
                                    
                                    
                                    if($scope.flag && $scope.selectedVehicleFileIds.length>0 ){
                                        VehicleReceptionSharedService.setVehicleFile($scope.selectedVehicleFileIds);
                                        $location.path("cop/ReceptionFile");
                                    }
                                    else{
                                        
                                        $scope.vehicleSearchRepresentation.projectCodeFamilyList = $scope.projectCodeFamilyList;
                                        $scope.vehicleSearchRepresentation.bodyWorkList = $scope.bodyWorkList;
                                        $scope.vehicleSearchRepresentation.carBrandList = $scope.brandIdList;
                                        $scope.vehicleSearchRepresentation.emsList = $scope.emsIdList;
                                        $scope.vehicleSearchRepresentation.engineList = $scope.engineIdList;
                                        $scope.vehicleSearchRepresentation.gearBoxList = $scope.gearBoxIdList;
                                        $scope.vehicleSearchRepresentation.fuelTypeList = $scope.fuelTypeIdList;
                                        $scope.vehicleSearchRepresentation.fuelList = $scope.fuelIdList;
                                        $scope.vehicleSearchRepresentation.countryList = $scope.countryIdList;
                                        $scope.vehicleSearchRepresentation.statusList = $scope.statusIdList;
                                        $scope.vehicleSearchRepresentation.modelYear = $scope.modelYrList;
                                        if (($scope.vehicleSearchRepresentation.tvvLabel != null && !!$scope.vehicleSearchRepresentation.tvvLabel)
                                                || $scope.vehicleSearchRepresentation.projectCodeFamilyList.length > 0
                                                || $scope.vehicleSearchRepresentation.bodyWorkList.length > 0
                                                || $scope.vehicleSearchRepresentation.carBrandList.length > 0
                                                || $scope.vehicleSearchRepresentation.emsList.length > 0
                                                || $scope.vehicleSearchRepresentation.engineList.length > 0
                                                || $scope.vehicleSearchRepresentation.gearBoxList.length > 0
                                                || $scope.vehicleSearchRepresentation.fuelTypeList.length > 0
                                                || $scope.vehicleSearchRepresentation.fuelList.length > 0
                                                || $scope.vehicleSearchRepresentation.countryList.length > 0
                                                || $scope.vehicleSearchRepresentation.modelYear.length > 0
                                                || ($scope.vehicleSearchRepresentation.counterMark != null&&!!$scope.vehicleSearchRepresentation.counterMark )
                                                || ($scope.vehicleSearchRepresentation.chasisNumber != null && !!$scope.vehicleSearchRepresentation.chasisNumber)
                                                || ($scope.vehicleSearchRepresentation.registrationNumber != null && !!$scope.vehicleSearchRepresentation.registrationNumber)
                                                || $scope.vehicleSearchRepresentation.statusList.length > 0
                                                || ($scope.vehicleSearchRepresentation.userId != null && !!$scope.vehicleSearchRepresentation.userId)) {
                                            $scope.searchVehicleFile();
                                        } else {
                                            $('#ConfirmationPopUp').modal('show');
                                            $scope.filteredVehicleItems = [];
                                        }
                                    }
                                };
                                // ------------------ENDS---------
                                // ----------------- Calling resource to get
                                // filtered items---
                                $scope.searchVehicleFile = function() {
                                    var response = SearchVehicleService.searchVehicleResource.searchVehicle($scope.vehicleSearchRepresentation,
                                            function() {
                                                $scope.filteredVehicleItems = response;
                                                if($scope.filteredVehicleItems.length===0){
                                                    NotificationService.notify(CultureService.localize('cop.search.vehicleFile.noresults'));
                                                }
                                                $scope.selectedVehicleFileIds = [];
                                            }, function() {
                                            });

                                };

                                // ---------- to add id in array on selecting
                                // vehicle file ------
                                $scope.selectVehicle = function(vehicleFileId, index) {

                                    var vehId = 'vehSelected' + index;
                                    var myClass = angular.element(document.querySelector('#' + vehId));
                                    var isSelected = false;
                                    for (var i = 0; i < $scope.selectedVehicleFileIds.length; i++) {
                                        if ($scope.selectedVehicleFileIds[i] === vehicleFileId) {

                                            index = i;
                                            isSelected = true;
                                        }
                                    }
                                    if (isSelected) {
                                        $scope.selectedVehicleFileIds.splice(index, 1);
                                        $(myClass).removeClass('vehicle-custom-border');
                                    } else {
                                        $scope.selectedVehicleFileIds.push(vehicleFileId);
                                        $(myClass).addClass('vehicle-custom-border');
                                    }
                                };
                                //---------- to add basket for selected vehicle file
                                $scope.addToBasket = function() {
                                    if($scope.selectedVehicleFileIds.length!==0){
                                        var objectToSend = {
                                        'vehicleFileIds' : $scope.selectedVehicleFileIds
                                    }
                                    BasketService.vehicleFileResource.addBasket(objectToSend, function() {
                                        NotificationService.notify(CultureService.localize('cop.vehicleFile.addBasket.success'));
                                        $scope.searchVehicleFile();
                                    }, function() {
                                        $scope.vehSearchEerrMessage=CultureService.localize('cop.vehicleFile.addBasket.error');
                                        $('#searchVehicleErrorModal').modal('show');
                                    })
                                }};
                                //------- refresh screen on reset button
                                $scope.resetAllFields = function() {
                                    if($scope.flag){
                                        VehicleReceptionSharedService.setVehicleFile($scope.selectedVehicleFileIds);
                                        $location.path("cop/ReceptionFile");
                                    }else{
                                    location.reload();
                                    }
                                };
                                //---------- double click function
                                $scope.dblClickVehicleFile = function(vehicleFile) {
                                     window.location="#!/cop/menu-list?vehicleFileId="+vehicleFile.entityId +'&&inBasket='+vehicleFile.inBasket;
                                };

                                
                                
                                // --------------Vehicle search ends---------
                            } ]);

    return {
        angularModules : [ 'VehicleFile' ]
    };
});