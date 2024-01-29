package org.javaacademy;

import java.util.LinkedList;

public class Line {
    private final String color;
    private final LinkedList<Station> stations = new LinkedList<>();
    private final Subway subway;

    public Line(String color, Subway subway) {
        this.color = color;
        this.subway = subway;
    }

    public String getColor() {
        return color;
    }

    public LinkedList<Station> getStations() {
        return stations;
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    @Override
    public String toString() {
        return "Line{color=\'" + color + "\', stations=" + stations + "}";
    }
}