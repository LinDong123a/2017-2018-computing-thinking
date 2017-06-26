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
    }
  };
}])

.service('TableService', ['$http',function($http){
  //
  return {
    getOne: function(callback){
      $http.get("http://localhost:8095/accredit/game/595080c96516891c2d10832c").success(function(data) {
        console.log("one gamer info:",data);
        callback(data);
      });
    }
  }
}]);
