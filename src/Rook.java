import java.util.ArrayList;

public class Rook {
    public static ArrayList<String> allMoves(int cellNr, int[] cells, boolean isWhitePiece) {
        ArrayList<String> moves = new ArrayList<>();
        int cellNrUpdated = cellNr;
        int pMovesUp = Move.pMovesUp(cellNr);
        int pMovesDown = Move.pMovesDown(cellNr);
        int pMovesLeft = Move.pMovesLeft(cellNr);
        int pMovesRight = Move.pMovesRight(cellNr);

        for (int i = 0; i < pMovesUp; i++) { // moves up
            // end, if piece is in the way
            if (cells[cellNrUpdated + 8] != 0) {
                // opposite coloured piece -> can be captured
                if ((cells[cellNrUpdated + 8] > 16 && isWhitePiece) || (cells[cellNrUpdated + 8] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveUp(cellNrUpdated));
                }
                break;
            }
            moves.add(cellNr + "-" + Move.moveUp(cellNrUpdated));
            cellNrUpdated += 8;
        }
        cellNrUpdated = cellNr;
        for (int i = 0; i < pMovesDown; i++) {   // moves down
            // end, if piece is in the way
            if (cells[cellNrUpdated - 8] != 0) {
                // opposite coloured piece -> can be captured
                if ((cells[cellNrUpdated - 8] > 16 && isWhitePiece) || (cells[cellNrUpdated - 8] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveDown(cellNrUpdated));
                }
                break;
            }
            moves.add(cellNr + "-" + Move.moveDown(cellNrUpdated));
            cellNrUpdated -= 8;
        }
        cellNrUpdated = cellNr;
        for (int i = 0; i < pMovesRight; i++) {  // moves right
            // end, if piece is in the way
            if (cells[cellNrUpdated + 1] != 0) {
                // opposite coloured piece -> can be captured
                if ((cells[cellNrUpdated + 1] > 16 && isWhitePiece) || (cells[cellNrUpdated + 1] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveRight(cellNrUpdated));
                }
                break;
            }
            moves.add(cellNr + "-" + Move.moveRight(cellNrUpdated));
            cellNrUpdated++;
        }
        cellNrUpdated = cellNr;
        for (int i = 0; i < pMovesLeft; i++) {  // moves right
            if (cells[cellNrUpdated - 1] != 0) {
                // opposite coloured piece -> can be captured
                if ((cells[cellNrUpdated - 1] > 16 && isWhitePiece) || (cells[cellNrUpdated - 1] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveLeft(cellNrUpdated));
                }
                // end, piece is blocking following cells
                break;
            }
            moves.add(cellNr + "-" + Move.moveLeft(cellNrUpdated));
            cellNrUpdated--;
        }
        return moves;
    }
}
