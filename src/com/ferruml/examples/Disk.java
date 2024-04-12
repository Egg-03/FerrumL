package com.ferruml.examples;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ferruml.system.hardware.Win32_DiskDrive;

public class Disk {
	public static void main(String[] args) {
		List<String> driveIDs = null;
		Map<String, String> drives = null;
		
		try {
			driveIDs = Win32_DiskDrive.getDriveID();
			System.out.println(driveIDs);
			
			for(String currentID : driveIDs) {
				drives = Win32_DiskDrive.getDrive(currentID);
				for(Map.Entry<String, String> currentDrive: drives.entrySet())
					System.out.println(currentDrive.getKey()+": "+currentDrive.getValue());
				System.out.println();
			}
		}catch(IOException | IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			
			drives = Collections.emptyMap();
			for(Map.Entry<String, String> entry: drives.entrySet())
				System.out.println(entry.getKey()+": "+entry.getValue());
			System.out.println();
		}
	}
}
