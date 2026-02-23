package org.envycorp.diploma.controller;

import lombok.RequiredArgsConstructor;
import org.envycorp.diploma.model.DTO.response.AnalyticResponseDto;
import org.envycorp.diploma.model.DTO.response.PathResponseDto;
import org.envycorp.diploma.service.AnalyticService;
import org.envycorp.diploma.service.PathSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/path")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PathFinderController {
    private final PathSearchService pathSearchService;
    private final AnalyticService analyticService;

    @GetMapping
    public ResponseEntity<PathResponseDto> getPath(@RequestParam Integer from,
                                                   @RequestParam Integer to) {
        return pathSearchService.findPath(from, to);
    }

    @GetMapping("/analytics/a_star")
    public ResponseEntity<List<AnalyticResponseDto>> getA_starAnalytics() {
        return new ResponseEntity<>(analyticService.getA_starAnalyticList(), HttpStatus.OK);
    }
    @GetMapping("/analytics/ALT")
    public ResponseEntity<List<AnalyticResponseDto>> getALTAnalytics() {
        return new ResponseEntity<>(analyticService.getALTAnalyticList(), HttpStatus.OK);
    }
}
