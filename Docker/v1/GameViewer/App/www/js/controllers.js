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
      //Load the modal from the given template URL
      $rootScope.modal_aier_train  = null;
      $ionicModal.fromTemplateUrl("templates/modal_aier_train.html",
        {
          scope: $scope,
          animation: 'slide-in-up'
        }).then(function(modal) {
        $rootScope.modal_aier_train = modal;
      });
      //Load the modal from the given template URL
      $rootScope.modal_sgf_post  = null;
      $ionicModal.fromTemplateUrl("templates/modal_sgf_post.html",
        {
          scope: $scope,
          animation: 'slide-in-up'
        }).then(function(modal) {
        $rootScope.modal_sgf_post = modal;
      });
      //
      $rootScope.curGamerId = null;
      $rootScope.gamerIds = [];
      $rootScope.tableInfo = null;
      $rootScope.aierList = [];
      $rootScope.sgfDto = null;
      $rootScope.anewAier = {name:null,model:null,gid:null};
      $rootScope.placeholder_aier = null;
      //GameStatus:STANDBY("standby", 0), PAIRED("paired", 1), PLAYING("playing", 2), ENDED("ended", 3),SAVED("saved", 4);
      //UserStatus:unTENANTED("untenanted", 0), STANDBY("standby", 2), PLAYING("playing", 3),TENANTED("tenanted",1);
      $rootScope.policysObj = {"完全随机":"random", "最佳着法":"best_move", "随机应变":"random_move", "蒙特卡洛模拟":"mcts"};
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
          //
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
      //
      $rootScope.getAiersByStatus = function ($index) {
        AierService.curStatusIndex = $index;
        AierService.getAllByStatus(function(data){
          console.log("AierService.getAllByStatus:", data);
          $rootScope.aierList = data;
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
  //GameStatus:STANDBY("standby", 0), PAIRED("paired", 1), PLAYING("playing", 2), ENDED("ended", 3),SAVED("saved", 4);
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
      //then refresh
      $scope.getAll();
    });
  }
  $scope.playAll = function(){
    console.log("game start!:");
    GameService.playAll(function(data){
      console.log("GameService.playAll:",  data);
      //then refresh
      $scope.getAll();
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

.controller('gameTableCtrl', ['$scope','$rootScope','TableService','ChainCodeService','$ionicModal','GameService','$ionicPopup','Enum','Base64','WpWikiService',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($rootScope,$scope,TableService,ChainCodeService,$ionicModal,GameService,$ionicPopup,Enum,Base64,WpWikiService) {
//
  $scope.getSgf = function(){
    $rootScope.modal_sgf_post.show();
    //
    GameService.getSgf(function(data){
      console.log("GameService.getSgf:",  data);
      $scope.sgfDto = data;
      //alert message
      // $ionicPopup.alert({
      //   title: '保存成功！',
      //   template: "http://"+data.url
      // });
    });
  }

  $scope.runAgent = function () {
    GameService.runAgent(function(data){
      console.log("GameService.runAgent:", data);
    });
  }
  $scope.anewWpPost = {title:"", content:""};
  $scope.publishSgf = function () {
    console.log("$scope.publishSgf called...");
    $scope.anewWpPost.title = $scope.sgfDto.name;
    $scope.anewWpPost.content = "[wgo]"+ $scope.sgfDto.url +"[/wgo]";
    //
    var username = 'user';
    var password = 'bitnami';

    // use our Base64 service to encode the user/pass
    var base64 = Base64.encode( username + ':' + password );
    // Some endpoint that needs auth
    // var usersURL = 'http://localhost/wp-json/wp/v2/users';
    var postsURL = 'http://localhost/wp-json/wp/v2/posts';
    WpWikiService.getAuth( base64, postsURL ).then(function(response) {
        console.log('WpWikiService.getAuth response:',response);
        //then post a article.
        WpWikiService.anewWpPost = $scope.anewWpPost;
        WpWikiService.createPost(function(response) {
          console.log('WpWikiService.createPost response:',response);
        });
      }
      ,function(response) {
        console.log('WpWikiService.getAuth Error:'+response);
    });
    //
    $rootScope.modal_sgf_post.hide();
  }
  $scope.addAier = function () {
    $rootScope.modal_aier_add.show();
    $rootScope.placeholder_aier = $scope.placeholder_aier = Enum.getTimestamp();
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
      // $scope.policysObj = {"RANDOM":"random", "BEST_MOVE":"best_move", "RANDOM_MOVE":"random_move", "MCTs":"mcts"};
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
        //get Aiers by status = trained.
        $rootScope.getAiersByStatus(3);
        //
        $scope.modal_user_add.show();
        $scope.anewUser = {name:Enum.getUUID(6),rank:0,policy:"random"};
      };
      $scope.userList = [];
      $scope.createUser = function () {
        //
        UserService.anewUser = $scope.anewUser;
        //get actual value by key.
        UserService.anewUser.policy = $rootScope.policysObj[$scope.anewUser.policy];
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
//
        GameService.rPlayerId = $id;
        GameService.runPlayer(function(data){
          console.log("GameService.runPlayer:", data);
          //refresh
          $scope.getUsers();
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
      $scope.createAier = function () {
        //
        var suffix = new Date($rootScope.placeholder_aier)
        var formatted_suffix = suffix.toJSON().slice(0,13); //e.g. "2016-11-11T08:00:00.000Z"
        $rootScope.anewAier.name = $rootScope.anewAier.name +"_"+formatted_suffix;
        $rootScope.anewAier.gid = $rootScope.tableInfo.id;
        console.log("$rootScope.anewAier:",$rootScope.anewAier);
        AierService.anewAier = $rootScope.anewAier;
        AierService.createOne(function(data){
          console.log("AierService.createOne:", data);
          //refresh.
          $rootScope.getAiers();
          $rootScope.modal_aier_add.hide();
        });
      }
      //
      $scope.trainAgent = function ($id) {
        AierService.curAgentId = $id;
        AierService.trainAgent(function(data){
          console.log("AierService.trainAgent:", data);
          //refresh.
          $rootScope.getAiers();
        });
      }
      //default calls
      $rootScope.getAiers();
    }])
