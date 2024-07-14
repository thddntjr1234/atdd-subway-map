package subway;

import javax.persistence.*;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "up_station_id")
    private Station upStation;
    @OneToOne
    @JoinColumn(name = "down_station_id")
    private Station downStation;
    @Column(nullable = false)
    private int distance;

    public Section() {
    }

    public Section(Station upwardStation, Station downwardStation, int distance) {
        this.upStation = upwardStation;
        this.downStation = downwardStation;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public Station getUpwardStation() {
        return upStation;
    }

    public Station getDownwardStation() {
        return downStation;
    }

    public int getDistance() {
        return distance;
    }
}
