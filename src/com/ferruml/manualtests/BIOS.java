package com.ferruml.manualtests;

import java.io.IOException;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.hardware.Win32_BIOS;

public class BIOS {
	public static void main(String[] args)
			throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		Map<String, String> bios = null;

		bios = Win32_BIOS.getPrimaryBIOS();
		for (Map.Entry<String, String> entry : bios.entrySet())
			System.out.println(entry.getKey() + ": " + entry.getValue());

	}
}
