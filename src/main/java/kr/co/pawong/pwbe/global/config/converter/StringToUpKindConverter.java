package kr.co.pawong.pwbe.global.config.converter;

import kr.co.pawong.pwbe.adoption.enums.UpKindCd;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUpKindConverter implements Converter<String, UpKindCd> {
    @Override
    public UpKindCd convert(String source) {
        return UpKindCd.fromValue(source);
    }
}
