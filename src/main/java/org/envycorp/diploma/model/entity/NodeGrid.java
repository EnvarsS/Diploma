package org.envycorp.diploma.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NodeGrid {
    @EqualsAndHashCode.Include
    private int id;
    private int x;
    private int y;

    private final List<NodeGrid> neighbors = new ArrayList<>();
}
