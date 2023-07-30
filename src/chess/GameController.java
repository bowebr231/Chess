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


public class GameController implements Initializable {

    @FXML
    GridPane boardGridPane;

    final StackPane[][] chessBoardView = new StackPane[ChessBoard.getSizeY()][ChessBoard.getSizeX()];

    private ChessPieceView prevSelectedPieceView = null;
    private StackPane prevSelectedSquare = null;
    private String prevSelectedSquareColor = null;

    private ChessPiece.Color turnColor = ChessPiece.Color.WHITE;

    private CheckmateStateMachine whiteCheckmateStateMachine;
    private CheckmateStateMachine blackCheckmateStateMachine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupChessModelAndView();
    }

    /**
     * Event Handler:
     * 1. If square is clicked with a piece on it, and it's that color's turn then piece is 'selected'.
     * 2. If another square is clicked then check if move is valid. If valid then move the piece there.
     * Deselect after this 2nd click regardless if valid or not.
     * 3. If a valid move is performed then turn changes to next color player.
     * <p>
     * Note: need way for View and Model to stay in sync and interact.
     *
     * @return
     */
    private EventHandler<MouseEvent> initGameBehaviorHandler() {
        return event -> {
            // Get and Check Square
            StackPane boardSquare = (StackPane) event.getSource();
            boolean squareIsEmpty = boardSquare.getChildren().size() == 0;

            // Get Game Piece
            ChessPieceView selectedPieceView = null;
            if (!squareIsEmpty) {
                selectedPieceView = ((ChessPieceView)boardSquare.getChildren().get(0));
            }

            // Try to move a piece
            if (prevSelectedPieceView != null) {
                ChessPiece prevSelectedPiece = prevSelectedPieceView.getPiece();
                Position start = ChessBoard.findPiecePosition(prevSelectedPiece);
                Position end = getChessBoardViewPosition(boardSquare);
                CheckmateStateMachine playerState = getPlayerStateMachineByTurn();

                if (prevSelectedPiece.canMove(start, end)
                        && !playerState.isMoveCheck(prevSelectedPiece, end))
                {
                    moveChessPiece(start, end);
                }
                clearSelection(boardSquare);
            } else {
                // Select piece if correct turn color
                if (!squareIsEmpty && selectedPieceView.getPiece().getColor() == turnColor) {
                    prevSelectedPieceView = selectedPieceView;
                    enableSelectionEffect(boardSquare, true);
                }
            }
        };
    }

    private void moveChessPiece(Position start, Position end) {
        prevSelectedPieceView.getPiece().moveToPosition(start, end);
        moveChessBoardViewPosition(ChessBoard.findPiecePosition(prevSelectedPieceView.getPiece()), prevSelectedPieceView);
        changeTurnColor();
        updateView();

        CheckmateStateMachine playerState = getPlayerStateMachineByTurn();

        CheckmateStateMachine.State state = playerState.getUpdatedState();
        if ( state == CheckmateStateMachine.State.CHECK) {
            System.out.println(turnColor + " player is in CHECK!");
        } else if (state == CheckmateStateMachine.State.CHECKMATE) {
            System.out.println(turnColor + "player is in CHECKMATE! Game Over");
        }
    }

    private CheckmateStateMachine getPlayerStateMachineByTurn() {
        return (turnColor == ChessPiece.Color.WHITE) ? whiteCheckmateStateMachine : blackCheckmateStateMachine;
    }

    private void enableSelectionEffect(StackPane boardSquare, boolean selectTarget) {
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

        // Create view interface by connecting to fxml GridPane squares
        for (Node square : boardGridPane.getChildren())
        {
            Position pos = convertPositionViewToModel(square);
            chessBoardView[pos.getY()][pos.getX()] = (StackPane) square;
        }

        // Add user 'click' game interface to View
        for (int y = 0; y < ChessBoard.getSizeY(); y++) {
            for (int x = 0; x < ChessBoard.getSizeX(); x++) {
                chessBoardView[y][x].addEventHandler(MouseEvent.MOUSE_CLICKED, initGameBehaviorHandler());
            }
        }

        // White and Black Pawns
        for (int x = 0; x < ChessBoard.getSizeY(); x++) {
            setPieceViewPosition(new Position(1, x), new Pawn(ChessPiece.Color.WHITE));
            setPieceViewPosition(new Position(6, x), new Pawn(ChessPiece.Color.BLACK));
        }


        // Create and position White Pieces
        King whiteKing = new King(ChessPiece.Color.WHITE);
        setPieceViewPosition(new Position(0, 0), new Rook(ChessPiece.Color.WHITE));
        setPieceViewPosition(new Position(0, 1), new Knight(ChessPiece.Color.WHITE));
        setPieceViewPosition(new Position(0, 2), new Bishop(ChessPiece.Color.WHITE));
        setPieceViewPosition(new Position(0, 3), whiteKing);
        setPieceViewPosition(new Position(0, 4), new Queen(ChessPiece.Color.WHITE));
        setPieceViewPosition(new Position(0, 5), new Bishop(ChessPiece.Color.WHITE));
        setPieceViewPosition(new Position(0, 6), new Knight(ChessPiece.Color.WHITE));
        setPieceViewPosition(new Position(0, 7), new Rook(ChessPiece.Color.WHITE));

        // Create and position Black Pieces
        King blackKing = new King(ChessPiece.Color.BLACK);
        setPieceViewPosition(new Position(7, 0), new Rook(ChessPiece.Color.BLACK));
        setPieceViewPosition(new Position(7, 1), new Knight(ChessPiece.Color.BLACK));
        setPieceViewPosition(new Position(7, 2), new Bishop(ChessPiece.Color.BLACK));
        setPieceViewPosition(new Position(7, 4), new Queen(ChessPiece.Color.BLACK));
        setPieceViewPosition(new Position(7, 3), blackKing);
        setPieceViewPosition(new Position(7, 5), new Bishop(ChessPiece.Color.BLACK));
        setPieceViewPosition(new Position(7, 6), new Knight(ChessPiece.Color.BLACK));
        setPieceViewPosition(new Position(7, 7), new Rook(ChessPiece.Color.BLACK));

        // MODEL

        whiteCheckmateStateMachine = new CheckmateStateMachine(whiteKing);
        blackCheckmateStateMachine = new CheckmateStateMachine(blackKing);

        // Init board model
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
        prevSelectedPieceView = null;
        enableSelectionEffect(boardSquare, false);
    }

    private void changeTurnColor() {
        turnColor = (turnColor == ChessPiece.Color.WHITE) ? ChessPiece.Color.BLACK : ChessPiece.Color.WHITE;
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
