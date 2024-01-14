package org.javaacademy;

import java.util.HashSet;

public class Subway {
    private final String city;
    private final HashSet<LineOfSubway> lines;

    public Subway(String city, HashSet<LineOfSubway> lines) {
        this.city = city;
        this.lines = lines;
    }
}
