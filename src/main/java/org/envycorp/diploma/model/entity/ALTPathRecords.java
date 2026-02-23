package org.envycorp.diploma.model.entity;

import java.util.List;

public record ALTPathRecords(
        List<Integer> ALTPath,
        List<Integer> ALTClosedNodes,
        Integer ALTExpandedNodes) {
}
