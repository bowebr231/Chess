package chess;

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

    @Test
    void getLine() {
        ArrayList<ChessPiece> expectedLine = new ArrayList<>();

        Position rookPos = new Position(1, 3);
        Position queenPos = new Position(3, 3);
        expectedLine.add(new Rook(ChessPiece.PieceColor.WHITE));
        expectedLine.add(new Queen(ChessPiece.PieceColor.BLACK));
        ChessBoard.putPieceHere(queenPos, expectedLine.get(1));
        ChessBoard.putPieceHere(rookPos, expectedLine.get(0));

        Position start = new Position(0, 3);
        Position end = new Position(7, 3);
        List<ChessPiece> actualLine = ChessBoard.getLine(start, end);

        assertSame(actualLine.get(1).getColor(), ChessPiece.PieceColor.WHITE);
        assertSame(actualLine.get(3).getColor(), ChessPiece.PieceColor.BLACK);

        assertNull(actualLine.get(0));
        assertNull(actualLine.get(2));
        assertNull(actualLine.get(4));
        assertNull(actualLine.get(5));
        assertNull(actualLine.get(6));
        assertNull(actualLine.get(7));


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
    }

    @Test
    void clearBoard() {
    }
}