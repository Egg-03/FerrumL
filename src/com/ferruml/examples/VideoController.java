package com.ferruml.examples;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ferruml.system.hardware.Win32_VideoController;

public class VideoController {
	public static void main(String[] args) {
		List<String> gpuIDs = null;
		Map<String, String> gpu = null;
		
		try {
			gpuIDs = Win32_VideoController.getGPUID();
			System.out.println(gpuIDs);
			
			for(String currentID : gpuIDs) {
				gpu = Win32_VideoController.getGPU(currentID);
				for(Map.Entry<String, String> entry: gpu.entrySet())
					System.out.println(entry.getKey()+": "+entry.getValue());
				System.out.println();
			}
		}catch(IOException | IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			
			gpu = Collections.emptyMap();
			for(Map.Entry<String, String> entry: gpu.entrySet())
				System.out.println(entry.getKey()+": "+entry.getValue());
			System.out.println();
		}
	}
}
