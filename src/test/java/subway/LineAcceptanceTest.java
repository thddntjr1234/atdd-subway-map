package subway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@DisplayName("지하철 노선 관련 기능")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@Sql(scripts = "/line-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
     * 노선 생성 공통 메서드
     */
    private void initLines() {
        LineRequest request = new LineRequest("7호선", "bg-red-600", 1L, 2L, 10);
        LineCommonApi.createLine(request);
        request = new LineRequest("인천1호선", "bg-blue-600", 3L, 4L, 12);
        LineCommonApi.createLine(request);
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

    /**
     * Given: 여러 개의 지하철 노선이 등록되어 있고,
     * When: 관리자가 지하철 노선 목록을 조회하면,
     * Then: 모든 지하철 노선 목록이 반환된다.
     */
    @DisplayName("지하철 노선 목록을 조회한다.")
    @Test
    void findLines() {
        //given
        initLines();

        //when
        List<LineResponse> lines = LineCommonApi.findLines().jsonPath().getList(".", LineResponse.class);
        List<String> names = lines.stream().map(LineResponse::getName).collect(Collectors.toList());

        //then
        assertThat(names).containsExactly("7호선", "인천1호선");
    }

    /**
     * Given: 특정 지하철 노선이 등록되어 있고,
     * When: 관리자가 해당 노선을 조회하면,
     * Then: 해당 노선의 정보가 반환된다.
     */
    @DisplayName("지하철 노선을 조회한다")
    @Test
    void findLine() {
        //given
        initLines();

        //when
        var response = LineCommonApi.findLineById(1L);
        LineResponse line = response.as(LineResponse.class);

        //then
        assertThat(line.getId()).isEqualTo(1L);
        assertThat(line.getName()).isEqualTo("7호선");
        assertThat(line.getStations()).containsExactly(new StationResponse(1L, "장암역"), new StationResponse(2L, "석남역"));
    }

    /**
     * Given: 특정 지하철 노선이 등록되어 있고,
     * When: 관리자가 해당 노선을 수정하면,
     * Then: 해당 노선의 정보가 수정된다.
     */
    @DisplayName("지하철 노선을 수정한다")
    @Test
    void modifyLine() {
        //given
        initLines();

        var response = LineCommonApi.findLineById(1L);
        LineResponse beforeLine = response.as(LineResponse.class);

        //when
        var request = new LineRequest(beforeLine.getId(), "신 7호선", "bg-red-300", beforeLine.getStations().get(1).getId(), beforeLine.getStations().get(0).getId(), 5);
        LineCommonApi.modifyLine(request);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        //then
        LineResponse afterLine = LineCommonApi.findLineById(1L).as(LineResponse.class);
        assertThat(beforeLine).isNotEqualTo(afterLine);
    }

    @DisplayName("지하철 노선을 삭제한다")
    @Test
    void deleteLine() {
        //given
        initLines();

        //when
        LineCommonApi.deleteLine(1L);

        //then
        var lines = LineCommonApi.findLines()
                .jsonPath()
                .getList("name", String.class);

        assertThat(lines).doesNotContain("7호선");
    }
}
