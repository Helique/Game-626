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
	
	static ArrayList<WaveData> data = new ArrayList<WaveData>();
	static ArrayList<Integer> buffer = new ArrayList<Integer>();
	static ArrayList<Integer> source = new ArrayList<Integer>();
	static ArrayList<String> Index = new ArrayList<String>();
	static int nextAvailableIndex = 0;
	
//	WaveData data1;
//	int buffer1;
//	int source1;

	public AudioEngine(){
		try {
			AL.create();
		} 
		catch (LWJGLException e) {
			e.printStackTrace();
		}	
	}
	
//	public void playOnce(String filePath) throws FileNotFoundException{
//		data1 = WaveData.create(new BufferedInputStream(new FileInputStream(filePath)));
//		buffer1 = alGenBuffers();
//        alBufferData(buffer1, data1.format, data1.data, data1.samplerate);
//		data1.dispose();
//		source1 = alGenSources();
//		alSourcei(source1, AL_BUFFER, buffer1);
//		alSourcePlay(source1);
//		AL.destroy();
//	}
	public void load(SoundClipLibrary sound){
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
		buffer.add(alGenBuffers());
		source.add(alGenSources());	
        alBufferData(buffer.get(currentIndex), data.get(currentIndex).format, data.get(currentIndex).data, data.get(currentIndex).samplerate);
		Index.add(currentIndex, filePath);
		nextAvailableIndex++;
	}
//	Replaced by AG called master, due to unnecessary repetition
//	public static void setMasterVolume(int vol){
//		
//	}
//	public static void increaseMasterVolume(AudioGroup ag, int vol){
//		
//	}
//	public static void decreaseMasterVolume(AudioGroup ag, int vol){
//		
//	}
	
	public static void setGroupVolume(AudioGroup ag, int vol){//vol in percent
		switch(ag){
			case MASTER :
				masterVolume = vol/200;//maximum is 0.5f, volume does not seem to get louder after that
				mvb = null;
				mvb = org.lwjgl.BufferUtils.createFloatBuffer(1);
				mvb.put(masterVolume);
				mvb.flip();
				alListener(AL_GAIN, mvb);
				break;
				
		}
	}
	
	public static void increaseGroupVolume(AudioGroup ag, int delta){
		
	}
	
	public static void decreaseGroupVolume(AudioGroup ag, int delta){
		
	}
	
	public void load(SoundClipLibrary[] sound){
		for(int i = 0; i < sound.length; i++){
			load(sound[i]);
		}
	}
	
	public void play(final SoundClipLibrary sound){
		new Thread(){
		    public void run(){
		      	String audioFilePath = sound.getFileLocation();
		      	int location = findAssetData(audioFilePath);
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

		AL.destroy();
	}
	
}
