package com.ferruml.system.network;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

public class Win32_NetworkAdapter {
	private static String classname = "Win32_NetworkAdapter";
	private static String attributes = "Name, Description, PNPDeviceID, MACAddress, Installed, NetEnabled, NetConnectionID, PhysicalAdapter, TimeOfLastReset";
	private Win32_NetworkAdapter() {
		throw new IllegalStateException("Utility Class");
	}
	
	//will retrieve all the adapter IDs which are currently active and providing Internet
	public static List<String> getAdapterID() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
			return WMIC.getIDWhere(classname, "NetEnabled", "true", "DeviceID");
		}
	
	//will return a hashmap of the following properties as a key and their corresponding values:
	//Name, Description, PNPDeviceID, MACAddress, Installed, NetEnabled, NetConnectionID, PhysicalAdapter, TimeOfLastReset, AdapterIP
	public static Map<String, String> getNetworkAdapters(String deviceID) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.getWhere(classname, "deviceID", deviceID, attributes);
	}
}
