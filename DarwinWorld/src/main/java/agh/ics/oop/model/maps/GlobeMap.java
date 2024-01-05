package agh.ics.oop.model.maps;

import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.MapField;
import agh.ics.oop.model.utils.Vector2d;

import java.util.*;

public class GlobeMap implements WorldMap {

    private final SimulationConfig config;
    private final Map<Vector2d, MapField> mapFields = new HashMap<>();
    private final List<Animal> animals = new LinkedList();
    private final List<MapField> emptyPreferredFields = new ArrayList<>();
    private final List<MapField> emptyNormalFields = new ArrayList<>();
    private final int width;
    private final int height;
    public GlobeMap(SimulationConfig config){
        this.config = config;
        this.width = config.getMapWidth();
        this.height = config.getMapHeight();

    }
    public void initializeMap() {
        //Tutaj będziemy wybierali preferowane pola, początkowe zwierzątka oraz rośliny itp
        createFields();
        generateAnimals();
    }
    private void createFields() {
        List<Integer> preferredRows = calculatePreferredRows(width, height);
        for(int j=0; j<height; j++) {
            boolean isRowPreferred = preferredRows.contains(j);
            for(int i=0; i<width; i++) {
                Vector2d pos = new Vector2d(i,j);
                MapField field = new MapField(pos, config, isRowPreferred);
                mapFields.put(pos, field);
                if(isRowPreferred) {
                    emptyPreferredFields.add(field);
                } else {
                    emptyNormalFields.add(field);
                }
            }
        }
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
    public void growPlants() {
        Random rand = new Random();
        for(int i=0; i<config.getDailyPlantsGrowth(); i++) {
            boolean choosePreferredField = rand.nextInt(5) != 0;
            if(choosePreferredField && emptyPreferredFields.size()>0) {
                findRandomFieldAndGrow(emptyPreferredFields);
            } else {
                if(emptyNormalFields.size() > 0) {
                    findRandomFieldAndGrow(emptyNormalFields);
                }
            }
        }
    }
    private void findRandomFieldAndGrow(List<MapField> fields) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(fields.size());
        MapField chosenField = fields.remove(randomIndex);
        chosenField.growPlant();
    }
    private List<Integer> calculatePreferredRows(int width, int height)  {
            int fieldsAmount = width*height;
            List<Integer> preferredRows = new LinkedList<>();
            int centralRow = height/2;
            int side = -1;
            int currentRow=centralRow;
            preferredRows.add(centralRow);
            while(preferredRows.size()*width < fieldsAmount*0.2) {
                int newRow = currentRow+side*preferredRows.size();
                preferredRows.add(newRow);
                currentRow=newRow;
                side*=-1;
            }
            return preferredRows;
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
