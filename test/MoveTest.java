import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @ParameterizedTest
    @ValueSource(strings = {"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w", "r3k1nr/pp3ppp/3p4/3P4/8/3P4/PP3PPP/RN2K2R - w", "6k1/B5q1/KR6/8/8/8/6p1/8 - w"})
    void moveGenPerformanceTest(String fen){
        Board board = Board.setupBoard(fen);
        List<String> pMoves = Move.possibleMovesW(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(20, pMoves.size());
    }

    @Test
    void moveGenerationStartW(){
        Board board = Board.setupBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w");
        List<String> pMoves = Move.possibleMovesW(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(20, pMoves.size());
    }
    @Test
    void moveGenerationStartB(){
        Board board = Board.setupBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - b");
        List<String> pMoves = new ArrayList<>();
        if (board.wTurn){
            pMoves = Move.possibleMovesW(board);
        } else{
            pMoves = Move.possibleMovesB(board);
        }
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(20, pMoves.size());
    }
    @Test
    void moveGeneration1000StartW(){
        //Board board = Board.setupBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w");
        Board board = Board.setupBoard("r3k1nr/pp3ppp/3p4/3P4/8/3P4/PP3PPP/RN2K2R - w");
        List<String> pMoves = new ArrayList<>();
        long start = System.currentTimeMillis();
        if (board.wTurn){
            for (int i = 0; i < 1000; i++){
                pMoves = Move.possibleMovesW(board);
            }
        } else{
            for (int i = 0; i < 1000; i++){
                //pMoves = Move.NEWpossibleMovesB(board);
            }
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        System.out.println("time: " + time);
        assertEquals(21, pMoves.size());
    }

    @Test
    void NEWmoveGenerationStartW(){
        Board board = Board.setupBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w");
        List<String> pMoves = new ArrayList<>();
        if (board.wTurn){
            pMoves = Move.possibleMovesW(board);
        } else{
            pMoves = Move.possibleMovesB(board);
        }
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(20, pMoves.size());
    }
    @Test
    void NEWmoveGeneration1000StartW(){
        //Board board = Board.setupBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w");
        Board board = Board.setupBoard("r3k1nr/pp3ppp/3p4/3P4/8/3P4/PP3PPP/RN2K2R - w");
        List<String> pMoves = new ArrayList<>();
        long start = System.currentTimeMillis();
        if (board.wTurn){
            for (int i = 0; i < 1000; i++){
                pMoves = Move.possibleMovesW(board);
            }
        } else{
            for (int i = 0; i < 1000; i++){
                //pMoves = Move.NEWpossibleMovesB(board);
            }
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        System.out.println("time: " + time);
        assertEquals(21, pMoves.size());
    }
    @Test
    void moveGenerationStartW1(){ // Stellung Dutch Defense (Gruppe AI)
        Board board = Board.setupBoard("rnbqkbnr/ppppp1pp/8/5p2/3P4/8/PPP1PPPP/RNBQKBNR w");
        List<String> pMoves = Move.possibleMovesW(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(28, pMoves.size());
    }
    @Test
    void moveGenerationStartW2(){ // Stellung nicht erlaubter En passant (Gruppe B)
        Board board = Board.setupBoard("rnbqkbnr/pp1p1ppp/4p3/1Pp5/8/2N5/P1PPPPPP/R1BQKBNR w");
        List<String> pMoves = Move.possibleMovesW(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(22, pMoves.size());
    }
    @Test
    void moveGenerationStartW3(){ // Stellung Rochade (Gruppe H)
        Board board = Board.setupBoard("1rbqk2r/3nBppp/p2p4/3Qp3/Pp2P3/6PB/1PP1NP1P/R3K2R w");
        List<String> pMoves = Move.possibleMovesW(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(53, pMoves.size());
    }
    @Test
    void moveGenerationStartW4(){ // Weißer König steht im Schach (Gruppe E)
        Board board = Board.setupBoard("rnb1kbnr/pp1ppppp/2p5/q7/8/3P1P2/PPP1P1PP/RNBQKBNR w");
        List<String> pMoves = Move.possibleMovesW(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(7, pMoves.size());
    }
    @Test
    void moveGenerationStartW5(){ // Stelllung Rochade und Pin (Gruppe F)
        Board board = Board.setupBoard("r2kq3/3r4/8/8/7b/p7/P3BN2/R3K2R w");
        List<String> pMoves = Move.possibleMovesW(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(10, pMoves.size());
    }
    @Test
    void moveGenerationStartW6(){ // Stellung Kolision (Gruppe F)
        Board board = Board.setupBoard("r7/P1P5/p7/P4k2/n7/P1P2K2/1BP2PBN/1QR2R1n w");
        List<String> pMoves = Move.possibleMovesW(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(16, pMoves.size());
    }
    @Test
    void moveGenerationStartW7(){ // Stellung weiß en passant pseudo-legal, aber nicht legal (Gruppe G)
        Board board = Board.setupBoard("rnb1kbnr/ppp2ppp/8/K2pP2q/5p2/3P4/PPP1B1PP/RNBQ2NR w");
        List<String> pMoves = Move.possibleMovesW(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(29, pMoves.size());
    }
    @Test
    void moveGenerationStartW8(){ // Stellung En Passant (Gruppe H)
        Board board = Board.setupBoard("r1bq1r2/pp2n3/4N2k/3pPppP/1b1n2Q1/2N5/PP3PP1/R1B1K2R w");
        board.lastmove = "54-38-22-0";
        List<String> pMoves = Move.possibleMovesW(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(40, pMoves.size());
        assertTrue(pMoves.contains("39-46"));
    }
    @Test
    void moveGenerationStartW9(){ // Stellung Endgame 1 (Gruppe R)
        Board board = Board.setupBoard("6k1/B5q1/KR6/8/8/8/6p1/8 - w");
        List<String> pMoves = Move.possibleMovesW(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(16, pMoves.size());
    }
    @Test
    void moveGenerationStartW9Sorted(){ // Stellung Endgame 1 (Gruppe R)
        Board board = Board.setupBoard("6k1/B5q1/KR6/8/8/8/6p1/8 - w");
        List<String> pMoves = Move.possibleMovesW(board);
        List<String> pMovesSorted = Move.moveSort(pMoves, board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        System.out.println("Generated " + pMovesSorted.size() + " moves: " + pMovesSorted);
        assertEquals(16, pMoves.size());
    }

    @Test
    void moveGenerationStartW10(){ // Stellung Rochade (Gruppe AF)
        Board board = Board.setupBoard("r3k2r/ppp1npbp/b3p1p1/8/8/4P3/PPP2PPP/R3K2R w");
        List<String> pMoves = Move.possibleMovesW(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(21, pMoves.size());
    }
    @Test
    void moveGenerationStartW11(){ // Stellungen I (Gruppe O)
        Board board = Board.setupBoard("r1b3k1/ppb2ppp/8/2B1p1P1/1P2N2P/P3P3/2P2P2/3rK2R w");
        List<String> pMoves = Move.possibleMovesW(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(2, pMoves.size());
    }
    @Test
    void moveGenerationStartW11Sorted(){ // Stellung Endgame 1 (Gruppe R)
        Board board = Board.setupBoard("r1b3k1/ppb2ppp/8/2B1p1P1/1P2N2P/P3P3/2P2P2/3rK2R w");
        List<String> pMoves = Move.possibleMovesW(board);
        List<String> pMovesSorted = Move.moveSort(pMoves, board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        System.out.println("Generated " + pMovesSorted.size() + " moves: " + pMovesSorted);
        assertEquals(2, pMoves.size());
    }

    @Test
    void moveGenerationStartW12(){ // Stellungen II (Gruppe O)
        Board board = Board.setupBoard("8/8/6k1/5n2/5P2/7p/2KN4/8 w");
        List<String> pMoves = Move.possibleMovesW(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(13, pMoves.size());
    }


    @Test
    void moveGenerationStartB1(){ // Stellung Giuoco Piano (Gruppe AI)
        Board board = Board.setupBoard("rnbqkbnr/1pppppp1/p6p/8/2B1P3/5N2/PPPP1PPP/RNBQK2R b");
        List<String> pMoves = Move.possibleMovesB(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(18, pMoves.size());
    }
    @Test
    void moveGenerationStartB2(){ // schwarz am Zug, Zwei schwarze Damen und Bauern (Gruppe E)
        Board board = Board.setupBoard("7k/7q/5pqp/6p1/NP3P1P/1KP3P1/2B5/8 b");
        List<String> pMoves = Move.possibleMovesB(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(24, pMoves.size());
    }
    @Test
    void moveGenerationStartB2Sorted(){ // Stellung Endgame 1 (Gruppe R)
        Board board = Board.setupBoard("7k/7q/5pqp/6p1/NP3P1P/1KP3P1/2B5/8 b");
        List<String> pMoves = Move.possibleMovesB(board);
        List<String> pMovesSorted = Move.moveSort(pMoves, board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        System.out.println("Generated " + pMovesSorted.size() + " moves: " + pMovesSorted);
        assertEquals(24, pMoves.size());
    }

    @Test
    void moveGenerationStartB3(){ // Stellung schwarz Rochade (Gruppe G)
        Board board = Board.setupBoard("B3k2r/p1pqbppp/n2p3n/4p3/6b1/1PPPP1P1/P4P1P/RNBQK1NR b");
        List<String> pMoves = Move.possibleMovesB(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
        assertEquals(37, pMoves.size());
    }
    // TODO Bauernumwandlung implementieren
    @Test
    void moveGenerationStartB4(){ // Stellung Endgame Reverse - mit Bauernumwandlung (Gruppe R)
        Board board = Board.setupBoard("6k1/B5q1/KR6/8/8/8/6p1/8 b");
        List<String> pMoves = Move.possibleMovesB(board);
        System.out.println("Generated " + pMoves.size() + " moves: " + pMoves);
    }
    @Test
    void moveGenInCheck(){
        Board board = Board.setupBoard("4k2r/1q1p1pp1/p3p3/1pb1P3/2r3P1/P1N1P2p/1PP1Q2P/2R1R1K1 - b");
        board.makeMove("34-20");
        System.out.println();
        board.printBoardPiecesWPOV();
        System.out.println(Move.possibleMovesW(board));

    }

}