var offlineApp = angular.module('offlineApp',['ngRoute']);



offlineApp.config(function($routeProvider){
   
    $routeProvider
    .when('/',{
        templateUrl:'../../views/offline-views/offline-basket.html',
       // controller:'offlineBasketController'
    })
    .when('/list',{
        templateUrl:'../../views/offline-views/offline-menulist.html',
       // controller:'offlineMenuListController'
    });
});

offlineApp.controller('homeCtrl', [ '$scope','$window','typeTestGroupIdb', function($scope,$window,typeTestGroupIdb){

	$scope.slideToggle = function() {

		$("#wrapper").toggleClass("toggled");
		$(".slide-resize").toggleClass("col-lg-10");
		$("#menu-toggle").toggleClass('fa-chevron-right');
	};
	

	//function for button to check network status
	$scope.checkNetwork = function(){
	    if (navigator.onLine) {
	        alert("You are online now.");
	        $window.location.href = '../../../../#!/';
	    } else {
	        $('#offlineMessage').modal('show');
	    }

	};
	
	
    $scope.preparationFileCaching = function(){
        
        typeTestGroupIdb.getPreparationFile().then(function(data){
            $scope.offlineRepresentationData=data;
        }, function(err){
            $window.alert(err);
        });
    };
    

  
    $scope.refreshList = function(){
        $scope.preparationFileCaching();
        
        typeTestGroupIdb.getPreparationFile().then(function(data){
            
            
        }, function(err){
            $window.alert(err);
        });

    };

    $scope.init = function(){
        
        typeTestGroupIdb.open().then(function(){
            $scope.refreshList();
        });
       

    }
     
     $scope.init();

}]);

