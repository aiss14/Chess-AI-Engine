import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Class for basic move functions: move one vertically/horizontal/diagonal
public class Move {

    public static List<String> possibleMovesW(Board board) {
        int[] cells = board.getCells();
        List<String> allMoves = new ArrayList<>();
        List<String> allLegalMoves = new ArrayList<>();

        int cellNr = -1;
        while (cellNr < 63) {
            // Skip over empty cells and black pieces
            cellNr++;
            while (cells[cellNr] == 0 || cells[cellNr] > 16) {
                if (cellNr == 63) {
                    break;
                }
                cellNr++;
            }
            switch (cells[cellNr]) {
                case 9 -> {     // King
                    List<String> allMovesKing;
                    allMovesKing = King.allMoves(cellNr, cells, true);
                    allMoves.addAll(allMovesKing);
                }
                case 10 -> {    // Queen
                    List<String> allMovesQueenHorizontal;
                    List<String> allMovesQueenDiagonal;
                    allMovesQueenHorizontal = Rook.allMoves(cellNr, cells, true);
                    allMovesQueenDiagonal = Bishop.allMoves(cellNr, cells, true);
                    allMoves.addAll(allMovesQueenHorizontal);
                    allMoves.addAll(allMovesQueenDiagonal);
                }
                case 11 -> {    // Rook
                    List<String> allMovesRook;
                    allMovesRook = Rook.allMoves(cellNr, cells, true);
                    allMoves.addAll(allMovesRook);
                }
                case 12 -> {    // Knight
                    List<String> allMovesKnight;
                    allMovesKnight = Knight.allMoves(cellNr, cells, true);
                    allMoves.addAll(allMovesKnight);
                }
                case 13 -> {    // Bishop
                    List<String> allMovesBishop;
                    allMovesBishop = Bishop.allMoves(cellNr, cells, true);
                    allMoves.addAll(allMovesBishop);
                }
                case 14 -> {    // Pawn
                    List<String> allMovesPawn;
                    allMovesPawn = Pawn.allMoves(cellNr, board, true);
                    String tmp;
                    while (!allMovesPawn.isEmpty()) {
                        tmp = allMovesPawn.get(0);
                        allMovesPawn.remove(0);
                        allMoves.add(tmp);
                    }
                }
                default -> {
                }
            }
        }
        for (String moveCheck : allMoves) {
            board.makeMove(moveCheck);
            if (!board.NEWisCheck(true)) {
                allLegalMoves.add(moveCheck);
            }
            board.unmakeLastMove();
        }
        rochadeW(board, board.hasMoved, allLegalMoves);
        allLegalMoves = moveSort(allLegalMoves, board);
        return allLegalMoves;
    }

    public static List<String> possibleMovesB(Board board) {
        int[] cells = board.getCells();
        List<String> allMoves = new ArrayList<>();
        List<String> allLegalMoves = new ArrayList<>();

        int cellNr = -1;
        while (cellNr < 63) {
            // Skip over empty cells and black pieces
            cellNr++;
            while (cells[cellNr] == 0 || cells[cellNr] < 16) {
                if (cellNr == 63) {
                    break;
                }
                cellNr++;
            }
            switch (cells[cellNr]) {
                case 17 -> { // King
                    List<String> allMovesKing;
                    allMovesKing = King.allMoves(cellNr, cells, false);
                    allMoves.addAll(allMovesKing);
                }
                case 18 -> { // Queen
                    List<String> allMovesQueenHorizontal;
                    List<String> allMovesQueenDiagonal;
                    allMovesQueenHorizontal = Rook.allMoves(cellNr, cells, false);
                    allMovesQueenDiagonal = Bishop.allMoves(cellNr, cells, false);
                    allMoves.addAll(allMovesQueenHorizontal);
                    allMoves.addAll(allMovesQueenDiagonal);
                }
                case 19 -> { // Rook
                    List<String> allMovesRook;
                    allMovesRook = Rook.allMoves(cellNr, cells, false);
                    allMoves.addAll(allMovesRook);
                }
                case 20 -> {    // Knight
                    List<String> allMovesKnight;
                    allMovesKnight = Knight.allMoves(cellNr, cells, false);
                    allMoves.addAll(allMovesKnight);
                }
                case 21 -> {    // Bishop
                    List<String> allMovesBishop;
                    allMovesBishop = Bishop.allMoves(cellNr, cells, false);
                    allMoves.addAll(allMovesBishop);
                }
                case 22 -> { // Pawn
                    List<String> allMovesPawn;
                    allMovesPawn = Pawn.allMoves(cellNr, board, false);
                    String tmp;
                    while (!allMovesPawn.isEmpty()) {
                        tmp = allMovesPawn.get(0);
                        allMovesPawn.remove(0);
                        allMoves.add(tmp);
                    }
                }
                default -> {
                }
            }
        }
        for (String moveCheck : allMoves) {
            board.makeMove(moveCheck);
            if (!board.NEWisCheck(false)) {
                allLegalMoves.add(moveCheck);
            }
            board.unmakeLastMove();
        }
        rochadeB(board, board.gethasMoved(), allLegalMoves);
        allLegalMoves = moveSort(allLegalMoves, board);
        return allLegalMoves;
    }

    public static List<String> possibleCounterMovesW(Board board) {
        int[] cells = board.getCells();
        List<String> allMoves = new ArrayList<>();

        int cellNr = -1;
        while (cellNr < 63) {
            // Skip over empty cells and black pieces
            cellNr++;
            while (cells[cellNr] == 0 || cells[cellNr] > 16) {
                if (cellNr == 63) {
                    break;
                }
                cellNr++;
            }
            switch (cells[cellNr]) {
                case 9 -> {     // King
                    List<String> allMovesKing;
                    allMovesKing = King.allMoves(cellNr, cells, true);
                    allMoves.addAll(allMovesKing);
                }
                //System.out.println("King moves: "+ allMovesKing);
                case 10 -> {    // Queen
                    List<String> allMovesQueenHorizontal;
                    List<String> allMovesQueenDiagonal;
                    allMovesQueenHorizontal = Rook.allMoves(cellNr, cells, true);
                    allMovesQueenDiagonal = Bishop.allMoves(cellNr, cells, true);
                    allMoves.addAll(allMovesQueenHorizontal);
                    allMoves.addAll(allMovesQueenDiagonal);
                }
                case 11 -> {    // Rook
                    List<String> allMovesRook;
                    allMovesRook = Rook.allMoves(cellNr, cells, true);
                    allMoves.addAll(allMovesRook);
                }
                //System.out.println("Rook moves: " + allMovesRook);
                case 12 -> {    // Knight
                    List<String> allMovesKnight;
                    allMovesKnight = Knight.allMoves(cellNr, cells, true);
                    allMoves.addAll(allMovesKnight);
                }
                //System.out.println("Knight moves: " + allMovesKnight);
                case 13 -> {    // Bishop
                    List<String> allMovesBishop;
                    allMovesBishop = Bishop.allMoves(cellNr, cells, true);
                    allMoves.addAll(allMovesBishop);
                }
                case 14 -> {    // Pawn
                    List<String> allMovesPawn;
                    allMovesPawn = Pawn.allMoves(cellNr, board, true);
                    String tmp;
                    while (!allMovesPawn.isEmpty()) {
                        tmp = allMovesPawn.get(0);
                        allMovesPawn.remove(0);
                        allMoves.add(tmp);
                    }
                }
                default -> {
                }
            }
        }
        return allMoves;
    }

    /*
    To calculate all possible counter moves, will not check with isCheck
 */
    public static List<String> possibleCounterMovesB(Board board) {
        int[] cells = board.getCells();
        List<String> allMoves = new ArrayList<>();

        int cellNr = -1;
        while (cellNr < 63) {
            // Skip over empty cells and black pieces
            cellNr++;
            while (cells[cellNr] == 0 || cells[cellNr] < 16) {
                if (cellNr == 63) {
                    break;
                }
                cellNr++;
            }
            switch (cells[cellNr]) {
                case 17 -> { // King
                    List<String> allMovesKing;
                    allMovesKing = King.allMoves(cellNr, cells, false);
                    allMoves.addAll(allMovesKing);
                }
                case 18 -> { // Queen
                    List<String> allMovesQueenHorizontal;
                    List<String> allMovesQueenDiagonal;
                    allMovesQueenHorizontal = Rook.allMoves(cellNr, cells, false);
                    allMovesQueenDiagonal = Bishop.allMoves(cellNr, cells, false);
                    allMoves.addAll(allMovesQueenHorizontal);
                    allMoves.addAll(allMovesQueenDiagonal);
                }
                case 19 -> { // Rook
                    List<String> allMovesRook;
                    allMovesRook = Rook.allMoves(cellNr, cells, false);
                    allMoves.addAll(allMovesRook);
                }
                case 20 -> {    // Knight
                    List<String> allMovesKnight;
                    allMovesKnight = Knight.allMoves(cellNr, cells, false);
                    allMoves.addAll(allMovesKnight);
                }
                case 21 -> {    // Bishop
                    List<String> allMovesBishop;
                    allMovesBishop = Bishop.allMoves(cellNr, cells, false);
                    allMoves.addAll(allMovesBishop);
                }
                case 22 -> { // Pawn
                    List<String> allMovesPawn;
                    allMovesPawn = Pawn.allMoves(cellNr, board, false);
                    String tmp;
                    while (!allMovesPawn.isEmpty()) {
                        tmp = allMovesPawn.get(0);
                        allMovesPawn.remove(0);
                        allMoves.add(tmp);
                    }
                }
                default -> {
                }
            }
        }
        return allMoves;
    }

    public static List<String> moveSort(List<String> pMoves, Board board){
        LinkedList<String> pMovesSorted = new LinkedList<>();
        LinkedList<String> checkMoves = new LinkedList<>();
        LinkedList<String> captureMoves = new LinkedList<>();
        for (String move : pMoves){
            int target = Integer.parseInt(move.split("-")[1]);
            int targetContent = board.getCellContent(target);
            board.makeMove(move);
            if (board.isCheck(board.getwTurn())){
                checkMoves.add(move);
            } else {
                switch (targetContent) {
                    case 10, 18 -> captureMoves.addFirst(move);
                    case 11, 19 -> captureMoves.add(move);
                    case 12, 13, 14, 20, 21, 22 -> captureMoves.addLast(move);
                    default -> pMovesSorted.addLast(move);
                }
            }
            board.unmakeLastMove();
        }
        while (!captureMoves.isEmpty()) {
            pMovesSorted.addFirst(captureMoves.poll());
        }
        while (!checkMoves.isEmpty()) {
            pMovesSorted.addFirst(checkMoves.poll());
        }

        return pMovesSorted;
    }

    public static List<String> CapturemovesW(Board board) {
        List<String> c_moves = new ArrayList<>();
        List<String> pmoves = possibleMovesW(board);
        for (String move : pmoves) {
            int target = Integer.parseInt(move.split("-")[1]);
            if (board.getCells()[target] > 16 && board.getCells()[target] != 0) {
                c_moves.add(move);
            }
        }
        return c_moves;
    }

    public static List<String> CapturemovesB(Board board) {
        List<String> c_moves = new ArrayList<>();
        List<String> pmoves = possibleMovesB(board);
        for (String move : pmoves) {
            int target = Integer.parseInt(move.split("-")[1]);
            if (board.getCells()[target] < 16 && board.getCells()[target] != 0) {
                c_moves.add(move);
            }
        }
        return c_moves;
    }

    public static void rochadeW(Board board, int[] hasMoved, List<String> moves) {
        int[] cells = board.getCells();
        if (hasMoved[0] == 0 && hasMoved[1] == 0 && cells[1] == 0 && cells[2] == 0 && cells[3] == 0 && cells[0] == 11 && cells[4] == 9) { // Rochade Queenside
            Board boardTmp1 = new Board(board);
            int[] cellTmp1 = boardTmp1.getCells();
            cellTmp1[4] = 0;
            cellTmp1[3] = 9;
            Board boardTmp2 = new Board(board);
            int[] cellTmp2 = boardTmp2.getCells();
            cellTmp2[4] = 0;
            cellTmp2[2] = 9;
            if (!boardTmp1.isCheck(true) && !boardTmp2.isCheck(true)) { // checking if king is check while moving in rochade
                moves.add("0-0-0-w");
            }
        }
        if (hasMoved[0] == 0 && hasMoved[2] == 0 && cells[5] == 0 && cells[6] == 0 && cells[7] == 11 && cells[4] == 9) { // Rochade Kingside
            Board boardTmp3 = new Board(board);
            int[] cellTmp3 = boardTmp3.getCells();
            cellTmp3[4] = 0;
            cellTmp3[5] = 9;
            Board boardTmp4 = new Board(board);
            int[] cellTmp4 = boardTmp4.getCells();
            cellTmp4[4] = 0;
            cellTmp4[6] = 9;
            if (!boardTmp3.isCheck(true) && !boardTmp4.isCheck(true)) { // checking if king is check while moving in rochade
                moves.add("0-0-w");
            }
        }
    }

    public static void rochadeB(Board board, int[] hasMoved, List<String> moves) {
        int[] cells = board.getCells();
        if (hasMoved[3] == 0 && hasMoved[4] == 0 && cells[59] == 0 && cells[58] == 0 && cells[57] == 0 && cells[56] == 19 && cells[60] == 17) { // Rochade Queenside
            Board boardTmp1 = new Board(board);
            int[] cellTmp1 = boardTmp1.getCells();
            cellTmp1[60] = 0;
            cellTmp1[59] = 17;
            Board boardTmp2 = new Board(board);
            int[] cellTmp2 = boardTmp2.getCells();
            cellTmp2[60] = 0;
            cellTmp2[59] = 17;
            if (!boardTmp1.isCheck(false) && !boardTmp2.isCheck(false)) { // checking if king is check while moving in rochade
                moves.add("0-0-0-b");
            }
        }
        if (hasMoved[3] == 0 && hasMoved[5] == 0 && cells[61] == 0 && cells[62] == 0 && cells[63] == 19 && cells[60] == 17) { // Rochade Kingside
            Board boardTmp3 = new Board(board);
            int[] cellTmp3 = boardTmp3.getCells();
            cellTmp3[60] = 0;
            cellTmp3[61] = 17;
            Board boardTmp4 = new Board(board);
            int[] cellTmp4 = boardTmp4.getCells();
            cellTmp4[60] = 0;
            cellTmp4[62] = 17;
            if (!boardTmp3.isCheck(false) && !boardTmp4.isCheck(false)) { // checking if king is check while moving in rochade
                moves.add("0-0-b");
            }
        }
    }

    public static String moveUp(int source) {
        // return destination cell, instead of the whole string
        return Integer.toString(source + 8);
    }

    public static String moveDown(int source) {
        return (Integer.toString(source - 8));
    }
    public static String moveLeft(int source) {
        return (Integer.toString(source - 1));
    }
    public static String moveRight(int source) {
        return (Integer.toString(source + 1));
    }
    public static String moveUpLeft(int source) {
        return (Integer.toString(source + 7));
    }
    public static String moveUpRight(int source) {
        return (Integer.toString(source + 9));
    }
    public static String moveDownLeft(int source) {
        return (Integer.toString(source - 9));
    }
    public static String moveDownRight(int source) {
        return (Integer.toString(source - 7));
    }
    // Returns number of possible moves up
    public static int pMovesUp(int cellNr) {
        return 7 - (cellNr / 8);
    }
    // Returns number of possible moves down
    public static int pMovesDown(int cellNr) {
        return cellNr / 8;
    }
    public static int pMovesRight(int cellNr) {
        return 7 - (cellNr % 8);
    }
    public static int pMovesLeft(int cellNr) {
        return (cellNr % 8);
    }


}
