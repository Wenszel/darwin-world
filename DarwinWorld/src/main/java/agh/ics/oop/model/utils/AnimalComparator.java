package agh.ics.oop.model.utils;

import agh.ics.oop.model.mapElements.Animal;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal animal1, Animal animal2) {
        if(animal1.getEnergy() != animal2.getEnergy()) {
            return animal1.getEnergy() - animal2.getEnergy();
        }
        //TODO
        //trzeba dodać pozostałe warunki do sprawdzania
        return 0;
    }
}
