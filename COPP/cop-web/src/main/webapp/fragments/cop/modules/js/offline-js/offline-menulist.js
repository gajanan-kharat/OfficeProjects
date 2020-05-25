

offlineApp.controller('offlineMenuListController', [ '$scope','$window','$routeParams', function($scope,$window,$routeParams){

    
    /**
     * Checks a value is not null and not empty
     */
    $scope.isNotEmptyisNotNull = function(_param) {
        if (null === _param || "" === _param) {
            return false;
        }
        return true;
    };
    //-------- check for parameter passed in url
    $scope.checkforRoutes = function() {

        if ($scope.isNotEmptyisNotNull($routeParams.vehicleFileId) && $routeParams.vehicleFileId !== undefined) {
            $scope.vehicleFileId = $routeParams.vehicleFileId;
         //   alert($routeParams.vehicleFileId);
        }
        $scope.inBasket = $routeParams.inBasket
    };

    $scope.checkforRoutes();
//  --------------Vehicle search ends---------

}]);



