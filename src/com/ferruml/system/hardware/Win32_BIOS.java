package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.Map;

import com.ferruml.formatter.wmic.WMIC;

public class Win32_BIOS {
	private static String classname = "Win32_BIOS";
	private static String attributes = "Name, Caption, Manufacturer, ReleaseDate, SMBIOSPResent, Status, Version, CurrentLanguage, SMBIOSBIOSVersion";
	private Win32_BIOS() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static Map<String, String> getPrimaryBIOS() throws IOException, IndexOutOfBoundsException {
		return WMIC.getWhere(classname, "PrimaryBIOS", "true", attributes);
	}
}
