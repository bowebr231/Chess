package chess.pieces;

import chess.ChessBoard;
import chess.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Not testing Queen or King movements yet since they are basically tested by the other pieces.
 */
class ChessPieceTest {

    @BeforeEach
    void setUp() {
        ChessBoard.clearBoard();
    }

    @Nested
    class BishopTests {
        // HAPPY PATH
        @Test
        void canMoveUpLeftTest() {
            Position start = new Position(0,7);
            Position end = new Position(7,0);
            Bishop bishop = new Bishop(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(bishop.canMove(start, end));
        }

        @Test
        void canMoveUpRightTest() {
            Position start = new Position(0,0);
            Position end = new Position(7,7);
            Bishop bishop = new Bishop(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(bishop.canMove(start, end));
        }

        @Test
        void canMoveDownLeftTest() {
            Position start = new Position(7,7);
            Position end = new Position(0,0);
            Bishop bishop = new Bishop(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(bishop.canMove(start, end));
        }

        @Test
        void canMoveDownRightTest() {
            Position start = new Position(7,0);
            Position end = new Position(0,7);
            Bishop bishop = new Bishop(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(bishop.canMove(start, end));
        }



        // ALTERNATE PATHS
        @Test
        void cannotMoveUpTest() {
            Position start = new Position(0,0);
            Position end = new Position(7,0);
            Bishop bishop = new Bishop(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(bishop.canMove(start, end));

        }

        @Test
        void cannotMoveDownTest() {
            Position start = new Position(7,0);
            Position end = new Position(0,0);
            Bishop bishop = new Bishop(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(bishop.canMove(start, end));

        }

        @Test
        void cannotMoveLeftTest() {
            Position start = new Position(0,7);
            Position end = new Position(0,0);
            Bishop bishop = new Bishop(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(bishop.canMove(start, end));

        }

        @Test
        void cannotMoveRightTest() {
            Position start = new Position(0,0);
            Position end = new Position(0,7);
            Bishop bishop = new Bishop(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(bishop.canMove(start, end));
        }

        @Test
        void cannotMoveDiagonalJaggedLineTest() {
            Position start = new Position(0,0);
            Position end = new Position(3,4);
            Bishop bishop = new Bishop(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(bishop.canMove(start, end));
        }

    } // end Bishop Tests

    @Nested
    class ChessPieceTests {
        @Test
        void canMoveInBoundsTest() {
            ChessPiece piece = new ChessPiece(ChessPiece.PieceColor.BLACK);
            for(int y = 0; y < ChessBoard.getSizeY(); y++) {
                for(int x = 0; x < ChessBoard.getSizeX(); x++) {
                    Assertions.assertTrue(piece.canMove(new Position(3,3), new Position(y, x)));
                    // System.out.println("x=" + x + " y=" + y);
                }
            }
        }

        @Test
        void canMoveOutOfBoundsEdgeTest() {
            ChessPiece piece = new ChessPiece(ChessPiece.PieceColor.BLACK);
            int edgeY;
            int edgeX;

            edgeY = -1;
            for(int x = 0; x < ChessBoard.getSizeX(); x++) {
                Assertions.assertFalse(piece.canMove(new Position(3,3), new Position(edgeY, x)));
            }

            edgeY = 8;
            for(int x = 0; x < ChessBoard.getSizeX(); x++) {
                Assertions.assertFalse(piece.canMove(new Position(3,3), new Position(edgeY, x)));
            }

            edgeX = -1;
            for(int y = 0; y < ChessBoard.getSizeX(); y++) {
                Assertions.assertFalse(piece.canMove(new Position(3,3), new Position(y, edgeX)));
            }

            edgeX = 8;
            for(int y = 0; y < ChessBoard.getSizeX(); y++) {
                Assertions.assertFalse(piece.canMove(new Position(3,3), new Position(y, edgeX)));
            }
        }
    } // end ChessPiece Tests

    @Nested
    class KnightTests {
        // HAPPY PATH
        @Test
        void canMoveUpRight1Test() {
            Position start = new Position(3,3);
            Position end = new Position(4,5);
            Knight knight = new Knight(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(knight.canMove(start, end));
        }

        @Test
        void canMoveUpRight2Test() {
            Position start = new Position(3,3);
            Position end = new Position(5,4);
            Knight knight = new Knight(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(knight.canMove(start, end));

        }

        @Test
        void canMoveDownRight1Test() {
            Position start = new Position(3,3);
            Position end = new Position(4,5);
            Knight knight = new Knight(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(knight.canMove(start, end));

        }

        @Test
        void canMoveDownRight2Test() {
            Position start = new Position(3,3);
            Position end = new Position(4,5);
            Knight knight = new Knight(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(knight.canMove(start, end));

        }

        @Test
        void canMoveDownLeft1Test() {
            Position start = new Position(3,3);
            Position end = new Position(2,1);
            Knight knight = new Knight(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(knight.canMove(start, end));

        }

        @Test
        void canMoveDownLeft2Test() {
            Position start = new Position(3,3);
            Position end = new Position(1,2);
            Knight knight = new Knight(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(knight.canMove(start, end));

        }

        @Test
        void canMoveUpLeft1Test() {
            Position start = new Position(3,3);
            Position end = new Position(4,1);
            Knight knight = new Knight(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(knight.canMove(start, end));

        }

        @Test
        void canMoveUpLeft2Test() {
            Position start = new Position(3,3);
            Position end = new Position(5,2);
            Knight knight = new Knight(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(knight.canMove(start, end));

        }

        // ALTERNATE PATHS
        @Test
        void cannotMove1Test() {
            Position start = new Position(3,3);
            Position end = new Position(3,4);
            Knight knight = new Knight(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(knight.canMove(start, end));

        }

        @Test
        void cannotMove2Test() {
            Position start = new Position(3,3);
            Position end = new Position(3,5);
            Knight knight = new Knight(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(knight.canMove(start, end));

        }

        @Test
        void cannotMove3Test() {
            Position start = new Position(3,3);
            Position end = new Position(3,6);
            Knight knight = new Knight(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(knight.canMove(start, end));

        }

        @Test
        void cannotMoveOutOfBoundsTest() {
            Position start = new Position(3,3);
            Position end = new Position(9,9);
            Knight knight = new Knight(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(knight.canMove(start, end));

        }

        @Test
        void cannotMoveOutOfBoundsButValidRulesTest() {
            Position start = new Position(7,7);
            Position end = new Position(9,8);
            Knight knight = new Knight(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(knight.canMove(start, end));

        }

    } // end Knight Tests

    @Nested
    class PawnTests {
        // HAPPY PATH
        @Test
        void canMoveUpTest1() {
            Position start = new Position(0,0);
            Position end = new Position(1,0);
            Pawn pawn = new Pawn(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(pawn.canMove(start, end));
        }

        @Test
        void canMoveUpTest2() {
            Position start = new Position(7,0);
            Position end = new Position(6,0);
            Pawn pawn = new Pawn(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(pawn.canMove(start, end));
        }

        @Test
        void canMoveUpTwiceTest1() {
            Position start = new Position(0,0);
            Position end = new Position(2,0);
            Pawn pawn = new Pawn(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(pawn.canMove(start, end));
        }

        @Test
        void canMoveUpTwiceTest2() {
            Position start = new Position(7,0);
            Position end = new Position(5,0);
            Pawn pawn = new Pawn(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(pawn.canMove(start, end));
        }

        @Test
        void canAttackDiagonalLeftTest() {
            Pawn enemy = new Pawn(ChessPiece.PieceColor.WHITE);
            ChessBoard.putPieceHere(new Position(4,4), enemy);

            Position start = new Position(3,5);
            Position end = new Position(4,4);
            Pawn pawn = new Pawn(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(pawn.canMove(start, end));
        }

        @Test
        void canAttackDiagonalRightTest() {
            Pawn enemy = new Pawn(ChessPiece.PieceColor.WHITE);
            ChessBoard.putPieceHere(new Position(4,4), enemy);

            Position start = new Position(3,3);
            Position end = new Position(4,4);
            Pawn pawn = new Pawn(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(pawn.canMove(start, end));
        }

        // ALTERNATE PATHS
        @Test
        void cannotAttackFriendlyTest() {
            Pawn friendly = new Pawn(ChessPiece.PieceColor.BLACK);
            ChessBoard.putPieceHere(new Position(4,4), friendly);

            Position start = new Position(3,3);
            Position end = new Position(4,4);
            Pawn pawn = new Pawn(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(pawn.canMove(start, end));
        }

        @Test
        void cannotMoveTwiceTwiceTest() {
            Position start = new Position(0,0);
            Position end = new Position(2,0);
            Position endEnd = new Position(4,0);
            Pawn pawn = new Pawn(ChessPiece.PieceColor.BLACK);

            pawn.moveToPosition(start, end);
            Assertions.assertFalse(pawn.canMove(end, endEnd));
        }

        @Test
        void cannotMoveDiagonalTest() {
            Position start = new Position(0,0);
            Position end = new Position(1,1);
            Pawn pawn = new Pawn(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(pawn.canMove(start, end));
        }

        @Test
        void cannotMoveWrongDirectionTest1() {
            Position start = new Position(3,0);
            Position end = new Position(2,0);
            Pawn pawn = new Pawn(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(pawn.canMove(start, end));
        }

        @Test
        void cannotMoveWrongDirectionTest2() {
            Position start = new Position(6,0);
            Position end = new Position(7,0);
            Pawn pawn = new Pawn(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(pawn.canMove(start, end));
        }

        @Test
        void cannotMoveAdjacentTest1() {
            Position start = new Position(0,0);
            Position end = new Position(0,1);
            Pawn pawn = new Pawn(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(pawn.canMove(start, end));
        }

        @Test
        void cannotMoveAdjacentTest2() {
            Position start = new Position(0,1);
            Position end = new Position(0,0);
            Pawn pawn = new Pawn(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(pawn.canMove(start, end));
        }


    } // end Pawn Tests

    @Nested
    class RookTests {
        // HAPPY PATH
        @Test
        void canMoveUpTest() {
            Position start = new Position(0,0);
            Position end = new Position(7,0);
            Rook rook = new Rook(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(rook.canMove(start, end));
        }

        @Test
        void canMoveDownTest() {
            Position start = new Position(7,0);
            Position end = new Position(0,0);
            Rook rook = new Rook(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(rook.canMove(start, end));
        }

        @Test
        void canMoveLeftTest() {
            Position start = new Position(0,7);
            Position end = new Position(0,0);
            Rook rook = new Rook(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(rook.canMove(start, end));
        }

        @Test
        void canMoveRightTest() {
            Position start = new Position(0,0);
            Position end = new Position(0,7);
            Rook rook = new Rook(ChessPiece.PieceColor.BLACK);

            Assertions.assertTrue(rook.canMove(start, end));
        }



        // ALTERNATE PATHS
        @Test
        void cannotMoveDiagonalTest() {
            Position start = new Position(0,7);
            Position end = new Position(7,0);
            Rook rook = new Rook(ChessPiece.PieceColor.BLACK);

            Assertions.assertFalse(rook.canMove(start, end));

        }

    } // end Rook Tests

} // end ChessPieceTest file