package practiceGame;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector4f;

public class world {
	public static int BLOCK_SIZE = 32;
	RenderCollator renderer = new RenderCollator();
	int offset = 0;
	boolean HeroMoveRight =  false;
	boolean HeroMoveLeft =  false;
	boolean HeroMoveDown =  false;
	boolean HeroMoveUp =  false;
	Terrain[][] grid;
	Hero firstPlayer = new Hero(10, 15, 3, 3);
	
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
		grid = new Terrain[12][12];
		for(int i = 0; i < grid.length;i++){
			for(int j = 0; j <grid[i].length;j ++){
				grid[i][j] = new Terrain(TerrainType.DIRT, j,i,this);
				//renderer.addRender("resources/dirt.png0", world.BLOCK_SIZE *j + offset, world.BLOCK_SIZE*i + offset, BLOCK_SIZE, BLOCK_SIZE,new Vector4f(0,0,1,1));
				
			}
		}
		for(int i = 0; i < grid.length;i++){
			grid[i][0] = new Terrain(TerrainType.STONE,0,i,this);
			grid[i][grid[0].length-1] = new Terrain(TerrainType.STONE,grid[0].length-1, i, this);
		}
		for(int i = 0; i < grid[0].length;i++){
			grid[0][i] = new Terrain(TerrainType.STONE,i,0,this);
			grid[grid.length- 1][i] = new Terrain(TerrainType.STONE, i, grid.length-1, this);
		}
		grid[4][4] = new Terrain(TerrainType.STONE, 4, 4, this);
		grid[6][6] = new Terrain(TerrainType.GRASS, 6, 6, this);
		grid[6][7] = new Terrain(TerrainType.GRASS, 7, 6, this);
		grid[7][6] = new Terrain(TerrainType.GRASS, 6, 7, this);
		grid[7][7] = new Terrain(TerrainType.GRASS, 7, 7, this);
		//renderer.loadTexture("resources/paintTest.png");
	}
	public void draw(){
		//Terrain test = new Terrain(BlockType.STONE, 0, 0);
		//test.draw();
		for(int i = 0; i < grid.length;i++){
			for(int j = 0; j <grid[i].length;j ++){
				if(grid[i][j].getType() == TerrainType.DIRT || grid[i][j].getType() == TerrainType.GRASS){

					//Terrain t = new Terrain(BlockType.DIRT, world.BLOCK_SIZE *i,world.BLOCK_SIZE*j);
					renderer.addRender("resources/dirt.png0", world.BLOCK_SIZE *j + offset, world.BLOCK_SIZE*i + offset, BLOCK_SIZE, BLOCK_SIZE,new Vector4f(0,0,1,1));
				}
				if(grid[i][j].getType() == TerrainType.STONE){
					//Terrain t = new Terrain(BlockType.DIRT, world.BLOCK_SIZE *i,world.BLOCK_SIZE*j);
					renderer.addRender("resources/stone.png0", world.BLOCK_SIZE *j + offset, world.BLOCK_SIZE*i + offset, BLOCK_SIZE, BLOCK_SIZE,new Vector4f(0,0,1,1));
				}
			}
		}
		for(int i =0; i<firstPlayer.logicY;i++){
			for(int j = 0; j <grid[i].length;j ++){
				if(grid[i][j].getType() == TerrainType.GRASS){

					//Terrain t = new Terrain(BlockType.DIRT, world.BLOCK_SIZE *i,world.BLOCK_SIZE*j);
					renderer.addRender("resources/grassTexture.png0", world.BLOCK_SIZE *j + offset, world.BLOCK_SIZE*i + offset, BLOCK_SIZE, BLOCK_SIZE,new Vector4f(0,0,1,1));
				}
			}
		}
		renderer.render((int) ((firstPlayer.renderX)-boot.screenWidth/2), (int) ((firstPlayer.renderY)-boot.screenHeight/2));
		renderer.clear();
		firstPlayer.draw(renderer);
		for(int i =firstPlayer.logicY; i<grid.length;i++){
			for(int j = 0; j <grid[i].length;j ++){
				if(grid[i][j].getType() == TerrainType.GRASS){

					//Terrain t = new Terrain(BlockType.DIRT, world.BLOCK_SIZE *i,world.BLOCK_SIZE*j);
					renderer.addRender("resources/grassTexture.png0", world.BLOCK_SIZE *j + offset, world.BLOCK_SIZE*i + offset, BLOCK_SIZE, BLOCK_SIZE,new Vector4f(0,0,1,1));
				}
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
		
		firstPlayer.update(delta, grid);
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
	
}
