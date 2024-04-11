package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.formatter.wmic.WMIC;

public class Win32_CacheMemory {
	private static String classname = "Win32_CacheMemory";
	private Win32_CacheMemory() {
		throw new IllegalStateException("Utility Class");
	}
	public static List<String> getCacheID() throws IOException, IndexOutOfBoundsException {
		String attribute = "DeviceID";
		return WMIC.getID(classname, attribute);
	}
	
	public static Map<String, String> getCPUCache(String currentCacheID) throws IOException, IndexOutOfBoundsException {
		String attributes = "DeviceID, Purpose, InstalledSize, Associativity";
		return WMIC.getWhere(classname, "DeviceID", currentCacheID, attributes);
	}
}