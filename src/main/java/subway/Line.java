package subway;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 30, nullable = false)
    private String color;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Section> sections = new ArrayList<>();

    public Line() {
    }

    public Line(String name, String color, Section section) {
        this.name = name;
        this.color = color;
        sections.add(section);
    }

    public void update(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void addSection(Section section) {
        if (!isUpstationAddable(section.getUpwardStation())) {
            throw new IllegalArgumentException("등록할 구간의 상행역은 해당 노선에 등록되어있는 하행 종점역이어야 합니다.");
        }

        if (!isdownStationAddable(section.getDownwardStation())) {
            throw new IllegalArgumentException("이미 해당 노선에 등록되어있는 역은 새로운 구간의 하행역이 될 수 없습니다.");
        }

        sections.add(section);
    }

    private boolean isUpstationAddable(Station upStation) {
        Section section = sections.get(0);
        return section.getDownwardStation().equals(upStation);
    }

    private boolean isdownStationAddable(Station downStation) {
        List<Station> stations = sections.stream()
                .flatMap(section -> Stream.of(section.getUpwardStation(), section.getDownwardStation()))
                .collect(Collectors.toList());

        return !stations.contains(downStation);
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

    public List<Section> getSections() {
        return sections;
    }
}
