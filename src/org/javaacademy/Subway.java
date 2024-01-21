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

    public Line createNewLine(String color) throws DuplicateLineColorException {
        if (isColorUnique(color)) {
            Line line = new Line(color, this);
            lines.add(line);
            return line;
        } else {
            throw new DuplicateLineColorException("Линия с цветом " + color + " уже существует.");
        }
    }

    private boolean isColorUnique(String color) {
        return lines.stream()
                .noneMatch(line -> line.getColor().equals(color));
    }

    public Station createFirstStation(String lineColor, String nameOfStation) throws FailedCreateStationException {
        if(!isNameOfStationUnique(nameOfStation)
            && isContainLineDesiredColor(lineColor)
            && isLineEmpty(lineColor)) {
                return new Station(nameOfStation);
        } else {
            throw new FailedCreateStationException("Не удалось создать станцию " + nameOfStation);
        }
    }

    public Station createEndStation(String lineColor,
                                    String nameOfStation,
                                    Duration transferTimeFromPreviousStation,
                                    Station availableStationForTransfer) throws FailedCreateStationException {

        Station lastStation = getLineByColor(lineColor).getStations().getLast();

        if(isContainLineDesiredColor(lineColor)
        && !getLineByColor(lineColor).getStations().isEmpty()
        && lastStation.getNextStation() == null
        && !transferTimeFromPreviousStation.isNegative()
        && !transferTimeFromPreviousStation.isZero()
        && isNameOfStationUnique(nameOfStation)) {
            Station station = new Station(nameOfStation);
            station.setLine(getLineByColor(lineColor));
            station.setPreviousStation(lastStation);
            lastStation.setTimeToNextStation(transferTimeFromPreviousStation);

            if(availableStationForTransfer != null) {
                station.setTransferStation(availableStationForTransfer);
            }

            return station;
        } else {
            throw new FailedCreateStationException("Не удалось создать станцию " + nameOfStation);
        }
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
}
