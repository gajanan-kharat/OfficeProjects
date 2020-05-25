define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {
	var specificCopModule = angular.module('specificCopModule', [
			'ui.grid', 'ui.grid.selection', 'ui.grid.cellNav', 'ui.grid.edit'
	]);
	/* Class Factory */
	specificCopModule.factory('ClaszService', [
			'$resource',

			function($resource) {
				return {
					ClaszResource : $resource('ClaszReference/:path/:entityId', {
						path : '@path',
						entityId : '@entityId'

					}, {
						'getAllClasz' : {
							method : 'GET',
							params : {
								path : 'AllClasz'
							},
							isArray : true
						},

						'saveClasz' : {
							method : 'POST',
							params : {
								path : 'Classes'
							},
						},

						'deleteClasz' : {
							method : 'DELETE',
							params : {
								path : 'Clasz'
							},
						},

					})
				};
			}
	]);
	/* Pollutant Factory */
	specificCopModule.factory('PollutantService', [
			'$resource',

			function($resource) {
				return {
					PollutantResource : $resource('pollutantReference/:path/:entityId', {
						path : '@path',
						entityId : '@entityId'

					}, {
						'getAllPollutantData' : {
							method : 'GET',
							params : {
								path : 'AllPollutantLables'
							},
							isArray : true
						},

						'savePollutant' : {
							method : 'POST',
							params : {
								path : 'PollutantLables'
							},

						},

						'deletePollutant' : {
							method : 'POST',
							params : {
								path : 'PollutantLabel'
							},

						},

					})
				};
			}
	]);
	/* Fuel Injection Type Factory */
	specificCopModule.factory('FuelInjectionTypeService', [
			'$resource',

			function($resource) {
				return {
					FuelInjectionTypeResource : $resource('fuelInjectTypeReference/:path/:entityId', {
						path : '@path',
						entityId : '@entityId'

					}, {
						'getFuelInjectionType' : {
							method : 'GET',
							params : {
								path : 'AllFuelInjectionTypes'
							},
							isArray : true
						},

						'saveFuelInjectionType' : {
							method : 'POST',
							params : {
								path : 'FuelInjectionTypes'
							},

						},

						'deleteFuelInjectionType' : {
							method : 'POST',
							params : {
								path : 'FuelInjectionType'
							},

						},

					})
				};
			}
	]);

	/* Vehicle Technology Factory */
	specificCopModule.factory('VehicleTechnologyService', [
			'$resource',

			function($resource) {
				return {
					VehicleTechnologyResource : $resource('vehicleTechnologyReference/:path/:entityId', {
						path : '@path',
						entityId : '@entityId'

					}, {
						'getAllVehicleTechnology' : {
							method : 'GET',
							params : {
								path : 'AllVehicleTechnologies'
							},
							isArray : true
						},

						'saveVehicleTechnology' : {
							method : 'POST',
							params : {
								path : 'VehicleTechnologies'
							},

						},

						'deleteVehicleTechnology' : {
							method : 'POST',
							params : {
								path : 'VehicleTechnology'
							},

						},
					})
				};
			}
	]);

	/* Type Approval Area Factory */
	specificCopModule.factory('TypeApprovalAreaService', [
			'$resource',

			function($resource) {
				return {
					TypeApprovalAreaResource : $resource('typeApprovalAreaReference/:path/:entityId', {
						path : '@path',
						entityId : '@entityId'

					}, {
						'getAllTypeApprovalArea' : {
							method : 'GET',
							params : {
								path : 'AllTypeApprovalAreas'
							},
							isArray : true
						},

						'saveTypeApprovalArea' : {
							method : 'POST',
							params : {
								path : 'TypeApprovalAreas'
							},

						},

						'deleteTypeApprovalArea' : {
							method : 'POST',
							params : {
								path : 'TypeApprovalArea'
							},

						},

						'getAllTypeApprovalAreaForRG' : {
							method : 'GET',
							params : {
								path : 'TypeApprovalAreasForRG'
							},
							isArray : true
						},

					})
				};
			}
	]);

	/* factor Coeff Application Type Factory */
	specificCopModule.factory('FactorCoeffApplicationTypeService', [
			'$resource',

			function($resource) {
				return {
					FactorCoeffApplicationTypeResource : $resource('factorCoeffApplicationTypeReference/:path/:entityId', {
						path : '@path',
						entityId : '@entityId'

					}, {
						'getAllFactorCoeffApplicationType' : {
							method : 'GET',
							params : {
								path : 'AllFactorCoeffApplicationTypes'
							},
							isArray : true
						},

						'saveFactorCoeffAppType' : {
							method : 'POST',
							params : {
								path : 'FactorCoeffAppTypes'
							},

						},

						'deleteFactorCoeffAppType' : {
							method : 'POST',
							params : {
								path : 'FactorCoeffAppType'
							},

						},
					})
				};
			}
	]);

	/* Vehicle Coeff Factory */
	specificCopModule.factory('VehicleCoefficentsService', [
			'$resource',

			function($resource) {
				return {
					VehicleCoefficentsResource : $resource('vehicleCoefficentsReference/:path/:entityId', {
						path : '@path',
						entityId : '@entityId'

					}, {
						'getAllVehicleCoefficents' : {
							method : 'GET',
							params : {
								path : 'AllVehicleCoefficents'
							},
							isArray : true
						},

						'saveVehicleCoefficents' : {
							method : 'POST',
							params : {
								path : 'VehicleCoefficents'
							},

						},

						'deleteVehicleCoefficents' : {
							method : 'POST',
							params : {
								path : 'VehicleCoefficent'
							},

						},

					})
				};
			}
	]);

	/* Fuel Factory */
	specificCopModule.factory('FuelService', [
			'$resource',

			function($resource) {
				return {
					FuelResource : $resource('fuelReference/:path/:entityId', {
						path : '@path',
						entityId : '@entityId'

					}, {
						'getAllFuel' : {
							method : 'GET',
							params : {
								path : 'AllFuels'
							},
							isArray : true
						},

						'saveFuel' : {
							method : 'POST',
							params : {
								path : 'Fuels'
							},

						},

						'deleteFuel' : {
							method : 'POST',
							params : {
								path : 'deleteFuel'
							},

						},
					})
				};
			}
	]);

	/* Category Factory */
	specificCopModule.factory('CategoryService', [
			'$resource',

			function($resource) {
				return {
					CategoryResource : $resource('categoryReference/:path/:entityId', {
						path : '@path',
						entityId : '@entityId'

					}, {
						'getAllCategory' : {
							method : 'GET',
							params : {
								path : 'CategoryList'
							},
							isArray : true
						},

						'saveCategory' : {
							method : 'POST',
							params : {
								path : 'Categories'
							},

						},

						'deleteCategory' : {
							method : 'POST',
							params : {
								path : 'Category'
							},

						},
					})
				};
			}
	]);
	
	specificCopModule.factory('CategoryClaszService', [
	                                  			'$resource',

	                                  			function($resource) {
	return {
	                                  					CategoryResource : $resource('categoryReference/:path/:entityId/:claszLabel', {
	                                  						path : '@path',
	                                  						entityId : '@entityId',
	                                  						claszLabel:'@claszLabel'

	                                  					}, {
	                                  						'deleteCategory' : {
	                                  							method : 'POST',
	                                  							params : {
	                                  								path : 'Category'
	                                  							},

	                                  						},
	                                  					})
	                                  				};
	                                  			}
	                                  	]);
    /* TireBrand Factory */
    specificCopModule.factory('TireBrandService', [ '$resource', function($resource) {
        return {
            TireBrandResource : $resource('tireBrandReference/:path/:entityId', {
                path : '@path',
                entityId : '@entityId'

            }, {
                'getAllTireBrands' : {
                    method : 'GET',
                    params : {
                        path : 'AllTireBrands'
                    },
                    isArray : true
                },

                'saveTireBrand' : {
                    method : 'POST',
                    params : {
                        path : 'TireBrands'
                    },

                },

                'deleteTireBrand' : {
                    method : 'POST',
                    params : {
                        path : 'TireBrand'
                    },

                },
            })
        };
    } ]);
    
    
    
    /* LambdaDecisionParameter Factory */
    specificCopModule.factory('LambdaDecisionParametersService', [ '$resource', function($resource) {
        return {
            LambdaDecisionParametersResource : $resource('LambdaDecisionParametersReference/:path/:entityId', {
                path : '@path',
                entityId : '@entityId'

            }, {'getAllLambdaDecisionParameters' : {
                    method : 'GET',
                    params : {
                        path : 'AllLambdaDecisionParameters'
                    },
                    isArray : true
                },

                'saveLambdaDecisionParameters' : {
                    method : 'POST',
                    params : {
                        path : 'LambdaDecisionParameters'
                    },

                },

                'deleteLambdaDecisionParameters' : {
                    method : 'POST',
                    params : {
                        path : 'LambdaDecisionParameter'
                    },

                },
            

                
            })
        };
    } ]);
    
    

    /* OpacityDecisionParameter Factory */
    specificCopModule.factory('OpacityDecisionParametersService', [ '$resource', function($resource) {
        return {
            OpacityDecisionParametersResource : $resource('OpacityDecisionParametersReference/:path/:entityId', {
                path : '@path',
                entityId : '@entityId'

            }, {'getAllOpacityDecisionParameters' : {
                    method : 'GET',
                    params : {
                        path : 'AllOpacityDecisionParameters'
                    },
                    isArray : true
                },

                'saveOpacityDecisionParameters' : {
                    method : 'POST',
                    params : {
                        path : 'OpacityDecisionParameters'
                    },

                },

                'deleteOpacityDecisionParameters' : {
                    method : 'POST',
                    params : {
                        path : 'OpacityDecisionParameter'
                    },

                },
            

                
            })
        };
    } ]);
    
    
    /* CODecisionParameter Factory */
    specificCopModule.factory('CODecisionParametersService', [ '$resource', function($resource) {
        return {
            CODecisionParametersResource : $resource('CODecisionParametersReference/:path/:entityId', {
                path : '@path',
                entityId : '@entityId'

            }, {'getAllCODecisionParameters' : {
                    method : 'GET',
                    params : {
                        path : 'AllCODecisionParameters'
                    },
                    isArray : true
                },

                'saveCODecisionParameters' : {
                    method : 'POST',
                    params : {
                        path : 'CODecisionParameters'
                    },

                },

                'deleteCODecisionParameters' : {
                    method : 'POST',
                    params : {
                        path : 'TireBrand'
                    },

                },
            

                
            })
        };
    } ]);

    
    /* CODecisionParameter Factory */
    specificCopModule.factory('CODecisionParametersService', [ '$resource', function($resource) {
        return {
            CODecisionParametersResource : $resource('CODecisionParametersReference/:path/:entityId', {
                path : '@path',
                entityId : '@entityId'

            }, {'getAllCODecisionParameters' : {
                    method : 'GET',
                    params : {
                        path : 'AllCODecisionParameters'
                    },
                    isArray : true
                },

                'saveCODecisionParameters' : {
                    method : 'POST',
                    params : {
                        path : 'CODecisionParameters'
                    },

                },

                'deleteCODecisionParameters' : {
                    method : 'POST',
                    params : {
                        path : 'CODecisionParameter'
                    },

                },
            

                
            })
        };
    } ]);
    
    
    /* TeamUser Factory */
    specificCopModule.factory('TeamUsersService', [ '$resource', function($resource) {
        return {
            TeamUsersResource : $resource('TeamUsersReference/:path/:entityId', {
                path : '@path',
                entityId : '@entityId'

            }, {'getAllTeamUsers' : {
                    method : 'GET',
                    params : {
                        path : 'AllTeamUsers'
                    },
                    isArray : true
                              
            },
            
            'saveTeamUsers' : {
                method : 'POST',
                params : {
                    path : 'TeamUsers'
                },

            },

            'deleteTeamUsers' : {
                method : 'POST',
                params : {
                    path : 'TeamUser'
                },

            },
        

            
        })
    };
} ]);
    
    /* Team Factory */
    specificCopModule.factory('TeamService', [ '$resource', function($resource) {
        return {
            TeamResource : $resource('TeamReference/:path/:entityId', {
                path : '@path',
                entityId : '@entityId'

            }, {'getAllTeam' : {
                    method : 'GET',
                    params : {
                        path : 'AllTeams'
                    },
                    isArray : true
                }
            

                
            })
        };
    } ]);
    
    
    
    
    /*ParametersConstants*/
    specificCopModule.factory('ParametersConstantsService', [ '$resource', function($resource) {
        return {
        	ParametersConstantsResource : $resource('ParametersConstantsReference/:path/:entityId', {
                path : '@path',
                	  entityId : '@entityId'

            }, {'getAllParametersConstants' : {
                    method : 'GET',
                    params : {
                        path : 'dataTypes'
                    },
                    isArray : true
                }
            })
        };
    }  
    ]);
    
    
    
    return {
        angularModules : [ 'specificCopModule' ]
    };

});

