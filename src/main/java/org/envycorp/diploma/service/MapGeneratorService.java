package org.envycorp.diploma.service;

import lombok.RequiredArgsConstructor;
import org.envycorp.diploma.engines.ALT.ALTPreprocessor;
import org.envycorp.diploma.engines.ALT.ALTSearchEngine;
import org.envycorp.diploma.engines.map.MapGeneratorEngine;
import org.envycorp.diploma.model.DTO.response.NodeGridDTO;
import org.envycorp.diploma.model.entity.NodeGrid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapGeneratorService {
    private final MapGeneratorEngine mapGeneratorEngine;
    private final ALTSearchEngine altSearchEngine;
    private final int LANDMARKS_COUNT = 16;

    public ResponseEntity<List<NodeGridDTO>> generateMap() {
        mapGeneratorEngine.generateMap();
        List<NodeGrid> nodeGrids = mapGeneratorEngine.getNodeGrids().values().stream().toList();
        List<NodeGridDTO> dto = nodeGrids.stream()
                .map(nodeGrid -> {
                    NodeGridDTO node = new NodeGridDTO();
                    node.setId(nodeGrid.getId());
                    node.setX(nodeGrid.getX());
                    node.setY(nodeGrid.getY());
                    node.setNeighbors(nodeGrid.getNeighbors().stream().map(NodeGrid::getId).toList());
                    return node;
                })
                .toList();

        altSearchEngine.setPreprocessor(
                new ALTPreprocessor(nodeGrids, LANDMARKS_COUNT)
        );

        return new ResponseEntity<>(
                dto,
                HttpStatus.OK
        );
    }
}
