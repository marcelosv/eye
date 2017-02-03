angular.module("EyeApp")

.factory('MonitorService', MonitorService);

MonitorService.$inject = ['$http', '$q'];

function MonitorService($http, $q){

	
	function getData(successFn, errorFn){
		var deferred = $q.defer();
		
	    $http({
	        method: "get",
	        url: '/receive'
	    })
	    .success(function(response){
	    	successFn(response);
	    })
	    .error(function(error){
	    	errorFn(error);
	    });
	}
	
	function getSystems(successFn, errorFn){
		$http({
	        method: "get",
	        url: '/v2/softwares'
	    })
	    .success(function(response){
	    	successFn(response);
	    })
	    .error(function(error){
	    	errorFn(error);
	    });
	}
	
	function getClients(system, successFn, errorFn){
		if(system == null){
			return; 
		}
		
	    $http({
	        method: "get",
	        url: '/v2/clients/'+system
	    })
	    .success(function(response){
	    	successFn(response);
	    })
	    .error(function(error){
	    	errorFn(error);
	    });
	}
	
	function getFeaturesAndPercentage(system, client, successFn, errorFn){
		if(system == null){
			return; 
		}
		
		$http({
	        method: "get",
	        url: '/v2/featuresandpercentage/' + system + "?client=" +client
	    })
	    .success(function(response){
	    	successFn(response);
	    })
	    .error(function(error){
	    	errorFn(error);
	    });
	}
	
	function getFeaturesBySystem(system, successFn, errorFn){
		if(system == null){
			return; 
		}
		
		$http({
	        method: "get",
	        url: '/v2/features/' + system
	    })
	    .success(function(response){
	    	successFn(response);
	    })
	    .error(function(error){
	    	errorFn(error);
	    });
	}
    
	return {
		getData: getData,
		getSystems: getSystems,
		getClients: getClients,
		getFeaturesAndPercentage: getFeaturesAndPercentage,
		getFeaturesBySystem: getFeaturesBySystem
	}
}
