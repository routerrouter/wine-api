package wine.com.br.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	
	@Bean
	public OpenAPI openApiConfig()
	{
		return new OpenAPI().info(new Info().title("Wine RESTFul Web Services")
				.version("1.0.0")
				.description("R for RESTFul Web Services")
				.termsOfService("http://wine.com/terms")
				.license(new License().name("Wine License").url("http://wine.com")));
	}
	
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
	}
}
