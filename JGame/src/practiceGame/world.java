package practiceGame;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector4f;

public class world {
	public static int BLOCK_SIZE = 32;
	public static RenderCollator renderer = new RenderCollator();
	int offset = 0;
	boolean HeroMoveRight =  false;
	boolean HeroMoveLeft =  false;
	boolean HeroMoveDown =  false;
	boolean HeroMoveUp =  false;
	Terrain[][][] grid;
	Building heroHouse = new Building(TerrainType.PLAYERHOUSE,1,7,1,this);
	Hero firstPlayer;
	
	public static world importWorld(){
		return null;
		
	}
	public static world createWorld(){
		return null;
		
	}
	public static void saveWorld(){
		
	}
	public world(){
		
		renderer.loadTexture("resources/tankRight.png");
		renderer.loadTexture("resources/dirt.png");
		renderer.loadTexture("resources/heroRight.png");
		renderer.loadTexture("resources/stone.png");
		renderer.loadTexture("resources/heroLeft.png");
		renderer.loadTexture("resources/heroFront.png");
		renderer.loadTexture("resources/heroBack.png");
		renderer.loadTexture("resources/grassTexture.png");
		renderer.loadTexture("resources/bud.png");
		renderer.loadTexture("resources/tape.png");
		renderer.loadTexture("resources/heroHouse.png");
		grid = new Terrain[12][12][3];
		for(int i = 0; i < grid.length;i++){
			for(int j = 0; j <grid[i].length;j ++){
				grid[i][j][0] = new Terrain(TerrainType.DIRT, j,i,0,this);
				//renderer.addRender("resources/dirt.png0", world.BLOCK_SIZE *j + offset, world.BLOCK_SIZE*i + offset, BLOCK_SIZE, BLOCK_SIZE,new Vector4f(0,0,1,1));
				
			}
		}
		for(int i = 0; i < grid.length;i++){
			grid[i][0][0] = new Terrain(TerrainType.STONE,0,i,0,this);
			grid[i][grid[0].length-1][0] = new Terrain(TerrainType.STONE,grid[0].length-1, i, 0, this);
		}
		for(int i = 0; i < grid[0].length;i++){
			grid[0][i][0] = new Terrain(TerrainType.STONE,i,0,0,this);
			grid[grid.length- 1][i][0] = new Terrain(TerrainType.STONE, i, grid.length-1, 0, this);
		}
		
		firstPlayer = new Hero(10, 15, 3, 3,grid);
		grid[4][4][0] = new Terrain(TerrainType.STONE, 4, 4, 1, this);
		grid[6][6][1] = new Terrain(TerrainType.GRASS, 6, 6, 1, this);
		grid[7][6][1] = new Terrain(TerrainType.GRASS, 7, 6, 1, this);
		grid[6][7][1] = new Terrain(TerrainType.GRASS, 6, 7, 1, this);
		grid[7][7][1] = new Terrain(TerrainType.GRASS, 7, 7, 1, this);
		grid[8][8][1] = new Collectable(TerrainType.BUD, 8, 8, 1, this);
		grid[2][1][1] = new Collectable(TerrainType.BUD, 2, 1, 1, this);
		grid[9][8][1] = new Collectable(TerrainType.TAPE, 9, 8, 1,this);
		grid[1][1][1] = new Collectable(TerrainType.TAPE, 1, 1, 1,this);
		buildings.add(heroHouse);
	}
	public void draw(){
		for(int i = 0; i < grid.length;i++){
			for(int j = 0; j <grid[i].length;j ++){
				if(grid[j][i][0] != null){
					grid[j][i][0].render();
					
				}
				
			}
		}
		for(int i =0; i<firstPlayer.logicY;i++){
			for(int j = 0; j <grid[i].length;j ++){
				if(grid[j][i][1] != null){
					grid[j][i][1].render();
				}
			}
		}
		renderer.render((int) ((firstPlayer.renderX)-boot.screenWidth/2), (int) ((firstPlayer.renderY)-boot.screenHeight/2));
		
		renderer.clear();
		for(Building b: buildings){
			if((b.logicY + b.logicH) <= firstPlayer.logicY){
				b.renderBuilding();
			}
		}
		renderer.render((int) ((firstPlayer.renderX)-boot.screenWidth/2), (int) ((firstPlayer.renderY)-boot.screenHeight/2));
		
		renderer.clear();
		firstPlayer.draw(renderer);
		renderer.render((int) ((firstPlayer.renderX)-boot.screenWidth/2), (int) ((firstPlayer.renderY)-boot.screenHeight/2));
		
		renderer.clear();
		for(int i =firstPlayer.logicY; i<grid.length;i++){
			for(int j = 0; j <grid[i].length;j ++){
				if(grid[j][i][1] != null){
					
					grid[j][i][1].render();
					
				}
			}
		}
		for(Building b: buildings){
			if((b.logicY + b.logicH) > firstPlayer.logicY){
				b.renderBuilding();
			}
		}
		
		renderer.render((int) ((firstPlayer.renderX)-boot.screenWidth/2), (int) ((firstPlayer.renderY)-boot.screenHeight/2));
		renderer.clear();
	}
	public void update(int delta){
		input();
		if(HeroMoveRight){
			firstPlayer.move(1, 0);
		}
		if(HeroMoveLeft){
			firstPlayer.move(-1, 0);
		}
		if(HeroMoveUp){
			firstPlayer.move(0, -1);
		}
		if(HeroMoveDown){
			firstPlayer.move(0, 1);
		}
		
		firstPlayer.update(delta);
	}
	private void input() {
		int mousex = Mouse.getX();
		int mousey =480 -  Mouse.getY() -1;
		boolean mouseClicked = Mouse.isButtonDown(0);
		if(mouseClicked){
			//grid.setAt(selector_x, selector_y, selection);
			//System.out.println(grid_x + "," + grid_y);
		}
		HeroMoveUp = Keyboard.isKeyDown(Keyboard.KEY_W);
		HeroMoveDown = Keyboard.isKeyDown(Keyboard.KEY_S);
		HeroMoveLeft = Keyboard.isKeyDown(Keyboard.KEY_A);
		HeroMoveRight = Keyboard.isKeyDown(Keyboard.KEY_D);
		
	}
	ArrayList<Building> buildings = new ArrayList<Building>();

	public void removeTerrain(int logicX, int logicY, int logicZ) {
		this.grid[logicX][logicY][logicZ] = null;
		
	}
	
}
