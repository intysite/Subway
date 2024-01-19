package org.javaacademy;

import java.util.LinkedHashSet;

public class Line {
    private final String color;
    private final LinkedHashSet<Station> stations = new LinkedHashSet<>();
    private final Subway subway;

    public Line(String color, Subway subway) {
        this.color = color;
        this.subway = subway;
    }

    public String getColor() {
        return color;
    }

    public LinkedHashSet<Station> getStations() {
        return stations;
    }
}