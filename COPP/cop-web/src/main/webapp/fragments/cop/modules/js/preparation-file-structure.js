define([
        'require', 'module', '{angular}/angular'
        ],function(require, module, angular){
    
    var preparationFileModule = angular.module('preparationFileStructure',[]);
    
    preparationFileModule.factory('pFStructureservice',['$resource',function($resource){
        
        return{
            pFStructureResource : $resource('pfStructure/:path',{
                path : '@path'
            },{
                'getGPCList' :{
                    method : 'GET',
                    params : {
                        path : 'preparationstructure'
                    }
                },
                'savePFS' :{
                    method : 'POST',
                    params : {
                        path : 'preparationstructure'
                    }
                }
            })
        }
        
    }]); 
    
    preparationFileModule.controller('PFSController',['$scope','pFStructureservice','NotificationService','CultureService','AuthorizationService','DataTypeService', function($scope,pFStructureservice, NotificationService, CultureService,authorizationService,DataTypeService){
       
        $scope.authorization = function() {
            return authorizationService.hasRole('seed-w20', 'POCMRole');
           
        };
        
        var loadGPCList = function(){
           
        pFStructureservice.pFStructureResource.getGPCList(function(response){
            $scope.pfsList = response;
            $scope.pfsListCompare = angular.copy($scope.pfsList);
           

        })
       }
       
       loadGPCList();
       
       $scope.showAddGPLModel = function(){
           $scope.Listlabel = '';
           $scope.ListDescription = '';
           $('#addListModal').modal('show');
           
       };
       
       $scope.addGPL = function(){
           if($scope.Listlabel !== ''){
               var labelCheck = true;
               if($scope.pfsList.preparationChecklists !== undefined){
                  
               
               for(var i=0; i<$scope.pfsList.preparationChecklists.length; i++){
                   if(($scope.Listlabel).toUpperCase() === ($scope.pfsList.preparationChecklists[i].label).toUpperCase()){
                       labelCheck =false;
                   }
               }
               }else{
                   $scope.pfsList = { 'preparationChecklists' : []};
               }
               if(labelCheck){
               $('#addListModal').modal('hide');
           $scope.pfsList.preparationChecklists.push({
               'label' : $scope.Listlabel,
               'description' : $scope.ListDescription,
               'enabled' : false,
               'preparationItems': []
           });
           }else{
        	   $scope.errorMsg = CultureService.localize('cop.preparationstructure.message.duplicate');
               $('#errorModel').modal('show');
           }
           }else{
        	   $scope.errorMsg = CultureService.localize('cop.coastdown.message.reuiredfield'); 
               $('#errorModel').modal('show');
           }
       };
       $scope.showDeleteGPL = function(index){
           $scope.GPLindex = index;
           $('#removeListModal').modal('show')
       };
       
       $scope.deleteGPL = function(){
           $scope.pfsList.preparationChecklists.splice($scope.GPLindex, 1);
       };
       
       $scope.showAddGPIModel = function(index){
           $scope.GPLindex = index;
           $scope.itemlabel = '';
           $scope.itemDescription = '';
           $('#addItemModal').modal('show');
       };
       
       $scope.addGPI = function(){
           if($scope.itemlabel !== ''){
               var labelCheck = true;
               for(var i=0; i<               $scope.pfsList.preparationChecklists[$scope.GPLindex].preparationItems.length; i++){
                   if(($scope.itemlabel).toUpperCase() === ($scope.pfsList.preparationChecklists[$scope.GPLindex].preparationItems[i].label).toUpperCase()){
                       labelCheck =false;
                   }
               }
               if(labelCheck){
               $('#addItemModal').modal('hide');
               
               $scope.pfsList.preparationChecklists[$scope.GPLindex].preparationItems.push({
                   'label' : $scope.itemlabel,
               'helpText' : $scope.itemDescription,
               'order' : null,
               'unit' : '',
               'authorizedComment' : false,
               'mandatory' : false
               })
               }else{
                   $scope.errorMsg = CultureService.localize('cop.preparationstructure.message.duplicate'); 
                   
                   $('#errorModel').modal('show');
               }
           }else{
        	   $scope.errorMsg = CultureService.localize('cop.coastdown.message.reuiredfield');
        	   $('#errorModel').modal('show');
           }
       };
       
       $scope.showDeleteGPI = function(gplIndex, gpiIndex){
           $scope.GPLindex = gplIndex;
           $scope.GPIindex = gpiIndex;
           $('#removeItemModal').modal('show');
       };
       
       $scope.deleteGPI = function(){
           $scope.pfsList.preparationChecklists[$scope.GPLindex].preparationItems.splice($scope.GPIindex, 1);
       };
       
       $scope.pfsSave = function(){
          var result = angular.equals($scope.pfsList, $scope.pfsListCompare);
           if(!result){
               var pfsObjCheck = true;
               for(var i=0; i<$scope.pfsList.preparationChecklists.length;i++){
                   if($scope.pfsList.preparationChecklists[i].order === null || $scope.pfsList.preparationChecklists[i].order === undefined){
    
                       pfsObjCheck = false;
                       $scope.errorMsg =  CultureService.localize('cop.coastdown.message.reuiredfield');
                       
                       break;
                   }else{                   
                   var count = 0;
                   for(var j=0; j<$scope.pfsList.preparationChecklists.length;j++){
                       
                       if(parseInt($scope.pfsList.preparationChecklists[i].order) === parseInt($scope.pfsList.preparationChecklists[j].order)){
                           if(count>0){
                               pfsObjCheck = false;
                               $scope.pfsList.preparationChecklists[j].order = null;
                               $scope.errorMsg =  CultureService.localize('cop.preparationstructure.message.duplicateOrderList') + $scope.pfsList.preparationChecklists[j].label;
                           break;
                           }
                           count++;
                       }
                       
                   };
                   if(!pfsObjCheck){
                	   break
                   }
                   
                   for(var k=0; k<$scope.pfsList.preparationChecklists[i].preparationItems.length; k++){
                       if($scope.pfsList.preparationChecklists[i].preparationItems[k].order !== null && $scope.pfsList.preparationChecklists[i].preparationItems[k].label !== '' && $scope.pfsList.preparationChecklists[i].preparationItems[k].dataType !== undefined){
                           var gpiCount = 0;
                           
                           for( var l=0; l<$scope.pfsList.preparationChecklists[i].preparationItems.length; l++){
                               if(parseInt($scope.pfsList.preparationChecklists[i].preparationItems[k].order) === parseInt($scope.pfsList.preparationChecklists[i].preparationItems[l].order) || ($scope.pfsList.preparationChecklists[i].preparationItems[k].label).toUpperCase() === ($scope.pfsList.preparationChecklists[i].preparationItems[l].label).toUpperCase()){
                            	   if(gpiCount>0){
                                       pfsObjCheck = false;
                                       $scope.pfsList.preparationChecklists[i].preparationItems[l].order = null;
                                       $scope.errorMsg = CultureService.localize('cop.preparationstructure.message.duplicateOrder') + $scope.pfsList.preparationChecklists[i].preparationItems[l].label;
                                   break;
                                   }
                                   gpiCount++;  
                               }
                           }
                       if(!pfsObjCheck){
                    	   break
                       }
                       }else{
                           pfsObjCheck = false;
                           $scope.errorMsg = CultureService.localize('cop.coastdown.message.reuiredfield');
                           break;
                       }
                   }
                 }
               };
              if(pfsObjCheck){
                  pFStructureservice.pFStructureResource.savePFS($scope.pfsList,function(){
                      NotificationService.notify(CultureService.localize('cop.preparationstructure.message.saveSuccess'))
                      
                      loadGPCList();
                  },function(){
                     
                  })
              }else{
            	  $('#errorModel').modal('show');
              };
              
              
           }
       };
       $scope.pfsCancel = function(){
           loadGPCList();
       };
       
       /* validation for number */
		$scope.numValidation = function(value){
			var INTEGER_REG = new RegExp("^[-]?[0-9]+$");
			if(!INTEGER_REG.test(value)){
				value = value.substring(0,value.length - 1);
			}
			return value;
		};
       
    /*** to load data type ***/
       DataTypeService.dataTypeResource.getAllDataTypesforPFS(function(response){
    	   $scope.PIdatatypeList = response
       },function(){
    	   
       });
     
    }]);
    
    return{
        angularModules : ['preparationFileStructure']
    };
});