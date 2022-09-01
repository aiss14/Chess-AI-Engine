import java.util.ArrayList;
import java.util.List;

public class KI {

    public static int EOF_cutoff = 10000;
    public Board board;
    public boolean isWhite;
    public List<String> moves;
    public List<Integer> scores;
    public int zusteande;
    public int cutoff;
    public int counter;
    public TTable TT;

    /*############################*/
    /*----- KI constructor -------*/
    /*############################*/
    public KI(Board board, boolean isWhite) {
        this.board = new Board(board);
        this.isWhite = isWhite;
        this.moves = new ArrayList<>();
        this.scores = new ArrayList<>();
        this.zusteande = 0;
        this.cutoff = 0;
        this.counter = 0;
        this.TT = new TTable();
    }

    /*############################*/
    /*----- basic functions ------*/
    /*############################*/
    public void CopyLists(List<String> ki_moves, List<Integer> ki_scores) {
        this.moves = ki_moves;
        this.scores = ki_scores;
    }

    public String getNextMove(Board board, long timelimit) {
        int score = IterativeDFS(board, timelimit - 10, 1);
        System.out.println();
        return this.moves.get(this.scores.indexOf(score));
    }

    public int IterativeDFS(Board board, long timelimit, int algo) {
        long start = System.currentTimeMillis();
        long end = start + timelimit;
        int depth = 1;
        int result = 0;
        while (System.currentTimeMillis() < end) {
            long roundStart = System.currentTimeMillis();
            this.counter = 0;
            switch (algo) {
                case 1 -> result = alphaBetaNM(board, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, depth);
                case 2 -> result = miniMax(board, depth, board.wTurn);
                case 3 -> result = alphaBetaNMTT(board, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, depth);
            }
            depth++;
            long searchDuration = System.currentTimeMillis() - roundStart;
            if ((end - System.currentTimeMillis()) < 5 * searchDuration) break;
        }
        return result;
    }

    /*###################################*/
    /*----- basic search functions ------*/
    /*###################################*/
    public int miniMax(Board board, int depth, boolean isWhite) {
        this.zusteande++;
        List<String> pmoves;
        List<String> bestmoves = new ArrayList<>();
        List<Integer> bestscores = new ArrayList<>();
        if (isWhite) {
            pmoves = Move.possibleMovesW(board);
        } else {
            pmoves = Move.possibleMovesB(board);
        }
        int score = Evaluation.evaluationFunc(board);
        if (depth == 0 || board.isWon || board.isDraw || pmoves.size() == 0 || score >= EOF_cutoff || score <= -EOF_cutoff)
            return score;
        if (isWhite) {  // max
            int maxEv = Integer.MIN_VALUE + 1;
            List<String> possiblemoves = Move.possibleMovesW(board);
            for (String move : possiblemoves) {
                board.makeMove(move);
                int eval = miniMax(board, depth - 1, false);
                if (eval > maxEv) {
                    bestmoves.add(0, move);
                    bestscores.add(0, eval);
                }
                maxEv = Math.max(maxEv, eval);
                board.unmakeLastMove();
            }
            CopyLists(bestmoves, bestscores);
            return maxEv;
        } else {    // min
            int minEv = Integer.MAX_VALUE - 1;
            List<String> possiblemoves = Move.possibleMovesB(board);
            for (String move : possiblemoves) {
                board.makeMove(move);
                int eval = miniMax(board, depth - 1, true);
                if (eval < minEv) {
                    bestmoves.add(0, move);
                    bestscores.add(0, eval);
                }
                minEv = Math.min(minEv, eval);
                board.unmakeLastMove();
            }
            CopyLists(bestmoves, bestscores);
            return minEv;
        }
    }

    public int alphaBeta(Board board, boolean isWhite, int alpha, int beta, int depth) {
        this.zusteande++;
        List<String> pmoves = Move.possibleMovesW(board);
        List<String> bestmoves = new ArrayList<>();
        List<Integer> bestscores = new ArrayList<>();
        int score = Evaluation.evaluationFunc(board);
        if (depth == 0 || board.isWon || board.isDraw || pmoves.size() == 0 || score >= EOF_cutoff || score <= -EOF_cutoff)
            return score;
        if (isWhite) {  // max
            int maxEv = Integer.MIN_VALUE + 1;
            List<String> possiblemoves = Move.possibleMovesW(board);
            for (String move : possiblemoves) {
                Board copy = new Board(board);
                copy.makeMove(move);
                int eval = alphaBeta(copy, false, alpha, beta, depth - 1);
                if (eval > maxEv) {
                    bestmoves.add(0, move);
                    bestscores.add(0, eval);
                }
                maxEv = Math.max(maxEv, eval);
                alpha = Math.max(maxEv, alpha);
                if (beta <= alpha) {
                    cutoff++;
                    break;
                }
            }
            CopyLists(bestmoves, bestscores);
            return maxEv;
        } else {    // min
            int minEv = Integer.MAX_VALUE - 1;
            List<String> possiblemoves = Move.possibleMovesB(board);
            for (String move : possiblemoves) {
                Board copy = new Board(board);
                copy.makeMove(move);
                int eval = alphaBeta(copy, true, alpha, beta, depth - 1);
                if (eval < minEv) {
                    bestmoves.add(0, move);
                    bestscores.add(0, eval);
                }
                minEv = Math.min(minEv, eval);
                beta = Math.min(beta, minEv);
                if (beta <= alpha) {
                    cutoff++;
                    break;
                }
            }
            CopyLists(bestmoves, bestscores);
            return minEv;
        }
    }

    /*######################################*/
    /*----- advanced search functions ------*/
    /*######################################*/
    public int alphaBetaTT(Board board, int alpha, int beta, int depth) {
        this.zusteande++;
        if (this.counter == 0) {
            this.moves = new ArrayList<>();
            this.scores = new ArrayList<>();
        }

        List<String> pmoves;
        if (board.wTurn) {
            pmoves = Move.possibleMovesW(board);
        } else {
            pmoves = Move.possibleMovesB(board);
        }
        this.counter++;
        TT_data entry = this.TT.TT_lookup(board, alpha, beta, depth);
        if (entry.bestScore != Integer.MIN_VALUE + 1)
            //already saved position in transposition table
            return entry.bestScore;
        entry.bestScore = Evaluation.evaluationFunc(board);

        if (depth == 0 || board.isWon || board.isDraw || pmoves.size() == 0 || entry.bestScore >= EOF_cutoff || entry.bestScore <= -EOF_cutoff) {
            this.TT.update(board, entry.bestScore, TTable.EXACT, depth);//TODO check original depth
            return entry.bestScore;
        }
        int type_default = TTable.UPPER_BOUND;
        for (String move : pmoves) {
            Board copy = new Board(board);
            copy.makeMove(move);
            entry.bestScore = -alphaBetaTT(copy, -beta, -alpha, depth - 1);
            if (entry.bestScore >= beta) {
                this.TT.update(board, beta, TTable.LOWER_BOUND, depth);
                return beta;
            }
            if (entry.bestScore > alpha) {
                type_default = TTable.EXACT;
                alpha = entry.bestScore;
            }
        }
        this.TT.update(board, alpha, type_default, depth);
        return alpha;
    }

    public int alphaBetaNM(Board board, int alpha, int beta, int depth) {
        this.zusteande++;
        List<String> pmoves;
        if (board.wTurn) {
            pmoves = Move.possibleMovesW(board);
        } else {
            pmoves = Move.possibleMovesB(board);
        }
        List<String> bestmoves = new ArrayList<>();
        List<Integer> bestscores = new ArrayList<>();
        int score = Evaluation.evaluationFunc(board);

        if (depth == 0 || board.isWon || board.isDraw || pmoves.size() == 0 || score >= EOF_cutoff || score <= -EOF_cutoff)
            if (board.wTurn) return score;
            else return -score;
        if (NullMove(board, alpha, beta, depth)) {
            if (board.wTurn) return score;
            else return -score;
        }
        for (String move : pmoves) {
            board.makeMove(move);
            int eval = -alphaBetaNM(board, -beta, -alpha, depth - 1);
            board.unmakeLastMove();
            if (eval > alpha) {
                bestmoves.add(0, move);
                bestscores.add(0, eval);
                alpha = eval;
                if (beta <= alpha) {
                    cutoff++;
                    return alpha;
                }
            }
        }
        CopyLists(bestmoves, bestscores);
        return alpha;
    }

    private Boolean NullMove(Board board, int alpha, int beta, int depth) {
        if (beta < Integer.MAX_VALUE - 1 && depth > 2 && !board.isCheck(board.wTurn)) {
            board.wTurn = !board.wTurn;
            int eval = alphaBetaNM(board, alpha, beta, depth - 1);
            board.wTurn = !board.wTurn;
            if (beta <= eval) {
                cutoff++;
                return true;
            }
        }
        return false;
    }

    public int alphaBetaNMTT(Board board, int alpha, int beta, int depth) {
        this.zusteande++;
        int alpha_org = alpha;
        boolean first = false;
        if (this.counter == 0) {
            first = true;
            this.moves = new ArrayList<>();
            this.scores = new ArrayList<>();
        }
        List<String> pmoves = new ArrayList<>();
        if (board.wTurn) {
            pmoves = Move.possibleMovesW(board);
        } else {
            pmoves = Move.possibleMovesB(board);
        }
        TT_data entry = this.TT.TT_lookup(board, alpha, beta, depth);
        if (entry.bestScore != Integer.MIN_VALUE + 1 && entry.depth >= depth) {
            if (entry.nodeType == TTable.EXACT) return entry.bestScore;
            else if (entry.nodeType == TTable.LOWER_BOUND) {
                alpha = Math.max(alpha, entry.bestScore);
            } else if (entry.nodeType == TTable.UPPER_BOUND) {
                beta = Math.min(beta, entry.bestScore);
            }
        }
        if (alpha >= beta) {
            cutoff++;
            return entry.bestScore;
        }
        if (depth == 0 || board.isWon || board.isDraw || pmoves.size() == 0) {
            entry.bestScore = Evaluation.evaluationFunc(board);
            this.TT.update(board, entry.bestScore, TTable.EXACT, depth);//TODO check original depth
            if (board.wTurn) return entry.bestScore;
            else return -entry.bestScore;
        }
        int eval = Integer.MIN_VALUE + 1;
        for (String move : pmoves) {
            board.makeMove(move);
            eval = Math.max(eval, -alphaBetaNMTT(board, -beta, -alpha, depth - 1));
            board.unmakeLastMove();
            if (eval > alpha) {
                alpha = eval;
                if (beta <= alpha) {
                    cutoff++;
                    return alpha;
                }
            }
            if (first) {
                this.moves.add(move);
                this.scores.add(alpha);
            }
        }
        entry.bestScore = eval;
        if (alpha_org >= eval) entry.nodeType = TTable.UPPER_BOUND;
        else if (eval >= beta) entry.nodeType = TTable.LOWER_BOUND;
        else entry.nodeType = TTable.EXACT;
        entry.depth = depth;
        this.TT.update(board, entry.bestScore, entry.nodeType, depth);
        return eval;
    }

    public int pvs(Board board, int alpha, int beta, int depth) {
        this.zusteande++;
        boolean first = false;
        boolean firstChild = true;
        if (this.counter == 0) {
            first = true;
            this.moves = new ArrayList<>();
            this.scores = new ArrayList<>();
        }
        this.counter++;
        List<String> pmoves;
        if (board.wTurn) {
            pmoves = Move.possibleMovesW(board);
        } else {
            pmoves = Move.possibleMovesB(board);
        }
        int score = Evaluation.evaluationFunc(board);

        if (depth == 0 || board.isWon() || pmoves.size() == 0 || score >= EOF_cutoff || score <= -EOF_cutoff) {
            if (board.wTurn) return score;
            else return -score;
        }
        //recursion for each move that's possible from the given board state
        for (String move : pmoves) {
            board.makeMove(move);
            int evaluation;
            if (firstChild) {
                evaluation = -pvs(board, -beta, -alpha, depth - 1);
                firstChild = false;
            } else {
                //null window search
                evaluation = -pvs(board, -alpha - 1, -alpha, depth - 1);
                if (alpha < evaluation && evaluation < beta) {
                    evaluation = -pvs(board, -beta, -evaluation, depth - 1);
                }
            }
            board.unmakeLastMove();
            if (first) {
                this.moves.add(move);
                this.scores.add(evaluation);
            }
            //if(evaluation >= beta)
            //   return beta;
            if (evaluation > alpha) {
                alpha = evaluation;
                //this.moves.add(move);
                //this.scores.add(evaluation);
            }
        }
        return alpha;
    }

    public int pvsTT(Board board, boolean isWhite, int alpha, int beta, int depth) {
        this.zusteande++;
        boolean first = false;
        boolean firstChild = true;
        if (this.counter == 0) {
            first = true;
            this.moves = new ArrayList<>();
            this.scores = new ArrayList<>();
        }
        this.counter++;
        List<String> pmoves;
        if (isWhite) {
            pmoves = Move.possibleMovesW(board);
        } else {
            pmoves = Move.possibleMovesB(board);
        }
        int hashFlag = TTable.UPPER_BOUND;
        int score = this.TT.TT_lookup(board, alpha, beta, depth).bestScore;

        if (score != Integer.MIN_VALUE + 1) return score;
        score = Evaluation.evaluationFunc(board);

        if (depth == 0 || board.isWon() || pmoves.size() == 0) {
            return score;
        }
        //recursion for each move that's possible from the given board state
        for (String move : pmoves) {
            Board bordCopy = new Board(board);
            bordCopy.makeMove(move);
            int evaluation;
            if (firstChild) {
                evaluation = -pvsTT(bordCopy, !isWhite, -beta, -alpha, depth - 1);
                firstChild = false;
            } else {
                //null window search
                evaluation = -pvsTT(bordCopy, !isWhite, -alpha - 1, -alpha, depth - 1);
                if (alpha < evaluation && evaluation < beta) {
                    evaluation = -pvsTT(bordCopy, !isWhite, -beta, -evaluation, depth - 1);
                    this.moves.add(move);
                    this.scores.add(evaluation);
                }
            }
            if (evaluation > alpha) {
                hashFlag = TTable.EXACT;
            }
            if (first) {
                this.moves.add(move);
                this.scores.add(evaluation);
            }
            alpha = Math.max(alpha, evaluation);
            if (alpha >= beta) {
                this.cutoff++;
                this.TT.update(board, beta, TTable.LOWER_BOUND, depth);
                return alpha;
            }
        }
        this.TT.update(board, alpha, hashFlag, depth);
        return alpha;
    }

    /* public int alphaBetaTT(Board board, boolean isWhite, int alpha, int beta, int depth) {
         this.zusteande++;
         List<String> pmoves = Move.possibleMovesW(board);
         List<String> bestmoves = new ArrayList<>();
         List<Integer> bestscores = new ArrayList<>();
         int score = this.TT.TT_lookup(board, alpha, beta, depth);
         if (score != Integer.MIN_VALUE+1)
             //already saved position in transposition table
             return score;
         score = evaluationFunc(board, board.wTurn);
         if (depth == 0 || board.isWon || board.isDraw || pmoves.size() == 0 || score >= EOF_cutoff || score <= -EOF_cutoff) {
             this.TT.update(board, score, 0, depth);
             return score;
         }
         if (isWhite) {  // max
             int maxEv = Integer.MIN_VALUE;
             int type_default = TT.UPPER_BOUND;
             List<String> possiblemoves = Move.possibleMovesW(board);
             for (String move : possiblemoves) {
                 //System.out.println("playing move: " + move);
                 board.makeMove(move);
                 int eval = alphaBetaTT(board, false, alpha, beta, depth - 1);
                 if (eval >= beta) {
                     this.TT.update(board, beta, TTable.LOWER_BOUND, depth);
                     return beta;
                 } else if (eval > alpha) {
                     type_default = TT.EXACT;
                     alpha = eval;
                 }
                 if (eval > maxEv) {
                     bestmoves.add(0, move);
                     bestscores.add(0, eval);
                 }
                 maxEv = Math.max(maxEv, eval);
                 alpha = Math.max(maxEv, alpha);
                 if (beta <= alpha) {
                     cutoff++;
                     break;
                 }
                 board.unmakeLastMove();
             }
             CopyLists(bestmoves, bestscores);
             this.TT.update(board, maxEv, type_default, depth);
             return maxEv;
         } else {    // min
             int minEv = Integer.MAX_VALUE-1;
             int type_default = TT.UPPER_BOUND;
             List<String> possiblemoves = Move.possibleMovesB(board);
             for (String move : possiblemoves) {
                 //System.out.println("playing move: " + move);
                 board.makeMove(move);
                 int eval = alphaBetaTT(board, true, alpha, beta, depth - 1);
                 if (eval >= beta) {
                     this.TT.update(board, beta, TTable.LOWER_BOUND, depth);
                     return beta;
                 } else if (eval > alpha) {
                     type_default = TT.EXACT;
                 }
                 if (eval < minEv) {
                     bestmoves.add(0, move);
                     bestscores.add(0, eval);
                 }
                 minEv = Math.min(minEv, eval);
                 beta = Math.min(beta, minEv);
                 if (beta <= alpha) {
                     cutoff++;
                     break;
                 }
                 board.unmakeLastMove();
             }
             CopyLists(bestmoves, bestscores);
             this.TT.update(board, minEv, type_default, depth);
             return minEv;
         }
     }
     */
}


