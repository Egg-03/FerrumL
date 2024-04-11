package com.ferruml.examples;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ferruml.system.hardware.Win32_Processor;


public class CPUTest {
	public static void main(String[] args) {
		List<String> deviceIDs = null;
		Map<String, String> currentCPU = null;
		
		try {
			deviceIDs = Win32_Processor.getProcessorList();
			System.out.println(deviceIDs);
			
			for(String currentID : deviceIDs) {
				currentCPU = Win32_Processor.getCurrentProcessor(currentID);
				for(Map.Entry<String, String> entry: currentCPU.entrySet())
					System.out.println(entry.getKey()+": "+entry.getValue());
				System.out.println();
			}
		}catch(IOException | IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			
			currentCPU = Collections.emptyMap();
			for(Map.Entry<String, String> entry: currentCPU.entrySet())
				System.out.println(entry.getKey()+": "+entry.getValue());
			System.out.println();
		}
	}
}