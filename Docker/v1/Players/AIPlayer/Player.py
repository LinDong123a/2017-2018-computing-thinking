import logging
logging.basicConfig()
logging.getLogger().setLevel(logging.DEBUG)

import configparser
cf = configparser.ConfigParser()
cf.read("dev.conf")
s = cf.sections()
print('conf section:', s)
o_jms = cf.options("jms")
print('conf options:', o_jms)
o_api = cf.options("api")
print('conf options:', o_api)
v_broker_url = cf.get("jms","broker_url")
print("v_broker_url:",v_broker_url)
v_queue_name = cf.get("jms","queue_name")
print("v_queue_name:",v_queue_name)
v_restful_url = cf.get("api","restful_url")
print("v_restful_url:",v_restful_url)
v_resource_name_user = cf.get("api","resource_name_user")
print("v_resource_name_user:",v_resource_name_user)
# import UUID-API
from simple_rest_client.api import API

#@see:http://nikipore.github.io/stompest/sync.html#module-stompest.sync.examples
from stompest.config import StompConfig
from stompest.sync import Stomp

#Stomp configure
CONFIG = StompConfig(v_broker_url)

if __name__ == '__main__':
    #First off,UUID REST client post,@see: http://python-simple-rest-client.readthedocs.io/en/0.1.1/quickstart.html
    # create api instance
    api = API(api_root_url = v_restful_url,params = {},headers = {},timeout = 2,append_slash = False,json_encode_body = True)
    # add users resource
    api.add_resource(resource_name=v_resource_name_user)
    # show resource actions
    print(api.user.actions)
    # GET list testing
    getListResponse = api.user.list(body=None, params={}, headers={})
    print("GET user list:",getListResponse)
    # POST one creating
    body = {'email': 'bot@toyhouse.cc', 'fullName': 'bot.toyhouse.cc'}
    # jsonBody = json.dumps(body)
    postResponse = api.user.create(body=body, params={}, headers={'Content-type': 'application/json'})
    print("postResponse:",postResponse)
    #Then,STOMP topic subscribe,@see: https://github.com/mozes/stompest
    client = Stomp(CONFIG)
    client.connect()
    client.send(v_queue_name, 'test message 1'.encode())
    # client.receiveFrame();
    client.subscribe(v_queue_name, {'ack': 'client'})
    # subscribing
    while (True):
        frame = client.receiveFrame()
        print(frame.body)
        client.ack(frame)
    # disconnect
    client.disconnect()