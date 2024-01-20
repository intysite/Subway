package org.javaacademy;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private final String color;
    private final List<Station> stations = new ArrayList<>();
    private final Subway subway;

    public Line(String color, Subway subway) {
        this.color = color;
        this.subway = subway;
    }

    public String getColor() {
        return color;
    }

    public List<Station> getStations() {
        return stations;
    }
}