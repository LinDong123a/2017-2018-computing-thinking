angular.module('app.services', [])

.factory('LobbyService', ['$http','envInfo',function($http,envInfo){
  //Private Variable
  console.debug("envInfo:",envInfo);
  return {
    gamerIds : [],
    pairAll: function(callback){
      $http.get(envInfo.api+"game/pair").success(function(data) {
        console.log("paired gamers:",data);
        callback(data);
      });
    },
    playAll: function(callback){
      $http.get(envInfo.api+"game/play").success(function(data) {
        console.log("played gamers:",data);
        callback(data);
      });
    },
    getAll: function(callback){
      $http.get(envInfo.api+"game").success(function(data) {
        console.log("all gamers:",data);
        callback(data);
      });
    },
    dismissAll: function(callback){
      $http.delete(envInfo.api+"game/").success(function(data) {
        console.log("dismissed gamers:",data);
        callback(data);
      });
    }
  };
}])

.service('TableService', ['$http','envInfo',function($http,envInfo){
  //
  return {
    gamerId : '',
    getOne: function(callback){
      $http.get(envInfo.api+"/game").success(function(data) {
        console.log("all gamer info:",data);
        callback(data);
      });
    }
  }
}])
  .service('ChainCodeService', ['$http','envInfo',function($http,envInfo){
    //
    return {
      gamerId : '',
      getOne: function(callback){
        $http.get(envInfo.api+"/chain/query/"+this.gamerId).success(function(data) {
          console.log("one gamer info:",data);
          callback(data);
        });
      }
    }
  }]);
