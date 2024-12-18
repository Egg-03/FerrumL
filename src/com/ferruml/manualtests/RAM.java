package com.ferruml.manualtests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.hardware.Win32_PhysicalMemory;

public class RAM {
	public static void main(String[] args)
			throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		List<String> tags = null;
		Map<String, String> memory = null;

		tags = Win32_PhysicalMemory.getTag();
		System.out.println(tags);

		for (String currentTag : tags) {
			memory = Win32_PhysicalMemory.getMemory(currentTag);
			for (Map.Entry<String, String> currentMemory : memory.entrySet())
				System.out.println(currentMemory.getKey() + ": " + currentMemory.getValue());
			System.out.println();
		}
	}
}
