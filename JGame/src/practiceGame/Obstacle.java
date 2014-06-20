package practiceGame;

public class Obstacle implements unit {
	Direction facingDirection;
	int logicX;
	int logicY;
	int Height;
	int Width;
	public Obstacle(Direction direction,int x, int y, int Height, int Width){
		facingDirection = direction;
		logicX = x;
		logicY = y;
		this.Height = Height;
		this.Width = Width;
		
	}
	public boolean walkThrough(Direction walkingDirection,unit unit){
		
		return false;
	}
	@Override
	public void draw(RenderCollator renderer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(int delta, Terrain[][] map) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLocation(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void intersects(unit unit) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
