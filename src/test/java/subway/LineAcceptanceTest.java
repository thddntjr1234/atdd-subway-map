package subway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@DisplayName("지하철 노선 관련 기능")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class LineAcceptanceTest {

    /**
     * 초기 지하철 역 생성
     */
    @BeforeEach
    void initStations() {
        Arrays.asList("장암역", "석남역", "계양역", "송도달빛축제공원역")
                .forEach(StationCommonApi::createStation);
    }

    /**
     * Given: 새로운 지하철 노선 정보를 입력하고,
     * When: 관리자가 노선을 생성하면,
     * Then: 해당 노선이 생성되고 노선 목록에 포함된다.
     */
    @DisplayName("지하철 노선을 등록한다.")
    @Test
    void createLine() {
        //given
        LineRequest request = new LineRequest("7호선", "bg-red-600", 1L, 2L, 10);

        var response = LineCommonApi.createLine(request);

        //when
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        //then
        LineResponse lineResponse = response.as(LineResponse.class);

        // stations 필드로 인해 내부 필드 일부를 검증
        assertThat(lineResponse.getId()).isEqualTo(1L);
        assertThat(lineResponse.getStations()).contains(new StationResponse(1L, "장암역"), new StationResponse(2L, "석남역"));
    }
}
