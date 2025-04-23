package kr.co.pawong.pwbe.config;

import kr.co.pawong.pwbe.config.converter.StringToUpKindConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToUpKindConverter());
    }
}
