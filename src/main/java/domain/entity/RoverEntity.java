package domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import utils.Direction;
import utils.Position;

@AllArgsConstructor @NoArgsConstructor @With @Getter
public class RoverEntity {
    private Position position;
    private Direction direction;
    private String instructions;

    public void move()
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override public String toString() {
        return position.x + " " + position.y + " " + direction.direction;
    }
}
