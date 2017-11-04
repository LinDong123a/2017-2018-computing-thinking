angular.module('app.controllers', [])

  .controller('appMainCtrl', ['$rootScope','$scope', '$stateParams','envInfo','$ionicModal','ChainCodeService','UserService','GameService','Enum','AierService','$interval','MqttClient','$stomp','envInfo','$ionicLoading','$ionicPopover','SharedObjects',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
    function ($rootScope,$scope, $stateParams,envInfo,$ionicModal,ChainCodeService,UserService,GameService,Enum,AierService,$interval,MqttClient,$stomp,envInfo,$ionicLoading,$ionicPopover,SharedObjects) {
      console.info("appMainCtrl init.");
      // Load the modal from the given template URL
      $rootScope.modal_settings = null;
      $ionicModal.fromTemplateUrl("templates/modal_settings.html",
        {
          scope: $scope,
          animation: 'slide-in-up'
        }).then(function (modal) {
        $rootScope.modal_settings = modal;
      });
      //Load the modal from the given template URL
      $rootScope.modal_aier_add = null;
      $ionicModal.fromTemplateUrl("templates/modal_aier_add.html",
        {
          scope: $scope,
          animation: 'slide-in-up'
        }).then(function (modal) {
        $rootScope.modal_aier_add = modal;
      });
      //Load the modal from the given template URL
      $rootScope.modal_gamer_add = null;
      $ionicModal.fromTemplateUrl("templates/modal_gamer_add.html",
          {
              scope: $scope,
              animation: 'slide-in-up'
          }).then(function (modal) {
          $rootScope.modal_gamer_add = modal;
      });
      //Load the modal from the given template URL
      $rootScope.modal_aier_train = null;
      $ionicModal.fromTemplateUrl("templates/modal_aier_train.html",
        {
          scope: $scope,
          animation: 'slide-in-up'
        }).then(function (modal) {
        $rootScope.modal_aier_train = modal;
      });
      //Load the modal from the given template URL
      $rootScope.modal_sgf_post = null;
      $ionicModal.fromTemplateUrl("templates/modal_sgf_post.html",
        {
          scope: $scope,
          animation: 'slide-in-up'
        }).then(function (modal) {
        $rootScope.modal_sgf_post = modal;
      });
      //Load the modal from the given template URL
      $rootScope.modal_board_tenuki = null;
      $ionicModal.fromTemplateUrl("templates/modal_board_tenuki.html",
        {
          scope: $scope,
          animation: 'slide-in-up',
          backdropClickToClose: false
        }).then(function (modal) {
        $rootScope.modal_board_tenuki = modal;
      });
      //popover
      $rootScope.popover = null;
      $ionicPopover.fromTemplateUrl('templates/popover.html', {
        scope: $scope,
      }).then(function (popover) {
        $rootScope.popover = popover;
      });
      //
      $rootScope.curGamerId = null;
      $rootScope.gamerIds = [];
      $rootScope.tableInfo = null;
      $rootScope.aierList = [];
      $rootScope.sgfDto = null;
      $rootScope.anewAier = {name: null, model: null, gid: null};
      $rootScope.placeholder_aier = null;
      //GameStatus:STANDBY("standby", 0), PAIRED("paired", 1), PLAYING("playing", 2), ENDED("ended", 3),SAVED("saved", 4);
      //UserStatus:unTENANTED("untenanted", 0), STANDBY("standby", 2), PLAYING("playing", 3),TENANTED("tenanted",1);
      // $rootScope.policysObj = {"完全随机":"random", "最佳着法":"best_move", "随机应变":"random_move", "蒙特卡洛模拟":"mcts"};
      $rootScope.policysObj = {"完全随机": "random", "最佳着法": "best_move", "随机应变": "random_move"};
      $rootScope.userTypes = {"机器玩家": 0, "人类玩家": 1};
      $rootScope.gamerTypes = {"机器对战": 0, "人机对战": 1,"人人对战": 3};
      $rootScope.boardTypes = ["wgo", "tenuki"];
      $rootScope.tag_vs = "_vs_";
      $rootScope.tag_play = "_play_";
      $rootScope.tag_end = "_end_";
      $rootScope.curTenukiGame = null;
      $rootScope.go_string = 'abcdefghijklmnopqrstuvwxyz';
      $rootScope.score_tenuki_black = 0;
      $rootScope.score_tenuki_white = 0;
      // $rootScope.gamerInfo;//notice:only for tenuki board display.using SharedObjects service
      //players
      // $rootScope.curPlayer;
      // $rootScope.curPlayer1;
      // $rootScope.curPlayer2;
        //UserStatus unTENANTED("untenanted", 1), STANDBY("standby", 0), PLAYING("playing", 3),TENANTED("tenanted",2);
      // store the interval promise
      var moveIndex = 0;
      var player = null;
      var gameTableDiv = null;
      $rootScope.tableInfo = null;
      //common functions.
      $rootScope.renderGameTable = function ($tableInfo, bType) {
        $rootScope.tableInfo = $tableInfo;
        if (bType == $rootScope.boardTypes[0]) {
          gameTableDiv = document.getElementById("gameTableDiv");
          console.log("$scope.gameTableDiv:", gameTableDiv);
          //
          if (gameTableDiv) {
            player = new WGo.BasicPlayer(gameTableDiv, {
              sgf: $rootScope.tableInfo.sgf
              , move: moveIndex
            });
          }
        } else if (bType == $rootScope.boardTypes[1]) {
          //MQTT
          // $rootScope.conMqtt($tableInfo.topic,$tableInfo.player1.name);

          //Stomp
          // $rootScope.connectStomp($tableInfo,$tableInfo.player1.name);
        }

      };
      $rootScope.getOneTable = function (bType) {
        console.log("$scope.getOne called.");
        //
        GameService.getOne(function (data) {
          console.log("GameService.getOne:", data);
          $rootScope.tableInfo = data;
          $rootScope.renderGameTable(data, bType);
          //
        });
      };

      $rootScope.updateEnvInfo = function () {
        //
        console.log("updated envInfo:", envInfo);
        $scope.modal_settings.hide();
      }

      $rootScope.getAiers = function () {
        AierService.getAll(function (data) {
          // console.log("AierService.getAll:", data);
          $rootScope.aierList = data;
          console.log("$rootScope.aierList:", $rootScope.aierList);
        });
      }
      //
      $rootScope.getAiersByStatus = function ($index) {
        AierService.curStatusIndex = $index;
        AierService.getAllByStatus(function (data) {
          console.log("AierService.getAllByStatus:", data);
          $rootScope.aierList = data;
        });
      }
      //
      $rootScope.getAllGames = function(){
          GameService.getAll(function(data){
              // console.log("GameService.getAll:",  data);
              $scope.lobbyList  = data;
              console.log("$scope.lobbyList:",  $scope.lobbyList);
              //
          });
      };
      //
      $rootScope.score_tenuki = function ($event) {
        var scoreObj = $rootScope.curTenukiGame.score();
        console.log("$rootScope.curTenukiGame.score():", scoreObj);
        $rootScope.score_tenuki_black = scoreObj.black;
        $rootScope.score_tenuki_white = scoreObj.white;
        $rootScope.popover.show($event);
      }
      $rootScope.close_tenuki = function () {
        $rootScope.modal_board_tenuki.hide();
        // console.log("SharedObjects.curGamer:",SharedObjects.curGamer,",SharedObjects.curPlayer:",SharedObjects.curPlayer);
        $rootScope.updateUserStatusById(SharedObjects.curGamer.id,SharedObjects.curPlayer.id,0);
        //emit player leave game.
        var playMessage = {game_id: SharedObjects.curGamer.id,
            user_id:SharedObjects.curPlayer.id,method:'leave',
            msg: ''};
        $rootScope.socketIO.emit('playEvent', playMessage);
      }
      $rootScope.save_tenuki = function () {
          $rootScope.modal_board_tenuki.hide();
          //update game status
          GameService.curGamerStatus = 3;
          GameService.updateStatusById(function (data) {
              console.log("GameService.updateStatusById:", data);
          });
      }
      //MQTT related
      //connect
      $rootScope.conMqtt = function ($gamerTopic, $userId) {
        var ip = envInfo.mqtt.host;
        var port = envInfo.mqtt.port;
        var id = $userId;
        console.log("conMqtt:", $gamerTopic, $userId, ip, port, id);

        MqttClient.init(ip, port, id);
        MqttClient.connect({onSuccess: successCallback});
        MqttClient.onConnectionLost = onConnectionLost;
        MqttClient.onMessageArrived = onMessageArrived;

        function successCallback() {
          MqttClient.subscribe($gamerTopic);
          message = new Paho.MQTT.Message("Hello");
          message.destinationName = $gamerTopic;
          MqttClient.send(message);
        }

        function onConnectionLost(responseObject) {
          if (responseObject.errorCode !== 0)
            console.log("onConnectionLost:" + responseObject.errorMessage);
        };

        function onMessageArrived(message) {
          console.log("onMessageArrived:" + message.payloadString);
          // MqttClient.disconnect();
        }
      };
      //disconnect
      $rootScope.disconMqtt = function ($gamerTopic) {
        MqttClient.unsubscribe($gamerTopic);
        MqttClient.disconnect();
      }
      //Stomp
      $stomp.setDebug(function (args) {
        $log.debug(args)
      })
      //Websocket/Stomp handler:
      var stompClient = null;

      $rootScope.connectStomp = function ($gamerInfo, $userId) {
        //
        // var client = Stomp.client( "ws://"+envInfo.mqtt.host+":61614/stomp", "v11.stomp" );
        // client.connect( "", "",
        //   function() {
        //   //FIXME:max of topic name string length.
        //     client.subscribe($gamerInfo.topic,
        //       function( message ) {
        //         alert( message );
        //       }
        //       ,{ priority: 9 }
        //     );
        //     client.send($gamerInfo.topic, { priority: 9 },$gamerInfo.topic);
        //   }
        // );
        //
        //
        stompClient = Stomp.client("ws://" + envInfo.mqtt.host + ":61614/stomp", "v11.stomp");
        stompClient.connect("", "",
          //   stompClient.connect({},
          function () {
            console.log("stompClient.connected.");
            stompClient.subscribe($gamerInfo.topic,
              function (message) {
                // called when the client receives a STOMP message from the server
                if (message.body) {
                  alert("got message with body " + message.body)
                } else {
                  alert("got empty message");
                }
                // alert( message );
                // (JSON.parse(message.body));
                // console.log(message.body);
                //1.receive game play message then place chess player.
                console.log("received game play info:", $gamerInfo);
                //2.frozen UI elements,while human player played piece.

                //3.receive game status message.

              }
              , {priority: 9}
              // {id: $userId}
            );
            stompClient.begin("tx-1");
            stompClient.send($gamerInfo.topic, {priority: 9}, $gamerInfo.topic);//For subscribe testing...
            // stompClient.send($gamerInfo.topic, {}, $gamerInfo.topic);
            stompClient.commit("tx-1");
          }
        );
      };
      $rootScope.sendToStomp = function ($gamerTopic, $message) {
        stompClient.send($gamerTopic, {priority: 9}, $message);
      }
      $rootScope.disconnectStomp = function () {
        stompClient.unsubscribe();
        stompClient.disconnect();
      }
      $rootScope.playAt_tenuki = function(lastMove){
        var letter_x = lastMove.charAt(3);
        var letter_y = lastMove.charAt(2);
        var n_x = $rootScope.go_string.indexOf(letter_x);
        var n_y = $rootScope.go_string.indexOf(letter_y);
        $rootScope.curTenukiGame.playAt(n_x, n_y);
      }
      $rootScope.updateUserStatusById = function ($gamerId,$playerId,$status) {
          GameService.curGamerId = $gamerId;
          GameService.curPlayerId = $playerId;
          GameService.curPlayerStatus = $status;
          GameService.updateUserStatusById(function (data) {
              console.log("GameService.updateUserStatusById:", data);
              $rootScope.getAllGames();//refresh.
          });
      }

      //SocketIO
        $rootScope.socketIO =  io.connect('http://'+envInfo.api.host+":9092");
        $rootScope.socketIO.on('connect', function() {
            // console.log("SocketIO:",io);
            console.log("SocketIO web client has connected to the server,$rootScope.socketIO:",$rootScope.socketIO);

            $rootScope.socketIO.on('playEvent', function(data) {
                // console.log( "playEvent:",data);
                if('join'==data.method){
                    //     console.log("join room:",data,",SharedObjects.curPlayer:",SharedObjects.curPlayer);
                    if(SharedObjects.curPlayer1 == null) {
                        SharedObjects.curPlayer1 = SharedObjects.curGamer.player1;
                    }else{
                        SharedObjects.curPlayer2 = SharedObjects.curGamer.player2;
                    }
                }else if('play'==data.method){
                    console.log( "playEvent:",data);
                }
                else if('leave'==data.method){
                    console.log( "leave room:",data);
                }else{
                  console.error("UNKOWN playEvent.method.");
                }

            });
        });
        //
        //
        // socket.on('disconnect', function() {
        //     output('<span class="disconnect-msg">The client has disconnected!</span>');
        // });
        //SSE
      var eventSource = null;
        var JIGO_TENUKI="B";
      $rootScope.connectSSE = function ($gamerInfo,$jigo) {
        console.log("$rootScope.connectSSE:",$gamerInfo,$jigo);
        eventSource = new EventSource(envInfo.api.url+"/game/sse/sgf/"+$gamerInfo.id);
        eventSource.onmessage = function(event) {
          if(event.data) {
            var moves = event.data.split(";");
            var lastMove = moves[moves.length-1];
            console.log("after SSE,lastMove:",lastMove);

            if(lastMove.indexOf($jigo)==-1){//opponent turn now
                $ionicLoading.hide();
                $rootScope.playAt_tenuki(lastMove);
            }
          }
          if (event.eventPhase == EventSource.CLOSED) {
              console.debug("Event Source Closed.");
          }
        };
      }
      $rootScope.getVsUserId = function($gamerInfo,$playerId){
        var player1 = $gamerInfo.player1;
        var player2 = $gamerInfo.player2;
        var vsPlayerId = (player1.id==$playerId)?player2.id:player1.id;
        console.log("$rootScope.getVsUserId:",vsPlayerId);
        return vsPlayerId;
      }
      //tenuki game setup
      //@see: https://www.npmjs.com/package/tenuki
      //
      $rootScope.isAITurnNow = function($gamerInfo,lastMove){
          var isAIturnNow = false;
          if( $gamerInfo.type=='HUMAN_VS_AI' || $gamerInfo.type=='AI_VS_HUMAN') {
              isAIturnNow = (lastMove.indexOf(JIGO_TENUKI) != -1) ? true : false;
          }
          console.log("$gamerInfo.type:"+$gamerInfo.type+",isAIturnNow?",isAIturnNow);
          return isAIturnNow;
      }
      //
      $rootScope.tenukiGameSetup = function($gamerInfo,$playerId,$jigo) {
        console.log("$rootScope.tenukiGameSetup:",$gamerInfo,$playerId,$jigo);
        var boardElement = document.querySelector(".tenuki-board");
        //clear history.
        while (boardElement.firstChild) {
            boardElement.removeChild(boardElement.firstChild);
        }
        // console.log("boardElement:",boardElement);
        var game = new tenuki.Game(boardElement);
        // console.log("boardElement game:",game);
        game.setup({
          scoring: "territory" // default is "territory"
        });
        // console.log("$gamerInfo,$playerId:",$gamerInfo,$playerId);
          // $rootScope.getVsUserId($gamerInfo,$playerId);
        //SSE connect.
        $rootScope.curTenukiGame = game;
        if($gamerInfo.type=='HUMAN_VS_HUMAN') {
          $rootScope.connectSSE($gamerInfo, $jigo);
        }
          // console.log(JIGO_TENUKI,$jigo,($jigo!=JIGO_TENUKI));
          if($jigo!=JIGO_TENUKI){
              $ionicLoading.show();//opponent player waiting
          } else if($jigo==null){//spectator only waiting
              return;
          }
        //
        game.callbacks.postRender = function (game) {
          //game.currentState() -- 游戏当前状态
          // alert(game.currentState());
//           console.log(game.intersectionAt(0, 0).value);
// // 'empty'
//           console.log(game.currentPlayer());
// // 'black'
//           console.log(game.isOver());
// // false
//           console.log(game.playAt(0, 0));
// // true
//           console.log(game.intersectionAt(0, 0).value);
// 'black'
          if (game.currentState().pass) {
            console.log(game.currentState().color + " passed");
          }
          if (game.currentState().playedPoint) {
            // console.log("$gamerInfo,$playerId:",$gamerInfo,$playerId);
            //$userId#play#B[cm],594a4c8b6516899e6a30e17f#play#B[cm]
            var x = game.currentState().playedPoint.x;
            var y = game.currentState().playedPoint.y;
            var sMoveInfo = ";B";
            if(game.currentState().color!='black') {
              sMoveInfo = ';W';
            }
            //
            sMoveInfo += '[' +$rootScope.go_string[x] + $rootScope.go_string[y] + ']';
            console.log("sMoveInfo:",sMoveInfo);
            //update sgf object if needed.
            GameService.curGamerId = $gamerInfo.id;
            GameService.curSgfObj = {header: null, body: sMoveInfo};
            GameService.curPlayerId  = $playerId;
            GameService.updateSgfObj(function (data) {
              //
              console.log("GameService.updateSgfObj:", data);
              var latestMoves = data.body.split(";");
              var lastMove = latestMoves[latestMoves.length-1];
              //then post to simpleAI server
                if($rootScope.isAITurnNow($gamerInfo,lastMove)) {
                  GameService.curPlayMessage = {game_id: $gamerInfo.id, user_id: $playerId, msg: sMoveInfo};
                  console.log("before GameService.vsSimpleAI,curPlayMessage:", GameService.curPlayMessage);
                  GameService.vsSimpleAI(function (data) {
                    console.log("GameService.vsSimpleAI response:", data);
                    var moves = data.msg.split(";");
                    var lastMove = moves[moves.length - 1];
                    $rootScope.playAt_tenuki(lastMove);
                  });
              }
              //
              if($gamerInfo.type=='HUMAN_VS_HUMAN') {
                  // console.log("!!!HUMAN_VS_HUMAN!!!");
                  //
                  $ionicLoading.show();
                  //
              }
                });
            // var moveInfo = game.currentState().color + " played[ " + game.currentState().playedPoint.y + "," + game.currentState().playedPoint.x+"]";
            //$userId_play_B[cm]
            // stompClient.send($gamerInfo.topic, {priority: 9}, moveInfo);
          }
        }
      }
  }])

.controller('gameLobbyCtrl', ['$scope','$rootScope','$stateParams', '$ionicModal','LobbyService','envInfo','$location','GameService','$location','$ionicPopup','Enum','SharedObjects',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($rootScope,$scope, $stateParams,$ionicModal,envInfo,$location,LobbyService,GameService,$location, $ionicPopup,Enum,SharedObjects) {
  //
  //Dynamic host modification
  // envInfo.mqtt.host = $location.host();
  // envInfo.api.host = $location.host();
  // envInfo.api.url = envInfo.api.host+envInfo.api.port+envInfo.api.context;
  // envInfo.mqtt.url = envInfo.mqtt.host+envInfo.mqtt.port;
  //
  $scope.envInfo = envInfo;
  $scope.anewGamer = {name:Enum.getUUID(6), type: null};
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
      $rootScope.getAllGames();
    });
  }
  $scope.playAll = function(){
    console.log("game start!:");
    GameService.playAll(function(data){
      console.log("GameService.playAll:",  data);
      //then refresh
        $rootScope.getAllGames();
    });
  }
  $scope.rPlayAll = function(){
    var promptPopup_rGame = $ionicPopup.prompt({
      title: '提示',
      template: '请输入对局数',
      inputType: 'number',
      inputPlaceholder: '0',
      okText:"确定",
      cancelText:"取消"
    });
    promptPopup_rGame.then(function(res) {
      console.log(res);
      if(res!=undefined && res>0){
        GameService.rGamerNum = res;
        GameService.rPlayAll(function(data){
          console.log("GameService.rPlayAll:",  data);
          //then refresh
            $rootScope.getAllGames();
        });
      }
      //
    });
  }
  $scope.curGamer;
  $scope.hPlayOne = function($gamerInfo, $player,$jigo){
//update shared objects
    SharedObjects.curPlayer = $player;
    $scope.curGamer = SharedObjects.curGamer = $gamerInfo;
    console.log("SharedObjects.curGamer:",SharedObjects.curGamer);
    $rootScope.modal_board_tenuki.show();
    // var boardElement = document.getElementById("tenuki-board");
    // window.board = new tenuki.Game({ element: boardElement });
    //0.game set up.
    // console.log("$gamerInfo,$playerId:",$gamerInfo,$playerId);
    $rootScope.tenukiGameSetup($gamerInfo,$player.id,$jigo);
    //1.stomp connect
    // $rootScope.connectStomp($gameInfo, $userId);
    //2.
    // GameService.curGamerId = $gamerInfo.id;
    // GameService.playOne(function(data){
    //   console.log("GameService.playOne:",  data);
    //   //then refresh
    //   $scope.getAll();
    // });
    //socket.join('some room');
      //and update status
      $rootScope.updateUserStatusById($gamerInfo.id,$player.id,3);
      //player join room...
      var playMessage = {game_id: $gamerInfo.id,
          user_id:$player.id,method:'join',
          msg: $jigo};
      $rootScope.socketIO.emit('playEvent', playMessage);
  }
  $scope.hWatchOne = function($gamerInfo){
    $rootScope.modal_board_tenuki.show();
    $rootScope.tenukiGameSetup($gamerInfo,null,null);
    //get sgf by gamer id.
      GameService.curGamerId = $gamerInfo.id;
      GameService.getSgf(function (data) {
          var historyMoves = data;
          console.log("historyMoves:",data);
          // $rootScope.playAt_tenuki(lastMove);

      })



  }
  $scope.dismissAll = function(){
    LobbyService.dismissAll(function(data){
      console.log("LobbyService.dismissAll:",  data);
      $scope.lobbyList  = [];
      console.log("$scope.lobbyList:",  $scope.lobbyList);
    });
  }
  $scope.toGameTableView  = function($gid,bType){
    console.log("$scope.toGameTableView called.");
    $rootScope.curGamerId = $gid;
    GameService.curGamerId = $gid;
    console.log(" $rootScope.curGamerId:",$rootScope.curGamerId);
    $location.url('/page1/page3');
    $rootScope.getOneTable(bType);
  }
  $scope.deleteOne = function($gamer){
    GameService.curGamerId = $gamer.id;
    GameService.deleteOne(function(data){
      console.log("GameService.deleteOne:",  data);
        $rootScope.getAllGames();//refresh.
    });
  }
  $scope.addGamer = function () {
      $scope.anewGamer = {name:Enum.getUUID(6), type: null};
      $rootScope.modal_gamer_add.show();
      $rootScope.placeholder_gamer = $scope.placeholder_aier = Enum.getTimestamp();
  }
  $scope.qCreateGamer = function () {
    console.log("createGamer...");
      $rootScope.modal_gamer_add.hide();

      GameService.qGamerType = $rootScope.gamerTypes[$scope.anewGamer.type];
      GameService.qGamerName = $scope.anewGamer.name;
      GameService.qCreateGamer(function(data){
          console.log("GameService.qCreateGamer:",  data);
          $rootScope.getAllGames();//refresh.
      });
  }
  //default calls
    $rootScope.getAllGames();//refresh.

}])

.controller('gameTableCtrl', ['$scope','$rootScope','envInfo','TableService','ChainCodeService','$ionicModal','GameService','$ionicPopup','Enum','Base64','WpWikiService',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($rootScope,$scope,envInfo,TableService,ChainCodeService,$ionicModal,GameService,$ionicPopup,Enum,Base64,WpWikiService) {
  //
  console.log("envInfo:",envInfo);
  //
  $scope.getSgf = function(){
    $rootScope.modal_sgf_post.show();
    //
    GameService.getSgf(function(data){
      console.log("GameService.getSgf:",  data);
      $scope.sgfDto = data;
    });
  }

  $scope.runAgent = function () {
    GameService.runAgent(function(data){
      console.log("GameService.runAgent:", data);
    });
  }
  $scope.anewWpPost = {title:"", content:"",status:"publish",slug:"",excerpt:""};//see: v2.wp-api.org/reference/posts
  $scope.publishSgf = function () {
    console.log("$scope.publishSgf called...");
    $scope.anewWpPost.title = $scope.sgfDto.name;
    $scope.anewWpPost.content = "[wgo]"+ $scope.sgfDto.url +"[/wgo]";
    $scope.anewWpPost.slug = ""+$rootScope.tableInfo.player1.name +"_VS_"+$rootScope.tableInfo.player2.name;
    $scope.anewWpPost.excerpt = $scope.sgfDto.result;
    //warn: for testing only.
    var username = 'user';
    var password = 'bitnami';

    // use our Base64 service to encode the user/pass
    var base64 = Base64.encode( username + ':' + password );
    // Some endpoint that needs auth
    // var usersURL = 'http://localhost/wp-json/wp/v2/users';
    var postsURL = envInfo.wp.host+'/wp-json/wp/v2/posts';
    console.log("postsURL:",postsURL);
    WpWikiService.getAuth( base64, postsURL ).then(function(response) {
        console.log('WpWikiService.getAuth response:',response);
        //then post a article.
        WpWikiService.anewWpPost = $scope.anewWpPost;
        WpWikiService.postsURL = postsURL;//!to avoid constant changed.
        console.log("before post,WpWikiService.anewWpPost:",WpWikiService.anewWpPost);
        console.log("before post,WpWikiService.postsURL:",WpWikiService.postsURL);
        WpWikiService.createPost(function(response) {
          console.log('WpWikiService.createPost response:',response);
          // alert message
            var htmlTemplate = "<a target='_blank' href='"+response.link+"'><div></p>"+response.link+"<p></div></a>";
          $ionicPopup.alert({
            title: '发布成功！',
            template: htmlTemplate
          });
        });
      }
      ,function(response) {
        console.log('WpWikiService.getAuth Error:',response);
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
        $scope.anewUser = {name:Enum.getUUID(6),rank:0,policy:"random",type:0};
      };
      $scope.userList = [];
      $scope.createUser = function () {
        //
        UserService.anewUser = $scope.anewUser;
        //get actual value by key.
        UserService.anewUser.policy = $rootScope.policysObj[$scope.anewUser.policy];
        UserService.anewUser.type = $rootScope.userTypes[$scope.anewUser.type];
        console.info("UserService.anewUser:", UserService.anewUser);
        //
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

      $scope.runPlayer = function ($user) {
//
        if($user.type==0)//AI
        {
          GameService.rPlayerId = $user.id;
          //
          GameService.runPlayer(function (data) {
            console.log("GameService.runPlayer:", data);
            //refresh
            $scope.getUsers();
          });
          //Human
        }else{
          GameService.tenUserId = $user.id;
          //
          GameService.tenantUser(function (data) {
            console.log("GameService.tenantUser:", data);
            //refresh
            $scope.getUsers();
          });
        }
      };
      //default calls
      $scope.getUsers();
    }])
  .controller('gameAierCtrl', ['$rootScope','$scope', '$stateParams','envInfo','$ionicModal','AierService','Enum',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
    function ($rootScope,$scope, $stateParams,envInfo,$ionicModal,AierService,Enum) {
      // console.info("envInfo:",envInfo);
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
      // $rootScope.getAiers();
    }])
