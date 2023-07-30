package chess.pieces;

import chess.ChessBoard;
import chess.Chess;
import chess.Position;

public class Queen extends ChessPiece {

    public Queen(Color color) {
        super(color);
        if (color == Color.WHITE) {
            super.chessPieceView.setImage(Chess.WHITE_QUEEN_IMAGE);
        } else {
            super.chessPieceView.setImage(Chess.BLACK_QUEEN_IMAGE);
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
        // Works because the two functions below are so thorough. Can't ever be both.
        if (ChessBoard.isDiagonalMove(diff) || ChessBoard.isAdjacentMove(diff)) {
            isValid = true;
        }
        return isValid;
    }
}
