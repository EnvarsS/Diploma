package org.envycorp.diploma.engines.map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.envycorp.diploma.model.entity.NodeGrid;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Getter
@NoArgsConstructor
public class MapGeneratorEngine {
    private PerlinNoiseGenerator noiseGenerator;

    private static final int MAP_SIZE = 100;
    private static final int TARGET_NODES = 800;
    private static final double NOISE_SCALE = 0.2; //0.005
    private static final double NOISE_THRESHOLD = -0.6;
    private static final double NEIGHBOR_RADIUS = 5;

    private final List<NodeGrid> nodes = new ArrayList<>();
    private final Map<Integer, NodeGrid> nodeGrids = new HashMap<>();

    public void generateMap() {
        nodes.clear();
        nodeGrids.clear();
        noiseGenerator = new PerlinNoiseGenerator(System.currentTimeMillis());
        Random random = new Random();

        int id = 0;

        while (nodes.size() < TARGET_NODES) {

            int x = random.nextInt(MAP_SIZE);
            int y = random.nextInt(MAP_SIZE);

            double n = noiseGenerator.noise(x * NOISE_SCALE, y * NOISE_SCALE);

            if (n < NOISE_THRESHOLD) {
                continue;
            }

            NodeGrid node = new NodeGrid();
            node.setId(id++);
            node.setX(x);
            node.setY(y);

            nodes.add(node);
            nodeGrids.put(node.getId(), node);
        }

        buildNeighbors();
    }

    private void buildNeighbors() {
        for (NodeGrid a : nodes) {
            for (NodeGrid b : nodes) {
                if (a == b) continue;

                if (distance(a, b) <= NEIGHBOR_RADIUS) {
                    a.getNeighbors().add(b);
                }
            }
        }
    }

    private static double distance(NodeGrid first, NodeGrid second) {
        return Math.hypot(first.getX() - second.getX(), first.getY() - second.getY());
    }
}
