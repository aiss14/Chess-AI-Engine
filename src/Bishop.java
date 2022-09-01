import java.util.ArrayList;

public class Bishop {

    public static ArrayList<String> allMoves(int cellNr, int[] cells, boolean isWhitePiece) {
        ArrayList<String> moves = new ArrayList<>();
        int cellNrUpdated = cellNr;
        int pMovesUp = Move.pMovesUp(cellNr);
        int pMovesDown = Move.pMovesDown(cellNr);
        int pMovesLeft = Move.pMovesLeft(cellNr);
        int pMovesRight = Move.pMovesRight(cellNr);

        // moves up right
        for (int i = 0; i < Integer.min(pMovesUp, pMovesRight); i++) {
            if (cells[cellNrUpdated + 9] != 0) {
                if ((cells[cellNrUpdated + 9] > 16 && isWhitePiece) || (cells[cellNrUpdated + 9] < 16 && !isWhitePiece)) { // Abfrgae nach enemy piece
                    moves.add(cellNr + "-" + Move.moveUpRight(cellNrUpdated));
                }
                break;
            }
            moves.add(cellNr + "-" + Move.moveUpRight(cellNrUpdated));
            cellNrUpdated += 9;
        }
        // moves up left
        cellNrUpdated = cellNr;
        for (int i = 0; i < Integer.min(pMovesUp, pMovesLeft); i++) {
            if (cells[cellNrUpdated + 7] != 0) {
                if ((cells[cellNrUpdated + 7] > 16 && isWhitePiece) || (cells[cellNrUpdated + 7] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveUpLeft(cellNrUpdated));
                }
                break;
            }
            moves.add(cellNr + "-" + Move.moveUpLeft(cellNrUpdated));
            cellNrUpdated += 7;
        }
        // moves down right
        cellNrUpdated = cellNr;
        for (int i = 0; i < Integer.min(pMovesDown, pMovesRight); i++) {
            if (cells[cellNrUpdated - 7] != 0) {
                if ((cells[cellNrUpdated - 7] > 16 && isWhitePiece) || (cells[cellNrUpdated - 7] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveDownRight(cellNrUpdated));
                }
                break;
            }
            moves.add(cellNr + "-" + Move.moveDownRight(cellNrUpdated));
            cellNrUpdated -= 7;
        }
        // moves down left
        cellNrUpdated = cellNr;
        for (int i = 0; i < Integer.min(pMovesDown, pMovesLeft); i++) {
            if (cells[cellNrUpdated - 9] != 0) {
                if ((cells[cellNrUpdated - 9] > 16 && isWhitePiece) || (cells[cellNrUpdated - 9] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveDownLeft(cellNrUpdated));
                }
                break;
            }
            moves.add(cellNr + "-" + Move.moveDownLeft(cellNrUpdated));
            cellNrUpdated -= 9;
        }
        return moves;
    }
}
