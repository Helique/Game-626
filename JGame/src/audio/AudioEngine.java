package audio;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.swing.text.MaskFormatter;

import static org.lwjgl.openal.AL10.*;

/**
 * @author Can
 *
 */
public class AudioEngine {
	static FloatBuffer  mvb = org.lwjgl.BufferUtils.createFloatBuffer(1);//master volume buffer
	static float masterVolume = 1.0f;
	
	int numberOfAudioGroups = 3;
	
	static ArrayList<WaveData> data = new ArrayList<WaveData>();
	static ArrayList<Integer> buffer = new ArrayList<Integer>();
	static ArrayList<Integer> source = new ArrayList<Integer>();
	static ArrayList<Float> volume = new ArrayList<Float>();
	static ArrayList<String> Index = new ArrayList<String>();
	static int nextAvailableIndex = 0;
	
	static ArrayList<ArrayList<Integer>> audioGroupsMatrix;
	static ArrayList<Float> oldAudioGroupVolume = new ArrayList<Float>();
	
//	WaveData data1;
//	int buffer1;
//	int source1;

	public AudioEngine(){
		//Initialize SoundGroupIndex
		audioGroupsMatrix = new ArrayList<ArrayList<Integer>>();
		audioGroupsMatrix.add(new ArrayList<Integer>());//MASTER
		audioGroupsMatrix.add(new ArrayList<Integer>());//SOUND_EFFECT
		audioGroupsMatrix.add(new ArrayList<Integer>());//BACKGROUND_MUSIC
		//Initialize groupVolumes
		for(int i = 0; i < numberOfAudioGroups; i++){
			oldAudioGroupVolume.add(1.0f);
		}
		try {
			AL.create();
		} 
		catch (LWJGLException e) {
			e.printStackTrace();
		}	
	}
	
	public void load(SoundClipLibrary sound, AudioGroup ag, int vol){
		String filePath = sound.getFileLocation();
		int currentIndex = nextAvailableIndex;
		try {
			System.out.println(filePath);
			data.add(WaveData.create(new BufferedInputStream(new FileInputStream(filePath))));
			//WaveData data = WaveData.create(new BufferedInputStream(new FileInputStream(filePath)));
		} catch (FileNotFoundException e) {
			System.out.println("----------------------------------------------");
			System.out.println("----------------------------------------------");
			System.out.println("not a valid file path: " + filePath);
			System.out.println("----------------------------------------------");
			e.printStackTrace();
			System.out.println("----------------------------------------------");
			System.out.println("----------------------------------------------");
		}
		//place it in the proper AudioGroup Index
		int agID = ag.getGroupID();
		audioGroupsMatrix.get(0).add(currentIndex);
		audioGroupsMatrix.get(agID).add(currentIndex);

		buffer.add(alGenBuffers());
		source.add(alGenSources());	
		volume.add(1.0f);
        alBufferData(buffer.get(currentIndex), data.get(currentIndex).format, data.get(currentIndex).data, data.get(currentIndex).samplerate);
		Index.add(currentIndex, filePath);
		nextAvailableIndex++;
	}
	
	public static void adjustGroupVolumeBy(AudioGroup ag, int howMuchInPercent){//negative (int  howMuchInPercent) to decrease volume
		int agID = ag.getGroupID();
		float deltaVol = howMuchInPercent/100;
		
		switch(ag){
			case MASTER :
				masterVolume += (deltaVol/2);//maximum is 0.5f, volume does not seem to get louder after that
				mvb = null;
				mvb = org.lwjgl.BufferUtils.createFloatBuffer(1);
				mvb.put(masterVolume);
				mvb.flip();
				alListener(AL_GAIN, mvb);
				break;
			case SOUND_EFFECT :
				ArrayList<Integer> soundEffects = audioGroupsMatrix.get(agID);
				for(int i = 0; i < soundEffects.size(); i++){
					float old = volume.get(soundEffects.get(i));
					volume.set(soundEffects.get(i), old + deltaVol);
				}
				break;
			case BACKGROUND_MUSIC :
				ArrayList<Integer> backgroundMusic = audioGroupsMatrix.get(agID);
				for(int i = 0; i < backgroundMusic.size(); i++){
					float old = volume.get(backgroundMusic.get(i));
					volume.set(backgroundMusic.get(i), old + deltaVol);
				}
				break;
				
		}
	}
	
	public static void setGroupVolume(AudioGroup ag, int vol){//vol in percent
		int agID = ag.getGroupID();
		float newVolume = vol/100;
		float deltaVol = (oldAudioGroupVolume.get(agID))- newVolume;
		
		switch(ag){
			case MASTER :
				masterVolume = vol/200;//maximum is 0.5f, volume does not seem to get louder after that
				mvb = null;
				mvb = org.lwjgl.BufferUtils.createFloatBuffer(1);
				mvb.put(masterVolume);
				mvb.flip();
				alListener(AL_GAIN, mvb);
				break;
			case SOUND_EFFECT :
				ArrayList<Integer> soundEffects = audioGroupsMatrix.get(agID);
				for(int i = 0; i < soundEffects.size(); i++){
					float old = volume.get(soundEffects.get(i));
					volume.set(soundEffects.get(i), old + deltaVol);
				}
				break;
			case BACKGROUND_MUSIC :
				ArrayList<Integer> backgroundMusic = audioGroupsMatrix.get(agID);
				for(int i = 0; i < backgroundMusic.size(); i++){
					float old = volume.get(backgroundMusic.get(i));
					volume.set(backgroundMusic.get(i), old + deltaVol);
				}
				break;
				
		}
	}

	public void load(Object[][] SCL_Vol, AudioGroup ag){// {{SoundClipLibrary, vol},
	for(int i = 0; i < SCL_Vol.length; i++){            //  {SoundClipLibrary, vol}}
		load((SoundClipLibrary)SCL_Vol[i][0], ag, (int)SCL_Vol[i][1]);
	}
}
	
	public void play(final SoundClipLibrary sound){
		new Thread(){
		    public void run(){
		      	String audioFilePath = sound.getFileLocation();
		      	int location = findAssetData(audioFilePath);
		      	alSourcef(source.get(location), AL_GAIN, volume.get(location));
		      	alSourcei(source.get(location), AL_BUFFER, buffer.get(location));
		      	alSourcePlay(source.get(location));
		    }
		}.start();
	}
	
	public int findAssetData(String filePath){
		return Index.indexOf(filePath);
	}
	
	public void terminate(){
		data = null;
		buffer = null;
		source = null;
		Index = null;
		volume = null;
		audioGroupsMatrix = null;
		oldAudioGroupVolume = null;
		nextAvailableIndex = 0;

		AL.destroy();
	}
	
}
