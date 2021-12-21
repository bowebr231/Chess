package chess.pieces;

import chess.ChessBoard;
import chess.Main;
import chess.Position;

public class Knight extends ChessPiece {

    public Knight(PieceColor color) {
        super(color);
        if (color == PieceColor.WHITE) {
            super.chessPieceView.setImage(Main.WHITE_KNIGHT_IMAGE);
        } else {
            super.chessPieceView.setImage(Main.BLACK_KNIGHT_IMAGE);
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
                && !ChessBoard.isOutOfBounds(end);
    }

    /**
     * Originally I just check the hard-coded differences for all 6 positions from the starting position.
     *
     *         Reduced:
     *         Regions: top-left, top-right, bottom-left, bottom-right.
     *         The behavior is essentially the same. The core behavior can be captured in a single region,
     *         and the rest can be reduced by using absolute value functions, I think.
     *
     *         If you add the total x and y difference together from the start position it should always equal 3.
     *         However, x could be 3... So, check that no x or y is zero.
     * @param diff Position end - start
     * @return true if the move is valid according to ChessPiece rules
     */
    private boolean isValidMove(Position diff) {
        boolean isValid = false;
        if (diff.getY() != 0 && diff.getX() != 0
                && Math.abs(diff.getY()) + Math.abs(diff.getX()) == 3) {
            isValid = true;
        }
        return isValid;
    }

}
