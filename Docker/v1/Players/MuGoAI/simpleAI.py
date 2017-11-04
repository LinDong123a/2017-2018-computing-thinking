#!/usr/bin/env python
# -*- coding: utf-8 -*-
import argparse
import argh
import os
import sys
import gtp as gtp_lib
import traceback
import sys

from policy import PolicyNetwork
from strategies import RandomPlayer, PolicyNetworkBestMovePlayer, PolicyNetworkRandomMovePlayer, MCTS
from load_data_sets import DataSet, parse_data_sets

string = 'abcdefghijklmnopqrstuvwxyz'
read_file = os.getcwd() + "/AI_FILE/savedmodel"
data_file = "yyf.sgf"

data_file_path = 'game_database/sgf/'


def AI(msg):
    print("AI(msg) called.")
    global read_file  # Extract information

    data_file = data_file_path + msg['game_id']
    x, y, color = parse_input_msg(msg)
    print(x, y, color)

    # Initialize the policy network
    n = PolicyNetwork(use_cpu=False)
    print("PolicyNetwork init.")
    print("n,read_file:",n,read_file)
    try:
        instance = PolicyNetworkBestMovePlayer(n, read_file)
    except Exception:
        print(traceback.format_exc())
    #instance = PolicyNetworkRandomMovePlayer(n, read_file)
    print("PolicyNetwork instanced.",instance)
    try:
        global gtp_engine
        gtp_engine = gtp_lib.Engine(instance)
        print("GTP Engine get ready.",gtp_engine)
    except Exception:
        print(traceback.format_exc())
    #sys.stderr.write("GTP Enginene ready\n")
    AI_cmd = parse_AI_instruction(color)
    print("AI_cmd parsed.")
    # To see if it has started playing chess and logging
    try:
        data_file_exist = os.path.exists(data_file)
    except Exception:
        print(traceback.format_exc())
    print("os.path.exists?",data_file_exist) 
    #sys.setdefaultencoding('utf-8')
    if os.path.exists(data_file):
        print("os.path.exists(data_file)!")
        rfile = open(data_file, 'r')
        cmd_list = rfile.readlines()
        for cmd in cmd_list:
            cmd = cmd.strip('\n ')
            if cmd == '':
                continue
            print("gtp_engine.send(cmd):", cmd)
            gtp_engine.send(cmd)
        # sys.stdout.write(cmd + '\n')
        # sys.stdout.flush()
        rfile.close()
    # Parse the other side of the chess instructions, write into the record file
    wfile = open(data_file, 'a')
    print("wfiled!!!")
    if msg['msg'][2].lower() == 't' and msg['msg'][3].lower() == 't':
        pass
    else:
        player_cmd = parse_player_input(msg['msg'][0], x, y)
        wfile.write(player_cmd + '\n')
        gtp_engine.send(player_cmd)
    # sys.stdout.write(player_cmd + '\n')
    # sys.stdout.flush()

    gtp_reply = gtp_engine.send(AI_cmd)
    gtp_cmd = parse_AI_input(color, gtp_reply)
    wfile.write(gtp_cmd)
    wfile.close()
    # sys.stdout.write(gtp_reply + '\n')
    # sys.stdout.flush()

    AI_x, AI_y = parse_AI_reply(gtp_reply)

    response = color + '[' + AI_x + AI_y + ']'
    # sys.stdout.write(response)
    # sys.stdout.flush()

    return {'game_id': msg['game_id'], 'msg': response}


def parse_AI_instruction(color):
    return "genmove " + color.upper()


def parse_AI_input(color, gtp_reply):
    return "play " + color.upper() + ' ' + gtp_reply[2:]


def parse_player_input(color, x, y):
    return "play " + color.upper() + ' ' + x.upper() + str(y)


def parse_input_msg(msg):
    global string

    # get the letters of position
    x = msg['msg'][2]
    y = string.index(msg['msg'][3])
    color = ''

    # decide color
    if msg['msg'][0] == 'B':
        color = 'W'
    else:
        color = 'B'

    # deal with the first of location that larger than 'i'
    if x >= 'i':
        x = string[string.index(x) + 1]

    # deal with the opposite allocation of vertical axis
    y = 19 - y

    return x, y, color


def parse_AI_reply(gtp_reply):
    AI_x = gtp_reply[2].lower()
    AI_y = int(gtp_reply[3:])

    if AI_x > 'i':
        AI_x = string[string.index(AI_x) - 1]

    AI_y = 19 - AI_y
    AI_y = string[AI_y]

    return AI_x, AI_y
