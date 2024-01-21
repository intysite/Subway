package org.javaacademy;

import java.time.Duration;

public class Station {
    private String name;
    private Station previousStation;
    private Station nextStation;
    private Station transferStation;
    private Duration timeToNextStation;
    private Line line;
    private Subway subway;

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Station getPreviousStation() {
        return previousStation;
    }

    public void setPreviousStation(Station previousStation) {
        this.previousStation = previousStation;
    }

    public Station getNextStation() {
        return nextStation;
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }

    public Station getTransferStation() {
        return transferStation;
    }

    public void setTransferStation(Station transferStation) {
        this.transferStation = transferStation;
    }

    public Duration getTimeToNextStation() {
        return timeToNextStation;
    }

    public void setTimeToNextStation(Duration timeToNextStation) {
        this.timeToNextStation = timeToNextStation;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
}
