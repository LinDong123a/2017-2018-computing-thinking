angular.module('app.services', [])

.factory('LobbyService', [function(){
  var people = []; //Private Variable
  return {
    getAll: function() {
      // return $http.get("path/to/resource").then(function(response){
      //   people = response;
      //   return response;
      // });
      return [
        {title: "UUID001 VS UUID 002",id:"uuid001"},
        {title: "UUID003 VS UUID 004",id:"uuid002"},
        {title: "UUID005 VS UUID 006",id:"uuid003"}
      ];
    }
  }

}])

.service('TableService', [function(){
  //
  return {
    getOne: function()
    {
      return  { sgf :"(;FF[4]GM[1]SZ[19]CA[UTF-8]SO[gokifu.com]BC[cn]WC[cn]PB[Gu Li]BR[9p]PW[Shi Yue]WR[5p]KM[7.5]DT[2012-10-21]RE[B+R];B[qd];"};
    }
  }
}]);
