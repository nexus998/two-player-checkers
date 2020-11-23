package handlers;

import java.awt.Point;

import entities.Figure;

public class JumpPossibility {
	private Point jumpPoint;
	private Figure capturedFigure;
	
	public JumpPossibility(Point jumpPoint, Figure capturedFigure) {
		this.jumpPoint = jumpPoint;
		this.capturedFigure = capturedFigure;
	}
	
	public Point getJumpPoint() {
		return this.jumpPoint;
	}
	
	public Figure getCapturedFigure() {
		return this.capturedFigure;
	}
}
