package com.ferruml.examples;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ferruml.system.operatingsystem.Win32_OperatingSystem;

public class OperatingSystem {
	public static void main(String[] args) {
		List<String> osList = null;
		Map<String, String> os = null;
		
		try {
			osList = Win32_OperatingSystem.getOSList();
			System.out.println(osList);
			
			for(String currentID : osList) {
				os = Win32_OperatingSystem.getOSInfo(currentID);
				for(Map.Entry<String, String> entry: os.entrySet())
					System.out.println(entry.getKey()+": "+entry.getValue());
				System.out.println();
			}
		}catch(IOException | IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			
			os = Collections.emptyMap();
			for(Map.Entry<String, String> entry: os.entrySet())
				System.out.println(entry.getKey()+": "+entry.getValue());
			System.out.println();
		}
	}
}
