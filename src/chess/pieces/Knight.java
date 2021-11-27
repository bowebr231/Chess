package chess.pieces;

import chess.Position;

public class Knight extends ChessPiece {

    public Knight(PieceColor color) {
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
        return isValidMove(start.getDifference(end)) && super.canMove(start, end);
    }

    /**
     *
     * @param diff Position end - start
     * @return true if the move is valid according to ChessPiece rules
     */
    private boolean isValidMove(Position diff) {
        boolean isValid = false;
        if (diff.getY() == -1) {
            if (diff.getX() == 2 || diff.getX() == -2) {
                isValid = true;
            }
        } else if (diff.getY() == -2) {
            if (diff.getX() == 1 || diff.getX() == -1) {
                isValid = true;
            }
        } else if (diff.getY() == 1) {
            if (diff.getX() == 2 || diff.getX() == -2) {
                isValid = true;
            }
        } else if (diff.getY() == 2) {
            if (diff.getX() == 1 || diff.getX() == -1) {
                isValid = true;
            }
        }
        return isValid;
    }

}
