package main;

import entities.Board;
import entities.Figure;
import entities.FigureColor;
import entities.Hand;

import gui.Window;

import javax.swing.*;
import java.awt.*;

public class Game {

    public static final int WIDTH = 512;
    public static final int HEIGHT = 512;
    public static final int FIGURE_SIZE = 64;

    private final Board board;
    private FigureColor currentTurnColor = FigureColor.White;

    public Game() {
        this.board = new Board();
        Hand hand = new Hand(board, this);
        board.spawnFigures();

        Engine engine = new Engine(board, hand, this);
        new Window(WIDTH, HEIGHT, "Checkers", engine);


    }
    public FigureColor getCurrentTurnColor() {
        return this.currentTurnColor;
    }
    public void switchTurn() {
        this.currentTurnColor = this.currentTurnColor == FigureColor.Black ? FigureColor.White : FigureColor.Black;
    }

    public void tryEndGame() {
        int whiteRemaining = 0;
        int blackRemaining = 0;
        for(Figure figure : board.getFigures()) {
            switch (figure.getFigureColor()) {
                case White -> whiteRemaining++;
                case Black -> blackRemaining++;
            }
        }
        if(whiteRemaining == 0 || blackRemaining == 0) {
            JOptionPane.showMessageDialog(null, (blackRemaining == 0 ? "White" : "Black") + " won!");
        }
    }
}
