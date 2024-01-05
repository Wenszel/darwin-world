package agh.ics.oop.model.maps;

import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.MapField;
import agh.ics.oop.model.utils.Vector2d;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GlobeMap implements WorldMap {

    private final SimulationConfig config;
    private final Map<Vector2d, MapField> mapFields = new HashMap<>();
    private final List<Animal> animals = new LinkedList();
    private final int width;
    private final int height;
    public GlobeMap(SimulationConfig config){
        this.config = config;
        this.width = config.getMapWidth();
        this.height = config.getMapHeight();
        List<Integer> prefferedRows = calculatePrefferedRows(width, height);
        for(int j=0; j<height; j++) {
            boolean isRowPreffered = prefferedRows.contains(j);
            for(int i=0; i<width; i++) {
                Vector2d pos = new Vector2d(i,j);
                mapFields.put(pos, new MapField(pos, config, isRowPreffered));
            }
        }
    }
    public void initializeMap() {
        //Tutaj będziemy wybierali preferowane pola, początkowe zwierzątka oraz rośliny itp
        generateAnimals();
    }

    private void generateAnimals(){
        //Losujemy mapfields i dajemy do nich zwierzaki (tutaj 2 przykładowe)
        Animal animal = new Animal(new Vector2d(2,2), config);
        Animal animal2 = new Animal(new Vector2d(1,3), config);

        mapFields.get(animal.getPosition()).addAnimal(animal);
        animals.add(animal);

        mapFields.get(animal2.getPosition()).addAnimal(animal2);
        animals.add(animal2);

    }
    public void moveAnimals() {
        for(Animal animal : animals) {
            mapFields.get(animal.getPosition()).removeAnimal(animal);
            animal.move(new Vector2d(width-1, height-1));
            mapFields.get(animal.getPosition()).addAnimal(animal);
        }
    }
    public void reproduceAnimals() {
        for(MapField field : mapFields.values()) {
            Animal newAnimal = field.reproduceAnimals();
            if(newAnimal != null) {
                animals.add(newAnimal);
            }
        }
    }
    private List<Integer> calculatePrefferedRows(int width, int height)  {
            int fieldsAmount = width*height;
            List<Integer> prefferedRows = new LinkedList<>();
            int centralRow = height/2;
            int side = -1;
            int currentRow=centralRow;
            prefferedRows.add(centralRow);
            while(prefferedRows.size()*width < fieldsAmount*0.2) {
                int newRow = currentRow+side*prefferedRows.size();
                prefferedRows.add(newRow);
                currentRow=newRow;
                side*=-1;
            }
            return prefferedRows;
    }
    public Map<Vector2d, MapField> getMapFields() {
        return mapFields;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
