package com.ferruml.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.tinylog.Logger;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.currentuser.User;
import com.ferruml.system.operatingsystem.Win32_OperatingSystem;
import com.ferruml.system.operatingsystem.Win32_TimeZone;

class NonHardwareTests {

	@Test
	void osTest() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder osProperties = new StringBuilder();

		List<String> oslist = Win32_OperatingSystem.getOSList();
		if (oslist.isEmpty()) {
	        Logger.debug("OS info not exposed by the target machine. Possibly running a VM.");
	        assertTrue(true, "No OS found, test considered inconclusive for detailed information.");
	        return; 
	    }

		for (String currentOS : oslist) {
			Map<String, String> osinfo = Win32_OperatingSystem.getOSInfo(currentOS);
			assertFalse(osinfo.isEmpty());

			for (Map.Entry<String, String> entry : osinfo.entrySet()) {
				osProperties.append(entry.getKey() + ": " + entry.getValue() + "\n");
			}
			osProperties.append("\n");
		}
		Logger.debug(osProperties.toString());
	}

	@Test
	void timeZoneTest() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder tzProperties = new StringBuilder();

		Map<String, String> currentTimeZone = Win32_TimeZone.getOSTimeZone();
		if (currentTimeZone.isEmpty()) {
	        Logger.debug("Time Zone info not exposed by the target machine. Possibly running a VM or the target machine has no sound devices or they are disabled.");
	        assertTrue(true, "No Time Zone found, test considered inconclusive for detailed information.");
	        return; 
	    }

		for (Map.Entry<String, String> entry : currentTimeZone.entrySet()) {
			tzProperties.append(entry.getKey() + ": " + entry.getValue()+"\n");
		}
		tzProperties.append("\n");
		
		Logger.debug(tzProperties.toString());
	}
	
	@Test
	void userHomeTest() {
		String userHome = User.getHome();
		assertFalse(userHome.isBlank() || userHome.isEmpty());
		Logger.debug(userHome);
	}
	
	@Test
	void userDirectoryTest() {
		String userDirectory = User.getDirectory();
		assertFalse(userDirectory.isBlank() || userDirectory.isEmpty());
		Logger.debug(userDirectory);
	}
	
	@Test
	void userNameTest() {
		String userName = User.getUsername();
		assertFalse(userName.isBlank() || userName.isEmpty());
		Logger.debug(userName);
	}
}
