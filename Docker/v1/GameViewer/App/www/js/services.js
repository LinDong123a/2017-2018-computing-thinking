angular.module('app.services', [])

.factory('LobbyService', ['$http','envInfo',function($http,envInfo){
  //Private Variable
  return {
    gamerIds : [],
    pairAll: function(callback){
      $http.get(envInfo.api.url+"/game/pair").success(function(data) {
        console.log("paired gamers:",data);
        callback(data);
      });
    },
    playAll: function(callback){
      $http.get(envInfo.api.url+"/game/play").success(function(data) {
        console.log("played gamers:",data);
        callback(data);
      });
    },
    getAll: function(callback){
      $http.get(envInfo.api.url+"/game").success(function(data) {
        console.log("all gamers:",data);
        callback(data);
      });
    },
    dismissAll: function(callback){
      $http.delete(envInfo.api.url+"game/").success(function(data) {
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
      $http.get(envInfo.api.url+"/game").success(function(data) {
        console.log("all gamer info:",data);
        callback(data);
      });
    }
  }
}])
  .service('UserService', ['$http','envInfo',function($http,envInfo){
    //
    return {
      delUserId:null,
      getUsers:function(callback){
        $http.get(envInfo.api.url+"/user").success(function(data) {
          console.log("get users:",data);
          callback(data);
        });
      }
      ,
      createUser:function(callback){
        $http.post(envInfo.api.url+"/user",this.anewUser).success(function(data) {
          console.log("one user created:",data);
          callback(data);
        });
      }
      ,
      deleteUser:function(callback){
        $http.delete(envInfo.api.url+"/user/"+this.delUserId).success(function(data) {
          console.log("one user deleted.:",data);
          callback(data);
        });
      }
    }
  }])
  .service('ChainCodeService', ['$http','envInfo',function($http,envInfo){
    //
    return {
      gamerId : '',
      anewUser : null,
      getOne: function(callback){
        $http.get(envInfo.api.url+"/chain/query/"+this.gamerId).success(function(data) {
          console.log("one gamer info:",data);
          callback(data);
        })
      }

    }
  }])
;
