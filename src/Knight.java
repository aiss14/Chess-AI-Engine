import java.util.ArrayList;

public class Knight {
    public static ArrayList<String> allMoves(int cellNr, int[] cells, boolean isWhitePiece) {
        ArrayList<String> moves = new ArrayList<>();
        int cellNrUpdated;
        int pMovesUp = Move.pMovesUp(cellNr); // Nr of cells up before board ends
        int pMovesDown = Move.pMovesDown(cellNr);
        int pMovesLeft = Move.pMovesLeft(cellNr);
        int pMovesRight = Move.pMovesRight(cellNr);

        String up2right1 = Integer.toString(cellNr + 17);
        String up2left1 = Integer.toString(cellNr + 15);
        String down2right1 = Integer.toString(cellNr - 15);
        String down2left1 = Integer.toString(cellNr - 17);
        String up1right2 = Integer.toString(cellNr + 10);
        String up1left2 = Integer.toString(cellNr + 6);
        String down1right2 = Integer.toString(cellNr - 6);
        String down1left2 = Integer.toString(cellNr - 10);

        ArrayList<String> jumps = new ArrayList<>();
        jumps.add(up2right1);
        jumps.add(up2left1);
        jumps.add(down2right1);
        jumps.add(down2left1);
        jumps.add(up1left2);
        jumps.add(up1right2);
        jumps.add(down1right2);
        jumps.add(down1left2);

        if (pMovesUp == 1) {
            jumps.remove(up2left1);
            jumps.remove(up2right1);
        }
        if (pMovesUp == 0) {
            jumps.remove(up2left1);
            jumps.remove(up2right1);
            jumps.remove(up1left2);
            jumps.remove(up1right2);
        }
        if (pMovesLeft == 1) {
            jumps.remove(up1left2);
            jumps.remove(down1left2);
        }
        if (pMovesLeft == 0) {
            jumps.remove(up1left2);
            jumps.remove(down1left2);
            jumps.remove(up2left1);
            jumps.remove(down2left1);
        }
        if (pMovesRight == 1) {
            jumps.remove(up1right2);
            jumps.remove(down1right2);
        }
        if (pMovesRight == 0) {
            jumps.remove(up1right2);
            jumps.remove(down1right2);
            jumps.remove(up2right1);
            jumps.remove(down2right1);
        }
        if (pMovesDown == 1) {
            jumps.remove(down2right1);
            jumps.remove(down2left1);
        }
        if (pMovesDown == 0) {
            jumps.remove(down2right1);
            jumps.remove(down2left1);
            jumps.remove(down1right2);
            jumps.remove(down1left2);
        }
        while (!jumps.isEmpty()) {
            cellNrUpdated = Integer.parseInt(jumps.get(0));
            jumps.remove(0);

            if (cells[cellNrUpdated] == 0) {
                moves.add(cellNr + "-" + cellNrUpdated);
            } else if ((cells[cellNrUpdated] > 16 && isWhitePiece) || (cells[cellNrUpdated] < 16 && !isWhitePiece)) {
                moves.add(cellNr + "-" + cellNrUpdated);
            }
        }
        return moves;
    }
}
