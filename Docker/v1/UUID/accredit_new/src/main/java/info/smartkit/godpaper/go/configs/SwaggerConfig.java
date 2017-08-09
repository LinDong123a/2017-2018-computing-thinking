package info.smartkit.godpaper.go.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class SwaggerConfig.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("info.smartkit.godpaper.go.controller"))
//                .paths(PathSelectors.ant("/shiny.guide"))
                .build()
                .apiInfo(apiInfo());
        //				.useDefaultResponseMessages(false)
//				.globalResponseMessage(RequestMethod.GET,
//						new ArrayList(new ResponseMessageBuilder()
//										.code(500)
//										.message("500 message")
//										.responseModel(new ModelRef("Error"))
//										.build(),
//								new ResponseMessageBuilder()
//										.code(403)
//										.message("Forbidden!")
//										.build()));
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "ToyhouseGo REST API",
                "ToyhouseGo description of API.",
                "0.0.1",
                "Terms of service",
                "z@smartkit.info",
                "ToyhouseGo License of API",
                "ToyhouseGo API license URL");
        return apiInfo;
    }


}
