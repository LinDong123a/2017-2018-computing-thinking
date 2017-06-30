angular.module('app.controllers', [])

.controller('gameLobbyCtrl', ['$scope','$rootScope','$stateParams', '$ionicModal','LobbyService','envInfo',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($rootScope,$scope, $stateParams,$ionicModal,LobbyService,envInfo) {
  //FIXME:$rootScope not working.
  // $rootScope.gamerIds = [];
  $scope.envInfo = envInfo;
	// Load the modal from the given template URL
  $scope.modal_settings = $ionicModal.fromTemplate(
    '<div class="modal"><header class="bar bar-header"> <h1 class="title">Settings</h1><div class="button button-clear" ng-click="modal_settings.hide()"><span class="icon ion-close-round"></span></div></header><p></p><ion-content class="has-header" has-header="true" padding="true"><div class="list"> <label class="item item-input item-stacked-label"> <span class="input-label">API:</span> <input type="text" placeholder="192.168.0.11" ng-model="envInfo.api"> </label> <label class="item item-input item-stacked-label"> <span class="input-label">MQTT:</span> <input type="text" placeholder="192.168.0.11" ng-model="envInfo.mqtt"> </label> <button  class="button button-stable  button-block icon-left ion-ios-checkmark-empty"ng-click="updateEnvInfo()">UPDATE</button> </div></ion-content></div>',
    {
    scope: $scope,
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
  $scope.dismissAll = function(){
    LobbyService.dismissAll(function(data){
      console.log("LobbyService.dismissAll:",  data);
      $scope.lobbyList  = [];
      console.log("$scope.lobbyList:",  $scope.lobbyList);
    });
  }
  $scope.updateEnvInfo = function(){
    //
    console.log("updated envInfo:",envInfo);
    $scope.modal_settings.hide();
  }

}])

.controller('gameTableCtrl', ['$scope','$rootScope','LobbyService','TableService','ChainCodeService',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($rootScope,$scope,LobbyService,TableService,ChainCodeService) {
//
  $scope.gamers = [];
  $scope.tableIndex = 0;
  $scope.tableInfo = {};
  $scope.getAll = function(){
    LobbyService.getAll(function(data){
      console.log("LobbyService.getAll:",  data);
      $scope.gamers = data;
    });
  }
  // console.log("scope.gamerIds[$scope.tableIndex]:",$scope.gamerIds[$scope.tableIndex]);
  $scope.getOne = function(){
    // console.log("LobbyService.gamerIds:", LobbyService.gamerIds);
    // TableService.gamerId = LobbyService.gamerIds[$scope.tableIndex];
    // console.log("TableService.gamerId:", TableService.gamerId);
    $scope.tableInfo = $scope.gamers[$scope.tableIndex];
    // $scope.tableInfo = TableService.getOne(function(data){
    console.info("$scope.tableInfo:",$scope.tableInfo);
    //TODO:ChainCode Verify
    // ChainCodeService.gamerId = $scope.tableInfo.id;
    // console.log("ChainCodeService.gamerId:", ChainCodeService.gamerId);
    // $scope.tableInfo = ChainCodeService.getOne(function(data){
    //   sgf = data;
      //
      var gameTableDiv = document.getElementById("gameTableDiv");
      console.log("$scope.gameTableDiv:",gameTableDiv);
      if(gameTableDiv) {
        var player = new WGo.BasicPlayer(gameTableDiv, {
          sgf: $scope.tableInfo.sgf
        });
      }
      $scope.tableIndex++;
      if($scope.tableIndex==$scope.gamers.length){
        $scope.tableIndex = 0; //next round.
      }
    };
  //default calls
  $scope.getAll();
}])

  .controller('accountCtrl', ['$scope', '$stateParams', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
    function ($scope, $stateParams,TableService) {

    }])
