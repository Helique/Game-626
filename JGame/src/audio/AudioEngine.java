package audio;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.lwjgl.openal.AL10.*;

public class AudioEngine {
	WaveData data;
	int buffer;
	int source;

	public AudioEngine(){
		try {
			AL.create();
		} 
		catch (LWJGLException e) {
			e.printStackTrace();
		}	
	}
	
	public void play(String filePath) throws FileNotFoundException{
		data = WaveData.create(new BufferedInputStream(new FileInputStream(filePath)));
		buffer = alGenBuffers();
        alBufferData(buffer, data.format, data.data, data.samplerate);
		data.dispose();
		source = alGenSources();
		alSourcei(source, AL_BUFFER, buffer);
		alSourcePlay(source);
	}
}
