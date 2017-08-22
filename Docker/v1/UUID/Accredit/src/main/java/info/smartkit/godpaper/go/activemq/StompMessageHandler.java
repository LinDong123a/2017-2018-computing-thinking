package info.smartkit.godpaper.go.activemq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.smartkit.godpaper.go.dto.PlayMessage;
import info.smartkit.godpaper.go.service.MqttService;
import info.smartkit.godpaper.go.service.StompService;
import info.smartkit.godpaper.go.settings.MqttQoS;
import info.smartkit.godpaper.go.settings.MqttVariables;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.projectodd.stilts.stomp.StompException;
import org.projectodd.stilts.stomp.StompMessage;
import org.projectodd.stilts.stomp.client.MessageHandler;

import javax.jms.JMSException;

public class StompMessageHandler implements MessageHandler {

    private  StompService stompService;
    private  MqttService mqttService;
    private String topic;
    //
    private String v_game_topic;
    private String v_player1_id;
    private String v_player2_id;
    private String v_player_id;
    private String message_json;
    //    private static Logger LOG = LogManager.getLogger(StompMessageHandler.class);
    @Override
    public void handle(StompMessage message) {
//        LOG.debug("handle StompMessage content:"+message.getContentAsString());
        String content = message.getContentAsString();
        System.out.println("handle StompMessage content:"+content);
        // opponent info
        if(content.indexOf(MqttVariables.tag_vs)!=-1){
            v_game_topic = content;
            try {
                stompService.subscribe(v_game_topic);
                mqttService.subscribe(v_game_topic);
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (StompException e) {
                e.printStackTrace();
            } catch (MqttException e) {
                e.printStackTrace();
            }
            //        if message.find(v_tag_vs)!=-1:
//        v_game_id = message;
//        v_player1_id = content.split(MqttVariables.tag_vs)[0].split(MqttVariables.clientId)[1];
//        print("v_player1_id:", v_player1_id)
//        v_player2_id = content.split(MqttVariables.tag_vs)[1].split(MqttVariables.clientId)[1];
//        print("v_player2_id:", v_player2_id)
//        logging.info("VS game id:%s,player1_id:%s,player2_id:%s", v_game_id,v_player1_id,v_player2_id)
//            # subscribe the game topic
//        client.subscribe(v_game_id,2)
//        logging.info("MQTT subscribe to:%s", v_game_id)
            v_player1_id = content.split(MqttVariables.tag_vs)[0].split(MqttVariables.clientId)[1];
//        print("v_player1_id:", v_player1_id)
            System.out.println("v_player1_id:" + v_player1_id);
            v_player2_id = content.split(MqttVariables.tag_vs)[1].split(MqttVariables.clientId)[1];
            System.out.println("v_player2_id:" + v_player2_id);
            //assume AI player first
            v_player_id = v_player1_id;
//               #FIXME:with open database:

            String fix_play_msg = "B[dp]";
            try {
                mqttService.publish(v_game_topic,v_player1_id + MqttVariables.tag_play + fix_play_msg,MqttQoS.EXCATLY_ONCE.getIndex());
            } catch (MqttException e) {
                e.printStackTrace();
            }
            try {
                stompService.publish(v_game_topic, v_player1_id + MqttVariables.tag_play + fix_play_msg, MqttQoS.EXCATLY_ONCE.getIndex());
            } catch (StompException e) {
                e.printStackTrace();
            }
        }

        if(content.indexOf(MqttVariables.tag_play)!=-1) {
            System.out.println("PLAY play msg raw:" + content);
//            logging.info("PLAY play msg raw:%s",message)
//            cur_player_id = message.split(v_tag_play)[0]
            String cur_player_id = content.split(MqttVariables.tag_play)[0];
//            print("cur_player_id:",cur_player_id)
            System.out.println("cur_player_id:" + cur_player_id);
//            pre_play_msg = message.split(v_tag_play)[1]#maybe null
            String pre_play_msg = content.split(MqttVariables.tag_play)[1];
//            logging.info("PLAY current player:%s,pre_play_msg:%s", cur_player_id,pre_play_msg)
            System.out.println("PLAY current player:" + cur_player_id + ",pre_play_msg:" + pre_play_msg);
//            if pre_play_msg=='':
//            if (pre_play_msg == "") {
//                # first hand
//            if v_player_id == v_player1_id: #double check
//                    #FIXME:with open database:
                pre_play_msg = "B[dp]";
//                    # message assemble
                ObjectMapper objectMapper = new ObjectMapper();
                PlayMessage playMessage = new PlayMessage(v_game_topic, v_player_id, pre_play_msg);
//            message_json = {'game_id': v_game_id,
//                    'user_id': v_player_id,
//                    'msg': pre_play_msg}
                try {
                    message_json = objectMapper.writeValueAsString(playMessage);
                    System.out.println("message_json:" + message_json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
//                    # append message
//            message_json_s.append(message_json)
//                    # print("assembled message_json_s:",message_json_s)
//                    #
//            client.publish(v_game_id, v_player_id + v_tag_play + pre_play_msg)
                try {
                    stompService.publish(v_game_topic, v_player_id + MqttVariables.tag_play + pre_play_msg, MqttQoS.EXCATLY_ONCE.getIndex());
                } catch (StompException e) {
                    e.printStackTrace();
                }
                try {
                    mqttService.publish(v_game_topic, v_player_id + MqttVariables.tag_play + pre_play_msg, MqttQoS.EXCATLY_ONCE.getIndex());
                } catch (MqttException e) {
                    e.printStackTrace();
                }
//            logging.info("PLAY FIRST_HAND.player_id:%s,play_msg:%s", cur_player_id, pre_play_msg)
                System.out.println("PLAY FIRST_HAND.player_id:" + cur_player_id + ",play_msg:" + pre_play_msg);
//            else:
//            # next round as opponent only
//            if cur_player_id != v_player_id:
//            logging.info("PLAY current player id:%s,v_player_id:%s",cur_player_id,v_player_id)
//                    # message assemble for sending
//                    message_json_send = {'game_id': v_game_id,
//                    'user_id': cur_player_id,
//                    'msg': pre_play_msg}
//                    # append message
//            message_json_s.append(message_json_send)
//                    # print("assembled message_json_s:", message_json_s)
//                    # msg response
//            logging.info("send to plainAI message_json_s:%s",message_json_s)
//            rank_model_path = os.getcwd() + "/AI_FILEs/"+str(v_player_rank)+ "/savedmodel"
//            logging.info("rank_model_path:%s", rank_model_path)
//            logging.info("v_player_policy:%s", v_player_policy)
//            result = plainAI.AI(message_json_s,rank_model_path,v_player_policy)
//            result['user_id'] = v_player_id
//                    # result['method'] = 'play'
//            logging.info('PLAY plainAI response:%s', result)
//                    # message assemble for response
//                    message_json_resp = {'game_id': v_game_id,
//                    'user_id': v_player_id,
//                    'msg': result['msg']}
//             # append message
//            message_json_s.append(message_json_resp)
//                    # publish message
//            client.publish(v_game_id, v_player_id + v_tag_play + result['msg'])
//                    # client.publish(v_game_id,json.dumps(result))
//                else:
//            logging.info("##PLAY current player:%s,pre_play_msg:%s", cur_player_id, pre_play_msg)
            }

//        if(content.indexOf(MqttVariables.!=-1){
//
//        }
    }
//    private String userId="594a4c8b6516899e6a30e17f";
    //
    public StompMessageHandler(StompService stompService,MqttService mqttService,String topic) {
        this.stompService = stompService;
        this.mqttService = mqttService;
        this.topic = topic;
    }
}
