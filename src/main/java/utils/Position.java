package utils;

import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class Position {
    public int x;
    public int y;

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return x == position.x && y == position.y;
    }

    @Override public int hashCode() {
        return Objects.hash(x, y);
    }
}
