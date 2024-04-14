package com.ferruml.examples;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ferruml.system.hardware.Win32_SoundDevice;

public class Sound {
	public static void main(String[] args) {
		List<String> soundDeviceIDs = null;
		Map<String, String> soundDevice = null;
		
		try {
			soundDeviceIDs = Win32_SoundDevice.getSoundDeviceID();
			System.out.println(soundDeviceIDs);
			
			for(String currentTag : soundDeviceIDs) {
				soundDevice = Win32_SoundDevice.getCurrentAudioDevice(currentTag);
				for(Map.Entry<String, String> currentMemory: soundDevice.entrySet())
					System.out.println(currentMemory.getKey()+": "+currentMemory.getValue());
				System.out.println();
			}
		}catch(IOException | IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			
			soundDevice = Collections.emptyMap();
			for(Map.Entry<String, String> currentMemory: soundDevice.entrySet())
				System.out.println(currentMemory.getKey()+": "+currentMemory.getValue());
			System.out.println();
		}
	}
}
