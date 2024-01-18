package org.javaacademy;

import java.util.HashSet;

public class Subway {
    private String city;
    private HashSet<Line> lines;

    public Subway(String city, HashSet<Line> lines) {
        this.city = city;
        this.lines = lines;
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
                .noneMatch(l -> l.getColor().equals(color));
    }
}
