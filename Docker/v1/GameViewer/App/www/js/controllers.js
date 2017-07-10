angular.module('app.controllers', [])

  .controller('appMainCtrl', ['$rootScope','$scope', '$stateParams','envInfo','$ionicModal','ChainCodeService','UserService','GameService','Enum','AierService',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
    function ($rootScope,$scope, $stateParams,envInfo,$ionicModal,ChainCodeService,UserService,GameService,Enum,AierService) {
      console.info("appMainCtrl init.");
      // Load the modal from the given template URL
      $rootScope.modal_settings = null;
      $ionicModal.fromTemplateUrl("templates/modal_settings.html",
        {
          scope: $scope,
          animation: 'slide-in-up'
        }).then(function(modal) {
        $rootScope.modal_settings = modal;
      });
      //Load the modal from the given template URL
      $rootScope.modal_aier_add  = null;
      $ionicModal.fromTemplateUrl("templates/modal_aier_add.html",
        {
          scope: $scope,
          animation: 'slide-in-up'
        }).then(function(modal) {
        $rootScope.modal_aier_add = modal;
      });
      //
      $rootScope.curGamerId = null;
      $rootScope.gamerIds = [];
      $rootScope.tableInfo = null;
      $rootScope.aierList = [];
      //common functions.
      $rootScope.renderGameTable = function ($tableInfo) {
        var gameTableDiv = document.getElementById("gameTableDiv");
        console.log("$scope.gameTableDiv:",gameTableDiv);
        if(gameTableDiv) {
          var player = new WGo.BasicPlayer(gameTableDiv, {
            sgf: $tableInfo.sgf
          });
        }
      };
      $rootScope.getOneTable = function() {
        console.log("$scope.getOne called.");
        //
        GameService.getOne(function(data){
          console.log("GameService.getOne:",  data);
          $rootScope.tableInfo = data;
          $rootScope.renderGameTable(data);
        });
      };

      $rootScope.updateEnvInfo = function(){
        //
        console.log("updated envInfo:",envInfo);
        $scope.modal_settings.hide();
      }

      $rootScope.getAiers = function () {
        AierService.getAll(function(data){
          console.log("AierService.getAll:", data);
          $rootScope.aierList = data;
          console.log("$rootScope.aierList:", $rootScope.aierList);
        });
      }

  }])

.controller('gameLobbyCtrl', ['$scope','$rootScope','$stateParams', '$ionicModal','LobbyService','envInfo','$location','GameService','$location',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($rootScope,$scope, $stateParams,$ionicModal,envInfo,$location,LobbyService,GameService,$location) {
  //
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
    $rootScope.curGamerId = $gid;
    GameService.curGamerId = $gid;
    console.log(" $rootScope.curGamerId:",$rootScope.curGamerId);
    $location.url('/page1/page3');
    $rootScope.getOneTable();
  }
  $scope.deleteOne = function($gamer){
    GameService.curGamerId = $gamer.id;
    GameService.deleteOne(function(data){
      console.log("GameService.playOne:",  data);
      $scope.getAll();//refresh.
    });
  }

  //default calls
  $scope.getAll();

}])

.controller('gameTableCtrl', ['$scope','$rootScope','TableService','ChainCodeService','$ionicModal','GameService','$ionicPopup',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($rootScope,$scope,TableService,ChainCodeService,$ionicModal,GameService,$ionicPopup) {
//
  $scope.gamers = [];
  $scope.tableIndex = 0;
  $scope.tableInfo = {};
  $scope.sgfDto = {cmd:null,url:null};
  //
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

      $rootScope.renderGameTable($scope.tableInfo);
      //
      $scope.tableIndex++;
      if($scope.tableIndex==$scope.gamers.length){
        $scope.tableIndex = 0; //next round.
      }
    };

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

  $scope.runAgent = function () {
    GameService.runAgent(function(data){
      console.log("GameService.runAgent:", data);
    });
  }
  $scope.trainAgent = function () {
    GameService.trainAgent(function(data){
      console.log("GameService.trainAgent:", data);
    });
  }

  //default calls
  if($rootScope.curGamerId!=null) {
    console.log("gameTableCtrl.getOneTable() called.");
    $rootScope.getOneTable();
  }

}])

  .controller('gamePlayerCtrl', ['$rootScope','$scope', '$stateParams','envInfo','$ionicModal','ChainCodeService','UserService','GameService','Enum',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
    function ($rootScope,$scope, $stateParams,envInfo,$ionicModal,ChainCodeService,UserService,GameService,Enum) {
      console.info("envInfo:",envInfo);
      //
      $scope.policysObj = {"RANDOM":"random", "BEST_MOVE":"best_move", "RANDOM_MOVE":"random_move", "MCTs":"mcts"};
      $scope.anewAI  = {name:Enum.getUUID(),historical:true};
      //Load the modal from the given template URL
      $scope.modal_user_add  = null;
      $ionicModal.fromTemplateUrl("templates/modal_user_add.html",
        {
          scope: $scope,
          animation: 'slide-in-up'
        }).then(function(modal) {
        $scope.modal_user_add = modal;
      });
//
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
  .controller('gameAierCtrl', ['$rootScope','$scope', '$stateParams','envInfo','$ionicModal','AierService','Enum',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
    function ($rootScope,$scope, $stateParams,envInfo,$ionicModal,AierService,Enum) {
      console.info("envInfo:",envInfo);
      //
      $scope.placeholder  = Enum.getTimestamp();
      $scope.anewAier = {name:null,model:null};

      $scope.createAier = function () {
        console.log("$scope.anewAier:",$scope.anewAier);
        AierService.anewAier = $scope.anewAier;
        AierService.createOne(function(data){
          console.log("AierService.createOne:", data);
          //refresh.
          $scope.getAll();
          $rootScope.modal_aier_add.hide();
        });
      }
      //default calls
      $rootScope.getAiers();
    }])
