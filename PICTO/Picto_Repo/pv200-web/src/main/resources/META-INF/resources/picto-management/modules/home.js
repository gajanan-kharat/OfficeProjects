var color = 0; /*
                 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
                 * 
                 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
                 */
define([
        'require', 'module', '{angular}/angular'
], function(require, module, angular) {
    'use strict';

    var mod;
    mod = angular.module('infiniteScroll', []), mod.directive('infiniteScroll', [
            "$window", function($window) {
                return {
                    link : function(scope, element, attrs) {
                        var offset = parseInt(attrs.threshold) || 0;
                        var e = element[0];

                        element.bind('scroll', function() {
                            if (scope.$eval(attrs.canLoad) && e.scrollTop + e.offsetHeight >= e.scrollHeight - offset) {
                          
                                    scope.$apply(attrs.infiniteScroll);
                                
                                
                            }
                        });
                    }
                };
            }
    ]);

    mod = angular.module('customControl', []).directive('elastic', [
                           '$timeout',
                           function($timeout) {
                               return {
                                   restrict: 'A',
                                   link: function($scope, element,attrs) {
                                       $scope.initialHeight = $scope.initialHeight || element[0].style.height;
                                       var resize = function() {
                                           element[0].style.height = $scope.initialHeight;
                                           element[0].style.height = "" + element[0].scrollHeight + "px";
                                       };
                                       element.on("input change", resize);
                                       $timeout(resize, 0);
                                       $scope.$watch(attrs.ngModel, resize);
                                   }
                               
                               };
                           }
                       ]);  

    
    var referentialManagement = angular.module('PictoManagement', [
            'ui.bootstrap', 'PictoCommon', 'infiniteScroll', 'customControl'
    ]), config = module && module.config() || {};   

    
    referentialManagement.controller('HomeController', [
            '$scope','$modal', '$window', '$http', 'PictoService', 'PictoFamilyService', 'DownloadPictoService', 'DownloadAIWorkService', 'ValidateAIWorkService', 'NotificatonContribService', 'ShoppingCartService', 'FavouritesService', 'ValidateNotificationService', 'CultureService', 'FilterService', 'CategoryService', 'PictoFamilyPDFService', 'PictoFamilyMultiPDFService', 'AuthenticationService', 'AuthorizationService', 'SuperCategoryService', 'PictosInCartService', 'CartService', 'SearchTextService', 'NotificationService', 'ApplicationService', 'PictoVariantService', 'GetAllSuperCategoryService', function($scope, $modal, $window, $http, pictoService, pictoFamilyService, downloadPictoService, downloadAIWorkService, validateAIWorkService, notificatonContribService, shoppingCartService, favouritesService, validateNotificationService, cultureService, filterService, categoryService, pictoFamilyPDFService, pictoFamilyMultiPDFService, authenticationService, authorizationService, superCategoryService, pictosInCartService, cartService, searchTextService, notificationService, applicationService, pictoVariantService, getAllSuperCategoryService) {
                var htmlElt = window.document.getElementsByTagName('html');
                $scope.version = htmlElt[0].getAttribute('data-w20-app-version');
                $scope.appid = applicationService.applicationId;  
				$scope.notification = [];
				/* MJ 26-Jul-2016 PSA PRP024006-54 Start */
				$scope.isNullType = false;
				/* MJ 26-Jul-2016 PSA PRP024006-54 Ends */
				$scope.pictosInCart = [];

                $scope.pictosInFav = [];
                $scope.selectedPictos = [];
                $scope.listOfCategories = [];               
                $scope.listOfSuperCategories = [];
                $scope.cartPictosFromServer = [];
                $scope.items = [];
                $scope.allPictos = [];
                $scope.selectedVariantPictos = [];
                $scope.availableVarList = [];

                $scope.multiSelectionCartMenu = false;
                $scope.createCaseFlag = false;
                $scope.deleteCaseFlag = false;
                $scope.notificationView = false;
                $scope.isCharteSort = false;
                $scope.isCharteSortApplied = false;
                $scope.selectedPictosIncart = [];
                $scope.selectedPicRefNumsIncart = [];

                $scope.pictoInfo = {};
                $scope.pictoInfoNew = {};
                $scope.tempPictoInfo = [];
                $scope.pictoInfoIncart = [];
                $scope.selectedPictoIds = [];
                $scope.selectedPictoObj = [];

                $scope.PictoTitleStatus = cultureService.localize('picto.js.label.titleStatus');
                $scope.PictoTitleStatusIncart = cultureService.localize('picto.js.label.titleStatus');
                $scope.informationViewVisibility = false;
                $scope.informationViewOptionsVisibility = false;
                $scope.createNewPictoFamily = false;
                $scope.ShowMultipleSelected = false;
                $scope.HideMultipleSelected = false;
                $scope.updateFlag = false;
                $scope.updateCartFlag = false;
                $scope.allVarsSelected = false;

                $scope.tempPictoInfo = [];
                $scope.pictos = [];
                $scope.frontage = null;
                $scope.remainVariant = [];

                $scope.aiWorkPictos = [];
                $scope.Category = null;
                $scope.nullChartePicto = true;
                $scope.typeIds = [];
                var check = 0;
                $scope.pictoInfo.deletedPictosList = [];
                $scope.pictoInfo.deletedSpecDrawList = [];
                $scope.downloadPictoName = null;
                $scope.downloadPictoNameInCart = null;

                $scope.formatVals = [];
                $scope.fileTypeVals = [];
                $scope.listSuperCategories = [];
                $scope.listTypes = [];
                $scope.allCatogory =[];
                $scope.listValidation = [];
                $scope.listInformation = [];
                $scope.listofVisbile = [];
                $scope.selectedCartPicto = [];
                $scope.selectedCartPictoObj = [];
                $scope.tempSelected =[];

                $scope.userLogin = authenticationService.subjectPrincipals().userId;
                $scope.userName = authenticationService.subjectPrincipals().firstName;
                $scope.hasProfileDRole = authorizationService.hasRole('seed-w20', 'PICTODRole');
                $scope.hasProfileARole = authorizationService.hasRole('seed-w20', 'PICTOARole');
                $scope.hasProfileCRole = authorizationService.hasRole('seed-w20', 'PICTOCRole');
                $scope.hasProfileGRole = authorizationService.hasRole('seed-w20', 'PICTOGRole');
                $scope.hasProfileERole = authorizationService.hasRole('seed-w20', 'PICTOERole');

                $scope.language = "fr";
                $scope.en = "English";
                $scope.fr = "French";

                $scope.focusChangeFlag = false;
                $scope.serviceInProgress = false;
                $scope.specificDrawingName = null;
                $scope.specificDrawingCommentFR = null;
                $scope.specificDrawingCommentEN = null;
                $scope.imageColor=false;
                $scope.ruleName = null;
                $scope.urlLink = null;
                
                $scope.reloadFlag = false;

                $scope.sd = [
                        {
                            title : "Specific K0 12/06/2014",
                            fr : "Utiloser",
                            en : "To use only for k0"
                        }, {
                            title : 'Specific K1 11/06/2014',
                            fr : 'Utiloser',
                            en : 'To use only for k1'
                        }
                ];

                $scope.md = [
                        {
                            title : "ECE_121"
                        }, {
                            title : "Iso_text"
                        }
                ];

                $scope.vari = [
                    {
                        title : "ECE_121",
                        imageLocation : ""
                    }
                ];

                $scope.dateFilter = [
                        {
                            fieldVal : "Before"
                        }, {
                            fieldVal : "After"
                        }
                ];

                $scope.listOfSort = [
                        {
                            sortBy : "Réf"
                        }, {
                            sortBy : "Charte"
                        }, {
                            sortBy : "Date"
                        },
                ];
                
                $scope.listOfSortEn = [
                                     {
                                         sortBy : "Ref"
                                     }, {
                                         sortBy : "Charter"
                                     }, {
                                         sortBy : "Date"
                                     },
                             ];
                
                $scope.addLoader = function(){
                    $('#pictoHomePage').addClass('loaderBlock');
                    $('#pictoLoader').addClass('loaderNew');
                }
                
                $scope.removeLoader = function(){
                    $('#pictoLoader').removeClass('loaderNew');
                    $('#pictoHomePage').removeClass('loaderBlock');
                }

                $scope.switchCultureCheck = function(lname) {
                    $scope.currentLanguage = lname;
                    if (lname == "en") {
                        $scope.language = "en";
                    } else if (lname == "fr") {
                        $scope.language = "fr";
                    }
                };
                $scope.switchCultureCheck(cultureService.culture().name);
                $scope.helpModal = function() {

                    if ($scope.language == "en") {
                        $window.open('http://docinfogroupe.inetpsa.com/ead/doc/ref.20555_16_01412/v.vc/pj');
                    } else {
                        $window.open('http://docinfogroupe.inetpsa.com/ead/doc/ref.20555_16_01410/v.vc/pj');
                    }

                };

                // to update notification
                $scope.notificationUpdate = function() {
                    var notif = notificatonContribService.notificatonContrib(function() {
                        $scope.notification = notif;
                    }, function() {

                    });

                };
                $scope.cartUpdate = function() {              
                    var cartItems = pictosInCartService.getPictosInCart(function() {
                    	 $scope.cartPictosFromServer = cartItems;                       

                    }, function() {

                    });
                };

                
                $(document).ready(function() {
                    $scope.notificationUpdate();
                    $scope.cartUpdate();
                    var t = categoryService.getAllCategories(function() {                    
                        $scope.listOfCategories = t;                    
                    }, function() {
                        $('#notifierDlg').modal('show');

                        $scope.notificationMessage = cultureService.localize('picto.message.categoryError');

                    });
                        
                });
                
               
                
                $scope.selectAll = function() {
                	$scope.pictoActive=false;
                    $scope.picIds = null;
                    $scope.reference = null
                    $scope.selectedPictos.splice(0);
                    $scope.selectedPictoIds.splice(0);
                    $scope.selectedPictoObj.splice(0);
                    if ($scope.hasChecked) {
                        for (var i = 0; i < $scope.allPictos.length; i++) {
                            var obj = $scope.allPictos[i];
                            $scope.picIds = obj.id;
                            $scope.reference = obj.familyID.referenceNum;
                            $scope.selectedPictos.push($scope.reference)
                            $scope.selectedPictoIds.push($scope.picIds);
                            $scope.selectedPictoObj.push(obj);
                            $scope.tempSelected.push($scope.reference);                            
                   
                            setInterval(function(){
                            	if(!$scope.pictoActive)
                            	{
                            		$('.picto-col').addClass('picto-active');

                            	}
                            },500);                           
                           
                        }

                    } else {
                    	$scope.pictoActive=true;
                        $('.custom-picto-col').removeClass('picto-active');
                        $scope.selectedPictos.splice(0);
                        $scope.selectedPictoIds.splice(0);
                        $scope.selectedPictoObj.splice(0);
                        $scope.tempSelected.splice(0);
                    }
                    if ($scope.selectedPictoIds.length > 1) {
                        $scope.PictoTitleStatus = cultureService.localize('picto.js.label.severalPictos');
                        $scope.SeveralPictoStatus = cultureService.localize('picto.js.label.SeveralPictoStatus');
                        $scope.informationViewVisibility = false;
                        $scope.informationViewOptionsVisibility = false;
                        $scope.createNewPictoFamily = false;
                        $scope.updateFlag = false;
                        $scope.ShowMultipleSelected = true;
                        $scope.HideMultipleSelected = true;
                        $scope.addNewpictoview = false;

                    } else {
                        $scope.addNewpictoview = false;
                        $scope.PictoTitleStatus = "Consult";
                        $scope.SeveralPictoStatus = "";
                        $scope.informationViewVisibility = false;
                        $scope.informationViewOptionsVisibility = false;
                        $scope.createNewPictoFamily = false;
                        $scope.updateFlag = false;
                        $scope.ShowMultipleSelected = false;
                        $scope.frontage = null;
                        $scope.remainVariant = [];
                        if ($scope.selectedPictoIds.length != 0) {
                            $scope.HideMultipleSelected = false;
                        }
                    }            
                    if ($scope.selectedPictoIds.length>1)
                    {
                    	$scope.setFavCartValue();   
                    }
                };

                /* PB - Added - 15-Jul-16 - Start */
                $scope.selectAllInCart = function() {
                    $scope.cartInformationViewVisibility = false;
                    $scope.picIds = null;
                    $scope.pictoSelectedIncart = true;
                    $scope.selectedPictosIncart.splice(0);
                    $scope.selectedPicRefNumsIncart.splice(0);
                    $scope.selectedCartPictoObj.splice(0);
                    if ($scope.hasCheckedIncart) {
                        for (var i = 0; i < $scope.cartPictosFromServer.length; i++) {
                            var obj = $scope.cartPictosFromServer[i];
                            $scope.picIds = obj.id;
                            $scope.reference = obj.familyID.referenceNum;
                            $scope.selectedPictosIncart.push($scope.picIds);
                            $scope.selectedPicRefNumsIncart.push($scope.reference);
                            $scope.selectedCartPictoObj.push(obj);
                            $('.custom-picto-col-incart').addClass('picto-active');
                        }

                    } else {
                        $('.custom-picto-col-incart').removeClass('picto-active');
                        $scope.selectedPictosIncart.splice(0);
                        $scope.selectedPicRefNumsIncart.splice(0);
                        $scope.pictoSelectedIncart = false;
                    }

                    if ($scope.selectedPictosIncart.length > 1) {
                        $scope.PictoTitleStatusIncart = cultureService.localize('picto.js.label.severalPictos');
                        $scope.SeveralPictoStatusIncart = cultureService.localize('picto.js.label.SeveralPictoStatus');
                        $scope.cartInformationViewVisibilitySeveral = true;
                        $scope.pictoSelectedForInfoIncart = false;
                        $scope.pictoSelectedIncart = true;

                    } else {
                        $scope.PictoTitleStatusIncart = "Consult";
                        $scope.SeveralPictoStatusIncart = "";
                    }
                    if ($scope.selectedPictosIncart.length>1)
                    {
                    	$scope.setFavCartValueShop();   
                    }
                };

                /********** Prasad : 12-05-2017 Download of AI Work file on double click START***********/
                $scope.downloadPictoAiFile = function(refNum, pictoId, calledFrom){
                    if ($scope.admin) {
                        if(calledFrom == 'centralArea' || calledFrom == 'infoArea' || calledFrom == 'centralAreaMultiSelection'){
                            $scope.ifDownloadIsFromCart = false;
                            if(calledFrom == 'centralArea' || calledFrom == 'infoArea'){
                                $scope.selectedPictoIds = [];
                                $scope.selectedPictos = [];  
                                $scope.selectedPictoIds.push(pictoId);
                                $scope.selectedPictos.push(refNum);
                                var pictoIds = pictoId;
                                /*To make the picto as active state on double click*/
                                var status = angular.element(document.querySelector('#picto' + refNum));
                                $(status).addClass('picto-active');  
                            }else if(calledFrom == 'centralAreaMultiSelection'){
                                var pictoIds = $scope.selectedPictoIds;
                            }
                        }else{
                            $scope.ifDownloadIsFromCart = true;
                            if(calledFrom == 'cartCentralArea' || calledFrom == 'cartInfoArea'){
                                $scope.selectedPictosIncart = [];
                                $scope.selectedPicRefNumsIncart = [];                      
                                $scope.selectedPictosIncart.push(pictoId);
                                $scope.selectedPicRefNumsIncart.push(refNum);
                                var pictoIds = pictoId;
                                var status = angular.element(document.querySelector('#pictoCart' + pictoId));
                                $(status).addClass('picto-active');
                            }else if(calledFrom == 'cartAreaMultiSelection'){
                                var pictoIds = $scope.selectedPictosIncart;
                            }
                        }

                        
                        //get picto infomration
                        $scope.aiWorkPictos = downloadAIWorkService.downloadAIWork(pictoIds, function() {
                        if ($scope.aiWorkPictos.length > 0) {
                            $('#downloadAiFile').modal('show');
                        }
                        }, function(error) {

                            $('#notifierDlg').modal('show');
                            if (error.status == 420) {
                                /*
                                 * SN - PSA - PRP024006-39 - 26-Jul-16 - Start
                                 */
                                $scope.notificationMessage = cultureService.localize('picto.message.thickClientInstall');
                                /*
                                 * SN - PSA - PRP024006-39 - 26-Jul-16 - End
                                 */
                            } else {
                                $scope.notificationMessage = cultureService.localize('picto.message.downloadAIError') + error.status;
                            }

                        });

                        $scope.infoAreaStatus();
                    }
                }
                /********** Prasad : 12-05-2017 Download of AI Work file on double click END ***********/
                

                $scope.Category = null;
                $scope.GetCategoryId = function(categoryId) {
                    $scope.Category = categoryId;
                }
                $scope.typeIds = [];
                /*
                 * MJ 08-Aug-2016 PSA PRP024006-54 Start
                 */
                $scope.imageType = function(imgTypeId) {
                    var imgTypeIdSelected = true;
                    if ($scope.typeIds.length > 0) {
                        for (var i = 0; i < $scope.typeIds.length; i++) {
                            if (imgTypeId === $scope.typeIds[i]) {
                                $scope.typeIds.splice(i, 1);
                                $scope.PictoFilterRepresentation.defaultFilter=false;
                                imgTypeIdSelected = false;
                                if (imgTypeId === 4) {
                                    $scope.isNullType = false;
                                }
                            }

                        }
                        if (imgTypeIdSelected) {
                            if (imgTypeId === 4) {
                                $scope.isNullType = true;
                                $scope.PictoFilterRepresentation.defaultFilter=false;
                            }

                            $scope.typeIds.push(imgTypeId);
                        }

                    } else {

                        $scope.typeIds.push(imgTypeId);
                        if (imgTypeId === 4) {
                            $scope.isNullType = true;
                        }
                    }

                    $scope.filterPictos();
                };

                /*
                 * MJ 08-Aug-2016 PSA PRP024006-54 End
                 */

                /**********   Filter Function *********/
                $scope.PictoFilterRepresentation = {

                                            defaultFilter : false,
                                            category : null,
                                            imageType : $scope.typeIds,
                                            validated : false,
                                            inProgress : false,
                                            pictoInfo : false,
                                            infoInProgress : false,
                                            dateSpan : null,
                                            dateFilter : null,
                                            favPictos : false,
                                            exceptFavPictos : false,
                                            visiblePicto : false,
                                            invisiblePictos : false,
                                            inModificationPictos : false,
                                            exceptInModification : false,
                                            sortByParameter : null,
                                            nullTypeSelected : $scope.isNullType,
                                            searchText : null
                                        };
                
                						setTimeout(function(){
                								$scope.PictoFilterRepresentation.dateSpan = 'After';
                						},6000);


                                        $scope.defaultFilterFun = function() {
                                            if ($scope.PictoFilterRepresentation.defaultFilter) {
                                                if (authorizationService.hasRole('seed-w20', 'PICTOARole')
                                                        || authorizationService.hasRole('seed-w20', 'PICTODRole')
                                                        || authorizationService.hasRole('seed-w20', 'PICTOERole')
                                                        || authorizationService.hasRole('seed-w20', 'ADMINRole')
                                                        || authorizationService.hasRole('seed-w20', 'PICTOCRole')
                                                        || authorizationService.hasRole('seed-w20', 'CONTRIBUTEURRole')
                                                        ||authorizationService.hasRole('seed-w20', 'PICTOGRole')
                                                        || authorizationService.hasRole('seed-w20', 'ENDUSERRole')) {                                                   
                                                    $scope.PictoFilterRepresentation.category = null;
                                                    $scope.filterCategory= null;
                                                    $scope.PictoFilterRepresentation.imageType = [];
                                                    $scope.typeIds = [];
                                                    $scope.PictoFilterRepresentation.validated = false;
                                                    $scope.PictoFilterRepresentation.inProgress = false;
                                                    $scope.PictoFilterRepresentation.pictoInfo = false;
                                                    $scope.PictoFilterRepresentation.infoInProgress = false;
                                                    $scope.PictoFilterRepresentation.dateSpan = null;
                                                    $scope.PictoFilterRepresentation.dateFilter = null;
                                                    $scope.PictoFilterRepresentation.favPictos = false;
                                                    $scope.PictoFilterRepresentation.exceptFavPictos = false;
                                                    $scope.PictoFilterRepresentation.visiblePicto = false;
                                                    $scope.PictoFilterRepresentation.invisiblePictos = false;
                                                    $scope.PictoFilterRepresentation.inModificationPictos = false;
                                                    $scope.PictoFilterRepresentation.exceptInModification = false;
                                                    $scope.PictoFilterRepresentation.nullTypeSelected = false;
                                                    $scope.isNullType = false;
                                                    $scope.imageType4 = false;
                                                    $scope.imageType1 = false;
                                                    $scope.imageType2 = false;
                                                    $scope.imageType3 = false;
                                                    $scope.filterPictos();
                                                }
                                            } 
                                            else {
                                                if (authorizationService.hasRole('seed-w20', 'PICTOCRole')
                                                        || authorizationService.hasRole('seed-w20', 'CONTRIBUTEURRole')||authorizationService.hasRole('seed-w20', 'PICTOGRole')
                                                        || authorizationService.hasRole('seed-w20', 'ENDUSERRole')) {
                                                    $scope.imageType1 = false;
                                                    $scope.imageType2 = false;
                                                    $scope.imageType3 = false;
                                                    $scope.typeIds.splice(0);
                                                    $scope.filterPictos();
                                                }
                                            }
                                        };
                                        
                                        $scope.sortingPicto = function(sortValue) {
                                            $scope.PictoFilterRepresentation.sortByParameter = sortValue;
                                            $scope.sortByParamName = sortValue;
                                            if(sortValue == 'Réf')
                                            	{
                                            	$scope.sortByParamName='Ref';
                                            	}
                                            
                                            if (sortValue == 'Charte' || sortValue=='Charter' ) {
                                                $scope.isCharteSort = true;
                                               
                                            } else {
                                                $scope.isCharteSort = false;
                                            }
                                            $scope.filterPictos();
                                        };
                                        $scope.categoryFilter = function(categoryName) {
                                            $scope.PictoFilterRepresentation.category = categoryName;
                                            $scope.filterPictos();
                                        };
                                        
                                        $scope.filterObj ={}                                        
                                                                               
                                        $scope.filterPictos = function() {
                                        	$scope.filterObj.filterCount =0;
                                        	                                            
                                            $scope.PictoFilterRepresentation.imageType=$scope.typeIds;
                                            if ($scope.PictoFilterRepresentation.dateFilter == "") {
                                                $scope.PictoFilterRepresentation.dateFilter = null;
                                            }
                                            $scope.appliedFilterString = '';
                                            if ($scope.PictoFilterRepresentation.searchText != null && $scope.PictoFilterRepresentation.searchText!='') {
                                                $scope.appliedFilterString = $scope.appliedFilterString + '<<'+$scope.PictoFilterRepresentation.searchText
                                                        + '>> + ';
                                            }
                                            
                                            if($scope.filterCategory !=null){
                                                $scope.PictoFilterRepresentation.category =$scope.filterCategory.name;      
                                                $scope.filterObj.filterCount++;
                                                $scope.PictoFilterRepresentation.defaultFilter=false;
                                                if($scope.language==='fr')
                                                {
                                                    $scope.appliedFilterString = $scope.appliedFilterString+$scope.filterCategory.categoryFr+ ' ; ';
                                                }
                                                else{
                                                    $scope.appliedFilterString = $scope.appliedFilterString+$scope.filterCategory.categoryEn + ' ; ';
                                                }
                                            }
                                            else
                                            {
                                                $scope.PictoFilterRepresentation.category= null;
                                            }
                                           
                                            if ($scope.typeIds.length != 0) {
                                                $scope.isCharteType=false;
                                               
                                                $scope.imageTypeString = '';
                                               
                                                for(var i=0;i<$scope.typeIds.length;i++){
                                                   
                                                    if($scope.typeIds[i]=='1'){
                                                        $scope.isCharteType=true;
                                                        $scope.PictoFilterRepresentation.defaultFilter=false;
                                                        $scope.filterObj.filterCount++;
                                                        $scope.imageTypeString = $scope.imageTypeString+
                                                        cultureService.localize('picto.filter.feedack.label.nonIso')+' ; ';
                                                    }
                                                    if($scope.typeIds[i]=='2'){
                                                        $scope.PictoFilterRepresentation.defaultFilter=false;
                                                        $scope.filterObj.filterCount++;
                                                        $scope.isCharteType=true;
                                                        $scope.imageTypeString = $scope.imageTypeString+
                                                        cultureService.localize('picto.filter.feedack.label.isoAut')+' ;';
                                                    }
                                                    if($scope.typeIds[i]=='3'){
                                                        $scope.isCharteType=true;
                                                        $scope.PictoFilterRepresentation.defaultFilter=false;
                                                        $scope.filterObj.filterCount++;
                                                        $scope.imageTypeString = $scope.imageTypeString+
                                                        cultureService.localize('picto.filter.feedack.label.isoReg')+' ; ';
                                                    }
                                                    
                                                }
                                                $scope.imageTypeString = $scope.imageTypeString.substring(0, $scope.imageTypeString.length - 2);
                                                
                                               if($scope.isCharteType){
                                                   $scope.appliedFilterString = $scope.appliedFilterString + cultureService.localize('picto.filter.feedack.label.isCharte')+' ('+$scope.imageTypeString+') ; ';
                                               }
                                                for(var i=0;i<$scope.typeIds.length;i++){
                                                    if($scope.typeIds[i]=='4'){
                                                        $scope.PictoFilterRepresentation.defaultFilter=false;
                                                        $scope.filterObj.filterCount++;
                                                        $scope.appliedFilterString = $scope.appliedFilterString+
                                                        cultureService.localize('picto.filter.label.others')+'; ';
                                                    }
                                                }
                                            }
                                            
                                            if ($scope.PictoFilterRepresentation.validated == true) {
                                            	$scope.filterObj.filterCount++;
                                                $scope.PictoFilterRepresentation.defaultFilter=false;
                                                $scope.appliedFilterString = $scope.appliedFilterString + cultureService.localize('picto.filter.label.validateProgress') +' ; ';
                                            }

                                            if ($scope.PictoFilterRepresentation.inProgress == true) {
                                            	$scope.filterObj.filterCount++;
                                                $scope.PictoFilterRepresentation.defaultFilter=false;
                                                $scope.appliedFilterString = $scope.appliedFilterString + cultureService.localize('picto.filter.label.inProgressValidation') +' ; ';
                                            }
                                            if ($scope.PictoFilterRepresentation.pictoInfo == true) {
                                            	$scope.filterObj.filterCount++;
                                                $scope.PictoFilterRepresentation.defaultFilter=false;
                                                $scope.appliedFilterString = $scope.appliedFilterString + cultureService.localize('picto.filter.label.infoToRead') +' ; ';
                                            }
                                            if ($scope.PictoFilterRepresentation.infoInProgress == true) {
                                            	$scope.filterObj.filterCount++;
                                                $scope.PictoFilterRepresentation.defaultFilter=false;
                                                $scope.appliedFilterString = $scope.appliedFilterString + cultureService.localize('picto.filter.label.inProgress') +' ; ';
                                            }
                                            
                                            if ($scope.PictoFilterRepresentation.dateFilter != null) {
                                            	$scope.filterObj.filterCount++;
                                                $scope.PictoFilterRepresentation.defaultFilter=false;
                                                $scope.appliedFilterString = $scope.appliedFilterString + cultureService.localize('picto.filter.feedack.label.filterByDate') +' ; ';
                                            }
                                            
                                            if ($scope.PictoFilterRepresentation.favPictos == true) {
                                            	$scope.filterObj.filterCount++;
                                                $scope.PictoFilterRepresentation.defaultFilter=false;
                                                $scope.appliedFilterString = $scope.appliedFilterString + cultureService.localize('picto.filter.label.onlyFavPictos') +' ; ';
                                            }
                                            if ($scope.PictoFilterRepresentation.exceptFavPictos == true) {
                                            	$scope.filterObj.filterCount++;
                                                $scope.PictoFilterRepresentation.defaultFilter=false;
                                                $scope.appliedFilterString = $scope.appliedFilterString + cultureService.localize('picto.filter.feedack.label.nonFav') +' ; ';
                                            }
                                            if ($scope.PictoFilterRepresentation.visiblePicto == true) {
                                            	$scope.filterObj.filterCount++;
                                                $scope.PictoFilterRepresentation.defaultFilter=false;
                                                $scope.appliedFilterString = $scope.appliedFilterString + cultureService.localize('picto.filter.label.pictoVisble')+' ; ';
                                            }
                                            if ($scope.PictoFilterRepresentation.invisiblePictos == true) {
                                            	$scope.filterObj.filterCount++;
                                                $scope.PictoFilterRepresentation.defaultFilter=false;
                                                $scope.appliedFilterString = $scope.appliedFilterString + cultureService.localize('picto.filter.label.pictoInVisble')+' ; ';
                                            }
                                            if ($scope.PictoFilterRepresentation.inModificationPictos == true) {
                                            	$scope.filterObj.filterCount++;
                                                $scope.PictoFilterRepresentation.defaultFilter=false;
                                                $scope.appliedFilterString = $scope.appliedFilterString + cultureService.localize('picto.filter.feedack.label.inMod')+' ; ';
                                            }
                                            if ($scope.PictoFilterRepresentation.exceptInModification == true) {
                                            	$scope.filterObj.filterCount++;
                                                $scope.PictoFilterRepresentation.defaultFilter=false;
                                                $scope.appliedFilterString = $scope.appliedFilterString + cultureService.localize('picto.filter.feedack.label.notInMod')+' ; ';
                                            }
                                          
                                            if ($scope.appliedFilterString == null || $scope.appliedFilterString == '') {
                                                $scope.appliedFilterString = cultureService.localize('picto.filter.feedack.label.noFilter');
                                            } else {
                                                //$scope.appliedFilterString = cultureService.localize('picto.filter.feedack.label.resultsBy') + $scope.appliedFilterString;
                                                $scope.appliedFilterString = $scope.appliedFilterString.substring(0, $scope.appliedFilterString.length - 2);
                                            }
                                            if ($scope.appliedFilterString.length > 132) {
                                                $scope.appliedFilterString = $scope.appliedFilterString.substring(0, $scope.appliedFilterString.length - ($scope.appliedFilterString.length-132));
                                                $scope.appliedFilterString = $scope.appliedFilterString +' . . .';
                                            }                                         
                                            /*
                                             * MJ 26-Jul-2016 PSA PRP024006-54
                                             * Start
                                             */
                                            $scope.PictoFilterRepresentation.nullTypeSelected = $scope.isNullType;
                                           
                                            /*
                                             * MJ 26-Jul-2016 PSA PRP024006-54
                                             * End
                                             */
                                            /*
                                             * GK - PSA - PRP024006-50 -
                                             * 15-Jul-16 - Start
                                             */
                                            $scope.addLoader();
                                            $scope.items = [];                               

                                            /*
                                             * GK - PSA - PRP024006-50 -
                                             * 15-Jul-16 - End
                                             */

                    var objectToSend = $scope.PictoFilterRepresentation;

                    var responce = filterService.pictoFilter.pictoFilter(objectToSend, function() {
                        $scope.allPictos = [];
                        $scope.allPictos = responce;
                        $scope.items = [];                      
                        $scope.canLoad = true;
                        
                        $scope.initializePictos();

                        setInterval(function(){   
                            $scope.notCharteLabel= cultureService.localize('picto.label.NotCharteKind');
                            $scope.templateCharte = "<div class='custom-break-sec' data-ng-if='showLoader' style='text-align:center'>"+$scope.notCharteLabel+"</div>" ;
                            $( ".custom-break-sec" ).remove();
                        $( ".custom-break" ).before($scope.templateCharte); 
                        }, 1000);
                   
                       
                        check = 0;     
                        setTimeout(function() {
    						for(var i=0; i<$scope.tempSelected.length; i++){
    	                          var status = angular.element(document.querySelector('#picto' +$scope.tempSelected[i] ));
    	                          $(status).addClass('picto-active');
    							}
    						  /*
                             * GK - PSA - PRP024006-50 - 15-Jul-16 - Start
                             */
                        }, 1000);
                        /*
                         * GK - PSA - PRP024006-50 - 15-Jul-16 - End
                         */
                    }, function(error) {
                        if(error.status = 412){
                            $('#notifierDlg').modal('show');
                            $scope.allPictos = [];
                            $scope.removeLoader();
                            $scope.notificationMessage = cultureService.localize('picto.message.filtertextincorrect.error');
                        }else{
                            $('#notifierDlg').modal('show');

                            $scope.notificationMessage = cultureService.localize('picto.message.filterError');
                        }
                        
                    });

                };

                $(function() {
                    var date = $('#filter-datepicker').datepicker({ dateFormat: 'yy-mm-dd' }).val();
                    $("#filter-datepicker").datepicker();
                });
              
                /************ End : Filter Function ****************/
            
               
                var incrementalPictos = 100;
                $scope.initializePictos = function() {
                    $scope.addLoader();
                    var lastPictosCount = $scope.allPictos.length - $scope.items.length;
                    if(lastPictosCount > incrementalPictos){
                        lastPictosCount = incrementalPictos;
                    }

                    for (var i = 0; i < lastPictosCount; i++) {
                        var limitedData = $scope.allPictos[$scope.items.length]
                        $scope.items.push(limitedData);
                    }
                    $scope.removeLoader();
                    $scope.canLoad = true;

                };
                
                $scope.addNextItems = function() {
                    $scope.addLoader();
                    if ($scope.allPictos.length > $scope.items.length) {
                        var lastPictosCount = $scope.allPictos.length - $scope.items.length;
                        if(lastPictosCount > incrementalPictos){
                            lastPictosCount = incrementalPictos;
                        }

                        for (var i = 0; i < lastPictosCount; i++) {
                            var limitedData = $scope.allPictos[$scope.items.length]
                            $scope.items.push(limitedData);
                        }

                        $scope.canLoad = true;

                    } else {
                        $scope.canLoad = false;
                    }  

                    $scope.removeLoader(); 
                };
                
                

                /************ ADD and Remove Multiple picto in cart  *********/
                $scope.multiplePictoCart = function(flag,event)
                {
                	
                	if (flag===1 || flag === true)
                		{
                		  $scope.multiPictoRemove(event);
                		
                		}
                	else
                		{
                		 if(flag===2)
         		    	{
         		    	  $scope.pictoCentral.cartValue=0;
         		    	}                		
                		    $scope.multiPictoAdded(event);
                		}
                	setTimeout(function(){
                		$scope.tempIntCentralFunction();
                	},3000);
                	
                };
                
                      
                $scope.multiPictoAdded = function(event) {                	
                          	
                    var pictos = 0;
                    $scope.showLoader=false;
                    var pictosCartList = [];
                    var cartPictoIDsFromServer = [];
                    for (var j = 0; j < $scope.cartPictosFromServer.length; j++) {
                    	cartPictoIDsFromServer.push($scope.cartPictosFromServer[j].id);
                    }
                    
                    for (var i = 0; i < $scope.selectedPictoIds.length; i++) {
                        var selectedpictoid = $scope.selectedPictoIds[i];
                        var added = true;
                        if(cartPictoIDsFromServer.indexOf(selectedpictoid) === -1){
                        	 added = false;
                        	 pictosCartList.push(selectedpictoid);
                             $(status).removeClass('picto-active');
                             pictos++;
                        }
//                        for (var j = 0; j < $scope.pictosInCart.length; j++) {
//                            if ($scope.pictosInCart[j] === selectedpictoid) {
//                                added = false;
//                                break;
//                            }
//                        }
//                        if (added) {
//                            $scope.pictosCartList.push(selectedpictoid);
//                            $scope.cartPictosFromServer.push(selectedpictoid);
//                            $(status).removeClass('picto-active');
//                            pictos++;
//                        }

                    }                
                    var objectToSend = {
                            login : $scope.userLogin,
                            pictoId : pictosCartList
                        };

                    $scope.addtocart(pictos, event);           
                    var t = cartService.CartItems.addShop(objectToSend, function() {   
                 		$scope.filterPictos();
                    	$scope.cartUpdate();
                    	$scope.showLoader=true;
                    	for (var i = 0; i < $scope.selectedPictoObj.length; i++) {
                            $scope.selectedPictoObj[i].cartFlag = true; 
                    	}
                        notificationService.notify(cultureService.localize('picto.message.addedCartSuccess')); 
                       }, function() {
                    	   $scope.showLoader=true;
                    	   notificationService.notify(cultureService.localize('picto.message.addedCartError'));                

                    });
                };
                
                $scope.selectVariantPicto = function(variantid) {
                    $scope.selectedVariantPictos.push(variantid);
            }

                $scope.selectDeselectVariantPicto = function(variantid) {

                    this.disabled = false;
                    var selection = true;
                    var incart = true;
                    var variantStatus = angular.element(document.querySelector('#variant' + variantid));                    

                    for (var i = 0; i < $scope.selectedVariantPictos.length; i++) {
                        if ($scope.selectedVariantPictos[i] === variantid) {
                        	selection=false;
							for (var j = 0; j < $scope.pictosInCart.length; j++) {
								if ($scope.pictosInCart[j] === variantid) {
									incart = false;
									selection=true;
									break;
								}
							}
							if (incart) {
								$scope.selectedVariantPictos.splice(i, 1);
								//$(variantStatus).removeClass('picto-active');
							}
							break;

                        }
                    }

                    if (selection) {
                        $scope.selectedVariantPictos.push(variantid);
                        //$(variantStatus).addClass('picto-active');
                    }
                };
                
                
                $scope.selectDeselectVariantPictoIncart = function(variantid, event) {
                    this.disabled = false;
                    var selection = true;
                    var incart = true;
                    var variantStatusIncart = angular.element(document.querySelector('#variantIncart' + variantid));

                    for (var i = 0; i < $scope.selectedVariantPictos.length; i++) {                    
                        if ($scope.selectedVariantPictos[i] === variantid) {
                        	selection=false;
							for (var j = 0; j < $scope.pictosInCart.length; j++) {
								if ($scope.pictosInCart[j] === variantid) {
									incart = false;
									selection=true;
									break;
								}
							}
							if (incart) {
								$scope.selectedVariantPictos.splice(i, 1);
								//$(variantStatusIncart).removeClass('picto-active');
							}
							break;

                        }
                    }

                    if (selection) {
                        $scope.selectedVariantPictos.push(variantid);
                        //$(variantStatusIncart).addClass('picto-active');
                    }
                };

                
                /************** Meha: Select Deselect pictos in Cart Start *****************/
                /* PB - Added - 15-Jul-16 - Start */
                $scope.selectDeselectPictoIncart = function(refnum, pid, pictoCartObj, event) {
                    if ($scope.updateCartFlag === true && !angular.equals($scope.tempPictoInfoInCart, $scope.pictoInfoIncart)) {
                        $('#modifyDlg').modal('show');
                    } else {
                        if (event.ctrlKey) {
                            $scope.pictoClickInCart(refnum, pid, pictoCartObj,event);
                        } else {
                            $scope.selectedPictosIncart = [];
                            $scope.selectedPicRefNumsIncart = [];
                            $scope.selectedCartPictoObj = [];
                            $('.custom-picto-col-incart').removeClass('picto-active');

                            if (!$scope.serviceInProgress) {
                                $scope.serviceInProgress = true
                                $scope.pictoClickInCart(refnum, pid, pictoCartObj,event);
                            }

                        }
                    }
                };

                $scope.pictoClickInCart = function(refnum, pid, pictoCartObj,event) {

                    $scope.addNewpictoview = false;

                    if ($scope.selectedPictosIncart.length > 1 && $scope.hasCheckedIncart == true) {
                        $scope.hasCheckedIncart = false;
                    }
                    $scope.hasCheckedIncart = false;
                    this.disabledIncart = true;
                    var selection = true;
                    /* PB - Added - 3-Aug-16 - Start */
                    var status = angular.element(document.querySelector('#pictoCart' + pid));
                    /* PB - Added - 3-Aug-16 - End */
                    for (var i = 0; i < $scope.selectedPictosIncart.length; i++) {

                        if ($scope.selectedPictosIncart[i] === pid) {

                            selection = false;
                            $(status).removeClass('picto-active');
                            $scope.selectedPictosIncart.splice(i, 1);
                            $scope.selectedPicRefNumsIncart.splice(i, 1);
                            $scope.selectedCartPictoObj.splice(i, 1);                    
                            if($scope.cartPictosFromServer.length === $scope.selectedPictosIncart.length)
                            {
                            	$scope.hasCheckedIncart = true;
                            } else
                            {
                            	$scope.hasCheckedIncart = false;
                            }
                            break;
                        }
                    }
                    if (selection) {
                        $scope.selectedPicRefNumsIncart.push(refnum);
                        $scope.selectedPictosIncart.push(pid);
                        $scope.selectedCartPictoObj.push(pictoCartObj); 
                        $(status).addClass('picto-active');
                        if ($scope.selectedPictosIncart.length > 0) {
                            $scope.getInformationForCart($scope.selectedPicRefNumsIncart[0]);
                        }
                        if($scope.cartPictosFromServer.length === $scope.selectedPictosIncart.length)
                        {
                        	$scope.hasCheckedIncart = true;
                        } else
                        {
                        	$scope.hasCheckedIncart = false;
                        }
                    }
                    if ($scope.selectedCartPictoObj.length>1)
                    {
                    	$scope.setFavCartValueShop();   
                    }
                    $scope.infoAreaStatusIncart();
                };

                /* PB - Added - 18-Jul-16 - Start */
                $scope.infoAreaStatusIncart = function() {
                    if ($scope.selectedPictosIncart.length > 1) {
                        $scope.PictoTitleStatusIncart = cultureService.localize('picto.js.label.severalPictos');
                        $scope.SeveralPictoStatusIncart = cultureService.localize('picto.js.label.SeveralPictoStatus');
                        $scope.cartInformationViewVisibility = false;
                        $scope.pictoIncartInfoAreaVisiblity = false;
                        $scope.pictoSelectedForInfoIncart = false;
                        $scope.cartInformationViewVisibilitySeveral = true;
                        $scope.pictoSelectedIncart = true;

					} else if ($scope.selectedPictosIncart.length === 0) {
						$scope.cartInformationViewVisibility = true;
						$scope.PictoTitleStatusIncart = "Consult";
						$scope.pictoIncartInfoAreaVisiblity = false;
						$scope.pictoSelectedForInfoIncart = false;
						$scope.cartInformationViewVisibility = false;
						$scope.cartInformationViewVisibilitySeveral = false;
					} else {
						$scope.PictoTitleStatusIncart = "Consult";
						$scope.SeveralPictoStatusIncart = "";
						$scope.cartInformationViewVisibility = true;
						$scope.cartInformationViewVisibilitySeveral = false;
						$scope.pictoIncartInfoAreaVisiblity = true;
						$scope.pictoSelectedForInfoIncart = true;
						$scope.pictoSelectedIncart = true;
					}
					
				};
				/* PB - Added - 18-Jul-16 - End */				
				/************** Meha: Select Deselect pictos in Cart End *****************/				
     
                $scope.selectDeselectPicto = function(pid, pictoId, pictoObj, event) {
                    if ($scope.createNewPictoFamily == true) {
                        $('#modifyDlg').modal('show');
                        $scope.addNewpictoview = false;
                    } else if ($scope.updateFlag === true && !angular.equals($scope.tempPictoInfo, $scope.pictoInfo)) {
                        $('#modifyDlg').modal('show');

                    } else {
                        if (event.ctrlKey) {
                            $scope.pictoClick(pid, pictoId, pictoObj, event);
                        } else {
                            $scope.selectedPictoIds = [];
                            $scope.selectedPictos = [];
                            $scope.selectedPictoObj = [];
                            $scope.tempSelected =[];
                            $scope.selectedVariantPictos =[];
                            $('.custom-picto-col-central').removeClass('picto-active');

                            if (!$scope.serviceInProgress) {
                                $scope.serviceInProgress = true
                                $scope.pictoClick(pid, pictoId, pictoObj, event);
                            }
                        }

                    }

                };

                $scope.pictoClick = function(pid, pictoId, pictoObj, event) {
                    $scope.addNewpictoview = false;

                    if ($scope.selectedPictoIds.length > 1 && $scope.hasChecked == true) {
                        $scope.hasChecked = false;
                    }
                    $scope.hasChecked = false;
                    $scope.pictoActive=true;
                    this.disabled = false;
                    var selection = true;
                    var incart = true;
                    var status = angular.element(document.querySelector('#picto' + pid));
                    /*
                     * PB - PSA - PRP024006-57 - 15-Jul-16 - Start
                     */
                    for (var i = 0; i < $scope.selectedPictos.length; i++) {

                        if ($scope.selectedPictos[i] === pid) {

							selection = false;
							for (var j = 0; j < $scope.pictosInCart.length; j++) {
								if ($scope.pictosInCart[j] === pid) {
									incart = false;
									break;
								}
							}
							if (incart) {
								$scope.selectedPictos.splice(i, 1);
								$scope.selectedPictoIds.splice(i, 1);
								$scope.selectedPictoObj.splice(i, 1);
								$scope.tempSelected.splice(i,1);
								$(status).removeClass('picto-active');
								if ($scope.selectedPictos.length > 0) {
									$scope.getInformation($scope.selectedPictos[0]);
								}
		                        if ($scope.selectedPictoObj.length>1)
		                        {
		                        	$scope.setFavCartValue();   
		                        }
		                  
		                        if($scope.allPictos.length === $scope.selectedPictoIds.length)
		                        {
		                        	$scope.hasChecked = true;
		                        } else
		                        {
		                        	$scope.hasChecked = false;
		                        }
								
							}
							break;

                        }
                    }
                    /*
                     * PB - PSA - PRP024006-57 - 15-Jul-16 - Start
                     */
                    if (selection) {
                        $scope.selectedPictoIds.push(pictoId);
                        $scope.selectedPictos.push(pid);
                        $scope.selectedPictoObj.push(pictoObj);
                        $scope.tempSelected.push(pid);
                        if ($scope.selectedPictoObj.length>1)
                        {
                        	$scope.setFavCartValue();   
                        }
                        $(status).addClass('picto-active');
                        if ($scope.selectedPictos.length > 0) {
                            $scope.getInformation($scope.selectedPictos[0]);
                        }
                        if($scope.allPictos.length === $scope.selectedPictoIds.length)
                        {
                        	$scope.hasChecked = true;
                        } else
                        {
                        	$scope.hasChecked = false;
                        }
                    }
                    $scope.infoAreaStatus();
                   
                };
                
                
				  $scope.tempIntCentralFunction = function() 
					  {
						for(var i=0; i<$scope.tempSelected.length; i++){
                          var status = angular.element(document.querySelector('#picto' +$scope.tempSelected[i] ));
                          $(status).addClass('picto-active');
						}
					  }

				  $scope.tempIntCartFunction = function() 
				  {
					for(var i=0; i<$scope.selectedPictosIncart.length; i++){
                      var status = angular.element(document.querySelector('#pictoCart' +$scope.selectedPictosIncart[i] ));
                      $(status).addClass('picto-active');
					}
				  }
				  
                $scope.infoAreaStatus = function() {
                    if ($scope.selectedPictos.length > 1) {
                        $scope.PictoTitleStatus = cultureService.localize('picto.js.label.severalPictos');
                        $scope.SeveralPictoStatus = cultureService.localize('picto.js.label.SeveralPictoStatus');
                        $scope.informationViewVisibility = false;
                        $scope.informationViewOptionsVisibility = false;
                        $scope.createNewPictoFamily = false;
                        $scope.updateFlag = false;
                        $scope.ShowMultipleSelected = true;
                        $scope.HideMultipleSelected = true;
                        $scope.addNewpictoview = false;

					} else if ($scope.selectedPictos.length === 0) {
						$scope.informationViewVisibility = false;
						$scope.PictoTitleStatus = "Consult";
						$scope.informationViewOptionsVisibility = false;
						$scope.createNewPictoFamily = false;
						$scope.updateFlag = false;
						$scope.SeveralPictoStatus = "";
						$scope.frontage = null;
						$scope.ShowMultipleSelected = false;
						$scope.remainVariant = [];
						$scope.addNewpictoview = false;
					} else {
						$scope.PictoTitleStatus = "Consult";
						$scope.SeveralPictoStatus = "";
						$scope.informationViewVisibility = true;
						$scope.informationViewOptionsVisibility = true;
						$scope.createNewPictoFamily = false;
						$scope.updateFlag = false;
						$scope.ShowMultipleSelected = false;
						if ($scope.selectedPictos.length != 0) {
							$scope.HideMultipleSelected = false;
						}
						$scope.addNewpictoview = false;
					}
					
					setTimeout(function(){   
						$('.frontageClass').addClass('picto-active');						
					}, 2000);
				};
			

				/**************** Meha : Start Get info area content *****************/
                $scope.getInformationForCart = function(refnum) {
                    var t = pictoFamilyService.getPictoInformation(refnum, function(responce) {
                        $scope.updateCartFlag = true;
                        $scope.pictoInfoIncart = t;
                        $scope.tempPictoInfoInCart = angular.copy($scope.pictoInfoIncart);
                        $scope.showFlag = true;
                        $scope.imageColor=false;                       
                        $scope.variantCount = 0;
                        
                        for (var i = 0; i < $scope.pictoInfoIncart.pictos.length; i++) {
                            if ($scope.pictoInfoIncart.pictos[i].colorFlag === 'red' || $scope.pictoInfoIncart.pictos[i].colorFlag === 'yellow' ) {
                                $scope.imageColor=true;
                            }
                            if($scope.pictoInfoIncart.pictos[i].isVisible && !$scope.pictoInfoIncart.pictos[i].isFrontagePicto)
                        	{
                        	  $scope.variantCount++;
                        	}                           
                        }                        
                        if ($scope.pictoInfoIncart.typeID != undefined && $scope.pictoInfoIncart.typeID.typeLabel == "Iconothèque") {
                            $scope.showFlag = false;
                        }

                        var supCatId = $scope.pictoInfoIncart.categoryID.superCategory.superCategoryId;
                        $scope.listAllCategory = [];
                        setTimeout(function() {
                            for (var i = 0; i < $scope.listAllCategoryCompleteReference.length; i++) {
                                if ($scope.listAllCategoryCompleteReference[i].superCategory.superCategoryId == supCatId) {
                                    $scope.listAllCategory.push($scope.listAllCategoryCompleteReference[i]);
                                }
                            }
                            $scope.serviceInProgress = false;
                            var pictoStatus = angular.element(document.querySelector('#pictoCart' + refnum));
                             $(pictoStatus).addClass('picto-active');
                        }, 200);

                    }, function() {
                        $scope.pictoInfoIncart = [];
                    });
                };

            	/**************** Meha : End Get info area content *****************/
                  
                
            	/******* Get the picto family information by reference number **********/	
                
                  $scope.getInformation = function(refnum) {
                    var t = pictoFamilyService.getPictoInformation(refnum, function(responce) {
                        $scope.deleteCaseFlag = false;
                        $scope.createCaseFlag = false;                  
                        $scope.pictoInfo = t;
                        $scope.tempPictoInfo = angular.copy($scope.pictoInfo);
                        $scope.updateFlag = true;
                        $scope.showFlag = true;
                        $scope.updateCartFlag = false;
                        $scope.imageColor=false;
                        $scope.variantCount = 0;                        
                        for (var i = 0; i < $scope.pictoInfo.pictos.length; i++) {
                            if ($scope.pictoInfo.pictos[i].colorFlag === 'red' || $scope.pictoInfo.pictos[i].colorFlag === 'yellow' ) {
                                $scope.imageColor=true;
                            }
                            if($scope.pictoInfo.pictos[i].isVisible && !$scope.pictoInfo.pictos[i].isFrontagePicto)
                            	{
                            	  $scope.variantCount++;
                            	}
                           
                        }                
                        if ($scope.pictoInfo.typeID != undefined && $scope.pictoInfo.typeID.typeLabel == "Iconothèque") {
                            $scope.showFlag = false;
                        }

                        var supCatId = $scope.pictoInfo.categoryID.superCategory.superCategoryId;
                        $scope.listAllCategory = [];
                        setTimeout(function() {
                            for (var i = 0; i < $scope.listAllCategoryCompleteReference.length; i++) {
                                if ($scope.listAllCategoryCompleteReference[i].superCategory.superCategoryId == supCatId) {
                                    $scope.listAllCategory.push($scope.listAllCategoryCompleteReference[i]);
                                }
                            }
                            $scope.serviceInProgress = false;
                        }, 200);

                    }, function() {
                        $scope.pictoInfo = {};
                        $scope.serviceInProgress = false;
                    });
                };

                /************** Start: Make the picto as frontage ***************************/
                $scope.makeFrontage = function(variant) {
                    for (var i = 0; i < $scope.pictoInfo.pictos.length; i++) {
                        if ($scope.pictoInfo.pictos[i].isFrontagePicto === true) {
                            $scope.pictoInfo.pictos[i].isFrontagePicto = false;
                        }
                        if ($scope.pictoInfo.pictos[i].id === variant.id) {
                            $scope.pictoInfo.pictos[i].isFrontagePicto = true;
                        }
                    }

                };

                $scope.makeFrontageCart = function(variant) {
                    for (var i = 0; i < $scope.pictoInfoIncart.pictos.length; i++) {
                        if ($scope.pictoInfoIncart.pictos[i].isFrontagePicto === true) {
                            $scope.pictoInfoIncart.pictos[i].isFrontagePicto = false;
                        }
                        if ($scope.pictoInfoIncart.pictos[i].id === variant.id) {
                            $scope.pictoInfoIncart.pictos[i].isFrontagePicto = true;
                        }
                    }

                };

                /********************* End: Make the picto as frontage *************************/

                /******************** Start: Add/Remove the pictos to favorite ************************/            
                $scope.addToFavoritesInfoArea = function(id, flag)
                {
                    $scope.showLoader=false;
                	$scope.reloadFlag = true;
                	$scope.addToFavorites(id, flag);
                }
                $scope.addToFavoritesMultiple = function(id, flag)
                {
                	$scope.showLoader=false;
                	$scope.reloadFlag = true;
                	$scope.addToFavorites(id, flag);
                }
                
                $scope.addToFavorites = function(id, flag)
                {
                	if (flag === 1 || flag === true)
                		{
                		     $scope.removeFavorite(id);
                		
                		}
                	else
                		{
                		    if(flag===2)
                		    	{
                		    	  $scope.pictoCentral.favValue=0;
                		    	}
                		    $scope.addToFavoritesList(id);
                		}
                	setTimeout(function(){
                		$scope.tempIntCentralFunction();
                	},2000);
                };
                
                $scope.addToFavoritesList = function(pid, event) {              

                    var selectedRows = $scope.selectedPictos;

                    var favoritesList = [];
                    for (var index = 0; index < $scope.selectedPictos.length; index++) {
                        var pictoFamilyObjectVar = selectedRows[index];
                        favoritesList.push(pictoFamilyObjectVar);
                    }
                    var objectToSend = {
                        login : $scope.userLogin,
                        pictoFamily : favoritesList
                    };

                    favouritesService.FavouritesItems.saveFavorite(objectToSend, function(responce) {
                    	
                    	 if ($scope.reloadFlag)
                    	 {
                    		 $scope.filterPictos();                
                    	 
                    	 }

                		 for(var i=0; i<$scope.selectedPictos.length; i++)
                		 {
                	   	    $scope.getInformation($scope.selectedPictos[i]);                           
                		 }
                		 for (var index = 0; index < $scope.selectedPictoObj.length; index++) {
                			 $scope.selectedPictoObj[index].familyID.favoriteFlag = true;
                         }
                		 $scope.showLoader=true;
                        notificationService.notify(cultureService.localize('picto.message.favaroiteSuccess'));
                        $scope.reloadFlag = false;
                    }, function() {
                    	 $scope.showLoader=true;
                    	 $scope.reloadFlag=false;
                        notificationService.notify(cultureService.localize('picto.message.favaroiteError'));

                    });
                };
                
                              
                $scope.removeFavorite = function() {                	

                    var selectedRows = $scope.selectedPictos;
                    var favoritesList = [];

                    for (var index = $scope.selectedPictos.length - 1; index >= 0; index--) {
                        var pictoFamilyObject = selectedRows[index];
                        favoritesList.push(pictoFamilyObject);                       
                    }
                    var objectToSend = {
                        login : $scope.userLogin,
                        pictoFamily : favoritesList
                    };

                    favouritesService.FavouritesItems.removeFavorite(objectToSend, function(success) {                    	
                   	 if ($scope.reloadFlag || $scope.PictoFilterRepresentation.favPictos)
                   		 {
                    	  $scope.filterPictos();
                   		 }
                    	 $scope.getInformation($scope.selectedPictos[0]); 
                    	 if($scope.PictoFilterRepresentation.favPictos && !$scope.PictoFilterRepresentation.exceptFavPictos)
                         {
                        	$scope.informationViewVisibility = false;
                        	$scope.informationViewOptionsVisibility = false;
                         }
                    	 $scope.showLoader=true;
                         notificationService.notify(cultureService.localize('picto.message.favRemoveSuccess'));
                         $scope.reloadFlag = false;
                    }, function(error) {
                    	 $scope.showLoader=true;
                    	 $scope.reloadFlag=false;
                        notificationService.notify(cultureService.localize('picto.message.favRemoveError'));

                    });

                };
                
                /***************** End: Add/Remove the pictos to favorite *******************************/
                
                
                
                $scope.addToCartInfoArea = function(id,cartFlag,event)
                {
                	$scope.reloadFlag = true;
                	$scope.addPictoToCartList(id,cartFlag,event);
                }              
                
                $scope.addPictoToCartList = function(id,cartFlag,event)
                {
                	if(cartFlag)
                		{
                			$scope.removeCartCentral(id,event);
                		  
                		}
                	else
                	{
                		 $scope.addPictoToCart(id,event);
                	}
                }

        
                /************** Check before adding to cart *******************/
                $scope.addPictoToCart = function(pid, event) {
                    var pictos = 0;
                    var selectedpictoid = [];
                    selectedpictoid.push(pid);

                    $scope.addtocart(1, event);                 
                    
                    var objectToSend = {
                            login : $scope.userLogin,
                            pictoId : selectedpictoid
                        };

                    var t = cartService.CartItems.addShop(objectToSend, function(){                
	                   	 if ($scope.reloadFlag)
	               		 {
	                   		 $scope.filterPictos();
	               		 }
                    	$scope.getInformation($scope.selectedPictos[0]);
                    	$scope.cartUpdate();
                    	 notificationService.notify(cultureService.localize('picto.message.addedCartSuccess'));     
                    	 $scope.reloadFlag=false;
                    }, function() {
                    	 $scope.reloadFlag=false;;
                    	 notificationService.notify(cultureService.localize('picto.message.addedCartError'));
                    });

                };
                
                $scope.removeCartCentral = function(pid, event) {
              
                    var selectedRows = pid;
                    var shopCartList = [];                    
                    shopCartList.push(selectedRows);

                         var objectToSend = {
                        login : $scope.userLogin,
                        pictoId : shopCartList
                         };

                    cartService.CartItems.removeShop(objectToSend, function(success) {                   
                   	   if ($scope.reloadFlag)
               		    {
                	       $scope.filterPictos();
               		     }
                    	$scope.getInformation($scope.selectedPictos[0]);
                    	$scope.cartUpdate();
                    	$scope.pictoIncartInfoAreaVisiblity = false;
  						$scope.pictoSelectedForInfoIncart = false;
  						$scope.cartInformationViewVisibility = false;
                        notificationService.notify(cultureService.localize('picto.message.removeCartSuccess'));
                        $scope.reloadFlag=false;
                    }, function(error) {
                    	 $scope.reloadFlag=false;
                        notificationService.notify(cultureService.localize('picto.message.removeCartError'));

                    });
                };
                
                $scope.multiPictoRemove = function(event) {   
                	
                    $scope.showLoader=false;        	
                    var selectedRows = $scope.selectedPictoIds;
                    var shopCartList = [];

                    for (var index = $scope.selectedPictos.length - 1; index >= 0; index--) {
                        var pictoCartObject = selectedRows[index];
                        shopCartList.push(pictoCartObject);
                      
                        }

                         var objectToSend = {
                        login : $scope.userLogin,
                        pictoId : shopCartList
                    };

                    cartService.CartItems.removeShop(objectToSend, function(success) {                   
                    	$scope.filterPictos();
                    	$scope.getInformation($scope.selectedPictos[0]);
                    	$scope.cartUpdate();
                    	$scope.showLoader=true;
                        notificationService.notify(cultureService.localize('picto.message.removeCartSuccess'));
                    }, function(error) {
                    	$scope.showLoader=true;
                        notificationService.notify(cultureService.localize('picto.message.removeCartError'));                   

                    });
                };
                

                /*************** Add to cart Animation **********************/
               $scope.addtocart = function(pictos, event) {
                    if (pictos != 0) {
                        var count = pictos;
                        var cartElem = angular.element(document.getElementsByClassName("shopping-cart"));
                        console.log(cartElem);
                        var offsetTopCart = cartElem.prop('offsetTop');
                        var offsetLeftCart = cartElem.prop('offsetLeft');
                        var widthCart = cartElem.prop('offsetWidth');
                        var heightCart = cartElem.prop('offsetHeight');
                        var imgElem = angular.element(event.target.parentNode);
                        var parentElem = angular.element(event.target.parentNode);
                        var offsetLeft = imgElem.prop("offsetLeft");
                        var offsetTop = imgElem.prop("offsetTop");

                        var imgClone = $('<span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-1x" style="color:#774477; padding:0px"></i><i class="fa fa-stack-1x" style="color:#FFF;padding:0px;font-size:0.7em;">' + count + '</i></span>');
                        imgClone.css({
                            'height' : '150px',
                            'position' : 'fixed',
                            'top' : offsetTop + 'px',
                            'left' : offsetLeft + 'px',
                            'opacity' : 1
                        });
                        imgClone.addClass('itemaddedanimate');
                        parentElem.append(imgClone);
                        setTimeout(function() {
                            imgClone.css({
                                'height' : '95px',
                                'top' : (offsetTopCart + heightCart - 70 / 2) + 'px',
                                'left' : (offsetLeftCart + widthCart / 2) + 'px',
                                'opacity' : 1
                            });
                        }, 500);
                        setTimeout(function() {
                            imgClone.css({
                                'height' : 0,
                                'opacity' : 0.5

                            });
                            cartElem.addClass('shakeit');
                        }, 1000);
                        setTimeout(function() {
                            cartElem.removeClass('shakeit');
                            imgClone.remove();
                        }, 1500);

                    } else {

                    }
                    
                };

                
                $scope.selectedPictoDetails = {
                    name : "0000_NomPicto_KO_25-10-15"
                };

                /****************   User Roles: Admin/Contrib/User  **************/
                $scope.validateUser = function() {

                    if (authorizationService.hasRole('seed-w20', 'PICTOARole') || authorizationService.hasRole('seed-w20', 'PICTODRole') || authorizationService.hasRole('seed-w20', 'PICTOERole') || authorizationService.hasRole('seed-w20', 'ADMINRole')) {
                        $scope.roleAdmin();
                    }

                    else if (authorizationService.hasRole('seed-w20', 'PICTOCRole') || authorizationService.hasRole('seed-w20', 'CONTRIBUTEURRole')) {
                        $scope.roleContrib();
                    }

                    else if (authorizationService.hasRole('seed-w20', 'PICTOGRole') || authorizationService.hasRole('seed-w20', 'ENDUSERRole')) {
                        $scope.roleUser();
                    }
                    $scope.sortByParamName = 'Ref';
                    $scope.PictoFilterRepresentation.sortByParameter = 'Réf';
                     $scope.isCharteSort = false;
                    $scope.filterPictos();
                    $scope.showLoader = false;


                };

                 /* For Admin role */
                $scope.roleAdmin = function() {
                    $scope.admin = true;
                    $scope.contrib = false;
                    $scope.user = false;

                    $scope.contribAdmin = function() {
                        return true;
                    };
                };

                /* For Contrib role */
                $scope.roleContrib = function() {
                    $scope.contrib = true;
                    $scope.admin = false;
                    $scope.user = false;
                    $scope.contribUser = function() {
                        return true;
                    };
                    $scope.contribAdmin = function() {
                        return true;
                    };
                };

                /* For user role */
                $scope.roleUser = function() {
                    $scope.user = true;
                    $scope.admin = false;
                    $scope.contrib = false;
                    $scope.contribUser = function() {
                        return true;
                    };
                };

                /*********** Remove the Variants **************/
                $scope.removeVariant = function(variant, index) {
                    pictoService.doDeletePictoVariantValidation(variant.id, function(success) {
                        if (success.length == 0) {
                            if ($scope.updateCartFlag) {
                                $scope.pictoInfoIncart.deletedPictosList.push(variant);
                                $scope.pictoInfoIncart.pictos.splice(index, 1);
                                return;
                            }

                            $scope.pictoInfo.deletedPictosList.push(variant);
                            $scope.pictoInfo.pictos.splice(index, 1);
                        } else {
                            notificationService.notify(cultureService.localize('picto.message.deletePreCondition') + success);
                        }

                    }, function(error) {

                        notificationService.notify(cultureService.localize('picto.message.deletePreCondition'));
                    });
                };

                /************ Remove the Specific Drawing ****************/

                $scope.removeSpecificDraw = function(specificDraw, index) {
                    if ($scope.updateCartFlag) {
                        $scope.pictoInfoIncart.deletedSpecDrawList.push(specificDraw);
                        $scope.pictoInfoIncart.specificDrawings.splice(index, 1);
                    } else {
                        $scope.pictoInfo.deletedSpecDrawList.push(specificDraw);
                        $scope.pictoInfo.specificDrawings.splice(index, 1);
                    }
                };

                $scope.removeRules = function(index) {
                    if ($scope.updateCartFlag) {
                        $scope.pictoInfoIncart.rules.splice(index, 1);
                    } else {
                        $scope.pictoInfo.rules.splice(index, 1);
                    }

                };

                $scope.createPicto = function() {

                };

                /* For sidebar toggle 24-02-2016 */
                $scope.filterClick = function() {
                    $("#wrapper").toggleClass("toggled");

                };

                $scope.activatePictoOptionsBar = function(picto) {
                    if($scope.selectedPictoIds.indexOf(picto.id) != -1){
                        return true;
                    }    
                    return false;
                }
                
              
                /************* Meha: validate selected AI working file download START *************/
                $scope.validateAi = function(isFromCartCenterAreaOnly) {
                    var pictoIds;
                    var pictoObjectList ;
                    if(isFromCartCenterAreaOnly){
                        pictoIds = $scope.selectedPictosIncart;
                        pictoObjectList = $scope.cartPictosFromServer;
                    }else{
                        pictoIds =  $scope.selectedPictoIds;
                        pictoObjectList =  $scope.allPictos;
                    }
                   
                    $scope.selectedPictosForAi = [];
                    $scope.selectedPictosForLocal = [];
                    for (var i = 0; i < pictoIds.length; i++) {
                        if ($('#pid' + pictoIds[i]).is(':checked')) {
                            $scope.selectedPictosForAi.push(pictoIds[i]);
                        } else if ($('#opnLcl' + pictoIds[i]).is(':checked')) {
                            $scope.selectedPictosForLocal.push(pictoIds[i]);
                        }

                    }
                    /* MJ - 21-Jul-16 - Start */
                    if ($scope.selectedPictosForAi.length > 0 || $scope.selectedPictosForLocal.length > 0) {
                        var tempList = [];
                        var tempListOfPictoIds = [];
                        for (var i = 0; i < $scope.selectedPictosForAi.length; i++) {

                            var temp = {
                                'keepLocalVersion' : 'false',
                                'downloadDBVersion' : 'true',

                                'pictoId' : $scope.selectedPictosForAi[i],
                            };
                            tempList.push(temp);
                            tempListOfPictoIds.push($scope.selectedPictosForAi[i]);
                        }
                        for (var i = 0; i < $scope.selectedPictosForLocal.length; i++) {

                            var temp = {
                                'openLocalVrsn' : 'true',
                                'downloadDBVersion' : 'false',

                                'pictoId' : $scope.selectedPictosForLocal[i],
                            };
                            tempList.push(temp);
                            tempListOfPictoIds.push($scope.selectedPictosForLocal[i]);
                        }
                        var objectToSend = {
                            'downloadAIWorkRepresentation' : tempList,
                        };
                        var t = validateAIWorkService.validateAIWork.validateAIWork(objectToSend, function() {
                            $('#notifierDlg').modal('show');                                  
                            $scope.notificationMessage = cultureService.localize('picto.message.downloadAI');
                            
                            //To update right area for Main Page and Cart Page
                            if(tempListOfPictoIds.length == 1){
                                if(isFromCartCenterAreaOnly){
                                    $scope.getInformationForCart($scope.selectedPicRefNumsIncart[0]);
                                }else{
                                    $scope.getInformation($scope.selectedPictos[0]);
                                }
                            }
                            
                            //To update center Area for Main Page and Cart Page
                            for (var i = 0; i < pictoObjectList.length; i++) {
                                var tempPicto = pictoObjectList[i];
                                if(tempListOfPictoIds.indexOf(tempPicto.id) != -1){
                                    tempPicto.colorFlag = 'red';
                                }
                                
                            }
                            
                            //To update center area if download done in CART AREA
                            if(isFromCartCenterAreaOnly){
                                for (var i = 0; i < $scope.allPictos.length; i++) {
                                    var tempPicto = $scope.allPictos[i];
                                    if(tempListOfPictoIds.indexOf(tempPicto.id) != -1){
                                        tempPicto.colorFlag = 'red';
                                    }
                                    
                                }
                            }
                            

                        }, function() {
                            $('#notifierDlg').modal('show');
                            $scope.notificationMessage = cultureService.localize('picto.message.downloadAIError');

                        });
                    }
                    /* MJ - 21-Jul-16 - Ends */

                };
                
                /************* Meha: validate selected AI working file download END *************/
                
                
                $scope.validateDataBeforeSave = function() {
                    var missingAttributes = "";
                    if ($scope.createCaseFlag) {
                        var selectedFile = document.getElementById('aiImagUpload').files[0];
                        if (selectedFile == null) {
                            notificationService.notify(cultureService.localize('picto.message.uploadFile'));
                            return false;
                        } else {
                            var ext = selectedFile.name.split('.').pop();
                            if (ext != "ai") {
                                notificationService.notify(cultureService.localize('picto.message.uploadAIFile'));
                                return false;
                            }
                        }

                        if ($scope.pictoInfo.pictos != undefined) {
                            if ($scope.pictoInfo.pictos.variantType == undefined) {
                                missingAttributes = missingAttributes + ", Variant";
                            }
                            if ($scope.pictoInfo.pictos.isVisible == undefined) {
                                missingAttributes = missingAttributes + ", Visibility";
                            }
                        } else {
                            missingAttributes = missingAttributes + ", Variant, Visibility";
                        }
                    }

                    if ($scope.pictoInfo.name == undefined || $scope.pictoInfo.name == "") {
                        missingAttributes = missingAttributes + " Name";
                    }

                    if ($scope.pictoInfo.informationTypeLabel == undefined || $scope.pictoInfo.informationTypeLabel == "") {
                        missingAttributes = missingAttributes + ", InformationType";
                    }

                    if ($scope.pictoInfo.validationLevel == undefined) {
                        missingAttributes = missingAttributes + ", ValidationLevel";
                    }

                    if ($scope.pictoInfo.typeID == undefined) {
                        missingAttributes = missingAttributes + ", Type";
                    }
                    
                    if ($scope.pictoInfo.categoryID != undefined) {
                        if ($scope.pictoInfo.categoryID.superCategory == undefined) {
                            missingAttributes = missingAttributes + ", SuperCategory";
                        }
                        if ($scope.pictoInfo.categoryID.name == undefined || $scope.pictoInfo.categoryID.name == "") {
                            missingAttributes = missingAttributes + ", Category";
                        }
                    }
                    else
                     {
                        missingAttributes = missingAttributes + ", Category";
                     }

                    if ($scope.pictoInfo.keywordFR == undefined || $scope.pictoInfo.keywordFR == "") {
                        missingAttributes = missingAttributes + ", KeywordFR";
                    }

                    if ($scope.pictoInfo.keywordEN == undefined || $scope.pictoInfo.keywordEN == "") {
                        missingAttributes = missingAttributes + ", KeywordEN";
                    }
                    if (missingAttributes == "") {
                        return true;

                    } else {
                        notificationService.notify(cultureService.localize('picto.message.mandatoryCheck') + missingAttributes);
                        return false;
                    }
                };

                $scope.validateDataBeforeSaveCart = function() {
                    var missingAttributes = "";

                    if ($scope.pictoInfoIncart.name == undefined || $scope.pictoInfoIncart.name == "") {
                        missingAttributes = missingAttributes + " Name";
                    }

                    if ($scope.pictoInfoIncart.informationTypeLabel == undefined) {
                        missingAttributes = missingAttributes + ", InformationType";
                    }

                    if ($scope.pictoInfoIncart.validationLevel == undefined) {
                        missingAttributes = missingAttributes + ", ValidationLevel";
                    }

                    if ($scope.pictoInfoIncart.typeID == undefined) {
                        missingAttributes = missingAttributes + ", Type";
                    }

                    if ($scope.pictoInfoIncart.categoryID.name == undefined || $scope.pictoInfoIncart.categoryID.name == "") {
                        missingAttributes = missingAttributes + ", Category";
                    }

                    if ($scope.pictoInfoIncart.categoryID != undefined) {
                        if ($scope.pictoInfoIncart.categoryID.superCategory == undefined) {
                            missingAttributes = missingAttributes + ", SuperCategory";
                        }
                    }

                    if ($scope.pictoInfoIncart.keywordFR == undefined || $scope.pictoInfoIncart.keywordFR == "") {
                        missingAttributes = missingAttributes + ", KeywordFR";
                    }

                    if ($scope.pictoInfoIncart.keywordEN == undefined || $scope.pictoInfoIncart.keywordEN == "") {
                        missingAttributes = missingAttributes + ", KeywordEN";
                    }
                    if (missingAttributes == "") {
                        return true;

                    } else {
                        notificationService.notify(cultureService.localize('picto.message.mandatoryCheck') + missingAttributes);
                        return false;
                    }
                };

                /********** Validation before Delete the Picto family ***********/

                $scope.deletePictoFamilyValidation = function() {
                    $scope.deleteCaseFlag = true;

                    pictoFamilyService.deletePictoFamilyValidation($scope.pictoInfo.referenceNum, function(success) {
                        if (success.length == 0) {
                            $('#confirmDlg').modal('show');
                        } else {
                            notificationService.notify(cultureService.localize('picto.message.deletePreCondition') + success);
                        }
                    }, function(error) {
                        notificationService.notify(cultureService.localize('picto.message.deletePreCondition'));
                    });
                };

                /**************** On Save Called From pictoRightBar.html **************************/
                $scope.validateInfoArea = function() {

                    $scope.deleteCaseFlag = false;
                    var obj = false;
                    if ($scope.updateCartFlag) {
                        obj = $scope.validateDataBeforeSaveCart();
                    } else {
                        obj = $scope.validateDataBeforeSave();
                    }

                    if (obj) {
                        $('#confirmDlg').modal('show');
                    }

                };

                /*****************Start: Process after Confirmation Actions ************************/

                $scope.onActionConfirmation = function() {
                    if ($scope.deleteCaseFlag && $scope.createCaseFlag) {
                        $scope.addNewpictoview = false;
                        $scope.informationViewOptionsVisibility = false;
                        $scope.informationViewVisibility = false;
                        $scope.createNewPictoFamily = false;
                        return;
                    }

                    if ($scope.updateCartFlag) {
                        $scope.updatePicto($scope.pictoInfoIncart);
                        return;
                    }

                    if ($scope.deleteCaseFlag) {
                        $scope.deletePicto();
                    } else if ($scope.createCaseFlag) {
                        pictoFamilyService.createPictoFamily($scope.pictoInfo, function(responce) {
                            /*
                             * SN - GL - 112 - 17-Jul-16 - Start
                             */
                            if ($scope.pictoInfo.pictos.version == undefined || $scope.pictoInfo.pictos.version == "") {
                                $scope.pictoInfo.pictos.version = null;
                            }
                            if (responce.refCharte == undefined || responce.refCharte == "") {
                            	responce.refCharte = null;
                            }
                            var selectedFile = document.getElementById('aiImagUpload').files[0];
                            $scope.uploadAiImage(responce.referenceNum, responce.name, $scope.pictoInfo.pictos.variantType, $scope.pictoInfo.pictos.version, responce.refCharte, selectedFile);
                            /*
                             * SN - GL - 112 - 17-Jul-16 - End
                             */
                            $scope.notificationUpdate();
                            $scope.filterPictos();   
                            notificationService.notify(cultureService.localize('picto.message.createSuccess'));
                            $scope.pictoInfo = {};
                            $("#aiImagUpload").val("");
                            $scope.createCaseFlag = false;
                            $scope.deleteCaseFlag = false;
                            $scope.createNewPictoFamily = false;
                            $scope.informationViewOptionsVisibility = false;
                            $scope.informationViewVisibility = false;
                            $scope.addNewpictoview = false;

                        }, function(error) {
                            if (error.status == 412) {
                                notificationService.notify(cultureService.localize('picto.message.refnumExist'));
                            } else {
                                notificationService.notify(cultureService.localize('picto.message.createError'));
                            }
                        });
                    } else {
                        $scope.updatePicto($scope.pictoInfo);
                    }

                };
                /****************End: Process after Confirmation Actions ************************/

                /********************** Delete Picto After Confirmation. STARTS******************/
                $scope.deletePicto = function() {

                    pictoFamilyService.deletePictoFamily($scope.pictoInfo.referenceNum, function(success) {
                        $('#picto' + $scope.pictoInfo.referenceNum).remove();
                        $scope.informationViewVisibility = false;
                        $scope.informationViewOptionsVisibility = false;
                        $scope.selectedPictos = [];
                        notificationService.notify(cultureService.localize('picto.message.deleteSuccess'));
                    }, function(error) {
                        if (error.status == 412) {
                            notificationService.notify(cultureService.localize('picto.message.deletePreCondition'));
                        } else {
                            notificationService.notify(cultureService.localize('picto.message.deleteError'));
                        }
                    });

                };

                /****************** Delete Picto After Confirmation. End**********************/

                $scope.multiAiDownload = function() {
                    $('#downloadAiFile').modal('show');
                };

                $scope.downloadCartPictosModal = function(pid, variant, event) {
                    $scope.ai = false;
                    $scope.igs = false;
                    $scope.jpg = false;
                    $scope.png = false;
                    $scope.aiw = false;
                    $scope.allVar = false;
                    $scope.allVarsSelected = false;        

                    var frontageVar = null;
                    var frontageId= null;
                    if($scope.selectedPictosIncart>1)
                    {
                    for (var i = 0; i < $scope.pictoInfoIncart.pictos.length; i++) {
                        if ($scope.pictoInfoIncart.pictos[i].isFrontagePicto) {
                            frontageVar = $scope.pictoInfoIncart.pictos[i].variantType;
                            frontageId=$scope.pictoInfoIncart.pictos[i].id;
                            if ($scope.pictoInfoIncart.pictos[i].version != null) {
                                frontageVar = $scope.pictoInfoIncart.pictos[i].variantType + '_' + $scope.pictoInfoIncart.pictos[i].version;
                            }
                        }
                        if ($scope.pictoInfoIncart.pictos[i].id == pid) {
                            if($scope.pictoInfoIncart.refCharte!=null){
                                $scope.downloadPictoNameInCart = $scope.pictoInfoIncart.referenceNum + '_'+ $scope.pictoInfoIncart.refCharte + $scope.pictoInfoIncart.name ;
                            }
                            else{
                            $scope.downloadPictoNameInCart = $scope.pictoInfoIncart.referenceNum + '_' + $scope.pictoInfoIncart.name ;
                            } if ($scope.pictoInfoIncart.pictos[i].version != null) {
                                if($scope.pictoInfoIncart.refCharte!=null){
                                    $scope.downloadPictoNameInCart = $scope.pictoInfoIncart.referenceNum + '_' + $scope.pictoInfoIncart.refCharte + $scope.pictoInfoIncart.name ;
                                }
                                else{
                                $scope.downloadPictoNameInCart = $scope.pictoInfoIncart.referenceNum + '_'  + $scope.pictoInfoIncart.name ;
                                }}

                        }
                    }
                    }

                    $scope.fileTypeVals.splice(0);
                    $scope.formatVals.splice(0);                    
                    var dataList = [];
                    var selectedRows = $scope.selectedPictosIncart;
                    
                    for (var index = 0; index < $scope.selectedPictosIncart.length; index++) {
                        var pictoFamilyObjectVar = selectedRows[index];
                        dataList.push(pictoFamilyObjectVar);
                    }
                    var objectToSend = {                            
                            pictoId : dataList
                       };
                    $scope.msg = pid;
                    var listVariants = pictoVariantService.GetVariants.getAvailableVariants(objectToSend, function() {
                        $scope.availableVarList = [];
                        $scope.tempVarList = [];
                        var hasExist=true;
                        for (var i = 0; i < listVariants.length; i++) {
                            if (listVariants[i] === variant && $scope.selectedPictosIncart.length == 1 && $scope.selectedPictosIncart[0]===pid ) {
                                $scope.availableVarList.push({
                                    'variant' : listVariants[i],
                                    selected : true
                                });
                                hasExist=false;
                                $scope.fileTypeVals.push(listVariants[i]);
                            } else if(frontageVar === listVariants[i] && frontageId === pid )
                            	{
                            	 $scope.availableVarList.push({
                                     'variant' : listVariants[i],
                                     selected : true
                                 });
                                 hasExist=false;
                                 $scope.fileTypeVals.push(listVariants[i]);
                            	
                            	}
                            /* if variant picto are slelcted */
                            for(var y = 0; y < $scope.selectedVariantPictos.length; y++ )
                            	{   	if ($scope.selectedVariantPictos[y]== pid && variant==listVariants[i] && hasExist)
                            	{                            		
                            				$scope.availableVarList.push({
                            					'variant' : listVariants[i],
                            					selected : true
                            				});    
                            				$scope.fileTypeVals.push(listVariants[i]); 
                            				break;
                            			}
                            			else if (variant !=listVariants[i])  
                            			{    
                            				 $scope.tempVarList.push({
                                                 'variant' : listVariants[i],
                                                 selected : false
                                             });                            				 
                            		   }                         				 
                            	} 
                            	/* variant picto not selected */
                                if($scope.selectedVariantPictos.length==0 && listVariants[i] != variant)
                        		{
                                	$scope.availableVarList.push({
                                        'variant' : listVariants[i],
                                        selected : false
                                    });
                        		}
                                /* Push the content to array without duplicate entry */
                            	for (var n = 0; n < $scope.tempVarList.length; n++)
                        		{
                        			if (listVariants[i] ==$scope.tempVarList[n].variant){
                        				$scope.availableVarList.push({
                                            'variant' : $scope.tempVarList[n].variant,
                                            selected : false
                                        });
                        			break;
                        			}
                        		}                           
                        }
                        $('#downloadCartModal').modal('show');
                    }, function(error) {
                    	notificationService.notify(cultureService.localize('picto.message.variantsError'));
                    });

                };

                $scope.inCartModal = function() {
                    $('#inCartDlg').modal('show');
                    $scope.cartUpdate();
                    $scope.pictoSelectedIncart = false;
                    $scope.hasCheckedIncart = false;
                    $scope.selectedPicRefNumsIncart = [];
                    $scope.selectedCartPictoObj = [];
                    $scope.selectedPictosIncart = [];
                    $scope.pictoIncartInfoAreaVisiblity = false;
                    $scope.pictoSelectedForInfoIncart = false;
                    $scope.cartInformationViewVisibility = false;
                    $scope.PictoTitleStatusIncart = "Consult";
                    $scope.cartInformationViewVisibilitySeveral = false;

                };

                $scope.notifModal = function() {

                    /* MJ - 21-Jul-16 - Start */
                    $scope.notificationUpdate();
                    /* MJ - 21-Jul-16 - Ends */
                    $('#notifDlg').modal('show');

                };

                $scope.multiPdfExportIncart = function(event) {
                    var reflistnum = $scope.selectedPicRefNumsIncart;
                    var t = pictoFamilyMultiPDFService.downloadMultiPictoPDF(reflistnum, $scope.language, function(responce) {
                        var win = window.open(responce, '_self');

                    }, function() {
                        $('#notifierDlg').modal('show');
                        $scope.notificationMessage = cultureService.localize('picto.message.multiExportError');

                    });
                };

                $scope.multiPdfExport = function(event) {
                    var reflistnum = $scope.selectedPictos;
                    var t = pictoFamilyMultiPDFService.downloadMultiPictoPDF(reflistnum, $scope.language, function(responce) {
                        var win = window.open(responce, '_self');

                    }, function() {
                        $('#notifierDlg').modal('show');
                        $scope.notificationMessage = cultureService.localize('picto.message.multiExportError');

                    });
                };

                $scope.pdfExport = function(refnum, variantType, name) {
                    var name = name + "_" + variantType;
                    var t = pictoFamilyPDFService.downloadPictoPDF(refnum, name, $scope.language, function(responce) {
                        var win = window.open(responce, '_self');

                    }, function() {
                        $('#notifierDlg').modal('show');
                        $scope.notificationMessage = cultureService.localize('picto.message.sigleExportError');

                    });
                };

                $scope.variantModal = function() {
                    $scope.variant = null;
                    $scope.version = null;
                    $scope.referenceNum = null;
                    $scope.pictoName = null;

                    $("#aiImagUpload").val("");
                    if ($scope.updateCartFlag) {
                        $scope.referenceNum = $scope.pictoInfoIncart.referenceNum;
                        $scope.pictoName = $scope.pictoInfoIncart.name;

                    } else {
                        $scope.referenceNum = $scope.pictoInfo.referenceNum;
                        $scope.pictoName = $scope.pictoInfo.name;

                    }
                    $('#addVariantDlg').modal('show');

                };

                /************************ Start: Add new Picto Variants *******************************/

                $scope.addNewPictoVariant = function(index) {
                    var missingAttributes = "variant";

                    var selectedFile = document.getElementById('aiImagUpload').files[0];
                    if (selectedFile == null) {
                        notificationService.notify(cultureService.localize('picto.message.uploadFile'));
                        return;
                    } else {
                        var ext = selectedFile.name.split('.').pop();
                        if (ext != "ai") {
                            notificationService.notify(cultureService.localize('picto.message.uploadAIFile'));
                            return;
                        }
                    }

                    if ($scope.variant == undefined) {
                        notificationService.notify(cultureService.localize('picto.message.mandatoryCheck') + missingAttributes);
                        return;
                    } else {
                        $('#addVariantDlg').modal("hide");
                    }

                    $scope.fileList = [];
                    $scope.fileList.push(selectedFile);
                    $scope.variantFlag = true;
                    if ($scope.updateCartFlag) {
                        for (var i = 0; i < $scope.pictoInfoIncart.pictos.length; i++) {
                            if ($scope.pictoInfoIncart.pictos[i].isFrontagePicto === true) {
                                $scope.pictoInformation = $scope.pictoInfoIncart.pictos[i];

                            }
                        }

                        for (var i = 0; i < $scope.pictoInfoIncart.pictos.length; i++) {
                            if ($scope.pictoInfoIncart.pictos[i].variantType === $scope.variant) {
                                if ($scope.version != 'undefined' && $scope.version != "" && $scope.pictoInfoIncart.pictos[i].version != $scope.version) {
                                    $scope.variantFlag = true;
                                } else {
                                    $scope.variantFlag = false;
                                    notificationService.notify(cultureService.localize('picto.message.existingVariants'));
                                    break;
                                }
                            }
                        }

                        if ($scope.variantFlag) {
                            $scope.pictoInfoIncart.pictos.push({
                                'id' : null,
                                'variantType' : $scope.variant,
                                'pictoUrl' : null,
                                'isFrontagePicto' : false,
                                'isVisible' : $scope.pictoInformation.isVisible,
                                'familyID' : $scope.pictoInformation.familyID,
                                'createDate' : null,
                                'modifyDate' : null,
                                'imageLocation' : null,
                                'validationLevel' : $scope.pictoInformation.validationLevel,
                                'version' : $scope.version,
                                'imageTypes' : {},
                                'aiFilePath' : $scope.fileList,                               
                            });
                            $scope.variant = null;
                            $scope.version = null;
                            $scope.fileList = [];

                        }

                    } else {

                        for (var i = 0; i < $scope.pictoInfo.pictos.length; i++) {
                            if ($scope.pictoInfo.pictos[i].isFrontagePicto === true) {
                                $scope.pictoInformation = $scope.pictoInfo.pictos[i];

                            }
                        }
                        for (var i = 0; i < $scope.pictoInfo.pictos.length; i++) {
                            if ($scope.pictoInfo.pictos[i].variantType === $scope.variant) {
                                if ($scope.version != 'undefined' && $scope.version != "" && $scope.pictoInfo.pictos[i].version != $scope.version) {
                                    $scope.variantFlag = true;
                                } else {
                                    $scope.variantFlag = false;
                                    notificationService.notify(cultureService.localize('picto.message.existingVariants'));
                                    break;
                                }
                            }
                        }

                        if ($scope.variantFlag) {
                            $scope.pictoInfo.pictos.push({
                                'id' : null,
                                'variantType' : $scope.variant,
                                'pictoUrl' : null,
                                'isFrontagePicto' : false,
                                'isVisible' : $scope.pictoInformation.isVisible,
                                'familyID' : $scope.pictoInformation.familyID,
                                'createDate' : null,
                                'modifyDate' : null,
                                'imageLocation' : null,
                                'validationLevel' : $scope.pictoInformation.validationLevel,
                                'version' : $scope.version,
                                'imageTypes' : {},
                                'aiFilePath' : $scope.fileList,
                            });
                            $scope.variant = null;
                            $scope.version = null;
                            $scope.fileList = [];

                        }
                    }

                };

                /************************** End: Add new Picto Variants **********************************/

                /********************* Start: Submit the rules for picto **********************************/
                
               $scope.addRules = function() {
                    $scope.ruleName = null;
                    $scope.urlLink = null;
                    $('#addMandatoryTexts').modal('show');

                };
                
                $scope.submitRules = function(index) {

                    var missingAttributes = "";
                    
                    if ($scope.ruleName == undefined) {
                        missingAttributes = missingAttributes + "Name"
                    }
                    if ($scope.urlLink == undefined) {
                        missingAttributes = missingAttributes + ",DocLink"
                    }
                    if (missingAttributes != "") {
                        notificationService.notify(cultureService.localize('picto.message.mandatoryCheck') + missingAttributes);
                        return;
                    } else {
                        $('#addMandatoryTexts').modal("hide");
                    }

                    if ($scope.updateCartFlag) {
                        $scope.pictoInfoIncart.rules.push({
                            id : null,
                            name : $scope.ruleName,
                            docLink : $scope.urlLink
                        });

                    } else {
                        $scope.pictoInfo.rules.push({
                            id : null,
                            name : $scope.ruleName,
                            docLink : $scope.urlLink
                        });

                    }
                   
                };

                /********************* End: Submit the rules for picto *************************/

                $scope.ruleModal = function() {
                    $('#addRuleDlg').modal('show');
                };

                /********** Add Specific Drawing function (+) ************/
                $scope.addSpecificDrawings = function() {
                    $scope.specificDrawingName = null;
                    $scope.specificDrawingCommentFR = null;
                    $scope.specificDrawingCommentEN = null;
                    $("#specificDrawUpload").val("");
                    $('#addSpecificDlg').modal('show');
                };

                /********************* Start: Submit the Specific Drawing for picto *******************/

                $scope.submitSpecificDrawing = function() {

                    var missingAttributes = "Name";

                    var selectedFile = document.getElementById('specificDrawUpload').files[0];

                    if (selectedFile == null) {
                        notificationService.notify(cultureService.localize('picto.message.uploadFile'));
                        return;
                    } else {
                        $('#addSpecificDlg').modal("hide");
                    }

                    var fileName = selectedFile.name;
                    $scope.specificDrawingName = fileName;
                    var frComments = $scope.specificDrawingCommentFR;
                    var enComments = $scope.specificDrawingCommentEN;       

                    if ($scope.updateCartFlag) {
                        for (var i = 0; i < $scope.pictoInfoIncart.specificDrawings.length; i++) {
                            if ($scope.pictoInfoIncart.specificDrawings[i].name === $scope.specificDrawingName) {
                                notificationService.notify(cultureService.localize('picto.message.existSpecificDrawName'));
                                return;
                            }
                        }
                        $scope.specificDrawFileList = [];
                        $scope.specificDrawFileList.push(selectedFile);
                        $scope.pictoInfoIncart.specificDrawings.push({ 
                            id : null,
                            name : $scope.specificDrawingName,
                            commentsFR : frComments,
                            commentsEN : enComments,
                            specificDrawFile : fileName,
                            specificDrawFileInfo : $scope.specificDrawFileList

                        });
                    } else {

                        for (var i = 0; i < $scope.pictoInfo.specificDrawings.length; i++) {
                            if ($scope.pictoInfo.specificDrawings[i].name === $scope.specificDrawingName) {
                                notificationService.notify(cultureService.localize('picto.message.existSpecificDrawName'));
                                return;
                            }
                        }
                        $scope.specificDrawFileList = [];
                        $scope.specificDrawFileList.push(selectedFile);
                        $scope.pictoInfo.specificDrawings.push({ 
                            'id' : null,
                            'name' : $scope.specificDrawingName,
                            'commentsFR' : frComments,
                            'commentsEN' : enComments,
                            'specificDrawFile' : fileName,
                            'specificDrawFileInfo' : $scope.specificDrawFileList

                        });
                    }               
                    $scope.specificDrawingName = null;        
                    $scope.specificDrawFileList = [];               

                };
                /*************** End: Submit the Specific Drawing for picto *********************/

             
                /************** Strat : Add to favorite from Cart Area ***********************/
                $scope.addCartFavorite =function(flag)
                {
                	if(flag ===1 || flag)
                	{
                		$scope.removeCartPictosFavorites();
                	}
                	else
                	{
                		$scope.addCartPictosToFavorites();
                	}
                	setTimeout(function(){
                		$scope.tempIntCartFunction();
                	},2000);
                }
                
                
                $scope.addToFavoritesMultipleCart = function(flag)
                {
                    $scope.addLoader();
                	if (flag === 1)
                		{
                			$scope.removeCartPictosFavorites()
                		}
                	else
                	{
                		 if(flag===2)
         		    	{
         		    	  $scope.pictoCart.favValueShop=0;
         		    	}
                		$scope.addCartPictosToFavorites()
                	}
                	setTimeout(function(){
                		$scope.tempIntCartFunction();
                	},2000);
                	
                }

                $scope.addCartPictosToFavorites = function() {
               
                    var selectedRows = $scope.selectedPicRefNumsIncart;

                    var favoritesList = [];
                    for (var index = 0; index < $scope.selectedPicRefNumsIncart.length; index++) {
                        var pictoFamilyObjectVar = selectedRows[index];
                        favoritesList.push(pictoFamilyObjectVar);
                    }
                    var objectToSend = {
                        login : $scope.userLogin,
                        pictoFamily : favoritesList
                    };

                    favouritesService.FavouritesItems.saveFavorite(objectToSend, function() {
                    	 $scope.filterPictos();
                    	 $scope.cartUpdate();
                    	 $scope.getInformationForCart($scope.selectedPicRefNumsIncart[0]);
                    	 $scope.removeLoader();
                        notificationService.notify(cultureService.localize('picto.message.favaroiteSuccess'));
                    }, function() {
                        $scope.removeLoader();
                        notificationService.notify(cultureService.localize('picto.message.favaroiteError'));

                    });

                };
                
                
                $scope.removeCartPictosFavorites = function() {
                    var selectedRows = $scope.selectedPicRefNumsIncart;
                    var favoritesList = [];

                    for (var index = $scope.selectedPicRefNumsIncart.length - 1; index >= 0; index--) {
                        var pictoFamilyObject = selectedRows[index];
                        favoritesList.push(pictoFamilyObject);                       
                    }
                    var objectToSend = {
                        login : $scope.userLogin,
                        pictoFamily : favoritesList
                    };

                    favouritesService.FavouritesItems.removeFavorite(objectToSend, function(success) {
                    	 $scope.filterPictos();
                    	 $scope.cartUpdate();
                    	 $scope.getInformationForCart($scope.selectedPicRefNumsIncart[0]); 
                    	 $scope.removeLoader();
                         notificationService.notify(cultureService.localize('picto.message.favRemoveSuccess'));
                    }, function(error) {
                        $scope.removeLoader();
                        notificationService.notify(cultureService.localize('picto.message.favRemoveError'));

                    });

                };          
                
                /************** End : Add to favorite from Cart Area ***********************/

                $("#checkAllVariants").change(function() {
                    $('input[name="multi"]').prop('checked', $(this).prop("checked"));
                });

                $scope.notifView = function(pid) {
                    $scope.notificationView = true;
                    $scope.informationViewVisibility = true;
                    $scope.informationViewOptionsVisibility = true;
                    $scope.getInformation(pid);
                };
               
                /************* Meha: Start Validate Contrib modification *********/
                                $scope.notifValidateModif = function(familyId) {
                    $scope.informationViewVisibility = true;
                    $scope.informationViewOptionsVisibility = true;
                    var t = validateNotificationService.validateNotification(familyId, function() {
                        $scope.notificationUpdate();
                        $scope.PictoTitleStatus = "Consult";
                        $scope.SeveralPictoStatus = "";
                        $scope.informationViewVisibility = false;
                        $scope.informationViewOptionsVisibility = false;
                        $scope.createNewPictoFamily = false;
                        $scope.ShowMultipleSelected = false;
                    }, function() {
                    });

                };              

                $scope.checkFormat = function(imgType) {

                    var imgTypeSelected = true;
                    if ($scope.formatVals.length > 0) {
                        for (var i = 0; i < $scope.formatVals.length; i++) {
                            if (imgType === $scope.formatVals[i]) {
                                $scope.formatVals.splice(i, 1);
                                imgTypeSelected = false;

                            }

                        }
                        if (imgTypeSelected) {
                            $scope.formatVals.push(imgType);
                        }

                    } else {

                        $scope.formatVals.push(imgType);
                    }

                };
                /* MJ Modified PSA PRP024006-45 START */
                $scope.checkType = function(variantName) {
                    var variantSelected = true;
                    if (variantName === 'allVar') {
                        if ($scope.allVarsSelected) {
                            $scope.fileTypeVals.splice(0);
                            for (var i = 0; i < $scope.availableVarList.length; i++) {
                                $scope.availableVarList[i].selected = false;
                            }
                            $scope.allVarsSelected = false;
                        } else {
                            for (var i = 0; i < $scope.availableVarList.length; i++) {
                                $scope.availableVarList[i].selected = true;
                                $scope.fileTypeVals.push($scope.availableVarList[i].variant);

                            }
                            $scope.allVarsSelected = true;
                        }
                    } else {
                        if ($scope.fileTypeVals.length > 0) {
                            for (var i = 0; i < $scope.fileTypeVals.length; i++) {
                                if (variantName === $scope.fileTypeVals[i]) {
                                    $scope.fileTypeVals.splice(i, 1);
                                    variantSelected = false;

                                    $scope.allVar = false;

                                }

                            }
                            if (variantSelected) {
                                $scope.fileTypeVals.push(variantName);
                                if ($scope.fileTypeVals.length == $scope.availableVarList.length) {
                                    $scope.allVar = true;
                                    $scope.allVarsSelected = true;
                                } else {
                                    $scope.allVar = false;
                                    $scope.allVarsSelected = false;
                                }
                            }

                        } else {

                            $scope.fileTypeVals.push(variantName);
                        }
                    }
                };
                /* MJ Modified PSA PRP024006-45 END */
                
                
                /*********** Start : Download Files *****************/
                $scope.downloadFiles = function() {
                	
                	$scope.showLoader=false;
                	
                    if ($scope.fileTypeVals.length === 0 || $scope.formatVals.length === 0) {
                    	$scope.showLoader=true;
                        $('#notifierDlg').modal('show');
                        $scope.notificationMessage = cultureService.localize('picto.message.downloadFormat');
               
                    } else {
                        $('#downloadTypes').modal('hide');
                        $scope.ai = false;
                        $scope.igs = false;
                        $scope.jpg = false;
                        $scope.png = false;
                        $scope.aiw = false;
                        $scope.allVar = false;
                        $scope.allVarsSelected = false;
                        $scope.l_DownloadPictosRepresentation = {

                            'imageType' : $scope.formatVals,
                            'pictoIds' : $scope.selectedPictoIds,
                            'variants' : $scope.fileTypeVals
                        };
                        var objectToSend = $scope.l_DownloadPictosRepresentation;
                        var responseMsg = downloadPictoService.downloadPictos.downloadPicto(objectToSend, function(responce) {
                            var win = window.open(responce, '_self');
                            $scope.fileTypeVals.splice(0);
                            $scope.formatVals.splice(0);
                            $scope.showLoader=true;
                            $('#notifierDlg').modal('show');
                            $scope.notificationMessage =  cultureService.localize('picto.message.downloadSuccess');
                            
                        }, function(error) {
                            $('#notifierDlg').modal('show');
                            if (error.status == 420) {
                                $scope.notificationMessage = cultureService.localize('picto.message.downloadError');   
                            } else {
                                $scope.notificationMessage = cultureService.localize('picto.message.downloadServerError') + error.status;
                            }
                            $scope.showLoader=true;
                        });
                    }
                };
                /*********** End : Download Files *****************/
                
                $scope.downloadPictoModal = function(pid, variant, event) {
                    var frontageVar = null;            
                    
                    if ($scope.selectedPictoIds.length === 1)
                    {
                    for (var i = 0; i < $scope.pictoInfo.pictos.length; i++) {
                        if ($scope.pictoInfo.pictos[i].isFrontagePicto) {
                            frontageVar = $scope.pictoInfo.pictos[i].variantType;
                            if ($scope.pictoInfo.pictos[i].version != null) {
                                frontageVar = $scope.pictoInfo.pictos[i].variantType + '_' + $scope.pictoInfo.pictos[i].version;
                            }
                        }                                               
                        
                        if ($scope.pictoInfo.pictos[i].id == pid) {
                            if ($scope.pictoInfo.refCharte != null) {
                            $scope.downloadPictoName = $scope.pictoInfo.referenceNum + '_' +$scope.pictoInfo.refCharte+ $scope.pictoInfo.name;
                            if ($scope.pictoInfo.pictos[i].version != null) {
                                $scope.downloadPictoName = $scope.pictoInfo.referenceNum + '_'+$scope.pictoInfo.refCharte+ $scope.pictoInfo.name;
                            }
                            }
                            else {
                                $scope.downloadPictoName = $scope.pictoInfo.referenceNum + '_' + $scope.pictoInfo.name;
                                if ($scope.pictoInfo.pictos[i].version != null) {
                                    $scope.downloadPictoName = $scope.pictoInfo.referenceNum + '_'+ $scope.pictoInfo.name ;
                                }
                            }
                        }                       
                     }
                   }

                    $scope.ai = false;
                    $scope.igs = false;
                    $scope.jpg = false;
                    $scope.png = false;
                    $scope.aiw = false;
                    $scope.allVar = false;
                    $scope.allVarsSelected = false;
                    $scope.fileTypeVals.splice(0);
                    $scope.formatVals.splice(0);
                    var pictoIds = $scope.selectedPictoIds;                      
                  
                    $scope.msg = pid;                   
                    var dataList = [];
                    var selectedRows = $scope.selectedPictoIds;
                    
                    for (var index = 0; index < $scope.selectedPictoIds.length; index++) {
                        var pictoFamilyObjectVar = selectedRows[index];
                        dataList.push(pictoFamilyObjectVar);
                    }
                    
                    var objectToSend = {                            
                            pictoId : dataList
                        };                    
                    
                    var listVariants = pictoVariantService.GetVariants.getAvailableVariants(objectToSend, function() {
                        $scope.availableVarList = [];  
                        $scope.tempVarList = [];
                        for (var i = 0; i < listVariants.length; i++) {                        
                            if (listVariants[i] === frontageVar && $scope.selectedPictoIds.length == 1 && $scope.selectedPictoIds[0]===pid ) {
                                $scope.availableVarList.push({
                                    'variant' : listVariants[i],
                                    selected : true
                                });
                                $scope.fileTypeVals.push(frontageVar);                         
                            }
                            /* if variant picto are slelcted */
                            for(var y = 0; y < $scope.selectedVariantPictos.length; y++ )
                            	{   	if ($scope.selectedVariantPictos[y]== pid && variant==listVariants[i])                            			
                            			{                            		
                            				$scope.availableVarList.push({
                            					'variant' : listVariants[i],
                            					selected : true
                            				});    
                            				$scope.fileTypeVals.push(listVariants[i]); 
                            				break;
                            			}
                            			else if (variant !=listVariants[i])  
                            			{    
                            				 $scope.tempVarList.push({
                                                 'variant' : listVariants[i],
                                                 selected : false
                                             });                            				 
                            		   }                         				 
                            	} 
                            	/* variant picto not selected */
                                if($scope.selectedVariantPictos.length==0 && listVariants[i] != frontageVar)
                        		{
                                	$scope.availableVarList.push({
                                        'variant' : listVariants[i],
                                        selected : false
                                    });
                        		}
                                /* Push the content to array without duplicate entry */
                            	for (var n = 0; n < $scope.tempVarList.length; n++)
                        		{
                        			if (listVariants[i] ==$scope.tempVarList[n].variant){
                        				$scope.availableVarList.push({
                                            'variant' : $scope.tempVarList[n].variant,
                                            selected : false
                                        });
                        			break;
                        			}
                        		}
                            
                      }                                        

                        $('#downloadTypes').modal('show');
                    }, function(error) {
                    	notificationService.notify(cultureService.localize('picto.message.variantsError'));
                    });

                };

           
                /***********  Meha: Start Download picto from cart **************/
                $scope.downloadCartFiles = function() {               ;
                $scope.addLoader();
                    if ($scope.fileTypeVals.length === 0 || $scope.formatVals.length === 0) {
                        $scope.removeLoader();
                        $('#notifierDlg').modal('show');
                        $scope.notificationMessage = cultureService.localize('picto.message.downloadFormat');
                    } else {
                        $('#downloadCartModal').modal('hide');

                        $scope.ai = false;
                        $scope.igs = false;
                        $scope.jpg = false;
                        $scope.png = false;
                        $scope.aiw = false;
                        $scope.allVar = false;
                        $scope.allVarsSelected = false;
                        $scope.selectedVar = false;
                        $scope.l_DownloadPictosRepresentation = {

                            'imageType' : $scope.formatVals,
                            'pictoIds' : $scope.selectedPictosIncart,
                            'variants' : $scope.fileTypeVals
                        };
                        var objectToSend = $scope.l_DownloadPictosRepresentation;
                        var t = downloadPictoService.downloadPictos.downloadPicto(objectToSend, function(responce) {
                            var win = window.open(responce, '_self');
                            $scope.fileTypeVals.splice(0);
                            $scope.formatVals.splice(0);
                            $scope.removeLoader();
                            $('#notifierDlg').modal('show');
                            $scope.notificationMessage = cultureService.localize('picto.message.downloadSuccess');
                        }, function(error) {
                            $scope.removeLoader();
                            $('#notifierDlg').modal('show');
                            if (error.status == 420) {
                                $scope.notificationMessage = cultureService.localize('picto.message.downloadError');
                            } else {
                                $scope.notificationMessage = cultureService.localize('picto.message.downloadServerError') + error.status;
                            }
                        });
                    }
                };

                $scope.changeAllKinfOfIcons = function() {
                    if ($scope.allKindofIcons == true) {
                        $scope.chartePsa = true
                        $scope.isoReg = true;
                        $scope.isoAut = true;
                        $scope.nonIso = true;
                        $scope.others = true
                    } else {
                        $scope.chartePsa = false
                        $scope.isoReg = false;
                        $scope.isoAut = false;
                        $scope.nonIso = false;
                        $scope.others = false;
                        /*
                         * MJ 26-Jul-2016 PSA PRP024006-54 Start
                         */
                        $scope.isNullType = false;
                        /*
                         * MJ 26-Jul-2016 PSA PRP024006-54 END
                         */
                    }
                };

                $scope.changeChartePsa = function() {
                    if ($scope.chartePsa == true) {
                        $scope.isoReg = true;
                        $scope.isoAut = true;
                        $scope.nonIso = true;
                        $scope.allKindofIcons = false;
                    } else {
                        $scope.isoReg = false;
                        $scope.isoAut = false;
                        $scope.nonIso = false;
                    }
                    if ($scope.others == false || $scope.chartePsa == false || $scope.isoReg == false || $scope.isoAut == false || $scope.nonIso == false) {
                        $scope.allKindofIcons = false;
                        /*
                         * MJ 26-Jul-2016 PSA PRP024006-54 Start
                         */
                        $scope.isNullType = false;
                        /*
                         * MJ 26-Jul-2016 PSA PRP024006-54 END
                         */
                    }
                    if ($scope.others == true && $scope.chartePsa == true && $scope.isoReg == true && $scope.isoAut == true && $scope.nonIso == true) {
                        $scope.allKindofIcons = true;
                    }
                };

                $scope.changeIcon = function() {
                    if ($scope.isoReg == true && $scope.isoAut == true && $scope.nonIso == true) {
                        $scope.chartePsa = true;
                    } else {
                        $scope.chartePsa = false;
                        $scope.allKindofIcons = false;
                    }
                    if ($scope.isoReg == true && $scope.isoAut == true && $scope.nonIso == true && $scope.others == true) {
                        $scope.allKindofIcons = true;
                    }
                };

                $scope.changeOthers = function() {
                    if ($scope.others == true && $scope.chartePsa == true) {
                        $scope.allKindofIcons = true;
                    }
                    if ($scope.others == false || $scope.chartePsa == false || $scope.isoReg == false || $scope.isoAut == false || $scope.nonIso == false) {
                        $scope.allKindofIcons = false;
                        /*
                         * MJ 26-Jul-2016 PSA PRP024006-54 Start
                         */
                        $scope.isNullType = false;
                        /*
                         * MJ 26-Jul-2016 PSA PRP024006-54 End
                         */
                    }
                    if ($scope.others == true && $scope.chartePsa == true && $scope.isoReg == true && $scope.isoAut == true && $scope.nonIso == true) {
                        $scope.allKindofIcons = true;
                    }
                };

                /******************** Start: Get all Super Category **************************/
                $scope.allSuperCategories = function() {
                    superCategoryService.getAllSuperCategory(function(response) {
                        $scope.listSuperCategories = response;

                    }, function() {
                    });
                };

                $scope.allSuperCategories();

                /****************** End: Get all Super Category ********************************/

                /*************** Start: Get all Category by super category name *****************/
                $scope.allCategoryFromSuperName = function(id) {
                    $scope.listAllCategoryCompleteReference = [];
                  
                    if (id != null && $scope.updateCartFlag) {
                        $scope.pictoInfoIncart.categoryID.name = "";
                    }
                    if (id != null && !$scope.updateCartFlag) {
                        $scope.pictoInfo.categoryID.name = "";
                    }

                    var t = superCategoryService.getAllCategory(id, function(response) {
                        $scope.listAllCategory = response;                       
                        $scope.listAllCategoryCompleteReference = response;
                    }, function() {

                    });
                };

                $scope.allCategoryFromSuperName(null);            
               
                $scope.allCategoryList =function()
                {
                	superCategoryService.getAllCategoryList(function(response) {
                    $scope.allCatogory = response;                       
                }, function() {

                });
              };
            
               $scope.allCategoryList();                
                /****************** End: Get all Category by super category name *****************/

                /********************** Start: Get List of Types for the pictos *****************/

                $scope.allTypes = function() {
                    pictoFamilyService.allTypes(function(response) {
                        $scope.listTypes = response;

                    }, function() {
                    });
                };

                $scope.allTypes();
                /*************** End: Get List of Types for the pictos ************************/

                /*************** Start: Create new picto *********************************/
                $scope.addNewPicto = function() {
                    pictoService.thickClientInstall(function(success) {
                        $scope.createCaseFlag = true;
                        $scope.deleteCaseFlag = false;
                        $scope.updateCartFlag = false;
                        $scope.pictos = [];
                        $scope.remainVariant = [];
                        $scope.pictoInfo = {};
                        $scope.createNewPictoFamily = true;
                        $scope.informationViewOptionsVisibility = true;
                        $scope.informationViewVisibility = true;
                        $scope.informationViewAddTitle = true;
                        $scope.variantInArea = true;
                        $scope.frontageInArea = true;
                        $scope.addNewpictoview = true;
                        $scope.showFlag = false;
                        $scope.imageColor=false;
                        $scope.pictoInfo.witnessActive = [
                                {
                                    "id" : 1,
                                    "color" : "Ambre",
                                    "flag" : false,
                                    "orderId" : 1
                                }, {
                                    "id" : 2,
                                    "color" : "Rouge",
                                    "flag" : false,
                                    "orderId" : 2
                                }, {
                                    "id" : 3,
                                    "color" : "Blue",
                                    "flag" : false,
                                    "orderId" : 3
                                }, {
                                    "id" : 4,
                                    "color" : "Vert",
                                    "flag" : false,
                                    "orderId" : 4
                                }, {
                                    "id" : 5,
                                    "color" : "White",
                                    "flag" : false,
                                    "orderId" : 5
                                }
                        ];

                        $scope.pictoInfo.witnessAlert = [
                                {
                                    "id" : 1,
                                    "color" : "Ambre",
                                    "flag" : false,
                                    "orderId" : 1
                                }, {
                                    "id" : 2,
                                    "color" : "Rouge",
                                    "flag" : false,
                                    "orderId" : 2
                                }, {
                                    "id" : 3,
                                    "color" : "Blue",
                                    "flag" : false,
                                    "orderId" : 3
                                }, {
                                    "id" : 4,
                                    "color" : "Vert",
                                    "flag" : false,
                                    "orderId" : 4
                                }, {
                                    "id" : 5,
                                    "color" : "White",
                                    "flag" : false,
                                    "orderId" : 5
                                }
                        ];

                        $scope.pictoInfo.witnessFailure = [
                                {
                                    "id" : 1,
                                    "color" : "Ambre",
                                    "flag" : false,
                                    "orderId" : 1
                                }, {
                                    "id" : 2,
                                    "color" : "Rouge",
                                    "flag" : false,
                                    "orderId" : 2
                                }, {
                                    "id" : 3,
                                    "color" : "Blue",
                                    "flag" : false,
                                    "orderId" : 3
                                }, {
                                    "id" : 4,
                                    "color" : "Vert",
                                    "flag" : false,
                                    "orderId" : 4
                                }, {
                                    "id" : 5,
                                    "color" : "White",
                                    "flag" : false,
                                    "orderId" : 5
                                }
                        ];
                    }, function(error) {
                        if (error.status == 412) {
                            $('#notifierDlg').modal('show');
                            /*
                             * SN - PSA - PRP024006-39 - 26-Jul-16 - End
                             */
                            $scope.notificationMessage = cultureService.localize('picto.message.thickClientInstall');
                            /*
                             * SN - PSA - PRP024006-39 - 26-Jul-16 - End
                             */
                        }
                    });
                    if ($scope.selectedPictoIds.length >= 1 || $scope.selectedPictos.length >= 1) {
                        $scope.selectedPictoIds = [];
                        $scope.selectedPictos = [];
                        $scope.selectedPictoObj=[];
                        $('.custom-picto-col').removeClass('picto-active');
                        $scope.infoAreaStatus();
                    }

                };
                /******************* End: Create new picto ****************************/
                
                /***** Object created to prepare representation *************/
                function PictoRepresentation(_variantType, _pictoUrl, _isFrontagePicto, _isVisible, _imageLocation, _validationLevel, _version) {
                    this.variantType = _variantType;
                    this.pictoUrl = _pictoUrl;
                    this.isFrontagePicto = false;
                    this.isVisible = true;
                    this.imageLocation = _imageLocation;
                    this.validationLevel = _validationLevel;
                    this.version = _version;
                }

                /* Object creation for Picto Family. */

                $scope.createPictoFamilyObject = function() {
                    $scope.pictoFamily = {
                        id : null,
                        referenceNum : null,
                        name : null,
                        informationType : null,
                        informationFR : null,
                        informationEN : null,
                        adminInfo : null,
                        functionFR : null,
                        functionEN : null,
                        typeID : null,
                        refCharte : null,
                        command : false,
                        commandInformation : null,
                        witnessActive : false,
                        witnessAlert : false,
                        witnessFailure : false,
                        rhnInfoFR : null,
                        rhnInfoEN : null,
                        categoryID : null,
                        keywordFR : null,
                        keywordEN : null,
                        pictos : [],
                        specificDrawings : [],
                        rules : [],
                    };
                };

                /******* To hide the selected type ************/
                $scope.selectType = function() {
                    var option = null;
                    if ($scope.updateCartFlag) {
                        option = $scope.pictoInfoIncart.typeID.typeLabel;

                    } else {
                        option = $scope.pictoInfo.typeID.typeLabel;
                    }
                    if (option === "Iconothèque") {
                        $scope.showFlag = false;
                    } else {
                        $scope.showFlag = true;
                    }

                };

                /************* Start: Get list of Validation Level ********************/

                $scope.allValidation = function() {
                    pictoService.getAllValidation(function(response) {
                        $scope.listValidation = response;

                    }, function() {
                    });
                };

                $scope.allValidation();

                /************* End: Get list of Validation Level *************************/

                /*************** Start: Get list of Information Level ********************/

                $scope.allInformation = function() {
                    pictoFamilyService.getAllInformation(function(response) {
                        $scope.listInformation = response;

                    }, function() {
                    });
                };

                $scope.allInformation();

                /*********** End: Get list of Information Level *******************/

                /**************** Start: Get list of Visibilty *********************/

                $scope.allVisible = function() {
                    pictoService.getAllVisible(function(response) {
                        $scope.listofVisbile = response;

                    }, function() {
                    });
                };

                $scope.allVisible();

                /***************** End: Get list of Visibilty *****************************/

                /************** Start: Remove the picto from Shopping Cart *****************/
                
                
                $scope.cartInfoArea = function(id,variant,event)
                {
                	var cartFlag = variant.cartFlag
                	if(cartFlag)
                		{
                		variant.cartFlag = 0;	
                		$scope.removeCartCentral(id,event);                		  
                		}
                	else
                	{
                		variant.cartFlag = 1; 
                		$scope.addPictoToCart(id,event);
                	}
                }
                $scope.removeCartPicto = function(value) {
                	
                	if(value === true)
                	{
                	    $scope.addLoader();
                	}

                    var objectToSend = {
                        login : $scope.userLogin,
                        pictoId : $scope.selectedPictosIncart
                    };
                    

                    cartService.CartItems.removeShop(objectToSend, function(success) {
                    	
                        $scope.cartUpdate();                      
                        $scope.pictoIncartInfoAreaVisiblity = false;
						$scope.pictoSelectedForInfoIncart = false;
						$scope.cartInformationViewVisibility = false;	
						//$scope.tempSelected =[];
      
                     	$scope.filterPictos();
                     	if ($scope.selectedPictos.length > 0) {
                     		$scope.getInformation($scope.selectedPictos[0]);
						}   
                     	$scope.removeLoader();
                        notificationService.notify(cultureService.localize('picto.message.removeCartSuccess'));
                    }, function(error) {
                        $scope.removeLoader();
                        notificationService.notify(cultureService.localize('picto.message.removeCartError'));

                    });

                };
                /******************* End: Remove the picto from Shopping Cart *********************/

                $scope.uploadFile = function() {
                	 $("#importPicto").val("");
                    $('#uploadFileDlg').modal('show');                   
                };

                var newPictoSelection = false;
                /**
                 * ******************** Start: Create picto focus change *****************************
                 */

                $scope.focusChange = function(event) {
                    $scope.hasChecked = false;
                    $scope.pictoActive=true;
                    $scope.focusChangeFlag = true;
                    if ($scope.createNewPictoFamily == true) {
                        $('#modifyDlg').modal('show');
                        $scope.addNewpictoview = false;
                    }

                    if ($scope.updateFlag === true && !angular.equals($scope.tempPictoInfo, $scope.pictoInfo)) {
                        $('#modifyDlg').modal('show');
                        return;

                    }

                    $scope.selectedPictoIds = [];
                    $scope.selectedPictos = [];
                    $scope.selectedPictos = [];                  
                    $scope.tempSelected = [];
                    $scope.selectedVariantPictos=[];
                    $('.custom-picto-col').removeClass('picto-active');
                    $scope.infoAreaStatus();
                    if (newPictoSelection) {
                        if ($scope.selectedPictoIds.length >= 1 || $scope.selectedPictos.length >= 1) {
                            $scope.selectedPictoIds = [];
                            $scope.selectedPictos = [];
                            $('.custom-picto-col').removeClass('picto-active');
                            $scope.infoAreaStatus();
                        }
                    } else {
                        newPictoSelection = true;
                    }

                };

                /**
                 * ************************ End: Create picto focus change *********************************
                 */

                /* PB - Added - 15-Jul-16 - Start */

                $scope.abortChange = function() {
                    $scope.createCaseFlag = false;
                    $scope.deleteCaseFlag = false;
                    $scope.createNewPictoFamily = false;
                    $scope.informationViewOptionsVisibility = false;
                    $scope.informationViewVisibility = false;
                    $scope.informationViewAddTitle = false;
                    $scope.variantInArea = false;
                    $scope.frontageInArea = false;
                    $scope.addNewpictoview = false;
                    $scope.updateFlag = false;
                    $scope.selectedPictoIds = [];
                    $scope.selectedPictos = [];
                    $scope.selectedPictos = [];
                    $('.custom-picto-col').removeClass('picto-active');
                    $scope.focusChangeFlag = false;

                    $scope.updateCartFlag = false;
                    $scope.selectedPictosIncart = [];
                    $scope.selectedPicRefNumsIncart = [];
                    $scope.selectedCartPictoObj = [];
                    $scope.pictoSelectedIncart = false;
                    $scope.cartInformationViewVisibility = false;
                    $scope.pictoIncartInfoAreaVisiblity = false;
                    $scope.pictoSelectedForInfoIncart = false;
                    $scope.cartInformationViewVisibilitySeveral = false;
                    $scope.PictoTitleStatusIncart = "Consult";
                    $('.custom-picto-col-incart').removeClass('picto-active');

                };

                $scope.cartInfocus = function(event) {

                    if ($scope.updateCartFlag === true && !angular.equals($scope.tempPictoInfoInCart, $scope.pictoInfoIncart)) {
                        $('#modifyDlg').modal('show');
                        return;
                    }

                    $scope.selectedPictosIncart = [];
                    $scope.selectedPicRefNumsIncart = [];
                    $scope.selectedCartPictoObj = [];
                    $('.custom-picto-col-incart').removeClass('picto-active');
                    $scope.pictoSelectedIncart = false;
                    $scope.infoAreaStatusIncart();
                    $scope.hasCheckedIncart = false;
                    if (newPictoSelection) {
                        if ($scope.selectedPicRefNumsIncart.length >= 1 || $scope.selectedPictosIncart.length >= 1) {
                            $scope.selectedPictosIncart = [];
                            $scope.selectedPicRefNumsIncart = [];
                            $('.custom-picto-col-incart').removeClass('picto-active');
                            $scope.infoAreaStatusIncart();
                        }
                    } else {
                        newPictoSelection = true;
                    }
                }

                $scope.pictoPropagation = function(event) {
                    event.stopPropagation();
                };
                /* PB - Added - 15-Jul-16 - End */

                /**
                 * To save the content called From Home.html *
                 */

                $scope.onActionModify = function() {
                    var obj = false;
                    if ($scope.updateCartFlag) {
                        obj = $scope.validateDataBeforeSaveCart();
                    } else {
                        obj = $scope.validateDataBeforeSave();
                    }

                    if (obj) {
                        $scope.onActionConfirmation();
                    }

                };

                /**
                 * ************************** Start: Upload images/files **********************************
                 */
                /* SN - GL - 112 - 17-Jul-16 - Start */
                $scope.uploadAiImage = function(refNum, name, variant, version, refCharte, selectedFile) {
                	if(refCharte == "")
                		{
                		  refCharte=null;
                		}
                    var fd = new FormData();
                    if (selectedFile == null) {
                        notificationService.notify("Select files for update.");
                    } else {
                        var ext = selectedFile.name.split('.').pop();
                        if (ext == "ai") {
                            fd.append("file", selectedFile);
                            var url = "./pictoFamily/uploadNewFile/" + refNum + "/" + name + "/" + variant + "/" + version + "/"+refCharte;

                            $http.post(url, fd, {
                                withCredentials : true,
                                headers : {
                                    'Content-Type' : undefined
                                },
                                transformRequest : angular.identity
                            }).then(function(response) {
                            }, function(error) {
                            })
                        } else {
                            notificationService.notify(cultureService.localize('picto.message.uploadAIFile'));
                        }
                    }
                };
                /* SN - GL - 112 - 17-Jul-16 - End */

                /*
                 * SN - PSA - PRP024006-61 - 17-Aug-16 - Start
                 */
                $scope.uploadSpecificDrawFile = function(refNum, name, refCharte, selectedFile) {
                	
                	if(refCharte == "")
            		{
            		  refCharte=null;
            		}
                    var fd = new FormData();
                    if (selectedFile == null) {
                        notificationService.notify("Select files for update.");
                    } else {
                        var ext = selectedFile.name.split('.').pop();
                        fd.append("file", selectedFile);
                        var url = "./pictoFamily/uploadSpecificDraw/" + refNum + "/" + name + "/"+refCharte;

                        $http.post(url, fd, {
                            withCredentials : true,
                            headers : {
                                'Content-Type' : undefined
                            },
                            transformRequest : angular.identity
                        }).then(function(response) {
                        }, function(error) {
                        })
                    }
                };
                /*
                 * SN - PSA - PRP024006-61 - 17-Aug-16 - Start
                 */

                /**
                 * *********************** End: Upload images/files ****************************************
                 */
                /**
                 * *********************** Start:Import shopping cart ************************************
                 */
                /* MR - GL - 22-Jul-16 - Start */
      
                $scope.importPictodetail = [];

                $scope.ImportPicto = function() {
                    $scope.importPictodetail = [];
                    var fileImport = document.getElementById('importPicto').files[0];
                    var fileName = document.getElementById('importPicto').value
                    var ext = fileName.substr(fileName.lastIndexOf('.') + 1);
                    if (fileImport && ext.toUpperCase() === 'TXT') {
                        var r = new FileReader();
                        r.onload = function(e) {
                            var contents = e.target.result;
                            var lines = contents.split('\n');

                            for (var line = 0; line < lines.length; line++) {

                                var word = lines[line].split(',');
                                if (word.length >= 3) {
                                    var ref = word[0].trim();
                                    var name = word[1].trim();
                                    var variant = word[2].trim();
                                    var version = null;
                                    if (angular.isUndefined(word[3]) || word[3] === '') {
                                        version = null
                                    } else {
                                        version = word[3].trim();
                                    }
                                    
                                 	if(version == "")
                                	{
                                		version=null;
                                	}                                      
                                    var objectToSend = {
                                            referenceNum : ref,
                                            name : name,
                                            variant : variant,
                                            version : version
                                        };                                    
                                    $scope.importPictodetail.push(objectToSend);                          
                                }
                            }    
                            $scope.findPicto($scope.importPictodetail);
                        }
                        r.readAsText(fileImport);
                    } else {
                        notificationService.notify(cultureService.localize('picto.message.fileImport'));
                    }                   
                };
                
                $scope.findPicto = function (importPictodetail)
                {
                	var objectToSend = {
                			   importPicto : importPictodetail
                          };
                	cartService.CartItems.import(objectToSend, function(responce){
                		$scope.cartUpdate();
                	},
                	 function(error) {
                		notificationService.notify(cultureService.localize('picto.message.fileImportFailed'));
                     });
                }                        
           
                /* MR - GL - 22-Jul-16 - Start */
                /**
                 * ********************** End:Import shopping cart ***********************************
                 */


                
                $scope.colorCheck = function(color, orginalColort) {
                    if (color === orginalColort) {
                        return true;
                    } else {
                        return false;
                    }

                }

                /* PB - GL - 5-Aug-16 - Start */

                $scope.eyeSlasherCart = function(picto) {
                    setTimeout(function() {
                        for (var i = 0; i < $scope.pictoInfoIncart.pictos.length; i++) {
                            if ($scope.pictoInfoIncart.pictos[i].id === picto.id) {
                                $scope.pictoInfoIncart.pictos.splice(i, 1);
                                $scope.pictoInfoIncart.pictos.push(picto);
                                break;
                            }
                        }

                        $scope.updatePicto($scope.pictoInfoIncart);
                        $scope.updateCartFlag = true;

                    }, 300);
                };

                $scope.eyeSlasherCentral = function(picto) {
                    if ($scope.updateFlag === true && !angular.equals($scope.tempPictoInfo, $scope.pictoInfo)) {
                        $scope.filterPictos();    
                        return;
                    }
                    setTimeout(function() {
                        for (var i = 0; i < $scope.pictoInfo.pictos.length; i++) {
                            if ($scope.pictoInfo.pictos[i].id === picto.id) {
                                $scope.pictoInfo.pictos.splice(i, 1);
                                $scope.pictoInfo.pictos.push(picto);
                                break;
                            }
                        }
                        $scope.updatePicto($scope.pictoInfo);
                    }, 300);
                }

                $scope.selectDeselectFrontage = function(frontageid,event) {
                    var frontageStatus = angular.element(document.querySelector('#frontage' + frontageid));

                    $(frontageStatus).addClass('picto-active');                
                }
                
                
                $scope.selectDeselectFrontageIncart = function(frontageid,event) {
                    var frontageStatusIncart = angular.element(document.querySelector('#frontageIncart' + frontageid));
                    $(frontageStatusIncart).addClass('picto-active');           

                }
                
                /* MJ - PSA - 24-Aug-16 - Start */
				$scope.checkNullCharte = function(refChart, refNum) {
					$( ".custom-break-sec" ).remove();
					var some = false;
					if (check === 0 && $scope.isCharteSort && refChart === null) {
						check++;
						setTimeout(function() {
                            $scope.notCharteLabel= cultureService.localize('picto.label.NotCharteKind');
                            $scope.templateCharte = "<div class='custom-break-sec' data-ng-if='showLoader' style='text-align:center'>"+$scope.notCharteLabel+"</div>" ;
							var test = angular.element(document.querySelector('#picto' + refNum));
							$(test).addClass('custom-break');
							$( ".custom-break" ).before(templateCharte);
						}, 5000);
						some = true;
					} else {
						some = false;
					}
					return some;
				};

                /* PB - GL - 5-Aug-16 - Start */

                /**
                 * ********************** Start : Update the picto family **************************
                 */
                $scope.updatePicto = function(pictoInfo) {
                    pictoFamilyService.updatePictoFamily(pictoInfo, function(success) {
                    	var updatePictoObj = success;
                        if ($scope.updateCartFlag) {
                            $scope.updateCartFlag = false;
                            for (var j = 0; j < pictoInfo.pictos.length; j++) {
                                if (pictoInfo.pictos[j].id == null) {
                                    $scope.uploadAiImage(pictoInfo.referenceNum, pictoInfo.name, pictoInfo.pictos[j].variantType, pictoInfo.pictos[j].version,pictoInfo.refCharte, pictoInfo.pictos[j].aiFilePath[0]);
                                }
                            }

                            for (var k = 0; k < pictoInfo.specificDrawings.length; k++) {
                                if (pictoInfo.specificDrawings[k].id == null) {
                                    $scope.uploadSpecificDrawFile(pictoInfo.referenceNum, pictoInfo.name,pictoInfo.refCharte,pictoInfo.specificDrawings[k].specificDrawFileInfo[0]);
                                }
                            }
                            $scope.cartUpdate();                  
                            $scope.updateContent(updatePictoObj);
                            $scope.selectedVariantPictos =[];
                            $scope.getInformationForCart($scope.selectedPicRefNumsIncart[0]);
                            if ($scope.selectedPictos.length > 0) {
                                $scope.getInformation($scope.selectedPictos[0]);
                            }

                        } else {
                        	
                            for (var j = 0; j < pictoInfo.pictos.length; j++) {
                                if (pictoInfo.pictos[j].id == null) {
                                    $scope.uploadAiImage(pictoInfo.referenceNum, pictoInfo.name, pictoInfo.pictos[j].variantType, pictoInfo.pictos[j].version,pictoInfo.refCharte, pictoInfo.pictos[j].aiFilePath[0]);
                                }
                            }

                            for (var k = 0; k < pictoInfo.specificDrawings.length; k++) {
                                if (pictoInfo.specificDrawings[k].id == null) {
                                    $scope.uploadSpecificDrawFile(pictoInfo.referenceNum, pictoInfo.name,pictoInfo.refCharte, pictoInfo.specificDrawings[k].specificDrawFileInfo[0]);
                                }
                            }
                            $("#aiImagUpload").val("");
                            $scope.updateContent(updatePictoObj);
                            
                            $scope.pictoInfo.deletedPictosList = [];
                            $scope.pictoInfo.deletedSpecDrawList = [];     
                            $scope.selectedVariantPictos =[];
                            if ($scope.notificationView) {
                                $scope.informationViewVisibility = false;
                                $scope.informationViewOptionsVisibility = false;
                                $scope.createNewPictoFamily = false;
                                $scope.SeveralPictoStatus = "";
                                $scope.frontage = null;
                                $scope.remainVariant = [];
                                $scope.ShowMultipleSelected = false;
                                $scope.HideMultipleSelected = true;
                                $scope.notificationView = false;
                                $scope.selectedPictos = [];
                                $scope.selectedPictoIds = [];
                                $scope.selectedPictoObj =[];
                                $('.custom-picto-col').removeClass('picto-active');
                            } else {
                                $scope.getInformation($scope.selectedPictos[0]);
                            }
                        }
                        setTimeout(function() {
                            for(var i=0; i<$scope.tempSelected.length; i++){
                                  var status = angular.element(document.querySelector('#picto' +$scope.tempSelected[i] ));
                                  $(status).addClass('picto-active');
                                }
                        }, 200);
                        notificationService.notify(cultureService.localize('picto.message.updateSuccess'));
                        $scope.notificationUpdate();

                    }, function(error) {
                        notificationService.notify(cultureService.localize('picto.message.updateError'));
                    });

                };
                $scope.updateContent = function(updatePicto)
                {
                	for (var i = 0; i < $scope.items.length; i++)
                	{
                		if($scope.items[i].familyID.familyId === updatePicto.familyID.familyId)
                		{
                			$scope.items[i]=updatePicto;
                			$scope.selectedPictoIds.splice(0);                		    
                		    $scope.selectedPictoIds.push($scope.items[i].id);	            
                		    break;	
                		}
                	}
                }
                
                $scope.getListOfSubCategory = function(superCategory) {
                    var t = categoryService.getAllCategories(superCategory, function() {
                        $scope.listOfCategories = t;
                      }, function() {
                        $('#notifierDlg').modal('show');
                        $scope.notificationMessage = cultureService.localize('picto.message.categoryError');

                    });
                }
                $(window).resize(function() {
                    var mobileWidth = (window.innerWidth > 0) ? window.innerWidth : screen.width;
                    var viewport = (mobileWidth > 767) ? 'width=device-width, initial-scale=1.0' : 'width=1200, initial-scale=1.0';
                    $("meta[name=viewport]").attr('content', viewport);
                }).resize();
                
                
                $scope.pictoCentral = {};
                $scope.pictoCentral.favValue=0;
                $scope.pictoCentral.cartValue=0;
                

                /* Central area selection of favorite and cart pictos */                
                $scope.setFavCartValue = function()
                {
                	
                	var favcount=0;
                	var cartCount=0;
                	for (var i=0; i<$scope.selectedPictoObj.length; i++)
                	{
                				if($scope.selectedPictoObj[i].familyID.favoriteFlag)
                			{
                				favcount++;
                			}
                		if($scope.selectedPictoObj[i].cartFlag)
            			{
                			cartCount++;
            			}
                		
                	}
                	if(favcount === $scope.selectedPictoObj.length)
        				{
        					/* remove from favorire(-) */
        					$scope.pictoCentral.favValue=1;
        					
        				} 
                		else if (favcount ===0 )
        				{
                			/* Add to favorire(+) */
                			$scope.pictoCentral.favValue=0;
                			
        				}
        			     else
        				{
        			    	 /* Neutral(0) */                				
        			    	 $scope.pictoCentral.favValue=2;
        				}                	
                          	
                	if(cartCount === $scope.selectedPictoObj.length)
        			{
        				/* remove from favorire(-) */
        				$scope.pictoCentral.cartValue=1;
        			 } 
                	 else if (cartCount ===0 )
        			 {
        				/* Add to favorire(+) */
        				 $scope.pictoCentral.cartValue=0;
        			 }
        			 else
        			 {
        				 	/* Neutral(0) */                				
        			    	$scope.pictoCentral.cartValue=2;
        			 }
                                	
                }
                
                /* Cart are selection of favorite and cart pictos */
                $scope.pictoCart = {}
                $scope.pictoCart.favValueShop=0;
                
                $scope.setFavCartValueShop = function()
                {
                	
                	var favcount=0;
                	var cartCount=0;
                	for (var i=0; i<$scope.selectedCartPictoObj.length; i++)
                	{
                		  if($scope.selectedCartPictoObj[i].familyID.favoriteFlag)
                		  {
                				favcount++;
                		  }
                		  if($scope.selectedCartPictoObj[i].cartFlag)
            			  {
                			cartCount++;
            			  }
                		
                	}
                	if(favcount === $scope.selectedCartPictoObj.length)
        			{
        				/* remove from favorire(-) */
        				$scope.pictoCart.favValueShop=1;
        			} 
                	else if (favcount ===0 )
        			{
        				/* Add to favorire(+) */
        				 $scope.pictoCart.favValueShop=0;
        			}
        		    else
        			{
        				/* Neutral(0) */                				
        			    $scope.pictoCart.favValueShop=2;
        			}                	
                          	
                	                             	
                }
                
                /**
                 * ********************** End : Update the picto family **************************
                 */
                               
               
            
            }
            

    ]);
 
    

    // return angular Module

    return {
        angularModules : [
            'PictoManagement'
        ]
    };

});
