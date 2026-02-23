package org.envycorp.diploma.model.entity;


import lombok.Data;

@Data
public class NodeRecord {
    public NodeGrid node;
    public NodeRecord parent;

    public double g;
    public double h;
    public double f;

    public NodeRecord(NodeGrid node) {
        this.node = node;
    }
}