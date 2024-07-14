package subway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

@DisplayName("지하철 구간 관련 기능")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "/line-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SectionAcceptanceTest {
    private StationResponse 계양역;
    private StationResponse 국제업무지구역;
    private StationResponse 송도달빛축제공원역;
    private LineResponse 인천1호선;

    @BeforeEach
    void setup() {
        계양역 = StationCommonApi.createStation("계양역").as(StationResponse.class);
        국제업무지구역 = StationCommonApi.createStation("국제업무지구역").as(StationResponse.class);
        송도달빛축제공원역 = StationCommonApi.createStation("송도달빛축제공원역").as(StationResponse.class);
        인천1호선 = LineCommonApi.createLine(new LineCreateRequest("인천1호선", "bg-blue-400", 계양역.getId(), 국제업무지구역.getId(), 10)).as(LineResponse.class);
    }

    /**
     * Given: 등록된 노선이 있고
     * When: 관리자가 새로운 구간을 노선에 추가하면
     * Then: 노선에 새로운 구간이 추가된다.
     */
    @DisplayName("지하철 구간을 등록한다.")
    @Test
    void addSection() {
        //given
        SectionCreateRequest request = new SectionCreateRequest(국제업무지구역.getId(), 송도달빛축제공원역.getId(), 3);

        //when
        var response = LineCommonApi.addSection(인천1호선.getId(), request);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        //then
        var line = LineCommonApi.findLineById(인천1호선.getId()).as(LineResponse.class);
        assertThat(line.getStations().size()).isEqualTo(3);

        var addedSection = response.as(SectionResponse.class);
        assertThat(addedSection).isEqualTo(new SectionResponse(국제업무지구역.getId(), 송도달빛축제공원역.getId(), 3));
    }
}
