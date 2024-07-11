package subway;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class LineService {
    private StationService stationService;
    private LineRepository lineRepository;

    public LineService(StationService stationService, LineRepository lineRepository) {
        this.stationService = stationService;
        this.lineRepository = lineRepository;
    }

    @Transactional
    public LineResponse saveLine(LineRequest lineRequest) {
        Station upwardStation = stationService.findByStationId(lineRequest.getUpwardStationId());
        Station downwardStation = stationService.findByStationId(lineRequest.getDownwardStationId());

        Line line = lineRepository.save(new Line(lineRequest.getName(), lineRequest.getColor(), upwardStation, downwardStation, lineRequest.getDistance()));

        return line.toResponse();
    }

    public List<LineResponse> findLines() {
        return lineRepository.findAll().stream()
                .map(Line::toResponse)
                .collect(Collectors.toList());
    }

    public LineResponse findLineById(Long id) {
        return lineRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("조회할 노선이 존재하지 않습니다.");
        }).toResponse();
    }

    @Transactional
    public void modifyLine(LineRequest lineRequest) {
        Line line = lineRepository.findById(lineRequest.getId())
                .orElseThrow(() -> new RuntimeException("수정할 노선이 존재하지 않습니다."));

        Station upwardStation = stationService.findByStationId(lineRequest.getUpwardStationId());
        Station downwardStation = stationService.findByStationId(lineRequest.getDownwardStationId());

        line.update(lineRequest.getId(), lineRequest.getName(), lineRequest.getColor(), upwardStation, downwardStation, lineRequest.getDistance());
        lineRepository.save(line);
    }
}
