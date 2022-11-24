package utils;

public enum Direction {

    NORTH("N"),
    EAST("E"),
    SOUTH("S"),
    WEST("W");

    public final String direction;

    public static Direction directionFromChar(char c) {
        return switch (c) {
            case 'N' -> NORTH;
            case 'E' -> EAST;
            case 'S' -> SOUTH;
            case 'W' -> WEST;
            default -> throw new IllegalArgumentException("Invalid direction: " + c);
        };
    }

    Direction(String direction) {
        this.direction = direction;
    }


}
