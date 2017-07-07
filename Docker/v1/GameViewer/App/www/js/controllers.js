angular.module('app.controllers', [])

.controller('gameLobbyCtrl', ['$scope','$rootScope','$stateParams', '$ionicModal','LobbyService','envInfo','$location','GameService','$location',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($rootScope,$scope, $stateParams,$ionicModal,envInfo,$location,LobbyService,GameService,$location) {
  //FIXME:$rootScope not working.
  // $rootScope.gamerIds = [];
  //Dynamic host modification
  // envInfo.mqtt.host = $location.host();
  // envInfo.api.host = $location.host();
  // envInfo.api.url = envInfo.api.host+envInfo.api.port+envInfo.api.context;
  // envInfo.mqtt.url = envInfo.mqtt.host+envInfo.mqtt.port;
  //
  $scope.envInfo = envInfo;
  //GameStatus:STANDBY("standby", 0), PAIRED("paired", 1), PLAYING("playing", 2), SAVED("saved", 3);
  //UserStatus:unTENANTED("untenanted", 0), STANDBY("standby", 2), PLAYING("playing", 3),TENANTED("tenanted",1);
  $scope.pairAll = function () {
    console.log("GameService:",GameService);
    GameService.pairAll(function(data){
      console.log("GameService.pairAll(:",  data);
      $scope.lobbyList  = data;
      console.log("$scope.lobbyList:",  $scope.lobbyList);
    });
  }
  $scope.playOne = function($gid){
    console.log("game start!:");
    GameService.curGamerId = $gid;
    GameService.playOne(function(data){
      console.log("GameService.playOne:",  data);
    });
  }
  $scope.playAll = function(){
    console.log("game start!:");
    GameService.playAll(function(data){
      console.log("GameService.playAll:",  data);
    });
  }
  $scope.dismissAll = function(){
    LobbyService.dismissAll(function(data){
      console.log("LobbyService.dismissAll:",  data);
      $scope.lobbyList  = [];
      console.log("$scope.lobbyList:",  $scope.lobbyList);
    });
  }
  $scope.getAll = function(){
    GameService.getAll(function(data){
      console.log("GameService.getAll:",  data);
      $scope.lobbyList  = data;
      console.log("$scope.lobbyList:",  $scope.lobbyList);
    });
  }
  $scope.toGameTableView  = function($gid){
    console.log("$scope.toGameTableView called.");
    GameService.curGamerId = $gid;
    $location.url('/page1/page3');
  }

  //default calls
  $scope.getAll();

}])

.controller('gameTableCtrl', ['$scope','$rootScope','TableService','ChainCodeService','$ionicModal','GameService','$ionicPopup',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($rootScope,$scope,TableService,ChainCodeService,$ionicModal,GameService,$ionicPopup) {
  // Load the modal from the given template URL
  $scope.modal_settings = null;
  $ionicModal.fromTemplateUrl("templates/modal_settings.html",
    {
      scope: $scope,
      animation: 'slide-in-up'
    }).then(function(modal) {
    $scope.modal_settings = modal;
  });
//
  $scope.gamers = [];
  $scope.tableIndex = 0;
  $scope.tableInfo = {};
  $scope.getAll = function(){
    GameService.getAll(function(data){
      console.log("GameService.getAll:",  data);
      $scope.gamers = data;
    });
  }
  // console.log("scope.gamerIds[$scope.tableIndex]:",$scope.gamerIds[$scope.tableIndex]);
  $scope.nextOne = function(){
    // console.log("LobbyService.gamerIds:", LobbyService.gamerIds);
    // TableService.gamerId = LobbyService.gamerIds[$scope.tableIndex];
    // console.log("TableService.gamerId:", TableService.gamerId);
    $scope.tableInfo = $scope.gamers[$scope.tableIndex];
    // $scope.tableInfo = TableService.getOne(function(data){
    console.info("$scope.tableInfo:",$scope.tableInfo);
    //TODO:ChainCode Verify
    // ChainCodeService.gamerId = $scope.tableInfo.id;
    // console.log("ChainCodeService.gamerId:", ChainCodeService.gamerId);
      //
      $scope.renderGameTable($scope.tableInfo);
      //
      $scope.tableIndex++;
      if($scope.tableIndex==$scope.gamers.length){
        $scope.tableIndex = 0; //next round.
      }
    };

  $scope.updateEnvInfo = function(){
    //
    console.log("updated envInfo:",envInfo);
    $scope.modal_settings.hide();
  }
  $scope.getSgf = function(){
    GameService.getSgf(function(data){
      console.log("GameService.getSgf:",  data);
      $scope.sgfDto = data;
      //alert message
      $ionicPopup.alert({
        title: '保存成功！',
        template: "http://"+data.url
      });
    });
  }
  $scope.getOne = function() {
    console.log("$scope.getOne called.");
    //
    GameService.getOne(function(data){
      console.log("GameService.getOne:",  data);
      $scope.renderGameTable(data);
    });
  }

  $scope.renderGameTable = function ($tableInfo) {
    var gameTableDiv = document.getElementById("gameTableDiv");
    console.log("$scope.gameTableDiv:",gameTableDiv);
    if(gameTableDiv) {
      var player = new WGo.BasicPlayer(gameTableDiv, {
        sgf: $tableInfo.sgf
      });
    }
  }

  //default calls
  $scope.getOne();
  // $scope.getAll();
}])

  .controller('gamePlayerCtrl', ['$scope', '$stateParams','envInfo','$ionicModal','ChainCodeService','UserService','GameService','Enum',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
    function ($scope, $stateParams,envInfo,$ionicModal,ChainCodeService,UserService,GameService,Enum) {
      console.info("envInfo:",envInfo);
      //
      $scope.policysObj = {"RANDOM":"random", "BEST_MOVE":"best_move", "RANDOM_MOVE":"random_move", "MCTs":"mcts"};
      //Load the modal from the given template URL
      $scope.modal_user_add  = null;
      $ionicModal.fromTemplateUrl("templates/modal_user_add.html",
        {
          scope: $scope,
          animation: 'slide-in-up'
        }).then(function(modal) {
        $scope.modal_user_add = modal;
      });

      $scope.addUser = function () {
        $scope.modal_user_add.show();
        $scope.anewUser = {name:Enum.getUUID(),rank:0,policy:"random"};
      };
      $scope.userList = [];
      $scope.createUser = function () {
        //
        UserService.anewUser = $scope.anewUser;
        console.info("UserService.anewUser:", UserService.anewUser);
        //
        console.log("envInfo:",envInfo);
        UserService.createUser(function(data){
          console.log("UserService.createOne(:",  data);
          $scope.userList.push(data);
          console.log("$scope.userList:",  $scope.userList);
          //
          $scope.modal_user_add.hide();
        });
      }

      $scope.getUsers = function () {
//
        UserService.getUsers(function(data){
          console.log("UserService.getUsers:", data);
          $scope.userList = data;
          console.log("$scope.userList:", $scope.userList);
        });
      }

      $scope.deleteUser = function ($user) {
//
        UserService.delUserId = $user.id;
        //
        UserService.deleteUser(function(data){
          console.log("UserService.deleteUser:", data);
         //refresh user list.
          $scope.getUsers();
        });
      }

      $scope.runPlayer = function ($id) {

        GameService.rPlayerId = $id;
        GameService.runPlayer(function(data){
          console.log("GameService.runPlayer:", data);
        });
      }
      //default calls
      $scope.getUsers();
    }])
