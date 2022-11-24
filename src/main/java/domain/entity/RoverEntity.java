package domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import utils.Direction;
import utils.Position;
import utils.TurnCompute;
import utils.exceptions.MoveException;
import utils.log.Logged;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @With @Getter
public class RoverEntity implements Logged {
    private Position position;
    private Direction direction;
    private String instructions;

    public void move(int width, int height, List<Position> roversPositions) throws MoveException {
        if (!instructions.matches("[LRM]+")) {
            throw new MoveException("Instructions must only contain L, R or M");
        }
        for (char instruction : instructions.toCharArray()) {
            switch (instruction) {
                case 'L' -> direction = TurnCompute.turnLeft.get(direction);
                case 'R' -> direction = TurnCompute.turnRight.get(direction);
                case 'M' -> moveForward();
                default -> throw new IllegalArgumentException("Invalid instruction: " + instruction);
            }
            verifyPosition(width, height);
        }

        if (roversPositions.contains(position)) {
            throw new MoveException("Rover cannot move to a position occupied by another rover");
        }
    }

    private void verifyPosition(int width, int height) throws MoveException {
        if (position.x < 0 || position.x > width || position.y < 0 || position.y > height) {
            throw new MoveException("Move out of bounds");
        }
    }

    void moveForward() {

        switch (direction) {
            case NORTH -> position.y++;
            case EAST -> position.x++;
            case SOUTH -> position.y--;
            case WEST -> position.x--;
        }
    }

    @Override public String toString() {
        return position +  " " + direction.direction;
    }
}
