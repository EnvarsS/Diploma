package org.envycorp.diploma.engines.ALT;

import lombok.Setter;
import org.envycorp.diploma.model.DTO.response.AnalyticResponseDto;
import org.envycorp.diploma.model.entity.ALTPathRecords;
import org.envycorp.diploma.model.entity.NodeGrid;
import org.envycorp.diploma.model.entity.NodeRecord;
import org.envycorp.diploma.service.AnalyticService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ALTSearchEngine {
    private final AnalyticService analyticService;

    @Setter
    private ALTPreprocessor preprocessor;
    public Integer amountOfNodesVisited;
    public Integer amountOfGeneratedNodes;

    public ALTSearchEngine(AnalyticService analyticService) {
        this.analyticService = analyticService;
    }

    public ALTPathRecords findPath(NodeGrid start, NodeGrid end) {
        amountOfNodesVisited = 0;
        amountOfGeneratedNodes = 0;

        Map<Integer, NodeRecord> allRecords = new HashMap<>();
        List<NodeRecord> open = new ArrayList<>();
        Set<Integer> closed = new HashSet<>();

        NodeRecord startRecord = new NodeRecord(start);
        startRecord.g = 0;
        startRecord.h = preprocessor.heuristic(start, end);
        startRecord.f = startRecord.g + startRecord.h;

        open.add(startRecord);
        allRecords.put(start.getId(), startRecord);

        while (!open.isEmpty()) {
            amountOfNodesVisited++;

            NodeRecord current = getLowestF(open);

            if (current.node.getId() == end.getId()) {
                List<Integer> path = reconstructPath(current);

                analyticService.ALTAddAnalytic(
                        new AnalyticResponseDto(
                                path.size(),
                                amountOfNodesVisited,
                                amountOfGeneratedNodes
                        ));

                return new ALTPathRecords(path, closed.stream().toList(), amountOfGeneratedNodes);
            }

            open.remove(current);
            closed.add(current.node.getId());

            for (NodeGrid neighbor : current.node.getNeighbors()) {

                if (closed.contains(neighbor.getId()))
                    continue;

                double tentativeG =
                        current.g + distance(current.node, neighbor);

                boolean isNew = !allRecords.containsKey(neighbor.getId());

                if (isNew) {
                    amountOfGeneratedNodes++;
                }

                NodeRecord neighborRecord =
                        allRecords.getOrDefault(
                                neighbor.getId(),
                                new NodeRecord(neighbor)
                        );

                if (!allRecords.containsKey(neighbor.getId())
                    || tentativeG < neighborRecord.g) {

                    neighborRecord.parent = current;
                    neighborRecord.g = tentativeG;
                    neighborRecord.h =
                            preprocessor.heuristic(neighbor, end);
                    neighborRecord.f =
                            neighborRecord.g + neighborRecord.h;

                    allRecords.put(neighbor.getId(), neighborRecord);

                    if (!open.contains(neighborRecord)) {
                        open.add(neighborRecord);
                    }
                }
            }
        }

        return null;
    }

    private NodeRecord getLowestF(List<NodeRecord> open) {

        NodeRecord best = open.get(0);

        for (NodeRecord record : open) {
            if (record.f < best.f) {
                best = record;
            }
        }

        return best;
    }

    private List<Integer> reconstructPath(NodeRecord endRecord) {

        List<NodeGrid> path = new ArrayList<>();
        NodeRecord current = endRecord;

        while (current != null) {
            path.add(current.node);
            current = current.parent;
        }

        Collections.reverse(path);
        return path.stream()
                .map(NodeGrid::getId)
                .toList();
    }

    private double distance(NodeGrid a, NodeGrid b) {
        return Math.hypot(
                a.getX() - b.getX(),
                a.getY() - b.getY()
        );
    }
}
