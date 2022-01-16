package chess;

import chess.pieces.Bishop;
import chess.pieces.ChessPiece;
import chess.pieces.Queen;
import chess.pieces.Rook;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {

    @BeforeEach
    void setUp() {ChessBoard.clearBoard();}

    @AfterEach
    void tearDown() {
    }

    @Nested
    class isOutOfBoundsTests {
        @Test
        void inBoundsTest() {
            for(int y = 0; y < ChessBoard.getSizeY(); y++) {
                for(int x = 0; x < ChessBoard.getSizeX(); x++) {
                    Assertions.assertFalse(ChessBoard.isOutOfBounds(new Position(y, x)));
                    // System.out.println("x=" + x + " y=" + y);
                }
            }
        }

        @Test
        void outOfBoundsEdgeTest() {
            int edgeY;
            int edgeX;

            edgeY = -1;
            for(int x = 0; x < ChessBoard.getSizeX(); x++) {
                Assertions.assertTrue(ChessBoard.isOutOfBounds(new Position(edgeY, x)));
            }

            edgeY = 8;
            for(int x = 0; x < ChessBoard.getSizeX(); x++) {
                Assertions.assertTrue(ChessBoard.isOutOfBounds(new Position(edgeY, x)));
            }

            edgeX = -1;
            for(int y = 0; y < ChessBoard.getSizeX(); y++) {
                Assertions.assertTrue(ChessBoard.isOutOfBounds(new Position(y, edgeX)));
            }

            edgeX = 8;
            for(int y = 0; y < ChessBoard.getSizeX(); y++) {
                Assertions.assertTrue(ChessBoard.isOutOfBounds(new Position(y, edgeX)));
            }
        }
    } // end ChessPiece Tests

    @Test
    void getSizeY() {
        assertEquals(ChessBoard.getSizeY(), 8);
    }

    @Test
    void getSizeX() {
        assertEquals(ChessBoard.getSizeX(), 8);
    }

    @Test
    void findPiecePosition() {
    }

    @Test
    void isEnemyPiecePresent() {
    }

    @Nested
    class isPieceBlockingPathTests {

        @Test
        void isPieceBlockingPathFriendly() {
            Rook rook = new Rook(ChessPiece.PieceColor.BLACK);
            ChessBoard.putPieceHere(new Position(0, 0), rook);
            ChessBoard.putPieceHere(new Position(3, 0), new Rook(ChessPiece.PieceColor.BLACK));

            Assertions.assertFalse(rook.canMove(new Position(0,0), new Position(7, 0)));

            Assertions.assertTrue(rook.canMove(new Position(0,0), new Position(0, 7)));
        }

        @Test
        void isPieceBlockingPathEnemy() {
            Rook rook = new Rook(ChessPiece.PieceColor.BLACK);
            ChessBoard.putPieceHere(new Position(0, 0), rook);
            ChessBoard.putPieceHere(new Position(3, 0), new Rook(ChessPiece.PieceColor.WHITE));

            Assertions.assertFalse(rook.canMove(new Position(0,0), new Position(7, 0)));
        }

        @Test
        void isPieceBlockingPathFriendlyTarget() {
            Rook rook = new Rook(ChessPiece.PieceColor.BLACK);
            ChessBoard.putPieceHere(new Position(0, 0), rook);
            ChessBoard.putPieceHere(new Position(3, 0), new Rook(ChessPiece.PieceColor.BLACK));

            Assertions.assertFalse(rook.canMove(new Position(0,0), new Position(3, 0)));
        }

        @Test
        void isPieceBlockingPathEnemyTarget() {
            Rook rook = new Rook(ChessPiece.PieceColor.BLACK);
            ChessBoard.putPieceHere(new Position(0, 0), rook);
            ChessBoard.putPieceHere(new Position(3, 0), new Rook(ChessPiece.PieceColor.WHITE));

            Assertions.assertTrue(rook.canMove(new Position(0,0), new Position(3, 0)));
        }
    }

    @Nested
    class GetLineTests {
        @Test
        void getUpwardVerticalLine() {
            ArrayList<ChessPiece> expectedLine = new ArrayList<>();

            Position start = new Position(0, 3);
            Position end = new Position(7, 3);
            Position rookPos = new Position(1, 3);
            Position queenPos = new Position(3, 3);

            // Skips start position
            expectedLine.add(new Rook(ChessPiece.PieceColor.WHITE));
            expectedLine.add(null);
            expectedLine.add(new Queen(ChessPiece.PieceColor.BLACK));
            expectedLine.add(null);
            expectedLine.add(null);
            expectedLine.add(null);
            expectedLine.add(null);

            assertTrue(ChessBoard.putPieceHere(rookPos, expectedLine.get(0)));
            assertTrue(ChessBoard.putPieceHere(queenPos, expectedLine.get(2)));

            List<ChessPiece> actualLine = ChessBoard.scanBoardLineForPieces(start, end);
            System.out.println("Expected length="+expectedLine.size());
            System.out.println("Actual length="+actualLine.size());

            for (int i = 0; i < 7; i++) {
                System.out.println("Count="+i);
                assertSame(actualLine.get(i), expectedLine.get(i));
            }
        }

        @Test
        void getDownwardVerticalLine() {
            ArrayList<ChessPiece> expectedLine = new ArrayList<>();

            Position start = new Position(7, 3);
            Position end = new Position(0, 3);

            Queen queen = new Queen(ChessPiece.PieceColor.BLACK);
            Rook rook = new Rook(ChessPiece.PieceColor.WHITE);
            Position rookPos = new Position(1, 3);
            Position queenPos = new Position(3, 3);

            // Skips start position
            expectedLine.add(null);
            expectedLine.add(null);
            expectedLine.add(null);
            expectedLine.add(queen);
            expectedLine.add(null);
            expectedLine.add(rook);
            expectedLine.add(null);

            assertTrue(ChessBoard.putPieceHere(rookPos, rook));
            assertTrue(ChessBoard.putPieceHere(queenPos, queen));

            List<ChessPiece> actualLine = ChessBoard.scanBoardLineForPieces(start, end);
            System.out.println("Expected length="+expectedLine.size());
            System.out.println("Actual length="+actualLine.size());

            for (int i = 0; i < 7; i++) {
                System.out.println("Count="+i);
                assertSame(actualLine.get(i), expectedLine.get(i));
            }
        }

        @Test
        void getLeftHorizontalLine() {
            ArrayList<ChessPiece> expectedLine = new ArrayList<>();

            Position start = new Position(3, 7);
            Position end = new Position(3, 0);

            Queen queen = new Queen(ChessPiece.PieceColor.BLACK);
            Rook rook = new Rook(ChessPiece.PieceColor.WHITE);
            Position rookPos = new Position(3, 1);
            Position queenPos = new Position(3, 3);

            // Skips start position
            expectedLine.add(null);     // 1
            expectedLine.add(null);     // 2
            expectedLine.add(null);     // 3
            expectedLine.add(queen);    // 4
            expectedLine.add(null);     // 5
            expectedLine.add(rook);     // 6
            expectedLine.add(null);     // 7

            assertTrue(ChessBoard.putPieceHere(rookPos, rook));
            assertTrue(ChessBoard.putPieceHere(queenPos, queen));

            List<ChessPiece> actualLine = ChessBoard.scanBoardLineForPieces(start, end);
            System.out.println("Expected length="+expectedLine.size());
            System.out.println("Actual length="+actualLine.size());

            for (int i = 0; i < 7; i++) {
                System.out.println("Count="+i);
                assertSame(actualLine.get(i), expectedLine.get(i));
            }
        }

        @Test
        void getUpRightDiagonalLine() {
            ArrayList<ChessPiece> expectedLine = new ArrayList<>();

            Position start = new Position(0, 0);
            Position end = new Position(7, 7);

            Queen queen = new Queen(ChessPiece.PieceColor.BLACK);
            Rook rook = new Rook(ChessPiece.PieceColor.WHITE);
            Position rookPos = new Position(4, 4);
            Position queenPos = new Position(6, 6);


            // Skips start position
            expectedLine.add(null);     // 1
            expectedLine.add(null);     // 2
            expectedLine.add(null);     // 3
            expectedLine.add(rook);     // 4
            expectedLine.add(null);     // 5
            expectedLine.add(queen);    // 6
            expectedLine.add(null);     // 7

            assertTrue(ChessBoard.putPieceHere(rookPos, rook));
            assertTrue(ChessBoard.putPieceHere(queenPos, queen));

            List<ChessPiece> actualLine = ChessBoard.scanBoardLineForPieces(start, end);
            System.out.println("Expected length="+expectedLine.size());
            System.out.println("Actual length="+actualLine.size());

            for (int i = 0; i < 7; i++) {
                System.out.println("Count="+i);
                assertSame(actualLine.get(i), expectedLine.get(i));
            }
        }

        @Test
        void getDownLeftDiagonalLine() {
            ArrayList<ChessPiece> expectedLine = new ArrayList<>();

            Position start = new Position(7, 7);
            Position end = new Position(0, 0);

            Queen queen = new Queen(ChessPiece.PieceColor.BLACK);
            Rook rook = new Rook(ChessPiece.PieceColor.WHITE);
            Position rookPos = new Position(4, 4);
            Position queenPos = new Position(6, 6);


            // Skips start position
            expectedLine.add(queen);     // 1
            expectedLine.add(null);     // 2
            expectedLine.add(rook);     // 3
            expectedLine.add(null);     // 4
            expectedLine.add(null);     // 5
            expectedLine.add(null);    // 6
            expectedLine.add(null);     // 7

            assertTrue(ChessBoard.putPieceHere(rookPos, rook));
            assertTrue(ChessBoard.putPieceHere(queenPos, queen));

            List<ChessPiece> actualLine = ChessBoard.scanBoardLineForPieces(start, end);
            System.out.println("Expected length="+expectedLine.size());
            System.out.println("Actual length="+actualLine.size());

            for (int i = 0; i < 7; i++) {
                System.out.println("Count="+i);
                assertSame(actualLine.get(i), expectedLine.get(i));
            }
        }

    }

    @Test
    void getPiece() {
        Rook piece = new Rook(ChessPiece.PieceColor.WHITE);
        ChessBoard.putPieceHere(new Position(3, 3), piece);

        Assertions.assertEquals(piece, ChessBoard.getPiece(new Position(3, 3)));

        ChessBoard.clearBoard();
        for(int y = 0; y < ChessBoard.getSizeY(); y++) {
            for(int x = 0; x < ChessBoard.getSizeX(); x++) {
                Assertions.assertNull(ChessBoard.getPiece(new Position(y, x)));
            }
        }

    }

    @Test
    void putPieceHere() {
        Rook rook = new Rook(ChessPiece.PieceColor.BLACK);
        Bishop bishop = new Bishop(ChessPiece.PieceColor.BLACK);

        Position pos = new Position(7, 7);
        assertTrue(ChessBoard.putPieceHere(pos, rook));
        assertFalse(ChessBoard.putPieceHere(pos, bishop));
        assertSame(ChessBoard.getPiece(pos), rook);

        Position newPos = new Position(5, 5);
        assertTrue(ChessBoard.putPieceHere(newPos, rook));
        assertNull(ChessBoard.getPiece(pos));
        assertSame(ChessBoard.getPiece(newPos), rook);
    }

    @Test
    void clearBoard() {
    }
}