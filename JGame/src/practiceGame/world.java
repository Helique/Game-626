package practiceGame;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector4f;

public class world {
	public static int BLOCK_SIZE = 32;
	public static RenderCollator renderer = new RenderCollator();
	boolean HeroMoveRight =  false;
	boolean HeroMoveLeft =  false;
	boolean HeroMoveDown =  false;
	boolean HeroMoveUp =  false;
	Area level1 = null;
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
	public Area generateLevel1(){
		Area level = new Area(12,12,this);
		for(int i = 0; i < level.getWidth();i++){
			for(int j = 0; j <level.getHeight();j ++){
				level.addTerrain(i, j, new Terrain(TerrainType.DIRT, i,j,0,this)); 
				//renderer.addRender("resources/dirt.png0", world.BLOCK_SIZE *j + offset, world.BLOCK_SIZE*i + offset, BLOCK_SIZE, BLOCK_SIZE,new Vector4f(0,0,1,1));
				
			}
		}
		for(int i = 0; i < level.getWidth();i++){
			level.addTerrain(i, 0, new Terrain(TerrainType.STONE,i,0,0,this));
			level.addTerrain(i, level.getHeight()-1, new Terrain(TerrainType.STONE,  i,level.getHeight()-1, 0, this));
		}
		for(int i = 0; i < level.getHeight();i++){
			level.addTerrain(0, i, new Terrain(TerrainType.STONE,0,i,0,this));
			level.addTerrain(level.getWidth()-1,i, new Terrain(TerrainType.STONE,  level.getWidth()-1,i, 0, this));
			
		}
		level.addTerrain(4, 4,new Terrain(TerrainType.STONE, 4, 4, 0, this));
		
		level.addObject(6, 6,new Terrain(TerrainType.GRASS, 6, 6, 1, this));
		level.addObject(7, 6,new Terrain(TerrainType.GRASS, 7, 6, 1, this));
		level.addObject(6, 7,new Terrain(TerrainType.GRASS, 6, 7, 1, this));
		level.addObject(7, 7,new Terrain(TerrainType.GRASS, 7, 7, 1, this));
		
		
		level.addObject(1, 7, heroHouse);
		level.addObject(8, 8, new Collectable(TerrainType.BUD, 8, 8, 1, this));
		level.addObject(2, 1, new Collectable(TerrainType.BUD, 2, 1, 1, this));
		level.addObject(9, 8, new Collectable(TerrainType.TAPE, 9, 8, 1,this));
		level.addObject(1, 1, new Collectable(TerrainType.TAPE, 1, 1, 1,this));
		
		return level;
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
		
		
		level1 = generateLevel1();
		firstPlayer = new Hero(10, 15, 3, 3,level1);
		
		
	}
	public void draw(){
		level1.renderTerrain();
		
		renderer.render((int) ((firstPlayer.renderX)-boot.screenWidth/2), (int) ((firstPlayer.renderY)-boot.screenHeight/2));
		
		renderer.clear();
		level1.renderObject(0, firstPlayer.logicY);
//		for(Building b: buildings){
//			if((b.logicY + b.logicH) <= firstPlayer.logicY){
//				b.renderBuilding();
//			}
//		}
		renderer.render((int) ((firstPlayer.renderX)-boot.screenWidth/2), (int) ((firstPlayer.renderY)-boot.screenHeight/2));
		
		renderer.clear();
		firstPlayer.draw(renderer);
		renderer.render((int) ((firstPlayer.renderX)-boot.screenWidth/2), (int) ((firstPlayer.renderY)-boot.screenHeight/2));
		
		renderer.clear();
		level1.renderObject(firstPlayer.logicY,level1.height);
//		for(Building b: buildings){
//			if((b.logicY + b.logicH) > firstPlayer.logicY){
//				b.renderBuilding();
//			}
//		}
		
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
	

	public void removeTerrain(int logicX, int logicY, int logicZ) {
		level1.removeTile(logicX, logicY, logicZ);
		
	}
	
}
