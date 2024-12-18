package com.ferruml.manualtests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.hardware.Win32_Printer;

public class Printer {
	public static void main(String[] args)
			throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		List<String> printerIDs = null;
		Map<String, String> printer = null;

		printerIDs = Win32_Printer.getDeviceIDList();
		System.out.println(printerIDs);

		for (String currentID : printerIDs) {
			printer = Win32_Printer.getCurrentPrinter(currentID);
			for (Map.Entry<String, String> entry : printer.entrySet())
				System.out.println(entry.getKey() + ": " + entry.getValue());
			System.out.println();
		}
	}
}
