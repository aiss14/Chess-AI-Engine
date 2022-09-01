import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {
    @Test
    void kingMoves1W(){
        Board board = Board.setupBoard("8/8/8/4K3/8/8/8/8 - w");
        ArrayList<String> expectedMoves = new ArrayList<String>();
        expectedMoves.add("36-27");
        expectedMoves.add("36-28");
        expectedMoves.add("36-29");
        expectedMoves.add("36-35");
        expectedMoves.add("36-37");
        expectedMoves.add("36-43");
        expectedMoves.add("36-44");
        expectedMoves.add("36-45");
        ArrayList<String> pMoves = King.allMoves(36, board.getCells(), true);
        System.out.println("pMoves (" + pMoves.size() + "): " + pMoves);
        assertEquals(expectedMoves.size(), pMoves.size());
        for (String move : pMoves){
            assertTrue(expectedMoves.contains(move));
        }
    }
    @Test
    void kingMoves2W(){
        Board board = Board.setupBoard("8/8/8/4KR2/8/8/8/8 - w");
        ArrayList<String> expectedMoves = new ArrayList<String>();
        expectedMoves.add("36-27");
        expectedMoves.add("36-28");
        expectedMoves.add("36-29");
        expectedMoves.add("36-35");
        expectedMoves.add("36-43");
        expectedMoves.add("36-44");
        expectedMoves.add("36-45");
        ArrayList<String> pMoves = King.allMoves(36, board.getCells(), true);
        System.out.println("pMoves (" + pMoves.size() + "): " + pMoves);
        assertEquals(expectedMoves.size(), pMoves.size());
        for (String move : pMoves){
            assertTrue(expectedMoves.contains(move));
        }
    }
    @Test
    void kingMoves3W(){
        Board board = Board.setupBoard("8/8/PPPPPPPP/3PKR2/PPPPPPPP/8/8/8 - w");
        ArrayList<String> expectedMoves = new ArrayList<String>();
        ArrayList<String> pMoves = King.allMoves(36, board.getCells(), true);
        System.out.println("pMoves (" + pMoves.size() + "): " + pMoves);
        assertEquals(expectedMoves.size(), pMoves.size());
        for (String move : pMoves){
            assertTrue(expectedMoves.contains(move));
        }
    }
    @Test
    void kingMoves4W(){
        Board board = Board.setupBoard("8/8/8/8/8/8/8/7K - w");
        ArrayList<String> expectedMoves = new ArrayList<String>();
        expectedMoves.add("7-15");
        expectedMoves.add("7-14");
        expectedMoves.add("7-6");
        ArrayList<String> pMoves = King.allMoves(7, board.getCells(), true);
        System.out.println("pMoves (" + pMoves.size() + "): " + pMoves);
        assertEquals(expectedMoves.size(), pMoves.size());
        for (String move : pMoves){
            assertTrue(expectedMoves.contains(move));
        }
    }
    @Test
    void dkingCaptureMoves1W(){
        Board board = Board.setupBoard("8/8/3pQp2/3PKr2/8/8/8/8 - w");
        ArrayList<String> expectedMoves = new ArrayList<String>();
        expectedMoves.add("36-27");
        expectedMoves.add("36-28");
        expectedMoves.add("36-29");
        expectedMoves.add("36-37");
        expectedMoves.add("36-43");
        expectedMoves.add("36-45");
        ArrayList<String> pMoves = King.allMoves(36, board.getCells(), true);
        System.out.println("pMoves (" + pMoves.size() + "): " + pMoves);
        assertEquals(expectedMoves.size(), pMoves.size());
        for (String move : pMoves){
            assertTrue(expectedMoves.contains(move));
        }
    }
    @Test
    void kingMoves1B(){
        Board board = Board.setupBoard("8/8/8/4k3/8/8/8/8 - b");
        ArrayList<String> expectedMoves = new ArrayList<String>();
        expectedMoves.add("36-27");
        expectedMoves.add("36-28");
        expectedMoves.add("36-29");
        expectedMoves.add("36-35");
        expectedMoves.add("36-37");
        expectedMoves.add("36-43");
        expectedMoves.add("36-44");
        expectedMoves.add("36-45");
        ArrayList<String> pMoves = King.allMoves(36, board.getCells(), false);
        System.out.println("pMoves (" + pMoves.size() + "): " + pMoves);
        assertEquals(expectedMoves.size(), pMoves.size());
        for (String move : pMoves){
            assertTrue(expectedMoves.contains(move));
        }
    }
    @Test
    void kingMoves2B(){
        Board board = Board.setupBoard("8/8/8/4kr2/8/8/8/8 - b");
        ArrayList<String> expectedMoves = new ArrayList<String>();
        expectedMoves.add("36-27");
        expectedMoves.add("36-28");
        expectedMoves.add("36-29");
        expectedMoves.add("36-35");
        expectedMoves.add("36-43");
        expectedMoves.add("36-44");
        expectedMoves.add("36-45");
        ArrayList<String> pMoves = King.allMoves(36, board.getCells(), false);
        System.out.println("pMoves (" + pMoves.size() + "): " + pMoves);
        assertEquals(expectedMoves.size(), pMoves.size());
        for (String move : pMoves){
            assertTrue(expectedMoves.contains(move));
        }
    }
    @Test
    void kingMoves3B(){
        Board board = Board.setupBoard("8/8/pppppppp/3pkr2/pppppppp/8/8/8 - b");
        ArrayList<String> expectedMoves = new ArrayList<String>();
        ArrayList<String> pMoves = King.allMoves(36, board.getCells(), false);
        System.out.println("pMoves (" + pMoves.size() + "): " + pMoves);
        assertEquals(expectedMoves.size(), pMoves.size());
        for (String move : pMoves){
            assertTrue(expectedMoves.contains(move));
        }
    }
    @Test
    void kingMoves4B(){
        Board board = Board.setupBoard("8/8/8/8/8/8/8/7k - b");
        ArrayList<String> expectedMoves = new ArrayList<String>();
        expectedMoves.add("7-15");
        expectedMoves.add("7-14");
        expectedMoves.add("7-6");
        ArrayList<String> pMoves = King.allMoves(7, board.getCells(), false);
        System.out.println("pMoves (" + pMoves.size() + "): " + pMoves);
        assertEquals(expectedMoves.size(), pMoves.size());
        for (String move : pMoves){
            assertTrue(expectedMoves.contains(move));
        }
    }
    @Test
    void kingCaptureMoves1B(){
        Board board = Board.setupBoard("8/8/3PqP2/3pkR2/8/8/8/8 - b");
        ArrayList<String> expectedMoves = new ArrayList<String>();
        expectedMoves.add("36-27");
        expectedMoves.add("36-28");
        expectedMoves.add("36-29");
        expectedMoves.add("36-37");
        expectedMoves.add("36-43");
        expectedMoves.add("36-45");
        ArrayList<String> pMoves = King.allMoves(36, board.getCells(), false);
        System.out.println("pMoves (" + pMoves.size() + "): " + pMoves);
        assertEquals(expectedMoves.size(), pMoves.size());
        for (String move : pMoves){
            assertTrue(expectedMoves.contains(move));
        }
    }
}