package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

/**
 * This class contains methods that query the Win32_BIOS class of WMI and fetch
 * BIOS information.
 * <p>
 * Fetches the following properties and their values: Name, Caption,
 * Manufacturer, ReleaseDate, SMBIOSPResent, Status, Version, CurrentLanguage,
 * SMBIOSBIOSVersion
 *
 * @author Egg-03
 */
public class Win32_BIOS {

	private static String classname = "Win32_BIOS";
	private static String attributes = "Name, Caption, Manufacturer, ReleaseDate, SMBIOSPResent, Status, Version, CurrentLanguage, SMBIOSBIOSVersion";

	private Win32_BIOS() {
		throw new IllegalStateException("Utility Class");
	}

	/**
	 * Fetches Primary BIOS Information (If your motherboard has multiple BIOSes,
	 * the Main BIOS information will always be fetched). Internally calls
	 * {@link com.ferruml.formatter.wmic.WMIC#getWhere(String, String, String, String)}
	 * with the parameters being (class_name, "PrimaryBIOS", "True", attributes);
	 *
	 * @return a {@link java.util.Map} containing the properties and their values
	 *         mentioned in the class description, as key-value pairs
	 * @throws IOException               re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#getWhere(String, String, String, String)}
	 *                                   when there are I/O Errors during streaming
	 *                                   of data from and to Command Prompt and other
	 *                                   generated files
	 * @throws IndexOutOfBoundsException re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#getWhere(String, String, String, String)}
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
	public static Map<String, String> getPrimaryBIOS() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.getWhere(classname, "PrimaryBIOS", "true", attributes);
	}
}
