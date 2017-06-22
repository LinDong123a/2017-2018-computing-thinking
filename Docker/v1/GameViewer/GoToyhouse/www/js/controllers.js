angular.module('app.controllers', [])

.controller('gameLobbyCtrl', ['$scope', '$stateParams', '$ionicModal','LobbyService','$stomp',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams,$ionicModal,LobbyService,$stomp) {
  //
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

  $scope.connectHeaders = {};
  $stomp
    .connect('http://localhost:61613',  $scope.connectHeaders)

    // frame = CONNECTED headers
    .then(function (frame) {
      var subscription = $stomp.subscribe('/A/*', function (payload, headers, res) {
        console.log("subscription response:",payload,headers,res);
        $scope.payload = payload
      }, {
        'headers': 'are awesome'
      })

      // // Unsubscribe
      // subscription.unsubscribe()
      //
      // // Send message
      // $stomp.send('/dest', {
      //   message: 'body'
      // }, {
      //   priority: 9,
      //   custom: 42 // Custom Headers
      // })
      //
      // // Disconnect
      // $stomp.disconnect().then(function () {
      //   $log.info('disconnected')
      // })
    })
  $scope.pairAll = function () {
    LobbyService.pairAll(function(data){
      console.log("LobbyService.getAll(:",  data);
      $scope.lobbyList  = data;
      console.log("$scope.lobbyList:",  $scope.lobbyList);
    });
  }
  $scope.playAll = function($gameID){
    console.log("game start toggle:",$gameID);
    LobbyService.playAll(function(data){
      console.log("LobbyService.playAll:",  data);
    });
  }

}])

.controller('gameTableCtrl', ['$scope', '$stateParams', 'TableService',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams,TableService) {
//
  var tableId = $stateParams.id;
  $scope.tableInfo = TableService.getOne(tableId);
  console.log("$scope.tableInfo:",$scope.tableInfo);
  //
  $scope.gameTableDiv = document.getElementById("gameTableDiv");
  console.log("$scope.gameTableDiv:",$scope.gameTableDiv);
  if($scope.gameTableDiv) {
    var player = new WGo.BasicPlayer($scope.gameTableDiv, {
      sgf: $scope.tableInfo.sgf
    });
  }
}])

  .controller('accountCtrl', ['$scope', '$stateParams', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
    function ($scope, $stateParams,TableService) {

    }])
