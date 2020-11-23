package tests;

import entities.Board;
import entities.Figure;
import entities.Hand;
import main.Game;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class BoardTests {

    @Test
    public void testOutOfBounds() {
        Board board = new Board();
        Point testPoint1 = new Point(1, 1);
        Point testPoint2 = new Point(8, 8);
        Point testPoint3 = new Point(0, 1);
        Point testPoint4 = new Point(9, 1);
        Point testPoint5 = new Point(9, 9);

        assertFalse("1,1 is out of bounds", board.isOutOfBounds(testPoint1));
        assertFalse("8,8 is out of bounds", board.isOutOfBounds(testPoint2));
        assertTrue("0,1 is not out of bounds", board.isOutOfBounds(testPoint3));
        assertTrue("9,1 is not out of bounds", board.isOutOfBounds(testPoint4));
        assertTrue("9,9 is not out of bounds", board.isOutOfBounds(testPoint5));
    }
    @Test
    public void testEmptyCells() {
        Board board = new Board();
        board.spawnFigures();

        Point testPoint1 = new Point(0,0);
        Point testPoint2 = new Point(1, 8);
        Point testPoint3 = new Point(1, 1);

        assertTrue("0,0 is not empty", board.isEmpty(testPoint1));
        assertFalse("1,8 is not empty", board.isEmpty(testPoint2));
        assertTrue("1,1 is not empty", board.isEmpty(testPoint3));
    }

    @Test
    public void testMiddlePoints() {
        Board board = new Board();

        assertEquals("middle point calculation is wrong", board.getMiddlePointBetweenTwoPoints(new Point(4, 1), new Point(2, 3)), new Point(3, 2));
        assertEquals("middle point calculation is wrong", board.getMiddlePointBetweenTwoPoints(new Point(5, 6), new Point(3, 8)), new Point(4, 7));
    }
}
