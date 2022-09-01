import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Objects;

public class TTable {

    static int EXACT = 0;
    static int UPPER_BOUND = 1;
    static int LOWER_BOUND = 2;
    public long[][] zArray = new long[13][64];
    public long[] zCastle = new long[4];
    public long[] zEnPassant = new long[8];
    public HashMap<Long, TT_data> hashtable;

    public TTable() {
        this.hashtable = new HashMap<>();
        zobristFillArray();
    }

    /*
    public static void main(String[] args) {
        TranspositionTable table = new TranspositionTable();
        table.zobristFillArray();
        long obj1= table.getZobristHash(Board.setupStartBoard());
        long obj2= table.getZobristHash(Board.setupStartBoard());
        System.out.println("zobrist 1 : " + obj1);
        System.out.println("zobrist 2 : " + obj2);
        assertEquals(obj1,obj2);
    }
    */


    public TT_data TT_lookup(Board board, int alpha, int beta, int depth) {
        long zKey = getZobristHash(board);
        TT_data board_pos = this.hashtable.get(zKey);

        if (Objects.isNull(board_pos)) return new TT_data(EXACT, Integer.MIN_VALUE + 1, depth);

        if (board_pos.depth >= depth) {
            if (board_pos.nodeType == EXACT) return board_pos;
            //cut node smaller than upper bound
            if ((board_pos.nodeType == UPPER_BOUND) && (board_pos.bestScore <= alpha))
                return new TT_data(UPPER_BOUND, alpha, depth);
            // cut node bigger than lower bound
            if ((board_pos.nodeType == LOWER_BOUND) && (board_pos.bestScore >= beta))
                return new TT_data(LOWER_BOUND, beta, depth);
        }
        return new TT_data(EXACT, Integer.MIN_VALUE + 1, depth);
    }

    public void update(Board board, int score, int type, int depth) {

        long zKey = getZobristHash(board);
        TT_data data = this.hashtable.get(zKey);
        if (data == null) {
            //first time we have this board , save it
            hashtable.put(zKey, new TT_data(type, score, depth));
        } else this.hashtable.get(zKey).updateData(type, score, depth);
    }

    public long random64() {
        SecureRandom random = new SecureRandom();
        return random.nextLong();
    }

    public void zobristFillArray() {
        // 0 empty, 1 WP , 2 WB , 3 WN , 4 WR , 5 WQ , 6 WK
        //          7 Bp , 8 Bb , 9 Bn , 10 Br, 11 Bq, 12 Bk
        for (int ptype = 0; ptype < 13; ptype++) {
            for (int cell = 0; cell < 64; cell++) {
                zArray[ptype][cell] = random64();
            }
        }
        for (int column = 0; column < 8; column++) {
            zEnPassant[column] = random64();
        }
        for (int i = 0; i < 4; i++) {
            zCastle[i] = random64();
        }
    }

    public long getZobristHash(Board board) {
        long hashIndex = 0;
        for (int cellNr = 0; cellNr < 64; cellNr++) {
            if (board.getCells()[cellNr] == 0) {//empty
                hashIndex ^= zArray[0][cellNr];
            } else if (board.getCells()[cellNr] == 14) {//white pawn
                hashIndex ^= zArray[1][cellNr];
            } else if (board.getCells()[cellNr] == 13) {//white bishop
                hashIndex ^= zArray[2][cellNr];
            } else if (board.getCells()[cellNr] == 12) {//white knight
                hashIndex ^= zArray[3][cellNr];
            } else if (board.getCells()[cellNr] == 11) {//white rook
                hashIndex ^= zArray[4][cellNr];
            } else if (board.getCells()[cellNr] == 10) {//white queen
                hashIndex ^= zArray[5][cellNr];
            } else if (board.getCells()[cellNr] == 9) {//white king
                hashIndex ^= zArray[6][cellNr];
            } else if (board.getCells()[cellNr] == 22) {//black pawn
                hashIndex ^= zArray[7][cellNr];
            } else if (board.getCells()[cellNr] == 21) {//black bishop
                hashIndex ^= zArray[8][cellNr];
            } else if (board.getCells()[cellNr] == 20) {//black knight
                hashIndex ^= zArray[9][cellNr];
            } else if (board.getCells()[cellNr] == 19) {//black rook
                hashIndex ^= zArray[10][cellNr];
            } else if (board.getCells()[cellNr] == 18) {//black queen
                hashIndex ^= zArray[11][cellNr];
            } else if (board.getCells()[cellNr] == 17) {//black king
                hashIndex ^= zArray[12][cellNr];
            }
        }
        return hashIndex;
    }

}


