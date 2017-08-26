#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/6/6 9:46
# @Author  : cyj
# @Site    : 
# @File    : server.py
# @Software: PyCharm

from flask import Flask, render_template, session, request

import simpleAI
import plainAI
import json

app = Flask(__name__, template_folder='./')
app.config['SECRET_KEY'] = 'secret!'

def jdefault(o):
    return o.__dict__

@app.route('/', methods=['POST'])
def post_info():
    print('receive AI message.request.data:', request.data)
    message_json_str = json.dumps(request.data.decode())
    print('receive AI message.message_json_str:', message_json_str)
    message_json_dict = json.loads(json.loads(message_json_str))
    print('receive AI message.message_json_dict:', message_json_dict)
    print('receive AI message.message_json_dict.type:', type(message_json_dict))
    print('receive AI message.message_json_dict.game_id:', message_json_dict['game_id'])
    # message = {'game_id': request.form.get('game_id', 'wrong_game_id'),
    #             'user_id': request.form.get('user_id', 'wrong_user_id'), 'msg': request.form.get('msg', 'wrong_msg')}
    # print("send to AI message_json:",message)
    # result = simpleAI.AI(message)
    moves = message_json_dict['msg'].split(";")
    print("send to AI moves:",moves)
    result = plainAI.AI(message_json_dict)
    result['user_id'] = 'MuGo'
    result['method'] = 'play'
    print('response AI message:', result)
    return json.dumps(result)


if __name__ == '__main__':
    # listen all requests form port 6000
    app.run('0.0.0.0', port=6001)
