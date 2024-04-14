package com.ferruml.examples;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ferruml.system.hardware.Win32_Printer;

public class Printer {
	public static void main(String[] args) {
		List<String> printerIDs = null;
		Map<String, String> printer = null;
		
		try {
			printerIDs = Win32_Printer.getDeviceIDList();
			System.out.println(printerIDs);
			
			for(String currentID : printerIDs) {
				printer = Win32_Printer.getCurrentPrinter(currentID);
				for(Map.Entry<String, String> entry: printer.entrySet())
					System.out.println(entry.getKey()+": "+entry.getValue());
				System.out.println();
			}
		}catch(IOException | IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			
			printer = Collections.emptyMap();
			for(Map.Entry<String, String> entry: printer.entrySet())
				System.out.println(entry.getKey()+": "+entry.getValue());
			System.out.println();
		}
	}
}
