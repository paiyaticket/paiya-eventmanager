package events.paiya.eventmanager.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                            "http://localhost:4200", 
                            "http://127.0.0.1:4200" , 
                            "https://paiya-ticket--paiya-413605.us-central1.hosted.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
