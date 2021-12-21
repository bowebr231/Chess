package chess;

import chess.pieces.ChessPiece;
import javafx.scene.image.ImageView;

public class ChessPieceView extends ImageView {
    private ChessPiece piece;

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }
}
