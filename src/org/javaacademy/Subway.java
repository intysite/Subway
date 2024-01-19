package org.javaacademy;

import java.util.HashSet;

public class Subway {
    private String city;
    private final HashSet<Line> lines = new HashSet<>();

    public Subway(String city) {
        this.city = city;
    }

    public Line createNewLine(String color) throws DuplicateColorException {
        if (isColorUnique(color)) {
            Line line = new Line(color, this);
            lines.add(line);
            return line;
        } else {
            throw new DuplicateColorException("Линия с цветом " + color + " уже существует.");
        }
    }

    private boolean isColorUnique(String color) {
        return lines.stream()
                .noneMatch(line -> line.getColor().equals(color));
    }

    public Station createFirstStation(String color, String nameOfStation) throws FailedCreateStationException {
        if(!isNameOfStationUnique(nameOfStation)
            && isContainLineDesiredColor(color)
            && isLineEmpty(color)) {
                return new Station(nameOfStation);
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
}
