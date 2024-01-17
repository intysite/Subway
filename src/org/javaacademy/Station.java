package org.javaacademy;

import java.time.Duration;

public class Station {
    private String name;
    private Station previousStation;
    private Station nextStation;
    private String transferStation;
    private Duration timeToNextStation;
    private Line line;
    private Subway subway;

    public Station(String name, Station previousStation) {
        this.name = name;
        this.previousStation = previousStation;
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }
}
