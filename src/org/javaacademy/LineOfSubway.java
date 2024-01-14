package org.javaacademy;

import java.util.LinkedHashSet;

public class LineOfSubway {
    private final String color;
    private final LinkedHashSet<Station> stations;
    private final Subway subway;

    public LineOfSubway(String color, LinkedHashSet<Station> stations, Subway subway) {
        this.color = color;
        this.stations = stations;
        this.subway = subway;
    }
}