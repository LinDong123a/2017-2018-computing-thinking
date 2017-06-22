angular.module('app.services', [])

.factory('LobbyService', ['$http',function($http){
  var gamers = []; //Private Variable
  return {
    pairAll: function(callback){
      $http.get("http://localhost:8095/accredit/game/pair").success(function(data) {
        console.log("paired gamers:",data);
        callback(data);
      });
    },
    playAll: function(callback){
      $http.get("http://localhost:8095/accredit/game/play").success(function(data) {
        console.log("played gamers:",data);
        callback(data);
      });
    },
  };
}])

.service('TableService', [function(){
  //
  return {
    getOne: function()
    {
      return  { sgf :"(;FF[4]GM[1]SZ[19]CA[UTF-8]SO[gokifu.com]BC[cn]WC[cn]PB[Gu Li]BR[9p]PW[Shi Yue]WR[5p]KM[7.5]DT[2012-10-21]RE[B+R];B[qd];W[dd];B[pq];W[dq];B[fc]"};
    }
  };
}]);
