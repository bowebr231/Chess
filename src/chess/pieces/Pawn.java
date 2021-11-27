package chess.pieces;

public class Pawn extends ChessPiece {
    public Pawn(PieceColor color) {
        super(color);
    }

    // Note: need to handle first move since can move 2 positions at start.
}
