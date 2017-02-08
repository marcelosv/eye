var app = angular.module("EyeApp");

app.controller('ModalDetailsController', ModalDetailsController);

ModalDetailsController.$inject = [ '$scope', '$modalInstance', 'params', '$timeout'];

function ModalDetailsController($scope, $modalInstance, params, $timeout) {

	$scope.action = params.actionObj;
	$scope.versionAction = params.actionVersion;
	$scope.system = params.system;
	$scope.exceptionsList = [];
	
	console.log($scope.action);
	
	$scope.buildGraphLine = function(){
		$scope.optionsGraph = {
			legend: true,
			responsive: true,
		    maintainAspectRatio: true,
		    scales: {
	            xAxes: [{
	                display: false
	            }]
	        }
		};
		
		var labels = [];
		
		angular.forEach($scope.action.timerList, function(){
			labels.push("Tempo de execução (seg)")
		});
		
		new Chart(document.getElementById("graphLineDetails"), {
			type: 'line',
			tooltipFillColor: "rgba(51, 51, 51, 0.55)",
			data: {
				labels: labels,
				datasets: [{
					data: $scope.action.timerList,
					label: "Total",
		            fill: true,
		            lineTension: 0.1,
		            backgroundColor: "#93c8ec",
		            borderColor: "#3498DB",
		            borderWidth: 2,
		           
		            pointRadius: 3,		            
		            pointBorderColor: "#73879C",
		            pointBackgroundColor: "#6B6D6E",
		            pointBorderWidth: 1,
		            
		            pointHoverRadius: 5,
		            pointHoverBorderColor: "#73879C",
		            pointHoverBackgroundColor: "#6B6D6E",
		            pointHoverBorderWidth: 1,
		            spanGaps: true
				}]
			},
			options: $scope.optionsGraph
		});
	}
	
	$scope.buildGraphBar = function (){
		$scope.optionsGraph = {
			legend: true,
			responsive: true,
		    maintainAspectRatio: true,
		    scales: {
	            xAxes: [{
	                display: true,
	                ticks: {
	                    beginAtZero:true
	                }
	            }]
	        }
		};
		
		new Chart(document.getElementById("graphBarDetails"), {
			type: 'horizontalBar',					
			tooltipFillColor: "rgba(51, 51, 51, 0.55)",
			data: {
				labels: ["Acessos", "Erros"],
				datasets: [{
					data: [$scope.action.featuresAndNoVersion[$scope.versionAction].accessNumber, $scope.action.featuresAndNoVersion[$scope.versionAction].errorNumber],
					label: "Quantidade",
		            fill: true,
		            backgroundColor: "#93c8ec",
		            borderColor: "#3498DB",
		            borderWidth: 2,
		          
		            spanGaps: true
				}]
			},
			options: $scope.optionsGraph
		});
	}
	
	$scope.createListExceptions = function(){
		if($scope.action.featuresAndNoVersion[$scope.versionAction].errors.length > 0){
			angular.forEach($scope.action.featuresAndNoVersion[$scope.versionAction].errors, function(value, key){
				var exception = {
					key: key,
					description: value
				}
				
				$scope.exceptionsList.push(exception);
			});
		}
	}
	
	$scope.ok = function() {
		$modalInstance.close();
	};
	
	$scope.init = function(){
		$timeout($scope.buildGraphLine, 800);
		
		$timeout($scope.buildGraphBar, 800);
		
		$scope.createListExceptions();
	}
	
	$scope.init();
}