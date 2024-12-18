package com.ferruml.system.operatingsystem;

import java.io.IOException;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

public class Win32_TimeZone {
	private static String classname = "Win32_TimeZone";
	private static String attributes = "Caption, Bias, StandardName";
	
	private Win32_TimeZone() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static Map<String, String> getOSTimeZone() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.get(classname, attributes);
	}
}
