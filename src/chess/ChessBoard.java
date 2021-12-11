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

    public static int getLastYIndex() {
        return SIZE_Y -1;
    }

    public static Position findPiecePosition(ChessPiece piece) {
        for (int y = 0; y < SIZE_Y; y++) {
            for (int x = 0; x < SIZE_X; x++) {
                if (chessBoard[y][x] != null && chessBoard[y][x] == (piece)) {
                    return new Position(y, x);
                }
            }
        }
        // If not found
        return null;
    }

    /**
     * Since the piece's x and y movement ratio is always the same check by this.
     * Equation of a line
     * @param diff
     * @return
     */
    public static boolean isDiagonalMove(Position diff) {
        return diff.getX() != 0 &&
                Math.abs(diff.getY() / diff.getX()) == 1;
    }

    /**
     * Alg Summary:
     *           If the selected move stays in the same row or column as the starting position.
     * @param diff
     * @return
     */
    public static boolean isAdjacentMove(Position diff) {
        return diff.getY() != 0 && diff.getX() == 0 // Same row
                || diff.getX() != 0 && diff.getY() == 0; // Same column
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


        final int destination = path.size() - 1;
        int position = 0;
        for (ChessPiece piece : path) {
            // Doesn't care whether piece is friend or foe unless on the last square.
            // If not friendly then it doesn't count as a block.
            if (piece != null &&
                    (position != destination || piece.getColor() == friendly)) {
                result = true;
                break;
            }
            position++;
        }
        return result;
    }

    public static List<ChessPiece> getLine(Position start, Position end) {
        Position diff = start.getDifference(end);
        List<ChessPiece> line = new ArrayList();
        Position incrementInDirection = Position.getDiffDirection(diff);
        int distance;

        if (isAdjacentMove(diff)) {
            distance = Math.abs(diff.getX()) + Math.abs(diff.getY());
        } else if (isDiagonalMove(diff)) {
            distance = Math.abs(diff.getX());
        } else {
            throw new IllegalArgumentException("The requested line is not adjacent or diagonal on the Chess Board");
        }

        // Starts at the next position since you don't count start
        Position temp = new Position(start);
        for (int i = 1; i <= distance; i++) {
            temp = temp.add(incrementInDirection);
            line.add(ChessBoard.getPiece(temp));
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
