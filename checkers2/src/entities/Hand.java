package entities;

import handlers.JumpPossibility;
import main.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Hand {
    private Figure figureInHand;
    private final Board board;
    private final Game game;

    public Hand(Board board, Game game) {
        this.board = board;
        this.game = game;
    }
    public boolean isHandEmpty() {
        return this.figureInHand == null;
    }
    public Figure getFigureInHand() {
        return this.figureInHand;
    }
    public void takeFigure(Figure figure) {
        if(this.getFigureInHand() != null) {
            return;
        }
        this.figureInHand = figure;

        figure.setVisible(false);
    }

    public void clearFigureInHand() {
        this.figureInHand = null;
    }

    public ArrayList<Point> getInvisiblePoints(Figure f, Point position) {
        List<JumpPossibility> poss = f.getMovementBehaviour().getJumpPossibilities(position);
        ArrayList<Point> possibilityPoints = new ArrayList<Point>();
        for(JumpPossibility po : poss) {
            possibilityPoints.add(po.getJumpPoint());
        }
        return possibilityPoints;
    }

    public void onClick(Point position, FigureColor currentTurnColor) {
        try {
            if (this.isHandEmpty()) {
                Figure f = board.getFigureAtPosition(position);

                if (f == null) return;
                if (!f.getFigureColor().equals(currentTurnColor)) return;

                this.takeFigure(f);

                board.setInvisibleFigurePositions(getInvisiblePoints(f, position));
            } else {
                //hand is holding a figure...
                if (this.getFigureInHand().getCurrentPosition().equals(position)) {
                    getFigureInHand().setVisible(true);
                    clearFigureInHand();

                    board.setInvisibleFigurePositions(new ArrayList<Point>());
                } else {
                    getFigureInHand().moveTo(position);
                    getFigureInHand().setVisible(true);

                    if (this.getFigureInHand().getLastMove().getCapturedFigure() != null) {
                        board.eliminateFigure(this.getFigureInHand().getLastMove().getCapturedFigure());
                        if (!board.getFigureAtPosition(position).hasAnotherJump())
                            game.switchTurn();
                    } else {
                        game.switchTurn();
                    }

                    clearFigureInHand();
                    board.setInvisibleFigurePositions(new ArrayList<Point>());
                    board.tryConvertFigureToKing(board.getFigureAtPosition(position));

                }
                game.tryEndGame();
            }
        }
        catch (Exception e) {

        }
    }
}
