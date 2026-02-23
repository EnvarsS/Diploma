package org.envycorp.diploma.model.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticResponseDto {
    private Integer pathLength;
    private Integer expandedNodes;
    private Integer generatedNodes;
}
