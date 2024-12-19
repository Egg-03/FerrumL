package com.ferruml.manualtests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.hardware.Win32_NetworkAdapter;

public class Network {
	public static void main(String[] args)
			throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		List<String> networkAdapterIDs = null;
		Map<String, String> networkAdapter = null;

		networkAdapterIDs = Win32_NetworkAdapter.getAdapterID();

		for (String currentID : networkAdapterIDs) {
			networkAdapter = Win32_NetworkAdapter.getNetworkAdapters(currentID);
			for (Map.Entry<String, String> entry : networkAdapter.entrySet())
				System.out.println(entry.getKey() + ": " + entry.getValue());
			System.out.println();
		}

	}
}
