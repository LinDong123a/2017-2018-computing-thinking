angular.module('app.controllers', [])

.controller('gameLobbyCtrl', ['$scope','$rootScope','$stateParams', '$ionicModal','LobbyService',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($rootScope,$scope, $stateParams,$ionicModal,LobbyService) {
  //FIXME:$rootScope not working.
  // $rootScope.gamerIds = [];
	// Load the modal from the given template URL
    $ionicModal.fromTemplateUrl('templates/modal——settings.html', function($ionicModal) {
      $scope.modal_settings = $ionicModal;
      // console.log("$scope.modal_game_lobby:",$scope.modal_game_lobby);
    }, {
        // Use our scope for the scope of the modal to keep it simple
        scope: $scope,
        // The animation we want to use for the modal entrance
        animation: 'slide-in-up'
    });

  $scope.pairAll = function () {
    LobbyService.pairAll(function(data){
      console.log("LobbyService.getAll(:",  data);
      $scope.lobbyList  = data;
      console.log("$scope.lobbyList:",  $scope.lobbyList);
    });
  }
  $scope.playAll = function(){
    console.log("game start!:");
    LobbyService.playAll(function(data){
      console.log("LobbyService.playAll:",  data);
      for(var i=0;i<data.length;i++){
        LobbyService.gamerIds.push(data[i].id);
      }
      console.log("LobbyService.gamerIds:",LobbyService.gamerIds);
    });
  }
  $scope.createUser = function(){
  //TODO:
  }
  $scope.dismissAll = function(){
    LobbyService.dismissAll(function(data){
      console.log("LobbyService.dismissAll:",  data);
      $scope.lobbyList  = [];
      console.log("$scope.lobbyList:",  $scope.lobbyList);
    });
  }
  $scope.updateEnvInfo = function(){
    //TODO:

  }
}])

.controller('gameTableCtrl', ['$scope','$rootScope','LobbyService','TableService',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($rootScope,$scope,LobbyService,TableService) {
//
  $scope.tableIndex = 0;
  $scope.tableInfo = {};
  // console.log("scope.gamerIds[$scope.tableIndex]:",$scope.gamerIds[$scope.tableIndex]);
  $scope.getOne = function(){
    console.log("LobbyService.gamerIds:", LobbyService.gamerIds);
    TableService.gamerId = LobbyService.gamerIds[$scope.tableIndex];
    console.log("TableService.gamerId:", TableService.gamerId);
    $scope.tableInfo = TableService.getOne(function(data){
      $scope.tableInfo = data;
      console.log("$scope.tableInfo:",$scope.tableInfo);
      //
      var gameTableDiv = document.getElementById("gameTableDiv");
      console.log("$scope.gameTableDiv:",gameTableDiv);
      if(gameTableDiv) {
        var player = new WGo.BasicPlayer(gameTableDiv, {
          sgf: $scope.tableInfo.sgf
        });
      }
      $scope.tableIndex++;
      if($scope.tableIndex==LobbyService.gamerIds.length){
        $scope.tableIndex = 0; //next round.
      }
    });

  }
}])

  .controller('accountCtrl', ['$scope', '$stateParams', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
    function ($scope, $stateParams,TableService) {

    }])
