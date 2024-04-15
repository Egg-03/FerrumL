package com.ferruml.examples;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import com.ferruml.system.operatingsystem.Win32_TimeZone;

public class TimeZone {
	public static void main(String[] args) {
		Map<String, String> timeZone = null;
		
		try {
			timeZone = Win32_TimeZone.getOSTimeZone();
			for(Map.Entry<String, String> entry: timeZone.entrySet())
				System.out.println(entry.getKey()+": "+entry.getValue());
			
		}catch(IOException | IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			timeZone = Collections.emptyMap();
			for(Map.Entry<String, String> entry: timeZone.entrySet())
				System.out.println(entry.getKey()+": "+entry.getValue());
		}
	}
}
