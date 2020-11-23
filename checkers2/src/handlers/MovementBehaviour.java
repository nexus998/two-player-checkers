package handlers;

import java.awt.Point;
import java.util.List;

public interface MovementBehaviour {
	public List<JumpPossibility> getJumpPossibilities(Point figurePosition);
	public JumpPossibility getJumpPossibilityFromPosition(Point fromPosition, Point toPosition);
}
