package subway;

import javax.persistence.*;

@Entity
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 30, nullable = false)
    private String color;
    @ManyToOne
    @JoinColumn(name = "upward_station_id")
    private Station upwardStation;
    @ManyToOne
    @JoinColumn(name = "downward_station_id")
    private Station downwardStation;
    @Column(nullable = false)
    private int distance;

    public Line() {
    }

    public Line(Long id, String name, String color, Station upwardStation, Station downwardStation, int distance) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.upwardStation = upwardStation;
        this.downwardStation = downwardStation;
        this.distance = distance;
    }

    public Line(String name, String color, Station upwardStation, Station downwardStation, int distance) {
        this.name = name;
        this.color = color;
        this.upwardStation = upwardStation;
        this.downwardStation = downwardStation;
        this.distance = distance;
    }

    public void update(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Station getUpwardStation() {
        return upwardStation;
    }

    public Station getDownwardStation() {
        return downwardStation;
    }

    public int getDistance() {
        return distance;
    }
}
