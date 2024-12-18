package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

public class Win32_SoundDevice {
	private static String classname = "Win32_SoundDevice";
	private static String attributes = "ProductName, Status, Caption, PNPDeviceID, Manufacturer";
	private Win32_SoundDevice() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static List<String> getSoundDeviceID() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.getID(classname, "Caption"); //fallback to Caption since DeviceID does not work
	}
	
	public static Map<String, String> getCurrentAudioDevice(String deviceID) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.getWhere(classname, "Caption", deviceID, attributes);
	}
}
