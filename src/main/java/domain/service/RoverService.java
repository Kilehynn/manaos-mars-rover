package domain.service;

import domain.entity.BoardEntity;
import domain.entity.RoverEntity;
import lombok.val;
import utils.Direction;
import utils.Position;
import utils.exceptions.InputFormatException;
import utils.exceptions.MoveException;
import utils.log.Logged;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class RoverService implements Logged {

    public String navigate(String input) throws InputFormatException, MoveException {
        val board = parseInput(input);
        board.getRovers()
             .forEach(roverEntity -> roverEntity.move(board.getWidth(),
                                                      board.getHeight(),
                                                      board.getRovers().stream().filter(rover -> !rover.equals(roverEntity)).map(RoverEntity::getPosition).toList()));
        return board.toString();
    }

    /*
     * ******************************************************************************* *
     *                                                                                 *
     *                                 PACKAGE RESTRICTED                              *
     *                ( should have been private but needed for testing )              *
     * ******************************************************************************* *
     */

    BoardEntity parseInput(String input) throws InputFormatException {
        if (input.isEmpty()) {
            throw new InputFormatException("Input must not be empty");
        }

        val lines = Arrays.stream(input.split("\n")).collect(ArrayList<String>::new, ArrayList::add, ArrayList::addAll);

        if (lines.size() % 2 == 0) {
            throw new InputFormatException("Input must have an odd number of lines");
        }

        val board = parseBoard(new BoardEntity(), lines.remove(0));
        return board.withRovers(parseRovers(board.getWidth(), board.getHeight(), lines));

    }

    BoardEntity parseBoard(BoardEntity board, String boardInfo) throws InputFormatException {

        // Handle case where there is empty elements in the split array
        val boardSize = Arrays.stream(boardInfo.split(" ", 0)).filter(s -> !s.isEmpty()).toList();
        if (boardSize.size() != 2) {
            throw new InputFormatException("The first line must contain the board upper-right coordinates, separated by a space");
        }

        int width = parseInt(boardSize.get(0), "The board width must be a positive integer");
        int height = parseInt(boardSize.get(1), "The board height must be a positive integer");
        if (width <= 0 || height <= 0) {
            throw new InputFormatException("The board width and height must be integers greater than 0");
        }
        return board.withWidth(width).withHeight(height);
    }

    ArrayList<RoverEntity> parseRovers(Integer boardWidth, Integer boardHeight, List<String> lines) throws InputFormatException {
        val rovers = new ArrayList<RoverEntity>();

        if (lines.size() == 0) return rovers;

        if (lines.size() % 2 != 0) {
            throw new InputFormatException("Input must have an odd number of lines");
        }

        for (int i = 0; i < lines.size(); i += 2) {
            String[] roverPosition = lines.get(i).split(" ");
            if (roverPosition.length != 3) {
                throw new InputFormatException("Rover position must be three values separated by a space");
            }
            final Position position = getPosition(boardWidth, boardHeight, rovers, roverPosition);

            try {
                if (roverPosition[2].length() != 1) {
                    throw new InputFormatException("Rover direction must be a single character");
                }
                val direction = Direction.directionFromChar(roverPosition[2].charAt(0));
                if (lines.get(i + 1).matches("[LMR]+")) {
                    val rover = new RoverEntity(position, direction, lines.get(i + 1));
                    rovers.add(rover);
                } else {
                    throw new InputFormatException("Rover instructions must be a string of L, M or R");
                }
            } catch (IllegalArgumentException e) {
                throw new InputFormatException("Rover direction must be one of N, E, S, W");
            }

        }
        return rovers;
    }

    /*
     * ******************************************************************************* *
     *                                                                                 *
     *                                    HELPERS                                      *
     *                                                                                 *
     * ******************************************************************************* *
     */

    private Position getPosition(Integer boardWidth, Integer boardHeight, ArrayList<RoverEntity> rovers, String[] roverPosition) throws InputFormatException {
        val x = parseInt(roverPosition[0], "Rover x coordinate must be a positive integer", "Rover x coordinate must be within the board", boardWidth);
        val y = parseInt(roverPosition[1], "Rover y coordinate must be a positive integer", "Rover y coordinate must be within the board", boardHeight);

        val position = new Position(x, y);
        val isPositionTaken = rovers.stream().filter(rover -> rover.getPosition().equals(position)).findAny();
        if (isPositionTaken.isPresent()) {
            throw new InputFormatException("Rovers cannot share the same position");
        }
        return position;
    }

    private Integer parseInt(String s, String parsingErrorMessage) throws InputFormatException {
        return parseInt(s, parsingErrorMessage, null, null);
    }

    private Integer parseInt(String s, String parsingErrorMessage, String outOfBoundsErrorMessage, Integer upperBound) throws InputFormatException {
        try {
            val number = Integer.parseInt(s);
            if (number < 0) {
                throw new InputFormatException(parsingErrorMessage);
            }
            if (outOfBoundsErrorMessage != null && number > upperBound) {
                throw new InputFormatException(outOfBoundsErrorMessage);
            }
            return number;
        } catch (NumberFormatException e) {
            throw new InputFormatException(parsingErrorMessage);
        }
    }

}
