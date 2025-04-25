package kr.co.pawong.pwbe.global.config;

import kr.co.pawong.pwbe.global.config.converter.StringToUpKindConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// upKindCd에 대한 client 요청을 UpKindCd enum 타입으로 변환할 수 있도록 하는 configuration
@Configuration
@RequiredArgsConstructor
public class ConversionConfig implements WebMvcConfigurer {
    private final StringToUpKindConverter stringToUpKindConverter;

    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToUpKindConverter);
    }
}
