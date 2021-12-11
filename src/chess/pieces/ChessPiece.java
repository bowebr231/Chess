package chess.pieces;

import chess.ChessBoard;
import chess.ChessPieceView;
import chess.Position;
import javafx.scene.image.ImageView;

abstract public class ChessPiece {

    protected final PieceColor color;
    protected ChessPieceView image;


    public enum PieceColor {
        BLACK,
        WHITE
    }

    public ChessPiece(PieceColor color) {
        this.color = color;
        this.image = new ChessPieceView();
        image.setFitHeight(70);
        image.setFitWidth(70);
    }

    /**
     * High-level movement. Checks if piece can move the entire path to a selected board position.
     * Unique to the chess piece rules.
     * @return
     */
    abstract public boolean canMove(Position start, Position end);

    public PieceColor getColor() {
        return color;
    }

    public ImageView getImage() {
        return image;
    }

    public boolean moveToPosition(Position start, Position end) {
        boolean moveSuccessful = false;

        if (canMove(start, end)) {
            moveSuccessful = true;
            ChessBoard.putPieceHere(end, this);
        }
        return moveSuccessful;
    }

    /*
    PreMove Checks:
    1. Cannot move to destroy a King unless 'Check' has already been declared at the end of a previous move.
    2. Allow special option for the rook, king swap if conditions are met. (Whatever it is called)

    Movement algorithm:
    1. User clicks a piece they want to move.
    2. If it's one of their pieces then piece is selected.
    3. User clicks where they want to move the piece to.
    4. If the piece "canMove()" to the selection location then move it there.
        Handles pathfinding, obstacles, and game board boundaries
    5. If the piece lands on an enemy piece then destroy it.

    PostMove Checks:
    1. isCheckmate(). It is a state. The attacking player does not perform an action.
        This check could be a pre or postmove check I think. But, performed only after attacking player moves.
    2. Stalemate check.

    CanMove() Algorithm:
    1. Partly defined by specific piece.
        General Alg:
        1. Inputs: Piece position, selected destination, and game board.
        2. Check piece's valid movement rules: adjacent, diagonal, special path, etc.
            Main thing that's needed is checkValidNext square.
            Note: To check all possible paths/destinations might be more difficult.
            Finally, it's a possibility alg will find a valid path before trying all possible scenarios.

            UPDATE: after double-checking a knight's movement rules this doesn't have to be so complicated.
                    I found a chart that says regardless of the path the knight takes the destinations will
                    always be the same. So, all I have to worry about is primarily checking for obstacles.

                    My movement rules can just do math checks on the destination and starting position coordinates
                    to see if they are valid moves for the piece. Knight can jump pieces too so no obstacles.

                    The other piece types are easier since their movements are linear regardless of if they are
                    diagonal or adjacent.

     */

    // private handleCollision(){};

}
