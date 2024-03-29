package org.javaacademy;

import org.javaacademy.exceptions.DuplicateLineColorException;
import org.javaacademy.exceptions.FailedCreateStationException;
import org.javaacademy.exceptions.NoWayException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class Subway {
    private final String city;
    private final Set<Line> lines = new HashSet<>();
    private final Map<String, LocalDate> monthlyPasses = new HashMap<>();
    private int numberOfLastPass = 0;

    public Subway(String city) {
        this.city = city;
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
        if(isNameOfStationUnique(nameOfStation)
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
                                    String availableLineForTransfer) throws FailedCreateStationException {

        Station lastStation = getLineByColor(lineColor).getStations().getLast();

        if(lastStation.getNextStation() == null
        && checkStatesToCreateStation(lineColor, nameOfStation, transferTimeFromPreviousStation)) {

            Station station = createStation(lineColor, nameOfStation);
            station.setPreviousStation(lastStation);
            lastStation.setNextStation(station);
            lastStation.setTimeToNextStation(transferTimeFromPreviousStation);

            if(availableLineForTransfer != null) {
                station.setChangeLines(availableLineForTransfer);
            }
        } else {
            throw new FailedCreateStationException("Не удалось создать станцию " + nameOfStation);
        }
    }

    private Station createStation(String lineColor, String nameOfStation) {
        Station station = new Station(nameOfStation);
        station.setLine(getLineByColor(lineColor));
        getLineByColor(lineColor).addStation(station);
        station.setSubway(this);
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
                .noneMatch(s -> s.getName().equals(nameOfStation));
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
                .orElseThrow();
    }

    private Station defineTransferStation(Line startLine, Line destinationLine) {
        return startLine.getStations().stream()
                .filter(s -> s.getChangeLines().isPresent())
                .findFirst()
                .orElseThrow();
    }

    private int countNumberOfStagesSameLine(Station startStation, Station destinationStation) throws NoWayException {

        if(startStation.equals(destinationStation)) {
            return 0;
        }

        LinkedList<Station> stations = startStation.getLine().getStations();

        long countStations = stations.stream()
                .filter(station -> station.equals(startStation) || station.equals(destinationStation))
                .count();

        if(countStations != 2) {
            throw new NoWayException(String.format("Нет пути между станциями %s и %s",
                    startStation.getName(), destinationStation.getName()));
        }

        if (stations.indexOf(startStation) > stations.indexOf(destinationStation)) {
            Collections.reverse(stations);
        }
        return countNumberOfStagesByNextStations(startStation, destinationStation, stations);
    }

    private int countNumberOfStagesByNextStations(Station startStation,
                                                  Station destinationStation,
                                                  LinkedList<Station> stations) {
        long countBetweenStations = stations.stream()
                .dropWhile(station -> !station.equals(startStation))
                .takeWhile(station -> !station.equals(destinationStation))
                .count();

        return (int) countBetweenStations;
    }

    public Station getStationByName(String nameOfStation) {
        return lines.stream()
                .flatMap(line -> line.getStations().stream())
                .filter(station -> station.getName().equals(nameOfStation))
                .findFirst()
                .orElseThrow();
    }

    public int countNumberOfStages(String nameOfStartStation, String nameOfDestinationStation) throws NoWayException {
        Station startStation = getStationByName(nameOfStartStation);
        Station destinationStation = getStationByName(nameOfDestinationStation);
        if(startStation.getLine().equals(destinationStation.getLine())) {
            return countNumberOfStagesSameLine(startStation, destinationStation);
        }
        Station transferStation = defineTransferStation(startStation.getLine(), destinationStation.getLine());
        Station appropriateTransferStation = defineTransferStation(destinationStation.getLine(), startStation.getLine());

        return countNumberOfStagesSameLine(startStation, transferStation) +
                countNumberOfStagesSameLine(appropriateTransferStation, destinationStation);
    }

    public void addMonthlyPass(LocalDate dateOfPurchase) {
        numberOfLastPass++;
        if (numberOfLastPass > 9999) {
            numberOfLastPass = 0;
        }
        String formattedNumber = "a" + String.format("%04d", numberOfLastPass);
        monthlyPasses.put(formattedNumber, dateOfPurchase.plusMonths(1));
    }

    public boolean isValidMonthlyPass(String numberOfPass, LocalDate checkDate) {
        return checkDate.isBefore(monthlyPasses.get(numberOfPass));
    }

    public void renewMonthlyPass(String numberOfPass, LocalDate dateOfPurchase) {
        monthlyPasses.put(numberOfPass, dateOfPurchase.plusMonths(1));
    }

    public void showAllIncome() {
        Map<LocalDate, Integer> sumByDate = lines.stream()
                .flatMap(line -> line.getStations().stream())
                .flatMap(station -> station.getTicketOffice().entrySet().stream())
                .collect(
                        TreeMap::new,
                        (result, entry) -> result.merge(entry.getKey(), entry.getValue(), Integer::sum),
                        TreeMap::putAll
                );

        sumByDate.forEach((date, sum) -> System.out.println(date + " - " + sum +
                " (сумма доходов со всех касс за эту дату)"));
    }

    @Override
    public String toString() {
        return "Metro{city=\'" + city + "\', lines=" + lines + "}";
    }
}