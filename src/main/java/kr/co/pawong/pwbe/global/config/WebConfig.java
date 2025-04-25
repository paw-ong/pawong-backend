package kr.co.pawong.pwbe.global.config;

import kr.co.pawong.pwbe.global.config.converter.StringToUpKindConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final StringToUpKindConverter stringToUpKindConverter;

    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToUpKindConverter);
    }
}
