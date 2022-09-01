import java.util.ArrayList;
import java.util.List;

public class Evaluation {

    public static void main(String[] args) {
        Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w");
        board.fen2Board();
        board.printBoardPiecesWPOV();
        System.out.println("evaluationFunc white: " + evaluationFunc(board));

        Board board146 = new Board("6k1/B5q1/KR6/8/8/8/8/6p1 - w");
        board146.fen2Board();
        board146.printBoardPiecesWPOV();
        System.out.println("evaluationFunc white: " + evaluationFunc(board146));

        Board board6261 = new Board("6k1/B7/KR4q1/8/8/8/6p1/8 - w");
        board6261.fen2Board();
        board6261.printBoardPiecesWPOV();
        System.out.println("evaluationFunc white: " + evaluationFunc(board6261));
    }

    private static String getPhase(Board board) {
        int pieces = Math.min(board.getPlayerPieces(true).get(0), board.getPlayerPieces(false).get(0));
        int pawns = Math.min(board.getPlayerPieces(true).get(1), board.getPlayerPieces(false).get(1));
        if (pieces > 6 && pawns <= 6) {
            return "middle";
        } else if (pieces <= 7) {
            return "end";
        } else return "opening";
    }

    public static int evaluationFunc(Board board) {
        List<String> wMoves = Move.possibleMovesW(board);
        List<String> bMoves = Move.possibleMovesB(board);
        int wScore = 0;
        int bScore = 0;

      /*  if ((isWhite) && board.NEWisCheck(!isWhite)) wScore += 100;
        else if ((!isWhite) && board.NEWisCheck(isWhite)) bScore += 100;
        if (isWhite && board.isCheckmate()) {
            bScore += 10000; // whites turn and checkmate --> black wins
        } else if (!isWhite && board.isCheckmate()) {
            wScore += 10000; // black turn and checkmate --> white wins
        }*/

        // wScore += MaterialScore(board, board.getPlayerPieces(true).get(0), true);
        // bScore += MaterialScore(board, board.getPlayerPieces(false).get(0), false);

        String phase = getPhase(board);
        switch (phase) {
            case "opening" -> {
                wScore += 10 * MobilityScore(board, "opening", true, wMoves);
                wScore += MaterialScore(board, board.getPlayerPieces(true).get(0), true);
                wScore += 2 * checkScore(board, true);
                wScore += 3 * CaptureScore(board, true);
                wScore += KingZoneAttack(board, true);
                bScore += 10 * MobilityScore(board, "opening", false, bMoves);
                bScore += MaterialScore(board, board.getPlayerPieces(false).get(0), false);
                bScore += 2 * checkScore(board, false);
                bScore += 3 * CaptureScore(board, false);
                bScore += KingZoneAttack(board, false);
            }
            //eval += 10 * (wmob - bmob);
            case "middle" -> {
                wScore += 5 * MobilityScore(board, "middle", true, wMoves);
                wScore += MaterialScore(board, board.getPlayerPieces(true).get(0), true);
                wScore += 2 * checkScore(board, true);
                wScore += 5 * CaptureScore(board, true);
                wScore += KingZoneAttack(board, true);
                bScore += 5 * MobilityScore(board, "middle", false, bMoves);
                bScore += MaterialScore(board, board.getPlayerPieces(false).get(0), false);
                bScore += 2 * checkScore(board, false);
                bScore += 5 * CaptureScore(board, false);
                bScore += KingZoneAttack(board, false);
            }
            //eval += 10 * (wmob - bmob);
            case "end" -> {
                wScore += 2 * MobilityScore(board, "end", true, wMoves);
                wScore += MaterialScore(board, board.getPlayerPieces(true).get(0), true);
                wScore += 3 * checkScore(board, true);
                wScore += 6 * CaptureScore(board, true);
                wScore += KingZoneAttack(board, true);
                bScore += 2 * MobilityScore(board, "end", false, bMoves);
                bScore += MaterialScore(board, board.getPlayerPieces(false).get(0), false);
                bScore += 3 * checkScore(board, false);
                bScore += 6 * CaptureScore(board, false);
                bScore += KingZoneAttack(board, false);
            }
            //eval += 10 * (wmob - bmob);
        }
        // wScore += 2 * checkScore(board, true) + 5 * CaptureScore(board, true) + KingZoneAttack(board, true);//checkmate and black turn
        // bScore += 2 * checkScore(board, false)+ 5 * CaptureScore(board, false)+ KingZoneAttack(board, false);

        //if (isWhite)
        return wScore - bScore;
        // else return bScore - wScore;
       /* eval += 5 * (wScore - bScore);// + 3; //* CaptureScore(board, isWhite) + KingZoneAttack(board, isWhite);
        if (!isWhite){
            return eval;
        } else return -eval;
        */
    }

    //TODO checkScore fix
    public static int checkScore(Board board, boolean isWhite) {
        int checkEval = 0;
        if (board.NEWisCheck(isWhite)) {
            checkEval += 100;
        }
        if (board.NEWisCheck(!isWhite)) {
            checkEval += 100;
        }
        if (isWhite && board.isCheckmate()) {
            checkEval += 10000; // whites turn and checkmate --> black wins
        } else if (!isWhite && board.isCheckmate()) {
            checkEval += 10000; // black turn and checkmate --> white wins
        }
        return checkEval;
    }

    public static int KingZoneAttack(Board board, boolean isWhite) {
        int attackingPieces = 0;
        int attackVal = 0;
        int wKing_pos = 0;
        int bKing_pos = 0;

        while (board.getCells()[wKing_pos] != 9 && wKing_pos < 63) {
            wKing_pos++;
        }
        while (board.getCells()[bKing_pos] != 17 && bKing_pos < 63) {
            bKing_pos++;
        }
        List<String> wk_moves = King.allMoves(wKing_pos, board.getCells(), true);
        List<String> bk_moves = King.allMoves(bKing_pos, board.getCells(), false);
        List<String> pmoves;
        List<Integer> zone = new ArrayList<>();
        if (isWhite) {
            pmoves = Move.possibleMovesW(board);
            for (String move : bk_moves) {
                int source = Integer.parseInt(move.split("-")[0]);
                int target = Integer.parseInt(move.split("-")[1]);
                zone.add(target);
            }
            for (String move : pmoves) {
                int source = Integer.parseInt(move.split("-")[0]);
                int target = Integer.parseInt(move.split("-")[1]);
                if (zone.contains(target)) {
                    attackingPieces++;
                    switch (board.getCells()[source]) {
                        case 12, 13 -> attackVal += 3;
                        case 11 -> attackVal += 5;
                        case 10 -> attackVal += 8;
                    }
                }
            }
        } else {
            List<String> attacks = new ArrayList<>();
            pmoves = Move.possibleMovesB(board);
            for (String move : wk_moves) {
                int source = Integer.parseInt(move.split("-")[0]);
                int target = Integer.parseInt(move.split("-")[1]);
                zone.add(target);
            }
            for (String move : pmoves) {
                int source = Integer.parseInt(move.split("-")[0]);
                int target = Integer.parseInt(move.split("-")[1]);
                if (zone.contains(target)) {
                    attackingPieces++;
                    switch (board.getCells()[source]) {
                        case 20, 21 -> attackVal += 3;
                        case 19 -> attackVal += 5;
                        case 18 -> attackVal += 8;
                    }
                }
            }
        }
        return attackVal * attackingPieces;
    }

    public static int CaptureScore(Board board, boolean isWhite) {
        int score = 0;
        if (isWhite) {
            List<String> c_moves = Move.CapturemovesW(board);
            for (String move : c_moves) {
                int source = Integer.parseInt(move.split("-")[0]);
                int target = Integer.parseInt(move.split("-")[1]);
                // best case : pawn(6) captures queen(1) , 6-1=5
                score += 2 * (board.getCells()[source] - 8) - (board.getCells()[source] - 16);
            }
        } else {
            List<String> c_moves = Move.CapturemovesB(board);
            for (String move : c_moves) {
                int source = Integer.parseInt(move.split("-")[0]);
                int target = Integer.parseInt(move.split("-")[1]);
                // best case : pawn(6) captures queen(1) , 6-1=5
                score += 2 * (board.getCells()[source] - 16) - (board.getCells()[source] - 8);
            }
        }
        return score;
    }

    public static int MaterialScore(Board board, int pieces, boolean isWhite) {
        int score = 0;
        if (isWhite) {
            int cellNr = 0;
            int each = 0;
            while (cellNr < 64 && each < pieces) {
                switch (board.cells[cellNr]) {
                    case 14 -> { //Pawn
                        score += 1;
                        each++;
                    } //Knight and Bishop
                    case 13, 12 -> {
                        score += 3;
                        each++;
                    }
                    case 11 -> {
                        score += 5;
                        each++;
                    }
                    case 10 -> {
                        score += 9;
                        each++;
                    }
                }
                cellNr++;
            }
        } else {
            int cellNr = 63;
            int each = 0;
            while (cellNr >= 0 && each < pieces) {
                switch (board.cells[cellNr]) {
                    case 22 -> { //Pawn
                        score += 1;
                        each++;
                    }//Knight and Bishop
                    case 20, 21 -> {
                        score += 3;
                        each++;
                    }
                    case 19 -> {
                        score += 5;
                        each++;
                    }
                    case 18 -> {
                        score += 9;
                        each++;
                    }
                }
                cellNr--;
            }
        }
        return score;
    }

    public static int MobilityScore(Board board, String phase, boolean isWhite, List<String> pmoves) {
        int score;
        int pieces = board.getPlayerPieces(isWhite).get(0);
        //each phase has its own mobility score as each piece's mobilty hasn't the same importance
        //depending the game phase, e.g bishop mobility is way more important than rook's in openings
        //but not in end games or middle
        if (phase.equals("opening")) {
            score = OpeningMobility(board, pmoves);
        } else if (phase.equals("middle")) {
            score = MiddleMobility(board, pmoves);
        } else score = EndMobility(board, pmoves);
        return score;
    }

    private static int EndMobility(Board board, List<String> pmoves) {
        int bonus = 0;
        for (String move : pmoves) {
            int source = Integer.parseInt(move.split("-")[0]);
            int target = Integer.parseInt(move.split("-")[1]);
            switch (board.getCells()[source]) {
                case 11, 19 ->//Rook is very important in midgame
                        bonus += 3;
                case 13, 21 ->//Bishop +1 bonus
                        bonus += 1;
                case 12, 20 ->//Knight +1 bonus
                        bonus += 1;
                case 10, 18 ->//Queen +2 bonus
                        bonus += 4;
                case 9, 17 ->//KoTH
                        bonus += DistToMiddleSquare(source) * 5;
                // for pawns we have a bonus for reaching last square
                case 14 -> bonus += 6 - Move.pMovesUp(source);
                case 22 -> bonus += 6 - Move.pMovesDown(source);
                default -> {
                }
            }
        }
        return bonus + pmoves.size();

    }

    private static int OpeningMobility(Board board, List<String> pmoves) {
        int bonus = 0;
        for (String move : pmoves) {

            int source = Integer.parseInt(move.split("-")[0]);
            int target = Integer.parseInt(move.split("-")[1]);
            switch (board.getCellContent(source)) {
                case 13, 21 ->//Bishop +2 bonus
                        bonus += 3;
                case 12, 20 ->//Knight +2 bonus
                        bonus += 2;
                case 10, 18 ->//Queen +2 bonus
                        bonus += 1;

                default -> {
                }
            }
        }
        return bonus + pmoves.size();
    }


    private static int MiddleMobility(Board board, List<String> pmoves) {
        int bonus = 0;
        for (String move : pmoves) {
            int source = Integer.parseInt(move.split("-")[0]);
            int target = Integer.parseInt(move.split("-")[1]);
            switch (board.getCells()[source]) {
                case 11, 19 ->//Rook is very important in midgame
                        bonus += 3;
                case 13, 21 ->//Bishop +1 bonus
                        bonus += 1;
                case 12, 20 ->//Knight +1 bonus
                        bonus += 1;
                case 10, 18 ->//Queen +2 bonus
                        bonus += 1;
                case 9, 17 ->//KoTH
                        bonus += DistToMiddleSquare(source) * 3;
                default -> {
                }
            }
        }
        return bonus + pmoves.size();
    }

    public static int DistToMiddleSquare(int cellNr) {
        //dist will be used as a score so the smaller the distance the bigger the score
        //8*8 square
        if (cellNr % 8 == 0 || cellNr % 8 == 7 || cellNr / 8 == 0 || cellNr / 8 == 7) return 0;
            //6*6 square
        else if (cellNr % 8 == 1 || cellNr % 8 == 6 || cellNr / 8 == 1 || cellNr / 8 == 6) return 2;
            //4*4
        else if (cellNr % 8 == 2 || cellNr % 8 == 5 || cellNr / 8 == 2 || cellNr / 8 == 5) return 10;
            //2*2(middlesquares)
        else return 1000;

    }
}
