package org.envycorp.diploma.model.entity;

import lombok.Data;

@Data
public class NodePath {
    private NodeGrid node;

    private NodePath parent;

    private double g;
    private double h;
    private double f;

    public NodePath(NodeGrid node, NodePath parent){
        this.node = node;
        this.parent = parent;
    }
}
