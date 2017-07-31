var DynamicEnvironment = DynamicEnvironment || {};
//Helper functions here.
/**
 * You can have as many environments as you like in here
 * just make sure the host matches up to your hostname including port
 */
var _environment;
var _environments = {
    local: {
        host: 'localhost',
        config: {
            /**
             * Add any config properties you want in here for this environment
             */
            api_ip: 'localhost'
            ,api_port:'8095'
            ,api_context:'/accredit'
            ,mqtt_ip:'127.0.0.1'
            ,mqtt_port:'1883'
        }
    },
    dev: {
        host: '192.168.0.6',
        config: {
            /**
             * Add any config properties you want in here for this environment
             */
            api_ip: '192.168.0.6'
            ,api_port:'8095'
            ,api_context:'/accredit'
            ,mqtt_ip:'192.168.0.6'
            ,mqtt_port:'1883'
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

    return "local";//default
};
DynamicEnvironment.get = function (property) {
    var result = _environments[_getEnvironment()].config[property];
    //var result = _environments["test"].config[property];
    console.log("DynamicEnvironment.get():",result);
    return result;
};
