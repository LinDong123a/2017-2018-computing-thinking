cmake_minimum_required(VERSION 3.7)
project(score_estimator)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        test_games/1776378.game
        test_games/2686282.game
        test_games/2686658.game
        test_games/2695200.game
        test_games/2697990.game
        test_games/2704027.game
        test_games/2733454.game
        test_games/2733986.game
        test_games/2743191.game
        test_games/2765606.game
        test_games/2795631.game
        test_games/2797036.game
        test_games/2820787.game
        test_games/2833755.game
        test_games/2901637.game
        test_games/2910148.game
        test_games/2927622.game
        test_games/3029489.game
        test_games/3085363.game
        test_games/3190829.game
        test_games/3227995.game
        test_games/3245224.game
        test_games/3247364.game
        test_games/3285190.game
        test_games/3320531.game
        test_games/3359805.game
        test_games/3365042.game
        test_games/3463236.game
        test_games/3939078.game
        test_games/3941535.game
        test_games/3962847.game
        tools/fetch_ogs_game.py
        Dockerfile
        estimator.cc
        estimator.h
        jsbindings.cc
        main.cc
        Makefile
        README.md)

add_executable(score_estimator ${SOURCE_FILES})