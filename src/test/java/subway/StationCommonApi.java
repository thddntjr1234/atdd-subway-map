package subway;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;

public class StationCommonApi {

  public static ExtractableResponse<Response> createStation(Map<String, String> params) {
    return RestAssured.given().log().all()
        .body(params)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().post("/stations")
        .then().log().all()
        .extract();
  }

  public static void deleteStation(Long id) {
    RestAssured.given().log().all()
        .when().delete("/stations/" + id)
        .then().log().all();
  }

  public static ExtractableResponse<Response> findAllStations() {
    return RestAssured.given().log().all()
        .when().get("/stations")
        .then().log().all()
        .extract();
  }
}
