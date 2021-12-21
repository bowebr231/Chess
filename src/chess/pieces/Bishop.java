package chess.pieces;

import chess.ChessBoard;
import chess.Main;
import chess.Position;

public class Bishop extends ChessPiece {

    public Bishop(PieceColor color) {
        super(color);
        if (color == PieceColor.WHITE) {
            super.chessPieceView.setImage(Main.WHITE_BISHOP_IMAGE);
        } else {
            super.chessPieceView.setImage(Main.BLACK_BISHOP_IMAGE);
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
     * Since the Bishop's x and y movement ratio is always the same check by this.
     *
     * @param diff Position end - start
     * @return true if the move is valid according to ChessPiece rules
     */
    private boolean isValidMove(Position diff) {
        boolean isValid = false;
        if (ChessBoard.isDiagonalMove(diff)) {
            isValid = true;
        }
        return isValid;
    }
}
