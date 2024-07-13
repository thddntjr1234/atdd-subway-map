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
    public LineResponse saveLine(LineCreateRequest request) {
        Station upwardStation = stationService.findByStationId(request.getUpwardStationId());
        Station downwardStation = stationService.findByStationId(request.getDownwardStationId());

        Line line = lineRepository.save(new Line(request.getName(), request.getColor(), upwardStation, downwardStation, request.getDistance()));

        return LineResponse.of(line);
    }

    public List<LineResponse> findLines() {
        return lineRepository.findAll().stream()
                .map(LineResponse::of)
                .collect(Collectors.toList());
    }

    public LineResponse findLineById(Long id) {
         Line line = lineRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("조회할 노선이 존재하지 않습니다.");
         });

         return LineResponse.of(line);
    }

    @Transactional
    public void updateLine(Long id, LineUpdateRequest request) {
        Line line = lineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("수정할 노선이 존재하지 않습니다."));

        line.update(request.getName(), request.getColor());
        lineRepository.save(line);
    }

    @Transactional
    public void deleteLine(Long id) {
        lineRepository.deleteById(id);
    }
}
