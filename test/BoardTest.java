import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    /*########################################*/
    /*----- board setup functions tests ------*/
    /*########################################*/
    @Test
    void fen2BoardTest(){
    }
    @Test
    void fen2Board_Board2fen_whitePlayer(){
        String fenIn = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w";
        var board = new Board(fenIn);
        board.fen2Board();
        String fenOut = board.board2fen();
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w", fenOut);
    }
    @ParameterizedTest
    @ValueSource(strings = {"rn2r1k1/1pq2p1p/p2p1bpB/3P4/P7/2PB4/5PPP/2R1R1K1 - w","rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R - b", "rn2k2r/ppp1pp1p/8/3N4/1q1nP1bb/8/PRQB1P1P/4KBNR - b", "rnbqkbnr/pp2pppp/3pb3/2p5/2B1P3/5N2/PPPP1PPP/RNBQK2R - b", "b2r3r/k3qp1p/pn3np1/Nppp4/4PQ2/P1N2PPB/1PP4P/1K1RR3 - b", "6k1/B5q1/KR6/8/8/8/6p1/8 - b"})
    void fen2Board_Board2fen_BlackPlayer(String fenIn){
        var board = new Board(fenIn);
        board.fen2Board();
        String fenOut = board.board2fen();
        System.out.println(fenOut);
        assertEquals(fenIn, fenOut);
    }
    @Test
    void copyBoard(){
        var board = Board.setupBoard("r3k2r/8/8/8/8/8/8/R3K2R - w");
        var copy = new Board(board);

        assertNotEquals(board, copy);
        assertArrayEquals(board.getCells(), copy.getCells());
        assertEquals(board.getwTurn(), copy.getwTurn());
        assertEquals(board.getLastMove(), copy.getLastMove());
        assertIterableEquals(board.getLastMoves(), copy.getLastMoves());
        board.setLastmove("test");
        assertNotEquals(board.getLastMove(), copy.getLastMove());
        System.out.println(Arrays.toString(board.getCells()));
        System.out.println(Arrays.toString(copy.getCells()));
        System.out.println(board.getLastMoves());
    }
    @Test
    void cellNr2field_a1(){
        var board = Board.setupStartBoard();
        String a1 = board.cell2field(0);
        assertEquals("a1", a1);
    }
    @Test
    void cellNr2field_e4(){
        var board = Board.setupStartBoard();
        String e4 = board.cell2field(28);
        assertEquals("e4", e4);
    }
    @Test
    void cellNr2field_h8(){
        var board = Board.setupStartBoard();
        String h8 = board.cell2field(63);
        assertEquals("h8", h8);
    }
    @Test
    void cellNr2field_a6(){
        var board = Board.setupStartBoard();
        String a6 = board.cell2field(40);
        assertEquals("a6", a6);
    }
    @Test
    void field2cellNR_a6(){
        var board = Board.setupStartBoard();
        int a6 = board.field2cell("a6");
        assertEquals(40, a6);
    }
    @Test
    void field2cellNr_f6(){
        var board = Board.setupStartBoard();
        int f6 = board.field2cell("f6");
        assertEquals(45 , f6);
    }
    @Test
    void field2cellNR_e3(){
        var board = Board.setupStartBoard();
        int e3 = board.field2cell("e3");
        assertEquals(20 , e3);
    }
    @Test
    void field2cellNr_a1(){
        var board = Board.setupStartBoard();
        int a1 = board.field2cell("a1");
        assertEquals(0, a1);
    }
    @Test
    void field2cellNr_h8(){
        var board = Board.setupStartBoard();
        int h8 = board.field2cell("h8");
        assertEquals(63, h8);
    }

    @Test
    void moveConvertIn(){
        var board = Board.setupStartBoard();
        String move = "e2-e4";
        String moveConverted = board.moveConvertIn(move);
        assertEquals("12-28", moveConverted);
    }
    @Test
    void moveConvertIn2(){
        var board = Board.setupStartBoard();
        String move = "g3-f5";
        String moveConverted = board.moveConvertIn(move);
        assertEquals("22-37", moveConverted);
    }
    @Test
    void moveConvertIn3(){
        var board = Board.setupStartBoard();
        String move = "a1-h8";
        String moveConverted = board.moveConvertIn(move);
        assertEquals("0-63", moveConverted);
    }

    /*###########################################*/
    /*----- basic game logic function tests -----*/
    /*###########################################*/
    @Test
    void makeMoveKnight(){
        var board = Board.setupStartBoard();
        String move = "1-18";
        board.makeMove(move);

        int[] cellsExpected = new int[]{11, 0, 13, 10, 9, 13, 12, 11, 14, 14, 14, 14, 14, 14, 14, 14, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 22, 22, 22, 22, 22, 22, 22, 22, 19, 20, 21, 18, 17, 21, 20, 19};
        assertEquals(board.getCellContent(1), 0);
        assertEquals(board.getCellContent(18), 12);
        assertArrayEquals(board.getCells(), cellsExpected);
    }
    @Test
    void makeMoveShortRochadeWhite(){
        String fenRochade ="r3k2r/8/8/8/8/8/8/R3K2R - w";
        var board = Board.setupBoard(fenRochade);
        int[] cellsExpected = new int[]{11, 0, 0, 0, 0, 11, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19, 0, 0, 0, 17, 0, 0, 19};
        String move = "0-0-w";
        board.makeMove(move);
        System.out.println();
        board.printBoardPiecesWPOV();
        assertArrayEquals(cellsExpected, board.getCells());
    }
    @Test
    void makeMoveLongRochadeWhite(){
        String fenRochade ="r3k2r/8/8/8/8/8/8/R3K2R - w";
        var board = Board.setupBoard(fenRochade);
        int[] cellsExpected = new int[]{0, 0, 9, 11, 0, 0, 0, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19, 0, 0, 0, 17, 0, 0, 19};
        String move = "0-0-0-w";
        board.makeMove(move);
        System.out.println();
        board.printBoardPiecesWPOV();
        assertArrayEquals(cellsExpected, board.getCells());
    }
    @Test
    void unmakeMoveShortRochadeWhite(){
        String fenRochade ="r3k2r/8/8/8/8/8/8/R3K2R - w";
        var board = Board.setupBoard(fenRochade);
        int[] cellsExpected = new int[]{11, 0, 0, 0, 9, 0, 0, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19, 0, 0, 0, 17, 0, 0, 19};
        String move = "0-0-w";
        board.makeMove(move);
        System.out.println();
        board.unmakeLastMove();
        board.printBoardPiecesWPOV();
        assertArrayEquals(cellsExpected, board.getCells());
    }
    @Test
    void unmakeMoveLongRochadeWhite(){
        String fenRochade ="r3k2r/8/8/8/8/8/8/R3K2R - w";
        var board = Board.setupBoard(fenRochade);
        int[] cellsExpected = new int[]{11, 0, 0, 0, 9, 0, 0, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19, 0, 0, 0, 17, 0, 0, 19};
        String move = "0-0-0-w";
        board.makeMove(move);
        board.unmakeLastMove();
        System.out.println();
        board.printBoardPiecesWPOV();
        assertArrayEquals(cellsExpected, board.getCells());
    }
    @Test
    void makeMoveShortRochadeBlack(){
        String fenRochade ="r3k2r/8/8/8/8/8/8/R3K2R - b";
        var board = Board.setupBoard(fenRochade);
        int[] cellsExpected = new int[]{11, 0, 0, 0, 9, 0, 0, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19, 0, 0, 0, 0, 19, 17, 0};
        String move = "0-0-b";
        board.makeMove(move);
        System.out.println();
        board.printBoardPiecesWPOV();
        assertArrayEquals(cellsExpected, board.getCells());
    }
    @Test
    void makeMoveLongRochadeBlack(){
        String fenRochade ="r3k2r/8/8/8/8/8/8/R3K2R - w";
        var board = Board.setupBoard(fenRochade);
        int[] cellsExpected = new int[]{11, 0, 0, 0, 9, 0, 0, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17, 19, 0, 0, 0, 19};
        String move = "0-0-0-b";
        board.makeMove(move);
        System.out.println();
        board.printBoardPiecesWPOV();
        assertArrayEquals(cellsExpected, board.getCells());
    }
    @Test
    void unmakeMoveShortRochadeBlack(){
        String fenRochade ="r3k2r/8/8/8/8/8/8/R3K2R - w";
        var board = Board.setupBoard(fenRochade);
        int[] cellsExpected = new int[]{11, 0, 0, 0, 9, 0, 0, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19, 0, 0, 0, 17, 0, 0, 19};
        String move = "0-0-b";
        board.makeMove(move);
        System.out.println();
        board.unmakeLastMove();
        board.printBoardPiecesWPOV();
        assertArrayEquals(cellsExpected, board.getCells());
    }
    @Test
    void unmakeMoveLongRochadeBlack(){
        String fenRochade ="r3k2r/8/8/8/8/8/8/R3K2R - w";
        var board = Board.setupBoard(fenRochade);
        int[] cellsExpected = new int[]{11, 0, 0, 0, 9, 0, 0, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19, 0, 0, 0, 17, 0, 0, 19};
        String move = "0-0-0-b";
        board.makeMove(move);
        board.unmakeLastMove();
        System.out.println();
        board.printBoardPiecesWPOV();
        assertArrayEquals(cellsExpected, board.getCells());
    }
    @Test
    void unmakeMove(){
        var board = Board.setupBoard("6k1/R5q1/KB6/8/8/8/6p1/8 - w");
        int[] expectedCells = board.getCells();
        String move = "40-33";
        board.makeMove(move);
        System.out.println();
        board.printBoardPiecesWPOV();
        System.out.println();
        board.unmakeLastMove();
        board.printBoardPiecesWPOV();
        assertEquals(expectedCells, board.getCells());
    }
    @Test
    void unmakeMove2(){
        var board = Board.setupBoard("6k1/R5q1/KB6/8/8/8/6p1/8 - w");
        int[] expectedCells = board.getCells();
        String move = "41-6";
        board.makeMove(move);
        System.out.println();
        board.printBoardPiecesWPOV();
        System.out.println();
        board.unmakeLastMove();
        board.printBoardPiecesWPOV();
        assertEquals(expectedCells, board.getCells());
    }

    @Test
    void makeAndUnmakeAllPMovesWhite(){
        var board = Board.setupStartBoard();
        int[] expectedCells = board.getCells();
        List<String> moves = Move.possibleMovesW(board);
        System.out.println(moves);
        for (String mv : moves){
            System.out.println("playing move: " + mv);
            board.makeMove(mv);
            board.unmakeLastMove();
        }
        System.out.println();
        board.printBoardPiecesWPOV();
        assertEquals(expectedCells, board.getCells());
    }
    @ParameterizedTest
    @ValueSource(strings = {"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - b", "r3k1nr/pp3ppp/3p4/3P4/8/3P4/PP3PPP/RN2K2R - b", "6k1/B5q1/KR6/8/8/8/6p1/8 - b"})
    void makeAndUnmakeAllPMovesBlack(String fen){
        var board = Board.setupBoard(fen);
        int[] expectedCells = board.getCells();
        List<String> moves = Move.possibleMovesB(board);
        System.out.println(moves);
        for (String mv : moves){
            System.out.println("playing move: " + mv);
            board.makeMove(mv);
            board.unmakeLastMove();
        }
        System.out.println();
        board.printBoardPiecesWPOV();
        assertEquals(expectedCells, board.getCells());
    }
    @Test
    void unmakeMove_capture(){
        var board = Board.setupBoard("6k1/R5q1/KB6/8/8/8/6p1/8 - w");
        int[] expectedCells = board.getCells();
        String move = "48-54";
        board.makeMove(move);
        System.out.println();
        board.printBoardPiecesWPOV();
        System.out.println();
        board.unmakeLastMove();
        board.printBoardPiecesWPOV();

        assertEquals(expectedCells, board.getCells());
    }

    @Test
    void lastMoves_Test(){
        var board = Board.setupStartBoard();
        System.out.println();
        int[] expectedCells = board.getCells();
        String move_w_e4 = "12-28-14-0";
        String move_b_e5 = "52-36-22-0";
        String move_b_f5 = "53-37-22-0";
        board.makeMove(move_w_e4);
        board.printBoardPiecesWPOV();
        System.out.println(board.getLastMoves());
        board.makeMove(move_b_e5);
        board.printBoardPiecesWPOV();
        System.out.println(board.getLastMoves());
        board.unmakeLastMove(); // unmake b_e5
        board.printBoardPiecesWPOV();
        System.out.println(board.getLastMoves());
        board.makeMove(move_b_f5);
        board.printBoardPiecesWPOV();
        System.out.println(board.getLastMoves());
        board.unmakeLastMove(); // unmake b_f5
        board.unmakeLastMove(); // unmake w_e4
        board.printBoardPiecesWPOV();
        assertArrayEquals(expectedCells, board.getCells());
    }
    @Test
    void isCheckmateBlack(){
        Board board = Board.setupBoard("rn2r1k1/1pq2p1p/p2p1bpB/3P4/P7/2PB4/5PPP/2R1R1K1 - w");
        board.makeMove("4-60");
        System.out.println(board.getIsWhite());
        board.printBoardPiecesWPOV();
        System.out.println("Black is checkmate: " + board.isCheckmate());
        assertTrue(board.isCheckmate());
    }
    @Test
    void isCheckBlack(){
        Board board = Board.setupBoard("rn2r1k1/1pq2p1p/p2p1bpB/3P4/P7/2PB4/5PPP/2R1R1K1 - w");
        board.makeMove("4-60");
        System.out.println("Whites turn: " + board.getIsWhite());
        board.printBoardPiecesWPOV();
        System.out.println("Black is checkmate: " + board.NEWisCheck(false));
        assertTrue(board.NEWisCheck(false));
    }
    @Test
    void isCheckmateWhite(){
        Board board = Board.setupBoard("rn2r1k1/1pq2p1p/p2p1bpB/3P4/P7/2P5/5PPP/4R1K1 - b");
        board.makeMove("60-4");
        System.out.println("Whites turn: " + board.getIsWhite());
        board.printBoardPiecesWPOV();
        System.out.println("White is checkmate: " + board.isCheckmate());
        assertTrue(board.isCheckmate());
    }
    @Test
    void isCheckWhite(){
        Board board = Board.setupBoard("rn2r1k1/1pq2p1p/p2p1bpB/3P4/P7/2P5/5PPP/4R1K1 - b");
        board.makeMove("60-4");
        System.out.println(board.getIsWhite());
        board.printBoardPiecesWPOV();
        System.out.println("White is check: " + board.NEWisCheck(true));
        assertTrue(board.NEWisCheck(true));
    }

}
