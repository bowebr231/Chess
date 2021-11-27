package chess.pieces;

import chess.ChessBoard;
import chess.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ChessPieceTest {

    @BeforeEach
    void setUp() {
        //ChessBoard chessBoard = new ChessBoard();
    }

    @Nested
    class ChessPieceTests {
        @Test
        void canMoveInBoundsTest() {
            ChessPiece piece = new ChessPiece();
            for(int y = 0; y < ChessBoard.getSizeY(); y++) {
                for(int x = 0; x < ChessBoard.getSizeX(); x++) {
                    Assertions.assertTrue(piece.canMove(new Position(3,3), new Position(y, x)));
                    // System.out.println("x=" + x + " y=" + y);
                }
            }
        }

        @Test
        void canMoveOutOfBoundsEdgeTest() {
            ChessPiece piece = new ChessPiece();
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
    }

    @Nested
    class KnightTests {
        // HAPPY PATH
        @Test
        void canMoveUpRight1Test() {
            Position start = new Position(3,3);
            Position end = new Position(4,5);
            Knight knight = new Knight();

            Assertions.assertTrue(knight.canMove(start, end));
        }

        @Test
        void canMoveUpRight2Test() {
            Position start = new Position(3,3);
            Position end = new Position(5,4);
            Knight knight = new Knight();

            Assertions.assertTrue(knight.canMove(start, end));

        }

        @Test
        void canMoveDownRight1Test() {
            Position start = new Position(3,3);
            Position end = new Position(4,5);
            Knight knight = new Knight();

            Assertions.assertTrue(knight.canMove(start, end));

        }

        @Test
        void canMoveDownRight2Test() {
            Position start = new Position(3,3);
            Position end = new Position(4,5);
            Knight knight = new Knight();

            Assertions.assertTrue(knight.canMove(start, end));

        }

        @Test
        void canMoveDownLeft1Test() {
            Position start = new Position(3,3);
            Position end = new Position(2,1);
            Knight knight = new Knight();

            Assertions.assertTrue(knight.canMove(start, end));

        }

        @Test
        void canMoveDownLeft2Test() {
            Position start = new Position(3,3);
            Position end = new Position(1,2);
            Knight knight = new Knight();

            Assertions.assertTrue(knight.canMove(start, end));

        }

        @Test
        void canMoveUpLeft1Test() {
            Position start = new Position(3,3);
            Position end = new Position(4,1);
            Knight knight = new Knight();

            Assertions.assertTrue(knight.canMove(start, end));

        }

        @Test
        void canMoveUpLeft2Test() {
            Position start = new Position(3,3);
            Position end = new Position(5,2);
            Knight knight = new Knight();

            Assertions.assertTrue(knight.canMove(start, end));

        }

        // ALTERNATE PATHS
        @Test
        void cannotMove1Test() {
            Position start = new Position(3,3);
            Position end = new Position(3,4);
            Knight knight = new Knight();

            Assertions.assertFalse(knight.canMove(start, end));

        }

        @Test
        void cannotMove2Test() {
            Position start = new Position(3,3);
            Position end = new Position(3,5);
            Knight knight = new Knight();

            Assertions.assertFalse(knight.canMove(start, end));

        }

        @Test
        void cannotMove3Test() {
            Position start = new Position(3,3);
            Position end = new Position(3,6);
            Knight knight = new Knight();

            Assertions.assertFalse(knight.canMove(start, end));

        }

        @Test
        void cannotMoveOutOfBoundsTest() {
            Position start = new Position(3,3);
            Position end = new Position(9,9);
            Knight knight = new Knight();

            Assertions.assertFalse(knight.canMove(start, end));

        }

        @Test
        void cannotMoveOutOfBoundsButValidRulesTest() {
            Position start = new Position(7,7);
            Position end = new Position(9,8);
            Knight knight = new Knight();

            Assertions.assertFalse(knight.canMove(start, end));

        }

    }
}