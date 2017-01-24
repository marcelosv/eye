var app = angular.module("myApp", []);

app.controller("myCtrl", function($scope, $http, $compile){

			$scope.client = null;
			$scope.stompClient = null;
			$scope.sistemaLogado = "";

			$scope.html = function(valor){
				return $compile(valor);
			};

			$scope.options = {
				legend: false,
				responsive: false
			};

			$scope.backgroundColor = [
				"#BDC3C7",
				"#9B59B6",
				"#E74C3C",
				"#26B99A",
				"#3498DB",
				"#8E44AD",
				"#9B59B6",
				"#F22613",
				"#E26A6A",
				"#96281B"
			];

			$scope.hoverBackgroundColor = [
				"#CFD4D8",
				"#B370CF",
				"#E95E4F",
				"#36CAAB",
				"#49A9EA",
				"#9B59B6",
				"#F22613",
				"#E26A6A",
				"#96281B",
				"#9B59B6"
			];

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

			$scope.ativo = true;

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

			$scope.selecionarClient = function(){
				$scope.recarregarTodos();
			}
			
			$scope.trocarSistemas = function(sistema){
				$scope.sistemaLogado = sistema;
				$scope.recarregarTodos();
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

				new Chart(document.getElementById("canvas1"), {
					type: 'doughnut',
					tooltipFillColor: "rgba(51, 51, 51, 0.55)",
					data: {
						labels: $scope.funcTotaisPercSep,
						datasets: [{
							data: valores,
							backgroundColor: $scope.backgroundColor,
							hoverBackgroundColor: $scope.hoverBackgroundColor
						}]
					},
					options: $scope.options
				});

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

			function disconnect() {
			    if ($scope.stompClient != null) {
			        $scope.stompClient.disconnect();
			    }
			    setConnected(false);
			    console.log("Disconnected");
			}
			
			$scope.ativoLoad();
			$scope.connect();
});