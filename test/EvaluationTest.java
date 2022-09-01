import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvaluationTest {

    @Test
    void evalEqualTest() {

        Board boardW = Board.setupBoard("r3k1nr/pp3ppp/3p4/3P4/8/3P4/PP3PPP/RN2K2R - w");
        List<String> pWmoves = Move.possibleMovesW(boardW);
        System.out.println();
        Board boardB = Board.setupBoard("r3k1nr/pp3ppp/3p4/3P4/8/3P4/PP3PPP/RN2K2R - b");
        List<String> pBmoves = Move.possibleMovesB(boardB);
        int evalW = Evaluation.evaluationFunc(boardW);
        int evalB = Evaluation.evaluationFunc(boardB);
        assertEquals(evalW, evalB);

    }

    /*##############################*/
    /*----- performance tests ------*/
    /*##############################*/
    @ParameterizedTest
    @ValueSource(strings = {"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w", "r3k1nr/pp3ppp/3p4/3P4/8/3P4/PP3PPP/RN2K2R - w", "6k1/B5q1/KR6/8/8/8/6p1/8 - w"})
    void evaluationTest1000White(String fen) {
        // setup board
        Board board = new Board(fen);
        board.fen2Board();
        System.out.println(fen);
        board.printBoardPiecesWPOV();

        // posible moves generation
        List<String> mv;
        mv = Move.possibleMovesW(board);
        System.out.println("possible moves (" + mv.size() + "): " + mv);
        System.out.println();

        // best move
        int max = -10000;
        int evaluation;
        String bestMove = "";

        long t_start1 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            // evaluating each move
            for (String move : mv) {
                board.makeMove(move);
                evaluation = Evaluation.evaluationFunc(board);
                if (i == 0) {
                    System.out.println("Evaluation for move (" + move + ") is: " + evaluation);
                }
                if (evaluation > max) {
                    max = evaluation;
                    bestMove = move;
                }
                board.unmakeLastMove();
            }
        }
        long t_end1 = System.nanoTime();
        long resStart = ((t_end1 - t_start1) / 1000) / 1000 / 1000;
        System.out.println();
        System.out.println("Evaluated Board 1000 times in: " + resStart + "ms");
        System.out.println("Best move found: " + bestMove);
        System.out.println("__________________________________");
    }

    @ParameterizedTest
    @ValueSource(strings = {"4k2r/1q1p1pp1/p3p3/1pb1P3/2r3P1/P1N1P2p/1PP1Q2P/2R1R1K1 - b", "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - b", "rnbqkbnr/pp2pppp/3pb3/2p5/2B1P3/5N2/PPPP1PPP/RNBQK2R - b", "b2r3r/k3qp1p/pn3np1/Nppp4/4PQ2/P1N2PPB/1PP4P/1K1RR3 b ", "6k1/B5q1/KR6/8/8/8/6p1/8 - b"})
    void evaluationTest1000Black(String fen) {
        // setup board
        Board board = new Board(fen);
        board.fen2Board();
        System.out.println(fen);
        board.printBoardPiecesWPOV();

        // posible moves generation
        List<String> mv;
        mv = Move.possibleMovesB(board);
        System.out.println("possible moves (" + mv.size() + "): " + mv);
        System.out.println();

        // best move
        int max = -10000;
        int evaluation;
        String bestMove = "";

        long t_start1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            // evaluating each move
            for (String move : mv) {
                board.makeMove(move);
                evaluation = Evaluation.evaluationFunc(board);
                if (i == 0) {
                    System.out.println("Evaluation for move (" + move + ") is: " + evaluation);
                }
                if (evaluation > max) {
                    max = evaluation;
                    bestMove = move;
                }
                board.unmakeLastMove();
            }
        }
        long t_end1 = System.currentTimeMillis();
        long resStart = ((t_end1 - t_start1) / 1000);
        System.out.println();
        System.out.println("Evaluated Board 1000 times in per " + resStart + "ms per board");
        System.out.println("Best move found: " + bestMove);
        System.out.println("__________________________________");
    }

    @ParameterizedTest
    @ValueSource(strings = {"6k1/p1p2pp1/nr1b3n/3P2N1/1pB2R2/4P2b/PP1B1P1P/3K4 w","rnr3k1/1pq2p1p/p2p1bpB/3P4/P3Q3/2PB4/5PPP/2R1R1K1 - w", "rnbqk1nr/ppp2ppp/8/3pp3/1b1PP3/5N2/PPP2PPP/RNBQKB1R -w", "rnr3k1/1pq2p1p/3p1bpB/3P4/8/2P5/5PPP/2R1R1K1 - w", "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w", "r3k1nr/pp3ppp/3p4/3P4/8/3P4/PP3PPP/RN2K2R - w", "6k1/B5q1/KR6/8/8/8/6p1/8 - w"})
    void evaluationTest(String fen) {
        // setup board
        Board board = new Board(fen);
        board.fen2Board();
        System.out.println(fen);
        board.printBoardPiecesWPOV();

        // posible moves generation
        List<String> mv;
        mv = Move.possibleMovesW(board);
        System.out.println("possible moves (" + mv.size() + "): " + mv);
        System.out.println();

        // best move
        int max = -10000;
        int evaluation = 0;
        String bestMove = "";

        long t_start1 = System.nanoTime();
        // evaluating each move
        for (String move : mv) {
            board.makeMove(move);
            evaluation = Evaluation.evaluationFunc(board);
            System.out.println("Evaluation for move (" + move + ") is: " + evaluation);
            if (evaluation > max) {
                max = evaluation;
                bestMove = move;
            }
            board.unmakeLastMove();
        }
        long t_end1 = System.nanoTime();
        long resStart = ((t_end1 - t_start1) / 1000) / 1000;
        System.out.println();
        System.out.println("Evaluated Board 1000 times in: " + "0," + resStart + "ms");
        System.out.println("Best move found: " + bestMove);
        System.out.println("__________________________________");
    }

    @ParameterizedTest
    @ValueSource(strings = {"rn2r1k1/1pq2p1p/3p1bpB/3P4/8/2P5/5PPP/2R1R1K1 - w", "rn2R1k1/1pq2p1p/3p1bpB/3P4/8/2P5/5PPP/2R3K1 - b", "1k4rr/8/8/8/8/8/8/7K - w", "3b1q1q/1N2PRQ1/rR3KBr/B4PP1/2Pk1r1b/1P2P1N1/2P2P2/8 - b"})
    void mainEvaluation(String fen) {
        Board board = Board.setupBoard(fen);
        List<String> pmoves;
        if (board.getwTurn()) pmoves = Move.possibleMovesW(board);
        else pmoves = Move.possibleMovesB(board);
        int eval = Evaluation.evaluationFunc(board);
        System.out.println(eval);
        System.out.println();
    }
}