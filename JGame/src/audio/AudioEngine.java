package audio;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.lwjgl.openal.AL10.*;

public class AudioEngine {
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
