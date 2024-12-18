package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

public class Win32_DiskDrive {
	private static String classname = "Win32_DiskDrive";
	private static String attributes = "Caption, Model, Size, FirmwareRevision, SerialNumber, Partitions, Status, InterfaceType, PNPDeviceID";
	private Win32_DiskDrive() {
		throw new IllegalStateException("Utility Class");
	}
	
	/*WARNING - Caption, instead of DeviceID or PNPDeviceID has been chosen as an identifier
	 * This is limiting because Captions can be same for two drives of the same model
	 * I couldn't get the real IDs to work with WMIC and I don't know how LinkedHashMap will react to duplicates
	 * It shouldn't be a problem since the map stores only one drive info at a time*/
	public static List<String> getDriveID() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException{
		return WMIC.getID(classname, "Index"); //DeviceID does not work, but Index does
	}
	
	public static Map<String, String> getDrive(String driveID) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException{
		return WMIC.getWhere(classname, "Index", driveID, attributes);
	}
}
