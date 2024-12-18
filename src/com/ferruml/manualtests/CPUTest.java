package com.ferruml.manualtests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.hardware.Win32_Processor;

public class CPUTest {
	public static void main(String[] args)
			throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		List<String> deviceIDs = null;
		Map<String, String> currentCPU = null;

		deviceIDs = Win32_Processor.getProcessorList();
		System.out.println(deviceIDs);

		for (String currentID : deviceIDs) {
			currentCPU = Win32_Processor.getCurrentProcessor(currentID);
			for (Map.Entry<String, String> entry : currentCPU.entrySet())
				System.out.println(entry.getKey() + ": " + entry.getValue());
			System.out.println();
		}

	}
}