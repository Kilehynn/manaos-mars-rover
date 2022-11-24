package domain.service;

import domain.entity.BoardEntity;
import domain.entity.RoverEntity;
import lombok.val;
import org.junit.jupiter.api.Test;
import utils.exceptions.InputFormatException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoverServiceTest {

    RoverService roverService = new RoverService();

    @Test
    void parseInput_EmptyString() {
        val exception = assertThrows(InputFormatException.class, () -> roverService.parseInput(""));
        assertEquals("Input must not be empty", exception.getMessage());
    }

    @Test
    void parseInput_EvenNumberOfLines() {
        val exception = assertThrows(InputFormatException.class, () -> roverService.parseInput("5 5\n1 2"));
        assertEquals("Input must have an odd number of lines", exception.getMessage());
    }
    @Test
    void parseBoard_InvalidBoardSize_BadFormat() {
        var exception = assertThrows(InputFormatException.class, () -> roverService.parseBoard(new BoardEntity(), "5 "));
        assertEquals("The first line must contain the board upper-right coordinates, separated by a space", exception.getMessage());
        exception = assertThrows(InputFormatException.class, () -> roverService.parseBoard(new BoardEntity(), " 5 "));
        assertEquals("The first line must contain the board upper-right coordinates, separated by a space", exception.getMessage());
    }

    @Test
    void parseBoard_InvalidBoardSize_NotAnInteger() {
        var exception = assertThrows(InputFormatException.class, () -> roverService.parseBoard(new BoardEntity(), "5 A\n"));
        assertEquals("The board height must be a positive integer", exception.getMessage());
        exception = assertThrows(InputFormatException.class, () -> roverService.parseBoard(new BoardEntity(), "A A\n"));
        assertEquals("The board width must be a positive integer", exception.getMessage());
    }

    @Test
    void parseBoard_InvalidBoardSize_NegativeInteger() {
        var exception = assertThrows(InputFormatException.class, () -> roverService.parseBoard(new BoardEntity(), "-5 5\n"));
        assertEquals("The board width must be a positive integer", exception.getMessage());
        exception = assertThrows(InputFormatException.class, () -> roverService.parseBoard(new BoardEntity(), "5 -5\n"));
        assertEquals("The board height must be a positive integer", exception.getMessage());
    }

    @Test
    void parseBoard_InvalidBoardSize_Zero() {
        var exception = assertThrows(InputFormatException.class, () -> roverService.parseBoard(new BoardEntity(), "0 5"));
        assertEquals("The board width and height must be integers greater than 0", exception.getMessage());
        exception = assertThrows(InputFormatException.class, () -> roverService.parseBoard(new BoardEntity(), "5 0"));
        assertEquals("The board width and height must be integers greater than 0", exception.getMessage());
    }

    @Test
    void parseBoard_Success() {
        val ref = new Object() {
            BoardEntity board;
        };
        assertDoesNotThrow(() -> ref.board = roverService.parseBoard(new BoardEntity(), "5 5"));
        assertEquals(5, ref.board.getWidth());
        assertEquals(5, ref.board.getHeight());
    }

    @Test
    void parseRover_EmptyString() throws InputFormatException {
        assertEquals(0, roverService.parseRovers(5, 5, new ArrayList<>()).size());
    }

    @Test
    void parseRover_BadRoverDefinition() {
        var exception = assertThrows(InputFormatException.class, () -> roverService.parseRovers(5, 5, List.of("1 2", "LMLMLMLMM")));
        assertEquals("Rover position must be three values separated by a space", exception.getMessage());

        exception = assertThrows(InputFormatException.class, () -> roverService.parseRovers(5, 5, List.of("1 2 3 4", "LMLMLMLMM")));
        assertEquals("Rover position must be three values separated by a space", exception.getMessage());

        exception = assertThrows(InputFormatException.class, () -> roverService.parseRovers(5, 5, List.of("-1 2 N", "LMLMLMLMM")));
        assertEquals("Rover x coordinate must be a positive integer", exception.getMessage());

        exception = assertThrows(InputFormatException.class, () -> roverService.parseRovers(5, 5, List.of("1 -2 N", "LMLMLMLMM")));
        assertEquals("Rover y coordinate must be a positive integer", exception.getMessage());

        exception = assertThrows(InputFormatException.class, () -> roverService.parseRovers(5, 5, List.of("6 2 S", "LMLMLMLMM")));
        assertEquals("Rover x coordinate must be within the board", exception.getMessage());

        exception = assertThrows(InputFormatException.class, () -> roverService.parseRovers(5, 5, List.of("1 6 S", "LMLMLMLMM")));
        assertEquals("Rover y coordinate must be within the board", exception.getMessage());

        exception = assertThrows(InputFormatException.class, () -> roverService.parseRovers(5, 5, List.of("1 2 AD", "LMLMLMLMM")));
        assertEquals("Rover direction must be a single character", exception.getMessage());

        exception = assertThrows(InputFormatException.class, () -> roverService.parseRovers(5, 5, List.of("1 2 A", "LMLMLMLMM")));
        assertEquals("Rover direction must be one of N, E, S, W", exception.getMessage());

        exception = assertThrows(InputFormatException.class, () -> roverService.parseRovers(5, 5, List.of("1 2 N","LETMEIN")));
        assertEquals("Rover instructions must be a string of L, M or R", exception.getMessage());

    }

    @Test
    void parseRover_ABitCrowded() {
        var exception = assertThrows(InputFormatException.class, () -> roverService.parseRovers(5, 5, List.of("1 2 N","LMR","1 2 N","LMR")));
        assertEquals("Rovers cannot share the same position", exception.getMessage());
    }

    @Test
    void parseRover_Success() {
        val ref = new Object() {
            List<RoverEntity> rovers;
        };
        assertDoesNotThrow(() -> ref.rovers = roverService.parseRovers(5, 5, List.of("1 2 N", "LMLMLMLMM")));
        assertEquals(1, ref.rovers.size());
        assertEquals(1, ref.rovers.get(0).getPosition().x);
        assertEquals(2, ref.rovers.get(0).getPosition().y);
        assertEquals("N", ref.rovers.get(0).getDirection().direction);
        assertEquals("LMLMLMLMM", ref.rovers.get(0).getInstructions());


        assertDoesNotThrow(() -> ref.rovers = roverService.parseRovers(5, 5, List.of("1 2 N", "LMLMLMLMM", "3 3 E", "MMRMMRMRRM")));
        assertEquals(2, ref.rovers.size());
        assertEquals(1, ref.rovers.get(0).getPosition().x);
        assertEquals(2, ref.rovers.get(0).getPosition().y);
        assertEquals("N", ref.rovers.get(0).getDirection().direction);
        assertEquals("LMLMLMLMM", ref.rovers.get(0).getInstructions());
        assertEquals(3, ref.rovers.get(1).getPosition().x);
        assertEquals(3, ref.rovers.get(1).getPosition().y);
        assertEquals("E", ref.rovers.get(1).getDirection().direction);
        assertEquals("MMRMMRMRRM", ref.rovers.get(1).getInstructions());

    }

    @Test
    void parseInput_Success() {
        val ref = new Object() {
            BoardEntity board;
        };
        assertDoesNotThrow(() -> ref.board = roverService.parseInput("5 5\n1 2 N\nLMLMLMLMM"));
        assertEquals(5, ref.board.getWidth());
        assertEquals(5, ref.board.getHeight());
        assertEquals(1, ref.board.getRovers().size());
        assertEquals(1, ref.board.getRovers().get(0).getPosition().x);
        assertEquals(2, ref.board.getRovers().get(0).getPosition().y);
        assertEquals("N", ref.board.getRovers().get(0).getDirection().direction);
        assertEquals("LMLMLMLMM", ref.board.getRovers().get(0).getInstructions());


        assertEquals("1 2 N\n", ref.board.toString());
    }
}