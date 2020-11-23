package handlers;

import entities.Board;
import entities.Figure;
import entities.FigureColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RegularMovementBehaviour implements MovementBehaviour {
    FigureColor figureColor;
    Board board;

    public RegularMovementBehaviour(FigureColor figureColor, Board board) {
        this.board = board;
        this.figureColor = figureColor;
    }

    @Override
    public List<JumpPossibility> getJumpPossibilities(Point figurePosition) {
        List<JumpPossibility> answer = new ArrayList<JumpPossibility>();
        boolean capture = false;

        Point northEast = new Point(figurePosition.x+1, figurePosition.y-1);
        Point northWest = new Point(figurePosition.x-1, figurePosition.y-1);
        Point southWest = new Point(figurePosition.x-1, figurePosition.y+1);
        Point southEast = new Point(figurePosition.x+1, figurePosition.y+1);
        Point[] pointsToCheck = {northEast, northWest, southWest, southEast};
        for(Point p : pointsToCheck) {
            if(!isBackwards(figurePosition, p) && !board.isOutOfBounds(p) && board.isEmpty(p)) {
                answer.add(new JumpPossibility(p, null));
            }
        }

        Point northEastJump = new Point(figurePosition.x+2, figurePosition.y-2);
        Point northWestJump = new Point(figurePosition.x-2, figurePosition.y-2);
        Point southWestJump = new Point(figurePosition.x-2, figurePosition.y+2);
        Point southEastJump = new Point(figurePosition.x+2, figurePosition.y+2);
        Point[] pointsToCheckJump = {northEastJump, northWestJump, southWestJump, southEastJump};
        for(Point p : pointsToCheckJump) {
            if(!board.isOutOfBounds(p) && board.isEmpty(p)) {
                Figure middleFigure = board.getFigureBetweenPoints(figurePosition, p);
                if(middleFigure != null && board.areDifferentColors(figureColor, middleFigure.getFigureColor())) {
                    answer.add(new JumpPossibility(p, middleFigure));
                    capture = true;
                }

            }
        }
        //if capture is possible, remove all other options
        if(capture)
            answer.removeIf(poss -> poss.getCapturedFigure() == null);

        return answer;
    }

    private boolean isBackwards(Point oldPoint, Point newPoint) {
        int directionMultiplier = (figureColor == FigureColor.Black) ? -1 : 1;
        return (newPoint.y - oldPoint.y) * directionMultiplier < 0;
    }

    @Override
    public JumpPossibility getJumpPossibilityFromPosition(Point fromPosition, Point toPosition) {
        List<JumpPossibility> jumpPossibilities = getJumpPossibilities(fromPosition);
        for(JumpPossibility pos : jumpPossibilities) {
            if(pos.getJumpPoint().equals(toPosition)) {
                return pos;
            }
        }
        return null;
    }
}
