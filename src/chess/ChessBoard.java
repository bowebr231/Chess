package chess;

import chess.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {

    private static final int SIZE_Y = 8;
    private static final int SIZE_X = 8;
    private static final ChessPiece[][] chessBoard = new ChessPiece[SIZE_Y][SIZE_X];

    public ChessBoard() {
        // Create 8x8 empty board for now.
        for (int y = 0; y < SIZE_Y; y++) {
            for (int x = 0; x < SIZE_X; x++) {
                chessBoard[y][x] = null;
            }
        }
    }

    public static boolean isOutOfBounds(Position pos) {
        return pos.getY() < 0 || pos.getY() >= SIZE_Y
                || pos.getX() < 0 || pos.getX() >= SIZE_X;
    }

    public static int getSizeY() {
        return SIZE_Y;
    }

    public static int getSizeX() {
        return SIZE_X;
    }

    public static Position findPiecePosition(ChessPiece piece) {
        for (int y = 0; y < SIZE_Y; y++) {
            for (int x = 0; x < SIZE_X; x++) {
                if (chessBoard[y][x] == piece) {
                    return new Position(y, x);
                }
            }
        }
        // If not found
        return null;
    }

    public static boolean isEnemyPiecePresent(Position pos, ChessPiece.PieceColor friendly) {
        return getPiece(pos) != null
                && getPiece(pos).getColor() != friendly;
    }

    /*
    Alg:
    Get the numbers needed to check the GameBoard array.
    1. Start and end positions give you the endpoints of the line (all moves that need checked are lines)
    2. Use diff as count needed for looping through numbers.
    3. Loop to get all number indices on path.
    4. For handling which directions (X or Y)... Abs and then something special for accessing the correct array index.
     */
    public static boolean isPieceBlockingPath(Position start, Position end, ChessPiece.PieceColor friendly) {
        boolean result = false;
        List<ChessPiece> path = getLine(start, end);

        int count = 0;
        for (ChessPiece position : path) {
            if (position != null
                    && path.size() - 1 != count) {
                result = true;
                break;
            } else if (position != null
                    && path.size() - 1 == count && position.getColor() == friendly) {
                result = true;
                break;
            }
            count++;
        }
        return result;
    }

    public static List<ChessPiece> getLine(Position start, Position end) {
        Position diff = start.getDifference(end);
        List<ChessPiece> line = new ArrayList();


        if (ChessPiece.isAdjacentMove(diff)) {
            Position incrementInDirection;
            Math.
            if (diff.getY() < 0 && diff.getX() == 0) {
                incrementInDirection = Position.DOWN;
            } else if (diff.getY() > 0 && diff.getX() == 0) {
                incrementInDirection = Position.UP;
            } else if (diff.getY() == 0 && diff.getX() < 0) {
                incrementInDirection = Position.LEFT;
            } else {
                incrementInDirection = Position.RIGHT;
            }

            // Looping to create Objects caused
            // Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
            for (int i = 1; i <= Math.abs(diff.getX()) + Math.abs(diff.getY()); i++) {
                line.add(ChessBoard.getPiece(start.add(incrementInDirection)));
            }

        } else if (ChessPiece.isDiagonalMove(diff)) {
            Position incrementInDirection;
            if (diff.getY() < 0 && diff.getX() < 0) {
                incrementInDirection = Position.DOWN_LEFT;
            } else if (diff.getY() > 0 && diff.getX() < 0) {
                incrementInDirection = Position.UP_LEFT;
            } else if (diff.getY() < 0 && diff.getX() > 0) {
                incrementInDirection = Position.DOWN_RIGHT;
            } else {
                incrementInDirection = Position.UP_RIGHT;
            }

            for (int i = 1; i <= Math.abs(diff.getX()); i++) {
                line.add(ChessBoard.getPiece(start.add(incrementInDirection)));
            }
        }
        return line;
    }

    public static ChessPiece getPiece(Position pos) {
        return chessBoard[pos.getY()][pos.getX()];
    }

    public static boolean putPieceHere(Position destination, ChessPiece piece) {
        boolean placementSuccessful = false;
        // Not out of bounds, and current position is not taken
        if (!isOutOfBounds(destination) && getPiece(destination) == null) {

            Position current = findPiecePosition(piece);
            // Piece was not on the board yet
            if (current == null) {
                chessBoard[destination.getY()][destination.getX()] = piece;
            } else {
                chessBoard[current.getY()][current.getX()] = null;
                chessBoard[destination.getY()][destination.getX()] = piece;
            }
            placementSuccessful = true;
        }
        return placementSuccessful;
    }

    public static void clearBoard() {
        for (int y = 0; y < SIZE_Y; y++) {
            for (int x = 0; x < SIZE_X; x++) {
                chessBoard[y][x] = null;
            }
        }
    }
}
