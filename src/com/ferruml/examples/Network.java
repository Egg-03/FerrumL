package com.ferruml.examples;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ferruml.system.network.Win32_NetworkAdapter;

public class Network {
	public static void main(String[] args) {
		List<String> networkAdapterIDs = null;
		Map<String, String> networkAdapter = null;
		
		try {
			networkAdapterIDs = Win32_NetworkAdapter.getAdapterID();
			
			for(String currentID : networkAdapterIDs) {
				networkAdapter = Win32_NetworkAdapter.getNetworkAdapters(currentID);
				for(Map.Entry<String, String> entry: networkAdapter.entrySet())
					System.out.println(entry.getKey()+": "+entry.getValue());
				System.out.println();
			}
			
		}catch(IOException | IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			
			networkAdapter = Collections.emptyMap();
			for(Map.Entry<String, String> entry: networkAdapter.entrySet())
				System.out.println(entry.getKey()+": "+entry.getValue());
			System.out.println();
		}
	}
}
