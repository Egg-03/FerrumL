package com.ferruml.examples;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import com.ferruml.system.hardware.Win32_BIOS;

public class BIOS {
	public static void main(String[] args) {
		Map<String, String> bios = null;
		
		try {
			bios = Win32_BIOS.getPrimaryBIOS();
			for(Map.Entry<String, String> entry: bios.entrySet())
				System.out.println(entry.getKey()+": "+entry.getValue());
			
		}catch(IOException | IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			bios = Collections.emptyMap();
			for(Map.Entry<String, String> entry: bios.entrySet())
				System.out.println(entry.getKey()+": "+entry.getValue());
		}
	}
}
