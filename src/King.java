import java.util.ArrayList;

public class King {
    public static ArrayList<String> allMoves(int cellNr, int[] cells, boolean isWhitePiece) {
        ArrayList<String> moves = new ArrayList<>();
        int pMovesUp = Move.pMovesUp(cellNr);
        int pMovesDown = Move.pMovesDown(cellNr);
        int pMovesLeft = Move.pMovesLeft(cellNr);
        int pMovesRight = Move.pMovesRight(cellNr);

        if (pMovesUp > 0) { // move up
            if (cells[cellNr + 8] != 0) {
                // opposite coloured piece -> can be captured
                if ((cells[cellNr + 8] > 16 && isWhitePiece) || (cells[cellNr + 8] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveUp(cellNr));
                }
            } else {
                moves.add(cellNr + "-" + Move.moveUp(cellNr));
            }
        }

        if (pMovesUp > 0 && pMovesRight > 0) { // move up right
            if (cells[cellNr + 9] != 0) {
                // opposite coloured piece -> can be captured
                if ((cells[cellNr + 9] > 16 && isWhitePiece) || (cells[cellNr + 9] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveUpRight(cellNr));
                }
            } else {
                moves.add(cellNr + "-" + Move.moveUpRight(cellNr));
            }
        }

        if (pMovesUp > 0 && pMovesLeft > 0) {   // move up left
            if (cells[cellNr + 7] != 0) {
                // opposite coloured piece -> can be captured
                if ((cells[cellNr + 7] > 16 && isWhitePiece) || (cells[cellNr + 7] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveUpLeft(cellNr));
                }
            } else {
                moves.add(cellNr + "-" + Move.moveUpLeft(cellNr));
            }
        }

        if (pMovesRight > 0) {  // move right
            if (cells[cellNr + 1] != 0) {
                // opposite coloured piece -> can be captured
                if ((cells[cellNr + 1] > 16 && isWhitePiece) || (cells[cellNr + 1] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveRight(cellNr));
                }
            } else {
                moves.add(cellNr + "-" + Move.moveRight(cellNr));
            }
        }

        if (pMovesLeft > 0) {   // move left
            if (cells[cellNr - 1] != 0) {
                // opposite coloured piece -> can be captured
                if ((cells[cellNr - 1] > 16 && isWhitePiece) || (cells[cellNr - 1] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveLeft(cellNr));
                }
            } else {
                moves.add(cellNr + "-" + Move.moveLeft(cellNr));
            }
        }

        if (pMovesDown > 0 && pMovesRight > 0) {    // move down right
            if (cells[cellNr - 7] != 0) {
                // opposite coloured piece -> can be captured
                if ((cells[cellNr - 7] > 16 && isWhitePiece) || (cells[cellNr - 7] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveDownRight(cellNr));
                }
            } else {
                moves.add(cellNr + "-" + Move.moveDownRight(cellNr));
            }
        }

        if (pMovesDown > 0) {   // move down
            if (cells[cellNr - 8] != 0) {
                // opposite coloured piece -> can be captured
                if ((cells[cellNr - 8] > 16 && isWhitePiece) || (cells[cellNr - 8] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveDown(cellNr));
                }
            } else {
                moves.add(cellNr + "-" + Move.moveDown(cellNr));
            }
        }

        if (pMovesDown > 0 && pMovesLeft > 0) { // move down left
            if (cells[cellNr - 9] != 0) {
                // opposite coloured piece -> can be captured
                if ((cells[cellNr - 9] > 16 && isWhitePiece) || (cells[cellNr - 9] < 16 && !isWhitePiece)) {
                    moves.add(cellNr + "-" + Move.moveDownLeft(cellNr));
                }
            } else {
                moves.add(cellNr + "-" + Move.moveDownLeft(cellNr));
            }
        }
        return moves;
    }
}
