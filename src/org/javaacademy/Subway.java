package org.javaacademy;

import java.time.Duration;
import java.util.HashSet;

public class Subway {
    private final String city;
    private final HashSet<Line> lines = new HashSet<>();

    public Subway(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public HashSet<Line> getLines() {
        return lines;
    }

    public void createNewLine(String color) throws DuplicateLineColorException {
        if (isColorUnique(color)) {
            Line line = new Line(color, this);
            lines.add(line);
        } else {
            throw new DuplicateLineColorException("Линия с цветом " + color + " уже существует.");
        }
    }

    private boolean isColorUnique(String color) {
        return lines.stream()
                .noneMatch(line -> line.getColor().equals(color));
    }

    public void createFirstStation(String lineColor, String nameOfStation) throws FailedCreateStationException {
        if(!isNameOfStationUnique(nameOfStation)
            && isContainLineDesiredColor(lineColor)
            && isLineEmpty(lineColor)) {
            createStation(lineColor, nameOfStation);
        } else {
            throw new FailedCreateStationException("Не удалось создать станцию " + nameOfStation);
        }
    }

    public void createEndStation(String lineColor,
                                    String nameOfStation,
                                    Duration transferTimeFromPreviousStation,
                                    Station availableStationForTransfer) throws FailedCreateStationException {

        Station lastStation = getLineByColor(lineColor).getStations().getLast();

        if(lastStation.getNextStation() == null
        && checkStatesToCreateStation(lineColor, nameOfStation, transferTimeFromPreviousStation)) {

            Station station = createStation(lineColor, nameOfStation);
            station.setPreviousStation(lastStation);
            lastStation.setNextStation(station);
            lastStation.setTimeToNextStation(transferTimeFromPreviousStation);

            if(availableStationForTransfer != null) {
                station.setChangeLines(availableStationForTransfer.getLine().getColor());
            }

        } else {
            throw new FailedCreateStationException("Не удалось создать станцию " + nameOfStation);
        }
    }

    private Station createStation(String lineColor, String nameOfStation) {
        Station station = new Station(nameOfStation);
        station.setLine(getLineByColor(lineColor));
        getLineByColor(lineColor).addStation(station);
        return station;
    }

    private boolean checkStatesToCreateStation(String lineColor,
                                               String nameOfStation,
                                               Duration transferTimeFromPreviousStation) {
        return isContainLineDesiredColor(lineColor)
                && !getLineByColor(lineColor).getStations().isEmpty()
                && !transferTimeFromPreviousStation.isNegative()
                && !transferTimeFromPreviousStation.isZero()
                && isNameOfStationUnique(nameOfStation);
    }

    private boolean isNameOfStationUnique(String nameOfStation) {
        return lines.stream()
                .flatMap(line -> line.getStations().stream())
                .anyMatch(s -> s.getName().equals(nameOfStation));
    }

    private boolean isContainLineDesiredColor(String color) {
        return lines.stream()
                .anyMatch(line -> line.getColor().equals(color));
    }

    private boolean isLineEmpty(String color) {
        return lines.stream()
                .filter(line -> line.getColor().equals(color))
                .allMatch(line -> line.getStations().isEmpty());
    }

    private Line getLineByColor(String lineColor) {
        return lines.stream()
                .filter(line -> line.getColor().equals(lineColor))
                .findFirst()
                .get();
    }

    private Station defineTransferStation(Line startLine, Line destinationLine) {
        return startLine.getStations().stream()
                .filter(l -> l.getChangeLines().equals(destinationLine.getColor()))
                .findFirst()
                .get();
    }

    @Override
    public String toString() {
        return "Metro{city=\'" + city + "\', lines=" + lines + "}";
    }
}