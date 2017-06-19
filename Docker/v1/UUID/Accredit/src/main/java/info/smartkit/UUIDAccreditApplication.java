package info.smartkit;

import info.smartkit.godpaper.go.activemq.ActivemqReceiver;
import info.smartkit.godpaper.go.activemq.ActivemqSender;
import info.smartkit.godpaper.go.activemq.ActivemqVariables;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@see: https://www.3pillarglobal.com/insights/building-a-microservice-architecture-with-spring-boot-and-docker-part-iii
@SpringBootApplication
@EnableSwagger2
public class UUIDAccreditApplication {

	private static Logger LOG = LogManager.getLogger(UUIDAccreditApplication.class);

	public static void main(String[] args) {
		System.setProperty("https.protocols", "TLSv1.1");
		SpringApplication.run(UUIDAccreditApplication.class, args);
//ActiveMQ testing
//		LOG.info("ActiveMQ initializing with channel name:" + ActivemqVariables.channelName);
//		 ActivemqSender sender = new ActivemqSender(ActivemqVariables.queueName);
//		 sender.sendMessage("echo");//For testing
//		 ActivemqReceiver receiver = new ActivemqReceiver(ActivemqVariables.queueName);
////		 ActivemqReceiver receiver = ActivemqReceiver.getInstance("SAMPLEQUEUE");
//		 receiver.receiveMessage();
	}
}
