package domain.service;

import domain.entity.BoardEntity;
import lombok.val;
import org.junit.jupiter.api.Test;
import utils.exceptions.InputFormatException;

import static org.junit.jupiter.api.Assertions.*;

class RoverServiceTest {

    RoverService roverService = new RoverService();

    @Test
    void parseInput_EmptyString() {
        assertThrows(InputFormatException.class, () -> roverService.parseInput(""), "Input must not be empty");
    }

    @Test
    void parseInput_EvenNumberOfLines() {
        assertThrows(InputFormatException.class, () -> roverService.parseInput("5 5\n1 2"), "Input must have an odd number of lines");
    }

    @Test
    void parseInput_OddNumberOfLines() {
        assertDoesNotThrow(() -> roverService.parseInput("5 5\n1 2\nLMLMLMLMM"));
    }

    @Test
    void parseInput_InvalidBoardSize_BadFormat() {
        assertThrows(InputFormatException.class, () -> roverService.parseInput("5 \n"), "The first line must contain the board upper-right coordinates, separated by a space");
        assertThrows(InputFormatException.class, () -> roverService.parseInput(" \n"), "The first line must contain the board upper-right coordinates, separated by a space");
    }

    @Test
    void parseInput_InvalidBoardSize_NotAnInteger() {
        assertThrows(InputFormatException.class, () -> roverService.parseInput("5 A\n"), "The board height must be a positive integer");
        assertThrows(InputFormatException.class, () -> roverService.parseInput("A A\n"), "The board width must be a positive integer");
    }

    @Test
    void parseInput_InvalidBoardSize_NegativeInteger() {
        assertThrows(InputFormatException.class, () -> roverService.parseInput("-5 5\n"), "The board width must be a positive integer");
        assertThrows(InputFormatException.class, () -> roverService.parseInput("5 -5\n"), "The board height must be a positive integer");
    }

    @Test
    void parseInput_InvalidBoardSize_Zero()
    {
        assertThrows(InputFormatException.class, () -> roverService.parseInput("0 5\n"), "The board width and height must be integers greater than 0");
        assertThrows(InputFormatException.class, () -> roverService.parseInput("5 0\n"), "The board width and height must be integers greater than 0");
    }

    @Test
    void parseInput_Success()
    {
        val ref = new Object() {
            BoardEntity board;
        };
        assertDoesNotThrow(() -> ref.board = roverService.parseInput("5 5\n1 2 N\nLMLMLMLMM"));
        assertEquals(5, ref.board.getWidth());
        assertEquals(5, ref.board.getHeight());
    }
}