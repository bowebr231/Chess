package chess.pieces;

import chess.ChessBoard;
import chess.Position;

public class King extends ChessPiece {

    public King(PieceColor color) {
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
        return isValidMove(start.getDifference(end))
                && !ChessBoard.isOutOfBounds(end)
                && !ChessBoard.isPieceBlockingPath(start, end, color);
    }

    /**
     * Since the Bishop's x and y movement ratio is always the same check by this.
     *
     * @param diff Position end - start
     * @return true if the move is valid according to ChessPiece rules
     */
    private boolean isValidMove(Position diff) {
        boolean isValid = false;
        if (Math.abs(diff.getY()) == 1 || Math.abs(diff.getX()) == 1) {
            isValid = true;
        }
        return isValid;
    }
}
