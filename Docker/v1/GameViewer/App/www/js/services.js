angular.module('app.services', [])

.factory('LobbyService', ['$http','envInfo',function($http,envInfo){
  //Private Variable
  return {
    gamerIds : [],
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

  .factory('GameService', ['$http','envInfo',function($http,envInfo){
    //Private Variable
    return {
      gamerIds : [],
      rPlayerId: null,//current running player.
      curGamerId : null,//current running gamer.
      curAgentId : "mugo",//current training agent.
      rGamerNum: 5,
      tenUserId:null,//tenant by user id
      curSgfObj:null,//current sgf object.
      pairAll: function(callback){
        $http.get(envInfo.api.url+"/game/pair").success(function(data) {
          console.log("paired gamers:",data);
          callback(data);
        });
      }
      ,
      runPlayer: function(callback){
        $http.get(envInfo.api.url+"/docker/run/player/"+this.rPlayerId).success(function(data) {
          console.log("runPlayer:",data);
          callback(data);
        });
      },
      trainAgent: function(callback){
        $http.get(envInfo.api.url+"/docker/train/agent/"+this.curAgentId).success(function(data) {
          console.log("trainAgent:",data);
          callback(data);
        });
      }
      ,
      playOne: function(callback){
        $http.get(envInfo.api.url+"/game/play/"+this.curGamerId).success(function(data) {
          console.log("played one gamer:",data);
          callback(data);
        });
      }
      ,
      playAll: function(callback){
        $http.get(envInfo.api.url+"/game/play").success(function(data) {
          console.log("played gamers:",data);
          callback(data);
        });
      },
      rPlayAll: function(callback){
        $http.get(envInfo.api.url+"/game/play/r/"+this.rGamerNum).success(function(data) {
          console.log("r played gamers:",data);
          callback(data);
        });
      },
      getAll: function(callback){
        $http.get(envInfo.api.url+"/game").success(function(data) {
          console.log("all gamers:",data);
          callback(data);
        });
      },
      getOne: function(callback){
        $http.get(envInfo.api.url+"/game/"+this.curGamerId).success(function(data) {
          console.log("gamer one:",data);
          callback(data);
        });
      }
      ,
      getSgf: function(callback){
        $http.get(envInfo.api.url+"/game/sgf/"+this.curGamerId).success(function(data) {
          console.log("gamer sgf:",data);
          callback(data);
        });
      }
      ,
      deleteOne: function(callback){
        $http.delete(envInfo.api.url+"/game/"+this.curGamerId).success(function(data) {
          console.log("gamer one:",data);
          callback(data);
        });
      }
      ,
      getSgfStr: function(callback){
        $http.get(envInfo.api.url+"/game/sse/sgf/"+this.curGamerId).success(function(data) {
          console.log("sse gamer sgf:",data);
          callback(data);
        });
      }
      ,
      tenantUser:function(callback){
        $http.get(envInfo.api.url+"/user/tenant/"+this.tenUserId).success(function(data) {
          console.log("one user tenanted.:",data);
          callback(data);
        });
      }
      ,
      updateSgfObj: function(callback){
        $http.put(envInfo.api.url+"/game/sgf/"+this.curGamerId,this.curSgfObj).success(function(data) {
          console.log("sse gamer sgf:",data);
          callback(data);
        });
      }
    };
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
  .service('AierService', ['$http','envInfo',function($http,envInfo){
    //
    return {
      aierId : '',
      anewAier : null,
      curAgentId : "mugo",
      curStatusIndex: 3,//or 0
      getOne: function(callback){
        $http.get(envInfo.api.url+"/ai"+this.aierId).success(function(data) {
          console.log("one Aier info:",data);
          callback(data);
        })
      },
      getAll: function(callback){
        $http.get(envInfo.api.url+"/ai").success(function(data) {
          console.log("all aiers:",data);
          callback(data);
        });
      },
      getAllByStatus: function(callback){
        $http.get(envInfo.api.url+"/ai/status/"+this.curStatusIndex).success(function(data) {
          console.log("all aiers:",data);
          callback(data);
        });
      },
      createOne:function(callback) {
        $http.post(envInfo.api.url + "/ai", this.anewAier).success(function (data) {
          console.log("one user created:", data);
          callback(data);
        });
      },
      runAgent: function(callback){
        $http.get(envInfo.api.url+"/docker/run/agent/"+this.curAgentId).success(function(data) {
          console.log("runAgent:",data);
          callback(data);
        });
      },
      trainAgent: function(callback){
        $http.get(envInfo.api.url+"/docker/train/agent/"+this.curAgentId).success(function(data) {
          console.log("runAgent:",data);
          callback(data);
        });
      },
    }
  }])
  .factory('WpWikiService',['$http','envInfo',function($http,envInfo) {
    return {
      anewWpPost:null,
      postsUrl:null,
      get: function(url) {
        return $http.jsonp( url );
      },
      getAuth: function(base64, url) {

        $http.defaults.headers.common['Authorization'] = 'Basic ' + base64;

        var req = { method:'GET', url: url }

        return $http( req );
      },
      createPost: function(callback) {
        // $http.defaults.headers.common['Authorization'] = 'Basic ' + base64;
        // $http.defaults.headers.common['Content-Type'] = 'application/json';
        // $http.defaults.headers.common['X-WP-Nonce'] = 'nonce';
        //
        $http.post(this.postsUrl,this.anewWpPost).success(function(data) {
          console.log("anew post created:",data);
          //reset $http header
          $http.defaults.headers.common = {};
          callback(data);
        });
      },
    }
  }])

  ///@see: http://forum.ionicframework.com/t/ionicloading-in-http-interceptor/4599/7
  .factory('TrendicityInterceptor',
    function ($injector, $q, $log) {

      var hideLoadingModalIfNecessary = function () {
        var $http = $http || $injector.get('$http');
        if ($http.pendingRequests.length === 0) {
          $injector.get('$ionicLoading').hide();
        }
      };

      return {
        request: function (config) {
          $injector.get('$ionicLoading').show();

          // Handle adding the access_token or auth request.

          return config;
        },
        requestError: function (rejection) {
          hideLoadingModalIfNecessary();
          return $q.reject(rejection);
        },
        response: function (response) {
          hideLoadingModalIfNecessary();
          return response;
        },
        responseError: function (rejection) {
          hideLoadingModalIfNecessary();
          //http status code check
          $log.error("detected what appears to be an server error...", rejection);
          if (rejection.status == 400) {
            rejection.status = 401; // Set the status to 401 so that angular-http-auth inteceptor will handle it
          }
          return $q.reject(rejection);
        }
      };
    })
  //@see http://stackoverflow.com/questions/16627860/angular-js-and-ng-swith-when-emulating-enum
  .factory('Enum', [function () {
    var service = {

      // AIvsAI("AIvsAI", 0), AIvsHUMAN("AIvsHUMAN", 1), HUMANvsHUMAN("HUMANvsHUMAN", 2);
      gamerTypes: [
        {
          name: "AIvsAI",
          index: 0
        },
        {
          name: "AIvsHUMAN",
          index: 1
        },
        {
          name: "HUMANvsHUMAN",
          index: 2
        }
      ]
      , getUUID: function (len) {
        // http://www.ietf.org/rfc/rfc4122.txt
        var s = [];
        var hexDigits = "0123456789abcdef";
        for (var i = 0; i < len; i++) {
          s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        // s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
        // s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
        // s[8] = s[13] = s[18] = s[23] = "_";

        var uuid = s.join("");
        return uuid;
      }
      , getTimestamp: function () {
        var now = new Date;
        var utc_timestamp = Date.UTC(now.getUTCFullYear(), now.getUTCMonth(), now.getUTCDate(),
          now.getUTCHours(), now.getUTCMinutes(), now.getUTCSeconds(), now.getUTCMilliseconds());
        return utc_timestamp;
      }
    };
    return service;
  }])
  //@see: http://scottbolinger.com/wp-api-angular-basic-authentication/
.factory('Base64', function() {
  var keyStr = 'ABCDEFGHIJKLMNOP' +
    'QRSTUVWXYZabcdef' +
    'ghijklmnopqrstuv' +
    'wxyz0123456789+/' +
    '=';
  return {
    encode: function (input) {
      var output = "";
      var chr1, chr2, chr3 = "";
      var enc1, enc2, enc3, enc4 = "";
      var i = 0;

      do {
        chr1 = input.charCodeAt(i++);
        chr2 = input.charCodeAt(i++);
        chr3 = input.charCodeAt(i++);

        enc1 = chr1 >> 2;
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
        enc4 = chr3 & 63;

        if (isNaN(chr2)) {
          enc3 = enc4 = 64;
        } else if (isNaN(chr3)) {
          enc4 = 64;
        }

        output = output +
          keyStr.charAt(enc1) +
          keyStr.charAt(enc2) +
          keyStr.charAt(enc3) +
          keyStr.charAt(enc4);
        chr1 = chr2 = chr3 = "";
        enc1 = enc2 = enc3 = enc4 = "";
      } while (i < input.length);

      return output;
    },

    decode: function (input) {
      var output = "";
      var chr1, chr2, chr3 = "";
      var enc1, enc2, enc3, enc4 = "";
      var i = 0;

      // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
      var base64test = /[^A-Za-z0-9\+\/\=]/g;
      if (base64test.exec(input)) {
        alert("There were invalid base64 characters in the input text.\n" +
          "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
          "Expect errors in decoding.");
      }
      input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

      do {
        enc1 = keyStr.indexOf(input.charAt(i++));
        enc2 = keyStr.indexOf(input.charAt(i++));
        enc3 = keyStr.indexOf(input.charAt(i++));
        enc4 = keyStr.indexOf(input.charAt(i++));

        chr1 = (enc1 << 2) | (enc2 >> 4);
        chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
        chr3 = ((enc3 & 3) << 6) | enc4;

        output = output + String.fromCharCode(chr1);

        if (enc3 != 64) {
          output = output + String.fromCharCode(chr2);
        }
        if (enc4 != 64) {
          output = output + String.fromCharCode(chr3);
        }

        chr1 = chr2 = chr3 = "";
        enc1 = enc2 = enc3 = enc4 = "";

      } while (i < input.length);

      return output;
    }
  };
})
;
