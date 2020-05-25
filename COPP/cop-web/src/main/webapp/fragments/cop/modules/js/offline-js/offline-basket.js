//event listeners to notify a change in online status
window.addEventListener("online", function() {
    console.log("Online");
}, true);
window.addEventListener("offline", function() {
    console.log("Offline");
}, true);

offlineApp.controller('offlineBasketController', [ '$scope','$window','typeTestGroupIdb', function($scope,$window,typeTestGroupIdb){


    $scope.dblClickVehicleFile = function(basket){
        window.location='#list?vehicleFileId='+basket.entityId+'&&inBasket=true';
    }


    //CRUD operations
    $scope.preparationFileCaching = function(){

        typeTestGroupIdb.getPreparationFile().then(function(data){
            $scope.offlineRepresentationData=data;
            $scope.offlineVehicleFileList= $scope.offlineRepresentationData[0].vehicleFileRepList;
            $scope.allVehicleInBasketCount = $scope.offlineRepresentationData[0].vehicleFileRepList.length;
            $scope.typeTestGroup = [];
            for(var i=0; i<$scope.offlineVehicleFileList.length;i++){
                var typeTestCheck = true;
                for(var j=0; j<$scope.typeTestGroup.length;j++){
                    if($scope.typeTestGroup[j].typeOfTestLabel=== $scope.offlineVehicleFileList[i].typeOfTestLabel){
                        $scope.typeTestGroup[j].basketDetail.push($scope.offlineVehicleFileList[i]);
                        typeTestCheck = false;
                        break;
                    }
                }
                if(typeTestCheck) {
                    var temp = {typeOfTestLabel:$scope.offlineVehicleFileList[i].typeOfTestLabel,
                            basketDetail :[
                                           $scope.offlineVehicleFileList[i]
                                           ]
                    };
                    $scope.typeTestGroup.push(temp);
                }
            }
        }, function(err){
            $window.alert(err);
        });
      typeTestGroupIdb.addPreparationFile($scope.vehicleData).then(function(){
     }, function(err){
      $window.alert(err);

      });
    };



    $scope.refreshList = function(){
        $scope.preparationFileCaching();

        typeTestGroupIdb.getPreparationFile().then(function(data){

            //     $scope.typeTestGroup=data;


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


    $scope.typeTestGroup = $scope.offlineRepresentationData;
    //function for button to check network status
    $scope.checkNetwork = function(){
        if (navigator.onLine) {
            $window.location.href = '../../../../#!/';
        } else {
            $('#offlineMessage').modal('show');
        }

    };

    $scope.listSelected = function(){
        $window.location.href = '#list';
    }


}]);