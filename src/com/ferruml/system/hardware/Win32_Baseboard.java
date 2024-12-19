package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

/**
 * This class contains methods that query Win32_Baseboard and
 * Win32_MotherboardDevice classes of WMI to fetch Motherboard information.
 * <p>
 * Fetches the following properties: Manufacturer, Model, Product, SerialNumber,
 * Version
 *
 * @author Egg-03
 * @version 1.3.1
 */
public class Win32_Baseboard {
	
	private static String classname = "Win32_Baseboard";
	private static String attributes = "Manufacturer, Model, Product, SerialNumber, Version";
	
	private Win32_Baseboard() {
		throw new IllegalStateException("Utility Class");
	}
	
	/**
	 * This method calls the
	 * {@link com.ferruml.formatter.wmic.WMIC#get(String, String)} function and
	 * passes the WMI Class name and the properties whose values we want to fetch, as
	 * parameters
	 *
	 * @return the Manufacturer, Model, Product, SerialNumber, Version details of
	 *         your Motherboard in the form of a {@link java.util.Map}
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
	public static Map<String,String> getMotherboard() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.get(classname, attributes);
	}
}
