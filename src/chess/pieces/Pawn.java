package chess.pieces;

import chess.ChessBoard;
import chess.Position;

public class Pawn extends ChessPiece {

    private boolean directionIsUp;
    private boolean isFirstMove;
    public Pawn(PieceColor color) {
        super(color);
        isFirstMove = true;
    }

    /**
     * Determines if the piece can move to the desired position
     * @param start
     * @param end
     * @return true if can move
     */
    @Override
    public boolean canMove(Position start, Position end) {
        if (isFirstMove) {
            // Determine pawn's sense of direction by checking which end of the board it's on
            if (start.getY() < ChessBoard.getSizeY() / 2) {
                directionIsUp = true;
            } else {
                directionIsUp = false;
            }
        }
        return isValidMove(start, end) && super.canMove(start, end);
    }

    /**
     *
     *
     * @param start Position
     * @param end Position
     * @return true if the move is valid according to ChessPiece rules
     */
    private boolean isValidMove(Position start, Position end) {
        /**
         * Cases:
         * 1. Move up one place.
         * 2. Diagonal attack.
         * 3. Move twice up on first move (Optionally)
         * 4. A special move called En Passant (May not implement)
         */
        boolean isValid = false;
        Position diff = start.getDifference(end);

        if (isCorrectDirection(diff)) {
            // Move 1 or 2 positions
            if (Math.abs(diff.getY()) == 1 && Math.abs(diff.getX()) == 0
                    || Math.abs(diff.getY()) == 2 && Math.abs(diff.getX()) == 0 && isFirstMove) {
                isValid = true;

                // Diagonal attack
            } else if (Math.abs(diff.getX()) == 1 && Math.abs(diff.getY()) == 1
                    && ChessBoard.isEnemyPiecePresent(end, this.color)) {
                isValid = true;
            }
        }
        return isValid;
    }

    private boolean didMoveUp(Position diff) {
        if (diff.getY() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isCorrectDirection(Position diff) {
        if (didMoveUp(diff) == directionIsUp) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean moveToPosition(Position start, Position end) {
        boolean moveSuccessful = super.moveToPosition(start, end);

        if (isFirstMove == true && moveSuccessful == true) {
            isFirstMove = false;
        }
        return moveSuccessful;
    }
}

// NOTES:

/*
Move Alg:
1. isFirstMove then can move 'up' 2
2. !isFirstMove then can move 'up' 1
3. if 'enemy' is upward 1 diagonal then can move to that position.
 */

/*
Sense of direction:

8x8 (Y,X) board.

0x# is facing up, 8x# is facing down.
How to tell if a move gets you closer to the other side?

If end is closer to top than start position was then you moved up.
If end is closer to bottom than start position was then you moved down.
 */
