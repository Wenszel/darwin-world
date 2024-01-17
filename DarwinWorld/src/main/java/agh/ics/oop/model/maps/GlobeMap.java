package agh.ics.oop.model.maps;

import agh.ics.oop.model.config.SimulationConfig;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.MapElement;
import agh.ics.oop.model.mapElements.MapField;
import agh.ics.oop.model.utils.Genotype;
import agh.ics.oop.model.utils.KeyValue;
import agh.ics.oop.model.utils.Vector2d;

import java.util.*;

public class GlobeMap implements WorldMap {
    protected UUID id;
    protected final Map<Vector2d, MapField> mapFields = new HashMap<>();
    protected final List<Animal> animals = new LinkedList<>();
    protected int deadAnimalsCounter = 0;
    protected double deadAnimalsAverageLifeLength = 0;
    protected final int width;
    protected final int height;
    private final SimulationConfig config;
    private final List<MapField> emptyPreferredFields = new ArrayList<>();
    private final List<MapField> emptyNormalFields = new ArrayList<>();
    Map<Genotype, Integer> genotypeCounts = new HashMap<>();
    List<KeyValue<Genotype, Integer>> mostPopularGenotypes = new LinkedList<>();
    public GlobeMap(SimulationConfig config) {
        this.id = UUID.randomUUID();
        this.config = config;
        this.width = config.getMapWidth();
        this.height = config.getMapHeight();
    }

    @Override
    public void initializeMap() {
        createFields();
        growPlants(config.getStartingPlants());
        generateAnimals(config.getStartingAnimals());

    }

    private void createFields() {
        List<Integer> preferredRows = calculatePreferredRows(width, height);
        for (int j = 0; j < height; j++) {
            boolean isRowPreferred = preferredRows.contains(j);
            for (int i = 0; i < width; i++) {
                Vector2d pos = new Vector2d(i, j);
                MapField field = new MapField(pos, config, isRowPreferred);
                mapFields.put(pos, field);
                if (isRowPreferred) {
                    emptyPreferredFields.add(field);
                } else {
                    emptyNormalFields.add(field);
                }
            }
        }
    }

    private void generateAnimals(int amount) {
        for (int i = 0; i < amount; i++) {
            Vector2d position = Vector2d.getRandomVector(config.getMapWidth(), config.getMapHeight());
            Animal animal = new Animal(position, config);
            mapFields.get(position).addAnimal(animal);
            animals.add(animal);
            addGenotypeToHeap(animal.getGenotype());
        }
    }

    @Override
    public void moveAnimals() {
        for (Animal animal : animals) {
            mapFields.get(animal.getPosition()).removeAnimal(animal);
            animal.move(new Vector2d(width - 1, height - 1));
            mapFields.get(animal.getPosition()).addAnimal(animal);
        }
    }

    @Override
    public void reproduceAnimals() {
        for (MapField field : mapFields.values()) {
            Optional<Animal> newAnimal = field.reproduceAnimals();
            if (newAnimal.isPresent()) {
                animals.add(newAnimal.get());
                addGenotypeToHeap(newAnimal.get().getGenotype());
            }
        }
    }

    @Override
    public void growPlants(int amount) {
        Random rand = new Random();
        for (int i = 0; i < amount; i++) {
            boolean choosePreferredField = rand.nextInt(5) != 0;
            if (choosePreferredField && emptyPreferredFields.size() > 0) {
                findRandomFieldAndGrow(emptyPreferredFields);
            } else {
                if (emptyNormalFields.size() > 0) {
                    findRandomFieldAndGrow(emptyNormalFields);
                }
            }
        }
    }

    public void consumePlants() {
        for (MapField field : mapFields.values()) {
            field.consumePlant().ifPresent(consumedField -> {
                if (consumedField.isPreferred()) {
                    emptyPreferredFields.add(consumedField);
                } else {
                    emptyNormalFields.add(consumedField);
                }
            });
        }
    }

    @Override
    public double removeDeadAnimals(int dayCounter) {
        ListIterator<Animal> iterator = animals.listIterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal.getEnergy() <= config.getDailyEnergyCost()) {
                mapFields.get(animal.getPosition()).removeAnimal(animal);
                animal.kill(dayCounter);
                this.deadAnimalsAverageLifeLength = (this.deadAnimalsAverageLifeLength * deadAnimalsCounter + animal.getDayAlive()) / (deadAnimalsCounter + 1);
                deadAnimalsCounter += 1;
                iterator.remove();
            }
        }
        return deadAnimalsAverageLifeLength;
    }

    private void findRandomFieldAndGrow(List<MapField> fields) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(fields.size());
        MapField chosenField = fields.remove(randomIndex);
        chosenField.growPlant();
    }

    private List<Integer> calculatePreferredRows(int width, int height) {
        int fieldsAmount = width * height;
        float preferredFieldsCoveragePercentage = 0.2f;
        List<Integer> preferredRows = new LinkedList<>();
        int centralRow = height / 2;
        int side = -1;
        int currentRow = centralRow;
        preferredRows.add(centralRow);
        while (preferredRows.size() * width < fieldsAmount * preferredFieldsCoveragePercentage) {
            int newRow = currentRow + side * preferredRows.size();
            preferredRows.add(newRow);
            currentRow = newRow;
            side *= -1;
        }
        return preferredRows;
    }

    private void addGenotypeToHeap(Genotype newGenotype) {
        genotypeCounts.put(newGenotype, genotypeCounts.getOrDefault(newGenotype, 0) + 1);
        KeyValue<Genotype, Integer> genotypeCount = containsGenotype(newGenotype, mostPopularGenotypes);
        if(genotypeCount!=null) {
            genotypeCount.setValue(genotypeCount.getValue()+1);
        } else if(mostPopularGenotypes.size()<3) {
            mostPopularGenotypes.add(new KeyValue(newGenotype, genotypeCounts.getOrDefault(newGenotype, 0)));
        } else if(genotypeCounts.get(newGenotype)>mostPopularGenotypes.get(0).getValue()) {
            mostPopularGenotypes.remove(0);
            mostPopularGenotypes.add(new KeyValue(newGenotype, genotypeCounts.getOrDefault(newGenotype, 0)));
        }
        mostPopularGenotypes.sort(Comparator.comparing(KeyValue::getValue));

    }
    private KeyValue<Genotype, Integer> containsGenotype(Genotype genotype, List<KeyValue<Genotype, Integer>> mostPopularGenotypes) {
        for (KeyValue<Genotype, Integer> keyValue : mostPopularGenotypes) {
            if (keyValue.getKey().equals(genotype)) {
                return keyValue;
            }
        }
        return null;
    }

    public Map<Vector2d, MapField> getMapFields() {
        return mapFields;
    }


    @Override
    public List<MapElement> stackObjectsToDraw(Vector2d position) {
        List<MapElement> elements = new LinkedList<>();
        return elements;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public int getNumberOfAnimals() {
        return animals.size();
    }

    @Override
    public List<KeyValue<Genotype, Integer>> getMostPopularGenotypes() {
        List<KeyValue<Genotype, Integer>> mostPopularGenotypesCopy = new LinkedList<>(mostPopularGenotypes);
        Collections.reverse(mostPopularGenotypesCopy);
        return mostPopularGenotypesCopy;
    }

    @Override
    public void unmarkAllAnimalsAsDescendants() {
        animals.forEach(Animal::unmarkAsDescendant);
    }

    @Override
    public UUID getId() {
        return id;
    }
}
