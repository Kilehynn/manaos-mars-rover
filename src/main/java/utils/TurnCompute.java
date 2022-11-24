package utils;

import java.util.Map;

public interface TurnCompute {

    Map<Direction,Direction> turnLeft = Map.of(
            Direction.NORTH, Direction.WEST,
            Direction.WEST, Direction.SOUTH,
            Direction.SOUTH, Direction.EAST,
            Direction.EAST, Direction.NORTH
    );

    Map<Direction,Direction> turnRight = Map.of(
            Direction.NORTH, Direction.EAST,
            Direction.EAST, Direction.SOUTH,
            Direction.SOUTH, Direction.WEST,
            Direction.WEST, Direction.NORTH
    );
}

