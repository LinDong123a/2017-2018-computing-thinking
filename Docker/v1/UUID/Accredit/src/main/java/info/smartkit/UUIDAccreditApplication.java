package info.smartkit;

import info.smartkit.godpaper.go.UUIDAccreditChainCode;
import info.smartkit.godpaper.go.service.ChainCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@see: https://www.3pillarglobal.com/insights/building-a-microservice-architecture-with-spring-boot-and-docker-part-i
@SpringBootApplication
@EnableSwagger2
public class UUIDAccreditApplication{

//	private static Logger LOG = LogManager.getLogger(UUIDAccreditApplication.class);

	@Autowired ChainCodeService chainCodeService;
	public static void main(String[] args) {
		System.setProperty("https.protocols", "TLSv1.1");
		SpringApplication.run(UUIDAccreditApplication.class, args);
		//ChainCode stub initialization with default chain name.
		new UUIDAccreditChainCode().start(null);
		//ChainCode deploy default?
	}

}
