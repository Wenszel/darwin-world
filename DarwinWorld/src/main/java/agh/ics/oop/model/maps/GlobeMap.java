package agh.ics.oop.model.maps;

import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.MapElement;
import agh.ics.oop.model.mapElements.MapField;
import agh.ics.oop.model.stats.AnimalStatistics;
import agh.ics.oop.model.utils.Vector2d;

import java.util.*;

public class GlobeMap implements WorldMap {
    protected final Map<Vector2d, MapField> mapFields = new HashMap<>();
    protected final List<Animal> animals = new LinkedList<>();
    protected final int width;
    protected final int height;
    private final SimulationConfig config;
    private final List<MapField> emptyPreferredFields = new ArrayList<>();
    private final List<MapField> emptyNormalFields = new ArrayList<>();

    public GlobeMap(SimulationConfig config){
        this.config = config;
        this.width = config.getMapWidth();
        this.height = config.getMapHeight();

    }

    @Override
    public void initializeMap() {
        //Tutaj będziemy wybierali preferowane pola, początkowe zwierzątka oraz rośliny itp
        createFields();
        growPlants(config.getStartingPlants());
        generateAnimals(config.getStartingAnimals());
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
    private void generateAnimals(int amount){
        for(int i=0; i<amount; i++) {
            Vector2d position = Vector2d.getRandomVector(config.getMapWidth(), config.getMapHeight());
            Animal animal = new Animal(position, config);
            mapFields.get(position).addAnimal(animal);
            animals.add(animal);
        }
    }

    @Override
    public void moveAnimals() {
        for(Animal animal : animals) {
            mapFields.get(animal.getPosition()).removeAnimal(animal);
            animal.move(new Vector2d(width-1, height-1));
            mapFields.get(animal.getPosition()).addAnimal(animal);
        }
    }

    @Override
    public void reproduceAnimals() {
        for(MapField field : mapFields.values()) {
            Optional<Animal> newAnimal = field.reproduceAnimals();
            newAnimal.ifPresent(animals::add);
        }
    }
    @Override
    public void growPlants(int amount) {
        Random rand = new Random();
        for(int i=0; i<amount; i++) {
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

    public void consumePlants() {
        for(MapField field : mapFields.values()) {
            field.consumePlant().ifPresent(consumedField -> {
                if(consumedField.isPreferred()) {
                    emptyPreferredFields.add(consumedField);
                } else {
                    emptyNormalFields.add(consumedField);
                }
            });
        }
    }
    public void removeDeadAnimals() {
        ListIterator<Animal> iterator = animals.listIterator();
        while(iterator.hasNext()) {
            Animal animal = iterator.next();
            if(animal.getEnergy() <= config.getDailyEnergyCost()) {
                mapFields.get(animal.getPosition()).removeAnimal(animal);
                animal.kill();
                iterator.remove();
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
            float preferredFieldsCoveragePercentage = 0.2f;
            List<Integer> preferredRows = new LinkedList<>();
            int centralRow = height/2;
            int side = -1;
            int currentRow=centralRow;
            preferredRows.add(centralRow);
            while(preferredRows.size()*width < fieldsAmount*preferredFieldsCoveragePercentage) {
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


    @Override
    public List<MapElement>  stackObjectsToDraw(Vector2d position) {
        List<MapElement> elements = new LinkedList<>();
        return elements;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
