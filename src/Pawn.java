import java.util.ArrayList;

public class Pawn {


    public static ArrayList<String> allMoves(int cellNr, Board board, boolean isWhitePiece) {
        int[] cells = board.getCells();
        ArrayList<String> moves = new ArrayList<>();
        int pMovesLeft = Move.pMovesLeft(cellNr);
        int pMovesRight = Move.pMovesRight(cellNr);
        int enPassant = board.enPassant();
        int lasttarget = Integer.parseInt(board.getLastMove().split("-")[1]);

        if (isWhitePiece) { // white Pawn
            if (cellNr > 7 && cellNr < 16 && cells[cellNr + 16] == 0 && cells[cellNr + 8] == 0) {    // in second row , possibility of 2 square advance
                moves.add(cellNr + "-" + Move.moveUp(cellNr + 8));
            }
            if (cellNr + 8 < 64 && cells[cellNr + 8] == 0) {
                moves.add(cellNr + "-" + Move.moveUp(cellNr));
            }
            if (cellNr + 7 < 64 && cells[cellNr + 7] > 16 && cells[cellNr + 7] != 0 && pMovesLeft > 0) {  // attack left
                moves.add(cellNr + "-" + Move.moveUpLeft(cellNr));
            }
            if (cellNr + 9 < 64) {
                if (cells[cellNr + 9] > 16 && cells[cellNr + 9] != 0 && pMovesRight > 0) { // attack right
                    moves.add(cellNr + "-" + Move.moveUpRight(cellNr));
                }
            }
            if (enPassant == 1 && cells[cellNr + 1] == 22 && Integer.parseInt(Move.moveUpRight(cellNr)) == lasttarget + 8)
                moves.add(cellNr + "-" + Move.moveUpRight(cellNr));
            if (enPassant == 1 && cells[cellNr - 1] == 22 && Integer.parseInt(Move.moveUpLeft(cellNr)) == lasttarget + 8)
                moves.add(cellNr + "-" + Move.moveUpLeft(cellNr));
        } else { // black Pawn
            if (cellNr > 47 && cellNr < 56 && cells[cellNr - 16] == 0 && cells[cellNr - 8] == 0) {   // in second row , possibility of 2 square advance
                moves.add(cellNr + "-" + Move.moveDown(cellNr - 8));
            }
            if (cellNr - 8 > 0) {
                if (cells[cellNr - 8] == 0) {
                    moves.add(cellNr + "-" + Move.moveDown(cellNr));
                }
            }

            if (cellNr - 9 > 0) {
                if (cells[cellNr - 9] < 16 && cells[cellNr - 9] != 0 && pMovesLeft > 0) {  // attack left (white players POV)
                    moves.add(cellNr + "-" + Move.moveDownLeft(cellNr));
                }
            }
            if (cellNr - 7 > 0) {
                if (cells[cellNr - 7] < 16 && cells[cellNr - 7] != 0 && pMovesRight > 0) { // attack right (white players POV)
                    moves.add(cellNr + "-" + Move.moveDownRight(cellNr));
                }
            }
            if (enPassant == -1 && cells[cellNr + 1] == 14 && Integer.parseInt(Move.moveDownRight(cellNr)) == lasttarget - 8)
                moves.add(cellNr + "-" + Move.moveDownRight(cellNr));
            if (enPassant == -1 && cells[cellNr - 1] == 14 && Integer.parseInt(Move.moveDownLeft(cellNr)) == lasttarget - 8)
                moves.add(cellNr + "-" + Move.moveDownLeft(cellNr));
        }
        return moves;
    }
    //TODO when pawn reaches enemy endrow change it with another piece of choice
}