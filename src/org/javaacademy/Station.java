package org.javaacademy;

import org.javaacademy.exceptions.NoWayException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

public class Station {
    private String name;
    private Station previousStation;
    private Station nextStation;
    private String changeLines;
    private Duration timeToNextStation;
    private Line line;
    private Subway subway;
    private HashMap<LocalDate, Integer> ticketOffice = new HashMap<>();

    public Station(String name) {
        this.name = name;
    }

    public void sellTicket(LocalDate dateOfPurchase, String startStation, String destinationStation) throws NoWayException {
        Integer ticketPrice = subway.countNumberOfStages(startStation, destinationStation) * 5 + 20;

        if(ticketOffice.containsKey(dateOfPurchase)) {
            ticketPrice += ticketOffice.get(dateOfPurchase);
        }

        ticketOffice.put(dateOfPurchase, ticketPrice);
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

    public Optional<String> getChangeLines() {
        return Optional.ofNullable(changeLines);
    }

    public void setChangeLines(String changeLines) {
        this.changeLines = changeLines;
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

    public HashMap<LocalDate, Integer> getTicketOffice() {
        return ticketOffice;
    }

    public void setSubway(Subway subway) {
        this.subway = subway;
    }

    @Override
    public String toString() {
        return "Station{name=\'" + name + "\', changeLines=" + changeLines + "}";
    }
}