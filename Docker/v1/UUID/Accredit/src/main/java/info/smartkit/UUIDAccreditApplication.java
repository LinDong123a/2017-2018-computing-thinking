package info.smartkit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@see: https://www.3pillarglobal.com/insights/building-a-microservice-architecture-with-spring-boot-and-docker-part-iii
@SpringBootApplication
@EnableSwagger2
public class UUIDAccreditApplication {

	public static void main(String[] args) {
		System.setProperty("https.protocols", "TLSv1.1");
		SpringApplication.run(UUIDAccreditApplication.class, args);
	}
}
