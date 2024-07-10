package subway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class LineController {
    private LineService lineService;

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    @PostMapping("/lines")
    public ResponseEntity<LineResponse> createLine(@RequestBody LineRequest lineRequest) {
        LineResponse line = lineService.saveLine(lineRequest);
        return ResponseEntity.created(URI.create("/lines/" + line.getId())).body(line);
    }

    @GetMapping("/lines")
    public ResponseEntity<List<LineResponse>> findAllLines() {
        List<LineResponse> lines = lineService.findLines();
        return ResponseEntity.ok(lines);
    }

    @GetMapping("/line")
    public ResponseEntity<LineResponse> findLineById(@ModelAttribute LineRequest lineRequest) {
        LineResponse line = lineService.findLineById(lineRequest.getId());
        return ResponseEntity.ok(line);
    }
}
