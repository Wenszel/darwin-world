package agh.ics.oop.model.utils;

import java.util.Random;

public class Direction {
    private int direction;

    public Direction() {
        Random rand = new Random();
        this.direction = rand.nextInt(8);
    }
    public void rotate(int value) {
        this.direction = (this.direction + value)%8;
    }
    public Vector2d toVector() {
        return switch (this.direction) {
            case 0 -> new Vector2d(0, 1);
            case 1 -> new Vector2d(1, 1);
            case 2 -> new Vector2d(1, 0);
            case 3 -> new Vector2d(1, -1);
            case 4 -> new Vector2d(0, -1);
            case 5 -> new Vector2d(-1, -1);
            case 6 -> new Vector2d(-1, 0);
            case 7 -> new Vector2d(-1, 1);
            default -> null;
        };
    }

    public void reverse() {
        rotate(4);
    }
}
