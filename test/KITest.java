import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KITest {


    /*##############################*/
    /*----- ki method tests ------*/
    /*##############################*/
    @Test
    void materialScoreW(){
        Board board = Board.setupBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w");
        int score = Evaluation.MaterialScore(board, board.getPlayerPieces(true).get(0), true);
        assertEquals(39, score);
    }
    @Test
    void materialScoreB(){
        Board board = Board.setupBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - b");
        int score = Evaluation.MaterialScore(board, board.getPlayerPieces(false).get(0), false);
        assertEquals(39, score);
    }




    @Test
    void minMaxTest(String fen, int depth){
        //setup Board
        Board board = new Board(fen);
        board.fen2Board();
        KI ki = new KI(board, board.wTurn);
        ki.miniMax(ki.board, depth, board.wTurn);
    }
    @Test
    void alphaBetaTest(String fen, int depth){
        System.out.println(fen);
        Board board = new Board(fen);
        board.fen2Board();
        board.printBoardPiecesWPOV();
        KI ki = new KI(board, board.wTurn);
        ki.counter = 0;
        long start = System.currentTimeMillis();
        ki.alphaBetaNM(board, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, depth);
        long end = System.currentTimeMillis();
        System.out.println("Anzahl der untersuchten Zustaende: " + ki.zusteande);
        System.out.println("bei einer Tiefe von: " + depth);
        long laufzeit = end - start;
        System.out.println("Laufzeit: " + laufzeit + "ms");

        System.out.println("Anzahl der Cutoffs: " + ki.cutoff);
        System.out.println("bester move: " + ki.moves);
        System.out.println("score: " + ki.scores);
    }

    void pvsTest(String fen, int depth) {
        System.out.println(fen);
        Board board = new Board(fen);
        board.fen2Board();
        board.printBoardPiecesWPOV();
        KI ki = new KI(board, board.wTurn);
        ki.counter = 0;
        long start = System.currentTimeMillis();
        ki.pvs(board, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, depth);
        long end = System.currentTimeMillis();
        System.out.println("Anzahl der untersuchten Zustaende: " + ki.zusteande);
        System.out.println("bei einer Tiefe von: " + depth);
        long laufzeit = end - start;
        System.out.println("Laufzeit: " + laufzeit + "ms");

        System.out.println("Anzahl der Cutoffs: " + ki.cutoff);
        System.out.println("bester move: " + ki.moves);
        System.out.println("score: " + ki.scores);
    }


    @Test
    void abtt() {
        for (int depth = 1; depth < 10; depth++) {
            alphaBetaTT_Test("r3k1nr/pp3ppp/3p4/3P4/8/3P4/PP3PPP/RN2K2R - w", depth);
        }
    }


    @ParameterizedTest
    @ValueSource(strings = {"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w", "r3k1nr/pp3ppp/3p4/3P4/8/3P4/PP3PPP/RN2K2R - w", "6k1/B5q1/KR6/8/8/8/6p1/8 - w"})
    void mainMiniMaxDepth2(String fen){
        //setup board and ki
        Board board = new Board(fen);
        board.fen2Board();
        KI ki = new KI(board, board.wTurn);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            ki.miniMax(ki.board, 2, board.wTurn);
        }
        long end = System.currentTimeMillis();

        long laufzeit = (end - start);
        System.out.println("Laufzeit: " + laufzeit + "ms");
    }

    @Test
    void mainMiniMaxDepth3(){
        String fenStart = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w";
        String fenMid = "6k1/p1p2pp1/nr1b3n/3P2N1/1pB2R2/4P2b/PP1B1P1P/3K4 w";
        String fenEnd = "8/7p/6p1/5p2/Q4P2/2pr2P1/7P/1K2k3 - w";

        ArrayList<String> fens = new ArrayList<>();
        fens.add(fenStart);
        fens.add(fenMid);
        fens.add(fenEnd);
        for (String fen : fens){
            minMaxTest(fen, 3);
            System.out.println();
        }
    }
    @Test
    void mainMiniMaxDepth4(){
        String fenStart = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w";
        String fenMid = "6k1/p1p2pp1/nr1b3n/3P2N1/1pB2R2/4P2b/PP1B1P1P/3K4 w";
        String fenEnd = "8/7p/6p1/5p2/Q4P2/2pr2P1/7P/1K2k3 - w";

        ArrayList<String> fens = new ArrayList<>();
        fens.add(fenStart);
        fens.add(fenMid);
        fens.add(fenEnd);
        for (String fen : fens){
            minMaxTest(fen, 4);
            System.out.println();
        }
    }
    @Test
    void mainAlphaBetaDepth2(){
        String fenStart = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w";
        String fenMid = "6k1/p1p2pp1/nr1b3n/3P2N1/1pB2R2/4P2b/PP1B1P1P/3K4 w";
        String fenEnd = "8/7p/6p1/5p2/Q4P2/2pr2P1/7P/1K2k3 - w";

        ArrayList<String> fens = new ArrayList<>();
        fens.add(fenStart);
        fens.add(fenMid);
        fens.add(fenEnd);
        for (String fen : fens){
            alphaBetaTest(fen, 2);
            System.out.println();
        }
    }
    @Test
    void mainAlphaBetaDepth3(){
        String fenStart = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w";
        String fenMid = "6k1/p1p2pp1/nr1b3n/3P2N1/1pB2R2/4P2b/PP1B1P1P/3K4 w";
        String fenEnd = "8/7p/6p1/5p2/Q4P2/2pr2P1/7P/1K2k3 - w";

        ArrayList<String> fens = new ArrayList<>();
        fens.add(fenStart);
        fens.add(fenMid);
        fens.add(fenEnd);
        for (String fen : fens){
            alphaBetaTest(fen, 3);
            System.out.println();
        }
    }
    @Test
    void mainAlphaBetaDepth4() {
        String fenStart = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w";
        String fenMid = "6k1/p1p2pp1/nr1b3n/3P2N1/1pB2R2/4P2b/PP1B1P1P/3K4 w";
        String fenEnd = "8/7p/6p1/5p2/Q4P2/2pr2P1/7P/1K2k3 - b";

        ArrayList<String> fens = new ArrayList<>();
        //fens.add(fenStart);
        fens.add(fenMid);
        fens.add(fenEnd);
        for (String fen : fens) {
            alphaBetaTest(fen, 2);
            System.out.println();
        }
    }

    @Test
    void mainPVSDepth4() {
        String fenStart = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w";
        String fenMid = "6k1/p1p2pp1/nr1b3n/3P2N1/1pB2R2/4P2b/PP1B1P1P/3K4 w";
        String fenEnd = "8/7p/6p1/5p2/Q4P2/2pr2P1/7P/1K2k3 - w";

        ArrayList<String> fens = new ArrayList<>();
        fens.add(fenStart);
        fens.add(fenMid);
        fens.add(fenEnd);
        for (String fen : fens) {
            pvsTest(fen, 4);
            System.out.println();
        }
    }

    @Test
    void alphaBetaTT_Test(String fen, int depth) {
        System.out.println(fen);
        Board board = new Board(fen);
        board.fen2Board();
        board.printBoardPiecesWPOV();
        KI ki = new KI(board, board.wTurn);
        ki.counter = 0;
        long start = System.currentTimeMillis();
        ki.alphaBetaNMTT(board, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, depth);
        long end = System.currentTimeMillis();
        System.out.println("Anzahl der untersuchten Zustaende: " + ki.zusteande);
        System.out.println("bei einer Tiefe von: " + depth);
        long laufzeit = end - start;
        System.out.println("Laufzeit: " + laufzeit + "ms");

        System.out.println("Anzahl der Cutoffs: " + ki.cutoff);
        System.out.println("bester move: " + ki.moves);
        System.out.println("score: " + ki.scores);
    }

    void pvsTT_Test(String fen, int depth) {
        System.out.println(fen);
        Board board = new Board(fen);
        board.fen2Board();
        board.printBoardPiecesWPOV();
        KI ki = new KI(board, board.wTurn);
        ki.counter = 0;
        long start = System.currentTimeMillis();
        ki.pvsTT(board, board.wTurn, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, depth);
        long end = System.currentTimeMillis();
        System.out.println("Anzahl der untersuchten Zustaende: " + ki.zusteande);
        System.out.println("bei einer Tiefe von: " + depth);
        long laufzeit = end - start;
        System.out.println("Laufzeit: " + laufzeit + "ms");

        System.out.println("Anzahl der Cutoffs: " + ki.cutoff);
        System.out.println("bester move: " + ki.moves);
        System.out.println("score: " + ki.scores);
    }

    @Test
    void mattIn2White() {
        Board board = Board.setupBoard("rnr3k1/1pq2p1p/p2p1bpB/3P4/P3Q3/2PB4/5PPP/2R1R1K1 - w");
        KI ki = new KI(board, board.wTurn);
        ki.counter = 0;
        for (int i = 0; i < 3; i++) {
            long start = System.currentTimeMillis();
            ki.alphaBetaNM(board, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, 3);
            long end = System.currentTimeMillis();
            System.out.println("Anzahl der untersuchten Zustaende: " + ki.zusteande);
            System.out.println("bei einer Tiefe von: " + (3-i));
            long laufzeit = end - start;
            System.out.println("Laufzeit: " + laufzeit + "ms");

            System.out.println("Anzahl der Cutoffs: " + ki.cutoff);
            System.out.println("bester move: " + ki.moves);
            System.out.println("score: " + ki.scores);

            board.makeMove(ki.moves.get(0));
            board.printBoardPiecesWPOV();
        }
        System.out.println(board.board2fen());
    }
    @Test
    void mattIn1White() {
        Board board = Board.setupBoard("rn2r1k1/1pq2p1p/p2p1bpB/3P4/P7/2PB4/5PPP/2R1R1K1 - w");
        KI ki = new KI(board, board.wTurn);
        ki.counter = 0;
        ki.alphaBetaNM(board, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, 1);

        System.out.println("bester move: " + ki.moves);
        System.out.println("score: " + ki.scores);

        board.makeMove(ki.moves.get(0));
        board.printBoardPiecesWPOV();
        board.changeWTurn();
    }
    @Test
    void mattIn2Black() {
        Board board = Board.setupBoard("4k2r/1q1p1pp1/p3p3/1pb1P3/2r3P1/P1N1P2p/1PP1Q2P/2R1R1K1 - b");
        KI ki = new KI(board, board.wTurn);
        ki.counter = 0;
        for (int i = 0; i < 3; i++) {
            System.out.println();
            long start = System.currentTimeMillis();
            ki.alphaBetaNM(board, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, 3);
            long end = System.currentTimeMillis();
            System.out.println("Anzahl der untersuchten Zustaende: " + ki.zusteande);
            System.out.println("bei einer Tiefe von: " + 3);
            long laufzeit = end - start;
            System.out.println("Laufzeit: " + laufzeit + "ms");

            System.out.println("Anzahl der Cutoffs: " + ki.cutoff);
            System.out.println("bester move: " + ki.moves);
            System.out.println("score: " + ki.scores);

            board.makeMove(ki.moves.get(0));
            board.printBoardPiecesWPOV();
            if (board.isWon()){
                System.out.println("Black Player won by checkmate");
            }
        }
        System.out.println(board.board2fen());
    }
    @Test
    void FindMoveWhite(){ // to find: 12-20
        Board board = Board.setupBoard("4k2r/1q1p1pp1/p3p3/1pb1P3/2r3P1/P1N1P2p/1PP1Q2P/2R1R1K1 - b");
        board.makeMove("34-20");
        System.out.println();
        board.printBoardPiecesWPOV();
        KI ki = new KI(board, board.wTurn);
        ki.counter = 0;
        ki.alphaBetaNM(board, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, 3);
    }
    
}