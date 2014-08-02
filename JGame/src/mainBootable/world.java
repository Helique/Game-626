package mainBootable;

import events.Event;
import events.EventType;
import events.Quest;
import events.Trigger;
import events.TriggerType;
import graphics.Overlays;
import graphics.RenderCollator;

import java.awt.event.KeyAdapter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import audio.AudioEngine;
import audio.SoundClipLibrary;
import players.Hero;
import practiceGame.ItemStack;
import practiceGame.ItemType;
import worldObjects.Area;
import worldObjects.Building;
import worldObjects.Collectable;
import worldObjects.Terrain;
import worldObjects.TerrainType;

public class world {
	public static int BLOCK_SIZE = 32;
	public static RenderCollator renderer = new RenderCollator();
	Overlays overlay = new Overlays();
	boolean HeroMoveRight =  false;
	boolean HeroMoveLeft =  false;
	boolean HeroMoveDown =  false;
	boolean HeroMoveUp =  false;
	boolean escapeKeyPressed = false;
	boolean inEvent = false;
	HashMap<String,Quest> inactiveQuests = new HashMap<String,Quest>();
	HashMap<String,Quest> activeQuests = new HashMap<String,Quest>();
	Event activeEvent = null;
	Area currentArea = null;
	Building heroHouse = new Building(TerrainType.PLAYERHOUSE,1,7,1,this, false);
	public static AudioEngine audioEngine = new AudioEngine();
	
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
		renderer.loadTexture("resources/inventoryBG.png");
		renderer.loadTexture("resources/drunkardTable.png");
		//load audio files
		audioEngine.load(SoundClipLibrary.DOOR_CREAK);
		
		currentArea = generateLevel1();
		firstPlayer = new Hero(10, 15, 3, 3,currentArea);
		
		
	}
	public void draw(){
		currentArea.renderTerrain();
		
		renderer.render((int) ((firstPlayer.getRenderX())-boot.screenWidth/2), (int) ((firstPlayer.getRenderY())-boot.screenHeight/2));
		
		renderer.clear();
		currentArea.renderObject(0, firstPlayer.getLogicY());

		renderer.render((int) ((firstPlayer.getRenderX())-boot.screenWidth/2), (int) ((firstPlayer.getRenderY())-boot.screenHeight/2));
		
		renderer.clear();
		firstPlayer.draw(renderer);
		renderer.render((int) ((firstPlayer.getRenderX())-boot.screenWidth/2), (int) ((firstPlayer.getRenderY())-boot.screenHeight/2));
		
		renderer.clear();
		currentArea.renderObject(firstPlayer.getLogicY(),currentArea.getHeight());
		
		renderer.render((int) ((firstPlayer.getRenderX())-boot.screenWidth/2), (int) ((firstPlayer.getRenderY())-boot.screenHeight/2));
		renderer.clear();
		overlay.render();
		renderer.render(0, 0);
		renderer.clear();
	}
	public void update(int delta){
		input();
		
		processLevelQuest();
		if(activeEvent != null){
			if(activeEvent.getType() == EventType.TELEPORT){
				if(activeEvent.getTeleArea().equals("meadHall")){
					currentArea = generateMeadHall();
					firstPlayer.setArea(currentArea);
					firstPlayer.setLocation(currentArea.getTelelocations()[activeEvent.getTeleportationLocation()][0], currentArea.getTelelocations()[activeEvent.getTeleportationLocation()][1]);
					
				}
			}
			activeEvent = null;
		} else {
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
		if(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && escapeKeyPressed){
			escapeKeyPressed = false;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && (!escapeKeyPressed)){
			escapeKeyPressed = true;
			overlay.toggleInventory();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_M)){
			try {
				Display.setFullscreen(false);
			} catch (LWJGLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	

	public void removeTerrain(int logicX, int logicY, int logicZ) {
		currentArea.removeTile(logicX, logicY, logicZ);
		
	}
	private void processLevelQuest()/* throws FileNotFoundException*/{
		ArrayList<Integer> removeTriggers = new ArrayList<Integer>();
		ArrayList<Integer> activateTriggers = new ArrayList<Integer>();
		for(Trigger t:currentArea.getAreaTriggers().getActivatedTriggers().values()){
			if(t.getType() == TriggerType.LOCATION){
				if(firstPlayer.getX() == t.getTriggerX() && firstPlayer.getY() == t.getTriggerY()){
					int[] triggers = t.getTriggersToActivate();
					if(triggers != null){
						for(int i: triggers){
							activateTriggers.add(i);
						}
					}
					activeEvent = currentArea.getAreaTriggers().getEvent(t.getProcedingEventNum());
					removeTriggers.add(t.getTriggerNumber());
				}
				
			}else if(t.getType() == TriggerType.ITEM_REQUIREMENT){
				if(firstPlayer.getBackPack().getItemCount(t.getItemRequirement().getItemType()) >= t.getItemRequirement().getNumber()){
					System.out.println("You now have enough of " + t.getItemRequirement().getItemType());
					int[] triggers = t.getTriggersToActivate();
					if(triggers != null){
						for(int i: triggers){
							activateTriggers.add(i);
						}
					}
					activeEvent = currentArea.getAreaTriggers().getEvent(t.getProcedingEventNum());
					removeTriggers.add(t.getTriggerNumber());
				}
			}
		}
		for(int i:removeTriggers){
			currentArea.getAreaTriggers().deactivateTrigger(i);
		}
		for(int i:activateTriggers){
			currentArea.getAreaTriggers().activateTrigger(i);
			
			audioEngine.play(SoundClipLibrary.DOOR_CREAK);
			//Event.playSoundEffect(SoundClipLibrary.DOOR_CREAK);
		}
	}
	public Area generateLevel1(){
		
		Trigger t = new Trigger(TriggerType.LOCATION,0);
		t.setLocation(heroHouse.getDoorX()+heroHouse.getLogicX(),heroHouse.getDoorY()+heroHouse.getLogicY());
		int[] t1ActivationTriggers = {1,2};
		t.setTriggersToActivate(t1ActivationTriggers);
		Trigger t2 = new Trigger(TriggerType.ITEM_REQUIREMENT, 1);
		t2.setItemRequirement(new ItemStack(ItemType.BUD, 2));
		Trigger t3 = new Trigger(TriggerType.ITEM_REQUIREMENT, 2);
		t3.setItemRequirement(new ItemStack(ItemType.TAPE, 2));
		
		ArrayList<Trigger> deactivatedTriggers = new ArrayList<Trigger>();
		ArrayList<Event> deactivatedEvents = new ArrayList<Event>();
		
		deactivatedTriggers.add(0,t);
		deactivatedTriggers.add(1, t2);
		deactivatedTriggers.add(2, t3);
		Event e = new Event(EventType.TELEPORT);
		e.setTeleLocation("meadHall", 0);
		deactivatedEvents.add(e);
		int[] initialTriggers = {0};
		Quest level1Triggers = new Quest(deactivatedTriggers, deactivatedEvents, initialTriggers);
		Area level = new Area(12,12,this);
		level.setAreaTriggers(level1Triggers);
		int[][] locations = {{3,3},{10,10}};
		level.setTelelocations(locations);
		for(int i = 0; i < level.getWidth();i++){
			for(int j = 0; j <level.getHeight();j ++){
				level.addTerrain(i, j, new Terrain(TerrainType.DIRT, i,j,0,this, true)); 
				//renderer.addRender("resources/dirt.png0", world.BLOCK_SIZE *j + offset, world.BLOCK_SIZE*i + offset, BLOCK_SIZE, BLOCK_SIZE,new Vector4f(0,0,1,1));
				
			}
		}
		for(int i = 0; i < level.getWidth();i++){
			level.addTerrain(i, 0, new Terrain(TerrainType.STONE,i,0,0,this, false));
			level.addTerrain(i, level.getHeight()-1, new Terrain(TerrainType.STONE,  i,level.getHeight()-1, 0, this, false));
		}
		for(int i = 0; i < level.getHeight();i++){
			level.addTerrain(0, i, new Terrain(TerrainType.STONE,0,i,0,this,false));
			level.addTerrain(level.getWidth()-1,i, new Terrain(TerrainType.STONE,  level.getWidth()-1,i, 0, this, false));
			
		}
		level.addTerrain(4, 4,new Terrain(TerrainType.STONE, 4, 4, 0, this, false));
		
		level.addObject(6, 6,new Terrain(TerrainType.GRASS, 6, 6, 1, this, true));
		level.addObject(7, 6,new Terrain(TerrainType.GRASS, 7, 6, 1, this, true));
		level.addObject(6, 7,new Terrain(TerrainType.GRASS, 6, 7, 1, this, true));
		level.addObject(7, 7,new Terrain(TerrainType.GRASS, 7, 7, 1, this, true));
		
		
		level.addObject(1, 7, heroHouse);
		level.addObject(8, 8, new Collectable(TerrainType.BUD, 8, 8, 1, this, true));
		level.addObject(2, 1, new Collectable(TerrainType.BUD, 2, 1, 1, this, true));
		level.addObject(9, 8, new Collectable(TerrainType.TAPE, 9, 8, 1,this, true));
		level.addObject(1, 1, new Collectable(TerrainType.TAPE, 1, 1, 1,this, true));
		
		return level;
	}
	public Area generateMeadHall(){
		Area level = new Area(25,14,this);

		
		ArrayList<Trigger> deactivatedTriggers = new ArrayList<Trigger>();
		ArrayList<Event> deactivatedEvents = new ArrayList<Event>();
		int[] initialTriggers = null;
		Quest level1Triggers = new Quest(deactivatedTriggers, deactivatedEvents, initialTriggers);
		level.setAreaTriggers(level1Triggers);
		
		int[][] telelocations = {{6,23}};
		level.setTelelocations(telelocations);
		

		
		
		for(int i = 0; i < level.getWidth();i++){
			for(int j = 0; j <level.getHeight();j ++){
				level.addTerrain(i, j, new Terrain(TerrainType.DIRT, i,j,0,this, true)); 
				//renderer.addRender("resources/dirt.png0", world.BLOCK_SIZE *j + offset, world.BLOCK_SIZE*i + offset, BLOCK_SIZE, BLOCK_SIZE,new Vector4f(0,0,1,1));
				
			}
		}
		for(int i = 0; i < level.getWidth();i++){
			level.addTerrain(i, 0, new Terrain(TerrainType.STONE,i,0,0,this, false));
			level.addTerrain(i, level.getHeight()-1, new Terrain(TerrainType.STONE,  i,level.getHeight()-1, 0, this,false));
		}
		for(int i = 0; i < level.getHeight();i++){
			level.addTerrain(0, i, new Terrain(TerrainType.STONE,0,i,0,this, false));
			level.addTerrain(level.getWidth()-1,i, new Terrain(TerrainType.STONE,  level.getWidth()-1,i, 0, this, false));
			
		}
		for (int i = 0; i < 3; i++){
			//level.addTerrain(4, 4,new Terrain(TerrainType.STONE, 4, 4, 0, this, false));
			Terrain t = new Terrain(TerrainType.DRUNKARDTABLE, 3, 5 + 5*i, 1, this, false);
			Terrain t2 = new Terrain(TerrainType.DRUNKARDTABLE, 8, 5 + 5*i, 1, this, false);
			level.addObject(3, 5 + 5*i, t);
			level.addObject(8, 5 + 5*i, t2);
		}
//		level.addObject(6, 6,new Terrain(TerrainType.GRASS, 6, 6, 1, this));
//		level.addObject(7, 6,new Terrain(TerrainType.GRASS, 7, 6, 1, this));
//		level.addObject(6, 7,new Terrain(TerrainType.GRASS, 6, 7, 1, this));
//		level.addObject(7, 7,new Terrain(TerrainType.GRASS, 7, 7, 1, this));
//		
//		
//		level.addObject(8, 8, new Collectable(TerrainType.BUD, 8, 8, 1, this));
//		level.addObject(2, 1, new Collectable(TerrainType.BUD, 2, 1, 1, this));
//		level.addObject(9, 8, new Collectable(TerrainType.TAPE, 9, 8, 1,this));
//		level.addObject(1, 1, new Collectable(TerrainType.TAPE, 1, 1, 1,this));
		
		return level;
	}
	
	
}
