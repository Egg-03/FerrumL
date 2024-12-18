package com.ferruml.manualtests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.hardware.Win32_SoundDevice;

public class Sound {
	public static void main(String[] args)
			throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		List<String> soundDeviceIDs = null;
		Map<String, String> soundDevice = null;

		soundDeviceIDs = Win32_SoundDevice.getSoundDeviceID();
		System.out.println(soundDeviceIDs);

		for (String currentTag : soundDeviceIDs) {
			soundDevice = Win32_SoundDevice.getCurrentAudioDevice(currentTag);
			for (Map.Entry<String, String> currentMemory : soundDevice.entrySet())
				System.out.println(currentMemory.getKey() + ": " + currentMemory.getValue());
			System.out.println();
		}

	}
}
