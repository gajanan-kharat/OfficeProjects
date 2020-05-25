define([ 'require', 'module', '{angular}/angular' ], function(require, module, angular) {
    var pictoCommon = angular.module('PictoCommon', [ 'ngResource' ]), config = module && module.config() || {},

    getAllPictoApiUrl = config.apiUrl.replace('./', '') + '/picto/getAllPictos';

    getAllPictoFamilyApiUrl = config.apiUrl.replace('./', '') + '/pictoFamilyService/family/getFamilyInformation';

    getPictoInformationApiUrl = config.apiUrl.replace('./', '') + '/pictoFamily/getPictoInformation';

    downloadPictoUrl = config.apiUrl.replace('./', '') + '/picto/downloadPicto';

    updatePictoFamilyApiUrl = config.apiUrl.replace('./', '') + '/pictoFamily/updatePictoFamily';

    downloadAIWorkUrl = config.apiUrl.replace('./', '') + '/picto/downloadAIWork';

    validateAIWorkUrl = config.apiUrl.replace('./', '') + '/picto/validateAIWorkDownload';

    notificationContribUrl = config.apiUrl.replace('./', '') + '/picto/notificationContrib';

    notificationValidationUrl = config.apiUrl.replace('./', '') + '/picto/validateNotification';

    shoppingCartUrl = config.apiUrl.replace('./', '') + '/picto/shoppingCartItem';

    getAllSuperCategoryApiUrl = config.apiUrl.replace('./', '') + '/superCategory/getAllSuperCategory';

    getAllCategoriesUrl = config.apiUrl.replace('./', '') + '/picto/allCategory';
    getSuperCategoriesUrl = config.apiUrl.replace('./', '') + '/picto/getSuperCategory';

    /* For PDF Download */
    downloadPictoPdfUrl = config.apiUrl.replace('./', '') + '/pictoFamily/downloadPictoPDF';

    /* For Multiple Picto Download */
    downloadMultiPictoPdfUrl = config.apiUrl.replace('./', '') + '/pictoFamily/downloadMultiPictoPDF';

    createPictoFamilyApiUrl = config.apiUrl.replace('./', '') + '/pictoFamily/createPictoFamily';

    deletePictoFamilyApiUrl = config.apiUrl.replace('./', '') + '/pictoFamily/deletePictoFamily';

    favouritesUrl = config.apiUrl.replace('./', '') + '/picto/favouritesItem';

    getFilteredPictosUrl = config.apiUrl.replace('./', '') + '/picto/filterPicto';

    addFavoritesApiUrl = config.apiUrl.replace('./', '') + '/pictoFamily/';

    getTypesApiUrl = config.apiUrl.replace('./', '') + '/pictoFamily/getAllTypes';

    getAllCategoryApiUrl = config.apiUrl.replace('./', '') + '/superCategory/getCategory';
    
    getAllCategoryListApiUrl = config.apiUrl.replace('./', '') + '/superCategory/getCategoryList';
   
    getCartPictosUrl = config.apiUrl.replace('./', '') + '/picto/getPictosInCart';

    getValidationApiUrl = config.apiUrl.replace('./', '') + '/picto/validation';

    deletePictoVariantValidationUrl = config.apiUrl.replace('./', '') + '/picto/deletePictoVariantValidation';

    getAllInformationApiUrl = config.apiUrl.replace('./', '') + '/pictoFamily/getAllInformation';

    getVisbleApiUrl = config.apiUrl.replace('./', '') + '/picto/visible';

    /* For Displaying Search in English and French */
    searchTextUrl = config.apiUrl.replace('./', '') + '/picto/searchenteredText';
 
    deletePictoValidationApiUrl = config.apiUrl.replace('./', '') + '/pictoFamily/deletePictoValidation';

    thickClientInstallApiUrl = config.apiUrl.replace('./', '') + '/picto/thickClientInstall';

    getAllVariants=config.apiUrl.replace('./', '') + '/picto/getVariantsList';
    /* Picto service factory */

    pictoCommon.factory('PictoService', [ '$resource', '$log', function($resource, $log) {

        var GetAllPictos = $resource(getAllPictoApiUrl, {

            'get' : {
                method : 'GET',
                isArray : true
            }

        });

        var GetAllValidation = $resource(getValidationApiUrl, {
            'get' : {
                method : 'GET',
                isArray : true
            }
        });
        var GetAllVisible = $resource(getVisbleApiUrl, {
            'get' : {
                method : 'GET',
                isArray : true
            }
        });
        var doDeletePictoVariantValidation = $resource(deletePictoVariantValidationUrl, {
            pictoId : '@pictoId'
        }, {
            'save' : {
                method : 'POST',
                isArray : true
            }
        });
        var thickClientInstall = $resource(thickClientInstallApiUrl, {
            'get' : {
                method : 'GET',
                isArray : false
            }
        });

        return {
            getAllData : function(success, error) {
                return GetAllPictos.query(success, error);
            },
            getAllValidation : function(success, error) {
                return GetAllValidation.query(success, error);
            },
            getAllVisible : function(success, error) {
                return GetAllVisible.query(success, error);
            },
            doDeletePictoVariantValidation : function(_pictoId, success, error) {
                return doDeletePictoVariantValidation.save({
                    pictoId : _pictoId
                }, success, error);
            },
            thickClientInstall : function(success, error) {
                return thickClientInstall.query(success, error);
            },

        };
    } ]);
    /* Getting List of available variants for selected pictos */
    pictoCommon.factory('PictoVariantService', [ '$resource' ,
                                               function($resource) {
                                                   return {
                                                	   GetVariants : $resource('picto/:path', {
                                                           path : '@path'
                    },{
                                                           'getAvailableVariants' : {
                                                               method : 'POST',                    
                                                               params : {
                                                                   path : 'getVariantsList'
                                                                 },
                isArray : true
            }
                                                       })
        };
    } ]);
    
      
    /* Category service factory */
    pictoCommon.factory('CategoryService', ['$resource', '$log', function($resource, $log) {
        var GetAllCategories = $resource(getAllCategoriesUrl, {
            'get': {
                method: 'GET',
                isArray: true
            }
        });
        return {
            getAllCategories: function(success, error) {
                return GetAllCategories.query(success, error);
            },
        };
    }]);
    /* Category service factory */
    pictoCommon.factory('GetAllSuperCategoryService', [ '$resource', '$log', function($resource, $log) {
        var GetSuperCategories = $resource(getSuperCategoriesUrl, 
            {
            'get' : {
                method : 'GET',
                isArray : true
            }
        });
        return {
            getSuperCategories : function(success, error) {
                return GetSuperCategories.query(success, error);
            },
        };
    } ]);
    /* Picto Family service factory */
    pictoCommon.factory('PictoFamilyService', [ '$resource', '$log', function($resource, $log) {
        var getPictoInformation = $resource(getPictoInformationApiUrl, {
            refnum : '@refnum'
        }, {
            'get' : {
                method : 'GET',
                isArray : false
            }

        });
        var UpdatePictoFamily = $resource(updatePictoFamilyApiUrl, {
            'save' : {
                method : 'POST',
                isArray : true
            }
        });
        var CreatePictoFamily = $resource(createPictoFamilyApiUrl, {
            'save' : {
                method : 'POST',
                isArray : true
            }
        });
        var DeletePictoFamily = $resource(deletePictoFamilyApiUrl, {
            refnum : '@refnum'
        }, {
            'save' : {
                method : 'POST',
                isArray : false
            }
        });
        var GetAllTypes = $resource(getTypesApiUrl, {
            'get' : {
                method : 'GET',
                isArray : true
            }
        });

        var GetAllInformation = $resource(getAllInformationApiUrl, {
            'get' : {
                method : 'GET',
                isArray : true
            }
        });
        var DeletePictoFamilyValidation = $resource(deletePictoValidationApiUrl, {
            refnum : '@refnum'
        }, {
            'save' : {
                method : 'POST',
                isArray : true
            }
        });
        return {
            getPictoInformation : function(_refnum, success, error) {
                return getPictoInformation.get({
                    refnum : _refnum
                }, success, error);
            },
            updatePictoFamily : function(_pictoFamily, success, error) {
                return UpdatePictoFamily.save(_pictoFamily, success, error);
            },
            createPictoFamily : function(_pictoFamily, success, error) {
                return CreatePictoFamily.save(_pictoFamily, success, error);
            },
            deletePictoFamily : function(_refnum, success, error) {
                return DeletePictoFamily.save({
                    refnum : _refnum
                }, success, error);
            },
            allTypes : function(success, error) {
                return GetAllTypes.query(success, error);
            },
            getAllInformation : function(success, error) {
                return GetAllInformation.query(success, error);
            },
            deletePictoFamilyValidation : function(_refnum, success, error) {
                return DeletePictoFamilyValidation.save({
                    refnum : _refnum
                }, success, error);
            },

        };
    } ]);

    pictoCommon.factory('DownloadAIWorkService', [ '$resource', '$log', function($resource, $log) {
        var downloadAIWork = $resource(downloadAIWorkUrl, {

            pictoIds : '@pictoIds'

        }, {
            'get' : {
                method : 'GET',
                isArray : true
            }

        });
        return {
            downloadAIWork : function(_pictoIds, success, error) {
                return downloadAIWork.get({
                    pictoIds : _pictoIds
                }, success, error);
            },
        };
    } ]);

    pictoCommon.factory('ShoppingCartService', [ '$resource', '$log', function($resource, $log) {
        var shoppingCartItems = $resource(shoppingCartUrl, {

            pictoIds : '@pictoIds'

        }, {
            'get' : {
                method : 'GET',
                isArray : false
            }

        });
        return {
            shoppingCartItems : function(_pictoIds, success, error) {
                return shoppingCartItems.get({
                    pictoIds : _pictoIds
                }, success, error);
            },
        };
    } ]);


    
    pictoCommon.factory('CartService', [ '$resource',
    function($resource) {
        return {
            CartItems : $resource('picto/:path', {
                path : '@path'
            }, {

                'removeShop' : {
                    method : 'POST',
                    params : {
                        path : 'removeCartPicto'
                      },
                    isArray : false
                },
                'addShop' : {
                    method : 'POST',                    
                    params : {
                        path : 'addCartPicto'
                      },
                    isArray : true
                },
                'import' : {
                    method : 'POST',                    
                    params : {
                        path : 'importCart'
                      },
                    isArray : true
                }
            })

        };
    } ]);


    /* Favorite service factory */
    pictoCommon.factory('FavouritesService', [ '$resource',

    function($resource) {
        return {
            FavouritesItems : $resource('pictoFamily/:path', {
                path : '@path'
            }, {

                'saveFavorite' : {
                    method : 'POST',                    
                    params : {
                        path : 'addFavoritePicto'
                      },
                    isArray : true
                },  'removeFavorite' : {
                    method : 'POST',
                    params : {
                        path : 'removeFavoritePicto'
                      },
                    isArray : false
                }
            })
        };
    } ]);


    pictoCommon.factory('NotificatonContribService', [ '$resource', '$log', function($resource, $log) {
        var notificatonContrib = $resource(notificationContribUrl, {
            'get' : {
                method : 'GET',
                isArray : true
            }

        });
        return {

            notificatonContrib : function(success, error) {
                return notificatonContrib.query(success, error);
            },
        };
    } ]);

    pictoCommon.factory('PictosInCartService', [ '$resource', '$log', function($resource, $log) {
        var getPictosInCart = $resource(getCartPictosUrl, {
            'get' : {
                method : 'GET',
                isArray : true
            }

        });
        return {

            getPictosInCart : function(success, error) {
                return getPictosInCart.query(success, error);
            },
        };
    } ]);

    pictoCommon.factory('DownloadPictoService', [ '$resource',

    function($resource) {
        return {
            downloadPictos : $resource(downloadPictoUrl, {
                path : '@path'
            }, {

                'downloadPicto' : {
                    method : 'POST',
                    isArray : true
                }
            })
        };
    } ]);

    pictoCommon.factory('ValidateAIWorkService', [ '$resource',

    function($resource) {
        return {
            validateAIWork : $resource(validateAIWorkUrl, {
                path : '@path'
            }, {

                'validateAIWork' : {
                    method : 'POST',
                    isArray : false
                }
            })
        };
    } ]);

    pictoCommon.factory('FilterService', [ '$resource',

    function($resource) {
        return {
            pictoFilter : $resource(getFilteredPictosUrl, {
                path : '@path'
            }, {

                'pictoFilter' : {
                    method : 'POST',
                    isArray : true
                }
            })
        };
    } ]);

    /* Added for Download PDF */
    pictoCommon.factory('PictoFamilyPDFService', [ '$resource', '$log', function($resource, $log) {
        var downloadPictoPDF = $resource(downloadPictoPdfUrl, {
            refnum : '@refnum',
            language : '@language'

        }, {
            'get' : {
                method : 'GET',
                isArray : true
            }

        });
        return {
            downloadPictoPDF : function(_refnum, _name, _language, success, error) {
                return downloadPictoPDF.get({
                    refnum : _refnum,
                    language : _language
                }, success, error);
            },
        };
    } ]);

    /* End Added for PDF */

    /* Added for Multiple Download PDF */
    pictoCommon.factory('PictoFamilyMultiPDFService', [ '$resource', '$log', function($resource, $log) {
        var downloadMultiPictoPDF = $resource(downloadMultiPictoPdfUrl, {
            reflistnum : '@reflistnum',
            language : '@language'
        }, {
            'get' : {
                method : 'GET',
                isArray : true
            }

        });
        return {
            downloadMultiPictoPDF : function(_reflistnum, _language, success, error) {
                return downloadMultiPictoPDF.get({
                    reflistnum : _reflistnum,
                    language : _language
                }, success, error);
            },
        };
    } ]);

    /* End Added for Multiple PDF */

    pictoCommon.factory('ValidateNotificationService', [ '$resource', '$log', function($resource, $log) {
        var validateNotification = $resource(notificationValidationUrl, {

            familyId : '@familyId'

        }, {
            'get' : {
                method : 'GET',
                isArray : false
            }

        });
        return {

            validateNotification : function(_familyId, success, error) {
                return validateNotification.get({
                    familyId : _familyId
                }, success, error);
            },
        };
    } ]);

    /* Added for Search Text in English and French */

    pictoCommon.factory('SearchTextService', [ '$resource', '$log', function($resource, $log) {
        var searchenteredText = $resource(searchTextUrl, {
            searctext : '@searctext'
        }, {
            'get' : {
                method : 'GET',
                isArray : true
            }

        });
        return {
            searchenteredText : function(_searctext, success, error) {
                return searchenteredText.get({
                    searctext : _searctext
                }, success, error);
            },
        };
    } ]);

    /* End Added for Search Text in English and French */

    /* Super category Service factory */

    pictoCommon.factory('SuperCategoryService', [ '$resource', '$log', function($resource, $log) {
        var GetAllSuperCategory = $resource(getAllSuperCategoryApiUrl, {
            'get' : {
                method : 'GET',
                isArray : true
            }
        });
        var GetAllCategory = $resource(getAllCategoryApiUrl, {
            id : '@name',
        }, {
            'get' : {
                method : 'GET',
                isArray : true
            }
        });
        
        var GetAllCategoryList = $resource(getAllCategoryListApiUrl, {
            'get' : {
                method : 'GET',
                isArray : true
            }
        });

        return {
            getAllSuperCategory : function(success, error) {
                return GetAllSuperCategory.query(success, error);
            },
            getAllCategory : function(_supeCategoryName, success, error) {
                return GetAllCategory.query({
                   id : _supeCategoryName
                }, success, error);
            },
            getAllCategoryList : function(success, error) {
                return GetAllCategoryList.query(success, error);
            },
        };

    } ]);
});