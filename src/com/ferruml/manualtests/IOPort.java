package com.ferruml.manualtests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.hardware.Win32_PortConnector;

public class IOPort {
	public static void main(String[] args)
			throws ShellException, InterruptedException, IndexOutOfBoundsException, IOException {
		List<String> portIDs = null;
		Map<String, String> port = null;
		
		
			portIDs = Win32_PortConnector.getBaseboardPortID();
			System.out.println(portIDs);
			
			for(String currentID : portIDs) {
				port = Win32_PortConnector.getBaseboardPorts(currentID);
				for(Map.Entry<String, String> entry: port.entrySet())
					System.out.println(entry.getKey()+": "+entry.getValue());
				System.out.println();
			}
		
	}
}
