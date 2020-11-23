package handlers;

import entities.Board;
import entities.Figure;
import entities.FigureColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KingMovementBehaviour implements MovementBehaviour {
    private FigureColor figureColor;
    private Board board;

    public KingMovementBehaviour(FigureColor figureColor, Board board) {
        this.figureColor = figureColor;
        this.board = board;
    }

    private boolean isCaptureAvailable(List<JumpPossibility> jumpPossibilities) {
        for(JumpPossibility poss : jumpPossibilities) {
            if(poss.getCapturedFigure() != null) return true;
        }
        return false;
    }

    private List<JumpPossibility> getOnlyCapturePossibilities(List<JumpPossibility> jumpPossibilities) {
        List<JumpPossibility> answer = new ArrayList<JumpPossibility>(jumpPossibilities);
        answer.removeIf(poss -> poss.getCapturedFigure() == null);
        return answer;
    }

    @Override
    public List<JumpPossibility> getJumpPossibilities(Point figurePosition) {
        List<JumpPossibility> answer = new ArrayList<JumpPossibility>();

        List<JumpPossibility> northEastList = checkCells(new ArrayList<JumpPossibility>(), new Point(figurePosition.x+1, figurePosition.y-1), null, 1, -1);
        List<JumpPossibility> northWestList = checkCells(new ArrayList<JumpPossibility>(), new Point(figurePosition.x-1, figurePosition.y-1), null, -1, -1);
        List<JumpPossibility> southEastList = checkCells(new ArrayList<JumpPossibility>(), new Point(figurePosition.x+1, figurePosition.y+1), null, 1, 1);
        List<JumpPossibility> southWestList = checkCells(new ArrayList<JumpPossibility>(), new Point(figurePosition.x-1, figurePosition.y+1), null, -1, 1);

        answer.addAll(northEastList);
        answer.addAll(northWestList);
        answer.addAll(southEastList);
        answer.addAll(southWestList);

        if(isCaptureAvailable(answer)) {
            answer = getOnlyCapturePossibilities(answer);
        }
        return answer;
    }

    private List<JumpPossibility> checkCells(List<JumpPossibility> list, Point position, Point capturedPosition, int xMulti, int yMulti) {
        if(board.isOutOfBounds(position) || (!board.isEmpty(position) && !board.areDifferentColors(board.getFigureAtPosition(position).getFigureColor(), figureColor))) return list;
        if(board.isEmpty(position)) {
            list.add(new JumpPossibility(position, capturedPosition != null ? board.getFigureAtPosition(capturedPosition) : null));
            return capturedPosition == null ? checkCells(list, new Point(position.x + xMulti, position.y + yMulti), null, xMulti, yMulti) : list;
        }
        else {
            return capturedPosition != null ? list : checkCells(list, new Point(position.x + xMulti, position.y + yMulti), position, xMulti, yMulti);
        }
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
