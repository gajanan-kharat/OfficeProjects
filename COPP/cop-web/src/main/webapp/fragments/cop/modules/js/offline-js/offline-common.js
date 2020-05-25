//Factory for indexedDb
offlineApp.factory('typeTestGroupIdb',[ '$window', '$q', function($window, $q){
    var indexedDB = $window.indexedDB;
    var db=null;
    var lastIndex=0;


    var open = function(){
        var deferred = $q.defer();
        var version = 1;
        var request = indexedDB.open("typeTestGroup", version);

        request.onupgradeneeded = function(e) {
            db = e.target.result;

            e.target.transaction.onerror = indexedDB.onerror;

            if(db.objectStoreNames.contains("typeTestGroup")) {
                db.deleteObjectStore("typeTestGroup");
            }

            var store = db.createObjectStore("typeTestGroup",
                    {keyPath: "entityId"});
        };

        request.onsuccess = function(e) {
            db = e.target.result;
            deferred.resolve();
        };

        request.onerror = function(){
            deferred.reject();
        };

        return deferred.promise;
    };


    var getPreparationFile = function(){
        var deferred = $q.defer();
        var offlineRepresentation = [];

        if(db === null){
            deferred.reject("IndexDB is not opened yet!");
        }
        else{
            var trans = db.transaction(["typeTestGroup"], "readwrite");
            var store = trans.objectStore("typeTestGroup");

            // Get everything in the store;
            var keyRange = IDBKeyRange.lowerBound(0);
            var cursorRequest = store.openCursor(keyRange);

            cursorRequest.onsuccess = function(e) {
                var result = e.target.result;
                if(result === null || result === undefined)
                {
                    deferred.resolve(offlineRepresentation);
                }
                else{
                    offlineRepresentation.push(result.value);
                    if(result.value.entityId > lastIndex){
                        lastIndex=result.value.entityId;
                    }
                    result.continue();
                }

            };

            cursorRequest.onerror = function(e){
                console.log(e.value);
                deferred.reject("Something went wrong!!!");
            };
        }

        return deferred.promise;

    };

    var addPreparationFile = function(typeTestGroup){
        var deferred = $q.defer();

        if(db === null){
            deferred.reject("IndexDB is not opened yet!");
        }
        else{
            var trans = db.transaction(["typeTestGroup"], "readwrite");
            var store = trans.objectStore("typeTestGroup");
            for(var i=0; i<typeTestGroup.length;i++){

                var request = store.put(typeTestGroup[i]);
            }

        }


        request.onsuccess = function(e) {
            deferred.resolve();

        };

        request.onerror = function(e) {
            console.log(e.value);
            deferred.reject("Todo item couldn't be added!");
        };


        return deferred.promise;
    };


    return {
        open: open,
        getPreparationFile: getPreparationFile,
        addPreparationFile: addPreparationFile
    };

}]);