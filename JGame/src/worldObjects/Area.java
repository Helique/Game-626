package worldObjects;

import java.util.ArrayList;

import mainBootable.world;

public class Area {
	Terrain[][][] grid;
	world parent;
	ArrayList<Building> buildings = new ArrayList<Building>();
	int height; 
	int width;
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public Area(int height, int width, world parent){
		grid = new Terrain[height][width][3];
		this.parent = parent;
		this.height = height;
		this.width = width;
		
	}
	public void addTerrain(int x, int y, Terrain t){
		grid[x][y][0] = t;
	}
	public void addObject(int x, int y, Terrain t){
		if(t.getType() == TerrainType.PLAYERHOUSE){
			Building b = (Building)t;
			buildings.add(b);
			for (int i =x ; i< (x+t.logicW); i++){
				for(int j = y+ b.roofHeight; j <(y+t.logicH); j++){
					grid[i][j][1] = t;
					grid[i][j][0] = new Terrain(TerrainType.STONE,i,j,0,parent);
					System.out.println(t.logicW + " W:H " + t.logicH);
				}
			}
			grid[b.doorX + b.logicX][b.doorY + b.logicY][0] = new Terrain(TerrainType.DIRT,b.doorX + b.logicX,b.doorY + b.logicY,0,parent);
			
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
			if(startY < (b.logicY + b.logicH) && (b.logicY + b.logicH)  <= stopY){
				b.renderBuilding();
			}
		}
	}
	public void renderOverlay(){
		
	}
	
}
