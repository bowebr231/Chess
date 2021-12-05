package chess;

import chess.pieces.ChessPiece;

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
