package org.envycorp.diploma.controller;

import lombok.RequiredArgsConstructor;
import org.envycorp.diploma.model.DTO.response.NodeGridDTO;
import org.envycorp.diploma.service.MapGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/generator")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class MapGeneratorController {
    private final MapGeneratorService mapGeneratorService;

    @GetMapping
    public ResponseEntity<List<NodeGridDTO>> generateMap() {
        System.out.println("Map generation requested");
        return mapGeneratorService.generateMap();
    }
}
