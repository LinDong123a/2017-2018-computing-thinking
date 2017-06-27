angular.module('app.controllers', [])

.controller('gameLobbyCtrl', ['$scope', '$stateParams', '$ionicModal','LobbyService',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams,$ionicModal,LobbyService) {
  //
  $scope.gamerIds = [];
	// Load the modal from the given template URL
    $ionicModal.fromTemplateUrl('templates/modal-game-lobby.html', function($ionicModal) {
      $scope.modal_game_lobby = $ionicModal;
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
      for(i=0;i<data.length;i++){
        $rootScope.gamerIds.push(data[i].id);
      }
      console.log("$scope.gamerIds:",$scope.gamerIds);
    });
  }
  $scope.createUser = function(){
  //TODO:
  }
  $scope.tenantUser = function(){
  //TODO:
  }

}])

.controller('gameTableCtrl', ['$scope','TableService',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope,TableService) {
//
  $scope.tableIndex = 0;
  $scope.tableInfo = {};
  // console.log("scope.gamerIds[$scope.tableIndex]:",$scope.gamerIds[$scope.tableIndex]);
  $scope.getOne = function(){
    $scope.tableInfo = TableService.getOne(function(data){
      $scope.tableInfo = data;
      console.log("TableService.getOne:",$scope.tableInfo );
      //
      $scope.gameTableDiv = document.getElementById("gameTableDiv");
      console.log("$scope.gameTableDiv:",$scope.gameTableDiv);
      if($scope.gameTableDiv) {
        var player = new WGo.BasicPlayer($scope.gameTableDiv, {
          sgf: $scope.tableInfo.sgf
        });
      }
    });

  }
}])

  .controller('accountCtrl', ['$scope', '$stateParams', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
    function ($scope, $stateParams,TableService) {

    }])
