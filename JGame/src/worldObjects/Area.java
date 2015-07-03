package worldObjects;

import java.util.ArrayList;

import events.Quest;
import graphics.RenderCollator;
import mainBootable.world;

public class Area {
	Terrain[][][] grid;
	world parent;
	ArrayList<Building> buildings = new ArrayList<Building>();
	private RenderCollator renderer = null;
	private Quest areaTriggers;
	public Quest getAreaTriggers() {
		return areaTriggers;
	}
	public void setAreaTriggers(Quest areaTriggers) {
		this.areaTriggers = areaTriggers;
	}
	int[][] teleLocations;
	public int[][] getTelelocations() {
		return teleLocations;
	}
	public void setTelelocations(int[][] telelocations) {
		this.teleLocations = telelocations;
	}
	int height; 
	int width;
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public Area(RenderCollator renderer, int height, int width, world parent){
		this.renderer = renderer;
		grid = new Terrain[width][height][3];
		//initArray(width, height, 3);
		/* David, we need to initialize the entire map array with a TerrainType 
		 * so that we don't get NullPointerException when I call getTerrainType()
		 */
		this.parent = parent;
		this.height = height;
		this.width = width;
		
	}
	public void addTerrain(int x, int y, Terrain t){
		grid[x][y][0] = t;
	}
	public void initArray(int xSize, int ySize, int zSize){
		for(int x = 0; x < xSize; x++){
			for(int y = 0; y < ySize; y++){
				for(int z = 0; z < zSize; z++){
					Terrain t = new Terrain(parent.renderer, TerrainType.AIR, x, y, z, parent, true);
					grid[x][y][z] = t;
				}
			}
		}
	}
	public void addObject(int x, int y, Terrain t){
		if(t.getType() == TerrainType.PLAYERHOUSE){
			Building b = (Building)t;
			buildings.add(b);
			grid[x][y/*+b.roofHeight*/][1] = t;
			for (int i =x ; i< (x+t.logicW); i++){
				for(int j = y+ b.roofHeight; j <(y+t.logicH); j++){
					//grid[i][j][1] = t;
					grid[i][j][0] = new Terrain(renderer,TerrainType.STONE,i,j,0,parent, false);
					System.out.println(t.logicW + " W:H " + t.logicH);
				}
			}
			grid[b.getDoorX() + b.getLogicX()][b.getDoorY() + b.getLogicY()][0] = new Terrain(renderer,TerrainType.DIRT,b.getDoorX() + b.getLogicX(),b.getDoorY() + b.getLogicY(),0,parent, true);
		} else if(t.getType() == TerrainType.DRUNKARDTABLE){
			for (int i =x ; i< (x+t.logicW); i++){
				for(int j = y; j <(y+t.logicH); j++){
					grid[i][j][1] = t;
					grid[i][j][0] = new Terrain(renderer,TerrainType.DIRT,i,j,0,parent, false);
					System.out.println(t.logicW + " W:H " + t.logicH);
				}
			}
		} else {
			grid[x][y][1] = t;
		}
	}
	public void addOverlay(int x, int y, Terrain t){
		
	}
	public void removeTerrain(int x, int y){
		grid[x][y][0] = null;
	}
	public void removeObject(int x, int y){
		grid[x][y][1] = null;
	}
	public void removeOverlay(int x, int y){
		grid[x][y][2] = null;
	}
	public Terrain getTerrain(int x, int y, int z){
		return grid[x][y][z];
	}
	public void renderTerrain(){
		for(int i =0; i<height; i++){
			for(int j = 0; j <width; j++){
				if(grid[j][i][0] != null){
					
					grid[j][i][0].render();
					
				}
			}
		}
		
		
	}
	public void removeTile(int x, int y, int z){
		grid[x][y][z] = null;
	}
	public void renderObject(int startY, int stopY){
		for(int i =startY; i<stopY; i++){
			for(int j = 0; j <width; j++){
				if(grid[j][i][1] != null){
					
					grid[j][i][1].render();
					
				}
			}
		}
		for(Building b: buildings){
			if(startY < (b.getLogicY() + b.logicH) && (b.getLogicY() + b.logicH)  <= stopY){
				b.renderBuilding();
			}
		}
	}
	public void renderOverlay(){
		
	}
	
}
