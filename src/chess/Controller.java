package chess;

import chess.pieces.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    GridPane boardGridPane;

    final StackPane[][] chessBoardView = new StackPane[ChessBoard.getSizeY()][ChessBoard.getSizeX()];

    private ChessPieceView prevSelectedPiece = null;
    private ChessPiece.PieceColor turnColor = ChessPiece.PieceColor.WHITE;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Connect to fxml GridPane squares
        Integer y;
        Integer x;
        for (Node node : boardGridPane.getChildren())
        {
            y = GridPane.getRowIndex(node);
            x = GridPane.getColumnIndex(node);

            if (y == null) {
                y = 0;
            }
            if (x == null) {
                x = 0;
            }
            int convertedY = (ChessBoard.getLastYIndex()) - y;
            chessBoardView[convertedY][x] = (StackPane) node;

            // System.out.println("y="+GridPane.getRowIndex(node));
            // System.out.println("x="+GridPane.getColumnIndex(node));
            // System.out.println();
        }

        // Add user 'click' game interface
        /* State Machine:
        1. If square is clicked with a piece on it, and it's that color's turn then piece is 'selected'.
        2. If another square is clicked then check if move is valid. If valid then move the piece there.
            Deselect after this 2nd click regardless if valid or not.
        3. If a valid move is performed then turn changes to next color player.

        Note: need way for View and Model to stay in sync and interact.
         */

        for (y = 0; y < ChessBoard.getSizeY(); y++) {
            for (x = 0; x < ChessBoard.getSizeX(); x++) {
                chessBoardView[y][x].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // Get and Check Square
                        StackPane boardSquare = (StackPane) event.getSource();
                        boolean squareIsEmpty = boardSquare.getChildren().size() == 0;

                        // Get Game Piece
                        ChessPieceView selectedPiece = null;
                        if (!squareIsEmpty) {
                            selectedPiece = ((ChessPieceView)boardSquare.getChildren().get(0));
                        }

                        // Try to move a piece
                        if (prevSelectedPiece != null) {
                            // Attempt move
                            // INSERT CODE
                            System.out.println("Attempting to move to: "+getChessBoardViewPosition(boardSquare).getY()+", "+getChessBoardViewPosition(boardSquare).getX());
                            System.out.println("From Position: "+ChessBoard.findPiecePosition(prevSelectedPiece.getPiece()).getY()+", "+ChessBoard.findPiecePosition(prevSelectedPiece.getPiece()).getX());

                            System.out.println("Attempting to move to: "+getChessBoardViewPosition(boardSquare).getY()+", "+getChessBoardViewPosition(boardSquare).getX());
                            System.out.println("From Position: "+ChessBoard.findPiecePosition(prevSelectedPiece.getPiece()).getY()+", "+ChessBoard.findPiecePosition(prevSelectedPiece.getPiece()).getX());
                            if (prevSelectedPiece.getPiece().moveToPosition(
                                    ChessBoard.findPiecePosition(prevSelectedPiece.getPiece()),
                                    getChessBoardViewPosition(boardSquare))
                            ) {
                                moveChessBoardViewPosition(ChessBoard.findPiecePosition(prevSelectedPiece.getPiece()),
                                        prevSelectedPiece);
                                changeTurnColor();
                                System.out.println("Piece moved!: "+prevSelectedPiece.getPiece());
                            }
                            clearSelection();
                            System.out.println("Selection cleared!");

                        } else {
                            // Select valid piece to move
                            if (!squareIsEmpty && selectedPiece.getPiece().getColor() == turnColor) {
                                prevSelectedPiece = selectedPiece;
                                System.out.println("New piece selected: "+selectedPiece.getPiece());
                            }
                        }
                    }
                });
            }
        }

        // Set Chess Board Pieces
        setupBoardPieceModelAndView();


        // ImageView piece = new ImageView(new Image(inputStream));
        // chessBoardView[0][0].getChildren().add(piece);

    }

    private void setupBoardPieceModelAndView() {
        // VIEWS
        // White and Black Pawns
        for (int x = 0; x < ChessBoard.getSizeY(); x++) {
            setChessBoardViewPosition(new Position(1, x), new Pawn(ChessPiece.PieceColor.WHITE));
            setChessBoardViewPosition(new Position(6, x), new Pawn(ChessPiece.PieceColor.BLACK));
        }

        // Other White Pieces
        setChessBoardViewPosition(new Position(0, 0), new Rook(ChessPiece.PieceColor.WHITE));
        setChessBoardViewPosition(new Position(0, 1), new Knight(ChessPiece.PieceColor.WHITE));
        setChessBoardViewPosition(new Position(0, 2), new Bishop(ChessPiece.PieceColor.WHITE));
        setChessBoardViewPosition(new Position(0, 3), new King(ChessPiece.PieceColor.WHITE));
        setChessBoardViewPosition(new Position(0, 4), new Queen(ChessPiece.PieceColor.WHITE));
        setChessBoardViewPosition(new Position(0, 5), new Bishop(ChessPiece.PieceColor.WHITE));
        setChessBoardViewPosition(new Position(0, 6), new Knight(ChessPiece.PieceColor.WHITE));
        setChessBoardViewPosition(new Position(0, 7), new Rook(ChessPiece.PieceColor.WHITE));

        // Other Black Pieces
        setChessBoardViewPosition(new Position(7, 0), new Rook(ChessPiece.PieceColor.BLACK));
        setChessBoardViewPosition(new Position(7, 1), new Knight(ChessPiece.PieceColor.BLACK));
        setChessBoardViewPosition(new Position(7, 2), new Bishop(ChessPiece.PieceColor.BLACK));
        setChessBoardViewPosition(new Position(7, 4), new Queen(ChessPiece.PieceColor.BLACK));
        setChessBoardViewPosition(new Position(7, 3), new King(ChessPiece.PieceColor.BLACK));
        setChessBoardViewPosition(new Position(7, 5), new Bishop(ChessPiece.PieceColor.BLACK));
        setChessBoardViewPosition(new Position(7, 6), new King(ChessPiece.PieceColor.BLACK));
        setChessBoardViewPosition(new Position(7, 7), new Rook(ChessPiece.PieceColor.BLACK));

        // MODEL
        for (int y = 0; y < ChessBoard.getSizeY(); y++) {
            for (int x = 0; x < ChessBoard.getSizeX(); x++) {
                ChessPiece piece = null;
                if ((chessBoardView[y][x].getChildren()).size() != 0) {
                    piece = ((ChessPieceView)chessBoardView[y][x].getChildren().get(0)).getPiece();
                }
                ChessBoard.putPieceHere(new Position(y, x), piece);
            }
        }

//        for (int y = 0; y < ChessBoard.getSizeY(); y++) {
//            for (int x = 0; x < ChessBoard.getSizeX(); x++) {
//                if (ChessBoard.getPiece(new Position(y, x)) != null) {
//                    System.out.println(ChessBoard.getPiece(new Position(y, x)));
//                }
//            }
//        }
    }

    private void clearSelection() {
        prevSelectedPiece = null;
    }

    private void changeTurnColor() {
        if (turnColor == ChessPiece.PieceColor.WHITE) {
            turnColor = ChessPiece.PieceColor.BLACK;
        } else {
            turnColor = ChessPiece.PieceColor.WHITE;
        }
    }

    public void setChessBoardViewPosition(Position pos, ChessPiece piece) {
        // int convertedY = (ChessBoard.getLastYIndex()) - pos.getY();

        chessBoardView[pos.getY()][pos.getX()].getChildren().clear();
        chessBoardView[pos.getY()][pos.getX()].getChildren().add(piece.getImage());
    }

    public void moveChessBoardViewPosition(Position pos, ChessPieceView view) {
        // int convertedY = (ChessBoard.getLastYIndex()) - pos.getY();

        Position prevPos = getChessBoardViewPosition((StackPane) view.getParent());
        chessBoardView[prevPos.getY()][prevPos.getX()].getChildren().clear();
        chessBoardView[pos.getY()][pos.getX()].getChildren().add(view);
    }

    public Position getChessBoardViewPosition(StackPane square){

        Integer y = GridPane.getRowIndex(square);
        Integer x = GridPane.getColumnIndex(square);

        if (y == null) {
            y = 0;
        }

        if (x == null) {
            x = 0;
        }

        int convertedY = (ChessBoard.getLastYIndex()) - y;
        // System.out.println("Y="+y+", x="+x);
        System.out.println("ConvertedY="+convertedY+", x="+x);
        return new Position(convertedY, x);
    }



//    private boolean isWhitePiece(ImageView view) {
//        boolean isWhite;
//        switch (view.getImage().toString()) {
//            case Main.BLACK_PAWN_IMAGE:
//            case Main.BLACK_ROOK_IMAGE:
//            case Main.BLACK_BISHOP_IMAGE:
//            case Main.BLACK_KNIGHT_IMAGE:
//            case Main.BLACK_QUEEN_IMAGE:
//            case Main.BLACK_KING_IMAGE:
//                isWhite = false;
//
//            case Main.WHITE_PAWN_IMAGE:
//            case Main.WHITE_ROOK_IMAGE:
//            case Main.WHITE_BISHOP_IMAGE:
//            case Main.WHITE_KNIGHT_IMAGE:
//            case Main.WHITE_QUEEN_IMAGE:
//            case Main.WHITE_KING_IMAGE:
//                isWhite = true;
//        }
//    }
}
