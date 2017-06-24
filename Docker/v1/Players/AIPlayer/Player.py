import logging
logging.basicConfig(format='%(asctime)s %(levelname)-8s %(message)s',level=logging.INFO,datefmt='%Y-%m-%d %H:%M:%S')

import configparser
cf = configparser.ConfigParser()
cf.read("dev.conf")
s = cf.sections()
# logging.info('conf section:',s)
o_jms = cf.options("jms")
print('conf options:', o_jms)
o_api = cf.options("api")
print('conf options:', o_api)
v_broker_url = cf.get("jms","broker_url")
print("v_broker_url:"+v_broker_url)
v_broker_port = cf.getint("jms","broker_port")
print("v_broker_port:",v_broker_port)
v_queue_name = cf.get("jms","queue_name")
print("v_queue_name default:",v_queue_name)
v_restful_url = cf.get("api","restful_url")
print("v_restful_url:",v_restful_url)
v_resource_name_user = cf.get("api","resource_name_user")
print("v_resource_name_user:",v_resource_name_user)
# if docker_links

import os
import json
from urllib.parse import urlparse
# import docker_links
# links = docker_links.parse_links(os.environ)
# print("docker_links:",json.dumps(links, indent=4))
# import UUID-API
from simple_rest_client.api import API


# see: https://pypi.python.org/pypi/paho-mqtt/1.1
import paho.mqtt.client as mqtt

import simpleAI


# create EventResource with custom actions
from simple_rest_client.resource import Resource
# create EventResource with custom actions
class UserResource(Resource):
     actions = {
         'list': {'method': 'GET', 'url': 'user'},
         'tenant': {'method': 'GET', 'url': 'user/tenant'},
}

if __name__ == '__main__':
    #First off,UUID REST client post,@see: http://python-simple-rest-client.readthedocs.io/en/0.1.1/quickstart.html
    # create api instance
    api = API(api_root_url = v_restful_url,params = {},headers = {},timeout = 2,append_slash = False,json_encode_body = True)
    # add users resource
    api.add_resource(resource_name=v_resource_name_user,resource_class=UserResource)
    # show resource actions
    logging.info("api.user.actions:%s",api.user.actions)
    # GET list testing
    # getListResponse = api.user.list(body=None, params={}, headers={})
    # logging.info("GET user list:%s",getListResponse)
    # # POST one creating
    # body = {'email': 'bot@toyhouse.cc', 'fullName': 'bot.toyhouse.cc'}
    # jsonBody = json.dumps(body)
    # postResponse = api.user.create(body=body, params={}, headers={'Content-type': 'application/json'})
    # logging.info("postResponse.body:%s", postResponse.body)
    # Tenant one without creating
    tenantResponse = api.user.tenant(body=None, params={}, headers={})
    logging.info("GET tenant user:%s", tenantResponse)

#
    # variables
    v_queue_name = tenantResponse.body['topicName']
    v_player_id = tenantResponse.body['id']
    logging.info("v_queue_name:%s",v_queue_name)
    logging.info("v_player_id:%s", v_player_id)

    # paho-mqtt
    # The callback for when the client receives a CONNACK response from the server.
    def on_connect(client, userdata, flags, rc):
        logging.info("MQTT Connected with result code:%s ",rc)

        # Subscribing in on_connect() means that if we lose the connection and
        # reconnect then subscriptions will be renewed.
        client.subscribe(v_queue_name)
        logging.info("MQTT subscribe to:%s",v_queue_name)
        # # testing
        # client.publish(v_queue_name, "#vs#594a4c8b6516899e6a30e17f")


    # The callback for when a PUBLISH message is received from the server.
    def on_message(client, userdata, msg):
        #
        logging.info("MQTT on_message,"+msg.topic + " " + str(msg.payload))
        message = str(msg.payload)
        # opponent info
        tag_vs = "_vs_"
        tag_play = "_play_"
        if message.find(tag_vs)!=-1:
            v_game_id = message
            # v_player_id = message.split(tag_vs)[0]
            # v_opponent_id = message.split(tag_vs)[1]
            logging.info("VS game id:%s", v_game_id)
            # subscribe the game topic
            client.subscribe(v_game_id)
            #  testing
            # client.publish(v_game_id, "594a4c8b6516899e6a30e17f#play#B[cm]")
            # client.publish(v_game_id, "594a4c8b6516899e6a30e17f#play#")
        # game table turn info from game topic
        if message.find(tag_play)!=-1:
            logging.info("PLAY play msg raw:%s",message)
            cur_player_id = message.split(tag_vs)[0]
            play_msg = message.split(tag_play)[1]#'B[cm]'
            logging.info("PLAY current play message:player_id:%s,play_msg:%s", cur_player_id,play_msg)
            # first hand
            if cur_player_id == v_player_id:
                ## fixture with open database:
                client.publish(v_game_id, v_player_id + tag_play + "#B[cm]")
            ## next round

            # opponent only
            if cur_player_id != v_player_id:
                logging.info("current playing id:%s",cur_player_id)
                # assemble game message.
                global v_game_id
                message_json = {'game_id': v_game_id,
                           'user_id': cur_player_id,
                           'msg': play_msg}
                # msg response
                result = simpleAI.AI(message_json)
                result['user_id'] = v_player_id
                # result['method'] = 'play'
                logging.info('PLAY simpleAI response:%s', result)
                # publish message
                client.publish(v_game_id, v_player_id + tag_play + result['msg'])
                # client.publish(v_game_id,json.dumps(result))


    # The callback for when this client publishes to the server.
    def on_publish(client, userdata, mid):
        ##
        logging.info("MQTT message published:%s",str(userdata))


    def on_log(client, userdata, level, buf):
        print(str(level) + " " + str(buf))


    # time.sleep(10)
    client = mqtt.Client()
    client.on_connect = on_connect
    client.on_message = on_message
    client.on_publish = on_publish
    client.on_log = on_log

    client.connect(v_broker_url, v_broker_port, 60)
    client.subscribe(v_queue_name,2)

    # Blocking call that processes network traffic, dispatches callbacks and
    # handles reconnecting.
    # Other loop*() functions are available that give a threaded interface and a
    # manual interface.
    client.loop_forever()