package com.ferruml.examples;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ferruml.system.hardware.Win32_PhysicalMemory;

public class RAM {
	public static void main(String[] args) {
		List<String> tags = null;
		Map<String, String> memory = null;
		
		try {
			tags = Win32_PhysicalMemory.getTag();
			System.out.println(tags);
			
			for(String currentTag : tags) {
				memory = Win32_PhysicalMemory.getMemory(currentTag);
				for(Map.Entry<String, String> currentMemory: memory.entrySet())
					System.out.println(currentMemory.getKey()+": "+currentMemory.getValue());
				System.out.println();
			}
		}catch(IOException | IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			
			memory = Collections.emptyMap();
			for(Map.Entry<String, String> currentMemory: memory.entrySet())
				System.out.println(currentMemory.getKey()+": "+currentMemory.getValue());
			System.out.println();
		}
	}
}
