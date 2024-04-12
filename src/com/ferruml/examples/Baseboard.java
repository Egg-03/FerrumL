package com.ferruml.examples;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import com.ferruml.system.hardware.Win32_Baseboard;

public class Baseboard {
	public static void main(String[] args) {
		Map<String, String> baseboard = null;
		
		try {
			baseboard = Win32_Baseboard.getMotherboard();	
			for(Map.Entry<String, String> entry: baseboard.entrySet())
				System.out.println(entry.getKey()+": "+entry.getValue());
			
		}catch(IOException | IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			baseboard = Collections.emptyMap();
			for(Map.Entry<String, String> entry: baseboard.entrySet())
				System.out.println(entry.getKey()+": "+entry.getValue());
		}
	}
}
