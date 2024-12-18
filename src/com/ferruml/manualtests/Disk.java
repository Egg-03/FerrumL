package com.ferruml.manualtests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.hardware.Win32_DiskDrive;

public class Disk {
	public static void main(String[] args)
			throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		List<String> driveIDs = null;
		Map<String, String> drives = null;

		driveIDs = Win32_DiskDrive.getDriveID();
		System.out.println(driveIDs);

		for (String currentID : driveIDs) {
			drives = Win32_DiskDrive.getDrive(currentID);
			for (Map.Entry<String, String> currentDrive : drives.entrySet())
				System.out.println(currentDrive.getKey() + ": " + currentDrive.getValue());
			System.out.println();
		}

	}
}
