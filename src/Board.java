import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board {
    private final String fen;
    public int[] hasMoved = new int[6];    // 0=K 1=R(Queenside) 2=R(Kingside) 3=k 4=r(Queenside) 5=r(kingside)
    public boolean wTurn; // true = white players turn
    public boolean isWon;
    public boolean isDraw;

    /*
    format: "sourceCellNr-targetCellNr-sourceCellContent-targetCellContent"
    enPassant format: "sourceCellNr-targetCellNr-sourceCellContent-targetCellContent-capturedCellNr-capturedPieceValue"
     */
    public String lastmove; // last move als array von moves
    public LinkedList<String> lastMoves;
    public boolean lastmoveEnPassant;
    public List<Integer> wpieces;
    public List<Integer> bpieces;
    public int[] cells;

    /*########################*/
    /*----- Constructors -----*/
    /*########################*/
    public Board(String fen) {
        cells = new int[64];
        for (int cellNr = 0; cellNr < 64; cellNr++){
            cells[cellNr] = 1;
        }
        this.fen = fen;
        wTurn = false;
        hasMoved[0] = 0;
        hasMoved[1] = 0;
        hasMoved[2] = 0;
        hasMoved[3] = 0;
        hasMoved[4] = 0;
        hasMoved[5] = 0;
        lastmove = "0-1-0-0-0-0";
        lastMoves = new LinkedList<>();
        wpieces = Arrays.asList(9, 10, 11, 12, 13, 14);
        bpieces = Arrays.asList(17, 18, 19, 20, 21, 22);
    }

    // constructor for creating of a board
    public Board(Board board) {
        cells = new int[64];
        this.cells = board.getCells().clone();
        this.hasMoved = board.gethasMoved();
        this.wTurn = board.getwTurn();
        this.isWon = board.getIsWon();
        this.lastmoveEnPassant = board.lastmoveEnPassant;
        this.isDraw = board.isDraw;
        this.fen = board.getFEN();
        this.lastmove = board.lastmove;
        this.lastMoves = new LinkedList<>();
        this.lastMoves = board.getLastMoves();
        this.wpieces = board.wpieces;
        this.bpieces = board.bpieces;
    }

    /*##################################*/
    /*----- board setup functions ------*/
    /*##################################*/
    public static Board setupStartBoard() {
        String fenStart = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - w";
        Board board = new Board(fenStart);
        board.fen2Board();
        board.printBoardPiecesWPOV();
        return board;
    }

    // setup board from fen String
    public static Board setupBoard(String fen) {
        Board board = new Board(fen);
        board.fen2Board();
        board.printBoardPiecesWPOV();
        return board;
    }

    public void fen2Board() {
        char[] fenArr = this.fen.toCharArray();
        int cellNr = 64;
        int rank = 7;
        int file = 0;
        for (char character : fenArr) {
            if (file == 8) {
                file = 0;
                rank--;
            }
            cellNr--;
            switch (character) {
                case 'r' -> {
                    cells[rank * 8 + file] = 19;
                    file++;
                }
                case 'n' -> {
                    cells[rank * 8 + file] = 20;
                    file++;
                }
                case 'b' -> {
                    if (cellNr > 64 || rank < 0 || file < 0) break;
                    cells[rank * 8 + file] = 21;
                    file++;
                }
                case 'q' -> {
                    cells[rank * 8 + file] = 18;
                    file++;
                }
                case 'k' -> {
                    cells[rank * 8 + file] = 17;
                    file++;
                }
                case 'p' -> {
                    cells[rank * 8 + file] = 22;
                    file++;
                }
                case 'R' -> {
                    cells[rank * 8 + file] = 11;
                    file++;
                }
                case 'N' -> {
                    cells[rank * 8 + file] = 12;
                    file++;
                }
                case 'B' -> {
                    cells[rank * 8 + file] = 13;
                    file++;
                }
                case 'Q' -> {
                    cells[rank * 8 + file] = 10;
                    file++;
                }
                case 'K' -> {
                    cells[rank * 8 + file] = 9;
                    file++;
                }
                case 'P' -> {
                    cells[rank * 8 + file] = 14;
                    file++;
                }
                case 'w' -> wTurn = true;
                default -> {
                    if (character == '/') {
                        cellNr++;
                        break;
                    }
                    int j = Character.getNumericValue(character);
                    for (int k = 0; k < j; k++) {
                        cells[rank * 8 + file] = 0;
                        file++;
                    }
                }
            }
        }
    }

    /*###########################*/
    /*----- print functions -----*/
    /*###########################*/
    // Board printed from white players POV
    public void printBoard() {
        int i = 0;
        LinkedList<Integer> row = new LinkedList<>();
        for (int cellNr = 63; cellNr > -1; cellNr--) {
            row.addFirst(cells[cellNr]);
            i++;

            if (i % 8 == 0 && i != 0) {
                for (int file = 0; file < 8; file++) {
                    if (!row.isEmpty() && row.peekFirst() < 10) {
                        System.out.print(" ");
                    }
                    System.out.print(row.pollFirst());
                    System.out.print(" | ");
                }
                System.out.println();
            }
        }
    }

    // Board printed with figures represented as char
    public void printBoardPiecesWPOV() {
        int i = 0;
        LinkedList<Integer> row = new LinkedList<>();
        for (int cellNr = 63; cellNr > -1; cellNr--) {
            row.addFirst(cells[cellNr]);
            i++;

            if (i % 8 == 0 && i != 0) {
                for (int file = 0; file < 8; file++) {
                    if (row.isEmpty()) {
                        break;
                    }
                    int tmp = row.pollFirst();
                    switch (tmp) {
                        case 9 -> System.out.print("K");
                        case 10 -> System.out.print("Q");
                        case 11 -> System.out.print("R");
                        case 12 -> System.out.print("N");
                        case 13 -> System.out.print("B");
                        case 14 -> System.out.print("P");
                        case 17 -> System.out.print("k");
                        case 18 -> System.out.print("q");
                        case 19 -> System.out.print("r");
                        case 20 -> System.out.print("n");
                        case 21 -> System.out.print("b");
                        case 22 -> System.out.print("p");
                        case 0 -> System.out.print(".");
                    }
                    System.out.print(" | ");
                }
                System.out.println();
            }
        }
    }

    /*################################*/
    /*----- game state functions -----*/
    /*################################*/
    // white = true --> check if white king is check
    public boolean NEWisCheck(boolean white) {
        boolean isCheck = false;
        List<String> enemy_moves;
        int kingpos = -1;
        if (white) {
            for (int cellNr = 0; cellNr < 64; cellNr++) {
                if (cells[cellNr] == 9) {
                    kingpos = cellNr;
                }
            }
            enemy_moves = Move.possibleCounterMovesB(this);
        } else {
            for (int cellNr = 0; cellNr < 64; cellNr++) {
                if (cells[cellNr] == 17) {
                    kingpos = cellNr;
                }
            }
            enemy_moves = Move.possibleCounterMovesW(this);
        }
        for (String emove : enemy_moves) {
            int etarget = Integer.parseInt(emove.split("-")[1]);
            if (etarget == kingpos) {
                return true;
            }
        }
        return isCheck;
    }

    public boolean isCheck(boolean white) {
        int[] cells = this.getCells();
        int[] cellsTmp = cells.clone();
        boolean isCheck = false;
        List<String> enemy_moves;
        int kingpos = -1;
        if (white) {
            for (int cellNr = 0; cellNr < 64; cellNr++) {
                if (cellsTmp[cellNr] == 9) {
                    kingpos = cellNr;
                    break;
                }
            }
            Board boardNew = new Board(this);
            //boardNew.setCells(cellsTmp);
            boardNew.cells = cellsTmp;
            enemy_moves = Move.possibleCounterMovesB(boardNew);
            //System.out.println(enemy_moves);
        } else {
            for (int cellNr = 63; cellNr > 0; cellNr--) {
                if (cellsTmp[cellNr] == 17) {
                    kingpos = cellNr;
                    break;
                }
            }
            Board boardNew = new Board(this);
            boardNew.setCells(cellsTmp);
            enemy_moves = Move.possibleCounterMovesW(boardNew);
        }
        for (String emove : enemy_moves) {
            int etarget = Integer.parseInt(emove.split("-")[1]);
            if (etarget == kingpos) {
                return true;
            }
        }
        return isCheck;
    }
    /*
    return: true    - checkmate
            false   - no checkmate
     */
    public boolean isWon() {
        int wKing_pos = 0;
        int bKing_pos = 0;

        while (cells[wKing_pos] != 9 && wKing_pos < 63) {   // determine King pos
            wKing_pos++;
        }
        while (cells[bKing_pos] != 17 && bKing_pos < 63) {
            bKing_pos++;
        }
        List<Integer> middleSquares = Arrays.asList(27, 28, 35, 36);
        if (middleSquares.contains(wKing_pos)) {  // win by King OTH
            System.out.println("White wins by King of the hill!");
            return true;
        } else if (middleSquares.contains(bKing_pos)) { // win by King OTH
            System.out.println("Black wins by King of the hill!");
            return true;
        } else return isCheckmate();
    }

    public boolean isCheckmate() { // returns true if white or black is checkmate
        if (this.wTurn && NEWisCheck(true) && Move.possibleMovesW(this).isEmpty()) {
            System.out.println("Black wins by checkmate!");
            return true;
        } else if (!this.wTurn && NEWisCheck(false) && Move.possibleMovesB(this).isEmpty()) {
            System.out.println("White wins by checkmate!");
            return true;
        }
        return false;
    }

    /*
    return: true    - Draw -> game ist over by draw
            false   - no Draw -> game isn't over

    public boolean isDraw() {
        if (wTurn && !isCheck(true) && Move.possibleMovesW(this).isEmpty()) {
            System.out.println("Draw! - white has no moves and isn't check");
            return true;
        } else if (!wTurn && !isCheck(false) && Move.possibleMovesB(this).isEmpty()) {
            System.out.println("Draw! - black has no moves and isn't check");
            return true;
        } else return false;
    }
    */

    // converts board to fen
    public String board2fen() {
        StringBuilder fen = new StringBuilder();
        for (int cellNr = 63; cellNr > -1; cellNr--) {
            if ((cellNr + 1) % 8 == 0 && cellNr != 63) {
                fen.insert(0, "/");
            }
            switch (cells[cellNr]) {
                case 0 -> {
                    int i = 1; // number of empty cells in a row
                    while ((cellNr) % 8 != 0) {
                        if (cells[cellNr] == 0) {
                            i++;
                        } else {
                            i--;
                            cellNr++;
                            break;
                        }
                        cellNr--;
                    }
                    if ((cellNr) % 8 == 0 && this.getCellContent(cellNr) != 0) {
                        i--;
                        cellNr++;
                    }
                    fen.insert(0, i);
                }
                case 9 -> fen.insert(0, "K");
                case 10 -> fen.insert(0, "Q");
                case 11 -> fen.insert(0, "R");
                case 12 -> fen.insert(0, "N");
                case 13 -> fen.insert(0, "B");
                case 14 -> fen.insert(0, "P");
                case 17 -> fen.insert(0, "k");
                case 18 -> fen.insert(0, "q");
                case 19 -> fen.insert(0, "r");
                case 20 -> fen.insert(0, "n");
                case 21 -> fen.insert(0, "b");
                case 22 -> fen.insert(0, "p");
            }
        }
        StringBuilder fenOut = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            if (row == 0) {
                fenOut = new StringBuilder(fen.toString().split("/")[row]);
            } else {
                fenOut.insert(0, fen.toString().split("/")[row] + "/");
            }
        }
        if (wTurn) {
            fenOut.append(" - w");
        } else {
            fenOut.append(" - b");
        }
        return fenOut.toString();
    }

    // converts cell number to chess field notation
    String cell2field(int cellNr) {
        String field = "";
        int cellNum = cellNr;
        int row = 1;
        switch (cellNr % 8) {
            case 0 -> field = "a";
            case 1 -> field = "b";
            case 2 -> field = "c";
            case 3 -> field = "d";
            case 4 -> field = "e";
            case 5 -> field = "f";
            case 6 -> field = "g";
            case 7 -> field = "h";
        }
        while (cellNum > 7) {
            row++;
            cellNum = cellNum - 8;
        }
        field = field + row;

        return field;
    }

    // converts chess field notation to cell number
    public int field2cell(String field) {
        // e.g. "e4" -> "28"
        int cellNr = 0;
        char file = field.charAt(0);
        switch (file) {
            case 'b' -> cellNr++;
            case 'c' -> cellNr = 2;
            case 'd' -> cellNr = 3;
            case 'e' -> cellNr = 4;
            case 'f' -> cellNr = 5;
            case 'g' -> cellNr = 6;
            case 'h' -> cellNr = 7;
        }
        char row = field.charAt(1);
        switch (row) {
            case '2' -> cellNr = cellNr + 8;
            case '3' -> cellNr = cellNr + 16;
            case '4' -> cellNr = cellNr + 24;
            case '5' -> cellNr = cellNr + 32;
            case '6' -> cellNr = cellNr + 40;
            case '7' -> cellNr = cellNr + 48;
            case '8' -> cellNr = cellNr + 56;
        }
        return cellNr;
    }

    // converting String move to cell number notation
    // e.g. "e2-e4" -> "12-28"
    public String moveConvertIn(String move) {
        if (move.equals("0-0") || move.equals("0-0-0")) {
            return move;
        }
        String moveCell;
        String source = move.split("-")[0];
        String target = move.split("-")[1];

        moveCell = field2cell(source) + "-" + field2cell(target);
        return moveCell;
    }

    /*######################################*/
    /*----- basic game logic functions -----*/
    /*######################################*/
    // play a move

    public void makeMove(String move) {
        int source = Integer.parseInt(move.split("-")[0]);
        int target = Integer.parseInt(move.split("-")[1]);
        int sourceContent = getCellContent(source);
        int targetContent = getCellContent(target);
        List<Integer> diag = Arrays.asList(-9, -7, 7, 9);
        boolean diagonal = diag.contains(target - source);
        this.lastmoveEnPassant = false;

        switch (move) {
            case "0-0-w" -> {  // white rochade kingside
                this.setCellContent(9, 6);
                this.setCellContent(11, 5);
                this.setCellContent(0, 4);
                this.setCellContent(0, 7);
                setLastmove("0-0-w");
            }
            case "0-0-0-w" -> {  // white rochade queenside
                this.setCellContent(9, 2);
                this.setCellContent(11, 3);
                this.setCellContent(0, 4);
                this.setCellContent(0, 0);
                setLastmove("0-0-0-w");
            }
            case "0-0-b" -> {  // black rochade kingside
                this.setCellContent(17, 62);
                this.setCellContent(19, 61);
                this.setCellContent(0, 60);
                this.setCellContent(0, 63);
                setLastmove("0-0-b");
            }
            case "0-0-0-b" -> {  // black rochade queenside
                this.setCellContent(17, 58);
                this.setCellContent(19, 59);
                this.setCellContent(0, 56);
                this.setCellContent(0, 60);
                setLastmove("0-0-0-b");
            }
            default -> {  // normal moves
                setLastmove(source + "-" + target + "-" + sourceContent + "-" + targetContent);
                this.cells[target] = this.cells[source];
                this.cells[source] = 0;
            }
        }

        //enPassant special case
        if (this.cells[target] == 0 && this.cells[source] == 14 && diagonal) { //white enPassant
            System.out.println("enPassant white");
            this.lastmoveEnPassant = true;
            setLastmove(lastmove + "-" + (target - 8) + "-" + getCellContent(target - 8));
            this.cells[target - 8] = 0;
        } else if (this.cells[target] == 0 && this.cells[source] == 22 && diagonal) { //black enPassant
            System.out.println("enPassant black");
            this.lastmoveEnPassant = true;
            setLastmove(lastmove + "-" + (target + 8) + "-" + getCellContent(target + 8));
            this.cells[target + 8] = 0;
        }
        this.lastMoves.add(this.getLastMove());
        this.flipIsWhite();
    }

    // unmake the move this.lastMove
    public void unmakeLastMove() {
        setLastmove(this.lastMoves.pollLast());
        int source = Integer.parseInt(this.lastmove.split("-")[0]);
        int target = Integer.parseInt(this.lastmove.split("-")[1]);
        if (source == 0 && target == 0) { // rochade
            switch (this.lastmove) {
                case "0-0-w" -> {  //white rochade kingside
                    this.setCellContent(9, 4);
                    this.setCellContent(11, 7);
                    this.setCellContent(0, 5);
                    this.setCellContent(0, 6);
                }
                case "0-0-0-w" -> {  //white rochade queenside
                    this.setCellContent(9, 4);
                    this.setCellContent(11, 0);
                    this.setCellContent(0, 2);
                    this.setCellContent(0, 3);
                }
                case "0-0-b" -> {  //black rochade kingside
                    this.setCellContent(17, 60);
                    this.setCellContent(19, 63);
                    this.setCellContent(0, 61);
                    this.setCellContent(0, 62);
                }
                case "0-0-0-b" -> {  //black rochade queenside
                    this.setCellContent(17, 60);
                    this.setCellContent(19, 56);
                    this.setCellContent(0, 58);
                    this.setCellContent(0, 59);
                }
            }
        } else {
            int sourceContent = Integer.parseInt(this.lastmove.split("-")[2]);
            int targetContent = Integer.parseInt(this.lastmove.split("-")[3]);
            //List<Integer> diag = Arrays.asList(-9, -7, 7, 9);
            //List<Integer> pawn = Arrays.asList(14, 22);
            //boolean diagonal = diag.contains(target - source);
            //boolean isPawn = pawn.contains(sourceContent);


            //if (diagonal && targetContent == 0 && isPawn) { // check for enPassant
            if (lastmoveEnPassant) { // check for enPassant
                int capturedCellNr = Integer.parseInt(this.lastmove.split("-")[4]);
                int capturedPiece = Integer.parseInt(this.lastmove.split("-")[5]);
                cells[capturedCellNr] = capturedPiece; // restore captured piece
            } else { // unmake normal move
                this.cells[source] = sourceContent;
                this.cells[target] = targetContent;
            }
        }
        this.flipIsWhite();
    }

    /*
return: 1   - enPassant possible for white
        0   - no enPassant possible
       -1   - enPassant possible for black
    */
    public int enPassant() {
        int target = Integer.parseInt(lastmove.split("-")[1]);
        int source = Integer.parseInt(lastmove.split("-")[0]);

        // checks there was a 2 square up jump made by a pawn
        boolean check = (Math.abs(target - source) == 16 && (cells[target] == 14 || cells[target] == 22));

        if (target + 8 <= 63 && wTurn && check && cells[target + 8] == 0) {
            return 1;
        } else if (target - 8 >= 0 && !wTurn && check && cells[target - 8] == 0) {
            return -1;
        } else return 0;
    }


    /* ############################# */
    /*----- Get and Set-Methods -----*/
    /* ############################# */
    public int[] getCells() {
        return cells;
    }
    public void setCells(int[] cellsNew) {
        cells = cellsNew;
    }
    public int[] gethasMoved() {
        return this.hasMoved;
    }
    public boolean getwTurn() {
        return this.wTurn;
    }
    public void changeWTurn() {
        this.wTurn = !this.wTurn;
    }
    public String getLastMove() {
        return this.lastmove;
    }
    public boolean getIsWon() {
        return this.isWon;
    }
    public String getFEN() {
        return this.fen;
    }
    public void setLastmove(String newLastMove) {
        this.lastmove = newLastMove;
    }
    public LinkedList<String> getLastMoves() {
        return lastMoves;
    }
    public int getCellContent(int cellNr) {
        return this.cells[cellNr];
    }
    public void setCellContent(int cellContent, int cellNr) {
        this.cells[cellNr] = cellContent;
    }
    public ArrayList<Integer> getPlayerPieces(boolean isWhite) {
        ArrayList<Integer> total = new ArrayList<>();
        int pieces = 0;
        int pawns = 0;
        if (isWhite) {
            for (int cell : cells) {
                if (wpieces.contains(cell)) pieces++;
                if (cell == 14) pawns++;

            }
        } else {
            for (int cell : cells) {
                if (bpieces.contains(cell)) pieces++;
                if (cell == 22) pawns++;
            }
        }
        total.add(pieces);
        total.add(pawns);
        return total;
    }
    public boolean getIsWhite() {
        return this.wTurn;
    }
    public void flipIsWhite() { // to change players turn
        this.wTurn = !this.wTurn;
    }
}