/*
 * Copyright (c) PSA.
 */
define([
        'require', 'module', '{angular}/angular'
], function(require, module, angular) {
    var EditSamplingApp = angular.module('EditSamplingApp', [
            'ngResource', 'ui.grid.rowEdit', 'ui.grid', 'ui.grid.resizeColumns', 'ui.grid.selection', 'ui.grid.cellNav', 'ui.grid.edit'
    ]);

    /**
     * Service to save sampling rules
     */
    EditSamplingApp.factory('SaveSamplingService', [
            '$resource', function($resource) {
                return {
                    SamplingResource : $resource('Sampling/:path', {
                        path : '@path',

                    }, {
                        savesamplingRule : {
                            method : 'POST',
                            params : {
                                path : 'SamplingRules'

                            },
                            isArray : true
                        }

                    })
                };

            }
    ]);

    /**
     * Service to load sampling rule from database
     */
    EditSamplingApp.factory('GetSamplingService', [
            '$resource', function($resource) {

                var EditSamplingRuleRepresentation = $resource('Sampling/SamplingGet', {

                    'query' : {
                        method : 'GET',
                        isArray : 'true'
                    }

                });
                var UniqueSamplingRule = $resource('Sampling/labels', {

                    'query' : {
                        method : 'GET',
                        isArray : 'true'
                    }

                });
                return {

                    getSampling : function(success, error) {

                        return EditSamplingRuleRepresentation.query(success, error);
                    },
                    getUniqueSamplingLabels : function(success, error) {

                        return UniqueSamplingRule.query(success, error);
                    }
                };

            }
    ]);

    EditSamplingApp.controller('EditSamplingController', [
            '$scope', 'SaveSamplingService', 'GetSamplingService', 'uiGridConstants', 'CultureService', 'HistoryService', 'NotificationService', 'AuthorizationService', function($scope, SaveSamplingService, GetSamplingService, uiGridConstants, CultureService, HistoryService, NotificationService, authorizationService) {
                /*----------------------------------------Authorization----------------------------------------*/

                var authentication = false;
                if (authorizationService.hasRole('seed-w20', 'POCNRole')) {
                    authentication = true;
                }

                $scope.authorization = function(role) {
                    return authorizationService.hasRole('seed-w20', role);
                };

                $scope.label = "";
                $scope.amtType = "";
                $scope.frequency = "";
                $scope.lowerLimit = "";
                $scope.higherLimit = "";
                $scope.neededAmt = "";
                $scope.amtOrPercent = "";
                $scope.lowerSymbol = "";
                $scope.higherSymbol = "";
                $scope.description = "";
                $scope.samplingData = [];
                $scope.higherlimit = 0;
                $scope.samplingDataToSave = [];
                const
                Amount_per_criticality = "Amount per criticality";
                const
                Amount_per_Amount = "Amount per Amount";
                const
                Amount_per_period = "Amount per period";
                var vehicule_tous_les = "véhicule tous les";
                const
                mois = "mois";
                const
                si_la_production_est = "si la production est";
                const
                par_tranche_de = "par tranche de";
                const
                de_la_production_tous_les = "de la production tous les";
                const
                et = "et";

                /* header template */
                var headertemplate = '<div class="ngHeaderText"><span>{{col.displayName}}</span>' + '<div ng-if="filterable" class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">' + '<input type="text" class="ui-grid-filter-input" ng-model="colFilter.term" ng-attr-placeholder="{{}}" />' + '<div class="ui-grid-filter-button" ng-click="colFilter.term = null">' + '<i class="ui-grid-icon-cancel" ng-show="!!colFilter.term">&nbsp;</i>' + '</div>' + '</div>' + '</div>';

                /**
                 * Function for data initialization
                 */

                $scope.samplingGrid = {
                    enableHorizontalScrollbar : 1,
                    enableColumnResizing : true,
                    enableCellEditOnFocus : true,
                    rowEditWaitInterval : -1,

                    columnDefs : [
                            {

                                name : 'samplingLabel',
                                field : 'label',
                                displayName : CultureService.localize('cop.samplingRule.grid.label'),
                                headerCellFilter : 'localize',
                                headerCellTemplate : '<div class="ngHeaderText"><span>{{col.displayName}}<span class="astrik">*</span></div>',
                                enableCellSelection : true,
                                enableRowSelection : false,
                                enableCellEditOnFocus : true,
                                enableCellEdit : authentication,
                                cellTemplate : '<div ng-attr-title="{{row.entity.label}}" ><form name="formlabel"><input type="text" ng-readonly="true" ng-model="MODEL_COL_FIELD" maxlength="45"/></form></div>'

                            }, {
                                name : 'AmountType',
                                displayName : CultureService.localize('cop.samplingRule.grid.AmountType'),
                                headerCellFilter : 'localize',
                                editableCellTemplate : 'ui-grid/dropdownEditor',
                                // width : '15%',
                                headerCellTemplate : headertemplate,
                                enableCellEdit : authentication,
                                field : 'amtType',
                                editDropdownValueLabel : 'AmountType',
                                editDropdownOptionsArray : [
                                        {
                                            id : 'Amount per period',
                                            AmountType : 'Amount per period'
                                        }, {
                                            id : 'Amount per criticality',
                                            AmountType : 'Amount per criticality'
                                        }, {
                                            id : 'Amount per Amount',
                                            AmountType : 'Amount per Amount'
                                        }
                                ],

                                cellTemplate : '<div ng-attr-title="{{row.entity.amtType}}"><form name="formlamounttype"><input type="text" ng-readonly="true" ng-model="MODEL_COL_FIELD" maxlength="45"/></form></div>'
                            },

                            {
                                name : 'samplingFrequency',
                                field : 'frequency',
                                displayName : CultureService.localize('cop.samplingRule.grid.Frequency'),
                                headerCellTemplate : headertemplate,
                                headerCellFilter : 'localize',
                                enableCellEdit : authentication,
                                type : 'number',
                                cellTemplate : '<div ng-attr-title="{{row.entity.frequency}}" ><form name="formfrequency"><input type="text" ng-readonly="true" ng-model="MODEL_COL_FIELD" maxlength="11"/></form></div>'
                            }, {
                                name : 'samplingNeededamt',
                                field : 'neededAmt',
                                displayName : CultureService.localize('cop.samplingRule.grid.NeededAmount'),
                                headerCellTemplate : headertemplate,
                                enableCellEdit : authentication,
                                headerCellFilter : 'localize',
                                type : 'number',
                                cellTemplate : '<div ng-attr-title="{{row.entity.neededAmt}}" ><form name="formneededamount"><input type="text" ng-readonly="true" ng-model="MODEL_COL_FIELD"/></form></div>'
                            }, {
                                name : 'samplingAmt',
                                field : 'amtOrPercent',
                                editableCellTemplate : 'ui-grid/dropdownEditor',
                                displayName : CultureService.localize('cop.samplingRule.grid.Amount'),
                                headerCellTemplate : headertemplate,
                                headerCellFilter : 'localize',
                                enableCellEdit : authentication,
                                editDropdownValueLabel : 'Amount',
                                editDropdownOptionsArray : [
                                        {
                                            id : 'Amount',
                                            Amount : 'Amount'
                                        }, {
                                            id : '%',
                                            Amount : '%'
                                        },

                                ],
                                cellTemplate : '<div><form name="formamountorpercent"><input type="text" ng-readonly="true" ng-model="MODEL_COL_FIELD" maxlength="45"/></form></div>'

                            }, {
                                name : 'samplingLowerSymb',
                                field : 'lowerSymbol',
                                editableCellTemplate : 'ui-grid/dropdownEditor',
                                cellEditableCondition : function($scope) {
                                    if ($scope.row.entity.amtType === 'Amount per period') {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                },
                                displayName : CultureService.localize('cop.samplingRule.grid.LowerSymbol'),
                                headerCellTemplate : headertemplate,
                                enableCellEdit : authentication,
                                headerCellFilter : 'localize',
                                // width : '8%',
                                editDropdownValueLabel : 'Lsymbol',
                                editDropdownOptionsArray : [
                                        {
                                            id : '>',
                                            Lsymbol : '>'
                                        }, {
                                            id : '>ou=',
                                            Lsymbol : '>ou='
                                        }

                                ],
                                cellTemplate : '<div><form name="formlowerSymbol"><input type="text" ng-disabled="row.entity.enableLowerSymbol" ng-readonly="true" ng-model="MODEL_COL_FIELD" /></form></div>'

                            }, {
                                name : 'samplingLowerLim',
                                field : 'lowerLimit',
                                enableCellEdit : authentication,
                                displayName : CultureService.localize('cop.samplingRule.grid.LowerLimit'),
                                headerCellTemplate : headertemplate,
                                headerCellFilter : 'localize',
                                type : 'number',
                                validators : {
                                    required : true
                                },
                                cellTemplate : '<div ng-attr-title="{{row.entity.lowerLimit}}" ><form name="formlowerlimit"><input type="text" maxlength="11"  ng-disabled="row.entity.enableLowerLimit"  ng-model="MODEL_COL_FIELD" ng-change="grid.appScope.addNewOrModifiedSamplingData(row.entity)"/></form></div>'

                            }, {
                                name : 'samplingHigherSymb',
                                field : 'higherSymbol',
                                editableCellTemplate : 'ui-grid/dropdownEditor',
                                displayName : CultureService.localize('cop.samplingRule.grid.HigherSymbol'),
                                headerCellTemplate : headertemplate,
                                enableCellEdit : authentication,
                                headerCellFilter : 'localize',
                                // width : '8%',
                                editDropdownValueLabel : 'Hsymbol',
                                cellEditableCondition : function($scope) {
                                    if ($scope.row.entity.amtType === 'Amount per period') {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                },
                                editDropdownOptionsArray : [
                                        {
                                            id : '<',
                                            Hsymbol : '<'
                                        }, {
                                            id : '<ou=',
                                            Hsymbol : '<ou='
                                        }

                                ],
                                cellTemplate : '<div><form name="formhighersymbol"><input type="text" ng-disabled="row.entity.enableHigherSymbol" ng-readonly="true" ng-model="MODEL_COL_FIELD"  /></form></div>'

                            }, {
                                name : 'samplingHigherLim',
                                field : 'higherLimit',
                                enableCellEdit : authentication,
                                displayName : CultureService.localize('cop.samplingRule.grid.HigherLimit'),
                                headerCellTemplate : headertemplate,
                                headerCellFilter : 'localize',
                                type : 'number',
                                cellTemplate : '<div ng-attr-title="{{row.entity.higherLimit}}" ><form name="formhigherlimit"><input type="text"  maxlength="11" ng-disabled="row.entity.enableHigherLimit"  ng-model="MODEL_COL_FIELD"  ng-change="grid.appScope.addNewOrModifiedSamplingData(row.entity)"/></form></div>'

                            }, {

                                name : 'samplingDescription',
                                field : 'description',
                                displayName : "",
                                headerCellTemplate : headertemplate,
                                enableCellEdit : false,
                                width : '25%',
                                cellTemplate : '<div ng-attr-title="{{row.entity.description}}" ><form name="formdescription"><input type="text" ng-readonly="true" ng-model="MODEL_COL_FIELD"/></form></div>'

                            }

                    ],

                    data : 'samplingData',
                    onRegisterApi : function(gridApi) {
                        $scope.gridApi = gridApi;
                        gridApi.edit.on.afterCellEdit($scope, function(rowentity, colDef, newValue, oldValue) {

                            if (newValue !== oldValue) {
                                $scope.addNewOrModifiedSamplingData(rowentity);
                            }
                            $scope.validateRow(rowentity);
                            $scope.createDescription(rowentity);
                        });

                    }

                },

                $scope.init = function() {
                    GetSamplingService.getSampling(function(response) {
                        for (var i = 0; i < response.length; i++) {
                            $scope.validateRow(response[i]);
                        }
                        $scope.samplingData = response;
                        $scope.samplingDataCopy = angular.copy($scope.samplingData);
                        ;

                    },

                    function(error) {
                        NotificationService.notify("cop.samplingRule.message.intializeError");
                    });
                };

                /**
                 * Function for adding row
                 */

                $scope.canEdit = function() {

                    return $scope.lsymbol;
                };
                $scope.addRow = function() {

                    var obj = {
                        label : $scope.label,
                        amtType : $scope.amtType,
                        frequency : $scope.frequency,
                        lowerLimit : $scope.lowerLimit,
                        higherLimit : $scope.higherLimit,
                        neededAmt : $scope.neededAmt,
                        amtOrPercent : $scope.amtOrPercent,
                        lowerSymbol : $scope.lowerSymbol,
                        higherSymbol : $scope.higherSymbol,
                        description : "",
                        newDataAdded : true

                    };
                    $scope.samplingData.push(obj);

                };

                /**
                 * Function for checking Amount Type
                 */

                /**
                 * Function for checking lower symbol
                 */

                $scope.addNewOrModifiedSamplingData = function(rowentity) {
                    rowentity.dataModified = true;

                };

                $scope.validateRow = function(rowentity) {
                    if (rowentity.amtType === Amount_per_criticality || rowentity.amtType === Amount_per_Amount && rowentity.amtOrPercent === "%") {

                        rowentity.lowerSymbol = "";
                        rowentity.higherSymbol = "";
                        rowentity.lowerLimit = "";
                        rowentity.higherLimit = "";
                        rowentity.enableLowerSymbol = true;
                        rowentity.enableLowerLimit = true;
                        rowentity.enableHigherSymbol = true;
                        rowentity.enableHigherLimit = true;

                    } else if (rowentity.amtType === Amount_per_Amount && rowentity.amtOrPercent === "Amount") {

                        rowentity.lowerSymbol = "";
                        rowentity.higherSymbol = "";
                        rowentity.higherLimit = "";
                        rowentity.enableLowerSymbol = true;
                        rowentity.enableHigherSymbol = true;
                        rowentity.enableHigherLimit = true;
                        rowentity.enableLowerLimit = false;
                        this.AmtamWord = "";
                    } else if (rowentity.selected === Amount_per_period) {

                        rowentity.enableLowerSymbol = false;
                        rowentity.enableLowerLimit = false;
                        rowentity.enableHigherSymbol = false;
                        rowentity.enableHigherLimit = false;

                    } else {

                        rowentity.enableLowerSymbol = false;
                        rowentity.enableLowerLimit = false;
                        rowentity.enableHigherSymbol = false;
                        rowentity.enableHigherLimit = false;

                    }
                };
                $scope.createDescription = function(rowentity) {

                    if (rowentity.neededAmt > 1)
                        vehicule_tous_les = "véhicules tous les";
                    else
                        vehicule_tous_les = "véhicule tous les";
                    if (rowentity.lowerSymbol == "" && rowentity.higherSymbol == "" && rowentity.lowerLimit == "" && rowentity.higherLimit == "" && rowentity.label == "") {
                        rowentity.description = "";
                    } else if (rowentity.lowerSymbol == "" && rowentity.higherSymbol != "" && rowentity.lowerLimit == "" && rowentity.higherLimit != "") {

                        rowentity.description = rowentity.neededAmt + " " + vehicule_tous_les + " " + rowentity.frequency + " " + mois + " " + si_la_production_est + " " + rowentity.higherSymbol + " " + rowentity.higherLimit;
                    } else if (rowentity.lowerSymbol != "" && rowentity.higherSymbol == "" && rowentity.lowerLimit != "" && rowentity.higherLimit == "") {
                        rowentity.description = rowentity.neededAmt + " " + vehicule_tous_les + " " + rowentity.frequency + " " + mois + " " + si_la_production_est + " " + rowentity.lowerSymbol + " " + rowentity.lowerLimit;
                    } else if (rowentity.lowerSymbol == "" && rowentity.lowerLimit == "" && rowentity.higherSymbol == "" && rowentity.higherLimit == "" && rowentity.amtOrPercent != "%") {
                        rowentity.description = rowentity.neededAmt + " " + vehicule_tous_les + " " + rowentity.frequency + " " + mois;
                    } else if (rowentity.lowerSymbol == "" && rowentity.higherSymbol == "" && rowentity.higherLimit == "" && rowentity.lowerLimit != "" && rowentity.amtOrPercent == "Amount") {
                        rowentity.description = rowentity.neededAmt + " " + vehicule_tous_les + " " + rowentity.frequency + " " + mois + " " + par_tranche_de + " " + rowentity.lowerLimit;
                    } else if (rowentity.lowerSymbol == "" && rowentity.lowerLimit == "" && rowentity.higherSymbol == "" && rowentity.higherLimit == "" && rowentity.amtOrPercent == "%")

                    {
                        rowentity.description = rowentity.neededAmt + " " + rowentity.amtOrPercent + " " + de_la_production_tous_les + " " + rowentity.frequency + " " + mois;
                    } else if (rowentity.lowerSymbol != "" && rowentity.higherSymbol != "" && rowentity.lowerLimit != "" && rowentity.higherLimit != "" && rowentity.label != "") {

                        rowentity.description = rowentity.neededAmt + " " + vehicule_tous_les + " " + rowentity.frequency + " " + mois + " " + si_la_production_est + " " + rowentity.lowerSymbol + " " + rowentity.lowerLimit + " " + et + " " + rowentity.higherSymbol + " " + rowentity.higherLimit;
                    }

                    $scope.description = rowentity.description;
                }

                /**
                 * Function for saving sampling rule to database
                 */

                $scope.saveRule = function() {
                    var templist = [];
                    for (var i = 0; i < $scope.samplingData.length; i++) {
                        if ($scope.samplingData[i].dataModified == true && $scope.samplingData[i].label != "" || $scope.samplingData[i].newDataAdded == true && $scope.samplingData[i].label != "") {
                            var obj = {
                                "label" : $scope.samplingData[i]["label"],
                                "amtType" : $scope.samplingData[i]["amtType"],
                                "frequency" : $scope.samplingData[i]["frequency"],
                                "lowerLimit" : $scope.samplingData[i]["lowerLimit"],
                                "higherLimit" : $scope.samplingData[i]["higherLimit"],
                                "neededAmt" : $scope.samplingData[i]["neededAmt"],
                                "amtOrPercent" : $scope.samplingData[i]["amtOrPercent"],
                                "lowerSymbol" : $scope.samplingData[i]["lowerSymbol"],
                                "higherSymbol" : $scope.samplingData[i]["higherSymbol"],
                                "description" : $scope.samplingData[i]["description"],
                                "entityId" : $scope.samplingData[i]["entityId"],
                                "enableLowerLimit" : $scope.samplingData[i]["enableLowerLimit"],
                                "enableLowerSymbol" : $scope.samplingData[i]["enableLowerSymbol"],
                                "enableHigherLimit" : $scope.samplingData[i]["enableHigherLimit"],
                                "enableHigherSymbol" : $scope.samplingData[i]["enableHigherSymbol"]
                            };
                            templist.push(obj);

                        }
                        var objectToSend = {
                            'samplingRuleRepresentation' : templist
                        };
                    }
                    ;

                    SaveSamplingService.SamplingResource.savesamplingRule(objectToSend, function(data) {

                        NotificationService.notify(CultureService.localize("cop.samplingRule.add.successMessage"));

                        $scope.init();

                    }, function(error) {
                        NotificationService.notify(CultureService.localize("cop.samplingRule.add.errorMessage"));
                    }

                    )

                };

                // Start Changes on Cancel Button
                $scope.CancelRuleConfirmation = function() {
                    // Get all dirty rows
                    $scope.gridDirtyRows = $scope.gridApi.rowEdit.getDirtyRows($scope.gridApi.grid);
                    var dataDirtyRows = $scope.gridDirtyRows.map(function(gridRow) {
                        return gridRow.entity;

                    });
                    // $scope.samplingData = $scope.samplingDataTemp;
                    // clean
                    // $scope.gridApi.rowEdit.setRowsClean(dataDirtyRows);
                    // $scope.gridApi.rowEdit.flushDirtyRows($scope.gridApi.grid);
                    if (dataDirtyRows.length > 0) {
                        $('#ConfirmCancelSamplingRule').modal('show');
                    }

                };
                $scope.cancelModifiedRuleChanges = function() {
                    $scope.samplingData = angular.copy($scope.samplingDataCopy);
                    var dataDirtyRows = $scope.gridDirtyRows.map(function(gridRow) {
                        return gridRow.entity;

                    });
                    $scope.gridApi.core.refresh();
                    $scope.gridApi.rowEdit.setRowsClean(dataDirtyRows);
                };
                // END Of Changes on Cancel Button
                /*------------------------------------------History-------------------------------------------------*/

                $scope.HistoryGridOptions = {
                    enableColumnResizing : true,
                    enableFiltering : true,
                    enableSorting : true,
                    columnDefs : [

                            {
                                name : 'date',
                                displayName : CultureService.localize('cop.history.date'),
                                type : 'date',
                                field : 'updationDate',
                                cellFilter : 'date:\'dd/MM/yyyy HH:mm:ss\'',
                                enableCellEdit : false,
                                cellTooltip : function(row) {
                                    return row.entity.updationDate;
                                }
                            }, {
                                name : 'userId',
                                displayName : CultureService.localize('cop.history.userId'),
                                field : 'userId',
                                enableCellEdit : false
                            }, {
                                name : 'userProfile',
                                displayName : CultureService.localize('cop.history.userProfile'),
                                field : 'userProfile',
                                enableCellEdit : false
                            }, {
                                name : 'description',
                                displayName : CultureService.localize('cop.history.description'),
                                field : 'description',
                                enableCellEdit : false
                            }, {
                                name : 'oldValue',
                                displayName : CultureService.localize('cop.history.oldValue'),
                                field : 'oldValue',
                                enableCellEdit : false
                            }, {
                                name : 'newValue',
                                displayName : CultureService.localize('cop.history.newValue'),
                                field : 'newValue',
                                enableCellEdit : false
                            },

                    ],
                    data : 'allHistory',

                };

                /* to get History Data */
                $scope.allHistory = [];
                $scope.getAllHistory = function(screenId) {

                    var historyResponse = HistoryService.HistroyResource.historyData({
                        screenId : screenId
                    }, function() {
                        $scope.allHistory = historyResponse;

                    }, function() {

                    });
                };
                $scope.showHistoryData = function(screenId) {
                    $scope.getAllHistory(screenId);
                    $('#showHistoryBox').modal('show');
                };
                /** ***************************************** */
                $scope.slideToggle = function() {

                    $("#wrapper").toggleClass("toggled");
                    $(".slide-resize").toggleClass("col-lg-10");
                    $("#menu-toggle").toggleClass('fa-chevron-right');
                };
                $("#emsDetail").css('background-color', '#DBDBDB', 'color', '#fff');
            }
    ]);

    return {
        angularModules : [
            'EditSamplingApp'
        ]
    };
});