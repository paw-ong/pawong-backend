package kr.co.pawong.pwbe.adoption.application.service.support;

import kr.co.pawong.pwbe.adoption.application.service.dto.request.AdoptionSearchCondition.Region;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class AdoptionSearchMapperTest {

  @Test
  void 도시만_문자열로_입력된_경우_Region_객체로_파싱() {
    // given
    String onlyCity = "서울특별시";
    // when
    Region result = AdoptionSearchMapper.parseRegion(onlyCity);
    // then
    assertThat(result.getCity()).isEqualTo("서울특별시");
    assertThat(result.getDistrict()).isEqualTo(null);
  }

  @Test
  void 도시와_군구가_문자열로_입력된_경우_Region_객체로_파싱() {
    // given
    String cityAndDistrict = "서울특별시 용산구";
    // when
    Region result = AdoptionSearchMapper.parseRegion(cityAndDistrict);
    // then
    assertThat(result.getCity()).isEqualTo("서울특별시");
    assertThat(result.getDistrict()).isEqualTo("용산구");
  }

  @Test
  void 빈_문자열_입력() {
    // given
    String blank = "";
    // when
    Region result = AdoptionSearchMapper.parseRegion(blank);
    // then
    assertThat(result.getCity()).isEqualTo(null);
    assertThat(result.getDistrict()).isEqualTo(null);
  }

}