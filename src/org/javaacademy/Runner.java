package org.javaacademy;

import org.javaacademy.exceptions.NoWayException;

import java.time.Duration;
import java.time.LocalDate;

public class Runner {
    public static void main(String[] args) throws NoWayException {

        Subway subway = new Subway("Пермь");

        try {
            subway.createNewLine("Красная");
            subway.createNewLine("Синяя");
            subway.createFirstStation("Красная", "Спортивная");
            subway.createEndStation("Красная", "Медведковская", Duration.ofMinutes(2).plusSeconds(21), null);
            subway.createEndStation("Красная", "Молодежная", Duration.ofMinutes(1).plusSeconds(58), null);
            subway.createEndStation("Красная", "Пермь 1", Duration.ofMinutes(3).plusSeconds(0), "Синяя");
            subway.createEndStation("Красная", "Пермь 2", Duration.ofMinutes(2).plusSeconds(10), null);
            subway.createEndStation("Красная", "Дворец Культуры", Duration.ofMinutes(4).plusSeconds(26), null);
            subway.createFirstStation("Синяя", "Пацанская");
            subway.createEndStation("Синяя", "Улица Кирова", Duration.ofMinutes(1).plusSeconds(30), null);
            subway.createEndStation("Синяя", "Тяжмаш", Duration.ofMinutes(1).plusSeconds(47), "Красная");
            subway.createEndStation("Синяя", "Нижнекамская", Duration.ofMinutes(3).plusSeconds(19), null);
            subway.createEndStation("Синяя", "Соборная", Duration.ofMinutes(1).plusSeconds(48), null);
            Station station = subway.findStationByName("Медведковская");
            station.sellTicket(LocalDate.now(), "Медведковская", "Нижнекамская");
            station.sellTicket(LocalDate.now(), "Медведковская", "Нижнекамская");
            System.out.println(station.getTicketOffice());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //System.out.println(subway.countNumberOfStages("Спортивная", "Нижнекамская"));
    }
}