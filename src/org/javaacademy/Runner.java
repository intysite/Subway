package org.javaacademy;

public class Runner {
    public static void main(String[] args) {

        Subway subway = new Subway("Пермь");

        try {
            subway.createNewLine("Красная");
            subway.createNewLine("Синяя");
            subway.createFirstStation("Красная", "Спортивная");
            subway.createFirstStation("Синяя", "Пацанская");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(subway);
    }
}
