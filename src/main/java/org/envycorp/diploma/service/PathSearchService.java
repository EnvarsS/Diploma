package org.envycorp.diploma.service;

import lombok.RequiredArgsConstructor;
import org.envycorp.diploma.engines.ALT.ALTSearchEngine;
import org.envycorp.diploma.engines.map.MapGeneratorEngine;
import org.envycorp.diploma.engines.a_star.A_starSearchEngine;
import org.envycorp.diploma.model.DTO.response.PathResponseDto;
import org.envycorp.diploma.model.entity.ALTPathRecords;
import org.envycorp.diploma.model.entity.A_starPathRecords;
import org.envycorp.diploma.model.entity.NodeGrid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class PathSearchService {
    private final A_starSearchEngine a_starSearchEngine;
    private final MapGeneratorEngine mapGeneratorEngine;
    private final ALTSearchEngine altSearchEngine;

    public ResponseEntity<PathResponseDto> findPath(Integer startId, Integer endId) {
        NodeGrid start = mapGeneratorEngine.getNodeGrids().get(startId);
        NodeGrid finish = mapGeneratorEngine.getNodeGrids().get(endId);

        A_starPathRecords a_starRecords = a_starSearchEngine.findPath(start, finish);
        ALTPathRecords altRecords = altSearchEngine.findPath(start, finish);

        PathResponseDto response = new PathResponseDto(
                a_starRecords.A_starPath(),
                altRecords.ALTPath(),
                a_starRecords.A_starClosedNodes(),
                altRecords.ALTClosedNodes(),
                a_starRecords.A_starExpandedNodes(),
                altRecords.ALTExpandedNodes()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
