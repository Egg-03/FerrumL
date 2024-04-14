package com.ferruml.examples;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ferruml.system.hardware.Win32_PortConnector;

public class IOPort {
	public static void main(String[] args) {
		List<String> portIDs = null;
		Map<String, String> port = null;
		
		try {
			portIDs = Win32_PortConnector.getBaseboardPortID();
			System.out.println(portIDs);
			
			for(String currentID : portIDs) {
				port = Win32_PortConnector.getBaseboardPorts(currentID);
				for(Map.Entry<String, String> entry: port.entrySet())
					System.out.println(entry.getKey()+": "+entry.getValue());
				System.out.println();
			}
		}catch(IOException | IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			
			port = Collections.emptyMap();
			for(Map.Entry<String, String> entry: port.entrySet())
				System.out.println(entry.getKey()+": "+entry.getValue());
			System.out.println();
		}
	}
}
