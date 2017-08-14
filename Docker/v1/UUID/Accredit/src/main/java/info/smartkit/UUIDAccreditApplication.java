package info.smartkit;

import info.smartkit.godpaper.go.UUIDAccreditChainCode;
import info.smartkit.godpaper.go.service.ChainCodeService;
import info.smartkit.godpaper.go.settings.ChainCodeProperties;
import info.smartkit.godpaper.go.settings.ChainCodeVariables;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


//@see: https://www.3pillarglobal.com/insights/building-a-microservice-architecture-with-spring-boot-and-docker-part-i
@SpringBootApplication
@EnableSwagger2
public class UUIDAccreditApplication{

	private static Logger LOG = LogManager.getLogger(UUIDAccreditApplication.class);

	@Autowired ChainCodeProperties chainCodeProperties;
	public static void main(String[] args) {
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

}
