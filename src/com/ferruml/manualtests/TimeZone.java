package com.ferruml.manualtests;

import java.io.IOException;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.operatingsystem.Win32_TimeZone;

public class TimeZone {
	public static void main(String[] args)
			throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		Map<String, String> timeZone = null;

		timeZone = Win32_TimeZone.getOSTimeZone();
		for (Map.Entry<String, String> entry : timeZone.entrySet())
			System.out.println(entry.getKey() + ": " + entry.getValue());

	}
}
