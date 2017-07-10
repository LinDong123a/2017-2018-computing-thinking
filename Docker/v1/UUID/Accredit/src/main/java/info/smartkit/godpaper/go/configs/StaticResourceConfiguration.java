package info.smartkit.godpaper.go.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"
            ,"classpath:/sgf/","classpath:/savedmodel/","classpath:/AI_FILEs/"};

    /**
     * Add our static resources folder mapping.
     */
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		//
//		registry.addResourceHandler("/**").addResourceLocations(
//				CLASSPATH_RESOURCE_LOCATIONS);
//		//
//		registry.addResourceHandler("/uploads/**").addResourceLocations(
//				"classpath:/uploads/");
//		// Activiti repository resources(diagram picture,process BPM files).
//		// registry.addResourceHandler("/repository/**").addResourceLocations("classpath:/repository/");
//		// Jasper report
//		registry.addResourceHandler("/static/**").addResourceLocations(
//				"classpath:/static/");
//		// registry.addResourceHandler("/reports/**").addResourceLocations("classpath:/reports/");
//		//
//		super.addResourceHandlers(registry);
//	}
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/sgf/**").addResourceLocations(
                "classpath:/sgf/");
        registry.addResourceHandler("/savedmodels/**").addResourceLocations(
                "classpath:/savedmodel/");
        registry.addResourceHandler("/AI_FILEs/**").addResourceLocations(
                "classpath:/AI_FILEs/");
    }


}
