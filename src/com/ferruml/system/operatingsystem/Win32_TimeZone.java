package com.ferruml.system.operatingsystem;

import java.io.IOException;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

/**
 * Fetches the OS Time-Zone information by querying WIn32_TimeZone class of WMI.
 * <p>
 * The following attributes are fetched: Caption, Bias, StandardName
 *
 * @author Egg-03
 * @version 1.3.1
 */
public class Win32_TimeZone {
	
	private static String classname = "Win32_TimeZone";
	private static String attributes = "Caption, Bias, StandardName";
	
	
	private Win32_TimeZone() {
		throw new IllegalStateException("Utility Class");
	}
	
	/**
	 * Retrieves the OS Time-zone
	 *
	 * @return a {@link java.util.Map} of Time-zone properties and values mentioned
	 *         in the class description
	 * @throws IOException               re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#get(String, String)}
	 *                                   when there are I/O Errors during streaming
	 *                                   of data from and to Command Prompt and other
	 *                                   generated files
	 * @throws IndexOutOfBoundsException re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#get(String, String)}
	 *                                   when there is a parsing error of data
	 *                                   fetched from Windows Command Prompt
	 * @throws ShellException            if any internal command used in the
	 *                                   Command Prompt throws errors
	 * @throws InterruptedException      if the thread waiting for the process to
	 *                                   exit, gets interrupted. When catching this
	 *                                   exception, you may re-throw it's
	 *                                   interrupted status by using
	 *                                   Thread.currentThread().interrupt();                                  
	 */
	public static Map<String, String> getOSTimeZone() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.get(classname, attributes);
	}
}
