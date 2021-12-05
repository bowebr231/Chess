package chess.pieces;

import chess.ChessBoard;
import chess.Position;

public class Rook extends ChessPiece {

    public Rook(PieceColor color) {
        super(color);
    }

    /**
     * Determines if the piece can move to the desired position
     * @param start
     * @param end
     * @return true if can move
     */
    @Override
    public boolean canMove(Position start, Position end) {
        return isValidMove(start.getDifference(end)) && super.canMove(start, end)
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
        if (isAdjacentMove(diff)) {
            isValid = true;
        }
        return isValid;
    }
}

