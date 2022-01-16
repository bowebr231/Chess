package chess;

import chess.pieces.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    GridPane boardGridPane;

    final StackPane[][] chessBoardView = new StackPane[ChessBoard.getSizeY()][ChessBoard.getSizeX()];

    private ChessPieceView prevSelectedPiece = null;
    private StackPane prevSelectedSquare = null;
    private String prevSelectedSquareColor = null;

    private ChessPiece.PieceColor turnColor = ChessPiece.PieceColor.WHITE;

    private PlayerStateMachine whiteStateMachine;
    private PlayerStateMachine blackStateMachine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Connect to fxml GridPane squares
        Integer y;
        Integer x;
        for (Node square : boardGridPane.getChildren())
        {
            Position pos = convertPositionViewToModel(square);
            chessBoardView[pos.getY()][pos.getX()] = (StackPane) square;
        }

        // Add user 'click' game interface

        /* Event Handler:
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
                            Position start = ChessBoard.findPiecePosition(prevSelectedPiece.getPiece());
                            Position end = getChessBoardViewPosition(boardSquare);
                            PlayerStateMachine turnPlayerState = getPlayerStateMachineByTurn();

                            if (prevSelectedPiece.getPiece().canMove(start, end)
                                    && !turnPlayerState.isMoveCheck(prevSelectedPiece.getPiece(), end))
                            {
                                prevSelectedPiece.getPiece().moveToPosition(start, end);
                                moveChessBoardViewPosition(ChessBoard.findPiecePosition(prevSelectedPiece.getPiece()), prevSelectedPiece);
                                changeTurnColor();
                                updateView();

                                turnPlayerState = getPlayerStateMachineByTurn();

                                PlayerStateMachine.PlayerState state = turnPlayerState.getUpdatedState();
                                if ( state == PlayerStateMachine.PlayerState.CHECK) {
                                    System.out.println(turnColor + " player is in CHECK!");
                                } else if (state == PlayerStateMachine.PlayerState.CHECKMATE) {
                                    System.out.println(turnColor + "player is in CHECKMATE! Game Over");
                                }
                            }
                            clearSelection(boardSquare);
                        } else {
                            // Select piece if correct turn color
                            if (!squareIsEmpty && selectedPiece.getPiece().getColor() == turnColor) {
                                prevSelectedPiece = selectedPiece;
                                setSelectionEffect(boardSquare, true);
                            }
                        }
                    }
                });
            }
        }

        // Set Chess Board Pieces
        setupChessModelAndView();
    }

    private PlayerStateMachine getPlayerStateMachineByTurn() {
        return (turnColor == ChessPiece.PieceColor.WHITE) ? whiteStateMachine : blackStateMachine;
    }

    private void setSelectionEffect(StackPane boardSquare, boolean selectTarget) {
        if (selectTarget) {
            prevSelectedSquare = boardSquare;
            prevSelectedSquareColor = boardSquare.getStyle();
            boardSquare.setStyle("-fx-background-color: LIMEGREEN;");
        } else {
            prevSelectedSquare.setStyle(prevSelectedSquareColor);
            prevSelectedSquare = null;
            prevSelectedSquareColor = null;
        }
    }

    private void setupChessModelAndView() {
        // VIEW
        // White and Black Pawns
        for (int x = 0; x < ChessBoard.getSizeY(); x++) {
            setPieceViewPosition(new Position(1, x), new Pawn(ChessPiece.PieceColor.WHITE));
            setPieceViewPosition(new Position(6, x), new Pawn(ChessPiece.PieceColor.BLACK));
        }

        // Init state machines for tracking Check/Checkmate
        King whiteKing = new King(ChessPiece.PieceColor.WHITE);
        King blackKing = new King(ChessPiece.PieceColor.BLACK);
        whiteStateMachine = new PlayerStateMachine(whiteKing);
        blackStateMachine = new PlayerStateMachine(blackKing);

        // Other White Pieces
        setPieceViewPosition(new Position(0, 0), new Rook(ChessPiece.PieceColor.WHITE));
        setPieceViewPosition(new Position(0, 1), new Knight(ChessPiece.PieceColor.WHITE));
        setPieceViewPosition(new Position(0, 2), new Bishop(ChessPiece.PieceColor.WHITE));
        setPieceViewPosition(new Position(0, 3), whiteKing);
        setPieceViewPosition(new Position(0, 4), new Queen(ChessPiece.PieceColor.WHITE));
        setPieceViewPosition(new Position(0, 5), new Bishop(ChessPiece.PieceColor.WHITE));
        setPieceViewPosition(new Position(0, 6), new Knight(ChessPiece.PieceColor.WHITE));
        setPieceViewPosition(new Position(0, 7), new Rook(ChessPiece.PieceColor.WHITE));

        // Other Black Pieces
        setPieceViewPosition(new Position(7, 0), new Rook(ChessPiece.PieceColor.BLACK));
        setPieceViewPosition(new Position(7, 1), new Knight(ChessPiece.PieceColor.BLACK));
        setPieceViewPosition(new Position(7, 2), new Bishop(ChessPiece.PieceColor.BLACK));
        setPieceViewPosition(new Position(7, 4), new Queen(ChessPiece.PieceColor.BLACK));
        setPieceViewPosition(new Position(7, 3), blackKing);
        setPieceViewPosition(new Position(7, 5), new Bishop(ChessPiece.PieceColor.BLACK));
        setPieceViewPosition(new Position(7, 6), new Knight(ChessPiece.PieceColor.BLACK));
        setPieceViewPosition(new Position(7, 7), new Rook(ChessPiece.PieceColor.BLACK));

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
    }

    private void debugPrintView() {
        for (int y = 0; y < ChessBoard.getSizeY(); y++) {
            for (int x = 0; x < ChessBoard.getSizeX(); x++) {
                if (chessBoardView[y][x].getChildren().size() != 0) {
                    System.out.println(chessBoardView[y][x].getChildren());
                }
            }
        }
    }

    private void clearSelection(StackPane boardSquare) {
        prevSelectedPiece = null;
        setSelectionEffect(boardSquare, false);
    }

    private void changeTurnColor() {
        if (turnColor == ChessPiece.PieceColor.WHITE) {
            turnColor = ChessPiece.PieceColor.BLACK;
        } else {
            turnColor = ChessPiece.PieceColor.WHITE;
        }
    }

    private void setPieceViewPosition(Position pos, ChessPiece piece) {

        chessBoardView[pos.getY()][pos.getX()].getChildren().clear();
        chessBoardView[pos.getY()][pos.getX()].getChildren().add(piece.getView());
    }

    private void moveChessBoardViewPosition(Position pos, ChessPieceView piece) {

        Position prevPos = getChessBoardViewPosition((StackPane) piece.getParent());
        chessBoardView[prevPos.getY()][prevPos.getX()].getChildren().clear();
        chessBoardView[pos.getY()][pos.getX()].getChildren().add(piece);
    }

    private Position getChessBoardViewPosition(StackPane square) {
        return convertPositionViewToModel(square);
    }

    private Position convertPositionViewToModel(Node square) {
        Integer y = GridPane.getRowIndex(square);
        Integer x = GridPane.getColumnIndex(square);

        if (y == null) {
            y = 0;
        }

        if (x == null) {
            x = 0;
        }

        int convertedY = (ChessBoard.getLastYIndex()) - y;

        return new Position(convertedY, x);
    }

    private void updateView() {
        for (int y = 0; y < ChessBoard.getSizeY(); y++) {
            for (int x = 0; x < ChessBoard.getSizeX(); x++) {
                Position pos = new Position(y, x);
                ChessPiece piece = ChessBoard.getPiece(pos);
                if (piece != null) {
                    setPieceViewPosition(pos, piece);
                } else {
                    chessBoardView[pos.getY()][pos.getX()].getChildren().clear();
                }
            }
        }
    }

}
