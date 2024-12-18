package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

public class Win32_Baseboard {
	private static String classname = "Win32_Baseboard";
	private static String attributes = "Manufacturer, Model, Product, SerialNumber, Version";
	private Win32_Baseboard() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static Map<String,String> getMotherboard() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.get(classname, attributes);
	}
}
