package entities;

import handlers.KingMovementBehaviour;
import handlers.RegularMovementBehaviour;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    private ArrayList<Figure> figures = new ArrayList<Figure>();
    private ArrayList<Point> invisibleFigurePositions = new ArrayList<Point>();

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    private final Point[] blackSpawnPoints = {
            new Point(1,8), new Point(3,8), new Point(5, 8), new Point(7, 8),
            new Point(2,7), new Point(4,7), new Point(6, 7), new Point(8, 7),
            new Point(1,6), new Point(3,6), new Point(5, 6), new Point(7, 6)
    };

    private final Point[] whiteSpawnPoints = {
            new Point(2,1), new Point(4,1), new Point(6, 1), new Point(8, 1),
            new Point(1,2), new Point(3,2), new Point(5, 2), new Point(7, 2),
            new Point(2,3), new Point(4,3), new Point(6, 3), new Point(8, 3)
    };

    private void spawnFigure(FigureColor figureColor, Point position) {
        Figure newFigure = new Figure(position, figureColor, FigureType.Regular);
        newFigure.setMovementBehaviour(new RegularMovementBehaviour(figureColor, this));
        figures.add(newFigure);
    }

    public ArrayList<Point> getInvisibleFigurePositions() {
        return this.invisibleFigurePositions;
    }
    public void setInvisibleFigurePositions(ArrayList<Point> invisibleFigurePositions) {
        this.invisibleFigurePositions = invisibleFigurePositions;
    }

    public void eliminateFigure(Figure figureToEliminate) {
        figures.remove(figureToEliminate);
    }

    public Figure getFigureAtPosition(Point position) {
        for(Figure f : getFigures()) {
            if(f.getCurrentPosition().equals(position)) {
                return f;
            }
        }
        return null;
    }

    public void convertFigureToKing(Figure figure) {
        figure.setMovementBehaviour(new KingMovementBehaviour(figure.getFigureColor(), this));
        figure.setFigureType(FigureType.King);
    }

    public void tryConvertFigureToKing(Figure figure) {
        if(figure.isInKingPosition()) {
            this.convertFigureToKing(figure);
        }
    }

    public void spawnFigures() {
        for(Point black : blackSpawnPoints) {
            spawnFigure(FigureColor.Black, black);
        }
        for(Point white : whiteSpawnPoints) {
            spawnFigure(FigureColor.White, white);
        }
    }
    public boolean isOutOfBounds(Point point) {
        return point.x > 8 || point.x < 1 || point.y > 8 || point.y < 1;
    }
    public boolean isEmpty(Point point) {
        return getFigureAtPosition(point) == null;
    }

    public Point getMiddlePointBetweenTwoPoints(Point oldPoint, Point newPoint) {
        int xMid = (newPoint.x + oldPoint.x) / 2;
        int yMid = (newPoint.y + oldPoint.y) / 2;
        Point middlePoint = new Point(xMid, yMid);
        return middlePoint;
    }

    public Figure getFigureBetweenPoints(Point oldPoint, Point newPoint) {
        Point middlePoint = getMiddlePointBetweenTwoPoints(oldPoint, newPoint);
        return getFigureAtPosition(middlePoint);
    }
    public boolean areDifferentColors(FigureColor color1, FigureColor color2) {
        return !color1.equals(color2);
    }

}
