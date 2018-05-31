package com.treblemaker.configs;

import com.treblemaker.utils.SystemUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AppConfigs {

	@Value("${tm.api.root.dir}")
	public String APPLICATION_ROOT;

	private static int randomId = 0;

	public static void generateSessionId(){
		randomId = new Random().nextInt(1000000);
	}

	public static final String TEMP_UPLOAD_FULL_PATH_LINUX = "/tmp/tempaudio";
	public static final String TEMP_UPLOAD_FULL_PATH_WINDOWS = "c:/tempaudio";

	public static String getTempUploadFullPath(){

		if(SystemUtil.isUnix()){
			return TEMP_UPLOAD_FULL_PATH_LINUX + randomId + ".wav";
		} else if(SystemUtil.isWindows()){
			return TEMP_UPLOAD_FULL_PATH_WINDOWS + randomId + ".wav";
		}

		throw new RuntimeException("Unknown Operating System");
	}

	public final int OCTAVE = 5;
	
	public final int SONG_BPM = 80;

	public final String CHORD_BREAK = "_";

	public int[] SUPPORTED_BPM = {80};

	public String getTrainedModelsDir(){
		return APPLICATION_ROOT + "/modeldata";
	}

	public final String COMPOSITION_OUTPUT = "Compositions\\";

	public String getFFMPEG(){
		return APPLICATION_ROOT + "ffmpeg\\bin\\ffmpeg.exe";
	}

	public final String BEAT_LOOPS_RELATIVE_PATH = "Loops\\BeatLoops\\";

	public final String HARMONIC_LOOPS_RELATIVE_PATH = "Loops\\HarmonicLoops\\";

	public String getHarmonicLoopsDir(){
		return APPLICATION_ROOT + HARMONIC_LOOPS_RELATIVE_PATH;
	}

	public String getBeatShimsDir(){
		return APPLICATION_ROOT + "Loops\\BeatLoops\\BeatShims\\";
	}

	public String getMockDataDir(){
		return APPLICATION_ROOT + "CapriciousEngine\\src\\main\\java\\com\\capricious\\tests\\Mocks\\";
	}

	//public final static int TEMPO = 80;
	public final String COMP_HI_FILENAME = "comphi.mid";
	public final  String COMP_ALT_HI_FILENAME = "compalthi.mid";
	public final  String COMP_MID_FILENAME = "compmid.mid";
	public final  String COMP_ALT_MID_FILENAME = "compaltmid.mid";
	public final  String COMP_LOW_FILENAME = "complow.mid";
	public final  String COMP_ALT_LOW_FILENAME = "compaltlow.mid";

	public final  String COMP_HI_AUDIO_FILENAME = "comphi.wav";
	public final  String COMP_ALT_HI_AUDIO_FILENAME = "compalthi.wav";
	public final  String COMP_MID_AUDIO_FILENAME = "compmid.wav";
	public final  String COMP_ALT_MID_AUDIO_FILENAME = "compaltmid.wav";
	public final  String COMP_LOW_AUDIO_FILENAME = "complow.wav";
	public final  String COMP_ALT_LOW_AUDIO_FILENAME = "compaltlow.wav";

	public final  String COMP_RHYTHM_FILENAME = "comprhythm.wav";
	public final  String COMP_RHYTHM_ALT_FILENAME = "comprhythmalt.wav";
	public final  String COMP_AMBIENCE_FILENAME = "compambience.wav";
	public final  String COMP_HARMONIC_FILENAME = "compharmonic.wav";
	public final  String COMP_HARMONIC_ALT_FILENAME = "compharmonicalt.wav";

	public final  String BEAT_SHIM_FILE_NAME = "beat_shim_2_bars_%s_bpm.wav";
	public final  String HARMONIC_SHIM_FILE_NAME = "harmonicloop_shim_2_bars_%s_bpm.wav";
 }
