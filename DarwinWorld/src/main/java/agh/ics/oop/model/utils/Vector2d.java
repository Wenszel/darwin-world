package agh.ics.oop.model.utils;

import java.util.Objects;
import java.util.Random;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2d getRandomVector(int maxX, int maxY) {
        Random rand = new Random();
        return new Vector2d(rand.nextInt(maxX), rand.nextInt(maxY));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Vector2d vector2d = (Vector2d) other;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "("+x+ "," + y +')';


    }
}
