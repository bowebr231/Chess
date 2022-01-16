package chess;

import chess.pieces.ChessPiece;
import chess.pieces.King;
import chess.pieces.Knight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerStateMachine {

    private PlayerState state = PlayerState.NOT_CHECK;
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

        List<Position> threatPositionList = getThreatPositions(kingPos);

        if (threatPositionList.size() > 0) {
            state = PlayerState.CHECK;
            if (!canEscape(kingPos, threatPositionList)) {
                state = PlayerState.CHECKMATE;
            }
        } else {
            state = PlayerState.NOT_CHECK;
        }

        return state;
    }

    private List<Position> getThreatPositions(final Position kingPosition) {
        List<Position> threatPositions = new ArrayList<>();

        // Scan all enemy pieces
        for (int y = 0; y < ChessBoard.getSizeY(); y++) {
            for (int x = 0; x < ChessBoard.getSizeX(); x++) {

                Position enemyPosition = new Position(y, x);
                ChessPiece enemy = ChessBoard.getPiece(enemyPosition);

                // If an enemy piece can reach the king, then 'check'
                if (enemy != null
                        && enemy.getColor() != king.getColor()
                        && enemy.canMove(enemyPosition, kingPosition)) {

                    threatPositions.add(enemyPosition);
                }
            }
        }
        return threatPositions;
    }

    private boolean canEscape(final Position kingPosition, final List<Position> threatPositionList) {
        // Can King move?

        // Can all threats be killed in one move?

        // Can friendly piece block all attack vectors?

        return canKingEscape(kingPosition)
                || canThreatsBeDestroyed(kingPosition, threatPositionList)
                || canFriendBlockThreats(kingPosition, threatPositionList);

    }

    /**
     * Test all moves the King can make to see if an escape exists.
     *
     * @param kingPosition
     * @return
     */
    private boolean canKingEscape(Position kingPosition) {
        // Basic King moves
        for (Position diff : KING_MOVE_DIFFS) {
            if (getThreatPositions(kingPosition.add(diff)).size() == 0) {
                return true;
            }
        }

        // TODO special king moves
        // [insert code]

        return false;
    }

    // NOTE: canThreatsBeDestroyed() and canFriendBlockThreats() maybe could have overlaps.
    // canThreatsBeDestroyed() maybe could block an enemy piece after killing an enemy?
    // No, if you kill an enemy piece you just take the enemy's place. And knight's on the other
    // hand would just jump their friendly pieces anyway.
    // However, if the king kills a piece it maybe could survive, but this would be handled in the first condition:
    // canKingEscape().

    private boolean canThreatsBeDestroyed(Position kingPosition, final List<Position> threatPositionList) {

        // Assumes if more than one threat exists then nothing can be done. Could be a false assumption!!!
        if (threatPositionList.size() < 2) {
            // Scan all friendly pieces
            for (int y = 0; y < ChessBoard.getSizeY(); y++) {
                for (int x = 0; x < ChessBoard.getSizeX(); x++) {

                    Position friendlyPosition = new Position(y, x);
                    ChessPiece friendly = ChessBoard.getPiece(friendlyPosition);

                    // If a friendly piece can destroy the 1 threat, then 'NOT_CHECK'
                    if (friendly != null
                            && friendly.getColor() == king.getColor()
                            && friendly.canMove(friendlyPosition, threatPositionList.get(0))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canFriendBlockThreats(Position kingPosition, final List<Position> threatPositionList) {
        ChessPiece enemy = ChessBoard.getPiece(threatPositionList.get(0));
        // Assumes if more than one threat exists then nothing can be done. Could be a false assumption!!!
        if (threatPositionList.size() < 2 && !(enemy instanceof Knight)) {

            List<Position> threatAttackPath = ChessBoard.getBoardLinePositions(threatPositionList.get(0), kingPosition);
            // Scan all friendly pieces
            for (int y = 0; y < ChessBoard.getSizeY(); y++) {
                for (int x = 0; x < ChessBoard.getSizeX(); x++) {

                    Position friendlyPosition = new Position(y, x);
                    ChessPiece friendly = ChessBoard.getPiece(friendlyPosition);

                    // If a friendly piece can block the attack path of the 1 threat, then 'NOT_CHECK'
                    if (friendly != null) {
                        for (Position blockingPosition : threatAttackPath) {
                            if (friendly.canMove(friendlyPosition, blockingPosition)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    // A challenge is destroying and blocking could happen in one move.
}
