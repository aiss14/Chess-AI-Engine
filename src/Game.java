import java.util.List;

public class Game {

    public static void main(String[] args) {

        startGame(20);
    }

    public static void startGame(int rounds) {
        String fenStart = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w";
        Board chessboard = new Board(fenStart);
        chessboard.fen2Board();
        chessboard.wTurn = true;
        KI wplayer = new KI(chessboard, true);
        KI bplayer = new KI(chessboard, false);
        System.out.println("Gamestart: White turn");
        chessboard.printBoard();
        int round_nb = 0;
        while (round_nb < rounds) {
            List<String> possiblemoves;
            String nextmove;
            if (chessboard.wTurn) {
                possiblemoves = Move.possibleMovesW(chessboard);
                nextmove = wplayer.getNextMove(chessboard, 3000);
                System.out.println("White Move n" + ((round_nb / 2) + 1) + ": " + nextmove + "\n");
            } else {
                possiblemoves = Move.possibleMovesB(chessboard);
                nextmove = bplayer.getNextMove(chessboard, 3000);
                System.out.println("Black Move n" + ((round_nb / 2) + 1) + ": " + nextmove + "\n");

            }
            if (chessboard.isCheckmate()) break;
            System.out.println("All possible moves (" + possiblemoves.size() + "): " + possiblemoves);
            chessboard.lastmove = nextmove;
            chessboard.makeMove(nextmove);
            chessboard.printBoard();
            round_nb++;
        }
        System.out.println("\n GameOver");
    }
}