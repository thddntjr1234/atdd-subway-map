package subway;

public class LineRequest {
    private Long id;
    private String name;
    private String color;
    private Long upwardStationId;
    private Long downwardStationId;
    private int distance;

    public LineRequest() {
    }

    public LineRequest(Long id, String name, String color, Long upwardStationId, Long downwardStationId, int distance) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.upwardStationId = upwardStationId;
        this.downwardStationId = downwardStationId;
        this.distance = distance;
    }

    public LineRequest(String name, String color, Long upwardStationId, Long downwardStationId, int distance) {
        this.name = name;
        this.color = color;
        this.upwardStationId = upwardStationId;
        this.downwardStationId = downwardStationId;
        this.distance = distance;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setUpwardStationId(Long upwardStationId) {
        this.upwardStationId = upwardStationId;
    }

    public void setDownwardStationId(Long downwardStationId) {
        this.downwardStationId = downwardStationId;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
