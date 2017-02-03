var app = angular.module("EyeApp", ['ui.bootstrap']);

app.controller('MonitorCtrl', MonitorCtrl);
		
MonitorCtrl.$inject = ['$scope', '$http', '$compile', 'MonitorService', '$modal', '$timeout'];		
		
		
function MonitorCtrl ($scope, $http, $compile, MonitorService, $modal, $timeout){

	$scope.client = null;
	$scope.stompClient = null;
	$scope.ativo = true;
	$scope.sistemaLogado = "";
	$scope.listClients = [];
	
	$scope.model = {
		systemsList: []
	}

	$scope.html = function(valor){
		return $compile(valor);
	};

	$scope.options = {
		legend: false,
		responsive: false
	};

	$scope.backgroundColor = [		
		"#9B59B6",
		"#E74C3C",
		"#26B99A",
		"#3498DB",		
		"#2ad12d",
		"#8E44AD",
		"#F22613",
		"#cf2828",
		"#96281B",
		"#049ed9"
	];

	$scope.hoverBackgroundColor = [
		"#c6a0d5",
		"#f09188",
		"#74e2cb",
		"#7cbde8",
		"#7ce57e",
		"#c193d5",
		"#f77f74",
		"#e88686",
		"#e47466",
		"#7ad8fc"
	];
	
	/*
	 * ------------- ----------- código antigo websocket ----------
	 * -------------------
	 */
	function setConnected(connected) {
	    $("#connect").prop("disabled", connected);
	    $("#disconnect").prop("disabled", !connected);
	    if (connected) {
	        $("#conversation").show();
	    }
	    else {
	        $("#conversation").hide();
	    }
	    $("#greetings").html("");
	}
	
	function disconnect() {
	    if ($scope.stompClient != null) {
	        $scope.stompClient.disconnect();
	    }
	    setConnected(false);
	    console.log("Disconnected");
	}
	
	$scope.connect = function() {
	    $scope.socket = new SockJS('/eye');
	    $scope.stompClient = Stomp.over($scope.socket);
	    $scope.stompClient.connect({}, function (frame) {
	        setConnected(true);
	        console.log('Connected: ' + frame);
	        
	        $scope.stompClient.subscribe('/topic/feacture_number', function (item) {
	        	$scope.processarDados(item);
	        });
	        
	        $scope.stompClient.subscribe('/topic/versions_total', function (item) {
	        	$scope.processarDadosVersoesTotais(item);
	        });

			$scope.stompClient.subscribe('/topic/feactures_perc', function (item) {
				$scope.processarDadosFuncTotaisPerc(item);
			});

			$scope.stompClient.subscribe('/topic/softwares', function (item) {
				$scope.processarSistemas(item);
			});
			
			$scope.stompClient.subscribe('/topic/clients', function (item) {
				$scope.processarClientsWebSocket(item);
			});

			$http.get("refresh");

	    });
	};

	$scope.ativoLoad = function(){
		$http({
			method: 'GET',
			url: '/active'
		}).then(function successCallback(response) {
			$scope.ativo = response.data;
		}, function errorCallback(response) {
		});
	};
	
	$scope.ativar = function(valor){
		$http.get("active/" + (valor ? "S" : "N") );
	};

	$scope.trocarSistemas = function(sistema){
		$scope.sistemaLogado = sistema;
		$scope.recarregarTodos();
		
		$scope.getFeaturesBySystem();
		$scope.getClients();
	};

	$scope.recarregarTodos = function(){
		$scope.processarDadosExecute();
		$scope.processarDadosFuncTotaisPercExecute();
		$scope.processarDadosVersoesTotaisExecute();
		$scope.processarClients();
	};
	
	$scope.processarSistemas = function(node){
		var itens = JSON.parse(node.body);
		$scope.sistemas = itens;
		$scope.$apply();
	};

	$scope.processarDadosFuncTotaisPerc = function(node) {
		var itens = JSON.parse(node.body);
		$scope.listaItensFuncTotaisPerc = itens;
		$scope.processarDadosFuncTotaisPercExecute();
		$scope.$apply();
	};

	$scope.processarClientsWebSocket = function(node) {
		$scope.listaClientitens = JSON.parse(node.body);
		$scope.processarClients();				
	};
	
	$scope.processarClients = function() {
		if( $scope.sistemaLogado == ""){
			return;
		}
					
		if( $scope.listaClientitens == null || $scope.listaClientitens == undefined){
			return;
		}
	
		$scope.listaClient = $scope.listaClientitens[$scope.sistemaLogado];
	};
	
	
	$scope.processarDadosFuncTotaisPercExecute = function() {
		if( $scope.sistemaLogado == ""){
			return;
		}

		$scope.sistemasFuncTotaisPerc = Object.keys($scope.listaItensFuncTotaisPerc);
		$scope.funcTotaisPerc = $scope.listaItensFuncTotaisPerc[$scope.sistemaLogado].lista;

		$scope.funcTotaisPercSep = Object.keys($scope.funcTotaisPerc);

		var valores = [];

		var cont = 0;
		angular.forEach($scope.funcTotaisPerc, function(value, key) {
			valores.push(value.perc);
			value.cor = $scope.backgroundColor[cont];
			cont++;
		});

		$timeout(function(){
			new Chart(document.getElementById("graphDoughnut"), {
				type: 'pie',
				tooltipFillColor: "rgba(51, 51, 51, 0.55)",
				data: {
					labels: $scope.funcTotaisPercSep,
					datasets: [{
						data: valores,
						backgroundColor: $scope.backgroundColor,
						hoverBackgroundColor: $scope.hoverBackgroundColor,
						borderWidth: 1
					}]
				},
				options: $scope.options
			});
			
			new Chart(document.getElementById("graphBar"), {
				type: 'bar',
				tooltipFillColor: "rgba(51, 51, 51, 0.55)",
				data: {
					labels: $scope.listClients,
					datasets: [{
						data: $scope.listNumberOfFeaturesByClient,
						backgroundColor: $scope.backgroundColor,
						hoverBackgroundColor: $scope.hoverBackgroundColor,
						borderWidth: 1,
						label: "Funcionalidades mapeadas"
					}]
				},
				options: $scope.options
			});
		}, 800);
	};

	$scope.clicarErro = function(funcao, versao){
		$scope.listaErros = [];

		if( $scope.sistemaLogado == ""){
			return;
		}

		var funcs = $scope.listaItens[$scope.sistemaLogado].funcionalidade[funcao];

		angular.forEach(funcs, function(funcao, keyFuncao) {
			if( funcao.version == versao && funcao.error ){

				var messagest = funcao.messageStackTrace;
				if( messagest != undefined && messagest != null ){
					messagest = messagest.replace("\n", "<br>");
				}

				$scope.listaErros.push( {exception: funcao.exception, mensagem: funcao.messageError, messageStackTrace: messagest});
			}
		});

	};

	$scope.processarDadosVersoesTotais = function(node) {
		var itens = JSON.parse(node.body);
		$scope.listaItensVersoesTotais = itens;

		$scope.processarDadosVersoesTotaisExecute();
		$scope.$apply();
	};

	$scope.processarDadosVersoesTotaisExecute = function(){

		if( $scope.sistemaLogado == ""){
			return;
		}

		$scope.sistemasVersoes = Object.keys($scope.listaItensVersoesTotais);
		$scope.versoesTotais = $scope.listaItensVersoesTotais[$scope.sistemaLogado].lista;
		
		$scope.versoesTotaisSep = Object.keys($scope.versoesTotais);

		$scope.versoesTotaisSep.reverse();

	};
	
	$scope.processarDados = function(node) {

		var itens = JSON.parse(node.body);
		$scope.listaItens = itens;
		$scope.processarDadosExecute();
		$scope.$apply();
	};

	$scope.processarDadosExecute = function(){
		if( $scope.sistemaLogado == ""){
			return;
		}

		$scope.sistemas = Object.keys($scope.listaItens);
		$scope.versoes =  Object.keys($scope.listaItens[$scope.sistemaLogado].versoes);

		$scope.versoes.reverse();

		$scope.funcionalidades =  Object.keys($scope.listaItens[$scope.sistemaLogado].funcionalidade);

		$scope.listaGraficoExec = [];

		var funcs = $scope.listaItens[$scope.sistemaLogado].funcionalidade;

		angular.forEach(funcs, function(funcao, keyFuncao) {

			funcao.tempoTotal = 0;
			funcao.tempoCount = 0;

			angular.forEach(funcao, function(item, keyItem) {
		
				var calcular = false;
				if( $scope.client == null || $scope.client == undefined || $scope.client == "Todos"){
					calcular = true;
				}		
						
				if( $scope.client != null && $scope.client != undefined && $scope.client != "Todos" && $scope.client == item.client ){
					calcular = true;
				}
		
				if( calcular ){
					funcao.tempoCount++;
					funcao.tempoTotal += item.timeExec;
				}
			});

			funcao.tempoMedio = funcao.tempoTotal / funcao.tempoCount;

			funcao.tempoMedioSeconds = parseFloat((funcao.tempoMedio / 1000) % 60 ).toFixed(4);
			funcao.tempoMedioMinutes = parseFloat(((funcao.tempoMedio / (1000*60)) % 60) ).toFixed(4);
		});
	};
	
	/*
	 * ------------- ----------- código antigo websocket ----------
	 * -------------------
	 */
	
	$scope.loadData = function(){
		$scope.trocarSistemas($scope.sistemaLogado);
	};
	
	$scope.openDetails = function(item, itemVersao){
		
	    var defaultSettings = {
	        backdrop	: 'static',
	        keyboard	: true,
	        templateUrl	: 'eye-details-modal.html',
	        controller 	: 'ModalDetailsController',
	        size       	: 'lg',
	        backdrop   	: true,
	        resolve	   	: {
	        	params	: function(){
	        		return {
	        			actionObj		: $scope.featuresList[item],
	        			actionVersion	: itemVersao,
	        			system			: $scope.sistemaLogado
	        		};
	        	}
	        }
	    };

        var modalInstance = $modal.open(defaultSettings);

        modalInstance.result.then(resultFn).catch(resultFn);
        
        function resultFn() {
            console.log('//to do');
        }   
	}

	$scope.getSystems = function(){
		MonitorService.getSystems(
			function(response){
				$scope.model.systemsList = response;
				$scope.getClients();
				$scope.getFeatures();
			},
			function(error){
				console.log(error);
			}
		);
	}
	
	$scope.getClients = function(){
		if( $scope.sistemaLogado == ""){
			return;
		}
		
		MonitorService.getClients(
				$scope.sistemaLogado,
			function(response){
				$scope.listClients = response;
				
				$scope.listNumberOfFeaturesByClient = [];
				$scope.listObjClients = {};
				
				angular.forEach($scope.listClients, function(value, key){	
					$scope.listObjClients = {
						cliente: value,
						listFuncionalidade: []
					}
					
					MonitorService.getFeaturesAndPercentage($scope.sistemaLogado, value,
						function(response){
							Object.getOwnPropertyNames(response).forEach(function(val, idx, array) {
								$scope.listObjClients.listFuncionalidade.push(val);						
							});
							
							$scope.listNumberOfFeaturesByClient.push($scope.listObjClients.listFuncionalidade.length);
						},
						function(error){
							console.log(error);
						}
					)
				});
			},
			function(error){
				console.log(error);
			}
		);
	}
	
	$scope.getFeaturesAndPercentage = function(client){
		if( $scope.sistemaLogado == ""){
			return;
		}
		
		MonitorService.getFeaturesAndPercentage(
				$scope.sistemaLogado,
				client,
			function(response){
				console.log(response);
			},
			function(error){
				console.log(error);
			}
		)
	}
	
	$scope.getFeaturesBySystem = function(){
		MonitorService.getFeaturesBySystem(
				$scope.sistemaLogado,
			function(response){
				$scope.featuresList = response;	
			},
			function(error){
				console.log("Ocorreu um erro ao requisitar end-point "+error);
			}
		)
	}
	
	$scope.init = function(){
		$scope.ativoLoad();
		$scope.connect();
	};

	

	$scope.init();
}