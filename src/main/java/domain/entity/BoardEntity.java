package domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor @NoArgsConstructor @With @Getter
public class BoardEntity {
    private int width;
    private int height;

    @Override public String toString() {
        return new StringBuilder().toString();
    }

}
