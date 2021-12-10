package chess;

import chess.pieces.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    GridPane chessBoard;

    final StackPane[][] chessBoardView = new StackPane[ChessBoard.getSizeY()][ChessBoard.getSizeX()];

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Connect to fxml GridPane squares
        Integer y;
        Integer x;
        for (Node node : chessBoard.getChildren())
        {
            y = GridPane.getRowIndex(node);
            x = GridPane.getColumnIndex(node);

            if (y == null) {
                y = 0;
            }
            if (x == null) {
                x = 0;
            }
            chessBoardView[y][x] = (StackPane) node;

            // System.out.println("y="+GridPane.getRowIndex(node));
            // System.out.println("x="+GridPane.getColumnIndex(node));
            // System.out.println();
        }

        for (x = 0; x < ChessBoard.getSizeY(); x++) {
            setChessBoardViewPosition(new Position(1, x), new Pawn(ChessPiece.PieceColor.WHITE));
            setChessBoardViewPosition(new Position(6, x), new Pawn(ChessPiece.PieceColor.BLACK));
        }

        // White Pieces
        setChessBoardViewPosition(new Position(0, 0), new Rook(ChessPiece.PieceColor.WHITE));
        setChessBoardViewPosition(new Position(0, 1), new Knight(ChessPiece.PieceColor.WHITE));
        setChessBoardViewPosition(new Position(0, 2), new Bishop(ChessPiece.PieceColor.WHITE));
        setChessBoardViewPosition(new Position(0, 3), new King(ChessPiece.PieceColor.WHITE));
        setChessBoardViewPosition(new Position(0, 4), new Queen(ChessPiece.PieceColor.WHITE));
        setChessBoardViewPosition(new Position(0, 5), new Bishop(ChessPiece.PieceColor.WHITE));
        setChessBoardViewPosition(new Position(0, 6), new Knight(ChessPiece.PieceColor.WHITE));
        setChessBoardViewPosition(new Position(0, 7), new Rook(ChessPiece.PieceColor.WHITE));

        // Black Pieces
        setChessBoardViewPosition(new Position(7, 0), new Rook(ChessPiece.PieceColor.BLACK));
        setChessBoardViewPosition(new Position(7, 1), new Knight(ChessPiece.PieceColor.BLACK));
        setChessBoardViewPosition(new Position(7, 2), new Bishop(ChessPiece.PieceColor.BLACK));
        setChessBoardViewPosition(new Position(7, 4), new Queen(ChessPiece.PieceColor.BLACK));
        setChessBoardViewPosition(new Position(7, 3), new King(ChessPiece.PieceColor.BLACK));
        setChessBoardViewPosition(new Position(7, 5), new Bishop(ChessPiece.PieceColor.BLACK));
        setChessBoardViewPosition(new Position(7, 6), new King(ChessPiece.PieceColor.BLACK));
        setChessBoardViewPosition(new Position(7, 7), new Rook(ChessPiece.PieceColor.BLACK));



        // ImageView piece = new ImageView(new Image(inputStream));
        // chessBoardView[0][0].getChildren().add(piece);

    }

    public void setChessBoardViewPosition(Position pos, ChessPiece piece) {
        chessBoardView[ChessBoard.getSizeY() - 1 - pos.getY()][pos.getX()].getChildren()
                .clear();

        chessBoardView[ChessBoard.getSizeY() - 1 - pos.getY()][pos.getX()].getChildren()
                .add(piece.getImage());
    }

    private void connectBoardView() {

    }
}
