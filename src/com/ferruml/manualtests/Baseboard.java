package com.ferruml.manualtests;

import java.io.IOException;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.hardware.Win32_Baseboard;

public class Baseboard {
	public static void main(String[] args)
			throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		Map<String, String> baseboard = null;

		baseboard = Win32_Baseboard.getMotherboard();
		for (Map.Entry<String, String> entry : baseboard.entrySet())
			System.out.println(entry.getKey() + ": " + entry.getValue());

	}
}
