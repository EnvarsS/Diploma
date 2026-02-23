package org.envycorp.diploma.model.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeGridDTO {
    private int id;

    private int x;
    private int y;

    private List<Integer> neighbors;
}
