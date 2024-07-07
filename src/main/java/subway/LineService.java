package subway;

import org.springframework.stereotype.Service;

@Service
public class LineService {
    private StationService stationService;
    private LineRepository lineRepository;

    public LineService(StationService stationService, LineRepository lineRepository) {
        this.stationService = stationService;
        this.lineRepository = lineRepository;
    }

    public LineResponse saveLine(LineRequest lineRequest) {
        Station upwardStation = stationService.findByStationId(lineRequest.getUpwardStationId());
        Station downwardStation = stationService.findByStationId(lineRequest.getDownwardStationId());

        Line line = lineRepository.save(new Line(lineRequest.getName(), lineRequest.getColor(), upwardStation, downwardStation, lineRequest.getDistance()));

        return createLineResponse(line);
    }

    private LineResponse createLineResponse(Line line) {
        return new LineResponse(line);
    }
}
