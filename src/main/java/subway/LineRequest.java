package subway;

import java.util.List;

public class LineRequest {
    private String name;
    private String color;
    private Long upwardStationId;
    private Long downwardStationId;
    private int distance;

    public LineRequest() {
    }

    public LineRequest(String name, String color, Long upwardStationId, Long downwardStationId, int distance) {
        this.name = name;
        this.color = color;
        this.upwardStationId = upwardStationId;
        this.downwardStationId = downwardStationId;
        this.distance = distance;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Long getUpwardStationId() {
        return upwardStationId;
    }

    public Long getDownwardStationId() {
        return downwardStationId;
    }

    public int getDistance() {
        return distance;
    }
}
