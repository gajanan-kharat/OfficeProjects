/*
 * Copyright (c) PSA.
 */
define([
		'require', 'module', '{angular}/angular'
], function(require, module, angular) {

	var genomeData = angular.module('genomeData', [
			'ui.grid', 'ui.grid.selection', 'ui.grid.cellNav', 'ui.grid.edit'
	]);
	genomeData.factory('MoturService', [
			'$resource',

			function($resource) {
				return {
					MoteurResource : $resource('MoteurReference/:path', {
						path : '@path',

					}, {
						'getAllMoteurData' : {
							method : 'GET',
							params : {
								path : 'Moteurs'

							},
							isArray : true
						},
						'saveAllMoteurData' : {
							method : 'POST',
							params : {
								path : 'Moteurs'

							},

						},
						'deleteMoteurData' : {
							method : 'POST',
							params : {
								path : 'Moteur'
							}
						}
					})
				};
			}
	]);

	genomeData.factory('CarburantService', [
			'$resource',

			function($resource) {
				return {
					CarburantResource : $resource('FuelTypeReference/:path', {
						path : '@path',

					}, {
						'getAllCarburantData' : {
							method : 'GET',
							params : {
								path : 'Carburants'

							},
							isArray : true
						},
						'saveAllCarburantData' : {
							method : 'POST',
							params : {
								path : 'Carburants'

							},

						},
						'deleteCarburantData' : {
							method : 'POST',
							params : {
								path : 'Carburant'
							}
						},
					       'getAllFuelType' : {
                               method : 'Get',
                               params :{
                                      path : 'fuelTypes'
                               },
                               isArray : true
                        }

					})
				};
			}
	]);
	genomeData.factory('FamilleService', [
			'$resource',

			function($resource) {
				return {
					FamilleResource : $resource('FamilleReference/:path', {
						path : '@path',

					}, {
						'getAllFamilleData' : {
							method : 'GET',
							params : {
								path : 'Famillies'

							},
							isArray : true
						},
						'saveAllFamilleData' : {
							method : 'POST',
							params : {
								path : 'Famillies'

							},

						},
						'deleteFamilleData' : {
							method : 'POST',
							params : {
								path : 'Familly'
							}
						}
					})
				};
			}
	]);
	genomeData.factory('SilhouetteService', [
			'$resource',

			function($resource) {
				return {
					SilhouetteResource : $resource('SilhouetteReference/:path', {
						path : '@path',

					}, {
						'getAllSilhouetteData' : {
							method : 'GET',
							params : {
								path : 'Silhouettes'

							},
							isArray : true
						},
						'saveAllSilhouetteData' : {
							method : 'POST',
							params : {
								path : 'Silhouettes'

							},

						},
						'deleteSilhouetteData' : {
							method : 'POST',
							params : {
								path : 'Silhouette'
							}
						}
					})
				};
			}
	]);
	genomeData.factory('ConstructeurService', [
			'$resource',

			function($resource) {
				return {
					ConstructeurResource : $resource('ConstructeurReference/:path', {
						path : '@path',

					}, {
						'getAllConstructeurData' : {
							method : 'GET',
							params : {
								path : 'Constructeurs'

							},
							isArray : true
						},
						'saveAllConstructeurData' : {
							method : 'POST',
							params : {
								path : 'Constructeurs'

							},

						},
						'deleteConstructeurData' : {
							method : 'POST',
							params : {
								path : 'Constructeur'
							}
						}
					})
				};
			}
	]);
	genomeData.factory('UsineService', [
			'$resource',

			function($resource) {
				return {
					UsineResource : $resource('UsineReference/:path', {
						path : '@path',

					}, {
						'getAllUsineData' : {
							method : 'GET',
							params : {
								path : 'Factories'

							},
							isArray : true
						},
						'saveAllUsineData' : {
							method : 'POST',
							params : {
								path : 'Factories'

							},

						},
						'deleteUsineData' : {
							method : 'POST',
							params : {
								path : 'Factory'
							}
						}
					})
				};
			}
	]);
	genomeData.factory('PaysService', [
			'$resource',

			function($resource) {
				return {
					PaysResource : $resource('PaysReference/:path', {
						path : '@path',

					}, {
						'getAllPaysData' : {
							method : 'GET',
							params : {
								path : 'PaysList'

							},
							isArray : true
						},
						'saveAllPaysData' : {
							method : 'POST',
							params : {
								path : 'PaysList'
							}
						},
						'deletePaysData' : {
							method : 'POST',
							params : {
								path : 'Pays'
							}
						}
					})
				};
			}
	]);
	genomeData.factory('ReglementationService', [
			'$resource',

			function($resource) {
				return {
					ReglementationResource : $resource('emissionStandard/:path', {
						path : '@path',

					}, {
						'getAllReglementationData' : {
							method : 'GET',
							params : {
								path : 'ReglementationList'

							},
							isArray : true
						},
						'saveAllReglementationData' : {
							method : 'POST',
							params : {
								path : 'ReglementationList'
							}
						},
						'deleteReglementationData' : {
							method : 'POST',
							params : {
								path : 'Reglementation'
							}
						}
					})
				};
			}
	]);
	genomeData.factory('BolteService', [
			'$resource',

			function($resource) {
				return {
					BolteResource : $resource('BolteReference/:path', {
						path : '@path',

					}, {
						'getAllBolteData' : {
							method : 'GET',
							params : {
								path : 'BolteList'

							},
							isArray : true
						},
						'saveAllBolteData' : {
							method : 'POST',
							params : {
								path : 'BolteList'
							}
						},
						'deleteBolteData' : {
							method : 'POST',
							params : {
								path : 'Bolte'
							}
						}
					})
				};
			}
	]);
	genomeData.factory('FinalReductionService', [
			'$resource',

			function($resource) {
				return {
					FinalReductionResource : $resource('FinalReductionReference/:path', {
						path : '@path',

					}, {
						'getAllFinalReductionData' : {
							method : 'GET',
							params : {
								path : 'FinalReductionData'

							},
							isArray : true
						},
						'saveAllFinalReductionData' : {
							method : 'POST',
							params : {
								path : 'FinalReductionData'
							}
						},
						'deleteFinalReductionData' : {
							method : 'POST',
							params : {
								path : 'FinalReduction'
							}
						}
					})
				};
			}
	]);

	return {
		angularModules : [
			'genomeData'
		]
	};
});
