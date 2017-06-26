import logging
logging.basicConfig(format='%(asctime)s %(levelname)-8s %(message)s',level=logging.INFO,datefmt='%Y-%m-%d %H:%M:%S')

import configparser
cf = configparser.ConfigParser()
cf.read("dev.conf")
s = cf.sections()
# logging.info('conf section:',s)
o_jms = cf.options("mqtt")
print('conf options:', o_jms)
o_api = cf.options("api")
print('conf options:', o_api)
v_broker_url = cf.get("mqtt","broker_url")
print("v_broker_url:"+v_broker_url)
v_broker_port = cf.getint("mqtt","broker_port")
print("v_broker_port:",v_broker_port)
v_tag_vs = cf.get("mqtt","tag_vs")
print("v_tag_vs:",v_tag_vs)
v_tag_play = cf.get("mqtt","tag_play")
print("v_tag_play:",v_tag_play)
v_client_id = cf.get("mqtt","client_id")
print("v_client_id:",v_client_id)
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
import plainAI


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
    logging.info("GET tenant user:%s", tenantResponse.body)

#
    # variables
    v_queue_name = tenantResponse.body['topicName']
    # v_player_id = tenantResponse.body['topicName']
    v_player_id = tenantResponse.body['id']
    logging.info("v_queue_name:%s",v_queue_name)
    logging.info("v_player_id:%s", v_player_id)
    v_game_id = ''
    v_player1_id = ''
    v_player2_id = ''
    message_json_s = []

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
        global v_game_id,v_player1_id,v_player2_id,message_json_s
        ##
        logging.info("MQTT on_message,"+msg.topic + " " + str(msg.payload.decode("utf-8")))
        message = str(msg.payload.decode("utf-8"))
        # opponent info
        if message.find(v_tag_vs)!=-1:
            v_game_id = message
            v_player1_id = message.split(v_tag_vs)[0].split(v_client_id)[1]
            print("v_player1_id:", v_player1_id)
            v_player2_id = message.split(v_tag_vs)[1].split(v_client_id)[1]
            print("v_player2_id:", v_player2_id)
            logging.info("VS game id:%s,player1_id:%s,player2_id:%s", v_game_id,v_player1_id,v_player2_id)
            # subscribe the game topic
            client.subscribe(v_game_id)
            logging.info("MQTT subscribe to:%s", v_game_id)
            #  testing
            # client.publish(v_game_id, "594a4c8b6516899e6a30e17f#play#B[cm]")
            # client.publish(v_game_id, "594a4c8b6516899e6a30e17f#play#")
        # game table turn info from game topic
        if message.find(v_tag_play)!=-1:
            logging.info("PLAY play msg raw:%s",message)
            cur_player_id = message.split(v_tag_play)[0]
            print("cur_player_id:",cur_player_id)
            pre_play_msg = message.split(v_tag_play)[1]#maybe null
            logging.info("PLAY current player:%s,pre_play_msg:%s", cur_player_id,pre_play_msg)
            if pre_play_msg=='':
                # first hand
                if v_player_id == v_player1_id: #double check
                    #FIXME:with open database:
                    pre_play_msg = 'B[dp]'
                    # message assemble
                    message_json = {'game_id': v_game_id,
                                    'user_id': v_player_id,
                                    'msg': pre_play_msg}
                    # append message
                    message_json_s.append(message_json)
                    print("assembled message_json_s:",message_json_s)
                    #
                    client.publish(v_game_id, v_player_id + v_tag_play + pre_play_msg)
                    logging.info("PLAY FIRST_HAND.player_id:%s,play_msg:%s", cur_player_id, pre_play_msg)
            else:
                # next round as opponent only
                if cur_player_id != v_player_id:
                    logging.info("PLAY current player id:%s,v_player_id:%s",cur_player_id,v_player_id)
                    # message assemble
                    message_json = {'game_id': v_game_id,
                               'user_id': cur_player_id,
                               'msg': pre_play_msg}
                    # append message
                    message_json_s.append(message_json)
                    print("assembled message_json_s:", message_json_s)
                    # msg response
                    logging.info("send to plainAI message_json_s:%s",message_json_s)
                    result = plainAI.AI(message_json_s)
                    result['user_id'] = v_player_id
                    # result['method'] = 'play'
                    logging.info('PLAY plainAI response:%s', result)
                    # append msa
                    # publish message
                    client.publish(v_game_id, v_player_id + v_tag_play + result['msg'])
                    # client.publish(v_game_id,json.dumps(result))


    # The callback for when this client publishes to the server.
    def on_publish(client, userdata, mid):
        ##
        logging.debug("MQTT message published:%s",str(userdata))


    def on_log(client, userdata, level, buf):
        print(str(level) + " " + str(buf))


    # time.sleep(10)
    client = mqtt.Client()
    client.on_connect = on_connect
    client.on_message = on_message
    client.on_publish = on_publish
    client.on_log = on_log

    client.connect(v_broker_url, v_broker_port, 60)
    #default channel name.
    client.subscribe(v_client_id,0)

    # Blocking call that processes network traffic, dispatches callbacks and
    # handles reconnecting.
    # Other loop*() functions are available that give a threaded interface and a
    # manual interface.
    client.loop_forever()