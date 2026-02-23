package org.envycorp.diploma.engines.ALT;

import org.envycorp.diploma.model.entity.NodeGrid;

import java.util.*;

public class ALTPreprocessor {

    private final List<NodeGrid> graph;
    private final List<NodeGrid> landmarks = new ArrayList<>();

    private final Map<Integer, Map<Integer, Double>> landmarkDistances = new HashMap<>();

    public ALTPreprocessor(List<NodeGrid> graph, int landmarkCount) {
        this.graph = graph;
        selectLandmarks(landmarkCount);
        preprocess();
    }

    private void selectLandmarks(int count) {
        List<NodeGrid> copy = new ArrayList<>(graph);
        Collections.shuffle(copy);

        for (int i = 0; i < count && i < copy.size(); i++) {
            landmarks.add(copy.get(i));
        }
    }

    private void preprocess() {
        for (NodeGrid landmark : landmarks) {
            Map<Integer, Double> distances = dijkstraSimple(landmark);
            landmarkDistances.put(landmark.getId(), distances);
        }
    }


    private Map<Integer, Double> dijkstraSimple(NodeGrid start) {

        Map<Integer, Double> dist = new HashMap<>();
        Set<Integer> unvisited = new HashSet<>();

        // initialize distances
        for (NodeGrid node : graph) {
            dist.put(node.getId(), Double.MAX_VALUE);
            unvisited.add(node.getId());
        }

        dist.put(start.getId(), 0.0);

        while (!unvisited.isEmpty()) {

            int currentId = getMinDistanceNode(dist, unvisited);

            if (currentId == -1)
                break;

            unvisited.remove(currentId);

            NodeGrid current = getNodeById(currentId);

            for (NodeGrid neighbor : current.getNeighbors()) {

                if (!unvisited.contains(neighbor.getId()))
                    continue;

                double weight = distance(current, neighbor);

                double newDist =
                        dist.get(currentId) + weight;

                if (newDist < dist.get(neighbor.getId())) {
                    dist.put(neighbor.getId(), newDist);
                }
            }
        }

        return dist;
    }

    private int getMinDistanceNode(Map<Integer, Double> dist,
                                   Set<Integer> unvisited) {

        double min = Double.MAX_VALUE;
        int minId = -1;

        for (int nodeId : unvisited) {
            double d = dist.get(nodeId);
            if (d < min) {
                min = d;
                minId = nodeId;
            }
        }

        return minId;
    }

    private NodeGrid getNodeById(int id) {
        for (NodeGrid node : graph) {
            if (node.getId() == id)
                return node;
        }
        return null;
    }

    public double heuristic(NodeGrid node, NodeGrid target) {

        double max = 0;

        for (NodeGrid landmark : landmarks) {

            double distLtoTarget =
                    landmarkDistances.get(landmark.getId())
                            .get(target.getId());

            double distLtoNode =
                    landmarkDistances.get(landmark.getId())
                            .get(node.getId());

            double value =
                    Math.abs(distLtoTarget - distLtoNode);

            max = Math.max(max, value);
        }

        return max;
    }

    private double distance(NodeGrid a, NodeGrid b) {
        return Math.hypot(
                a.getX() - b.getX(),
                a.getY() - b.getY()
        );
    }
}
