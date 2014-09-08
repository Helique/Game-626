package players;

import utility.Direction;
import graphics.RenderCollator;

public interface unit {
	public void draw();
	//public void update(int delta,Terrain[][][] map);
	public void setLocation(int x, int y);
	public void setX(int x);
	public void setY(int y);
	public void intersects(unit unit);
	public boolean walkThrough(Direction walkingDirection,unit unit);
	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();
	void update(int delta);
	
	
	
}
