package com.ferruml.manualtests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.hardware.Win32_VideoController;

public class VideoController {
	public static void main(String[] args)
			throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		List<String> gpuIDs = null;
		Map<String, String> gpu = null;

		gpuIDs = Win32_VideoController.getGPUID();
		System.out.println(gpuIDs);

		for (String currentID : gpuIDs) {
			gpu = Win32_VideoController.getGPU(currentID);
			for (Map.Entry<String, String> entry : gpu.entrySet())
				System.out.println(entry.getKey() + ": " + entry.getValue());
			System.out.println();
		}
	}
}
