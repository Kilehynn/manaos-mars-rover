package domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import utils.Direction;

@AllArgsConstructor @NoArgsConstructor @With @Getter
public class RoverEntity {
    private int x;
    private int y;
    private Direction direction;
    private String instructions;

    public void move()
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
