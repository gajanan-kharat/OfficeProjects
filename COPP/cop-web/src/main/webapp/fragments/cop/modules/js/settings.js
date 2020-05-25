/*
 * Copyright (c) PSA.
 */
define([
        'require', 'module', '{angular}/angular'
    ],

    function(require, module, angular) {
        var Settings = angular.module('Settings', [
            'ngResource', 'ui.grid', 'ui.grid.selection', 'ui.grid.cellNav', 'ui.grid.edit', 'copCommon', 'ui.grid.rowEdit'
        ]);

        /* Modal directive */
        Settings.directive('modal', function() {

            return {

                template: '<div class="modal fade">' + '<div class="modal-dialog">' + '<div class="modal-content">' + '<div class="modal-header">' + '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' + '<h4 class="modal-title">{{ title }}</h4>' + '</div>' + '<div class="modal-body" ng-transclude></div>' + '</div>' + '</div>' + '</div>',
                restrict: 'E',
                transclude: true,
                replace: true,
                scope: true,

                link: function postLink(scope, element, attrs) {
                    scope.title = attrs.title;

                    scope.$watch(attrs.visible, function(value) {
                            if (value=== true)
                            $(element).modal('show');
                        else
                            $(element).modal('hide');
                    });

                    $(element).on('shown.bs.modal', function() {
                        scope.$apply(function() {
                            scope.$parent[attrs.visible] = true;
                        });
                    });

                    $(element).on('hidden.bs.modal', function() {
                        scope.$apply(function() {
                            scope.$parent[attrs.visible] = false;
                        });
                    });
                }
            };

        });

        /* End */

        /* Service for saving test nature */
        Settings.factory('SaveTestNatureService', [
            '$resource',
            function($resource) {
                var TestNatureRepresentation = $resource('TestNature/TestNatures', {

                    'save': {
                        method: 'POST',
                        isArray: false
                    }
                });
                return {

                    createTestNature: function(TestData, success, error) {
                        var TestRepresentationTosave = new TestNatureRepresentation();
                        TestRepresentationTosave.label = TestData.label;
                        TestRepresentationTosave.type = TestData.type;
                        TestRepresentationTosave.hierarchy = TestData.hierarchy;
                        return TestRepresentationTosave.$save(success, error);
                    }
                };
            }
        ]);

        /* End */ 
        Settings.factory('UpdateTestNatureService', [
    	                                             			'$resource', function($resource) {
    	                                             				return {
    	                                             					UpdateTestNatureResource : $resource('TestNature/:path', {
    	                                             						path : '@path'

    	                                             					}, {
    	                                             						'updateTestNature' : {
    	                                             							method : 'POST',
    	                                             							params : {
    	                                             								path : 'updatetestnatures'

    	                                             							},
    	                                             							isArray : true
    	                                             						}
    	                                             					})
    	                                             				};
    	                                             			}
    	                                             	]);
    	
    	
        /* Service for saving Status */
        Settings.factory('SaveStatuseService', [
            '$resource', function($resource) {
                var StatusRepresentation = $resource('Status/StatusList', {

                    'save': {
                        method: 'POST',
                        isArray: false
                    }
                });
                return {

                    createStatus: function(StatusData, success, error) {
                        var StatusRepresentationTosave = new StatusRepresentation();
                        StatusRepresentationTosave.guiLabel = StatusData.guiLabel;
                        StatusRepresentationTosave.label = StatusData.label;
                        return StatusRepresentationTosave.$save(success, error);
                    }
                };
            }
        ]);

        /* End */

        /* Service for loading Status data */
        Settings.factory('GetStatusService', [
            '$resource', function($resource) {

                var StatusRepresentation = $resource('Status/StatusList', {
                    'query': {
                        method: 'GET',
                        isArray: true
                    }
                });
                return {
                    getStatus: function(success, error) {
                        return StatusRepresentation.query(success, error);
                    }
                };
            }
        ]);
        /* End */

        /* Service for loading test nature data */
        Settings.factory('GetTestNatureService', [
            '$resource', function($resource) {

                var TestNatureRepresentation = $resource('TestNature/TestNatures', {
                    'query': {
                        method: 'GET',
                        isArray: true
                    }
                });
                return {

                    getTestNature: function(success, error) {
                        return TestNatureRepresentation.query(success, error);
                    }
                };
            }
        ]);
        /* End */

            /* Service for loading type of test data */
            Settings.factory('GetTypeOfTestService', [ '$resource', function($resource) {

                var TypeOfTestRepresentation = $resource('typeoftest/typeoftestlist', {
                    'query' : {
                        method : 'GET',
                        isArray : true
                    }
                });
                return {

                    getTypeOfTest : function(success, error) {
                        return TypeOfTestRepresentation.query(success, error);
                    }
                };
            } ]);

            /* Ends */
        /* Service for deleting Testnature record */
        Settings.factory('DeleteTestNatureService', [
            '$resource',
            function($resource) {

                return {
                    TestNatureResource: $resource('TestNature/:path/:id', {
                        id: '@id',
                        path: '@path'
                    }, {

                        deleteTestNature: {
                            method: 'GET',
                            params: {
                                path: 'TestNatureDelete'
                            },
                            isArray: true
                        }

                    })
                };
            }
        ]);
        /* End */
            /* Service for deleting Type Test record */

            Settings.factory('DeleteTypeTestService', [ '$resource', function($resource) {

                return {
                    TypeTestResource : $resource('typeoftest/:path/:id', {
                        id : '@id',
                        path : '@path'
                    }, {

                        deleteTestType : {
                            method : 'GET',
                            params : {
                                path : 'TypeTestDelete'
                            },
                            isArray : true
                        }

                    })
                };
            } ]);
        /* End */

        /* Service for deleting status data */
        Settings.factory('DeleteStatusService', [
            '$resource',
            function($resource) {

                return {
                    StatusResource: $resource('Status/:path/:id/:natureLabel', {
                        id: '@id',
                        natureLabel: '@natureLabel',
                        path: '@path'

                    }, {
                        deleteStatus: {
                            method: 'POST',
                            params: {
                                path: 'StatusDelete'
                            },
                            isArray: true
                        }
                    })
                };

            }
        ]);
        /* End */

        /* Service for Mandatory Data Values (Status, TestNature) */
        Settings.factory('MandatoryService', [
            '$resource',
            function($resource) {

                return {
                    MandatoryResource: $resource('MandatoryDataReference/:path', {
                        path: '@path'
                    }, {

                        getESMandatoryData: {
                            method: 'POST',
                            params: {
                                path: 'ESMandatoryData'
                            },
                            isArray: true
                        },
                        saveMandatoryDataEMS: {
                            method: 'POST',
                            params: {
                                path: 'MandatoryDataEMS'
                            },
                            isArray: true
                        },
                        getTvvMandatoryData: {
                            method: 'POST',
                            params: {
                                path: 'TvvMandatoryData'
                            },
                            isArray: true
                        },
                        saveMandatoryDataTvv: {
                            method: 'POST',
                            params: {
                                path: 'MandatoryDataTvv'
                            },
                            isArray: true
                        },
                        getAllTvvStructure: {
                            method: 'POST',
                            params: {
                                path: 'AllTvvStructure'
                            },
                            isArray: true
                        }

                    })
                };

            }
        ]);

          
/* Service for saving Type Of Test */
            Settings.factory('SaveTypeOfTestService', [ '$resource',

            function($resource) {
                return {
                    TypeOFTestResource : $resource('typeoftest/:path/:entityId', {
                        path : '@path',
                        entityId : '@entityId'
                    }, {
                        'createTypeOfTest' : {
                            method : 'POST',
                            params : {
                                path : 'savetypeoftestlist'

                            },
                            isArray : true
                        },

                    })
                };
            } ]);
/* Ends */
        // start
        Settings.factory('SavePossibleStatusTestService', [
            '$resource',

            function($resource) {
                return {
                    StatusResource: $resource('Status/:path/:entityId', {
                        path: '@path',
                        entityId: '@entityId'
                    }, {
                        'saveAllChanges': {
                            method: 'POST',
                            params: {
                                path: 'StatusList'

                            },
                            isArray: true
                        },

                    })
                };
            }
        ]);
        // end

            Settings
                    .controller(
                            'SettingsController',
                            [
                                    '$scope',
                                    '$templateCache',
                                    'SaveTestNatureService',
                                    'SaveStatuseService',
                                    'GetStatusService',
                                    'GetTypeOfTestService',
                                    'GetTestNatureService',
                                    'DeleteTestNatureService',
                                    'DeleteStatusService',
                                    'EMSService',
                                    'EmsDepTDLService',
                                    'EmsDepTCLService',
                                    'PGListService',
                                    'uiGridConstants',
                                    'CultureService',
                                    'SavePossibleStatusTestService',
                                    'MandatoryService',
                                    'NotificationService',
                                    'AuthorizationService',
                                    'SaveTypeOfTestService',
                                    'DeleteTypeTestService',
                                    'UpdateTestNatureService',
                                    function($scope, $templateCache, SaveTestNatureService, SaveStatuseService, GetStatusService,
                                            GetTypeOfTestService, GetTestNatureService, DeleteTestNatureService, DeleteStatusService, EMSService,
                                            EmsDepTDLService, EmsDepTCLService, PGListService, uiGridConstants, cultureService,
                                            SavePossibleStatusTestService, MandatoryService, NotificationService, authorizationService,
                                            SaveTypeOfTestService,DeleteTypeTestService,UpdateTestNatureService) {

                /*----------------------------------------Authorization----------------------------------------*/


                $scope.authorization = function(role) {
                    return authorizationService.hasRole('seed-w20', role);
                };
                                        var authenticationForEdit = false;
                                        if (authorizationService.hasRole('seed-w20', 'POCMRole')) {
                                            authenticationForEdit = true;
                                        }
                /*-----------------------------------------------------------------------------*/
                $scope.testdata = [];
                $scope.statusdata = [];
                $scope.addtestobj = [];
                $scope.addstatusobj = [];
                $scope.testarr = [];
                $scope.saveteststatusdata = [];
                $scope.testtype = "";
                $scope.selectedName = "Test";
                $scope.statuslabel = "";
                $scope.sguilabel = "";

                $scope.mandatoryTvvStatus = false;
                $scope.statusTabheading = true;
                $scope.mandatoryTvvEmission = false;
                $scope.esmandatoryDataRepresentation = [];

                $scope.pocStatuslist = [];
                $scope.typeOfTestData=[];
                $scope.addtypetestsobj=[];

                $scope.testtype = "";
                $scope.genericTVVdata = [];
                $scope.emsDependentData = [];
                $scope.limitsList = [];
                $scope.emsDependentListNew = [];
                $scope.statusdataCopy = [];
                $scope.esmandatoryDataRepresentationCopy = [];
                $scope.importmandatoryDataRepresentation = [];
               
                var headertemplate = '<div class="ngHeaderText"><span>{{col.displayName}}</span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>';

                // ---------------------------------------------------TVV DEPENDENT MANDATORY DATA STARTS--------------------------------------------//
                
                $scope.customFilterArray=function(objGrid)
                {
                   
                    $scope.arrTest = [];
                    var term;
                    var field;
                    for (var i = 0; i < objGrid.columns.length; i++) {
                        term = objGrid.columns[i].filter.term;
                       field = objGrid.columns[i].field;
                       var obj={'term':term,'field':field };
                       $scope.arrTest.push(obj);
                }
                };

                // custom filter starts
                $scope.customFilterCondition = function(input, cellValue,row,column) {
                    
                    
                    
                    
                    
                    for(var i=0;i<$scope.arrTest.length;i++)
                        {
                          if($scope.arrTest[i].field===column.field)
                              {
                               input=$scope.arrTest[i].term;
                             
                              }
                        }

                    if (cellValue !== null && cellValue !== undefined) {
                        
                        cellValue = cellValue.toString();
                        cellValue = cellValue.replace(/\s+/g, "").toLowerCase();

                       input = input.replace(/\s+/g, "").toLowerCase();
                       

                        if (input.toString().indexOf('~') ===-1 && input.toString().indexOf('?') !== -1) {

                            if (input.length  === cellValue.length) {
                               var str = input.split("?");

                                var str1 = str[0].slice(0, str[0].length - 1);
                                if (str1==="") {
                                    return cellValue.slice(-str[1].length) === str[1]
                                } else {
                                    if (cellValue.slice(0, str[0].length - 1) === str1 && cellValue.slice(-str[1].length) ===str[1])
                                        return cellValue;
                                }

                            }
                        } else if (input.toString().indexOf('~~') !== -1) {
                            var index = input.indexOf('~');
                            input = input.substr(0, index) + input.substr(index + 1);

                            if (cellValue.slice(0, input.length) === input)
                                return cellValue;
                        } else if (input.toString().indexOf('~') !== -1) {
                            str = input.split("~");
                            if (str[1].indexOf('?') !== -1) {
                                input = input.replace(/\~/g, "");
                                input = input.replace(/\\/g, "");

                                if (cellValue.slice(0, input.length) == input)
                                    return cellValue;
                            } else if (input.indexOf('') !== -1) {
                                input = input + '*';
                                input = input.replace(/\~/g, "");
                                input = input.replace(/\\/g, "");
                                if (cellValue == input)
                                    return cellValue;

                            }

                        } else if (input.indexOf('*') !== -1) {

                            input = input.replace(/\\/g, '');

                            str = input.split("*");
                            var strArr = [];
                          
                            if (str.length > 2) {
                                var currValueToSearch = cellValue;
                                var isFilterOk = false;
                                for (var filterInputIndex = 0; filterInputIndex < str.length; filterInputIndex++) {
                                    var searchedChars = str[filterInputIndex];
                                    
                                    if(searchedChars !== ""){
                                        var indexOfSearchedChars = currValueToSearch.indexOf(searchedChars);
                                        if(indexOfSearchedChars > -1 ){
                                            currValueToSearch = currValueToSearch.substr(indexOfSearchedChars + searchedChars.length);
                                            isFilterOk = true;
                                        }else{
                                            isFilterOk =false;
                                            break;
                                        }
                                    }
                                   
                                }
                                var lastCharOfSearcText = input.charAt(input.length - 1);
                                if(isFilterOk && lastCharOfSearcText !== '*'){
                                    if(cellValue.charAt(cellValue.length - 1) !== lastCharOfSearcText){
                                        isFilterOk = false;
                                    }
                                    
                                }
                                 return isFilterOk;
                                
                               } else {
                                if(str[1]!="")
                                    {
                                     if (cellValue.slice(0, str[0].length) === str[0] && cellValue.slice(-str[1].length) === str[1])
                                    return cellValue;
                                    }
                                else
                                    {
                                    if (cellValue.slice(0, str[0].length) === str[0])
                                        return cellValue;
                                    }
                                 }

                        } else if (cellValue.indexOf(input) > -1) {

                            return cellValue;

                        }
                    }

                  };
                // end of custom filter

                $scope.columnDefESDependentGrid = [

                    {
                        name: 'objectType',
                        field: 'objectType',
                        filter: {
                            condition: $scope.customFilterCondition
                        },
                        width: '10%',
                        displayName: "",
                        cellTooltip: function(row) {
                            return row.entity.objectType;
                        },
                        enableCellEdit : authenticationForEdit,
                        headerCellTemplate: headertemplate
                    }, {
                        name: 'esDepListLabel',
                        field: 'esDepListLabel',
                        filter: {
                            condition: $scope.customFilterCondition
                        },
                        width: '10%',
                        cellTooltip: function(row) {
                            return row.entity.esDepListLabel;
                        },
                        enableCellEdit: true,
                        displayName: " ",
                        headerCellTemplate: headertemplate
                    }, {
                        name: 'label',
                        field: 'label',
                        filter: {
                            condition: $scope.customFilterCondition
                        },
                        width: '10%',
                        enableCellEdit: true,
                        displayName: " ",
                        cellTooltip: function(row) {
                            return row.entity.label;
                        },
                        headerCellTemplate: headertemplate
                    }
                ];

                $scope.esDependentGrid = {
                    enableColumnResizing: true,

                    data: 'esmandatoryDataRepresentation',
                    enableFiltering: true,
                    columnDefs: $scope.columnDefESDependentGrid,
                    onRegisterApi : function(gridApi) {
                        $scope.gridApi = gridApi;
                        $scope.gridApi.core.on.filterChanged($scope, function () {
                            $scope.objGrid = this.grid;
                            $scope.customFilterArray( $scope.objGrid);

                         });
                    }
                };
                // ON CHECK BOX CLICK EVENT
                $scope.onCheckBoxClickESDependentGrid = function(mandatoryFlagList, mandatoryDataRepresentation) {
                      if (!authenticationForEdit)
                        return;
                    var selectedCombinationID = mandatoryFlagList.entityId;
                    var currentConfiguration = mandatoryDataRepresentation.mandatoryIdValues;
                    if (mandatoryFlagList.flag == true) {
                        mandatoryFlagList.flag = false;
                        if (currentConfiguration.indexOf(',') == -1) {
                            currentConfiguration = '';
                        } else if (currentConfiguration.indexOf(',' + selectedCombinationID) > -1) {
                            currentConfiguration = currentConfiguration.replace(',' + selectedCombinationID, '');

                        } else if (currentConfiguration.indexOf(',' + selectedCombinationID + ',') > -1) {
                            currentConfiguration = currentConfiguration.replace(',' + selectedCombinationID, '');

                        } else if (currentConfiguration.indexOf(selectedCombinationID + ',') > -1) {
                            currentConfiguration = currentConfiguration.replace(selectedCombinationID + ',', '');
                        }

                    } else {
                        mandatoryFlagList.flag = true;
                        if (currentConfiguration == undefined || currentConfiguration.length == 0) {
                            currentConfiguration = '' + selectedCombinationID;
                        } else {
                            if (!currentConfiguration.indexOf(selectedCombinationID) > -1) {
                                currentConfiguration = currentConfiguration + "," + selectedCombinationID;
                            }
                        }

                    }
                    mandatoryDataRepresentation.mandatoryIdValues = currentConfiguration;
                };

                $scope.getESDependentData = function(selectedES) {
                                            MandatoryService.MandatoryResource
                                                    .getESMandatoryData(
                                                            selectedES,
                                                            function(success) {
                        $scope.esmandatoryDataRepresentation = success;
                                                                // Change made
                                                                // for Cancel
                                                                // Button
                                                                $scope.esmandatoryDataRepresentationCopy = angular
                                                                        .copy($scope.esmandatoryDataRepresentation);
                                                                if ($scope.esmandatoryDataRepresentation == null
                                                                        || $scope.esmandatoryDataRepresentation.length == 0)
                            return;
                        var statusNatureList = $scope.esmandatoryDataRepresentation[0].mandatoryFlagList;
                        // dynamic Boolean field creation
                        for (var i = 0; i < statusNatureList.length; i++) {
                             var pocstatusLabel = statusNatureList[i].statusLabel + ' - '
                                                                            + statusNatureList[i].testNatureLabel;

                            $scope.headers = [];
                            var dynamicHeaders = {
                                name: pocstatusLabel,
                                displayName: pocstatusLabel,
                                enableCellEdit: true,
                                enableFiltering: false,
                                field: 'mandatoryFlagList.flag',
                                cellTemplate: '<div class="ui-grid-selection-row-header-buttons ui-grid-icon-ok row-checkbox" ng-class="{\'ui-grid-row-selected\': row.entity.mandatoryFlagList[' + i + '].flag}" ng-click="grid.appScope.onCheckBoxClickESDependentGrid(row.entity.mandatoryFlagList[' + i + '], row.entity)"/>',
                                headerCellTemplate: "ui-grid/uiGridCheckboxHeaderCell"
                            };

                            $scope.headers.push(pocstatusLabel);
                            $scope.columnDefESDependentGrid.push(dynamicHeaders);

                        }

                    }, function(error) {
                        NotificationService.notify("In Error");
                    });

                };

                $scope.saveMandatoryConfigurationES = function() {

                    var ManageMandatoryDataRequestDto = {
                        genericDataList: $scope.esmandatoryDataRepresentation
                    };

                    MandatoryService.MandatoryResource.saveMandatoryDataEMS(ManageMandatoryDataRequestDto, function(success) {
                        NotificationService.notify(cultureService.localize('cop.testCondition.message.successMessage'));
                    }, function(error) {
                        NotificationService.notify("In Error");
                    });

                };

                $scope.displayESDependentScreen = function() {
                    $scope.esmandatoryDataRepresentation = [];
                    EMSService.EmissionStandardResource.getAllEmissionStandards(function(response) {
                        $scope.emsList = response;
                        $scope.listsAvail = true;

                    }, function(error) {
                        NotificationService.notify(cultureService.localize('cop.testCondition.message.fetchError'));
                    });

                    $scope.statusTabheading = false;
                    $scope.mandatoryTvvStatus = false;
                    $scope.mandatoryTvvEmission = true;
                };

                                        // ---------------------------------------------------TVV
                                        // DEPENDENT MANDATORY DATA
                                        // ENDS--------------------------------------------//
                $scope.getTVVDependentData = function(selectedTvv) {
                                            MandatoryService.MandatoryResource
                                                    .getTvvMandatoryData(
                                                            selectedTvv,
                                                            function(success) {
                        $scope.esmandatoryDataRepresentation = success;
                                                                // Change made
                                                                // for Cancel
                                                                // Button
                                                                $scope.esmandatoryDataRepresentationCopy = angular
                                                                        .copy($scope.esmandatoryDataRepresentation);
                        var statusNatureList = $scope.esmandatoryDataRepresentation[0].mandatoryFlagList;
                                                                // dynamic
                                                                // Boolean field
                                                                // creation
                        for (var i = 0; i < statusNatureList.length; i++) {
                            var pocstatusLabel = statusNatureList[i].statusLabel + ' - ' + statusNatureList[i].testNatureLabel;

                            // dynamic Boolean field creation

                            $scope.headers = [];
                            var dynamicHeaders = {
                                name: pocstatusLabel,
                                displayName: pocstatusLabel,
                                enableCellEdit: true,
                                enableFiltering: false,

                                field: 'mandatoryFlagList.flag',
                                cellTemplate: '<div class="ui-grid-selection-row-header-buttons ui-grid-icon-ok row-checkbox" ng-class="{\'ui-grid-row-selected\': row.entity.mandatoryFlagList[' + i + '].flag}" ng-click="grid.appScope.onCheckBoxClickESDependentGrid(row.entity.mandatoryFlagList[' + i + '], row.entity)"/>',
                                headerCellTemplate: "ui-grid/uiGridCheckboxHeaderCell"
                            };

                            $scope.headers.push(pocstatusLabel);
                            $scope.columnDefESDependentGrid.push(dynamicHeaders);
                            

                        }

                    }, function(error) {
                        NotificationService.notify("In Error");
                    });

                };

                $scope.saveMandatoryConfigurationTVV = function() {
                    var ManageMandatoryDataRequestDto = {
                        genericDataList: $scope.esmandatoryDataRepresentation
                    };

                    MandatoryService.MandatoryResource.saveMandatoryDataTvv(ManageMandatoryDataRequestDto, function(success) {
                        $scope.esmandatoryDataRepresentation = success;
                        // Change made for Cancel Button
                        $scope.esmandatoryDataRepresentationCopy = angular.copy($scope.esmandatoryDataRepresentation);
                        NotificationService.notify(cultureService.localize('cop.testCondition.message.successMessage'));
                    }, function(error) {

                    });

                };

                // To get Drop down values of mandatory Screen
                $scope.displayTvvDependentScreen = function() {
                    $scope.esmandatoryDataRepresentation = [];
                    MandatoryService.MandatoryResource.getAllTvvStructure(function(response) {
                        $scope.tvvList = response;
                        $scope.listsAvail = true;

                    }, function(error) {
                        NotificationService.notify(cultureService.localize('cop.testCondition.message.fetchError'));
                    });

                    $scope.statusTabheading = false;
                    $scope.mandatoryTvvStatus = true;
                    $scope.mandatoryTvvEmission = false;
                };

                $scope.tvvDependentGrid = [{
                    name: 'tvvtechdata',
                    field: 'title',
                    filter: {
                        condition: $scope.customFilterCondition
                    },
                    cellTooltip: function(row) {
                        return row.entity.title;
                    },
                    displayName: cultureService.localize("cop.esDependent.tab.TechnicalData.Tvv"),
                    headerCellFilter: 'localize'
                }, {
                    name: 'tvvlabel',
                    field: 'label',
                    filter: {
                        condition: $scope.customFilterCondition
                    },
                    cellTooltip: function(row) {
                        return row.entity.label;
                    },
                    displayName: ""
                }, {
                    name: 'tvvtechlabel',
                    field: 'genericlabel',
                    filter: {
                        condition: $scope.customFilterCondition
                    },
                    cellTooltip: function(row) {
                        return row.entity.genericlabel;
                    },
                    displayName: ""
                }
                ];
              

                                        // -----------------------------------------INITIALIZE
                                        // STATUS DATA in
                                        // TAB1---------------------------------------//
                $scope.getStatusNatureInfo = function() {

                                            /*
											 * Function for loading Status data STARTS
											 */
                    GetStatusService.getStatus(

                        function(response) {
                            $scope.statusdata = response;

                            for (var i = 0; i < $scope.statusdata.length; i++) {
                                $scope.statusdata[i].testNatureLabelOld = $scope.statusdata[i].testNatureLabel;
                            }

                            // Change made for Cancel Button
                            $scope.statusdataCopy = angular.copy($scope.statusdata);
                                            }, function() {
                            NotificationService.notify(cultureService.localize("cop.settings.status.load.errorMessage"));
                        }

                    );
                                            /*
											 * Function for loading Status data ENDS
											 */
                };

                                        // --------------initialize type of test
                                        // data STARTS -----------------//

                                        $scope.getTypeOfTestInfo = function() {
                                            /*
											 * Function for loading TypeOfTest data STARTS
											 */
                                            GetTypeOfTestService.getTypeOfTest(

                                            function(response) {
                                                for (var i = 0; i < response.length;) {
                                                    var obj = {
                                                        'type' : response[i]["testNatureType"],
                                                        'entityId' : response[i]["typeOfTestId"],
                                                        'label' : response[i]["label"]

                                                    };
                                                    $scope.typeOfTestData.push(obj);
                                                    i++;
                                                }
                                            }, function(error) {
                                                NotificationService.notify(cultureService.localize("cop.settings.typeOfTest.load.errorMessage"));
                                            }

                                            );
                                            /*
											 * Function for loading TypeOfTest data ENDS
											 */
                                        };
                                        // --------------initialize type of test
                                        // data ENDS -----------------//

                $scope.getTestNatures = function() {
                    /* Function for loading testnature data STARTS */
                    GetTestNatureService.getTestNature(function(response) {

                        for (var i = 0; i < response.length;) {
                            var obj = {
                                'type': response[i]["type"],
                                'entityId': response[i]["entityId"],
                                                        'label' : response[i]["label"],
                                                        'hierarchy' : response[i]["hierarchy"]

                            };
                            $scope.testdata.push(obj);
                            i++;
                        }
                        
                    }, function() {

                        NotificationService.notify(cultureService.localize("cop.settings.testNature.load.errorMessage"));
                    });
                    /* Function for loading testnature data ENDS */
                };

                $scope.getTestNatures();
                $scope.getStatusNatureInfo();
                $scope.getTypeOfTestInfo();
                // -----------------------------------------INITIALIZE STATUS DATA in TAB1 ENDS---------------------------------------//

                // -----------------------------------------TEST NATURE MANAGEMENT STARTS----------------------------------------//
                /* Function for adding testNature */
                                        $scope.addTest = function(addedTest, testnatureHierarchy, adddescr) {
                                            if(testnatureHierarchy!==null && testnatureHierarchy!=="" && testnatureHierarchy!==undefined && addedTest !== undefined && addedTest.length > 0){
    

                                           
                                                var newTest = {
                                                        type: addedTest,
                                                    label : adddescr,
                                                    hierarchy : testnatureHierarchy
                        };

                        for (var i = 0; i < $scope.testdata.length; i++) {
                            var testLabel = $scope.testdata[i].type;
                                                    var hierarchy = $scope.testdata[i].hierarchy;
                                                    if (testLabel.toUpperCase() === addedTest.toUpperCase() || hierarchy=== testnatureHierarchy) {
                                                        NotificationService.notify(cultureService
                                                                .localize("cop.settings.testNature.add.duplicate.errorMessage"));
                                return;
                            }

                        }

                                                $scope.addtestobj.push(newTest);

                        $('#testModal').modal('hide');

                                               

                                                    SaveTestNatureService.createTestNature(newTest, function(data) {

                                    var obj = {
                                        type: data.type,
                                        label: data.label,
                                        entityId: data.entityId,
                                        hierarchy : data.hierarchy

                                    };

                                    $scope.testdata.push(obj);

                                    NotificationService.notify(cultureService.localize("cop.settings.testNature.add.successMessage"));

                                }, function() {
                                    NotificationService.notify(cultureService.localize("cop.settings.testNature.add.errorMessage"));
                                }

                            );
                        
                        $scope.addtestobj = [];
                    

                                        } };
                                        /* Function for updating  testNature hierarchy */
                                        $scope.updateTestNature = function() {

                                        	$scope.testdata= UpdateTestNatureService.UpdateTestNatureResource.updateTestNature($scope.testdata, function() {

                                        	}, function() {
                                        		$scope.getTestNatures();
                                        		NotificationService.notify(cultureService.localize("cop.setting.testnature.duplicate.hierarchy"));
                                        	}

                                        	);

                                        };
                /*
				 * $scope.changedTest = function(index, testobj) {
				 * 
				 * $scope.statusdata[index]["testrepresentationdata"] = [ testobj ]; $scope.statusdata[index]["testNatureLabel"] = testobj; };
				 */
                /* End */

                /* Function for deleting Test Nature */
                $scope.deleteTest = function(index) {

                    var idTodelete = $scope.testdata[index].entityId;

                    DeleteTestNatureService.TestNatureResource.deleteTestNature({
                        id: idTodelete
                    }, function() {
                        NotificationService.notify(cultureService.localize("cop.setting.testNature.delete.successMessage"));
                        $scope.testdata.splice(index, 1);
                    }, function() {
                        NotificationService.notify(cultureService.localize("cop.setting.testNature.deleteerroreMessage"));

                    });

                };
                $scope.testgrid = {
                		
                    columnDefs: [{
                        name: 'Test',
                        field: 'type',
                        cellTooltip: function(row) {
                            return row.entity.type;
                        },
                        displayName : cultureService.localize("cop.settings.natureOfTest.label"),
                        headerTooltip: function ( col ){
                        return col.displayName;
                         },
                       enableColumnMenu : false,
                       enableCellEdit : false,
                       enableSorting :false,
                       headerCellClass: 'ngHeaderText custom-grid-header no-padding',

                                                    },

                                                    {

                                                        name : 'Hirarchy',
                                                        field : 'hierarchy',
                                                        cellTooltip : function(row) {
                                                            return row.entity.hirarchy;
                                                        },
                                                        displayName : cultureService.localize("cop.settings.testNature.createpopup.hierarchy"),
                                                        headerTooltip: function ( col ){
                                                            return col.displayName;
                                                        },
                                                        enableCellEdit : true,
                                                        enableColumnMenu : false,
                                                        enableSorting :false,
                                                        headerCellClass: 'ngHeaderText custom-grid-header no-padding',

                                                    },

                                                    {
                        name: 'delete',
                                                        width : 25,
                                                        cellTemplate : '<button class="btn primary" ng-show="grid.appScope.authorization(\'POCMRole\')" ng-click="grid.appScope.deleteTest(grid.renderContainers.body.visibleRowCache.indexOf(row))"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>',
                        displayName: "",
                        headerCellTemplate: '<div class="ngHeaderText"></div>',
                        visible: authenticationForEdit
                                                    } ,
                                                    ],
                                                    onRegisterApi:function (gridApi) {
                                                        $scope.gridApi = gridApi;
                                                    },

                    data: 'testdata',
                    headerRowHeigh: 0

                };
                                        // -----------------------------------------TEST
                                        // NATURE MANAGEMENT
                                        // ENDS----------------------------------------//
                                        // ----------------------------------------------TYPE
                                        // OF TEST GRID
                                        // ---------------------------------------------//
                                        $scope.typeoftestgrid = {

                                            enableCellEditOnFocus : true,
                                            enableColumnResizing : true,
                                            rowEditWaitInterval : -1,
                                            columnDefs : [
                                                    {
                                                        name : 'TypeOfTestLabel',
                                                        field : 'label',
                                                        width : '30%',
                                                        cellEditableCondition : function($scope) {
                                                            if (angular.isUndefined($scope.row.entity.entityId)) {
                                                                return true;
                                                            } else {
                                                                return false;
                                                            }
                                                        },
                                                        cellTooltip : function(row) {
                                                            return row.entity.label;
                                                        },
                                                        displayName : cultureService.localize("cop.typeoftest.label"),
                                                        headerTooltip: function ( col ){
                                                            return col.displayName;
                                                        },
                                                        enableColumnMenu : false,
                                                        enableSorting :false,
                                                        headerCellClass: 'ngHeaderText custom-grid-header no-padding',
                                                        
                                                    },
                                                    {
                                                        name : 'Nature',
                                                        field : 'type',
                                                        cellTemplate : '<input type="select" title="{{row.entity.type}}" style="height:27px;text-overflow: ellipsis;" ng-disabled="true" ng-model="row.entity.type"/>',
                                                        editType : 'dropdown',
                                                        editableCellTemplate : 'ui-grid/dropdownEditor',
                                                        editDropdownOptionsArray : $scope.testdata,
                                                        editDropdownIdLabel : 'type',
                                                        editDropdownValueLabel : 'type',
                                                        enableCellEdit : authenticationForEdit,
                                                        displayName : cultureService.localize("cop.settings.natureOfTest.label"),
                                                        headerTooltip: function ( col ){
                                                            return col.displayName;
                                                        },
                                                        enableColumnMenu : false,
                                                        enableSorting :false,
                                                        headerCellClass: 'ngHeaderText custom-grid-header no-padding'
                                                    },
                                                    {
                                                        name : 'delete',
                                                        width : '5%',
                                                        enableCellEdit : false,
                                                        cellTemplate : '<button class="btn primary" ng-show="grid.appScope.authorization(\'POCMRole\')" ng-click="grid.appScope.deleteTypeTest(grid.renderContainers.body.visibleRowCache.indexOf(row))"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>',
                                                        displayName : "",
                                                        headerCellTemplate : '<div class="ngHeaderText"></div>'
                                                    } ],

                                            data : 'typeOfTestData',

                                        };
                                        // TYPE OF TEST MANAGEMENT
                                        // STARTS----------------------------------------//
                                        $scope.addTypeOfTest = function() {

                                            var typetestsobj = {
                                                label : $scope.statuslabel,
                                                testNature : $scope.testNatureLabel
                                            };

                                            $scope.addtypetestsobj.push(typetestsobj);
                                            $scope.typeOfTestData.push(typetestsobj);
                                           

                                        };
                                        /* End */
                                        /* Delete type of test */
                                        /* Function for deleting Test Nature */
                                        $scope.deleteTypeTest = function(index) {

                                            var idTodelete = $scope.typeOfTestData[index].entityId;
                                            if(idTodelete === undefined){
                                                $scope.typeOfTestData.splice(index, 1);
                                                return
                                            }
                                            DeleteTypeTestService. TypeTestResource.deleteTestType({
                                                id : idTodelete
                                            }, function() {
                                                NotificationService.notify("type test deleted successfully");
                                                $scope.typeOfTestData.splice(index, 1);
                                                
                                            }, function(error) {
                                                if (error.status === 412) {
                                                    $scope.errorMsgSettings=cultureService
                                                            .localize("cop.setting.status.delete.totinuse");
                                                    $('#settingsErrorModal').modal('show');
                                                }else{
                                                    $scope.errorMsgSettings=cultureService
                                                            .localize("cop.setting.status.deleteErr.typeoftest");
                                                $('#settingsErrorModal').modal('show');
                                                }
                                            });

                                        };
                                        // -----------------------Delete Type OF
                                        // Test ends-------
                                        // -----------------------------------------STATUS
                                        // MANAGEMENT
                                        // STARTS----------------------------------------//
                $scope.addStatus = function() {

                    var statusobj = {
                        label: $scope.statuslabel,
                        guiLabel: $scope.sguilabel
                    };

                    $scope.addstatusobj.push(statusobj);
                    $scope.statusdata.push(statusobj);
                    $scope.showModalstatus = false;
                    /*
					 * //Change made for Cancel Button $scope.statusdataCopy = angular.copy($scope.statusdata);
					 */

                };
                /* End */

                                        /*
										 * Function for saving test and status data
										 */

                                        $scope.saveTypeTestStatusData = function() {
                                        
                                        		$scope.updateTestNature();
                                        		$scope.saveTypeTest();
                                        		$scope.saveTestStatus();
                                                 
                                        };
                                        
                                        $scope.saveTypeTest = function() {
                                            $scope.savetesttype = [];
                                            $scope.savetesttypetemp = [];
                                            $scope.savetypelists=[];
                                            for (var i = 0; i < $scope.typeOfTestData.length; i++) {
                                                if ($scope.typeOfTestData[i]['label'].length === 0 
                                                        || $scope.typeOfTestData[i]['type'] === undefined
                                                        || $scope.typeOfTestData[i]['type'].length === 0) {
                                                    NotificationService.notify("Incomplete Data");
                                                    return;
                                                }
                                                    var typetestDataObj = {
                                                            'label' : $scope.typeOfTestData[i]['label'],
                                                            'testNatureType' : $scope.typeOfTestData[i]['type']
                                                        };
                                                        $scope.savetypelists.push(typetestDataObj);
                                               
                                            }

                                            $scope.typeOfTestData = [];
                                            var objectToSend = {
                                                'typeOfTestRepresentationList' : $scope.savetypelists
                                            };
                                            SaveTypeOfTestService.TypeOFTestResource.createTypeOfTest(objectToSend, function(success) {
                                                for (var i = 0; i < success.length;) {
                                                    var obj = {
                                                        'type' : success[i]["testNatureType"],
                                                        'entityId' : success[i]["typeOfTestId"],
                                                        'label' : success[i]["label"]

                                                    };
                                                    $scope.typeOfTestData.push(obj);
                                                    i++;
                                                }
                                                $scope.savetypelists = [];
                                                NotificationService.notify(cultureService.localize('cop.testCondition.totSave.successMessage'));
                                                }, function() {
                                                NotificationService.notify(cultureService.localize('cop.testCondition.totSave.errorMessage'));
                                            });

                                        };
                $scope.saveTestStatus = function() {

                    $scope.savelists = [];
                    $scope.saveliststemp = [];
                    for (var i = 0; i < $scope.statusdata.length; i++) {
                                                if ($scope.statusdata[i]['guiLabel'].length === 0 || $scope.statusdata[i]['label'].length === 0
                                                        || $scope.statusdata[i]['testNatureLabel'] === undefined
                                                        || $scope.statusdata[i]['testNatureLabel']===null || $scope.statusdata[i]['testNatureLabel'].length === 0) {
                                                    $scope.errorMsgSettings=cultureService.localize("cop.setting.status.error.incompleteData");
                                                   $('#settingsErrorModal').modal('show');
                            return;
                        }
                        $scope.savelists.push($scope.statusdata[i]);
                    }

                    $scope.statusdata = [];
                    var objectToSend = {
                        'statusRepresentationList': $scope.savelists
                    };
                    SavePossibleStatusTestService.StatusResource.saveAllChanges(objectToSend, function(success) {

                        $scope.statusdata = success;
                        NotificationService.notify(cultureService.localize('cop.testCondition.message.successMessage'));
                        // Change made for Cancel Button
                        $scope.statusdataCopy = angular.copy($scope.statusdata);

                    }, function(error) {
                        NotificationService.notify(cultureService.localize('cop.testCondition.message.errorMessage'));
                    });
                };

                /* Function for deleting Status */
                $scope.deleteStatus = function(index) {
                    var idTodelete = $scope.statusdata[index].entityId;
                    var natureLabel = $scope.statusdata[index].testNatureLabel;

                                            if (idTodelete === undefined){
                                                $scope.statusdata.splice(index, 1);
                        return;
                                            }

                    DeleteStatusService.StatusResource.deleteStatus({
                        id: idTodelete,
                        natureLabel: natureLabel
                    }, function(success) {
                        $scope.statusdata.splice(index, 1);
                        NotificationService.notify(cultureService.localize("cop.setting.status.delete.successMessage"));
                    }, function(error) {
                        if (error.status === 412) {
                                                    NotificationService.notify(cultureService
                                                            .localize("cop.setting.status.delete.preconditionfailed"));
                        } else {
                            NotificationService.notify(cultureService.localize("cop.setting.status.deleteerroreMessage"));
                        }

                    });

                };

                $scope.statusgrid = {
                    enableCellEditOnFocus: true,
                    enableColumnResizing: true,
                    rowEditWaitInterval: -1,
                    columnDefs: [{
                        name: 'StatusLabel',
                        field: 'label',
                        width: '30%',
                        cellEditableCondition: function($scope) {
                            if (angular.isUndefined($scope.row.entity.entityId)) {
                                return true;
                            }
                           return false;
                                                            
                        },
                        cellTooltip: function(row) {
                            return row.entity.label;
                        },
                                                        displayName : cultureService.localize("cop.technicalData.text.label"),
                                                        headerTooltip: function ( col ){
                                                            return col.displayName;
                                                        },
                                                        enableColumnMenu : false,
                                                        enableSorting :false,
                                                        headerCellClass: 'ngHeaderText custom-grid-header no-padding'
                                                            },
                                                    {
                        name: 'StatusGUILabel',
                        field: 'guiLabel',
                        cellEditableCondition: function($scope) {
                            if (angular.isUndefined($scope.row.entity.entityId)) {
                                return true;
                            }
                                                                return false;
                                                            
                        },
                        cellTooltip: function(row) {
                            return row.entity.guiLabel;
                        },
                        width: '30%',
                                                        displayName : cultureService.localize("cop.status.displaylabel"),
                                                        headerTooltip: function ( col ){
                                                            return col.displayName;
                                                        },
                                                        enableColumnMenu : false,
                                                        enableSorting :false,
                                                        headerCellClass: 'ngHeaderText custom-grid-header no-padding'
                                                    },
                                                    {
                        name: 'Nature',
                        field: 'testNatureLabel',
                                                        cellTemplate : '<input type="select" title="{{row.entity.testNatureLabel}}" style="height:27px;text-overflow: ellipsis;" ng-disabled="true" ng-model="row.entity.testNatureLabel"/>',
                        editType: 'dropdown',
                        editableCellTemplate: 'ui-grid/dropdownEditor',
                        editDropdownOptionsArray: $scope.testdata,
                        editDropdownIdLabel: 'type',
                        editDropdownValueLabel: 'type',
                        displayName : cultureService.localize("cop.settings.testNature.createpopup.header"),
                        headerTooltip: function ( col ){
                       return col.displayName;
                        },
                       enableColumnMenu : false,
                       enableCellEdit : authenticationForEdit,
                        enableSorting :false,
                        headerCellClass: 'ngHeaderText custom-grid-header no-padding',
                        },
                                                    {
                        name: 'delete',
                        width: '5%',
                        enableCellEdit: false,
                                                        cellTemplate : '<button class="btn primary" ng-show="grid.appScope.authorization(\'POCMRole\')" ng-click="grid.appScope.deleteStatus(grid.renderContainers.body.visibleRowCache.indexOf(row))"><span class="glyphicon glyphicon-remove" style="color:red"></span></button>',
                        displayName: "",
                        headerCellTemplate : '<div class="ngHeaderText"></div>',
                        visible:authenticationForEdit
                    }],

                    data: 'statusdata',

                };
                /** ********** Import **************** */

                $scope.showImportModel = function() {

                    $('#importModel').modal('show');

                };
                // $scope.importdata = null;

                $scope.selectedData = function(selectedValue) {
                    $scope.importdata = selectedValue;
                }

                $scope.impoertSelected = function() {

                                            MandatoryService.MandatoryResource
                                                    .getESMandatoryData(
                                                            $scope.importdata,
                                                            function(success) {
                            $scope.importmandatoryDataRepresentation = success;
                            for (var i = 0; i < $scope.importmandatoryDataRepresentation.length; i++) {
                                for (var j = 0; j < $scope.esmandatoryDataRepresentation.length; j++) {

                                    if ($scope.importmandatoryDataRepresentation[i].objectType === $scope.esmandatoryDataRepresentation[j].objectType && $scope.importmandatoryDataRepresentation[i].esDepListLabel === $scope.esmandatoryDataRepresentation[j].esDepListLabel && $scope.importmandatoryDataRepresentation[i].label === $scope.esmandatoryDataRepresentation[j].label) {
                                        $scope.esmandatoryDataRepresentation[j].mandatoryFlagList = $scope.importmandatoryDataRepresentation[i].mandatoryFlagList;
                                        $scope.esmandatoryDataRepresentation[j].mandatoryIdValues = $scope.importmandatoryDataRepresentation[i].mandatoryIdValues
                                    }
                                }
                            }

                        }, function(error) {
                            NotificationService.notify(cultureService.localize('cop.testCondition.message.fetchError'));
                        });
                    
                    $scope.importdata = null;
                    $('#importModel').modal('hide');

                };
                // -----------------------------------------STATUS MANAGEMENT ENDS----------------------------------------//
                $scope.mandatoryEMSgrid = {
                    data: 'emsDependentData',
                    enableFiltering: true,
                    enableColumnResizing: true,
                    columnDefs: $scope.mandatoryEmsdata
                };

                $scope.mandatoryEMSgrid.onRegisterApi = function(emsGridapi) {
                    // set gridApi on scope if needed
                    $scope.emsGridapi = emsGridapi;
                };

                $scope.statusgrid.onRegisterApi = function(gridApi) {

                    $scope.gridApi = gridApi;
                    gridApi.edit.on.afterCellEdit($scope, function(rowentity) {

                        /* Function for deleting Status */
                        var statusRow = $scope.statusdata;
                        var count = 0;

                        for (var i = 0; i < statusRow.length; i++) {
                            if (statusRow[i]['guiLabel'] + statusRow[i]['label'] + statusRow[i]['testNatureLabel'] === rowentity.guiLabel + rowentity.label + rowentity.testNatureLabel) {
                                count++;
                                if (count > 1) {
                                    rowentity.testNatureLabel = '';
                                    NotificationService.notify("Either Incomplete or Duplicate Data");

                                }

                            }
                        }

                        /*
						 * for (var i = 0; i < $scope.testdata.length; i++) { if ($scope.testdata[i]['type'] === rowentity.testNatureLabel) { rowentity.testrepresentationdata[0].entityId = $scope.testdata[i]['entityId']; break; } }
						 */
                    });

                };
                                        $scope.typeoftestgrid.onRegisterApi = function(gridApi) {

                                            $scope.typeoftestgridApi = gridApi;
                                            gridApi.edit.on
                                                    .afterCellEdit($scope,
                                                            function(rowentity) {
                                                            });

                                        };
                                        
                                        $scope.tvvDependentGrid.onRegisterApi=function(gridApi) {
                                            $scope.gridApi = gridApi;
                                            $scope.gridApi.core.on.filterChanged($scope, function () {
                                                $scope.objGrid = this.grid;
                                                $scope.customFilterArray( $scope.objGrid);

                                             });

                                            
                                        };
                $scope.toggleTestModal = function() {
                    $('#testModal').modal('show');
                    $scope.testnature = '';
                    $scope.testnatureHierarchy = '';
                };

                $scope.slideToggle = function() {
                    $("#wrapper").toggleClass("toggled");
                    $("#menu-toggle").toggleClass('fa-chevron-right');
                    $(".slide-resize").toggleClass("col-lg-10");
                    $("#content-middle").toggleClass("hidden-xs");
                };

                $("#setting").css('background-color', '#DBDBDB', 'color', '#fff');

                $templateCache.put('ui-grid/uiGridCheckboxHeaderCell', "<div ng-class=\"{ 'sortable': sortable }\"><div class=\"ui-grid-vertical-bar\">&nbsp;</div><div class=\"ui-grid-cell-contents\" col-index=\"renderIndex\"><span>{{ col.displayName CUSTOM_FILTERS }}</span> <span ui-grid-visible=\"col.sort.direction\" ng-class=\"{ 'ui-grid-icon-up-dir': col.sort.direction == asc, 'ui-grid-icon-down-dir': col.sort.direction == desc, 'ui-grid-icon-blank': !col.sort.direction }\">&nbsp;</span></div><div class=\"ui-grid-column-menu-button\" ng-if=\"grid.options.enableColumnMenus && !col.isRowHeader  && col.colDef.enableColumnMenu !== false\" class=\"ui-grid-column-menu-button\" ng-click=\"toggleMenu($event)\"><i class=\"ui-grid-icon-angle-down\">&nbsp;</i></div><div ng-if=\"filterable\" class=\"ui-grid-filter-container\" ng-repeat=\"colFilter in col.filters\" align=\"center\"><input type=\"checkbox\" class=\"ui-grid-filter-input\" ng-model=\"colFilter.term\" ng-click=\"$event.stopPropagation()\" ng-attr-placeholder=\"{{colFilter.placeholder || ''}}\"><div class=\"ui-grid-filter-button\" ng-click=\"colFilter.term = null\"><i class=\"ui-grid-icon-cancel\" ng-show=\"!!colFilter.term\">&nbsp;</i><!-- use !! because angular interprets 'f' as false --></div></div></div>");

                // Start Changes on Cancel Button
                $scope.CancelStatusConfirmation = function() {
                    // Get all dirty rows
                    $scope.gridDirtyRows = $scope.gridApi.rowEdit.getDirtyRows($scope.gridApi.grid);
                                            $scope.gridTypeDirtyRows = $scope.typeoftestgridApi.rowEdit.getDirtyRows($scope.typeoftestgridApi.grid);
                                            var dataTypeDirtyRows = $scope.gridTypeDirtyRows.map(function(gridRow) {
                                                return gridRow.entity;

                                            });
                    var dataDirtyRows = $scope.gridDirtyRows.map(function(gridRow) {
                        return gridRow.entity;

                    });
                   if (dataDirtyRows.length > 0 || dataTypeDirtyRows.length>0) {
                        $('#ConfirmCancelStatus').modal('show');
                    }


                $scope.cancelCreation = function(label, modalName) {
                    $scope.label = label;
                    $scope.modaName = modalName;
                    if (label != undefined || label != null)
                        $('#cancelCreationModal').modal('show');
                    else
                        $('#' + $scope.modaName).modal('hide');
                };
                $scope.continueCreation = function() {

                    $('#' + $scope.modaName).modal('hide');

                };
                $scope.cancelModifiedStatusChanges = function() {
                    $scope.statusdata = angular.copy($scope.statusdataCopy);
                    var dataDirtyRows = $scope.gridDirtyRows.map(function(gridRow) {
                        return gridRow.entity;

                    });
                    $scope.gridApi.core.refresh();
                    $scope.gridApi.rowEdit.setRowsClean(dataDirtyRows);
                    $scope.typeOfTestData.splice(0);
                    $scope.getTypeOfTestInfo();
                                            
                };
                // END Of Changes on Cancel Button

                // Start Changes on Cancel Button for Mandatory Fields
                $scope.CancelEMSTVVConfirmation = function() {
                    $scope.dataNotChanged = angular.equals($scope.esmandatoryDataRepresentation, $scope.esmandatoryDataRepresentationCopy);
                    if ($scope.dataNotChanged == false) {
                        $('#ConfirmCancelEMSTVV').modal('show');
                    }
                };
                $scope.cancelModifiedEMSTVVChanges = function() {
                    $scope.esmandatoryDataRepresentation = angular.copy($scope.esmandatoryDataRepresentationCopy);
                };
                // END Of Changes on Cancel Button for Mandatory Fields
            }
         }

        ]);

        return {
            angularModules: [
                'Settings'
            ]
        };
    });