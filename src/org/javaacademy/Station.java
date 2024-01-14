package org.javaacademy;

public class Station {
    private final String name;
    private final Station previousStation;
    private Station nextStation;

    public Station(String name, Station previousStation) {
        this.name = name;
        this.previousStation = previousStation;
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }
}
