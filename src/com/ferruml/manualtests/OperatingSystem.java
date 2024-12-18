package com.ferruml.manualtests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.operatingsystem.Win32_OperatingSystem;

public class OperatingSystem {
	public static void main(String[] args) throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		List<String> osList = null;
		Map<String, String> os = null;
		
		
			osList = Win32_OperatingSystem.getOSList();
			System.out.println(osList);
			
			for(String currentID : osList) {
				os = Win32_OperatingSystem.getOSInfo(currentID);
				for(Map.Entry<String, String> entry: os.entrySet())
					System.out.println(entry.getKey()+": "+entry.getValue());
				System.out.println();
			}
		
	}
}
