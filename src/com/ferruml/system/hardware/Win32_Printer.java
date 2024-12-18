package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

public class Win32_Printer {
	private static String classname = "Win32_Printer";
	private static String attributes = "Name, HorizontalResolution, VerticalResolution, Capabilities, CapabilityDescriptions, Default, DriverName, Hidden, Local, Network, PortName, PrintProcessor, Shared, ShareName, SpoolEnabled, WorkOffline";
	private Win32_Printer() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static List<String> getDeviceIDList() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.getID(classname, "DeviceID");
	}
	
	public static Map<String, String> getCurrentPrinter(String deviceID) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.getWhere(classname, "DeviceID", deviceID, attributes);
	}
}
