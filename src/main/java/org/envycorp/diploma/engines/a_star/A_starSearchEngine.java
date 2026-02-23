package org.envycorp.diploma.engines.a_star;

import org.envycorp.diploma.model.DTO.response.AnalyticResponseDto;
import org.envycorp.diploma.model.entity.A_starPathRecords;
import org.envycorp.diploma.model.entity.NodeGrid;
import org.envycorp.diploma.model.entity.NodePath;
import org.envycorp.diploma.service.AnalyticService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class A_starSearchEngine {
    private final AnalyticService analyticService;

    public A_starSearchEngine(AnalyticService analyticService) {
        this.analyticService = analyticService;
    }

    public Integer amountOfNodesVisited;
    public Integer amountOfGeneratedNodes;

    public A_starPathRecords findPath(NodeGrid start, NodeGrid end) {
        try {
            amountOfNodesVisited = 0;
            amountOfGeneratedNodes = 0;
            ArrayList<NodePath> openList = new ArrayList<>();
            HashSet<NodeGrid> closedList = new HashSet<>();

            NodePath startPath = new NodePath(start, null);
            startPath.setG(0);
            startPath.setH(distance(start, end));
            startPath.setF(startPath.getG() + startPath.getH());

            openList.add(startPath);

            while (!openList.isEmpty()) {
                amountOfNodesVisited++;
                NodePath current = findNodeWithLowestF(openList);

                if (current.getNode().equals(end)) {
                    List<Integer> path = constructPath(current);

                    System.out.println(path + " " + path.size());

                    analyticService.a_starAddAnalytic(
                            new AnalyticResponseDto(
                                    path.size(),
                                    amountOfNodesVisited,
                                    amountOfGeneratedNodes
                            ));

                    return new A_starPathRecords(path, closedList.stream().map(NodeGrid::getId).toList(), amountOfGeneratedNodes);
                }

                openList.remove(current);
                closedList.add(current.getNode());

                for (NodeGrid neighborNode : current.getNode().getNeighbors()) {
                    if (closedList.contains(neighborNode))
                        continue;

                    double tentativeG = current.getG() + distance(current.getNode(), neighborNode);

                    NodePath nextNode = getFromOpenList(neighborNode, openList);

                    if (nextNode == null) {
                        amountOfGeneratedNodes++;
                        NodePath newNode = new NodePath(neighborNode, current);
                        newNode.setG(tentativeG);
                        newNode.setH(heuristic(neighborNode, end));
                        newNode.setF(newNode.getG() + newNode.getH());
                        openList.add(newNode);
                    } else if (tentativeG < nextNode.getG()) {
                        nextNode.setG(tentativeG);
                        nextNode.setParent(current);
                        nextNode.setF(nextNode.getG() + nextNode.getH());

                    }
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static double distance(NodeGrid first, NodeGrid second) {
        return Math.hypot(first.getX() - second.getX(), first.getY() - second.getY());
    }

    private static double heuristic(NodeGrid first, NodeGrid second) {
        return Math.hypot(first.getX() - second.getX(), first.getY() - second.getY());
    }

    private static NodePath findNodeWithLowestF(ArrayList<NodePath> openList) {
        NodePath lowestCostNode = openList.get(0);
        for (int i = 1; i < openList.size(); i++) {
            NodePath current = openList.get(i);
            if (current.getF() < lowestCostNode.getF()) {
                lowestCostNode = current;
            }
        }
        return lowestCostNode;
    }

    private static NodePath getFromOpenList(NodeGrid node, ArrayList<NodePath> openList) {
        for (NodePath nodePath : openList) {
            if (nodePath.getNode().equals(node))
                return nodePath;
        }
        return null;
    }

    private static List<Integer> constructPath(NodePath nodePath) {
        ArrayList<NodeGrid> path = new ArrayList<>();
        NodePath current = nodePath;
        while (current != null) {
            path.add(0, current.getNode());
            current = current.getParent();
        }

        return path.stream()
                .map(NodeGrid::getId)
                .toList();
    }
}
