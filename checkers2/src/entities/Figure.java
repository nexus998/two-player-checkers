package entities;

import java.awt.Point;
import java.util.List;

import handlers.JumpPossibility;
import handlers.KingMovementBehaviour;
import handlers.MovementBehaviour;
import handlers.RegularMovementBehaviour;

public class Figure {
	private boolean visible;
	private Point currentPosition;
	private FigureColor figureColor;
	private FigureType figureType;
	private MovementBehaviour movementBehaviour;
	
	private JumpPossibility lastMove;

	public Figure(Point position, FigureColor color, FigureType type) {
		this.currentPosition = position;
		this.figureColor = color;
		this.figureType = type;
		this.setVisible(true);
	}
	public void setMovementBehaviour(MovementBehaviour movementBehaviour) {
		this.movementBehaviour = movementBehaviour;
	}

	public boolean getVisible() {
		return this.visible;
	}
	public void setVisible(boolean value) {
		this.visible = value;
	}

	public Point getCurrentPosition() {
		return this.currentPosition;
	}
	public FigureColor getFigureColor() { return this.figureColor; }
	public FigureType getFigureType() { return this.figureType; }
	public void setFigureType(FigureType newType) {
		this.figureType = newType;
	}
	public void moveTo(Point position) {
		JumpPossibility jumpPoss = this.movementBehaviour.getJumpPossibilityFromPosition(currentPosition, position);
		this.currentPosition = jumpPoss.getJumpPoint();
		this.lastMove = jumpPoss;
	}
	public MovementBehaviour getMovementBehaviour() {
		return this.movementBehaviour;
	}
	public JumpPossibility getLastMove() {
		return this.lastMove;
	}

	public boolean hasAnotherJump() {
		List<JumpPossibility> possibilityList = movementBehaviour.getJumpPossibilities(this.getCurrentPosition());

		for(JumpPossibility poss : possibilityList) {
			if(poss.getCapturedFigure() != null) {
				return true;
			}
		}
		return false;
	}

	public boolean isInKingPosition() {
		int kingRow = this.getFigureColor() == FigureColor.Black ? 1 : 8;
		if(this.getCurrentPosition().y == kingRow) {
			return true;
		}
		return false;
	}

	public boolean isEnemyTo(Figure figure) {
		return !this.getFigureColor().equals(figure.getFigureColor());
	}
}
