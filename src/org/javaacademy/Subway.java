package org.javaacademy;

import java.util.HashSet;

public class Subway {
    private String city;
    private HashSet<Line> lines;

    public Subway(String city, HashSet<Line> lines) {
        this.city = city;
        this.lines = lines;
    }

    public Line createNewLine(String color) {
        //TO DO Проверка на то, что не существует линии с таким же цветом.

        return new Line(color, this);
    }
}
