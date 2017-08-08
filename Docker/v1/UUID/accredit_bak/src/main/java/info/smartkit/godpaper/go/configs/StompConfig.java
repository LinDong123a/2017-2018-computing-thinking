package info.smartkit.godpaper.go.configs;

import info.smartkit.godpaper.go.settings.MqttProperties;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.hooks.SpringContextHook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * Created by smartkit on 08/08/2017.
 * @see: https://www.oschina.net/translate/easy-messaging-with-stomp-over-websockets-using-activemq-and-hornetq
 */
@Configuration
public class StompConfig {

        @Autowired MqttProperties mqttProperties;

        @Bean( initMethod = "start", destroyMethod = "stop" )
        public BrokerService broker() throws Exception {
                final BrokerService broker = new BrokerService();
                broker.addConnector( "ws://"+mqttProperties.getIp()+":61614" );
                broker.setPersistent( false );
                broker.setShutdownHooks( Collections.< Runnable >singletonList( new SpringContextHook() ) );

                final ActiveMQTopic topic = new ActiveMQTopic( "TH_59873488ece87924a4b01c7c_vs_TH_59873483ece87924a4b01c7b" );
                broker.setDestinations( new ActiveMQDestination[] { topic }  );

                final ManagementContext managementContext = new ManagementContext();
                managementContext.setCreateConnector( true );
                broker.setManagementContext( managementContext );

                return broker;
        }
}
