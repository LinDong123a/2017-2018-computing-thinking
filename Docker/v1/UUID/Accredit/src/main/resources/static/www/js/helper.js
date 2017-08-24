var DynamicEnvironment = DynamicEnvironment || {};
//Helper functions here.
/**
 * You can have as many environments as you like in here
 * just make sure the host matches up to your hostname including port
 */
var _environment;
var ofc_environment;
var _environments = {
    local: {
        host: 'localhost',
        config: {
            /**
             * Add any config properties you want in here for this environment
             */
            api_ip: '172.20.10.12'
            ,api_port:'8095'
            ,api_context:'/accredit'
            ,mqtt_ip: '172.20.10.12'
            ,mqtt_port:'1883'
            ,wp_ip:'172.20.10.12'
            ,sas_ip:'172.20.10.12'//simpleAIServer
        }
    },
    dev: {
        host: '172.17.0.1',
        config: {
            /**
             * Add any config properties you want in here for this environment
             */
            api_ip: '172.17.0.1'
            ,api_port:'8095'
            ,api_context:'/accredit'
            ,mqtt_ip:'172.17.0.1'
            ,mqtt_port:'1883'
            ,wp_ip:'172.17.0.1'
            ,sas_ip:'172.17.0.1'//simpleAIServer
        }
    },
    test: {
        host: '118.190.152.88',
        config: {
            /**
             * Add any config properties you want in here for this environment
             */
            api_ip: '118.190.152.88'
            ,api_port:'8095'
            ,api_context:'/accredit'
            ,mqtt_ip:'118.190.152.88'
            ,mqtt_port:'1883'
            ,wp_ip:'118.190.152.88'
            ,sas_ip:'118.190.152.88'//simpleAIServer
        }
    },
    stage: {
        host: '47.92.119.102',
        config: {
            /**
             * Add any config properties you want in here for this environment
             */
            api_ip: '47.92.119.102'
            ,api_port:'8095'
            ,api_context:'/accredit'
            ,mqtt_ip:'47.92.119.102'
            ,mqtt_port:'1883'
            ,wp_ip:'47.92.119.102'
            ,sas_ip:'47.92.119.102'//simpleAIServer
        }
    }
};
_getEnvironment = function () {
    var protocol = location.protocol;
    var slashes = protocol.concat("//");
    // var host = slashes.concat(window.location.hostname);
    var host = window.location.host;
    console.log("host:"+host);
    var hostname = window.location.hostname;
    console.log("hostname:"+hostname);
    if (_environment) {
        return _environment;
    }

    for (var environment in _environments) {
        if (typeof _environments[environment].host && _environments[environment].host == hostname) {
          _environment = environment;
          return _environment;
        }
    }
  //out of configure:
  ofc_environment = {
    host: hostname,
    config: {
      api_ip: hostname
      , api_port: host.split(":")[1]
      , api_context: '/accredit'
      , mqtt_ip: hostname
      , mqtt_port: '1883'
    }
  }
  console.log("out of config _environment:",ofc_environment);
  return _environment;
  // return "local";//default
};
DynamicEnvironment.get = function (property) {
  //out of config:
  if(_environments[_getEnvironment()]) {
    var result = _environments[_getEnvironment()].config[property];
  }else{
    var result = ofc_environment.config[property];
  }
    //var result = _environments["test"].config[property];
    console.log("DynamicEnvironment.get():",result);
    return result;
};
