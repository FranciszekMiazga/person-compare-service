package com.task.personcompareservice.model;

import lombok.Getter;

@Getter
public enum Classification {
    ADDED("ADDED"),
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH"),
    DELETED("DELETED");

    private final String value;

    Classification(String value) {
        this.value = value;
    }

    public static Classification fromSimilarity(final double similarity) {
        if (similarity > 0.9) {
            return HIGH;
        } else if (similarity >= 0.4) {
            return MEDIUM;
        } else {
            return LOW;
        }
    }
}
