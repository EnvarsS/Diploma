package org.envycorp.diploma.model.entity;

import java.util.List;


public record A_starPathRecords(
        List<Integer> A_starPath,
        List<Integer> A_starClosedNodes,
        Integer A_starExpandedNodes) {
}
