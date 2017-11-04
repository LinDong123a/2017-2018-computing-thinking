package info.smartkit;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import info.smartkit.godpaper.go.UUIDAccreditChainCode;
import info.smartkit.godpaper.go.dto.PlayMessage;
import info.smartkit.godpaper.go.service.SocketIoService;
import info.smartkit.godpaper.go.settings.ChainCodeProperties;
import info.smartkit.godpaper.go.settings.ChainCodeVariables;
import info.smartkit.godpaper.go.settings.SocketIoVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@see: https://www.3pillarglobal.com/insights/building-a-microservice-architecture-with-spring-boot-and-docker-part-i
@SpringBootApplication
@EnableSwagger2
public class UUIDAccreditApplication{

	private static final Logger LOG = LoggerFactory.getLogger(UUIDAccreditApplication.class);

	@Autowired ChainCodeProperties chainCodeProperties;
	@Autowired SocketIoService socketIoService;
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("https.protocols", "TLSv1.1");
		SpringApplication.run(UUIDAccreditApplication.class, args);
//		ApplicationContext context = new AnnotationConfigApplicationContext( StompConfig.class );
		//ChainCode stub initialization with default chain name.
		LOG.info("ChainCodeVariables.enabled:"+ChainCodeVariables.enabled);
		LOG.info("ChainCodeVariables.chainName:"+ChainCodeVariables.chainName);
		if (ChainCodeVariables.enabled) {
			new UUIDAccreditChainCode().start(null);
		}
		//ChainCode deploy default?
		//SocketIO...
		Configuration config = new Configuration();
		config.setHostname(SocketIoVariables.ip);
		config.setPort(SocketIoVariables.port);
		SocketIoVariables.server = new SocketIOServer(config);
		SocketIoVariables.server.addEventListener("playEvent", PlayMessage.class, new DataListener<PlayMessage>() {
			@Override
			public void onData(SocketIOClient client, PlayMessage data, AckRequest ackRequest) {
				SocketIoVariables.server.getBroadcastOperations().sendEvent("playEvent", data);
			}
		});

		SocketIoVariables.server.start();
		Thread.sleep(Integer.MAX_VALUE);
		SocketIoVariables.server.stop();
	}

//	@Value("${db_rebuild}")private Boolean db_rebuildMongoData;
	//
	@Bean
	public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator()
	{        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
		//configurable?
		boolean db_rebuildMongoData = true;
		if(db_rebuildMongoData){
			//
			Resource initData = new ClassPathResource("init_aier.json");
			factory.setResources(new Resource[]{initData});
		}else{
			factory.setResources(new Resource[]{});
		}
		return factory;
	}

	/**
	 * A servlet filter that inserts various values retrieved from the incoming http
	 * request into the MDC
	 *
	 * @return {@link FilterRegistrationBean}
	 */
	@Bean
	public FilterRegistrationBean userInsertingMdcFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		MDCInsertingServletFilter userFilter = new MDCInsertingServletFilter();
		registrationBean.setFilter(userFilter);
		return registrationBean;
	}

}
