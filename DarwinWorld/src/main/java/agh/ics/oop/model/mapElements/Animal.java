package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.config.SimulationConfig;
import agh.ics.oop.model.stats.AnimalStatistics;
import agh.ics.oop.model.stats.AnimalStatisticsBuilder;
import agh.ics.oop.model.utils.Direction;
import agh.ics.oop.model.utils.Genotype;

import agh.ics.oop.model.utils.Vector2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;


public class Animal implements MapElement {
    private Vector2d position;
    private int energy;
    private final Genotype genotype;
    private final Direction direction;

    private final int dailyEnergyCost;
    private final int reproductionCost;
    private final int energyFromPlant;
    private final LinkedList<Animal> children = new LinkedList<>();
    private int dayAlive = 0;
    private int deathDay;
    private int eatenPlants = 0;
    private int descendants = 0;
    private Animal alfaAnimal = null;

    public Animal(Vector2d position, SimulationConfig config) {
        this.direction = new Direction();
        this.position = position;
        this.genotype = new Genotype(config.getGenotypeLength());
        this.energy = config.getStartingEnergy();
        this.dailyEnergyCost = config.getDailyEnergyCost();
        this.reproductionCost=config.getReproductionCost();
        this.energyFromPlant = config.getEnergyFromPlant();
    }

    public Animal(Vector2d position, SimulationConfig config, Animal strongerParent, Animal weakerParent) {
        this.direction = new Direction();
        this.position = position;
        this.energy = 2*config.getReproductionCost();
        this.genotype = new Genotype(config.getGenotypeLength(), strongerParent, weakerParent, config.getMinMutations(), config.getMaxMutations(), config.getMutationVariant());
        this.dailyEnergyCost = config.getDailyEnergyCost();
        this.reproductionCost=config.getReproductionCost();
        this.energyFromPlant = config.getEnergyFromPlant();
    }

    public void move(Vector2d rightBottomCorner) {
        this.direction.rotate(genotype.getCurrentGene());
        genotype.goToNextGene();
        Vector2d destination = this.position.add(this.direction.toVector());
        this.position = calculatePosition(destination, rightBottomCorner);
        this.energy-=this.dailyEnergyCost;
        this.dayAlive+=1;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public void drawOnMap(GraphicsContext gc, double fieldWidth, double fieldHeight) {
        double fullSaturationEnergy = 10 * dailyEnergyCost;
        double energyRatio = (double) energy / fullSaturationEnergy;
        energyRatio = Math.max(0, Math.min(energyRatio, 1));
        double pinkIntensity = 1 - energyRatio;
        double pinkComponent = pinkIntensity * 0.5;
        if (this.equals(alfaAnimal)) {
            gc.setFill(Color.GOLD);
        } else {
            gc.setFill(new Color(1.0, pinkComponent, pinkComponent, 1));
        }
        gc.fillRect(position.getX() * fieldWidth, position.getY() * fieldHeight, fieldWidth, fieldHeight);
    }

    public void reproduction(Animal child) {
        this.energy -= reproductionCost;
        if (alfaAnimal != null && child.alfaAnimal == null) {
            alfaAnimal.descendants += 1;
            child.alfaAnimal = alfaAnimal;
        }
        this.children.add(child);
    }

    public void consumePlant() {
        this.energy+=energyFromPlant;
        this.eatenPlants+=1;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public int getEnergy() {
        return energy;
    }

    public Genotype getGenotype() {
        return genotype;
    }

    private Vector2d calculatePosition(Vector2d destination, Vector2d rightBottomCorner) {
        int x = destination.getX();
        int y = destination.getY();
        if (y > rightBottomCorner.getY()) {
            this.direction.reverse();
            y = rightBottomCorner.getY();
        } else if (y < 0) {
            this.direction.reverse();
            y = 0;
        }
        if (x > rightBottomCorner.getX()) x = 0;
        if (x < 0) x = rightBottomCorner.getX();
        return new Vector2d(x, y);
    }

    public void kill(int dayCounter) {
        this.deathDay = dayCounter;
    }

    public void markAsAlfa() {
        this.alfaAnimal = this;
    }
     public void markDescendant(Animal alfaAnimal) {
        if(alfaAnimal.equals(this.alfaAnimal)) {
            children.forEach(child -> child.markDescendant(alfaAnimal));
        } else if(this.alfaAnimal == null) {
            this.alfaAnimal = alfaAnimal;
            alfaAnimal.descendants+=1;
            children.forEach(child -> child.markDescendant(alfaAnimal));
        }
    }

    public void unmarkAsDescendant() {
        alfaAnimal = null;
        descendants = 0;
    }

    public int getChildrenAmount() {
        return children.size();
    }

    public int getDayAlive() {
        return dayAlive;
    }

    public AnimalStatistics getAnimalStats()  {
        return new AnimalStatisticsBuilder()
                .setGenotype(genotype)
                .setCurrentActiveGene(genotype.getCurrentGene())
                .setEnergy(energy)
                .setEatenPlants(eatenPlants)
                .setChildrenAmount(children.size())
                .setDescendantAmount(descendants)
                .setDayAlive(dayAlive)
                .setDeathDay(deathDay)
                .build();
    }
}
