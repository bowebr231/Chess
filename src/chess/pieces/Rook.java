package chess.pieces;

import chess.ChessBoard;
import chess.Main;
import chess.Position;

public class Rook extends ChessPiece {

    public Rook(PieceColor color) {
        super(color);
        if (color == PieceColor.WHITE) {
            super.chessPieceView.setImage(Main.WHITE_ROOK_IMAGE);
        } else {
            super.chessPieceView.setImage(Main.BLACK_ROOK_IMAGE);
        }
        super.chessPieceView.setPiece(this);
    }

    /**
     * Determines if the piece can move to the desired position
     * @param start
     * @param end
     * @return true if can move
     */
    @Override
    public boolean canMove(Position start, Position end) {
        return isValidMove(start.getDifference(end))
                && !ChessBoard.isOutOfBounds(end)
                && !ChessBoard.isPieceBlockingPath(start, end, color);
    }

    /**
     * Alg Summary:
     *     If the selected move stays in the same row or column as the starting position.
     *
     * @param diff Position end - start
     * @return true if the move is valid according to ChessPiece rules
     */
    private boolean isValidMove(Position diff) {
        boolean isValid = false;
        if (ChessBoard.isAdjacentMove(diff)) {
            isValid = true;
        }
        return isValid;
    }
}

