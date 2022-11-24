package domain.service;

import domain.entity.BoardEntity;
import lombok.val;
import utils.exceptions.InputFormatException;
import utils.log.Logged;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Arrays;

@ApplicationScoped
public class RoverService implements Logged {

    public String navigate(String input) throws InputFormatException {
        val board = parseInput(input);
        return board.toString();
    }

    /*
     * ******************************************************************************* *
     *                                                                                 *
     *                                    HELPERS                                      *
     *                                                                                 *
     * ******************************************************************************* *
     */

    BoardEntity parseInput(String input) throws InputFormatException {
        BoardEntity board = new BoardEntity();

        if (input.isEmpty()) {
            throw new InputFormatException("Input must not be empty");
        }

        val lines = Arrays.stream(input.split("\n")).collect(ArrayList<String>::new, ArrayList::add, ArrayList::addAll);

        if (lines.size() % 2 == 0) {
            throw new InputFormatException("Input must have an odd number of lines");
        }
        String[] boardSize = lines.remove(0).split(" ");
        if (boardSize.length != 2) {
            throw new InputFormatException("The first line must contain the board upper-right coordinates, separated by a space");
        }
        val width = parseInt(boardSize[0], "The board width must be a positive integer");
        val height = parseInt(boardSize[1], "The board height must be a positive integer");
        if (width <= 0 || height <= 0) {
            throw new InputFormatException("The board width and height must be integers greater than 0");
        }
        board = board.withWidth(width).withHeight(height);
        return board;

    }

    private Integer parseInt(String s, String parsingErrorMessage) throws InputFormatException {
        return parseInt(s, parsingErrorMessage, null, null);
    }

    private Integer parseInt(String s, String parsingErrorMessage, String outOfBoundsErrorMessage, Integer upperBound) throws InputFormatException {
        try {
            val number = Integer.parseInt(s);
            if (number < 0 || (outOfBoundsErrorMessage != null && number > upperBound)) {
                throw new InputFormatException(outOfBoundsErrorMessage);
            }
            return number;
        } catch (NumberFormatException e) {
            throw new InputFormatException(parsingErrorMessage);
        }
    }

}
