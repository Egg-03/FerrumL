package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

public class Win32_PhysicalMemory {
	private static String classname = "Win32_PhysicalMemory";
	private static String attributes = "Name, Manufacturer, Model, OtherIdentifyingInfo, PartNumber, Tag, FormFactor, BankLabel, Capacity, DataWidth, Speed, ConfiguredClockSpeed, DeviceLocator, SerialNumber";
	private Win32_PhysicalMemory() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static List<String> getTag() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException{
		return WMIC.getID(classname, "Tag");
	}
	
	public static Map<String, String> getMemory(String memoryID) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException{
		return WMIC.getWhere(classname, "Tag", memoryID, attributes);
	}
	
}
