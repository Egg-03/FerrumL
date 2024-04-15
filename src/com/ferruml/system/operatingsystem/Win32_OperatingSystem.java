package com.ferruml.system.operatingsystem;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.formatter.wmic.WMIC;

public class Win32_OperatingSystem {
	private static String classname = "Win32_OperatingSystem";
	private static String attributes = "Caption, InstallDate, CSName, LastBootUpTime, LocalDateTime, Distributed, NumberOfUsers, Version, BootDevice, BuildNumber, BuildType, Manufacturer, OSArchitecture, MUILanguages, PortableOperatingSystem, Primary, RegisteredUser, SerialNumber, ServicePackMajorVersion, ServicePackMinorVersion, SystemDirectory, SystemDrive, WindowsDirectory";
	private Win32_OperatingSystem(){
		throw new IllegalStateException("Utility Class");
	}
	
	public static List<String> getOSList() throws IOException, IndexOutOfBoundsException {
		return WMIC.getID(classname, "Caption");
		}
	
	public static Map<String, String> getOSInfo(String OSName) throws IOException, IndexOutOfBoundsException {
		return WMIC.getWhere(classname, "Caption", OSName, attributes);
	}
}