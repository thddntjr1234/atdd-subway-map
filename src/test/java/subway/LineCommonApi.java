package subway;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class LineCommonApi {
    public static ExtractableResponse<Response> createLine(LineRequest request) {
        return RestAssured.given().log().all()
                .body(request).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/lines")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> findLines() {
        return RestAssured.given().log().all()
                .when().get("/lines")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> findLineById(Long id) {
        return RestAssured.given().log().all()
                .queryParam("id", id)
                .when().get("/line")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> modifyLine(LineRequest request) {
        return RestAssured.given().log().all()
                .body(request).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/line")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> deleteLine(Long id) {
        return RestAssured.given().log().all()
                .param("id", id)
                .when().delete("/line")
                .then().log().all()
                .extract();
    }
}
