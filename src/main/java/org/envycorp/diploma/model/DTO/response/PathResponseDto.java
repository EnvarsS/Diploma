package org.envycorp.diploma.model.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PathResponseDto {
    private List<Integer> A_starPath;
    private List<Integer> ALTPath;

    private List<Integer> A_starClosedNodes;
    private List<Integer> ALTClosedNodes;

    private Integer A_starGeneratedNodes;
    private Integer ALTGeneratedNodes;
}
