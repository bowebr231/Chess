package chess;

import chess.pieces.ChessPiece;

public class ChessBoard {

    private static final int SIZE_Y = 8;
    private static final int SIZE_X = 8;
    public static ChessPiece[][] chessBoard = new ChessPiece[SIZE_Y][SIZE_X];

    public ChessBoard() {
        // Create 8x8 empty board for now.
        for (int y = 0; y < SIZE_Y; y++) {
            for (int x = 0; x < SIZE_X; x++) {
                chessBoard[y][x] = null;
            }
        }
    }

    public static boolean isOutOfBounds(Position pos) {
        if (pos.getY() < 0 || pos.getY() >= SIZE_Y || pos.getX() < 0 || pos.getX() >= SIZE_X) {
            return true;
        } else {
            return false;
        }
    }

    public static int getSizeY() {
        return SIZE_Y;
    }

    public static int getSizeX() {
        return SIZE_X;
    }
}
