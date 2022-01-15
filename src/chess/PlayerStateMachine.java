package chess;

import chess.ChessBoard;
import chess.Position;
import chess.pieces.ChessPiece;
import chess.pieces.King;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerStateMachine {

    private PlayerState state = PlayerState.NOT_CHECK;
    private final ChessPiece.PieceColor stateMachineColor;
    private final King king;

    /**
     * Storage used to copy the state of the original chessboard during testing for checkmate with the actual board.
     * Each test will end with reverting the board back to original state since the board is static and would have to
     * be redesigned in order to make copies.
     *
     */
    private static final ChessPiece[][] chessBoardBackup =
            new ChessPiece[ChessBoard.getSizeY()][ChessBoard.getSizeX()];

    public static final Position UP = new Position(1, 0);
    public static final Position DOWN = new Position(-1, 0);
    public static final Position LEFT = new Position(0, -1);
    public static final Position RIGHT = new Position(0, 1);
    public static final Position UP_LEFT = new Position(1, -1);
    public static final Position UP_RIGHT = new Position(1, 1);
    public static final Position DOWN_LEFT = new Position(-1, -1);
    public static final Position DOWN_RIGHT = new Position(-1, 1);
    public static final List<Position> KING_MOVE_DIFFS = Collections.unmodifiableList(
            Arrays.asList(
            UP,
            DOWN,
            LEFT,
            RIGHT,
            UP_LEFT,
            UP_RIGHT,
            DOWN_RIGHT,
            DOWN_LEFT));

    public PlayerStateMachine(King king) {
        this.king = king;
        this.stateMachineColor = king.color;
    }

    enum PlayerState {
        NOT_CHECK,
        CHECK,
        CHECKMATE
    }

    private void saveBoardCopy() {
        // Create 8x8 empty board for now.
        for (int y = 0; y < ChessBoard.getSizeY(); y++) {
            for (int x = 0; x < ChessBoard.getSizeX(); x++) {
                chessBoardBackup[y][x] = ChessBoard.getPiece(new Position(y, x));
            }
        }
    }

    private void restoreBoardCopy() {
        // Create 8x8 empty board for now.
        for (int y = 0; y < ChessBoard.getSizeY(); y++) {
            for (int x = 0; x < ChessBoard.getSizeX(); x++) {
                 ChessBoard.putPieceHere(new Position(y, x), chessBoardBackup[y][x]);
            }
        }
    }

    /*
    Problem To Solve:

    In order to easily know that a move will get the king out of check I figured I need
    more than just 1 static board instance in order to more easily calculate if a move will move the king out of check.

    This might required refactoring/redesigning how my ChessBoard class is constructed. I need to be able to copy it,
    and I need to be able to be able to operate on it without operating on the original instance of it.
     */

    /*
    Note: I think I originally design this updateState() method to call
    isCheck(), and isCheckMate(). But, then I started to redesign the method names
    by the many operations needed to calculate these states since check and checkmate are so closely bound, I think.
     */
    public PlayerState updateState() {
        Position kingPos = ChessBoard.findPiecePosition(king);

        int numberOfThreats = numberOfThreats(kingPos);

        if (numberOfThreats > 0) {
            state = PlayerState.CHECK;
            if (!canEscape(kingPos, numberOfThreats)) {
                state = PlayerState.CHECKMATE;
            }
        } else {
            state = PlayerState.NOT_CHECK;
        }

        return state;
    }



    private int getThreatVectors(Position kingPosition) {
        int numberOfThreats = 0;

        // Scan all enemy pieces
        for (int y = 0; y < ChessBoard.getSizeY(); y++) {
            for (int x = 0; x < ChessBoard.getSizeX(); x++) {

                Position enemyPosition = new Position(y, x);
                ChessPiece piece = ChessBoard.getPiece(enemyPosition);

                // If an enemy piece can reach the king, then 'check'
                if (piece != null
                        && piece.getColor() != stateMachineColor
                        && piece.canMove(enemyPosition, kingPosition)) {

                    numberOfThreats++;
                }
            }
        }
        return numberOfThreats;
    }

    private boolean canEscape(Position kingPosition, final int numberOfThreats) {
        // Can King move?

        // Can all threats be killed in one move?

        // Can friendly piece block all attack vectors?

        return canKingEscape(kingPosition)
                || canThreatsBeDestroyed(kingPosition, numberOfThreats)
                || canFriendBlockThreats(kingPosition);

    }

    private boolean canKingEscape(Position kingPosition) {
        for (Position diff : KING_MOVE_DIFFS) {
            if (numberOfThreats(kingPosition.add(diff)) == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean canThreatsBeDestroyed(Position kingPosition, final int numberOfThreats) {
        boolean result = false;

        if (numberOfThreats < 2) {
            // Scan all friendly pieces
            for (int y = 0; y < ChessBoard.getSizeY(); y++) {
                for (int x = 0; x < ChessBoard.getSizeX(); x++) {

                    Position friendlyPosition = new Position(y, x);
                    ChessPiece piece = ChessBoard.getPiece(friendlyPosition);

                    // If a friendly piece can destroy all threats, then 'not check'
                    if (piece != null
                            && piece.color != stateMachineColor
                            && piece.canMove(friendlyPosition, kingPosition)) {

                        result = true;
                        return result;
                    }
                }
            }
        }
        return result;
    }

    private boolean canFriendBlockThreats(Position kingPosition) {

    }

    // A challenge is destroying and blocking could happen in one move.
}
