package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.formatter.wmic.WMIC;

public class Win32_Processor{
	static String alias = "Win32_Processor"; //alias for Win32_Processor
	static String precedent = "DeviceID";
	
	private Win32_Processor() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static List<String> getProcessorList() throws IOException, IndexOutOfBoundsException {
		return WMIC.getID(alias, precedent);
	}
	
	public static Map<String, String> getCurrentProcessor(String currentProcessorID) throws IOException, IndexOutOfBoundsException {
		String attributes = "Name, NumberOfCores, NumberOfLogicalProcessors, ThreadCount, Manufacturer, AddressWidth, L2CacheSize, L3CacheSize, MaxClockSpeed, ExtClock, SocketDesignation, Version, Caption, Family, Stepping, VirtualizationFirmwareEnabled, ProcessorID";
		return WMIC.getWhere(alias, precedent, currentProcessorID, attributes);
	}
}

