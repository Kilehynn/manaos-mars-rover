package domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;

@AllArgsConstructor @NoArgsConstructor @With @Getter
public class BoardEntity {
    private int width;
    private int height;
    private ArrayList<RoverEntity> rovers;

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        for (RoverEntity rover : rovers) {
            sb.append(rover.toString());
            sb.append("\n");
        }
        return sb.toString();
    }


}
